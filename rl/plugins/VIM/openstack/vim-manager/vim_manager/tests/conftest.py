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
"""Defines fixtures available to all tests."""

# from unittest import mock

# import jwt
import pytest
import requests_mock
import webtest

from vim_manager import application
from vim_manager import conf
from vim_manager import log
from vim_manager import version

LOG = log.get_logger(__name__)


@pytest.yield_fixture(scope='function')
def testconf():
    default_conf = conf.ConfigOpts()
    default_conf.clear()
    conf.register_all_opts(default_conf)
    default_conf.set_default('host', default='fake-consul', group='discovery')
    default_conf.set_default('port', default='1337', group='discovery')
    loaded_conf = application.load_config(conf=default_conf)
    yield loaded_conf
    loaded_conf.reset()


@pytest.fixture(scope='function')
def testconfig():
    return {
        'DEBUG': True,
        'HOST': '11.11.11.11',
        'PORT': 1111,
        'SECRET_KEY': 'TEST SECRET',
        'SWAGGER': {
            "title": "VIM Manager Service API",
            "description": "VIM Manager Service API",
            "version": version.version_string(),
        }
    }


@pytest.fixture(scope="function")
def access_token():
    return {
        'acr': '1',
        'allowed-origins': [],
        'aud': 'admin-cli',
        'auth_time': 0,
        'azp': 'admin-cli',
        'email': 'org@manager.com',
        'exp': 1503656288,
        'family_name': 'Org',
        'given_name': 'Manager',
        'iat': 1503655988,
        'iss': 'http://127.0.0.1:8080/auth/realms/falcon',
        'jti': '3880811c-6868-485f-9ae4-af6fc4720a4d',
        'name': 'Org Manager',
        'nbf': 0,
        'preferred_username': 'org-manager',
        'realm_access': {'roles': ['org-admin', 'uma_authorization']},
        'resource_access': {'account': {'roles': ['manage-account',
                                                  'manage-account-links',
                                                  'view-profile']}},
        'session_state': '9c3740b1-bd6a-4d55-9ff5-3ab7181dddd4',
        'sub': 'd873e1c7-a957-4ff9-a3f3-4cc8951797be',
        'typ': 'Bearer',
    }


# @pytest.yield_fixture(scope="function")
# def m_jwt_decode():
#     with mock.patch.object(jwt, "decode") as m_decode:
#         yield m_decode


# @pytest.yield_fixture(scope="function")
# def context(m_jwt_decode, access_token):
#     m_jwt_decode.return_value = access_token
#     return access_token


@pytest.yield_fixture(scope='function')
def app(testconfig):
    """An application for the tests."""
    _app = application.create_app(testconfig)
    ctx = _app.test_request_context()
    ctx.push()

    yield _app

    ctx.pop()


@pytest.fixture(scope='function')
def testapp(app):
    """A Webtest app."""
    return webtest.TestApp(app, extra_environ=None)


# @pytest.fixture
# @pytest.mark.usefixtures('context')
# def authapp(app, context):
#     # We want to add the access token header
#     return webtest.TestApp(
#         app,
#         extra_environ={'HTTP_X_AUTH_TOKEN': 'WILL BE IGNORED'})


@pytest.yield_fixture(scope='function')
def request_mock():
    with requests_mock.mock() as m_req:
        yield m_req
        LOG.debug("Tear down discovery_client mock")
