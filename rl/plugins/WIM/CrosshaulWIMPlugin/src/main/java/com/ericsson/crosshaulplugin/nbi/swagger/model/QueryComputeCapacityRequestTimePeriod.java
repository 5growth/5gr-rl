package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.util.Date;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The time interval for which capacity is queried. When omitted, an interval starting \&quot;now\&quot; is used. The time interval can be specified since resource reservations can be made for a specified time interval.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The time interval for which capacity is queried. When omitted, an interval starting \"now\" is used. The time interval can be specified since resource reservations can be made for a specified time interval.")

public class QueryComputeCapacityRequestTimePeriod   {
  
  private @Valid Date startTime = null;
  private @Valid Date stopTime = null;

  /**
   * Indication when the capacity query period starts.
   **/
  public QueryComputeCapacityRequestTimePeriod startTime(Date startTime) {
    this.startTime = startTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the capacity query period starts.")
  @JsonProperty("startTime")
  @NotNull
  public Date getStartTime() {
    return startTime;
  }
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Indication when the capacity query period stops.
   **/
  public QueryComputeCapacityRequestTimePeriod stopTime(Date stopTime) {
    this.stopTime = stopTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the capacity query period stops.")
  @JsonProperty("stopTime")
  @NotNull
  public Date getStopTime() {
    return stopTime;
  }
  public void setStopTime(Date stopTime) {
    this.stopTime = stopTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryComputeCapacityRequestTimePeriod queryComputeCapacityRequestTimePeriod = (QueryComputeCapacityRequestTimePeriod) o;
    return Objects.equals(startTime, queryComputeCapacityRequestTimePeriod.startTime) &&
        Objects.equals(stopTime, queryComputeCapacityRequestTimePeriod.stopTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, stopTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryComputeCapacityRequestTimePeriod {\n");
    
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    stopTime: ").append(toIndentedString(stopTime)).append("\n");
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

