
# CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cpuMap** | **String** | Shows the association of virtual CPU cores to physical CPU cores. | 
**cpuPinningPolicy** | **String** | The policy can take values of \&quot;static\&quot; or \&quot;dynamic\&quot;. In case of \&quot;static\&quot; the virtual CPU cores have been allocated to physical CPU cores according to the rules defined in cpuPinningRules. In case of \&quot;dynamic\&quot; the allocation of virtual CPU cores to physical CPU cores is decided by the VIM. | 
**cpuPinningRules** | **String** | A list of rules that should be considered during the allocation of the virtual CPUs to physical CPUs in case of \&quot;static\&quot; cpuPinningPolicy. | 



