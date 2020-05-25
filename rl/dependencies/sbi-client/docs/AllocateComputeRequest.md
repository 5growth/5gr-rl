
# AllocateComputeRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**affinityOrAntiAffinityConstraints** | [**List&lt;AllocateComputeRequestAffinityOrAntiAffinityConstraints&gt;**](AllocateComputeRequestAffinityOrAntiAffinityConstraints.md) | A list of elements with affinity or anti affinity information of the virtualised compute resource to allocate. There should be a relation between the constraints defined in the different elements of the list. | 
**computeFlavourId** | **String** | Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate. The Compute Flavour is created with Create Compute Flavour operation | 
**computeName** | **String** | Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side. | 
**interfaceData** | [**List&lt;AllocateComputeRequestInterfaceData&gt;**](AllocateComputeRequestInterfaceData.md) | The data of network interfaces which are specific to a Virtual Compute Resource instance. | 
**locationConstraints** | **String** | If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone. | 
**metadata** | [**List&lt;MetaDataInner&gt;**](MetaDataInner.md) | The binary software image file. | 
**reservationId** | **String** | Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used. | 
**resourceGroupId** | **String** | Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain. | 
**userData** | [**AllocateComputeRequestUserData**](AllocateComputeRequestUserData.md) |  | 
**vcImageId** | **String** | Identifier of the virtualisation container software image (e.g. a virtual machine image). Cardinality can be 0 if an \&quot;empty\&quot; virtualisation container is allocated.  | 



