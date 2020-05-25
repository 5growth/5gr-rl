# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.allocate_compute_request import AllocateComputeRequest  # noqa: E501
from mtp_plugin_kubernetes.models.delete_inter_nfvi_pop_connectivity_request import DeleteInterNfviPopConnectivityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response2001 import InlineResponse2001  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response201 import InlineResponse201  # noqa: E501
from mtp_plugin_kubernetes.models.inter_nfvi_pop_connectivity_request import InterNfviPopConnectivityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestAbstractResourcesController(BaseTestCase):
    """AbstractResourcesController integration test stubs"""

    def test_allocate_abstract_compute(self):
        """Test case for allocate_abstract_compute

        Allocate abstract compute resources
        """
        body = AllocateComputeRequest()
        response = self.client.open(
            '54000:/kubernetes/abstract-compute-resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_collect_mtp_cloud_network_resource_information(self):
        """Test case for collect_mtp_cloud_network_resource_information

        Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
        """
        response = self.client.open(
            '54000:/kubernetes/abstract-resources',
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_create_inter_nfvi_po_p_connectivity(self):
        """Test case for create_inter_nfvi_po_p_connectivity

        Create inter-NFVI-PoP connectivity
        """
        body = InterNfviPopConnectivityRequest()
        response = self.client.open(
            '54000:/kubernetes/abstract-network-resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_delete_inter_nfvi_po_p_connectivity(self):
        """Test case for delete_inter_nfvi_po_p_connectivity

        Delete inter-NFVI-PoP connectivity
        """
        body = DeleteInterNfviPopConnectivityRequest()
        response = self.client.open(
            '54000:/kubernetes/abstract-network-resources',
            method='DELETE',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_resources(self):
        """Test case for terminate_resources

        Terminate abstract compute resources
        """
        query_string = [('computeId', 'computeId_example')]
        response = self.client.open(
            '54000:/kubernetes/abstract-compute-resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
