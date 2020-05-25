package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * This is used to describe the maximum latency tolerated by the mobile edge application.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "This is used to describe the maximum latency tolerated by the mobile edge application.")

public class LatencyDescriptor   {
  
  private @Valid String timeUnit = null;
  private @Valid BigDecimal latency = null;

  /**
   * Time unit, e.g., ms
   **/
  public LatencyDescriptor timeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Time unit, e.g., ms")
  @JsonProperty("timeUnit")
  @NotNull
  public String getTimeUnit() {
    return timeUnit;
  }
  public void setTimeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
  }

  /**
   * The value of the latency
   **/
  public LatencyDescriptor latency(BigDecimal latency) {
    this.latency = latency;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The value of the latency")
  @JsonProperty("latency")
  @NotNull
  public BigDecimal getLatency() {
    return latency;
  }
  public void setLatency(BigDecimal latency) {
    this.latency = latency;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LatencyDescriptor latencyDescriptor = (LatencyDescriptor) o;
    return Objects.equals(timeUnit, latencyDescriptor.timeUnit) &&
        Objects.equals(latency, latencyDescriptor.latency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timeUnit, latency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LatencyDescriptor {\n");
    
    sb.append("    timeUnit: ").append(toIndentedString(timeUnit)).append("\n");
    sb.append("    latency: ").append(toIndentedString(latency)).append("\n");
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

