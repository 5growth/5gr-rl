#!/usr/bin/env python3

import connexion
from mtp_plugin_kubernetes import encoder
from mtp_plugin_kubernetes.config.config import ConfigurationFile
import logging


def main():
    server_port = ConfigurationFile().config['VIM']['Server']['Port']
    url_prefix = ConfigurationFile().config['VIM']['Server']['URLprefix']
    app = connexion.App(__name__, specification_dir='./swagger/')
    app.app.json_encoder = encoder.JSONEncoder
    app.app.logger.warn("Kubernetes plugin port:" + server_port)
    app.app.logger.warn("Kubernetes plugin url prefix:" + url_prefix)
    app.add_api('swagger.yaml', arguments={'title': 'Kubernetes plugin for MTP'},
                swagger_ui=False, swagger_json=False,
                base_path=url_prefix)

    app.run(port=server_port)


if __name__ == '__main__':
    main()
