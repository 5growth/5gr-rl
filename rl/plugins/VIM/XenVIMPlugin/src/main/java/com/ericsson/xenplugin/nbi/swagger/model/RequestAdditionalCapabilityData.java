package com.ericsson.xenplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RequestAdditionalCapabilityData   {
  
  private @Valid String requestedAdditionalCapabilityName = null;
  private @Valid Boolean supportMandatory = null;
  private @Valid String minRequestedAdditionalCapabilityVersion = null;
  private @Valid String preferredRequestedAdditionalCapabilityVersion = null;
  private @Valid Object targetPerformanceParameters = null;

  /**
   * Identifies a requested additional capability for the VDU. ETSI GS NFV-IFA 002 describes acceleration capabilities.
   **/
  public RequestAdditionalCapabilityData requestedAdditionalCapabilityName(String requestedAdditionalCapabilityName) {
    this.requestedAdditionalCapabilityName = requestedAdditionalCapabilityName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifies a requested additional capability for the VDU. ETSI GS NFV-IFA 002 describes acceleration capabilities.")
  @JsonProperty("requestedAdditionalCapabilityName")
  @NotNull
  public String getRequestedAdditionalCapabilityName() {
    return requestedAdditionalCapabilityName;
  }
  public void setRequestedAdditionalCapabilityName(String requestedAdditionalCapabilityName) {
    this.requestedAdditionalCapabilityName = requestedAdditionalCapabilityName;
  }

  /**
   * Indicates whether the requested additional capability is mandatory for successful operation.
   **/
  public RequestAdditionalCapabilityData supportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
    return this;
  }

  
  @ApiModelProperty(value = "Indicates whether the requested additional capability is mandatory for successful operation.")
  @JsonProperty("supportMandatory")
  public Boolean isSupportMandatory() {
    return supportMandatory;
  }
  public void setSupportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
  }

  /**
   * Identifies the minimum version of the requested additional capability.
   **/
  public RequestAdditionalCapabilityData minRequestedAdditionalCapabilityVersion(String minRequestedAdditionalCapabilityVersion) {
    this.minRequestedAdditionalCapabilityVersion = minRequestedAdditionalCapabilityVersion;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the minimum version of the requested additional capability.")
  @JsonProperty("minRequestedAdditionalCapabilityVersion")
  public String getMinRequestedAdditionalCapabilityVersion() {
    return minRequestedAdditionalCapabilityVersion;
  }
  public void setMinRequestedAdditionalCapabilityVersion(String minRequestedAdditionalCapabilityVersion) {
    this.minRequestedAdditionalCapabilityVersion = minRequestedAdditionalCapabilityVersion;
  }

  /**
   * Identifies the preferred version of the requested additional capability.
   **/
  public RequestAdditionalCapabilityData preferredRequestedAdditionalCapabilityVersion(String preferredRequestedAdditionalCapabilityVersion) {
    this.preferredRequestedAdditionalCapabilityVersion = preferredRequestedAdditionalCapabilityVersion;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the preferred version of the requested additional capability.")
  @JsonProperty("preferredRequestedAdditionalCapabilityVersion")
  public String getPreferredRequestedAdditionalCapabilityVersion() {
    return preferredRequestedAdditionalCapabilityVersion;
  }
  public void setPreferredRequestedAdditionalCapabilityVersion(String preferredRequestedAdditionalCapabilityVersion) {
    this.preferredRequestedAdditionalCapabilityVersion = preferredRequestedAdditionalCapabilityVersion;
  }

  /**
   * Identifies specific attributes, dependent on the requested additional capability type.
   **/
  public RequestAdditionalCapabilityData targetPerformanceParameters(Object targetPerformanceParameters) {
    this.targetPerformanceParameters = targetPerformanceParameters;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies specific attributes, dependent on the requested additional capability type.")
  @JsonProperty("targetPerformanceParameters")
  public Object getTargetPerformanceParameters() {
    return targetPerformanceParameters;
  }
  public void setTargetPerformanceParameters(Object targetPerformanceParameters) {
    this.targetPerformanceParameters = targetPerformanceParameters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestAdditionalCapabilityData requestAdditionalCapabilityData = (RequestAdditionalCapabilityData) o;
    return Objects.equals(requestedAdditionalCapabilityName, requestAdditionalCapabilityData.requestedAdditionalCapabilityName) &&
        Objects.equals(supportMandatory, requestAdditionalCapabilityData.supportMandatory) &&
        Objects.equals(minRequestedAdditionalCapabilityVersion, requestAdditionalCapabilityData.minRequestedAdditionalCapabilityVersion) &&
        Objects.equals(preferredRequestedAdditionalCapabilityVersion, requestAdditionalCapabilityData.preferredRequestedAdditionalCapabilityVersion) &&
        Objects.equals(targetPerformanceParameters, requestAdditionalCapabilityData.targetPerformanceParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestedAdditionalCapabilityName, supportMandatory, minRequestedAdditionalCapabilityVersion, preferredRequestedAdditionalCapabilityVersion, targetPerformanceParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestAdditionalCapabilityData {\n");
    
    sb.append("    requestedAdditionalCapabilityName: ").append(toIndentedString(requestedAdditionalCapabilityName)).append("\n");
    sb.append("    supportMandatory: ").append(toIndentedString(supportMandatory)).append("\n");
    sb.append("    minRequestedAdditionalCapabilityVersion: ").append(toIndentedString(minRequestedAdditionalCapabilityVersion)).append("\n");
    sb.append("    preferredRequestedAdditionalCapabilityVersion: ").append(toIndentedString(preferredRequestedAdditionalCapabilityVersion)).append("\n");
    sb.append("    targetPerformanceParameters: ").append(toIndentedString(targetPerformanceParameters)).append("\n");
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

