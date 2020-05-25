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

from random import randint
from ipaddress import ip_address, ip_network

from nbi.nbi_server import db_session
from db.db_models import *
from sbi import openstack_connector, wim_connector
import logging
from nbi.nbi_server import ra_client


def calculate_connectivity(connectivity_id, inner_graph, src_pe, dst_pe, req_bw, req_delay):
    """
    Calculating of the inter-NFVI-PoP connectivity (OPTION_B)

    :param connectivity_id
    :type connectivity_id: str
    :param inner_graph
    :type inner_graph: networkx graph
    :param src_pe
    :type src_pe: str
    :param dst_pe:
    :type dst_pe: str
    :param req_bw:
    :type req_bw: int
    :param req_delay:
    :type req_delay: int
    :rtype: List[object]
    """
    logger = logging.getLogger('cttc_rl.ro.calculate_connectivity')
    # list_of_wim is a parameter to be sent with connectivity_id to the ServiceDB
    list_of_wim_id = []
    for i, node in inner_graph.nodes(data=True):
        if node['type'] == "WIM":
            list_of_wim_id.append(node['name'])
    # logger.debug('List_of_wim: {}'.format(list_of_wim_id))
    # find the list of interWan and Gw2Pe links
    list_of_inter_wan_link = []
    list_of_gw_to_pe_link = []
    for i, j, edge in inner_graph.edges(data=True):
        edge['aPEId'] = inner_graph.node[i]['name']
        edge['zPEId'] = inner_graph.node[j]['name']
        if edge['type'] == "interWanLink":
            list_of_inter_wan_link.append(edge)
        elif edge['type'] == "Gw2PeLink":
            list_of_gw_to_pe_link.append(edge)
    logger.debug("List of interWanLinks: {}".format(list_of_inter_wan_link))
    logger.info("Retrieved list of interWanLinks")
    # retrieve the list of WIM (id of RL) involved in the LL
    topologies = []
    for wim in list_of_wim_id:
        wim_conn = wim_connector.create_connector(wim)
        topologies.append({"wim_id": wim, "topology": wim_conn.get_topology()})
    # logger.info("Topologies: {}".format(topologies))
    logger.info('Retrieved list of WIM involved in the LL.')
    # CALCULATE THE PATH THROUGH THE RA CLIENT
    ra_response = ra_client.compute_path(connectivity_id,
                                         list_of_inter_wan_link,
                                         topologies,
                                         src_pe,
                                         dst_pe,
                                         req_bw,
                                         req_delay)
    logger.info("RA Response: {}".format(str(ra_response)))
    return ra_response, list_of_gw_to_pe_link


def retrieve_connectivity(inner_graph, ll_request_attributes):
    """
    Retrieve of the inter-NFVI-PoP connectivity (OPTION_A)

    :param inner_graph
    :type inner_graph: networkx graph
    :param ll_request_attributes:
    :type ll_request_attributes: dict
    :rtype: List[object]
    """
    logger = logging.getLogger('cttc_rl.ro.retrieve_connectivity')
    # find the list of interWan and Gw2Pe links
    list_of_gw_to_pe_link = []
    for i, j, edge in inner_graph.edges(data=True):
        edge['aPEId'] = inner_graph.node[i]['name']
        edge['zPEId'] = inner_graph.node[j]['name']
        if edge['type'] == "Gw2PeLink":
            list_of_gw_to_pe_link.append(edge)
    logger.info("List of Gw2PeLinks: {}".format(list_of_gw_to_pe_link))
    logical_link_id = ll_request_attributes['logicalLinkAttributes']['logicalLinkId']
    logger.info("Logical Link Id: {}".format(logical_link_id))
    logical_link_in_db = db_session.query(Dbllinternfvipops).filter_by(logicalLinkId=logical_link_id).first()
    logger.info("Logical Link in DB: {}".format(logical_link_in_db))
    abs_algorithm_response = logical_link_in_db.pathLL
    logger.info("ABS Algo Response: {} ({})".format(abs_algorithm_response, type(abs_algorithm_response)))
    # convert the pathLL field from str to dict
    abs_algorithm_response = eval(abs_algorithm_response)
    logger.info("ABS Algo Response after conversion: {} ({})".format(abs_algorithm_response,
                                                                     type(abs_algorithm_response)))
    return abs_algorithm_response, list_of_gw_to_pe_link


