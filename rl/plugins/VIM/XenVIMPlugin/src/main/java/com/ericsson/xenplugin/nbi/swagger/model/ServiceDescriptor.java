package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.CategoryRef;
import com.ericsson.xenplugin.nbi.swagger.model.ServiceDescriptorTransportsSupported;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ServiceDescriptor   {
  
  private @Valid String serName = null;
  private @Valid CategoryRef serCategory = null;
  private @Valid String version = null;
  private @Valid ServiceDescriptorTransportsSupported transportsSupported = null;

  /**
   * The name of the service, for example, RNIS, LocationService, etc.
   **/
  public ServiceDescriptor serName(String serName) {
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
  public ServiceDescriptor serCategory(CategoryRef serCategory) {
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
  public ServiceDescriptor version(String version) {
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
   **/
  public ServiceDescriptor transportsSupported(ServiceDescriptorTransportsSupported transportsSupported) {
    this.transportsSupported = transportsSupported;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("transportsSupported")
  public ServiceDescriptorTransportsSupported getTransportsSupported() {
    return transportsSupported;
  }
  public void setTransportsSupported(ServiceDescriptorTransportsSupported transportsSupported) {
    this.transportsSupported = transportsSupported;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceDescriptor serviceDescriptor = (ServiceDescriptor) o;
    return Objects.equals(serName, serviceDescriptor.serName) &&
        Objects.equals(serCategory, serviceDescriptor.serCategory) &&
        Objects.equals(version, serviceDescriptor.version) &&
        Objects.equals(transportsSupported, serviceDescriptor.transportsSupported);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serName, serCategory, version, transportsSupported);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceDescriptor {\n");
    
    sb.append("    serName: ").append(toIndentedString(serName)).append("\n");
    sb.append("    serCategory: ").append(toIndentedString(serCategory)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    transportsSupported: ").append(toIndentedString(transportsSupported)).append("\n");
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

