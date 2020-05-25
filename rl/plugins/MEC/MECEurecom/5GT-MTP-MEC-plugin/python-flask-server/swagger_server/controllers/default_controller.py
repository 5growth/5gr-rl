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

import connexion
import six
import datetime
import uuid
import requests
import sys
import json
import time
from flask import abort, make_response, Response, jsonify
from swagger_server.models.inline_response200 import InlineResponse200  # noqa: E501
from swagger_server.models.mec_traffic_service_creation_request import MECTrafficServiceCreationRequest # noqa: E501
from swagger_server.models.mec_traffic_service_creation_response import MECTrafficServiceCreationResponse  # noqa: E501
from swagger_server.models.location_info import LocationInfo as MECLocationInfo  # noqa: E501
from swagger_server.models.mec_region_info import MECRegionInfo  # noqa: E501
from swagger_server.models.mec_region_info_mec_region_info import MECRegionInfoMECRegionInfo as MRI  #
from swagger_server import util
from swagger_server.controllers.mecdb import *

configuration = {
  "db_host": "127.0.0.1",
  "db_port": 27017,
  "db_name": "mtpmec"
}
md = MECDB(configuration)

def service_regions_get():  # noqa: E501
    """Retrieve a list of all covered regions.

    Retrieve a list of MEC regions, reporting their identifiers and location information. # noqa: E501


    :rtype: InlineResponse200
    """

    regions = md.get_regions()
    ret = [] 
    for r in regions:
      li = LocationInfo(r.location.latitude, r.location.longitude, r.location.altitude, r.location.range)
      mri = MECRegionInfo(MRI(r.id, li.serialize()))
      ret.append(mri)
    return make_response(jsonify(InlineResponse200(ret)), 200)


def service_regions_region_id_get(RegionId):  # noqa: E501
    """Retrieve a list of MEC service requests for the given region.

    Retrieve a list of MEC service requests for the given region. # noqa: E501

    :param RegionId: Region identifier.
    :type RegionId: str

    :rtype: List[MECTrafficServiceCreationRequest]
    """
    req_list = md.get_services_for_region(RegionId)
    retval = []
    for r in req_list:
      retval.append(r.serialize())
    return retval


def service_requests_get():  # noqa: E501
    """Retrieve a list of MEC service requests.

    Retrieve a list of MEC service requests. # noqa: E501


    :rtype: List[MECTrafficServiceCreationRequest]
    """
    req_list = md.get_services()
    retval = []
    for r in req_list:
      retval.append(r.serialize())
    return retval



def service_requests_post(mecTrafficServiceCreationRequest):  # noqa: E501
    """Create MEC service rules.

    Create MEC service rules. # noqa: E501

    :param mecTrafficServiceCreationRequest: Information about the MEC app, including required services, traffic rules, etc.
    :type mecTrafficServiceCreationRequest: dict | bytes

    :rtype: MECTrafficServiceCreationResponse
    """
    if connexion.request.is_json:
      req = MECTrafficServiceCreationRequest.from_dict(connexion.request.get_json()).to_dict()  # noqa: E501
    else:
      req = mecTrafficServiceCreationRequest

    region = md.get_region(req["region_id"])
    
    # create a service request id
    service_id = str(uuid.uuid4())
     
    # get creation timestamp
    timestamp = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")
     
    # create service object
    required = None
    optional = None
    produced = None
    traffic_rule = None
    dns_rule = None
    if "app_service_required" in req:
      required = req["app_service_required"]
    if "app_service_optional" in req:
      optional = req["app_service_optional"]
    if "app_service_produced" in req:
      produced = req["app_service_produced"]
    if "app_dns_rule" in req:
      dns_rule = req["app_dns_rule"]
    if "app_traffic_rule" in req:
      traffic_rule = req["app_traffic_rule"]    
    
    service = ServiceRequest(
      id = service_id, 
      region_id = region.id, 
      creation_timestamp = timestamp, 
      last_modified = None, 
      service_required = required,
      service_produced = produced,
      service_optional = optional,
      dns_rule = dns_rule, 
      traffic_rule = traffic_rule, 
      state = "CREATED")

    # record request in database
    md.insert_service(service)
    
    # check for DNS rules
    if dns_rule is not None:
      for rule in dns_rule:
        domainName = rule["domain_name"]
        ipAddress = rule["ip_address"]
        # communicate w. MEC platform and add rule
        body = {
          "url": domainName,
          "mec-ipaddr": ipAddress,
          "mec-macaddr": "00:00:00:00:00:00",
          "type": 1
        }
        url = region.mepinfo["endpoint_url"] + "/api/mp1/traffic/all"
        
        try:
          resp = requests.post(url, json=body)
        except:
          md.update_service_state(service_id, "FAILED")
          abort(400, "Applying DNS rule on the MEC platform failed.")      
        if resp.status_code != 200:
          md.update_service_state(service_id, "FAILED")
          abort(400, "Applying DNS rule on the MEC platform failed.")

    # check for traffic rules
    if traffic_rule is not None:
      for rule in traffic_rule:
        if "traffic_filter" in rule:
          if rule["traffic_filter"][0]["protocol"][0] == "tcp":
            protocol = 1
          elif rule["traffic_filter"][0]["protocol"][0] == "udp":
            protocol = 2
          else:
            protocol = 0 # any
          dstaddr = rule["traffic_filter"][0]["dst_address"][0] # this is the IP address of the service
          dstport = rule["traffic_filter"][0]["dst_port"][0] # destination port for the service
          imsi_list = []
          # for the moment, we abuse this field: instead of source addresses we put IMSI number
          for i in rule["traffic_filter"][0]["src_address"]: 
            imsi_list.append(i)
      
          # the MEC instance IP address needs to be present in the dst_interface field
          # dst_interface_type should normally be "IP", but these fields are anyway ignored.
          # only the dst_ip_address should be present and is considered
          if "dst_interface" not in rule or "dst_ip_address" not in rule["dst_interface"][0]:
            md.update_service_state(service_id, "FAILED")
            abort(400, "Applying traffic rule on the MEC platform failed: Missing dst_interface information.")
          mec_ip_addr = rule["dst_interface"][0]["dst_ip_address"]
          
          if "dst_mac_address" not in rule["dst_interface"][0]:
            mec_mac = "00:00:00:00:00:00"
          else:
            mec_mac = rule["dst_interface"][0]["dst_mac_address"]

          # apply traffic redirection rule
          body = {
            "ue_list": imsi_list,
            "service_ip": dstaddr,
            "port": dstport,
            "transport": protocol,
            "mec_ipaddr": mec_ip_addr,
            "mec_macaddr": mec_mac,
            "type": 2   
          }
          url = region.mepinfo["endpoint_url"] + "/api/mp1/traffic/imsi"
          resp = requests.post(url, json=body)

          if resp.status_code != 200:
            md.update_service_state(service_id, "FAILED")
            abort(400, "Applying traffic rule on the MEC platform failed.")

    # if we've reached this point, the MEC service request has been handled.
    md.update_service_state(service_id, "CREATED")

    # create response
    resp = MECTrafficServiceCreationResponse(service_id)    
    return make_response(jsonify(resp), 201)


