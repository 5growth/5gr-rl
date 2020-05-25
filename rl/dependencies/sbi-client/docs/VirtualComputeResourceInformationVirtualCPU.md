
# VirtualComputeResourceInformationVirtualCPU

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cpuArchitecture** | **String** | CPU architecture type. Examples are x86, ARM. | 
**cpuClock** | [**BigDecimal**](BigDecimal.md) | Minimum CPU clock rate (e.g. in MHz) available for the virtualised CPU resources. | 
**numVirtualCpu** | [**BigDecimal**](BigDecimal.md) | Number of virtual CPUs. Cardinality \&quot;1\&quot; covers the case where a specific configuration for the consumable resource is advertised. | 
**virtualCpuOversubscriptionPolicy** | **String** | The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no concrete policy is defined. | 
**virtualCpuPinningSupported** | **Boolean** | It defines whether CPU pinning capability is available on the consumable virtualised compute resource. | 



