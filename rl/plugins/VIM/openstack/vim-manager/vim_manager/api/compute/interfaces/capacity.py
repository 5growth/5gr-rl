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
from urllib.parse import urlparse

import flask
import http
import json
import requests

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

import ast

from vim_manager import conf
from vim_manager.api.schema import CapacityInformation
from vim_manager.api.schema import Filter
from vim_manager.api.schema import NfviPop
from vim_manager.api.schema import ResourceZone
from vim_manager.api.schema import TimePeriodInformation
from vim_manager.osc.clients import OpenStackClients

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_capacity_management', __name__)


class QueryComputeCapacityRequest(Schema):
    zoneId = fields.Str(
        required=True,
        description='When specified this parameter identifies the resource '
        'zone for which the capacity is requested. When not '
        'specified the total capacity managed by the VIM instance '
        'will be returned.')
    computeResourceTypeId = fields.Str(
        required=True,
        description='Identifier of the resource type for which the issuer '
        'wants to know the available, total, reserved and/or '
        'allocated capacity.')
    resourceCriteria = fields.Str(
        required=True,
        description='Input capacity computation parameter for selecting the '
        'virtual memory, virtual CPU and acceleration '
        'capabilities for which the issuer wants to know the '
        'available, total, reserved and/or allocated capacity. '
        'Selecting parameters/attributes that shall be used are '
        'defined in the VirtualComputeResourceInformation, '
        'VirtualCpuResourceInformation, and '
        'VirtualMemoryResourceInformation information elements. '
        'This information element and the computeResourceTypeId '
        'are mutually exclusive.')
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
    """Query Compute Capacity operation.

    This operation supports retrieval of capacity information for the various
    types of consumable virtualised compute resources available in the
    Virtualised Compute Resources Information Management Interface.
    """
    # TODO replace get by post
    # parameters = [{
    #     "in": "body",
    #     "name": "QueryComputeCapacityRequest",
    #     "schema": QueryComputeCapacityRequest,
    #     "required": True
    # }]

    parameters = [{
        "name": "ComputeResourceTypeId",
        "in": "path",
        "type": "string",
        "description": "The type of ressource requested: VirtualCpuResourceInformation,VirtualMemoryResourceInformation,VirtualStorageResourceInformation",
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
    tags = ['virtualisedComputeResources']
    operationId = "queryComputeCapacity"

    # TODO replace get by post
    # def post(self):
    #     data = flask.request.get_json()
    #     resource_type_input = data.get('computeResourceTypeId')

    #     resource_types = {
    #         'VirtualCpuResourceInformation': 'VCPU',
    #         'VirtualMemoryResourceInformation': 'MEMORY_MB',
    #         'VirtualStorageResourceInformation': 'DISK_GB'
    #     }

    #     resource_type = resource_types.get(resource_type_input)
    #     config = flask.current_app.osloconfig
    #     session = OpenStackClients(config).session

    #     response = session.get('/resource_providers', endpoint_filter={'service_type': 'placement', 'interface': 'public', 'region_name': 'RegionOne' })
    #     resource_providers = json.loads(response.__dict__['_content'].decode("utf-8"))

    #     available_capacity = 0
    #     total_capacity = 0
    #     allocated_capacity = 0
    #     # check all the compute nodes
    #     for resource_provider in resource_providers['resource_providers']:
    #         links = resource_provider['links']
    #         for link in links:
    #             if link['rel'] == 'inventories':
    #                 response = session.get(link['href'], endpoint_filter={'service_type': 'placement', 'interface': 'public', 'region_name': 'RegionOne' })
    #                 data = json.loads(response.__dict__['_content'].decode("utf-8"))
    #                 inventories = data['inventories'][resource_type]
    #                 available_capacity = available_capacity + ( inventories['total'] - inventories['reserved'] )
    #                 total_capacity = total_capacity + inventories['total']
    #                 allocated_capacity = allocated_capacity + inventories['reserved']

    #     capacity_information = {
    #         'availableCapacity': available_capacity,
    #         'reservedCapacity': None,  # May be use blazar lib
    #         'totalCapacity': total_capacity,
    #         'allocatedCapacity': allocated_capacity
    #     }

    #     return flask.jsonify(capacity_information), OK

    # def get(self, ComputeResourceTypeId):
    def get(self):

        # resource_types = {
        #     'VirtualCpuResourceInformation': 'VCPU',
        #     'VirtualMemoryResourceInformation': 'MEMORY_MB',
        #     'VirtualStorageResourceInformation': 'DISK_GB'
        # }
        resource_types = ['MEMORY_MB', 'VCPU']

        data = flask.request.args.getlist('ComputeResourceTypeId')
        # import ipdb; ipdb.set_trace()
        resource_type = resource_types[int(data[0])]

        config = flask.current_app.osloconfig
        session = OpenStackClients(config).session

        response = session.get('/resource_providers', endpoint_filter={'service_type': 'placement', 'interface': 'public', 'region_name': 'RegionOne' })
        resource_providers = json.loads(response.__dict__['_content'].decode("utf-8"))

        available_capacity = 0
        total_capacity = 0
        allocated_capacity = 0
        # check all the compute nodes
        for resource_provider in resource_providers['resource_providers']:
            links = resource_provider['links']
            for link in links:
                if link['rel'] == 'inventories':
                    linkt = link['href'].replace("/placement", "")
                    response = session.get(linkt, endpoint_filter={'service_type': 'placement', 'interface': 'public', 'region_name': 'RegionOne' })
                    data = json.loads(response.__dict__['_content'].decode("utf-8"))
                    inventories = data['inventories'][resource_type]
                    available_capacity = available_capacity + ( inventories['total'] - inventories['reserved'] )
                    total_capacity = total_capacity + inventories['total']
                    allocated_capacity = allocated_capacity + inventories['reserved']

        capacity_information = {
            'availableCapacity': available_capacity,
            'reservedCapacity': None,  # May be use blazar lib
            'totalCapacity': total_capacity,
            'allocatedCapacity': allocated_capacity
        }

        return flask.jsonify(capacity_information), OK

class ComputeResourceZoneQueryAPI(SwaggerView):
    """Query Compute Resource Zone operation.

    This operation enables the NFVO to query information about a Resource
    Zone, e.g. listing the properties of the Resource Zone, and other metadata.
    """
    # TODO replace get by post
    # parameters = [{
    #     "name": "filter",
    #     "in": "body",
    #     "schema": Filter,
    #     "description": "Input filter for selecting information to query. "
    #                    "For instance, based on identifier of the Resource "
    #                    "Zone, identifier of the NFVI-PoP, properties of "
    #                    "the Resource Zone, or other meta-data.",
    #     "required": True
    # }]
    parameters = [{
        "name": "nfviPopId",
        "in": "path",
        "type": "string",
        "description": "The nfviPopId related to a zone",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'The filtered information that has been retrieved '
                           'about the Resource Zone. The cardinality can be 0 '
                           'if no matching information exist.',
            'schema': {
                'type': 'array',
                'items': ResourceZone
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
    tags = ['virtualisedComputeResources']
    operationId = "queryComputeResourceZone"

    # TODO fix this the post should be used for this type of resquet
    # def post(self):

    #     return flask.jsonify(''), OK

    # def get(self, nfviPopId):
    def get(self):
        #import ipdb; ipdb.set_trace()
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()
        #neutron = OpenStackClients(config).neutron()

        availabilityZones =  nova.availability_zones.list()
        resourceZones = []
        for availabilityZone in availabilityZones:
            if availabilityZone.zoneName == 'internal':
                continue

            resourceZone = {
                'zoneId'   : conf.cfg.CONF.ressource_zone.zoneId,
                'zoneName' : availabilityZone.zoneName,
                'zoneState' : 'available' if availabilityZone.zoneState['available'] else 'unavailable',
                'nfviPopId' : conf.cfg.CONF.ressource_zone.nfviPopId,
                'zoneProperty' : conf.cfg.CONF.ressource_zone.zoneProperty
            }
            resourceZones.append(resourceZone)

        return flask.jsonify(resourceZones), OK

# class ComputeResourceZoneQueryAPI_withoutID(SwaggerView):
#     """Query Compute Resource Zone operation.

#     This operation enables the NFVO to query information about a Resource
#     Zone, e.g. listing the properties of the Resource Zone, and other metadata.
#     """
#     # TODO delete this is a stubbed

#     responses = {
#         OK: {
#             'description': 'The filtered information that has been retrieved '
#                            'about the Resource Zone. The cardinality can be 0 '
#                            'if no matching information exist.',
#             'schema': {
#                 'type': 'array',
#                 'items': ResourceZone
#             },
#         },
#         UNAUTHORIZED: {
#             "description": "Unauthorized",
#         },
#         FORBIDDEN: {
#             "description": "Forbidden",
#         },
#         BAD_REQUEST: {
#             "description": "Bad request",
#         },
#     }
#     tags = ['virtualisedComputeResources']
#     operationId = "queryComputeResourceZone"

#     # TODO fix this the post should be used for this type of resquet
#     # def post(self):

#     #     return flask.jsonify(''), OK

#     def get(self):
#         resourceZones = {
#             'zoneId'   : conf.cfg.CONF.ressource_zone.zoneId,
#             'zoneName' : conf.cfg.CONF.ressource_zone.zoneName,
#             'zoneState' : conf.cfg.CONF.ressource_zone.zoneState,
#             'nfviPopId' : conf.cfg.CONF.ressource_zone.nfviPopId,
#             'zoneProperty' : conf.cfg.CONF.ressource_zone.zoneProperty
#         }
#         return flask.jsonify(resourceZones), OK

class NfviPopComputeInformationQueryAPI(SwaggerView):
    """Query NFVI-PoP Compute Information operation.

    This operation enables the NFVOs to query general information to the VIM
    concerning the geographical location and network connectivity endpoints to
    the NFVI-PoP(s) administered by the VIM, and to determine network
    endpoints to reach VNFs instantiated making use of virtualised compute
    resources in the NFVI as specified by the exchanged information elements.
    """
    # TODO replace get by post
    # parameters = [{
    #     "name": "filter",
    #     "in": "body",
    #     "schema": Filter,
    #     "description": "Input filter for selecting information to query.",
    #     "required": True
    # }]

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
    tags = ['virtualisedComputeResources']
    operationId = "queryNFVIPoPComputeInformation"
    # TODO replace get by post
    # def post(self):
    #     nfvi_pop_param = {
    #         'nfviPopId' : conf.cfg.CONF.nfvi_pop.nfviPopId,
    #         'vimId' : conf.cfg.CONF.nfvi_pop.vimId,
    #         'geographicalLocationInfo' : conf.cfg.CONF.nfvi_pop.geographicalLocationInfo,
    #         'networkConnectivityEndpoint' : conf.cfg.CONF.nfvi_pop.networkConnectivityEndpoint
    #     }
    #     return flask.jsonify(nfvi_pop_param), OK

    def get(self):
        nfvi_pop_param = [{
            'nfviPopId' : conf.cfg.CONF.nfvi_pop.nfviPopId,
            'vimId' : conf.cfg.CONF.nfvi_pop.vimId,
            'geographicalLocationInfo' : conf.cfg.CONF.nfvi_pop.geographicalLocationInfo,
            'networkConnectivityEndpoint' : conf.cfg.CONF.nfvi_pop.networkConnectivityEndpoint
        }]
        return flask.jsonify(nfvi_pop_param), OK


blueprint.add_url_rule(
    # TODO replace get by post
    # '/v1/compute_resources/capacities/query',
    # '/v1/compute_resources/capacities/<ComputeResourceTypeId>',
    '/v1/compute_resources/capacities',
    strict_slashes=False,
    view_func=CapacityQueryAPI.as_view('queryComputeCapacity'),
    # TODO replace get by post
    # methods=['POST'])
    methods=['GET'])

blueprint.add_url_rule(
    # TODO replace get by post
    # '/v1/compute_resources/resource_zones/query',
    # '/v1/compute_resources/resource_zones/<nfviPopId>',
    '/v1/compute_resources/resource_zones/',
    strict_slashes=False,
    view_func=ComputeResourceZoneQueryAPI.as_view(
        'queryComputeResourceZoneQuery'),
    # methods=['POST'])
    methods=['GET'])

# blueprint.add_url_rule(
#     # TODO delete this function it's a stubbed one
#     '/v1/compute_resources/resource_zones',
#     strict_slashes=False,
#     view_func=ComputeResourceZoneQueryAPI_withoutID.as_view(
#         'queryComputeResourceZoneQuery_withoutID'),
#     methods=['GET'])

blueprint.add_url_rule(
    # TODO replace get by post
    # '/v1/compute_resources/nfvi_pop_compute_information/query',
    '/v1/compute_resources/nfvi_pop_compute_information',
    strict_slashes=False,
    view_func=NfviPopComputeInformationQueryAPI.as_view(
        'queryNfviPopComputeInformation'),
    # TODO replace get by post
    # methods=['POST'])
    methods=['GET'])
