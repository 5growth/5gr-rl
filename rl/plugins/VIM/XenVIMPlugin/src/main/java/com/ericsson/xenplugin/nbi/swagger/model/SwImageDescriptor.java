package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Describes the software image which is directly loaded on the virtualisation machine instantiating this Application.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Describes the software image which is directly loaded on the virtualisation machine instantiating this Application.")

public class SwImageDescriptor   {
  
  private @Valid String id = null;
  private @Valid String name = null;
  private @Valid String version = null;
  private @Valid String checksum = null;
  private @Valid String containerFormat = null;
  private @Valid String diskFormat = null;
  private @Valid BigDecimal minDisk = null;
  private @Valid BigDecimal minRam = null;
  private @Valid BigDecimal size = null;
  private @Valid String swImage = null;
  private @Valid String operatingSystem = null;
  private @Valid List<String> supportedVirtualizationEnvironment = new ArrayList<String>();

  /**
   * The identifier of this software image.
   **/
  public SwImageDescriptor id(String id) {
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
   * The name of this software image.
   **/
  public SwImageDescriptor name(String name) {
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
   * The version of this software image.
   **/
  public SwImageDescriptor version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(value = "The version of this software image.")
  @JsonProperty("version")
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * The checksum of this software image
   **/
  public SwImageDescriptor checksum(String checksum) {
    this.checksum = checksum;
    return this;
  }

  
  @ApiModelProperty(value = "The checksum of this software image")
  @JsonProperty("checksum")
  public String getChecksum() {
    return checksum;
  }
  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  /**
   * The container format describes the container file format in which software image is provided.
   **/
  public SwImageDescriptor containerFormat(String containerFormat) {
    this.containerFormat = containerFormat;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The container format describes the container file format in which software image is provided.")
  @JsonProperty("containerFormat")
  @NotNull
  public String getContainerFormat() {
    return containerFormat;
  }
  public void setContainerFormat(String containerFormat) {
    this.containerFormat = containerFormat;
  }

  /**
   * The disk format of a software image is the format of the underlying disk image.
   **/
  public SwImageDescriptor diskFormat(String diskFormat) {
    this.diskFormat = diskFormat;
    return this;
  }

  
  @ApiModelProperty(value = "The disk format of a software image is the format of the underlying disk image.")
  @JsonProperty("diskFormat")
  public String getDiskFormat() {
    return diskFormat;
  }
  public void setDiskFormat(String diskFormat) {
    this.diskFormat = diskFormat;
  }

  /**
   * The minimal disk size requirement for this software image. The value of the \&quot;size of storage\&quot; attribute of the VirtualStorageDesc referencing this SwImageDesc shall not be smaller than the value of minDisk.
   **/
  public SwImageDescriptor minDisk(BigDecimal minDisk) {
    this.minDisk = minDisk;
    return this;
  }

  
  @ApiModelProperty(value = "The minimal disk size requirement for this software image. The value of the \"size of storage\" attribute of the VirtualStorageDesc referencing this SwImageDesc shall not be smaller than the value of minDisk.")
  @JsonProperty("minDisk")
  public BigDecimal getMinDisk() {
    return minDisk;
  }
  public void setMinDisk(BigDecimal minDisk) {
    this.minDisk = minDisk;
  }

  /**
   * The minimal RAM requirement for this software image. The value of the \&quot;size\&quot; attribute of VirtualMemoryData of the Vdu referencing this SwImageDesc shall not be smaller than the value of minRam.
   **/
  public SwImageDescriptor minRam(BigDecimal minRam) {
    this.minRam = minRam;
    return this;
  }

  
  @ApiModelProperty(value = "The minimal RAM requirement for this software image. The value of the \"size\" attribute of VirtualMemoryData of the Vdu referencing this SwImageDesc shall not be smaller than the value of minRam.")
  @JsonProperty("minRam")
  public BigDecimal getMinRam() {
    return minRam;
  }
  public void setMinRam(BigDecimal minRam) {
    this.minRam = minRam;
  }

  /**
   * The size of this software image.
   **/
  public SwImageDescriptor size(BigDecimal size) {
    this.size = size;
    return this;
  }

  
  @ApiModelProperty(value = "The size of this software image.")
  @JsonProperty("size")
  public BigDecimal getSize() {
    return size;
  }
  public void setSize(BigDecimal size) {
    this.size = size;
  }

  /**
   * This is a reference to the actual software image. The reference can be relative to the root of the VNF Package or can be a URL.
   **/
  public SwImageDescriptor swImage(String swImage) {
    this.swImage = swImage;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "This is a reference to the actual software image. The reference can be relative to the root of the VNF Package or can be a URL.")
  @JsonProperty("swImage")
  @NotNull
  public String getSwImage() {
    return swImage;
  }
  public void setSwImage(String swImage) {
    this.swImage = swImage;
  }

  /**
   * Identifies the operating system used in the software image. This attribute may also identify if a 32 bit or 64 bit software image is used.
   **/
  public SwImageDescriptor operatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the operating system used in the software image. This attribute may also identify if a 32 bit or 64 bit software image is used.")
  @JsonProperty("operatingSystem")
  public String getOperatingSystem() {
    return operatingSystem;
  }
  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  /**
   * Identifies the virtualisation environments (e.g. hypervisor) compatible with this software image.
   **/
  public SwImageDescriptor supportedVirtualizationEnvironment(List<String> supportedVirtualizationEnvironment) {
    this.supportedVirtualizationEnvironment = supportedVirtualizationEnvironment;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the virtualisation environments (e.g. hypervisor) compatible with this software image.")
  @JsonProperty("supportedVirtualizationEnvironment")
  public List<String> getSupportedVirtualizationEnvironment() {
    return supportedVirtualizationEnvironment;
  }
  public void setSupportedVirtualizationEnvironment(List<String> supportedVirtualizationEnvironment) {
    this.supportedVirtualizationEnvironment = supportedVirtualizationEnvironment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SwImageDescriptor swImageDescriptor = (SwImageDescriptor) o;
    return Objects.equals(id, swImageDescriptor.id) &&
        Objects.equals(name, swImageDescriptor.name) &&
        Objects.equals(version, swImageDescriptor.version) &&
        Objects.equals(checksum, swImageDescriptor.checksum) &&
        Objects.equals(containerFormat, swImageDescriptor.containerFormat) &&
        Objects.equals(diskFormat, swImageDescriptor.diskFormat) &&
        Objects.equals(minDisk, swImageDescriptor.minDisk) &&
        Objects.equals(minRam, swImageDescriptor.minRam) &&
        Objects.equals(size, swImageDescriptor.size) &&
        Objects.equals(swImage, swImageDescriptor.swImage) &&
        Objects.equals(operatingSystem, swImageDescriptor.operatingSystem) &&
        Objects.equals(supportedVirtualizationEnvironment, swImageDescriptor.supportedVirtualizationEnvironment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, version, checksum, containerFormat, diskFormat, minDisk, minRam, size, swImage, operatingSystem, supportedVirtualizationEnvironment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SwImageDescriptor {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
    sb.append("    containerFormat: ").append(toIndentedString(containerFormat)).append("\n");
    sb.append("    diskFormat: ").append(toIndentedString(diskFormat)).append("\n");
    sb.append("    minDisk: ").append(toIndentedString(minDisk)).append("\n");
    sb.append("    minRam: ").append(toIndentedString(minRam)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    swImage: ").append(toIndentedString(swImage)).append("\n");
    sb.append("    operatingSystem: ").append(toIndentedString(operatingSystem)).append("\n");
    sb.append("    supportedVirtualizationEnvironment: ").append(toIndentedString(supportedVirtualizationEnvironment)).append("\n");
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

