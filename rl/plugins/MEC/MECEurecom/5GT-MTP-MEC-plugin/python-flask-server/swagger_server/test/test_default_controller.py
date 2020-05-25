# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from swagger_server.models.inline_response200 import InlineResponse200  # noqa: E501
from swagger_server.models.mec_traffic_service_creation_request import MECTrafficServiceCreationRequest  # noqa: E501
from swagger_server.models.mec_traffic_service_creation_response import MECTrafficServiceCreationResponse  # noqa: E501
from swagger_server.test import BaseTestCase


class TestDefaultController(BaseTestCase):
    """DefaultController integration test stubs"""

    def test_service_regions_get(self):
        """Test case for service_regions_get

        Retrieve a list of all covered regions.
        """
        response = self.client.open(
            '/MEC/v1/service/regions',
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_service_regions_region_id_get(self):
        """Test case for service_regions_region_id_get

        Retrieve a list of MEC service requests for the given region.
        """
        response = self.client.open(
            '/MEC/v1/service/regions/{RegionId}'.format(RegionId='RegionId_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_service_requests_get(self):
        """Test case for service_requests_get

        Retrieve a list of MEC service requests.
        """
        response = self.client.open(
            '/MEC/v1/service/requests',
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_service_requests_post(self):
        """Test case for service_requests_post

        Create MEC service rules.
        """
        mecTrafficServiceCreationRequest = MECTrafficServiceCreationRequest()
        response = self.client.open(
            '/MEC/v1/service/requests',
            method='POST',
            data=json.dumps(mecTrafficServiceCreationRequest),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_service_requests_request_id_delete(self):
        """Test case for service_requests_request_id_delete

        Delete service.
        """
        response = self.client.open(
            '/MEC/v1/service/requests/{RequestId}'.format(RequestId='RequestId_example'),
            method='DELETE')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_service_requests_request_id_get(self):
        """Test case for service_requests_request_id_get

        Retrieve information about a MEC service request.
        """
        response = self.client.open(
            '/MEC/v1/service/requests/{RequestId}'.format(RequestId='RequestId_example'),
            method='GET')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
