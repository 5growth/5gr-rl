package com.ericsson.xenplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class MECTrafficServiceCreationRequest   {
  
  private @Valid String requestId = null;
  private @Valid String regionId = null;
  private @Valid List<ServiceDependency> appServiceRequired = new ArrayList<ServiceDependency>();
  private @Valid List<ServiceDependency> appServiceOptional = new ArrayList<ServiceDependency>();
  private @Valid List<ServiceDescriptor> appServiceProduced = new ArrayList<ServiceDescriptor>();
  private @Valid List<TransportDependency> transportDependencies = new ArrayList<TransportDependency>();
  private @Valid List<TrafficRuleDescriptor> appTrafficRule = new ArrayList<TrafficRuleDescriptor>();
  private @Valid List<DNSRuleDescriptor> appDNSRule = new ArrayList<DNSRuleDescriptor>();
  private @Valid LatencyDescriptor appLatency = null;

public enum StatusEnum {

    CREATED(String.valueOf("CREATED")), FAILED(String.valueOf("FAILED")), PENDING(String.valueOf("PENDING")), DELETED(String.valueOf("DELETED"));


    private String value;

    StatusEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String v) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid StatusEnum status = null;
  private @Valid String createdAt = null;
  private @Valid String lastModified = null;

  /**
   * Identifier of the request, assigned by the MEC plugin at creation time.
   **/
  public MECTrafficServiceCreationRequest requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the request, assigned by the MEC plugin at creation time.")
  @JsonProperty("requestId")
  public String getRequestId() {
    return requestId;
  }
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  /**
   * Identifier of the region where the MEC app will be instantiated.
   **/
  public MECTrafficServiceCreationRequest regionId(String regionId) {
    this.regionId = regionId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the region where the MEC app will be instantiated.")
  @JsonProperty("regionId")
  @NotNull
  public String getRegionId() {
    return regionId;
  }
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  /**
   * Describes services a mobile edge application requires to run.
   **/
  public MECTrafficServiceCreationRequest appServiceRequired(List<ServiceDependency> appServiceRequired) {
    this.appServiceRequired = appServiceRequired;
    return this;
  }

  
  @ApiModelProperty(value = "Describes services a mobile edge application requires to run.")
  @JsonProperty("appServiceRequired")
  public List<ServiceDependency> getAppServiceRequired() {
    return appServiceRequired;
  }
  public void setAppServiceRequired(List<ServiceDependency> appServiceRequired) {
    this.appServiceRequired = appServiceRequired;
  }

  /**
   * Describes services a mobile edge application may use if available.
   **/
  public MECTrafficServiceCreationRequest appServiceOptional(List<ServiceDependency> appServiceOptional) {
    this.appServiceOptional = appServiceOptional;
    return this;
  }

  
  @ApiModelProperty(value = "Describes services a mobile edge application may use if available.")
  @JsonProperty("appServiceOptional")
  public List<ServiceDependency> getAppServiceOptional() {
    return appServiceOptional;
  }
  public void setAppServiceOptional(List<ServiceDependency> appServiceOptional) {
    this.appServiceOptional = appServiceOptional;
  }

  /**
   * Describes services a mobile edge application is able to produce to the platform or other mobile edge applications. Only relevant for service-producing apps.
   **/
  public MECTrafficServiceCreationRequest appServiceProduced(List<ServiceDescriptor> appServiceProduced) {
    this.appServiceProduced = appServiceProduced;
    return this;
  }

  
  @ApiModelProperty(value = "Describes services a mobile edge application is able to produce to the platform or other mobile edge applications. Only relevant for service-producing apps.")
  @JsonProperty("appServiceProduced")
  public List<ServiceDescriptor> getAppServiceProduced() {
    return appServiceProduced;
  }
  public void setAppServiceProduced(List<ServiceDescriptor> appServiceProduced) {
    this.appServiceProduced = appServiceProduced;
  }

  /**
   * Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.
   **/
  public MECTrafficServiceCreationRequest transportDependencies(List<TransportDependency> transportDependencies) {
    this.transportDependencies = transportDependencies;
    return this;
  }

  
  @ApiModelProperty(value = "Transports, if any, that this application requires to be provided by the platform. These transports will be used by the application to deliver services provided by this application. Only relevant for service-producing apps.")
  @JsonProperty("transportDependencies")
  public List<TransportDependency> getTransportDependencies() {
    return transportDependencies;
  }
  public void setTransportDependencies(List<TransportDependency> transportDependencies) {
    this.transportDependencies = transportDependencies;
  }

  /**
   * Describes traffic rules the mobile edge application requires.
   **/
  public MECTrafficServiceCreationRequest appTrafficRule(List<TrafficRuleDescriptor> appTrafficRule) {
    this.appTrafficRule = appTrafficRule;
    return this;
  }

  
  @ApiModelProperty(value = "Describes traffic rules the mobile edge application requires.")
  @JsonProperty("appTrafficRule")
  public List<TrafficRuleDescriptor> getAppTrafficRule() {
    return appTrafficRule;
  }
  public void setAppTrafficRule(List<TrafficRuleDescriptor> appTrafficRule) {
    this.appTrafficRule = appTrafficRule;
  }

  /**
   * Describes DNS rules the mobile edge application requires.
   **/
  public MECTrafficServiceCreationRequest appDNSRule(List<DNSRuleDescriptor> appDNSRule) {
    this.appDNSRule = appDNSRule;
    return this;
  }

  
  @ApiModelProperty(value = "Describes DNS rules the mobile edge application requires.")
  @JsonProperty("appDNSRule")
  public List<DNSRuleDescriptor> getAppDNSRule() {
    return appDNSRule;
  }
  public void setAppDNSRule(List<DNSRuleDescriptor> appDNSRule) {
    this.appDNSRule = appDNSRule;
  }

  /**
   **/
  public MECTrafficServiceCreationRequest appLatency(LatencyDescriptor appLatency) {
    this.appLatency = appLatency;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("appLatency")
  public LatencyDescriptor getAppLatency() {
    return appLatency;
  }
  public void setAppLatency(LatencyDescriptor appLatency) {
    this.appLatency = appLatency;
  }

  /**
   * Status of the request.
   **/
  public MECTrafficServiceCreationRequest status(StatusEnum status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(value = "Status of the request.")
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   * Timestamp of when service request was created.
   **/
  public MECTrafficServiceCreationRequest createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  
  @ApiModelProperty(value = "Timestamp of when service request was created.")
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Timestamp of when service was last modified (e.g., deleted).
   **/
  public MECTrafficServiceCreationRequest lastModified(String lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  
  @ApiModelProperty(value = "Timestamp of when service was last modified (e.g., deleted).")
  @JsonProperty("lastModified")
  public String getLastModified() {
    return lastModified;
  }
  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECTrafficServiceCreationRequest meCTrafficServiceCreationRequest = (MECTrafficServiceCreationRequest) o;
    return Objects.equals(requestId, meCTrafficServiceCreationRequest.requestId) &&
        Objects.equals(regionId, meCTrafficServiceCreationRequest.regionId) &&
        Objects.equals(appServiceRequired, meCTrafficServiceCreationRequest.appServiceRequired) &&
        Objects.equals(appServiceOptional, meCTrafficServiceCreationRequest.appServiceOptional) &&
        Objects.equals(appServiceProduced, meCTrafficServiceCreationRequest.appServiceProduced) &&
        Objects.equals(transportDependencies, meCTrafficServiceCreationRequest.transportDependencies) &&
        Objects.equals(appTrafficRule, meCTrafficServiceCreationRequest.appTrafficRule) &&
        Objects.equals(appDNSRule, meCTrafficServiceCreationRequest.appDNSRule) &&
        Objects.equals(appLatency, meCTrafficServiceCreationRequest.appLatency) &&
        Objects.equals(status, meCTrafficServiceCreationRequest.status) &&
        Objects.equals(createdAt, meCTrafficServiceCreationRequest.createdAt) &&
        Objects.equals(lastModified, meCTrafficServiceCreationRequest.lastModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, regionId, appServiceRequired, appServiceOptional, appServiceProduced, transportDependencies, appTrafficRule, appDNSRule, appLatency, status, createdAt, lastModified);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECTrafficServiceCreationRequest {\n");
    
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    regionId: ").append(toIndentedString(regionId)).append("\n");
    sb.append("    appServiceRequired: ").append(toIndentedString(appServiceRequired)).append("\n");
    sb.append("    appServiceOptional: ").append(toIndentedString(appServiceOptional)).append("\n");
    sb.append("    appServiceProduced: ").append(toIndentedString(appServiceProduced)).append("\n");
    sb.append("    transportDependencies: ").append(toIndentedString(transportDependencies)).append("\n");
    sb.append("    appTrafficRule: ").append(toIndentedString(appTrafficRule)).append("\n");
    sb.append("    appDNSRule: ").append(toIndentedString(appDNSRule)).append("\n");
    sb.append("    appLatency: ").append(toIndentedString(appLatency)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
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

