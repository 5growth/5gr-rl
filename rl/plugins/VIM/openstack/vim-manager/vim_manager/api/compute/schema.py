# Copyright 2018 b<>com.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
# IDDN number: IDDN.FR.001.470053.000.S.C.2018.000.00000.
#
# -*- coding: utf-8 -*-

from flasgger import fields
from flasgger import Schema

from marshmallow import validates
from marshmallow import ValidationError

from netaddr import EUI
from netaddr import IPAddress

from vim_manager.api.schema import VirtualStorage
from vim_manager.api.schema import KeyValuePair

class VirtualMemory(Schema):
    virtualMemSize = fields.Number(
        required=True,
        description='Amount of virtual Memory (e.g. in MB).')
    virtualMemOversubscriptionPolicy = fields.Str(
        required=True,
        description='The memory core oversubscription policy in terms of '
                    'virtual memory to physical memory on the platform. The '
                    'cardinality can be 0 if no policy has been defined '
                    'during the allocation request.')
    numaEnabled = fields.Bool(
        required=True,
        description='It specifies the memory allocation to be cognisant of '
                    'the relevant process/core allocation.')


class VirtualCpuPinning(Schema):
    cpuPinningPolicy = fields.Str(
        required=True,
        description='The policy can take values of "static" or "dynamic". In '
                    'case of "static" the virtual CPU cores have been '
                    'allocated to physical CPU cores according to the rules '
                    'defined in cpuPinningRules. In case of "dynamic" the '
                    'allocation of virtual CPU cores to physical CPU cores is '
                    'decided by the VIM.')
    cpuPinningRules = fields.Str(
        required=True,
        many=True,
        description='A list of rules that should be considered during the '
                    'allocation of the virtual CPUs to physical CPUs in case '
                    'of "static" cpuPinningPolicy.')
    cpuMap = fields.Str(
        required=True,
        description='Shows the association of virtual CPU cores to physical '
                    'CPU cores.')


class VirtualCpu(Schema):
    """The virtual CPU(s) of the virtualised compute."""
    cpuArchitecture = fields.Str(
        required=True,
        description='CPU architecture type. Examples are x86, ARM.')
    numVirtualCpu = fields.Int(
        required=True,
        description='Number of virtual CPUs.')
    cpuClock = fields.Number(
        required=True,
        description='Minimum CPU clock rate (e.g. in MHz) available for the '
                    'virtualised CPU resources.')
    virtualCpuOversubscriptionPolicy = fields.Str(
        required=True,
        description='The CPU core oversubscription policy, e.g. the relation '
                    'of virtual CPU cores to physical CPU cores/threads. The '
                    'cardinality can be 0 if no policy has been defined '
                    'during the allocation request.')
    virtualCpuPinning = fields.Nested(
        VirtualCpuPinning,
        required=True,
        description='The virtual CPU pinning configuration for the '
                    'virtualised compute resource.')


class VirtualNetworkInterface(Schema):
    """VirtualNetworkInterface.

    A virtual network interface resource is a communication endpoint under
    an instantiated compute resource.
    """
    resourceId = fields.Str(
        required=True,
        description='Identifier of the virtual network interface.')
    ownerId = fields.Str(
        required=True,
        description='Identifier of the owner of the network interface (e.g. '
                    'a virtualised compute resource). ')
    networkId = fields.Str(
        required=True,
        description='In the case when the virtual network interface is '
                    'attached to the network, it identifies such a network. '
                    'The cardinality can be 0 in the case that a network '
                    'interface is created without being attached to any '
                    'specific network.')
    networkPortId = fields.Str(
        required=True,
        description='If the virtual network interface is attached to a '
                    'specific network port, it identifies such a network '
                    'port. The cardinality can be 0 in the case that a '
                    'network interface is created without any specific '
                    'network port attachment.')
    ipAddress = fields.Str(
        required=True,
        many=True,
        description='The virtual network interface can be configured with '
                    'specific IP address(es) associated to the network to be '
                    'attached to. The cardinality can be 0 in the case that a '
                    'network interface is created without being attached to '
                    'any specific network, or when an IP address can be '
                    'automatically configured, e.g. by DHCP.')
    typeVirtualNic = fields.Str(
        required=True,
        description='Type of network interface. The type allows for defining '
                    'how such interface is to be realized, e.g. normal '
                    'virtual NIC, with direct PCI passthrough, etc.')
    typeConfiguration = fields.Str(
        required=True,
        many=True,
        description='Extra configuration that the virtual network interface '
                    'supports based on the type of virtual network interface, '
                    'including support for SR-IOV with configuration of '
                    'virtual functions (VF).')
    macAddress = fields.Str(
        required=True,
        description='The MAC address of the virtual network interface.')
    bandwidth = fields.Number(
        required=True,
        description='The bandwidth of the virtual network interface (in '
                    'Mbps).')
    accelerationCapability = fields.Str(
        required=True,
        many=True,
        description='Shows the acceleration capabilities utilized by the '
                    'virtual network interface. The cardinality can be 0, if '
                    'no acceleration capability is utilized.')
    operationalState = fields.Str(
        required=True,
        description='The operational state of the virtual network interface.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised resource.')


