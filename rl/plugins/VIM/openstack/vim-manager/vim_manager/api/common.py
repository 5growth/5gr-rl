# Module file: common.py
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

def compare_value (op, value1, value2):
    # import ipdb; ipdb.set_trace()
    if (op == 'eq'):
        return (value1 == value2)
    elif (op == 'ne'):
        return (value1 != value2)
    elif  (op == 'gt'):
        return (value1 > value2)
    elif  (op == 'ge'):
        return (value1 >= value2)
    elif  (op == 'lt'):
        return (value1 < value2)
    elif  (op == 'le'):
        return (value1 <= value2)
    else:
        return '-1'