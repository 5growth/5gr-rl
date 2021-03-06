# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.query_compute_capacity_request_time_period import \
    QueryComputeCapacityRequestTimePeriod


class QueryNetworkCapacityRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, attribute_selector: str=None, network_resource_type_id: str=None, resource_criteria: str=None, time_period: QueryComputeCapacityRequestTimePeriod=None, zone_id: str=None):  # noqa: E501
        """QueryNetworkCapacityRequest - a model defined in Swagger

        :param attribute_selector: The attribute_selector of this QueryNetworkCapacityRequest.  # noqa: E501
        :type attribute_selector: str
        :param network_resource_type_id: The network_resource_type_id of this QueryNetworkCapacityRequest.  # noqa: E501
        :type network_resource_type_id: str
        :param resource_criteria: The resource_criteria of this QueryNetworkCapacityRequest.  # noqa: E501
        :type resource_criteria: str
        :param time_period: The time_period of this QueryNetworkCapacityRequest.  # noqa: E501
        :type time_period: QueryComputeCapacityRequestTimePeriod
        :param zone_id: The zone_id of this QueryNetworkCapacityRequest.  # noqa: E501
        :type zone_id: str
        """
        self.swagger_types = {
            'attribute_selector': str,
            'network_resource_type_id': str,
            'resource_criteria': str,
            'time_period': QueryComputeCapacityRequestTimePeriod,
            'zone_id': str
        }

        self.attribute_map = {
            'attribute_selector': 'attributeSelector',
            'network_resource_type_id': 'networkResourceTypeId',
            'resource_criteria': 'resourceCriteria',
            'time_period': 'timePeriod',
            'zone_id': 'zoneId'
        }

        self._attribute_selector = attribute_selector
        self._network_resource_type_id = network_resource_type_id
        self._resource_criteria = resource_criteria
        self._time_period = time_period
        self._zone_id = zone_id

    @classmethod
    def from_dict(cls, dikt) -> 'QueryNetworkCapacityRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The QueryNetworkCapacityRequest of this QueryNetworkCapacityRequest.  # noqa: E501
        :rtype: QueryNetworkCapacityRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def attribute_selector(self) -> str:
        """Gets the attribute_selector of this QueryNetworkCapacityRequest.

        Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.  # noqa: E501

        :return: The attribute_selector of this QueryNetworkCapacityRequest.
        :rtype: str
        """
        return self._attribute_selector

    @attribute_selector.setter
    def attribute_selector(self, attribute_selector: str):
        """Sets the attribute_selector of this QueryNetworkCapacityRequest.

        Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.  # noqa: E501

        :param attribute_selector: The attribute_selector of this QueryNetworkCapacityRequest.
        :type attribute_selector: str
        """
        if attribute_selector is None:
            raise ValueError("Invalid value for `attribute_selector`, must not be `None`")  # noqa: E501

        self._attribute_selector = attribute_selector

    @property
    def network_resource_type_id(self) -> str:
        """Gets the network_resource_type_id of this QueryNetworkCapacityRequest.

        Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.  # noqa: E501

        :return: The network_resource_type_id of this QueryNetworkCapacityRequest.
        :rtype: str
        """
        return self._network_resource_type_id

    @network_resource_type_id.setter
    def network_resource_type_id(self, network_resource_type_id: str):
        """Sets the network_resource_type_id of this QueryNetworkCapacityRequest.

        Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.  # noqa: E501

        :param network_resource_type_id: The network_resource_type_id of this QueryNetworkCapacityRequest.
        :type network_resource_type_id: str
        """
        if network_resource_type_id is None:
            raise ValueError("Invalid value for `network_resource_type_id`, must not be `None`")  # noqa: E501

        self._network_resource_type_id = network_resource_type_id

    @property
    def resource_criteria(self) -> str:
        """Gets the resource_criteria of this QueryNetworkCapacityRequest.

        Input capacity computation parameter for selecting the characteristics of the virtual network for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualNetworkResourceInformation information element. This information element and the networkResourceTypeID are mutually exclusive.  # noqa: E501

        :return: The resource_criteria of this QueryNetworkCapacityRequest.
        :rtype: str
        """
        return self._resource_criteria

    @resource_criteria.setter
    def resource_criteria(self, resource_criteria: str):
        """Sets the resource_criteria of this QueryNetworkCapacityRequest.

        Input capacity computation parameter for selecting the characteristics of the virtual network for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualNetworkResourceInformation information element. This information element and the networkResourceTypeID are mutually exclusive.  # noqa: E501

        :param resource_criteria: The resource_criteria of this QueryNetworkCapacityRequest.
        :type resource_criteria: str
        """
        if resource_criteria is None:
            raise ValueError("Invalid value for `resource_criteria`, must not be `None`")  # noqa: E501

        self._resource_criteria = resource_criteria

    @property
    def time_period(self) -> QueryComputeCapacityRequestTimePeriod:
        """Gets the time_period of this QueryNetworkCapacityRequest.


        :return: The time_period of this QueryNetworkCapacityRequest.
        :rtype: QueryComputeCapacityRequestTimePeriod
        """
        return self._time_period

    @time_period.setter
    def time_period(self, time_period: QueryComputeCapacityRequestTimePeriod):
        """Sets the time_period of this QueryNetworkCapacityRequest.


        :param time_period: The time_period of this QueryNetworkCapacityRequest.
        :type time_period: QueryComputeCapacityRequestTimePeriod
        """
        if time_period is None:
            raise ValueError("Invalid value for `time_period`, must not be `None`")  # noqa: E501

        self._time_period = time_period

    @property
    def zone_id(self) -> str:
        """Gets the zone_id of this QueryNetworkCapacityRequest.

        When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.  # noqa: E501

        :return: The zone_id of this QueryNetworkCapacityRequest.
        :rtype: str
        """
        return self._zone_id

    @zone_id.setter
    def zone_id(self, zone_id: str):
        """Sets the zone_id of this QueryNetworkCapacityRequest.

        When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.  # noqa: E501

        :param zone_id: The zone_id of this QueryNetworkCapacityRequest.
        :type zone_id: str
        """
        if zone_id is None:
            raise ValueError("Invalid value for `zone_id`, must not be `None`")  # noqa: E501

        self._zone_id = zone_id
