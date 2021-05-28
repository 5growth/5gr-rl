/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rl.extinterface.nbi.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.rl.extinterface.nbi.swagger.model.QoSPolicy;
import java.io.IOException;

/**
 * Response for assign QoS queue
 */
@ApiModel(description = "Response for assign QoS queue")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class AssignQoSResponse {
  @SerializedName("sliceId")
  private String sliceId = null;

  @SerializedName("srcEndpoint")
  private String srcEndpoint = null;

  @SerializedName("dstEndpoint")
  private String dstEndpoint = null;

  @SerializedName("policy")
  private QoSPolicy policy = null;

  public AssignQoSResponse sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

   /**
   * Get sliceId
   * @return sliceId
  **/
  @ApiModelProperty(value = "")
  public String getSliceId() {
    return sliceId;
  }

  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  public AssignQoSResponse srcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
    return this;
  }

   /**
   * Get srcEndpoint
   * @return srcEndpoint
  **/
  @ApiModelProperty(value = "")
  public String getSrcEndpoint() {
    return srcEndpoint;
  }

  public void setSrcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
  }

  public AssignQoSResponse dstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
    return this;
  }

   /**
   * Get dstEndpoint
   * @return dstEndpoint
  **/
  @ApiModelProperty(value = "")
  public String getDstEndpoint() {
    return dstEndpoint;
  }

  public void setDstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
  }

  public AssignQoSResponse policy(QoSPolicy policy) {
    this.policy = policy;
    return this;
  }

   /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(value = "")
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
    return Objects.equals(this.sliceId, assignQoSResponse.sliceId) &&
        Objects.equals(this.srcEndpoint, assignQoSResponse.srcEndpoint) &&
        Objects.equals(this.dstEndpoint, assignQoSResponse.dstEndpoint) &&
        Objects.equals(this.policy, assignQoSResponse.policy);
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

