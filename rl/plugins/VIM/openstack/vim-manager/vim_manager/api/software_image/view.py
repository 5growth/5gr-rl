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
import flask
import http

from vim_manager.api import common
from vim_manager.api.schema import Filter
from vim_manager.api.schema import KeyValuePair
from vim_manager.osc.clients import OpenStackClients

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('software_image', __name__)


class SoftwareImageAddQuery(Schema):
    name = fields.Str(
        required=True, description='The name of the software image.')
    provider = fields.Str(
        required=True, description='The provider of the software image.')
    version = fields.Str(
        required=True, description='The version of the software image.')
    userMetadata = fields.Nested(
        KeyValuePair,
        required=True,
        description='User-defined metadata.',
        many=True)
    softwareImage = fields.Str(
        required=True, description='The binary software image file.')
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
        'group", logical grouping of virtual resources assigned '
        'to a tenant within an Infrastructure Domain.')
    visibility = fields.Str(
        required=True,
        description='Controls the visibility of the image. In case of '
        '"private" value the image is available only for the '
        'tenant assigned to the provided resourceGroupId and the '
        'administrator tenants of the VIM while in case of '
        '"public" value, all tenants of the VIM can use the '
        'image.')


class SoftwareImageInformation(Schema):
    id = fields.Str(
        required=True, description='The identifier of this software image.')
    name = fields.Str(
        required=True, description='The name of this software image.')
    provider = fields.Str(
        required=True, description='The provider of this software image.')
    version = fields.Str(
        allow_none=True,
        required=True,
        description='The version of this software image.')
    checksum = fields.Str(
        required=True, description='The checksum of the software image file.')
    containerFormat = fields.Str(
        required=True,
        description='The container format indicates whether the software '
        'image is in a file format that also contains metadata '
        'about the actual software.')
    diskFormat = fields.Str(
        required=True,
        description='The disk format of a software image is the format of the '
        'underlying disk image.')
    createdAt = fields.Str(
        required=True, description='The created time of this software image.')
    updatedAt = fields.Str(
        allow_none=True,
        required=True,
        description='The updated time of this software image.')
    minDisk = fields.Str(
        required=True, description='The minimal Disk for this software image.')
    minRam = fields.Str(
        required=True, description='The minimal RAM for this software image.')
    size = fields.Str(
        required=True, description='The size of this software image.')
    status = fields.Str(
        required=True, description='The status of this software image.')
    # userMetadata = fields.Str(description='User-defined metadata')


