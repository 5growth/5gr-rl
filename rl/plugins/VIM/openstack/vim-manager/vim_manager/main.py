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
"""Create an application instance."""

# import atexit
import json
import os
import sys

import click
from flask import cli as flaskcli
from werkzeug import serving

from vim_manager import application
from vim_manager import conf
from vim_manager import log
from vim_manager import version as appversion

CONF = conf.CONF
LOG = log.get_logger(__name__)


def get_app(info):
    """VIM Manager entrypoint."""
    return application.create_app(info.data.get("config_file", None))


@click.group(
    cls=flaskcli.FlaskGroup,
    add_default_commands=False,
    create_app=get_app,
    )
@click.option("--config-file", type=click.Path(exists=True), default=None)
def cli(**_):
    """CLI root command."""


@click.command('run', short_help='Runs a development server.')
@click.option('--with-threads/--without-threads', default=False,
              help='Enable or disable multithreading.')
@flaskcli.pass_script_info
def run_command(info, with_threads):
    """Run a local development server for the Flask application.

    This local server is recommended for development purposes only but it
    can also be used for simple intranet deployments.  By default it will
    not support any sort of concurrency at all to simplify debugging.  This
    can be changed with the --with-threads option which will enable basic
    multithreading.

    The reloader and debugger are by default enabled if the debug flag of
    Flask is enabled and disabled otherwise.
    """
    _conf = application.load_config(
        config_file=click.get_current_context(
            ).find_root().params["config_file"])

    if _conf.DEFAULT.debug:
        log_level = log.DEBUG
    else:
        log_level = log.LOG_LEVELS[_conf.DEFAULT.log_level]
    log.setup(level=log_level)

    _conf.log_opt_values(LOG, log.DEBUG)

    info.data['config_file'] = click.get_current_context(
        ).find_root().params["config_file"]

    app = flaskcli.DispatchingApp(
        info.load_app, use_eager_loading=not _conf.DEFAULT.debug)

    # Extra startup messages.  This depends a bit on Werkzeug internals to
    # not double execute when the reloader kicks in.
    if os.environ.get('WERKZEUG_RUN_MAIN') != 'true':
        # If we have an import path we can print it out now which can help
        # people understand what's being served.  If we do not have an
        # import path because the app was loaded through a callback then
        # we won't print anything.
        if info.app_import_path is not None:
            click.echo(' * Serving Flask app "%s"' % info.app_import_path)

        click.echo(
            ' * Debug mode %s' % (_conf.DEFAULT.debug and 'on' or 'off'))

    serving.run_simple(
        _conf.DEFAULT.host,
        _conf.DEFAULT.port,
        application=app,
        use_reloader=_conf.DEFAULT.debug,
        use_debugger=_conf.DEFAULT.debug,
        threaded=with_threads)


@click.command('version', help="Display service version")
def version():
    click.echo(appversion.version_string())


@click.command('apispec', help="Display OpenAPI specification")
@click.option("--output", default=None, help="Output filepath.")
def apispec(output):
    """Generate the OpenAPI specification."""
    testapp = application.create_app().test_client()
    result = testapp.get("/")
    template = json.loads(result.data.decode('utf-8'))
    if output:
        with open(output, 'w') as out:
            json.dump(template, out, indent=2)
    else:
        json.dump(template, sys.stdout, indent=2)


cli.add_command(version)
cli.add_command(apispec)
cli.add_command(run_command)


def main():
    """Service entrypoint"""
    cli()


if __name__ == '__main__':
    main()
