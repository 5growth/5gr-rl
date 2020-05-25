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

from itertools import combinations
from random import randint

from nbi.nbi_server import db_session, ra_client, OPTION_PA_ALGORITHM, OPTION_A_BOOT_QUOTES
from db.db_models import *
import logging
from orchestrator import domResLogic
from sbi import wim_connector

# Nfvi_pops part
from nbi.swagger_server.models.nfvi_pops_inner import NfviPopsInner
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes import NfviPopsInnerNfviPopAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_network_connectivity_endpoint import \
    NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_resource_zone_attributes import \
    NfviPopsInnerNfviPopAttributesResourceZoneAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_memory_resource_attributes import \
    NfviPopsInnerNfviPopAttributesMemoryResourceAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_cpu_resource_attributes import \
    NfviPopsInnerNfviPopAttributesCpuResourceAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_storage_resource_attributes import \
    NfviPopsInnerNfviPopAttributesStorageResourceAttributes
# logicalLinkinterNfviPops part
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner import LogicalLinkInterNfviPopsInner
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links import \
    LogicalLinkInterNfviPopsInnerLogicalLinks
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links_network_qo_s import \
    LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS


def get_abstraction(request_from_rest_api=True):
    """
    Get the abstracted resources for the RL
    :param request_from_rest_api: boolean representing if request comes from REST API or GUI (if False)
    :return: nfvi_pops: list of NfviPoPs
            lls_list: list of Logical Links
    """
    logger = logging.getLogger('cttc_rl.absLogic.get_abstraction')
    r_zone_attr_list = db_session.query(Dbresourceattributes).all()
    nfvi_pops = calc_abs_compute(r_zone_attr_list)
    # define a list of GW that belongs to "local" domain of RL
    list_gw_not_federated = [net_gw.net_gw_ip_address for pop in nfvi_pops
                             for net_gw in pop.nfvi_pop_attributes.network_connectivity_endpoint]
    if OPTION_PA_ALGORITHM == "OPTION_A" and OPTION_A_BOOT_QUOTES is False and request_from_rest_api:
        pre_calculate_paths(list_gw_not_federated, ra_client.k_paths_ina)

    # logicalLinkInterNfviPops part
    lls_list = []
    lls = db_session.query(Dbllinternfvipops).all()
    for ll in lls:
        if ll.srcGwIpAddress in list_gw_not_federated and ll.dstGwIpAddress in list_gw_not_federated:
            ll_inter_pops_ll_qos = LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS(
                link_cost_value=ll.linkCostValue,
                link_cost=ll.linkCost,
                link_delay_value=ll.linkDelayValue,
                link_delay=ll.linkDelay
            )
            ll_inter_pops_ll = LogicalLinkInterNfviPopsInnerLogicalLinks(
                logical_link_id=ll.logicalLinkId,
                total_bandwidth=ll.totalBandwidth,
                available_bandwidth=ll.availableBandwidth,
                network_qo_s=ll_inter_pops_ll_qos,
                src_gw_ip_address=ll.srcGwIpAddress,
                local_link_id=ll.localLinkId,
                dst_gw_ip_address=ll.dstGwIpAddress,
                remote_link_id=ll.remoteLinkId,
                network_layer=ll.networkLayer,
                inter_nfvi_pop_network_type=ll.interNfviPopNetworkType,
                inter_nfvi_pop_network_topology=ll.interNfviPopNetworkTopology

            )
            lls_list.append(LogicalLinkInterNfviPopsInner(logical_links=ll_inter_pops_ll))
            logger.info("Collected all info for {}".format(ll))
        else:
            # represents a LL to/from a Federated NFVI-PoP
            logger.info("{} is for federated domain".format(ll))
    return nfvi_pops, lls_list


