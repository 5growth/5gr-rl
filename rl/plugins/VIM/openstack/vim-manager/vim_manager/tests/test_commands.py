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

import os
import shlex
import textwrap
# from unittest import mock

import click.testing
# from flask import cli as flaskcli
import pytest
import simplejson

from vim_manager import main


@pytest.fixture(scope="function")
def cli_runner():
    return click.testing.CliRunner(echo_stdin=True)


@pytest.yield_fixture(scope="function")
def cli_dir(cli_runner):
    with cli_runner.isolated_filesystem() as fs:
        yield fs


@pytest.fixture(scope="function")
def config_file(cli_dir):
    # We make sure no environment variable previously set will interfere
    config_file_content = textwrap.dedent("""
    [DEFAULT]
    host=127.0.0.1
    port=8000
    debug=False

    [discovery]
    driver=static
    driver_config=identity:http://fake-identity:1337
    """)
    filepath = os.path.join(cli_dir, "test-config.conf")
    with open(filepath, "w") as _config_file:
        _config_file.write(config_file_content)
    return filepath


@pytest.yield_fixture(scope="function")
def cli(cli_runner, cli_dir):
    """Execute falcon command."""
    def _cli(cmd, env=None):
        # command = " --output-format=json %s" % cmd
        cmdlist = shlex.split(cmd)
        return cli_runner.invoke(
            main.cli, args=cmdlist, env=env or {}, catch_exceptions=False)

    return _cli


@pytest.yield_fixture(scope="session")
def expected_schema():
    schema_fp = os.path.join(os.path.dirname(__file__), "testdata/schema.json")
    with open(schema_fp) as schema_file:
        return simplejson.load(schema_file)


def test_run_version(cli, mocker):
    m_version_string = mocker.patch.object(main.appversion, "version_string")
    m_version_string.return_value = "1.2.3"
    result = cli("version")

    assert result.exit_code == 0, result.output
    assert result.output == "1.2.3\n"


# Commented Test because the DispatcherMiddleware may not be the final choice.
#
# def test_run_application(cli, config_file, mocker):
#     # m_message_init = mocker.patch.object(main.messaging, "init")
#     # m_message_init.return_value = (mock.Mock(name="consumer"), mock.Mock())
#     m_run_simple = mocker.patch.object(main.serving, "run_simple")
#     fake_app = mock.Mock(name="FakeApp")
#     m_dispatching_app = mocker.patch.object(flaskcli, "DispatchingApp")
#     m_dispatching_app.return_value = fake_app

#     result = cli("--config-file %s run" % config_file)

#     assert result.exit_code == 0, result.output
#     # assert m_message_init.call_count == 1
#     m_run_simple.assert_called_once_with(
#         "127.0.0.1",
#         8000,
#         application=fake_app,
#         threaded=False,
#         use_debugger=False,
#         use_reloader=False,
#     )


# def test_run_apispec(cli, config_file, expected_schema):
#     result = cli("--config-file %s apispec" % config_file)

#     assert result.exit_code == 0, result.output
#     current_schema = simplejson.loads(result.output)

#     # We don't want to raise an error for a version bump
#     del current_schema["info"]["version"]

#     assert current_schema == expected_schema, \
#       ("\n\n======================= API CHANGED =======================\n\n"
#        "DON'T FORGET TO UPDATE FALCON-SDK AND FALCONCLIENT TOO!!!"
#        "\n\n======================= API CHANGED =======================\n\n")


# def test_run_apispec_output_in_file(cli, config_file, expected_schema):
#     result = cli("--config-file %s apispec --output schema.json" %
#          config_file)

#     assert result.exit_code == 0, result.output

#     with open("schema.json") as schema_file:
#         current_schema = simplejson.load(schema_file)

#     # We don't want to raise an error for a version bump
#     del current_schema["info"]["version"]

#     assert result.output == ""
#     assert current_schema == expected_schema, \
#        ("\n\n======================= API CHANGED =======================\n\n"
#        "DON'T FORGET TO UPDATE FALCON-SDK AND FALCONCLIENT TOO!!!"
#        "\n\n======================= API CHANGED =======================\n\n")
