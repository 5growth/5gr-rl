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

Identifier = "string"


class Filter(Schema):
    filter = fields.Dict(
        required=True,
        description='The filter is used to select elements for query purpose.')


class KeyValuePair(Schema):
    key = fields.Str(required=True)
    value = fields.Str(required=True)


class AffinityOrAntiAffinityResourceList(Schema):
    """AffinityOrAntiAffinityResourceList.

    The AffinityOrAntiAffinityResourceList information element defines an
    explicit list of resources to express affinity or anti-affinity between
    these resources and a current resource. The scope of the affinity or
    anti-affinity can also be defined.
    """

    resource = fields.List(
        fields.Str(),
        required=True,
        many=True,
        description='List of identifiers of virtualised resources.')


class AffinityOrAntiAffinityConstraint(Schema):
    type = fields.Str(
        required=True,
        description='Indicates whether this is an affinity or '
                    'anti-affinity constraint.')
    scope = fields.Str(
        required=True,
        description='Qualifies the scope of the constraint. In case of '
                    'compute resource: e.g. "NFVI-PoP" or "NFVI Node". In '
                    'case of ports: e.g. "virtual switch or router" or '
                    '"physical NIC", or "physical network" or "NFVI Node". In '
                    'case of networks: e.g. "physical NIC", "physical '
                    'network" or "NFVI Node". In case of subnets: it should '
                    'be ignored. Defaults to "NFVI Node" if absent.')
    affinityAntiAffinityResourceList = fields.Nested(
        AffinityOrAntiAffinityResourceList,
        required=True,
        description='Consumer-managed list of identifiers of virtualised '
                    'resources with which the actual resource is requested to '
                    'be affine or anti-affine. Either '
                    'affinityAntiAffinityResourceList or '
                    'affinityAntiAffinityResourceGroup but not both shall be '
                    'present.')
    affinityAntiAffinityResourceGroup = fields.Str(
        required=True,
        description='Identifier of the producer-managed group of virtualised '
                    'resources with which the actual resource is requested to '
                    'be affine or anti-affine. Either '
                    'affinityAntiAffinityResourceList or '
                    'affinityAntiAffinityResourceGroup but not both shall be '
                    'present.'
    )


class UserData(Schema):
    content = fields.Str(
        required=True,
        description='String containing the user data to customize a '
                    'virtualised compute resource at boot-time.')
    method = fields.Str(
        required=True,
        description='Method used as transportation media to convey the '
                    'content of the UserData to the virtualised compute '
                    'resource. Possible values: CONFIG-DRIVE.')


class VirtualStorage(Schema):
    """VirtualStorage.

    The VirtualStorage information element encapsulates information of
    an instantiated virtualised storage resource.
    """

    storageId = fields.Str(
        required=True,
        description='Identifier of the virtualised storage resource.')
    storageName = fields.Str(
        required=True,
        description='Name of the virtualised storage resource.')
    flavourId = fields.Str(
        required=True,
        description='Identifier of the storage flavour used to instantiate '
                    'this virtual storage.')
    typeOfStorage = fields.Str(
        required=True,
        description='Type of virtualised storage resource (e.g. volume, '
                    'object).')
    sizeOfStorage = fields.Number(
        required=True,
        description='Size of virtualised storage resource (e.g. size of '
                    'volume, in GB).')
    rdmaEnabled = fields.Bool(
        required=False,
        description='Indicates if the storage supports RDMA.')
    ownerId = fields.Str(
        required=True,
        description='Identifier of the virtualised resource that owns and '
                    'uses such a virtualised storage resource. The value can '
                    'be NULL if the virtualised storage is not attached yet '
                    'to any other resource (e.g. a virtual machine).')
    zoneId = fields.Str(
        required=True,
        description='If present, it identifies the Resource Zone where the '
                    'virtual storage resources have been allocated.')
    hostId = fields.Str(
        required=True,
        description='Identifier of the host where the virtualised storage '
                    'resource is allocated. A cardinality of 0 refers to '
                    'distributed storage solutions.')
    operationalState = fields.Str(
        required=True,
        description='Operational state of the resource.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'virtualised resource.')


class ResourceZone(Schema):
    zoneId = fields.Number(
        required=True,
        description='The identifier of the Resource Zone.')
    zoneName = fields.Str(
        required=True,
        description='The name of the Resource Zone.')
    zoneState = fields.Str(
        required=True,
        description='Information about the current state of the Resource '
                    'Zone, e.g. if the Resource Zone is available.')
    nfviPopId = fields.Number(
        required=True,
        description='The identifier of the NFVI-PoP the Resource Zone belongs '
                    'to.')
    zoneProperty = fields.Str(
        required=True,
        description='Set of properties that define the capabilities '
                    'associated to the Resource Zone. Examples of '
                    'capabilities may include: support of certain compute '
                    'resource types (e.g. low performance, acceleration '
                    'capabilities, etc. association to certain NFVI-PoP '
                    'physical segregation (e.g. different power or network '
                    'sub-systems, availability of redundancy power '
                    'sub-systems), etc.')
    metadata = fields.Nested(
        KeyValuePair,
        required=False,
        description='List of metadata key-value pairs used by the consumer '
        'to associate meaningful metadata to the related '
        'Resource Zone.')


class NfviPop(Schema):
    nfviPopId = fields.Number(
        required=True,
        description='Identification of the NFVI-PoP.')
    vimId = fields.Number(
        required=True,
        description='Identification of the VIM.')
    geographicalLocationInfo = fields.Str(
        required=True,
        description='It provides information about the geographic location '
                    '(e.g. geographic coordinates or address of the building, '
                    'etc.) of the NFVI resources that the VIM manages.')
    networkConnectivityEndpoint = fields.Str(
        required=True,
        many=True,
        description='Information about network connectivity endpoints to the '
                    'NFVI-PoP that the VIM manages which helps build topology '
                    'information relative to NFVI-PoP connectivity to other '
                    'NFVI-PoP or N-PoP.')


class CapacityInformation(Schema):
    availableCapacity = fields.Str(
        required=True,
        description='The free capacity available for allocation and '
                    'reservation. It can be specified in terms of current '
                    'capacity; or minimum and maximum capacity; average '
                    'capacity; or other statistical measurement in the '
                    'specified time interval. The set of measurements is to '
                    'be defined during Stage 3.')
    reservedCapacity = fields.Str(
        required=True,
        description='The reserved capacity. It can be specified in terms of '
                    'current capacity; or minimum and maximum capacity; '
                    'average capacity; or other statistical measurement in '
                    'the specified time interval. The set of measurements is '
                    'to be defined during Stage 3.')
    totalCapacity = fields.Str(
        required=True,
        description='The total capacity is usually specified as a fixed '
                    'capacity without variations in time. The set of '
                    'measurements is left to Stage 3.')
    allocatedCapacity = fields.Str(
        required=True,
        description='The allocated capacity is usually specified as the '
                    'current allocated capacity.')


class TimePeriodInformation(Schema):
    startTime = fields.DateTime(
        required=True,
        description='Indication when the capacity query period starts.')
    stopTime = fields.DateTime(
        required=True,
        description='Indication when the capacity query period stops.')
