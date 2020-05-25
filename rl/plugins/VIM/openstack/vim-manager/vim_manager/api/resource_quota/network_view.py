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
from vim_manager.api.resource_quota.schema import ResourceGroupIds
from vim_manager.api.resource_quota.schema import VirtualNetworkQuota
from vim_manager.api.resource_quota.schema import VirtualNetworkQuotaData
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

blueprint = flask.Blueprint('network_resources_quota', __name__)


class CreateNetworkResourceQuotaRequest(Schema):
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
        'group", logical grouping of virtual resources assigned '
        'to a tenant within an Infrastructure Domain.')
    virtualNetworkQuota = fields.Nested(
        VirtualNetworkQuotaData,
        required=True,
        description='Type and configuration of virtualised network resources '
        'that need to be restricted by the quota, e.g. '
        '{"numPublicIps": 20}.')


class NetworkResourceQuotaCreateAPI(SwaggerView):
    """Create Network Resource Quota operation.

    This operation allows requesting the quota of virtualised network
    resources as indicated by the consumer functional block.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": CreateNetworkResourceQuotaRequest,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Element containing information about the quota '
                           'resource.',
            'schema': VirtualNetworkQuota,
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

    tags = ['virtualisedResourceQuota']
    operationId = "createNetworkQuota"

    def post(self):
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        data = flask.request.get_json()

        resource_group_id = data['resourceGroupId']
        # import ipdb; ipdb.set_trace()
        network_quota = data['virtualNetworkQuota']

        resources = {'quota': {}}
        if network_quota.get('numPublicIps'):
            resources['quota']['floatingip'] = network_quota.get(
                'numPublicIps')

        if network_quota.get('numPorts'):
            resources['quota']['port'] = network_quota.get('numPorts')

        if network_quota.get('numSubnets'):
            resources['quota']['subnet'] = network_quota.get('numSubnets')

        neutron.update_quota(resource_group_id, body=resources)

        quotas = neutron.show_quota(resource_group_id)['quota']
        quotas_data = {
            'resourceGroupId': resource_group_id,
            'numPublicIps': quotas['floatingip'],
            'numPorts': quotas['port'],
            'numSubnets': quotas['subnet'],
        }

        return flask.jsonify(quotas_data), CREATED


class NetworkResourceQuotaTerminateAPI(SwaggerView):
    """Terminate Network Resource Quota operation.

    This operation allows terminating one or more issued network resource
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
            'schema': ResourceGroupIds,
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
    operationId = "terminateNetworkQuota"

    def delete(self):
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        resource_group_ids = flask.request.args.getlist('resourceGroupId')

        deleted_ids = []
        for resource_group_id in resource_group_ids:
            try:
                neutron.delete_quota(resource_group_id)
                deleted_ids.append(resource_group_id)
            except Exception:
                pass

        return flask.jsonify(deleted_ids), OK


class NetworkResourceQuotaQueryAPI(SwaggerView):
    """Query Network Resource Quota operation.

    This operation allows querying information about quota network resources
    that the consumer has access to.
    """

    parameters = [{
        "name": "queryQuotaFilter",
        "in": "body",
        "schema": Filter,
        "description": "Query filter based on e.g. name, identifier, "
                       "meta-data information or status information, "
                       "expressing the type of information to be "
                       "retrieved. It can also be used to specify one or "
                       "more quotas to be queried by providing their "
                       "identifiers.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Element containing information about the quota '
                           'resource(s) matching the filter. The cardinality '
                           'can be 0 if no matching quota exists.',
            'schema': {
                'type': 'array',
                'items': VirtualNetworkQuota
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
    operationId = "queryNetworkQuota"

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
        neutron = OpenStackClients(config).neutron()
        session = OpenStackClients(config).session
        response = session.get('/v3/projects', endpoint_filter={'service_type': 'identity', 'interface': 'public', 'region_name': 'RegionOne'})
        content = json.loads(response.__dict__['_content'].decode("utf-8"))

        project_ids = [ p['id'] for p in content['projects'] ]

        quotas_projects = []
        for project_id in project_ids:
            quotas = neutron.show_quota(project_id)['quota']
            quotas_projects.append({
                'resourceGroupId': project_id,
                'numPublicIps': quotas['floatingip'],
                'numPorts': quotas['port'],
                'numSubnets': quotas['subnet'],
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
    '/v1/quotas/network_resources/query',
    strict_slashes=False,
    view_func=NetworkResourceQuotaQueryAPI.as_view('queryNetworkQuota'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/quotas/network_resources',
    strict_slashes=False,
    view_func=NetworkResourceQuotaCreateAPI.as_view('createNetworkQuota'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/quotas/network_resources',
    strict_slashes=False,
    view_func=NetworkResourceQuotaTerminateAPI.as_view(
        'terminateNetworkQuota'),
    methods=['DELETE'])
