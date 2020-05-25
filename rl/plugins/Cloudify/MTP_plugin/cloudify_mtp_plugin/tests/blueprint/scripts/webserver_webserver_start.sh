#/bin/bash
set -e
curl -X POST \
  http://localhost:8888/vnfconfig/v1/configuration \
  -H 'Content-Type: application/json' \
  -d '{
"key": "vnf.spr2",
"value": "'"$1:800"'"
}'
