# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network_resource_information import VirtualNetworkResourceInformation  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestVirtualisedNetworkResourcesInformationController(BaseTestCase):
    """VirtualisedNetworkResourcesInformationController integration test stubs"""

    def test_query_network_information(self):
        """Test case for query_network_information

        
        """
        informationQueryFilter = Filter()
        response = self.client.open(
            '/information',
            method='GET',
            data=json.dumps(informationQueryFilter),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
