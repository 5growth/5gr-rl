
# VirtualComputeQuota

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**numVCPUs** | **Integer** | Number of CPU cores that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota. | 
**numVcInstances** | **Integer** | Number of virtualisation container instances that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**virtualMemSize** | [**BigDecimal**](BigDecimal.md) | Size of virtual memory that has been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota. | 



