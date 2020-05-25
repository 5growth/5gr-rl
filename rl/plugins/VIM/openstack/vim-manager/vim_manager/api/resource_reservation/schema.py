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

from vim_manager.api.compute.schema import VirtualComputeFlavour
from vim_manager.api.compute.schema import VirtualCpu
from vim_manager.api.compute.schema import VirtualMemory
from vim_manager.api.compute.schema import VirtualNetworkInterface

from vim_manager.api.schema import KeyValuePair
from vim_manager.api.schema import VirtualStorage

# Compute Pool Reservation


class VirtualComputeAttributesReservationData(Schema):
    accelerationCapability = fields.Str(
        required=True,
        many=True,
        description='Selected acceleration capabilities (e.g. crypto, GPU) '
                    'from the set of capabilities offered by the compute node '
                    'acceleration resources. The cardinality can be 0, if no '
                    'particular acceleration capability is requested.')
    cpuArchitecture = fields.Str(
        required=True,
        description='CPU architecture type. Examples are "x86", "ARM". The '
                    'cardinality can be 0, if no particular CPU architecture '
                    'type is requested.')
    virtualCpuOversubscriptionPolicy = fields.Str(
        required=True,
        description='The CPU core oversubscription policy in terms of virtual '
                    'CPU cores to physical CPU cores/threads on the platform. '
                    'The cardinality can be 0, if no particular value is '
                    'requested.')


class ComputePoolReservation(Schema):
    numCpuCores = fields.Int(
        required=True,
        description='Number of CPU cores to be reserved.')
    numVcInstances = fields.Int(
        required=True,
        description='Number of virtualised container instances to be '
                    'reserved.')
    virtualMemSize = fields.Number(
        required=True,
        description='Size of virtual memory to be reserved.')
    computeAttributes = fields.Nested(
        VirtualComputeAttributesReservationData,
        required=True,
        description='Information specifying additional attributes of the '
                    'compute resource to be reserved.')


class ReservedVirtualComputeAttributes(Schema):
    accelerationCapability = fields.Str(
        required=True,
        many=True,
        description='Selected acceleration capabilities (e.g. crypto, GPU) '
                    'from the set of capabilities offered by the compute node '
                    'acceleration resources. The cardinality can be 0, if no '
                    'particular acceleration capability is provided.')
    cpuArchitecture = fields.Str(
        required=True,
        description='CPU architecture type. Examples are "x86", "ARM". The '
                    'cardinality can be 0, if no particular CPU architecture '
                    'type is provided.')
    virtualCpuOversubscriptionPolicy = fields.Str(
        required=True,
        description='The CPU core oversubscription policy in terms of virtual '
                    'CPU cores to physical CPU cores/threads on the platform. '
                    'The cardinality can be 0, if no particular value is '
                    'provided.')


class ReservedComputePool(Schema):
    numCpuCores = fields.Int(
        required=True,
        description='Number of CPU cores that have been reserved.')
    numVcInstances = fields.Int(
        required=True,
        description='Number of virtual container instances that have been '
                    'reserved.')
    virtualMemSize = fields.Number(
        required=True,
        description='Size of virtual memory that has been reserved.')
    computeAttributes = fields.Nested(
        ReservedVirtualComputeAttributes,
        required=True,
        description='Information specifying additional attributes of the '
                    'virtual compute resource that have been reserved.')
    zoneId = fields.Str(
        required=True,
        description='References the resource zone where the virtual compute '
                    'resources have been reserved. Cardinality can be 0 to '
                    'cover the case where reserved compute resources are not '
                    'bound to a specific resource zone.')


# Virtualisation Container Reservation

class VirtualisationContainerReservation(Schema):
    containerId = fields.Str(
        required=True,
        description='The identifier of the virtualisation container to be '
                    'reserved.')
    containerFlavour = fields.Nested(
        VirtualComputeFlavour,
        required=True,
        description='The containerFlavour encapsulates information of the '
                    'virtualisation container to be reserved.')


