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
import sys
import time

from vim_manager.osc.clients import OpenStackClients

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager import conf
from vim_manager.api import common
from vim_manager.api.compute.schema import VirtualCompute
from vim_manager.api.compute.schema import VirtualInterfaceData

from vim_manager.api.schema import AffinityOrAntiAffinityConstraint
from vim_manager.api.schema import Filter
from vim_manager.api.schema import Identifier
from vim_manager.api.schema import UserData
from vim_manager.api.schema import KeyValuePair

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_management', __name__)

def extract_virtual_compute(server, server_ports):
    operational_state = 'enabled' if server.status == 'ACTIVE' else 'disabled'
    ports = extract_virtual_network_interface(server_ports)
    for network_name, interface_addresses in server.addresses.items():
        for interface_address in interface_addresses:
            for port in ports:
                if port['macAddress'] == interface_address['OS-EXT-IPS-MAC:mac_addr']:
                    port.update({"networkName": network_name})
                    port.update({"metadata": [{"key" : "dc", "value": conf.cfg.CONF.nfvi_pop.nfviPopId}]})
                    if interface_address['OS-EXT-IPS:type'] == 'floating':
                        port.update({"floatingIP": interface_address['addr']})

    return {
        'computeId': server.id,
        'computeName': server.name,
        'flavourId': server.flavor['id'],
        'accelerationCapability': [],
        'virtualCpu': extract_virtual_cpu(server),
        'virtualMemory': extract_virtual_memory(server),
        #'virtualNetworkInterface': server.networks,
        'virtualNetworkInterface': ports,
        'virtualDisks': '',
        'vcImageId': '',
        'zoneId': getattr(server, 'OS-EXT-AZ:availability_zone'),
        'hostId': server.hostId,
        'operationalState': operational_state
    }


def extract_virtual_cpu(server):
    return {
        'cpuArchitecture': '',
        'numVirtualCpu': 0,
        'cpuClock': 0,
        'virtualCpuOversubscriptionPolicy': '',
        'virtualCpuPinning': extract_virtual_cpu_pinning(server)
    }

def extract_virtual_network_interface(server_ports):
    # server_ports=[{'allowed_address_pairs': [], 'extra_dhcp_opts': [], 'updated_at': '2019-07-22T12:21:00Z', 'device_owner': 'compute:nova', 'revision_number': 11, 'port_security_enabled': True, 'binding:profile': {}, 'fixed_ips': [{'subnet_id': '3437278e-40cc-4ad8-8b8e-6eeed5a41bbc', 'ip_address': '192.168.120.107'}], 'id': '5a6f16db-a9bd-476a-bd61-4f669afa8727', 'security_groups': ['3a058ff6-806f-4113-b20c-e136dc3f6150'], 'binding:vif_details': {'port_filter': True}, 'binding:vif_type': 'bridge', 'mac_address': 'fa:16:3e:3a:ac:93', 'project_id': '9f16230284044eaba08bd8eacb1e5550', 'status': 'ACTIVE', 'binding:host_id': 'rdev-opcisrv107', 'description': '', 'tags': [], 'device_id': 'd3bc1a60-e44c-4217-b37d-2b365fe06574', 'name': '', 'admin_state_up': True, 'network_id': 'f9b79a53-deef-4ead-8fbd-0eabd1ee4399', 'tenant_id': '9f16230284044eaba08bd8eacb1e5550', 'created_at': '2019-07-22T12:20:17Z', 'binding:vnic_type': 'normal'}]
    #import ipdb; ipdb.set_trace()
    vnis=[]
    for port in server_ports:
        ips=[]
        for ip in port['fixed_ips']:
            ips.append(ip['ip_address'])
        vni= {
            'resourceId': port['id'],
            'ownerId': port['device_id'],
            'networkId': port['network_id'],
            'networkPortId': port['id'],
            'ipAddress': ips,
            'typeVirtualNic': port['binding:vnic_type'],
            'typeConfiguration': port['binding:vif_type'],
            'macAddress': port['mac_address'],
            'bandwidth': '0',
            'accelerationCapability': '',
            'operationalState': port['status'],
            'metadata': []
        }
        vnis.append(vni)
    return vnis
   

