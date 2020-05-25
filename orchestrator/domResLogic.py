# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Author: Luca Vettori

import networkx as nx
from networkx.readwrite import json_graph

from nbi.nbi_server import db_session
from db.db_models import *
from sbi import openstack_connector, wim_connector
import logging

from sqlalchemy.exc import IntegrityError


def get_aggregate(r_zone):
    """
    Get the aggregate resource for a list of nfvipops/zones.
    :param r_zone: list of nfvipops/zones to be queried
    :return: 0 ok, -1 error
    """
    logger = logging.getLogger('cttc_rl.domResLogic.get_aggregate')
    for zone in r_zone:
        vim = zone[0]
        pop = zone[1]
        r_zone_attr = zone[2]
        if r_zone_attr is not None:
            logger.info('Collecting for "{}/{}/{}"'.format(vim.name, pop.nfviPopId, r_zone_attr.zoneName))
            # IMPORTANT ASSUMPTION: 1 VIM / 1 NFVIPOP / 1 ZONE
            # SO THE PROGRAM ASKS DIRECTLY TO THE VIM WITHOUT SPECIFYING THE NFVIPOP AND ZONE
            os_resources = openstack_connector.retrieve_resource(vim)
            # updating the resourceAttributeDb with the last query to VIM
            try:
                r_zone_attr.availableMemory = os_resources['mem']['available']
                r_zone_attr.reservedMemory = os_resources['mem']['reserved']
                r_zone_attr.allocatedMemory = os_resources['mem']['allocated']
                r_zone_attr.totalMemory = os_resources['mem']['total']
                r_zone_attr.availableCpu = os_resources['cpu']['available']
                r_zone_attr.reservedCpu = os_resources['cpu']['reserved']
                r_zone_attr.allocatedCpu = os_resources['cpu']['allocated']
                r_zone_attr.totalCpu = os_resources['cpu']['total']
                r_zone_attr.availableStorage = os_resources['storage']['available']
                r_zone_attr.reservedStorage = os_resources['storage']['reserved']
                r_zone_attr.allocatedStorage = os_resources['storage']['allocated']
                r_zone_attr.totalStorage = os_resources['storage']['total']
                db_session.commit()
            except IntegrityError:
                logger.error('Something wrong with the database')
                db_session.rollback()
                return -1
    return 0


def selected_node(graph, name):
    """
    Method to select a specific node inside of the graph with name and type as key of node itself
    :param graph: nx.Graph()
    :param name: input value to be searched in the graph nodes
    :return: the first (in theory the only one) index of node with corresponding name index or the length of graph
    """
    select_node = [n for n, v in graph.nodes(data=True) if v['name'] == name]
    if not select_node:
        # logger.debug("graph.nodes, graph.number_of_nodes(): {}, {}".format(graph.nodes, graph.number_of_nodes()))
        # return graph.number_of_nodes()
        if not graph.nodes:
            # in case of empty graph
            return 0
        else:
            # return the index of the "last" node + 1, in order to avoid some "hole" problem in graph indexing
            return list(graph.nodes)[-1] + 1
    else:
        return select_node[0]


def add_call_to_graph(links, g):
    """
    Method to add a call to a graph
    :param links: list of links
    :param g: nx.Graph()
    :return: networkx graph
    """
    # definition of type of inter_link
    for link in links:
        if link['inter_link_type'] == "interWanLink":
            length = 100
        else:
            length = 50
        g.add_edge(selected_node(g, link['source']),
                   selected_node(g, link['destination']),
                   length=length,
                   type="call")
    return g


def decompose_wim(wim_id, g):
    """
    Method to create a networkx DiGraph due to bidirectional nature of flow rules
    :param g: nx.Graph()
    :param wim_id: id for wim
    :type wim_id: str
    :return: networkx graph
    """
    images = {
        "OF": "/static/images/switch.png",
        "OF-W": "/static/images/w-switch.png",
        "GMPLS": "/static/images/oxc.png",
        "accessPE": "/static/images/router.png",
        "access_metroPE": "/static/images/router.png",
        "metro_corePE": "/static/images/router.png",
        "corePE": "/static/images/router.png"
    }
    result = db_session.query(Dbdomainlist).filter_by(type="WIM", domain_id=wim_id).first()
    if result is None:
        raise AttributeError("WIM id '{}' not correct in DB".format(wim_id))
    wim_conn = wim_connector.create_connector(result.domain_id)
    topology = wim_conn.get_topology()
    for i, node in enumerate(topology['nodes']):
        if 'nodeId' in node:
            g.add_node(selected_node(g, node['nodeId']),
                       # g.number_of_nodes() + i,
                       name=node['nodeId'],
                       img=images[node['nodetype']],
                       size="10",
                       type=node['nodetype'],
                       group=int(wim_id))
    for node in topology['edges']:
        g.add_edge(selected_node(g, node['source']),
                   selected_node(g, node['target']),
                   length=75)
    return g