class ReservedVirtualisationContainer(Schema):
    containerId = fields.Str(
        required=True,
        description='The identifier of the virtualisation container that has '
                    'been reserved.')
    flavourId = fields.Nested(
        ReservedComputePool,
        many=True,
        required=True,
        description='Identifier of the given compute flavour used to reserve '
                    'the virtualisation container.')
    accelerationCapability = fields.Str(
        required=True,
        description='Selected acceleration capabilities (e.g. crypto, GPU) '
                    'from the set of capabilities offered by the compute node '
                    'acceleration resources. The cardinality can be 0, if no '
                    'particular acceleration capability is provided.')
    virtualMemory = fields.Nested(
        VirtualMemory,
        required=True,
        description='The virtual memory of the reserved virtualisation '
                    'container.')
    virtualCpu = fields.Nested(
        VirtualCpu,
        required=True,
        description='The virtual CPU(s) of the reserved virtualisation '
                    'container.')
    virtualDisks = fields.Nested(
        VirtualStorage,
        many=True,
        required=True,
        description='Element with information of the virtualised storage '
                    'resources attached to the reserved virtualisation '
                    'container.')
    virtualNetworkInterface = fields.Nested(
        VirtualNetworkInterface,
        many=True,
        required=True,
        description='Element with information of the virtual network '
                    'interfaces of the reserved virtualisation container.')
    zoneId = fields.Str(
        required=True,
        description='References the resource zone where the virtualisation '
                    'container has been reserved. Cardinality can be 0 to '
                    'cover the case where reserved network resources are not '
                    'bound to a specific resource zone.')


class ReservedVirtualCompute(Schema):
    reservationId = fields.Str(
        required=True,
        description='Identifier of the resource reservation.')
    computePoolReserved = fields.Nested(
        ReservedComputePool,
        required=True,
        description='Information about compute resources that have been '
                    'reserved, e.g. {"cpu_cores": 90, "vm_instances": 10, '
                    '"ram": 10000}')
    virtualisationContainerReserved = fields.Nested(
        ReservedVirtualisationContainer,
        required=True,
        description='Information about the virtualisation container(s) that '
                    'have been reserved.')
    reservationStatus = fields.Str(
        required=True,
        description='Status of the compute resource reservation, e.g. to '
                    'indicate if a reservation is being used.')
    startTime = fields.DateTime(
        required=True,
        description='Indication when the consumption of the resources starts. '
                    'If the value is 0, resources are reserved for immediate '
                    'use.')
    endTime = fields.DateTime(
        required=True,
        description='Indication when the reservation ends (when it is '
                    'expected that the resources will no longer be needed) '
                    'and used by the VIM to schedule the reservation. If not '
                    'present, resources are reserved for unlimited usage '
                    'time.')
    expiryTime = fields.DateTime(
        required=True,
        description='Indication when the VIM can release the reservation in '
                    'case no allocation request against this reservation was '
                    'made.')


# Network Reservation


class VirtualNetworkAttributesReservationData(Schema):
    bandwidth = fields.Number(
        required=True,
        description='Minimum network bitrate (in Mbps).')
    networkType = fields.Str(
        required=True,
        description='The type of network that maps to the virtualised network '
                    'to be reserved. Examples are: "local", "vlan", "vxlan", '
                    '"gre", etc.')
    segmentType = fields.Str(
        required=True,
        description='The isolated segment for the virtualised network to be '
                    'reserved. For instance, for a "vlan" networkType, it '
                    'corresponds to the vlan identifier; and for a "gre" '
                    'networkType, this corresponds to a gre key.')
    isShared = fields.Bool(
        required=True,
        description='It defines whether the virtualised network to be '
                    'reserved is shared among consumers.')
    metadata = fields.Nested(
        KeyValuePair,
        many=True,
        description='List of metadata key-value pairs used by the consumer to '
                    'associate meaningful metadata to the related virtualised '
                    'resource.')


class VirtualNetworkPortReservationData(Schema):
    portId = fields.Int(
        required=True,
        description='Identifier of the network port to reserve.')
    portType = fields.Str(
        required=True,
        description='Type of network port. Examples of types are access '
                    'ports, or trunk ports (layer 1) that become transport '
                    'for multiple layer 2 or layer 3 networks.')
    segmentId = fields.Str(
        required=True,
        description='The isolated segment the network port belongs to. For '
                    'instance, for a "vlan", it corresponds to the vlan '
                    'identifier; and for a "gre", this corresponds to a gre '
                    'key. The cardinality can be 0 to allow for flat networks '
                    'without any specific segmentation.')
    bandwidth = fields.Number(
        required=True,
        description='The bitrate of the virtual network port (in Mbps).')
    metadata = fields.Nested(
        KeyValuePair,
        many=True,
        description='List of metadata key-value pairs used by the consumer to '
                    'associate meaningful metadata to the related virtualised '
                    'resource.')


