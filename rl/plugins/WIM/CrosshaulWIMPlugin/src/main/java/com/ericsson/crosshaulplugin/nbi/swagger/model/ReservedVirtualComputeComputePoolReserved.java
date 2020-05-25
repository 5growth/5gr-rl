package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeComputePoolReservedComputeAttributes;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about compute resources that have been reserved, e.g. {\&quot;cpu_cores\&quot;: 90, \&quot;vm_instances\&quot;: 10, \&quot;ram\&quot;: 10000}
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about compute resources that have been reserved, e.g. {\"cpu_cores\": 90, \"vm_instances\": 10, \"ram\": 10000}")

public class ReservedVirtualComputeComputePoolReserved   {
  
  private @Valid ReservedVirtualComputeComputePoolReservedComputeAttributes computeAttributes = null;
  private @Valid Integer numCpuCores = null;
  private @Valid Integer numVcInstances = null;
  private @Valid BigDecimal virtualMemSize = null;
  private @Valid String zoneId = null;

  /**
   **/
  public ReservedVirtualComputeComputePoolReserved computeAttributes(ReservedVirtualComputeComputePoolReservedComputeAttributes computeAttributes) {
    this.computeAttributes = computeAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("computeAttributes")
  @NotNull
  public ReservedVirtualComputeComputePoolReservedComputeAttributes getComputeAttributes() {
    return computeAttributes;
  }
  public void setComputeAttributes(ReservedVirtualComputeComputePoolReservedComputeAttributes computeAttributes) {
    this.computeAttributes = computeAttributes;
  }

  /**
   * Number of CPU cores that have been reserved.
   **/
  public ReservedVirtualComputeComputePoolReserved numCpuCores(Integer numCpuCores) {
    this.numCpuCores = numCpuCores;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of CPU cores that have been reserved.")
  @JsonProperty("numCpuCores")
  @NotNull
  public Integer getNumCpuCores() {
    return numCpuCores;
  }
  public void setNumCpuCores(Integer numCpuCores) {
    this.numCpuCores = numCpuCores;
  }

  /**
   * Number of virtual container instances that have been reserved.
   **/
  public ReservedVirtualComputeComputePoolReserved numVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtual container instances that have been reserved.")
  @JsonProperty("numVcInstances")
  @NotNull
  public Integer getNumVcInstances() {
    return numVcInstances;
  }
  public void setNumVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
  }

  /**
   * Size of virtual memory that has been reserved.
   **/
  public ReservedVirtualComputeComputePoolReserved virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Size of virtual memory that has been reserved.")
  @JsonProperty("virtualMemSize")
  @NotNull
  public BigDecimal getVirtualMemSize() {
    return virtualMemSize;
  }
  public void setVirtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
  }

  /**
   * References the resource zone where the virtual compute resources have been reserved. Cardinality can be 0 to cover the case where reserved compute resources are not bound to a specific resource zone.
   **/
  public ReservedVirtualComputeComputePoolReserved zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "References the resource zone where the virtual compute resources have been reserved. Cardinality can be 0 to cover the case where reserved compute resources are not bound to a specific resource zone.")
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
    ReservedVirtualComputeComputePoolReserved reservedVirtualComputeComputePoolReserved = (ReservedVirtualComputeComputePoolReserved) o;
    return Objects.equals(computeAttributes, reservedVirtualComputeComputePoolReserved.computeAttributes) &&
        Objects.equals(numCpuCores, reservedVirtualComputeComputePoolReserved.numCpuCores) &&
        Objects.equals(numVcInstances, reservedVirtualComputeComputePoolReserved.numVcInstances) &&
        Objects.equals(virtualMemSize, reservedVirtualComputeComputePoolReserved.virtualMemSize) &&
        Objects.equals(zoneId, reservedVirtualComputeComputePoolReserved.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computeAttributes, numCpuCores, numVcInstances, virtualMemSize, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualComputeComputePoolReserved {\n");
    
    sb.append("    computeAttributes: ").append(toIndentedString(computeAttributes)).append("\n");
    sb.append("    numCpuCores: ").append(toIndentedString(numCpuCores)).append("\n");
    sb.append("    numVcInstances: ").append(toIndentedString(numVcInstances)).append("\n");
    sb.append("    virtualMemSize: ").append(toIndentedString(virtualMemSize)).append("\n");
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

