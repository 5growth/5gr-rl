
# AllocateNetworkResultNetworkData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**bandwidth** | [**BigDecimal**](BigDecimal.md) | Minimum network bandwidth (in Mbps). | 
**isShared** | **Boolean** | It defines whether the virtualised network is shared among consumers. | 
**networkPort** | [**List&lt;AllocateNetworkResultNetworkDataNetworkPort&gt;**](AllocateNetworkResultNetworkDataNetworkPort.md) | Element providing information of an instantiated virtual network port. | 
**networkQoS** | [**List&lt;AllocateNetworkResultNetworkDataNetworkQoS&gt;**](AllocateNetworkResultNetworkDataNetworkQoS.md) | Element providing information about Quality of Service attributes that the network shall support. The cardinality can be \&quot;0\&quot; to allow for networks without any specified QoS requirements. | 
**networkResourceId** | **String** | Identifier of the virtualised network resource. | 
**networkResourceName** | **String** | Name of the virtualised network resource. | 
**networkType** | **String** | The type of network that maps to the virtualised network. This list is extensible. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, \&quot;l3-vpn\&quot;, etc. The cardinality can be \&quot;0\&quot; to cover the case where this attribute is not required to create the virtualised network. | 
**operationalState** | **String** | The operational state of the virtualised network. | 
**segmentType** | **String** | The isolated segment for the virtualised network. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; to allow for flat networks without any specific segmentation. | 
**sharingCriteria** | **String** | Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers. | 
**subnet** | **String** | Only present if the network provides layer 3 connectivity. | 
**zoneId** | **String** | If present, it identifies the Resource Zone where the virtual network resources have been allocated. | 



