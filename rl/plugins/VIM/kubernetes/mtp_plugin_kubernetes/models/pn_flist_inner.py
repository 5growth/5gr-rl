# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.pnf_info import PNFInfo


class PNFlistInner(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, pnfinfo: PNFInfo=None):  # noqa: E501
        """PNFlistInner - a model defined in Swagger

        :param pnfinfo: The pnfinfo of this PNFlistInner.  # noqa: E501
        :type pnfinfo: PNFInfo
        """
        self.swagger_types = {
            'pnfinfo': PNFInfo
        }

        self.attribute_map = {
            'pnfinfo': 'pnfinfo'
        }

        self._pnfinfo = pnfinfo

    @classmethod
    def from_dict(cls, dikt) -> 'PNFlistInner':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The PNFlist_inner of this PNFlistInner.  # noqa: E501
        :rtype: PNFlistInner
        """
        return util.deserialize_model(dikt, cls)

    @property
    def pnfinfo(self) -> PNFInfo:
        """Gets the pnfinfo of this PNFlistInner.


        :return: The pnfinfo of this PNFlistInner.
        :rtype: PNFInfo
        """
        return self._pnfinfo

    @pnfinfo.setter
    def pnfinfo(self, pnfinfo: PNFInfo):
        """Sets the pnfinfo of this PNFlistInner.


        :param pnfinfo: The pnfinfo of this PNFlistInner.
        :type pnfinfo: PNFInfo
        """
        if pnfinfo is None:
            raise ValueError("Invalid value for `pnfinfo`, must not be `None`")  # noqa: E501

        self._pnfinfo = pnfinfo
