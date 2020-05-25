# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from swagger_server.models.base_model_ import Model
from swagger_server.models.inter_nfvi_pop_network_type import InterNfviPopNetworkType  # noqa: F401,E501
from swagger_server.models.logical_link_path_list import LogicalLinkPathList  # noqa: F401,E501
from swagger_server.models.meta_data import MetaData  # noqa: F401,E501
from swagger_server.models.network_layer import NetworkLayer  # noqa: F401,E501
from swagger_server import util


class InterNfviPopConnectivityRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, logical_link_path_list: LogicalLinkPathList=None, network_layer: NetworkLayer=None, inter_nfvi_pop_network_type: InterNfviPopNetworkType=None, meta_data: MetaData=None):  # noqa: E501
        """InterNfviPopConnectivityRequest - a model defined in Swagger

        :param logical_link_path_list: The logical_link_path_list of this InterNfviPopConnectivityRequest.  # noqa: E501
        :type logical_link_path_list: LogicalLinkPathList
        :param network_layer: The network_layer of this InterNfviPopConnectivityRequest.  # noqa: E501
        :type network_layer: NetworkLayer
        :param inter_nfvi_pop_network_type: The inter_nfvi_pop_network_type of this InterNfviPopConnectivityRequest.  # noqa: E501
        :type inter_nfvi_pop_network_type: InterNfviPopNetworkType
        :param meta_data: The meta_data of this InterNfviPopConnectivityRequest.  # noqa: E501
        :type meta_data: MetaData
        """
        self.swagger_types = {
            'logical_link_path_list': LogicalLinkPathList,
            'network_layer': NetworkLayer,
            'inter_nfvi_pop_network_type': InterNfviPopNetworkType,
            'meta_data': MetaData
        }

        self.attribute_map = {
            'logical_link_path_list': 'logicalLinkPathList',
            'network_layer': 'networkLayer',
            'inter_nfvi_pop_network_type': 'interNfviPopNetworkType',
            'meta_data': 'metaData'
        }

        self._logical_link_path_list = logical_link_path_list
        self._network_layer = network_layer
        self._inter_nfvi_pop_network_type = inter_nfvi_pop_network_type
        self._meta_data = meta_data

    @classmethod
    def from_dict(cls, dikt) -> 'InterNfviPopConnectivityRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The interNfviPopConnectivityRequest of this InterNfviPopConnectivityRequest.  # noqa: E501
        :rtype: InterNfviPopConnectivityRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def logical_link_path_list(self) -> LogicalLinkPathList:
        """Gets the logical_link_path_list of this InterNfviPopConnectivityRequest.


        :return: The logical_link_path_list of this InterNfviPopConnectivityRequest.
        :rtype: LogicalLinkPathList
        """
        return self._logical_link_path_list

    @logical_link_path_list.setter
    def logical_link_path_list(self, logical_link_path_list: LogicalLinkPathList):
        """Sets the logical_link_path_list of this InterNfviPopConnectivityRequest.


        :param logical_link_path_list: The logical_link_path_list of this InterNfviPopConnectivityRequest.
        :type logical_link_path_list: LogicalLinkPathList
        """
        if logical_link_path_list is None:
            raise ValueError("Invalid value for `logical_link_path_list`, must not be `None`")  # noqa: E501

        self._logical_link_path_list = logical_link_path_list

    @property
    def network_layer(self) -> NetworkLayer:
        """Gets the network_layer of this InterNfviPopConnectivityRequest.


        :return: The network_layer of this InterNfviPopConnectivityRequest.
        :rtype: NetworkLayer
        """
        return self._network_layer

    @network_layer.setter
    def network_layer(self, network_layer: NetworkLayer):
        """Sets the network_layer of this InterNfviPopConnectivityRequest.


        :param network_layer: The network_layer of this InterNfviPopConnectivityRequest.
        :type network_layer: NetworkLayer
        """
        if network_layer is None:
            raise ValueError("Invalid value for `network_layer`, must not be `None`")  # noqa: E501

        self._network_layer = network_layer

    @property
    def inter_nfvi_pop_network_type(self) -> InterNfviPopNetworkType:
        """Gets the inter_nfvi_pop_network_type of this InterNfviPopConnectivityRequest.


        :return: The inter_nfvi_pop_network_type of this InterNfviPopConnectivityRequest.
        :rtype: InterNfviPopNetworkType
        """
        return self._inter_nfvi_pop_network_type

    @inter_nfvi_pop_network_type.setter
    def inter_nfvi_pop_network_type(self, inter_nfvi_pop_network_type: InterNfviPopNetworkType):
        """Sets the inter_nfvi_pop_network_type of this InterNfviPopConnectivityRequest.


        :param inter_nfvi_pop_network_type: The inter_nfvi_pop_network_type of this InterNfviPopConnectivityRequest.
        :type inter_nfvi_pop_network_type: InterNfviPopNetworkType
        """
        if inter_nfvi_pop_network_type is None:
            raise ValueError("Invalid value for `inter_nfvi_pop_network_type`, must not be `None`")  # noqa: E501

        self._inter_nfvi_pop_network_type = inter_nfvi_pop_network_type

    @property
    def meta_data(self) -> MetaData:
        """Gets the meta_data of this InterNfviPopConnectivityRequest.


        :return: The meta_data of this InterNfviPopConnectivityRequest.
        :rtype: MetaData
        """
        return self._meta_data

    @meta_data.setter
    def meta_data(self, meta_data: MetaData):
        """Sets the meta_data of this InterNfviPopConnectivityRequest.


        :param meta_data: The meta_data of this InterNfviPopConnectivityRequest.
        :type meta_data: MetaData
        """
        if meta_data is None:
            raise ValueError("Invalid value for `meta_data`, must not be `None`")  # noqa: E501

        self._meta_data = meta_data