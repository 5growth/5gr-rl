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
import java.io.IOException;

/**
 * QoS policy description
 */
@ApiModel(description = "QoS policy description")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class QoSPolicy {
  @SerializedName("maxCapacity")
  private String maxCapacity = null;

  @SerializedName("maxBurstSize")
  private String maxBurstSize = null;

  @SerializedName("minBW")
  private String minBW = null;

  @SerializedName("maxBW")
  private String maxBW = null;

  public QoSPolicy maxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
    return this;
  }

   /**
   * Get maxCapacity
   * @return maxCapacity
  **/
  @ApiModelProperty(value = "")
  public String getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public QoSPolicy maxBurstSize(String maxBurstSize) {
    this.maxBurstSize = maxBurstSize;
    return this;
  }

   /**
   * Get maxBurstSize
   * @return maxBurstSize
  **/
  @ApiModelProperty(value = "")
  public String getMaxBurstSize() {
    return maxBurstSize;
  }

  public void setMaxBurstSize(String maxBurstSize) {
    this.maxBurstSize = maxBurstSize;
  }

  public QoSPolicy minBW(String minBW) {
    this.minBW = minBW;
    return this;
  }

   /**
   * Get minBW
   * @return minBW
  **/
  @ApiModelProperty(value = "")
  public String getMinBW() {
    return minBW;
  }

  public void setMinBW(String minBW) {
    this.minBW = minBW;
  }

  public QoSPolicy maxBW(String maxBW) {
    this.maxBW = maxBW;
    return this;
  }

   /**
   * Get maxBW
   * @return maxBW
  **/
  @ApiModelProperty(value = "")
  public String getMaxBW() {
    return maxBW;
  }

  public void setMaxBW(String maxBW) {
    this.maxBW = maxBW;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QoSPolicy qoSPolicy = (QoSPolicy) o;
    return Objects.equals(this.maxCapacity, qoSPolicy.maxCapacity) &&
        Objects.equals(this.maxBurstSize, qoSPolicy.maxBurstSize) &&
        Objects.equals(this.minBW, qoSPolicy.minBW) &&
        Objects.equals(this.maxBW, qoSPolicy.maxBW);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxCapacity, maxBurstSize, minBW, maxBW);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QoSPolicy {\n");
    
    sb.append("    maxCapacity: ").append(toIndentedString(maxCapacity)).append("\n");
    sb.append("    maxBurstSize: ").append(toIndentedString(maxBurstSize)).append("\n");
    sb.append("    minBW: ").append(toIndentedString(minBW)).append("\n");
    sb.append("    maxBW: ").append(toIndentedString(maxBW)).append("\n");
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
