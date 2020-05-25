# Copyright 2018 b<>com.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
# IDDN number: IDDN.FR.001.470053.000.S.C.2018.000.00000.
#
# -*- encoding: utf-8 -*-

import os

from oslo_config import cfg

from vim_manager import log

GROUP_NAME = 'nfvi_pop'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for POP',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.IntOpt(
        'nfviPopId',
        default=os.environ.get('NFVI_POP_ID', 321),
        help="Identification of the NFVI-PoP."),
    cfg.IntOpt(
        'vimId',
        default=os.environ.get('VIM_ID', 123456),
        help="Identification of the VIM."),
    cfg.StrOpt(
        'geographicalLocationInfo',
        default=os.environ.get('GEO_INFO', 'Rennes'),
        help="It provides information about the geographic location "),
    cfg.StrOpt(
        'networkConnectivityEndpoint',
        default=os.environ.get('NET_ENDPOINT', 'stuff'),
        help="Information about network connectivity endpoints to the NFVI-PoP"),
    cfg.StrOpt(
        'floating_network',
        default=os.environ.get('NET_ENDPOINT', 'stuff'),
        help="Information about floating network name"),
    cfg.StrOpt(
        'inter_pop_physical_network',
        default=os.environ.get('NET_ENDPOINT', 'stuff'),
        help="Information about inter pop physical network"),
    cfg.StrOpt(
        'inter_pop_vlans',
        default=os.environ.get('NET_ENDPOINT', 'stuff'),
        help="Information about inter pop vlans"),

]

def register_opts(conf):
    conf.register_opts(OPTS, group=group)

def list_opts():
    return [(GROUP_NAME, OPTS)]
