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
import re
from uuid import uuid4

from nbi.nbi_server import db_session, OPTION_PA_ALGORITHM, OPTION_A_BOOT_QUOTES
from db.db_models import *
from nbi.swagger_server.models.inter_nfvi_pop_connnectivity_id_list_inner import InterNfviPopConnnectivityIdListInner
import logging
# import orchestrator.domResLogic as domResLogic
# import orchestrator.absLogic as absLogic
# import orchestrator.ro as ro
from orchestrator import domResLogic, absLogic, ro

# Nfvi_pops part
from nbi.swagger_server.models.inline_response201 import InlineResponse201

from sqlalchemy.exc import IntegrityError


def get_nfvipops(vims):
    """
    Get the list of NfviPop/ZoneId elements for all the VIMs.
    :param vims: list of VIMs from a sqlalchemy table
    :return: zone_list: list of nfvipops/zones to be queried
    """
    logger = logging.getLogger('cttc_rl.orch.get_nfvipops')
    zone_list = []
    if vims is not None:
        for vim in vims:
            # logger.info('Collecting for "{}"'.format(vim.name))
            # Retrieve all the NfviPoP associated to the VIM
            # PRELIMINARY ASSUMPTION: 1 NFVI_POP
            pops = db_session.query(Dbnfvipops).filter_by(vimId=vim.domain_id).all()
            for pop in pops:
                if pop is not None:
                    logger.info('Collecting for PoP: "{}" ("{}")'.format(pop.nfviPopId, pop.geographicalLocationInfo))
                    # Retrieve all the zoneIDs associated to the NFVIPoP
                    # PRELIMINARY ASSUMPTION: 1 ZONE_ID
                    r_zone_attr_list = db_session.query(Dbresourceattributes).filter_by(
                        nfviPopId=pop.nfviPopId).all()
                    for r_zone_attr in r_zone_attr_list:
                        zone_list.append((vim, pop, r_zone_attr))
    return zone_list


def retrieve_rl_resources():
    """
    Retrieve all the Resources (compute and networking) for the RL.
    :return: nfvi_pops
    :return: lls_list
    """
    logger = logging.getLogger('cttc_rl.orch.retrieve_rl_resources')
    # get the list of VIMs from DB
    vims = db_session.query(Dbdomainlist).filter_by(type="VIM").all()
    zones = get_nfvipops(vims)
    logger.info("Retrieved all the list of VIM/NfviPoP/Zone elements")
    if domResLogic.get_aggregate(zones) != -1:
        return absLogic.get_abstraction()
    else:
        message = "Error in retrieving RL resources"
        logger.error(message)
        raise TypeError(message)


def retrieve_rl_federated_resources():
    """
    Retrieve all the Federated Resources (compute and networking) for the RL.
    :return: nfvi_pops
    :return: lls_list
    """
    return absLogic.get_fed_abstraction()


