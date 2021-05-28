import connexion
from flask_testing import TestCase

from mtp_plugin_kubernetes import encoder


class BaseTestCase(TestCase):
    def setUp(self):
        pass

    def create_app(self):
        app = connexion.App(__name__, specification_dir='../../mtp_plugin_kubernetes/swagger')
        app.app.json_encoder = encoder.JSONEncoder
        options = {"swagger_ui": False,
                   "serve_spec": False
                   }
        app.add_api('swagger.yaml', arguments={'title': 'Kubernetes plugin for MTP'},
                    options=options)

        return app.app
