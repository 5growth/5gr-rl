
# CreateComputeResourceReservationRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**affinityConstraint** | [**List&lt;AllocateComputeRequestAffinityOrAntiAffinityConstraints&gt;**](AllocateComputeRequestAffinityOrAntiAffinityConstraints.md) | Element with affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the affinity information of the virtualisation container(s) to reserve. | 
**antiAffinityConstraint** | [**List&lt;AllocateComputeRequestAffinityOrAntiAffinityConstraints&gt;**](AllocateComputeRequestAffinityOrAntiAffinityConstraints.md) | Element with anti-affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the anti-affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the anti-affinity information of the virtualisation container(s) to reserve. | 
**computePoolReservation** | [**CreateComputeResourceReservationRequestComputePoolReservation**](CreateComputeResourceReservationRequestComputePoolReservation.md) |  | 
**endTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time. | 
**expiryTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the VIM can release the reservation in case no allocation request against this reservation was made. | 
**locationConstraints** | **String** | If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**startTime** | [**OffsetDateTime**](OffsetDateTime.md) | Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use. | 
**virtualisationContainerReservation** | [**List&lt;CreateComputeResourceReservationRequestVirtualisationContainerReservation&gt;**](CreateComputeResourceReservationRequestVirtualisationContainerReservation.md) | Virtualisation containers that need to be reserved (e.g. following a specific compute \&quot;flavour\&quot;) | 



