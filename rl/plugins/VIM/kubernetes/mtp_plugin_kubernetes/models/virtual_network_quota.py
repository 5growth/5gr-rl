# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class VirtualNetworkQuota(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, num_ports: int=None, num_public_ips: int=None, num_subnets: int=None, resource_group_id: str=None):  # noqa: E501
        """VirtualNetworkQuota - a model defined in Swagger

        :param num_ports: The num_ports of this VirtualNetworkQuota.  # noqa: E501
        :type num_ports: int
        :param num_public_ips: The num_public_ips of this VirtualNetworkQuota.  # noqa: E501
        :type num_public_ips: int
        :param num_subnets: The num_subnets of this VirtualNetworkQuota.  # noqa: E501
        :type num_subnets: int
        :param resource_group_id: The resource_group_id of this VirtualNetworkQuota.  # noqa: E501
        :type resource_group_id: str
        """
        self.swagger_types = {
            'num_ports': int,
            'num_public_ips': int,
            'num_subnets': int,
            'resource_group_id': str
        }

        self.attribute_map = {
            'num_ports': 'numPorts',
            'num_public_ips': 'numPublicIps',
            'num_subnets': 'numSubnets',
            'resource_group_id': 'resourceGroupId'
        }

        self._num_ports = num_ports
        self._num_public_ips = num_public_ips
        self._num_subnets = num_subnets
        self._resource_group_id = resource_group_id

    @classmethod
    def from_dict(cls, dikt) -> 'VirtualNetworkQuota':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VirtualNetworkQuota of this VirtualNetworkQuota.  # noqa: E501
        :rtype: VirtualNetworkQuota
        """
        return util.deserialize_model(dikt, cls)

    @property
    def num_ports(self) -> int:
        """Gets the num_ports of this VirtualNetworkQuota.

        Number of ports that have been restricted by the quota. The cardinality can be 0 if no specific number of ports has been requested to be restricted by the quota.  # noqa: E501

        :return: The num_ports of this VirtualNetworkQuota.
        :rtype: int
        """
        return self._num_ports

    @num_ports.setter
    def num_ports(self, num_ports: int):
        """Sets the num_ports of this VirtualNetworkQuota.

        Number of ports that have been restricted by the quota. The cardinality can be 0 if no specific number of ports has been requested to be restricted by the quota.  # noqa: E501

        :param num_ports: The num_ports of this VirtualNetworkQuota.
        :type num_ports: int
        """
        if num_ports is None:
            raise ValueError("Invalid value for `num_ports`, must not be `None`")  # noqa: E501

        self._num_ports = num_ports

    @property
    def num_public_ips(self) -> int:
        """Gets the num_public_ips of this VirtualNetworkQuota.

        Number of public IP addresses that have been restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses has been requested to be restricted by the quota.  # noqa: E501

        :return: The num_public_ips of this VirtualNetworkQuota.
        :rtype: int
        """
        return self._num_public_ips

    @num_public_ips.setter
    def num_public_ips(self, num_public_ips: int):
        """Sets the num_public_ips of this VirtualNetworkQuota.

        Number of public IP addresses that have been restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses has been requested to be restricted by the quota.  # noqa: E501

        :param num_public_ips: The num_public_ips of this VirtualNetworkQuota.
        :type num_public_ips: int
        """
        if num_public_ips is None:
            raise ValueError("Invalid value for `num_public_ips`, must not be `None`")  # noqa: E501

        self._num_public_ips = num_public_ips

    @property
    def num_subnets(self) -> int:
        """Gets the num_subnets of this VirtualNetworkQuota.

        Number of subnets that have been restricted by the quota. The cardinality can be 0 if no specific number of subnets has been requested to be restricted by the quota.  # noqa: E501

        :return: The num_subnets of this VirtualNetworkQuota.
        :rtype: int
        """
        return self._num_subnets

    @num_subnets.setter
    def num_subnets(self, num_subnets: int):
        """Sets the num_subnets of this VirtualNetworkQuota.

        Number of subnets that have been restricted by the quota. The cardinality can be 0 if no specific number of subnets has been requested to be restricted by the quota.  # noqa: E501

        :param num_subnets: The num_subnets of this VirtualNetworkQuota.
        :type num_subnets: int
        """
        if num_subnets is None:
            raise ValueError("Invalid value for `num_subnets`, must not be `None`")  # noqa: E501

        self._num_subnets = num_subnets

    @property
    def resource_group_id(self) -> str:
        """Gets the resource_group_id of this VirtualNetworkQuota.

        Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.  # noqa: E501

        :return: The resource_group_id of this VirtualNetworkQuota.
        :rtype: str
        """
        return self._resource_group_id

    @resource_group_id.setter
    def resource_group_id(self, resource_group_id: str):
        """Sets the resource_group_id of this VirtualNetworkQuota.

        Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.  # noqa: E501

        :param resource_group_id: The resource_group_id of this VirtualNetworkQuota.
        :type resource_group_id: str
        """
        if resource_group_id is None:
            raise ValueError("Invalid value for `resource_group_id`, must not be `None`")  # noqa: E501

        self._resource_group_id = resource_group_id
