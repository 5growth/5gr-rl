# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.create_compute_resource_quota_request import CreateComputeResourceQuotaRequest  # noqa: E501
from mtp_plugin_kubernetes.models.create_network_resource_quota_request import CreateNetworkResourceQuotaRequest  # noqa: E501
from mtp_plugin_kubernetes.models.resource_group_ids import ResourceGroupIds  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_quota import VirtualComputeQuota  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network_quota import VirtualNetworkQuota  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestVirtualisedResourceQuotaController(BaseTestCase):
    """VirtualisedResourceQuotaController integration test stubs"""

    def test_create_compute_quota(self):
        """Test case for create_compute_quota

        
        """
        body = CreateComputeResourceQuotaRequest()
        response = self.client.open(
            '/quotas/compute_resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_create_network_quota(self):
        """Test case for create_network_quota

        
        """
        body = CreateNetworkResourceQuotaRequest()
        response = self.client.open(
            '/quotas/network_resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_compute_quota(self):
        """Test case for query_compute_quota

        
        """
        response = self.client.open(
            '/quotas/compute_resources',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_network_quota(self):
        """Test case for query_network_quota

        
        """
        response = self.client.open(
            '/quotas/network_resources',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_compute_quota(self):
        """Test case for terminate_compute_quota

        
        """
        query_string = [('resourceGroupId', 'resourceGroupId_example')]
        response = self.client.open(
            '/quotas/compute_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_network_quota(self):
        """Test case for terminate_network_quota

        
        """
        query_string = [('resourceGroupId', 'resourceGroupId_example')]
        response = self.client.open(
            '/quotas/network_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
