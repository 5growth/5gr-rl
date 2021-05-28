# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.reserved_virtual_compute_virtualisation_container_reserved_virtual_network_interface import \
    ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface
from mtp_plugin_kubernetes.models.virtual_compute_virtual_cpu import VirtualComputeVirtualCpu
from mtp_plugin_kubernetes.models.virtual_compute_virtual_memory import VirtualComputeVirtualMemory


class VirtualCompute(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, acceleration_capability: List[str]=None, compute_id: str=None, compute_name: str=None, flavour_id: str=None, host_id: str=None, operational_state: str=None, vc_image_id: str=None, virtual_cpu: VirtualComputeVirtualCpu=None, virtual_disks: str=None, virtual_memory: VirtualComputeVirtualMemory=None, virtual_network_interface: List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]=None, zone_id: str=None, mecapp_id: str=None):  # noqa: E501
        """VirtualCompute - a model defined in Swagger

        :param acceleration_capability: The acceleration_capability of this VirtualCompute.  # noqa: E501
        :type acceleration_capability: List[str]
        :param compute_id: The compute_id of this VirtualCompute.  # noqa: E501
        :type compute_id: str
        :param compute_name: The compute_name of this VirtualCompute.  # noqa: E501
        :type compute_name: str
        :param flavour_id: The flavour_id of this VirtualCompute.  # noqa: E501
        :type flavour_id: str
        :param host_id: The host_id of this VirtualCompute.  # noqa: E501
        :type host_id: str
        :param operational_state: The operational_state of this VirtualCompute.  # noqa: E501
        :type operational_state: str
        :param vc_image_id: The vc_image_id of this VirtualCompute.  # noqa: E501
        :type vc_image_id: str
        :param virtual_cpu: The virtual_cpu of this VirtualCompute.  # noqa: E501
        :type virtual_cpu: VirtualComputeVirtualCpu
        :param virtual_disks: The virtual_disks of this VirtualCompute.  # noqa: E501
        :type virtual_disks: str
        :param virtual_memory: The virtual_memory of this VirtualCompute.  # noqa: E501
        :type virtual_memory: VirtualComputeVirtualMemory
        :param virtual_network_interface: The virtual_network_interface of this VirtualCompute.  # noqa: E501
        :type virtual_network_interface: List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]
        :param zone_id: The zone_id of this VirtualCompute.  # noqa: E501
        :type zone_id: str
        :param mecapp_id: The mecapp_id of this VirtualCompute.  # noqa: E501
        :type mecapp_id: str
        """
        self.swagger_types = {
            'acceleration_capability': List[str],
            'compute_id': str,
            'compute_name': str,
            'flavour_id': str,
            'host_id': str,
            'operational_state': str,
            'vc_image_id': str,
            'virtual_cpu': VirtualComputeVirtualCpu,
            'virtual_disks': str,
            'virtual_memory': VirtualComputeVirtualMemory,
            'virtual_network_interface': List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface],
            'zone_id': str,
            'mecapp_id': str
        }

        self.attribute_map = {
            'acceleration_capability': 'accelerationCapability',
            'compute_id': 'computeId',
            'compute_name': 'computeName',
            'flavour_id': 'flavourId',
            'host_id': 'hostId',
            'operational_state': 'operationalState',
            'vc_image_id': 'vcImageId',
            'virtual_cpu': 'virtualCpu',
            'virtual_disks': 'virtualDisks',
            'virtual_memory': 'virtualMemory',
            'virtual_network_interface': 'virtualNetworkInterface',
            'zone_id': 'zoneId',
            'mecapp_id': 'mecappID'
        }

        self._acceleration_capability = acceleration_capability
        self._compute_id = compute_id
        self._compute_name = compute_name
        self._flavour_id = flavour_id
        self._host_id = host_id
        self._operational_state = operational_state
        self._vc_image_id = vc_image_id
        self._virtual_cpu = virtual_cpu
        self._virtual_disks = virtual_disks
        self._virtual_memory = virtual_memory
        self._virtual_network_interface = virtual_network_interface
        self._zone_id = zone_id
        self._mecapp_id = mecapp_id

    @classmethod
    def from_dict(cls, dikt) -> 'VirtualCompute':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VirtualCompute of this VirtualCompute.  # noqa: E501
        :rtype: VirtualCompute
        """
        return util.deserialize_model(dikt, cls)

    @property
    def acceleration_capability(self) -> List[str]:
        """Gets the acceleration_capability of this VirtualCompute.

        Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.  # noqa: E501

        :return: The acceleration_capability of this VirtualCompute.
        :rtype: List[str]
        """
        return self._acceleration_capability

    @acceleration_capability.setter
    def acceleration_capability(self, acceleration_capability: List[str]):
        """Sets the acceleration_capability of this VirtualCompute.

        Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.  # noqa: E501

        :param acceleration_capability: The acceleration_capability of this VirtualCompute.
        :type acceleration_capability: List[str]
        """
        if acceleration_capability is None:
            raise ValueError("Invalid value for `acceleration_capability`, must not be `None`")  # noqa: E501

        self._acceleration_capability = acceleration_capability

    @property
    def compute_id(self) -> str:
        """Gets the compute_id of this VirtualCompute.

        Identifier of the virtualised compute resource.  # noqa: E501

        :return: The compute_id of this VirtualCompute.
        :rtype: str
        """
        return self._compute_id

    @compute_id.setter
    def compute_id(self, compute_id: str):
        """Sets the compute_id of this VirtualCompute.

        Identifier of the virtualised compute resource.  # noqa: E501

        :param compute_id: The compute_id of this VirtualCompute.
        :type compute_id: str
        """
        if compute_id is None:
            raise ValueError("Invalid value for `compute_id`, must not be `None`")  # noqa: E501

        self._compute_id = compute_id

    @property
    def compute_name(self) -> str:
        """Gets the compute_name of this VirtualCompute.

        Name of the virtualised compute resource.  # noqa: E501

        :return: The compute_name of this VirtualCompute.
        :rtype: str
        """
        return self._compute_name

    @compute_name.setter
    def compute_name(self, compute_name: str):
        """Sets the compute_name of this VirtualCompute.

        Name of the virtualised compute resource.  # noqa: E501

        :param compute_name: The compute_name of this VirtualCompute.
        :type compute_name: str
        """
        if compute_name is None:
            raise ValueError("Invalid value for `compute_name`, must not be `None`")  # noqa: E501

        self._compute_name = compute_name

    @property
    def flavour_id(self) -> str:
        """Gets the flavour_id of this VirtualCompute.

        Identifier of the given compute flavour used to instantiate this virtual compute.  # noqa: E501

        :return: The flavour_id of this VirtualCompute.
        :rtype: str
        """
        return self._flavour_id

    @flavour_id.setter
    def flavour_id(self, flavour_id: str):
        """Sets the flavour_id of this VirtualCompute.

        Identifier of the given compute flavour used to instantiate this virtual compute.  # noqa: E501

        :param flavour_id: The flavour_id of this VirtualCompute.
        :type flavour_id: str
        """
        if flavour_id is None:
            raise ValueError("Invalid value for `flavour_id`, must not be `None`")  # noqa: E501

        self._flavour_id = flavour_id

    @property
    def host_id(self) -> str:
        """Gets the host_id of this VirtualCompute.

        Identifier of the host the virtualised compute resource is allocated on.  # noqa: E501

        :return: The host_id of this VirtualCompute.
        :rtype: str
        """
        return self._host_id

    @host_id.setter
    def host_id(self, host_id: str):
        """Sets the host_id of this VirtualCompute.

        Identifier of the host the virtualised compute resource is allocated on.  # noqa: E501

        :param host_id: The host_id of this VirtualCompute.
        :type host_id: str
        """
        if host_id is None:
            raise ValueError("Invalid value for `host_id`, must not be `None`")  # noqa: E501

        self._host_id = host_id

    @property
    def operational_state(self) -> str:
        """Gets the operational_state of this VirtualCompute.

        Operational state of the compute resource.  # noqa: E501

        :return: The operational_state of this VirtualCompute.
        :rtype: str
        """
        return self._operational_state

    @operational_state.setter
    def operational_state(self, operational_state: str):
        """Sets the operational_state of this VirtualCompute.

        Operational state of the compute resource.  # noqa: E501

        :param operational_state: The operational_state of this VirtualCompute.
        :type operational_state: str
        """
        if operational_state is None:
            raise ValueError("Invalid value for `operational_state`, must not be `None`")  # noqa: E501

        self._operational_state = operational_state

    @property
    def vc_image_id(self) -> str:
        """Gets the vc_image_id of this VirtualCompute.

        Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.  # noqa: E501

        :return: The vc_image_id of this VirtualCompute.
        :rtype: str
        """
        return self._vc_image_id

    @vc_image_id.setter
    def vc_image_id(self, vc_image_id: str):
        """Sets the vc_image_id of this VirtualCompute.

        Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.  # noqa: E501

        :param vc_image_id: The vc_image_id of this VirtualCompute.
        :type vc_image_id: str
        """
        if vc_image_id is None:
            raise ValueError("Invalid value for `vc_image_id`, must not be `None`")  # noqa: E501

        self._vc_image_id = vc_image_id

    @property
    def virtual_cpu(self) -> VirtualComputeVirtualCpu:
        """Gets the virtual_cpu of this VirtualCompute.


        :return: The virtual_cpu of this VirtualCompute.
        :rtype: VirtualComputeVirtualCpu
        """
        return self._virtual_cpu

    @virtual_cpu.setter
    def virtual_cpu(self, virtual_cpu: VirtualComputeVirtualCpu):
        """Sets the virtual_cpu of this VirtualCompute.


        :param virtual_cpu: The virtual_cpu of this VirtualCompute.
        :type virtual_cpu: VirtualComputeVirtualCpu
        """
        if virtual_cpu is None:
            raise ValueError("Invalid value for `virtual_cpu`, must not be `None`")  # noqa: E501

        self._virtual_cpu = virtual_cpu

    @property
    def virtual_disks(self) -> str:
        """Gets the virtual_disks of this VirtualCompute.

        Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)  # noqa: E501

        :return: The virtual_disks of this VirtualCompute.
        :rtype: str
        """
        return self._virtual_disks

    @virtual_disks.setter
    def virtual_disks(self, virtual_disks: str):
        """Sets the virtual_disks of this VirtualCompute.

        Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)  # noqa: E501

        :param virtual_disks: The virtual_disks of this VirtualCompute.
        :type virtual_disks: str
        """
        if virtual_disks is None:
            raise ValueError("Invalid value for `virtual_disks`, must not be `None`")  # noqa: E501

        self._virtual_disks = virtual_disks

    @property
    def virtual_memory(self) -> VirtualComputeVirtualMemory:
        """Gets the virtual_memory of this VirtualCompute.


        :return: The virtual_memory of this VirtualCompute.
        :rtype: VirtualComputeVirtualMemory
        """
        return self._virtual_memory

    @virtual_memory.setter
    def virtual_memory(self, virtual_memory: VirtualComputeVirtualMemory):
        """Sets the virtual_memory of this VirtualCompute.


        :param virtual_memory: The virtual_memory of this VirtualCompute.
        :type virtual_memory: VirtualComputeVirtualMemory
        """
        if virtual_memory is None:
            raise ValueError("Invalid value for `virtual_memory`, must not be `None`")  # noqa: E501

        self._virtual_memory = virtual_memory

    @property
    def virtual_network_interface(self) -> List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]:
        """Gets the virtual_network_interface of this VirtualCompute.

        Element with information of the instantiated virtual network interfaces of the compute resource.  # noqa: E501

        :return: The virtual_network_interface of this VirtualCompute.
        :rtype: List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]
        """
        return self._virtual_network_interface

    @virtual_network_interface.setter
    def virtual_network_interface(self, virtual_network_interface: List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]):
        """Sets the virtual_network_interface of this VirtualCompute.

        Element with information of the instantiated virtual network interfaces of the compute resource.  # noqa: E501

        :param virtual_network_interface: The virtual_network_interface of this VirtualCompute.
        :type virtual_network_interface: List[ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface]
        """
        if virtual_network_interface is None:
            raise ValueError("Invalid value for `virtual_network_interface`, must not be `None`")  # noqa: E501

        self._virtual_network_interface = virtual_network_interface

    @property
    def zone_id(self) -> str:
        """Gets the zone_id of this VirtualCompute.

        If present, it identifies the Resource Zone where the virtual compute resources have been allocated.  # noqa: E501

        :return: The zone_id of this VirtualCompute.
        :rtype: str
        """
        return self._zone_id

    @zone_id.setter
    def zone_id(self, zone_id: str):
        """Sets the zone_id of this VirtualCompute.

        If present, it identifies the Resource Zone where the virtual compute resources have been allocated.  # noqa: E501

        :param zone_id: The zone_id of this VirtualCompute.
        :type zone_id: str
        """
        if zone_id is None:
            raise ValueError("Invalid value for `zone_id`, must not be `None`")  # noqa: E501

        self._zone_id = zone_id

    @property
    def mecapp_id(self) -> str:
        """Gets the mecapp_id of this VirtualCompute.

        If present, it identifies the reference MEC AppD reference Descritptor to apply for the allocated compute resources  # noqa: E501

        :return: The mecapp_id of this VirtualCompute.
        :rtype: str
        """
        return self._mecapp_id

    @mecapp_id.setter
    def mecapp_id(self, mecapp_id: str):
        """Sets the mecapp_id of this VirtualCompute.

        If present, it identifies the reference MEC AppD reference Descritptor to apply for the allocated compute resources  # noqa: E501

        :param mecapp_id: The mecapp_id of this VirtualCompute.
        :type mecapp_id: str
        """
        if mecapp_id is None:
            raise ValueError("Invalid value for `mecapp_id`, must not be `None`")  # noqa: E501

        self._mecapp_id = mecapp_id
