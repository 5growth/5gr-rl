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
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.rl.extinterface.nbi.swagger.model.QueryComputeCapacityRequestTimePeriod;
import java.io.IOException;

/**
 * QueryComputeCapacityRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class QueryComputeCapacityRequest {
  @SerializedName("attributeSelector")
  private String attributeSelector = null;

  @SerializedName("computeResourceTypeId")
  private String computeResourceTypeId = null;

  @SerializedName("resourceCriteria")
  private String resourceCriteria = null;

  @SerializedName("timePeriod")
  private QueryComputeCapacityRequestTimePeriod timePeriod = null;

  @SerializedName("zoneId")
  private String zoneId = null;

  public QueryComputeCapacityRequest attributeSelector(String attributeSelector) {
    this.attributeSelector = attributeSelector;
    return this;
  }

   /**
   * Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.
   * @return attributeSelector
  **/
  @ApiModelProperty(required = true, value = "Input parameter for selecting which capacity information (i.e. available, total, reserved and/or allocated capacity) is queried. When not present, all four values are requested.")
  public String getAttributeSelector() {
    return attributeSelector;
  }

  public void setAttributeSelector(String attributeSelector) {
    this.attributeSelector = attributeSelector;
  }

  public QueryComputeCapacityRequest computeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
    return this;
  }

   /**
   * Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.
   * @return computeResourceTypeId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the resource type for which the issuer wants to know the available, total, reserved and/or allocated capacity.")
  public String getComputeResourceTypeId() {
    return computeResourceTypeId;
  }

  public void setComputeResourceTypeId(String computeResourceTypeId) {
    this.computeResourceTypeId = computeResourceTypeId;
  }

  public QueryComputeCapacityRequest resourceCriteria(String resourceCriteria) {
    this.resourceCriteria = resourceCriteria;
    return this;
  }

   /**
   * Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualComputeResourceInformation, VirtualCpuResourceInformation, and VirtualMemoryResourceInformation information elements. This information element and the computeResourceTypeId are mutually exclusive.
   * @return resourceCriteria
  **/
  @ApiModelProperty(required = true, value = "Input capacity computation parameter for selecting the virtual memory, virtual CPU and acceleration capabilities for which the issuer wants to know the available, total, reserved and/or allocated capacity. Selecting parameters/attributes that shall be used are defined in the VirtualComputeResourceInformation, VirtualCpuResourceInformation, and VirtualMemoryResourceInformation information elements. This information element and the computeResourceTypeId are mutually exclusive.")
  public String getResourceCriteria() {
    return resourceCriteria;
  }

  public void setResourceCriteria(String resourceCriteria) {
    this.resourceCriteria = resourceCriteria;
  }

  public QueryComputeCapacityRequest timePeriod(QueryComputeCapacityRequestTimePeriod timePeriod) {
    this.timePeriod = timePeriod;
    return this;
  }

   /**
   * Get timePeriod
   * @return timePeriod
  **/
  @ApiModelProperty(required = true, value = "")
  public QueryComputeCapacityRequestTimePeriod getTimePeriod() {
    return timePeriod;
  }

  public void setTimePeriod(QueryComputeCapacityRequestTimePeriod timePeriod) {
    this.timePeriod = timePeriod;
  }

  public QueryComputeCapacityRequest zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

   /**
   * When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.
   * @return zoneId
  **/
  @ApiModelProperty(required = true, value = "When specified this parameter identifies the resource zone for which the capacity is requested. When not specified the total capacity managed by the VIM instance will be returned.")
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
    return Objects.equals(this.attributeSelector, queryComputeCapacityRequest.attributeSelector) &&
        Objects.equals(this.computeResourceTypeId, queryComputeCapacityRequest.computeResourceTypeId) &&
        Objects.equals(this.resourceCriteria, queryComputeCapacityRequest.resourceCriteria) &&
        Objects.equals(this.timePeriod, queryComputeCapacityRequest.timePeriod) &&
        Objects.equals(this.zoneId, queryComputeCapacityRequest.zoneId);
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

