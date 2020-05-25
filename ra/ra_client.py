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

from six.moves.configparser import RawConfigParser

from urllib3 import HTTPConnectionPool

import ra.swagger_client
from ra.swagger_client.rest import ApiException
from ra.swagger_client.models import CompRouteInput, CompRouteInputInterWanLinks, \
    CompRouteInputAbsWanTopo, CompRouteInputNodes, CompRouteInputRequestList, \
    CompRouteInputQosCons, NetwLinkAtts, CompRouteInputEdges

import logging


class RaClient(object):
    def __init__(self, filename=None):
        self.filename = filename
        self.logger = logging.getLogger('cttc_rl.ra.ra_client')

        self.conf = ra.swagger_client.Configuration()
        # conf.host = 'http://10.1.16.46:8088'
        ra_config = RawConfigParser()
        ra_config.read(filename)
        ra_server = ra_config.get("DEFAULT", "ra_default")
        self.logger.info("'{}' choose as RA".format(ra_server))
        self.conf.host = 'http://{}:{}'.format(ra_config.get(ra_server, "ip"), ra_config.get(ra_server, "port"))
        # self.ra_id = int(ra_config.get(ra_server, "id"))
        self.k_paths_csa = int(ra_config.get(ra_server, "kpaths_for_CsA"))
        self.k_paths_ina = int(ra_config.get(ra_server, "kpaths_for_InA"))
        self.description = ra_config.get(ra_server, "description")

    def compute_path(self,
                     inter_nfvipop_connectivity_id,
                     inter_wan_links,
                     list_of_topology,
                     src_pe,
                     dst_pe,
                     required_bw,
                     required_delay):
        api_instance = ra.swagger_client.InterNfviPopCompRouteApi(ra.swagger_client.ApiClient(configuration=self.conf))
        try:
            abs_wan_topo = []
            for topology in list_of_topology:
                nodes = []
                for node in topology['topology']['nodes']:
                    nodes.append(CompRouteInputNodes(node_id=node['nodeId']))
                edges = []
                for edge in topology['topology']['edges']:
                    if any(n['nodeId'] == edge['source'] for n in topology['topology']['nodes']) and any(
                            n['nodeId'] == edge['target'] for n in topology['topology']['nodes']):
                        # check if the link contains an "external" node, for example a PE from another WIM
                        # error raised with multiple WIM and onos controller
                        # that gives the link with an external PE Address
                        edges.append(CompRouteInputEdges(a_node_id=edge['source'],
                                                         z_node_id=edge['target'],
                                                         a_link_id=int(edge['localIfid']),
                                                         z_link_id=int(edge['remoteIfid']),
                                                         netw_link_qo_s=NetwLinkAtts(
                                                             link_cost="linkCost",
                                                             link_cost_value=10,
                                                             link_delay="linkDelay",
                                                             link_delay_value=float(edge.get('latency', 0)),
                                                             link_avail_bw="linkAvailBw",
                                                             link_avail_bw_value=float(edge['unreservBw']))
                                                         )
                                     )
                abs_wan_topo.append(CompRouteInputAbsWanTopo(wim_id=topology['wim_id'],
                                                             nodes=nodes,
                                                             edges=edges))
            # ra_id is "CSA" because is OPTION_B
            body = CompRouteInput(ra_id="CSA",
                                  sync_paths=False,
                                  request_list=[
                                      CompRouteInputRequestList(
                                          request_id=1,
                                          inter_nfvi_pop_connectivity_id=inter_nfvipop_connectivity_id,
                                          src_pe_id=src_pe,
                                          dst_pe_id=dst_pe,
                                          k_paths=self.k_paths_csa,
                                          qos_cons=CompRouteInputQosCons(
                                              bandwidth_cons='bandwidthCons',
                                              bandwidth_cons_value=required_bw,
                                              delay_cons='delayCons',
                                              delay_cons_value=required_delay
                                          )
                                      )
                                  ],
                                  inter_wan_links=[CompRouteInputInterWanLinks(
                                      a_wim_id=link['aWimId'],
                                      z_wim_id=link['zWimId'],
                                      a_pe_id=link['aPEId'],
                                      z_pe_id=link['zPEId'],
                                      a_link_id=int(link['aLinkId']),
                                      z_link_id=int(link['zLinkId']),
                                      # TODO check these static values!!!
                                      netw_link_qo_s=NetwLinkAtts(link_cost="linkCost",
                                                                  link_cost_value=10,
                                                                  link_delay="linkDelay",
                                                                  link_delay_value=1,
                                                                  link_avail_bw="linkAvailableBw",
                                                                  link_avail_bw_value=20)
                                  ) for link in inter_wan_links],
                                  abs_wan_topo=abs_wan_topo
                                  )
            self.logger.info("Body of the RA request done!")
            self.logger.debug("Body of the RA: {}".format(body))
            api_response = api_instance.comp_route_inter_nfvi_pop(params=body,
                                                                  async_req=True)
            response = api_response.get()
            # self.logger.debug("Body and type of response: {}, {}".format(response, type(response)))
            # to be consistent with the OPTION_A
            return response.response_list[0].response[0]
        except (ApiException, HTTPConnectionPool) as e:
            self.logger.error("Exception when calling DefaultApi->exposures_get: %s\n" % str(e))
            raise KeyError("Error in RA client!")

    def compute_abs_path(self,
                         inter_wan_links,
                         list_of_topology,
                         list_src_dst_pes):
        api_instance = ra.swagger_client.InterNfviPopCompRouteApi(ra.swagger_client.ApiClient(configuration=self.conf))
        # fixed value for bw and delay for InA
        required_bw = 0
        required_delay = 0
        try:
            abs_wan_topo = []
            for topology in list_of_topology:
                nodes = []
                for node in topology['topology']['nodes']:
                    nodes.append(CompRouteInputNodes(node_id=node['nodeId']))
                edges = []
                for edge in topology['topology']['edges']:
                    if any(n['nodeId'] == edge['source'] for n in topology['topology']['nodes']) and any(
                            n['nodeId'] == edge['target'] for n in topology['topology']['nodes']):
                        # check if the link contains an "external" node, for example a PE from another WIM
                        # error raised with multiple WIM and onos controller
                        # that gives the link with an external PE Address
                        edges.append(CompRouteInputEdges(a_node_id=edge['source'],
                                                         z_node_id=edge['target'],
                                                         a_link_id=int(edge['localIfid']),
                                                         z_link_id=int(edge['remoteIfid']),
                                                         netw_link_qo_s=NetwLinkAtts(
                                                             link_cost="linkCost",
                                                             link_cost_value=10,
                                                             link_delay="linkDelay",
                                                             link_delay_value=float(edge.get('latency', 0)),
                                                             link_avail_bw="linkAvailBw",
                                                             link_avail_bw_value=float(edge['unreservBw']))
                                                         )
                                     )
                abs_wan_topo.append(CompRouteInputAbsWanTopo(wim_id=topology['wim_id'],
                                                             nodes=nodes,
                                                             edges=edges))
            # create multiple request for a single call to the RA
            request_list=[]
            for i, src_dst_pes in enumerate(list_src_dst_pes):
                request_list.append(CompRouteInputRequestList(
                    request_id=i+1,
                    inter_nfvi_pop_connectivity_id="connectivity_"+str(i+1),
                    src_pe_id=src_dst_pes[1],
                    dst_pe_id=src_dst_pes[2],
                    k_paths=self.k_paths_ina,
                    qos_cons=CompRouteInputQosCons(
                        bandwidth_cons='bandwidthCons',
                        bandwidth_cons_value=required_bw,
                        delay_cons='delayCons',
                        delay_cons_value=required_delay
                    )))
            # ra_id is "InA because is OPTION_A
            body = CompRouteInput(ra_id="InA",
                                  sync_paths=False,
                                  request_list=request_list,
                                  inter_wan_links=[CompRouteInputInterWanLinks(
                                      a_wim_id=link['aWimId'],
                                      z_wim_id=link['zWimId'],
                                      a_pe_id=link['aPEId'],
                                      z_pe_id=link['zPEId'],
                                      a_link_id=int(link['aLinkId']),
                                      z_link_id=int(link['zLinkId']),
                                      netw_link_qo_s=NetwLinkAtts(link_cost="linkCost",
                                                                  link_cost_value=10,
                                                                  link_delay="linkDelay",
                                                                  link_delay_value=1,
                                                                  link_avail_bw="linkAvailableBw",
                                                                  link_avail_bw_value=20)
                                  ) for link in inter_wan_links],
                                  abs_wan_topo=abs_wan_topo
                                  )
            self.logger.info("Body of the RA request done!")
            self.logger.debug("Body of the RA: {}".format(body))
            api_response = api_instance.comp_route_inter_nfvi_pop(params=body,
                                                                  async_req=True)
            response = api_response.get()
            # self.logger.debug("Body and type of response: {}, {}".format(response, type(response)))
            # to be consistent with the OPTION_B
            return response.response_list
        except(ApiException, HTTPConnectionPool)as e:
            self.logger.error("Exception when calling DefaultApi->exposures_get: %s\n" % str(e))
            raise KeyError("Error in RA client!")