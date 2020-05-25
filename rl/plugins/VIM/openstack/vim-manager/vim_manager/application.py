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
# -*- coding: utf-8 -*-

import datetime
import os
import uuid

import arrow
import flask
from oslo_config import cfg
import simplejson

from vim_manager.api import compute as compute_api
from vim_manager.api import healthcheck
from vim_manager.api import network as network_api
from vim_manager.api import resource_quota as resource_quota_api
from vim_manager.api import resource_reservation as resource_reservation_api
from vim_manager.api import software_image as software_image_api
from vim_manager import conf
from vim_manager import exception
from vim_manager import extensions
from vim_manager import log
from vim_manager import version

LOG = log.get_logger(__name__)


class CustomJSONEncoder(simplejson.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, (datetime.datetime, arrow.Arrow)):
            return str(arrow.get(obj))
        if isinstance(obj, uuid.UUID):
            return str(obj)
        return super(CustomJSONEncoder, self).default(obj)


def register_blueprints(app):
    """Register Flask blueprints."""
    app.register_blueprint(healthcheck.blueprint)
    software_image_api.register_blueprints(app)
    compute_api.register_blueprints(app)
    network_api.register_blueprints(app)
    resource_quota_api.register_blueprints(app)
    resource_reservation_api.register_blueprints(app)


def register_extensions(app):
    extensions.swag.init_app(app)


def register_errorhandlers(app):
    """Register error handlers."""
    for exc_cls in exception.ServiceException.__subclasses__():
        app.register_error_handler(exc_cls.code, exc_cls)


def load_config(conf=conf.CONF, config_file=None):
    project = 'vim-manager'
    default_config_files = cfg.find_config_files(project=project)

    extra_defaults = []
    if config_file is None:
        config_file = 'etc/%(project)s/%(project)s.conf' % dict(
            project=project)
        if os.path.exists(config_file):
            extra_defaults.append(config_file)
    else:
        extra_defaults.append(config_file)

    # We don't really use the CLI parsing so we don't shadow the flask commands
    conf([],
         project=project,
         prog=project,
         version=version.version_info.release_string(),
         default_config_files=default_config_files + extra_defaults)

    return conf


def get_app_config(conf):
    return {
        # Default config
        'DEBUG': conf.DEFAULT.debug,
        'HOST': conf.DEFAULT.host,
        'PORT': conf.DEFAULT.port,
        'SECRET_KEY': conf.DEFAULT.app_secret,

        # # Authentication config
        # 'AUTH_APIKEY_HEADER': conf.auth.apikey_header,
        # 'AUTH_JWT_ALGORITHM': conf.auth.jwt_algorithm,
        # 'AUTH_JWT_VERIFY': conf.auth.jwt_verify,
        # 'AUTH_JWT_KEY': conf.auth.jwt_key,
        # Swagger config
        'SWAGGER': {
            "title":
                "VIM Manager API",
            "description":
                "VIM Manager API based on ETSI GS NFV-IFA 005 V2.3.1",
            "version":
                version.version_string(),
            "uiversion":
                3
        }
    }


def create_app(config=None):
    """An application factory

    See http://flask.pocoo.org/docs/patterns/appfactories/.
    :param config: The configuration dict to use.
    """
    app = flask.Flask('vim-manager')
    app.json_encoder = CustomJSONEncoder

    if not config:
        osloconfig = load_config()
        config = get_app_config(osloconfig)

    if isinstance(config, str):
        osloconfig = load_config(config_file=config)
        config = get_app_config(osloconfig)
        app.config.from_mapping(config)
    elif isinstance(config, dict):
        osloconfig = load_config()
        app.config.from_mapping(config)

    app.osloconfig = osloconfig

    register_extensions(app)
    register_errorhandlers(app)
    register_blueprints(app)

    return app
