package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.AllocateComputeRequestAffinityOrAntiAffinityConstraints;
import com.sssa.wimplugin.nbi.swagger.model.CreateNetworkResourceReservationRequestNetworkReservation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateNetworkResourceReservationRequest   {
  
  private @Valid List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();
  private @Valid List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();
  private @Valid Date endTime = null;
  private @Valid Date expiryTime = null;
  private @Valid String locationConstraints = null;
  private @Valid CreateNetworkResourceReservationRequestNetworkReservation networkReservation = null;
  private @Valid String resourceGroupId = null;
  private @Valid Date startTime = null;

  /**
   * Element with anti-affinity information of the virtual network resources to reserve.
   **/
  public CreateNetworkResourceReservationRequest affinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with anti-affinity information of the virtual network resources to reserve.")
  @JsonProperty("affinityConstraint")
  @NotNull
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAffinityConstraint() {
    return affinityConstraint;
  }
  public void setAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
  }

  /**
   * If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.
   **/
  public CreateNetworkResourceReservationRequest antiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.")
  @JsonProperty("antiAffinityConstraint")
  @NotNull
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAntiAffinityConstraint() {
    return antiAffinityConstraint;
  }
  public void setAntiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
  }

  /**
   * Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   **/
  public CreateNetworkResourceReservationRequest endTime(Date endTime) {
    this.endTime = endTime;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.")
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
  public CreateNetworkResourceReservationRequest expiryTime(Date expiryTime) {
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
   * If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.
   **/
  public CreateNetworkResourceReservationRequest locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.")
  @JsonProperty("locationConstraints")
  @NotNull
  public String getLocationConstraints() {
    return locationConstraints;
  }
  public void setLocationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
  }

  /**
   **/
  public CreateNetworkResourceReservationRequest networkReservation(CreateNetworkResourceReservationRequestNetworkReservation networkReservation) {
    this.networkReservation = networkReservation;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkReservation")
  @NotNull
  public CreateNetworkResourceReservationRequestNetworkReservation getNetworkReservation() {
    return networkReservation;
  }
  public void setNetworkReservation(CreateNetworkResourceReservationRequestNetworkReservation networkReservation) {
    this.networkReservation = networkReservation;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public CreateNetworkResourceReservationRequest resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
  @JsonProperty("resourceGroupId")
  @NotNull
  public String getResourceGroupId() {
    return resourceGroupId;
  }
  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  /**
   * Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.
   **/
  public CreateNetworkResourceReservationRequest startTime(Date startTime) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNetworkResourceReservationRequest createNetworkResourceReservationRequest = (CreateNetworkResourceReservationRequest) o;
    return Objects.equals(affinityConstraint, createNetworkResourceReservationRequest.affinityConstraint) &&
        Objects.equals(antiAffinityConstraint, createNetworkResourceReservationRequest.antiAffinityConstraint) &&
        Objects.equals(endTime, createNetworkResourceReservationRequest.endTime) &&
        Objects.equals(expiryTime, createNetworkResourceReservationRequest.expiryTime) &&
        Objects.equals(locationConstraints, createNetworkResourceReservationRequest.locationConstraints) &&
        Objects.equals(networkReservation, createNetworkResourceReservationRequest.networkReservation) &&
        Objects.equals(resourceGroupId, createNetworkResourceReservationRequest.resourceGroupId) &&
        Objects.equals(startTime, createNetworkResourceReservationRequest.startTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityConstraint, antiAffinityConstraint, endTime, expiryTime, locationConstraints, networkReservation, resourceGroupId, startTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceReservationRequest {\n");
    
    sb.append("    affinityConstraint: ").append(toIndentedString(affinityConstraint)).append("\n");
    sb.append("    antiAffinityConstraint: ").append(toIndentedString(antiAffinityConstraint)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    networkReservation: ").append(toIndentedString(networkReservation)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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

