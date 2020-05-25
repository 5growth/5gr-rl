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

from vim_manager.api.resource_reservation.schema import ReservedVirtualNetwork
from vim_manager.api.resource_reservation.schema import \
    VirtualNetworkReservation
from vim_manager.api.schema import AffinityOrAntiAffinityConstraint
from vim_manager.api.schema import Filter
from vim_manager.api.schema import Identifier

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('network_resource_reservation', __name__)


class CreateNetworkResourceReservationRequest(Schema):
    networkReservation = fields.Nested(
        VirtualNetworkReservation,
        required=True,
        description='Type and configuration of virtualised network resources '
        'that need to be reserved, e.g. {"PublicIps": 20}')
    startTime = fields.DateTime(
        required=True,
        description='Indication when the consumption of the resources starts. '
        'If the value is 0, resources are reserved for immediate '
        'use.')
    endTime = fields.DateTime(
        required=True,
        description='Indication when the reservation ends (when the issuer of '
        'the request expects that the resources will no longer be '
        'needed) and used by the VIM to schedule the reservation. '
        'If not present, resources are reserved for unlimited '
        'usage time.')
    expiryTime = fields.DateTime(
        required=True,
        description='Indication when the VIM can release the reservation in '
        'case no allocation request against this reservation was '
        'made.')
    affinityConstraint = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='Element with anti-affinity information of the virtual '
        'network resources to reserve.')
    antiAffinityConstraint = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='If present, it defines location constraints for the '
        'resource(s) is (are) requested to be reserved, e.g. in '
        'what particular Resource Zone.')
    locationConstraints = fields.Str(
        required=True,
        description='If present, it defines location constraints for the '
        'resource(s) is (are) requested to be reserved, e.g. in '
        'what particular Resource Zone.')
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
        'group", logical grouping of virtual resources assigned '
        'to a tenant within an Infrastructure Domain.')


class NetworkResourceReservationCreateAPI(SwaggerView):
    """Create Network Resource Reservation operation.

    This operation allows requesting the reservation of virtualised network
    resources as indicated by the consumer functional block.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": CreateNetworkResourceReservationRequest,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Element containing information about the reserved '
                           'resource.',
            'schema': ReservedVirtualNetwork,
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
        CONFLICT: {
            "description": "",
        },
    }

    tags = ['virtualisedResourceReservation']
    operationId = "createNetworkReservation"

    def post(self):

        return flask.jsonify('Not implemented on OpenStack'), OK


class NetworkResourceReservationTerminateAPI(SwaggerView):
    """Terminate Network Resource Reservation operation.

    This operation allows terminating one or more issued network resource
    reservation(s). When the operation is done on multiple ids, it is assumed
    to be best-effort, i.e. it can succeed for a subset of the ids, and fail
    for the remaining ones.
    """

    parameters = [{
        'name': 'reservationId',
        'in': 'query',
        'type': 'array',
        'items': {
            'type': Identifier
        },
        'description': 'Identifier of the resource reservation(s) to '
                       'terminate.',
        'required': True
    }]

    responses = {
        OK: {
            'description': 'Identifier of the resource reservation(s) '
                           'successfully terminated.',
            'schema': {
                'type': 'array',
                'items': {
                    'type': Identifier
                }
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

    tags = ['virtualisedResourceReservation']
    operationId = "terminateNetworkReservation"

    def delete(self):

        return flask.jsonify('Not implemented on OpenStack'), OK


class NetworkResourceReservationQueryAPI(SwaggerView):
    """Query Virtualised Network Resource operation.

    This operation allows querying information about reserved compute
    resources that the consumer has access to.
    """

    parameters = [{
        "name": "queryComputeFilter",
        "in": "body",
        "schema": Filter,
        "description": "Query filter based on e.g. name, identifier, "
                       "meta-data information or status information, "
                       "expressing the type of information to be "
                       "retrieved. It can also be used to specify one or "
                       "more resources to be queried by providing their "
                       "identifiers.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Element containing information about the reserved '
                           'resource. Cardinality is 0 if the query did not '
                           'return any result.',
            'schema': {
                'type': 'array',
                'items': ReservedVirtualNetwork
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
    tags = ['virtualisedResourceReservation']
    operationId = "queryNetworkReservation"

    def post(self):
        return flask.jsonify('Not implemented on OpenStack'), OK


blueprint.add_url_rule(
    '/v1/reservations/network_resources/query',
    strict_slashes=False,
    view_func=NetworkResourceReservationQueryAPI.as_view(
        'queryNetworkReservation'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/reservations/network_resources',
    strict_slashes=False,
    view_func=NetworkResourceReservationCreateAPI.as_view(
        'createNetworkReservation'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/reservations/network_resources',
    strict_slashes=False,
    view_func=NetworkResourceReservationTerminateAPI.as_view(
        'terminateNetworkReservation'),
    methods=['DELETE'])
