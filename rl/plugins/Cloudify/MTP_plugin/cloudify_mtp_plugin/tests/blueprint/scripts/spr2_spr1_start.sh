#/bin/bash
curl -X PATCH \
  http://localhost:8888/vnfconfig/v1/configuration \
  -H 'Content-Type: application/json' \
  -d '{
      "vnfConfigurationData": {
      "cpConfiguration": [],
      "dhcpServer": "null",
      "vnfSpecificData": [{
      "key": "vnf.spr1.vdu.spr1_vdu.intcp.spr1DataInt.address",
      "value": "'"$1"'"
      }
      ]
      },
      "vnfcConfigurationData": [],
      "vnfInstanceId": "553"
}'
