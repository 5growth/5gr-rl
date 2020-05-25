package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.CreateNetworkResourceQuotaRequestVirtualComputeQuota;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateNetworkResourceQuotaRequest   {
  
  private @Valid String resourceGroupId = null;
  private @Valid CreateNetworkResourceQuotaRequestVirtualComputeQuota virtualComputeQuota = null;

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public CreateNetworkResourceQuotaRequest resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
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
  public CreateNetworkResourceQuotaRequest virtualComputeQuota(CreateNetworkResourceQuotaRequestVirtualComputeQuota virtualComputeQuota) {
    this.virtualComputeQuota = virtualComputeQuota;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualComputeQuota")
  @NotNull
  public CreateNetworkResourceQuotaRequestVirtualComputeQuota getVirtualComputeQuota() {
    return virtualComputeQuota;
  }
  public void setVirtualComputeQuota(CreateNetworkResourceQuotaRequestVirtualComputeQuota virtualComputeQuota) {
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
    CreateNetworkResourceQuotaRequest createNetworkResourceQuotaRequest = (CreateNetworkResourceQuotaRequest) o;
    return Objects.equals(resourceGroupId, createNetworkResourceQuotaRequest.resourceGroupId) &&
        Objects.equals(virtualComputeQuota, createNetworkResourceQuotaRequest.virtualComputeQuota);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceGroupId, virtualComputeQuota);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceQuotaRequest {\n");
    
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

