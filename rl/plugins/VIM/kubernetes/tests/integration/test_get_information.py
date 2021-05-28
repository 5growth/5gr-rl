# coding: utf-8

from __future__ import absolute_import

import os
import unittest
from unittest.mock import patch

import kubernetes
from flask import json
from urllib3 import HTTPResponse

from tests.integration.base_test_case import BaseTestCase
resource_root = os.path.dirname(__file__)
os.chdir(resource_root + "/../../")


def stub_list_namespaced_multus_network():
    with open(resource_root + '/stub_results/stub_list_namespaced_multus_network.txt', 'rb') as infile:
        body = infile.read()
    return_http_response = HTTPResponse(body=body, status=200)
    return return_http_response


def stub_configuration_file():
    with open(resource_root + '/stub_results/stub_configuration_file.json', 'r') as infile:
        json_object = json.load(infile)
    return json_object


def stub_create_namespaced_multus_network():
    raise kubernetes.client.exceptions.ApiException(reason='Conflict')


def stub_get_namespaced_multus_network():
    with open(resource_root + '/stub_results/stub_get_namespaced_multus_network.txt', 'rb') as infile:
        body = infile.read()
    return_http_response = HTTPResponse(body=body, status=200)
    return return_http_response


def stub_delete_namespaced_multus_network():
    return_http_response = HTTPResponse(body="body", status=200)
    return return_http_response


class TestGetInformationController(BaseTestCase):

    @patch('mtp_plugin_kubernetes.controllers.vim_compute_resources_controller.KubeGetInfo')
    @patch('sbi.kube_network.ConfigurationFile')
    def test_get_compute_resources_information(self, mock_config_file, mock_kube_get_info):
        mock_config_file.return_value.config = stub_configuration_file()
        mock_kube_get_info.return_value.allocatable_memory = '1743776'
        mock_kube_get_info.return_value.allocatable_processor = '1.15'
        mock_kube_get_info.return_value.allocated_memory = '194561'
        mock_kube_get_info.return_value.architecture = 'amd64'
        mock_kube_get_info.return_value.configuration = None
        mock_kube_get_info.return_value.total_memory = '1938336'
        mock_kube_get_info.return_value.total_processor = '2.0'

        response = self.client.open(
            '/v1/compute_resources/information?zoneId=zoneid%3Dnova1',
            method='GET')
        json_response = json.loads(response.data)
        with open(resource_root + '/stub_results/get_compute_resources_information_reference_response.json', 'r') as infile:
            reference_json_response = json.load(infile)
        self.assert200(response,
                       "Request get /v1/compute_resources/information?zoneId=zoneid%3Dnova1 returned wrong status")
        self.assertEqual(reference_json_response, json_response,
                         "Request get /v1/compute_resources/information?zoneId=zoneid%3Dnova1 returned wrong respponse")

    @patch('sbi.kube_network.ConfigurationFile')
    def test_get_nfvi_pop_compute_information(self, mock_config_file):
        mock_config_file.return_value.config = stub_configuration_file()

        response = self.client.open(
            '/v1/compute_resources/nfvi_pop_compute_information',
            method='GET')
        json_response = json.loads(response.data)
        with open(resource_root + '/stub_results/get_nfvi_pop_compute_information_reference_response.json', 'r') as infile:
            reference_json_response = json.load(infile)
        self.assert200(response, "Request get /v1/compute_resources/nfvi_pop_compute_information returned wrong status")
        self.assertEqual(reference_json_response, json_response,
                         "Request get /v1/compute_resources/nfvi_pop_compute_information returned wrong respponse")

    @patch('mtp_plugin_kubernetes.controllers.vim_compute_resources_controller.KubeGetInfo')
    @patch('sbi.kube_network.ConfigurationFile')
    def test_get_capacities_compute_resource_type_id_0(self, mock_config_file, mock_kube_get_info):
        mock_config_file.return_value.config = stub_configuration_file()
        mock_kube_get_info.return_value.allocatable_memory = '1743776'
        mock_kube_get_info.return_value.allocatable_processor = '1.15'
        mock_kube_get_info.return_value.allocated_memory = '194561'
        mock_kube_get_info.return_value.architecture = 'amd64'
        mock_kube_get_info.return_value.configuration = None
        mock_kube_get_info.return_value.total_memory = '1938336'
        mock_kube_get_info.return_value.total_processor = '2.0'

        response = self.client.open(
            '/v1/compute_resources/capacities?ComputeResourceTypeId=0',
            method='GET')
        json_response = json.loads(response.data)
        with open(resource_root + '/stub_results/get_capacities_compute_type_id_0_reference_resource.json', 'r') as infile:
            reference_json_response = json.load(infile)
        self.assert200(response,
                       "Request get /v1/compute_resources/capacities?ComputeResourceTypeId=0 returned wrong status")
        self.assertEqual(reference_json_response, json_response,
                         "Request get /v1/compute_resources/capacities?ComputeResourceTypeId=0 "
                         "returned wrong respponse")

    @patch('mtp_plugin_kubernetes.controllers.vim_compute_resources_controller.KubeGetInfo')
    @patch('sbi.kube_network.ConfigurationFile')
    def test_get_capacities_compute_resource_type_id_1(self, mock_config_file, mock_kube_get_info):
        mock_config_file.return_value.config = stub_configuration_file()
        mock_kube_get_info.return_value.allocatable_memory = '1743776'
        mock_kube_get_info.return_value.allocatable_processor = '1.15'
        mock_kube_get_info.return_value.allocated_memory = '194561'
        mock_kube_get_info.return_value.architecture = 'amd64'
        mock_kube_get_info.return_value.configuration = None
        mock_kube_get_info.return_value.total_memory = '1938336'
        mock_kube_get_info.return_value.total_processor = '2.0'
        mock_kube_get_info.return_value.allocated_processor = '0.85'
        mock_kube_get_info.return_value.allocatable_processor = '1.15'

        response = self.client.open(
            '/v1/compute_resources/capacities?ComputeResourceTypeId=1',
            method='GET')
        json_response = json.loads(response.data)
        with open(resource_root + '/stub_results/get_capacities_compute_type_id_1_reference_response.json', 'r') as infile:
            reference_json_response = json.load(infile)
        self.assert200(response,
                       "Request get /v1/compute_resources/capacities?ComputeResourceTypeId=1 returned wrong status")
        self.assertEqual(reference_json_response, json_response,
                         "Request get /v1/compute_resources/capacities?ComputeResourceTypeId=1 "
                         "returned wrong respponse")