def instantiate_connectivity(path, gw_pe_links, connectivity_id, src_vnf_ip, dst_vnf_ip, metadata_call, req_bw):
    """
    Instantiation of the inter-NFVI-PoP connectivity

    :param path
    :type path: dict
    :param gw_pe_links
    :param connectivity_id
    :type connectivity_id: str
    :param src_vnf_ip:
    :type src_vnf_ip: string
    :param dst_vnf_ip:
    :type dst_vnf_ip: string
    :param metadata_call
    :type metadata_call: dict
    :param req_bw:
    :type req_bw: int
    :rtype: List[object]
    """
    logger = logging.getLogger('cttc_rl.ro.instantiate_connectivity')
    # create the list of links for the call in order to be depicted in the Resource Viewer
    list_links_call = []
    # inter_wan_links used in the call
    inter_wan_links = path['inter_wan_links']
    if inter_wan_links is not None:
        for inter_wan_link in inter_wan_links:
            list_links_call.append({"source": inter_wan_link['a_pe_id'],
                                    "destination": inter_wan_link['z_pe_id'],
                                    "inter_link_type": "interWanLink"})
    # intra_wan_links used in the call
    intra_wan_links = path['wan_paths']
    for intra_wan_link in intra_wan_links:
        for element in intra_wan_link['wan_path_elements']:
            list_links_call.append({"source": element['a_node_id'],
                                    "destination": element['z_node_id'],
                                    "inter_link_type": "intraWanLink"})
    # logger.debug("list_links_call: {}".format(list_links_call))
    # Retrieve the VLAN through VNF IP ADDRESS and VL table
    list_cidr_vlan = [('192.168.' + str(vl.cidr) + '.0/24', vl.vlanId) for vl in db_session.query(Dbvirtuallinks).all()]
    # removing all the duplicates in the list
    list_cidr_vlan = list(set(list_cidr_vlan))
    vlan_id = ''
    for cidr_vlan in list_cidr_vlan:
        if ip_address(src_vnf_ip) in ip_network(cidr_vlan[0]) and ip_address(dst_vnf_ip) in ip_network(cidr_vlan[0]):
            vlan_id = cidr_vlan[1]
    if not vlan_id:
        logger.error("Something wrong in retrieving the VLAN.")
        raise KeyError("Something wrong in retrieving the VLAN.")
    else:
        logger.info("VLAN Id for the VNFs used: {}".format(vlan_id))

    # retrieve the list of CallId in VL table
    dict_used_call_id = {}
    interconnectivity_list = db_session.query(Dbinternfvipopconnectivity).all()
    if interconnectivity_list is not None:
        for interconnectivity in interconnectivity_list:
            dict_used_call_id.update(interconnectivity.callIdInvolved)
    # create a dict of new call to be instantiated
    dict_new_call_id = create_new_call_ids(dict_used_call_id)
    # Creating Overall calls for WIM in the PA Response
    list_of_involved_wim = []
    for wan in intra_wan_links:
        wan_id_int = int(wan['wim_id'])  # because in the Dbdomain the id is an int and in the path is a str
        list_of_involved_wim.append(wan_id_int)
        wim_conn = wim_connector.create_connector(wan_id_int)
        wim_conn_response = wim_conn.\
            create_call(callId=dict_new_call_id,
                        internal_path=wan['wan_path_elements'],
                        inter_wan_path=inter_wan_links,
                        edge_paths=gw_pe_links,
                        src_ip=src_vnf_ip,
                        dst_ip=dst_vnf_ip,
                        metadata_call=metadata_call,
                        vlan=vlan_id,
                        bw=req_bw)
        if wim_conn_response == 0:
            logger.info("Call '{}' Done".format(connectivity_id))
        else:
            error_message = "Something wrong in call for WIM: '{}'".format(wan['wim_id'])
            logger.error(error_message)
            # TODO #12 think about a rollback mechanism for instantiating LL (in case one WIM can't set up the call)
            raise KeyError(error_message)

    return list_of_involved_wim, list_links_call, dict_new_call_id


