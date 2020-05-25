package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Message for request the operating change of the resources
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Message for request the operating change of the resources")

public class OperateComputeRequest   {
  
  private @Valid String id = null;
  private @Valid String popId = null;
  private @Valid String operationStatus = null;
  private @Valid MetaData metaData = null;

  /**
   * Identifier of the resource
   **/
  public OperateComputeRequest id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource")
  @JsonProperty("Id")
  @NotNull
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Identifier of the resource
   **/
  public OperateComputeRequest popId(String popId) {
    this.popId = popId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the resource")
  @JsonProperty("PopId")
  public String getPopId() {
    return popId;
  }
  public void setPopId(String popId) {
    this.popId = popId;
  }

  /**
   * Operation Type on the resource
   **/
  public OperateComputeRequest operationStatus(String operationStatus) {
    this.operationStatus = operationStatus;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Operation Type on the resource")
  @JsonProperty("OperationStatus")
  @NotNull
  public String getOperationStatus() {
    return operationStatus;
  }
  public void setOperationStatus(String operationStatus) {
    this.operationStatus = operationStatus;
  }

  /**
   **/
  public OperateComputeRequest metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("metaData")
  @NotNull
  public MetaData getMetaData() {
    return metaData;
  }
  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
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
    return Objects.equals(id, operateComputeRequest.id) &&
        Objects.equals(popId, operateComputeRequest.popId) &&
        Objects.equals(operationStatus, operateComputeRequest.operationStatus) &&
        Objects.equals(metaData, operateComputeRequest.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, popId, operationStatus, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperateComputeRequest {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    popId: ").append(toIndentedString(popId)).append("\n");
    sb.append("    operationStatus: ").append(toIndentedString(operationStatus)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
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

