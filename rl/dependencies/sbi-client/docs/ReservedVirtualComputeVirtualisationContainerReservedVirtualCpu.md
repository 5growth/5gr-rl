
# ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cpuArchitecture** | **String** | CPU architecture type. Examples are x86, ARM. | 
**cpuClock** | [**BigDecimal**](BigDecimal.md) | Minimum CPU clock rate (e.g. in MHz) available for the virtualised CPU resources. | 
**numVirtualCpu** | **Integer** | Number of virtual CPUs. | 
**virtualCpuOversubscriptionPolicy** | **String** | The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no policy has been defined during the allocation request. | 
**virtualCpuPinning** | [**CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning**](CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning.md) |  | 



