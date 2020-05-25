
# ReservedVirtualNetwork

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**endTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time. | 
**expiryTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the VIM can release the reservation in case no allocation request against this reservation was made. | 
**networkAttributes** | [**ReservedVirtualNetworkNetworkAttributes**](ReservedVirtualNetworkNetworkAttributes.md) |  | 
**networkPorts** | [**List&lt;ReservedVirtualNetworkNetworkPorts&gt;**](ReservedVirtualNetworkNetworkPorts.md) | List of specific network ports that have been reserved. | 
**publicIps** | **String** | List of public IP addresses that have been reserved. | 
**reservationId** | **String** | Identifier of the resource reservation. | 
**reservationStatus** | **String** | Status of the network resource reservation, e.g. to indicate if a reservation is being used. | 
**startTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use. | 
**zoneId** | **String** | References the resource zone where the virtual network resources have been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone. | 