def pre_calculate_paths(list_gw_not_federated, nr_logical_links):
    """
    Pre calculation of the underlying paths
    :param list_gw_not_federated: list of GWs not Federated
    :param nr_logical_links: number of Logical Links to be calculated
    """
    logger = logging.getLogger('cttc_rl.absLogic.pre_calculate_paths')
    # delete all the entries in LL tables
    lls_deleted = db_session.query(Dbllinternfvipops).delete()
    logger.info("Deleted {} rows in LL table".format(lls_deleted))
    db_session.commit()
    # decompose the stitching table in order to retrieve the total graph
    # creating a nx graph from stitching table
    total_graph = domResLogic.decompose_stitching()
    # list_of_wim is a parameter to be sent with connectivity_id to the ServiceDB
    list_of_wim_id = []
    for i, node in total_graph.nodes(data=True):
        if node['type'] == "WIM":
            list_of_wim_id.append(node['name'])
    # logger.info('List_of_wim: {}'.format(list_of_wim_id))
    # find the list of interWan and Gw2Pe links
    list_of_inter_wan_link = []
    for i, j, edge in total_graph.edges(data=True):
        edge['aPEId'] = total_graph.node[i]['name']
        edge['zPEId'] = total_graph.node[j]['name']
        if edge['type'] == "interWanLink":
            list_of_inter_wan_link.append(edge)
    logger.debug("List of interWanLinks: {}".format(list_of_inter_wan_link))
    # logger.info("Retrieved list of interWanLinks")
    topologies = []
    # retrieve topologies for all WIM involved
    for wim in list_of_wim_id:
        wim_conn = wim_connector.create_connector(wim)
        topologies.append({"wim_id": wim, "topology": wim_conn.get_topology()})
    logger.debug('Retrieved list of WIM involved in the LL: {}'.format(topologies))
    # find all the pairs of GWs (simple combination)
    list_of_pair_gws = list(combinations(list_gw_not_federated, 2))
    logger.info("List of pair of GWs: {}".format(list_of_pair_gws))
    list_of_pair_gw_pes = []
    for index_ll, couple_gws in enumerate(list_of_pair_gws):
        # create logical link id sequentially and not random
        logical_link_id = 100000 + (index_ll + 1) * 10000
        logger.info("Pair of GWs: {}, {} to look for the LL".format(*couple_gws))
        # Reducing the graph between Src Gw and Dst Gw
        src_gw, dst_gw = couple_gws
        # Reducing the graph between the two GWs (in order to remove the NFVIPOP connected to the GW
        # In this way it remains only one adjacent node connected to the GW (and for assumption is a PE)
        inner_graph = domResLogic.reducing_graph(total_graph, src_gw, dst_gw)
        # retrieve the index of source and destination GWs in the graph
        node_src_gw = [x for x, y in inner_graph.nodes(data=True) if y['name'] == src_gw]
        node_dst_gw = [x for x, y in inner_graph.nodes(data=True) if y['name'] == dst_gw]
        # find adjacents node (PEs) of SRC and DST GW -> having reduced the graph is only one node i.e. a PE
        adjacents_to_src_gw = [n for n in inner_graph.neighbors(node_src_gw[0])]
        adjacents_to_dst_gw = [n for n in inner_graph.neighbors(node_dst_gw[0])]
        # ASSUMPTION: ONE PE CONNECTED TO ONE GW
        src_pe_id = inner_graph.node[adjacents_to_src_gw[0]]['name']
        dst_pe_id = inner_graph.node[adjacents_to_dst_gw[0]]['name']
        logger.info("Pair of PEs associated to GWs: {}, {}".format(src_pe_id, dst_pe_id))
        local_link_ids = []
        remote_link_ids = []
        logical_link_ids_f = []
        logical_link_ids_b = []
        for i in range(nr_logical_links):
            # TODO #6 check if necessary an unique local_link_id and remote_link_id
            local_link_id = randint(1000, 9998)
            local_link_ids.append(local_link_id)
            remote_link_ids.append(local_link_id + 1)
            # create LLids
            # logical_link_id = create_logical_link_id()
            logical_link_ids_f.append(str(logical_link_id + i) + '_f')
            logical_link_ids_b.append(str(logical_link_id + i) + '_b')
        list_of_pair_gw_pes.append((src_gw, src_pe_id,
                                    dst_pe_id, dst_gw,
                                    logical_link_ids_f,
                                    local_link_ids, remote_link_ids))
        list_of_pair_gw_pes.append((dst_gw, dst_pe_id,
                                    src_pe_id, src_gw,
                                    logical_link_ids_b,
                                    remote_link_ids, local_link_ids))
    # PRE-CALCULATING A PATH THROUGH THE PA CLIENT FOR THE LOGICAL LINK
    ra_abs_response = ra_client.compute_abs_path(inter_wan_links=list_of_inter_wan_link,
                                                 list_of_topology=topologies,
                                                 list_src_dst_pes=list_of_pair_gw_pes)
    logger.info("ABS Response calculated!")
    logger.debug("RA ABS Response: {}".format(str(ra_abs_response)))
    # HP: the RA algorithm gives the same number of response that pairs of PEs
    for pair_gw_pes, response in zip(list_of_pair_gw_pes, ra_abs_response):
        # creation of a number of LLs corresponding to the k_paths_ina (default 3)
        for i, resp in enumerate(response.response):
            db_session.add(Dbllinternfvipops(logicalLinkId=pair_gw_pes[4][i],
                                             totalBandwidth=resp.req_bw/1000000.0,  # in MB
                                             availableBandwidth=resp.req_bw/1000000.0,  # in MB
                                             linkCostValue=resp.path_cost,
                                             linkCost='cost',
                                             linkDelayValue=resp.latency,
                                             linkDelay='delay',
                                             srcGwIpAddress=pair_gw_pes[0],
                                             localLinkId=pair_gw_pes[5][i],
                                             dstGwIpAddress=pair_gw_pes[3],
                                             remoteLinkId=pair_gw_pes[6][i],
                                             networkLayer='L2',
                                             interNfviPopNetworkType='',
                                             interNfviPopNetworkTopology='',
                                             pathLL=str(resp)))
            logger.info("Added LL '{}' to DB.".format(pair_gw_pes[4][i]))
    db_session.commit()


