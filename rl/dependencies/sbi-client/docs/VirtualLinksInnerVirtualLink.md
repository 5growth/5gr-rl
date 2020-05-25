
# VirtualLinksInnerVirtualLink

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**virtualLinkId** | **String** | (numbered) Identifier of the virtual link | 
**totalBandwidth** | [**BigDecimal**](BigDecimal.md) | Total bandwidth capacity supported by the logical link (in Mbps). | 
**availableBandwidth** | [**BigDecimal**](BigDecimal.md) | Available bandwidth capacity supported by the logical link (in Mbps). | 
**networkQoS** | [**VirtualLinksInnerVirtualLinkNetworkQoS**](VirtualLinksInnerVirtualLinkNetworkQoS.md) |  | 
**srcGwId** | **String** | 5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32). | 
**srcLinkId** | **Integer** | Local Logical Link Id. |  [optional]
**dstGwId** | **String** | 5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32). | 
**dstLinkId** | **Integer** | Remote Logical Link Id. |  [optional]
**networkLayer** | **String** |  |  [optional]



