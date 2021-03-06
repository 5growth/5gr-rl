# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.create_network_resource_reservation_request_network_reservation_network_attributes import \
    CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes
from mtp_plugin_kubernetes.models.create_network_resource_reservation_request_network_reservation_network_ports import \
    CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts


class CreateNetworkResourceReservationRequestNetworkReservation(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, network_attributes: CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes=None, network_ports: List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]=None, num_public_ips: int=None):  # noqa: E501
        """CreateNetworkResourceReservationRequestNetworkReservation - a model defined in Swagger

        :param network_attributes: The network_attributes of this CreateNetworkResourceReservationRequestNetworkReservation.  # noqa: E501
        :type network_attributes: CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes
        :param network_ports: The network_ports of this CreateNetworkResourceReservationRequestNetworkReservation.  # noqa: E501
        :type network_ports: List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]
        :param num_public_ips: The num_public_ips of this CreateNetworkResourceReservationRequestNetworkReservation.  # noqa: E501
        :type num_public_ips: int
        """
        self.swagger_types = {
            'network_attributes': CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes,
            'network_ports': List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts],
            'num_public_ips': int
        }

        self.attribute_map = {
            'network_attributes': 'networkAttributes',
            'network_ports': 'networkPorts',
            'num_public_ips': 'numPublicIps'
        }

        self._network_attributes = network_attributes
        self._network_ports = network_ports
        self._num_public_ips = num_public_ips

    @classmethod
    def from_dict(cls, dikt) -> 'CreateNetworkResourceReservationRequestNetworkReservation':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The CreateNetworkResourceReservationRequest_networkReservation of this CreateNetworkResourceReservationRequestNetworkReservation.  # noqa: E501
        :rtype: CreateNetworkResourceReservationRequestNetworkReservation
        """
        return util.deserialize_model(dikt, cls)

    @property
    def network_attributes(self) -> CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes:
        """Gets the network_attributes of this CreateNetworkResourceReservationRequestNetworkReservation.


        :return: The network_attributes of this CreateNetworkResourceReservationRequestNetworkReservation.
        :rtype: CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes
        """
        return self._network_attributes

    @network_attributes.setter
    def network_attributes(self, network_attributes: CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes):
        """Sets the network_attributes of this CreateNetworkResourceReservationRequestNetworkReservation.


        :param network_attributes: The network_attributes of this CreateNetworkResourceReservationRequestNetworkReservation.
        :type network_attributes: CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes
        """
        if network_attributes is None:
            raise ValueError("Invalid value for `network_attributes`, must not be `None`")  # noqa: E501

        self._network_attributes = network_attributes

    @property
    def network_ports(self) -> List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]:
        """Gets the network_ports of this CreateNetworkResourceReservationRequestNetworkReservation.

        List of specific network ports to be reserved.  # noqa: E501

        :return: The network_ports of this CreateNetworkResourceReservationRequestNetworkReservation.
        :rtype: List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]
        """
        return self._network_ports

    @network_ports.setter
    def network_ports(self, network_ports: List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]):
        """Sets the network_ports of this CreateNetworkResourceReservationRequestNetworkReservation.

        List of specific network ports to be reserved.  # noqa: E501

        :param network_ports: The network_ports of this CreateNetworkResourceReservationRequestNetworkReservation.
        :type network_ports: List[CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts]
        """
        if network_ports is None:
            raise ValueError("Invalid value for `network_ports`, must not be `None`")  # noqa: E501

        self._network_ports = network_ports

    @property
    def num_public_ips(self) -> int:
        """Gets the num_public_ips of this CreateNetworkResourceReservationRequestNetworkReservation.

        Number of public IP addresses to be reserved.  # noqa: E501

        :return: The num_public_ips of this CreateNetworkResourceReservationRequestNetworkReservation.
        :rtype: int
        """
        return self._num_public_ips

    @num_public_ips.setter
    def num_public_ips(self, num_public_ips: int):
        """Sets the num_public_ips of this CreateNetworkResourceReservationRequestNetworkReservation.

        Number of public IP addresses to be reserved.  # noqa: E501

        :param num_public_ips: The num_public_ips of this CreateNetworkResourceReservationRequestNetworkReservation.
        :type num_public_ips: int
        """
        if num_public_ips is None:
            raise ValueError("Invalid value for `num_public_ips`, must not be `None`")  # noqa: E501

        self._num_public_ips = num_public_ips
