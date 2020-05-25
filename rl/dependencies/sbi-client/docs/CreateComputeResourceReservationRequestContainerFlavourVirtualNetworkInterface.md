
# CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accelerationCapability** | **String** | Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized. | 
**bandwidth** | **String** | The bandwidth of the virtual network interface (in Mbps). | 
**ipAddress** | **String** | The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP. | 
**macAddress** | **String** | The MAC address of the virtual network interface. | 
**metadata** | **String** | List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource. | 
**networkId** | **String** | In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network. | 
**networkPortId** | **String** | If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment. | 
**operationalState** | **String** | The operational state of the virtual network interface. | 
**ownerId** | **String** | Identifier of the owner of the network interface (e.g. a virtualised compute resource).  | 
**resourceId** | **String** | Identifier of the virtual network interface. | 
**typeConfiguration** | **String** | Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF). | 
**typeVirtualNic** | **String** | Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc. | 



