package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeComputePoolReserved;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReserved;
import java.util.Date;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ReservedVirtualCompute   {
  
  private @Valid ReservedVirtualComputeComputePoolReserved computePoolReserved = null;
  private @Valid Date endTime = null;
  private @Valid Date expiryTime = null;
  private @Valid String reservationId = null;
  private @Valid String reservationStatus = null;
  private @Valid Date startTime = null;
  private @Valid ReservedVirtualComputeVirtualisationContainerReserved virtualisationContainerReserved = null;

  /**
   **/
  public ReservedVirtualCompute computePoolReserved(ReservedVirtualComputeComputePoolReserved computePoolReserved) {
    this.computePoolReserved = computePoolReserved;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("computePoolReserved")
  @NotNull
  public ReservedVirtualComputeComputePoolReserved getComputePoolReserved() {
    return computePoolReserved;
  }
  public void setComputePoolReserved(ReservedVirtualComputeComputePoolReserved computePoolReserved) {
    this.computePoolReserved = computePoolReserved;
  }

  /**
   * Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   **/
  public ReservedVirtualCompute endTime(Date endTime) {
    this.endTime = endTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.")
  @JsonProperty("endTime")
  @NotNull
  public Date getEndTime() {
    return endTime;
  }
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
   * Indication when the VIM can release the reservation in case no allocation request against this reservation was made.
   **/
  public ReservedVirtualCompute expiryTime(Date expiryTime) {
    this.expiryTime = expiryTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the VIM can release the reservation in case no allocation request against this reservation was made.")
  @JsonProperty("expiryTime")
  @NotNull
  public Date getExpiryTime() {
    return expiryTime;
  }
  public void setExpiryTime(Date expiryTime) {
    this.expiryTime = expiryTime;
  }

  /**
   * Identifier of the resource reservation.
   **/
  public ReservedVirtualCompute reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource reservation.")
  @JsonProperty("reservationId")
  @NotNull
  public String getReservationId() {
    return reservationId;
  }
  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  /**
   * Status of the compute resource reservation, e.g. to indicate if a reservation is being used.
   **/
  public ReservedVirtualCompute reservationStatus(String reservationStatus) {
    this.reservationStatus = reservationStatus;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Status of the compute resource reservation, e.g. to indicate if a reservation is being used.")
  @JsonProperty("reservationStatus")
  @NotNull
  public String getReservationStatus() {
    return reservationStatus;
  }
  public void setReservationStatus(String reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  /**
   * Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.
   **/
  public ReservedVirtualCompute startTime(Date startTime) {
    this.startTime = startTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.")
  @JsonProperty("startTime")
  @NotNull
  public Date getStartTime() {
    return startTime;
  }
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   **/
  public ReservedVirtualCompute virtualisationContainerReserved(ReservedVirtualComputeVirtualisationContainerReserved virtualisationContainerReserved) {
    this.virtualisationContainerReserved = virtualisationContainerReserved;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualisationContainerReserved")
  @NotNull
  public ReservedVirtualComputeVirtualisationContainerReserved getVirtualisationContainerReserved() {
    return virtualisationContainerReserved;
  }
  public void setVirtualisationContainerReserved(ReservedVirtualComputeVirtualisationContainerReserved virtualisationContainerReserved) {
    this.virtualisationContainerReserved = virtualisationContainerReserved;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualCompute reservedVirtualCompute = (ReservedVirtualCompute) o;
    return Objects.equals(computePoolReserved, reservedVirtualCompute.computePoolReserved) &&
        Objects.equals(endTime, reservedVirtualCompute.endTime) &&
        Objects.equals(expiryTime, reservedVirtualCompute.expiryTime) &&
        Objects.equals(reservationId, reservedVirtualCompute.reservationId) &&
        Objects.equals(reservationStatus, reservedVirtualCompute.reservationStatus) &&
        Objects.equals(startTime, reservedVirtualCompute.startTime) &&
        Objects.equals(virtualisationContainerReserved, reservedVirtualCompute.virtualisationContainerReserved);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computePoolReserved, endTime, expiryTime, reservationId, reservationStatus, startTime, virtualisationContainerReserved);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualCompute {\n");
    
    sb.append("    computePoolReserved: ").append(toIndentedString(computePoolReserved)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    reservationStatus: ").append(toIndentedString(reservationStatus)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    virtualisationContainerReserved: ").append(toIndentedString(virtualisationContainerReserved)).append("\n");
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

