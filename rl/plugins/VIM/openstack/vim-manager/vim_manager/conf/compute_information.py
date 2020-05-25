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
# -*- encoding: utf-8 -*-

import os

from oslo_config import cfg

from vim_manager import log

GROUP_NAME = 'compute_information'
group = cfg.OptGroup(
    name=GROUP_NAME,
    title='Configuration Options for compute information',
    help="All parameters can be overidden via environment variables",
)

OPTS = [
    cfg.IntOpt(
        'computeResourceTypeMemory',
        default=os.environ.get('TYPE_ID', 0),
        help="Identifier of the consumable virtualised compute resource type=Memory."),
    cfg.IntOpt(
        'computeResourceTypeCpu',
        default=os.environ.get('TYPE_ID', 1),
        help="Identifier of the consumable virtualised compute resource type=CPU."),
    cfg.IntOpt(
        'virtualMemSize',
        default=os.environ.get('MEM_SIZE', 512),
        help="Amount of virtual memory (e.g. in MB)"),
    cfg.StrOpt(
        'virtualMemOversubscriptionPolicy',
        default=os.environ.get('VIRT_MEM_POLICY', "theWhat"),
        help="The memory core oversubscription policy"),
    cfg.BoolOpt(
        'numaSupported',
        default=os.environ.get('NUMA', True),
        help="It specifies if the memory allocation can be cognisant of the relevant process/core allocation."),
    cfg.StrOpt(
        'cpuArchitecture',
        default=os.environ.get('CPU_ARCH', 'x86'),
        help="CPU architecture type. Examples are x86, ARM"),
    cfg.IntOpt(
        'numVirtualCpu',
        default=os.environ.get('NUM_CPU', 4),
        help="Number of virtual CPUs"),
    cfg.IntOpt(
        'cpuClock',
        default=os.environ.get('CPU_CLOCK', 2),
        help="Minimum CPU clock rate (e.g. in MHz)"),
    cfg.StrOpt(
        'virtualCpuOversubscriptionPolicy',
        default=os.environ.get('VIRT_CPU_POLICY', "noIdea"),
        help="The CPU core oversubscription policy"),
    cfg.BoolOpt(
        'virtualCpuPinningSupported',
        default=os.environ.get('CPU_SPIN', True),
        help="It defines whether CPU pinning capability is available"),
    cfg.StrOpt(
        'accelerationCapability',
        default=os.environ.get('ACC_CAPA', 'crypto'),
        help="Acceleration capabilities (e.g. crypto, GPU)"),
]

def register_opts(conf):
    conf.register_opts(OPTS, group=group)

def list_opts():
    return [(GROUP_NAME, OPTS)]
