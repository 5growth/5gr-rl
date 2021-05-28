# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util
from mtp_plugin_kubernetes.models.dns_rule_descriptor import DNSRuleDescriptor
from mtp_plugin_kubernetes.models.latency_descriptor import LatencyDescriptor
from mtp_plugin_kubernetes.models.service_dependency import ServiceDependency
from mtp_plugin_kubernetes.models.service_descriptor import ServiceDescriptor
from mtp_plugin_kubernetes.models.traffic_rule_descriptor import TrafficRuleDescriptor
from mtp_plugin_kubernetes.models.transport_dependency import TransportDependency


class MECTrafficServiceCreationRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, request_id: str=None, region_id: str=None, app_service_required: List[ServiceDependency]=None, app_service_optional: List[ServiceDependency]=None, app_service_produced: List[ServiceDescriptor]=None, transport_dependencies: List[TransportDependency]=None, app_traffic_rule: List[TrafficRuleDescriptor]=None, app_dns_rule: List[DNSRuleDescriptor]=None, app_latency: LatencyDescriptor=None, status: str=None, created_at: str=None, last_modified: str=None):  # noqa: E501
        """MECTrafficServiceCreationRequest - a model defined in Swagger

        :param request_id: The request_id of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type request_id: str
        :param region_id: The region_id of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type region_id: str
        :param app_service_required: The app_service_required of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_service_required: List[ServiceDependency]
        :param app_service_optional: The app_service_optional of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_service_optional: List[ServiceDependency]
        :param app_service_produced: The app_service_produced of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_service_produced: List[ServiceDescriptor]
        :param transport_dependencies: The transport_dependencies of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type transport_dependencies: List[TransportDependency]
        :param app_traffic_rule: The app_traffic_rule of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_traffic_rule: List[TrafficRuleDescriptor]
        :param app_dns_rule: The app_dns_rule of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_dns_rule: List[DNSRuleDescriptor]
        :param app_latency: The app_latency of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type app_latency: LatencyDescriptor
        :param status: The status of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type status: str
        :param created_at: The created_at of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type created_at: str
        :param last_modified: The last_modified of this MECTrafficServiceCreationRequest.  # noqa: E501
        :type last_modified: str
        """
        self.swagger_types = {
            'request_id': str,
            'region_id': str,
            'app_service_required': List[ServiceDependency],
            'app_service_optional': List[ServiceDependency],
            'app_service_produced': List[ServiceDescriptor],
            'transport_dependencies': List[TransportDependency],
            'app_traffic_rule': List[TrafficRuleDescriptor],
            'app_dns_rule': List[DNSRuleDescriptor],
            'app_latency': LatencyDescriptor,
            'status': str,
            'created_at': str,
            'last_modified': str
        }

        self.attribute_map = {
            'request_id': 'requestId',
            'region_id': 'regionId',
            'app_service_required': 'appServiceRequired',
            'app_service_optional': 'appServiceOptional',
            'app_service_produced': 'appServiceProduced',
            'transport_dependencies': 'transportDependencies',
            'app_traffic_rule': 'appTrafficRule',
            'app_dns_rule': 'appDNSRule',
            'app_latency': 'appLatency',
            'status': 'status',
            'created_at': 'createdAt',
            'last_modified': 'lastModified'
        }

        self._request_id = request_id
        self._region_id = region_id
        self._app_service_required = app_service_required
        self._app_service_optional = app_service_optional
        self._app_service_produced = app_service_produced
        self._transport_dependencies = transport_dependencies
        self._app_traffic_rule = app_traffic_rule
        self._app_dns_rule = app_dns_rule
        self._app_latency = app_latency
        self._status = status
        self._created_at = created_at
        self._last_modified = last_modified

    @classmethod
    def from_dict(cls, dikt) -> 'MECTrafficServiceCreationRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The MECTrafficServiceCreationRequest of this MECTrafficServiceCreationRequest.  # noqa: E501
        :rtype: MECTrafficServiceCreationRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def request_id(self) -> str:
        """Gets the request_id of this MECTrafficServiceCreationRequest.

        Identifier of the request, assigned by the MEC plugin at creation time.  # noqa: E501

        :return: The request_id of this MECTrafficServiceCreationRequest.
        :rtype: str
        """
        return self._request_id

    @request_id.setter
    def request_id(self, request_id: str):
        """Sets the request_id of this MECTrafficServiceCreationRequest.

        Identifier of the request, assigned by the MEC plugin at creation time.  # noqa: E501

        :param request_id: The request_id of this MECTrafficServiceCreationRequest.
        :type request_id: str
        """

        self._request_id = request_id

    @property
    def region_id(self) -> str:
        """Gets the region_id of this MECTrafficServiceCreationRequest.

        Identifier of the region where the MEC app will be instantiated.  # noqa: E501

        :return: The region_id of this MECTrafficServiceCreationRequest.
        :rtype: str
        """
        return self._region_id

    @region_id.setter
    def region_id(self, region_id: str):
        """Sets the region_id of this MECTrafficServiceCreationRequest.

        Identifier of the region where the MEC app will be instantiated.  # noqa: E501

        :param region_id: The region_id of this MECTrafficServiceCreationRequest.
        :type region_id: str
        """
        if region_id is None:
            raise ValueError("Invalid value for `region_id`, must not be `None`")  # noqa: E501

        self._region_id = region_id

    @property
    def app_service_required(self) -> List[ServiceDependency]:
        """Gets the app_service_required of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application requires to run.  # noqa: E501

        :return: The app_service_required of this MECTrafficServiceCreationRequest.
        :rtype: List[ServiceDependency]
        """
        return self._app_service_required

    @app_service_required.setter
    def app_service_required(self, app_service_required: List[ServiceDependency]):
        """Sets the app_service_required of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application requires to run.  # noqa: E501

        :param app_service_required: The app_service_required of this MECTrafficServiceCreationRequest.
        :type app_service_required: List[ServiceDependency]
        """

        self._app_service_required = app_service_required

    @property
    def app_service_optional(self) -> List[ServiceDependency]:
        """Gets the app_service_optional of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application may use if available.  # noqa: E501

        :return: The app_service_optional of this MECTrafficServiceCreationRequest.
        :rtype: List[ServiceDependency]
        """
        return self._app_service_optional

    @app_service_optional.setter
    def app_service_optional(self, app_service_optional: List[ServiceDependency]):
        """Sets the app_service_optional of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application may use if available.  # noqa: E501

        :param app_service_optional: The app_service_optional of this MECTrafficServiceCreationRequest.
        :type app_service_optional: List[ServiceDependency]
        """

        self._app_service_optional = app_service_optional

    @property
    def app_service_produced(self) -> List[ServiceDescriptor]:
        """Gets the app_service_produced of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application is able to produce to the platform or other mobile edge applications. Only relevant for service-producing apps.  # noqa: E501

        :return: The app_service_produced of this MECTrafficServiceCreationRequest.
        :rtype: List[ServiceDescriptor]
        """
        return self._app_service_produced

    @app_service_produced.setter
    def app_service_produced(self, app_service_produced: List[ServiceDescriptor]):
        """Sets the app_service_produced of this MECTrafficServiceCreationRequest.

        Describes services a mobile edge application is able to produce to the platform or other mobile edge applications. Only relevant for service-producing apps.  # noqa: E501

        :param app_service_produced: The app_service_produced of this MECTrafficServiceCreationRequest.
        :type app_service_produced: List[ServiceDescriptor]
        """

        self._app_service_produced = app_service_produced

    @property
    def transport_dependencies(self) -> List[TransportDependency]:
        """Gets the transport_dependencies of this MECTrafficServiceCreationRequest.

        Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.  # noqa: E501

        :return: The transport_dependencies of this MECTrafficServiceCreationRequest.
        :rtype: List[TransportDependency]
        """
        return self._transport_dependencies

    @transport_dependencies.setter
    def transport_dependencies(self, transport_dependencies: List[TransportDependency]):
        """Sets the transport_dependencies of this MECTrafficServiceCreationRequest.

        Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.  # noqa: E501

        :param transport_dependencies: The transport_dependencies of this MECTrafficServiceCreationRequest.
        :type transport_dependencies: List[TransportDependency]
        """

        self._transport_dependencies = transport_dependencies

    @property
    def app_traffic_rule(self) -> List[TrafficRuleDescriptor]:
        """Gets the app_traffic_rule of this MECTrafficServiceCreationRequest.

        Describes traffic rules the mobile edge application requires.  # noqa: E501

        :return: The app_traffic_rule of this MECTrafficServiceCreationRequest.
        :rtype: List[TrafficRuleDescriptor]
        """
        return self._app_traffic_rule

    @app_traffic_rule.setter
    def app_traffic_rule(self, app_traffic_rule: List[TrafficRuleDescriptor]):
        """Sets the app_traffic_rule of this MECTrafficServiceCreationRequest.

        Describes traffic rules the mobile edge application requires.  # noqa: E501

        :param app_traffic_rule: The app_traffic_rule of this MECTrafficServiceCreationRequest.
        :type app_traffic_rule: List[TrafficRuleDescriptor]
        """

        self._app_traffic_rule = app_traffic_rule

    @property
    def app_dns_rule(self) -> List[DNSRuleDescriptor]:
        """Gets the app_dns_rule of this MECTrafficServiceCreationRequest.

        Describes DNS rules the mobile edge application requires.  # noqa: E501

        :return: The app_dns_rule of this MECTrafficServiceCreationRequest.
        :rtype: List[DNSRuleDescriptor]
        """
        return self._app_dns_rule

    @app_dns_rule.setter
    def app_dns_rule(self, app_dns_rule: List[DNSRuleDescriptor]):
        """Sets the app_dns_rule of this MECTrafficServiceCreationRequest.

        Describes DNS rules the mobile edge application requires.  # noqa: E501

        :param app_dns_rule: The app_dns_rule of this MECTrafficServiceCreationRequest.
        :type app_dns_rule: List[DNSRuleDescriptor]
        """

        self._app_dns_rule = app_dns_rule

    @property
    def app_latency(self) -> LatencyDescriptor:
        """Gets the app_latency of this MECTrafficServiceCreationRequest.


        :return: The app_latency of this MECTrafficServiceCreationRequest.
        :rtype: LatencyDescriptor
        """
        return self._app_latency

    @app_latency.setter
    def app_latency(self, app_latency: LatencyDescriptor):
        """Sets the app_latency of this MECTrafficServiceCreationRequest.


        :param app_latency: The app_latency of this MECTrafficServiceCreationRequest.
        :type app_latency: LatencyDescriptor
        """

        self._app_latency = app_latency

    @property
    def status(self) -> str:
        """Gets the status of this MECTrafficServiceCreationRequest.

        Status of the request.  # noqa: E501

        :return: The status of this MECTrafficServiceCreationRequest.
        :rtype: str
        """
        return self._status

    @status.setter
    def status(self, status: str):
        """Sets the status of this MECTrafficServiceCreationRequest.

        Status of the request.  # noqa: E501

        :param status: The status of this MECTrafficServiceCreationRequest.
        :type status: str
        """
        allowed_values = ["CREATED", "FAILED", "PENDING", "DELETED"]  # noqa: E501
        if status not in allowed_values:
            raise ValueError(
                "Invalid value for `status` ({0}), must be one of {1}"
                .format(status, allowed_values)
            )

        self._status = status

    @property
    def created_at(self) -> str:
        """Gets the created_at of this MECTrafficServiceCreationRequest.

        Timestamp of when service request was created.  # noqa: E501

        :return: The created_at of this MECTrafficServiceCreationRequest.
        :rtype: str
        """
        return self._created_at

    @created_at.setter
    def created_at(self, created_at: str):
        """Sets the created_at of this MECTrafficServiceCreationRequest.

        Timestamp of when service request was created.  # noqa: E501

        :param created_at: The created_at of this MECTrafficServiceCreationRequest.
        :type created_at: str
        """

        self._created_at = created_at

    @property
    def last_modified(self) -> str:
        """Gets the last_modified of this MECTrafficServiceCreationRequest.

        Timestamp of when service was last modified (e.g., deleted).  # noqa: E501

        :return: The last_modified of this MECTrafficServiceCreationRequest.
        :rtype: str
        """
        return self._last_modified

    @last_modified.setter
    def last_modified(self, last_modified: str):
        """Sets the last_modified of this MECTrafficServiceCreationRequest.

        Timestamp of when service was last modified (e.g., deleted).  # noqa: E501

        :param last_modified: The last_modified of this MECTrafficServiceCreationRequest.
        :type last_modified: str
        """

        self._last_modified = last_modified