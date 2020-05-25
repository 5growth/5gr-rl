
# CreateComputeResourceReservationRequestContainerFlavour

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accelerationCapability** | **String** | Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is requested. | 
**flavourId** | **String** | Identifier given to the compute flavour. | 
**storageAttributes** | [**List&lt;CreateComputeResourceReservationRequestContainerFlavourStorageAttributes&gt;**](CreateComputeResourceReservationRequestContainerFlavourStorageAttributes.md) | Element containing information about the size of virtualised storage resources (e.g. size of volume, in GB), the type of storage (e.g. volume, object). | 
**virtualCpu** | [**List&lt;CreateComputeResourceReservationRequestContainerFlavourVirtualCpu&gt;**](CreateComputeResourceReservationRequestContainerFlavourVirtualCpu.md) | The virtual CPU(s) of the virtualised compute. | 
**virtualMemory** | [**CreateComputeResourceReservationRequestContainerFlavourVirtualMemory**](CreateComputeResourceReservationRequestContainerFlavourVirtualMemory.md) |  | 
**virtualNetworkInterface** | [**CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface**](CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.md) |  | 



