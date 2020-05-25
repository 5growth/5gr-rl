package com.ericsson.crosshaulplugin.nbi.swagger.model;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateParameters   {
  
  private @Valid String locationConstraints = null;
  private @Valid String reservationId = null;
  private @Valid String typeNetworkData = null;
  private @Valid String affinityOrAntiAffinityConstraints = null;
  private @Valid String typeNetworkPortData = null;
  private @Valid String resourceGroupId = null;
  private @Valid String metadata = null;
  private @Valid String networkResourceType = null;
  private @Valid String networkResourceName = null;
  private @Valid String typeSubnetData = null;
  private @Valid BigDecimal bandwidth = null;
  private @Valid String delay = null;
  private @Valid String networkType = null;
  private @Valid String segmentType = null;
  private @Valid String networkQoS = null;
  private @Valid Boolean isShared = null;
  private @Valid String sharingCriteria = null;
  private @Valid String layer3Attributes = null;
  private @Valid String portType = null;
  private @Valid String networkId = null;
  private @Valid String segmentId = null;
  private @Valid String ingressPointIPAddress = null;
  private @Valid String ingressPointPortAddress = null;
  private @Valid String egressPointIPAddress = null;
  private @Valid String egressPointPortAddress = null;
  private @Valid String wanLinkId = null;

  /**
   * Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image.
   **/
  public AllocateParameters locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Controls the visibility of the image. In case of \"private\" value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \"public\" value, all tenants of the VIM can use the image.")
  @JsonProperty("locationConstraints")
  @NotNull
  public String getLocationConstraints() {
    return locationConstraints;
  }
  public void setLocationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
  }

  /**
   * Identifier of the resource reservation applicable to this virtualised resource management operation.
   **/
  public AllocateParameters reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource reservation applicable to this virtualised resource management operation.")
  @JsonProperty("reservationId")
  @NotNull
  public String getReservationId() {
    return reservationId;
  }
  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  /**
   * The network data provides information about the particular virtual network resource to create. Cardinality can be \&quot;0\&quot; depending on the value of networkResourceType.
   **/
  public AllocateParameters typeNetworkData(String typeNetworkData) {
    this.typeNetworkData = typeNetworkData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The network data provides information about the particular virtual network resource to create. Cardinality can be \"0\" depending on the value of networkResourceType.")
  @JsonProperty("typeNetworkData")
  @NotNull
  public String getTypeNetworkData() {
    return typeNetworkData;
  }
  public void setTypeNetworkData(String typeNetworkData) {
    this.typeNetworkData = typeNetworkData;
  }

  /**
   * The binary software image file.
   **/
  public AllocateParameters affinityOrAntiAffinityConstraints(String affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("affinityOrAntiAffinityConstraints")
  @NotNull
  public String getAffinityOrAntiAffinityConstraints() {
    return affinityOrAntiAffinityConstraints;
  }
  public void setAffinityOrAntiAffinityConstraints(String affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
  }

  /**
   * The binary software image file.
   **/
  public AllocateParameters typeNetworkPortData(String typeNetworkPortData) {
    this.typeNetworkPortData = typeNetworkPortData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("typeNetworkPortData")
  @NotNull
  public String getTypeNetworkPortData() {
    return typeNetworkPortData;
  }
  public void setTypeNetworkPortData(String typeNetworkPortData) {
    this.typeNetworkPortData = typeNetworkPortData;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public AllocateParameters resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
  @JsonProperty("resourceGroupId")
  @NotNull
  public String getResourceGroupId() {
    return resourceGroupId;
  }
  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  /**
   * The binary software image file.
   **/
  public AllocateParameters metadata(String metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("metadata")
  @NotNull
  public String getMetadata() {
    return metadata;
  }
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Type of virtualised network resource. Possible values are: \&quot;network\&quot;, \&quot;subnet\&quot; or network-port.
   **/
  public AllocateParameters networkResourceType(String networkResourceType) {
    this.networkResourceType = networkResourceType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of virtualised network resource. Possible values are: \"network\", \"subnet\" or network-port.")
  @JsonProperty("networkResourceType")
  @NotNull
  public String getNetworkResourceType() {
    return networkResourceType;
  }
  public void setNetworkResourceType(String networkResourceType) {
    this.networkResourceType = networkResourceType;
  }

  /**
   * Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side.
   **/
  public AllocateParameters networkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side.")
  @JsonProperty("networkResourceName")
  @NotNull
  public String getNetworkResourceName() {
    return networkResourceName;
  }
  public void setNetworkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
  }

  /**
   * The subnet data provides information about the particular sub-network resource to create.
   **/
  public AllocateParameters typeSubnetData(String typeSubnetData) {
    this.typeSubnetData = typeSubnetData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The subnet data provides information about the particular sub-network resource to create.")
  @JsonProperty("typeSubnetData")
  @NotNull
  public String getTypeSubnetData() {
    return typeSubnetData;
  }
  public void setTypeSubnetData(String typeSubnetData) {
    this.typeSubnetData = typeSubnetData;
  }

  /**
   * The bandwidth of the virtual network interface (in Mbps).
   **/
  public AllocateParameters bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The bandwidth of the virtual network interface (in Mbps).")
  @JsonProperty("bandwidth")
  @NotNull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * Transmission delay.
   **/
  public AllocateParameters delay(String delay) {
    this.delay = delay;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Transmission delay.")
  @JsonProperty("delay")
  @NotNull
  public String getDelay() {
    return delay;
  }
  public void setDelay(String delay) {
    this.delay = delay;
  }

  /**
   * The type of network that maps to the virtualised network.
   **/
  public AllocateParameters networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network.")
  @JsonProperty("networkType")
  @NotNull
  public String getNetworkType() {
    return networkType;
  }
  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  /**
   * The isolated segment for the virtualised network.
   **/
  public AllocateParameters segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network.")
  @JsonProperty("segmentType")
  @NotNull
  public String getSegmentType() {
    return segmentType;
  }
  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }

  /**
   * Element providing information about Quality of Service attributes that the network shall support.
   **/
  public AllocateParameters networkQoS(String networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element providing information about Quality of Service attributes that the network shall support.")
  @JsonProperty("networkQoS")
  @NotNull
  public String getNetworkQoS() {
    return networkQoS;
  }
  public void setNetworkQoS(String networkQoS) {
    this.networkQoS = networkQoS;
  }

  /**
   * It defines whether the virtualised network is shared among consumers.
   **/
  public AllocateParameters isShared(Boolean isShared) {
    this.isShared = isShared;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It defines whether the virtualised network is shared among consumers.")
  @JsonProperty("isShared")
  @NotNull
  public Boolean isIsShared() {
    return isShared;
  }
  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  /**
   * Only present for shared networks. Indicate the sharing criteria for this network. These criteria might be a list of authorized consumers.
   **/
  public AllocateParameters sharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Only present for shared networks. Indicate the sharing criteria for this network. These criteria might be a list of authorized consumers.")
  @JsonProperty("sharingCriteria")
  @NotNull
  public String getSharingCriteria() {
    return sharingCriteria;
  }
  public void setSharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
  }

  /**
   * The attribute allows setting up a network providing defined layer 3 connectivity.
   **/
  public AllocateParameters layer3Attributes(String layer3Attributes) {
    this.layer3Attributes = layer3Attributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The attribute allows setting up a network providing defined layer 3 connectivity.")
  @JsonProperty("layer3Attributes")
  @NotNull
  public String getLayer3Attributes() {
    return layer3Attributes;
  }
  public void setLayer3Attributes(String layer3Attributes) {
    this.layer3Attributes = layer3Attributes;
  }

  /**
   * Type of network port.
   **/
  public AllocateParameters portType(String portType) {
    this.portType = portType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of network port.")
  @JsonProperty("portType")
  @NotNull
  public String getPortType() {
    return portType;
  }
  public void setPortType(String portType) {
    this.portType = portType;
  }

  /**
   * Identifier of the network that the port belongs to.
   **/
  public AllocateParameters networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the network that the port belongs to.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * The isolated segment the network port belongs to.
   **/
  public AllocateParameters segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment the network port belongs to.")
  @JsonProperty("segmentId")
  @NotNull
  public String getSegmentId() {
    return segmentId;
  }
  public void setSegmentId(String segmentId) {
    this.segmentId = segmentId;
  }

  /**
   * The ingress point IP Address.
   **/
  public AllocateParameters ingressPointIPAddress(String ingressPointIPAddress) {
    this.ingressPointIPAddress = ingressPointIPAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The ingress point IP Address.")
  @JsonProperty("ingressPointIPAddress")
  @NotNull
  public String getIngressPointIPAddress() {
    return ingressPointIPAddress;
  }
  public void setIngressPointIPAddress(String ingressPointIPAddress) {
    this.ingressPointIPAddress = ingressPointIPAddress;
  }

  /**
   * The ingress point Port(interface) Address.
   **/
  public AllocateParameters ingressPointPortAddress(String ingressPointPortAddress) {
    this.ingressPointPortAddress = ingressPointPortAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The ingress point Port(interface) Address.")
  @JsonProperty("ingressPointPortAddress")
  @NotNull
  public String getIngressPointPortAddress() {
    return ingressPointPortAddress;
  }
  public void setIngressPointPortAddress(String ingressPointPortAddress) {
    this.ingressPointPortAddress = ingressPointPortAddress;
  }

  /**
   * The engress point IP Address.
   **/
  public AllocateParameters egressPointIPAddress(String egressPointIPAddress) {
    this.egressPointIPAddress = egressPointIPAddress;
    return this;
  }

  
  @ApiModelProperty(value = "The engress point IP Address.")
  @JsonProperty("egressPointIPAddress")
  public String getEgressPointIPAddress() {
    return egressPointIPAddress;
  }
  public void setEgressPointIPAddress(String egressPointIPAddress) {
    this.egressPointIPAddress = egressPointIPAddress;
  }

  /**
   * The engress point Port(interface) Address.
   **/
  public AllocateParameters egressPointPortAddress(String egressPointPortAddress) {
    this.egressPointPortAddress = egressPointPortAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The engress point Port(interface) Address.")
  @JsonProperty("egressPointPortAddress")
  @NotNull
  public String getEgressPointPortAddress() {
    return egressPointPortAddress;
  }
  public void setEgressPointPortAddress(String egressPointPortAddress) {
    this.egressPointPortAddress = egressPointPortAddress;
  }

  /**
   * The logical link ID between two nodes.
   **/
  public AllocateParameters wanLinkId(String wanLinkId) {
    this.wanLinkId = wanLinkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The logical link ID between two nodes.")
  @JsonProperty("wanLinkId")
  @NotNull
  public String getWanLinkId() {
    return wanLinkId;
  }
  public void setWanLinkId(String wanLinkId) {
    this.wanLinkId = wanLinkId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateParameters allocateParameters = (AllocateParameters) o;
    return Objects.equals(locationConstraints, allocateParameters.locationConstraints) &&
        Objects.equals(reservationId, allocateParameters.reservationId) &&
        Objects.equals(typeNetworkData, allocateParameters.typeNetworkData) &&
        Objects.equals(affinityOrAntiAffinityConstraints, allocateParameters.affinityOrAntiAffinityConstraints) &&
        Objects.equals(typeNetworkPortData, allocateParameters.typeNetworkPortData) &&
        Objects.equals(resourceGroupId, allocateParameters.resourceGroupId) &&
        Objects.equals(metadata, allocateParameters.metadata) &&
        Objects.equals(networkResourceType, allocateParameters.networkResourceType) &&
        Objects.equals(networkResourceName, allocateParameters.networkResourceName) &&
        Objects.equals(typeSubnetData, allocateParameters.typeSubnetData) &&
        Objects.equals(bandwidth, allocateParameters.bandwidth) &&
        Objects.equals(delay, allocateParameters.delay) &&
        Objects.equals(networkType, allocateParameters.networkType) &&
        Objects.equals(segmentType, allocateParameters.segmentType) &&
        Objects.equals(networkQoS, allocateParameters.networkQoS) &&
        Objects.equals(isShared, allocateParameters.isShared) &&
        Objects.equals(sharingCriteria, allocateParameters.sharingCriteria) &&
        Objects.equals(layer3Attributes, allocateParameters.layer3Attributes) &&
        Objects.equals(portType, allocateParameters.portType) &&
        Objects.equals(networkId, allocateParameters.networkId) &&
        Objects.equals(segmentId, allocateParameters.segmentId) &&
        Objects.equals(ingressPointIPAddress, allocateParameters.ingressPointIPAddress) &&
        Objects.equals(ingressPointPortAddress, allocateParameters.ingressPointPortAddress) &&
        Objects.equals(egressPointIPAddress, allocateParameters.egressPointIPAddress) &&
        Objects.equals(egressPointPortAddress, allocateParameters.egressPointPortAddress) &&
        Objects.equals(wanLinkId, allocateParameters.wanLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locationConstraints, reservationId, typeNetworkData, affinityOrAntiAffinityConstraints, typeNetworkPortData, resourceGroupId, metadata, networkResourceType, networkResourceName, typeSubnetData, bandwidth, delay, networkType, segmentType, networkQoS, isShared, sharingCriteria, layer3Attributes, portType, networkId, segmentId, ingressPointIPAddress, ingressPointPortAddress, egressPointIPAddress, egressPointPortAddress, wanLinkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateParameters {\n");
    
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    typeNetworkData: ").append(toIndentedString(typeNetworkData)).append("\n");
    sb.append("    affinityOrAntiAffinityConstraints: ").append(toIndentedString(affinityOrAntiAffinityConstraints)).append("\n");
    sb.append("    typeNetworkPortData: ").append(toIndentedString(typeNetworkPortData)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkResourceType: ").append(toIndentedString(networkResourceType)).append("\n");
    sb.append("    networkResourceName: ").append(toIndentedString(networkResourceName)).append("\n");
    sb.append("    typeSubnetData: ").append(toIndentedString(typeSubnetData)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    delay: ").append(toIndentedString(delay)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    sharingCriteria: ").append(toIndentedString(sharingCriteria)).append("\n");
    sb.append("    layer3Attributes: ").append(toIndentedString(layer3Attributes)).append("\n");
    sb.append("    portType: ").append(toIndentedString(portType)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    segmentId: ").append(toIndentedString(segmentId)).append("\n");
    sb.append("    ingressPointIPAddress: ").append(toIndentedString(ingressPointIPAddress)).append("\n");
    sb.append("    ingressPointPortAddress: ").append(toIndentedString(ingressPointPortAddress)).append("\n");
    sb.append("    egressPointIPAddress: ").append(toIndentedString(egressPointIPAddress)).append("\n");
    sb.append("    egressPointPortAddress: ").append(toIndentedString(egressPointPortAddress)).append("\n");
    sb.append("    wanLinkId: ").append(toIndentedString(wanLinkId)).append("\n");
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

