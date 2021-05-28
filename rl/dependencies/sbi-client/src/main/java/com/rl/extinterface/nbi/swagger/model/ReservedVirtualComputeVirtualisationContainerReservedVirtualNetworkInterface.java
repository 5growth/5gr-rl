/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rl.extinterface.nbi.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface {
  @SerializedName("accelerationCapability")
  private String accelerationCapability = null;

  @SerializedName("bandwidth")
  private String bandwidth = null;

  @SerializedName("ipAddress")
  private List<String> ipAddress = new ArrayList<String>();

  @SerializedName("macAddress")
  private String macAddress = null;

  @SerializedName("metadata")
  private MetaData metadata = null;

  @SerializedName("networkName")
  private String networkName = null;

  @SerializedName("networkId")
  private String networkId = null;

  @SerializedName("networkPortId")
  private String networkPortId = null;

  @SerializedName("operationalState")
  private String operationalState = null;

  @SerializedName("ownerId")
  private String ownerId = null;

  @SerializedName("resourceId")
  private String resourceId = null;

  @SerializedName("typeConfiguration")
  private String typeConfiguration = null;

  @SerializedName("typeVirtualNic")
  private String typeVirtualNic = null;

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

   /**
   * Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.
   * @return accelerationCapability
  **/
  @ApiModelProperty(required = true, value = "Shows the acceleration capabilities utilized by the virtual network interface. The cardinality can be 0, if no acceleration capability is utilized.")
  public String getAccelerationCapability() {
    return accelerationCapability;
  }

  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface bandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * The bandwidth of the virtual network interface (in Mbps).
   * @return bandwidth
  **/
  @ApiModelProperty(required = true, value = "The bandwidth of the virtual network interface (in Mbps).")
  public String getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface ipAddress(List<String> ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface addIpAddressItem(String ipAddressItem) {
    this.ipAddress.add(ipAddressItem);
    return this;
  }

   /**
   * The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.
   * @return ipAddress
  **/
  @ApiModelProperty(required = true, value = "The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.")
  public List<String> getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(List<String> ipAddress) {
    this.ipAddress = ipAddress;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

   /**
   * The MAC address of the virtual network interface.
   * @return macAddress
  **/
  @ApiModelProperty(required = true, value = "The MAC address of the virtual network interface.")
  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface metadata(MetaData metadata) {
    this.metadata = metadata;
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(required = true, value = "")
  public MetaData getMetadata() {
    return metadata;
  }

  public void setMetadata(MetaData metadata) {
    this.metadata = metadata;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface networkName(String networkName) {
    this.networkName = networkName;
    return this;
  }

   /**
   * name of the virtual network
   * @return networkName
  **/
  @ApiModelProperty(value = "name of the virtual network")
  public String getNetworkName() {
    return networkName;
  }

  public void setNetworkName(String networkName) {
    this.networkName = networkName;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

   /**
   * In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.
   * @return networkId
  **/
  @ApiModelProperty(required = true, value = "In the case when the virtual network interface is attached to the network, it identifies such a network. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network.")
  public String getNetworkId() {
    return networkId;
  }

  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface networkPortId(String networkPortId) {
    this.networkPortId = networkPortId;
    return this;
  }

   /**
   * If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.
   * @return networkPortId
  **/
  @ApiModelProperty(required = true, value = "If the virtual network interface is attached to a specific network port, it identifies such a network port. The cardinality can be 0 in the case that a network interface is created without any specific network port attachment.")
  public String getNetworkPortId() {
    return networkPortId;
  }

  public void setNetworkPortId(String networkPortId) {
    this.networkPortId = networkPortId;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

   /**
   * The operational state of the virtual network interface.
   * @return operationalState
  **/
  @ApiModelProperty(required = true, value = "The operational state of the virtual network interface.")
  public String getOperationalState() {
    return operationalState;
  }

  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

   /**
   * Identifier of the owner of the network interface (e.g. a virtualised compute resource). 
   * @return ownerId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the owner of the network interface (e.g. a virtualised compute resource). ")
  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

   /**
   * Identifier of the virtual network interface.
   * @return resourceId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the virtual network interface.")
  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface typeConfiguration(String typeConfiguration) {
    this.typeConfiguration = typeConfiguration;
    return this;
  }

   /**
   * Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).
   * @return typeConfiguration
  **/
  @ApiModelProperty(required = true, value = "Extra configuration that the virtual network interface supports based on the type of virtual network interface, including support for SR-IOV with configuration of virtual functions (VF).")
  public String getTypeConfiguration() {
    return typeConfiguration;
  }

  public void setTypeConfiguration(String typeConfiguration) {
    this.typeConfiguration = typeConfiguration;
  }

  public ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface typeVirtualNic(String typeVirtualNic) {
    this.typeVirtualNic = typeVirtualNic;
    return this;
  }

   /**
   * Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.
   * @return typeVirtualNic
  **/
  @ApiModelProperty(required = true, value = "Type of network interface. The type allows for defining how such interface is to be realized, e.g. normal virtual NIC, with direct PCI passthrough, etc.")
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
    ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface = (ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface) o;
    return Objects.equals(this.accelerationCapability, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.accelerationCapability) &&
        Objects.equals(this.bandwidth, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.bandwidth) &&
        Objects.equals(this.ipAddress, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.ipAddress) &&
        Objects.equals(this.macAddress, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.macAddress) &&
        Objects.equals(this.metadata, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.metadata) &&
        Objects.equals(this.networkName, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.networkName) &&
        Objects.equals(this.networkId, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.networkId) &&
        Objects.equals(this.networkPortId, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.networkPortId) &&
        Objects.equals(this.operationalState, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.operationalState) &&
        Objects.equals(this.ownerId, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.ownerId) &&
        Objects.equals(this.resourceId, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.resourceId) &&
        Objects.equals(this.typeConfiguration, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.typeConfiguration) &&
        Objects.equals(this.typeVirtualNic, reservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.typeVirtualNic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, bandwidth, ipAddress, macAddress, metadata, networkName, networkId, networkPortId, operationalState, ownerId, resourceId, typeConfiguration, typeVirtualNic);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkName: ").append(toIndentedString(networkName)).append("\n");
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

