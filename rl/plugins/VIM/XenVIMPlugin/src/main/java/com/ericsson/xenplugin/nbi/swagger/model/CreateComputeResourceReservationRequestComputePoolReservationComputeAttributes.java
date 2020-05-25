package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information specifying additional attributes of the compute resource to be reserved.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information specifying additional attributes of the compute resource to be reserved.")

public class CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes   {
  
  private @Valid String accelerationCapability = null;
  private @Valid String cpuArchitecture = null;
  private @Valid String virtualCpuOversubscriptionPolicy = null;

  /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is requested.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is requested.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public String getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * CPU architecture type. Examples are \&quot;x86\&quot;, \&quot;ARM\&quot;. The cardinality can be 0, if no particular CPU architecture type is requested.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes cpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "CPU architecture type. Examples are \"x86\", \"ARM\". The cardinality can be 0, if no particular CPU architecture type is requested.")
  @JsonProperty("cpuArchitecture")
  @NotNull
  public String getCpuArchitecture() {
    return cpuArchitecture;
  }
  public void setCpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
  }

  /**
   * The CPU core oversubscription policy in terms of virtual CPU cores to physical CPU cores/threads on the platform. The cardinality can be 0, if no particular value is requested.
   **/
  public CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes virtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The CPU core oversubscription policy in terms of virtual CPU cores to physical CPU cores/threads on the platform. The cardinality can be 0, if no particular value is requested.")
  @JsonProperty("virtualCpuOversubscriptionPolicy")
  @NotNull
  public String getVirtualCpuOversubscriptionPolicy() {
    return virtualCpuOversubscriptionPolicy;
  }
  public void setVirtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes createComputeResourceReservationRequestComputePoolReservationComputeAttributes = (CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes) o;
    return Objects.equals(accelerationCapability, createComputeResourceReservationRequestComputePoolReservationComputeAttributes.accelerationCapability) &&
        Objects.equals(cpuArchitecture, createComputeResourceReservationRequestComputePoolReservationComputeAttributes.cpuArchitecture) &&
        Objects.equals(virtualCpuOversubscriptionPolicy, createComputeResourceReservationRequestComputePoolReservationComputeAttributes.virtualCpuOversubscriptionPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, cpuArchitecture, virtualCpuOversubscriptionPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    virtualCpuOversubscriptionPolicy: ").append(toIndentedString(virtualCpuOversubscriptionPolicy)).append("\n");
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

