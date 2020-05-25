# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from mtp_plugin_kubernetes.models.software_image_add_query import SoftwareImageAddQuery  # noqa: E501
from mtp_plugin_kubernetes.models.software_image_information import SoftwareImageInformation  # noqa: E501
from mtp_plugin_kubernetes.test import BaseTestCase


class TestSoftwareImagesController(BaseTestCase):
    """SoftwareImagesController integration test stubs"""

    def test_add_software_image(self):
        """Test case for add_software_image

        
        """
        body = SoftwareImageAddQuery()
        response = self.client.open(
            '/software_images',
            method='POST',
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_delete_software_image(self):
        """Test case for delete_software_image

        
        """
        response = self.client.open(
            '/software_images/{id}'.format(id='id_example'),
            method='DELETE',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_software_image(self):
        """Test case for query_software_image

        
        """
        response = self.client.open(
            '/software_images/{id}'.format(id='id_example'),
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_query_software_images(self):
        """Test case for query_software_images

        
        """
        response = self.client.open(
            '/software_images',
            method='GET',
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
