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

GROUP_NAME = 'ressource_zone'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for ressource zone',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
       'zoneId',
       default=os.environ.get('ZONE_ID', 123),
       help="The identifier of the Resource Zone."),
    cfg.StrOpt(
       'zoneName',
       default=os.environ.get('ZONE_NAME', 'Twilight'),
       help="The name of the Resource Zone."),
    cfg.StrOpt(
        'zoneState',
        default=os.environ.get('ZONER_STATE', 'available'),
        help="Information about the current state of the Resource"),
    cfg.IntOpt(
        'nfviPopId',
        default=os.environ.get('NFVI_POP', 321),
        help="The identifier of the NFVI-PoP the Resource Zone belongs"),
    cfg.StrOpt(
        'zoneProperty',
        default=os.environ.get('ZONE_PROPERTY', 'cpu'),
        help="The identifier of the NFVI-PoP the Resource Zone belongs")
]

def register_opts(conf):
    conf.register_opts(OPTS, group=group)

def list_opts():
    return [(GROUP_NAME, OPTS)]
