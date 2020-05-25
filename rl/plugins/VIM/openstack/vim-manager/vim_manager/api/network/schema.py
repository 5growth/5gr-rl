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

from netaddr import IPAddress

from vim_manager.api.schema import KeyValuePair


class NetworkQoS(Schema):
    qosName = fields.Str(
        required=True,
        description='Name given to the QoS parameter.')
    qosValue = fields.Str(
        required=True,
        description='Value of the QoS parameter.')


class NetworkSubnetData(Schema):
    networkId = fields.Str(
        required=True,
        description='The identifier of the virtualised network that the '
                    'virtualised sub-network is attached to. The cardinality '
                    'can be 0 to cover the case where this type is used to '
                    'describe the L3 attributes of a network rather than a '
                    'subnetwork or when NetworkSubnetData is part of Update '
                    'Virtualised Network Resource.')
    ipVersion = fields.Str(
        required=True,
        description='The IP version of the network/subnetwork. Cardinality '
                    'can be 0 when NetworkSubnetData is part of Update '
                    'Virtualised Network Resource.')
    gatewayIp = fields.Str(
        IPAddress,
        required=True,
        description='Specifies the IP address of the network/subnetwork '
                    'gateway when the gateway is selected by the requestor.')
    cidr = fields.Str(
        required=True,
        description='The CIDR of the network/subnetwork, i.e. network address '
                    'and subnet mask. Cardinality can be 0 when '
                    'NetworkSubnetData is part of Update Virtualised Network '
                    'Resource.')
    isDhcpEnabled = fields.Bool(
        required=True,
        description='True when DHCP is to be enabled for this '
                    'network/subnetwork, or false otherwise.')
    addressPool = fields.Str(
        required=True,
        many=True,
        description='Address pools for the network/subnetwork. The '
                    'cardinality can be 0 when VIM is allowed to allocate all'
                    'addresses in the CIDR except for the address of the '
                    'network/subnetwork gateway.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class VirtualNetworkPortData(Schema):
    portType = fields.Str(
        required=True,
        description='Type of network port. Examples of types are access ports '
                    '(layer 2 or 3), or trunk ports (layer 1) that become '
                    'transport for multiple layer 2 or layer 3 networks.')
    networkId = fields.Str(
        required=True,
        description='Identifier of the network that the port belongs to. When '
                    'creating a port, such port needs to be part of a '
                    'network. Cardinality can be 0 when '
                    'VirtualNetworkPortData is part of Update Virtualised '
                    'Network Resource')
    segmentId = fields.Str(
        required=True,
        description='The isolated segment the network port belongs to. For '
                    'instance, for a "vlan", it corresponds to the vlan '
                    'identifier; and for a "gre", this corresponds to a gre '
                    'key. The cardinality can be "0" to allow for flat '
                    'networks without any specific segmentation.')
    bandwidth = fields.Number(
        required=True,
        description='The bandwidth of the virtual network port (in Mbps). '
                    'Cardinality can be "0" to allow for virtual network '
                    'ports without any specified bandwidth requirements.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class VirtualNetworkData(Schema):
    bandwidth = fields.Number(
        required=True,
        description='Minimum network bandwidth (in Mbps).')
    networkType = fields.Str(
        required=True,
        description='The type of network that maps to the virtualised '
                    'network. This list is extensible. Examples are: "local", '
                    '"vlan", "vxlan", "gre", "l3-vpn", etc. The cardinality '
                    'can be "0" to cover the case where this attribute is not '
                    'required to create the virtualised network.')
    segmentType = fields.Str(
        required=True,
        description='The isolated segment for the virtualised network. For '
                    'instance, for a "vlan" networkType, it corresponds to '
                    'the vlan identifier; and for a "gre" networkType, this '
                    'corresponds to a gre key. The cardinality can be "0" to '
                    'allow for flat networks without any specific '
                    'segmentation.')
    networkQoS = fields.Nested(
        NetworkQoS,
        many=True,
        required=True,
        description='Element providing information about Quality of Service '
                    'attributes that the network shall support. The '
                    'cardinality can be "0" to allow for networks without any '
                    'specified QoS requirements.')
    isShared = fields.Bool(
        required=True,
        description='It defines whether the virtualised network is shared '
                    'among consumers.')
    sharingCriteria = fields.Str(
        required=True,
        description='Only present for shared networks. Indicate the sharing '
                    'criteria for this network. This criteria might be a list '
                    'of authorized consumers.')
    layer3Attributes = fields.Nested(
        NetworkSubnetData,
        required=True,
        many=True,
        description='The attribute allows setting up a network providing '
                    'defined layer 3 connectivity. See clause 8.4.4.4 for '
                    'further information on the attributes required for layer '
                    '3 connectivity.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class VirtualNetworkPort(Schema):
    resourceId = fields.Str(
        required=True,
        description='Identifier of the virtual network port.')
    networkId = fields.Str(
        required=True,
        description='Identifier of the network that the port belongs to. When '
                    'creating a port, such port needs to be part of a '
                    'network.')
    attachedResourceId = fields.Str(
        required=True,
        description='Identifier of the attached resource to the network port '
                    '(e.g. a virtualised compute resource, or identifier of '
                    'the virtual network interface). The cardinality can be '
                    '"0" if there is no specific resource connected to the '
                    'network port.')
    portType = fields.Str(
        required=True,
        description='Type of network port. Examples of types are access ports '
                    '(layer 2 or 3), or trunk ports (layer 1) that become '
                    'transport for multiple layer 2 or layer 3 networks.')
    segmentId = fields.Str(
        required=True,
        description='The isolated segment the network port belongs to. For '
                    'instance, for a "vlan", it corresponds to the vlan '
                    'identifier; and for a "gre", this corresponds to a gre '
                    'key. The cardinality can be "0" for flat networks '
                    'without any specific segmentation.')
    bandwidth = fields.Number(
        required=True,
        description='The bandwidth of the virtual network port (in Mbps). '
                    'Cardinality can be "0" for virtual network ports without '
                    'any specific allocated bandwidth.')
    operationalState = fields.Str(
        required=True,
        description='The operational state of the virtual network port.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class VirtualNetwork(Schema):
    networkResourceId = fields.String(
        required=True,
        description='Identifier of the virtualised network resource.')
    networkResourceName = fields.String(
        required=True,
        description='Name of the virtualised network resource.')
    subnet = fields.String(
        required=True,
        many=True,
        description='Only present if the network provides layer 3 '
                    'connectivity.')
    networkPort = fields.Nested(
        VirtualNetworkPort,
        required=True,
        many=True,
        description='Element providing information of an instantiated virtual '
                    'network port.')
    bandwidth = fields.Number(
        required=True,
        description='Minimum network bandwidth (in Mbps).')
    networkType = fields.Str(
        required=True,
        description='The type of network that maps to the virtualised '
                    'network. This list is extensible. Examples are: "local", '
                    '"vlan", "vxlan", "gre", "l3-vpn", etc. The cardinality '
                    'can be "0" to cover the case where this attribute is not '
                    'required to create the virtualised network.')
    segmentType = fields.Str(
        required=True,
        description='The isolated segment for the virtualised network. For '
                    'instance, for a "vlan" networkType, it corresponds to '
                    'the vlan identifier; and for a "gre" networkType, this '
                    'corresponds to a gre key. The cardinality can be "0" to '
                    'allow for flat networks without any specific '
                    'segmentation.')
    networkQoS = fields.Nested(
        NetworkQoS,
        many=True,
        required=True,
        description='Element providing information about Quality of Service '
                    'attributes that the network shall support. The '
                    'cardinality can be "0" to allow for networks without any '
                    'specified QoS requirements.')
    isShared = fields.Bool(
        required=True,
        description='It defines whether the virtualised network is shared '
                    'among consumers.')
    sharingCriteria = fields.Str(
        required=True,
        description='Only present for shared networks. Indicate the sharing '
                    'criteria for this network. This criteria might be a list '
                    'of authorized consumers.')

    zoneId = fields.String(
        required=True,
        description='If present, it identifies the Resource Zone where the '
                    'virtual network resources have been allocated.')
    operationalState = fields.String(
        required=True,
        description='The operational state of the virtualised network.')

    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class NetworkSubnet(Schema):
    resourceId = fields.Str(
        required=True,
        description='Identifier of the virtualised sub-network.')
    networkId = fields.Str(
        required=True,
        description='The identifier of the virtualised network that the '
                    'virtualised sub-network is attached to. The cardinality '
                    'can be 0 to cover the case where this type is used to '
                    'describe the L3 attributes of a network rather than a '
                    'subnetwork.')
    ipVersion = fields.Str(
        required=True,
        description='The IP version of the network/subnetwork.')
    gatewayIp = fields.Str(
        IPAddress,
        required=True,
        description='The IP address of the network/subnetwork gateway.')
    cidr = fields.Str(
        required=True,
        description='The CIDR of the network/subnetwork, i.e. network address '
                    'and subnet mask.')
    isDhcpEnabled = fields.Bool(
        required=True,
        description='True when DHCP is enabled for this network/subnetwork, '
                    'or false otherwise.')
    addressPool = fields.Str(
        required=True,
        many=True,
        description='Address pools for the network/subnetwork. The '
                    'cardinality can be 0 when VIM is allowed to allocate all '
                    'addresses in the CIDR except for the address of the '
                    'network/subnetwork gateway.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        many=True,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised ressource.')


class VirtualNetworkResourceInformation(Schema):
    networkResourceTypeId = fields.Str(
        required=True,
        description='Identifier of the network resource type.')
    bandwidth = fields.Number(
        required=True,
        description='Minimum network bandwidth (in Mbps).')
    networkType = fields.Str(
        required=True,
        description='The type of network that maps to the virtualised '
                    'network. Examples are: "local", "vlan", "vxlan", "gre", '
                    'etc.')
    networkQoS = fields.Nested(
        NetworkQoS,
        many=True,
        required=True,
        description='Element providing information about Quality of Service '
                    'attributes that the network shall support.')
