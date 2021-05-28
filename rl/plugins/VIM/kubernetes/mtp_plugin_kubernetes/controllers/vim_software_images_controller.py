import connexion
import six

from mtp_plugin_kubernetes.models.software_image_add_query import SoftwareImageAddQuery  # noqa: E501
from mtp_plugin_kubernetes.models.software_image_information import SoftwareImageInformation  # noqa: E501
from mtp_plugin_kubernetes import util


def add_software_image(body):  # noqa: E501
    """add_software_image

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: SoftwareImageInformation
    """
    if connexion.request.is_json:
        body = SoftwareImageAddQuery.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def delete_software_image(id):  # noqa: E501
    """delete_software_image

     # noqa: E501

    :param id: The identifier of the software image to be deleted.
    :type id: str

    :rtype: str
    """
    return 'Not supported'


def query_software_image(id):  # noqa: E501
    """query_software_image

     # noqa: E501

    :param id: The identifier of the software image to be queried.
    :type id: str

    :rtype: SoftwareImageInformation
    """
    return 'Not supported'


def query_software_images():  # noqa: E501
    """query_software_images

     # noqa: E501


    :rtype: List[SoftwareImageInformation]
    """
    return 'Not supported'
