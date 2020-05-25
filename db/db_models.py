# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Author: Luca Vettori

from sqlalchemy import Column, Integer, Text, Boolean, PickleType
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy_repr import RepresentableBase
from sqlalchemy_utils import ScalarListType

Base = declarative_base(cls=RepresentableBase)


class Dbdomainlist(Base):
    """
    Db_domain_list DB Connector
    """
    __tablename__ = "domainlist"

    id = Column(Integer, primary_key=True)
    name = Column(Text)
    domain_id = Column(Text)
    type = Column(Text)
    account_type = Column(Text)
    ip = Column(Text)
    port = Column(Text)
    url = Column(Text)
    username = Column(Text)
    userpassword = Column(Text)
    tenantName = Column(Text)
    monitoringEndpoint = Column(Text)

    # def __repr__(self):
    #     return '<DomainList %r>' % self.name


class Dbnfvipops(Base):
    """
    Db_nfvi_pops DB Connector
    """
    __tablename__ = "nfvipops"

    id = Column(Integer, primary_key=True)
    geographicalLocationInfo = Column(Text)
    nfviPopId = Column(Text)
    vimId = Column(Text)
    federated = Column(Boolean)
    federatedVimId = Column(Text)
    networkCE = Column(ScalarListType())

    # def __repr__(self):
    #     return '<NFVI Pop %r>' % self.nfviPopId


class Dbllinternfvipops(Base):
    """
    Db_ll_inter_nfvi_pops DB connector
    """
    __tablename__ = "llinternfvipops"

    id = Column(Integer, primary_key=True)
    logicalLinkId = Column(Text)
    totalBandwidth = Column(Integer)
    availableBandwidth = Column(Integer)
    linkCostValue = Column(Integer)
    linkCost = Column(Text)
    linkDelayValue = Column(Integer)
    linkDelay = Column(Text)
    srcGwIpAddress = Column(Text)
    localLinkId = Column(Integer)
    dstGwIpAddress = Column(Text)
    remoteLinkId = Column(Integer)
    networkLayer = Column(Text)
    interNfviPopNetworkType = Column(Text)
    interNfviPopNetworkTopology = Column(Text)
    pathLL = Column(Text)
    # pathLL = Column(InlineResponse200)

    def __repr__(self):
        return '<Logical Links Inter Nfvi Pops %r>' % self.logicalLinkId


class Dbresourceattributes(Base):
    """
    Db_resource_attributes DB connector
    """
    __tablename__ = "resourceattributes"

    id = Column(Integer, primary_key=True)
    nfviPopId = Column(Text)
    zoneId = Column(Text)
    zoneName = Column(Text)
    zoneState = Column(Text)
    zoneProperty = Column(Text)
    metadata_resourceattributes = Column(Text)
    availableMemory = Column(Text)
    reservedMemory = Column(Text)
    allocatedMemory = Column(Text)
    totalMemory = Column(Text)
    availableCpu = Column(Text)
    reservedCpu = Column(Text)
    allocatedCpu = Column(Text)
    totalCpu = Column(Text)
    availableStorage = Column(Text)
    reservedStorage = Column(Text)
    allocatedStorage = Column(Text)
    totalStorage = Column(Text)

    # def __repr__(self):
    #     return '<Resource Attributes %r>' % self.zoneId


class Dbinternfvipopconnectivity(Base):
    """
    Db_inter_nfvi_pop_connectivity DB connector
    """
    __tablename__ = "internfvipopconnectivity"

    id = Column(Integer, primary_key=True)
    interNfviPopConnectivityId = Column(Text)
    # callIdInvolved = Column(ScalarListType())
    callIdInvolved = Column(PickleType)
    logicalLinkId = Column(Text)
    srcGwIpAddress = Column(Text)
    localLinkId = Column(Integer)
    dstGwIpAddress = Column(Text)
    remoteLinkId = Column(Integer)
    reqBandwidth = Column(Integer)
    reqLatency = Column(Integer)
    networkLayer = Column(Text)
    interNfviPopNetworkType = Column(Text)
    metadata_interconnectivity = Column(Text)
    interNfviPopNetworkSegmentType = Column(Text)
    serviceId = Column(Text)
    wimInvolved = Column(ScalarListType())
    pathCall = Column(Text)

    # def __repr__(self):
    #     return '<InterNfvi Pop Connectivity %r>' % self.interNfviPopConnectivityId


# DB for users
class Dbuser(Base):
    """
    User DB connector
    """
    __tablename__ = "users"

    id = Column(Integer, primary_key=True)
    username = Column(Text)
    password = Column(Text)
    role = Column(Text)

    # def __repr__(self):
    #     return '<User %r>' % self.username


# DB for stitching
class Dbstitching(Base):
    """
    Stitching DB Connector
    """
    __tablename__ = "stitching"

    id = Column(Integer, primary_key=True)
    ingressElementId = Column(Text)
    ingressElementAddress = Column(Text)
    aLinkId = Column(Text)
    egressElementId = Column(Text)
    egressElementAddress = Column(Text)
    zLinkId = Column(Text)
    linkCost = Column(Text)
    linkDelay = Column(Text)
    linkAvailBw = Column(Text)

    # def __repr__(self):
    #     return '<Stitching %r>' % self.id


class Dbvirtuallinks(Base):
    """
    Virtual links DB Connector
    """
    __tablename__ = "virtuallinks"

    id = Column(Integer, primary_key=True)
    vlName = Column(Text)
    serviceId = Column(Text)
    # TODO #1 choose one of the two following columns (vimId or nfviPopId)
    vimId = Column(Text)
    nfviPopId = Column(Text)
    #
    floatingIp = Column(Boolean)
    networkId = Column(Text)
    routerId = Column(Text)
    subnetId = Column(Text)
    cidr = Column(Integer)
    vlanId = Column(Integer)
    pool = Column(Integer)


def get_class_by_tablename(tablename):
    """Return class reference mapped to table.

    :param tablename: String with name of table.
    :return: Class reference or None.
    """
    for c in Base._decl_class_registry.values():
        if hasattr(c, '__tablename__') and c.__tablename__ == tablename:
            return c
