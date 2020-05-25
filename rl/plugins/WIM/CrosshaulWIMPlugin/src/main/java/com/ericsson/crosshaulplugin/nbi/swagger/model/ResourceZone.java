package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.MetaDataInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ResourceZone   {
  
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String nfviPopId = null;
  private @Valid String zoneId = null;
  private @Valid String zoneName = null;
  private @Valid String zoneProperty = null;
  private @Valid String zoneState = null;

  /**
   * Other metadata associated to the Resource Zone.
   **/
  public ResourceZone metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(value = "Other metadata associated to the Resource Zone.")
  @JsonProperty("metadata")
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }
  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  /**
   * The identifier of the NFVI-PoP the Resource Zone belongs to.
   **/
  public ResourceZone nfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the NFVI-PoP the Resource Zone belongs to.")
  @JsonProperty("nfviPopId")
  @NotNull
  public String getNfviPopId() {
    return nfviPopId;
  }
  public void setNfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
  }

  /**
   * The identifier of the Resource Zone.
   **/
  public ResourceZone zoneId(String zoneId) {
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
  public ResourceZone zoneName(String zoneName) {
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
   * Set of properties that define the capabilities associated to the Resource Zone. Examples of capabilities may include: support of certain compute resource types (e.g. low performance, acceleration capabilities, etc. association to certain NFVI-PoP physical segregation (e.g. different power or network sub-systems, availability of redundancy power sub-systems), etc.
   **/
  public ResourceZone zoneProperty(String zoneProperty) {
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
   * Information about the current state of the Resource Zone, e.g. if the Resource Zone is available.
   **/
  public ResourceZone zoneState(String zoneState) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceZone resourceZone = (ResourceZone) o;
    return Objects.equals(metadata, resourceZone.metadata) &&
        Objects.equals(nfviPopId, resourceZone.nfviPopId) &&
        Objects.equals(zoneId, resourceZone.zoneId) &&
        Objects.equals(zoneName, resourceZone.zoneName) &&
        Objects.equals(zoneProperty, resourceZone.zoneProperty) &&
        Objects.equals(zoneState, resourceZone.zoneState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, nfviPopId, zoneId, zoneName, zoneProperty, zoneState);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceZone {\n");
    
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    nfviPopId: ").append(toIndentedString(nfviPopId)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    zoneName: ").append(toIndentedString(zoneName)).append("\n");
    sb.append("    zoneProperty: ").append(toIndentedString(zoneProperty)).append("\n");
    sb.append("    zoneState: ").append(toIndentedString(zoneState)).append("\n");
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

