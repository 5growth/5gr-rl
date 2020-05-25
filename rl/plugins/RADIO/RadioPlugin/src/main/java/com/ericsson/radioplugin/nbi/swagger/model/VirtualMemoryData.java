package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The virtual memory of the virtualised compute.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The virtual memory of the virtualised compute.")

public class VirtualMemoryData   {
  
  private @Valid BigDecimal virtualMemSize = null;
  private @Valid Boolean numaEnabled = null;
  private @Valid String virtualMemOversubscriptionPolicy = null;

  /**
   * Amount of virtual memory (e.g. in MB).
   **/
  public VirtualMemoryData virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Amount of virtual memory (e.g. in MB).")
  @JsonProperty("virtualMemSize")
  @NotNull
  public BigDecimal getVirtualMemSize() {
    return virtualMemSize;
  }
  public void setVirtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
  }

  /**
   * It specifies the memory allocation to be  cognisant of the relevant process/core allocation. The cardinality can be 0 during the allocation request, if no particular value is requested.
   **/
  public VirtualMemoryData numaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
    return this;
  }

  
  @ApiModelProperty(value = "It specifies the memory allocation to be  cognisant of the relevant process/core allocation. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  @JsonProperty("numaEnabled")
  public Boolean isNumaEnabled() {
    return numaEnabled;
  }
  public void setNumaEnabled(Boolean numaEnabled) {
    this.numaEnabled = numaEnabled;
  }

  /**
   * The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 during the allocation request, if no particular value is requested.
   **/
  public VirtualMemoryData virtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(value = "The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  @JsonProperty("virtualMemOversubscriptionPolicy")
  public String getVirtualMemOversubscriptionPolicy() {
    return virtualMemOversubscriptionPolicy;
  }
  public void setVirtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualMemoryData virtualMemoryData = (VirtualMemoryData) o;
    return Objects.equals(virtualMemSize, virtualMemoryData.virtualMemSize) &&
        Objects.equals(numaEnabled, virtualMemoryData.numaEnabled) &&
        Objects.equals(virtualMemOversubscriptionPolicy, virtualMemoryData.virtualMemOversubscriptionPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualMemSize, numaEnabled, virtualMemOversubscriptionPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualMemoryData {\n");
    
    sb.append("    virtualMemSize: ").append(toIndentedString(virtualMemSize)).append("\n");
    sb.append("    numaEnabled: ").append(toIndentedString(numaEnabled)).append("\n");
    sb.append("    virtualMemOversubscriptionPolicy: ").append(toIndentedString(virtualMemOversubscriptionPolicy)).append("\n");
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

