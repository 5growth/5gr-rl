
# VirtualCompute

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accelerationCapability** | **List&lt;String&gt;** | Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided. | 
**computeId** | **String** | Identifier of the virtualised compute resource. | 
**computeName** | **String** | Name of the virtualised compute resource. | 
**flavourId** | **String** | Identifier of the given compute flavour used to instantiate this virtual compute. | 
**hostId** | **String** | Identifier of the host the virtualised compute resource is allocated on. | 
**operationalState** | **String** | Operational state of the compute resource. | 
**vcImageId** | **String** | Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \&quot;empty\&quot; virtualisation container is allocated. | 
**virtualCpu** | [**VirtualComputeVirtualCpu**](VirtualComputeVirtualCpu.md) |  | 
**virtualDisks** | **String** | Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.) | 
**virtualMemory** | [**VirtualComputeVirtualMemory**](VirtualComputeVirtualMemory.md) |  | 
**virtualNetworkInterface** | [**List&lt;ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface&gt;**](ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.md) | Element with information of the instantiated virtual network interfaces of the compute resource. | 
**zoneId** | **String** | If present, it identifies the Resource Zone where the virtual compute resources have been allocated. | 



