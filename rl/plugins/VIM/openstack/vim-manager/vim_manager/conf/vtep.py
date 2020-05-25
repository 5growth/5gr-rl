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

GROUP_NAME = 'vtep'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for vtep vm',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
        'vtep_name',
        default=os.environ.get('VTEP_NAME', 'vm_vtep_bridge'),
        help="Name of the vtep vm."),
    cfg.StrOpt(
        'vtep_image',
        default=os.environ.get('VTEP_IMAGE', 'c4646799-cbe8-4850-9ef7-1c33b30c05dc'),
        help="Image ID for the vtep vm."),
    cfg.StrOpt(
        'vtep_flavor',
        default=os.environ.get('VTEP_FLAVOR', '70bc3010-f21f-4430-801f-e7aa7bc5861c'),
        help="Flavor ID for the vtep vm."),
    cfg.ListOpt(
        'vtep_dns',
        default=os.environ.get('VTEP_DNS', ["10.1.10.10", "10.1.10.11"]),
        help="DNS address for the project.")
]

vtep_param = { 'vtep_name' : 'dummy', 'vtep_image' : '1234567890', 'vtep_flavor' : '0987654321', 'vtep_dns' : ['w.x.y.z', 'z.y.x.w'] }

def register_opts(conf):
    conf.register_opts(OPTS, group=group)

def list_opts():
    return [(GROUP_NAME, OPTS)]
