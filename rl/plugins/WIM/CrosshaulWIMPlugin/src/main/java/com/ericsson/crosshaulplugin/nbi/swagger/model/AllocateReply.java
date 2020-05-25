package com.ericsson.crosshaulplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateReply   {
  
  private @Valid String networkId = null;
  private @Valid String networkType = null;
  private @Valid String segmentType = null;

  /**
   * Identifier of the network.
   **/
  public AllocateReply networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the network.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * The type of network that maps to the virtualised network (VLAN, VXLAN, L3VPN).
   **/
  public AllocateReply networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network (VLAN, VXLAN, L3VPN).")
  @JsonProperty("networkType")
  @NotNull
  public String getNetworkType() {
    return networkType;
  }
  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  /**
   * The isolated segment for the virtualised network (i.e., vlan tag)
   **/
  public AllocateReply segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network (i.e., vlan tag)")
  @JsonProperty("segmentType")
  @NotNull
  public String getSegmentType() {
    return segmentType;
  }
  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateReply allocateReply = (AllocateReply) o;
    return Objects.equals(networkId, allocateReply.networkId) &&
        Objects.equals(networkType, allocateReply.networkType) &&
        Objects.equals(segmentType, allocateReply.segmentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkId, networkType, segmentType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateReply {\n");
    
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
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

