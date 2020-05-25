package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesCpuResourceAttributes;
import com.ericsson.crosshaulplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesMemoryResourceAttributes;
import com.ericsson.crosshaulplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesStorageResourceAttributes;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NfviPopsInnerNfviPopAttributesResourceZoneAttributes   {
  
  private @Valid String zoneId = null;
  private @Valid String zoneName = null;
  private @Valid String zoneState = null;
  private @Valid String zoneProperty = null;
  private @Valid String metadata = null;
  private @Valid NfviPopsInnerNfviPopAttributesMemoryResourceAttributes memoryResourceAttributes = null;
  private @Valid NfviPopsInnerNfviPopAttributesCpuResourceAttributes cpuResourceAttributes = null;
  private @Valid NfviPopsInnerNfviPopAttributesStorageResourceAttributes storageResourceAttributes = null;

  /**
   * The identifier of the Resource Zone.
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the Resource Zone.")
  @JsonProperty("zoneId")
  @NotNull
  public String getZoneId() {
    return zoneId;
  }
  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  /**
   * The name of the Resource Zone.
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes zoneName(String zoneName) {
    this.zoneName = zoneName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The name of the Resource Zone.")
  @JsonProperty("zoneName")
  @NotNull
  public String getZoneName() {
    return zoneName;
  }
  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  /**
   * Information about the current state of the Resource Zone, e.g. if the Resource Zone is available.
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes zoneState(String zoneState) {
    this.zoneState = zoneState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Information about the current state of the Resource Zone, e.g. if the Resource Zone is available.")
  @JsonProperty("zoneState")
  @NotNull
  public String getZoneState() {
    return zoneState;
  }
  public void setZoneState(String zoneState) {
    this.zoneState = zoneState;
  }

  /**
   * Set of properties that define the capabilities associated to the Resource Zone. Examples of capabilities may include: support of certain compute resource types (e.g. low performance, acceleration capabilities, etc. association to certain NFVI-PoP physical segregation (e.g. different power or network sub-systems, availability of redundancy power sub-systems), etc.
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes zoneProperty(String zoneProperty) {
    this.zoneProperty = zoneProperty;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Set of properties that define the capabilities associated to the Resource Zone. Examples of capabilities may include: support of certain compute resource types (e.g. low performance, acceleration capabilities, etc. association to certain NFVI-PoP physical segregation (e.g. different power or network sub-systems, availability of redundancy power sub-systems), etc.")
  @JsonProperty("zoneProperty")
  @NotNull
  public String getZoneProperty() {
    return zoneProperty;
  }
  public void setZoneProperty(String zoneProperty) {
    this.zoneProperty = zoneProperty;
  }

  /**
   * List of metadata key-value pairs used to associate meaningful metadata to the NFVI-PoP Zone.
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes metadata(String metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of metadata key-value pairs used to associate meaningful metadata to the NFVI-PoP Zone.")
  @JsonProperty("metadata")
  @NotNull
  public String getMetadata() {
    return metadata;
  }
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes memoryResourceAttributes(NfviPopsInnerNfviPopAttributesMemoryResourceAttributes memoryResourceAttributes) {
    this.memoryResourceAttributes = memoryResourceAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("memoryResourceAttributes")
  @NotNull
  public NfviPopsInnerNfviPopAttributesMemoryResourceAttributes getMemoryResourceAttributes() {
    return memoryResourceAttributes;
  }
  public void setMemoryResourceAttributes(NfviPopsInnerNfviPopAttributesMemoryResourceAttributes memoryResourceAttributes) {
    this.memoryResourceAttributes = memoryResourceAttributes;
  }

  /**
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes cpuResourceAttributes(NfviPopsInnerNfviPopAttributesCpuResourceAttributes cpuResourceAttributes) {
    this.cpuResourceAttributes = cpuResourceAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("cpuResourceAttributes")
  @NotNull
  public NfviPopsInnerNfviPopAttributesCpuResourceAttributes getCpuResourceAttributes() {
    return cpuResourceAttributes;
  }
  public void setCpuResourceAttributes(NfviPopsInnerNfviPopAttributesCpuResourceAttributes cpuResourceAttributes) {
    this.cpuResourceAttributes = cpuResourceAttributes;
  }

  /**
   **/
  public NfviPopsInnerNfviPopAttributesResourceZoneAttributes storageResourceAttributes(NfviPopsInnerNfviPopAttributesStorageResourceAttributes storageResourceAttributes) {
    this.storageResourceAttributes = storageResourceAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("storageResourceAttributes")
  @NotNull
  public NfviPopsInnerNfviPopAttributesStorageResourceAttributes getStorageResourceAttributes() {
    return storageResourceAttributes;
  }
  public void setStorageResourceAttributes(NfviPopsInnerNfviPopAttributesStorageResourceAttributes storageResourceAttributes) {
    this.storageResourceAttributes = storageResourceAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributesResourceZoneAttributes nfviPopsInnerNfviPopAttributesResourceZoneAttributes = (NfviPopsInnerNfviPopAttributesResourceZoneAttributes) o;
    return Objects.equals(zoneId, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.zoneId) &&
        Objects.equals(zoneName, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.zoneName) &&
        Objects.equals(zoneState, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.zoneState) &&
        Objects.equals(zoneProperty, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.zoneProperty) &&
        Objects.equals(metadata, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.metadata) &&
        Objects.equals(memoryResourceAttributes, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.memoryResourceAttributes) &&
        Objects.equals(cpuResourceAttributes, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.cpuResourceAttributes) &&
        Objects.equals(storageResourceAttributes, nfviPopsInnerNfviPopAttributesResourceZoneAttributes.storageResourceAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneId, zoneName, zoneState, zoneProperty, metadata, memoryResourceAttributes, cpuResourceAttributes, storageResourceAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesResourceZoneAttributes {\n");
    
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    zoneName: ").append(toIndentedString(zoneName)).append("\n");
    sb.append("    zoneState: ").append(toIndentedString(zoneState)).append("\n");
    sb.append("    zoneProperty: ").append(toIndentedString(zoneProperty)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    memoryResourceAttributes: ").append(toIndentedString(memoryResourceAttributes)).append("\n");
    sb.append("    cpuResourceAttributes: ").append(toIndentedString(cpuResourceAttributes)).append("\n");
    sb.append("    storageResourceAttributes: ").append(toIndentedString(storageResourceAttributes)).append("\n");
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

