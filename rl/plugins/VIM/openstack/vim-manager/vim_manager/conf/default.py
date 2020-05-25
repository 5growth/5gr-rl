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

GROUP_NAME = 'DEFAULT'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for the service',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.StrOpt(
        'host',
        default=os.environ.get('HOST', '127.0.0.1'),
        help="Service host (default: HOST or '127.0.0.1')"),
    cfg.IntOpt(
        'port',
        default=os.environ.get('PORT', 8000),
        help="Service port (default: PORT or 8000)"),
    cfg.StrOpt(
        'app_secret',
        default=os.environ.get('APP_SECRET', 'not secret anymore'),
        help="App secret (default: 'APP_SECRET'"),
    cfg.BoolOpt(
        'debug',
        default=os.environ.get('DEBUG', False),
        help="Is in debug mode ? (default: DEBUG or false)"),
    cfg.StrOpt(
        'log_level',
        default=os.environ.get('LOG_LEVEL', 'warning'),
        choices=log.LOG_LEVELS.keys(),
        help="Service log level (default: LOG_LEVEL or 'warning')"),
    cfg.StrOpt(
        'project_id',
        default=os.environ.get('PROJECT_ID', '776354dbbda448af872ed1c72ced19ee'),
        help="ID of the current project/tenant.")
]


def register_opts(conf):
    conf.register_opts(OPTS, group=group)


def list_opts():
    return [(GROUP_NAME, OPTS)]
