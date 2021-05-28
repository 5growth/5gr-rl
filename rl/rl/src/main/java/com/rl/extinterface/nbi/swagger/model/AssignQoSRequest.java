package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Request for assign QoS queue
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Request for assign QoS queue")

public class AssignQoSRequest   {
  
  private @Valid String sliceId = null;
  private @Valid String srcEndpoint = null;
  private @Valid String dstEndpoint = null;
  private @Valid String interface1VLAN = null;
  private @Valid String interface2VLAN = null;
  private @Valid String wanLinkId = null;
  private @Valid QoSPolicy policy = null;

  /**
   **/
  public AssignQoSRequest sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("sliceId")
  public String getSliceId() {
    return sliceId;
  }
  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  /**
   **/
  public AssignQoSRequest srcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("srcEndpoint")
  public String getSrcEndpoint() {
    return srcEndpoint;
  }
  public void setSrcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
  }

  /**
   **/
  public AssignQoSRequest dstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("dstEndpoint")
  public String getDstEndpoint() {
    return dstEndpoint;
  }
  public void setDstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
  }

  /**
   **/
  public AssignQoSRequest interface1VLAN(String interface1VLAN) {
    this.interface1VLAN = interface1VLAN;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("interface1VLAN")
  public String getInterface1VLAN() {
    return interface1VLAN;
  }
  public void setInterface1VLAN(String interface1VLAN) {
    this.interface1VLAN = interface1VLAN;
  }

  /**
   **/
  public AssignQoSRequest interface2VLAN(String interface2VLAN) {
    this.interface2VLAN = interface2VLAN;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("interface2VLAN")
  public String getInterface2VLAN() {
    return interface2VLAN;
  }
  public void setInterface2VLAN(String interface2VLAN) {
    this.interface2VLAN = interface2VLAN;
  }

  /**
   **/
  public AssignQoSRequest wanLinkId(String wanLinkId) {
    this.wanLinkId = wanLinkId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("wanLinkId")
  public String getWanLinkId() {
    return wanLinkId;
  }
  public void setWanLinkId(String wanLinkId) {
    this.wanLinkId = wanLinkId;
  }

  /**
   **/
  public AssignQoSRequest policy(QoSPolicy policy) {
    this.policy = policy;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("policy")
  public QoSPolicy getPolicy() {
    return policy;
  }
  public void setPolicy(QoSPolicy policy) {
    this.policy = policy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignQoSRequest assignQoSRequest = (AssignQoSRequest) o;
    return Objects.equals(sliceId, assignQoSRequest.sliceId) &&
        Objects.equals(srcEndpoint, assignQoSRequest.srcEndpoint) &&
        Objects.equals(dstEndpoint, assignQoSRequest.dstEndpoint) &&
        Objects.equals(interface1VLAN, assignQoSRequest.interface1VLAN) &&
        Objects.equals(interface2VLAN, assignQoSRequest.interface2VLAN) &&
        Objects.equals(wanLinkId, assignQoSRequest.wanLinkId) &&
        Objects.equals(policy, assignQoSRequest.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId, srcEndpoint, dstEndpoint, interface1VLAN, interface2VLAN, wanLinkId, policy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQoSRequest {\n");
    
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    srcEndpoint: ").append(toIndentedString(srcEndpoint)).append("\n");
    sb.append("    dstEndpoint: ").append(toIndentedString(dstEndpoint)).append("\n");
    sb.append("    interface1VLAN: ").append(toIndentedString(interface1VLAN)).append("\n");
    sb.append("    interface2VLAN: ").append(toIndentedString(interface2VLAN)).append("\n");
    sb.append("    wanLinkId: ").append(toIndentedString(wanLinkId)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
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

