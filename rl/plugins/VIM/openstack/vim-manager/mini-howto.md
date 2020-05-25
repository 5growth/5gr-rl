# Vim-manager mini how-to
This guide presents the basics to deploy and use the vim-manager.

# Openstack prerequisite

 1. Openstack "queen" version (at least)
 2. Having a tenant with a router linked to the external network
 3. Add the public key for the vtep image (in Openstack import the file vim-manager-key.pub, name the key **vim-manager-key** )

Note: the private and public key are at the root of the repo

# Install vim-manager

    $ tar xzf vim-manager.X.Y.Z.tgz 
    $ cd vim_manager.X.Y.Z
    $ pip install .

Or, if you have virtualenvwrapper installed:

    $ mkvirtualenv vim-manager -p python3.7
    $ tar xzf vim-manager.X.Y.Z.tgz
    $ cd vim_manager.X.Y.Z
    $ pip install .

# Config file
In order  to have access to the openstack server we use a config file with the credentials. We have delivered an exemple file to illustrate what you need (file available at the root of the repo).

## config.conf.exemple

**[DEFAULT]**
debug = true
**\# Host of the running vim-manager**
host = 192.168.68.101
**\# Using the port number is a way to run two or more vim-manager with different 
\# config files on different openstack server**
port = 8001
project_id = 776354dbbda448af872ed1c72ced19ee
**\# Openstack credentials**
[clients_auth]
auth_strategy = keystone
region_name = RegionOne
project_name = OSMV4-VIM
username = user-openstack
password = xxxxxxxxx
auth_url = https://XX.YY.ZZ.WW:5000/v3
auth_type = password
project_domain_name = Default
user_domain_name = Default
insecure = True
**[nfvi_pop]**
**\# Identification of the NFVI-PoP.**
nfviPopId = BCOM
**\# Identification of the VIM.**
vimId = 123456
**\# It provides information about the geographic location** 
geographicalLocationInfo = Rennes
**\# Information about network connectivity endpoints to the NFVI-PoP**
networkConnectivityEndpoint = stuff
[vtep]
**\# The name of the vtep vm**
vtep_name = vm_vtep_bridge
**\# The image used for the vtep vm, this is a prerequisite**
**\# you'll have to import the image provided with the vim-manager delivery**
vtep_image = c4646799-cbe8-4850-9ef7-1c33b30c05dc
vtep_flavor = 70bc3010-f21f-4430-801f-e7aa7bc5861c
vtep_dns = 10.1.10.10, 10.1.10.11

---
**\# flavor exemple**
 #+----------------------------+--------------------------------------+
#| Field                      | Value                                |
#+----------------------------+--------------------------------------+
#| OS-FLV-DISABLED:disabled   | False                                |
#| OS-FLV-EXT-DATA:ephemeral  | 0                                    |
#| access_project_ids         | None                                 |
#| disk                       | 20                                   |
#| id                         | b74c8a98-d710-41e8-90f0-549c5b81f1e9 |
#| name                       | f.small                              |
#| os-flavor-access:is_public | True                                 |
#| properties                 |                                      |
#| ram                        | 2048                                 |
#| rxtx_factor                | 1.0                                  |
#| swap                       |                                      |
#| vcpus                      | 1                                    |
#+----------------------------+--------------------------------------+

---

# Run the vim-manager

    $ vim-manager --config-file config.conf.exemple run

Note: It is possible to run more than one vim-manager on different tenant, just modify the port number in the config file.

# More documentation

Refers to the directory **doc**, it contains more info about the usage of the vim-manager and some example of curl request.

In the directory **postman** there is all the request need to use the vim-manager in files that you can import in the postman soft.

