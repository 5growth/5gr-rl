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
# -*- encoding: utf-8 -*-

import logging
import simplejson

from werkzeug import exceptions as wexc

LOG = logging.getLogger(__name__)


class ApiException(wexc.HTTPException):
    """Base Exception.

    To correctly use this class, inherit from it and define
    a 'description' property. That description will get printf'd
    with the keyword arguments provided to the constructor.
    """

    description = "An unknown exception occurred"
    code = 500

    def __init__(self, message=None, **kwargs):
        self.kwargs = kwargs

        if 'code' not in self.kwargs:
            try:
                self.kwargs['code'] = self.code
            except AttributeError:
                pass

        if not message:
            try:
                message = self.description % kwargs
            except Exception:
                # kwargs doesn't match a variable in description
                # log the issue and the kwargs
                LOG.exception('Exception in string format operation')
                for name, value in kwargs.items():
                    LOG.error("%(name)s: %(value)s",
                              {'name': name, 'value': value})

                # at least get the core description out if something happened
                message = self.description

        super().__init__(message)

    def __str__(self):
        """Encode to utf-8 then wsme api can consume it as well"""
        return self.description

    def format_message(self):
        if self.__class__.__name__.endswith('_Remote'):
            return self.args[0]
        return str(self)

    def get_headers(self, environ=None):
        """Get a list of headers."""
        return [('Content-Type', 'application/json')]

    def get_body(self, environ=None):
        """Get the error body."""
        return simplejson.dumps({
            'code': self.code,
            'name': self.name,
            'description': self.get_description(environ),
        })

    def get_description(self, environ=None):
        return u'%s' % self.description


class ServiceException(ApiException):
    pass


class ClientNotConfigured(ServiceException):
    """Service not found through discovery"""

    description = "'Your agent is not configured'"


class NotAuthorized(wexc.Unauthorized, ServiceException):
    """Not Authenticated"""


class PermissionDenied(wexc.Forbidden, ServiceException):
    """Does not have the right level of privileges"""

    description = ("Permission denied: %(role)s do not match "
                   "required roles (%(required)s)")


class BadRequest(wexc.BadRequest, ServiceException):
    """BadRequest"""


class Conflict(wexc.Conflict, ServiceException):
    """Conflict"""


class NotFound(wexc.NotFound, ServiceException):
    """Not Found"""


class ServiceNotFound(wexc.NotFound, ServiceException):
    """Service not found through discovery"""

    description = "No '%(type)s' service could be found"


class UnsupportedDriver(ServiceException):
    """Driver is not supported by the application"""

    description = "Driver '%(name)s' is not supported"


class LoadingError(ServiceException):
    description = "Error loading plugin '%(name)s'"


class ResourceNotFound(NotFound):
    description = "The %(name)s resource %(id)s could not be found"


class InvalidIdentity(BadRequest):
    description = "Expected a UUID but received: %(identity)s"


class ResourceAlreadyExists(Conflict):
    description = "A %(name)s with ID %(id)s already exists"


class AuthorizationFailure(Exception):
    description = "Authorization failure"
