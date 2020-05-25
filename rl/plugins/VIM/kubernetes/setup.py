# coding: utf-8

import sys
from setuptools import setup, find_packages

NAME = "mtp_plugin_kubernetes"
VERSION = "1.0.0"

# To install the library, run the following
#
# python setup.py install
#
# prerequisite: setuptools
# http://pypi.python.org/pypi/setuptools

REQUIRES = ["connexion", 'bitmath', 'urllib3', 'PyYAML', 'python-kubernetes', 'xmltodict']

setup(
    name=NAME,
    version=VERSION,
    description="Kubernetes plugin for MTP",
    author_email="",
    url="",
    keywords=["Swagger", "Kubernetes plugin for MTP"],
    install_requires=REQUIRES,
    packages=find_packages(),
    package_data={'': ['swagger/swagger.yaml']},
    include_package_data=True,
    entry_points={
        'console_scripts': ['mtp_plugin_kubernetes=mtp_plugin_kubernetes.__main__:main']},
    long_description="""\
    Kubernetes plugin for MTP
    """
)

