package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualNetworkResourceInformation   {
  
  private @Valid BigDecimal bandwidth = null;
  private @Valid List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS = new ArrayList<AllocateNetworkResultNetworkDataNetworkQoS>();
  private @Valid String networkResourceTypeId = null;
  private @Valid String networkType = null;

  /**
   * Minimum network bandwidth (in Mbps).
   **/
  public VirtualNetworkResourceInformation bandwidth(BigDecimal bandwidth) {
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
   * Element providing information about Quality of Service attributes that the network shall support.
   **/
  public VirtualNetworkResourceInformation networkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element providing information about Quality of Service attributes that the network shall support.")
  @JsonProperty("networkQoS")
  @NotNull
  public List<AllocateNetworkResultNetworkDataNetworkQoS> getNetworkQoS() {
    return networkQoS;
  }
  public void setNetworkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
  }

  /**
   * Identifier of the network resource type.
   **/
  public VirtualNetworkResourceInformation networkResourceTypeId(String networkResourceTypeId) {
    this.networkResourceTypeId = networkResourceTypeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the network resource type.")
  @JsonProperty("networkResourceTypeId")
  @NotNull
  public String getNetworkResourceTypeId() {
    return networkResourceTypeId;
  }
  public void setNetworkResourceTypeId(String networkResourceTypeId) {
    this.networkResourceTypeId = networkResourceTypeId;
  }

  /**
   * The type of network that maps to the virtualised network. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, etc.
   **/
  public VirtualNetworkResourceInformation networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network. Examples are: \"local\", \"vlan\", \"vxlan\", \"gre\", etc.")
  @JsonProperty("networkType")
  @NotNull
  public String getNetworkType() {
    return networkType;
  }
  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetworkResourceInformation virtualNetworkResourceInformation = (VirtualNetworkResourceInformation) o;
    return Objects.equals(bandwidth, virtualNetworkResourceInformation.bandwidth) &&
        Objects.equals(networkQoS, virtualNetworkResourceInformation.networkQoS) &&
        Objects.equals(networkResourceTypeId, virtualNetworkResourceInformation.networkResourceTypeId) &&
        Objects.equals(networkType, virtualNetworkResourceInformation.networkType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, networkQoS, networkResourceTypeId, networkType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetworkResourceInformation {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    networkResourceTypeId: ").append(toIndentedString(networkResourceTypeId)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
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

