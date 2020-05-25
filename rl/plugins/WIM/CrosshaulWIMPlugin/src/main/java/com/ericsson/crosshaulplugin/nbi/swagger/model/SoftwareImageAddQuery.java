package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.MetaDataInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SoftwareImageAddQuery   {
  
  private @Valid String name = null;
  private @Valid String provider = null;
  private @Valid String resourceGroupId = null;
  private @Valid String softwareImage = null;
  private @Valid List<MetaDataInner> userMetadata = new ArrayList<MetaDataInner>();
  private @Valid String version = null;
  private @Valid String visibility = null;

  /**
   * The name of the software image.
   **/
  public SoftwareImageAddQuery name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The name of the software image.")
  @JsonProperty("name")
  @NotNull
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * The provider of the software image.
   **/
  public SoftwareImageAddQuery provider(String provider) {
    this.provider = provider;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The provider of the software image.")
  @JsonProperty("provider")
  @NotNull
  public String getProvider() {
    return provider;
  }
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   **/
  public SoftwareImageAddQuery resourceGroupId(String resourceGroupId) {
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
   * The binary software image file.
   **/
  public SoftwareImageAddQuery softwareImage(String softwareImage) {
    this.softwareImage = softwareImage;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The binary software image file.")
  @JsonProperty("softwareImage")
  @NotNull
  public String getSoftwareImage() {
    return softwareImage;
  }
  public void setSoftwareImage(String softwareImage) {
    this.softwareImage = softwareImage;
  }

  /**
   * User-defined metadata.
   **/
  public SoftwareImageAddQuery userMetadata(List<MetaDataInner> userMetadata) {
    this.userMetadata = userMetadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "User-defined metadata.")
  @JsonProperty("userMetadata")
  @NotNull
  public List<MetaDataInner> getUserMetadata() {
    return userMetadata;
  }
  public void setUserMetadata(List<MetaDataInner> userMetadata) {
    this.userMetadata = userMetadata;
  }

  /**
   * The version of the software image.
   **/
  public SoftwareImageAddQuery version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The version of the software image.")
  @JsonProperty("version")
  @NotNull
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image.
   **/
  public SoftwareImageAddQuery visibility(String visibility) {
    this.visibility = visibility;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Controls the visibility of the image. In case of \"private\" value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \"public\" value, all tenants of the VIM can use the image.")
  @JsonProperty("visibility")
  @NotNull
  public String getVisibility() {
    return visibility;
  }
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoftwareImageAddQuery softwareImageAddQuery = (SoftwareImageAddQuery) o;
    return Objects.equals(name, softwareImageAddQuery.name) &&
        Objects.equals(provider, softwareImageAddQuery.provider) &&
        Objects.equals(resourceGroupId, softwareImageAddQuery.resourceGroupId) &&
        Objects.equals(softwareImage, softwareImageAddQuery.softwareImage) &&
        Objects.equals(userMetadata, softwareImageAddQuery.userMetadata) &&
        Objects.equals(version, softwareImageAddQuery.version) &&
        Objects.equals(visibility, softwareImageAddQuery.visibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, provider, resourceGroupId, softwareImage, userMetadata, version, visibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoftwareImageAddQuery {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    softwareImage: ").append(toIndentedString(softwareImage)).append("\n");
    sb.append("    userMetadata: ").append(toIndentedString(userMetadata)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
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

