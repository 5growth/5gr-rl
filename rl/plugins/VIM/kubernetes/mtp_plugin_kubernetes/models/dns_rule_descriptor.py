# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class DNSRuleDescriptor(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, dns_rule_id: str=None, domain_name: str=None, ip_address_type: str=None, ip_address: str=None, ttl: float=None):  # noqa: E501
        """DNSRuleDescriptor - a model defined in Swagger

        :param dns_rule_id: The dns_rule_id of this DNSRuleDescriptor.  # noqa: E501
        :type dns_rule_id: str
        :param domain_name: The domain_name of this DNSRuleDescriptor.  # noqa: E501
        :type domain_name: str
        :param ip_address_type: The ip_address_type of this DNSRuleDescriptor.  # noqa: E501
        :type ip_address_type: str
        :param ip_address: The ip_address of this DNSRuleDescriptor.  # noqa: E501
        :type ip_address: str
        :param ttl: The ttl of this DNSRuleDescriptor.  # noqa: E501
        :type ttl: float
        """
        self.swagger_types = {
            'dns_rule_id': str,
            'domain_name': str,
            'ip_address_type': str,
            'ip_address': str,
            'ttl': float
        }

        self.attribute_map = {
            'dns_rule_id': 'dnsRuleId',
            'domain_name': 'domainName',
            'ip_address_type': 'ipAddressType',
            'ip_address': 'ipAddress',
            'ttl': 'ttl'
        }

        self._dns_rule_id = dns_rule_id
        self._domain_name = domain_name
        self._ip_address_type = ip_address_type
        self._ip_address = ip_address
        self._ttl = ttl

    @classmethod
    def from_dict(cls, dikt) -> 'DNSRuleDescriptor':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The DNSRuleDescriptor of this DNSRuleDescriptor.  # noqa: E501
        :rtype: DNSRuleDescriptor
        """
        return util.deserialize_model(dikt, cls)

    @property
    def dns_rule_id(self) -> str:
        """Gets the dns_rule_id of this DNSRuleDescriptor.

        Identifies the DNS rule.  # noqa: E501

        :return: The dns_rule_id of this DNSRuleDescriptor.
        :rtype: str
        """
        return self._dns_rule_id

    @dns_rule_id.setter
    def dns_rule_id(self, dns_rule_id: str):
        """Sets the dns_rule_id of this DNSRuleDescriptor.

        Identifies the DNS rule.  # noqa: E501

        :param dns_rule_id: The dns_rule_id of this DNSRuleDescriptor.
        :type dns_rule_id: str
        """
        if dns_rule_id is None:
            raise ValueError("Invalid value for `dns_rule_id`, must not be `None`")  # noqa: E501

        self._dns_rule_id = dns_rule_id

    @property
    def domain_name(self) -> str:
        """Gets the domain_name of this DNSRuleDescriptor.

        FQDN of the DNS rule.  # noqa: E501

        :return: The domain_name of this DNSRuleDescriptor.
        :rtype: str
        """
        return self._domain_name

    @domain_name.setter
    def domain_name(self, domain_name: str):
        """Sets the domain_name of this DNSRuleDescriptor.

        FQDN of the DNS rule.  # noqa: E501

        :param domain_name: The domain_name of this DNSRuleDescriptor.
        :type domain_name: str
        """
        if domain_name is None:
            raise ValueError("Invalid value for `domain_name`, must not be `None`")  # noqa: E501

        self._domain_name = domain_name

    @property
    def ip_address_type(self) -> str:
        """Gets the ip_address_type of this DNSRuleDescriptor.

        Specifies the IP address type (IP_V6, IP_V4)  # noqa: E501

        :return: The ip_address_type of this DNSRuleDescriptor.
        :rtype: str
        """
        return self._ip_address_type

    @ip_address_type.setter
    def ip_address_type(self, ip_address_type: str):
        """Sets the ip_address_type of this DNSRuleDescriptor.

        Specifies the IP address type (IP_V6, IP_V4)  # noqa: E501

        :param ip_address_type: The ip_address_type of this DNSRuleDescriptor.
        :type ip_address_type: str
        """
        allowed_values = ["IP_V6", "IP_V4"]  # noqa: E501
        if ip_address_type not in allowed_values:
            raise ValueError(
                "Invalid value for `ip_address_type` ({0}), must be one of {1}"
                .format(ip_address_type, allowed_values)
            )

        self._ip_address_type = ip_address_type

    @property
    def ip_address(self) -> str:
        """Gets the ip_address of this DNSRuleDescriptor.

        IP address given by the DNS rule.  # noqa: E501

        :return: The ip_address of this DNSRuleDescriptor.
        :rtype: str
        """
        return self._ip_address

    @ip_address.setter
    def ip_address(self, ip_address: str):
        """Sets the ip_address of this DNSRuleDescriptor.

        IP address given by the DNS rule.  # noqa: E501

        :param ip_address: The ip_address of this DNSRuleDescriptor.
        :type ip_address: str
        """
        if ip_address is None:
            raise ValueError("Invalid value for `ip_address`, must not be `None`")  # noqa: E501

        self._ip_address = ip_address

    @property
    def ttl(self) -> float:
        """Gets the ttl of this DNSRuleDescriptor.

        Time-to-live value.  # noqa: E501

        :return: The ttl of this DNSRuleDescriptor.
        :rtype: float
        """
        return self._ttl

    @ttl.setter
    def ttl(self, ttl: float):
        """Sets the ttl of this DNSRuleDescriptor.

        Time-to-live value.  # noqa: E501

        :param ttl: The ttl of this DNSRuleDescriptor.
        :type ttl: float
        """

        self._ttl = ttl