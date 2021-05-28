package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.AssignQoSResponse;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AssignQoSListInner   {
  
  private @Valid AssignQoSResponse qosQueueList = null;

  /**
   **/
  public AssignQoSListInner qosQueueList(AssignQoSResponse qosQueueList) {
    this.qosQueueList = qosQueueList;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("qosQueueList")
  public AssignQoSResponse getQosQueueList() {
    return qosQueueList;
  }
  public void setQosQueueList(AssignQoSResponse qosQueueList) {
    this.qosQueueList = qosQueueList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignQoSListInner assignQoSListInner = (AssignQoSListInner) o;
    return Objects.equals(qosQueueList, assignQoSListInner.qosQueueList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qosQueueList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignQoSListInner {\n");
    
    sb.append("    qosQueueList: ").append(toIndentedString(qosQueueList)).append("\n");
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

