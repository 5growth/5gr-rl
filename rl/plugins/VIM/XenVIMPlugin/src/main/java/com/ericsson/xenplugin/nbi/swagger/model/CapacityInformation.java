package com.ericsson.xenplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CapacityInformation   {
  
  private @Valid String allocatedCapacity = null;
  private @Valid String availableCapacity = null;
  private @Valid String reservedCapacity = null;
  private @Valid String totalCapacity = null;

  /**
   * The allocated capacity is usually specified as the current allocated capacity.
   **/
  public CapacityInformation allocatedCapacity(String allocatedCapacity) {
    this.allocatedCapacity = allocatedCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The allocated capacity is usually specified as the current allocated capacity.")
  @JsonProperty("allocatedCapacity")
  @NotNull
  public String getAllocatedCapacity() {
    return allocatedCapacity;
  }
  public void setAllocatedCapacity(String allocatedCapacity) {
    this.allocatedCapacity = allocatedCapacity;
  }

  /**
   * The free capacity available for allocation and reservation. It can be specified in terms of current capacity; or minimum and maximum capacity; average capacity; or other statistical measurement in the specified time interval. The set of measurements is to be defined during Stage 3.
   **/
  public CapacityInformation availableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The free capacity available for allocation and reservation. It can be specified in terms of current capacity; or minimum and maximum capacity; average capacity; or other statistical measurement in the specified time interval. The set of measurements is to be defined during Stage 3.")
  @JsonProperty("availableCapacity")
  @NotNull
  public String getAvailableCapacity() {
    return availableCapacity;
  }
  public void setAvailableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
  }

  /**
   * The reserved capacity. It can be specified in terms of current capacity; or minimum and maximum capacity; average capacity; or other statistical measurement in the specified time interval. The set of measurements is to be defined during Stage 3.
   **/
  public CapacityInformation reservedCapacity(String reservedCapacity) {
    this.reservedCapacity = reservedCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The reserved capacity. It can be specified in terms of current capacity; or minimum and maximum capacity; average capacity; or other statistical measurement in the specified time interval. The set of measurements is to be defined during Stage 3.")
  @JsonProperty("reservedCapacity")
  @NotNull
  public String getReservedCapacity() {
    return reservedCapacity;
  }
  public void setReservedCapacity(String reservedCapacity) {
    this.reservedCapacity = reservedCapacity;
  }

  /**
   * The total capacity is usually specified as a fixed capacity without variations in time. The set of measurements is left to Stage 3.
   **/
  public CapacityInformation totalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The total capacity is usually specified as a fixed capacity without variations in time. The set of measurements is left to Stage 3.")
  @JsonProperty("totalCapacity")
  @NotNull
  public String getTotalCapacity() {
    return totalCapacity;
  }
  public void setTotalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CapacityInformation capacityInformation = (CapacityInformation) o;
    return Objects.equals(allocatedCapacity, capacityInformation.allocatedCapacity) &&
        Objects.equals(availableCapacity, capacityInformation.availableCapacity) &&
        Objects.equals(reservedCapacity, capacityInformation.reservedCapacity) &&
        Objects.equals(totalCapacity, capacityInformation.totalCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allocatedCapacity, availableCapacity, reservedCapacity, totalCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CapacityInformation {\n");
    
    sb.append("    allocatedCapacity: ").append(toIndentedString(allocatedCapacity)).append("\n");
    sb.append("    availableCapacity: ").append(toIndentedString(availableCapacity)).append("\n");
    sb.append("    reservedCapacity: ").append(toIndentedString(reservedCapacity)).append("\n");
    sb.append("    totalCapacity: ").append(toIndentedString(totalCapacity)).append("\n");
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

