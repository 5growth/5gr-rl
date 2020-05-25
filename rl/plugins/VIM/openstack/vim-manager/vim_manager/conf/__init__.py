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

from oslo_config import cfg

from vim_manager.conf import clients_auth
from vim_manager.conf import default
from vim_manager.conf import nfvi_pop
from vim_manager.conf import ressource_zone
from vim_manager.conf import compute_information
from vim_manager.conf import vtep
from vim_manager.conf import discovery
from vim_manager.conf import glance_client
from vim_manager.conf import identity
from vim_manager.conf import neutron_client
from vim_manager.conf import nova_client

CONF = cfg.CONF
# ConfigOpts = cfg.ConfigOpts

def register_all_opts(conf):
    clients_auth.register_opts(conf)
    default.register_opts(conf)
    discovery.register_opts(conf)
    glance_client.register_opts(conf)
    identity.register_opts(conf)
    neutron_client.register_opts(conf)
    nova_client.register_opts(conf)
    nfvi_pop.register_opts(conf)
    ressource_zone.register_opts(conf)
    compute_information.register_opts(conf)
    vtep.register_opts(conf)


register_all_opts(CONF)
