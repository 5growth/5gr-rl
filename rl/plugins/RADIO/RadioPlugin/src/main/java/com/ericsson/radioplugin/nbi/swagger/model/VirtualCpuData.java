package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.VirtualCpuPinningData;
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

public class VirtualCpuData   {
  
  private @Valid String cpuArchitecture = null;
  private @Valid BigDecimal numVirtualCpu = null;
  private @Valid BigDecimal virtualCpuClock = null;
  private @Valid String virtualCpuOversubscriptionPolicy = null;
  private @Valid VirtualCpuPinningData virtualCpuPinning = null;

  /**
   * CPU architecture type. Examples are x86, ARM. The cardinality can be 0 during the allocation request, if no particular CPU architecture type is requested.
   **/
  public VirtualCpuData cpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
    return this;
  }

  
  @ApiModelProperty(value = "CPU architecture type. Examples are x86, ARM. The cardinality can be 0 during the allocation request, if no particular CPU architecture type is requested.")
  @JsonProperty("cpuArchitecture")
  public String getCpuArchitecture() {
    return cpuArchitecture;
  }
  public void setCpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
  }

  /**
   * Number of virtual CPUs
   **/
  public VirtualCpuData numVirtualCpu(BigDecimal numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtual CPUs")
  @JsonProperty("numVirtualCpu")
  @NotNull
  public BigDecimal getNumVirtualCpu() {
    return numVirtualCpu;
  }
  public void setNumVirtualCpu(BigDecimal numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
  }

  /**
   * Minimum virtual CPU clock rate (e.g. in MHz). The cardinality can be 0 during the allocation request, if no particular value is requested.
   **/
  public VirtualCpuData virtualCpuClock(BigDecimal virtualCpuClock) {
    this.virtualCpuClock = virtualCpuClock;
    return this;
  }

  
  @ApiModelProperty(value = "Minimum virtual CPU clock rate (e.g. in MHz). The cardinality can be 0 during the allocation request, if no particular value is requested.")
  @JsonProperty("virtualCpuClock")
  public BigDecimal getVirtualCpuClock() {
    return virtualCpuClock;
  }
  public void setVirtualCpuClock(BigDecimal virtualCpuClock) {
    this.virtualCpuClock = virtualCpuClock;
  }

  /**
   * The CPU core oversubscription policy e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 during the allocation request, if no particular value is requested.
   **/
  public VirtualCpuData virtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(value = "The CPU core oversubscription policy e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 during the allocation request, if no particular value is requested.")
  @JsonProperty("virtualCpuOversubscriptionPolicy")
  public String getVirtualCpuOversubscriptionPolicy() {
    return virtualCpuOversubscriptionPolicy;
  }
  public void setVirtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
  }

  /**
   **/
  public VirtualCpuData virtualCpuPinning(VirtualCpuPinningData virtualCpuPinning) {
    this.virtualCpuPinning = virtualCpuPinning;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualCpuPinning")
  public VirtualCpuPinningData getVirtualCpuPinning() {
    return virtualCpuPinning;
  }
  public void setVirtualCpuPinning(VirtualCpuPinningData virtualCpuPinning) {
    this.virtualCpuPinning = virtualCpuPinning;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualCpuData virtualCpuData = (VirtualCpuData) o;
    return Objects.equals(cpuArchitecture, virtualCpuData.cpuArchitecture) &&
        Objects.equals(numVirtualCpu, virtualCpuData.numVirtualCpu) &&
        Objects.equals(virtualCpuClock, virtualCpuData.virtualCpuClock) &&
        Objects.equals(virtualCpuOversubscriptionPolicy, virtualCpuData.virtualCpuOversubscriptionPolicy) &&
        Objects.equals(virtualCpuPinning, virtualCpuData.virtualCpuPinning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuArchitecture, numVirtualCpu, virtualCpuClock, virtualCpuOversubscriptionPolicy, virtualCpuPinning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualCpuData {\n");
    
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    numVirtualCpu: ").append(toIndentedString(numVirtualCpu)).append("\n");
    sb.append("    virtualCpuClock: ").append(toIndentedString(virtualCpuClock)).append("\n");
    sb.append("    virtualCpuOversubscriptionPolicy: ").append(toIndentedString(virtualCpuOversubscriptionPolicy)).append("\n");
    sb.append("    virtualCpuPinning: ").append(toIndentedString(virtualCpuPinning)).append("\n");
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

