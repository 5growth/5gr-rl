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

# Quota

# # Compute


def create_quotas_compute_resources(project_id):
    payload = {
        'resourceGroupId': project_id,
        'virtualComputeQuota': {
            'numVCPUs': 2,
            'numVcInstances': 2,
            'virtualMemSize': 4096
        }
    }
    r = requests.post(
        'http://127.0.0.1:8000/quotas/compute_resources', json=payload)
    quotas = json.loads(r.text)
    return quotas


def get_quotas_compute_resources():
    r = requests.post('http://127.0.0.1:8000/quotas/compute_resources/query')

    if r.status_code != 200:
        return False

    quotas = json.loads(r.text)

    return quotas

# def get_network_resources(network_id):
#     payload = {
#         'networkResourceId': [network_id]
#     }
#     r = requests.get(
#             'http://127.0.0.1:8000/network_resources', params=payload)
#
#     if r.status_code != 200:
#         return False
#
#     resources = json.loads(r.text)
#
#     network_resource = [
#         r for r in resources if r['networkResourceId'] == network_id]
#
#     return len(network_resource) == 1


def delete_quotas_compute_resources(quota_id):
    url = 'http://127.0.0.1:8000/quotas/compute_resources?resourceGroupId='
    r = requests.delete(url + quota_id)

    deleted_quotas = json.loads(r.text)

    return r.status_code == 200 and quota_id in deleted_quotas


def test_quotas_network_resources():
    project_id = 'de30d94754e3482983d10121b0c76fd2'
    assert create_quotas_compute_resources(project_id)

    assert get_quotas_compute_resources()

    assert delete_quotas_compute_resources(project_id)

# # Network


def test_quotas_compute_resources_query():
    r = requests.post('http://127.0.0.1:8000/quotas/compute_resources/query')
    assert r.status_code == 200


def test_quotas_network_resources_query():
    r = requests.post('http://127.0.0.1:8000/quotas/network_resources/query')
    assert r.status_code == 200
