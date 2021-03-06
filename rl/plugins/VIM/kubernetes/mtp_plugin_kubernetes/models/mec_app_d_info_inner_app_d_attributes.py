# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class MECAppDInfoInnerAppDAttributes(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, app_d_info: str=None, mec_app_d_id: str=None):  # noqa: E501
        """MECAppDInfoInnerAppDAttributes - a model defined in Swagger

        :param app_d_info: The app_d_info of this MECAppDInfoInnerAppDAttributes.  # noqa: E501
        :type app_d_info: str
        :param mec_app_d_id: The mec_app_d_id of this MECAppDInfoInnerAppDAttributes.  # noqa: E501
        :type mec_app_d_id: str
        """
        self.swagger_types = {
            'app_d_info': str,
            'mec_app_d_id': str
        }

        self.attribute_map = {
            'app_d_info': 'appDInfo',
            'mec_app_d_id': 'mecAppDId'
        }

        self._app_d_info = app_d_info
        self._mec_app_d_id = mec_app_d_id

    @classmethod
    def from_dict(cls, dikt) -> 'MECAppDInfoInnerAppDAttributes':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The MECAppDInfo_inner_appDAttributes of this MECAppDInfoInnerAppDAttributes.  # noqa: E501
        :rtype: MECAppDInfoInnerAppDAttributes
        """
        return util.deserialize_model(dikt, cls)

    @property
    def app_d_info(self) -> str:
        """Gets the app_d_info of this MECAppDInfoInnerAppDAttributes.

        It provides information about MEC APPD (TBD)  # noqa: E501

        :return: The app_d_info of this MECAppDInfoInnerAppDAttributes.
        :rtype: str
        """
        return self._app_d_info

    @app_d_info.setter
    def app_d_info(self, app_d_info: str):
        """Sets the app_d_info of this MECAppDInfoInnerAppDAttributes.

        It provides information about MEC APPD (TBD)  # noqa: E501

        :param app_d_info: The app_d_info of this MECAppDInfoInnerAppDAttributes.
        :type app_d_info: str
        """
        if app_d_info is None:
            raise ValueError("Invalid value for `app_d_info`, must not be `None`")  # noqa: E501

        self._app_d_info = app_d_info

    @property
    def mec_app_d_id(self) -> str:
        """Gets the mec_app_d_id of this MECAppDInfoInnerAppDAttributes.

        Identification of the MEC APPdId  # noqa: E501

        :return: The mec_app_d_id of this MECAppDInfoInnerAppDAttributes.
        :rtype: str
        """
        return self._mec_app_d_id

    @mec_app_d_id.setter
    def mec_app_d_id(self, mec_app_d_id: str):
        """Sets the mec_app_d_id of this MECAppDInfoInnerAppDAttributes.

        Identification of the MEC APPdId  # noqa: E501

        :param mec_app_d_id: The mec_app_d_id of this MECAppDInfoInnerAppDAttributes.
        :type mec_app_d_id: str
        """
        if mec_app_d_id is None:
            raise ValueError("Invalid value for `mec_app_d_id`, must not be `None`")  # noqa: E501

        self._mec_app_d_id = mec_app_d_id
