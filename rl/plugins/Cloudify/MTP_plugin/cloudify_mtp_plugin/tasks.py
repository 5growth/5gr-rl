# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import json
import requests
from cloudify.decorators import operation
from cloudify.exceptions import NonRecoverableError
from cloudify import compute
from cloudify import exceptions

REQUEST_TIMEOUT = 3000
MTP_ID_PROPERTY = 'external_id'  # resource's openstack id
MTP_TYPE_PROPERTY = 'external_type'  # resource's openstack type
MTP_NAME_PROPERTY = 'external_name'  # resource's openstack name
MTP_EXTERNAL_RESOURCE = 'external_resource'
MTP_RESOURCE_EXISTS = 'if_external_resource_exists'

def convert_metadata_array_to_dict(data):
    metadata = {}
    for item in data:
        elt = { item['key'] : item['value'] }
        metadata.update(elt)
    return metadata

def get_relationships_by_openstack_type(ctx, type_name):
    return [rel for rel in ctx.instance.relationships
            if rel.target.instance.runtime_properties.get(
            MTP_TYPE_PROPERTY) == type_name]


def get_mtp_config(ctx):
    if 'mtp_config' not in ctx.node.properties.keys():
        raise NonRecoverableError("Do not set mtp_config")
    mtp_config = ctx.node.properties['mtp_config']
    if 'url' not in ctx.node.properties['mtp_config'].keys():
        raise NonRecoverableError("Do not set url in mtp_config")
    return mtp_config

@operation
def create_mtp_subnet_vl(ctx, **kwargs):
    ctx.logger.info("MTP creating intra subnet")
    add_url = "/mtpbase/network_resources"
    mtp_network_props = ctx.node.properties['mtp_network']
    mtp_config = get_mtp_config(ctx)
    ctx.logger.info("request body:" + json.dumps(mtp_network_props, indent=4))
    json_string = json.dumps(mtp_network_props)
    http_session = requests.session()

    url = mtp_config['url'] + add_url
    headers = {"Content-Type": "application/json"}
    try:
        response_str = http_session.post(url, data=json_string, headers=headers, timeout=REQUEST_TIMEOUT)
    except requests.exceptions.RequestException as e:
        ctx.logger.info(e)
        return ctx.operation.retry(message='Connection error to url ' + mtp_config['url'])
    http_session.close()

    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))

    if response_str.status_code not in [200, 201, 202, 203]:
        raise NonRecoverableError("Request to create network returned wrong code")

    response = json.loads(response_str.text)
    response['networkData']['metadata'] = convert_metadata_array_to_dict(response['networkData']['metadata'])
    response['subnetData']['metadata'] = convert_metadata_array_to_dict(response['subnetData']['metadata'])
    ctx.instance.runtime_properties.update({MTP_ID_PROPERTY: str(response['subnetData']['networkId'])})
    ctx.instance.runtime_properties.update({MTP_NAME_PROPERTY: str(response['subnetData']['metadata']['name'])})
    ctx.instance.runtime_properties.update({MTP_TYPE_PROPERTY: "mtp_subnet_vl"})
    ctx.instance.runtime_properties.update({MTP_EXTERNAL_RESOURCE: response})


@operation
def delete_mtp_subnet_vl(ctx, **kwargs):
    ctx.logger.info("MTP deleting intra subnet")
    add_url = "/mtpbase/network_resources?networkResourceId="
    runtime_properties = ctx.instance.runtime_properties
    network_resource_id = runtime_properties[MTP_ID_PROPERTY]

    if 'mtp_config' not in ctx.node.properties.keys():
        raise NonRecoverableError("Do not set mtp_config")
    mtp_config = ctx.node.properties['mtp_config']

    if 'url' not in mtp_config.keys():
        raise NonRecoverableError("Do not set url in mtp_config")

    http_session = requests.session()
    url = mtp_config['url'] + add_url + network_resource_id
    headers = {"Content-Type": "application/json"}

    ctx.logger.info(url)
    response_str = http_session.delete(url, headers=headers, timeout=REQUEST_TIMEOUT)
    http_session.close()
    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))

    if response_str.status_code not in [200, 201, 202, 203]:
        raise NonRecoverableError("Request to delete network returned wrong code")