def get_fed_abstraction():
    """
    Get the abstracted resources for a federated domain
    :return: fed_nfvi_pops: list of Federated NfviPoPs
            fed_lls_list: list of Federated Logical Links
    """
    logger = logging.getLogger('cttc_rl.absLogic.get_fed_abstraction')
    fed_pops = []
    for fed in db_session.query(Dbnfvipops).filter_by(federated=True).all():
        # building Nfvipop Attributes part
        nfvi_pop_attributes = NfviPopsInnerNfviPopAttributes(
            geographical_location_info=fed.geographicalLocationInfo,
            # vim_id=pop.vimId,  # removed because Federated NFVI-PoP has no VIM associated
            network_connectivity_endpoint=[
                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint(net_gw_ip_address=gtw_ip)
                for gtw_ip in fed.networkCE],
            nfvi_pop_id=fed.nfviPopId,
            federated_vim_id=fed.federatedVimId,  # added to extent the call to federation
            resource_zone_attributes=[]  # empty for default in our case
        )
        fed_pops.append(NfviPopsInner(nfvi_pop_attributes=nfvi_pop_attributes))

    # define a list of federated GW
    list_gw_federated = [net_ce.net_gw_ip_address for pop in fed_pops
                         for net_ce in pop.nfvi_pop_attributes.network_connectivity_endpoint]
    # logger.info("List of Federated GWs: {}".format(list_gw_federated))
    fed_lls_list = []
    fed_lls = db_session.query(Dbllinternfvipops).all()
    for ll in fed_lls:
        if ll.srcGwIpAddress in list_gw_federated or ll.dstGwIpAddress in list_gw_federated:
            ll_inter_pops_ll_qos = LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS(
                link_cost_value=ll.linkCostValue,
                link_cost=ll.linkCost,
                link_delay_value=ll.linkDelayValue,
                link_delay=ll.linkDelay
            )
            ll_inter_pops_ll = LogicalLinkInterNfviPopsInnerLogicalLinks(
                logical_link_id=ll.logicalLinkId,
                total_bandwidth=ll.totalBandwidth,
                available_bandwidth=ll.availableBandwidth,
                network_qo_s=ll_inter_pops_ll_qos,
                src_gw_ip_address=ll.srcGwIpAddress,
                local_link_id=ll.localLinkId,
                dst_gw_ip_address=ll.dstGwIpAddress,
                remote_link_id=ll.remoteLinkId,
                network_layer=ll.networkLayer,
                inter_nfvi_pop_network_type=ll.interNfviPopNetworkType,
                inter_nfvi_pop_network_topology=ll.interNfviPopNetworkTopology

            )
            fed_lls_list.append(LogicalLinkInterNfviPopsInner(logical_links=ll_inter_pops_ll))
            logger.info("Collected info for {} federated".format(ll))
        else:
            # represents a LL to/from a Federated NFVI-PoP
            logger.debug("{} is not for federated domain".format(ll))
    return fed_pops, fed_lls_list


