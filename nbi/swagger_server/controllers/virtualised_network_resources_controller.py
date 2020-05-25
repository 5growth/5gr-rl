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
import traceback
import connexion

from nbi.swagger_server.models.allocate_network_request import AllocateNetworkRequest  # noqa: E501
from nbi.swagger_server.models.allocate_network_result import AllocateNetworkResult  # noqa: E501
from nbi.swagger_server.models.allocate_network_result_network_data import AllocateNetworkResultNetworkData
from nbi.swagger_server.models.allocate_network_result_subnet_data import AllocateNetworkResultSubnetData
from nbi.swagger_server.models.meta_data_inner import MetaDataInner
from nbi.swagger_server.models.filter import Filter  # noqa: E501
from nbi.swagger_server.models.http_errors import *
import logging
from orchestrator import orch


def vi_mallocate_network(params):  # noqa: E501
    """vi_mallocate_network

     # noqa: E501

    :param params: 
    :type params: dict | bytes

    :rtype: AllocateNetworkResult
    """
    logger = logging.getLogger('cttc_rl.nbi.vim_allocate_network')
    if connexion.request.is_json:
        params = AllocateNetworkRequest.from_dict(connexion.request.get_json())  # noqa: E501
    try:
        response = orch.create_vl_network(params)
        # logger.debug("Response from vl query: {}".format(response))
    except(KeyError, TypeError, AttributeError, ConnectionError) as e:
        print(traceback.format_exc(), type(e).__name__, e.args)
        logger.error(e)
        if str(e) == "'Network already in DB VL!'":
            # return error_xxx(str(e), 409)
            # changed to empty response and status code 200, due to service composition issue in the SO
            return {}, 200
        else:
            return error400(str(e))
    return AllocateNetworkResult(network_data=
                                 AllocateNetworkResultNetworkData(bandwidth=0,
                                                                  is_shared=True,
                                                                  network_port=[],
                                                                  network_qo_s=[],
                                                                  network_resource_id=response['network_id'],
                                                                  network_resource_name=params.network_resource_name,
                                                                  # network_type=params.type_network_data,
                                                                  network_type="vlan",
                                                                  operational_state="enabled",
                                                                  segment_type=str(response['vlan_id']),
                                                                  sharing_criteria="",
                                                                  subnet=[params.network_resource_name + "-subnet"],
                                                                  zone_id="",
                                                                  metadata=[
                                                                      MetaDataInner(key='provider:segmentation_id',
                                                                                    value=str(response['vlan_id']))]),
                                 network_port_data=None,
                                 subnet_data=
                                 AllocateNetworkResultSubnetData(address_pool=[response['pool']],
                                                                 cidr="192.168.{}.0/24".format(response['CIDR']),
                                                                 gateway_ip="192.168.{}.1".format(response['CIDR']),
                                                                 ip_version="IPv4",
                                                                 is_dhcp_enabled=True,
                                                                 metadata=[],
                                                                 network_id=response['subnet_id'],
                                                                 operational_state="enabled",
                                                                 resource_id=response['subnet_id']
                                                                 )
                                 ), 201


def vi_mquery_networks(networkQueryFilter):  # noqa: E501
    """vi_mquery_networks

     # noqa: E501

    :param networkQueryFilter: Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers.
    :type networkQueryFilter: dict | bytes

    :rtype: List[VirtualNetwork]
    """
    if connexion.request.is_json:
        network_query_filter = Filter.from_dict(connexion.request.get_json())  # noqa: E501
    return error501('Not implemented yet.')


def vi_mterminate_networks(networkResourceId):  # noqa: E501
    """vi_mterminate_networks

     # noqa: E501

    :param networkResourceId: Identifier of the virtualised network resource(s) to be terminated.
    :type networkResourceId: List[str]

    :rtype: List[str]
    """
    logger = logging.getLogger('cttc_rl.nbi.vim_terminate_networks')
    network_deleted = []
    try:

        for net in networkResourceId:
            orch.delete_vl_network(net)
            network_deleted.append(net)
        logger.info("Deleted all the networks")
        return network_deleted, 200
    except(KeyError, TypeError, AttributeError) as e:
        logger.error(e)
        return error400(str(e))
