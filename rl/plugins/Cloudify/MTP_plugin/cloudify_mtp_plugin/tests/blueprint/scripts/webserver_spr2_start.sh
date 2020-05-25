#/bin/bash
set -e
sudo sed -i "/video_server_cache_backend/a\    server $1_$2 $2:80 check" /etc/haproxy/haproxy.cfg
sudo systemctl reload haproxy.service
