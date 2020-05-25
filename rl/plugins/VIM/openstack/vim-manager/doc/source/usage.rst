=====
Usage
=====

Once you have installed the ``vim-manager`` package and
generate the configuration file, you can run the
VIM Manager Service with this command:


For developer
-------------

.. code-block:: bash

    $ vim-manager [--config-file <conf_file>] run


For a more production mode you can use a wsgi http server in front of the
application.

For production (using WSGI)
---------------------------

With Gunicorn you can use this command:

.. code-block:: bash

    $ VIM_MANAGER_CONFIG_FILE=<conf_file> gunicorn -w 4 vim_manager.wsgi:app


For production (using docker)
-----------------------------

.. code-block:: bash

    $ docker build -t vim-manager .
    $ docker run --net=host vim-manager:latest


To get help about command options :

.. code-block:: bash

    $ vim-manager help


For example, here is a minimal config file.

.. code-block:: bash

  [DEFAULT]

  [clients_auth]
  auth_strategy = keystone
  region_name = regionOne
  project_name = demo
  username = demo
  password = secret
  auth_url = http://keystone.local/identity/v3 #Identity service URL on your OpenStack instance
  auth_type = password
  project_domain_name = Default
  user_domain_name = Default


All the configuration can be overriden by environment variables. All
details can be found in the configuration section.


Next, we will show you some snippets of curl commands to access vim-manager
API.

Remember to change the example values to match with your data environment.

Software image
______________

.. code-block:: bash

 # Add
 curl -X POST --form 'softwareImage=@/home/userid/alpine-standard-3.8.0-x86_64.iso' --form 'name=my-flavor' --form 'visibility=private'  --form 'version=v3' http://127.0.0.1:8000/v1/software_images

 # Get images data
 curl -X POST http://127.0.0.1:8000/v1/software_images/query

 # Delete image
 curl -X DELETE http://127.0.0.1:8000/v1/software_images/ce2fa73e-99ac-40aa-97b4-1ab44e745e67


Nerwork resources
_________________

.. code-block:: bash

 # Create Network
 curl -H 'Content-Type: application/json' -X POST -d '{"networkResourceName": "my-network", "reservationId": "my-network-id", "networkResourceType": "network"}'  http://127.0.0.1:8000/v1/network_resources

 # Create Subnet (remember to replace network id with the one created just above)
 curl -H 'Content-Type: application/json' -X POST -d '{"networkResourceName": "my-subnet-network", "reservationId": "my-subnet-id", "networkResourceType": "subnet", "typeSubnetData": {"networkId": "50402b53-06c2-4fcf-b2d1-2cbeb4e72d45", "ipVersion": "IPv4", "cidr": "192.168.0.0/24", "gatewayIp": "192.168.0.1", "isDhcpEnabled": 1}}' http://127.0.0.1:8000/v1/network_resources

 # Create Network Port
 curl -H 'Content-Type: application/json' -X POST -d '{"networkResourceName": "my-port-network", "reservationId": "my-port-id", "networkResourceType": "network-port", "typeNetworkPortData": {"networkId": "50402b53-06c2-4fcf-b2d1-2cbeb4e72d45"}}'  http://127.0.0.1:8000/v1/network_resources

 # Get
 curl -X POST http://127.0.0.1:8000/v1/network_resources/query

 # Delete
 curl -X DELETE "http://127.0.0.1:8000/v1/network_resources?networkResourceId=50402b53-06c2-4fcf-b2d1-2cbeb4e72d45"


Compute resources
_________________

.. code-block:: bash

 # Allocate
 # Remember to create a network first
 curl -H 'Content-Type: application/json' -X POST -d '{"computeName":"cdatest", "computeFlavourId": "2", "vcImageId": "4c55e188-36b4-4858-afa9-74d8dbeed771", "networkId": "50402b53-06c2-4fcf-b2d1-2cbeb4e72d45"}'  http://127.0.0.1:8000/v1/compute_resources

 # Get
 curl -X POST http://127.0.0.1:8000/v1/compute_resources/query

 # Terminate
 curl -H 'Content-Type: application/json' -X DELETE  http://127.0.0.1:8000/v1/compute_resources?computeIdcb766877-b5fe-4b1d-81a1-d5a452223d16


.. code-block:: bash

 # Flavour Creation
 curl -H 'Content-Type: application/json' -X POST -d '{"flavourId": "my-flavor", "virtualMemory": {"virtualMemSize": 2}, "virtualCpu": {"numVirtualCpu": 1}, "storageAttributes": {"sizeOfStorage": 1}}'  http://127.0.0.1:8000/v1/compute_resources/flavours

 # Get all flavours
 curl -X POST http://127.0.0.1:8000/v1/compute_resources/flavours/query

 # Delete one flavour
 curl -X DELETE http://127.0.0.1:8000/v1/compute_resources/flavors/my-flavor



Quota
_____


Compute

.. code-block:: bash

 # Create (ResourceGroupId represent a project id in OpenStack)
 curl -H 'Content-Type: application/json' -X POST -d '{"resourceGroupId": "de30d94754e3482983d10121b0c76fd2", "virtualComputeQuota": {"numVCPUs": 2, "numVcInstances": 2, "virtualMemSize": 4096}}'  http://127.0.0.1:8000/v1/quotas/compute_resources

 # Delete
 curl -H 'Content-Type: application/json' -X DELETE http://127.0.0.1:8000/v1/quotas/compute_resources?resourceGroupId=de30d94754e3482983d10121b0c76fd2

 # Get
 curl -H 'Content-Type: application/json' -X POST http://127.0.0.1:8000/v1/quotas/compute_resources/query

Network

.. code-block:: bash

 # Create
 curl -H 'Content-Type: application/json' -X POST -d '{"resourceGroupId": "de30d94754e3482983d10121b0c76fd2", "virtualNetworkQuota": {"numPublicIps": 10, "numPorts": 5, "numSubnets": 2}}'  http://127.0.0.1:8000/v1/quotas/network_resources

 # Delete
 curl -H 'Content-Type: application/json' -X DELETE http://127.0.0.1:8000/v1/quotas/network_resources?resourceGroupId=de30d94754e3482983d10121b0c76fd2

 # Get
 curl -H 'Content-Type: application/json' -X POST http://127.0.0.1:8000/v1/quotas/network_resources/query



