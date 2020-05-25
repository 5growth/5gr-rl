package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.QueryComputeCapacityRequestTimePeriod;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QueryNetworkCapacityRequest   {
  
  private @Valid String attributeSelector = null;
  private @Valid String networkResourceTypeId = null;
  private @Valid String resourceCriteria = null;
  private @Valid QueryComputeCapacityRequestTimePeriod timePeriod = null;
  private @Valid String zoneId = null;

  /**
   * Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.
   **/
  public QueryNetworkCapacityRequest attributeSelector(String attributeSelector) {
    this.attributeSelector = attributeSelector;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.")
  @JsonProperty("attributeSelector")
  @NotNull
  public String getAttributeSelector() {
    return attributeSelector;
  }
  public void setAttributeSelector(String attributeSelector) {
    this.attributeSelector = attributeSelector;
  }

  /**
   * Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.
   **/
  public QueryNetworkCapacityRequest networkResourceTypeId(String networkResourceTypeId) {
    this.networkResourceTypeId = networkResourceTypeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.")
  @JsonProperty("networkResourceTypeId")
  @NotNull
  public String getNetworkResourceTypeId() {
    return networkResourceTypeId;
  }
  public void setNetworkResourceTypeId(String networkResourceTypeId) {
    this.networkResourceTypeId = networkResourceTypeId;
  }

  /**
   * Input capacity computation parameter for selecting the characteristics of the virtual network for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualNetworkResourceInformation information element. This information element and the networkResourceTypeID are mutually exclusive.
   **/
  public QueryNetworkCapacityRequest resourceCriteria(String resourceCriteria) {
    this.resourceCriteria = resourceCriteria;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Input capacity computation parameter for selecting the characteristics of the virtual network for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualNetworkResourceInformation information element. This information element and the networkResourceTypeID are mutually exclusive.")
  @JsonProperty("resourceCriteria")
  @NotNull
  public String getResourceCriteria() {
    return resourceCriteria;
  }
  public void setResourceCriteria(String resourceCriteria) {
    this.resourceCriteria = resourceCriteria;
  }

  /**
   **/
  public QueryNetworkCapacityRequest timePeriod(QueryComputeCapacityRequestTimePeriod timePeriod) {
    this.timePeriod = timePeriod;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("timePeriod")
  @NotNull
  public QueryComputeCapacityRequestTimePeriod getTimePeriod() {
    return timePeriod;
  }
  public void setTimePeriod(QueryComputeCapacityRequestTimePeriod timePeriod) {
    this.timePeriod = timePeriod;
  }

  /**
   * When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.
   **/
  public QueryNetworkCapacityRequest zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.")
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
    QueryNetworkCapacityRequest queryNetworkCapacityRequest = (QueryNetworkCapacityRequest) o;
    return Objects.equals(attributeSelector, queryNetworkCapacityRequest.attributeSelector) &&
        Objects.equals(networkResourceTypeId, queryNetworkCapacityRequest.networkResourceTypeId) &&
        Objects.equals(resourceCriteria, queryNetworkCapacityRequest.resourceCriteria) &&
        Objects.equals(timePeriod, queryNetworkCapacityRequest.timePeriod) &&
        Objects.equals(zoneId, queryNetworkCapacityRequest.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributeSelector, networkResourceTypeId, resourceCriteria, timePeriod, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryNetworkCapacityRequest {\n");
    
    sb.append("    attributeSelector: ").append(toIndentedString(attributeSelector)).append("\n");
    sb.append("    networkResourceTypeId: ").append(toIndentedString(networkResourceTypeId)).append("\n");
    sb.append("    resourceCriteria: ").append(toIndentedString(resourceCriteria)).append("\n");
    sb.append("    timePeriod: ").append(toIndentedString(timePeriod)).append("\n");
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

