====================
Cluster Docker Image
====================

How to build it?
----------------

Building of the ``vim-manager`` Docker image requires installation of other
packages not published by hosted in a local b<>com repository. To fetch them,
you need to set ``PIP_INDEX_URL``, ``PIP_EXTRA_INDEX_URL`` and
``PIP_TRUSTED_HOST`` environment variables.

You can build the Docker image of the ``vim-manager`` with this
command (do not forget to use your own credentials in this command, in place of
*johndoe* username and *AP2yNWwe4sJ1DmnSsYzndWb23eJ* key) :

You can build the docker image by issuing the following command  :

.. code-block:: shell

    $ docker build --no-cache \
                   --build-arg REGISTRY_URL=falcon-docker-virtual.repository.b-com.com/ \
                   --build-arg _PIP_INDEX_URL=http://johndoe:AP2yNWwe4sJ1DmnSsYzndWb23eJ@repository.b-com.com/api/pypi/falcon-pypi-virtual/simple \
                   --build-arg _PIP_EXTRA_INDEX_URL=<another_url_of_package_indexes>\
                   --build-arg _PIP_TRUSTED_HOST=repository.b-com.com \
                   -t falcon/vim-manager .

.. note::

    REGISTRY_URL build argument is optional. It is required if you need to
    build your container image from a dependant image layer stored in
    a third Docker registry, like the b<>com one.

or

You can use the helper script *./build.sh* which will collect these parameters
in your local machine pip settings.

.. code-block:: shell

    $ export FALCON_REGISTRY_URL='falcon-docker-virtual.repository.b-com.com/'
    $ ./build.sh


How to Run it?
--------------

For development needs, you can run a single instance of the
``vim-manager``:

::

    $ docker run -d --net=host ${FALCON_REGISTRY_URL}falcon/vim-manager:latest
