package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The virtual CPU pinning configuration for the virtualised compute resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The virtual CPU pinning configuration for the virtualised compute resource.")

public class CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning   {
  
  private @Valid String cpuMap = null;
  private @Valid String cpuPinningPolicy = null;
  private @Valid String cpuPinningRules = null;

  /**
   * Shows the association of virtual CPU cores to physical CPU cores.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning cpuMap(String cpuMap) {
    this.cpuMap = cpuMap;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Shows the association of virtual CPU cores to physical CPU cores.")
  @JsonProperty("cpuMap")
  @NotNull
  public String getCpuMap() {
    return cpuMap;
  }
  public void setCpuMap(String cpuMap) {
    this.cpuMap = cpuMap;
  }

  /**
   * The policy can take values of \&quot;static\&quot; or \&quot;dynamic\&quot;. In case of \&quot;static\&quot; the virtual CPU cores have been allocated to physical CPU cores according to the rules defined in cpuPinningRules. In case of \&quot;dynamic\&quot; the allocation of virtual CPU cores to physical CPU cores is decided by the VIM.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning cpuPinningPolicy(String cpuPinningPolicy) {
    this.cpuPinningPolicy = cpuPinningPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The policy can take values of \"static\" or \"dynamic\". In case of \"static\" the virtual CPU cores have been allocated to physical CPU cores according to the rules defined in cpuPinningRules. In case of \"dynamic\" the allocation of virtual CPU cores to physical CPU cores is decided by the VIM.")
  @JsonProperty("cpuPinningPolicy")
  @NotNull
  public String getCpuPinningPolicy() {
    return cpuPinningPolicy;
  }
  public void setCpuPinningPolicy(String cpuPinningPolicy) {
    this.cpuPinningPolicy = cpuPinningPolicy;
  }

  /**
   * A list of rules that should be considered during the allocation of the virtual CPUs to physical CPUs in case of \&quot;static\&quot; cpuPinningPolicy.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning cpuPinningRules(String cpuPinningRules) {
    this.cpuPinningRules = cpuPinningRules;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "A list of rules that should be considered during the allocation of the virtual CPUs to physical CPUs in case of \"static\" cpuPinningPolicy.")
  @JsonProperty("cpuPinningRules")
  @NotNull
  public String getCpuPinningRules() {
    return cpuPinningRules;
  }
  public void setCpuPinningRules(String cpuPinningRules) {
    this.cpuPinningRules = cpuPinningRules;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning createComputeResourceReservationRequestContainerFlavourVirtualCpuPinning = (CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning) o;
    return Objects.equals(cpuMap, createComputeResourceReservationRequestContainerFlavourVirtualCpuPinning.cpuMap) &&
        Objects.equals(cpuPinningPolicy, createComputeResourceReservationRequestContainerFlavourVirtualCpuPinning.cpuPinningPolicy) &&
        Objects.equals(cpuPinningRules, createComputeResourceReservationRequestContainerFlavourVirtualCpuPinning.cpuPinningRules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuMap, cpuPinningPolicy, cpuPinningRules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning {\n");
    
    sb.append("    cpuMap: ").append(toIndentedString(cpuMap)).append("\n");
    sb.append("    cpuPinningPolicy: ").append(toIndentedString(cpuPinningPolicy)).append("\n");
    sb.append("    cpuPinningRules: ").append(toIndentedString(cpuPinningRules)).append("\n");
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

