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
# -*- coding: utf-8 -*

import json
import requests


# Resources

# # Network


def get_images():
    r = requests.post(
        'http://127.0.0.1:8000/software_images/query')

    if r.status_code != 200:
        return False

    images = json.loads(r.text)

    return images


def create_images():
    payload = {
        'name': 'cdasilva-image',
        'provider': 'cdasilva',
        'visibility': 'private',
        'version': 'v1',
        'softwareImage': '/home/cdasilva/alpine-standard-3.8.0-x86_64.iso',
    }
    files = {
        'softwareImage': open(
            '/home/cdasilva/alpine-standard-3.8.0-x86_64.iso', 'rb')
    }
    r = requests.post(
        'http://127.0.0.1:8000/software_images', files=files, data=payload)
    image = json.loads(r.text)

    if r.status_code != 201:
        return False

    return image['id']


def delete_image(image_id):
    r = requests.delete('http://127.0.0.1:8000/software_images/' + image_id)

    deleted_resources = json.loads(r.text)

    return r.status_code == 200 and image_id in deleted_resources


def test_images():
    image_id = create_images()

    assert get_images()

    assert delete_image(image_id)
