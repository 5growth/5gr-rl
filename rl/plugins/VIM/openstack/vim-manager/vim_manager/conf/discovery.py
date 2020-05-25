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

GROUP_NAME = "discovery"
group = cfg.OptGroup(
    name=GROUP_NAME,
    title="Configuration Options for the service discovery",
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
        "driver",
        default=os.environ.get("DISCOVERY_DRIVER", "static"),
        help="Driver name (default: DISCOVERY_DRIVER or 'static')"),
    cfg.DictOpt(
        "driver_config",
        default=cfg.types.Dict()(
            os.environ.get("DISCOVERY_DRIVER_CONFIG", dict())),
        help="discovery port (default: DISCOVERY_DRIVER_CONFIG or {})"),
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
