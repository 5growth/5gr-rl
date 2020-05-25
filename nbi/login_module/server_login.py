# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
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

# Author: Luca Vettori


from flask import render_template, session
from functools import wraps


def login_passed(f):
    """
    Decorate method to check if the user is logged in the session.
    If not it return to the html login page
    :param f: function
    :return: decorate the parameter in case of success, or rendering of a html login page in case of not logged
    """
    @wraps(f)
    def decorate_func(*args, **kwargs):
        # uncomment/comment the below code to activate/deactivate login system access
        if not session.get('logged_in'):
            # link = request.path
            # return render_template('login.html', next=link)
            return render_template('login.html')
        else:
            return f(*args, **kwargs)

    return decorate_func
