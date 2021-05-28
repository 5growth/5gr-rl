import connexion
import six

from mtp_plugin_kubernetes.models.inline_response200 import InlineResponse200  # noqa: E501
from mtp_plugin_kubernetes.models.mec_traffic_service_creation_request import MECTrafficServiceCreationRequest  # noqa: E501
from mtp_plugin_kubernetes.models.mec_traffic_service_creation_response import MECTrafficServiceCreationResponse  # noqa: E501
from mtp_plugin_kubernetes import util


def service_regions_get():  # noqa: E501
    """Retrieve a list of all covered regions.

    Retrieve a list of MEC regions, reporting their identifiers and location information. # noqa: E501


    :rtype: InlineResponse200
    """
    return 'Not supported'


def service_regions_region_id_get(RegionId):  # noqa: E501
    """Retrieve a list of MEC service requests for the given region.

    Retrieve a list of MEC service requests for the given region. # noqa: E501

    :param RegionId: Region identifier.
    :type RegionId: str

    :rtype: List[MECTrafficServiceCreationRequest]
    """
    return 'Not supported'


def service_requests_get():  # noqa: E501
    """Retrieve a list of MEC service requests.

    Retrieve a list of MEC service requests. # noqa: E501


    :rtype: List[MECTrafficServiceCreationRequest]
    """
    return 'Not supported'


def service_requests_post(mecTrafficServiceCreationRequest):  # noqa: E501
    """Create MEC service rules.

    Create MEC service rules. # noqa: E501

    :param mecTrafficServiceCreationRequest: Information about the MEC app, including required services, traffic rules, etc.
    :type mecTrafficServiceCreationRequest: dict | bytes

    :rtype: MECTrafficServiceCreationResponse
    """
    if connexion.request.is_json:
        mecTrafficServiceCreationRequest = MECTrafficServiceCreationRequest.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def service_requests_request_id_delete(RequestId):  # noqa: E501
    """Delete service.

    Delete service identified by the given request ID. # noqa: E501

    :param RequestId: Request identifier.
    :type RequestId: str

    :rtype: None
    """
    return 'Not supported'


def service_requests_request_id_get(RequestId):  # noqa: E501
    """Retrieve information about a MEC service request.

    Retrieve information about a MEC service request. # noqa: E501

    :param RequestId: Request identifier.
    :type RequestId: str

    :rtype: MECTrafficServiceCreationRequest
    """
    return 'Not supported'
