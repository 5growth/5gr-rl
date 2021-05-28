# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.network_layer import NetworkLayer
from mtp_plugin_kubernetes.models.virtual_links_inner_virtual_link_network_qo_s import \
    VirtualLinksInnerVirtualLinkNetworkQoS


class VirtualLinksInnerVirtualLink(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, virtual_link_id: str=None, total_bandwidth: float=None, available_bandwidth: float=None, network_qo_s: VirtualLinksInnerVirtualLinkNetworkQoS=None, src_gw_id: str=None, src_link_id: int=None, dst_gw_id: str=None, dst_link_id: int=None, network_layer: NetworkLayer=None):  # noqa: E501
        """VirtualLinksInnerVirtualLink - a model defined in Swagger

        :param virtual_link_id: The virtual_link_id of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type virtual_link_id: str
        :param total_bandwidth: The total_bandwidth of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type total_bandwidth: float
        :param available_bandwidth: The available_bandwidth of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type available_bandwidth: float
        :param network_qo_s: The network_qo_s of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type network_qo_s: VirtualLinksInnerVirtualLinkNetworkQoS
        :param src_gw_id: The src_gw_id of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type src_gw_id: str
        :param src_link_id: The src_link_id of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type src_link_id: int
        :param dst_gw_id: The dst_gw_id of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type dst_gw_id: str
        :param dst_link_id: The dst_link_id of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type dst_link_id: int
        :param network_layer: The network_layer of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :type network_layer: NetworkLayer
        """
        self.swagger_types = {
            'virtual_link_id': str,
            'total_bandwidth': float,
            'available_bandwidth': float,
            'network_qo_s': VirtualLinksInnerVirtualLinkNetworkQoS,
            'src_gw_id': str,
            'src_link_id': int,
            'dst_gw_id': str,
            'dst_link_id': int,
            'network_layer': NetworkLayer
        }

        self.attribute_map = {
            'virtual_link_id': 'virtualLinkId',
            'total_bandwidth': 'totalBandwidth',
            'available_bandwidth': 'availableBandwidth',
            'network_qo_s': 'networkQoS',
            'src_gw_id': 'srcGwId',
            'src_link_id': 'srcLinkId',
            'dst_gw_id': 'dstGwId',
            'dst_link_id': 'dstLinkId',
            'network_layer': 'networkLayer'
        }

        self._virtual_link_id = virtual_link_id
        self._total_bandwidth = total_bandwidth
        self._available_bandwidth = available_bandwidth
        self._network_qo_s = network_qo_s
        self._src_gw_id = src_gw_id
        self._src_link_id = src_link_id
        self._dst_gw_id = dst_gw_id
        self._dst_link_id = dst_link_id
        self._network_layer = network_layer

    @classmethod
    def from_dict(cls, dikt) -> 'VirtualLinksInnerVirtualLink':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The virtualLinks_inner_virtualLink of this VirtualLinksInnerVirtualLink.  # noqa: E501
        :rtype: VirtualLinksInnerVirtualLink
        """
        return util.deserialize_model(dikt, cls)

    @property
    def virtual_link_id(self) -> str:
        """Gets the virtual_link_id of this VirtualLinksInnerVirtualLink.

        (numbered) Identifier of the virtual link  # noqa: E501

        :return: The virtual_link_id of this VirtualLinksInnerVirtualLink.
        :rtype: str
        """
        return self._virtual_link_id

    @virtual_link_id.setter
    def virtual_link_id(self, virtual_link_id: str):
        """Sets the virtual_link_id of this VirtualLinksInnerVirtualLink.

        (numbered) Identifier of the virtual link  # noqa: E501

        :param virtual_link_id: The virtual_link_id of this VirtualLinksInnerVirtualLink.
        :type virtual_link_id: str
        """
        if virtual_link_id is None:
            raise ValueError("Invalid value for `virtual_link_id`, must not be `None`")  # noqa: E501

        self._virtual_link_id = virtual_link_id

    @property
    def total_bandwidth(self) -> float:
        """Gets the total_bandwidth of this VirtualLinksInnerVirtualLink.

        Total bandwidth capacity supported by the logical link (in Mbps).  # noqa: E501

        :return: The total_bandwidth of this VirtualLinksInnerVirtualLink.
        :rtype: float
        """
        return self._total_bandwidth

    @total_bandwidth.setter
    def total_bandwidth(self, total_bandwidth: float):
        """Sets the total_bandwidth of this VirtualLinksInnerVirtualLink.

        Total bandwidth capacity supported by the logical link (in Mbps).  # noqa: E501

        :param total_bandwidth: The total_bandwidth of this VirtualLinksInnerVirtualLink.
        :type total_bandwidth: float
        """
        if total_bandwidth is None:
            raise ValueError("Invalid value for `total_bandwidth`, must not be `None`")  # noqa: E501

        self._total_bandwidth = total_bandwidth

    @property
    def available_bandwidth(self) -> float:
        """Gets the available_bandwidth of this VirtualLinksInnerVirtualLink.

        Available bandwidth capacity supported by the logical link (in Mbps).  # noqa: E501

        :return: The available_bandwidth of this VirtualLinksInnerVirtualLink.
        :rtype: float
        """
        return self._available_bandwidth

    @available_bandwidth.setter
    def available_bandwidth(self, available_bandwidth: float):
        """Sets the available_bandwidth of this VirtualLinksInnerVirtualLink.

        Available bandwidth capacity supported by the logical link (in Mbps).  # noqa: E501

        :param available_bandwidth: The available_bandwidth of this VirtualLinksInnerVirtualLink.
        :type available_bandwidth: float
        """
        if available_bandwidth is None:
            raise ValueError("Invalid value for `available_bandwidth`, must not be `None`")  # noqa: E501

        self._available_bandwidth = available_bandwidth

    @property
    def network_qo_s(self) -> VirtualLinksInnerVirtualLinkNetworkQoS:
        """Gets the network_qo_s of this VirtualLinksInnerVirtualLink.


        :return: The network_qo_s of this VirtualLinksInnerVirtualLink.
        :rtype: VirtualLinksInnerVirtualLinkNetworkQoS
        """
        return self._network_qo_s

    @network_qo_s.setter
    def network_qo_s(self, network_qo_s: VirtualLinksInnerVirtualLinkNetworkQoS):
        """Sets the network_qo_s of this VirtualLinksInnerVirtualLink.


        :param network_qo_s: The network_qo_s of this VirtualLinksInnerVirtualLink.
        :type network_qo_s: VirtualLinksInnerVirtualLinkNetworkQoS
        """
        if network_qo_s is None:
            raise ValueError("Invalid value for `network_qo_s`, must not be `None`")  # noqa: E501

        self._network_qo_s = network_qo_s

    @property
    def src_gw_id(self) -> str:
        """Gets the src_gw_id of this VirtualLinksInnerVirtualLink.

        5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).  # noqa: E501

        :return: The src_gw_id of this VirtualLinksInnerVirtualLink.
        :rtype: str
        """
        return self._src_gw_id

    @src_gw_id.setter
    def src_gw_id(self, src_gw_id: str):
        """Sets the src_gw_id of this VirtualLinksInnerVirtualLink.

        5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).  # noqa: E501

        :param src_gw_id: The src_gw_id of this VirtualLinksInnerVirtualLink.
        :type src_gw_id: str
        """
        if src_gw_id is None:
            raise ValueError("Invalid value for `src_gw_id`, must not be `None`")  # noqa: E501

        self._src_gw_id = src_gw_id

    @property
    def src_link_id(self) -> int:
        """Gets the src_link_id of this VirtualLinksInnerVirtualLink.

        Local Logical Link Id.  # noqa: E501

        :return: The src_link_id of this VirtualLinksInnerVirtualLink.
        :rtype: int
        """
        return self._src_link_id

    @src_link_id.setter
    def src_link_id(self, src_link_id: int):
        """Sets the src_link_id of this VirtualLinksInnerVirtualLink.

        Local Logical Link Id.  # noqa: E501

        :param src_link_id: The src_link_id of this VirtualLinksInnerVirtualLink.
        :type src_link_id: int
        """

        self._src_link_id = src_link_id

    @property
    def dst_gw_id(self) -> str:
        """Gets the dst_gw_id of this VirtualLinksInnerVirtualLink.

        5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).  # noqa: E501

        :return: The dst_gw_id of this VirtualLinksInnerVirtualLink.
        :rtype: str
        """
        return self._dst_gw_id

    @dst_gw_id.setter
    def dst_gw_id(self, dst_gw_id: str):
        """Sets the dst_gw_id of this VirtualLinksInnerVirtualLink.

        5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).  # noqa: E501

        :param dst_gw_id: The dst_gw_id of this VirtualLinksInnerVirtualLink.
        :type dst_gw_id: str
        """
        if dst_gw_id is None:
            raise ValueError("Invalid value for `dst_gw_id`, must not be `None`")  # noqa: E501

        self._dst_gw_id = dst_gw_id

    @property
    def dst_link_id(self) -> int:
        """Gets the dst_link_id of this VirtualLinksInnerVirtualLink.

        Remote Logical Link Id.  # noqa: E501

        :return: The dst_link_id of this VirtualLinksInnerVirtualLink.
        :rtype: int
        """
        return self._dst_link_id

    @dst_link_id.setter
    def dst_link_id(self, dst_link_id: int):
        """Sets the dst_link_id of this VirtualLinksInnerVirtualLink.

        Remote Logical Link Id.  # noqa: E501

        :param dst_link_id: The dst_link_id of this VirtualLinksInnerVirtualLink.
        :type dst_link_id: int
        """

        self._dst_link_id = dst_link_id

    @property
    def network_layer(self) -> NetworkLayer:
        """Gets the network_layer of this VirtualLinksInnerVirtualLink.


        :return: The network_layer of this VirtualLinksInnerVirtualLink.
        :rtype: NetworkLayer
        """
        return self._network_layer

    @network_layer.setter
    def network_layer(self, network_layer: NetworkLayer):
        """Sets the network_layer of this VirtualLinksInnerVirtualLink.


        :param network_layer: The network_layer of this VirtualLinksInnerVirtualLink.
        :type network_layer: NetworkLayer
        """

        self._network_layer = network_layer
