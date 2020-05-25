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


class CompRouteInputInterWanLinks(object):
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
        'a_wim_id': 'str',
        'z_wim_id': 'str',
        'a_pe_id': 'str',
        'z_pe_id': 'str',
        'a_link_id': 'int',
        'z_link_id': 'int',
        'netw_link_qo_s': 'NetwLinkAtts'
    }

    attribute_map = {
        'a_wim_id': 'aWimId',
        'z_wim_id': 'zWimId',
        'a_pe_id': 'aPEId',
        'z_pe_id': 'zPEId',
        'a_link_id': 'aLinkId',
        'z_link_id': 'zLinkId',
        'netw_link_qo_s': 'netwLinkQoS'
    }

    def __init__(self, a_wim_id=None, z_wim_id=None, a_pe_id=None, z_pe_id=None, a_link_id=None, z_link_id=None, netw_link_qo_s=None):  # noqa: E501
        """CompRouteInputInterWanLinks - a model defined in Swagger"""  # noqa: E501

        self._a_wim_id = None
        self._z_wim_id = None
        self._a_pe_id = None
        self._z_pe_id = None
        self._a_link_id = None
        self._z_link_id = None
        self._netw_link_qo_s = None
        self.discriminator = None

        self.a_wim_id = a_wim_id
        self.z_wim_id = z_wim_id
        self.a_pe_id = a_pe_id
        self.z_pe_id = z_pe_id
        self.a_link_id = a_link_id
        self.z_link_id = z_link_id
        self.netw_link_qo_s = netw_link_qo_s

    @property
    def a_wim_id(self):
        """Gets the a_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501

        Identifier of outgoing WIM in the inter-WAN link  # noqa: E501

        :return: The a_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: str
        """
        return self._a_wim_id

    @a_wim_id.setter
    def a_wim_id(self, a_wim_id):
        """Sets the a_wim_id of this CompRouteInputInterWanLinks.

        Identifier of outgoing WIM in the inter-WAN link  # noqa: E501

        :param a_wim_id: The a_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: str
        """
        if a_wim_id is None:
            raise ValueError("Invalid value for `a_wim_id`, must not be `None`")  # noqa: E501

        self._a_wim_id = a_wim_id

    @property
    def z_wim_id(self):
        """Gets the z_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501

        Identifier of incoming WIM in the inter-WAN link  # noqa: E501

        :return: The z_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: str
        """
        return self._z_wim_id

    @z_wim_id.setter
    def z_wim_id(self, z_wim_id):
        """Sets the z_wim_id of this CompRouteInputInterWanLinks.

        Identifier of incoming WIM in the inter-WAN link  # noqa: E501

        :param z_wim_id: The z_wim_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: str
        """
        if z_wim_id is None:
            raise ValueError("Invalid value for `z_wim_id`, must not be `None`")  # noqa: E501

        self._z_wim_id = z_wim_id

    @property
    def a_pe_id(self):
        """Gets the a_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501

        outgoing PE Id  # noqa: E501

        :return: The a_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: str
        """
        return self._a_pe_id

    @a_pe_id.setter
    def a_pe_id(self, a_pe_id):
        """Sets the a_pe_id of this CompRouteInputInterWanLinks.

        outgoing PE Id  # noqa: E501

        :param a_pe_id: The a_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: str
        """
        if a_pe_id is None:
            raise ValueError("Invalid value for `a_pe_id`, must not be `None`")  # noqa: E501

        self._a_pe_id = a_pe_id

    @property
    def z_pe_id(self):
        """Gets the z_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501

        incoming PE Id  # noqa: E501

        :return: The z_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: str
        """
        return self._z_pe_id

    @z_pe_id.setter
    def z_pe_id(self, z_pe_id):
        """Sets the z_pe_id of this CompRouteInputInterWanLinks.

        incoming PE Id  # noqa: E501

        :param z_pe_id: The z_pe_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: str
        """
        if z_pe_id is None:
            raise ValueError("Invalid value for `z_pe_id`, must not be `None`")  # noqa: E501

        self._z_pe_id = z_pe_id

    @property
    def a_link_id(self):
        """Gets the a_link_id of this CompRouteInputInterWanLinks.  # noqa: E501

        Link Id. attached to the aPEId  # noqa: E501

        :return: The a_link_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: int
        """
        return self._a_link_id

    @a_link_id.setter
    def a_link_id(self, a_link_id):
        """Sets the a_link_id of this CompRouteInputInterWanLinks.

        Link Id. attached to the aPEId  # noqa: E501

        :param a_link_id: The a_link_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: int
        """
        if a_link_id is None:
            raise ValueError("Invalid value for `a_link_id`, must not be `None`")  # noqa: E501

        self._a_link_id = a_link_id

    @property
    def z_link_id(self):
        """Gets the z_link_id of this CompRouteInputInterWanLinks.  # noqa: E501

        Link Id. attached to the zPEId  # noqa: E501

        :return: The z_link_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: int
        """
        return self._z_link_id

    @z_link_id.setter
    def z_link_id(self, z_link_id):
        """Sets the z_link_id of this CompRouteInputInterWanLinks.

        Link Id. attached to the zPEId  # noqa: E501

        :param z_link_id: The z_link_id of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: int
        """
        if z_link_id is None:
            raise ValueError("Invalid value for `z_link_id`, must not be `None`")  # noqa: E501

        self._z_link_id = z_link_id

    @property
    def netw_link_qo_s(self):
        """Gets the netw_link_qo_s of this CompRouteInputInterWanLinks.  # noqa: E501

        Specifies the link attributes  # noqa: E501

        :return: The netw_link_qo_s of this CompRouteInputInterWanLinks.  # noqa: E501
        :rtype: NetwLinkAtts
        """
        return self._netw_link_qo_s

    @netw_link_qo_s.setter
    def netw_link_qo_s(self, netw_link_qo_s):
        """Sets the netw_link_qo_s of this CompRouteInputInterWanLinks.

        Specifies the link attributes  # noqa: E501

        :param netw_link_qo_s: The netw_link_qo_s of this CompRouteInputInterWanLinks.  # noqa: E501
        :type: NetwLinkAtts
        """
        if netw_link_qo_s is None:
            raise ValueError("Invalid value for `netw_link_qo_s`, must not be `None`")  # noqa: E501

        self._netw_link_qo_s = netw_link_qo_s

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
        if issubclass(CompRouteInputInterWanLinks, dict):
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
        if not isinstance(other, CompRouteInputInterWanLinks):
            return False

        return self.__dict__ == other.__dict__

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        return not self == other