#! /bin/bash
set -e
sudo sed -i "/server $1_$2 $2:80 check/d" /etc/haproxy/haproxy.cfg
sudo systemctl reload haproxy.service
