/*
 * WIM Manager API
 * WIM Manager API
 *
 * OpenAPI spec version: 0.0.3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * It defines the virtual memory characteristics of the consumable virtualised compute resource.
 */
@ApiModel(description = "It defines the virtual memory characteristics of the consumable virtualised compute resource.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class VirtualComputeResourceInformationVirtualMemory   {
  @JsonProperty("numaSupported")
  private Boolean numaSupported = null;

  @JsonProperty("virtualMemOversubscriptionPolicy")
  private String virtualMemOversubscriptionPolicy = null;

  @JsonProperty("virtualMemSize")
  private BigDecimal virtualMemSize = null;

  public VirtualComputeResourceInformationVirtualMemory numaSupported(Boolean numaSupported) {
    this.numaSupported = numaSupported;
    return this;
  }

  /**
   * It specifies if the memory allocation can be cognisant of the relevant process/core allocation.
   * @return numaSupported
   **/
  @JsonProperty("numaSupported")
  @ApiModelProperty(required = true, value = "It specifies if the memory allocation can be cognisant of the relevant process/core allocation.")
  @NotNull
  public Boolean isNumaSupported() {
    return numaSupported;
  }

  public void setNumaSupported(Boolean numaSupported) {
    this.numaSupported = numaSupported;
  }

  public VirtualComputeResourceInformationVirtualMemory virtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
    return this;
  }

  /**
   * The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no concrete policy is defined.
   * @return virtualMemOversubscriptionPolicy
   **/
  @JsonProperty("virtualMemOversubscriptionPolicy")
  @ApiModelProperty(required = true, value = "The memory core oversubscription policy in terms of virtual memory to physical memory on the platform. The cardinality can be 0 if no concrete policy is defined.")
  @NotNull
  public String getVirtualMemOversubscriptionPolicy() {
    return virtualMemOversubscriptionPolicy;
  }

  public void setVirtualMemOversubscriptionPolicy(String virtualMemOversubscriptionPolicy) {
    this.virtualMemOversubscriptionPolicy = virtualMemOversubscriptionPolicy;
  }

  public VirtualComputeResourceInformationVirtualMemory virtualMemSize(BigDecimal virtualMemSize) {
    this.virtualMemSize = virtualMemSize;
    return this;
  }

  /**
   * Amount of virtual memory (e.g. in MB). Cardinality \&quot;1\&quot; covers the case where a specific configuration for the consumable resource is advertised.
   * @return virtualMemSize
   **/
  @JsonProperty("virtualMemSize")
  @ApiModelProperty(required = true, value = "Amount of virtual memory (e.g. in MB). Cardinality \"1\" covers the case where a specific configuration for the consumable resource is advertised.")
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
    return Objects.equals(this.numaSupported, virtualComputeResourceInformationVirtualMemory.numaSupported) &&
        Objects.equals(this.virtualMemOversubscriptionPolicy, virtualComputeResourceInformationVirtualMemory.virtualMemOversubscriptionPolicy) &&
        Objects.equals(this.virtualMemSize, virtualComputeResourceInformationVirtualMemory.virtualMemSize);
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

