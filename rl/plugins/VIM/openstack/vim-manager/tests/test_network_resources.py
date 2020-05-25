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
# -*- coding: utf-8 -*

import json
import requests


# Resources

# # Network

def create_subnet(network_id):
    payload = {
        'networkResourceName': 'cdasilva-subnet-network',
        'reservationId': 'cdasilva-subnet-id',
        'networkResourceType': 'subnet',
        'typeSubnetData': {
            'networkId': network_id,
            'ipVersion': 'IPv4',
            'cidr': '192.168.0.0/24',
            'gatewayIp': '192.168.0.1',
            'isDhcpEnabled': 1
        }
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources', json=payload)
    network = json.loads(r.text)

    if r.status_code != 201:
        return False

    subnet_id = network['subnetData']['resourceId']
    return subnet_id


def create_network_resources():
    payload = {
        'networkResourceName': 'cdasilva-network',
        'reservationId': 'cdasilva-network-id',
        'networkResourceType': 'network'
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources', json=payload)
    network = json.loads(r.text)

    if r.status_code != 201:
        return False

    network_id = network['networkData']['networkResourceId']
    return network_id


def get_network_resources(network_id):
    payload = {
        'networkResourceId': [network_id]
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources/query', params=payload)

    if r.status_code != 200:
        return False

    resources = json.loads(r.text)

    network_resource = [
        r for r in resources if r['networkResourceId'] == network_id]

    return len(network_resource) == 1


def delete_network_resources(network_id):
    r = requests.delete(
        'http://127.0.0.1:8000/network_resources?'
        'networkResourceId=' + network_id)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and network_id in deleted_resources


def get_network_capacity(network_id):
    payload = {
        'zoneId': '',
        'networkResourceId': network_id,
        'resourceCriteria': '',
        'attributeSelector': '',
        'timePeriod': '',
    }
    r = requests.post(
        'http://127.0.0.1:8000/network_resources/capacities/query',
        json=payload)

    if r.status_code != 200:
        return False

    capacities = json.loads(r.text)

    return capacities


def test_network_resources_management():
    network_id = create_network_resources()
    if not network_id:
        assert False

    assert get_network_resources(network_id)

    assert delete_network_resources(network_id)


def test_network_capacity():
    network_id = create_network_resources()
    if not network_id:
        assert False

    subnet_id = create_subnet(network_id)
    if not subnet_id:
        assert False

    assert get_network_capacity(network_id)

    assert delete_network_resources(network_id)
