package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.MetaDataInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateNetworkRequest   {
  
  private @Valid String affinityOrAntiAffinityConstraints = null;
  private @Valid String locationConstraints = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String networkResourceName = null;
  private @Valid String networkResourceType = null;
  private @Valid String reservationId = null;
  private @Valid String resourceGroupId = null;
  private @Valid String typeNetworkData = null;
  private @Valid String typeNetworkPortData = null;
  private @Valid String typeSubnetData = null;

  /**
   * The binary software image file.
   **/
  public AllocateNetworkRequest affinityOrAntiAffinityConstraints(String affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("affinityOrAntiAffinityConstraints")
  @NotNull
  public String getAffinityOrAntiAffinityConstraints() {
    return affinityOrAntiAffinityConstraints;
  }
  public void setAffinityOrAntiAffinityConstraints(String affinityOrAntiAffinityConstraints) {
    this.affinityOrAntiAffinityConstraints = affinityOrAntiAffinityConstraints;
  }

  /**
   * Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image.
   **/
  public AllocateNetworkRequest locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Controls the visibility of the image. In case of \"private\" value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \"public\" value, all tenants of the VIM can use the image.")
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
  public AllocateNetworkRequest metadata(List<MetaDataInner> metadata) {
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
   * Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side.
   **/
  public AllocateNetworkRequest networkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name provided by the consumer for the virtualised network resource to allocate. It can be used for identifying resources from consumer side.")
  @JsonProperty("networkResourceName")
  @NotNull
  public String getNetworkResourceName() {
    return networkResourceName;
  }
  public void setNetworkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
  }

  /**
   * Type of virtualised network resource. Possible values are: \&quot;network\&quot;, \&quot;subnet\&quot; or network-port.
   **/
  public AllocateNetworkRequest networkResourceType(String networkResourceType) {
    this.networkResourceType = networkResourceType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of virtualised network resource. Possible values are: \"network\", \"subnet\" or network-port.")
  @JsonProperty("networkResourceType")
  @NotNull
  public String getNetworkResourceType() {
    return networkResourceType;
  }
  public void setNetworkResourceType(String networkResourceType) {
    this.networkResourceType = networkResourceType;
  }

  /**
   * Identifier of the resource reservation applicable to this virtualised resource management operation.
   **/
  public AllocateNetworkRequest reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource reservation applicable to this virtualised resource management operation.")
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
  public AllocateNetworkRequest resourceGroupId(String resourceGroupId) {
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
   * The network data provides information about the particular virtual network resource to create. Cardinality can be \&quot;0\&quot; depending on the value of networkResourceType.
   **/
  public AllocateNetworkRequest typeNetworkData(String typeNetworkData) {
    this.typeNetworkData = typeNetworkData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The network data provides information about the particular virtual network resource to create. Cardinality can be \"0\" depending on the value of networkResourceType.")
  @JsonProperty("typeNetworkData")
  @NotNull
  public String getTypeNetworkData() {
    return typeNetworkData;
  }
  public void setTypeNetworkData(String typeNetworkData) {
    this.typeNetworkData = typeNetworkData;
  }

  /**
   * The binary software image file.
   **/
  public AllocateNetworkRequest typeNetworkPortData(String typeNetworkPortData) {
    this.typeNetworkPortData = typeNetworkPortData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("typeNetworkPortData")
  @NotNull
  public String getTypeNetworkPortData() {
    return typeNetworkPortData;
  }
  public void setTypeNetworkPortData(String typeNetworkPortData) {
    this.typeNetworkPortData = typeNetworkPortData;
  }

  /**
   * The binary software image file.
   **/
  public AllocateNetworkRequest typeSubnetData(String typeSubnetData) {
    this.typeSubnetData = typeSubnetData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("typeSubnetData")
  @NotNull
  public String getTypeSubnetData() {
    return typeSubnetData;
  }
  public void setTypeSubnetData(String typeSubnetData) {
    this.typeSubnetData = typeSubnetData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkRequest allocateNetworkRequest = (AllocateNetworkRequest) o;
    return Objects.equals(affinityOrAntiAffinityConstraints, allocateNetworkRequest.affinityOrAntiAffinityConstraints) &&
        Objects.equals(locationConstraints, allocateNetworkRequest.locationConstraints) &&
        Objects.equals(metadata, allocateNetworkRequest.metadata) &&
        Objects.equals(networkResourceName, allocateNetworkRequest.networkResourceName) &&
        Objects.equals(networkResourceType, allocateNetworkRequest.networkResourceType) &&
        Objects.equals(reservationId, allocateNetworkRequest.reservationId) &&
        Objects.equals(resourceGroupId, allocateNetworkRequest.resourceGroupId) &&
        Objects.equals(typeNetworkData, allocateNetworkRequest.typeNetworkData) &&
        Objects.equals(typeNetworkPortData, allocateNetworkRequest.typeNetworkPortData) &&
        Objects.equals(typeSubnetData, allocateNetworkRequest.typeSubnetData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityOrAntiAffinityConstraints, locationConstraints, metadata, networkResourceName, networkResourceType, reservationId, resourceGroupId, typeNetworkData, typeNetworkPortData, typeSubnetData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkRequest {\n");
    
    sb.append("    affinityOrAntiAffinityConstraints: ").append(toIndentedString(affinityOrAntiAffinityConstraints)).append("\n");
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkResourceName: ").append(toIndentedString(networkResourceName)).append("\n");
    sb.append("    networkResourceType: ").append(toIndentedString(networkResourceType)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    typeNetworkData: ").append(toIndentedString(typeNetworkData)).append("\n");
    sb.append("    typeNetworkPortData: ").append(toIndentedString(typeNetworkPortData)).append("\n");
    sb.append("    typeSubnetData: ").append(toIndentedString(typeSubnetData)).append("\n");
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

