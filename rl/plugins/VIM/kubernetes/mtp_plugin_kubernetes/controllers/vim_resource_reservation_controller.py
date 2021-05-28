import connexion
import six

from mtp_plugin_kubernetes.models.create_compute_resource_reservation_request import CreateComputeResourceReservationRequest  # noqa: E501
from mtp_plugin_kubernetes.models.create_network_resource_reservation_request import CreateNetworkResourceReservationRequest  # noqa: E501
from mtp_plugin_kubernetes.models.reserved_virtual_compute import ReservedVirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.models.reserved_virtual_network import ReservedVirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes import util


def create_compute_reservation(body):  # noqa: E501
    """create_compute_reservation

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: ReservedVirtualCompute
    """
    if connexion.request.is_json:
        body = CreateComputeResourceReservationRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def create_network_reservation(body):  # noqa: E501
    """create_network_reservation

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: ReservedVirtualNetwork
    """
    if connexion.request.is_json:
        body = CreateNetworkResourceReservationRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def query_compute_reservation():  # noqa: E501
    """query_compute_reservation

     # noqa: E501


    :rtype: List[ReservedVirtualCompute]
    """
    return 'Not supported'


def query_network_reservation():  # noqa: E501
    """query_network_reservation

     # noqa: E501


    :rtype: List[ReservedVirtualNetwork]
    """
    return 'Not supported'


def terminate_compute_reservation(reservationId):  # noqa: E501
    """terminate_compute_reservation

     # noqa: E501

    :param reservationId: Identifier of the resource reservation(s) to terminate.
    :type reservationId: List[str]

    :rtype: List[str]
    """
    return 'Not supported'


def terminate_network_reservation(reservationId):  # noqa: E501
    """terminate_network_reservation

     # noqa: E501

    :param reservationId: Identifier of the resource reservation(s) to terminate.
    :type reservationId: List[str]

    :rtype: List[str]
    """
    return 'Not supported'