@operation
def create_flavour(ctx, **kwargs):
    ctx.logger.info("MTP creating flavour")
    add_url = "/v1/compute_resources/flavours"
    mtp_flavour_props = ctx.node.properties['mtp_flavour']
    mtp_config = get_mtp_config(ctx)
    ctx.logger.info("request body:" + json.dumps(mtp_flavour_props, indent=4))
    json_string = json.dumps(mtp_flavour_props)
    http_session = requests.session()
    url = mtp_config['url'] + add_url
    headers = {"Content-Type": "application/json"}
    try:
        response_str = http_session.post(url, data=json_string, headers=headers, timeout=REQUEST_TIMEOUT)
    except requests.exceptions.RequestException as e:
        ctx.logger.info(e)
        return ctx.operation.retry(message='Connection error to url ' + mtp_config['url'])
    http_session.close()
    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))

    if response_str.status_code in [500]:
        if "already exists" in response_str.text:
            ctx.logger.warn("Flavour already exists:" + response_str.text)
            ctx.instance.runtime_properties.update({MTP_RESOURCE_EXISTS: "true"})
        else:
            raise NonRecoverableError("Request to create network returned wrong message")

    if response_str.status_code not in [200, 201, 202, 203, 500]:
        raise NonRecoverableError("Request to create flavour returned wrong code")
    else:
        ctx.logger.info("Flavour created")

    ctx.instance.runtime_properties.update({MTP_ID_PROPERTY: mtp_flavour_props['flavourId']})
    ctx.instance.runtime_properties.update({MTP_NAME_PROPERTY: mtp_flavour_props['flavourId']})
    ctx.instance.runtime_properties.update({MTP_TYPE_PROPERTY: "mtp_flavour"})
    ctx.instance.runtime_properties.update({MTP_EXTERNAL_RESOURCE: mtp_flavour_props})


@operation
def delete_flavour(ctx, **kwargs):
    runtime_properties = ctx.instance.runtime_properties
    if MTP_RESOURCE_EXISTS in runtime_properties.keys():
        ctx.logger.info("MTP flavour will not be deleted because it existed before deployment")
        return

    ctx.logger.info("Deleting MTP flavour")
    add_url = "/v1/compute_resources/flavours/"
    network_resource_id = runtime_properties[MTP_ID_PROPERTY]
    mtp_config = get_mtp_config(ctx)
    http_session = requests.session()
    url = mtp_config['url'] + add_url + network_resource_id
    headers = {"Content-Type": "application/json"}
    ctx.logger.info(url)
    response_str = http_session.delete(url, headers=headers, timeout=REQUEST_TIMEOUT)
    http_session.close()
    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))
    if response_str.status_code not in [200, 201, 202, 203]:
        raise NonRecoverableError("Request to delete flavor returned wrong code")
    ctx.logger.info("MTP delete flavour")


