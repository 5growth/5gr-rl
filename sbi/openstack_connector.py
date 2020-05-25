# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
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

# Author: Luca Vettori


"""
Implements some utils function for Openstack

"""
import time

from future.moves.urllib.parse import urlparse  # python3 compatible
from ipaddress import ip_address, ip_network
import requests
import json
import logging
from nbi.nbi_server import db_session
from db.db_models import *

logger = logging.getLogger('cttc_rl.sbi.openstack_connector')


def get_endpoints(catalog):
    """
    Retrieve main endpoints of Openstack (compute, volume3, network)
    :param catalog: catalog of Endpoints (in Token response)
    :return: tuple with compute, volume3, network endpoints
    """
    compute_endpoint = list(filter(lambda x: x['type'] == 'compute', catalog))[0]['endpoints'][0]['url']
    volume3_endpoint = list(filter(lambda x: x['type'] == 'volumev3', catalog))[0]['endpoints'][0]['url']
    network_endpoint = list(filter(lambda x: x['type'] == 'network', catalog))[0]['endpoints'][0]['url']
    return compute_endpoint, volume3_endpoint, network_endpoint


def query_resources(vim_values, start_time, end_time):
    """
    Retrieve resources of an Openstack
    :param start_time: start time analysis
    :param end_time: stop time analysis
    :param vim_values: vim data from database
    :return: tuple with nova, volume3 resources response request.
    """
    if vim_values is None:
        raise AttributeError('Openstack not well configured')
    else:
        token_response, project_id, token = token_request(vim_values)
        headers = {'X-Auth-Token': token}
        # retrieve all the endpoints for services module of Openstack from Token response
        catalog_endpoint = json.loads(token_response.content.decode('utf-8'))['token']['catalog']
        compute_endpoint_url, volume_endpoint_url, network_endpoint_url = get_endpoints(catalog_endpoint)
        logger.info("Compute url: {}".format(compute_endpoint_url))
        logger.info("Volume url: {}".format(volume_endpoint_url))
        logger.info("Network url: {}".format(network_endpoint_url))
        # request of info to Nova Module of Openstack
        nova_resource = requests.get('{}/{}/limits'.format(compute_endpoint_url, project_id), headers=headers)
        nova_resource_statistic = requests.get('{}/os-hypervisors/statistics'.format(compute_endpoint_url),
                                               headers=headers)
        if start_time is None and end_time is None:
            simple_tenant_usage_resource_url = ('{}/{}/os-simple-tenant-usage/{}'.format(compute_endpoint_url,
                                                                                         project_id, project_id))
        else:
            simple_tenant_usage_resource_url = ('{}/{}/os-simple-tenant-usage/{}?start={}&end={}'
                                                .format(compute_endpoint_url,
                                                        project_id, project_id, start_time, end_time))
        simple_tenant_usage_resource = requests.get(simple_tenant_usage_resource_url, headers=headers)
        # request of info to Volume Module of Openstack
        volume3_resource = requests.get('{}/limits'.format(volume_endpoint_url), headers=headers)
        # request of info to Network Module of Openstack
        network = requests.get('{}v2.0/networks'.format(network_endpoint_url), headers=headers)

        return nova_resource, volume3_resource, simple_tenant_usage_resource, network, nova_resource_statistic


def token_request(vim_values):
    """
    Retrieve token of an Openstack
    :param vim_values: vim data from database
    :return: tuple with token_response, project_id, token.
    """
    # Request for a Token to an Openstack server
    # Header of the request
    headers = {'Content-type': 'application/json'}
    # Body of the request
    body_for_token = {
        "auth": {
            "identity": {
                "methods": [
                    "password"
                ],
                "password": {
                    "user": {
                        "name": vim_values.username,
                        "domain": {
                            "name": "Default"
                        },
                        "password": vim_values.userpassword
                    }
                }
            }
            # , "scope": {
            #           "project": {
            #               "domain": {
            #                   "id": "default"
            #               },
            #               "name": "osm-project54"
            #           }
            #       }
        }
    }
    token_response = requests.post(vim_values.url + '/auth/tokens',
                                   data=json.dumps(body_for_token),
                                   headers=headers)
    # Token is in the response headers
    token = token_response.headers['X-Subject-Token']
    logger.info('Token retrieved')
    # internal Openstack id for  the project related to the VIM
    project_id = json.loads(token_response.content.decode('utf-8'))['token']['project']['id']
    return token_response, project_id, token


