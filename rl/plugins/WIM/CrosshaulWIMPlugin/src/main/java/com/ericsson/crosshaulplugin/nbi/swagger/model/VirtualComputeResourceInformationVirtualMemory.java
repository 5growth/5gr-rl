package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * It defines the virtual memory characteristics of the consumable virtualised compute resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "It defines the virtual memory characteristics of the consumable virtualised compute resource.")

public class VirtualComputeResourceInformationVirtualMemory   {
  
  private @Valid Boolean numaSupported = null;
  private @Valid String virtualMemOversubscriptionPolicy = null;
  private @Valid BigDecimal virtualMemSize = null;

  /**
   * It specifies if the memory allocation can be cognisant of the relevant process/core allocation.
   **/
  public VirtualComputeResourceInformationVirtualMemory numaSupported(Boolean numaSupported) {
    this.numaSupported = numaSupported;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It specifies if the memory allocation can be cognisant of the relevant process/core allocation.")
  @JsonProperty("numaSupported")
  @NotNull
  public Boolean isNumaSupported() {
    return numaSupported;
  }
  public void setNumaSupported(Boolean numaSupported) {
    this.numaSupported = numaSupported;
  }

  /**
   * The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no concrete policy is defined.
   **/
  public VirtualComputeResourceInformationVirtualMemory virtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no concrete policy is defined.")
  @JsonProperty("virtualMemOversubscriptionPolicy")
  @NotNull
  public String getVirtualMemOversubscriptionPolicy() {
    return virtualMemOversubscriptionPolicy;
  }
  public void setVirtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
  }

  /**
   * Amount of virtual memory (e.g. in MB). Cardinality \&quot;1\&quot; covers the case where a specific configuration for the consumable resource is advertised.
   **/
  public VirtualComputeResourceInformationVirtualMemory virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Amount of virtual memory (e.g. in MB). Cardinality \"1\" covers the case where a specific configuration for the consumable resource is advertised.")
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
    VirtualComputeResourceInformationVirtualMemory virtualComputeResourceInformationVirtualMemory = (VirtualComputeResourceInformationVirtualMemory) o;
    return Objects.equals(numaSupported, virtualComputeResourceInformationVirtualMemory.numaSupported) &&
        Objects.equals(virtualMemOversubscriptionPolicy, virtualComputeResourceInformationVirtualMemory.virtualMemOversubscriptionPolicy) &&
        Objects.equals(virtualMemSize, virtualComputeResourceInformationVirtualMemory.virtualMemSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numaSupported, virtualMemOversubscriptionPolicy, virtualMemSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeResourceInformationVirtualMemory {\n");
    
    sb.append("    numaSupported: ").append(toIndentedString(numaSupported)).append("\n");
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