def decompose_stitching():
    """
    Return the networkx graph representation of the stitching table
    :return: networkx graph
    """
    logger = logging.getLogger('cttc_rl.domResLogic.decompose_stitching')
    stitching_links = db_session.query(Dbstitching).all()
    # create a networkx DiGraph due to bidirectional nature of flow rules
    g = nx.MultiDiGraph()
    for stitching_link in stitching_links:
        ingress_domain_element = db_session.query(Dbnfvipops)\
            .filter_by(nfviPopId=stitching_link.ingressElementId).first()
        # logger.info(stitching_link, ingress_domain_element)
        if ingress_domain_element is not None and \
                stitching_link.ingressElementAddress in ingress_domain_element.networkCE:
            # NFVIPOP/NFVIPOP-Fed case
            if ingress_domain_element.federated:
                img_link = "/static/images/pop_fed.png"
                element_type = "NFVIPOP-Fed"
            else:
                img_link = "/static/images/pop5.png"
                element_type = "NFVIPOP"
            g.add_node(selected_node(g, stitching_link.ingressElementId),
                       name=stitching_link.ingressElementId,
                       type=element_type,
                       img=img_link,
                       size="15",
                       group=int(stitching_link.ingressElementId))
            g.add_node(selected_node(g, stitching_link.ingressElementAddress),
                       name=stitching_link.ingressElementAddress,
                       type="GW/PE",
                       img="/static/images/router.png",
                       size="7.5",
                       group=int(stitching_link.ingressElementId))
            g.add_edge(selected_node(g, stitching_link.ingressElementId),
                       selected_node(g, stitching_link.ingressElementAddress),
                       length=15,
                       type="NFVIPOP_GW_Link")
            ingress_type = "NVIPOP/GW"
        else:
            # WIM case
            g.add_node(selected_node(g, stitching_link.ingressElementId),
                       name=stitching_link.ingressElementId,
                       type="WIM",
                       img="/static/images/control.png",
                       size="15",
                       group=int(stitching_link.ingressElementId))
            g.add_node(selected_node(g, stitching_link.ingressElementAddress),
                       name=stitching_link.ingressElementAddress,
                       type="GW/PE",
                       img="/static/images/router.png",
                       size="7.5",
                       group=int(stitching_link.ingressElementId))
            g.add_edge(selected_node(g, stitching_link.ingressElementId),
                       selected_node(g, stitching_link.ingressElementAddress),
                       length=15,
                       type="WIM_PE_Link")
            ingress_type = "WIM/PE"
        egress_domain_element = db_session.query(Dbnfvipops).filter_by(nfviPopId=stitching_link.egressElementId).first()
        if egress_domain_element is not None and stitching_link.egressElementAddress in egress_domain_element.networkCE:
            # NFVIPOP/NFVIPOP-Fed case
            if egress_domain_element.federated:
                img_link = "/static/images/pop_fed.png"
                element_type = "NFVIPOP-Fed"
            else:
                img_link = "/static/images/pop5.png"
                element_type = "NFVIPOP"
            g.add_node(selected_node(g, stitching_link.egressElementAddress),
                       name=stitching_link.egressElementAddress,
                       type="GW/PE",
                       img="/static/images/router.png",
                       size="7.5",
                       group=int(stitching_link.egressElementId))
            g.add_node(selected_node(g, stitching_link.egressElementId),
                       name=stitching_link.egressElementId,
                       type=element_type,
                       img=img_link,
                       size="15",
                       group=int(stitching_link.egressElementId))
            g.add_edge(selected_node(g, stitching_link.egressElementAddress),
                       selected_node(g, stitching_link.egressElementId),
                       length=15,
                       type="NFVIPOP_GW_Link")
            egress_type = "NVIPOP/GW"
        else:
            # WIM case
            g.add_node(selected_node(g, stitching_link.egressElementAddress),
                       name=stitching_link.egressElementAddress,
                       type="GW/PE",
                       img="/static/images/router.png",
                       size="7.5",
                       group=int(stitching_link.egressElementId))
            g.add_node(selected_node(g, stitching_link.egressElementId),
                       name=stitching_link.egressElementId,
                       type="WIM",
                       img="/static/images/control.png",
                       size="15",
                       group=int(stitching_link.egressElementId))
            g.add_edge(selected_node(g, stitching_link.egressElementAddress),
                       selected_node(g, stitching_link.egressElementId),
                       length=15,
                       type="WIM_PE_Link")
            egress_type = "WIM/PE"
        # definition of type of inter_link
        if ingress_type == "NVIPOP/GW" and egress_type == "WIM/PE":
            inter_link_type = "Gw2PeLink"
        elif ingress_type == "WIM/PE" and egress_type == "NVIPOP/GW":
            inter_link_type = "Gw2PeLink"
        elif ingress_type == "WIM/PE" and egress_type == "WIM/PE":
            inter_link_type = "interWanLink"
        else:
            raise KeyError("Error in decomposing stiching. GW-to-GW connection!")

        g.add_edge(selected_node(g, stitching_link.ingressElementAddress),
                   selected_node(g, stitching_link.egressElementAddress),
                   length=100,
                   aWimId=stitching_link.ingressElementId,
                   zWimId=stitching_link.egressElementId,
                   aLinkId=stitching_link.aLinkId,
                   zLinkId=stitching_link.zLinkId,
                   type=inter_link_type)

    logger.debug(json_graph.node_link_data(g))
    logger.info("Decomposed the stitching table")
    return g


def reducing_graph(graph, first_name, second_name):
    """
    Return the sub-graph between two node (with type GW/PW)
    :param graph: networkx graph to be sub-divided
    :param first_name: str: name of first node
    :param second_name: str: name of seconde name
    :return: networkx graph
    """
    logger = logging.getLogger('cttc_rl.domResLogic.reducing_graph')
    paths_between_generators = nx.all_simple_paths(graph,
                                                   source=selected_node(graph, first_name),
                                                   target=selected_node(graph, second_name))
    nodes_involved = {node for path in paths_between_generators for node in path}
    sub_graph = graph.subgraph(nodes_involved)
    logger.debug(json_graph.node_link_data(sub_graph))
    return sub_graph
