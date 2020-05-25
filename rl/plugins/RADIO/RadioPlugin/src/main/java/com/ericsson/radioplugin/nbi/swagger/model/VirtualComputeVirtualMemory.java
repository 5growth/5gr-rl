package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The virtual memory of the compute.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The virtual memory of the compute.")

public class VirtualComputeVirtualMemory   {
  
  private @Valid Boolean numaEnabled = null;
  private @Valid String virtualMemOversubscriptionPolicy = null;
  private @Valid BigDecimal virtualMemSize = null;

  /**
   * It specifies the memory allocation to be cognisant of the relevant process/core allocation.
   **/
  public VirtualComputeVirtualMemory numaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It specifies the memory allocation to be cognisant of the relevant process/core allocation.")
  @JsonProperty("numaEnabled")
  @NotNull
  public Boolean isNumaEnabled() {
    return numaEnabled;
  }
  public void setNumaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
  }

  /**
   * The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no policy has been defined during the allocation request.
   **/
  public VirtualComputeVirtualMemory virtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no policy has been defined during the allocation request.")
  @JsonProperty("virtualMemOversubscriptionPolicy")
  @NotNull
  public String getVirtualMemOversubscriptionPolicy() {
    return virtualMemOversubscriptionPolicy;
  }
  public void setVirtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
  }

  /**
   * Amount of virtual Memory (e.g. in MB).
   **/
  public VirtualComputeVirtualMemory virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Amount of virtual Memory (e.g. in MB).")
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
    VirtualComputeVirtualMemory virtualComputeVirtualMemory = (VirtualComputeVirtualMemory) o;
    return Objects.equals(numaEnabled, virtualComputeVirtualMemory.numaEnabled) &&
        Objects.equals(virtualMemOversubscriptionPolicy, virtualComputeVirtualMemory.virtualMemOversubscriptionPolicy) &&
        Objects.equals(virtualMemSize, virtualComputeVirtualMemory.virtualMemSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numaEnabled, virtualMemOversubscriptionPolicy, virtualMemSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeVirtualMemory {\n");
    
    sb.append("    numaEnabled: ").append(toIndentedString(numaEnabled)).append("\n");
    sb.append("    virtualMemOversubscriptionPolicy: ").append(toIndentedString(virtualMemOversubscriptionPolicy)).append("\n");
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

