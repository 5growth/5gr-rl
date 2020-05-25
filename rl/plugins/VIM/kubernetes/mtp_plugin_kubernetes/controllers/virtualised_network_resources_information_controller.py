import connexion
import six

from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network_resource_information import VirtualNetworkResourceInformation  # noqa: E501
from mtp_plugin_kubernetes import util


def query_network_information(informationQueryFilter):  # noqa: E501
    """query_network_information

     # noqa: E501

    :param informationQueryFilter: Filter defining the information of consumable virtualised resources on which the query applies.
    :type informationQueryFilter: dict | bytes

    :rtype: List[VirtualNetworkResourceInformation]
    """
    if connexion.request.is_json:
        informationQueryFilter = Filter.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'
