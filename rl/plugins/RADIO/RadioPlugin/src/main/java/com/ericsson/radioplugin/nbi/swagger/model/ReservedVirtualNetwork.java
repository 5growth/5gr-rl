package com.ericsson.radioplugin.nbi.swagger.model;

import com.ericsson.radioplugin.nbi.swagger.model.ReservedVirtualNetworkNetworkAttributes;
import com.ericsson.radioplugin.nbi.swagger.model.ReservedVirtualNetworkNetworkPorts;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ReservedVirtualNetwork   {
  
  private @Valid Date endTime = null;
  private @Valid Date expiryTime = null;
  private @Valid ReservedVirtualNetworkNetworkAttributes networkAttributes = null;
  private @Valid List<ReservedVirtualNetworkNetworkPorts> networkPorts = new ArrayList<ReservedVirtualNetworkNetworkPorts>();
  private @Valid String publicIps = null;
  private @Valid String reservationId = null;
  private @Valid String reservationStatus = null;
  private @Valid Date startTime = null;
  private @Valid String zoneId = null;

  /**
   * Indication when the reservation ends (when it is expected that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   **/
  public ReservedVirtualNetwork endTime(Date endTime) {
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
  public ReservedVirtualNetwork expiryTime(Date expiryTime) {
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
   **/
  public ReservedVirtualNetwork networkAttributes(ReservedVirtualNetworkNetworkAttributes networkAttributes) {
    this.networkAttributes = networkAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkAttributes")
  @NotNull
  public ReservedVirtualNetworkNetworkAttributes getNetworkAttributes() {
    return networkAttributes;
  }
  public void setNetworkAttributes(ReservedVirtualNetworkNetworkAttributes networkAttributes) {
    this.networkAttributes = networkAttributes;
  }

  /**
   * List of specific network ports that have been reserved.
   **/
  public ReservedVirtualNetwork networkPorts(List<ReservedVirtualNetworkNetworkPorts> networkPorts) {
    this.networkPorts = networkPorts;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of specific network ports that have been reserved.")
  @JsonProperty("networkPorts")
  @NotNull
  public List<ReservedVirtualNetworkNetworkPorts> getNetworkPorts() {
    return networkPorts;
  }
  public void setNetworkPorts(List<ReservedVirtualNetworkNetworkPorts> networkPorts) {
    this.networkPorts = networkPorts;
  }

  /**
   * List of public IP addresses that have been reserved.
   **/
  public ReservedVirtualNetwork publicIps(String publicIps) {
    this.publicIps = publicIps;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of public IP addresses that have been reserved.")
  @JsonProperty("publicIps")
  @NotNull
  public String getPublicIps() {
    return publicIps;
  }
  public void setPublicIps(String publicIps) {
    this.publicIps = publicIps;
  }

  /**
   * Identifier of the resource reservation.
   **/
  public ReservedVirtualNetwork reservationId(String reservationId) {
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
   * Status of the network resource reservation, e.g. to indicate if a reservation is being used.
   **/
  public ReservedVirtualNetwork reservationStatus(String reservationStatus) {
    this.reservationStatus = reservationStatus;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Status of the network resource reservation, e.g. to indicate if a reservation is being used.")
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
  public ReservedVirtualNetwork startTime(Date startTime) {
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
   * References the resource zone where the virtual network resources have been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.
   **/
  public ReservedVirtualNetwork zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "References the resource zone where the virtual network resources have been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.")
  @JsonProperty("zoneId")
  @NotNull
  public String getZoneId() {
    return zoneId;
  }
  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualNetwork reservedVirtualNetwork = (ReservedVirtualNetwork) o;
    return Objects.equals(endTime, reservedVirtualNetwork.endTime) &&
        Objects.equals(expiryTime, reservedVirtualNetwork.expiryTime) &&
        Objects.equals(networkAttributes, reservedVirtualNetwork.networkAttributes) &&
        Objects.equals(networkPorts, reservedVirtualNetwork.networkPorts) &&
        Objects.equals(publicIps, reservedVirtualNetwork.publicIps) &&
        Objects.equals(reservationId, reservedVirtualNetwork.reservationId) &&
        Objects.equals(reservationStatus, reservedVirtualNetwork.reservationStatus) &&
        Objects.equals(startTime, reservedVirtualNetwork.startTime) &&
        Objects.equals(zoneId, reservedVirtualNetwork.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endTime, expiryTime, networkAttributes, networkPorts, publicIps, reservationId, reservationStatus, startTime, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualNetwork {\n");
    
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    networkAttributes: ").append(toIndentedString(networkAttributes)).append("\n");
    sb.append("    networkPorts: ").append(toIndentedString(networkPorts)).append("\n");
    sb.append("    publicIps: ").append(toIndentedString(publicIps)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    reservationStatus: ").append(toIndentedString(reservationStatus)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
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

