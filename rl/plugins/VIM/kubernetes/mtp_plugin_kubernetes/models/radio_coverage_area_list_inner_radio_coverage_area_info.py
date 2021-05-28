# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.location_info import LocationInfo


class RadioCoverageAreaListInnerRadioCoverageAreaInfo(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, coverage_area_id: str=None, coverage_area_goegraphical_info: str=None, coverage_area_min_bandwidth: float=None, coverage_area_max_bandwidth: float=None, coverage_area_delay: float=None, location_info: LocationInfo=None):  # noqa: E501
        """RadioCoverageAreaListInnerRadioCoverageAreaInfo - a model defined in Swagger

        :param coverage_area_id: The coverage_area_id of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type coverage_area_id: str
        :param coverage_area_goegraphical_info: The coverage_area_goegraphical_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type coverage_area_goegraphical_info: str
        :param coverage_area_min_bandwidth: The coverage_area_min_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type coverage_area_min_bandwidth: float
        :param coverage_area_max_bandwidth: The coverage_area_max_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type coverage_area_max_bandwidth: float
        :param coverage_area_delay: The coverage_area_delay of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type coverage_area_delay: float
        :param location_info: The location_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :type location_info: LocationInfo
        """
        self.swagger_types = {
            'coverage_area_id': str,
            'coverage_area_goegraphical_info': str,
            'coverage_area_min_bandwidth': float,
            'coverage_area_max_bandwidth': float,
            'coverage_area_delay': float,
            'location_info': LocationInfo
        }

        self.attribute_map = {
            'coverage_area_id': 'coverageAreaId',
            'coverage_area_goegraphical_info': 'coverageAreaGoegraphicalInfo',
            'coverage_area_min_bandwidth': 'coverageAreaMinBandwidth',
            'coverage_area_max_bandwidth': 'coverageAreaMaxBandwidth',
            'coverage_area_delay': 'coverageAreaDelay',
            'location_info': 'locationInfo'
        }

        self._coverage_area_id = coverage_area_id
        self._coverage_area_goegraphical_info = coverage_area_goegraphical_info
        self._coverage_area_min_bandwidth = coverage_area_min_bandwidth
        self._coverage_area_max_bandwidth = coverage_area_max_bandwidth
        self._coverage_area_delay = coverage_area_delay
        self._location_info = location_info

    @classmethod
    def from_dict(cls, dikt) -> 'RadioCoverageAreaListInnerRadioCoverageAreaInfo':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The RadioCoverageAreaList_inner_radioCoverageAreaInfo of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.  # noqa: E501
        :rtype: RadioCoverageAreaListInnerRadioCoverageAreaInfo
        """
        return util.deserialize_model(dikt, cls)

    @property
    def coverage_area_id(self) -> str:
        """Gets the coverage_area_id of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        (numbered) Identifier of the Radio Coverage Area  # noqa: E501

        :return: The coverage_area_id of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: str
        """
        return self._coverage_area_id

    @coverage_area_id.setter
    def coverage_area_id(self, coverage_area_id: str):
        """Sets the coverage_area_id of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        (numbered) Identifier of the Radio Coverage Area  # noqa: E501

        :param coverage_area_id: The coverage_area_id of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type coverage_area_id: str
        """
        if coverage_area_id is None:
            raise ValueError("Invalid value for `coverage_area_id`, must not be `None`")  # noqa: E501

        self._coverage_area_id = coverage_area_id

    @property
    def coverage_area_goegraphical_info(self) -> str:
        """Gets the coverage_area_goegraphical_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Identifier of the Radio Coverage Area Geographical Information  # noqa: E501

        :return: The coverage_area_goegraphical_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: str
        """
        return self._coverage_area_goegraphical_info

    @coverage_area_goegraphical_info.setter
    def coverage_area_goegraphical_info(self, coverage_area_goegraphical_info: str):
        """Sets the coverage_area_goegraphical_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Identifier of the Radio Coverage Area Geographical Information  # noqa: E501

        :param coverage_area_goegraphical_info: The coverage_area_goegraphical_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type coverage_area_goegraphical_info: str
        """

        self._coverage_area_goegraphical_info = coverage_area_goegraphical_info

    @property
    def coverage_area_min_bandwidth(self) -> float:
        """Gets the coverage_area_min_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Minimum Bandwidth of the Radio Coverage Area  # noqa: E501

        :return: The coverage_area_min_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: float
        """
        return self._coverage_area_min_bandwidth

    @coverage_area_min_bandwidth.setter
    def coverage_area_min_bandwidth(self, coverage_area_min_bandwidth: float):
        """Sets the coverage_area_min_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Minimum Bandwidth of the Radio Coverage Area  # noqa: E501

        :param coverage_area_min_bandwidth: The coverage_area_min_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type coverage_area_min_bandwidth: float
        """
        if coverage_area_min_bandwidth is None:
            raise ValueError("Invalid value for `coverage_area_min_bandwidth`, must not be `None`")  # noqa: E501

        self._coverage_area_min_bandwidth = coverage_area_min_bandwidth

    @property
    def coverage_area_max_bandwidth(self) -> float:
        """Gets the coverage_area_max_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Maximum Bandwidth of the Radio Coverage Area  # noqa: E501

        :return: The coverage_area_max_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: float
        """
        return self._coverage_area_max_bandwidth

    @coverage_area_max_bandwidth.setter
    def coverage_area_max_bandwidth(self, coverage_area_max_bandwidth: float):
        """Sets the coverage_area_max_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Maximum Bandwidth of the Radio Coverage Area  # noqa: E501

        :param coverage_area_max_bandwidth: The coverage_area_max_bandwidth of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type coverage_area_max_bandwidth: float
        """
        if coverage_area_max_bandwidth is None:
            raise ValueError("Invalid value for `coverage_area_max_bandwidth`, must not be `None`")  # noqa: E501

        self._coverage_area_max_bandwidth = coverage_area_max_bandwidth

    @property
    def coverage_area_delay(self) -> float:
        """Gets the coverage_area_delay of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Minimum delay supported by the Radio Coverage Area.  # noqa: E501

        :return: The coverage_area_delay of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: float
        """
        return self._coverage_area_delay

    @coverage_area_delay.setter
    def coverage_area_delay(self, coverage_area_delay: float):
        """Sets the coverage_area_delay of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.

        Minimum delay supported by the Radio Coverage Area.  # noqa: E501

        :param coverage_area_delay: The coverage_area_delay of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type coverage_area_delay: float
        """
        if coverage_area_delay is None:
            raise ValueError("Invalid value for `coverage_area_delay`, must not be `None`")  # noqa: E501

        self._coverage_area_delay = coverage_area_delay

    @property
    def location_info(self) -> LocationInfo:
        """Gets the location_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.


        :return: The location_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :rtype: LocationInfo
        """
        return self._location_info

    @location_info.setter
    def location_info(self, location_info: LocationInfo):
        """Sets the location_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.


        :param location_info: The location_info of this RadioCoverageAreaListInnerRadioCoverageAreaInfo.
        :type location_info: LocationInfo
        """
        if location_info is None:
            raise ValueError("Invalid value for `location_info`, must not be `None`")  # noqa: E501

        self._location_info = location_info