class VirtualCompute(Schema):
    """Attributes for the VirtualCompute information element."""

    computeId = fields.Str(
        required=True,
        description='Identifier of the virtualised compute resource.')
    computeName = fields.Str(
        required=True,
        description='Name of the virtualised compute resource.')
    flavourId = fields.Str(
        required=True,
        description='Identifier of the given compute flavour used to '
                    'instantiate this virtual compute.')
    accelerationCapability = fields.List(
        fields.String(),
        required=True,
        description='Selected acceleration capabilities (e.g. crypto, GPU) '
                    'from the set of capabilities offered by the compute node '
                    'acceleration resources. The cardinality can be 0, if no '
                    'particular acceleration capability is provided.')
    virtualCpu = fields.Nested(
        VirtualCpu,
        required=True,
        description='The virtual CPU(s) of the virtualised compute.')
    virtualMemory = fields.Nested(
        VirtualMemory,
        required=True,
        description='The virtual memory of the compute.')
    virtualNetworkInterface = fields.Nested(
        VirtualNetworkInterface,
        required=True,
        many=True,
        description='Element with information of the instantiated virtual '
                    'network interfaces of the compute resource.')
    virtualDisks = fields.Str(
        VirtualStorage,
        required=True,
        many=True,
        description='Element with information of the virtualised storage '
                    'resources (volumes, ephemeral that are attached to the '
                    'compute resource.)')
    vcImageId = fields.Str(
        required=True,
        description='Identifier of the virtualisation container software '
                    'image (e.g. virtual machine image). Cardinality can be 0 '
                    'if an "empty" virtualisation container is allocated.')
    zoneId = fields.Str(
        required=True,
        description='If present, it identifies the Resource Zone where the '
                    'virtual compute resources have been allocated.')
    hostId = fields.Str(
        required=True,
        description='Identifier of the host the virtualised compute resource '
                    'is allocated on.')
    operationalState = fields.Str(
        required=True,
        description='Operational state of the compute resource.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised resource.')


class VirtualComputeFlavour(Schema):
    flavourName = fields.Str(
        required=False,
        description='Name of the flavour !! These field is not compliant IFA005 !!')
    flavourId = fields.Str(
        required=True,
        description='Identifier given to the compute flavour.')
    accelerationCapability = fields.Str(
        required=True,
        description='Selected acceleration capabilities (e.g. crypto, GPU) '
                    'from the set of capabilities offered by the compute node '
                    'acceleration resources. The cardinality can be 0, if no '
                    'particular acceleration capability is requested.')
    virtualMemory = fields.Nested(
        VirtualMemory,
        required=True,
        description='The virtual memory of the virtualised compute.')
    virtualCpu = fields.Nested(
        VirtualCpu,
        many=True,
        required=True,
        description='The virtual CPU(s) of the virtualised compute.')
    storageAttributes = fields.Nested(
        VirtualStorage,
        many=True,
        required=True,
        description='Element containing information about the size of '
                    'virtualised storage resources (e.g. size of volume, in '
                    'GB), the type of storage (e.g. volume, object).')
    virtualNetworkInterface = fields.Nested(
        VirtualNetworkInterface,
        required=True,
        description='The virtual network interfaces of the virtualised '
                    'compute.')


