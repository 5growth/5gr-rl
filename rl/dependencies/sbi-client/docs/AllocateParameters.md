
# AllocateParameters

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**locationConstraints** | **String** | Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image. | 
**reservationId** | **String** | Identifier of the resource reservation applicable to this virtualised resource management operation. | 
**typeNetworkData** | **String** | The network data provides information about the particular virtual network resource to create. Cardinality can be \&quot;0\&quot; depending on the value of networkResourceType. | 
**affinityOrAntiAffinityConstraints** | **String** | The binary software image file. | 
**typeNetworkPortData** | **String** | The binary software image file. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**metadata** | **String** | The binary software image file. | 
**networkResourceType** | **String** | Type of virtualised network resource. Possible values are: \&quot;network\&quot;, \&quot;subnet\&quot; or network-port. | 
**networkResourceName** | **String** | Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side. | 
**typeSubnetData** | **String** | The subnet data provides information about the particular sub-network resource to create. | 
**bandwidth** | [**BigDecimal**](BigDecimal.md) | The bandwidth of the virtual network interface (in Mbps). | 
**delay** | **String** | Transmission delay. | 
**networkType** | **String** | The type of network that maps to the virtualised network. | 
**segmentType** | **String** | The isolated segment for the virtualised network. | 
**networkQoS** | **String** | Element providing information about Quality of Service attributes that the network shall support. | 
**isShared** | **Boolean** | It defines whether the virtualised network is shared among consumers. | 
**sharingCriteria** | **String** | Only present for shared networks. Indicate the sharing criteria for this network. These criteria might be a list of authorized consumers. | 
**layer3Attributes** | **String** | The attribute allows setting up a network providing defined layer 3 connectivity. | 
**portType** | **String** | Type of network port. | 
**networkId** | **String** | Identifier of the network that the port belongs to. | 
**segmentId** | **String** | The isolated segment the network port belongs to. | 
**ingressPointIPAddress** | **String** | The ingress point IP Address. | 
**ingressPointPortAddress** | **String** | The ingress point Port(interface) Address. | 
**egressPointIPAddress** | **String** | The engress point IP Address. |  [optional]
**egressPointPortAddress** | **String** | The engress point Port(interface) Address. | 
**wanLinkId** | **String** | The logical link ID between two nodes. | 



