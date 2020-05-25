package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.AllocateComputeRequestAffinityOrAntiAffinityConstraints;
import com.sssa.wimplugin.nbi.swagger.model.AllocateComputeRequestInterfaceData;
import com.sssa.wimplugin.nbi.swagger.model.AllocateComputeRequestUserData;
import com.sssa.wimplugin.nbi.swagger.model.MetaDataInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateComputeRequest   {
  
  private @Valid List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityOrAntiAffinityConstraints = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();
  private @Valid String computeFlavourId = null;
  private @Valid String computeName = null;
  private @Valid List<AllocateComputeRequestInterfaceData> interfaceData = new ArrayList<AllocateComputeRequestInterfaceData>();
  private @Valid String locationConstraints = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String reservationId = null;
  private @Valid String resourceGroupId = null;
  private @Valid AllocateComputeRequestUserData userData = null;
  private @Valid String vcImageId = null;

  /**
   * A list of elements with affinity or anti affinity information of the virtualised compute resource to allocate. There should be a relation between the constraints defined in the different elements of the list.
   **/
  public AllocateComputeRequest affinityOrAntiAffinityConstraints(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "A list of elements with affinity or anti affinity information of the virtualised compute resource to allocate. There should be a relation between the constraints defined in the different elements of the list.")
  @JsonProperty("affinityOrAntiAffinityConstraints")
  @NotNull
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAffinityOrAntiAffinityConstraints() {
    return affinityOrAntiAffinityConstraints;
  }
  public void setAffinityOrAntiAffinityConstraints(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
  }

  /**
   * Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate. The Compute Flavour is created with Create Compute Flavour operation
   **/
  public AllocateComputeRequest computeFlavourId(String computeFlavourId) {
    this.computeFlavourId = computeFlavourId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the Compute Flavour that provides information about the particular memory, CPU and disk resources for virtualised compute resource to allocate. The Compute Flavour is created with Create Compute Flavour operation")
  @JsonProperty("computeFlavourId")
  @NotNull
  public String getComputeFlavourId() {
    return computeFlavourId;
  }
  public void setComputeFlavourId(String computeFlavourId) {
    this.computeFlavourId = computeFlavourId;
  }

  /**
   * Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.
   **/
  public AllocateComputeRequest computeName(String computeName) {
    this.computeName = computeName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name provided by the consumer for the virtualised compute resource to allocate. It can be used for identifying resources from consumer side.")
  @JsonProperty("computeName")
  @NotNull
  public String getComputeName() {
    return computeName;
  }
  public void setComputeName(String computeName) {
    this.computeName = computeName;
  }

  /**
   * The data of network interfaces which are specific to a Virtual Compute Resource instance.
   **/
  public AllocateComputeRequest interfaceData(List<AllocateComputeRequestInterfaceData> interfaceData) {
    this.interfaceData = interfaceData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The data of network interfaces which are specific to a Virtual Compute Resource instance.")
  @JsonProperty("interfaceData")
  @NotNull
  public List<AllocateComputeRequestInterfaceData> getInterfaceData() {
    return interfaceData;
  }
  public void setInterfaceData(List<AllocateComputeRequestInterfaceData> interfaceData) {
    this.interfaceData = interfaceData;
  }

  /**
   * If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone.
   **/
  public AllocateComputeRequest locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be allocated, e.g. in what particular Resource Zone.")
  @JsonProperty("locationConstraints")
  @NotNull
  public String getLocationConstraints() {
    return locationConstraints;
  }
  public void setLocationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
  }

  /**
   * The binary software image file.
   **/
  public AllocateComputeRequest metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("metadata")
  @NotNull
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }
  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  /**
   * Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.
   **/
  public AllocateComputeRequest reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource reservation applicable to this virtualised resource management operation. Cardinality can be 0 if no resource reservation was used.")
  @JsonProperty("reservationId")
  @NotNull
  public String getReservationId() {
    return reservationId;
  }
  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public AllocateComputeRequest resourceGroupId(String resourceGroupId) {
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
   **/
  public AllocateComputeRequest userData(AllocateComputeRequestUserData userData) {
    this.userData = userData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("userData")
  @NotNull
  public AllocateComputeRequestUserData getUserData() {
    return userData;
  }
  public void setUserData(AllocateComputeRequestUserData userData) {
    this.userData = userData;
  }

  /**
   * Identifier of the virtualisation container software image (e.g. a virtual machine image). Cardinality can be 0 if an \&quot;empty\&quot; virtualisation container is allocated. 
   **/
  public AllocateComputeRequest vcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualisation container software image (e.g. a virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated. ")
  @JsonProperty("vcImageId")
  @NotNull
  public String getVcImageId() {
    return vcImageId;
  }
  public void setVcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequest allocateComputeRequest = (AllocateComputeRequest) o;
    return Objects.equals(affinityOrAntiAffinityConstraints, allocateComputeRequest.affinityOrAntiAffinityConstraints) &&
        Objects.equals(computeFlavourId, allocateComputeRequest.computeFlavourId) &&
        Objects.equals(computeName, allocateComputeRequest.computeName) &&
        Objects.equals(interfaceData, allocateComputeRequest.interfaceData) &&
        Objects.equals(locationConstraints, allocateComputeRequest.locationConstraints) &&
        Objects.equals(metadata, allocateComputeRequest.metadata) &&
        Objects.equals(reservationId, allocateComputeRequest.reservationId) &&
        Objects.equals(resourceGroupId, allocateComputeRequest.resourceGroupId) &&
        Objects.equals(userData, allocateComputeRequest.userData) &&
        Objects.equals(vcImageId, allocateComputeRequest.vcImageId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityConstraints, computeFlavourId, computeName, interfaceData, locationConstraints, metadata, reservationId, resourceGroupId, userData, vcImageId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequest {\n");
    
    sb.append("    affinityOrAntiAffinityConstraints: ").append(toIndentedString(affinityOrAntiAffinityConstraints)).append("\n");
    sb.append("    computeFlavourId: ").append(toIndentedString(computeFlavourId)).append("\n");
    sb.append("    computeName: ").append(toIndentedString(computeName)).append("\n");
    sb.append("    interfaceData: ").append(toIndentedString(interfaceData)).append("\n");
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    userData: ").append(toIndentedString(userData)).append("\n");
    sb.append("    vcImageId: ").append(toIndentedString(vcImageId)).append("\n");
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

