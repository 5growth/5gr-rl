# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class VIMVirtualComputeVirtualNetworkInterface(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, acceleration_capability: str=None, bandwidth: str=None, ip_address: List[str]=None, mac_address: str=None, metadata: str=None, network_id: str=None, network_port_id: str=None, operational_state: str=None, owner_id: str=None, resource_id: str=None, type_configuration: str=None, type_virtual_nic: str=None):  # noqa: E501
        """VIMVirtualComputeVirtualNetworkInterface - a model defined in Swagger

        :param acceleration_capability: The acceleration_capability of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type acceleration_capability: str
        :param bandwidth: The bandwidth of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type bandwidth: str
        :param ip_address: The ip_address of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type ip_address: List[str]
        :param mac_address: The mac_address of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type mac_address: str
        :param metadata: The metadata of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type metadata: str
        :param network_id: The network_id of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type network_id: str
        :param network_port_id: The network_port_id of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type network_port_id: str
        :param operational_state: The operational_state of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type operational_state: str
        :param owner_id: The owner_id of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type owner_id: str
        :param resource_id: The resource_id of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type resource_id: str
        :param type_configuration: The type_configuration of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type type_configuration: str
        :param type_virtual_nic: The type_virtual_nic of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :type type_virtual_nic: str
        """
        self.swagger_types = {
            'acceleration_capability': str,
            'bandwidth': str,
            'ip_address': List[str],
            'mac_address': str,
            'metadata': str,
            'network_id': str,
            'network_port_id': str,
            'operational_state': str,
            'owner_id': str,
            'resource_id': str,
            'type_configuration': str,
            'type_virtual_nic': str
        }

        self.attribute_map = {
            'acceleration_capability': 'accelerationCapability',
            'bandwidth': 'bandwidth',
            'ip_address': 'ipAddress',
            'mac_address': 'macAddress',
            'metadata': 'metadata',
            'network_id': 'networkId',
            'network_port_id': 'networkPortId',
            'operational_state': 'operationalState',
            'owner_id': 'ownerId',
            'resource_id': 'resourceId',
            'type_configuration': 'typeConfiguration',
            'type_virtual_nic': 'typeVirtualNic'
        }

        self._acceleration_capability = acceleration_capability
        self._bandwidth = bandwidth
        self._ip_address = ip_address
        self._mac_address = mac_address
        self._metadata = metadata
        self._network_id = network_id
        self._network_port_id = network_port_id
        self._operational_state = operational_state
        self._owner_id = owner_id
        self._resource_id = resource_id
        self._type_configuration = type_configuration
        self._type_virtual_nic = type_virtual_nic

    @classmethod
    def from_dict(cls, dikt) -> 'VIMVirtualComputeVirtualNetworkInterface':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VIMVirtualCompute_virtualNetworkInterface of this VIMVirtualComputeVirtualNetworkInterface.  # noqa: E501
        :rtype: VIMVirtualComputeVirtualNetworkInterface
        """
        return util.deserialize_model(dikt, cls)

    @property
    def acceleration_capability(self) -> str:
        """Gets the acceleration_capability of this VIMVirtualComputeVirtualNetworkInterface.

        Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.  # noqa: E501

        :return: The acceleration_capability of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._acceleration_capability

    @acceleration_capability.setter
    def acceleration_capability(self, acceleration_capability: str):
        """Sets the acceleration_capability of this VIMVirtualComputeVirtualNetworkInterface.

        Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.  # noqa: E501

        :param acceleration_capability: The acceleration_capability of this VIMVirtualComputeVirtualNetworkInterface.
        :type acceleration_capability: str
        """
        if acceleration_capability is None:
            raise ValueError("Invalid value for `acceleration_capability`, must not be `None`")  # noqa: E501

        self._acceleration_capability = acceleration_capability

    @property
    def bandwidth(self) -> str:
        """Gets the bandwidth of this VIMVirtualComputeVirtualNetworkInterface.

        The bandwidth of the virtual network interface (in Mbps).  # noqa: E501

        :return: The bandwidth of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._bandwidth

    @bandwidth.setter
    def bandwidth(self, bandwidth: str):
        """Sets the bandwidth of this VIMVirtualComputeVirtualNetworkInterface.

        The bandwidth of the virtual network interface (in Mbps).  # noqa: E501

        :param bandwidth: The bandwidth of this VIMVirtualComputeVirtualNetworkInterface.
        :type bandwidth: str
        """
        if bandwidth is None:
            raise ValueError("Invalid value for `bandwidth`, must not be `None`")  # noqa: E501

        self._bandwidth = bandwidth

    @property
    def ip_address(self) -> List[str]:
        """Gets the ip_address of this VIMVirtualComputeVirtualNetworkInterface.

        The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.  # noqa: E501

        :return: The ip_address of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: List[str]
        """
        return self._ip_address

    @ip_address.setter
    def ip_address(self, ip_address: List[str]):
        """Sets the ip_address of this VIMVirtualComputeVirtualNetworkInterface.

        The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.  # noqa: E501

        :param ip_address: The ip_address of this VIMVirtualComputeVirtualNetworkInterface.
        :type ip_address: List[str]
        """
        if ip_address is None:
            raise ValueError("Invalid value for `ip_address`, must not be `None`")  # noqa: E501

        self._ip_address = ip_address

    @property
    def mac_address(self) -> str:
        """Gets the mac_address of this VIMVirtualComputeVirtualNetworkInterface.

        The MAC address of the virtual network interface.  # noqa: E501

        :return: The mac_address of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._mac_address

    @mac_address.setter
    def mac_address(self, mac_address: str):
        """Sets the mac_address of this VIMVirtualComputeVirtualNetworkInterface.

        The MAC address of the virtual network interface.  # noqa: E501

        :param mac_address: The mac_address of this VIMVirtualComputeVirtualNetworkInterface.
        :type mac_address: str
        """
        if mac_address is None:
            raise ValueError("Invalid value for `mac_address`, must not be `None`")  # noqa: E501

        self._mac_address = mac_address

    @property
    def metadata(self) -> str:
        """Gets the metadata of this VIMVirtualComputeVirtualNetworkInterface.

        List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.  # noqa: E501

        :return: The metadata of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._metadata

    @metadata.setter
    def metadata(self, metadata: str):
        """Sets the metadata of this VIMVirtualComputeVirtualNetworkInterface.

        List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.  # noqa: E501

        :param metadata: The metadata of this VIMVirtualComputeVirtualNetworkInterface.
        :type metadata: str
        """
        if metadata is None:
            raise ValueError("Invalid value for `metadata`, must not be `None`")  # noqa: E501

        self._metadata = metadata

    @property
    def network_id(self) -> str:
        """Gets the network_id of this VIMVirtualComputeVirtualNetworkInterface.

        In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.  # noqa: E501

        :return: The network_id of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._network_id

    @network_id.setter
    def network_id(self, network_id: str):
        """Sets the network_id of this VIMVirtualComputeVirtualNetworkInterface.

        In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.  # noqa: E501

        :param network_id: The network_id of this VIMVirtualComputeVirtualNetworkInterface.
        :type network_id: str
        """
        if network_id is None:
            raise ValueError("Invalid value for `network_id`, must not be `None`")  # noqa: E501

        self._network_id = network_id

    @property
    def network_port_id(self) -> str:
        """Gets the network_port_id of this VIMVirtualComputeVirtualNetworkInterface.

        If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.  # noqa: E501

        :return: The network_port_id of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._network_port_id

    @network_port_id.setter
    def network_port_id(self, network_port_id: str):
        """Sets the network_port_id of this VIMVirtualComputeVirtualNetworkInterface.

        If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.  # noqa: E501

        :param network_port_id: The network_port_id of this VIMVirtualComputeVirtualNetworkInterface.
        :type network_port_id: str
        """
        if network_port_id is None:
            raise ValueError("Invalid value for `network_port_id`, must not be `None`")  # noqa: E501

        self._network_port_id = network_port_id

    @property
    def operational_state(self) -> str:
        """Gets the operational_state of this VIMVirtualComputeVirtualNetworkInterface.

        The operational state of the virtual network interface.  # noqa: E501

        :return: The operational_state of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._operational_state

    @operational_state.setter
    def operational_state(self, operational_state: str):
        """Sets the operational_state of this VIMVirtualComputeVirtualNetworkInterface.

        The operational state of the virtual network interface.  # noqa: E501

        :param operational_state: The operational_state of this VIMVirtualComputeVirtualNetworkInterface.
        :type operational_state: str
        """
        if operational_state is None:
            raise ValueError("Invalid value for `operational_state`, must not be `None`")  # noqa: E501

        self._operational_state = operational_state

    @property
    def owner_id(self) -> str:
        """Gets the owner_id of this VIMVirtualComputeVirtualNetworkInterface.

        Identifier of the owner of the network interface (e.g. a virtualised compute resource).   # noqa: E501

        :return: The owner_id of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._owner_id

    @owner_id.setter
    def owner_id(self, owner_id: str):
        """Sets the owner_id of this VIMVirtualComputeVirtualNetworkInterface.

        Identifier of the owner of the network interface (e.g. a virtualised compute resource).   # noqa: E501

        :param owner_id: The owner_id of this VIMVirtualComputeVirtualNetworkInterface.
        :type owner_id: str
        """
        if owner_id is None:
            raise ValueError("Invalid value for `owner_id`, must not be `None`")  # noqa: E501

        self._owner_id = owner_id

    @property
    def resource_id(self) -> str:
        """Gets the resource_id of this VIMVirtualComputeVirtualNetworkInterface.

        Identifier of the virtual network interface.  # noqa: E501

        :return: The resource_id of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._resource_id

    @resource_id.setter
    def resource_id(self, resource_id: str):
        """Sets the resource_id of this VIMVirtualComputeVirtualNetworkInterface.

        Identifier of the virtual network interface.  # noqa: E501

        :param resource_id: The resource_id of this VIMVirtualComputeVirtualNetworkInterface.
        :type resource_id: str
        """
        if resource_id is None:
            raise ValueError("Invalid value for `resource_id`, must not be `None`")  # noqa: E501

        self._resource_id = resource_id

    @property
    def type_configuration(self) -> str:
        """Gets the type_configuration of this VIMVirtualComputeVirtualNetworkInterface.

        Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).  # noqa: E501

        :return: The type_configuration of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._type_configuration

    @type_configuration.setter
    def type_configuration(self, type_configuration: str):
        """Sets the type_configuration of this VIMVirtualComputeVirtualNetworkInterface.

        Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).  # noqa: E501

        :param type_configuration: The type_configuration of this VIMVirtualComputeVirtualNetworkInterface.
        :type type_configuration: str
        """
        if type_configuration is None:
            raise ValueError("Invalid value for `type_configuration`, must not be `None`")  # noqa: E501

        self._type_configuration = type_configuration

    @property
    def type_virtual_nic(self) -> str:
        """Gets the type_virtual_nic of this VIMVirtualComputeVirtualNetworkInterface.

        Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.  # noqa: E501

        :return: The type_virtual_nic of this VIMVirtualComputeVirtualNetworkInterface.
        :rtype: str
        """
        return self._type_virtual_nic

    @type_virtual_nic.setter
    def type_virtual_nic(self, type_virtual_nic: str):
        """Sets the type_virtual_nic of this VIMVirtualComputeVirtualNetworkInterface.

        Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.  # noqa: E501

        :param type_virtual_nic: The type_virtual_nic of this VIMVirtualComputeVirtualNetworkInterface.
        :type type_virtual_nic: str
        """
        if type_virtual_nic is None:
            raise ValueError("Invalid value for `type_virtual_nic`, must not be `None`")  # noqa: E501

        self._type_virtual_nic = type_virtual_nic