def extract_virtual_cpu_pinning(server):
    policy = 'dynamic' if True else 'static'
    return {'cpuPinningPolicy': policy, 'cpuPinningRules': '', 'cpuMap': ''}


def extract_virtual_memory(server):
    return {
        'virtualMemSize': 0,
        'virtualMemOversubscriptionPolicy': None,
        'numaEnabled': False
    }

# format metadata requested by api IFA005
# from [ { "key" : "my_key_name1", "value" : "my_value1" }, ..., { "key" : "my_key_nameN", "value" : "my_valueN" } ]
# to { "my_key_name1" : "my_value1", ..., "my_key_nameN" : "my_valueN"}
def convert_metadata_array_to_dict(data):
    metadata = {}
    for item in data:
        elt = { item['key'] : item['value'] }
        metadata.update(elt)
    return metadata


class AllocateComputeRequest(Schema):
    computeName = fields.Str(
        required=True,
        description='Name provided by the consumer for the virtualised '
        'compute resource to allocate. It can be used for '
        'identifying resources from consumer side.')
    reservationId = fields.Str(
        required=True,
        description='Identifier of the resource reservation applicable to '
        'this virtualised resource management operation. '
        'Cardinality can be 0 if no resource reservation was '
        'used.')
    computeFlavourId = fields.Str(
        required=True,
        description='Identifier of the Compute Flavour that provides '
        'information about the particular memory, CPU and disk '
        'resources for virtualised compute resource to allocate. '
        'The Compute Flavour is created with Create Compute '
        'Flavour operation')
    affinityOrAntiAffinityConstraints = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='A list of elements with affinity or anti affinity '
        'information of the virtualised compute resource to '
        'allocate. There should be a relation between the '
        'constraints defined in the different elements of the '
        'list.')
    interfaceData = fields.Nested(
        VirtualInterfaceData,
        many=True,
        required=True,
        description='The data of network interfaces which are specific to a '
        'Virtual Compute Resource instance.')
    vcImageId = fields.Str(
        required=True,
        description='Identifier of the virtualisation container software '
        'image (e.g. a virtual machine image). Cardinality can be '
        '0 if an "empty" virtualisation container is allocated. ')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised resource.')
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
        'group", logical grouping of virtual resources assigned '
        'to a tenant within an Infrastructure Domain.')
    locationConstraints = fields.Str(
        required=True,
        description='If present, it defines location constraints for the '
        'resource(s) is (are) requested to be allocated, e.g. in '
        'what particular Resource Zone.')
    userData = fields.Nested(
        UserData,
        required=True,
        description='Element containing user data to customize the '
        'virtualised compute resource at boot-time.')


