# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class VirtualCpuPinningData(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, cpu_pinning_policy: str=None, cpu_pinning_map: object=None):  # noqa: E501
        """VirtualCpuPinningData - a model defined in Swagger

        :param cpu_pinning_policy: The cpu_pinning_policy of this VirtualCpuPinningData.  # noqa: E501
        :type cpu_pinning_policy: str
        :param cpu_pinning_map: The cpu_pinning_map of this VirtualCpuPinningData.  # noqa: E501
        :type cpu_pinning_map: object
        """
        self.swagger_types = {
            'cpu_pinning_policy': str,
            'cpu_pinning_map': object
        }

        self.attribute_map = {
            'cpu_pinning_policy': 'cpuPinningPolicy',
            'cpu_pinning_map': 'cpuPinningMap'
        }

        self._cpu_pinning_policy = cpu_pinning_policy
        self._cpu_pinning_map = cpu_pinning_map

    @classmethod
    def from_dict(cls, dikt) -> 'VirtualCpuPinningData':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VirtualCpuPinningData of this VirtualCpuPinningData.  # noqa: E501
        :rtype: VirtualCpuPinningData
        """
        return util.deserialize_model(dikt, cls)

    @property
    def cpu_pinning_policy(self) -> str:
        """Gets the cpu_pinning_policy of this VirtualCpuPinningData.

        ndicates the policy for CPU pinning. The policy can take values of \"static\" or \"dynamic\". The cardinality can be 0 during the allocation request, if no particular value is requested.  # noqa: E501

        :return: The cpu_pinning_policy of this VirtualCpuPinningData.
        :rtype: str
        """
        return self._cpu_pinning_policy

    @cpu_pinning_policy.setter
    def cpu_pinning_policy(self, cpu_pinning_policy: str):
        """Sets the cpu_pinning_policy of this VirtualCpuPinningData.

        ndicates the policy for CPU pinning. The policy can take values of \"static\" or \"dynamic\". The cardinality can be 0 during the allocation request, if no particular value is requested.  # noqa: E501

        :param cpu_pinning_policy: The cpu_pinning_policy of this VirtualCpuPinningData.
        :type cpu_pinning_policy: str
        """
        allowed_values = ["static", "dynamic"]  # noqa: E501
        if cpu_pinning_policy not in allowed_values:
            raise ValueError(
                "Invalid value for `cpu_pinning_policy` ({0}), must be one of {1}"
                .format(cpu_pinning_policy, allowed_values)
            )

        self._cpu_pinning_policy = cpu_pinning_policy

    @property
    def cpu_pinning_map(self) -> object:
        """Gets the cpu_pinning_map of this VirtualCpuPinningData.

        If cpuPinningPolicy is defined as \"static\", the cpuPinningMap provides the map of pinning virtual CPU cores to physical CPU cores/threads. Cardinality is 0 if cpuPinningPolicy has a different value than \"static\".  # noqa: E501

        :return: The cpu_pinning_map of this VirtualCpuPinningData.
        :rtype: object
        """
        return self._cpu_pinning_map

    @cpu_pinning_map.setter
    def cpu_pinning_map(self, cpu_pinning_map: object):
        """Sets the cpu_pinning_map of this VirtualCpuPinningData.

        If cpuPinningPolicy is defined as \"static\", the cpuPinningMap provides the map of pinning virtual CPU cores to physical CPU cores/threads. Cardinality is 0 if cpuPinningPolicy has a different value than \"static\".  # noqa: E501

        :param cpu_pinning_map: The cpu_pinning_map of this VirtualCpuPinningData.
        :type cpu_pinning_map: object
        """

        self._cpu_pinning_map = cpu_pinning_map
