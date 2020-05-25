#!/bin/bash

docker build . -t wagon
docker run --name wagon wagon /bin/true
docker cp wagon:/prom/cloudify_mtp_plugin-0.0.1-py27-none-manylinux1_x86_64.wgn cloudify_mtp_plugin-0.0.1-py27-none-manylinux1_x86_64.wgn
docker rm -f wagon
docker rmi wagon
mtp_id=$(cfy plugins list | grep  cloudify_mtp_plugin | awk '{print $2}')
cfy plugins delete $mtp_id
cfy plugins upload cloudify_mtp_plugin-0.0.1-py27-none-manylinux1_x86_64.wgn -y plugin.yaml
