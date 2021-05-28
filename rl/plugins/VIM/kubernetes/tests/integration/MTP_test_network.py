import json
import logging
import unittest
import deepdiff
import requests


class TestMTPNetwork(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_create_network(self):
        create_network_request = \
            {
                "typeSubnetData": {
                    "networkId": "null",
                    "addressPool": [
                        0
                    ],
                    "resourceId": "null",
                    "gatewayIp": None,
                    "ipVersion": "IPv4",
                    "isDhcpEnabled": True,
                    "cidr": "192.168.10.0/24",
                    "metadata": [
                        {
                            "value": "False",
                            "key": "ip-floating-required"
                        },
                        {
                            "value": "[\"8.8.8.8\"]",
                            "key": "dns"
                        },
                        {
                            "value": "40",
                            "key": "interpop_vlan"
                        }
                    ]
                },
                "networkResourceType": "subnet-vlan",
                "typeNetworkPortData": "",
                "resourceGroupId": "null",
                "reservationId": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl",
                "typeNetworkData": None,
                "affinityOrAntiAffinityConstraints": "null",
                "networkResourceName": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl",
                "locationConstraints": "null",
                "metadata": [
                    {
                        "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795",
                        "key": "ServiceId"
                    },
                    {
                        "value": 1,
                        "key": "AbstractNfviPoPId"
                    }
                ]
            }

        json_response_reference = \
            {
                "networkData": {
                    "bandwidth": 0,
                    "isShared": False,
                    "networkPort": [],
                    "networkQoS": [],
                    "networkResourceId": "1",
                    "networkResourceName": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl",
                    "networkType": "vlan",
                    "operationalState": "enabled",
                    "segmentType": "40",
                    "sharingCriteria": "",
                    "subnet": [],
                    "zoneId": "",
                    "metadata": [
                        {
                            "key": "id",
                            "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl"
                        },
                        {
                            "key": "name",
                            "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl"
                        },
                        {
                            "key": "admin_state_up",
                            "value": "True"
                        },
                        {
                            "key": "provider:network_type",
                            "value": "vlan"
                        },
                        {
                            "key": "provider:segmentation_id",
                            "value": "40"
                        },
                        {
                            "key": "ipv4_address_scope",
                            "value": "None"
                        },
                        {
                            "key": "ipv6_address_scope",
                            "value": "None"
                        }
                    ]
                },
                "networkPortData": None,
                "subnetData": {
                    "addressPool": [
                        0
                    ],
                    "cidr": "192.168.10.0/24",
                    "gatewayIp": None,
                    "ipVersion": "4",
                    "isDhcpEnabled": True,
                    "metadata": [
                        {
                            "key": "id",
                            "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl"
                        },
                        {
                            "key": "name",
                            "value": "subnet-fgt-91e0e29-3b9b-46de-9015-863cb2283795_n6_cmm_vl"
                        },
                        {
                            "key": "network_id",
                            "value": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl"
                        },
                        {
                            "key": "ip_version",
                            "value": "4"
                        },
                        {
                            "key": "enable_dhcp",
                            "value": "True"
                        },
                        {
                            "key": "ipv6_address_mode",
                            "value": "None"
                        },
                        {
                            "key": "gateway_ip",
                            "value": "None"
                        },
                        {
                            "key": "cidr",
                            "value": "192.168.10.0/24"
                        },
                        {
                            "key": "allocation_pools",
                            "value": "[{'start': '192.168.10.2', 'end': '192.168.10.20'}]"
                        },
                        {
                            "key": "SegmentationID",
                            "value": "40"
                        }
                    ],
                    "networkId": "1",
                    "operationalState": "enabled",
                    "resourceId": "fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl"
                }
            }

        headers = {
            'Content-Type': 'application/json'
        }
        response = requests.request("POST",
                                    'http://127.0.0.1:50000/mtpbase/network_resources',
                                    headers=headers,
                                    json=create_network_request

                                    )
        self.assertEqual(response.status_code, 200, "200")

        json_response = response.json()
        logging.info(json.dumps(json_response, indent=4))
        exclude_paths = ["root['networkData']['networkResourceId']",
                         "root['subnetData']['networkId']"]
        ddiff = deepdiff.DeepDiff(json_response_reference,
                                  json_response,
                                  ignore_order=True,
                                  verbose_level=0,
                                  exclude_paths=exclude_paths)
        self.assertEqual(ddiff, {}, "create_network returned wrong object")

        network_resource_id = json_response['networkData']['networkResourceId']
        logging.info(network_resource_id)
        response = requests.request("DELETE",
                                    'http://127.0.0.1:50000/mtpbase/network_resources?networkResourceId='
                                    + network_resource_id
                                    )
        self.assertEqual(response.status_code, 200, "200")


if __name__ == '__main__':
    unittest.main()
