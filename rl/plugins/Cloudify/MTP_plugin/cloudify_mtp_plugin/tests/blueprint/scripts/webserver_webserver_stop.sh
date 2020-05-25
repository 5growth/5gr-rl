#! /bin/bash
set -e
curl -X DELETE \
  http://localhost:8888/vnfconfig/v1/configuration/$1:800
