Filter exemples
===============

'/v1/compute_resources/query'

{  "filter1" : {
    "op" : "eq",
    "param":  "computeName",
    "value" : "IFA005AllocateCompute2"
  },
  "filter2" : {
    "op" : "eq",
    "param":  "computeId",
    "value" : "2bc553e8-53ee-478c-b0b8-ac0ee79f9c5f"
  },
  "filter3" : {
    "op" : "ne",
    "param":  "virtualMemory.virtualMemSize",
    "value" : "1024"
  }
}

Note :

La requête openstack qui remonte la liste des serveur, ne marche pas bien et ne remonte pas la liste de tous les networks.
Un certain nombre de champs IFA ne sont pas disponible non plus comme par exemple le champs : virtualMemSize

Pour le moment les valeur int, sont sous forme de string dans le filtre, mais je vais les repasser en int pour simplifier le code et puis c’est plus logique.

'/v1/compute_resources/flavours/query'

{  "filter1" : {
    "op" : "eq",
    "param":  "flavourId",
    "value" : "29ecda75-1d3e-46c3-9c4b-639d3359b8fc"
  },
  "filter2" : {
    "op" : "eq",
    "param": "virtualCpu.numVirtualCpu",
    "value" : "8"
  },
  "filter3" : {
    "op" : "eq",
    "param": "storageAttributes.sizeOfStorage",
    "value" : "160"
  }
}

'/v1/network_resources/query'

{  "filter1" : {
    "op" : "eq",
    "param" : "networkResourceId",
    "value" : "08b59ebb-831a-4e27-9b14-a33a6a3a04fd"
  },
  "filter2" : {
    "op" : "eq",
    "param" : "metadata.availability_zones",
    "value" : ["nova"]
  },
  "filter3" : {
    "op" : "eq",
    "param" : "metadata.port_security_enabled",
    "value" : true
  },
  "filter4" : {
    "op" : "eq",
    "param" : "subnet.addressPool",
    "value" : {
                                 "start": "172.16.4.2",
                                 "end": "172.16.4.254"
                  }
  },
  "filter5" : {
    "op" : "eq",
    "param" : "subnet.dns_nameservers",
    "value" : ["10.1.10.10", "10.1.10.11"]
  }
}


'/v1/software_images/query'

{  "filter1" : {
    "op" : "ne",
    "param":  "id",
    "value" : "6eccea7f-390a-43bd-a410-11b03a7bcff7"
  },
  "filter2" : {
    "op" : "le",
    "param": "size",
    "value" : "1804271616"
  },
  "filter3" : {
    "op" : "eq",
    "param": "status",
    "value" : "active"
  },
  "filter3" : {
    "op" : "eq",
    "param": "createdAt",
    "value" : "2019-03-07T16:47:53Z"
  }
}

'/v1/quotas/compute_resources/query'
{  "filter1" : {
    "op" : "ne",
    "param":  "resourceGroupId",
    "value" : "05cfb0fd7fa449f386f0313b809f18ec"
  },
  "filter2" : {
    "op" : "lt",
    "param": "numVCPUs",
    "value" : "200"
  },
  "filter3" : {
    "op" : "lt",
    "param": "numVcInstances",
    "value" : "101"
  },
  "filter4" : {
    "op" : "eq",
    "param": "virtualMemSize",
    "value" : "512000"
  }
}

'/v1/quotas/network_resources/query'

{  "filter1" : {
    "op" : "ne",
    "param":  "resourceGroupId",
    "value" : "05cfb0fd7fa449f386f0313b809f18ec"
  },
  "filter2" : {
    "op" : "lt",
    "param": "numPublicIps",
    "value" : "200"
  },
  "filter3" : {
    "op" : "eq",
    "param": "numPorts",
    "value" : "500"
  },
  "filter4" : {
    "op" : "eq",
    "param": "numSubnets",
    "value" : "100"
  }
}

