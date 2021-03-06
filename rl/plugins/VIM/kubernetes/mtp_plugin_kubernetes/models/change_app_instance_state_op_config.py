# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class ChangeAppInstanceStateOpConfig(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, min_graceful_stop_timeout: float=None, mac_recommended_graceful_stop_timeout: float=None):  # noqa: E501
        """ChangeAppInstanceStateOpConfig - a model defined in Swagger

        :param min_graceful_stop_timeout: The min_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.  # noqa: E501
        :type min_graceful_stop_timeout: float
        :param mac_recommended_graceful_stop_timeout: The mac_recommended_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.  # noqa: E501
        :type mac_recommended_graceful_stop_timeout: float
        """
        self.swagger_types = {
            'min_graceful_stop_timeout': float,
            'mac_recommended_graceful_stop_timeout': float
        }

        self.attribute_map = {
            'min_graceful_stop_timeout': 'minGracefulStopTimeout',
            'mac_recommended_graceful_stop_timeout': 'macRecommendedGracefulStopTimeout'
        }

        self._min_graceful_stop_timeout = min_graceful_stop_timeout
        self._mac_recommended_graceful_stop_timeout = mac_recommended_graceful_stop_timeout

    @classmethod
    def from_dict(cls, dikt) -> 'ChangeAppInstanceStateOpConfig':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The ChangeAppInstanceStateOpConfig of this ChangeAppInstanceStateOpConfig.  # noqa: E501
        :rtype: ChangeAppInstanceStateOpConfig
        """
        return util.deserialize_model(dikt, cls)

    @property
    def min_graceful_stop_timeout(self) -> float:
        """Gets the min_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.

        Minimum timeout value for graceful stop of application instance.  # noqa: E501

        :return: The min_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.
        :rtype: float
        """
        return self._min_graceful_stop_timeout

    @min_graceful_stop_timeout.setter
    def min_graceful_stop_timeout(self, min_graceful_stop_timeout: float):
        """Sets the min_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.

        Minimum timeout value for graceful stop of application instance.  # noqa: E501

        :param min_graceful_stop_timeout: The min_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.
        :type min_graceful_stop_timeout: float
        """
        if min_graceful_stop_timeout is None:
            raise ValueError("Invalid value for `min_graceful_stop_timeout`, must not be `None`")  # noqa: E501

        self._min_graceful_stop_timeout = min_graceful_stop_timeout

    @property
    def mac_recommended_graceful_stop_timeout(self) -> float:
        """Gets the mac_recommended_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.

        Maximum recommended timeout value that can be needed to gracefully stop an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.  # noqa: E501

        :return: The mac_recommended_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.
        :rtype: float
        """
        return self._mac_recommended_graceful_stop_timeout

    @mac_recommended_graceful_stop_timeout.setter
    def mac_recommended_graceful_stop_timeout(self, mac_recommended_graceful_stop_timeout: float):
        """Sets the mac_recommended_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.

        Maximum recommended timeout value that can be needed to gracefully stop an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.  # noqa: E501

        :param mac_recommended_graceful_stop_timeout: The mac_recommended_graceful_stop_timeout of this ChangeAppInstanceStateOpConfig.
        :type mac_recommended_graceful_stop_timeout: float
        """

        self._mac_recommended_graceful_stop_timeout = mac_recommended_graceful_stop_timeout
