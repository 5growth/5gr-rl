package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.LocationInfo;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * 5GT - Element providing information for Radio Coverage Area supported by PoP.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "5GT - Element providing information for Radio Coverage Area supported by PoP.")

public class RadioCoverageAreaListInnerRadioCoverageAreaInfo   {
  
  private @Valid String coverageAreaId = null;
  private @Valid String coverageAreaGoegraphicalInfo = null;
  private @Valid BigDecimal coverageAreaMinBandwidth = null;
  private @Valid BigDecimal coverageAreaMaxBandwidth = null;
  private @Valid BigDecimal coverageAreaDelay = null;
  private @Valid LocationInfo locationInfo = null;

  /**
   * (numbered) Identifier of the Radio Coverage Area
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "(numbered) Identifier of the Radio Coverage Area")
  @JsonProperty("coverageAreaId")
  @NotNull
  public String getCoverageAreaId() {
    return coverageAreaId;
  }
  public void setCoverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
  }

  /**
   * Identifier of the Radio Coverage Area Geographical Information
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaGoegraphicalInfo(String coverageAreaGoegraphicalInfo) {
    this.coverageAreaGoegraphicalInfo = coverageAreaGoegraphicalInfo;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the Radio Coverage Area Geographical Information")
  @JsonProperty("coverageAreaGoegraphicalInfo")
  public String getCoverageAreaGoegraphicalInfo() {
    return coverageAreaGoegraphicalInfo;
  }
  public void setCoverageAreaGoegraphicalInfo(String coverageAreaGoegraphicalInfo) {
    this.coverageAreaGoegraphicalInfo = coverageAreaGoegraphicalInfo;
  }

  /**
   * Minimum Bandwidth of the Radio Coverage Area
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaMinBandwidth(BigDecimal coverageAreaMinBandwidth) {
    this.coverageAreaMinBandwidth = coverageAreaMinBandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum Bandwidth of the Radio Coverage Area")
  @JsonProperty("coverageAreaMinBandwidth")
  @NotNull
  public BigDecimal getCoverageAreaMinBandwidth() {
    return coverageAreaMinBandwidth;
  }
  public void setCoverageAreaMinBandwidth(BigDecimal coverageAreaMinBandwidth) {
    this.coverageAreaMinBandwidth = coverageAreaMinBandwidth;
  }

  /**
   * Maximum Bandwidth of the Radio Coverage Area
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaMaxBandwidth(BigDecimal coverageAreaMaxBandwidth) {
    this.coverageAreaMaxBandwidth = coverageAreaMaxBandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Maximum Bandwidth of the Radio Coverage Area")
  @JsonProperty("coverageAreaMaxBandwidth")
  @NotNull
  public BigDecimal getCoverageAreaMaxBandwidth() {
    return coverageAreaMaxBandwidth;
  }
  public void setCoverageAreaMaxBandwidth(BigDecimal coverageAreaMaxBandwidth) {
    this.coverageAreaMaxBandwidth = coverageAreaMaxBandwidth;
  }

  /**
   * Minimum delay supported by the Radio Coverage Area.
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaDelay(BigDecimal coverageAreaDelay) {
    this.coverageAreaDelay = coverageAreaDelay;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum delay supported by the Radio Coverage Area.")
  @JsonProperty("coverageAreaDelay")
  @NotNull
  public BigDecimal getCoverageAreaDelay() {
    return coverageAreaDelay;
  }
  public void setCoverageAreaDelay(BigDecimal coverageAreaDelay) {
    this.coverageAreaDelay = coverageAreaDelay;
  }

  /**
   **/
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo locationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("locationInfo")
  @NotNull
  public LocationInfo getLocationInfo() {
    return locationInfo;
  }
  public void setLocationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RadioCoverageAreaListInnerRadioCoverageAreaInfo radioCoverageAreaListInnerRadioCoverageAreaInfo = (RadioCoverageAreaListInnerRadioCoverageAreaInfo) o;
    return Objects.equals(coverageAreaId, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaId) &&
        Objects.equals(coverageAreaGoegraphicalInfo, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaGoegraphicalInfo) &&
        Objects.equals(coverageAreaMinBandwidth, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaMinBandwidth) &&
        Objects.equals(coverageAreaMaxBandwidth, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaMaxBandwidth) &&
        Objects.equals(coverageAreaDelay, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaDelay) &&
        Objects.equals(locationInfo, radioCoverageAreaListInnerRadioCoverageAreaInfo.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coverageAreaId, coverageAreaGoegraphicalInfo, coverageAreaMinBandwidth, coverageAreaMaxBandwidth, coverageAreaDelay, locationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RadioCoverageAreaListInnerRadioCoverageAreaInfo {\n");
    
    sb.append("    coverageAreaId: ").append(toIndentedString(coverageAreaId)).append("\n");
    sb.append("    coverageAreaGoegraphicalInfo: ").append(toIndentedString(coverageAreaGoegraphicalInfo)).append("\n");
    sb.append("    coverageAreaMinBandwidth: ").append(toIndentedString(coverageAreaMinBandwidth)).append("\n");
    sb.append("    coverageAreaMaxBandwidth: ").append(toIndentedString(coverageAreaMaxBandwidth)).append("\n");
    sb.append("    coverageAreaDelay: ").append(toIndentedString(coverageAreaDelay)).append("\n");
    sb.append("    locationInfo: ").append(toIndentedString(locationInfo)).append("\n");
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

