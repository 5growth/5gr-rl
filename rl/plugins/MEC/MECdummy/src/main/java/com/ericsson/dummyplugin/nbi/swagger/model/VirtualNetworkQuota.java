package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualNetworkQuota   {
  
  private @Valid Integer numPorts = null;
  private @Valid Integer numPublicIps = null;
  private @Valid Integer numSubnets = null;
  private @Valid String resourceGroupId = null;

  /**
   * Number of ports that have been restricted by the quota. The cardinality can be 0 if no specific number of ports has been requested to be restricted by the quota.
   **/
  public VirtualNetworkQuota numPorts(Integer numPorts) {
    this.numPorts = numPorts;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of ports that have been restricted by the quota. The cardinality can be 0 if no specific number of ports has been requested to be restricted by the quota.")
  @JsonProperty("numPorts")
  @NotNull
  public Integer getNumPorts() {
    return numPorts;
  }
  public void setNumPorts(Integer numPorts) {
    this.numPorts = numPorts;
  }

  /**
   * Number of public IP addresses that have been restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses has been requested to be restricted by the quota.
   **/
  public VirtualNetworkQuota numPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of public IP addresses that have been restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses has been requested to be restricted by the quota.")
  @JsonProperty("numPublicIps")
  @NotNull
  public Integer getNumPublicIps() {
    return numPublicIps;
  }
  public void setNumPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
  }

  /**
   * Number of subnets that have been restricted by the quota. The cardinality can be 0 if no specific number of subnets has been requested to be restricted by the quota.
   **/
  public VirtualNetworkQuota numSubnets(Integer numSubnets) {
    this.numSubnets = numSubnets;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of subnets that have been restricted by the quota. The cardinality can be 0 if no specific number of subnets has been requested to be restricted by the quota.")
  @JsonProperty("numSubnets")
  @NotNull
  public Integer getNumSubnets() {
    return numSubnets;
  }
  public void setNumSubnets(Integer numSubnets) {
    this.numSubnets = numSubnets;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public VirtualNetworkQuota resourceGroupId(String resourceGroupId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetworkQuota virtualNetworkQuota = (VirtualNetworkQuota) o;
    return Objects.equals(numPorts, virtualNetworkQuota.numPorts) &&
        Objects.equals(numPublicIps, virtualNetworkQuota.numPublicIps) &&
        Objects.equals(numSubnets, virtualNetworkQuota.numSubnets) &&
        Objects.equals(resourceGroupId, virtualNetworkQuota.resourceGroupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numPorts, numPublicIps, numSubnets, resourceGroupId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetworkQuota {\n");
    
    sb.append("    numPorts: ").append(toIndentedString(numPorts)).append("\n");
    sb.append("    numPublicIps: ").append(toIndentedString(numPublicIps)).append("\n");
    sb.append("    numSubnets: ").append(toIndentedString(numSubnets)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
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

