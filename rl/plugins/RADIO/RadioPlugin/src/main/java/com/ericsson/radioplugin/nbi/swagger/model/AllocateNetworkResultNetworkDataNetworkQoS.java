package com.ericsson.radioplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateNetworkResultNetworkDataNetworkQoS   {
  
  private @Valid String qosName = null;
  private @Valid String qosValue = null;

  /**
   * Name given to the QoS parameter.
   **/
  public AllocateNetworkResultNetworkDataNetworkQoS qosName(String qosName) {
    this.qosName = qosName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name given to the QoS parameter.")
  @JsonProperty("qosName")
  @NotNull
  public String getQosName() {
    return qosName;
  }
  public void setQosName(String qosName) {
    this.qosName = qosName;
  }

  /**
   * Value of the QoS parameter.
   **/
  public AllocateNetworkResultNetworkDataNetworkQoS qosValue(String qosValue) {
    this.qosValue = qosValue;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Value of the QoS parameter.")
  @JsonProperty("qosValue")
  @NotNull
  public String getQosValue() {
    return qosValue;
  }
  public void setQosValue(String qosValue) {
    this.qosValue = qosValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkResultNetworkDataNetworkQoS allocateNetworkResultNetworkDataNetworkQoS = (AllocateNetworkResultNetworkDataNetworkQoS) o;
    return Objects.equals(qosName, allocateNetworkResultNetworkDataNetworkQoS.qosName) &&
        Objects.equals(qosValue, allocateNetworkResultNetworkDataNetworkQoS.qosValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qosName, qosValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResultNetworkDataNetworkQoS {\n");
    
    sb.append("    qosName: ").append(toIndentedString(qosName)).append("\n");
    sb.append("    qosValue: ").append(toIndentedString(qosValue)).append("\n");
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

