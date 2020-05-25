package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * geographical location info covered by radio region
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "geographical location info covered by radio region")

public class NfviPopsInnerNfviPopAttributesCovrageLocationInfo   {
  
  private @Valid Double latitude = null;
  private @Valid Double longitude = null;
  private @Valid Float altitude = null;
  private @Valid Float range = null;

  /**
   * latitude of location Info.
   **/
  public NfviPopsInnerNfviPopAttributesCovrageLocationInfo latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "latitude of location Info.")
  @JsonProperty("latitude")
  @NotNull
  public Double getLatitude() {
    return latitude;
  }
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  /**
   * longitude of location Info.
   **/
  public NfviPopsInnerNfviPopAttributesCovrageLocationInfo longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "longitude of location Info.")
  @JsonProperty("longitude")
  @NotNull
  public Double getLongitude() {
    return longitude;
  }
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  /**
   * altitude of location Info.
   **/
  public NfviPopsInnerNfviPopAttributesCovrageLocationInfo altitude(Float altitude) {
    this.altitude = altitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "altitude of location Info.")
  @JsonProperty("altitude")
  @NotNull
  public Float getAltitude() {
    return altitude;
  }
  public void setAltitude(Float altitude) {
    this.altitude = altitude;
  }

  /**
   * radius of location Info.
   **/
  public NfviPopsInnerNfviPopAttributesCovrageLocationInfo range(Float range) {
    this.range = range;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "radius of location Info.")
  @JsonProperty("range")
  @NotNull
  public Float getRange() {
    return range;
  }
  public void setRange(Float range) {
    this.range = range;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributesCovrageLocationInfo nfviPopsInnerNfviPopAttributesCovrageLocationInfo = (NfviPopsInnerNfviPopAttributesCovrageLocationInfo) o;
    return Objects.equals(latitude, nfviPopsInnerNfviPopAttributesCovrageLocationInfo.latitude) &&
        Objects.equals(longitude, nfviPopsInnerNfviPopAttributesCovrageLocationInfo.longitude) &&
        Objects.equals(altitude, nfviPopsInnerNfviPopAttributesCovrageLocationInfo.altitude) &&
        Objects.equals(range, nfviPopsInnerNfviPopAttributesCovrageLocationInfo.range);
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude, altitude, range);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesCovrageLocationInfo {\n");
    
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    altitude: ").append(toIndentedString(altitude)).append("\n");
    sb.append("    range: ").append(toIndentedString(range)).append("\n");
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