def retrieve_resource(vim):
    """
    Retrieve resource an Openstack, divided for mem/cpu/storage
    :param vim: vim data from database
    :return: dict with mem/cpu/storage.
    """
    mem = {}
    cpu = {}
    storage = {}
    resources = query_resources(vim, None, None)
    nova_res = json.loads(resources[0].content)['limits']['absolute']
    volume_res = json.loads(resources[1].content)['limits']['absolute']
    # memory section
    mem['allocated'] = nova_res['totalRAMUsed']
    mem['total'] = nova_res['maxTotalRAMSize']
    mem['available'] = nova_res['maxTotalRAMSize'] - nova_res['totalRAMUsed']
    mem['reserved'] = 0  # TODO #17 check reserved item
    # cpu section
    cpu['allocated'] = nova_res['totalCoresUsed']
    cpu['total'] = nova_res['maxTotalCores']
    cpu['available'] = nova_res['maxTotalCores'] - nova_res['totalCoresUsed']
    cpu['reserved'] = 0  # TODO #18 check reserved item
    # storage section
    storage['allocated'] = volume_res['totalGigabytesUsed']
    storage['total'] = volume_res['maxTotalVolumeGigabytes']
    storage['available'] = volume_res['maxTotalVolumeGigabytes'] - volume_res['totalGigabytesUsed']
    storage['reserved'] = 0  # TODO #19 check reserved item
    logger.info('Resources retrieved!')
    return {'mem': mem,
            'cpu': cpu,
            'storage': storage}


