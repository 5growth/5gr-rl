from decimal import *

import connexion

import sbi.kube_create_container
from sbi.kube_create_container import AllocateCompute
from sbi.kube_get_info import KubeGetInfo
from mtp_plugin_kubernetes.config.config import ConfigurationFile
from mtp_plugin_kubernetes.models.allocate_compute_request import AllocateComputeRequest  # noqa: E501
from mtp_plugin_kubernetes.models.capacity_information import CapacityInformation  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_flavour import VirtualComputeFlavour  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_resource_information import VirtualComputeResourceInformation  # noqa: E501
from mtp_plugin_kubernetes.models.virtual_compute_resource_information_virtual_cpu import \
    VirtualComputeResourceInformationVirtualCPU  # noqa: F401,E501
from mtp_plugin_kubernetes.models.virtual_compute_resource_information_virtual_memory import \
    VirtualComputeResourceInformationVirtualMemory  # noqa: F401,E501


def allocate_compute(body):  # noqa: E501
    """allocate_compute

     # noqa: E501

    :param body: 
    :type body: dict | bytes

    :rtype: VirtualCompute
    """
    if connexion.request.is_json:
        body = AllocateComputeRequest.from_dict(connexion.request.get_json())  # noqa: E501
        allocate_compute = AllocateCompute()
        # allocate_compute.update_service()
        # allocate_compute.update_deployment()
        response = allocate_compute.allocate_compute(connexion.request.get_json())
    return response


def create_flavour(Flavour):  # noqa: E501
    """create_flavour

     # noqa: E501

    :param Flavour: 
    :type Flavour: dict | bytes

    :rtype: str
    """
    if connexion.request.is_json:
        Flavour = VirtualComputeFlavour.from_dict(connexion.request.get_json())  # noqa: E501
    return 'Not supported'


def delete_flavours(id):  # noqa: E501
    """delete_flavours

     # noqa: E501

    :param id: Identifier of the Compute Flavour to be deleted.
    :type id: str

    :rtype: None
    """
    return 'Not supported'


def query_compute_capacity(ComputeResourceTypeId):  # noqa: E501
    """query_compute_capacity

     # noqa: E501

    :param ComputeResourceTypeId: 
    :type ComputeResourceTypeId: str

    :rtype: CapacityInformation
    """
    kub = KubeGetInfo()

    if ComputeResourceTypeId == "11":
        cap_info = CapacityInformation(
            allocated_capacity=kub.allocated_memory,
            available_capacity=kub.allocatable_memory,
            reserved_capacity="0",
            total_capacity=kub.total_memory
        )
    elif ComputeResourceTypeId == "21":
        cap_info = CapacityInformation(
            allocated_capacity=Decimal(kub.allocated_processor).quantize(Decimal('1.'), rounding=ROUND_HALF_EVEN),
            available_capacity=Decimal(kub.allocatable_processor).quantize(Decimal('1.'), rounding=ROUND_HALF_EVEN),
            reserved_capacity="0",
            total_capacity=Decimal(kub.total_processor).quantize(Decimal('1.'), rounding=ROUND_HALF_DOWN)
        )
    return cap_info


def query_compute_information(zoneId):  # noqa: E501
    """query_compute_information

     # noqa: E501

    :param zoneId: Filter defining the information of consumable virtualised resources on which the query applies.
    :type zoneId: str

    :rtype: List[VirtualComputeResourceInformation]
    """
    kube_get_info = KubeGetInfo()
    print(kube_get_info.architecture)

    return_list = []
    virtual_cpu = VirtualComputeResourceInformationVirtualCPU(
        cpu_architecture=kube_get_info.architecture,
        cpu_clock=0,
        num_virtual_cpu=Decimal(kube_get_info.allocatable_processor).quantize(Decimal('1.'), rounding=ROUND_UP),
        virtual_cpu_pinning_supported=False,
        virtual_cpu_oversubscription_policy="null"
    )

    virtual_memory = VirtualComputeResourceInformationVirtualMemory(
        numa_supported=False,
        virtual_mem_oversubscription_policy="null",
        virtual_mem_size=0
    )

    virt_comp_resouce_information_11 = VirtualComputeResourceInformation(
        acceleration_capability="null",
        compute_resource_type_id="11",
        virtual_cpu=None,
        virtual_memory=virtual_memory
    )

    virt_comp_resouce_information_21 = VirtualComputeResourceInformation(
        acceleration_capability="null",
        compute_resource_type_id="21",
        virtual_cpu=virtual_cpu,
        virtual_memory=None
    )
    return_list.append(virt_comp_resouce_information_11)
    return_list.append(virt_comp_resouce_information_21)
    print(return_list)

    return return_list


def query_compute_resource_zone():  # noqa: E501
    """query_compute_resource_zone

     # noqa: E501


    :rtype: List[ResourceZone]
    """
    return_list = []
    zonelist = ConfigurationFile().config['VIM']['DomainInfo']['Zonelist']['Zone']

    if isinstance(zonelist, list) == False:
        return_list = [zonelist]
    else:
        return_list.extend(zonelist)
    return return_list


def query_flavors():  # noqa: E501
    """query_flavors

     # noqa: E501


    :rtype: List[VirtualComputeFlavour]
    """
    return 'Not supported'


def query_nfvi_po_p_compute_information():  # noqa: E501
    """query_nfvi_po_p_compute_information

     # noqa: E501


    :rtype: List[NfviPop]
    """
    return_list = []
    nfvipops = ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop']
    if (isinstance(nfvipops, list) == False):
        return_list = [nfvipops]
    else:
        return_list.extend(nfvipops)
    return return_list


def query_resources(**kwargs):  # noqa: E501
    """query_resources

     # noqa: E501


    :rtype: List[VirtualCompute]
    """

    return sbi.kube_create_container.AllocateCompute().get_info_request(kwargs)


def terminate_abstract_resources(computeId):  # noqa: E501
    """terminate_abstract_resources

     # noqa: E501

    :param computeId: Identifier(s) of the abstract compute resource(s) to be terminated.
    :type computeId: List[str]

    :rtype: List[str]
    """
    allocate_compute = AllocateCompute()
    response = allocate_compute.terminate_compute(computeId)

    return response