class VirtualNetworkReservation(Schema):
    numPublicIps = fields.Int(
        required=True,
        description='Number of public IP addresses to be reserved.')
    networkAttributes = fields.Nested(
        VirtualNetworkAttributesReservationData,
        required=True,
        description='Information specifying additional attributes of the '
                    'network resource to be reserved.')
    networkPorts = fields.Nested(
        VirtualNetworkPortReservationData,
        many=True,
        required=True,
        description='List of specific network ports to be reserved.')


class ReservedVirtualNetworkAttributes(Schema):
    bandwidth = fields.Number(
        required=True,
        description='Minimum network bitrate (in Mbps).')
    networkType = fields.Str(
        required=True,
        description='The type of network that maps to the virtualised network '
                    'that has been reserved. Examples are: "local", "vlan", '
                    '"vxlan", "gre", etc.')
    segmentType = fields.Str(
        required=True,
        description='The isolated segment for the virtualised network that '
                    'has been reserved. For instance, for a "vlan" '
                    'networkType, it corresponds to the vlan identifier; and '
                    'for a "gre" networkType, this corresponds to a gre key.')
    isShared = fields.Bool(
        required=True,
        description='It defines whether the virtualised network that has been '
                    'reserved is shared among consumers.')
    metadata = fields.Nested(
        KeyValuePair,
        many=True,
        description='List of metadata key-value pairs used by the consumer to '
                    'associate meaningful metadata to the related virtualised '
                    'resource.')


class ReservedVirtualNetworkPort(Schema):
    portId = fields.Str(
        required=True,
        description='Identifier of the network port that has been reserved.')
    portType = fields.Str(
        required=True,
        description='Type of network port. Examples of types are access '
                    'ports, or trunk ports (layer 1) that become transport '
                    'for multiple layer 2 or layer 3 networks.')
    segmentId = fields.Str(
        required=True,
        description='The isolated segment the network port belongs to. For '
                    'instance, for a "vlan", it corresponds to the vlan '
                    'identifier; and for a "gre", this corresponds to a gre '
                    'key. The cardinality can be 0 to allow for flat networks '
                    'without any specific segmentation.')
    bandwidth = fields.Number(
        required=True,
        description='The bitrate of the virtual network port (in Mbps)')
    metadata = fields.Nested(
        KeyValuePair,
        many=True,
        description='List of metadata key-value pairs used by the consumer to '
                    'associate meaningful metadata to the related virtualised '
                    'resource.')


class ReservedVirtualNetwork(Schema):
    reservationId = fields.Str(
        required=True,
        description='Identifier of the resource reservation.')
    publicIps = fields.Str(
        many=True,
        required=True,
        description='List of public IP addresses that have been reserved.')
    networkAttributes = fields.Nested(
        ReservedVirtualNetworkAttributes,
        required=True,
        description='Information specifying additional attributes of the '
                    'network resource that has been reserved.')
    networkPorts = fields.Nested(
        ReservedVirtualNetworkPort,
        many=True,
        required=True,
        description='List of specific network ports that have been reserved.')
    reservationStatus = fields.Str(
        required=True,
        description='Status of the network resource reservation, e.g. to '
                    'indicate if a reservation is being used.')
    startTime = fields.DateTime(
        required=True,
        description='Indication when the consumption of the resources starts. '
                    'If the value is 0, resources are reserved for immediate '
                    'use.')
    endTime = fields.DateTime(
        required=True,
        description='Indication when the reservation ends (when it is '
                    'expected that the resources will no longer be needed) '
                    'and used by the VIM to schedule the reservation. If not '
                    'present, resources are reserved for unlimited usage '
                    'time.')
    expiryTime = fields.DateTime(
        required=True,
        description='Indication when the VIM can release the reservation in '
                    'case no allocation request against this reservation was '
                    'made.')
    zoneId = fields.Str(
        required=True,
        description='References the resource zone where the virtual network '
                    'resources have been reserved. Cardinality can be 0 to '
                    'cover the case where reserved network resources are not '
                    'bound to a specific resource zone.')
