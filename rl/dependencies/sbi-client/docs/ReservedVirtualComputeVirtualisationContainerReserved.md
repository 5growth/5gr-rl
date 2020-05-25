
# ReservedVirtualComputeVirtualisationContainerReserved

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accelerationCapability** | **String** | Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided. | 
**containerId** | **String** | The identifier of the virtualisation container that has been reserved. | 
**flavourId** | [**List&lt;ReservedVirtualComputeVirtualisationContainerReservedFlavourId&gt;**](ReservedVirtualComputeVirtualisationContainerReservedFlavourId.md) | Identifier of the given compute flavour used to reserve the virtualisation container. | 
**virtualCpu** | [**ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu**](ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu.md) |  | 
**virtualDisks** | [**List&lt;CreateComputeResourceReservationRequestContainerFlavourStorageAttributes&gt;**](CreateComputeResourceReservationRequestContainerFlavourStorageAttributes.md) | Element with information of the virtualised storage resources attached to the reserved virtualisation container. | 
**virtualMemory** | [**ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory**](ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory.md) |  | 
**virtualNetworkInterface** | [**List&lt;ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface&gt;**](ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.md) | Element with information of the virtual network interfaces of the reserved virtualisation container. | 
**zoneId** | **String** | References the resource zone where the virtualisation container has been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone. | 



