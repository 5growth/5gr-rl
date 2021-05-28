package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * QoS policy description
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "QoS policy description")

public class QoSPolicy   {
  
  private @Valid String policyId = null;
  private @Valid String maxCapacity = null;
  private @Valid String maxBurstSize = null;
  private @Valid String minBW = null;
  private @Valid String maxBW = null;

  /**
   **/
  public QoSPolicy policyId(String policyId) {
    this.policyId = policyId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("policyId")
  public String getPolicyId() {
    return policyId;
  }
  public void setPolicyId(String policyId) {
    this.policyId = policyId;
  }

  /**
   **/
  public QoSPolicy maxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("maxCapacity")
  public String getMaxCapacity() {
    return maxCapacity;
  }
  public void setMaxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  /**
   **/
  public QoSPolicy maxBurstSize(String maxBurstSize) {
    this.maxBurstSize = maxBurstSize;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("maxBurstSize")
  public String getMaxBurstSize() {
    return maxBurstSize;
  }
  public void setMaxBurstSize(String maxBurstSize) {
    this.maxBurstSize = maxBurstSize;
  }

  /**
   **/
  public QoSPolicy minBW(String minBW) {
    this.minBW = minBW;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("minBW")
  public String getMinBW() {
    return minBW;
  }
  public void setMinBW(String minBW) {
    this.minBW = minBW;
  }

  /**
   **/
  public QoSPolicy maxBW(String maxBW) {
    this.maxBW = maxBW;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("maxBW")
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
    return Objects.equals(policyId, qoSPolicy.policyId) &&
        Objects.equals(maxCapacity, qoSPolicy.maxCapacity) &&
        Objects.equals(maxBurstSize, qoSPolicy.maxBurstSize) &&
        Objects.equals(minBW, qoSPolicy.minBW) &&
        Objects.equals(maxBW, qoSPolicy.maxBW);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyId, maxCapacity, maxBurstSize, minBW, maxBW);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QoSPolicy {\n");
    
    sb.append("    policyId: ").append(toIndentedString(policyId)).append("\n");
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

