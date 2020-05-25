
# AllocateNetworkResultSubnetData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addressPool** | **String** | Address pools for the network/subnetwork. The cardinality can be 0 when VIM is allowed to allocate all addresses in the CIDR except for the address of the network/subnetwork gateway. | 
**cidr** | **String** | The CIDR of the network/subnetwork, i.e. network address and subnet mask. | 
**gatewayIp** | **String** | The IP address of the network/subnetwork gateway. | 
**ipVersion** | **String** | The IP version of the network/subnetwork. | 
**isDhcpEnabled** | **Boolean** | True when DHCP is enabled for this network/subnetwork, or false otherwise. | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource. |  [optional]
**networkId** | **String** | The identifier of the virtualised network that the virtualised sub-network is attached to. The cardinality can be 0 to cover the case where this type is used to describe the L3 attributes of a network rather than a subnetwork. | 
**operationalState** | **String** | The operational state of the virtualised sub-network. | 
**resourceId** | **String** | Identifier of the virtualised sub-network. | 



