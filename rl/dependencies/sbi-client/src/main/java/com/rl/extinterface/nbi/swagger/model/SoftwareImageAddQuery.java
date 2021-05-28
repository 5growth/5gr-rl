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
import com.rl.extinterface.nbi.swagger.model.MetaData;
import java.io.IOException;

/**
 * SoftwareImageAddQuery
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class SoftwareImageAddQuery {
  @SerializedName("name")
  private String name = null;

  @SerializedName("provider")
  private String provider = null;

  @SerializedName("resourceGroupId")
  private String resourceGroupId = null;

  @SerializedName("softwareImage")
  private String softwareImage = null;

  @SerializedName("userMetadata")
  private MetaData userMetadata = null;

  @SerializedName("version")
  private String version = null;

  @SerializedName("visibility")
  private String visibility = null;

  public SoftwareImageAddQuery name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the software image.
   * @return name
  **/
  @ApiModelProperty(required = true, value = "The name of the software image.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SoftwareImageAddQuery provider(String provider) {
    this.provider = provider;
    return this;
  }

   /**
   * The provider of the software image.
   * @return provider
  **/
  @ApiModelProperty(required = true, value = "The provider of the software image.")
  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public SoftwareImageAddQuery resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

   /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   * @return resourceGroupId
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
  public String getResourceGroupId() {
    return resourceGroupId;
  }

  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  public SoftwareImageAddQuery softwareImage(String softwareImage) {
    this.softwareImage = softwareImage;
    return this;
  }

   /**
   * The binary software image file.
   * @return softwareImage
  **/
  @ApiModelProperty(required = true, value = "The binary software image file.")
  public String getSoftwareImage() {
    return softwareImage;
  }

  public void setSoftwareImage(String softwareImage) {
    this.softwareImage = softwareImage;
  }

  public SoftwareImageAddQuery userMetadata(MetaData userMetadata) {
    this.userMetadata = userMetadata;
    return this;
  }

   /**
   * Get userMetadata
   * @return userMetadata
  **/
  @ApiModelProperty(required = true, value = "")
  public MetaData getUserMetadata() {
    return userMetadata;
  }

  public void setUserMetadata(MetaData userMetadata) {
    this.userMetadata = userMetadata;
  }

  public SoftwareImageAddQuery version(String version) {
    this.version = version;
    return this;
  }

   /**
   * The version of the software image.
   * @return version
  **/
  @ApiModelProperty(required = true, value = "The version of the software image.")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public SoftwareImageAddQuery visibility(String visibility) {
    this.visibility = visibility;
    return this;
  }

   /**
   * Controls the visibility of the image. In case of \&quot;private\&quot; value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \&quot;public\&quot; value, all tenants of the VIM can use the image.
   * @return visibility
  **/
  @ApiModelProperty(required = true, value = "Controls the visibility of the image. In case of \"private\" value the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM while in case of \"public\" value, all tenants of the VIM can use the image.")
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
    return Objects.equals(this.name, softwareImageAddQuery.name) &&
        Objects.equals(this.provider, softwareImageAddQuery.provider) &&
        Objects.equals(this.resourceGroupId, softwareImageAddQuery.resourceGroupId) &&
        Objects.equals(this.softwareImage, softwareImageAddQuery.softwareImage) &&
        Objects.equals(this.userMetadata, softwareImageAddQuery.userMetadata) &&
        Objects.equals(this.version, softwareImageAddQuery.version) &&
        Objects.equals(this.visibility, softwareImageAddQuery.visibility);
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

