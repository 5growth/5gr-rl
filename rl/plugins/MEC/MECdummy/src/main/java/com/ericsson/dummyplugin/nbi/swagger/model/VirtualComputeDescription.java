package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.dummyplugin.nbi.swagger.model.RequestAdditionalCapabilityData;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualCpuData;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualMemoryData;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Describes CPU, Memory and acceleration requirements of the Virtualisation machine.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Describes CPU, Memory and acceleration requirements of the Virtualisation machine.")

public class VirtualComputeDescription   {
  
  private @Valid String virtualComputeDescId = null;
  private @Valid List<RequestAdditionalCapabilityData> requestAdditionalCapabilities = new ArrayList<RequestAdditionalCapabilityData>();
  private @Valid VirtualMemoryData virtualMemory = null;
  private @Valid VirtualCpuData virtualCpu = null;

  /**
   * Unique identifier of this VirtualComputeDescription in the appD.
   **/
  public VirtualComputeDescription virtualComputeDescId(String virtualComputeDescId) {
    this.virtualComputeDescId = virtualComputeDescId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Unique identifier of this VirtualComputeDescription in the appD.")
  @JsonProperty("virtualComputeDescId")
  @NotNull
  public String getVirtualComputeDescId() {
    return virtualComputeDescId;
  }
  public void setVirtualComputeDescId(String virtualComputeDescId) {
    this.virtualComputeDescId = virtualComputeDescId;
  }

  /**
   * Specifies requirements for additional  capabilities. These may be for a range of purposes. One example is acceleration related capabilities.
   **/
  public VirtualComputeDescription requestAdditionalCapabilities(List<RequestAdditionalCapabilityData> requestAdditionalCapabilities) {
    this.requestAdditionalCapabilities = requestAdditionalCapabilities;
    return this;
  }

  
  @ApiModelProperty(value = "Specifies requirements for additional  capabilities. These may be for a range of purposes. One example is acceleration related capabilities.")
  @JsonProperty("requestAdditionalCapabilities")
  public List<RequestAdditionalCapabilityData> getRequestAdditionalCapabilities() {
    return requestAdditionalCapabilities;
  }
  public void setRequestAdditionalCapabilities(List<RequestAdditionalCapabilityData> requestAdditionalCapabilities) {
    this.requestAdditionalCapabilities = requestAdditionalCapabilities;
  }

  /**
   **/
  public VirtualComputeDescription virtualMemory(VirtualMemoryData virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualMemory")
  @NotNull
  public VirtualMemoryData getVirtualMemory() {
    return virtualMemory;
  }
  public void setVirtualMemory(VirtualMemoryData virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  /**
   **/
  public VirtualComputeDescription virtualCpu(VirtualCpuData virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualCpu")
  @NotNull
  public VirtualCpuData getVirtualCpu() {
    return virtualCpu;
  }
  public void setVirtualCpu(VirtualCpuData virtualCpu) {
    this.virtualCpu = virtualCpu;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualComputeDescription virtualComputeDescription = (VirtualComputeDescription) o;
    return Objects.equals(virtualComputeDescId, virtualComputeDescription.virtualComputeDescId) &&
        Objects.equals(requestAdditionalCapabilities, virtualComputeDescription.requestAdditionalCapabilities) &&
        Objects.equals(virtualMemory, virtualComputeDescription.virtualMemory) &&
        Objects.equals(virtualCpu, virtualComputeDescription.virtualCpu);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualComputeDescId, requestAdditionalCapabilities, virtualMemory, virtualCpu);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualComputeDescription {\n");
    
    sb.append("    virtualComputeDescId: ").append(toIndentedString(virtualComputeDescId)).append("\n");
    sb.append("    requestAdditionalCapabilities: ").append(toIndentedString(requestAdditionalCapabilities)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
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

