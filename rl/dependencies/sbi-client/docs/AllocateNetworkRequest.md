
# AllocateNetworkRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**affinityOrAntiAffinityConstraints** | **String** | The binary software image file. | 
**locationConstraints** | **String** | Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image. | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | The binary software image file. | 
**networkResourceName** | **String** | Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side. | 
**networkResourceType** | **String** | Type of virtualised network resource. Possible values are: \&quot;network\&quot;, \&quot;subnet\&quot; or network-port. | 
**reservationId** | **String** | Identifier of the resource reservation applicable to this virtualised resource management operation. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**typeNetworkData** | **String** | The network data provides information about the particular virtual network resource to create. Cardinality can be \&quot;0\&quot; depending on the value of networkResourceType. | 
**typeNetworkPortData** | **String** | The binary software image file. | 
**typeSubnetData** | **String** | The binary software image file. | 



