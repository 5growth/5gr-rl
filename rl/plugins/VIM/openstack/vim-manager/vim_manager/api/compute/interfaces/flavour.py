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

from vim_manager.osc.clients import OpenStackClients

from flasgger import SwaggerView

from vim_manager.api import common
from vim_manager.api.compute.schema import VirtualComputeFlavour
from vim_manager.api.schema import Filter

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('flavour_resources', __name__)


def extract_flavour(flavour):
    return {
        'name': flavour.name,
        'flavourId': flavour.id,
        'accelerationCapability': '',
        'virtualMemory': {
            'virtualMemSize': flavour.ram,
            'virtualMemOversubscriptionPolicy': None,
            'numaEnabled': True,
        },
        'virtualCpu': {
            'cpuArchitecture': '',
            'numVirtualCpu': flavour.vcpus,
            'cpuClock': '',
            'virtualCpuOversubscriptionPolicy': '',
            'virtualCpuPinning': {}
        },
        'storageAttributes': {
            'storageId': '',
            'storageName': '',
            'flavourId': '',
            'typeOfStorage': '',
            'sizeOfStorage': flavour.disk,
            'ownerId': '',
            'zoneId': '',
            'hostId': '',
            'operationalState': '',
        },
        'virtualNetworkInterface': {},
    }


class ComputeFlavourCreateAPI(SwaggerView):
    """Create Compute Flavour operation.

    This operation allows requesting the creation of a flavour as indicated
    by the consumer functional block.
    """

    parameters = [{
        "name": "Flavour",
        "in": "body",
        "schema": VirtualComputeFlavour,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Identifier of the created Compute Flavour.',
            'schema': {
                'type': 'string'
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
        CONFLICT: {
            "description": "flavour already added",
        },
    }

    tags = ['virtualisedComputeResources']
    operationId = "createFlavour"

    def post(self):
        data = flask.request.get_json()

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        id = data['flavourId']
        ram = data['virtualMemory']['virtualMemSize']
        vcpus = data['virtualCpu']['numVirtualCpu']
        disk = data['storageAttributes']['sizeOfStorage']

        flavour = nova.flavors.create(id, ram, vcpus, disk, id)

        return flask.jsonify(flavour.id), CREATED


class ComputeFlavourDeleteAPI(SwaggerView):
    """Delete Compute Flavour operation.

    This operation allows deleting a Compute Flavour.
    """

    parameters = [{
        "name": "id",
        "in": "path",
        "type": "string",
        "description": "Identifier of the Compute Flavour to be deleted.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'No output parameters',
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
    # definitions = dict(SoftwareImage=SoftwareImageInformation)
    tags = ['virtualisedComputeResources']
    operationId = "deleteFlavours"

    def delete(self, id):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        nova.flavors.delete(id)

        return '', OK


class ComputeFlavourQueryAPI(SwaggerView):
    """Query Compute Flavour operation.

    This operation allows querying information about created Compute Flavours.
    """
    parameters = [{
        "name": "queryComputeFlavourFilter",
        "in": "body",
        "schema": Filter,
        "description":
            "Query filter based on e.g. name, identifier, "
            "meta-data information or status information, "
            "expressing the type of information to be retrieved. "
            "It can also be used to specify one or more Compute Flavours to "
            "be queried by providing their identifiers.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'A list of Compute Flavours matching the query.',
            'schema': {
                'type': 'array',
                'items': VirtualComputeFlavour
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
    operationId = "queryFlavors"

    def get_param_value(self, flavor, param):

        elt_list = param.split('.')
        size_list = len(elt_list)

        # import ipdb; ipdb.set_trace()
        if ( size_list == 1):
            return flavor[elt_list[0]]
        elif  ( size_list == 2):
            return flavor[elt_list[0]][elt_list[1]]
        else:
            return '-1'

    def post(self):

        data_filter = flask.request.get_json()
        filter_list = list(data_filter.keys())

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        flavours = [extract_flavour(f) for f in nova.flavors.list()]

        filtered_flavor = []
        for flavor in flavours:
            match = True
            for item in filter_list:
                # Get param value from flavor
                try:
                    value = self.get_param_value(flavor, data_filter[item]['param'])

                except (KeyError) as e:
                    print (e)
                    return flask.jsonify('Error param name, for ' + item + ' (' + str(e) + ')'), OK

                test = common.compare_value(data_filter[item]['op'], value,  data_filter[item]['value'])
                if test == '-1':
                    return flask.jsonify('Error wrong operator, for ' + item ), OK
                elif test:
                    match = True
                else:
                    match = False
                    break
            if match :
                filtered_flavor.append(flavor)
        # import ipdb; ipdb.set_trace()
        return flask.jsonify(filtered_flavor), OK


blueprint.add_url_rule(
    '/v1/compute_resources/flavours/query',
    strict_slashes=False,
    view_func=ComputeFlavourQueryAPI.as_view('queryFlavour'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/compute_resources/flavours',
    strict_slashes=False,
    view_func=ComputeFlavourCreateAPI.as_view('createFlavour'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/compute_resources/flavours/<id>',
    strict_slashes=False,
    view_func=ComputeFlavourDeleteAPI.as_view('deleteFlavour'),
    methods=['DELETE'])