def get_status_os_network(info):
    """
    Get the status of network in Openstack (created in this framework)
    :param info: dict
    :return: bool, dict
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(domain_id=info['vimId']).first()
    # retrieve the token for OS
    token_response, project_id, token = token_request(vim_values)
    catalog_endpoint = json.loads(token_response.content.decode('utf-8'))['token']['catalog']
    compute_endpoint_url, volume_endpoint_url, network_endpoint_url = get_endpoints(catalog_endpoint)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    network_response = requests.get('{}v2.0/networks/{}'.format(network_endpoint_url, info['netId']),
                                    headers=headers)
    if network_response.status_code == requests.codes.ok:
        network_response = network_response.json()
        # logger.debug("network_response: {}".format(network_response)
        return True, network_response['network']
    else:
        # logger.debug("network_response.json()['NeutronError']: {}".format(network_response.json()['NeutronError']))
        logger.debug(network_response.json()['NeutronError'])
        logger.error("Error in getting network resource for VIM")
        return False, network_response.json()['NeutronError']


def create_os_networks(info_to_create_networks, metadata_subnet=None):
    """
    Create a network on a provider network in Openstack
    :param info_to_create_networks: dict
    :param metadata_subnet: dict
    :return: dict (of ids)
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(
        domain_id=info_to_create_networks['vimId']).first()  # to be done in RL more internal
    # retrieve the token for OS
    token_response, project_id, token = token_request(vim_values)
    catalog_endpoint = json.loads(token_response.content.decode('utf-8'))['token']['catalog']
    compute_endpoint_url, volume_endpoint_url, network_endpoint_url = get_endpoints(catalog_endpoint)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    # find uuid of provider_network
    provider_network = 'public'  # it could be other name
    # provider_network_floating = 'publicFloating'
    provider_network_floating = 'public'
    provider_network_uuid = ''
    network_response = requests.get('{}v2.0/networks'.format(network_endpoint_url), headers=headers)
    if network_response.status_code == requests.codes.ok:
        network_response = network_response.json()
    else:
        logger.error("Error in getting network resource for VIM")

    # retrieve the provider network uuid
    for net in network_response['networks']:
        if net['name'] == provider_network_floating:
            provider_network_uuid = net['id']

    # create the network in OS
    body_network = {
        "network": {
            "admin_state_up": 'true',
            "name": info_to_create_networks["name"],
            "provider:network_type": "vlan",
            "provider:physical_network": provider_network,
            "provider:segmentation_id": info_to_create_networks["vlan_id"],
            "shared": "true",
            "router:external": "true"
        }
    }
    logger.info("Request of Network Creation: POST, URL: {}, Body: {} ".format("{}v2.0/networks".format(network_endpoint_url), body_network))
    response_network = requests.post("{}v2.0/networks".format(network_endpoint_url),
                                     data=json.dumps(body_network),
                                     headers=headers)
    if response_network.status_code == 201:
        response_network = response_network.json()
    else:
        logger.error(response_network.content)
        raise KeyError("Error in creating Network")
    network_id = response_network['network']['id']
    network_name = response_network['network']['name']
    logger.info("Network '{}' created with id: {}".format(network_name, network_id))
    # create the subnet
    # be careful with dhcps, they consume ips and they can be in other vim's!!
    if info_to_create_networks["pool"] == 0:
        start_ip = 2  # because by default OS create the internal GW to the "xxx.xxx.xxx.1" ip-address
    else:
        start_ip = 1 + 20 * info_to_create_networks["pool"]
    # last pool (12) end to 254
    if info_to_create_networks["pool"] == 12:
        end_ip = 254
    else:
        end_ip = 20 * info_to_create_networks["pool"] + 20
    logger.debug("Pool {}: IP from {} to {}".format(info_to_create_networks['pool'], start_ip, end_ip))
    body_subnet = {
        "subnet": {
            "network_id": response_network['network']['id'],
            "name": response_network['network']['name'] + "-subnet",
            "ip_version": 4,
            "cidr": "192.168." + str(info_to_create_networks['CIDR']) + ".0/24",
            "allocation_pools": [
                {
                    "start": "192.168." + str(info_to_create_networks['CIDR']) + '.' + str(start_ip),
                    "end": "192.168." + str(info_to_create_networks['CIDR']) + '.' + str(end_ip)}
            ],
            "gateway_ip": "192.168." + str(info_to_create_networks['CIDR']) + ".1",
            "enable_dhcp": 'true'
        }
    }
    logger.info("Request of SubNetwork Creation: POST, URL: {}, Body: {} ".format("{}v2.0/subnets".format(network_endpoint_url), body_subnet))
    response_subnet = requests.post("{}v2.0/subnets".format(network_endpoint_url),
                                    data=json.dumps(body_subnet),
                                    headers=headers)
    if response_subnet.status_code == 201:
        response_subnet = response_subnet.json()
        logger.info("Subnet '{}' created successfully with id: {}".format(body_subnet['subnet']['name'],
                                                                          response_subnet['subnet']['id']))
    else:
        logger.error(response_subnet.json())
        raise KeyError("Error in creating Sub-Network: {}".format(response_subnet.content))

    subnet_id = response_subnet['subnet']['id']
    # here, I create the router if necessary
    router_id = ""
    if info_to_create_networks['floating_ips']:
        body_router = {
                 "router": {
                             "name": "router_net_" + response_network['network']['name'],
                             "description": "router between external network '{}' and vld '{}'".format(provider_network,
                                                                                                       network_name),
                             "external_gateway_info": {
                                 "network_id": provider_network_uuid,  # mandatory an uuid
                                 "enable_snat": "true",
                              },
                             "availability_zone_hints": [],
                             "admin_state_up": "true"
                            }
               }
        logger.info("Request of Router Creation: POST, URL: {}, Body: {} ".format("{}v2.0/routers"
                                                                                  .format(network_endpoint_url),
                                                                                  body_router))
        response_router = requests.post("{}v2.0/routers".format(network_endpoint_url),
                                        data=json.dumps(body_router),
                                        headers=headers)
        if response_router.status_code == 201:
            response_router = response_router.json()
            logger.info("Router '{}' created successfully with id: '{}'".format(body_router['router']['name'],
                                                                                response_router['router']['id']))
        else:
            logger.error("Error in creating Router")
            raise KeyError("Error in creating Router")
        router_id = response_router['router']['id']
        body_subnet_router = {
            "subnet_id": subnet_id
        }
        logger.info("Request of Router to be added to Subnet: PUT, URL: {}, Body: {} "
                    .format("{}v2.0/routers/{}/add_router_interface".format(network_endpoint_url, router_id),
                            body_subnet_router))
        response_subnet_router = requests.put(
            "{}v2.0/routers/{}/add_router_interface".format(network_endpoint_url, router_id),
            data=json.dumps(body_subnet_router), headers=headers)
        if response_subnet_router.status_code == requests.codes.ok:
            logger.info("Add subnet '{}' to router '{}'".format(body_subnet['subnet']['name'],
                                                                body_router['router']['name']))
        else:
            raise KeyError("Error in adding subnet to router")
        if 'mon_cidr' in metadata_subnet:
            if vim_values.monitoringEndpoint:
                # write static routes in the router
                body_static_routes = {
                    "router": {
                        "routes": [
                            {
                                "destination": metadata_subnet['mon_cidr'],
                                "nexthop": vim_values.monitoringEndpoint
                            }
                        ]
                    }
                }
                response_static_routes = requests.put(
                    "{}v2.0/routers/{}".format(network_endpoint_url, router_id),
                    data=json.dumps(body_static_routes), headers=headers)
                if response_static_routes.status_code == requests.codes.ok:
                    logger.info("Add static route to router '{}'".format(body_router['router']['name']))
                else:
                    raise KeyError("Error in adding static routes to to router")
            else:
                logger.info("Could not add static route because 'Monitoring Endpoint' not in the Domain List Field")
    # finishing creating the router
    return {"subnet_id": subnet_id, "router_id": router_id, "network_id": network_id}