def delete_path(inter_nfvi_pop_connectivity_id, list_of_wim):
    """
    Deleting an inter-NFVI-PoP connectivity

    :param inter_nfvi_pop_connectivity_id
    :type inter_nfvi_pop_connectivity_id: str
    :param list_of_wim
    :type list_of_wim: list
    """
    logger = logging.getLogger('cttc_rl.ro.delete_path')
    # retrieve the list of COP call id to be deleted for a specific inter-nfvi-pop connectivity id
    dict_call_ids = db_session.query(Dbinternfvipopconnectivity)\
        .filter_by(interNfviPopConnectivityId=inter_nfvi_pop_connectivity_id).first().callIdInvolved
    logger.info("Dict of calls to be deleted: {}".format(dict_call_ids))
    for wim in list_of_wim:
        wim_int = int(wim)  # because in the Dbdomain the id is an int and in the path is a str
        # for each WIM send the delete request to corresponding WIM connector
        wim_conn = wim_connector.create_connector(wim_int)
        response = wim_conn.delete_call(dict_call_ids)
        if response != 0:
            raise KeyError("Some error in deleting call '{}' in WIM '{}'".format(inter_nfvi_pop_connectivity_id, wim))
    logger.info("InterNFVIPoP Connectivity ID: '{}' deleted from all the WIM involved".
                format(inter_nfvi_pop_connectivity_id))
    return None


def create_intrapop_net(body_request, metadata_in_body, network_in_db):
    """
    Creating an intra-NFVI-PoP network

    :param body_request: body request
    :param metadata_in_body: dict
    :param network_in_db: list
    :return body_create_net: dict
    """
    logger = logging.getLogger('cttc_rl.ro.create_intrapop_net')
    # create dictionary of metadata from subnet request
    metadata_subnet = {}
    for element in body_request.type_subnet_data.metadata:
        metadata_subnet[element['key']] = element['value']
    # print("metadata subnet: {}".format(metadata_subnet))
    # list of vlan and cidr already used in Db (removed the duplicates)
    list_of_vlan = list(set([n.vlanId for n in network_in_db]))
    list_of_cidr = list(set([n.cidr for n in network_in_db]))
    # filter the all() query to the ones with same elements in the request
    # networks_filtered = [n for n in network_in_db if n.vlName == body_request.network_resource_name and
    #                      n.serviceId == metadata_in_body['serviceId']]
    # update 2019-04-29: networks filtered only for network name (and not also for serviceId) in order to
    # develop service composition
    networks_filtered = [n for n in network_in_db if n.vlName == body_request.network_resource_name]
    body_create_net = {
        "name": body_request.network_resource_name,
        "vimId": metadata_in_body['vimId'],
        "floating_ips": eval(metadata_subnet['ip-floating-required'])  # converting str to bool (True/False)
    }
    # create a static route for network
    if "mon_cidr" in metadata_subnet:
        logger.info("'Monitoring CIDR' in the request. Static route is going to be created in the Network")
    pool_index = []
    if networks_filtered:
        # case of network with same name/serviceId is already present in the DB.
        for network in networks_filtered:
            if not network.vimId == metadata_in_body['vimId']:
                pool_index.append(network.pool)
            else:
                raise KeyError("Network already in DB VL!")
        # choose a new pool of IP address for the new vimId
        vlan_id = networks_filtered[0].vlanId
        # Update for Federation: added CIDR in intrapop network creation
        if bool(body_request.type_subnet_data.cidr) and \
                not int(body_request.type_subnet_data.cidr.split('.')[2]) == networks_filtered[0].cidr:
            logger.error("Something wrong with CIDR in request.")
            raise KeyError("Error in 'create_intrapop_net'. CIDR in request does not correspond in the RL db.")
        cidr_id = networks_filtered[0].cidr
        if bool(body_request.type_subnet_data.address_pool):
            net_pool_to_avoid_by_request = body_request.type_subnet_data.address_pool
            logger.debug("Pool to avoid {}".format(net_pool_to_avoid_by_request))
            pool_index.extend(body_request.type_subnet_data.address_pool)
        pool_index = sorted(pool_index)
        # loop to create the Pool index ID value
        while True:
            new_pool_index = randint(1, 12)
            if pool_index is None or new_pool_index not in pool_index:
                break
        logger.info("Network already created in other VIMs. VLAN '{}' and CIDR '192.168.{}.0/24'. New pool: '{}'".
                    format(vlan_id, cidr_id, new_pool_index))
        body_create_net["vlan_id"] = vlan_id
        body_create_net["CIDR"] = cidr_id
        body_create_net['pool'] = new_pool_index
    else:
        vlan = create_unique_net_vlan(list_of_vlan)
        if bool(body_request.type_subnet_data.cidr):
            # Update for Federation: added CIDR in intrapop network creation
            logger.debug("CIDR passed in the request. {}".format(body_request.type_subnet_data.cidr))
            cidr = int(body_request.type_subnet_data.cidr.split('.')[2])
        else:
            cidr = create_unique_net_cidr(list_of_cidr)
        if bool(body_request.type_subnet_data.address_pool):
            # Update for Federation: added Address Pool in intra_pop network creation
            net_pool_to_avoid_by_request = eval(body_request.type_subnet_data.address_pool)
            logger.debug("Pool to avoid {}".format(net_pool_to_avoid_by_request))
            pool_index.extend((eval(body_request.type_subnet_data.address_pool)))
            # loop to create the Pool index ID value
            while True:
                new_pool_index = randint(1, 12)
                if pool_index is None or new_pool_index not in pool_index:
                    break
        else:
            new_pool_index = 0
        logger.info("Network is going to be created. VLAN '{}', CIDR '192.168.{}.0/24', Pool: '{}'".
                    format(vlan, cidr, new_pool_index))
        body_create_net["vlan_id"] = vlan
        body_create_net["CIDR"] = cidr
        body_create_net['pool'] = new_pool_index
    net_ids = openstack_connector.create_os_networks(body_create_net, metadata_subnet)
    body_create_net['subnet_id'] = net_ids['subnet_id']
    body_create_net['router_id'] = net_ids['router_id']
    body_create_net['network_id'] = net_ids['network_id']
    return body_create_net


