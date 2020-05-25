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

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api.schema import CapacityInformation
from vim_manager.api.schema import Filter
from vim_manager.api.schema import NfviPop
from vim_manager.api.schema import TimePeriodInformation

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('network_capacity_management', __name__)


class QueryNetworkCapacityRequest(Schema):
    zoneId = fields.Str(
        required=True,
        description='When specified this parameter identifies the resource '
        'zone for which the capacity is requested. When not '
        'specified the total capacity managed by the VIM instance '
        'will be returned.')
    networkResourceTypeId = fields.Str(
        required=True,
        description='Identifier of the resource type for which the issuer '
        'wants to know the available, total, reserved and/or '
        'allocated capacity.')
    resourceCriteria = fields.Str(
        required=True,
        description='Input capacity computation parameter for selecting the '
        'characteristics of the virtual network for which the '
        'issuer wants to know the available, total, reserved '
        'and/or allocated capacity. Selecting '
        'parameters/attributes that shall be used are defined in '
        'the VirtualNetworkResourceInformation information '
        'element. This information element and the '
        'networkResourceTypeID are mutually exclusive.')
    attributeSelector = fields.Str(
        required=True,
        description='Input parameter for selecting which capacity information '
        '(i.e. available, total, reserved and/or allocated '
        'capacity) is queried. When not present, all four values '
        'are requested.')
    timePeriod = fields.Nested(
        TimePeriodInformation,
        required=True,
        description='The time interval for which capacity is queried. When '
        'omitted, an interval starting "now" is used. The time '
        'interval can be specified since resource reservations '
        'can be made for a specified time interval.')


class CapacityQueryAPI(SwaggerView):
    """Query Network Capacity operation.

    This operation supports retrieval of capacity information for the various
    types of consumable virtualised network resources available in the
    Virtualised Network Resources Information Management Interface.
    """

    parameters = [{
        "in": "body",
        "name": "QueryNetworkCapacityRequest",
        "schema": QueryNetworkCapacityRequest,
        "required": True
    }]
    responses = {
        OK: {
            'description': 'The capacity during the requested time period. '
                           'The scope is according to parameter zoneId of the '
                           'request during the time interval.',
            'schema': CapacityInformation
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
    tags = ['virtualisedNetworkResources']
    operationId = "queryNetworkCapacity"

    def post(self):
        return flask.jsonify('Not implemented in OpenStack'), OK


class NfviPopNetworkInformationQueryAPI(SwaggerView):
    """Query NFVI-PoP Network Information operation.

   This operation enables the NFVOs to query general information to the VIM
   concerning the geographical location and network connectivity endpoints
   (e.g. network gateway) to the NFVI-PoP(s) administered by the VIM, and to
   determine network endpoints to reach VNFs instantiated making use of
   virtualised network resources in the NFVI as specified by the exchanged
   parameters.
    """

    parameters = [{
        "name": "NfviPopNetworkInformationRequest",
        "in": "body",
        "schema": Filter,
        "description": "Input filter for selecting information to query.",
        "required": True
    }]
    responses = {
        OK: {
            'description': 'The filtered information that has been retrieved. '
                           'The cardinality can be 0 if no matching '
                           'information exist.',
            'schema': {
                'type': 'array',
                'items': NfviPop
            },
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
    tags = ['virtualisedNetworkResources']
    operationId = "queryNFVIPoPNetworkInformation"

    def post(self):

        return flask.jsonify('Not implemented in OpenStack'), OK


blueprint.add_url_rule(
    '/v1/network_resources/capacities/query',
    strict_slashes=False,
    view_func=CapacityQueryAPI.as_view('queryNetworkCapacity'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/network_resources/nfvi-pop-network-information/query',
    strict_slashes=False,
    view_func=NfviPopNetworkInformationQueryAPI.as_view(
        'queryNfviPopNetworkInformation'),
    methods=['POST'])