def service_requests_request_id_delete(RequestId):  # noqa: E501
    """Delete service.

    Delete service identified by the given request ID. # noqa: E501

    :param RequestId: Request identifier.
    :type RequestId: str

    :rtype: None
    """

    # retrieve service information
    srv = md.get_service(RequestId)

    if srv is None:
      abort(404, "Service request not found.")

    if srv.state == "FAILED":
      # nothing to do, just remove record from database
      md.delete_service(RequestId)
      return make_response("Service deleted", 201)
    
    region = md.get_region(srv.region_id)
    if region is None:
      abort(404, "Service region not found.")

    # check for DNS rules and remove them
    if srv.dns_rule is not None:
      for rule in srv.dns_rule:
        domainName = rule["domain_name"]
        ipAddress = rule["ip_address"]
        # Create delete request body
        body = {
          "url": domainName,
          "mec-ipaddr": ipAddress,
          "mec-macaddr": "00:00:00:00:00:00",
          "type": 1
        }

        # request the MEP to remove redirection rule
        warning = ""
        url = region.mepinfo["endpoint_url"] + "/api/mp1/traffic/all"
        try:
          resp = requests.delete(url, json=body)
          if resp.status_code != 200:
            # If something went wrong with communicating with the MEP we
            # just warn the caller (but still return a "success" http code)
            warning += "Warning: Removing DNS rule on the MEC platform failed.\n"
        except:
          warning = "Warning: Could not communicate with the MEP\n"


    # check for traffic rules and remove them
    if srv.traffic_rule is not None:    
      for rule in srv.traffic_rule:
        if "traffic_filter" in rule:
          if rule["traffic_filter"][0]["protocol"][0] == "tcp":
            protocol = 1
          elif rule["traffic_filter"][0]["protocol"][0] == "udp":
            protocol = 2
          else:
            protocol = 0 # any
          dstaddr = rule["traffic_filter"][0]["dst_address"][0] # this is the IP address of the service
          dstport = rule["traffic_filter"][0]["dst_port"][0] # destination port for the service
          imsi_list = []
          # as before, we abuse this field: instead of source addresses we put IMSI number
          for i in rule["traffic_filter"][0]["src_address"]: 
            imsi_list.append(i)
      
          # the MEC instance IP address is present in the dst_interface field
          # we don't have to check again that the field is there since this is carried out
          # at creation time and if it fails, the service request is not processed and the service
          # is marked as FAILED.
          mec_ip_addr = rule["dst_interface"][0]["dst_ip_address"]
          mec_mac = rule["dst_interface"][0]["dst_mac_address"]
          # remove traffic redirection rule
          body = {
            "ue_list": imsi_list,
            "service_ip": dstaddr,
            "port": dstport,
            "transport": protocol,
            "mec_ipaddr": mec_ip_addr,
            "mec_macaddr": mec_mac,
            "type": 2   
          }
          url = region.mepinfo["endpoint_url"] + "/api/mp1/traffic/imsi"
          try:
            resp = requests.delete(url, json=body)
            if resp.status_code != 200:
              # Add a warning
              warning += "Warning: Could not communicate with the MEP.\n"
          except:
            warning += "Warning: Removing DNS rule on the MEC platform failed.\n"

    # remove from database
    md.delete_service(RequestId)
    
    if warning:
      return make_response(warning, 201)
    else:
      return make_response("Service deleted successfully.", 201)


def service_requests_request_id_get(RequestId):  # noqa: E501
    """Retrieve information about a MEC service request.

    Retrieve information about a MEC service request. # noqa: E501

    :param RequestId: Request identifier.
    :type RequestId: str

    :rtype: MECTrafficServiceCreationRequest
    """
    srv = md.get_service(RequestId)
    if srv is None:
      abort(404, "Service request not found.")
    else:
      return srv.serialize()

