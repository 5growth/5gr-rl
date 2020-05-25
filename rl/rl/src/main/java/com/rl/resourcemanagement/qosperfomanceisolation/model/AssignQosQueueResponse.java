package com.rl.resourcemanagement.qosperfomanceisolation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public class AssignQosQueueResponse {
  
  private @Valid String queueId = null;

  /**
   * SLICE Id
   **/
  public AssignQosQueueResponse queueId(String queueId) {
    this.queueId = queueId;
    return this;
  }


  @ApiModelProperty(required = true, value = "sLICE Id")
  @JsonProperty("queueId")
  @NotNull
  public String getQueueId() {
    return queueId;
  }
  public void setQueueId(String queueId) {
    this.queueId = queueId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignQosQueueResponse allocateComputeRequest = (AssignQosQueueResponse) o;
    return Objects.equals(queueId, allocateComputeRequest.queueId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(queueId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQueueRequest {\n");
    sb.append("    queueId: ").append(toIndentedString(queueId)).append("\n");
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

