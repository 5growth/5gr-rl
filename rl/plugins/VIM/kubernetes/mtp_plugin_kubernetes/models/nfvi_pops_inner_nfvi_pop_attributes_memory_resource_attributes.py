# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class NfviPopsInnerNfviPopAttributesMemoryResourceAttributes(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, available_capacity: str=None, reserved_capacity: str=None, total_capacity: str=None, allocated_capacity: str=None):  # noqa: E501
        """NfviPopsInnerNfviPopAttributesMemoryResourceAttributes - a model defined in Swagger

        :param available_capacity: The available_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.  # noqa: E501
        :type available_capacity: str
        :param reserved_capacity: The reserved_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.  # noqa: E501
        :type reserved_capacity: str
        :param total_capacity: The total_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.  # noqa: E501
        :type total_capacity: str
        :param allocated_capacity: The allocated_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.  # noqa: E501
        :type allocated_capacity: str
        """
        self.swagger_types = {
            'available_capacity': str,
            'reserved_capacity': str,
            'total_capacity': str,
            'allocated_capacity': str
        }

        self.attribute_map = {
            'available_capacity': 'availableCapacity',
            'reserved_capacity': 'reservedCapacity',
            'total_capacity': 'totalCapacity',
            'allocated_capacity': 'allocatedCapacity'
        }

        self._available_capacity = available_capacity
        self._reserved_capacity = reserved_capacity
        self._total_capacity = total_capacity
        self._allocated_capacity = allocated_capacity

    @classmethod
    def from_dict(cls, dikt) -> 'NfviPopsInnerNfviPopAttributesMemoryResourceAttributes':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The NfviPops_inner_nfviPopAttributes_memoryResourceAttributes of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.  # noqa: E501
        :rtype: NfviPopsInnerNfviPopAttributesMemoryResourceAttributes
        """
        return util.deserialize_model(dikt, cls)

    @property
    def available_capacity(self) -> str:
        """Gets the available_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The free capacity available for allocation and reservation of memory resources.  # noqa: E501

        :return: The available_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :rtype: str
        """
        return self._available_capacity

    @available_capacity.setter
    def available_capacity(self, available_capacity: str):
        """Sets the available_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The free capacity available for allocation and reservation of memory resources.  # noqa: E501

        :param available_capacity: The available_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :type available_capacity: str
        """
        if available_capacity is None:
            raise ValueError("Invalid value for `available_capacity`, must not be `None`")  # noqa: E501

        self._available_capacity = available_capacity

    @property
    def reserved_capacity(self) -> str:
        """Gets the reserved_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The reserved capacity of   memory resources.  # noqa: E501

        :return: The reserved_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :rtype: str
        """
        return self._reserved_capacity

    @reserved_capacity.setter
    def reserved_capacity(self, reserved_capacity: str):
        """Sets the reserved_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The reserved capacity of   memory resources.  # noqa: E501

        :param reserved_capacity: The reserved_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :type reserved_capacity: str
        """
        if reserved_capacity is None:
            raise ValueError("Invalid value for `reserved_capacity`, must not be `None`")  # noqa: E501

        self._reserved_capacity = reserved_capacity

    @property
    def total_capacity(self) -> str:
        """Gets the total_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The total capacity of memory resources.  # noqa: E501

        :return: The total_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :rtype: str
        """
        return self._total_capacity

    @total_capacity.setter
    def total_capacity(self, total_capacity: str):
        """Sets the total_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The total capacity of memory resources.  # noqa: E501

        :param total_capacity: The total_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :type total_capacity: str
        """
        if total_capacity is None:
            raise ValueError("Invalid value for `total_capacity`, must not be `None`")  # noqa: E501

        self._total_capacity = total_capacity

    @property
    def allocated_capacity(self) -> str:
        """Gets the allocated_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The allocated capacity of memory resources.  # noqa: E501

        :return: The allocated_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :rtype: str
        """
        return self._allocated_capacity

    @allocated_capacity.setter
    def allocated_capacity(self, allocated_capacity: str):
        """Sets the allocated_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.

        The allocated capacity of memory resources.  # noqa: E501

        :param allocated_capacity: The allocated_capacity of this NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.
        :type allocated_capacity: str
        """
        if allocated_capacity is None:
            raise ValueError("Invalid value for `allocated_capacity`, must not be `None`")  # noqa: E501

        self._allocated_capacity = allocated_capacity
