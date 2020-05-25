# Copyright (C) 2018 CTTC/CERCA
# License: To be defined. Currently use is restricted to partners of the 5G-Transformer project,
#          http://5g-transformer.eu/, no use or redistribution of any kind outside the 5G-Transformer project is
#          allowed.
# Disclaimer: this software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
# either express or implied.
from http import HTTPStatus


def error400(message):
    return {"message": message, "status": 400, "type": "Bad request"}, 400


def error401(message):
    return {"message": message, "status": 401, "type": "Unauthorized"}, 401


def error403(message):
    return {"message": message, "status": 403, "type": "Forbidden"}, 403


def error404(message):
    return {"message": message, "status": 404, "type": "Not Found"}, 404


def error501(message):
    return {"message": message, "status": 501, "type": "Not implemented"}, 501


def error_xxx(message, code):
    return {"message": message, "status": code, "type": HTTPStatus(code).phrase}, code
