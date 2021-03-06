# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.nfvi_pops_inner_nfvi_pop_attributes import NfviPopsInnerNfviPopAttributes


class NfviPopsInner(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, nfvi_pop_attributes: NfviPopsInnerNfviPopAttributes=None):  # noqa: E501
        """NfviPopsInner - a model defined in Swagger

        :param nfvi_pop_attributes: The nfvi_pop_attributes of this NfviPopsInner.  # noqa: E501
        :type nfvi_pop_attributes: NfviPopsInnerNfviPopAttributes
        """
        self.swagger_types = {
            'nfvi_pop_attributes': NfviPopsInnerNfviPopAttributes
        }

        self.attribute_map = {
            'nfvi_pop_attributes': 'nfviPopAttributes'
        }

        self._nfvi_pop_attributes = nfvi_pop_attributes

    @classmethod
    def from_dict(cls, dikt) -> 'NfviPopsInner':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The NfviPops_inner of this NfviPopsInner.  # noqa: E501
        :rtype: NfviPopsInner
        """
        return util.deserialize_model(dikt, cls)

    @property
    def nfvi_pop_attributes(self) -> NfviPopsInnerNfviPopAttributes:
        """Gets the nfvi_pop_attributes of this NfviPopsInner.


        :return: The nfvi_pop_attributes of this NfviPopsInner.
        :rtype: NfviPopsInnerNfviPopAttributes
        """
        return self._nfvi_pop_attributes

    @nfvi_pop_attributes.setter
    def nfvi_pop_attributes(self, nfvi_pop_attributes: NfviPopsInnerNfviPopAttributes):
        """Sets the nfvi_pop_attributes of this NfviPopsInner.


        :param nfvi_pop_attributes: The nfvi_pop_attributes of this NfviPopsInner.
        :type nfvi_pop_attributes: NfviPopsInnerNfviPopAttributes
        """
        if nfvi_pop_attributes is None:
            raise ValueError("Invalid value for `nfvi_pop_attributes`, must not be `None`")  # noqa: E501

        self._nfvi_pop_attributes = nfvi_pop_attributes