def create_connectivity(body, request_from_rest_api):
    """Create inter-NfviPop Connectivity

    :param body: Create inter-NfviPop Connectivity
    :type body: InterNfviPopConnectivityRequest
    :param request_from_rest_api: request comes from REST API or Web GUI
    :type request_from_rest_api: bool
    """
    logger = logging.getLogger('cttc_rl.orch.create_connectivity')
    conn_list = []
    if request_from_rest_api:
        ll_attributes = body.logical_link_path_list
        net_layer = body.network_layer
        net_type = body.inter_nfvi_pop_network_type
        meta_data = body.meta_data
    else:
        ll_attributes = body['logicalLinkPathList']
        net_layer = body['networkLayer']
        net_type = body['interNfviPopNetworkType']
        meta_data = body['metaData']
    if net_layer.upper() != 'VLAN':
        raise KeyError("'networkLayer' parameter should be 'VLAN'. No other options implemented yet.")
    for ll_attr in ll_attributes:
        # Creating id for inter NfviPop connectivity
        connectivity_id = create_connectivity_id()
        # decompose the LL to retrieve NfviPoP and GW involved
        ll_endpoint = absLogic.decompose_ll(ll_attr)
        # decompose the stitching table in order to retrieve the internal PEs and WIMs
        # creating a nx graph from stitching table
        total_graph = domResLogic.decompose_stitching()
        # Reducing the graph between Src Gw and Dst Gw
        src_gw = ll_endpoint[1]
        dst_gw = ll_endpoint[3]
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
        # logger.debug("SRC_PE_ID, DST_PE_ID: {}, {}".format(src_pe_id, dst_pe_id))
        # retrieve the ServiceID related to the LL
        service_id_in_metadata = ''
        for meta_data_element in meta_data:
            if meta_data_element['key'] == "ServiceId":
                service_id_in_metadata = meta_data_element['value']
                logger.info("ServiceId in the request: '{}'".format(service_id_in_metadata))

        if not service_id_in_metadata:
            # case of ServiceId not defined in the request
            logger.error("ServiceId wrong in the request")
            raise KeyError("Check the ServiceId in the request")
        # retrieve the IP address
        src_vnf_ip = ''
        dst_vnf_ip = ''
        src_mac_add = ''
        dst_mac_add = ''
        vlan_federation = None
        for metadata_vnf in ll_attr['metaData']:
            if metadata_vnf['key'] == "srcVnfIpAddress":
                src_vnf_ip = metadata_vnf['value']
            if metadata_vnf['key'] == "dstVnfIpAddress":
                dst_vnf_ip = metadata_vnf['value']
            if metadata_vnf['key'] == "srcVnfMacAddress":
                src_mac_add = metadata_vnf['value']
            if metadata_vnf['key'] == "dstVnfMacAddress":
                dst_mac_add = metadata_vnf['value']
            # vlanId parameter in metadata sent in case of Federation
            if metadata_vnf['key'] == "vlanId":
                vlan_federation = int(metadata_vnf['value'])
        check_mac_address = lambda x: True if re.match("[0-9a-f]{2}([-:]?)[0-9a-f]{2}(\\1[0-9a-f]{2}){4}$",
                                                       x.lower()) else False
        if check_mac_address(src_mac_add) and check_mac_address(dst_mac_add):
            logger.info("MAC addresses of VNFs are validated")
        else:
            raise KeyError("MAC Address of VNF not correct!")
        if not src_vnf_ip or not dst_vnf_ip or not src_mac_add or not dst_mac_add:
            err = "'srcVnfIpAddress', 'dstVnfIpAddress', " \
                  "'srcVnfMacAddress' and 'dstVnfMacAddress' are mandatory parameters"
            logger.error(err)
            raise KeyError(err)
        logger.info("VNF SRC IP ADD: '{}', VNF DST IP ADD: '{}'".format(src_vnf_ip, dst_vnf_ip))
        logger.info("VNF SRC MAC ADD: '{}', VNF DST MAC ADD: '{}'".format(src_mac_add, dst_mac_add))
        # creating metadata for call request on mac addresses
        metadata_call = {"srcMacAddr": src_mac_add, "dstMacAddr": dst_mac_add}
        # add the vlanId parameter to metadata of COP call in case of Federation
        if vlan_federation is not None:
            logger.info("VLAN Id to be used for Federation Issue: {}".format(vlan_federation))
            metadata_call['vlanId'] = vlan_federation
        ll_to_be_updated = db_session.query(Dbllinternfvipops). \
            filter_by(logicalLinkId=ll_attr['logicalLinkAttributes']['logicalLinkId']).first()
        # check if the requested BW in the request is correct (not over the totalBandwidth of LL, not negative)
        if ll_attr['reqBandwidth'] > ll_to_be_updated.totalBandwidth or ll_attr['reqBandwidth'] < 0:
            raise ValueError("ReqBW in the Allocation request does not respect the limits of the LL.")
        # check if the latency in the request is correct (not below the delay of the LL)
        if ll_attr['reqLatency'] < ll_to_be_updated.linkDelayValue:
            raise ValueError("ReqLatency in the Allocation request does not respect the limits of the LL.")
        if OPTION_PA_ALGORITHM == "OPTION_B":
            # calculation of Inter-NFVIPoP Connectivity path for OPTION_B
            # below assumption of 2020-05-15
            # requested BW for OPTION_B (CsA) is the one represented by the relative field in the request
            requested_bandwidth = ll_attr['reqBandwidth']
            # TODO: same behaviour for request latency???
            computed_path, gw_pe_links = ro.calculate_connectivity(connectivity_id=connectivity_id,
                                                                   inner_graph=total_graph,
                                                                   src_pe=src_pe_id,
                                                                   dst_pe=dst_pe_id,
                                                                   req_bw=requested_bandwidth,
                                                                   req_delay=ll_attr['reqLatency'])
            # in order to use the pathLL for OPTION_A i will convert the compute path from Inline Class to dict
            computed_path = eval(str(computed_path))
        else:
            # OPTION_PA_ALGORITHM == "OPTION_A"
            # requested BW for OPTION_A (InA) is the one represented by the relative field in the request
            requested_bandwidth = ll_attr['reqBandwidth']
            # retrieving pre-calculated Inter-NFVIPoP Connectivity path from DB for OPTION_A
            computed_path, gw_pe_links = ro.retrieve_connectivity(inner_graph=total_graph,
                                                                  ll_request_attributes=ll_attr)
            # update LL db as TEI did, TODO: it should be done?
            if OPTION_A_BOOT_QUOTES:
                ll_to_be_updated.availableBandwidth -= requested_bandwidth
                if ll_to_be_updated.availableBandwidth < 0:
                    raise ValueError("Available BW for the LL '{}' can not support the reqBw".
                                     format(ll_to_be_updated.logicalLinkId))
                logger.info("Updated Availabe BW for LL '{}'.".format(ll_to_be_updated.logicalLinkId))
        # logger.info("Computed path: {}, {}".format(computed_path, type(computed_path)))
        # instantiation of the new Inter-NFVIPoP Connectivity in the RO package
        list_of_wim, list_links_call, dict_new_call_ids = ro.instantiate_connectivity(path=computed_path,
                                                                                      gw_pe_links=gw_pe_links,
                                                                                      connectivity_id=connectivity_id,
                                                                                      src_vnf_ip=src_vnf_ip,
                                                                                      dst_vnf_ip=dst_vnf_ip,
                                                                                      metadata_call=metadata_call,
                                                                                      req_bw=requested_bandwidth)

        # append the links between GW-PE of source and destination of the LL
        # The following lines represent the call from GWs to PEs elements,
        # it works but i don't know if it worth draw it
        # list_links_call.extend([{"source": src_gw, "destination": src_pe_id, "inter_link_type": "interWanLink"},
        #                         {"source": dst_pe_id, "destination": dst_gw, "inter_link_type": "interWanLink"}])
        db_session.add(Dbinternfvipopconnectivity(
            interNfviPopConnectivityId=connectivity_id,
            callIdInvolved=dict_new_call_ids,
            logicalLinkId=ll_attr['logicalLinkAttributes']['logicalLinkId'],
            srcGwIpAddress=ll_attr['logicalLinkAttributes']['srcGwIpAddress'],
            localLinkId=ll_attr['logicalLinkAttributes']['localLinkId'],
            dstGwIpAddress=ll_attr['logicalLinkAttributes']['dstGwIpAddress'],
            remoteLinkId=ll_attr['logicalLinkAttributes']['remoteLinkId'],
            reqBandwidth=requested_bandwidth,
            reqLatency=ll_attr['reqLatency'],
            networkLayer=net_layer,
            interNfviPopNetworkType=net_type,
            metadata_interconnectivity='',
            interNfviPopNetworkSegmentType='',
            serviceId=service_id_in_metadata,
            wimInvolved=list_of_wim,
            pathCall=str(list_links_call)
        ))
        logger.info("Created interNfviPop Connectivity with id: {}".format(connectivity_id))
        conn_list.append(InlineResponse201(inter_nfvi_pop_connnectivity_id=connectivity_id,
                                           inter_nfvi_pop_network_segment_type=net_type))
        # time.sleep(5)
        db_session.commit()

    return conn_list


