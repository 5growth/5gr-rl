# coding: utf-8

"""
    5Growth RL RA API

    RESTful API for the 5Gr RL RA. Find more at http://5growth.eu  # noqa: E501

    OpenAPI spec version: 1.1.1
    Contact: cnd@lists.cttc.es
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


import pprint
import re  # noqa: F401

import six


class CompRouteOutputItemNoPath(object):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    """
    Attributes:
      swagger_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    swagger_types = {
        'issue': 'int'
    }

    attribute_map = {
        'issue': 'issue'
    }

    def __init__(self, issue=None):  # noqa: E501
        """CompRouteOutputItemNoPath - a model defined in Swagger"""  # noqa: E501

        self._issue = None
        self.discriminator = None

        self.issue = issue

    @property
    def issue(self):
        """Gets the issue of this CompRouteOutputItemNoPath.  # noqa: E501

        Value identifying the reason why no path was provided. 1 means no path satisfying the constraints  # noqa: E501

        :return: The issue of this CompRouteOutputItemNoPath.  # noqa: E501
        :rtype: int
        """
        return self._issue

    @issue.setter
    def issue(self, issue):
        """Sets the issue of this CompRouteOutputItemNoPath.

        Value identifying the reason why no path was provided. 1 means no path satisfying the constraints  # noqa: E501

        :param issue: The issue of this CompRouteOutputItemNoPath.  # noqa: E501
        :type: int
        """
        if issue is None:
            raise ValueError("Invalid value for `issue`, must not be `None`")  # noqa: E501

        self._issue = issue

    def to_dict(self):
        """Returns the model properties as a dict"""
        result = {}

        for attr, _ in six.iteritems(self.swagger_types):
            value = getattr(self, attr)
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: x.to_dict() if hasattr(x, "to_dict") else x,
                    value
                ))
            elif hasattr(value, "to_dict"):
                result[attr] = value.to_dict()
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], item[1].to_dict())
                    if hasattr(item[1], "to_dict") else item,
                    value.items()
                ))
            else:
                result[attr] = value
        if issubclass(CompRouteOutputItemNoPath, dict):
            for key, value in self.items():
                result[key] = value

        return result

    def to_str(self):
        """Returns the string representation of the model"""
        return pprint.pformat(self.to_dict())

    def __repr__(self):
        """For `print` and `pprint`"""
        return self.to_str()

    def __eq__(self, other):
        """Returns true if both objects are equal"""
        if not isinstance(other, CompRouteOutputItemNoPath):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other