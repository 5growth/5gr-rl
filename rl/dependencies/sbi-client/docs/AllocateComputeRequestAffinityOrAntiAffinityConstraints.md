
# AllocateComputeRequestAffinityOrAntiAffinityConstraints

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**affinityAntiAffinityResourceGroup** | **String** | Identifier of the producer-managed group of virtualised resources with which the actual resource is requested to be affine or anti-affine. Either affinityAntiAffinityResourceList or affinityAntiAffinityResourceGroup but not both shall be present. | 
**affinityAntiAffinityResourceList** | [**AllocateComputeRequestAffinityAntiAffinityResourceList**](AllocateComputeRequestAffinityAntiAffinityResourceList.md) |  | 
**scope** | **String** | Qualifies the scope of the constraint. In case of compute resource: e.g. \&quot;NFVI-PoP\&quot; or \&quot;NFVI Node\&quot;. In case of ports: e.g. \&quot;virtual switch or router\&quot; or \&quot;physical NIC\&quot;, or \&quot;physical network\&quot; or \&quot;NFVI Node\&quot;. In case of networks: e.g. \&quot;physical NIC\&quot;, \&quot;physical network\&quot; or \&quot;NFVI Node\&quot;. In case of subnets: it should be ignored. Defaults to \&quot;NFVI Node\&quot; if absent. | 
**type** | **String** | Indicates whether this is an affinity or anti-affinity constraint. | 



