package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequestAffinityAntiAffinityResourceList;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateComputeRequestAffinityOrAntiAffinityConstraints   {
  
  private @Valid String affinityAntiAffinityResourceGroup = null;
  private @Valid AllocateComputeRequestAffinityAntiAffinityResourceList affinityAntiAffinityResourceList = null;
  private @Valid String scope = null;
  private @Valid String type = null;

  /**
   * Identifier of the producer-managed group of virtualised resources with which the actual resource is requested to be affine or anti-affine. Either affinityAntiAffinityResourceList or affinityAntiAffinityResourceGroup but not both shall be present.
   **/
  public AllocateComputeRequestAffinityOrAntiAffinityConstraints affinityAntiAffinityResourceGroup(String affinityAntiAffinityResourceGroup) {
    this.affinityAntiAffinityResourceGroup = affinityAntiAffinityResourceGroup;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the producer-managed group of virtualised resources with which the actual resource is requested to be affine or anti-affine. Either affinityAntiAffinityResourceList or affinityAntiAffinityResourceGroup but not both shall be present.")
  @JsonProperty("affinityAntiAffinityResourceGroup")
  @NotNull
  public String getAffinityAntiAffinityResourceGroup() {
    return affinityAntiAffinityResourceGroup;
  }
  public void setAffinityAntiAffinityResourceGroup(String affinityAntiAffinityResourceGroup) {
    this.affinityAntiAffinityResourceGroup = affinityAntiAffinityResourceGroup;
  }

  /**
   **/
  public AllocateComputeRequestAffinityOrAntiAffinityConstraints affinityAntiAffinityResourceList(AllocateComputeRequestAffinityAntiAffinityResourceList affinityAntiAffinityResourceList) {
    this.affinityAntiAffinityResourceList = affinityAntiAffinityResourceList;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("affinityAntiAffinityResourceList")
  @NotNull
  public AllocateComputeRequestAffinityAntiAffinityResourceList getAffinityAntiAffinityResourceList() {
    return affinityAntiAffinityResourceList;
  }
  public void setAffinityAntiAffinityResourceList(AllocateComputeRequestAffinityAntiAffinityResourceList affinityAntiAffinityResourceList) {
    this.affinityAntiAffinityResourceList = affinityAntiAffinityResourceList;
  }

  /**
   * Qualifies the scope of the constraint. In case of compute resource: e.g. \&quot;NFVI-PoP\&quot; or \&quot;NFVI Node\&quot;. In case of ports: e.g. \&quot;virtual switch or router\&quot; or \&quot;physical NIC\&quot;, or \&quot;physical network\&quot; or \&quot;NFVI Node\&quot;. In case of networks: e.g. \&quot;physical NIC\&quot;, \&quot;physical network\&quot; or \&quot;NFVI Node\&quot;. In case of subnets: it should be ignored. Defaults to \&quot;NFVI Node\&quot; if absent.
   **/
  public AllocateComputeRequestAffinityOrAntiAffinityConstraints scope(String scope) {
    this.scope = scope;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Qualifies the scope of the constraint. In case of compute resource: e.g. \"NFVI-PoP\" or \"NFVI Node\". In case of ports: e.g. \"virtual switch or router\" or \"physical NIC\", or \"physical network\" or \"NFVI Node\". In case of networks: e.g. \"physical NIC\", \"physical network\" or \"NFVI Node\". In case of subnets: it should be ignored. Defaults to \"NFVI Node\" if absent.")
  @JsonProperty("scope")
  @NotNull
  public String getScope() {
    return scope;
  }
  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Indicates whether this is an affinity or anti-affinity constraint.
   **/
  public AllocateComputeRequestAffinityOrAntiAffinityConstraints type(String type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indicates whether this is an affinity or anti-affinity constraint.")
  @JsonProperty("type")
  @NotNull
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequestAffinityOrAntiAffinityConstraints allocateComputeRequestAffinityOrAntiAffinityConstraints = (AllocateComputeRequestAffinityOrAntiAffinityConstraints) o;
    return Objects.equals(affinityAntiAffinityResourceGroup, allocateComputeRequestAffinityOrAntiAffinityConstraints.affinityAntiAffinityResourceGroup) &&
        Objects.equals(affinityAntiAffinityResourceList, allocateComputeRequestAffinityOrAntiAffinityConstraints.affinityAntiAffinityResourceList) &&
        Objects.equals(scope, allocateComputeRequestAffinityOrAntiAffinityConstraints.scope) &&
        Objects.equals(type, allocateComputeRequestAffinityOrAntiAffinityConstraints.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityAntiAffinityResourceGroup, affinityAntiAffinityResourceList, scope, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequestAffinityOrAntiAffinityConstraints {\n");
    
    sb.append("    affinityAntiAffinityResourceGroup: ").append(toIndentedString(affinityAntiAffinityResourceGroup)).append("\n");
    sb.append("    affinityAntiAffinityResourceList: ").append(toIndentedString(affinityAntiAffinityResourceList)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

