# coding: utf-8

from __future__ import absolute_import

import os
import unittest
from unittest.mock import patch

import deepdiff as deepdiff
from flask import json

from tests.integration.base_test_case import BaseTestCase
from tests.integration.test_vim_network_resources_controller import stub_configuration_file
from tests.integration.stub_results.stub_kubernetes_response_for_create_pod_request import \
    stub_read_namespaced_pod

resource_root = os.path.dirname(__file__)
os.chdir(resource_root + "/../../")

def stub_delete_namespaced_pod(**args):
    return None


class TestVirtualisedComputeResourcesController(BaseTestCase):

    # @unittest.skip("")
    @patch('sbi.kube_pod.client')
    @patch('sbi.kube_network.ConfigurationFile')
    def test_allocate_compute(self,
                              mock_config_file,
                              mock_kube_client
                              ):
        mock_config_file.return_value.config = stub_configuration_file()
        mock_kube_client.CoreV1Api.return_value.create_namespaced_pod.return_value = None


        mock_kube_client.CoreV1Api.return_value.read_namespaced_pod.return_value = stub_read_namespaced_pod()

        with open(resource_root + '/stub_results/create_pod_request.json', 'r') as infile:
            create_pod_request = json.load(infile)

        with open(resource_root + '/stub_results/create_pod_reference_response.json', 'r') as infile:
            json_response_reference = json.load(infile)

        response = self.client.open(
            '/v1/compute_resources',
            method='POST',
            data=json.dumps(create_pod_request),
            content_type='application/json'
        )
        self.assertEqual(response.status_code, 201, "201")
        json_response = json.loads(response.data)
        exclude_paths = ["root['virtualNetworkInterface'][0]['ipAddress'][0]",
                         "root['virtualNetworkInterface'][0]['macAddress']"]
        ddiff = deepdiff.DeepDiff(json_response_reference, json_response,
                                  verbose_level=0,
                                  exclude_paths=exclude_paths).to_dict()
        self.assertEqual(ddiff, {}, "allocate_compute returned wrong object")

    @patch('sbi.kube_pod.client')
    @patch('sbi.kube_network.ConfigurationFile')
    def test_terminate_compute(self,
                               mock_config_file,
                               mock_kube_client
                               ):
        mock_config_file.return_value.config = stub_configuration_file()
        mock_kube_client.CoreV1Api.return_value.delete_namespaced_pod.side_effect = stub_delete_namespaced_pod

        response = self.client.open(
            '/v1/compute_resources?computeId=fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1',
            method='DELETE'
        )
        self.assertEqual(response.status_code, 200, "200")
        json_response = json.loads(response.data)
        self.assertEqual(json_response, ['fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1'],
                         "terminate_compute returned wrong object")

