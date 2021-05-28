package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Response for assign QoS queue
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Response for assign QoS queue")

public class AssignQoSResponse   {
  
  private @Valid String sliceId = null;
  private @Valid String srcEndpoint = null;
  private @Valid String dstEndpoint = null;
  private @Valid QoSPolicy policy = null;

  /**
   **/
  public AssignQoSResponse sliceId(String sliceId) {
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
  public AssignQoSResponse srcEndpoint(String srcEndpoint) {
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
  public AssignQoSResponse dstEndpoint(String dstEndpoint) {
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
  public AssignQoSResponse policy(QoSPolicy policy) {
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
    AssignQoSResponse assignQoSResponse = (AssignQoSResponse) o;
    return Objects.equals(sliceId, assignQoSResponse.sliceId) &&
        Objects.equals(srcEndpoint, assignQoSResponse.srcEndpoint) &&
        Objects.equals(dstEndpoint, assignQoSResponse.dstEndpoint) &&
        Objects.equals(policy, assignQoSResponse.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId, srcEndpoint, dstEndpoint, policy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQoSResponse {\n");
    
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    srcEndpoint: ").append(toIndentedString(srcEndpoint)).append("\n");
    sb.append("    dstEndpoint: ").append(toIndentedString(dstEndpoint)).append("\n");
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