class SoftwareImageAddAPI(SwaggerView):
    """Add image operation.

    This operation allows adding a new software image to the image
    repository managed by the VIM.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": SoftwareImageAddQuery,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Metadata about the Software Image that has been '
                           'added.',
            'schema': SoftwareImageInformation,
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
            "description": "software image already added",
        },
    }

    tags = ['softwareImages']
    operationId = "addSoftwareImage"

    def post(self):
        request = flask.request
        if 'softwareImage' not in request.files:
            return flask.jsonify('No file part'), BAD_REQUEST

        file = request.files['softwareImage']

        # if user does not select file, browser also
        # submit an empty part without filename
        if file.filename == '':
            return flask.jsonify('No selected file'), BAD_REQUEST

        # filename = secure_filename(file.filename)

        data = request.values

        config = flask.current_app.osloconfig
        glance = OpenStackClients(config).glance()

        visibility = data.get('visibility', 'private')

        image = glance.images.create(
            name=data['name'],
            disk_format='iso',
            container_format='ova',
            visibility=visibility,
            provider=data.get('provider', ''),
            version=data.get('version', ''),
            filename=file.filename)
        glance.images.upload(image.id, file)

        image = glance.images.get(image.id)

        data = {
            'id': image['id'],
            'name': image['name'],
            'provider': image['owner'],
            'version': image.get('version', None),
            'checksum': image['checksum'],
            'containerFormat': image['container_format'],
            'diskFormat': image['disk_format'],
            'createdAt': image['created_at'],
            'updatedAt': image['updated_at'],
            'minDisk': image['min_disk'],
            'minRam': image['min_ram'],
            'size': image['size'],
            'status': image['status']
        }

        return flask.jsonify(data), CREATED


class SoftwareImagesQueryAPI(SwaggerView):
    """Query images operation.

    This operation allows querying the information of the software images
    in the image repository managed by the VIM.
    """

    parameters = [{
        "name": "imageQueryFilter",
        "in": "body",
        "schema": Filter,
        "description": "The filter is used to select the software image "
                       "instances on which this operation is to act.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'The information of all software images matching '
                           'the query.',
            'schema': {
                'type': 'array',
                'items': SoftwareImageInformation
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
    tags = ['softwareImages']
    operationId = "querySoftwareImages"

    def get_param_value(self, image, param):

        elt_list = param.split('.')
        size_list = len(elt_list)

        if ( size_list == 1):
            return image[elt_list[0]]
        elif  ( size_list == 2):
            return image[elt_list[0]][elt_list[1]]
        elif  ( size_list == 3):
            return image[elt_list[0]][elt_list[1]][elt_list[2]]
        else:
            return '-1'

    def post(self):
        data_filter = flask.request.get_json()
        filter_list = list(data_filter.keys())

        config = flask.current_app.osloconfig
        glance = OpenStackClients(config).glance()
        glance_images = glance.images.list()

        images = [{
            'id': image['id'],
            'name': image['name'],
            'provider': image['owner'],
            'version': image.get('version', None),
            'checksum': image['checksum'],
            'containerFormat': image['container_format'],
            'diskFormat': image['disk_format'],
            'createdAt': image['created_at'],
            'updatedAt': image['updated_at'],
            'minDisk': image['min_disk'],
            'minRam': image['min_ram'],
            'size': image['size'],
            'status': image['status']
        } for image in glance_images]

        filtered_image = []
        for image in images:
            match = False
            for item in filter_list:
                # Get param value from server
                try:
                    value = self.get_param_value(image, data_filter[item]['param'])

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
                filtered_image.append(image)

        return flask.jsonify(filtered_image), OK

class SoftwareImageQueryAPI(SwaggerView):
    """Query image operation.

    This operation allows querying information about a specific software
    image in the image repository managed by the VIM.
    """

    parameters = [{
        "name": "id",
        "in": "path",
        "type": "string",
        "description": "The identifier of the software image to be queried.",
        "required": True
    }]
    responses = {
        OK: {
            'description': 'The information of the software image matching '
                           'the query.',
            'schema': SoftwareImageInformation
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
    tags = ['softwareImages']
    operationId = "querySoftwareImage"

    def get(self, id):
        config = flask.current_app.osloconfig
        glance = OpenStackClients(config).glance()

        image = glance.images.get(id)

        data = {
            'id': image['id'],
            'name': image['name'],
            'provider': image['owner'],
            'version': image.get('version', None),
            'checksum': image['checksum'],
            'containerFormat': image['container_format'],
            'diskFormat': image['disk_format'],
            'createdAt': image['created_at'],
            'updatedAt': image['updated_at'],
            'minDisk': image['min_disk'],
            'minRam': image['min_ram'],
            'size': image['size'],
            'status': image['status']
        }

        return flask.jsonify(data), OK


class SoftwareImageDeleteAPI(SwaggerView):
    """Delete image operation.

    This operation enables the deletion of a software image from the VIM.
    """

    parameters = [{
        "name": "id",
        "in": "path",
        "type": "string",
        "description": "The identifier of the software image to be "
                       "deleted.",
        "required": True
    }]
    responses = {
        OK: {
            'description': 'The identifier of the software image successfully '
                           'deleted.',
            'schema': {
                'type': 'string'
            }
        },
        NOT_FOUND: {
            'description': 'Not found',
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

    tags = ['softwareImages']
    operationId = "deleteSoftwareImage"

    def delete(self, id):
        config = flask.current_app.osloconfig
        glance = OpenStackClients(config).glance()
        glance.images.delete(id)

        return flask.jsonify(id), OK


blueprint.add_url_rule(
    '/v1/software_images',
    strict_slashes=False,
    view_func=SoftwareImageAddAPI.as_view('addSoftwareImage'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/software_images/query',
    strict_slashes=False,
    view_func=SoftwareImagesQueryAPI.as_view('querySoftwareImages'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/software_images/<id>',
    strict_slashes=False,
    view_func=SoftwareImageQueryAPI.as_view('querySoftwareImage'),
    methods=['GET'])

blueprint.add_url_rule(
    '/v1/software_images/<id>',
    strict_slashes=False,
    view_func=SoftwareImageDeleteAPI.as_view('deleteSoftwareImage'),
    methods=['DELETE'])
