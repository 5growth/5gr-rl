# -*- coding: utf-8 -*-

"""
Copyright 2018 Pantelis Frangoudis, EURECOM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""

from mecdb import *

configuration = {
  "db_host": "127.0.0.1",
  "db_port": 27017,
  "db_name": "mtpmec"
}

md = MECDB(configuration)

li = LocationInfo(43.6143899, 7.068931, 0.0, 10)
mep = MEP("2", "http://127.0.0.1:13000", {"type": "eurecom"})
region = Region("2", "Sophia-Antipolis", mep, li)
md.insert_mep(mep)
md.insert_region(region)

print(md.get_regions())

