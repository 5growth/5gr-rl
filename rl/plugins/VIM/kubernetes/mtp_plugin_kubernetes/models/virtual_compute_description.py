# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.request_additional_capability_data import RequestAdditionalCapabilityData
from mtp_plugin_kubernetes.models.virtual_cpu_data import VirtualCpuData
from mtp_plugin_kubernetes.models.virtual_memory_data import VirtualMemoryData


class VirtualComputeDescription(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, virtual_compute_desc_id: str=None, request_additional_capabilities: List[RequestAdditionalCapabilityData]=None, virtual_memory: VirtualMemoryData=None, virtual_cpu: VirtualCpuData=None):  # noqa: E501
        """VirtualComputeDescription - a model defined in Swagger

        :param virtual_compute_desc_id: The virtual_compute_desc_id of this VirtualComputeDescription.  # noqa: E501
        :type virtual_compute_desc_id: str
        :param request_additional_capabilities: The request_additional_capabilities of this VirtualComputeDescription.  # noqa: E501
        :type request_additional_capabilities: List[RequestAdditionalCapabilityData]
        :param virtual_memory: The virtual_memory of this VirtualComputeDescription.  # noqa: E501
        :type virtual_memory: VirtualMemoryData
        :param virtual_cpu: The virtual_cpu of this VirtualComputeDescription.  # noqa: E501
        :type virtual_cpu: VirtualCpuData
        """
        self.swagger_types = {
            'virtual_compute_desc_id': str,
            'request_additional_capabilities': List[RequestAdditionalCapabilityData],
            'virtual_memory': VirtualMemoryData,
            'virtual_cpu': VirtualCpuData
        }

        self.attribute_map = {
            'virtual_compute_desc_id': 'virtualComputeDescId',
            'request_additional_capabilities': 'requestAdditionalCapabilities',
            'virtual_memory': 'virtualMemory',
            'virtual_cpu': 'virtualCpu'
        }

        self._virtual_compute_desc_id = virtual_compute_desc_id
        self._request_additional_capabilities = request_additional_capabilities
        self._virtual_memory = virtual_memory
        self._virtual_cpu = virtual_cpu

    @classmethod
    def from_dict(cls, dikt) -> 'VirtualComputeDescription':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VirtualComputeDescription of this VirtualComputeDescription.  # noqa: E501
        :rtype: VirtualComputeDescription
        """
        return util.deserialize_model(dikt, cls)

    @property
    def virtual_compute_desc_id(self) -> str:
        """Gets the virtual_compute_desc_id of this VirtualComputeDescription.

        Unique identifier of this VirtualComputeDescription in the appD.  # noqa: E501

        :return: The virtual_compute_desc_id of this VirtualComputeDescription.
        :rtype: str
        """
        return self._virtual_compute_desc_id

    @virtual_compute_desc_id.setter
    def virtual_compute_desc_id(self, virtual_compute_desc_id: str):
        """Sets the virtual_compute_desc_id of this VirtualComputeDescription.

        Unique identifier of this VirtualComputeDescription in the appD.  # noqa: E501

        :param virtual_compute_desc_id: The virtual_compute_desc_id of this VirtualComputeDescription.
        :type virtual_compute_desc_id: str
        """
        if virtual_compute_desc_id is None:
            raise ValueError("Invalid value for `virtual_compute_desc_id`, must not be `None`")  # noqa: E501

        self._virtual_compute_desc_id = virtual_compute_desc_id

    @property
    def request_additional_capabilities(self) -> List[RequestAdditionalCapabilityData]:
        """Gets the request_additional_capabilities of this VirtualComputeDescription.

        Specifies requirements for additional  capabilities. These may be for a range of purposes. One example is acceleration related capabilities.  # noqa: E501

        :return: The request_additional_capabilities of this VirtualComputeDescription.
        :rtype: List[RequestAdditionalCapabilityData]
        """
        return self._request_additional_capabilities

    @request_additional_capabilities.setter
    def request_additional_capabilities(self, request_additional_capabilities: List[RequestAdditionalCapabilityData]):
        """Sets the request_additional_capabilities of this VirtualComputeDescription.

        Specifies requirements for additional  capabilities. These may be for a range of purposes. One example is acceleration related capabilities.  # noqa: E501

        :param request_additional_capabilities: The request_additional_capabilities of this VirtualComputeDescription.
        :type request_additional_capabilities: List[RequestAdditionalCapabilityData]
        """

        self._request_additional_capabilities = request_additional_capabilities

    @property
    def virtual_memory(self) -> VirtualMemoryData:
        """Gets the virtual_memory of this VirtualComputeDescription.


        :return: The virtual_memory of this VirtualComputeDescription.
        :rtype: VirtualMemoryData
        """
        return self._virtual_memory

    @virtual_memory.setter
    def virtual_memory(self, virtual_memory: VirtualMemoryData):
        """Sets the virtual_memory of this VirtualComputeDescription.


        :param virtual_memory: The virtual_memory of this VirtualComputeDescription.
        :type virtual_memory: VirtualMemoryData
        """
        if virtual_memory is None:
            raise ValueError("Invalid value for `virtual_memory`, must not be `None`")  # noqa: E501

        self._virtual_memory = virtual_memory

    @property
    def virtual_cpu(self) -> VirtualCpuData:
        """Gets the virtual_cpu of this VirtualComputeDescription.


        :return: The virtual_cpu of this VirtualComputeDescription.
        :rtype: VirtualCpuData
        """
        return self._virtual_cpu

    @virtual_cpu.setter
    def virtual_cpu(self, virtual_cpu: VirtualCpuData):
        """Sets the virtual_cpu of this VirtualComputeDescription.


        :param virtual_cpu: The virtual_cpu of this VirtualComputeDescription.
        :type virtual_cpu: VirtualCpuData
        """
        if virtual_cpu is None:
            raise ValueError("Invalid value for `virtual_cpu`, must not be `None`")  # noqa: E501

        self._virtual_cpu = virtual_cpu
