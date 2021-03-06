# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.nfvi_pops_inner_nfvi_pop_attributes_covrage_location_info import \
    NfviPopsInnerNfviPopAttributesCovrageLocationInfo


class NfviPopsInnerNfviPopAttributesRadioCoverageAreas(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, coverage_area_id: str=None, covrage_location_info: NfviPopsInnerNfviPopAttributesCovrageLocationInfo=None, min_bandwidth: float=None, max_bandwidth: float=None, delay: float=None):  # noqa: E501
        """NfviPopsInnerNfviPopAttributesRadioCoverageAreas - a model defined in Swagger

        :param coverage_area_id: The coverage_area_id of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :type coverage_area_id: str
        :param covrage_location_info: The covrage_location_info of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :type covrage_location_info: NfviPopsInnerNfviPopAttributesCovrageLocationInfo
        :param min_bandwidth: The min_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :type min_bandwidth: float
        :param max_bandwidth: The max_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :type max_bandwidth: float
        :param delay: The delay of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :type delay: float
        """
        self.swagger_types = {
            'coverage_area_id': str,
            'covrage_location_info': NfviPopsInnerNfviPopAttributesCovrageLocationInfo,
            'min_bandwidth': float,
            'max_bandwidth': float,
            'delay': float
        }

        self.attribute_map = {
            'coverage_area_id': 'coverageAreaId',
            'covrage_location_info': 'covrageLocationInfo',
            'min_bandwidth': 'minBandwidth',
            'max_bandwidth': 'maxBandwidth',
            'delay': 'delay'
        }

        self._coverage_area_id = coverage_area_id
        self._covrage_location_info = covrage_location_info
        self._min_bandwidth = min_bandwidth
        self._max_bandwidth = max_bandwidth
        self._delay = delay

    @classmethod
    def from_dict(cls, dikt) -> 'NfviPopsInnerNfviPopAttributesRadioCoverageAreas':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The NfviPops_inner_nfviPopAttributes_RadioCoverageAreas of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.  # noqa: E501
        :rtype: NfviPopsInnerNfviPopAttributesRadioCoverageAreas
        """
        return util.deserialize_model(dikt, cls)

    @property
    def coverage_area_id(self) -> str:
        """Gets the coverage_area_id of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Coverage Area Identifier supported by Radio PoP  # noqa: E501

        :return: The coverage_area_id of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :rtype: str
        """
        return self._coverage_area_id

    @coverage_area_id.setter
    def coverage_area_id(self, coverage_area_id: str):
        """Sets the coverage_area_id of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Coverage Area Identifier supported by Radio PoP  # noqa: E501

        :param coverage_area_id: The coverage_area_id of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :type coverage_area_id: str
        """

        self._coverage_area_id = coverage_area_id

    @property
    def covrage_location_info(self) -> NfviPopsInnerNfviPopAttributesCovrageLocationInfo:
        """Gets the covrage_location_info of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.


        :return: The covrage_location_info of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :rtype: NfviPopsInnerNfviPopAttributesCovrageLocationInfo
        """
        return self._covrage_location_info

    @covrage_location_info.setter
    def covrage_location_info(self, covrage_location_info: NfviPopsInnerNfviPopAttributesCovrageLocationInfo):
        """Sets the covrage_location_info of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.


        :param covrage_location_info: The covrage_location_info of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :type covrage_location_info: NfviPopsInnerNfviPopAttributesCovrageLocationInfo
        """

        self._covrage_location_info = covrage_location_info

    @property
    def min_bandwidth(self) -> float:
        """Gets the min_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Minimummum bandwidth provided by the coverage area  # noqa: E501

        :return: The min_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :rtype: float
        """
        return self._min_bandwidth

    @min_bandwidth.setter
    def min_bandwidth(self, min_bandwidth: float):
        """Sets the min_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Minimummum bandwidth provided by the coverage area  # noqa: E501

        :param min_bandwidth: The min_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :type min_bandwidth: float
        """

        self._min_bandwidth = min_bandwidth

    @property
    def max_bandwidth(self) -> float:
        """Gets the max_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Maximum bandwidth provided by the coverage area  # noqa: E501

        :return: The max_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :rtype: float
        """
        return self._max_bandwidth

    @max_bandwidth.setter
    def max_bandwidth(self, max_bandwidth: float):
        """Sets the max_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        Maximum bandwidth provided by the coverage area  # noqa: E501

        :param max_bandwidth: The max_bandwidth of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :type max_bandwidth: float
        """

        self._max_bandwidth = max_bandwidth

    @property
    def delay(self) -> float:
        """Gets the delay of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        minimum delay guaranteed by the coverage area  # noqa: E501

        :return: The delay of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :rtype: float
        """
        return self._delay

    @delay.setter
    def delay(self, delay: float):
        """Sets the delay of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.

        minimum delay guaranteed by the coverage area  # noqa: E501

        :param delay: The delay of this NfviPopsInnerNfviPopAttributesRadioCoverageAreas.
        :type delay: float
        """

        self._delay = delay
