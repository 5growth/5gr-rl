
# QueryComputeCapacityRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**attributeSelector** | **String** | Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested. | 
**computeResourceTypeId** | **String** | Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity. | 
**resourceCriteria** | **String** | Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualComputeResourceInformation, VirtualCpuResourceInformation, and VirtualMemoryResourceInformation information elements. This information element and the computeResourceTypeId are mutually exclusive. | 
**timePeriod** | [**QueryComputeCapacityRequestTimePeriod**](QueryComputeCapacityRequestTimePeriod.md) |  | 
**zoneId** | **String** | When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned. | 



