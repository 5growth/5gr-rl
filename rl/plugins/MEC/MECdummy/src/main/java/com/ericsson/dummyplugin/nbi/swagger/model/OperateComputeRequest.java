package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Message for request the operating change of the compute resources
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Message for request the operating change of the compute resources")

public class OperateComputeRequest   {
  
  private @Valid String computeId = null;
  private @Valid String computeOperation = null;

  /**
   * Identifier of the compute resource
   **/
  public OperateComputeRequest computeId(String computeId) {
    this.computeId = computeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the compute resource")
  @JsonProperty("computeId")
  @NotNull
  public String getComputeId() {
    return computeId;
  }
  public void setComputeId(String computeId) {
    this.computeId = computeId;
  }

  /**
   * Operation Type on the compute resource
   **/
  public OperateComputeRequest computeOperation(String computeOperation) {
    this.computeOperation = computeOperation;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Operation Type on the compute resource")
  @JsonProperty("computeOperation")
  @NotNull
  public String getComputeOperation() {
    return computeOperation;
  }
  public void setComputeOperation(String computeOperation) {
    this.computeOperation = computeOperation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperateComputeRequest operateComputeRequest = (OperateComputeRequest) o;
    return Objects.equals(computeId, operateComputeRequest.computeId) &&
        Objects.equals(computeOperation, operateComputeRequest.computeOperation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computeId, computeOperation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperateComputeRequest {\n");
    
    sb.append("    computeId: ").append(toIndentedString(computeId)).append("\n");
    sb.append("    computeOperation: ").append(toIndentedString(computeOperation)).append("\n");
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

