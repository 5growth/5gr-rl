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
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;

/**
 * CreateNetworkResourceReservationRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-08T16:03:30.222Z")
public class CreateNetworkResourceReservationRequest {
  @SerializedName("affinityConstraint")
  private List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();

  @SerializedName("antiAffinityConstraint")
  private List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();

  @SerializedName("endTime")
  private OffsetDateTime endTime = null;

  @SerializedName("expiryTime")
  private OffsetDateTime expiryTime = null;

  @SerializedName("locationConstraints")
  private String locationConstraints = null;

  @SerializedName("networkReservation")
  private CreateNetworkResourceReservationRequestNetworkReservation networkReservation = null;

  @SerializedName("resourceGroupId")
  private String resourceGroupId = null;

  @SerializedName("startTime")
  private OffsetDateTime startTime = null;

  public CreateNetworkResourceReservationRequest affinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
    return this;
  }

  public CreateNetworkResourceReservationRequest addAffinityConstraintItem(AllocateComputeRequestAffinityOrAntiAffinityConstraints affinityConstraintItem) {
    this.affinityConstraint.add(affinityConstraintItem);
    return this;
  }

   /**
   * Element with anti-affinity information of the virtual network resources to reserve.
   * @return affinityConstraint
  **/
  @ApiModelProperty(required = true, value = "Element with anti-affinity information of the virtual network resources to reserve.")
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAffinityConstraint() {
    return affinityConstraint;
  }

  public void setAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
  }

  public CreateNetworkResourceReservationRequest antiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
    return this;
  }

  public CreateNetworkResourceReservationRequest addAntiAffinityConstraintItem(AllocateComputeRequestAffinityOrAntiAffinityConstraints antiAffinityConstraintItem) {
    this.antiAffinityConstraint.add(antiAffinityConstraintItem);
    return this;
  }

   /**
   * If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.
   * @return antiAffinityConstraint
  **/
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.")
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAntiAffinityConstraint() {
    return antiAffinityConstraint;
  }

  public void setAntiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
  }

  public CreateNetworkResourceReservationRequest endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   * @return endTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.")
  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public CreateNetworkResourceReservationRequest expiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
    return this;
  }

   /**
   * Indication when the VIM can release the reservation in case no allocation request against this reservation was made.
   * @return expiryTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the VIM can release the reservation in case no allocation request against this reservation was made.")
  public OffsetDateTime getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
  }

  public CreateNetworkResourceReservationRequest locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

   /**
   * If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.
   * @return locationConstraints
  **/
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.")
  public String getLocationConstraints() {
    return locationConstraints;
  }

  public void setLocationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
  }

  public CreateNetworkResourceReservationRequest networkReservation(CreateNetworkResourceReservationRequestNetworkReservation networkReservation) {
    this.networkReservation = networkReservation;
    return this;
  }

   /**
   * Get networkReservation
   * @return networkReservation
  **/
  @ApiModelProperty(required = true, value = "")
  public CreateNetworkResourceReservationRequestNetworkReservation getNetworkReservation() {
    return networkReservation;
  }

  public void setNetworkReservation(CreateNetworkResourceReservationRequestNetworkReservation networkReservation) {
    this.networkReservation = networkReservation;
  }

  public CreateNetworkResourceReservationRequest resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

   /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   * @return resourceGroupId
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
  public String getResourceGroupId() {
    return resourceGroupId;
  }

  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  public CreateNetworkResourceReservationRequest startTime(OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.
   * @return startTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.")
  public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
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
    return Objects.equals(this.affinityConstraint, createNetworkResourceReservationRequest.affinityConstraint) &&
        Objects.equals(this.antiAffinityConstraint, createNetworkResourceReservationRequest.antiAffinityConstraint) &&
        Objects.equals(this.endTime, createNetworkResourceReservationRequest.endTime) &&
        Objects.equals(this.expiryTime, createNetworkResourceReservationRequest.expiryTime) &&
        Objects.equals(this.locationConstraints, createNetworkResourceReservationRequest.locationConstraints) &&
        Objects.equals(this.networkReservation, createNetworkResourceReservationRequest.networkReservation) &&
        Objects.equals(this.resourceGroupId, createNetworkResourceReservationRequest.resourceGroupId) &&
        Objects.equals(this.startTime, createNetworkResourceReservationRequest.startTime);
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
