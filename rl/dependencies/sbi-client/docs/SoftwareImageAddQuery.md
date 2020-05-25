
# SoftwareImageAddQuery

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | The name of the software image. | 
**provider** | **String** | The provider of the software image. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**softwareImage** | **String** | The binary software image file. | 
**userMetadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | User-defined metadata. | 
**version** | **String** | The version of the software image. | 
**visibility** | **String** | Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image. | 



