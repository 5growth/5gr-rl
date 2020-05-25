# -*- coding: utf-8 -*-
"""Create an application instance."""
import os

from vim_manager import application

config_file = os.getenv('VIM_MANAGER_CONFIG_FILE')

app = application.create_app(config_file)
