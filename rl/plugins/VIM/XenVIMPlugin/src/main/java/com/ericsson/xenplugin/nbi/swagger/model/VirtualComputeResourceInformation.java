package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualComputeResourceInformation   {
  
  private @Valid String accelerationCapability = null;
  private @Valid String computeResourceTypeId = null;
  private @Valid VirtualComputeResourceInformationVirtualCPU virtualCPU = null;
  private @Valid VirtualComputeResourceInformationVirtualMemory virtualMemory = null;

  /**
   * Acceleration capabilities (e.g. crypto, GPU) for the consumable virtualised compute resources from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.
   **/
  public VirtualComputeResourceInformation accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Acceleration capabilities (e.g. crypto, GPU) for the consumable virtualised compute resources from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public String getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * Identifier of the consumable virtualised compute resource type.
   **/
  public VirtualComputeResourceInformation computeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the consumable virtualised compute resource type.")
  @JsonProperty("computeResourceTypeId")
  @NotNull
  public String getComputeResourceTypeId() {
    return computeResourceTypeId;
  }
  public void setComputeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
  }

  /**
   **/
  public VirtualComputeResourceInformation virtualCPU(VirtualComputeResourceInformationVirtualCPU virtualCPU) {
    this.virtualCPU = virtualCPU;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualCPU")
  @NotNull
  public VirtualComputeResourceInformationVirtualCPU getVirtualCPU() {
    return virtualCPU;
  }
  public void setVirtualCPU(VirtualComputeResourceInformationVirtualCPU virtualCPU) {
    this.virtualCPU = virtualCPU;
  }

  /**
   **/
  public VirtualComputeResourceInformation virtualMemory(VirtualComputeResourceInformationVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualMemory")
  @NotNull
  public VirtualComputeResourceInformationVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }
  public void setVirtualMemory(VirtualComputeResourceInformationVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualComputeResourceInformation virtualComputeResourceInformation = (VirtualComputeResourceInformation) o;
    return Objects.equals(accelerationCapability, virtualComputeResourceInformation.accelerationCapability) &&
        Objects.equals(computeResourceTypeId, virtualComputeResourceInformation.computeResourceTypeId) &&
        Objects.equals(virtualCPU, virtualComputeResourceInformation.virtualCPU) &&
        Objects.equals(virtualMemory, virtualComputeResourceInformation.virtualMemory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, computeResourceTypeId, virtualCPU, virtualMemory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeResourceInformation {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    computeResourceTypeId: ").append(toIndentedString(computeResourceTypeId)).append("\n");
    sb.append("    virtualCPU: ").append(toIndentedString(virtualCPU)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
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

