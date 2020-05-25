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

from keystoneauth1 import loading as ka_loading

CLIENTS_AUTH = "clients_auth"


def register_opts(conf):
    ka_loading.register_session_conf_options(conf, CLIENTS_AUTH)
    ka_loading.register_auth_conf_options(conf, CLIENTS_AUTH)


def list_opts():
    return [(
        "clients_auth",
        ka_loading.get_session_conf_options() +
        ka_loading.get_auth_common_conf_options(),
    )]