def calc_abs_compute(r_zone_attr_list):
    """
    Calculating the abstraction for a list of resource_attributes (VIM/NfviPoP/zoneId)
    :param: r_zone_attr_list: list of resource_zone_attributes
    :return: nfvi_pops: list of NfviPoPs
    """
    logger = logging.getLogger('cttc_rl.absLogic.calc_abs_compute')
    nfvi_pops = []
    if r_zone_attr_list is not None:
        for r_zone_attr in r_zone_attr_list:
            pop = db_session.query(Dbnfvipops).filter_by(nfviPopId=r_zone_attr.nfviPopId).first()
            if pop is not None:
                vim = db_session.query(Dbdomainlist).filter_by(domain_id=pop.vimId).first()
                if vim is not None:
                    res_id = NfviPopsInnerNfviPopAttributesResourceZoneAttributes(
                        zone_id=r_zone_attr.zoneId,
                        zone_name=r_zone_attr.zoneName,
                        zone_state=r_zone_attr.zoneState,
                        zone_property=r_zone_attr.zoneProperty,
                        metadata=r_zone_attr.metadata_resourceattributes,
                        memory_resource_attributes=NfviPopsInnerNfviPopAttributesMemoryResourceAttributes(
                            available_capacity=r_zone_attr.availableMemory,
                            reserved_capacity=r_zone_attr.reservedMemory,
                            allocated_capacity=r_zone_attr.allocatedMemory,
                            total_capacity=r_zone_attr.totalMemory
                        ),
                        cpu_resource_attributes=NfviPopsInnerNfviPopAttributesCpuResourceAttributes(
                            available_capacity=r_zone_attr.availableCpu,
                            reserved_capacity=r_zone_attr.reservedCpu,
                            allocated_capacity=r_zone_attr.allocatedCpu,
                            total_capacity=r_zone_attr.totalCpu
                        ),
                        storage_resource_attributes=NfviPopsInnerNfviPopAttributesStorageResourceAttributes(
                            available_capacity=r_zone_attr.availableStorage,
                            reserved_capacity=r_zone_attr.reservedStorage,
                            allocated_capacity=r_zone_attr.allocatedStorage,
                            total_capacity=r_zone_attr.totalStorage

                        )
                    )
                    nfvi_pop_already_existing = False
                    for nfvi_pop in nfvi_pops:
                        if nfvi_pop.nfvi_pop_attributes.vim_id == str(vim.domain_id) and \
                                nfvi_pop.nfvi_pop_attributes.nfvi_pop_id == pop.nfviPopId:
                            nfvi_pop_already_existing = True
                            nfvi_pop.nfvi_pop_attributes.resource_zone_attributes.append(res_id)
                            break

                    if not nfvi_pop_already_existing:
                        # building the response for Nfvipop part
                        nfvi_pop_attributes = NfviPopsInnerNfviPopAttributes(
                            geographical_location_info=pop.geographicalLocationInfo,
                            vim_id=pop.vimId,
                            network_connectivity_endpoint=[
                                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint(net_gw_ip_address=gtw_ip)
                                for
                                gtw_ip in pop.networkCE],
                            nfvi_pop_id=pop.nfviPopId,
                            resource_zone_attributes=[res_id]
                        )
                        nfvi_pops.append(NfviPopsInner(nfvi_pop_attributes=nfvi_pop_attributes))
                else:
                    logger.debug('Pop "{}" has not a valid VimId')
                logger.info("Collected info for NFVI-POP '{}' ({})".format(pop.nfviPopId, pop.geographicalLocationInfo))
            else:
                logger.debug('No NfviPop registered for ZoneId: {}'.format(r_zone_attr.zoneId))
    else:
        logger.debug('No ZoneId registered')
    return nfvi_pops


def decompose_ll(logical_link):
    """
    Decompose the LL (to be instantiated)
    :param logical_link: LL to be instantiated
    :return
    """
    # verify the LL to be instantiated really exists in the DB of all possible LL
    ll = db_session.query(Dbllinternfvipops).filter_by(
        logicalLinkId=logical_link['logicalLinkAttributes']['logicalLinkId'],
        srcGwIpAddress=logical_link['logicalLinkAttributes']['srcGwIpAddress'],
        localLinkId=logical_link['logicalLinkAttributes']['localLinkId'],
        dstGwIpAddress=logical_link['logicalLinkAttributes']['dstGwIpAddress'],
        remoteLinkId=logical_link['logicalLinkAttributes']['remoteLinkId']).first()
    if ll is not None:
        # getting the src_gw and dst_gw
        src_gw = logical_link['logicalLinkAttributes']['srcGwIpAddress']
        dst_gw = logical_link['logicalLinkAttributes']['dstGwIpAddress']
        # getting the src and dst NfviPoP associated to upper GW
        # query in the Dbnfvipops for networkCE field (that is a list)
        src_nfvi_pop = None
        for pop in db_session.query(Dbnfvipops).all():
            if src_gw in pop.networkCE:
                src_nfvi_pop = pop
                break
        # query in the Dbnfvipops for networkCE field (that is a list)
        dst_nfvi_pop = None
        for pop in db_session.query(Dbnfvipops).all():
            if dst_gw in pop.networkCE:
                dst_nfvi_pop = pop
                break
        return [src_nfvi_pop.nfviPopId, src_gw, dst_nfvi_pop.nfviPopId, dst_gw]
    else:
        raise KeyError("LL decomposition failed. Check the list of Logical Links in the request.")


def create_logical_link_id():
    """
    Create unique logical link id
    :return ll_id : unique Logical Link Id (str)

    """
    logger = logging.getLogger('cttc_rl.absLogic.create_logical_link_id')
    # loop to create the Logical Link ID value
    while True:
        ll_id = str(randint(100000, 200000))
        # only check if exist the forward LL
        ll_id_f = ll_id + '_f'
        if db_session.query(Dbllinternfvipops).filter_by(logicalLinkId=ll_id_f).first() is None:
            break
    logger.info("Created the Logical Link id: '{}'".format(ll_id))
    return ll_id
