/*
 * WIM Manager API
 * WIM Manager API
 *
 * OpenAPI spec version: 0.0.3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Element providing information about Quality of Service attributes that the network shall support.
 */
@ApiModel(description = "Element providing information about Quality of Service attributes that the network shall support.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class VirtualNetworkResourceInformationNetworkQoS   {
  @JsonProperty("qosValue")
  private String qosValue = null;

  @JsonProperty("qosName")
  private String qosName = null;

  public VirtualNetworkResourceInformationNetworkQoS qosValue(String qosValue) {
    this.qosValue = qosValue;
    return this;
  }

  /**
   * Value of the QoS parameter.
   * @return qosValue
   **/
  @JsonProperty("qosValue")
  @ApiModelProperty(required = true, value = "Value of the QoS parameter.")
  @NotNull
  public String getQosValue() {
    return qosValue;
  }

  public void setQosValue(String qosValue) {
    this.qosValue = qosValue;
  }

  public VirtualNetworkResourceInformationNetworkQoS qosName(String qosName) {
    this.qosName = qosName;
    return this;
  }

  /**
   * Name given to the QoS parameter.
   * @return qosName
   **/
  @JsonProperty("qosName")
  @ApiModelProperty(required = true, value = "Name given to the QoS parameter.")
  @NotNull
  public String getQosName() {
    return qosName;
  }

  public void setQosName(String qosName) {
    this.qosName = qosName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetworkResourceInformationNetworkQoS virtualNetworkResourceInformationNetworkQoS = (VirtualNetworkResourceInformationNetworkQoS) o;
    return Objects.equals(this.qosValue, virtualNetworkResourceInformationNetworkQoS.qosValue) &&
        Objects.equals(this.qosName, virtualNetworkResourceInformationNetworkQoS.qosName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qosValue, qosName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetworkResourceInformationNetworkQoS {\n");
    
    sb.append("    qosValue: ").append(toIndentedString(qosValue)).append("\n");
    sb.append("    qosName: ").append(toIndentedString(qosName)).append("\n");
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
