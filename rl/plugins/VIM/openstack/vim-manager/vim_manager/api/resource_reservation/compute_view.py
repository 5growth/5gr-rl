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

from vim_manager.api.resource_reservation.schema import ComputePoolReservation
from vim_manager.api.resource_reservation.schema import ReservedVirtualCompute
from vim_manager.api.resource_reservation.schema import \
    VirtualisationContainerReservation
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

blueprint = flask.Blueprint('compute_resource_reservation', __name__)


class CreateComputeResourceReservationRequest(Schema):
    computePoolReservation = fields.Nested(
        ComputePoolReservation,
        required=True,
        description='Amount of compute resources that need to be reserved, '
        'e.g. {"cpu_cores": 90, "vm_instances": 10, "ram": '
        '10000}.')
    virtualisationContainerReservation = fields.Nested(
        VirtualisationContainerReservation,
        many=True,
        required=True,
        description='Virtualisation containers that need to be reserved (e.g. '
        'following a specific compute "flavour")')
    affinityConstraint = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='Element with affinity information of the virtualised '
        'compute resources to reserve. For the resource '
        'reservation at resource pool granularity level, it '
        'defines the affinity information of the virtual compute '
        'pool resources to reserve. For the resource reservation '
        'at virtual container granularity level, it defines the '
        'affinity information of the virtualisation container(s) '
        'to reserve.')
    antiAffinityConstraint = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='Element with anti-affinity information of the '
        'virtualised compute resources to reserve. For the '
        'resource reservation at resource pool granularity level, '
        'it defines the anti-affinity information of the virtual '
        'compute pool resources to reserve. For the resource '
        'reservation at virtual container granularity level, it '
        'defines the anti-affinity information of the '
        'virtualisation container(s) to reserve.')
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


class ComputeResourceReservationCreateAPI(SwaggerView):
    """Create Compute Resource Reservation operation.

    This operation allows requesting the reservation of virtualised compute
    resources as indicated by the consumer functional block.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": CreateComputeResourceReservationRequest,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Element containing information about the reserved '
                           'resource.',
            'schema': ReservedVirtualCompute,
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
    operationId = "createComputeReservation"

    def post(self):
        return flask.jsonify('Not implemented on OpenStack'), OK


class ComputeResourceReservationTerminateAPI(SwaggerView):
    """Terminate Compute Resource Reservation operation.

    This operation allows terminating one or more issued compute resource
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
    operationId = "terminateComputeReservation"

    def delete(self):

        return flask.jsonify('Not implemented on OpenStack'), OK


class ComputeResourceReservationQueryAPI(SwaggerView):
    """Query Virtualised Compute Resource operation.

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
                'items': ReservedVirtualCompute
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
    operationId = "queryComputeReservation"

    def post(self):

        return flask.jsonify('Not implemented on OpenStack'), OK


blueprint.add_url_rule(
    '/v1/reservations/compute_resources/query',
    strict_slashes=False,
    view_func=ComputeResourceReservationQueryAPI.as_view(
        'queryComputeReservation'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/reservations/compute_resources',
    strict_slashes=False,
    view_func=ComputeResourceReservationCreateAPI.as_view(
        'createComputeReservation'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/reservations/compute_resources',
    strict_slashes=False,
    view_func=ComputeResourceReservationTerminateAPI.as_view(
        'terminateComputeReservation'),
    methods=['DELETE'])
