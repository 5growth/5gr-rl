
# CreateComputeResourceReservationRequestContainerFlavourStorageAttributes

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**flavourId** | **String** | Identifier of the storage flavour used to instantiate this virtual storage. | 
**hostId** | **String** | Identifier of the host where the virtualised storage resource is allocated. A cardinality of 0 refers to distributed storage solutions. | 
**operationalState** | **String** | Operational state of the resource. | 
**ownerId** | **String** | Identifier of the virtualised resource that owns and uses such a virtualised storage resource. The value can be NULL if the virtualised storage is not attached yet to any other resource (e.g. a virtual machine). | 
**sizeOfStorage** | [**BigDecimal**](BigDecimal.md) | Size of virtualised storage resource (e.g. size of volume, in GB). | 
**storageId** | **String** | Identifier of the virtualised storage resource. | 
**storageName** | **String** | Name of the virtualised storage resource. | 
**typeOfStorage** | **String** | Type of virtualised storage resource (e.g. volume, object). | 
**zoneId** | **String** | If present, it identifies the Resource Zone where the virtual storage resources have been allocated. | 



