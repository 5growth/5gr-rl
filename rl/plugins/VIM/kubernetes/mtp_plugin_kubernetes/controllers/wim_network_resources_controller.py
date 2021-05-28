import connexion
import six

from mtp_plugin_kubernetes.models.allocate_parameters import AllocateParameters  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_reply import AllocateReply  # noqa: E501
from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response2002 import InlineResponse2002  # noqa: E501
from mtp_plugin_kubernetes.models.network_ids import NetworkIds  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network import VirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes import util


def allocate_network(params):  # noqa: E501
    """allocate_network

     # noqa: E501

    :param params: 
    :type params: dict | bytes

    :rtype: AllocateReply
    """
    if connexion.request.is_json:
        params = AllocateParameters.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def collect_wim_abstracted_information():  # noqa: E501
    """Retrieve aggregated WAN Connectivity

    Retrieve aggregated WAN Connectivity # noqa: E501


    :rtype: InlineResponse2002
    """
    return 'Not supported'


def query_networks(networkQueryFilter):  # noqa: E501
    """query_networks

     # noqa: E501

    :param networkQueryFilter: Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers.
    :type networkQueryFilter: dict | bytes

    :rtype: List[VirtualNetwork]
    """
    if connexion.request.is_json:
        networkQueryFilter = Filter.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def terminate_networks(networkId):  # noqa: E501
    """terminate_networks

     # noqa: E501

    :param networkId: Identifier of the virtualised network resource(s) to be terminated.
    :type networkId: str

    :rtype: List[NetworkIds]
    """
    return 'Not supported'
