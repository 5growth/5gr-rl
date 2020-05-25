# -*- coding: utf-8 -*-

"""
Copyright 2018 Pantelis Frangoudis, EURECOM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""

import pymongo
import datetime

class LocationInfo(object):
  """Represents an area characterized a circle around given coordinates.
  """
  def __init__(self, longitude=0.0, latitude=0.0, altitude=0.0, radius=0.0):
    self.longitude = longitude
    self.latitude = latitude
    self.altitude = altitude
    self.range = radius
    
  def serialize(self):
    return {
      "longitude": self.longitude,
      "latitude": self.latitude,
      "altitude": self.altitude,
      "range": self.range
    }

  @staticmethod
  def deserialize(locationinfo):
    if not locationinfo:
      return None
    else:
      return LocationInfo(
        locationinfo["longitude"],
        locationinfo["latitude"],
        locationinfo["altitude"],
        locationinfo["range"]
      )
  
class Region(object):
  """Region representation. Each region is managed by a single MEP.
  """
  def __init__(self, regionid, description, mepinfo, location=None):
    self.id = regionid
    self.description = description
    self.mepinfo = mepinfo
    self.location = location

  def serialize(self):
    return {
      "id": self.id,
      "description": self.description,
      "mepinfo": self.mepinfo.serialize(),
      "location": self.location.serialize()
    }

  @staticmethod
  def deserialize(region):
    if not region:
      return None
    else:
      location = None
      if "location" in region:
        location = LocationInfo.deserialize(region["location"])
      mepinfo = None
      if "mepinfo" in region:
        mepinfo = MEP.deserialize(region["mepinfo"])
      return Region(
        region["id"], 
        region["description"],
        region["mepinfo"],
        location
      )
    
class MEP(object):
  """Information about the MEP of a region/NFVI-PoP
  """
  def __init__(self, id, endpoint_url, info=None):
    self.id = id
    self.endpoint_url = endpoint_url
    self.info = info

  def serialize(self):
    return {
      "id": self.id,
      "endpoint_url": self.endpoint_url,
      "info": self.info
    }

  @staticmethod
  def deserialize(mep):
    if not mep:
      return None
    else:
      return MEP(
        mep["id"], 
        mep["endpoint_url"],
        mep["info"],
      )

class ServiceRequest(object):
  """Information about an application package onboarded at a specific region.
  """
  def __init__(self, id, region_id, creation_timestamp, last_modified = None, service_required = None, service_produced = None, service_optional = None, dns_rule = None, traffic_rule = None, state = "CREATED"):
    self.id = id
    self.region_id = region_id
    self.creation_timestamp = creation_timestamp
    self.last_modified = last_modified
    self.service_produced = service_produced
    self.service_required = service_required
    self.service_optional = service_optional
    self.dns_rule = dns_rule
    self.traffic_rule = traffic_rule
    self.state = state

  def serialize(self):
    return {
      "id": self.id,
      "region_id": self.region_id,
      "creation_timestamp": self.creation_timestamp,
      "last_modified": self.last_modified,
      "service_produced": self.service_produced,
      "service_required": self.service_required,
      "service_optional": self.service_optional,
      "dns_rule": self.dns_rule,
      "traffic_rule": self.traffic_rule,
      "state": self.state
    }
    
  @staticmethod
  def deserialize(srv):
    if not srv:
      return None
    else:
      return ServiceRequest(
        id = srv["id"],
        region_id = srv["region_id"],
        creation_timestamp = srv["creation_timestamp"],
        last_modified = srv["last_modified"],
        state = srv["state"],
        service_required = srv["service_required"],
        service_produced = srv["service_produced"],
        service_optional = srv["service_optional"],
        dns_rule = srv["dns_rule"],
        traffic_rule = srv["traffic_rule"]
      )

class MECDB(object):
  """MEC plugin database.
  """
  
  def __init__(self, configuration):
    self.db_client = pymongo.MongoClient(configuration["db_host"], configuration["db_port"])
    self.db = self.db_client[configuration["db_name"]]
    # create collections and indexes
    self.db_collection_regions = self.db["regions"]
    self.db_collection_regions.create_index("id", unique=True)
    self.db_collection_meps = self.db["meps"]
    self.db_collection_meps.create_index("id", unique=True)
    self.db_collection_services = self.db["services"]
    self.db_collection_services.create_index("id", unique=True)

  #################################################################
  # Region
  #################################################################

  def get_region(self, regionid):
    """Retrieve information about a region.
    """
    return Region.deserialize(self.db_collection_regions.find_one({"id": str(regionid)}, projection={"_id": False}))

  def get_regions(self):
    """List all regions.
    """
    retval = []
    regions = self.db_collection_regions.find(projection={"_id": False})
    for r in regions:
      retval.append(Region.deserialize(r))
    return retval
    
  def insert_region(self, region):
    """Insert one region in the database.
    """
    self.db_collection_regions.insert_one(region.serialize())

  def delete_region(self, id):
    """Remove the given region from the database.
    """
    self.db_collection_regions.delete_one({"id": id})

  #################################################################
  # Service requests
  #################################################################

  def get_service(self, id):
    """Retrieve information about a particular MEC service request.
    """
    return ServiceRequest.deserialize(self.db_collection_services.find_one({"id": id}, projection={"_id": False}))

  def get_services(self):
    """Get a list of all MEC service requests.
    """
    retval = []
    services = self.db_collection_services.find(projection={"_id": False})
    for s in services:
      retval.append(ServiceRequest.deserialize(s))
    return retval

  def get_services_for_region(self, region_id):
    """Get a list of all service requests of a region
    """
    retval = []
    services = self.db_collection_services.find({"region_id": region_id}, projection={"_id": False})
    for s in services:
      retval.append(ServiceRequest.deserialize(s))
    return retval
   
  def insert_service(self, service):
    """Insert service request in the database.
    """
    self.db_collection_services.insert_one(service.serialize())

  def update_service_state(self, service_id, state):
    """Update service request state
    """
    timestamp = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")
    service = self.db_collection_services.find_one_and_update(
      {"id": service_id}, 
      {'$set': {"state": state, "last_modified": timestamp}},
      projection={"_id": False},
      return_document=pymongo.collection.ReturnDocument.AFTER)
    return service

  def delete_service(self, id):
    """Remove service request from the database.
    """
    self.db_collection_services.delete_one({"id": id})
    

  #################################################################
  # MEP
  #################################################################
  
  def get_mep(self, id):
    """Retrieve information on a particular MEP.
    """
    return MEP.deserialize(self.db_collection_meps.find_one({"id": id}, projection={"_id": False}))

  def get_meps(self):
    """List all VIMs.
    """
    retval = []
    meps = self.db_collection_app_meps.find(projection={"_id": False})
    for m in meps:
      retval.append(MEP.deserialize(m))
    return retval

  def insert_mep(self, mep):
    """Insert MEP information in the database.
    """
    self.db_collection_meps.insert_one(mep.serialize())

  def delete_mep(self, id):
    """Delete a MEP from the database.
    """
    self.db_collection_meps.delete_one({"id": id})