class VirtualisedComputeResourceAllocateAPI(SwaggerView):
    """Allocate Virtualised Compute Resource operation.

    This operation allows requesting the allocation of virtualised compute
    resources as indicated by the consumer functional block.
    """

    parameters = [{
        "name": "body",
        "in": "body",
        "schema": AllocateComputeRequest,
        "required": True
    }]
    responses = {
        CREATED: {
            'description': 'Element containing information of the newly '
                           'instantiated virtualised compute resource.',
            'schema': VirtualCompute,
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

    tags = ['virtualisedComputeResources']
    operationId = "allocateComputeResources"

    def convert_name_to_uid_image(self, name):
        config = flask.current_app.osloconfig
        glance = OpenStackClients(config).glance()
        for image in glance.images.list():
            if image.name == name:
                return image.id
        return name

    def convert_name_to_uid_flavour(self, name):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()
        for flavors in nova.flavors.list():
            if flavors.name == name:
                return flavors.id
        return name



    def post(self):
        data = flask.request.get_json()
        print('-----------------------------------------------')
        print(data)
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()
        neutron = OpenStackClients(config).neutron()

        name = data['computeName']
        image = self.convert_name_to_uid_image(data['vcImageId'])

        flavour = self.convert_name_to_uid_flavour(data['computeFlavourId'])

        external_network_name = conf.cfg.CONF.nfvi_pop.floating_network

        networks = neutron.list_networks(name=external_network_name)['networks']
        if len(networks) > 1:
            message = "More then one networks with name:" + external_network_name
            raise Exception(message)
        if len(networks) == 0:
            message = "No networks with name:" + external_network_name
            print(message)
            raise Exception(message)

        floating_network_id = networks[0]['id']

        _nics=[]
        interfaceData =  data.get('interfaceData')
        for item in interfaceData:

            port_body = {
                "port": {
                    "admin_state_up": "true",
                    "network_id": item['networkId'],
                    "port_security_enabled": "false",
                }
            }
            if 'ipAddress' in item.keys() and item['ipAddress'] != "":
                port_body['port'].update({"fixed_ips":[{"ip_address": item['ipAddress']}]})
            if 'macAddress' in item.keys() and item['macAddress'] != "":
                port_body['port'].update({"mac_address": item['macAddress']})

            port = neutron.create_port(port_body)
            _nics.append( { 'port-id' : port['port']['id'] } )

            if 'floatingIP' in item.keys():
                        floating_body = \
                        {
                            "floatingip": {
                                "floating_network_id": floating_network_id,
                                "port_id": port['port']['id']
                            }
                        }
                        if item['floatingIP'] != "":
                            floating_body['floatingip'].update({'floating_ip_address': item['floatingIP']})
                        floating_ip = neutron.create_floatingip(floating_body)


        if 'metadata' in data:
            metadata = convert_metadata_array_to_dict(data['metadata'])
            if 'key-name' in metadata.keys():
                key_name = metadata['key-name']
            else:
               key_name=None

        if 'userData' in data and 'content' in data['userData'].keys():
            user_data = data['userData']['content']
        else:
            user_data = None

        kwargs = dict(
            meta=None,
            files={},
            reservation_id=None,
            min_count=1,
            max_count=1,
            security_groups=[],
            userdata=user_data,
            # key_name=None,
            key_name=key_name,
            availability_zone=None,
            block_device_mapping_v2=[],
            nics = _nics,
            scheduler_hints={},
            config_drive=None,
        )

        #import ipdb; ipdb.set_trace()

        server = nova.servers.create(name, image, flavour, **kwargs)

        # wait the vm to be up to see all information in server params (including server.networks)

        count = 0
        max_count = 25
        time.sleep(27)
        while (server.status == 'BUILD') and (count < max_count):
            time.sleep(3) # to avoid to access openstack to often
            # the try is to avoid crash if the server doesn't yet exist just wait
            try:
                server = nova.servers.get(server.id)
            except Exception:
                pass



            count = count + 1

        # add foating ip address if there is one in metadata params
        if metadata and 'floating-ip' in metadata.keys():
            admin_ipaddr = None
            # get the local admin ip address to bound the floating ip address
            if 'admin-network-name' in metadata.keys() and metadata['admin-network-name'] in server.networks.keys():
                # the local ip address is always index 0 hence the hardcoded value [0]
                admin_ipaddr = server.networks[metadata['admin-network-name']][0]
            else:
                # TODO if we stop we probably need to delete the vtep vm because if we try another time
                # it won't work as the vm will already exist so we have to think of a way to clean up if
                # ther is a problem
                return flask.jsonify('Error wrong admin-network-name, expecting: ' +  str(server.networks.keys())  ), OK

            admin_network_id  = neutron.list_networks(name=metadata['admin-network-name'])['networks'][0]['id']
            # get the port_id of the vm_vtp admin interface ip for floating ip mapping
            ports = neutron.list_ports(network_id=admin_network_id)['ports']
            for port in ports:
                for fixed_ip in port['fixed_ips']:
                    if (fixed_ip['ip_address'] == admin_ipaddr):
                        port_id = port['id']
                        break

            body_update_floatingip = {
                "floatingip": {
                    "port_id": port_id
                }
            }
            # get the id of the floating ip that we want to bind to the the vtep_vm
            floatingip_id = neutron.list_floatingips(floating_ip_address=metadata['floating-ip'])['floatingips'][0]['id']
            # attach the floating ip to the interface of the vm
            float_update = neutron.update_floatingip(floatingip_id, body=body_update_floatingip)
            print (float_update)
        
        #import ipdb; ipdb.set_trace()
        server_ports = neutron.list_ports(device_id=server.id)['ports']

        data = extract_virtual_compute(server, server_ports)
        print(data)
        print('-------------------------------------------------')

        return flask.jsonify(data), CREATED


class VirtualisedComputeResourceTerminateAPI(SwaggerView):
    """Terminate Virtualised Compute Resource operation.

    This operation allows de-allocating and terminating one or more
    instantiated virtualised compute resource(s). When the operation is done
    on multiple resources, it is assumed to be best-effort, i.e. it can
    succeed for a subset of the resources, and fail for the remaining ones.
    """

    parameters = [{
        'name': 'computeId',
        'in': 'query',
        'type': 'array',
        'items': {
            'type': Identifier
        },
        'description': 'Identifier(s) of the virtualised compute '
                       'resource(s) to be terminated.',
        'required': True
    }]

    responses = {
        OK: {
            'description': 'Identifier(s) of the virtualised compute '
                           'resource(s) successfully terminated.',
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

    tags = ['virtualisedComputeResources']
    operationId = "TerminateComputeResources"

    def delete(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()
        neutron = OpenStackClients(config).neutron()
        compute_ids = flask.request.args.getlist('computeId')

        ports = neutron.list_ports(device_id=compute_ids)
        for port in ports["ports"]:
            floatingIPs = neutron.list_floatingips(port_id=port['id'])
            for floatingIP in floatingIPs['floatingips']:
                neutron.delete_floatingip(floatingIP['id'])
            neutron.delete_port(port['id'])
        stopped_servers = []
        for compute_id in compute_ids:
            try:
                address = None
                server = nova.servers.get(compute_id)
                for key, value in server.addresses.items():
                    for item in value:
                        if item['OS-EXT-IPS:type'] == 'floating':
                            address = item['addr']
                            break
                server.delete()
                if address != None:
                    floatingip_id = neutron.list_floatingips(floating_ip_address=address)['floatingips'][0]['id']
                    neutron.delete_floatingip(floatingip_id)

                stopped_servers.append(compute_id)
            except Exception:
                pass

        return flask.jsonify(stopped_servers), OK


class VirtualisedComputeResourceQueryAPI(SwaggerView):
    """Query Virtualised Compute Resource operation.

    This operation allows querying information about instantiated virtualised
    compute resources.
    """

    parameters = [{
        "name": "queryComputeFilter",
        "in": "body",
        "schema": Filter,
        "description": "Query filter based on e.g. name, identifier, "
                       "meta-data information or status information, "
                       "expressing the type of information to be retrieved. "
                       "It can also be used to specify one or more resources "
                       "to be queried by providing their identifiers.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Element containing information about the virtual '
                           'compute resource(s) matching the filter. The '
                           'cardinality can be 0 if no matching compute '
                           'resources exist.',
            'schema': {
                'type': 'array',
                'items': VirtualCompute
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
    operationId = "queryComputeResources"

    def get_param_value(self, server, param):

        elt_list = param.split('.')
        size_list = len(elt_list)

        if ( size_list == 1):
            return server[elt_list[0]]
        elif  ( size_list == 2):
            return server[elt_list[0]][elt_list[1]]
        elif  ( size_list == 3):
            return server[elt_list[0]][elt_list[1]][elt_list[2]]
        else:
            return '-1'

    def post(self):
        data_filter = flask.request.get_json()
        filter_list = list(data_filter.keys())

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        servers = nova.servers.list()
        resources = [extract_virtual_compute(server) for server in servers]

        filtered_ressource = []
        for server in resources:
            match = False
            for item in filter_list:
                # Get param value from server
                try:
                    value = self.get_param_value(server, data_filter[item]['param'])
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
                filtered_ressource.append(server)

        # import ipdb; ipdb.set_trace()
        return flask.jsonify(filtered_ressource), OK

blueprint.add_url_rule(
    '/v1/compute_resources/query',
    strict_slashes=False,
    view_func=VirtualisedComputeResourceQueryAPI.as_view(
        'queryComputeResources'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/compute_resources',
    strict_slashes=False,
    view_func=VirtualisedComputeResourceAllocateAPI.as_view(
        'allocateComputeResources'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/compute_resources',
    strict_slashes=False,
    view_func=VirtualisedComputeResourceTerminateAPI.as_view(
        'TerminateComputeResources'),
    methods=['DELETE'])
