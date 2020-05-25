package com.ericsson.radioplugin.nbi.swagger.model;

import com.ericsson.radioplugin.nbi.swagger.model.AllocateComputeRequestAffinityOrAntiAffinityConstraints;
import com.ericsson.radioplugin.nbi.swagger.model.CreateComputeResourceReservationRequestComputePoolReservation;
import com.ericsson.radioplugin.nbi.swagger.model.CreateComputeResourceReservationRequestVirtualisationContainerReservation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateComputeResourceReservationRequest   {
  
  private @Valid List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();
  private @Valid List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();
  private @Valid CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation = null;
  private @Valid Date endTime = null;
  private @Valid Date expiryTime = null;
  private @Valid String locationConstraints = null;
  private @Valid String resourceGroupId = null;
  private @Valid Date startTime = null;
  private @Valid List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation = new ArrayList<CreateComputeResourceReservationRequestVirtualisationContainerReservation>();

  /**
   * Element with affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the affinity information of the virtualisation container(s) to reserve.
   **/
  public CreateComputeResourceReservationRequest affinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the affinity information of the virtualisation container(s) to reserve.")
  @JsonProperty("affinityConstraint")
  @NotNull
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAffinityConstraint() {
    return affinityConstraint;
  }
  public void setAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
  }

  /**
   * Element with anti-affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the anti-affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the anti-affinity information of the virtualisation container(s) to reserve.
   **/
  public CreateComputeResourceReservationRequest antiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with anti-affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the anti-affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the anti-affinity information of the virtualisation container(s) to reserve.")
  @JsonProperty("antiAffinityConstraint")
  @NotNull
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAntiAffinityConstraint() {
    return antiAffinityConstraint;
  }
  public void setAntiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
  }

  /**
   **/
  public CreateComputeResourceReservationRequest computePoolReservation(CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation) {
    this.computePoolReservation = computePoolReservation;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("computePoolReservation")
  @NotNull
  public CreateComputeResourceReservationRequestComputePoolReservation getComputePoolReservation() {
    return computePoolReservation;
  }
  public void setComputePoolReservation(CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation) {
    this.computePoolReservation = computePoolReservation;
  }

  /**
   * Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   **/
  public CreateComputeResourceReservationRequest endTime(Date endTime) {
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
  public CreateComputeResourceReservationRequest expiryTime(Date expiryTime) {
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
  public CreateComputeResourceReservationRequest locationConstraints(String locationConstraints) {
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
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public CreateComputeResourceReservationRequest resourceGroupId(String resourceGroupId) {
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
  public CreateComputeResourceReservationRequest startTime(Date startTime) {
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
   * Virtualisation containers that need to be reserved (e.g. following a specific compute \&quot;flavour\&quot;)
   **/
  public CreateComputeResourceReservationRequest virtualisationContainerReservation(List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation) {
    this.virtualisationContainerReservation = virtualisationContainerReservation;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Virtualisation containers that need to be reserved (e.g. following a specific compute \"flavour\")")
  @JsonProperty("virtualisationContainerReservation")
  @NotNull
  public List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> getVirtualisationContainerReservation() {
    return virtualisationContainerReservation;
  }
  public void setVirtualisationContainerReservation(List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation) {
    this.virtualisationContainerReservation = virtualisationContainerReservation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequest createComputeResourceReservationRequest = (CreateComputeResourceReservationRequest) o;
    return Objects.equals(affinityConstraint, createComputeResourceReservationRequest.affinityConstraint) &&
        Objects.equals(antiAffinityConstraint, createComputeResourceReservationRequest.antiAffinityConstraint) &&
        Objects.equals(computePoolReservation, createComputeResourceReservationRequest.computePoolReservation) &&
        Objects.equals(endTime, createComputeResourceReservationRequest.endTime) &&
        Objects.equals(expiryTime, createComputeResourceReservationRequest.expiryTime) &&
        Objects.equals(locationConstraints, createComputeResourceReservationRequest.locationConstraints) &&
        Objects.equals(resourceGroupId, createComputeResourceReservationRequest.resourceGroupId) &&
        Objects.equals(startTime, createComputeResourceReservationRequest.startTime) &&
        Objects.equals(virtualisationContainerReservation, createComputeResourceReservationRequest.virtualisationContainerReservation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityConstraint, antiAffinityConstraint, computePoolReservation, endTime, expiryTime, locationConstraints, resourceGroupId, startTime, virtualisationContainerReservation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequest {\n");
    
    sb.append("    affinityConstraint: ").append(toIndentedString(affinityConstraint)).append("\n");
    sb.append("    antiAffinityConstraint: ").append(toIndentedString(antiAffinityConstraint)).append("\n");
    sb.append("    computePoolReservation: ").append(toIndentedString(computePoolReservation)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    virtualisationContainerReservation: ").append(toIndentedString(virtualisationContainerReservation)).append("\n");
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