@operation
def create_compute(ctx, **kwargs):
    ctx.logger.info("MTP creating compute")
    add_url = "/mtpbase/abstract-compute-resources"
    mtp_compute_props = ctx.node.properties['mtp_compute']
    mtp_config = get_mtp_config(ctx)

    service_id = ""
    for prop in mtp_compute_props['metadata']:
        if prop['key'] == 'ServiceId':
            service_id = prop['value']
            break;

    rel_networks = get_relationships_by_openstack_type(ctx, 'mtp_subnet_vl')
    if 'interfaceData' not in mtp_compute_props:
        if len(rel_networks) > 0:
            for rel_network in rel_networks:
                id_network = str(rel_network.target.instance.runtime_properties[MTP_EXTERNAL_RESOURCE]
                ['networkData']['metadata']['id'])
                mtp_compute_props.update({"interfaceData": []})
                mtp_compute_props["interfaceData"].append({"networkId": id_network,
                                                           "ipAddress": '',
                                                           "macAddress": ''})
    else:
        for idx, interfaceData in enumerate(mtp_compute_props['interfaceData']):
            id_network = interfaceData['networkId']
            is_found = False
            for rel_network in rel_networks:
                if id_network == rel_network.target.node.name:
                    id_network = str(rel_network.target.instance.runtime_properties[MTP_EXTERNAL_RESOURCE]
                                     ['networkData']['metadata']['id'])
                    interfaceData['networkId'] = id_network
                    is_found = True
                    break;
            if not is_found:
                del(mtp_compute_props['interfaceData'][idx])

    rel_floating_ips = get_relationships_by_openstack_type(ctx, 'mtp_floating_ip')

    if len(rel_floating_ips) > 1:
        raise NonRecoverableError("Floating set more than one")

    if len(rel_floating_ips) != 0:
        rel_floating_ip = rel_floating_ips[0]
        floating_ip_ip = \
        rel_floating_ip.target.instance.runtime_properties['external_resource']['networkPortData']['metadata'][
            'floating_ip']
        floating_ip_network = rel_floating_ip.target.node.properties['mtp_floating_ip']['networkResourceName']
        mtp_compute_props['metadata'].update({'admin-network-name': floating_ip_network})
        mtp_compute_props['metadata'].update({'floating-ip': floating_ip_ip})

    existing_userdata = mtp_compute_props['userData']['content']
    install_agent_userdata = ctx.agent.init_script()
    if existing_userdata != "":
        final_userdata = compute.create_multi_mimetype_userdata(
            [existing_userdata, install_agent_userdata])
    else:
        final_userdata = install_agent_userdata
    mtp_compute_props['userData']['content'] = final_userdata
    ctx.logger.info("request body:" + json.dumps(mtp_compute_props, indent=4))
    json_string = json.dumps(mtp_compute_props)
    http_session = requests.session()
    url = mtp_config['url'] + add_url
    headers = {"Content-Type": "application/json"}
    try:
        response_str = http_session.post(url, data=json_string, headers=headers, timeout=REQUEST_TIMEOUT)
    except requests.exceptions.RequestException as e:
        ctx.logger.info(e)
        return ctx.operation.retry(message='Connection error to url ' + mtp_config['url'])
    http_session.close()
    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))
    if response_str.status_code not in [200, 201, 202, 203]:
        raise NonRecoverableError(
            "Request to create compute returned wrong code " + str(response_str.status_code) + response_str.text)
    response = json.loads(response_str.text)
    try:
        interfaces_in = response['virtualNetworkInterface']
        interfaces_out = {}
        service_id_str = str(service_id) + "_"
        for interface in interfaces_in:
            interface_name = str(interface['networkName'])
            interface_name = interface_name.replace(service_id_str, "")
            interfaces_out.update({interface_name: interface})
        response['virtualNetworkInterface'] = interfaces_out
        ctx.instance.runtime_properties.update({MTP_ID_PROPERTY: response['computeId']})
        ctx.instance.runtime_properties.update({MTP_NAME_PROPERTY: response['computeName']})
        ctx.instance.runtime_properties.update({MTP_TYPE_PROPERTY: "mtp_compute"})
        ctx.instance.runtime_properties.update({MTP_EXTERNAL_RESOURCE: response})
    except TypeError as e:
        raise NonRecoverableError("Request to create compute returned wrong response format " + response_str.text)

@operation
def delete_compute(ctx, **kwargs):
    ctx.logger.info("MTP deleting compute")
    add_url = "/mtpbase/abstract-compute-resources?computeId="
    runtime_properties = ctx.instance.runtime_properties
    network_resource_id = runtime_properties[MTP_ID_PROPERTY]

    if 'mtp_config' not in ctx.node.properties.keys():
        raise NonRecoverableError("Do not set mtp_config")
    mtp_config = ctx.node.properties['mtp_config']

    if 'url' not in mtp_config.keys():
        raise NonRecoverableError("Do not set url in mtp_config")

    http_session = requests.session()
    url = mtp_config['url'] + add_url + network_resource_id
    headers = {"Content-Type": "application/json"}

    ctx.logger.info(url)
    response_str = http_session.delete(url, headers=headers, timeout=REQUEST_TIMEOUT)
    http_session.close()
    ctx.logger.info("response.status_code = " + str(response_str.status_code))
    ctx.logger.info("response.text = " + str(response_str.text))

    if response_str.status_code not in [200, 201, 202, 203]:
        raise NonRecoverableError("Request to delete network returned wrong code")
