# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.allocate_network_request import AllocateNetworkRequest  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_network_result import AllocateNetworkResult  # noqa: E501
from mtp_plugin_kubernetes.models.capacity_information import CapacityInformation  # noqa: E501
from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.nfvi_pop import NfviPop  # noqa: E501
from mtp_plugin_kubernetes.models.query_network_capacity_request import QueryNetworkCapacityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network import VirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestVirtualisedNetworkResourcesController(BaseTestCase):
    """VirtualisedNetworkResourcesController integration test stubs"""

    def test_query_network_capacity(self):
        """Test case for query_network_capacity

        
        """
        QueryNetworkCapacityRequest = QueryNetworkCapacityRequest()
        response = self.client.open(
            '/network_resources/capacities',
            method='GET',
            data=json.dumps(QueryNetworkCapacityRequest),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_nfvi_po_p_network_information(self):
        """Test case for query_nfvi_po_p_network_information

        
        """
        NfviPopNetworkInformationRequest = Filter()
        response = self.client.open(
            '/network_resources/nfvi-pop-network-information',
            method='GET',
            data=json.dumps(NfviPopNetworkInformationRequest),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_vi_mallocate_network(self):
        """Test case for vi_mallocate_network

        
        """
        params = AllocateNetworkRequest()
        response = self.client.open(
            '/network_resources',
            method='POST',
            data=json.dumps(params),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_vi_mquery_networks(self):
        """Test case for vi_mquery_networks

        
        """
        networkQueryFilter = Filter()
        response = self.client.open(
            '/network_resources',
            method='GET',
            data=json.dumps(networkQueryFilter),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_vi_mterminate_networks(self):
        """Test case for vi_mterminate_networks

        
        """
        query_string = [('networkResourceId', 'networkResourceId_example')]
        response = self.client.open(
            '/network_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
