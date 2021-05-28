import connexion
import six

from mtp_plugin_kubernetes.models.allocate_compute_request import AllocateComputeRequest  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_network_request import AllocateNetworkRequest  # noqa: E501
from mtp_plugin_kubernetes.models.allocate_network_result import AllocateNetworkResult  # noqa: E501
from mtp_plugin_kubernetes.models.app_d import AppD  # noqa: E501
from mtp_plugin_kubernetes.models.delete_inter_nfvi_pop_connectivity_request import DeleteInterNfviPopConnectivityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.filter import Filter  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response2003 import InlineResponse2003  # noqa: E501
from mtp_plugin_kubernetes.models.inline_response201 import InlineResponse201  # noqa: E501
from mtp_plugin_kubernetes.models.inter_nfvi_pop_connectivity_request import InterNfviPopConnectivityRequest  # noqa: E501
from mtp_plugin_kubernetes.models.operate_compute_request import OperateComputeRequest  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_network import VirtualNetwork  # noqa: E501
from mtp_plugin_kubernetes import util


def abstract_operate_compute(body):  # noqa: E501
    """abstract_operate_compute

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: VirtualCompute
    """
    if connexion.request.is_json:
        body = OperateComputeRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def allocate_abstract_compute(body):  # noqa: E501
    """Allocate abstract compute resources

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: VirtualCompute
    """
    if connexion.request.is_json:
        body = AllocateComputeRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def collect_mtp_cloud_network_resource_information():  # noqa: E501
    """Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

    Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity # noqa: E501


    :rtype: InlineResponse2003
    """
    return 'Not supported'


def collect_mtp_federated_cloud_network_resource_information():  # noqa: E501
    """Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation

    Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation # noqa: E501


    :rtype: InlineResponse2003
    """
    return 'Not supported'


def create_inter_nfvi_po_p_connectivity(body):  # noqa: E501
    """Create inter-NFVI-PoP connectivity

     # noqa: E501

    :param body: Create inter-NfviPop Connectivity
    :type body: dict | bytes

    :rtype: List[InlineResponse201]
    """
    if connexion.request.is_json:
        body = InterNfviPopConnectivityRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def delete_inter_nfvi_po_p_connectivity(body):  # noqa: E501
    """Delete inter-NFVI-PoP connectivity

     # noqa: E501

    :param body: Delete inter-NfviPop Connectivity
    :type body: dict | bytes

    :rtype: None
    """
    if connexion.request.is_json:
        body = DeleteInterNfviPopConnectivityRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def mecapp_onboard_app_d_id_get(AppDId):  # noqa: E501
    """Retrieve information about a specific application package.

    Retrieve information about a specific application package. # noqa: E501

    :param AppDId: Application package identifier.
    :type AppDId: str

    :rtype: AppD
    """
    return 'Not supported'


def mecapp_onboard_get():  # noqa: E501
    """Retrieve a list of onboarded application packages.

    Retrieve a list of onboarded application packages. # noqa: E501


    :rtype: List[AppD]
    """
    return 'Not supported'


def terminate_resources(computeId):  # noqa: E501
    """Terminate abstract compute resources

     # noqa: E501

    :param computeId: Identifier(s) of the virtualised compute resource(s) to be terminated.
    :type computeId: List[str]

    :rtype: List[str]
    """
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
