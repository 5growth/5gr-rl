package com.ericsson.crosshaulplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SoftwareImageInformation   {
  
  private @Valid String checksum = null;
  private @Valid String containerFormat = null;
  private @Valid String createdAt = null;
  private @Valid String diskFormat = null;
  private @Valid String id = null;
  private @Valid String minDisk = null;
  private @Valid String minRam = null;
  private @Valid String name = null;
  private @Valid String provider = null;
  private @Valid String size = null;
  private @Valid String status = null;
  private @Valid String updatedAt = null;
  private @Valid String version = null;

  /**
   * The checksum of the software image file.
   **/
  public SoftwareImageInformation checksum(String checksum) {
    this.checksum = checksum;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The checksum of the software image file.")
  @JsonProperty("checksum")
  @NotNull
  public String getChecksum() {
    return checksum;
  }
  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  /**
   * The container format indicates whether the software image is in a file format that also contains metadata about the actual software.
   **/
  public SoftwareImageInformation containerFormat(String containerFormat) {
    this.containerFormat = containerFormat;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The container format indicates whether the software image is in a file format that also contains metadata about the actual software.")
  @JsonProperty("containerFormat")
  @NotNull
  public String getContainerFormat() {
    return containerFormat;
  }
  public void setContainerFormat(String containerFormat) {
    this.containerFormat = containerFormat;
  }

  /**
   * The created time of this software image.
   **/
  public SoftwareImageInformation createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The created time of this software image.")
  @JsonProperty("createdAt")
  @NotNull
  public String getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * The disk format of a software image is the format of the underlying disk image.
   **/
  public SoftwareImageInformation diskFormat(String diskFormat) {
    this.diskFormat = diskFormat;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The disk format of a software image is the format of the underlying disk image.")
  @JsonProperty("diskFormat")
  @NotNull
  public String getDiskFormat() {
    return diskFormat;
  }
  public void setDiskFormat(String diskFormat) {
    this.diskFormat = diskFormat;
  }

  /**
   * The identifier of this software image.
   **/
  public SoftwareImageInformation id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of this software image.")
  @JsonProperty("id")
  @NotNull
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * The minimal Disk for this software image.
   **/
  public SoftwareImageInformation minDisk(String minDisk) {
    this.minDisk = minDisk;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The minimal Disk for this software image.")
  @JsonProperty("minDisk")
  @NotNull
  public String getMinDisk() {
    return minDisk;
  }
  public void setMinDisk(String minDisk) {
    this.minDisk = minDisk;
  }

  /**
   * The minimal RAM for this software image.
   **/
  public SoftwareImageInformation minRam(String minRam) {
    this.minRam = minRam;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The minimal RAM for this software image.")
  @JsonProperty("minRam")
  @NotNull
  public String getMinRam() {
    return minRam;
  }
  public void setMinRam(String minRam) {
    this.minRam = minRam;
  }

  /**
   * The name of this software image.
   **/
  public SoftwareImageInformation name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The name of this software image.")
  @JsonProperty("name")
  @NotNull
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * The provider of this software image.
   **/
  public SoftwareImageInformation provider(String provider) {
    this.provider = provider;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The provider of this software image.")
  @JsonProperty("provider")
  @NotNull
  public String getProvider() {
    return provider;
  }
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /**
   * The size of this software image.
   **/
  public SoftwareImageInformation size(String size) {
    this.size = size;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The size of this software image.")
  @JsonProperty("size")
  @NotNull
  public String getSize() {
    return size;
  }
  public void setSize(String size) {
    this.size = size;
  }

  /**
   * The status of this software image.
   **/
  public SoftwareImageInformation status(String status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The status of this software image.")
  @JsonProperty("status")
  @NotNull
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * The updated time of this software image.
   **/
  public SoftwareImageInformation updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The updated time of this software image.")
  @JsonProperty("updatedAt")
  @NotNull
  public String getUpdatedAt() {
    return updatedAt;
  }
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * The version of this software image.
   **/
  public SoftwareImageInformation version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The version of this software image.")
  @JsonProperty("version")
  @NotNull
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoftwareImageInformation softwareImageInformation = (SoftwareImageInformation) o;
    return Objects.equals(checksum, softwareImageInformation.checksum) &&
        Objects.equals(containerFormat, softwareImageInformation.containerFormat) &&
        Objects.equals(createdAt, softwareImageInformation.createdAt) &&
        Objects.equals(diskFormat, softwareImageInformation.diskFormat) &&
        Objects.equals(id, softwareImageInformation.id) &&
        Objects.equals(minDisk, softwareImageInformation.minDisk) &&
        Objects.equals(minRam, softwareImageInformation.minRam) &&
        Objects.equals(name, softwareImageInformation.name) &&
        Objects.equals(provider, softwareImageInformation.provider) &&
        Objects.equals(size, softwareImageInformation.size) &&
        Objects.equals(status, softwareImageInformation.status) &&
        Objects.equals(updatedAt, softwareImageInformation.updatedAt) &&
        Objects.equals(version, softwareImageInformation.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checksum, containerFormat, createdAt, diskFormat, id, minDisk, minRam, name, provider, size, status, updatedAt, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoftwareImageInformation {\n");
    
    sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
    sb.append("    containerFormat: ").append(toIndentedString(containerFormat)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    diskFormat: ").append(toIndentedString(diskFormat)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    minDisk: ").append(toIndentedString(minDisk)).append("\n");
    sb.append("    minRam: ").append(toIndentedString(minRam)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

