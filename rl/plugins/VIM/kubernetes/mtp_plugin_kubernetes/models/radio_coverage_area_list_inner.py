# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.radio_coverage_area_list_inner_radio_coverage_area_info import \
    RadioCoverageAreaListInnerRadioCoverageAreaInfo


class RadioCoverageAreaListInner(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, radio_coverage_area_info: RadioCoverageAreaListInnerRadioCoverageAreaInfo=None):  # noqa: E501
        """RadioCoverageAreaListInner - a model defined in Swagger

        :param radio_coverage_area_info: The radio_coverage_area_info of this RadioCoverageAreaListInner.  # noqa: E501
        :type radio_coverage_area_info: RadioCoverageAreaListInnerRadioCoverageAreaInfo
        """
        self.swagger_types = {
            'radio_coverage_area_info': RadioCoverageAreaListInnerRadioCoverageAreaInfo
        }

        self.attribute_map = {
            'radio_coverage_area_info': 'radioCoverageAreaInfo'
        }

        self._radio_coverage_area_info = radio_coverage_area_info

    @classmethod
    def from_dict(cls, dikt) -> 'RadioCoverageAreaListInner':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The RadioCoverageAreaList_inner of this RadioCoverageAreaListInner.  # noqa: E501
        :rtype: RadioCoverageAreaListInner
        """
        return util.deserialize_model(dikt, cls)

    @property
    def radio_coverage_area_info(self) -> RadioCoverageAreaListInnerRadioCoverageAreaInfo:
        """Gets the radio_coverage_area_info of this RadioCoverageAreaListInner.


        :return: The radio_coverage_area_info of this RadioCoverageAreaListInner.
        :rtype: RadioCoverageAreaListInnerRadioCoverageAreaInfo
        """
        return self._radio_coverage_area_info

    @radio_coverage_area_info.setter
    def radio_coverage_area_info(self, radio_coverage_area_info: RadioCoverageAreaListInnerRadioCoverageAreaInfo):
        """Sets the radio_coverage_area_info of this RadioCoverageAreaListInner.


        :param radio_coverage_area_info: The radio_coverage_area_info of this RadioCoverageAreaListInner.
        :type radio_coverage_area_info: RadioCoverageAreaListInnerRadioCoverageAreaInfo
        """
        if radio_coverage_area_info is None:
            raise ValueError("Invalid value for `radio_coverage_area_info`, must not be `None`")  # noqa: E501

        self._radio_coverage_area_info = radio_coverage_area_info
