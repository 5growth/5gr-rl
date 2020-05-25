
# AllocateNetworkResultNetworkPortData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**attachedResourceId** | **String** | Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface). The cardinality can be \&quot;0\&quot; if there is no specific resource connected to the network port. | 
**bandwidth** | [**BigDecimal**](BigDecimal.md) | The bandwidth of the virtual network port (in Mbps). Cardinality can be \&quot;0\&quot; for virtual network ports without any specific allocated bandwidth. | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource. |  [optional]
**networkId** | **String** | Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network. | 
**operationalState** | **String** | The operational state of the virtual network port. | 
**portType** | **String** | Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks. | 
**resourceId** | **String** | Identifier of the virtual network port. | 
**segmentId** | **String** | The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; for flat networks without any specific segmentation. | 



