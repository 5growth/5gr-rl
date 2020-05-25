# -*- encoding: utf-8 -*-
# Copyright (c) 2015 b<>com
# Copyright (c) 2016 Intel Corp
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
# implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import pytest

from vim_manager.conf import opts


@pytest.fixture(scope="module")
def conf_sections():
    return [
        'DEFAULT',
        'auth',
        'clients_auth',
        'default',
        'discovery',
        'glance_client',
        'identity',
        'nova_client',
        'opts',
    ]


def test_run_list_opts(conf_sections):
    actual_sections = dict(opts.list_opts())

    assert actual_sections is not None
    for section_name, options in actual_sections.items():
        assert section_name in conf_sections
        assert isinstance(options, (list, tuple))
        assert options
