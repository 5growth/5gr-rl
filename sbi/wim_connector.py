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

import requests
import logging
from nbi.nbi_server import db_session
from db.db_models import Dbdomainlist


class ConnectorException(Exception):
    """Common and base class Exception for all WIM connector exceptions"""
    def __init__(self, message, http_code=requests.codes.bad_request):
        Exception.__init__(self, message)
        self.http_code = http_code


class ConnectorConnectionException(ConnectorException):
    """Connectivity error with the WIM"""
    def __init__(self, message, http_code=requests.codes.service_unavailable):
        ConnectorException.__init__(self, message, http_code)


class ConnectorUnexpectedResponse(ConnectorException):
    """Get an wrong response from WIM"""
    def __init__(self, message, http_code=requests.codes.internal_server_error):
        ConnectorException.__init__(self, message, http_code)


class ConnectorAuthException(ConnectorException):
    """Invalid credentials or authorization to perform this action over the WIM"""
    def __init__(self, message, http_code=requests.codes.unauthorized):
        ConnectorException.__init__(self, message, http_code)


class ConnectorNotFoundException(ConnectorException):
    """The item is not found at WIM"""
    def __init__(self, message, http_code=requests.codes.not_found):
        ConnectorException.__init__(self, message, http_code)


class ConnectorConflictException(ConnectorException):
    """There is a conflict, e.g. more item found than one"""
    def __init__(self, message, http_code=requests.codes.conflict):
        ConnectorException.__init__(self, message, http_code)


class ConnectorNotSupportedException(ConnectorException):
    """The request is not supported by connector"""
    def __init__(self, message, http_code=requests.codes.service_unavailable):
        ConnectorException.__init__(self, message, http_code)


class ConnectorNotImplemented(ConnectorException):
    """The method is not implemented by the connected"""
    def __init__(self, message, http_code=requests.codes.not_implemented):
        ConnectorException.__init__(self, message, http_code)


class WimConnector:
    def __init__(self):
        self.logger = logging.getLogger('cttc_rl.sbi.wim_connector')
        self.description = "Generic Wim connector. Need to be override to be used!"

    def get_access(self):
        """
        Get the ip address to connect with WIM
        """
        raise ConnectorNotImplemented("Should have implemented this")

    def get_context(self):
        """
        Query Context to a WIM
        """
        raise ConnectorNotImplemented("Should have implemented this")

    def create_call(self, callId, internal_path, inter_wan_path, edge_paths, src_ip, dst_ip, metadata_call, vlan, bw):
        """
        Create a call in WIM server
        """
        raise ConnectorNotImplemented("Should have implemented this")

    def delete_call(self, callIds):
        """
        Delete a call in WIM server
        """
        raise ConnectorNotImplemented("Should have implemented this")


def create_connector(wim_id):
    wim_properties = db_session.query(Dbdomainlist).filter_by(type="WIM", domain_id=wim_id).first()
    connector_type = wim_properties.account_type
    pkg = __import__("sbi.{}_connector".format(connector_type))
    wim_conn = getattr(pkg, "{}_connector".format(connector_type))
    wim_connector = wim_conn.Connector(wim_properties)
    return wim_connector
