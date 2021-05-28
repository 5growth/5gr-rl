# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.inter_nfvi_pop_connnectivity_id import InterNfviPopConnnectivityId
from mtp_plugin_kubernetes.models.inter_nfvi_pop_network_segment_type import InterNfviPopNetworkSegmentType


class InlineResponse201(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, inter_nfvi_pop_connnectivity_id: InterNfviPopConnnectivityId=None, inter_nfvi_pop_network_segment_type: InterNfviPopNetworkSegmentType=None):  # noqa: E501
        """InlineResponse201 - a model defined in Swagger

        :param inter_nfvi_pop_connnectivity_id: The inter_nfvi_pop_connnectivity_id of this InlineResponse201.  # noqa: E501
        :type inter_nfvi_pop_connnectivity_id: InterNfviPopConnnectivityId
        :param inter_nfvi_pop_network_segment_type: The inter_nfvi_pop_network_segment_type of this InlineResponse201.  # noqa: E501
        :type inter_nfvi_pop_network_segment_type: InterNfviPopNetworkSegmentType
        """
        self.swagger_types = {
            'inter_nfvi_pop_connnectivity_id': InterNfviPopConnnectivityId,
            'inter_nfvi_pop_network_segment_type': InterNfviPopNetworkSegmentType
        }

        self.attribute_map = {
            'inter_nfvi_pop_connnectivity_id': 'interNfviPopConnnectivityId',
            'inter_nfvi_pop_network_segment_type': 'interNfviPopNetworkSegmentType'
        }

        self._inter_nfvi_pop_connnectivity_id = inter_nfvi_pop_connnectivity_id
        self._inter_nfvi_pop_network_segment_type = inter_nfvi_pop_network_segment_type

    @classmethod
    def from_dict(cls, dikt) -> 'InlineResponse201':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The inline_response_201 of this InlineResponse201.  # noqa: E501
        :rtype: InlineResponse201
        """
        return util.deserialize_model(dikt, cls)

    @property
    def inter_nfvi_pop_connnectivity_id(self) -> InterNfviPopConnnectivityId:
        """Gets the inter_nfvi_pop_connnectivity_id of this InlineResponse201.


        :return: The inter_nfvi_pop_connnectivity_id of this InlineResponse201.
        :rtype: InterNfviPopConnnectivityId
        """
        return self._inter_nfvi_pop_connnectivity_id

    @inter_nfvi_pop_connnectivity_id.setter
    def inter_nfvi_pop_connnectivity_id(self, inter_nfvi_pop_connnectivity_id: InterNfviPopConnnectivityId):
        """Sets the inter_nfvi_pop_connnectivity_id of this InlineResponse201.


        :param inter_nfvi_pop_connnectivity_id: The inter_nfvi_pop_connnectivity_id of this InlineResponse201.
        :type inter_nfvi_pop_connnectivity_id: InterNfviPopConnnectivityId
        """
        if inter_nfvi_pop_connnectivity_id is None:
            raise ValueError("Invalid value for `inter_nfvi_pop_connnectivity_id`, must not be `None`")  # noqa: E501

        self._inter_nfvi_pop_connnectivity_id = inter_nfvi_pop_connnectivity_id

    @property
    def inter_nfvi_pop_network_segment_type(self) -> InterNfviPopNetworkSegmentType:
        """Gets the inter_nfvi_pop_network_segment_type of this InlineResponse201.


        :return: The inter_nfvi_pop_network_segment_type of this InlineResponse201.
        :rtype: InterNfviPopNetworkSegmentType
        """
        return self._inter_nfvi_pop_network_segment_type

    @inter_nfvi_pop_network_segment_type.setter
    def inter_nfvi_pop_network_segment_type(self, inter_nfvi_pop_network_segment_type: InterNfviPopNetworkSegmentType):
        """Sets the inter_nfvi_pop_network_segment_type of this InlineResponse201.


        :param inter_nfvi_pop_network_segment_type: The inter_nfvi_pop_network_segment_type of this InlineResponse201.
        :type inter_nfvi_pop_network_segment_type: InterNfviPopNetworkSegmentType
        """
        if inter_nfvi_pop_network_segment_type is None:
            raise ValueError("Invalid value for `inter_nfvi_pop_network_segment_type`, must not be `None`")  # noqa: E501

        self._inter_nfvi_pop_network_segment_type = inter_nfvi_pop_network_segment_type
