#!/usr/bin/env python3

import connexion
from mtp_plugin_kubernetes import encoder
from mtp_plugin_kubernetes.config.config import ConfigurationFile
import logging


def main():
    server_port = ConfigurationFile().config['VIM']['Server']['Port']
    app = connexion.App(__name__, specification_dir='./swagger/')
    app.app.json_encoder = encoder.JSONEncoder
    app.app.logger.info("Kubernetes plugin port:" + server_port)
    options = {"swagger_ui": False,
               "serve_spec": False
               }
    app.add_api('swagger.yaml', arguments={'title': 'Kubernetes plugin for MTP'},
                options=options)

    app.run(port=server_port)


if __name__ == '__main__':
    main()
