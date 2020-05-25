import connexion
import six

from mtp_plugin_kubernetes.models.allocate_network_request import AllocateNetworkRequest  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_network_result import AllocateNetworkResult  # noqa: E501
from mtp_plugin_kubernetes.models.capacity_information import CapacityInformation  # noqa: E501
from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.nfvi_pop import NfviPop  # noqa: E501
from mtp_plugin_kubernetes.models.query_network_capacity_request import QueryNetworkCapacityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network import VirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes import util


def query_network_capacity(QueryNetworkCapacityRequest):  # noqa: E501
    """query_network_capacity

     # noqa: E501

    :param QueryNetworkCapacityRequest: 
    :type QueryNetworkCapacityRequest: dict | bytes

    :rtype: CapacityInformation
    """
    if connexion.request.is_json:
        QueryNetworkCapacityRequest = QueryNetworkCapacityRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def query_nfvi_po_p_network_information(NfviPopNetworkInformationRequest):  # noqa: E501
    """query_nfvi_po_p_network_information

     # noqa: E501

    :param NfviPopNetworkInformationRequest: Input filter for selecting information to query.
    :type NfviPopNetworkInformationRequest: dict | bytes

    :rtype: List[NfviPop]
    """
    if connexion.request.is_json:
        NfviPopNetworkInformationRequest = Filter.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def vi_mallocate_network(params):  # noqa: E501
    """vi_mallocate_network

     # noqa: E501

    :param params: 
    :type params: dict | bytes

    :rtype: AllocateNetworkResult
    """
    if connexion.request.is_json:
        params = AllocateNetworkRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def vi_mquery_networks(networkQueryFilter):  # noqa: E501
    """vi_mquery_networks

     # noqa: E501

    :param networkQueryFilter: Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers.
    :type networkQueryFilter: dict | bytes

    :rtype: List[VirtualNetwork]
    """
    if connexion.request.is_json:
        networkQueryFilter = Filter.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def vi_mterminate_networks(networkResourceId):  # noqa: E501
    """vi_mterminate_networks

     # noqa: E501

    :param networkResourceId: Identifier of the virtualised network resource(s) to be terminated.
    :type networkResourceId: List[str]

    :rtype: List[str]
    """
    return 'Not supported'
