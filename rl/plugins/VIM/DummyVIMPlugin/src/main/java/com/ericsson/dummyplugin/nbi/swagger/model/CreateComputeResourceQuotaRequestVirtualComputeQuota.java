package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.")

public class CreateComputeResourceQuotaRequestVirtualComputeQuota   {
  
  private @Valid Integer numVCPUs = null;
  private @Valid Integer numVcInstances = null;
  private @Valid BigDecimal virtualMemSize = null;

  /**
   * Number of CPU cores to be restricted by the quota. The cardinality can be 0 if no specific number of CPU cores is to be restricted by the quota or the quota for CPU cores is not to be update.
   **/
  public CreateComputeResourceQuotaRequestVirtualComputeQuota numVCPUs(Integer numVCPUs) {
    this.numVCPUs = numVCPUs;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of CPU cores to be restricted by the quota. The cardinality can be 0 if no specific number of CPU cores is to be restricted by the quota or the quota for CPU cores is not to be update.")
  @JsonProperty("numVCPUs")
  @NotNull
  public Integer getNumVCPUs() {
    return numVCPUs;
  }
  public void setNumVCPUs(Integer numVCPUs) {
    this.numVCPUs = numVCPUs;
  }

  /**
   * Number of virtualisation container instances to be restricted by the quota. The cardinality can be 0 if no specific number of virtualisation container instances is to be restricted by the quota or the quota for virtualisation container instances is not to be update.
   **/
  public CreateComputeResourceQuotaRequestVirtualComputeQuota numVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtualisation container instances to be restricted by the quota. The cardinality can be 0 if no specific number of virtualisation container instances is to be restricted by the quota or the quota for virtualisation container instances is not to be update.")
  @JsonProperty("numVcInstances")
  @NotNull
  public Integer getNumVcInstances() {
    return numVcInstances;
  }
  public void setNumVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
  }

  /**
   * Size of virtual memory to be restricted by the quota. The cardinality can be 0 if no specific size of virtual memory is to be restricted by the quota or the quota for virtual memory is not to be update.
   **/
  public CreateComputeResourceQuotaRequestVirtualComputeQuota virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Size of virtual memory to be restricted by the quota. The cardinality can be 0 if no specific size of virtual memory is to be restricted by the quota or the quota for virtual memory is not to be update.")
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
    CreateComputeResourceQuotaRequestVirtualComputeQuota createComputeResourceQuotaRequestVirtualComputeQuota = (CreateComputeResourceQuotaRequestVirtualComputeQuota) o;
    return Objects.equals(numVCPUs, createComputeResourceQuotaRequestVirtualComputeQuota.numVCPUs) &&
        Objects.equals(numVcInstances, createComputeResourceQuotaRequestVirtualComputeQuota.numVcInstances) &&
        Objects.equals(virtualMemSize, createComputeResourceQuotaRequestVirtualComputeQuota.virtualMemSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numVCPUs, numVcInstances, virtualMemSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceQuotaRequestVirtualComputeQuota {\n");
    
    sb.append("    numVCPUs: ").append(toIndentedString(numVCPUs)).append("\n");
    sb.append("    numVcInstances: ").append(toIndentedString(numVcInstances)).append("\n");
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

