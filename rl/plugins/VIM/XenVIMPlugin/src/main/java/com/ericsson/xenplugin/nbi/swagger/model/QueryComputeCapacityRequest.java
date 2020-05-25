package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.QueryComputeCapacityRequestTimePeriod;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QueryComputeCapacityRequest   {
  
  private @Valid String attributeSelector = null;
  private @Valid String computeResourceTypeId = null;
  private @Valid String resourceCriteria = null;
  private @Valid QueryComputeCapacityRequestTimePeriod timePeriod = null;
  private @Valid String zoneId = null;

  /**
   * Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.
   **/
  public QueryComputeCapacityRequest attributeSelector(String attributeSelector) {
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
  public QueryComputeCapacityRequest computeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.")
  @JsonProperty("computeResourceTypeId")
  @NotNull
  public String getComputeResourceTypeId() {
    return computeResourceTypeId;
  }
  public void setComputeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
  }

  /**
   * Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualComputeResourceInformation, VirtualCpuResourceInformation, and VirtualMemoryResourceInformation information elements. This information element and the computeResourceTypeId are mutually exclusive.
   **/
  public QueryComputeCapacityRequest resourceCriteria(String resourceCriteria) {
    this.resourceCriteria = resourceCriteria;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualComputeResourceInformation, VirtualCpuResourceInformation, and VirtualMemoryResourceInformation information elements. This information element and the computeResourceTypeId are mutually exclusive.")
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
  public QueryComputeCapacityRequest timePeriod(QueryComputeCapacityRequestTimePeriod timePeriod) {
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
  public QueryComputeCapacityRequest zoneId(String zoneId) {
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
    QueryComputeCapacityRequest queryComputeCapacityRequest = (QueryComputeCapacityRequest) o;
    return Objects.equals(attributeSelector, queryComputeCapacityRequest.attributeSelector) &&
        Objects.equals(computeResourceTypeId, queryComputeCapacityRequest.computeResourceTypeId) &&
        Objects.equals(resourceCriteria, queryComputeCapacityRequest.resourceCriteria) &&
        Objects.equals(timePeriod, queryComputeCapacityRequest.timePeriod) &&
        Objects.equals(zoneId, queryComputeCapacityRequest.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributeSelector, computeResourceTypeId, resourceCriteria, timePeriod, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryComputeCapacityRequest {\n");
    
    sb.append("    attributeSelector: ").append(toIndentedString(attributeSelector)).append("\n");
    sb.append("    computeResourceTypeId: ").append(toIndentedString(computeResourceTypeId)).append("\n");
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

