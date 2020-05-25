package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Consumer-managed list of identifiers of virtualised resources with which the actual resource is requested to be affine or anti-affine. Either affinityAntiAffinityResourceList or affinityAntiAffinityResourceGroup but not both shall be present.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Consumer-managed list of identifiers of virtualised resources with which the actual resource is requested to be affine or anti-affine. Either affinityAntiAffinityResourceList or affinityAntiAffinityResourceGroup but not both shall be present.")

public class AllocateComputeRequestAffinityAntiAffinityResourceList   {
  
  private @Valid List<String> resource = new ArrayList<String>();

  /**
   * List of identifiers of virtualised resources.
   **/
  public AllocateComputeRequestAffinityAntiAffinityResourceList resource(List<String> resource) {
    this.resource = resource;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of identifiers of virtualised resources.")
  @JsonProperty("resource")
  @NotNull
  public List<String> getResource() {
    return resource;
  }
  public void setResource(List<String> resource) {
    this.resource = resource;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequestAffinityAntiAffinityResourceList allocateComputeRequestAffinityAntiAffinityResourceList = (AllocateComputeRequestAffinityAntiAffinityResourceList) o;
    return Objects.equals(resource, allocateComputeRequestAffinityAntiAffinityResourceList.resource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequestAffinityAntiAffinityResourceList {\n");
    
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
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

