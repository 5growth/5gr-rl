#!/bin/bash
# Copyright 2018 b<>com.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
# IDDN number: IDDN.FR.001.470053.000.S.C.2018.000.00000.
#

#######################################
# Default Parameters
#######################################
PIP_CONF_FILE="$HOME/.pip/pip.conf"
PIP_INDEX_URL=
PIP_TRUSTED_HOST=
PIP_EXTRA_INDEX_URL=
PIP_UPPER_CONSTRAINTS_FILE="https://forge.b-com.com/www/falcon/sourcedoc/constraints/docs/_downloads/upper-constraints.txt"

#######################################
# Print formatted message to stdout
# Globals:
#   DEBUG       = 'Y'|'N' : prints DEBUG level messages
# Arguments:
#   $1          = LEVEL : '*'|'DEBUG'|'QUEST'
#   $*          = Message
#######################################mess() {
message() {
    LEVEL=$1 ; shift ; MESSAGE=$*
    if [ "$LEVEL" != "DEBUG" -o "$DEBUG" = "Y" ] ; then
        echo -e "    [\e[0;32m${LEVEL}\e[0m]\t$MESSAGE" ; fi
    [ "$LEVEL" = "QUEST" ] && read QUEST
    return 0
}

build () {
    if [ -f $PIP_CONF_FILE ]; then
        echo
        message INFO "Read the file $PIP_CONF_FILE"
        sed -i "s/ //g" ${PIP_CONF_FILE}
        PIP_INDEX_URL=`awk -F "=" '$1 == "index-url" {print $2}' ${PIP_CONF_FILE}`
        PIP_EXTRA_INDEX_URL=`awk -F "=" '$1 == "extra-index-url" {print $2}' ${PIP_CONF_FILE}`
        PIP_TRUSTED_HOST=`awk -F "=" '$1 == "trusted-host" {print $2}' ${PIP_CONF_FILE}`
    fi

    echo
    message INFO "Docker build falcon/vim-manager"

    if [ -z $PIP_INDEX_URL ]; then
        message ERROR "Edit the file $PIP_CONF_FILE and add your"\
                      "PIP_INDEX_URL parameter"
    else
        docker build --no-cache \
                     --build-arg REGISTRY_URL=${FALCON_REGISTRY_URL} \
                     --build-arg _PIP_INDEX_URL=${PIP_INDEX_URL} \
                     --build-arg _PIP_EXTRA_INDEX_URL=${PIP_EXTRA_INDEX_URL} \
                     --build-arg _PIP_TRUSTED_HOST=${PIP_TRUSTED_HOST} \
                     --build-arg _PIP_UPPER_CONSTRAINTS_FILE=${PIP_UPPER_CONSTRAINTS_FILE} \
                     -t falcon/vim-manager .
    fi

    return 0
}

build
