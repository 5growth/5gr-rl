package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The virtual network interfaces of the virtualised compute.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The virtual network interfaces of the virtualised compute.")

public class CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface   {
  
  private @Valid String accelerationCapability = null;
  private @Valid String bandwidth = null;
  private @Valid String ipAddress = null;
  private @Valid String macAddress = null;
  private @Valid String metadata = null;
  private @Valid String networkId = null;
  private @Valid String networkPortId = null;
  private @Valid String operationalState = null;
  private @Valid String ownerId = null;
  private @Valid String resourceId = null;
  private @Valid String typeConfiguration = null;
  private @Valid String typeVirtualNic = null;

  /**
   * Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public String getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * The bandwidth of the virtual network interface (in Mbps).
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface bandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The bandwidth of the virtual network interface (in Mbps).")
  @JsonProperty("bandwidth")
  @NotNull
  public String getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.")
  @JsonProperty("ipAddress")
  @NotNull
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * The MAC address of the virtual network interface.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The MAC address of the virtual network interface.")
  @JsonProperty("macAddress")
  @NotNull
  public String getMacAddress() {
    return macAddress;
  }
  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface metadata(String metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.")
  @JsonProperty("metadata")
  @NotNull
  public String getMetadata() {
    return metadata;
  }
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface networkPortId(String networkPortId) {
    this.networkPortId = networkPortId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.")
  @JsonProperty("networkPortId")
  @NotNull
  public String getNetworkPortId() {
    return networkPortId;
  }
  public void setNetworkPortId(String networkPortId) {
    this.networkPortId = networkPortId;
  }

  /**
   * The operational state of the virtual network interface.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The operational state of the virtual network interface.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * Identifier of the owner of the network interface (e.g. a virtualised compute resource). 
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the owner of the network interface (e.g. a virtualised compute resource). ")
  @JsonProperty("ownerId")
  @NotNull
  public String getOwnerId() {
    return ownerId;
  }
  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  /**
   * Identifier of the virtual network interface.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtual network interface.")
  @JsonProperty("resourceId")
  @NotNull
  public String getResourceId() {
    return resourceId;
  }
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface typeConfiguration(String typeConfiguration) {
    this.typeConfiguration = typeConfiguration;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).")
  @JsonProperty("typeConfiguration")
  @NotNull
  public String getTypeConfiguration() {
    return typeConfiguration;
  }
  public void setTypeConfiguration(String typeConfiguration) {
    this.typeConfiguration = typeConfiguration;
  }

  /**
   * Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface typeVirtualNic(String typeVirtualNic) {
    this.typeVirtualNic = typeVirtualNic;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.")
  @JsonProperty("typeVirtualNic")
  @NotNull
  public String getTypeVirtualNic() {
    return typeVirtualNic;
  }
  public void setTypeVirtualNic(String typeVirtualNic) {
    this.typeVirtualNic = typeVirtualNic;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface = (CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface) o;
    return Objects.equals(accelerationCapability, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.accelerationCapability) &&
        Objects.equals(bandwidth, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.bandwidth) &&
        Objects.equals(ipAddress, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.ipAddress) &&
        Objects.equals(macAddress, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.macAddress) &&
        Objects.equals(metadata, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.metadata) &&
        Objects.equals(networkId, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.networkId) &&
        Objects.equals(networkPortId, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.networkPortId) &&
        Objects.equals(operationalState, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.operationalState) &&
        Objects.equals(ownerId, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.ownerId) &&
        Objects.equals(resourceId, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.resourceId) &&
        Objects.equals(typeConfiguration, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.typeConfiguration) &&
        Objects.equals(typeVirtualNic, createComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.typeVirtualNic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, bandwidth, ipAddress, macAddress, metadata, networkId, networkPortId, operationalState, ownerId, resourceId, typeConfiguration, typeVirtualNic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    networkPortId: ").append(toIndentedString(networkPortId)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    typeConfiguration: ").append(toIndentedString(typeConfiguration)).append("\n");
    sb.append("    typeVirtualNic: ").append(toIndentedString(typeVirtualNic)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

