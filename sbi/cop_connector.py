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

import copy

import requests
import json
import logging
from future.moves.urllib.parse import urlunparse  # python3 compatible
from requests.exceptions import ConnectionError
from sbi import wim_connector


class Connector(wim_connector.WimConnector):
    def __init__(self, parameters):
        super().__init__()
        self.description = "Cop Connector!"
        self.logger = logging.getLogger('cttc_rl.sbi.cop_connector')
        self.headers = {'Content-type': 'application/json'}
        #
        self.domain_id = parameters.domain_id
        self.name = parameters.name
        self.type = parameters.account_type
        self.ip = parameters.ip
        self.port = parameters.port
        self.url = parameters.url
        self.username = parameters.username
        self.password = parameters.userpassword
        self.tenant = parameters.tenantName

    def get_access(self):
        """
        Get the ip address to connect with WIM
        :return: wim_url: address of WIM
        """
        if self.ip is not None and self.port is not None:
            wim_url = self.ip + ':' + self.port \
                if self.url is None or not self.url else self.url
            wim_url = urlunparse(('http', wim_url, '/restconf/config/', '', '', ''))
            return wim_url
        else:
            self.logger.error("Something wrong with WIMs ID")
            raise KeyError("Something wrong with WIMs ID")

    def get_topology(self):
        """
        Get resource topology of a WIM
        :return: topology: dict
        """
        url = self.get_access()
        try:
            response = requests.get('{}context/topology/0'.format(url))
            topology = json.loads(response.content) if response.status_code == requests.codes.ok else {
                "Error": "WIM not working!"
            }
            self.logger.debug("Topology of WIM '{}': {}".format(self.domain_id, topology))
            self.logger.info("Topology of WIM '{}' retrieved.".format(self.domain_id))
            return topology
        except ConnectionError as e:
            self.logger.error(e)
            return e

    def get_context(self):
        """
        Query Context to a WIM
        :return: context: dict
        """
        url = self.get_access()
        try:
            response = requests.get('{}context'.format(url))
            context = json.loads(response.content) if response.status_code == requests.codes.ok else {
                "Error": "WIM not working!"
            }
            self.logger.debug("Context of WIM '{}': {}".format(self.domain_id, context))
            self.logger.info("Context of WIM '{}' retrieved.".format(self.domain_id))
            return context
        except ConnectionError as e:
            self.logger.error(e)
            return e

    def create_call(self, callId, internal_path, inter_wan_path, edge_paths, src_ip, dst_ip, metadata_call, vlan, bw):
        """
        Create a call in COP server
        :param callId: callId COP: dict
        :param internal_path: internal path
        :param inter_wan_path: list of inter-wan links reduced by the PA algorithm
        :param edge_paths: list of edge path between gws and wans
        :param src_ip: IP address of source: str
        :param dst_ip: IP address of destination: str
        :param metadata_call: metadata for cop Call: dict
        :param vlan: vlan id: int
        :param bw: required BW: int
        :return: response content, status code
        """
        # common part for ARP and IP calls
        # calculate the aEnd of this WIM
        wim_id_str = str(self.domain_id)
        a_end = {}
        if inter_wan_path is not None:
            for link in inter_wan_path:
                if link['z_wim_id'] == wim_id_str:
                    a_end['nodeId'] = link['z_pe_id']
                    a_end['edgeEndId'] = str(link['z_link_id'])
                    a_end['endpointId'] = link['z_pe_id'] + "_" + str(link['z_link_id'])
                    # logger.info("an interWan Link in aEnd")
        if not a_end:
            for link in edge_paths:
                if link['zWimId'] == wim_id_str and internal_path[0]['a_node_id'] == link['zPEId']:
                    a_end['nodeId'] = link['zPEId']
                    a_end['edgeEndId'] = link['zLinkId']
                    a_end['endpointId'] = link['zPEId'] + "_" + link['zLinkId']
                    # logger.info("GW2PE link in aEnd")
        if not a_end:
            self.logger.error("Something wrong in creating COP call request (aEnd)")
            raise KeyError("Something wrong in creating COP call request (aEnd)")
        else:
            self.logger.debug("aEnd: {}".format(a_end))
        # calculate the zEnd of this WIM
        z_end = {}
        if inter_wan_path is not None:
            for link in inter_wan_path:
                if link['a_wim_id'] == wim_id_str:
                    z_end['nodeId'] = link['a_pe_id']
                    z_end['edgeEndId'] = str(link['a_link_id'])
                    z_end['endpointId'] = link['a_pe_id'] + "_" + str(link['a_link_id'])
                    # logger.info("an interWan Link in zEnd")
        if not z_end:
            for link in edge_paths:
                if link['aWimId'] == wim_id_str and internal_path[-1]['z_node_id'] == link['aPEId']:
                    z_end['nodeId'] = link['aPEId']
                    z_end['edgeEndId'] = link['aLinkId']
                    z_end['endpointId'] = link['aPEId'] + "_" + link['aLinkId']
                    # logger.info("GW2PE link in zEnd")
        if not z_end:
            self.logger.error("Something wrong in creating COP call request (zEnd)")
            raise KeyError("Something wrong in creating COP call request (zEnd)")
        else:
            self.logger.debug("zEnd: {}".format(z_end))
        # build the topoComponent field
        topo_components = [{"nodeId": a_end['nodeId'],
                            "edgeEndId": a_end['edgeEndId'],
                            "endpointId": "0"}]
        for path in internal_path:
            topo_components.append({"nodeId": path['a_node_id'],
                                    "edgeEndId": str(path['a_link_id']),
                                    "endpointId": str(len(topo_components))})
            topo_components.append({"nodeId": path['z_node_id'],
                                    "edgeEndId": str(path['z_link_id']),
                                    "endpointId": str(len(topo_components))})
        topo_components.append({"nodeId": z_end['nodeId'],
                                "edgeEndId": z_end['edgeEndId'],
                                "endpointId": str(len(topo_components))})
        self.logger.debug("Forward: {}, {}, {}, {}".format(a_end, z_end, topo_components, metadata_call))
        # defining element for backward calls
        a_end_back = z_end
        z_end_back = a_end
        topo_components_back = copy.deepcopy(topo_components)
        topo_components_back.reverse()
        for i, component in enumerate(topo_components_back):
            component['endpointId'] = str(i)
        metadata_call_back = {'srcMacAddr': metadata_call['dstMacAddr'], 'dstMacAddr': metadata_call['srcMacAddr']}
        # in case of Federation also the vlanId parameter is present
        if "vlanId" in metadata_call:
            metadata_call_back['vlanId'] = metadata_call['vlanId']
        self.logger.debug("Backward: {}, {}, {}, {}".format(a_end_back, z_end_back, topo_components_back,
                                                            metadata_call_back))
        # create a list of callIds from dict
        call_ids_list = []
        for key in callId:
            call_ids_list.append(key)
        # ARP Forward call
        arp_forward_call_body = {
            "callId": call_ids_list[0],
            "contextId": "admin",
            "transportLayer": {
                "direction": "unidir",
                "layer": "ethernet"
            },
            "trafficParams": {
                "reservedBandwidth": str(bw) + "000000"
            },
            "aEnd": a_end,
            "match": {
                "includePath": {
                  "topoComponents": topo_components
                },
                # "ethSrc": "",
                # "ethDst": "",
                "ethType": 2054,
                "arpSpa": src_ip + "/32",
                "arpTpa": dst_ip + "/32",
                "metadata": json.dumps(metadata_call),
                "vlanVid": vlan
            },
            "zEnd": z_end
        }
        self.logger.debug("ARP Forward Call body: {}".format(arp_forward_call_body))
        # logger.info("ARP Forward Call body: {}".format(arp_forward_call_body))
        url_arp_forward = "{}calls/call/{}".format(self.get_access(), call_ids_list[0])

        # ARP Backward call
        arp_backward_call_body = {
            "callId": call_ids_list[1],
            "contextId": "admin",
            "transportLayer": {
                "direction": "unidir",
                "layer": "ethernet"
            },
            "trafficParams": {
                "reservedBandwidth": str(bw) + "000000"
            },
            "aEnd": a_end_back,
            "match": {
                "includePath": {
                  "topoComponents": topo_components_back
                },
                # "ethSrc": "",
                # "ethDst": "",
                "ethType": 2054,
                "arpSpa": dst_ip + "/32",
                "arpTpa": src_ip + "/32",
                "metadata": json.dumps(metadata_call_back),
                "vlanVid": vlan
            },
            "zEnd": z_end_back
        }
        self.logger.debug("ARP Backward Call body: {}".format(arp_backward_call_body))
        # logger.info("ARP Backward Call body: {}".format(arp_backward_call_body))
        url_arp_backward = "{}calls/call/{}".format(self.get_access(), call_ids_list[1])
        try:
            # POST requests to COP server in order to create a ARP calls for forward and backward directions.
            # ARP call forward
            response = requests.post(url_arp_forward, data=json.dumps(arp_forward_call_body), headers=self.headers)
            # logger.info(response.status_code, response.content)
            self.logger.info("Call {} to WIM '{}' done".format(arp_forward_call_body['callId'], wim_id_str))
            if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                self.logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim_id_str))
            else:
                self.logger.info(json.loads(response.content)['Error'])
                self.logger.error("Error in creating call '{}' in WIM '{}'".format(arp_forward_call_body['callId'],
                                                                                   wim_id_str))
                return -1
            # ARP call backward
            response = requests.post(url_arp_backward, data=json.dumps(arp_backward_call_body), headers=self.headers)
            # logger.info(response.status_code, response.content)
            self.logger.info("Call {} to WIM '{}' done".format(arp_backward_call_body['callId'], wim_id_str))
            if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                self.logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim_id_str))
            else:
                self.logger.info(json.loads(response.content)['Error'])
                self.logger.error("Error in creating call '{}' in WIM '{}'".format(arp_backward_call_body['callId'],
                                                                                   wim_id_str))
                return -1
        except IOError as e:
            self.logger.error(e)
            raise KeyError("Error in creating call. Check the log")

        # IP Forward call
        ip_forward_call_body = {
            "callId": call_ids_list[2],
            "contextId": "admin",
            "transportLayer": {
                "direction": "unidir",
                "layer": "ethernet"
            },
            "trafficParams": {
                "reservedBandwidth": str(bw) + "000000"
            },
            "aEnd": a_end,
            "match": {
                "includePath": {
                  "topoComponents": topo_components
                },
                # "ethSrc": "",
                # "ethDst": "",
                "ethType": 2048,
                "ipv4Src": src_ip + "/32",
                "ipv4Dst": dst_ip + "/32",
                "metadata": json.dumps(metadata_call),
                "vlanVid": vlan
            },
            "zEnd": z_end
        }
        self.logger.debug("IP Forward Call body: {}".format(ip_forward_call_body))
        # logger.info("IP Forward Call body: {}".format(ip_forward_call_body))
        url_ip_forward = "{}calls/call/{}".format(self.get_access(), call_ids_list[2])

        # IP Backward call
        ip_backward_call_body = {
            "callId": call_ids_list[3],
            "contextId": "admin",
            "transportLayer": {
                "direction": "unidir",
                "layer": "ethernet"
            },
            "trafficParams": {
                "reservedBandwidth": str(bw) + "000000"
            },
            "aEnd": a_end_back,
            "match": {
                "includePath": {
                  "topoComponents": topo_components_back
                },
                # "ethSrc": "",
                # "ethDst": "",
                "ethType": 2048,
                "ipv4Src": dst_ip + "/32",
                "ipv4Dst": src_ip + "/32",
                "metadata": json.dumps(metadata_call_back),
                "vlanVid": vlan
            },
            "zEnd": z_end_back
        }
        self.logger.debug("IP Backward Call body: {}".format(ip_backward_call_body))
        # logger.info("IP Backward Call body: {}".format(ip_backward_call_body))
        url_ip_backward = "{}calls/call/{}".format(self.get_access(), call_ids_list[3])
        try:
            # POST requests to COP server in order to create a IP calls for forward and backward directions.
            # IP call forward
            response = requests.post(url_ip_forward, data=json.dumps(ip_forward_call_body), headers=self.headers)
            # logger.info(response.status_code, response.content)
            self.logger.info("Call {} to WIM '{}' done".format(ip_forward_call_body['callId'], wim_id_str))
            if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                self.logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim_id_str))
            else:
                self.logger.info(json.loads(response.content)['Error'])
                self.logger.error("Error in creating call '{}' in WIM '{}'".format(ip_forward_call_body['callId'],
                                                                                   wim_id_str))
                return -1
            # IP call backward
            response = requests.post(url_ip_backward, data=json.dumps(ip_backward_call_body), headers=self.headers)
            # logger.info(response.status_code, response.content)
            self.logger.info("Call {} to WIM '{}' done".format(ip_backward_call_body['callId'], wim_id_str))
            if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                self.logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim_id_str))
            else:
                self.logger.info(json.loads(response.content)['Error'])
                self.logger.error("Error in creating call '{}' in WIM '{}'".format(ip_backward_call_body['callId'],
                                                                                   wim_id_str))
                return -1
        except IOError as e:
            self.logger.error(e)
            raise KeyError("Error in creating call. Check the log")
        # both cop response are ok
        return 0

    def delete_call(self, callIds):
        """
        Delete a call in COP server
        :param callIds: callId COP: dict
        :return: 0 if Ok, otherwise raise a Exception
        """
        for callId_key in callIds:
            url = "{}calls/call/{}".format(self.get_access(), callId_key)
            try:
                # DELETE request to COP server in order to delete the call.
                response = requests.delete(url, headers=self.headers)
                # logger.info(response.status_code, response.content)
                if response.status_code == requests.codes.ok:
                    self.logger.info("Call '{}' deleted".format(callId_key))
                else:
                    raise KeyError("Response from WIM '{}' is {}!".format(self.domain_id, response.status_code))
            except IOError as e:
                self.logger.error(e)
                raise KeyError("Error in deleting call. Check the log")
        return 0
