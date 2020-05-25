# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from os import path
import unittest
import json

from cloudify.test_utils import workflow_test


class TestPlugin(unittest.TestCase):

    @workflow_test(path.join('blueprint', 'vCDN_v02_il_vCDN_small.yaml'),
                   resources_to_copy=[(path.join('../', '../',
                                                 'plugin.yaml'),
                                       'plugin')
                                      ])
    def test_mtp_task(self, cfy_local):
        cfy_local.execute('install', task_retries=10, task_retry_interval=5)
        instance = cfy_local.storage.get_node_instances()[0]
        print("outputs after install")
        instance = cfy_local.storage.get_node_instances()
        print json.dumps(cfy_local.outputs(), sort_keys=True, indent=20)
        print instance
        print json.dumps(instance, sort_keys=True, indent=20)
        print ("cfy_local.outputs()")
        print (json.dumps(cfy_local.outputs(), indent=4))
        print ('Press Enter for uninstall')
        raw_input('Press Enter')

        cfy_local.execute('uninstall')
        print("outputs after uninstall")
        instance = cfy_local.storage.get_node_instances()
        print (cfy_local.outputs())
        print instance
        print json.dumps(instance, sort_keys=True, indent=20)
