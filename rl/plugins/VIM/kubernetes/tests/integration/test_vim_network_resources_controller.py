# coding: utf-8

from __future__ import absolute_import

import os
import unittest
from unittest.mock import patch

import deepdiff as deepdiff
import kubernetes
from flask import json
from urllib3 import HTTPResponse

from tests.integration.base_test_case import BaseTestCase

resource_root = os.path.dirname(__file__)
os.chdir(resource_root + "/../../")

def stub_list_namespaced_multus_network(args):
    with open(resource_root + '/stub_results/stub_list_namespaced_multus_network.txt', 'rb') as infile:
        body = infile.read()
    return_http_response = HTTPResponse(body=body, status=200)
    return return_http_response


def stub_configuration_file():
    with open(resource_root + '/stub_results/stub_configuration_file.json', 'r') as infile:
        json_object = json.load(infile)
    return json_object


def stub_create_namespaced_multus_network(**args):
    raise kubernetes.client.exceptions.ApiException(reason='Conflict')


def stub_get_namespaced_multus_network(**args):
    with open(resource_root + '/stub_results/stub_get_namespaced_multus_network.txt', 'rb') as infile:
        body = infile.read()
    return_http_response = HTTPResponse(body=body, status=200)
    return return_http_response


def stub_delete_namespaced_multus_network(**args):
    return_http_response = HTTPResponse(body="body", status=200)
    return return_http_response


class TestVIMNetworkResourcesController(BaseTestCase):

    @patch('sbi.kube_network.KubNetwork.list_namespaced_multus_network',
           side_effect=stub_list_namespaced_multus_network)
    @patch('sbi.kube_network.ConfigurationFile')
    def test_free_vlan(self, mock_config_file, mock_list_namespaced_multus_network):
        """Test case for free_vlan

        Retrieve free vlan tag from VIM domain
        """
        mock_config_file.return_value.config = stub_configuration_file()

        response = self.client.open(
            '/v1/network_resources/free_vlan',
            method='GET')
        json_response = json.loads(response.data)
        self.assert200(response, "Request get /v1/network_resources/free_vlan returned wrong status")
        reference_json_response = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
                                32, 33, 34, 35, 36, 37,
                                38, 39, 42, 43, 44, 45, 46, 47, 48, 49]
        self.assertEqual(reference_json_response, json_response,
                         "Request get /v1/network_resources/free_vlan returned wrong respponse")

    # @unittest.skip("")
    @patch('sbi.kube_network.ConfigurationFile')
    def test_create_network(self, mock_config_file):
        mock_config_file.return_value.config = stub_configuration_file()
        create_network_request = \
            {
                "metadata": [
                    {
                        "key": "interpop_vlan",
                        "value": "40"
                    }
                ],
                "networkResourceName": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl",
                "networkResourceType": "network",
                "reservationId": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl"
            }

        with open(resource_root + '/stub_results/create_network_reference_response.json', 'r') as infile:
            json_response_reference = json.load(infile)

        response = self.client.open(
            '/v1/network_resources',
            method='POST',
            data=json.dumps(create_network_request),
            content_type='application/json'
        )
        self.assertEqual(response.status_code, 201, "201")

        json_response = json.loads(response.data)
        ddiff = deepdiff.DeepDiff(json_response_reference, json_response, ignore_order=True, view='tree', verbose_level=2)
        self.assertEqual(ddiff, {}, "create_network returned wrong object")

    @patch('sbi.kube_network.KubNetwork.get_namespaced_multus_network',
           side_effect=stub_get_namespaced_multus_network)
    @patch('sbi.kube_network.KubNetwork.create_namespaced_multus_network',
           side_effect=stub_create_namespaced_multus_network)
    @patch('sbi.kube_network.ConfigurationFile')
    def test_create_subnet(self, mock_config_file, mock_create_namespaced_multus_network,
                           mock_get_namespaced_multus_network):
        mock_config_file.return_value.config = stub_configuration_file()
        with open(resource_root + '/stub_results/create_subnet_request.json', 'r') as infile:
            create_subnet_request = json.load(infile)

        with open(resource_root + '/stub_results/create_subnetwork_reference_response.json', 'r') as infile:
            json_response_reference = json.load(infile)

        response = self.client.open(
            '/v1/network_resources',
            method='POST',
            data=json.dumps(create_subnet_request),
            content_type='application/json'
        )
        self.assertEqual(response.status_code, 201, "201")
        json_response = json.loads(response.data)
        ddiff = deepdiff.DeepDiff(json_response_reference, json_response, ignore_order=True, verbose_level=2).to_dict()
        self.assertEqual(ddiff, {}, "create_subnetwork returned wrong object")

    @patch('sbi.kube_network.KubNetwork.delete_namespaced_multus_network',
           side_effect=stub_delete_namespaced_multus_network)
    @patch('sbi.kube_network.ConfigurationFile')
    def test_terminate_subnet(self, mock_config_file, mock_delete_namespaced_multus_network):
        mock_config_file.return_value.config = stub_configuration_file()
        response = self.client.open(
            '/v1/network_resources?networkResourceId=fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl',
            method='DELETE',
        )
        self.assertEqual(response.status_code, 200, "200")
        json_response = json.loads(response.data)
        self.assertEqual(json_response, ['fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl'],
                         "terminate_network returned wrong object")
