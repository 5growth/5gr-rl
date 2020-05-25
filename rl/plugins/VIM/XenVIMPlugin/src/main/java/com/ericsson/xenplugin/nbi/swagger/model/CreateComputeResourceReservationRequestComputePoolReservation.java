package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.xenplugin.nbi.swagger.model.CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Amount of compute resources that need to be reserved, e.g. {\&quot;cpu_cores\&quot;: 90, \&quot;vm_instances\&quot;: 10, \&quot;ram\&quot;: 10000}.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Amount of compute resources that need to be reserved, e.g. {\"cpu_cores\": 90, \"vm_instances\": 10, \"ram\": 10000}.")

public class CreateComputeResourceReservationRequestComputePoolReservation   {
  
  private @Valid CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes computeAttributes = null;
  private @Valid Integer numCpuCores = null;
  private @Valid Integer numVcInstances = null;
  private @Valid BigDecimal virtualMemSize = null;

  /**
   **/
  public CreateComputeResourceReservationRequestComputePoolReservation computeAttributes(CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes computeAttributes) {
    this.computeAttributes = computeAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("computeAttributes")
  @NotNull
  public CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes getComputeAttributes() {
    return computeAttributes;
  }
  public void setComputeAttributes(CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes computeAttributes) {
    this.computeAttributes = computeAttributes;
  }

  /**
   * Number of CPU cores to be reserved.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservation numCpuCores(Integer numCpuCores) {
    this.numCpuCores = numCpuCores;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of CPU cores to be reserved.")
  @JsonProperty("numCpuCores")
  @NotNull
  public Integer getNumCpuCores() {
    return numCpuCores;
  }
  public void setNumCpuCores(Integer numCpuCores) {
    this.numCpuCores = numCpuCores;
  }

  /**
   * Number of virtualised container instances to be reserved.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservation numVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtualised container instances to be reserved.")
  @JsonProperty("numVcInstances")
  @NotNull
  public Integer getNumVcInstances() {
    return numVcInstances;
  }
  public void setNumVcInstances(Integer numVcInstances) {
    this.numVcInstances = numVcInstances;
  }

  /**
   * Size of virtual memory to be reserved.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservation virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Size of virtual memory to be reserved.")
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
    CreateComputeResourceReservationRequestComputePoolReservation createComputeResourceReservationRequestComputePoolReservation = (CreateComputeResourceReservationRequestComputePoolReservation) o;
    return Objects.equals(computeAttributes, createComputeResourceReservationRequestComputePoolReservation.computeAttributes) &&
        Objects.equals(numCpuCores, createComputeResourceReservationRequestComputePoolReservation.numCpuCores) &&
        Objects.equals(numVcInstances, createComputeResourceReservationRequestComputePoolReservation.numVcInstances) &&
        Objects.equals(virtualMemSize, createComputeResourceReservationRequestComputePoolReservation.virtualMemSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computeAttributes, numCpuCores, numVcInstances, virtualMemSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestComputePoolReservation {\n");
    
    sb.append("    computeAttributes: ").append(toIndentedString(computeAttributes)).append("\n");
    sb.append("    numCpuCores: ").append(toIndentedString(numCpuCores)).append("\n");
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

