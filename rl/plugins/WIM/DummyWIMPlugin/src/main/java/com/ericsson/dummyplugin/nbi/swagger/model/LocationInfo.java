package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Geographical location info of a region, expressed as a circle centered at specific coordinates and with a specified range.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Geographical location info of a region, expressed as a circle centered at specific coordinates and with a specified range.")

public class LocationInfo   {
  
  private @Valid BigDecimal latitude = null;
  private @Valid BigDecimal longitude = null;
  private @Valid BigDecimal altitude = null;
  private @Valid BigDecimal range = null;

  /**
   * Latitude coordinate
   **/
  public LocationInfo latitude(BigDecimal latitude) {
    this.latitude = latitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Latitude coordinate")
  @JsonProperty("latitude")
  @NotNull
  public BigDecimal getLatitude() {
    return latitude;
  }
  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  /**
   * Longitude coordinate
   **/
  public LocationInfo longitude(BigDecimal longitude) {
    this.longitude = longitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Longitude coordinate")
  @JsonProperty("longitude")
  @NotNull
  public BigDecimal getLongitude() {
    return longitude;
  }
  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  /**
   * Altitude coordinate
   **/
  public LocationInfo altitude(BigDecimal altitude) {
    this.altitude = altitude;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Altitude coordinate")
  @JsonProperty("altitude")
  @NotNull
  public BigDecimal getAltitude() {
    return altitude;
  }
  public void setAltitude(BigDecimal altitude) {
    this.altitude = altitude;
  }

  /**
   * Coverage radius (in km)
   **/
  public LocationInfo range(BigDecimal range) {
    this.range = range;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Coverage radius (in km)")
  @JsonProperty("range")
  @NotNull
  public BigDecimal getRange() {
    return range;
  }
  public void setRange(BigDecimal range) {
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
    LocationInfo locationInfo = (LocationInfo) o;
    return Objects.equals(latitude, locationInfo.latitude) &&
        Objects.equals(longitude, locationInfo.longitude) &&
        Objects.equals(altitude, locationInfo.altitude) &&
        Objects.equals(range, locationInfo.range);
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude, altitude, range);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationInfo {\n");
    
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

