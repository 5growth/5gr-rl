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
import json

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api import common
from vim_manager.api.resource_quota.schema import VirtualComputeQuota
from vim_manager.api.resource_quota.schema import VirtualComputeQuotaData
from vim_manager.api.schema import Filter
from vim_manager.api.schema import Identifier

from vim_manager.osc.clients import OpenStackClients

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_resources_quota', __name__)


class CreateComputeResourceQuotaRequest(Schema):
    resourceGroupId = fields.Str(
        required=True,
        description='Name provided by the consumer for the virtualised '
        'compute resource to allocate. It can be used for '
        'identifying resources from consumer side.')
    virtualComputeQuota = fields.Nested(
        VirtualComputeQuotaData,
        required=True,
        description='Identifier of the resource reservation applicable to '
        'this virtualised resource management operation. '
        'Cardinality can be 0 if no resource reservation was '
        'used.')


class ComputeResourceQuotaCreateAPI(SwaggerView):
    """Create Compute Resource Quota operation.

    This operation allows requesting the quota of virtualised compute
    resources as indicated by the consumer functional block.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": CreateComputeResourceQuotaRequest,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Element containing information about the quota '
                           'resource.',
            'schema': VirtualComputeQuota,
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

    tags = ['virtualisedResourceQuota']
    operationId = "createComputeQuota"

    def post(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        data = flask.request.get_json()

        resource_group_id = data['resourceGroupId']
        compute_quota = data['virtualComputeQuota']

        resources = {}
        if compute_quota.get('numVCPUs'):
            resources['cores'] = compute_quota.get('numVCPUs')

        if compute_quota.get('numVcInstances'):
            resources['instances'] = compute_quota.get('numVcInstances')

        if compute_quota.get('virtualMemSize'):
            resources['ram'] = compute_quota.get('virtualMemSize')

        nova.quotas.update(resource_group_id, **resources)

        quotas = nova.quotas.get(resource_group_id)

        quotas_data = {
            'resourceGroupId': quotas.id,
            'numVCPUs': quotas.cores,
            'numVcInstances': quotas.instances,
            'virtualMemSize': quotas.ram
        }

        return flask.jsonify(quotas_data), CREATED


class ComputeResourceQuotaTerminateAPI(SwaggerView):
    """Terminate Compute Resource Quota operation.

    This operation allows terminating one or more issued compute resource
    quota(s). When the operation is done on multiple ids, it is assumed to be
    best-effort, i.e. it can succeed for a subset of the ids, and fail for
    the remaining ones.
    """

    parameters = [{
        'name': 'resourceGroupId',
        "in": "query",
        'type': 'array',
        'items': {
            'type': Identifier
        },
        'description': 'Unique identifier of the "infrastructure resource '
                       'group", logical grouping of virtual resources '
                       'assigned to a tenant within an Infrastructure '
                       'Domain.',
        'required': True
    }]

    responses = {
        OK: {
            'description': 'Unique identifier of the "infrastructure resource '
                           'group", logical grouping of virtual resources '
                           'assigned to a tenant within an Infrastructure '
                           'Domain.',
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

    tags = ['virtualisedResourceQuota']
    operationId = "terminateComputeQuota"

    def delete(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        resource_group_ids = flask.request.args.getlist('resourceGroupId')

        deleted_ids = []
        for resource_group_id in resource_group_ids:
            try:
                nova.quotas.delete(resource_group_id)
                deleted_ids.append(resource_group_id)
            except Exception:
                pass

        return flask.jsonify(deleted_ids), OK


class ComputeResourceQuotaQueryAPI(SwaggerView):
    """Query Compute Resource Quota operation.

    This operation allows querying quota information about compute resources
    that the consumer has access to.
    """
    parameters = [{
        "name": "queryQuotaFilter",
        "in": "body",
        "schema": Filter,
        "description": "Query filter based on e.g. name, identifier, "
                       "meta-data information or status information "
                       "expressing the type of information to be "
                       "retrieved. It can also be used to specify one or "
                       "more quotas to be queried by providing their "
                       "identifiers.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Element containing information about the quota '
                           'resource. The cardinality can be 0 if no matching '
                           'quota exists.',
            'schema': {
                'type': 'array',
                'items': VirtualComputeQuota
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
    tags = ['virtualisedResourceQuota']
    operationId = "queryComputeQuota"

    def get_param_value(self, project, param):
        elt_list = param.split('.')
        size_list = len(elt_list)

        if ( size_list == 1):
            return project[elt_list[0]]
        else:
            return '-1'

    def post(self):
        data_filter = flask.request.get_json()
        filter_list = list(data_filter.keys())

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()
        session = OpenStackClients(config).session
        response = session.get('/v3/projects', endpoint_filter={'service_type': 'identity', 'interface': 'public', 'region_name': 'RegionOne'})
        content = json.loads(response.__dict__['_content'].decode("utf-8"))

        project_ids = [ p['id'] for p in content['projects'] ]

        quotas_projects = []
        for project_id in project_ids:
            quotas = nova.quotas.get(project_id)

            quotas_projects.append({
                'resourceGroupId': quotas.id,
                'numVCPUs': quotas.cores,
                'numVcInstances': quotas.instances,
                'virtualMemSize': quotas.ram
            })

        filtered_project = []
        for project in quotas_projects:
            match = False
            for item in filter_list:
                # Get param value from server
                try:
                    value = self.get_param_value(project, data_filter[item]['param'])

                except (KeyError) as e:
                    print (e)
                    return flask.jsonify('Error param name, for ' + item + ' (' + str(e) + ')'), OK

                test = common.compare_value(data_filter[item]['op'], value, data_filter[item]['value'])
                if test == '-1':
                    return flask.jsonify('Error wrong operator, for ' + item ), OK
                elif test:
                    match = True
                else:
                    match = False
                    break
            if match :
                filtered_project.append(project)

        # import ipdb; ipdb.set_trace()
        return flask.jsonify(filtered_project), OK


blueprint.add_url_rule(
    '/v1/quotas/compute_resources/query',
    strict_slashes=False,
    view_func=ComputeResourceQuotaQueryAPI.as_view('queryComputeQuota'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/quotas/compute_resources',
    strict_slashes=False,
    view_func=ComputeResourceQuotaCreateAPI.as_view('createNetworkQuota'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/quotas/compute_resources',
    strict_slashes=False,
    view_func=ComputeResourceQuotaTerminateAPI.as_view(
        'terminateNetworkQuota'),
    methods=['DELETE'])
