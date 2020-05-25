
# ReservedVirtualCompute

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**computePoolReserved** | [**ReservedVirtualComputeComputePoolReserved**](ReservedVirtualComputeComputePoolReserved.md) |  | 
**endTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time. | 
**expiryTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the VIM can release the reservation in case no allocation request against this reservation was made. | 
**reservationId** | **String** | Identifier of the resource reservation. | 
**reservationStatus** | **String** | Status of the compute resource reservation, e.g. to indicate if a reservation is being used. | 
**startTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use. | 
**virtualisationContainerReserved** | [**ReservedVirtualComputeVirtualisationContainerReserved**](ReservedVirtualComputeVirtualisationContainerReserved.md) |  | 



