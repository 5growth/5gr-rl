
from setuptools import setup

# Replace the place holders with values for your project

setup(

    # Do not use underscores in the plugin name.

    name='cloudify_mtp_plugin',
    version='0.0.1',
    author='Tomakh Konstantin',
    author_email='tomahkvt@gmail.com',
    description='A Cloudify plugin for MTP',

    # This must correspond to the actual packages in the plugin.
    packages=['cloudify_mtp_plugin'],

    license='LICENSE',
    zip_safe=False,
    install_requires=[
        # Necessary dependency for developing plugins, do not remove!
        "cloudify-plugins-common>=4.3.3",
        "requests"

    ],
    test_requires=[
        "cloudify-plugins-common>=4.3.3",
        "nose",
        "requests"
    ]
)
