
# CreateComputeResourceQuotaRequestVirtualComputeQuota

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**numVCPUs** | **Integer** | Number of CPU cores to be restricted by the quota. The cardinality can be 0 if no specific number of CPU cores is to be restricted by the quota or the quota for CPU cores is not to be update. | 
**numVcInstances** | **Integer** | Number of virtualisation container instances to be restricted by the quota. The cardinality can be 0 if no specific number of virtualisation container instances is to be restricted by the quota or the quota for virtualisation container instances is not to be update. | 
**virtualMemSize** | [**BigDecimal**](BigDecimal.md) | Size of virtual memory to be restricted by the quota. The cardinality can be 0 if no specific size of virtual memory is to be restricted by the quota or the quota for virtual memory is not to be update. | 



