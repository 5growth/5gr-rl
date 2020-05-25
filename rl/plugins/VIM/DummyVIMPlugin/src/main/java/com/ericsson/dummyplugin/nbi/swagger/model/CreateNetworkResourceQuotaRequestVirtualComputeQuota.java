package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Type and configuration of virtualised network resources that need to be restricted by the quota, e.g. {\&quot;numPublicIps\&quot;: 20}.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Type and configuration of virtualised network resources that need to be restricted by the quota, e.g. {\"numPublicIps\": 20}.")

public class CreateNetworkResourceQuotaRequestVirtualComputeQuota   {
  
  private @Valid Integer numPorts = null;
  private @Valid Integer numPublicIps = null;
  private @Valid Integer numSubnets = null;

  /**
   * Number of ports to be restricted by the quota. The cardinality can be 0 if no specific number of ports is to be restricted by the quota or the quota for ports is not to be update.
   **/
  public CreateNetworkResourceQuotaRequestVirtualComputeQuota numPorts(Integer numPorts) {
    this.numPorts = numPorts;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of ports to be restricted by the quota. The cardinality can be 0 if no specific number of ports is to be restricted by the quota or the quota for ports is not to be update.")
  @JsonProperty("numPorts")
  @NotNull
  public Integer getNumPorts() {
    return numPorts;
  }
  public void setNumPorts(Integer numPorts) {
    this.numPorts = numPorts;
  }

  /**
   * Number of public IP addresses to be restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses is to be restricted by the quota or the quota for public IP addresses is not to be update.
   **/
  public CreateNetworkResourceQuotaRequestVirtualComputeQuota numPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of public IP addresses to be restricted by the quota. The cardinality can be 0 if no specific number of public IP addresses is to be restricted by the quota or the quota for public IP addresses is not to be update.")
  @JsonProperty("numPublicIps")
  @NotNull
  public Integer getNumPublicIps() {
    return numPublicIps;
  }
  public void setNumPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
  }

  /**
   * Number of subnets to be restricted by the quota. The cardinality can be 0 if no specific number of subnets is to be restricted by the quota or the quota for subnets is not to be update.
   **/
  public CreateNetworkResourceQuotaRequestVirtualComputeQuota numSubnets(Integer numSubnets) {
    this.numSubnets = numSubnets;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of subnets to be restricted by the quota. The cardinality can be 0 if no specific number of subnets is to be restricted by the quota or the quota for subnets is not to be update.")
  @JsonProperty("numSubnets")
  @NotNull
  public Integer getNumSubnets() {
    return numSubnets;
  }
  public void setNumSubnets(Integer numSubnets) {
    this.numSubnets = numSubnets;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNetworkResourceQuotaRequestVirtualComputeQuota createNetworkResourceQuotaRequestVirtualComputeQuota = (CreateNetworkResourceQuotaRequestVirtualComputeQuota) o;
    return Objects.equals(numPorts, createNetworkResourceQuotaRequestVirtualComputeQuota.numPorts) &&
        Objects.equals(numPublicIps, createNetworkResourceQuotaRequestVirtualComputeQuota.numPublicIps) &&
        Objects.equals(numSubnets, createNetworkResourceQuotaRequestVirtualComputeQuota.numSubnets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numPorts, numPublicIps, numSubnets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceQuotaRequestVirtualComputeQuota {\n");
    
    sb.append("    numPorts: ").append(toIndentedString(numPorts)).append("\n");
    sb.append("    numPublicIps: ").append(toIndentedString(numPublicIps)).append("\n");
    sb.append("    numSubnets: ").append(toIndentedString(numSubnets)).append("\n");
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

