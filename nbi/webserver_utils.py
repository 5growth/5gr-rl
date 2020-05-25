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
from orchestrator.domResLogic import decompose_stitching, decompose_wim, add_call_to_graph
import networkx as nx
from networkx.readwrite import json_graph


def resource_view(call_id=None):
    """
    Return the node-link format of the Resource view of RL
    :param call_id: list of links (representing an entire call) to be converted in networkx graph
    :return: json graph format to be handled by D3 Javascript Element in rendered html page
    """
    resource_graph = decompose_stitching()
    list_wim = []
    # comment the following part in case you want to represent WIM as entity
    for i, n in resource_graph.nodes(data=True):
        if n['type'] == "WIM":
            list_wim.append((i, n['name']))
    for wim in list_wim:
        resource_graph.remove_node(wim[0])
        resource_graph = decompose_wim(wim[1], resource_graph)
    # add the call (edges) to the resource graph
    if call_id:
        resource_graph = add_call_to_graph(call_id, resource_graph)
    return json_graph.node_link_data(resource_graph)


def wim_resource_view(wim_id):
    """
    Return the node-link format of the Resource view for a specific WIM of RL
    :param wim_id: identier of WIM
    :return: json graph format to be handled by D3 Javascript Element in rendered html page
    """
    wim_resource_graph = decompose_wim(wim_id, nx.DiGraph())
    return json_graph.node_link_data(wim_resource_graph)


def abstracted_view(pops, fed_pops, lls):
    """
    Return the node-link format of the Abstracted view of RL
    :param pops: list of nfvipops
    :param fed_pops: list of federated nfvipops
    :param lls: list of logical links
    :return: json graph format to be handled by D3 Javascript Element in rendered html page
    """
    # create a nx.MultiGraph (allow to have multiple links between the same 2 nodes
    g = nx.MultiGraph()
    # dealing with NfviPops
    for pop in pops:
        current_node_id = g.number_of_nodes()
        # add a node to nx.Graph for every NFVI-PoP
        for resource_zone_attribute in pop.nfvi_pop_attributes.resource_zone_attributes:
            # ASSUMPTION: 1 ZONEID FOR NFVIPOP
            # even though another zoneId is configured, it will not appear in the abs graph
            g.add_node(current_node_id,
                       name=pop.nfvi_pop_attributes.nfvi_pop_id,
                       zone_id=resource_zone_attribute.zone_id,
                       zone_name=resource_zone_attribute.zone_name,
                       gateway=pop.nfvi_pop_attributes.network_connectivity_endpoint,
                       geo_location=pop.nfvi_pop_attributes.geographical_location_info,
                       type="NFVIPOP",
                       img="static/images/pop5.png",
                       group=1)
            for i, network_ce in enumerate(pop.nfvi_pop_attributes.network_connectivity_endpoint):
                g.add_node(current_node_id+i+1,
                           name=network_ce.net_gw_ip_address,
                           type="GATEWAY",
                           img="static/images/router.png",
                           group=2)
                g.add_edge(current_node_id, current_node_id+i+1, length=20)
    # dealing with Federated NfviPops
    for fed_pop in fed_pops:
        current_node_id = g.number_of_nodes()
        g.add_node(current_node_id,
                   name=fed_pop.nfvi_pop_attributes.nfvi_pop_id,
                   geo_location=fed_pop.nfvi_pop_attributes.geographical_location_info,
                   type="NFVIPOP-Fed",
                   img="static/images/pop_fed.png",
                   group=1)
        for i, network_ce in enumerate(fed_pop.nfvi_pop_attributes.network_connectivity_endpoint):
            g.add_node(current_node_id+i+1,
                       name=network_ce.net_gw_ip_address,
                       type="GATEWAY",
                       img="static/images/router.png",
                       group=2)
            g.add_edge(current_node_id, current_node_id+i+1, length=20)
    # dealing with Logical Links
    for ll in lls:
        replicated = False
        source_node = -1
        destination_node = -1
        for node in g:
            # logger.debug("g.nodes[node]['gateway']: {}".format(g.nodes[node]['gateway']))
            if g.nodes[node]['type'] == "GATEWAY" and g.nodes[node]["name"] == ll.logical_links.src_gw_ip_address:
                source_node = node
            # if 'gateway' in g.nodes[node] and g.nodes[node]['gateway'] == ll.logical_links.src_gw_ip_address:
            #     source_node = node
            if g.nodes[node]['type'] == "GATEWAY" and g.nodes[node]["name"] == ll.logical_links.dst_gw_ip_address:
                destination_node = node
            # if 'gateway' in g.nodes[node] and g.nodes[node]['gateway'] == ll.logical_links.dst_gw_ip_address:
            #     destination_node = node
        # if case there is an edge
        if source_node != -1 and destination_node != -1:
            # not displaying one of 2 bidirectional links
            # ASSUMPTION: Added using llid_f and llid_b to distinguish both directions of LL
            for i, o, e in g.edges(data=True):
                if 'llid' in e and e['llid'] == ll.logical_links.logical_link_id.split("_")[0]:
                    replicated = True
            if not replicated:
                g.add_edge(source_node,
                           destination_node,
                           llid=ll.logical_links.logical_link_id.split("_")[0],
                           length=200)
    return json_graph.node_link_data(g)  # node-link format to serialize
