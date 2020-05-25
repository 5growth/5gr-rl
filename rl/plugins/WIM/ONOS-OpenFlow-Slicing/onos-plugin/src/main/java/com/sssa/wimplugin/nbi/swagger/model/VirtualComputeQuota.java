package com.sssa.wimplugin.nbi.swagger.model;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualComputeQuota   {
  
  private @Valid Integer numVCPUs = null;
  private @Valid Integer numVcInstances = null;
  private @Valid String resourceGroupId = null;
  private @Valid BigDecimal virtualMemSize = null;

  /**
   * Number of CPU cores that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.
   **/
  public VirtualComputeQuota numVCPUs(Integer numVCPUs) {
    this.numVCPUs = numVCPUs;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of CPU cores that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.")
  @JsonProperty("numVCPUs")
  @NotNull
  public Integer getNumVCPUs() {
    return numVCPUs;
  }
  public void setNumVCPUs(Integer numVCPUs) {
    this.numVCPUs = numVCPUs;
  }

  /**
   * Number of virtualisation container instances that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.
   **/
  public VirtualComputeQuota numVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtualisation container instances that have been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.")
  @JsonProperty("numVcInstances")
  @NotNull
  public Integer getNumVcInstances() {
    return numVcInstances;
  }
  public void setNumVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public VirtualComputeQuota resourceGroupId(String resourceGroupId) {
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
   * Size of virtual memory that has been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.
   **/
  public VirtualComputeQuota virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Size of virtual memory that has been restricted by the quota. The cardinality can be 0 if no specific number of CPU cores has been requested to be restricted by the quota.")
  @JsonProperty("virtualMemSize")
  @NotNull
  public BigDecimal getVirtualMemSize() {
    return virtualMemSize;
  }
  public void setVirtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualComputeQuota virtualComputeQuota = (VirtualComputeQuota) o;
    return Objects.equals(numVCPUs, virtualComputeQuota.numVCPUs) &&
        Objects.equals(numVcInstances, virtualComputeQuota.numVcInstances) &&
        Objects.equals(resourceGroupId, virtualComputeQuota.resourceGroupId) &&
        Objects.equals(virtualMemSize, virtualComputeQuota.virtualMemSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numVCPUs, numVcInstances, resourceGroupId, virtualMemSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeQuota {\n");
    
    sb.append("    numVCPUs: ").append(toIndentedString(numVCPUs)).append("\n");
    sb.append("    numVcInstances: ").append(toIndentedString(numVcInstances)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    virtualMemSize: ").append(toIndentedString(virtualMemSize)).append("\n");
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