class VirtualCpuResourceInformation(Schema):
    cpuArchitecture = fields.Str(
        required=True,
        description='CPU architecture type. Examples are x86, ARM.')
    numVirtualCpu = fields.Number(
        required=True,
        description='Number of virtual CPUs. Cardinality "1" covers the case '
                    'where a specific configuration for the consumable '
                    'resource is advertised.')
    cpuClock = fields.Number(
        required=True,
        description='Minimum CPU clock rate (e.g. in MHz) available for the '
                    'virtualised CPU resources.')
    virtualCpuOversubscriptionPolicy = fields.Str(
        required=True,
        description='The CPU core oversubscription policy, e.g. the relation '
                    'of virtual CPU cores to physical CPU cores/threads. The '
                    'cardinality can be 0 if no concrete policy is defined.')
    virtualCpuPinningSupported = fields.Bool(
        required=True,
        description='It defines whether CPU pinning capability is available '
                    'on the consumable virtualised compute resource.')


class VirtualMemoryResourceInformation(Schema):
    virtualMemSize = fields.Number(
        required=True,
        description='Amount of virtual memory (e.g. in MB). Cardinality "1" '
                    'covers the case where a specific configuration for the '
                    'consumable resource is advertised.')
    virtualMemOversubscriptionPolicy = fields.Str(
        required=True,
        description='The memory core oversubscription policy in terms of '
                    'virtual memory to physical memory on the platform. The '
                    'cardinality can be 0 if no concrete policy is defined.')
    numaSupported = fields.Bool(
        required=True,
        description='It specifies if the memory allocation can be cognisant '
                    'of the relevant process/core allocation.')


class VirtualComputeResourceInformation(Schema):
    computeResourceTypeId = fields.Str(
        required=True,
        description='Identifier of the consumable virtualised compute '
                    'resource type.')
    virtualMemory = fields.Nested(
        VirtualMemoryResourceInformation,
        required=True,
        description='It defines the virtual memory characteristics of the '
                    'consumable virtualised compute resource.')
    virtualCPU = fields.Nested(
        VirtualCpuResourceInformation,
        required=True,
        description='It defines the virtual CPU(s) characteristics of the '
                    'consumable virtualised compute resource.')
    accelerationCapability = fields.Str(
        many=True,
        required=True,
        description='Acceleration capabilities (e.g. crypto, GPU) for the '
                    'consumable virtualised compute resources from the set of '
                    'capabilities offered by the compute node acceleration '
                    'resources. The cardinality can be 0, if no particular '
                    'acceleration capability is provided.')


class VirtualInterfaceData(Schema):
    """VirtualInterfaceData.

    A virtual interface represents the data of a virtual network interface
    specific to a Virtual Compute Resource instance.
    """

    ipAddress = fields.Str(
        required=True,
        description='The virtual network interface can be configured with '
                    'specific IP address(es) associated to the network to be '
                    'attached to. The cardinality can be 0 in the case that a '
                    'network interface is created without being attached to '
                    'any specific network, or when an IP address can be '
                    'automatically configured, e.g. by DHCP.')
    macAddress = fields.Str(
        required=True,
        description='The MAC address desired for the virtual network '
                    'interface. The cardinality can be 0 to allow for network '
                    'interface without specific MAC address configuration.')

    networkId = fields.Str(
        required=True,
        description='Field not included in the IFA005 norm and added by BCOM '
                    'This field is needed by openstack to create the interface on the '
                    'specific subnet.')

    @validates('ipAddress')
    def validate_ipAddress(self, value):
        try:
            IPAddress(value)
        except Exception as e:
            raise ValidationError(e)

    @validates('macAddress')
    def validate_macAddress(self, value):
        try:
            EUI(value)
        except Exception as e:
            raise ValidationError(e)
