# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class PNFInfo(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self):  # noqa: E501
        """PNFInfo - a model defined in Swagger

        """
        self.swagger_types = {
        }

        self.attribute_map = {
        }

    @classmethod
    def from_dict(cls, dikt) -> 'PNFInfo':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The PNFInfo of this PNFInfo.  # noqa: E501
        :rtype: PNFInfo
        """
        return util.deserialize_model(dikt, cls)