def create_connectivity_id():
    """
    Create a new unique Inter-NFVIPoP Connectivity Id
    :return connectivity_id : unique Inter-NFVIPoP Connectivity Id
    """
    logger = logging.getLogger('cttc_rl.orch.retrieve_rl_resources')
    connectivity_id = None
    new_connectivity_id = False
    while not new_connectivity_id:
        connectivity_id = str(uuid4())[:8]
        # check in the DB
        new_connectivity_id = True if db_session.query(Dbinternfvipopconnectivity).filter_by(
            interNfviPopConnectivityId=connectivity_id).first() is None else False
    logger.info("Created the inter-NFVIPoP connectivity id: '{}'".format(connectivity_id))
    return connectivity_id


def delete_connectivity(body):
    """
    Deleted a InterNFVI-POP request
    :param body: request
    """
    logger = logging.getLogger('cttc_rl.orch.delete_connectivity')
    meta_data = body.meta_data
    # retrieve the ServiceID related to the LL
    service_id_in_metadata = ''
    for meta_data_element in meta_data:
        if meta_data_element['key'] == "ServiceId":
            service_id_in_metadata = meta_data_element['value']
            logger.info("ServiceId in the request: '{}'".format(service_id_in_metadata))

    if not service_id_in_metadata:
        # case of ServiceId not defined in the request
        logger.error("ServiceId wrong in the request")
        raise KeyError("Check the ServiceId in the request")

    # find all the Inter-NFVIPoP connectivity IDs associated to the ServiceId in the body
    conn_to_be_deleted = db_session.query(Dbinternfvipopconnectivity).filter_by(serviceId=service_id_in_metadata) \
        .all()
    if not conn_to_be_deleted:
        raise KeyError("ServiceId in the request not in DB.")
    else:
        # retrieve just the list of IDs
        list_conn_to_be_deleted = [elem.interNfviPopConnectivityId for elem in conn_to_be_deleted]

        # find the list of Inter-NFVIPoP connectivity IDs in the body
        list_of_internfvipopcon = []
        for inter_nfvi_pop_connectivity in body.inter_nfvi_pop_connnectivity_id_list:
            list_of_internfvipopcon.append(InterNfviPopConnnectivityIdListInner
                                           .from_dict(inter_nfvi_pop_connectivity).inter_nfvi_pop_connnectivity_id)
        # Delete all the InterNFVI-PoP Connectivity Id in the list of DELETE request
        for inter_nfvi_pop_con_id in list_of_internfvipopcon:
            element = db_session.query(Dbinternfvipopconnectivity).filter_by(
                serviceId=service_id_in_metadata,
                interNfviPopConnectivityId=inter_nfvi_pop_con_id).first()
            if element:
                if OPTION_PA_ALGORITHM == "OPTION_A" and OPTION_A_BOOT_QUOTES:
                    # update LL db. TODO: it should be done?
                    ll_to_be_updated = db_session.query(Dbllinternfvipops). \
                        filter_by(logicalLinkId=element.logicalLinkId).first()
                    ll_to_be_updated.availableBandwidth += element.reqBandwidth
                    if ll_to_be_updated.availableBandwidth > ll_to_be_updated.totalBandwidth:
                        raise ValueError("Available BW for the LL '{}' can not be more than Total BW!".
                                         format(ll_to_be_updated.logicalLinkId))
                    logger.info("Updated Availabe BW for LL '{}'.".format(ll_to_be_updated.logicalLinkId))
                # delete path in all the WIM involved
                ro.delete_path(element.interNfviPopConnectivityId, element.wimInvolved)
                # once delete the path, remove the entry in the DB
                try:
                    db_session.delete(element)
                    db_session.commit()
                    logger.info("Removed the entry in ServiceId Table")
                except IntegrityError:
                    db_session.rollback()
                    logger.error('Error in database operation')
            else:
                raise ValueError("InterNfviPoPConnectivity Id '{}' is not associated to serviceId '{}'!".
                                 format(id, service_id_in_metadata))

        if sorted(list_conn_to_be_deleted) == sorted(list_of_internfvipopcon):
            logger.info("All the InterNfviPopConnnectivityId associated to ServiceId '{}' are in the DELETE request".
                        format(service_id_in_metadata))
        else:
            logger.info("Some InterNfviPoPConnectivityId associated to Service Id '{}' are not in the DELETE request".
                        format(service_id_in_metadata))
        return None


