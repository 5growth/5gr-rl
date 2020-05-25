
# ReservedVirtualNetworkNetworkAttributes

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bandwidth** | [**BigDecimal**](BigDecimal.md) | Minimum network bitrate (in Mbps). | 
**isShared** | **Boolean** | It defines whether the virtualised network that has been reserved is shared among consumers. | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource. |  [optional]
**networkType** | **String** | The type of network that maps to the virtualised network that has been reserved. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, etc. | 
**segmentType** | **String** | The isolated segment for the virtualised network that has been reserved. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key. | 



