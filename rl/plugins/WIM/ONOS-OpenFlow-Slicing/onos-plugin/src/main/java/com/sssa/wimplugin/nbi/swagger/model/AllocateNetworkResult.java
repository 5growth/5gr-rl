package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultNetworkPortData;
import com.sssa.wimplugin.nbi.swagger.model.AllocateNetworkResultSubnetData;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateNetworkResult   {
  
  private @Valid AllocateNetworkResultNetworkData networkData = null;
  private @Valid AllocateNetworkResultNetworkPortData networkPortData = null;
  private @Valid AllocateNetworkResultSubnetData subnetData = null;

  /**
   **/
  public AllocateNetworkResult networkData(AllocateNetworkResultNetworkData networkData) {
    this.networkData = networkData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkData")
  @NotNull
  public AllocateNetworkResultNetworkData getNetworkData() {
    return networkData;
  }
  public void setNetworkData(AllocateNetworkResultNetworkData networkData) {
    this.networkData = networkData;
  }

  /**
   **/
  public AllocateNetworkResult networkPortData(AllocateNetworkResultNetworkPortData networkPortData) {
    this.networkPortData = networkPortData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkPortData")
  @NotNull
  public AllocateNetworkResultNetworkPortData getNetworkPortData() {
    return networkPortData;
  }
  public void setNetworkPortData(AllocateNetworkResultNetworkPortData networkPortData) {
    this.networkPortData = networkPortData;
  }

  /**
   **/
  public AllocateNetworkResult subnetData(AllocateNetworkResultSubnetData subnetData) {
    this.subnetData = subnetData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("subnetData")
  @NotNull
  public AllocateNetworkResultSubnetData getSubnetData() {
    return subnetData;
  }
  public void setSubnetData(AllocateNetworkResultSubnetData subnetData) {
    this.subnetData = subnetData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkResult allocateNetworkResult = (AllocateNetworkResult) o;
    return Objects.equals(networkData, allocateNetworkResult.networkData) &&
        Objects.equals(networkPortData, allocateNetworkResult.networkPortData) &&
        Objects.equals(subnetData, allocateNetworkResult.subnetData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkData, networkPortData, subnetData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResult {\n");
    
    sb.append("    networkData: ").append(toIndentedString(networkData)).append("\n");
    sb.append("    networkPortData: ").append(toIndentedString(networkPortData)).append("\n");
    sb.append("    subnetData: ").append(toIndentedString(subnetData)).append("\n");
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

