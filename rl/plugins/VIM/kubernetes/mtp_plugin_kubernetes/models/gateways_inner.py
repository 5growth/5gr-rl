# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.gateways_inner_gateway_attributes import GatewaysInnerGatewayAttributes


class GatewaysInner(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, gateway_attributes: GatewaysInnerGatewayAttributes=None):  # noqa: E501
        """GatewaysInner - a model defined in Swagger

        :param gateway_attributes: The gateway_attributes of this GatewaysInner.  # noqa: E501
        :type gateway_attributes: GatewaysInnerGatewayAttributes
        """
        self.swagger_types = {
            'gateway_attributes': GatewaysInnerGatewayAttributes
        }

        self.attribute_map = {
            'gateway_attributes': 'gatewayAttributes'
        }

        self._gateway_attributes = gateway_attributes

    @classmethod
    def from_dict(cls, dikt) -> 'GatewaysInner':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The gateways_inner of this GatewaysInner.  # noqa: E501
        :rtype: GatewaysInner
        """
        return util.deserialize_model(dikt, cls)

    @property
    def gateway_attributes(self) -> GatewaysInnerGatewayAttributes:
        """Gets the gateway_attributes of this GatewaysInner.


        :return: The gateway_attributes of this GatewaysInner.
        :rtype: GatewaysInnerGatewayAttributes
        """
        return self._gateway_attributes

    @gateway_attributes.setter
    def gateway_attributes(self, gateway_attributes: GatewaysInnerGatewayAttributes):
        """Sets the gateway_attributes of this GatewaysInner.


        :param gateway_attributes: The gateway_attributes of this GatewaysInner.
        :type gateway_attributes: GatewaysInnerGatewayAttributes
        """
        if gateway_attributes is None:
            raise ValueError("Invalid value for `gateway_attributes`, must not be `None`")  # noqa: E501

        self._gateway_attributes = gateway_attributes
