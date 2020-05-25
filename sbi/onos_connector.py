import copy
import json
import logging
from urllib.parse import urlunparse
import requests
from requests.auth import HTTPBasicAuth

from sbi import wim_connector


class Connector(wim_connector.WimConnector):
    def __init__(self, parameters):
        super().__init__()
        self.description = "Onos Connector!"
        self.logger = logging.getLogger('cttc_rl.sbi.onos_connector')
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
        self.auth = HTTPBasicAuth(self.username, self.password)

    @staticmethod
    def ofi2pp(of_id):
        of_id = of_id.split(':')[-1]
        pp_id = ':'.join(a + b for a, b in zip(of_id[::2], of_id[1::2]))
        return pp_id

    @staticmethod
    def pp2ofi(pp_id):
        pp_id = pp_id.replace(':', '')
        return 'of:' + pp_id

    def get_access(self):
        """
        Get the ip address to connect with WIM
        :return: wim_url: address of WIM
        """
        if self.ip is not None and self.port is not None:
            wim_url = self.ip + ':' + self.port \
                if self.url is None or not self.url else self.url
            wim_url = urlunparse(('http', wim_url, '/onos/v1/', '', '', ''))
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
            # devices request that will fill the nodes of topology
            response = requests.get('{}devices'.format(url), auth=self.auth)
            devices = json.loads(response.content) if response.status_code == requests.codes.ok else {
                "Error": "WIM not working!"
            }
            nodes = []
            for device in devices['devices']:
                device = dict(device)
                response_device = requests.get('{}devices/{}/ports'.format(url, device['id']), auth=self.auth)
                device_port_resp = json.loads(
                    response_device.content) if response.status_code == requests.codes.ok else {
                    "Error": "WIM not working!"
                }
                ports = []
                for port in device_port_resp['ports']:
                    port = dict(port)
                    ports.append({
                        'edgeEndId': port['port'],
                        'peerNodeId': '',  # omitted TODO check if can be retrieve by links request
                        'name': port['annotations']['portName'],
                        'switchingCap': 'psc'  # statically fixed
                    })
                nodes.append({
                    'domain': '1',  # statically fixed
                    'edgeEnd': [ports],
                    'name': Connector.ofi2pp(device['id']),
                    'nodeId': Connector.ofi2pp(device['id']),
                    'nodetype': 'OF'  # statically fixed
                })
            # links request that will fill the edges of topology
            response = requests.get('{}links'.format(url), auth=self.auth)
            links = json.loads(response.content) if response.status_code == requests.codes.ok else {
                "Error": "WIM '{}' not working!".format(self.domain_id)
            }
            # retrieve the edges of ONOS network
            edges = []
            for link in links['links']:
                link = dict(link)
                edges.append({
                    'edgeId': Connector.ofi2pp(link['src']['device']) + '_to_' + Connector.ofi2pp(link['dst']['device']),
                    'edgeType': 'eth_edge',
                    'latency': '3.2',  # TODO confirm
                    'localIfid': link['src']['port'],
                    'maxResvBw': '1.0e+9',  # TODO confirm
                    'metric': '3.700000000e+01',  # TODO confirm
                    'remoteIfid': link['dst']['port'],
                    'source': Connector.ofi2pp(link['src']['device']),
                    'switchingCap': 'lsc',
                    'target': Connector.ofi2pp(link['dst']['device']),
                    'unreservBw': '1.0e+9'  # TODO confirm
                })
            # create the topology
            topology = {
                'topologyId': '0',
                'nodes': nodes,
                'edges': edges
            }
            return topology
        except ConnectionError as e:
            return e

    def get_context(self):
        """
        Query Context to a WIM
        :return: context: dict
        """
        try:
            topology_0_dict = self.get_topology()
            context = {
                'contextId': "admin",
                'serviceEndpoint': [],
                'topology': [topology_0_dict]
            }
            # self.logger.debug("Context of WIM '{}': {}".format(self.domain_id, context))
            self.logger.info("Context of WIM '{}' retrieved.".format(self.domain_id))
            return context
        except ConnectionError as e:
            # self.logger.error(e)
            return e

    def create_call(self, callId, internal_path, inter_wan_path, edge_paths, src_ip, dst_ip, metadata_call, vlan, bw):
        """
        Create calls in ONOS server
        :param callId: dict
        :param internal_path: internal path
        :param inter_wan_path: list of inter-wan links reduced by the PA algorithm
        :param edge_paths: list of edge path between gws and wans
        :param src_ip: IP address of source: str
        :param dst_ip: IP address of destination: str
        :param metadata_call: metadata for cop Call: dict
        :param vlan: vlan id: int
        :param bw: required BW: int  # TODO check where it fits inside onos controller
        :return: response content, status code
        """
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
        self.logger.debug("Forward: {}, {}".format(topo_components, metadata_call))
        # defining element for backward calls
        topo_components_back = copy.deepcopy(topo_components)
        topo_components_back.reverse()
        for i, component in enumerate(topo_components_back):
            component['endpointId'] = str(i)
        metadata_call_back = {}
        # in case of mac_addresses in the metadata field
        if 'srcMacAddr' in metadata_call and 'dstMacAddr' in metadata_call:
            metadata_call_back['srcMacAddr'] = metadata_call['dstMacAddr']
            metadata_call_back['dstMacAddr'] = metadata_call['srcMacAddr']
        # in case of Federation also the vlanId parameter is present
        if "vlanId" in metadata_call:
            metadata_call_back['vlanId'] = metadata_call['vlanId']
        self.logger.debug("Backward: {}, {}".format(topo_components_back, metadata_call_back))
        switch_and_ports_for_forward_flow = []
        for i, k in zip(topo_components[0::2], topo_components[1::2]):
            switch_and_ports_for_forward_flow.append({"of_id": Connector.pp2ofi(i['nodeId']),
                                                      'in_port': i['edgeEndId'],
                                                      'out_port': k['edgeEndId']})
        self.logger.info(switch_and_ports_for_forward_flow)
        switch_and_ports_for_backward_flow = []
        for i, k in zip(topo_components_back[0::2], topo_components_back[1::2]):
            switch_and_ports_for_backward_flow.append({'of_id': Connector.pp2ofi(i['nodeId']),
                                                       'in_port': i['edgeEndId'],
                                                       'out_port': k['edgeEndId']})
        self.logger.info(switch_and_ports_for_backward_flow)
        # create a list of callIds from dict
        call_ids_list = []
        for key in callId:
            call_ids_list.append(key)
        # CREATE FORWARD FLOW FOR EACH SWITCH INVOLVED
        for element in switch_and_ports_for_forward_flow:
            self.logger.info("Creating IP and ARP flows in switch {}".format(Connector.ofi2pp(element['of_id'])))
            forward_flow_body = {
                "priority": 50000,  # TODO check this value
                "timeout": 0,
                "isPermanent": True,
                "deviceId": element['of_id'],
                "treatment": {
                    "instructions": [
                        {
                            "type": "OUTPUT",
                            "port": element['out_port']
                        }
                    ]
                },
                "selector": {
                    "criteria": [
                        {
                            "type": "IN_PORT",
                            "port": element['in_port']
                        },
                        {
                            "type": "VLAN_VID",
                            "vlanId": str(vlan)
                        }
                    ]
                }
            }
            self.logger.debug("Generic Forward Flow body: {}".format(forward_flow_body))
            arp_forward_flow_body = copy.deepcopy(forward_flow_body)
            arp_forward_flow_body['selector']['criteria'].extend([
                {
                    "type": "ETH_TYPE",
                    "ethType": "0x0806"
                },
                {
                    "type": "ARP_SPA",
                    "ip": src_ip
                },
                {
                    "type": "ARP_TPA",
                    "ip": dst_ip
                }
            ])
            self.logger.debug("ARP Forward Flow body: {}".format(arp_forward_flow_body))
            ip_forward_flow_body = copy.deepcopy(forward_flow_body)
            ip_forward_flow_body['selector']['criteria'].extend([
                {
                    "type": "ETH_TYPE",
                    "ethType": "0x0800"
                },
                {
                    "type": "IPV4_SRC",
                    "ip": "{}/32".format(src_ip)
                },
                {
                    "type": "IPV4_DST",
                    "ip": "{}/32".format(dst_ip)
                }
            ])
            self.logger.debug("IP Forward Flow body: {}".format(ip_forward_flow_body))
            try:
                # POST requests to ONOS server in order to create a ARP call and an IP call for forward.
                # ARP flow forward
                url_arp_forward = "{}flows/{}?appId={}".format(self.get_access(), element['of_id'], call_ids_list[0])
                response = requests.post(url_arp_forward,
                                         data=json.dumps(arp_forward_flow_body),
                                         headers=self.headers,
                                         auth=self.auth)
                # logger.info(response.status_code, response.content)
                if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                    self.logger.info("Call {} to WIM '{}' OK".format(call_ids_list[0], wim_id_str))
                    flow_id_arp_forward = response.headers['Location'].split('/')[-1]
                    callId[call_ids_list[0]].append({"wim_id": self.domain_id,
                                                     "of_id": element['of_id'],
                                                     "flow_id": flow_id_arp_forward})
                else:
                    self.logger.error("Error in creating call '{}' in WIM '{}'".format(call_ids_list[0], wim_id_str))
                    return -1
                # IP flow forward
                url_ip_forward = "{}flows/{}?appId={}".format(self.get_access(), element['of_id'], call_ids_list[1])
                response = requests.post(url_ip_forward,
                                         data=json.dumps(ip_forward_flow_body),
                                         headers=self.headers,
                                         auth=self.auth)
                # logger.info(response.status_code, response.content)
                if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                    self.logger.info("Call {} to WIM '{}' ok".format(call_ids_list[1], wim_id_str))
                    flow_id_ip_forward = response.headers['Location'].split('/')[-1]
                    callId[call_ids_list[1]].append({"wim_id": self.domain_id,
                                                     "of_id": element['of_id'],
                                                     "flow_id": flow_id_ip_forward})
                else:
                    self.logger.error("Error in creating call '{}' in WIM '{}'".format(call_ids_list[1], wim_id_str))
                    return -1
            except IOError as e:
                self.logger.error(e)
                raise KeyError("Error in creating call. Check the log")

        # CREATE BACKWARD FLOW FOR EACH SWITCH INVOLVED
        for element in switch_and_ports_for_backward_flow:
            self.logger.info("Creating IP and ARP flows in switch {}".format(Connector.ofi2pp(element['of_id'])))
            backward_flow_body = {
                "priority": 50000,  # TODO check this value
                "timeout": 0,
                "isPermanent": True,
                "deviceId": element['of_id'],
                "treatment": {
                    "instructions": [
                        {
                            "type": "OUTPUT",
                            "port": element['out_port']
                        }
                    ]
                },
                "selector": {
                    "criteria": [
                        {
                            "type": "IN_PORT",
                            "port": element['in_port']
                        },
                        {
                            "type": "VLAN_VID",
                            "vlanId": str(vlan)
                        }
                    ]
                }
            }
            self.logger.debug("Generic Backward Flow body: {}".format(backward_flow_body))
            arp_backward_flow_body = copy.deepcopy(backward_flow_body)
            arp_backward_flow_body['selector']['criteria'].extend([
                {
                    "type": "ETH_TYPE",
                    "ethType": "0x0806"
                },
                {
                    "type": "ARP_SPA",
                    "ip": dst_ip
                },
                {
                    "type": "ARP_TPA",
                    "ip": src_ip
                }
            ])
            self.logger.debug("ARP Backward Flow body: {}".format(arp_backward_flow_body))
            ip_backward_flow_body = copy.deepcopy(backward_flow_body)
            ip_backward_flow_body['selector']['criteria'].extend([
                {
                    "type": "ETH_TYPE",
                    "ethType": "0x0800"
                },
                {
                    "type": "IPV4_SRC",
                    "ip": "{}/32".format(dst_ip)
                },
                {
                    "type": "IPV4_DST",
                    "ip": "{}/32".format(src_ip)
                }
            ])
            self.logger.debug("IP Backward Flow body: {}".format(ip_backward_flow_body))
            try:
                # POST requests to ONOS server in order to create a ARP call and an IP call for backward.
                # ARP flow backward
                url_arp_backward = "{}flows/{}?appId={}".format(self.get_access(), element['of_id'], call_ids_list[2])
                response = requests.post(url_arp_backward,
                                         data=json.dumps(arp_backward_flow_body),
                                         headers=self.headers,
                                         auth=self.auth)
                # logger.info(response.status_code, response.content)
                if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                    self.logger.info("Call {} to WIM '{}' OK".format(call_ids_list[2], wim_id_str))
                    flow_id_arp_backward = response.headers['Location'].split('/')[-1]
                    callId[call_ids_list[2]].append({"wim_id": self.domain_id,
                                                     "of_id": element['of_id'],
                                                     "flow_id": flow_id_arp_backward})
                else:
                    self.logger.error("Error in creating call '{}' in WIM '{}'".format(call_ids_list[2], wim_id_str))
                    return -1
                # IP flow backward
                url_ip_backward = "{}flows/{}?appId={}".format(self.get_access(), element['of_id'], call_ids_list[3])
                response = requests.post(url_ip_backward,
                                         data=json.dumps(ip_backward_flow_body),
                                         headers=self.headers,
                                         auth=self.auth)
                # logger.info(response.status_code, response.content)
                if response.status_code == requests.codes.created or response.status_code == requests.codes.ok:
                    self.logger.info("Call {} to WIM '{}' OK".format(call_ids_list[3], wim_id_str))
                    flow_id_ip_backward = response.headers['Location'].split('/')[-1]
                    callId[call_ids_list[3]].append({"wim_id": self.domain_id,
                                                     "of_id": element['of_id'],
                                                     "flow_id": flow_id_ip_backward})
                else:
                    self.logger.error("Error in creating call '{}' in WIM '{}'".format(call_ids_list[3], wim_id_str))
                    return -1
            except IOError as e:
                self.logger.error(e)
                raise KeyError("Error in creating call. Check the log")
        return 0

    def delete_call(self, callIds):
        """
        Delete a call in ONOS server
        :param callIds: dict
        :return: 0 if Ok, otherwise raise a Exception
        """
        # retrieve all the flow to be deleted from ONOS server
        # url_flows = "{}flows".format(self.get_access())
        # response = requests.get(url_flows, auth=self.auth)
        # all_flows = json.loads(response.content) if response.status_code == requests.codes.ok else {
        #     "Error": "WIM not working!"
        # }
        # all_flows = all_flows['flows']
        # flows_to_be_deleted = []
        # for flow in all_flows:
        #     flow = dict(flow)
        #     if flow['appId'] in callIds:
        #         flows_to_be_deleted.append((flow['appId'], flow['deviceId'], flow['id']))
        # retrieve all the flow to be deleted from DB
        flows_to_be_deleted = []
        for key, value in callIds.items():
            for v in value:
                if v['wim_id'] == self.domain_id:
                    flows_to_be_deleted.append((key, v['of_id'], v['flow_id']))
        self.logger.info("Flows to be deleted: {}".format(flows_to_be_deleted))
        for flow in flows_to_be_deleted:
            try:
                # DELETE request to ONOS server in order to delete the flow in a specific switch
                url_delete = '{}flows/{}/{}'.format(self.get_access(), flow[1], flow[2])
                response = requests.delete(url_delete, auth=self.auth)
                if response.status_code == requests.codes.no_content:
                    self.logger.debug("Call '{}' deleted from switch {}".format(flow[0], Connector.ofi2pp(flow[1])))
                else:
                    raise KeyError("Response from WIM '{}' is {}!".format(self.domain_id, response.status_code))
            except IOError as e:
                self.logger.error(e)
                raise KeyError("Error in deleting call. Check the log")
        return 0
