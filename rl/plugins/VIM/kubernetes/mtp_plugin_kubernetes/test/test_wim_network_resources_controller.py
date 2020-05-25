# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.allocate_parameters import AllocateParameters  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_reply import AllocateReply  # noqa: E501
from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response200 import InlineResponse200  # noqa: E501
from mtp_plugin_kubernetes.models.network_ids import NetworkIds  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network import VirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestWIMNetworkResourcesController(BaseTestCase):
    """WIMNetworkResourcesController integration test stubs"""

    def test_allocate_network(self):
        """Test case for allocate_network

        
        """
        params = AllocateParameters()
        response = self.client.open(
            '/network-resources',
            method='POST',
            data=json.dumps(params),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_collect_wim_abstracted_information(self):
        """Test case for collect_wim_abstracted_information

        Retrieve aggregated WAN Connectivity
        """
        response = self.client.open(
            '/abstract-network',
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_networks(self):
        """Test case for query_networks

        
        """
        networkQueryFilter = Filter()
        response = self.client.open(
            '/network-resources',
            method='GET',
            data=json.dumps(networkQueryFilter),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_networks(self):
        """Test case for terminate_networks

        
        """
        response = self.client.open(
            '/network-resources/{networkId}'.format(networkId='networkId_example'),
            method='DELETE',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
