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

import flask
import http

from flasgger import SwaggerView

from vim_manager.api.compute.schema import VirtualComputeResourceInformation
from vim_manager.api.schema import Filter
from vim_manager import conf

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_information_management', __name__)


class InformationQueryAPI(SwaggerView):
    """Query Virtualised Compute Resource Information operation.

    This operation supports retrieval of information for the various types of
    virtualised compute resources managed by the VIM.
    """
    # TODO replace get by post
    # parameters = [{
    #     "name": "informationQueryFilter",
    #     "in": "body",
    #     "schema": Filter,
    #     "description": "Filter defining the information of consumable "
    #                    "virtualised resources on which the query applies.",
    #     "required": True
    # }]

    parameters = [{
        "name": "zoneId",
        "in": "path",
        "type": "string",
        "description": "The requested zoneId",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Virtualised compute resource information in the '
                           'VIM that satisfies the query condition.',
            'schema': {
                'type': 'array',
                'items': VirtualComputeResourceInformation
            }
        },
        UNAUTHORIZED: {
            "description": "Unauthorized",
        },
        FORBIDDEN: {
            "description": "Forbidden",
        },
        BAD_REQUEST: {
            "description": "Bad request",
        },
    }
    tags = ['virtualisedComputeResources']
    operationId = "queryComputeInformation"

    # def post(self):

    #     virtual_memory = {
    #         'virtualMemSize': 0,
    #         'virtualMemOversubscriptionPolicy': '',
    #         'numaSupported': True
    #     }

    #     virtual_cpu = {
    #         'cpuArchitecture': '',
    #         'numVirtualCpu': 0,
    #         'cpuClock': 0,
    #         'virtualCpuOversubscriptionPolicy': '',
    #         'virtualCpuPinningSupported': True,
    #     }

    #     informations = {
    #         'computeResourceTypeId': '',
    #         'virtualMemory': virtual_memory,
    #         'virtualCPU': virtual_cpu,
    #         'accelerationCapability': '',
    #     }
    #     return flask.jsonify(informations), OK

    def get(self):

        data = flask.request.args.getlist('zoneId')
        print ( 'zoneId: ' + str(data))

        virtual_memory = {
            'virtualMemSize': conf.cfg.CONF.compute_information.virtualMemSize,
            'virtualMemOversubscriptionPolicy': conf.cfg.CONF.compute_information.virtualMemOversubscriptionPolicy,
            'numaSupported': conf.cfg.CONF.compute_information.numaSupported
        }

        virtual_cpu = {
            'cpuArchitecture': conf.cfg.CONF.compute_information.cpuArchitecture,
            'numVirtualCpu': conf.cfg.CONF.compute_information.numVirtualCpu,
            'cpuClock': conf.cfg.CONF.compute_information.cpuClock,
            'virtualCpuOversubscriptionPolicy': conf.cfg.CONF.compute_information.virtualCpuOversubscriptionPolicy,
            'virtualCpuPinningSupported': conf.cfg.CONF.compute_information.virtualCpuPinningSupported,
        }

        informations = [
            {
                'computeResourceTypeId': conf.cfg.CONF.compute_information.computeResourceTypeMemory,
                'virtualMemory': virtual_memory,
                'accelerationCapability': conf.cfg.CONF.compute_information.accelerationCapability,
            },
            {
                'computeResourceTypeId': conf.cfg.CONF.compute_information.computeResourceTypeCpu,
                'virtualCPU': virtual_cpu,
                'accelerationCapability': conf.cfg.CONF.compute_information.accelerationCapability,
            }
        ]
        return flask.jsonify(informations), OK

blueprint.add_url_rule(
    # TODO replace get by post
    # '/v1/compute_resources/information/query',
    # '/v1/compute_resources/information/<zoneId>',
    '/v1/compute_resources/information',

    strict_slashes=False,
    view_func=InformationQueryAPI.as_view('queryComputeInformation'),
    # TODO replace get by post
    # methods=['POST'])
    methods=['GET'])
