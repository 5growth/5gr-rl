package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The virtual CPU(s) of the virtualised compute.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The virtual CPU(s) of the virtualised compute.")

public class VirtualComputeVirtualCpu   {
  
  private @Valid String cpuArchitecture = null;
  private @Valid BigDecimal cpuClock = null;
  private @Valid Integer numVirtualCpu = null;
  private @Valid String virtualCpuOversubscriptionPolicy = null;
  private @Valid CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning virtualCpuPinning = null;

  /**
   * CPU architecture type. Examples are x86, ARM.
   **/
  public VirtualComputeVirtualCpu cpuArchitecture(String cpuArchitecture) {
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
  public VirtualComputeVirtualCpu cpuClock(BigDecimal cpuClock) {
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
   * Number of virtual CPUs.
   **/
  public VirtualComputeVirtualCpu numVirtualCpu(Integer numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of virtual CPUs.")
  @JsonProperty("numVirtualCpu")
  @NotNull
  public Integer getNumVirtualCpu() {
    return numVirtualCpu;
  }
  public void setNumVirtualCpu(Integer numVirtualCpu) {
    this.numVirtualCpu = numVirtualCpu;
  }

  /**
   * The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no policy has been defined during the allocation request.
   **/
  public VirtualComputeVirtualCpu virtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The CPU core oversubscription policy, e.g. the relation of virtual CPU cores to physical CPU cores/threads. The cardinality can be 0 if no policy has been defined during the allocation request.")
  @JsonProperty("virtualCpuOversubscriptionPolicy")
  @NotNull
  public String getVirtualCpuOversubscriptionPolicy() {
    return virtualCpuOversubscriptionPolicy;
  }
  public void setVirtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
  }

  /**
   **/
  public VirtualComputeVirtualCpu virtualCpuPinning(CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning virtualCpuPinning) {
    this.virtualCpuPinning = virtualCpuPinning;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualCpuPinning")
  @NotNull
  public CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning getVirtualCpuPinning() {
    return virtualCpuPinning;
  }
  public void setVirtualCpuPinning(CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning virtualCpuPinning) {
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
    VirtualComputeVirtualCpu virtualComputeVirtualCpu = (VirtualComputeVirtualCpu) o;
    return Objects.equals(cpuArchitecture, virtualComputeVirtualCpu.cpuArchitecture) &&
        Objects.equals(cpuClock, virtualComputeVirtualCpu.cpuClock) &&
        Objects.equals(numVirtualCpu, virtualComputeVirtualCpu.numVirtualCpu) &&
        Objects.equals(virtualCpuOversubscriptionPolicy, virtualComputeVirtualCpu.virtualCpuOversubscriptionPolicy) &&
        Objects.equals(virtualCpuPinning, virtualComputeVirtualCpu.virtualCpuPinning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuArchitecture, cpuClock, numVirtualCpu, virtualCpuOversubscriptionPolicy, virtualCpuPinning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeVirtualCpu {\n");
    
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    cpuClock: ").append(toIndentedString(cpuClock)).append("\n");
    sb.append("    numVirtualCpu: ").append(toIndentedString(numVirtualCpu)).append("\n");
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