def create_vl_network(body):
    """
    Create a VL network in RL
    :param body: request
    """
    logger = logging.getLogger('cttc_rl.orch.create_vl_network')
    if body.network_resource_type != 'subnet-vlan':
        raise KeyError("The value in 'networkResourceType' parameter only can be 'subnet-vlan'. "
                       "No other technologies implemented yet")
    # create dictionary of metadata from body of request
    metadata = {}
    for element in body.metadata:
        metadata[element.key] = element.value
    # check if "ServiceId", "AbstractNfviPoPId" and "vimId" are in the metadata
    if "vimId" not in metadata or "ServiceId" not in metadata or "AbstractNfviPoPId" not in metadata:
        raise KeyError("'ServiceId', 'AbstractNfviPoPId' and 'vimId' parameters are mandatory in metadata.")
    # check if "ip-floating-required" parameter is in typeSubnetData's metadata
    metadata_subnet = {}
    for element in body.type_subnet_data.metadata:
        metadata_subnet[element['key']] = element['value']
    if "ip-floating-required" not in metadata_subnet:
        raise KeyError("'ip-floating-required' parameter is mandatory in 'typeSubnetData/metadata' field")
    # check if the selected VIM and NFVI-PoP are inside the NFVI-PoP DB
    vim_exist = db_session.query(Dbnfvipops).filter_by(vimId=metadata['vimId'], nfviPopId=metadata['AbstractNfviPoPId'])
    if vim_exist is None:
        raise KeyError("vimId and AbstractNfviPoPId do not correspond to any registered entity!")
    logger.info("Request to create vl network '{}' for ServiceId '{}' in VIM/NFVI-PoP '{}/{}'".format(
        body.network_resource_name,
        metadata['ServiceId'],
        metadata['vimId'],
        metadata['AbstractNfviPoPId']))
    net_created = ro.create_intrapop_net(body, metadata, db_session.query(Dbvirtuallinks).all())
    logger.info("VL network '{}' created.".format(body.network_resource_name))
    # updating the DB
    try:
        db_session.add(Dbvirtuallinks(vlName=net_created['name'],
                                      serviceId=metadata['ServiceId'],
                                      vimId=net_created['vimId'],
                                      floatingIp=net_created['floating_ips'],
                                      networkId=net_created['network_id'],
                                      subnetId=net_created['subnet_id'],
                                      routerId=net_created['router_id'],
                                      cidr=net_created['CIDR'],
                                      vlanId=net_created['vlan_id'],
                                      pool=net_created['pool']))
        db_session.commit()
        logger.info("DB updated.")
    except IntegrityError:
        db_session.rollback()
        logger.error("Error in DB")
        raise KeyError("Error in loading DB")
    return net_created


def delete_vl_network(network_id):
    """
    Delete a VL network in RL
    :param network_id: id of network to be deleted
    """
    logger = logging.getLogger('cttc_rl.orch.delete_vl_network')
    vl_to_be_deleted = db_session.query(Dbvirtuallinks).filter_by(networkId=network_id).first()
    if vl_to_be_deleted is not None:
        ro.delete_intrapop_net(vl_to_be_deleted)
    else:
        raise KeyError("NetworkReourceId not in DB")
    try:
        db_session.delete(vl_to_be_deleted)
        db_session.commit()
        logger.info('Deleted "{}".'.format(network_id))
    except IntegrityError:
        db_session.rollback()
        logger.error('Error in database operation')
    return 0
