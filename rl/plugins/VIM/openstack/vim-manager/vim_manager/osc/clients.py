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

import functools
import sys

from glanceclient import client as glclient
from keystoneauth1 import loading as ka_loading
from keystoneclient import client as keyclient
from keystoneclient import exceptions as keystone_exceptions
from neutronclient.v2_0 import client as netclient
from novaclient import client as nvclient

from vim_manager.exception import AuthorizationFailure

_CLIENTS_AUTH_GROUP = 'clients_auth'


def wrap_keystone_exception(func):
    """Wrap keystone exceptions and throw Watcher specific exceptions."""
    @functools.wraps(func)
    def wrapped(*args, **kw):
        try:
            return func(*args, **kw)
        except keystone_exceptions.AuthorizationFailure:
            raise AuthorizationFailure(sys.exc_info()[1])
        except keystone_exceptions.ClientException:
            raise AuthorizationFailure(
                'Unexpected keystone client error occurred: %s' %
                sys.exc_info()[1])
    return wrapped


class OpenStackClients(object):
    """Convenience class to create and cache client instances."""

    def __init__(self, config):
        self.reset_clients()
        self.config = config

    def reset_clients(self):
        self._session = None
        self._keystone = None
        self._glance = None
        self._nova = None
        self._neutron = None

    def _get_keystone_session(self):
        auth = ka_loading.load_auth_from_conf_options(self.config,
                                                      _CLIENTS_AUTH_GROUP)
        sess = ka_loading.load_session_from_conf_options(self.config,
                                                         _CLIENTS_AUTH_GROUP,
                                                         auth=auth)
        return sess

    @property
    def auth_url(self):
        return self.keystone().auth_url

    @property
    def session(self):
        if not self._session:
            self._session = self._get_keystone_session()
        return self._session

    def _get_client_option(self, client, option):
        return getattr(getattr(self.config, '%s_client' % client), option)

    @wrap_keystone_exception
    def keystone(self):
        if not self._keystone:
            self._keystone = keyclient.Client(session=self.session)

        return self._keystone

    @wrap_keystone_exception
    def glance(self):
        if self._glance:
            return self._glance

        glance_client_version = self._get_client_option(
            'glance', 'api_version')
        glance_endpoint_type = self._get_client_option('glance',
                                                       'endpoint_type')
        self._glance = glclient.Client(glance_client_version,
                                       interface=glance_endpoint_type,
                                       session=self.session)
        return self._glance

    @wrap_keystone_exception
    def nova(self):
        if self._nova:
            return self._nova

        novaclient_version = self._get_client_option('nova', 'api_version')
        nova_endpoint_type = self._get_client_option('nova', 'endpoint_type')
        self._nova = nvclient.Client(novaclient_version,
                                     endpoint_type=nova_endpoint_type,
                                     session=self.session)
        return self._nova

    @wrap_keystone_exception
    def neutron(self):
        if self._neutron:
            return self._neutron

        self._neutron = netclient.Client(session=self.session)
        self._neutron.format = 'json'
        return self._neutron