def delete_os_networks(info_to_delete_networks):
    """
    Delete a network in Openstack
    :param info_to_delete_networks: dict
    :return: boolean
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(
        domain_id=info_to_delete_networks['vimId']).first()  # to be done in RL more internal
    # retrieve the token for OS
    token_response, project_id, token = token_request(vim_values)
    catalog_endpoint = json.loads(token_response.content.decode('utf-8'))['token']['catalog']
    compute_endpoint_url, volume_endpoint_url, network_endpoint_url = get_endpoints(catalog_endpoint)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    # retrieve the network_id of the network created
    network_response = requests.get('{}v2.0/networks'.format(network_endpoint_url), headers=headers)
    if network_response.status_code == requests.codes.ok:
        network_response = network_response.json()
    else:
        logger.error("Error in getting network resource for VIM")
        return False

    # retrieve the provider network uuid
    network_id = ''
    for net in network_response['networks']:
        if net['name'] == info_to_delete_networks['name']:
            network_id = net['id']

    if info_to_delete_networks['floating_ips']:
        # router_name = 'router_net_' + info_to_delete_networks['name']
        router_id = info_to_delete_networks['router_id']
        body_remove_subnet = {
                "subnet_id": info_to_delete_networks['subnet_id']
        }
        remove_subnet_from_router = requests.put(
            "{}v2.0/routers/{}/remove_router_interface".format(network_endpoint_url, router_id),
            data=json.dumps(body_remove_subnet), headers=headers)
        delete_router = requests.delete("{}v2.0/routers/{}".format(network_endpoint_url, router_id),
                                        headers=headers)
        if delete_router.status_code != 204:
            logger.error("Something went wrong in deleting network: '{}'".format(delete_router.json()['NeutronError']))
            return False
        else:
            logger.info("Removed router with id: {}".format(info_to_delete_networks['router_id']))
        time.sleep(5)  # it takes time to remove a router

    response_delete_net = requests.delete("{}v2.0/networks/{}".format(network_endpoint_url, network_id),
                                          headers=headers)
    if response_delete_net.status_code != 204:
        logger.error("Something went wrong in deleting network: '{}'".format(
            response_delete_net.json()['NeutronError']))
        return False
    else:
        logger.info("Deleted VL '{}' from VIM '{}'".format(info_to_delete_networks['name'],
                                                         info_to_delete_networks['vimId']))
        return True