def create_unique_net_vlan(vlans_list):
    """
    Create unique network features: VLAN
    :param vlans_list: list

    """
    # loop to create the VLAN ID value
    while True:
        vlan_id = randint(1, 4094)
        if vlans_list is None or vlan_id not in vlans_list:
            break
    return vlan_id


def create_unique_net_cidr(cidrs_list):
    """
    Create unique network features: CIDR of IP address
    :param cidrs_list: list

    """
    # loop to create the CIDR value
    while True:
        cidr_id = randint(0, 255)
        if cidrs_list is None or cidr_id not in cidrs_list:
            break
    return cidr_id


def create_new_call_ids(dict_used_calls):
    """
    Create new call Ids for internfvipopconnectivityId
    :param dict_used_calls: list

    """
    four_call_id = {}
    for i in range(0, 4):
        while True:
            new_index = str(randint(2000, 9000))
            if new_index not in dict_used_calls:
                dict_used_calls[new_index] = []
                four_call_id[new_index] = []
                break
    return four_call_id


def delete_intrapop_net(vl_network):
    """
    Deleting an intra-NFVI-PoP network

    :param vl_network: vl DB row
    """
    logger = logging.getLogger('cttc_rl.ro.delete_intrapop_net')
    body_delete_net = {
        "name": vl_network.vlName,
        "vimId": vl_network.vimId,  # vim.values
        "floating_ips": vl_network.floatingIp,
        "subnet_id": vl_network.subnetId,
        "router_id": vl_network.routerId
    }
    if openstack_connector.delete_os_networks(body_delete_net):
        logger.info("VL '{}' deleted from VIM '{}'".format(body_delete_net['name'], body_delete_net['vimId']))
        return True
    else:
        raise KeyError("Error in deleting VL '{}'".format(body_delete_net['name']))
