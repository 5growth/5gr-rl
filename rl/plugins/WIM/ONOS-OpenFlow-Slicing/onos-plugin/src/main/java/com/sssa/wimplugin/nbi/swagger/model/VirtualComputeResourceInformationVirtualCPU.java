package com.sssa.wimplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * It defines the virtual CPU(s) characteristics of the consumable virtualised compute resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "It defines the virtual CPU(s) characteristics of the consumable virtualised compute resource.")

public class VirtualComputeResourceInformationVirtualCPU   {
  
  private @Valid String cpuArchitecture = null;
  private @Valid BigDecimal cpuClock = null;
  private @Valid BigDecimal numVirtualCpu = null;
  private @Valid String virtualCpuOversubscriptionPolicy = null;
  private @Valid Boolean virtualCpuPinningSupported = null;

  /**
   * CPU architecture type. Examples are x86, ARM.
   **/
  public VirtualComputeResourceInformationVirtualCPU cpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "CPU architecture type. Examples are x86, ARM.")
  @JsonProperty("cpuArchitecture")
  @NotNull
  public String getCpuArchitecture() {
    return cpuArchitecture;
  }
  public void setCpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
  }

  /**
   * Minimum CPU clock rate (e.g. in MHz) available for the virtualised CPU resources.
   **/
  public VirtualComputeResourceInformationVirtualCPU cpuClock(BigDecimal cpuClock) {
    this.cpuClock = cpuClock;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum CPU clock rate (e.g. in MHz) available for the virtualised CPU resources.")
  @JsonProperty("cpuClock")
  @NotNull
  public BigDecimal getCpuClock() {
    return cpuClock;
  }
  public void setCpuClock(BigDecimal cpuClock) {
    this.cpuClock = cpuClock;
  }

  /**
   * Number of virtual CPUs. Cardinality \&quot;1\&quot; covers the case where a specific configuration for the consumable resource is advertised.
   **/
  public VirtualComputeResourceInformationVirtualCPU numVirtualCpu(BigDecimal numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtual CPUs. Cardinality \"1\" covers the case where a specific configuration for the consumable resource is advertised.")
  @JsonProperty("numVirtualCpu")
  @NotNull
  public BigDecimal getNumVirtualCpu() {
    return numVirtualCpu;
  }
  public void setNumVirtualCpu(BigDecimal numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
  }

  /**
   * The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no concrete policy is defined.
   **/
  public VirtualComputeResourceInformationVirtualCPU virtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no concrete policy is defined.")
  @JsonProperty("virtualCpuOversubscriptionPolicy")
  @NotNull
  public String getVirtualCpuOversubscriptionPolicy() {
    return virtualCpuOversubscriptionPolicy;
  }
  public void setVirtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
  }

  /**
   * It defines whether CPU pinning capability is available on the consumable virtualised compute resource.
   **/
  public VirtualComputeResourceInformationVirtualCPU virtualCpuPinningSupported(Boolean virtualCpuPinningSupported) {
    this.virtualCpuPinningSupported = virtualCpuPinningSupported;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It defines whether CPU pinning capability is available on the consumable virtualised compute resource.")
  @JsonProperty("virtualCpuPinningSupported")
  @NotNull
  public Boolean isVirtualCpuPinningSupported() {
    return virtualCpuPinningSupported;
  }
  public void setVirtualCpuPinningSupported(Boolean virtualCpuPinningSupported) {
    this.virtualCpuPinningSupported = virtualCpuPinningSupported;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualComputeResourceInformationVirtualCPU virtualComputeResourceInformationVirtualCPU = (VirtualComputeResourceInformationVirtualCPU) o;
    return Objects.equals(cpuArchitecture, virtualComputeResourceInformationVirtualCPU.cpuArchitecture) &&
        Objects.equals(cpuClock, virtualComputeResourceInformationVirtualCPU.cpuClock) &&
        Objects.equals(numVirtualCpu, virtualComputeResourceInformationVirtualCPU.numVirtualCpu) &&
        Objects.equals(virtualCpuOversubscriptionPolicy, virtualComputeResourceInformationVirtualCPU.virtualCpuOversubscriptionPolicy) &&
        Objects.equals(virtualCpuPinningSupported, virtualComputeResourceInformationVirtualCPU.virtualCpuPinningSupported);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuArchitecture, cpuClock, numVirtualCpu, virtualCpuOversubscriptionPolicy, virtualCpuPinningSupported);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeResourceInformationVirtualCPU {\n");
    
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    cpuClock: ").append(toIndentedString(cpuClock)).append("\n");
    sb.append("    numVirtualCpu: ").append(toIndentedString(numVirtualCpu)).append("\n");
    sb.append("    virtualCpuOversubscriptionPolicy: ").append(toIndentedString(virtualCpuOversubscriptionPolicy)).append("\n");
    sb.append("    virtualCpuPinningSupported: ").append(toIndentedString(virtualCpuPinningSupported)).append("\n");
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

