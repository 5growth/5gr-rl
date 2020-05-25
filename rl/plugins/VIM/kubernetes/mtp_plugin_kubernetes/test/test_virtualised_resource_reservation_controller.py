# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.create_compute_resource_reservation_request import CreateComputeResourceReservationRequest  # noqa: E501
from mtp_plugin_kubernetes.models.create_network_resource_reservation_request import CreateNetworkResourceReservationRequest  # noqa: E501
from mtp_plugin_kubernetes.models.reserved_virtual_compute import ReservedVirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.models.reserved_virtual_network import ReservedVirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestVirtualisedResourceReservationController(BaseTestCase):
    """VirtualisedResourceReservationController integration test stubs"""

    def test_create_compute_reservation(self):
        """Test case for create_compute_reservation

        
        """
        body = CreateComputeResourceReservationRequest()
        response = self.client.open(
            '/reservations/compute_resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_create_network_reservation(self):
        """Test case for create_network_reservation

        
        """
        body = CreateNetworkResourceReservationRequest()
        response = self.client.open(
            '/reservations/network_resources',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_compute_reservation(self):
        """Test case for query_compute_reservation

        
        """
        response = self.client.open(
            '/reservations/compute_resources',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_network_reservation(self):
        """Test case for query_network_reservation

        
        """
        response = self.client.open(
            '/reservations/network_resources',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_compute_reservation(self):
        """Test case for terminate_compute_reservation

        
        """
        query_string = [('reservationId', 'reservationId_example')]
        response = self.client.open(
            '/reservations/compute_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_terminate_network_reservation(self):
        """Test case for terminate_network_reservation

        
        """
        query_string = [('reservationId', 'reservationId_example')]
        response = self.client.open(
            '/reservations/network_resources',
            method='DELETE',
            content_type='application/json',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
