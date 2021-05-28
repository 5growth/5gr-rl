import json
import logging
import unittest

import deepdiff
import requests


class TestMTPPod(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_create_pod(self):
        create_pod_request = \
            {
                "userData": {
                    "content": "/bin/ash, -c, trap : TERM INT; sleep infinity & wait",
                    "method": "CONFIG-DRIVE"
                },
                "interfaceData": [
                    {
                        "ipAddress": "192.168.10.17",
                        "macAddress": "52:54:00:11:2a:29",
                        "networkId": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl"
                    }
                ],
                "computeFlavourId": "flavor_DARLVNF",
                "resourceGroupId": "",
                "mecAppDId": "",
                "affinityOrAntiAffinityConstraints": [
                    {
                        "type": "",
                        "affinityAntiAffinityResourceGroup": "",
                        "affinityAntiAffinityResourceList": {
                            "resource": [
                                ""
                            ]
                        },
                        "scope": ""
                    }
                ],
                "reservationId": "",
                "locationConstraints": "",
                "computeName": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-DARLVNF-1",
                "vcImageId": "jabbo16/iperf-host:1.0.0",
                "metadata": [
                    {
                        "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795",
                        "key": "ServiceId"
                    },
                    {
                        "value": "1",
                        "key": "AbstractNfviPoPId"
                    },
                    {
                        "value": "innovaliakey",
                        "key": "key-name"
                    }
                ]
            }

        json_response_reference = \
            {
                "accelerationCapability": [],
                "computeId": "4",
                "computeName": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-DARLVNF-1",
                "flavourId": None,
                "hostId": "192.168.100.151",
                "operationalState": "enabled",
                "vcImageId": "",
                "virtualCpu": {
                    "cpuArchitecture": "",
                    "cpuClock": 0,
                    "numVirtualCpu": 0,
                    "virtualCpuOversubscriptionPolicy": "",
                    "virtualCpuPinning": {
                        "cpuMap": "",
                        "cpuPinningPolicy": "dynamic",
                        "cpuPinningRules": ""
                    }
                },
                "virtualDisks": "",
                "virtualMemory": {
                    "numaEnabled": False,
                    "virtualMemOversubscriptionPolicy": None,
                    "virtualMemSize": 0
                },
                "virtualNetworkInterface": [
                    {
                        "accelerationCapability": "",
                        "bandwidth": "0",
                        "ipAddress": [
                            "10.244.0.110"
                        ],
                        "macAddress": "0a:f9:30:6e:f4:1c",
                        "floatingIP": None,
                        "metadata": [
                            {
                                "key": "dc",
                                "value": "1"
                            }
                        ],
                        "networkName": None,
                        "networkId": "",
                        "networkPortId": "eth0",
                        "operationalState": "ACTIVE",
                        "ownerId": None,
                        "resourceId": "eth0",
                        "typeConfiguration": "Multus-cni",
                        "typeVirtualNic": "normal"
                    },
                    {
                        "accelerationCapability": "",
                        "bandwidth": "0",
                        "ipAddress": [
                            "192.168.10.17"
                        ],
                        "macAddress": "52:54:00:11:2a:27",
                        "floatingIP": None,
                        "metadata": [
                            {
                                "key": "dc",
                                "value": "1"
                            }
                        ],
                        "networkName": None,
                        "networkId": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl",
                        "networkPortId": "net1",
                        "operationalState": "ACTIVE",
                        "ownerId": None,
                        "resourceId": "net1",
                        "typeConfiguration": "Multus-cni",
                        "typeVirtualNic": "normal"
                    }
                ],
                "zoneId": "nova",
                "mecappID": ""
            }

        headers = {
            'Content-Type': 'application/json'
        }
        response = requests.request("POST",
                                    'http://127.0.0.1:50000/mtpbase/abstract-compute-resources',
                                    headers=headers,
                                    json=create_pod_request

                                    )
        self.assertEqual(response.status_code, 200, "200")

        json_response = response.json()
        logging.info(json.dumps(json_response, indent=4))
        exclude_paths = ["root['computeId']",
                         "root['hostId']",
                         "root['virtualNetworkInterface'][0]['ipAddress'][0]",
                         "root['virtualNetworkInterface'][0]['macAddress']"]
        ddiff = deepdiff.DeepDiff(json_response_reference,
                                  json_response,
                                  ignore_order=True,
                                  verbose_level=0,
                                  exclude_paths=exclude_paths)
        logging.info(json.dumps(ddiff, indent=4))
        self.assertEqual(ddiff, {}, "create_network returned wrong object")

        compute_id = json_response['computeId']
        response = requests.request("DELETE",
                                    'http://127.0.0.1:50000/mtpbase/abstract-compute-resources?computeId=' + compute_id
                                    )
        self.assertEqual(response.status_code, 200, "200")
