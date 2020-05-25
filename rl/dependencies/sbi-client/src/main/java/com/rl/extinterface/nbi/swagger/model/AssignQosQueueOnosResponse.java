package com.rl.extinterface.nbi.swagger.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AssignQosQueueOnosResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-08-05T11:05:49.089Z")
public class AssignQosQueueOnosResponse {

  @SerializedName("sliceId")
  private String sliceId = null;

  @SerializedName("srcEndpoint")
  private String srcEndpoint = null;

  @SerializedName("dstEndpoint")
  private String dstEndpoint = null;

  @SerializedName("maxCapacity")
  private String maxCapacity = null;

  @SerializedName("switchIdentifiers")
  private List<String> switchIdentifiers = new ArrayList<String>();

  @SerializedName("policy")
  private String policy = null;


  public AssignQosQueueOnosResponse sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  /**
   * Identifier of the Network Slice to which apply QoS performance isolation
   * @return sliceId
   **/
  @ApiModelProperty(required = true, value = "Identifier of the Network Slice to which apply QoS performance isolation")
  public String getSliceId() {
    return sliceId;
  }

  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  public AssignQosQueueOnosResponse srcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
    return this;
  }

  /**
   * Identifer of the Source Endpoint of the Network Slice.
   * @return srcEndpoint
   **/
  @ApiModelProperty(required = true, value = "Identifier of Source Endpoint the network slice ")
  public String getSrcEndpoint() {
    return srcEndpoint;
  }

  public void setSrcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
  }



  public AssignQosQueueOnosResponse dstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
    return this;
  }

  /**
   * Identifer of the Source Endpoint of the Network Slice.
   * @return dstEndpoint
   **/
  @ApiModelProperty(required = true, value = "Identifier of Source Endpoint the network slice ")
  public String getDstEndpoint() {
    return dstEndpoint;
  }

  public void setDstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
  }


  public AssignQosQueueOnosResponse maxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
    return this;
  }

  /**
   * Maximum capacity allowed for the slice.
   * @return maxCapacity
   **/
  @ApiModelProperty(required = true, value = "Maximum capacity allowed for the slice.")
  public String getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
  }


  public AssignQosQueueOnosResponse switchIdentifiers(List<String> switchIdentifiers) {
    this.switchIdentifiers = switchIdentifiers;
    return this;
  }

  public AssignQosQueueOnosResponse addSwitchIdentifiersItem(String switchIdentifiersItem) {
    this.switchIdentifiers.add(switchIdentifiersItem);
    return this;
  }

  /**
   * List of switch identifiers where to apply the QoS perfomance isolation
   * @return switchIdentifiers
   **/
  @ApiModelProperty(required = true, value = "List of switch identifiers where to apply the QoS perfomance isolation")
  public List<String> getSwitchIdentifiers() {
    return switchIdentifiers;
  }

  public void setSwitchIdentifiers(List<String> switchIdentifiers) {
    this.switchIdentifiers = switchIdentifiers;
  }

  public AssignQosQueueOnosResponse policy(String policy) {
    this.policy = policy;
    return this;
  }

  /**
   * Identifier of the QoS policy to apply
   * @return policy
   **/
  @ApiModelProperty(required = true, value = "Identifier of the QoS policy to apply")
  public String getPolicy() {
    return policy;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignQosQueueOnosResponse assignQosQueueOnosResponse = (AssignQosQueueOnosResponse) o;
    return Objects.equals(this.sliceId, assignQosQueueOnosResponse.sliceId) &&
            Objects.equals(this.srcEndpoint, assignQosQueueOnosResponse.srcEndpoint) &&
            Objects.equals(this.dstEndpoint, assignQosQueueOnosResponse.dstEndpoint) &&
            Objects.equals(this.maxCapacity, assignQosQueueOnosResponse.maxCapacity) &&
            Objects.equals(this.switchIdentifiers, assignQosQueueOnosResponse.switchIdentifiers) &&
            Objects.equals(this.policy, assignQosQueueOnosResponse.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId, srcEndpoint, maxCapacity, switchIdentifiers, policy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQosQueueResponse {\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    srcEndpoint: ").append(toIndentedString(srcEndpoint)).append("\n");
    sb.append("    dstEndpoint: ").append(toIndentedString(dstEndpoint)).append("\n");
    sb.append("    maxCapacity: ").append(toIndentedString(maxCapacity)).append("\n");
    sb.append("    switchIdentifiers: ").append(toIndentedString(switchIdentifiers)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

