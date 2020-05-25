package com.rl.resourcemanagement.qosperfomanceisolation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AssignQosQueueRequest {
  
  private @Valid String sliceId = null;
  private @Valid String srcEndpoint = null;
  private @Valid String dstEndpoint = null;
  private @Valid String maxCapacity = null;
  private @Valid List<String> switchIdentifiers = new ArrayList<>();
  private @Valid String policy = null;

  /**
   * Slice Id
   **/
  public AssignQosQueueRequest sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  @ApiModelProperty(required = true, value = "Slice Id")
  @JsonProperty("sliceId")
  @NotNull
  public String getSliceId() {
    return sliceId;
  }
  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  /**
   * Identifier of the source endpoint of the Network Slice.
   **/
  public AssignQosQueueRequest srcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
    return this;
  }

  @ApiModelProperty(required = true, value = "Identifier of the source endpoint of the Network Slice.")
  @JsonProperty("srcEndpoint")
  @NotNull
  public String getSrcEndpoint() {
    return srcEndpoint;
  }
  public void setSrcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
  }

  /**
   * Identifier of the destination endpoint of the Network Slice.
   **/
  public AssignQosQueueRequest dstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
    return this;
  }

  @ApiModelProperty(required = true, value = "Identifier of the destination endpoint of the Network Slice.")
  @JsonProperty("dstEndpoint")
  @NotNull
  public String getDstEndpoint() {
    return dstEndpoint;
  }
  public void setDstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
  }

  /**
   * Maximum capacity allowed for the slice
   **/
  public AssignQosQueueRequest maxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
    return this;
  }

  @ApiModelProperty(required = true, value = "Maximum capacity allowed for the slice.")
  @JsonProperty("maxCapacity")
  @NotNull
  public String getMaxCapacity() {
    return maxCapacity;
  }
  public void setMaxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  /**
   * List of switch identifiers where to apply the QoS perfomance isolation
   **/
  public AssignQosQueueRequest switchIdentifiers(List<String> switchIdentifiers) {
    this.switchIdentifiers = switchIdentifiers;
    return this;
  }

  @ApiModelProperty(required = true, value = "List of switch identifiers where to apply the QoS perfomance isolation.")
  @JsonProperty("switchIdentifiers")
  @NotNull
  public List<String> getSwitchIdentifiers() {
    return switchIdentifiers;
  }
  public void setSwitchIdentifiers(List<String> switchIdentifiers) {
    this.switchIdentifiers = switchIdentifiers;
  }

  /**
   * Identifier of the QoS policy to apply
   **/
  public AssignQosQueueRequest policy(String policy) {
    this.policy = policy;
    return this;
  }

  @ApiModelProperty(required = true, value = "Identifier of the QoS policy to apply")
  @JsonProperty("policy")
  @NotNull
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
    AssignQosQueueRequest assignQosQueueRequest = (AssignQosQueueRequest) o;
    return Objects.equals(sliceId, assignQosQueueRequest.sliceId) &&
            Objects.equals(srcEndpoint, assignQosQueueRequest.srcEndpoint) &&
            Objects.equals(dstEndpoint, assignQosQueueRequest.dstEndpoint) &&
            Objects.equals(switchIdentifiers, assignQosQueueRequest.switchIdentifiers) &&
            Objects.equals(maxCapacity , assignQosQueueRequest.maxCapacity) &&
            Objects.equals(policy, assignQosQueueRequest.policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQueueRequest {\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    srcEndpoint: ").append(toIndentedString(srcEndpoint)).append("\n");
    sb.append("    dstEndpoint: ").append(toIndentedString(dstEndpoint)).append("\n");
    sb.append("    path: ").append(toIndentedString(switchIdentifiers)).append("\n");
    sb.append("    maxCapacity: ").append(toIndentedString(maxCapacity)).append("\n");
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

