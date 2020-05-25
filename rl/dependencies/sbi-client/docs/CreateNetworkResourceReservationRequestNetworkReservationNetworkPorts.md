
# CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bandwidth** | [**BigDecimal**](BigDecimal.md) | The bitrate of the virtual network port (in Mbps). | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource. |  [optional]
**portId** | **Integer** | Identifier of the network port to reserve. | 
**portType** | **String** | Type of network port. Examples of types are access ports, or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks. | 
**segmentId** | **String** | The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be 0 to allow for flat networks without any specific segmentation. | 



