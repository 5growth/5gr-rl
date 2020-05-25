package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.CategoryRef;
import com.ericsson.dummyplugin.nbi.swagger.model.TransportDependency;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ServiceDependency   {
  
  private @Valid String serName = null;
  private @Valid CategoryRef serCategory = null;
  private @Valid String version = null;
  private @Valid List<TransportDependency> serTransportDependencies = new ArrayList<TransportDependency>();
  private @Valid Object requestedPermissions = null;

  /**
   * The name of the service, for example, RNIS, LocationService, etc.
   **/
  public ServiceDependency serName(String serName) {
    this.serName = serName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The name of the service, for example, RNIS, LocationService, etc.")
  @JsonProperty("serName")
  @NotNull
  public String getSerName() {
    return serName;
  }
  public void setSerName(String serName) {
    this.serName = serName;
  }

  /**
   **/
  public ServiceDependency serCategory(CategoryRef serCategory) {
    this.serCategory = serCategory;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("serCategory")
  public CategoryRef getSerCategory() {
    return serCategory;
  }
  public void setSerCategory(CategoryRef serCategory) {
    this.serCategory = serCategory;
  }

  /**
   * The version of the service.
   **/
  public ServiceDependency version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The version of the service.")
  @JsonProperty("version")
  @NotNull
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Indicates transport and serialization format dependencies of consuming the service. Defaults to REST + JSON if absent. This attribute indicates groups of transport bindings that a service-consuming ME application supports for the consumption of the ME service defined by this ServiceDependency structure. If at leastone of the indicated groups is supported by the service it may be consumed by the application.
   **/
  public ServiceDependency serTransportDependencies(List<TransportDependency> serTransportDependencies) {
    this.serTransportDependencies = serTransportDependencies;
    return this;
  }

  
  @ApiModelProperty(value = "Indicates transport and serialization format dependencies of consuming the service. Defaults to REST + JSON if absent. This attribute indicates groups of transport bindings that a service-consuming ME application supports for the consumption of the ME service defined by this ServiceDependency structure. If at leastone of the indicated groups is supported by the service it may be consumed by the application.")
  @JsonProperty("serTransportDependencies")
  public List<TransportDependency> getSerTransportDependencies() {
    return serTransportDependencies;
  }
  public void setSerTransportDependencies(List<TransportDependency> serTransportDependencies) {
    this.serTransportDependencies = serTransportDependencies;
  }

  /**
   * Requested permissions regarding the access of the application to the service. See clause 8.2 of ETSI GS MEC 009. The format of this attribute is left for the data model design stage.
   **/
  public ServiceDependency requestedPermissions(Object requestedPermissions) {
    this.requestedPermissions = requestedPermissions;
    return this;
  }

  
  @ApiModelProperty(value = "Requested permissions regarding the access of the application to the service. See clause 8.2 of ETSI GS MEC 009. The format of this attribute is left for the data model design stage.")
  @JsonProperty("requestedPermissions")
  public Object getRequestedPermissions() {
    return requestedPermissions;
  }
  public void setRequestedPermissions(Object requestedPermissions) {
    this.requestedPermissions = requestedPermissions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceDependency serviceDependency = (ServiceDependency) o;
    return Objects.equals(serName, serviceDependency.serName) &&
        Objects.equals(serCategory, serviceDependency.serCategory) &&
        Objects.equals(version, serviceDependency.version) &&
        Objects.equals(serTransportDependencies, serviceDependency.serTransportDependencies) &&
        Objects.equals(requestedPermissions, serviceDependency.requestedPermissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serName, serCategory, version, serTransportDependencies, requestedPermissions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceDependency {\n");
    
    sb.append("    serName: ").append(toIndentedString(serName)).append("\n");
    sb.append("    serCategory: ").append(toIndentedString(serCategory)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    serTransportDependencies: ").append(toIndentedString(serTransportDependencies)).append("\n");
    sb.append("    requestedPermissions: ").append(toIndentedString(requestedPermissions)).append("\n");
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

