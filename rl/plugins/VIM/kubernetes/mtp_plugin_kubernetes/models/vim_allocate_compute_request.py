# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.allocate_compute_request_affinity_or_anti_affinity_constraints import \
    AllocateComputeRequestAffinityOrAntiAffinityConstraints
from mtp_plugin_kubernetes.models.allocate_compute_request_interface_data import AllocateComputeRequestInterfaceData
from mtp_plugin_kubernetes.models.allocate_compute_request_user_data import AllocateComputeRequestUserData
from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.meta_data_inner import MetaDataInner


class VIMAllocateComputeRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, affinity_or_anti_affinity_constraints: List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]=None, compute_flavour_id: str=None, compute_name: str=None, interface_data: List[AllocateComputeRequestInterfaceData]=None, location_constraints: str=None, metadata: List[MetaDataInner]=None, reservation_id: str=None, resource_group_id: str=None, user_data: AllocateComputeRequestUserData=None, vc_image_id: str=None):  # noqa: E501
        """VIMAllocateComputeRequest - a model defined in Swagger

        :param affinity_or_anti_affinity_constraints: The affinity_or_anti_affinity_constraints of this VIMAllocateComputeRequest.  # noqa: E501
        :type affinity_or_anti_affinity_constraints: List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]
        :param compute_flavour_id: The compute_flavour_id of this VIMAllocateComputeRequest.  # noqa: E501
        :type compute_flavour_id: str
        :param compute_name: The compute_name of this VIMAllocateComputeRequest.  # noqa: E501
        :type compute_name: str
        :param interface_data: The interface_data of this VIMAllocateComputeRequest.  # noqa: E501
        :type interface_data: List[AllocateComputeRequestInterfaceData]
        :param location_constraints: The location_constraints of this VIMAllocateComputeRequest.  # noqa: E501
        :type location_constraints: str
        :param metadata: The metadata of this VIMAllocateComputeRequest.  # noqa: E501
        :type metadata: List[MetaDataInner]
        :param reservation_id: The reservation_id of this VIMAllocateComputeRequest.  # noqa: E501
        :type reservation_id: str
        :param resource_group_id: The resource_group_id of this VIMAllocateComputeRequest.  # noqa: E501
        :type resource_group_id: str
        :param user_data: The user_data of this VIMAllocateComputeRequest.  # noqa: E501
        :type user_data: AllocateComputeRequestUserData
        :param vc_image_id: The vc_image_id of this VIMAllocateComputeRequest.  # noqa: E501
        :type vc_image_id: str
        """
        self.swagger_types = {
            'affinity_or_anti_affinity_constraints': List[AllocateComputeRequestAffinityOrAntiAffinityConstraints],
            'compute_flavour_id': str,
            'compute_name': str,
            'interface_data': List[AllocateComputeRequestInterfaceData],
            'location_constraints': str,
            'metadata': List[MetaDataInner],
            'reservation_id': str,
            'resource_group_id': str,
            'user_data': AllocateComputeRequestUserData,
            'vc_image_id': str
        }

        self.attribute_map = {
            'affinity_or_anti_affinity_constraints': 'affinityOrAntiAffinityConstraints',
            'compute_flavour_id': 'computeFlavourId',
            'compute_name': 'computeName',
            'interface_data': 'interfaceData',
            'location_constraints': 'locationConstraints',
            'metadata': 'metadata',
            'reservation_id': 'reservationId',
            'resource_group_id': 'resourceGroupId',
            'user_data': 'userData',
            'vc_image_id': 'vcImageId'
        }

        self._affinity_or_anti_affinity_constraints = affinity_or_anti_affinity_constraints
        self._compute_flavour_id = compute_flavour_id
        self._compute_name = compute_name
        self._interface_data = interface_data
        self._location_constraints = location_constraints
        self._metadata = metadata
        self._reservation_id = reservation_id
        self._resource_group_id = resource_group_id
        self._user_data = user_data
        self._vc_image_id = vc_image_id

    @classmethod
    def from_dict(cls, dikt) -> 'VIMAllocateComputeRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The VIMAllocateComputeRequest of this VIMAllocateComputeRequest.  # noqa: E501
        :rtype: VIMAllocateComputeRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def affinity_or_anti_affinity_constraints(self) -> List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]:
        """Gets the affinity_or_anti_affinity_constraints of this VIMAllocateComputeRequest.

        A list of elements with affinity or anti affinity information of the virtualised compute resource to allocate. There should be a relation between the constraints defined in the different elements of the list.  # noqa: E501

        :return: The affinity_or_anti_affinity_constraints of this VIMAllocateComputeRequest.
        :rtype: List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]
        """
        return self._affinity_or_anti_affinity_constraints

    @affinity_or_anti_affinity_constraints.setter
    def affinity_or_anti_affinity_constraints(self, affinity_or_anti_affinity_constraints: List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]):
        """Sets the affinity_or_anti_affinity_constraints of this VIMAllocateComputeRequest.

        A list of elements with affinity or anti affinity information of the virtualised compute resource to allocate. There should be a relation between the constraints defined in the different elements of the list.  # noqa: E501

        :param affinity_or_anti_affinity_constraints: The affinity_or_anti_affinity_constraints of this VIMAllocateComputeRequest.
        :type affinity_or_anti_affinity_constraints: List[AllocateComputeRequestAffinityOrAntiAffinityConstraints]
        """
        if affinity_or_anti_affinity_constraints is None:
            raise ValueError("Invalid value for `affinity_or_anti_affinity_constraints`, must not be `None`")  # noqa: E501

        self._affinity_or_anti_affinity_constraints = affinity_or_anti_affinity_constraints

    @property
    def compute_flavour_id(self) -> str:
        """Gets the compute_flavour_id of this VIMAllocateComputeRequest.

        Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate. The Compute Flavour is created with Create Compute Flavour operation  # noqa: E501

        :return: The compute_flavour_id of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._compute_flavour_id

    @compute_flavour_id.setter
    def compute_flavour_id(self, compute_flavour_id: str):
        """Sets the compute_flavour_id of this VIMAllocateComputeRequest.

        Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate. The Compute Flavour is created with Create Compute Flavour operation  # noqa: E501

        :param compute_flavour_id: The compute_flavour_id of this VIMAllocateComputeRequest.
        :type compute_flavour_id: str
        """
        if compute_flavour_id is None:
            raise ValueError("Invalid value for `compute_flavour_id`, must not be `None`")  # noqa: E501

        self._compute_flavour_id = compute_flavour_id

    @property
    def compute_name(self) -> str:
        """Gets the compute_name of this VIMAllocateComputeRequest.

        Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.  # noqa: E501

        :return: The compute_name of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._compute_name

    @compute_name.setter
    def compute_name(self, compute_name: str):
        """Sets the compute_name of this VIMAllocateComputeRequest.

        Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.  # noqa: E501

        :param compute_name: The compute_name of this VIMAllocateComputeRequest.
        :type compute_name: str
        """
        if compute_name is None:
            raise ValueError("Invalid value for `compute_name`, must not be `None`")  # noqa: E501

        self._compute_name = compute_name

    @property
    def interface_data(self) -> List[AllocateComputeRequestInterfaceData]:
        """Gets the interface_data of this VIMAllocateComputeRequest.

        The data of network interfaces which are specific to a Virtual Compute Resource instance.  # noqa: E501

        :return: The interface_data of this VIMAllocateComputeRequest.
        :rtype: List[AllocateComputeRequestInterfaceData]
        """
        return self._interface_data

    @interface_data.setter
    def interface_data(self, interface_data: List[AllocateComputeRequestInterfaceData]):
        """Sets the interface_data of this VIMAllocateComputeRequest.

        The data of network interfaces which are specific to a Virtual Compute Resource instance.  # noqa: E501

        :param interface_data: The interface_data of this VIMAllocateComputeRequest.
        :type interface_data: List[AllocateComputeRequestInterfaceData]
        """
        if interface_data is None:
            raise ValueError("Invalid value for `interface_data`, must not be `None`")  # noqa: E501

        self._interface_data = interface_data

    @property
    def location_constraints(self) -> str:
        """Gets the location_constraints of this VIMAllocateComputeRequest.

        If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone.  # noqa: E501

        :return: The location_constraints of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._location_constraints

    @location_constraints.setter
    def location_constraints(self, location_constraints: str):
        """Sets the location_constraints of this VIMAllocateComputeRequest.

        If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone.  # noqa: E501

        :param location_constraints: The location_constraints of this VIMAllocateComputeRequest.
        :type location_constraints: str
        """
        if location_constraints is None:
            raise ValueError("Invalid value for `location_constraints`, must not be `None`")  # noqa: E501

        self._location_constraints = location_constraints

    @property
    def metadata(self) -> List[MetaDataInner]:
        """Gets the metadata of this VIMAllocateComputeRequest.

        The binary software image file.  # noqa: E501

        :return: The metadata of this VIMAllocateComputeRequest.
        :rtype: List[MetaDataInner]
        """
        return self._metadata

    @metadata.setter
    def metadata(self, metadata: List[MetaDataInner]):
        """Sets the metadata of this VIMAllocateComputeRequest.

        The binary software image file.  # noqa: E501

        :param metadata: The metadata of this VIMAllocateComputeRequest.
        :type metadata: List[MetaDataInner]
        """
        if metadata is None:
            raise ValueError("Invalid value for `metadata`, must not be `None`")  # noqa: E501

        self._metadata = metadata

    @property
    def reservation_id(self) -> str:
        """Gets the reservation_id of this VIMAllocateComputeRequest.

        Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.  # noqa: E501

        :return: The reservation_id of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._reservation_id

    @reservation_id.setter
    def reservation_id(self, reservation_id: str):
        """Sets the reservation_id of this VIMAllocateComputeRequest.

        Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.  # noqa: E501

        :param reservation_id: The reservation_id of this VIMAllocateComputeRequest.
        :type reservation_id: str
        """
        if reservation_id is None:
            raise ValueError("Invalid value for `reservation_id`, must not be `None`")  # noqa: E501

        self._reservation_id = reservation_id

    @property
    def resource_group_id(self) -> str:
        """Gets the resource_group_id of this VIMAllocateComputeRequest.

        Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.  # noqa: E501

        :return: The resource_group_id of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._resource_group_id

    @resource_group_id.setter
    def resource_group_id(self, resource_group_id: str):
        """Sets the resource_group_id of this VIMAllocateComputeRequest.

        Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.  # noqa: E501

        :param resource_group_id: The resource_group_id of this VIMAllocateComputeRequest.
        :type resource_group_id: str
        """
        if resource_group_id is None:
            raise ValueError("Invalid value for `resource_group_id`, must not be `None`")  # noqa: E501

        self._resource_group_id = resource_group_id

    @property
    def user_data(self) -> AllocateComputeRequestUserData:
        """Gets the user_data of this VIMAllocateComputeRequest.


        :return: The user_data of this VIMAllocateComputeRequest.
        :rtype: AllocateComputeRequestUserData
        """
        return self._user_data

    @user_data.setter
    def user_data(self, user_data: AllocateComputeRequestUserData):
        """Sets the user_data of this VIMAllocateComputeRequest.


        :param user_data: The user_data of this VIMAllocateComputeRequest.
        :type user_data: AllocateComputeRequestUserData
        """
        if user_data is None:
            raise ValueError("Invalid value for `user_data`, must not be `None`")  # noqa: E501

        self._user_data = user_data

    @property
    def vc_image_id(self) -> str:
        """Gets the vc_image_id of this VIMAllocateComputeRequest.

        Identifier of the virtualisation container software image (e.g. a virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.   # noqa: E501

        :return: The vc_image_id of this VIMAllocateComputeRequest.
        :rtype: str
        """
        return self._vc_image_id

    @vc_image_id.setter
    def vc_image_id(self, vc_image_id: str):
        """Sets the vc_image_id of this VIMAllocateComputeRequest.

        Identifier of the virtualisation container software image (e.g. a virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.   # noqa: E501

        :param vc_image_id: The vc_image_id of this VIMAllocateComputeRequest.
        :type vc_image_id: str
        """
        if vc_image_id is None:
            raise ValueError("Invalid value for `vc_image_id`, must not be `None`")  # noqa: E501

        self._vc_image_id = vc_image_id
