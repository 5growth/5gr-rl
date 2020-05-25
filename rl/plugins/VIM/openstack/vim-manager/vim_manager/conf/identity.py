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

GROUP_NAME = 'identity'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title="Configuration Options for identity",
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt('driver',
               default=os.environ.get('IDENTITY_DRIVER', 'keycloak'),
               help="Identity driver "
                    "(default: IDENTITY_DRIVER or 'keycloak')"),
    cfg.StrOpt('app_realm',
               default=os.environ.get('APP_REALM', 'falcon'),
               help="Application realm "
                    "(default: APP_REALM or 'falcon')"),
    cfg.StrOpt('client_id',
               default=os.environ.get('CLIENT_ID', 'cluster-service'),
               help="Service Client ID with full access to Keycloak"
                    "(default: CLIENT_ID or 'cluster-service')"),
    cfg.StrOpt('client_secret',
               default=os.environ.get(
                   'CLIENT_SECRET', '02305a63-42a3-4e22-9297-a98f7ea9a72e'),
               secret=True,
               help="Client secret key (default: CLIENT_SECRET or "
                    "02305a63-42a3-4e22-9297-a98f7ea9a72e)"),
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
