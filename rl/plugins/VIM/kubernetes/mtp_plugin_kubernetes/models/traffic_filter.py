# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class TrafficFilter(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, src_address: List[str]=None, dst_address: List[str]=None, src_port: List[str]=None, dst_port: List[str]=None, protocol: List[str]=None, token: List[str]=None, src_tunnel_address: List[str]=None, dst_tunnel_address: List[str]=None, src_tunnel_port: List[str]=None, dst_tunnel_port: List[str]=None, qci: float=None, dscp: float=None, tc: float=None):  # noqa: E501
        """TrafficFilter - a model defined in Swagger

        :param src_address: The src_address of this TrafficFilter.  # noqa: E501
        :type src_address: List[str]
        :param dst_address: The dst_address of this TrafficFilter.  # noqa: E501
        :type dst_address: List[str]
        :param src_port: The src_port of this TrafficFilter.  # noqa: E501
        :type src_port: List[str]
        :param dst_port: The dst_port of this TrafficFilter.  # noqa: E501
        :type dst_port: List[str]
        :param protocol: The protocol of this TrafficFilter.  # noqa: E501
        :type protocol: List[str]
        :param token: The token of this TrafficFilter.  # noqa: E501
        :type token: List[str]
        :param src_tunnel_address: The src_tunnel_address of this TrafficFilter.  # noqa: E501
        :type src_tunnel_address: List[str]
        :param dst_tunnel_address: The dst_tunnel_address of this TrafficFilter.  # noqa: E501
        :type dst_tunnel_address: List[str]
        :param src_tunnel_port: The src_tunnel_port of this TrafficFilter.  # noqa: E501
        :type src_tunnel_port: List[str]
        :param dst_tunnel_port: The dst_tunnel_port of this TrafficFilter.  # noqa: E501
        :type dst_tunnel_port: List[str]
        :param qci: The qci of this TrafficFilter.  # noqa: E501
        :type qci: float
        :param dscp: The dscp of this TrafficFilter.  # noqa: E501
        :type dscp: float
        :param tc: The tc of this TrafficFilter.  # noqa: E501
        :type tc: float
        """
        self.swagger_types = {
            'src_address': List[str],
            'dst_address': List[str],
            'src_port': List[str],
            'dst_port': List[str],
            'protocol': List[str],
            'token': List[str],
            'src_tunnel_address': List[str],
            'dst_tunnel_address': List[str],
            'src_tunnel_port': List[str],
            'dst_tunnel_port': List[str],
            'qci': float,
            'dscp': float,
            'tc': float
        }

        self.attribute_map = {
            'src_address': 'srcAddress',
            'dst_address': 'dstAddress',
            'src_port': 'srcPort',
            'dst_port': 'dstPort',
            'protocol': 'protocol',
            'token': 'token',
            'src_tunnel_address': 'srcTunnelAddress',
            'dst_tunnel_address': 'dstTunnelAddress',
            'src_tunnel_port': 'srcTunnelPort',
            'dst_tunnel_port': 'dstTunnelPort',
            'qci': 'qci',
            'dscp': 'dscp',
            'tc': 'tc'
        }

        self._src_address = src_address
        self._dst_address = dst_address
        self._src_port = src_port
        self._dst_port = dst_port
        self._protocol = protocol
        self._token = token
        self._src_tunnel_address = src_tunnel_address
        self._dst_tunnel_address = dst_tunnel_address
        self._src_tunnel_port = src_tunnel_port
        self._dst_tunnel_port = dst_tunnel_port
        self._qci = qci
        self._dscp = dscp
        self._tc = tc

    @classmethod
    def from_dict(cls, dikt) -> 'TrafficFilter':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The TrafficFilter of this TrafficFilter.  # noqa: E501
        :rtype: TrafficFilter
        """
        return util.deserialize_model(dikt, cls)

    @property
    def src_address(self) -> List[str]:
        """Gets the src_address of this TrafficFilter.

        An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.  # noqa: E501

        :return: The src_address of this TrafficFilter.
        :rtype: List[str]
        """
        return self._src_address

    @src_address.setter
    def src_address(self, src_address: List[str]):
        """Sets the src_address of this TrafficFilter.

        An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.  # noqa: E501

        :param src_address: The src_address of this TrafficFilter.
        :type src_address: List[str]
        """

        self._src_address = src_address

    @property
    def dst_address(self) -> List[str]:
        """Gets the dst_address of this TrafficFilter.

        An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.  # noqa: E501

        :return: The dst_address of this TrafficFilter.
        :rtype: List[str]
        """
        return self._dst_address

    @dst_address.setter
    def dst_address(self, dst_address: List[str]):
        """Sets the dst_address of this TrafficFilter.

        An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.  # noqa: E501

        :param dst_address: The dst_address of this TrafficFilter.
        :type dst_address: List[str]
        """

        self._dst_address = dst_address

    @property
    def src_port(self) -> List[str]:
        """Gets the src_port of this TrafficFilter.

        A port or a range of ports.  # noqa: E501

        :return: The src_port of this TrafficFilter.
        :rtype: List[str]
        """
        return self._src_port

    @src_port.setter
    def src_port(self, src_port: List[str]):
        """Sets the src_port of this TrafficFilter.

        A port or a range of ports.  # noqa: E501

        :param src_port: The src_port of this TrafficFilter.
        :type src_port: List[str]
        """

        self._src_port = src_port

    @property
    def dst_port(self) -> List[str]:
        """Gets the dst_port of this TrafficFilter.

        A port or a range of ports.  # noqa: E501

        :return: The dst_port of this TrafficFilter.
        :rtype: List[str]
        """
        return self._dst_port

    @dst_port.setter
    def dst_port(self, dst_port: List[str]):
        """Sets the dst_port of this TrafficFilter.

        A port or a range of ports.  # noqa: E501

        :param dst_port: The dst_port of this TrafficFilter.
        :type dst_port: List[str]
        """

        self._dst_port = dst_port

    @property
    def protocol(self) -> List[str]:
        """Gets the protocol of this TrafficFilter.

        Specifies the protocol of the traffic rule.  # noqa: E501

        :return: The protocol of this TrafficFilter.
        :rtype: List[str]
        """
        return self._protocol

    @protocol.setter
    def protocol(self, protocol: List[str]):
        """Sets the protocol of this TrafficFilter.

        Specifies the protocol of the traffic rule.  # noqa: E501

        :param protocol: The protocol of this TrafficFilter.
        :type protocol: List[str]
        """

        self._protocol = protocol

    @property
    def token(self) -> List[str]:
        """Gets the token of this TrafficFilter.

        Used for token based traffic rule.  # noqa: E501

        :return: The token of this TrafficFilter.
        :rtype: List[str]
        """
        return self._token

    @token.setter
    def token(self, token: List[str]):
        """Sets the token of this TrafficFilter.

        Used for token based traffic rule.  # noqa: E501

        :param token: The token of this TrafficFilter.
        :type token: List[str]
        """

        self._token = token

    @property
    def src_tunnel_address(self) -> List[str]:
        """Gets the src_tunnel_address of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :return: The src_tunnel_address of this TrafficFilter.
        :rtype: List[str]
        """
        return self._src_tunnel_address

    @src_tunnel_address.setter
    def src_tunnel_address(self, src_tunnel_address: List[str]):
        """Sets the src_tunnel_address of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :param src_tunnel_address: The src_tunnel_address of this TrafficFilter.
        :type src_tunnel_address: List[str]
        """

        self._src_tunnel_address = src_tunnel_address

    @property
    def dst_tunnel_address(self) -> List[str]:
        """Gets the dst_tunnel_address of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :return: The dst_tunnel_address of this TrafficFilter.
        :rtype: List[str]
        """
        return self._dst_tunnel_address

    @dst_tunnel_address.setter
    def dst_tunnel_address(self, dst_tunnel_address: List[str]):
        """Sets the dst_tunnel_address of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :param dst_tunnel_address: The dst_tunnel_address of this TrafficFilter.
        :type dst_tunnel_address: List[str]
        """

        self._dst_tunnel_address = dst_tunnel_address

    @property
    def src_tunnel_port(self) -> List[str]:
        """Gets the src_tunnel_port of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :return: The src_tunnel_port of this TrafficFilter.
        :rtype: List[str]
        """
        return self._src_tunnel_port

    @src_tunnel_port.setter
    def src_tunnel_port(self, src_tunnel_port: List[str]):
        """Sets the src_tunnel_port of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :param src_tunnel_port: The src_tunnel_port of this TrafficFilter.
        :type src_tunnel_port: List[str]
        """

        self._src_tunnel_port = src_tunnel_port

    @property
    def dst_tunnel_port(self) -> List[str]:
        """Gets the dst_tunnel_port of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :return: The dst_tunnel_port of this TrafficFilter.
        :rtype: List[str]
        """
        return self._dst_tunnel_port

    @dst_tunnel_port.setter
    def dst_tunnel_port(self, dst_tunnel_port: List[str]):
        """Sets the dst_tunnel_port of this TrafficFilter.

        Used for GTP tunnel based traffic rule.  # noqa: E501

        :param dst_tunnel_port: The dst_tunnel_port of this TrafficFilter.
        :type dst_tunnel_port: List[str]
        """

        self._dst_tunnel_port = dst_tunnel_port

    @property
    def qci(self) -> float:
        """Gets the qci of this TrafficFilter.

        Used to match all packets that have the same QCI.  # noqa: E501

        :return: The qci of this TrafficFilter.
        :rtype: float
        """
        return self._qci

    @qci.setter
    def qci(self, qci: float):
        """Sets the qci of this TrafficFilter.

        Used to match all packets that have the same QCI.  # noqa: E501

        :param qci: The qci of this TrafficFilter.
        :type qci: float
        """

        self._qci = qci

    @property
    def dscp(self) -> float:
        """Gets the dscp of this TrafficFilter.

        Used to match all IPv4 packets that have the same DSCP.  # noqa: E501

        :return: The dscp of this TrafficFilter.
        :rtype: float
        """
        return self._dscp

    @dscp.setter
    def dscp(self, dscp: float):
        """Sets the dscp of this TrafficFilter.

        Used to match all IPv4 packets that have the same DSCP.  # noqa: E501

        :param dscp: The dscp of this TrafficFilter.
        :type dscp: float
        """

        self._dscp = dscp

    @property
    def tc(self) -> float:
        """Gets the tc of this TrafficFilter.

        Used to match all IPv6 packets that have the same TC.  # noqa: E501

        :return: The tc of this TrafficFilter.
        :rtype: float
        """
        return self._tc

    @tc.setter
    def tc(self, tc: float):
        """Sets the tc of this TrafficFilter.

        Used to match all IPv6 packets that have the same TC.  # noqa: E501

        :param tc: The tc of this TrafficFilter.
        :type tc: float
        """

        self._tc = tc
