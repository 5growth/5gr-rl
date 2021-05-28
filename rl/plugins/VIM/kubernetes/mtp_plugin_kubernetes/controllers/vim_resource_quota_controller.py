import connexion
import six

from mtp_plugin_kubernetes.models.create_compute_resource_quota_request import CreateComputeResourceQuotaRequest  # noqa: E501
from mtp_plugin_kubernetes.models.create_network_resource_quota_request import CreateNetworkResourceQuotaRequest  # noqa: E501
from mtp_plugin_kubernetes.models.resource_group_ids import ResourceGroupIds  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_quota import VirtualComputeQuota  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network_quota import VirtualNetworkQuota  # noqa: E501
from mtp_plugin_kubernetes import util


def create_compute_quota(body):  # noqa: E501
    """create_compute_quota

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: VirtualComputeQuota
    """
    if connexion.request.is_json:
        body = CreateComputeResourceQuotaRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def create_network_quota(body):  # noqa: E501
    """create_network_quota

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: VirtualNetworkQuota
    """
    if connexion.request.is_json:
        body = CreateNetworkResourceQuotaRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def query_compute_quota():  # noqa: E501
    """query_compute_quota

     # noqa: E501


    :rtype: List[VirtualComputeQuota]
    """
    return 'Not supported'


def query_network_quota():  # noqa: E501
    """query_network_quota

     # noqa: E501


    :rtype: List[VirtualNetworkQuota]
    """
    return 'Not supported'


def terminate_compute_quota(resourceGroupId):  # noqa: E501
    """terminate_compute_quota

     # noqa: E501

    :param resourceGroupId: Unique identifier of the infrastructure resource group, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
    :type resourceGroupId: List[str]

    :rtype: List[str]
    """
    return 'Not supported'


def terminate_network_quota(resourceGroupId):  # noqa: E501
    """terminate_network_quota

     # noqa: E501

    :param resourceGroupId: Unique identifier of the ;infrastructure resource group;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
    :type resourceGroupId: List[str]

    :rtype: ResourceGroupIds
    """
    return 'Not supported'
