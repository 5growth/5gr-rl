
# VirtualComputeResourceInformationVirtualMemory

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**numaSupported** | **Boolean** | It specifies if the memory allocation can be cognisant of the relevant process/core allocation. | 
**virtualMemOversubscriptionPolicy** | **String** | The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no concrete policy is defined. | 
**virtualMemSize** | [**BigDecimal**](BigDecimal.md) | Amount of virtual memory (e.g. in MB). Cardinality \&quot;1\&quot; covers the case where a specific configuration for the consumable resource is advertised. | 



