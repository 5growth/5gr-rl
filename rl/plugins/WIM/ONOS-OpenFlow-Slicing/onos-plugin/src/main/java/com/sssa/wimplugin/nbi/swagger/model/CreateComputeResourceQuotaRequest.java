package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.CreateComputeResourceQuotaRequestVirtualComputeQuota;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateComputeResourceQuotaRequest   {
  
  private @Valid String resourceGroupId = null;
  private @Valid CreateComputeResourceQuotaRequestVirtualComputeQuota virtualComputeQuota = null;

  /**
   * Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.
   **/
  public CreateComputeResourceQuotaRequest resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.")
  @JsonProperty("resourceGroupId")
  @NotNull
  public String getResourceGroupId() {
    return resourceGroupId;
  }
  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  /**
   **/
  public CreateComputeResourceQuotaRequest virtualComputeQuota(CreateComputeResourceQuotaRequestVirtualComputeQuota virtualComputeQuota) {
    this.virtualComputeQuota = virtualComputeQuota;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualComputeQuota")
  @NotNull
  public CreateComputeResourceQuotaRequestVirtualComputeQuota getVirtualComputeQuota() {
    return virtualComputeQuota;
  }
  public void setVirtualComputeQuota(CreateComputeResourceQuotaRequestVirtualComputeQuota virtualComputeQuota) {
    this.virtualComputeQuota = virtualComputeQuota;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceQuotaRequest createComputeResourceQuotaRequest = (CreateComputeResourceQuotaRequest) o;
    return Objects.equals(resourceGroupId, createComputeResourceQuotaRequest.resourceGroupId) &&
        Objects.equals(virtualComputeQuota, createComputeResourceQuotaRequest.virtualComputeQuota);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceGroupId, virtualComputeQuota);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceQuotaRequest {\n");
    
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    virtualComputeQuota: ").append(toIndentedString(virtualComputeQuota)).append("\n");
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

