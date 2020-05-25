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

# # Compute

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


def create_compute_resources():
    network_id = create_network_resources()
    if not network_id:
        return False

    create_subnet(network_id)

    payload = {
        'computeName': 'cdasilva-compute',
        'computeFlavourId': '2',
        'vcImageId': 'b06cc64a-401a-4886-b91f-e917214bd207',
        'networkId': network_id
    }
    # 'computeFlavourId': '19fc95dc-9598-4b81-9d36-8d76bfee46d0',  # m1.small
    r = requests.post(
        'http://127.0.0.1:8000/compute_resources', json=payload)
    compute = json.loads(r.text)

    if r.status_code != 201:
        return False

    compute_id = compute['computeId']
    return compute_id


def get_compute_capacity():
    payload = {
        'computeResourceTypeId': 'VirtualCpuResourceInformation'
    }

    url = 'http://127.0.0.1:8000/compute_resources/capacities/query'
    r = requests.post(url, json=payload)

    if r.status_code != 200:
        return False

    capacities = json.loads(r.text)

    return capacities


def get_compute_resources(compute_id):
    payload = {
        'computeResourceId': [compute_id]
    }

    r = requests.post(
        'http://127.0.0.1:8000/compute_resources/query', json=payload)

    if r.status_code != 200:
        return False

    resources = json.loads(r.text)

    compute_resource = [r for r in resources if r['computeId'] == compute_id]

    return len(compute_resource) == 1


def delete_compute_resources(compute_id):
    r = requests.delete(
        'http://127.0.0.1:8000/compute_resources?computeId=' + compute_id)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and compute_id in deleted_resources


def test_compute_resources_management():
    compute_id = create_compute_resources()
    assert compute_id

    assert get_compute_resources(compute_id)

    assert delete_compute_resources(compute_id)


def test_compute_capacity():
    compute_id = create_compute_resources()
    assert compute_id

    assert get_compute_capacity()
