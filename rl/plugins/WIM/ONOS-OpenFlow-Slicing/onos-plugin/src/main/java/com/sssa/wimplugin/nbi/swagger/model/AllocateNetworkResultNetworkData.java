package com.sssa.wimplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkPort;
import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * If network types are created satisfactorily, it contains the data relative to the instantiated virtualised network resource. Cardinality can be \&quot;0\&quot; if the request did not include creation of such type of resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "If network types are created satisfactorily, it contains the data relative to the instantiated virtualised network resource. Cardinality can be \"0\" if the request did not include creation of such type of resource.")

public class AllocateNetworkResultNetworkData   {
  
  private @Valid BigDecimal bandwidth = null;
  private @Valid Boolean isShared = null;
  private @Valid List<AllocateNetworkResultNetworkDataNetworkPort> networkPort = new ArrayList<AllocateNetworkResultNetworkDataNetworkPort>();
  private @Valid List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS = new ArrayList<AllocateNetworkResultNetworkDataNetworkQoS>();
  private @Valid String networkResourceId = null;
  private @Valid String networkResourceName = null;
  private @Valid String networkType = null;
  private @Valid String operationalState = null;
  private @Valid String segmentType = null;
  private @Valid String sharingCriteria = null;
  private @Valid String subnet = null;
  private @Valid String zoneId = null;

  /**
   * Minimum network bandwidth (in Mbps).
   **/
  public AllocateNetworkResultNetworkData bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum network bandwidth (in Mbps).")
  @JsonProperty("bandwidth")
  @NotNull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * It defines whether the virtualised network is shared among consumers.
   **/
  public AllocateNetworkResultNetworkData isShared(Boolean isShared) {
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
   * Element providing information of an instantiated virtual network port.
   **/
  public AllocateNetworkResultNetworkData networkPort(List<AllocateNetworkResultNetworkDataNetworkPort> networkPort) {
    this.networkPort = networkPort;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element providing information of an instantiated virtual network port.")
  @JsonProperty("networkPort")
  @NotNull
  public List<AllocateNetworkResultNetworkDataNetworkPort> getNetworkPort() {
    return networkPort;
  }
  public void setNetworkPort(List<AllocateNetworkResultNetworkDataNetworkPort> networkPort) {
    this.networkPort = networkPort;
  }

  /**
   * Element providing information about Quality of Service attributes that the network shall support. The cardinality can be \&quot;0\&quot; to allow for networks without any specified QoS requirements.
   **/
  public AllocateNetworkResultNetworkData networkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element providing information about Quality of Service attributes that the network shall support. The cardinality can be \"0\" to allow for networks without any specified QoS requirements.")
  @JsonProperty("networkQoS")
  @NotNull
  public List<AllocateNetworkResultNetworkDataNetworkQoS> getNetworkQoS() {
    return networkQoS;
  }
  public void setNetworkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
  }

  /**
   * Identifier of the virtualised network resource.
   **/
  public AllocateNetworkResultNetworkData networkResourceId(String networkResourceId) {
    this.networkResourceId = networkResourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised network resource.")
  @JsonProperty("networkResourceId")
  @NotNull
  public String getNetworkResourceId() {
    return networkResourceId;
  }
  public void setNetworkResourceId(String networkResourceId) {
    this.networkResourceId = networkResourceId;
  }

  /**
   * Name of the virtualised network resource.
   **/
  public AllocateNetworkResultNetworkData networkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name of the virtualised network resource.")
  @JsonProperty("networkResourceName")
  @NotNull
  public String getNetworkResourceName() {
    return networkResourceName;
  }
  public void setNetworkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
  }

  /**
   * The type of network that maps to the virtualised network. This list is extensible. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, \&quot;l3-vpn\&quot;, etc. The cardinality can be \&quot;0\&quot; to cover the case where this attribute is not required to create the virtualised network.
   **/
  public AllocateNetworkResultNetworkData networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network. This list is extensible. Examples are: \"local\", \"vlan\", \"vxlan\", \"gre\", \"l3-vpn\", etc. The cardinality can be \"0\" to cover the case where this attribute is not required to create the virtualised network.")
  @JsonProperty("networkType")
  @NotNull
  public String getNetworkType() {
    return networkType;
  }
  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  /**
   * The operational state of the virtualised network.
   **/
  public AllocateNetworkResultNetworkData operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The operational state of the virtualised network.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * The isolated segment for the virtualised network. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; to allow for flat networks without any specific segmentation.
   **/
  public AllocateNetworkResultNetworkData segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network. For instance, for a \"vlan\" networkType, it corresponds to the vlan identifier; and for a \"gre\" networkType, this corresponds to a gre key. The cardinality can be \"0\" to allow for flat networks without any specific segmentation.")
  @JsonProperty("segmentType")
  @NotNull
  public String getSegmentType() {
    return segmentType;
  }
  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }

  /**
   * Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.
   **/
  public AllocateNetworkResultNetworkData sharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.")
  @JsonProperty("sharingCriteria")
  @NotNull
  public String getSharingCriteria() {
    return sharingCriteria;
  }
  public void setSharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
  }

  /**
   * Only present if the network provides layer 3 connectivity.
   **/
  public AllocateNetworkResultNetworkData subnet(String subnet) {
    this.subnet = subnet;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Only present if the network provides layer 3 connectivity.")
  @JsonProperty("subnet")
  @NotNull
  public String getSubnet() {
    return subnet;
  }
  public void setSubnet(String subnet) {
    this.subnet = subnet;
  }

  /**
   * If present, it identifies the Resource Zone where the virtual network resources have been allocated.
   **/
  public AllocateNetworkResultNetworkData zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it identifies the Resource Zone where the virtual network resources have been allocated.")
  @JsonProperty("zoneId")
  @NotNull
  public String getZoneId() {
    return zoneId;
  }
  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkResultNetworkData allocateNetworkResultNetworkData = (AllocateNetworkResultNetworkData) o;
    return Objects.equals(bandwidth, allocateNetworkResultNetworkData.bandwidth) &&
        Objects.equals(isShared, allocateNetworkResultNetworkData.isShared) &&
        Objects.equals(networkPort, allocateNetworkResultNetworkData.networkPort) &&
        Objects.equals(networkQoS, allocateNetworkResultNetworkData.networkQoS) &&
        Objects.equals(networkResourceId, allocateNetworkResultNetworkData.networkResourceId) &&
        Objects.equals(networkResourceName, allocateNetworkResultNetworkData.networkResourceName) &&
        Objects.equals(networkType, allocateNetworkResultNetworkData.networkType) &&
        Objects.equals(operationalState, allocateNetworkResultNetworkData.operationalState) &&
        Objects.equals(segmentType, allocateNetworkResultNetworkData.segmentType) &&
        Objects.equals(sharingCriteria, allocateNetworkResultNetworkData.sharingCriteria) &&
        Objects.equals(subnet, allocateNetworkResultNetworkData.subnet) &&
        Objects.equals(zoneId, allocateNetworkResultNetworkData.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, isShared, networkPort, networkQoS, networkResourceId, networkResourceName, networkType, operationalState, segmentType, sharingCriteria, subnet, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResultNetworkData {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    networkPort: ").append(toIndentedString(networkPort)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    networkResourceId: ").append(toIndentedString(networkResourceId)).append("\n");
    sb.append("    networkResourceName: ").append(toIndentedString(networkResourceName)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
    sb.append("    sharingCriteria: ").append(toIndentedString(sharingCriteria)).append("\n");
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
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

