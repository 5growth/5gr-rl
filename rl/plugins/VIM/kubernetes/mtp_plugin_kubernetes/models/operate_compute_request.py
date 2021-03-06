# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from mtp_plugin_kubernetes.models.base_model_ import Model
from mtp_plugin_kubernetes import util


class OperateComputeRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """

    def __init__(self, compute_id: str=None, compute_operation: str=None):  # noqa: E501
        """OperateComputeRequest - a model defined in Swagger

        :param compute_id: The compute_id of this OperateComputeRequest.  # noqa: E501
        :type compute_id: str
        :param compute_operation: The compute_operation of this OperateComputeRequest.  # noqa: E501
        :type compute_operation: str
        """
        self.swagger_types = {
            'compute_id': str,
            'compute_operation': str
        }

        self.attribute_map = {
            'compute_id': 'computeId',
            'compute_operation': 'computeOperation'
        }

        self._compute_id = compute_id
        self._compute_operation = compute_operation

    @classmethod
    def from_dict(cls, dikt) -> 'OperateComputeRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The OperateComputeRequest of this OperateComputeRequest.  # noqa: E501
        :rtype: OperateComputeRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def compute_id(self) -> str:
        """Gets the compute_id of this OperateComputeRequest.

        Identifier of the compute resource  # noqa: E501

        :return: The compute_id of this OperateComputeRequest.
        :rtype: str
        """
        return self._compute_id

    @compute_id.setter
    def compute_id(self, compute_id: str):
        """Sets the compute_id of this OperateComputeRequest.

        Identifier of the compute resource  # noqa: E501

        :param compute_id: The compute_id of this OperateComputeRequest.
        :type compute_id: str
        """
        if compute_id is None:
            raise ValueError("Invalid value for `compute_id`, must not be `None`")  # noqa: E501

        self._compute_id = compute_id

    @property
    def compute_operation(self) -> str:
        """Gets the compute_operation of this OperateComputeRequest.

        Operation Type on the compute resource  # noqa: E501

        :return: The compute_operation of this OperateComputeRequest.
        :rtype: str
        """
        return self._compute_operation

    @compute_operation.setter
    def compute_operation(self, compute_operation: str):
        """Sets the compute_operation of this OperateComputeRequest.

        Operation Type on the compute resource  # noqa: E501

        :param compute_operation: The compute_operation of this OperateComputeRequest.
        :type compute_operation: str
        """
        if compute_operation is None:
            raise ValueError("Invalid value for `compute_operation`, must not be `None`")  # noqa: E501

        self._compute_operation = compute_operation
