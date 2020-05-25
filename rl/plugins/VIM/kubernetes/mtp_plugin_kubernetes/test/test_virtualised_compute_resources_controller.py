# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.allocate_compute_request import AllocateComputeRequest  # noqa: E501
from mtp_plugin_kubernetes.models.capacity_information import CapacityInformation  # noqa: E501
from mtp_plugin_kubernetes.models.nfvi_pop import NfviPop  # noqa: E501
from mtp_plugin_kubernetes.models.resource_zone import ResourceZone  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_flavour import VirtualComputeFlavour  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_resource_information import VirtualComputeResourceInformation  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestVirtualisedComputeResourcesController(BaseTestCase):
    """VirtualisedComputeResourcesController integration test stubs"""

    def test_allocate_compute(self):
        """Test case for allocate_compute

        
        """
        body = AllocateComputeRequest()
        response = self.client.open(
            '/compute_resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_create_flavour(self):
        """Test case for create_flavour

        
        """
        Flavour = VirtualComputeFlavour()
        response = self.client.open(
            '/compute_resources/flavours',
            method='POST',
            data=json.dumps(Flavour),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_delete_flavours(self):
        """Test case for delete_flavours

        
        """
        response = self.client.open(
            '/compute_resources/flavours/{id}'.format(id='id_example'),
            method='DELETE',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_compute_capacity(self):
        """Test case for query_compute_capacity

        
        """
        query_string = [('ComputeResourceTypeId', 'ComputeResourceTypeId_example')]
        response = self.client.open(
            '/compute_resources/capacities',
            method='GET',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_compute_information(self):
        """Test case for query_compute_information

        
        """
        query_string = [('zoneId', 'zoneId_example')]
        response = self.client.open(
            '/compute_resources/information',
            method='GET',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_compute_resource_zone(self):
        """Test case for query_compute_resource_zone

        
        """
        response = self.client.open(
            '/compute_resources/resource_zones',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_flavors(self):
        """Test case for query_flavors

        
        """
        response = self.client.open(
            '/compute_resources/flavours',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_nfvi_po_p_compute_information(self):
        """Test case for query_nfvi_po_p_compute_information

        
        """
        response = self.client.open(
            '/compute_resources/nfvi_pop_compute_information',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_resources(self):
        """Test case for query_resources

        
        """
        response = self.client.open(
            '/compute_resources',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_abstract_resources(self):
        """Test case for terminate_abstract_resources

        
        """
        query_string = [('computeId', 'computeId_example')]
        response = self.client.open(
            '/compute_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
