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
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

/**
 * 5GT - Element providing information for Radio Coverage Area supported by PoP.
 */
@ApiModel(description = "5GT - Element providing information for Radio Coverage Area supported by PoP.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-08T16:03:30.222Z")
public class RadioCoverageAreaListInnerRadioCoverageAreaInfo {
  @SerializedName("coverageAreaId")
  private String coverageAreaId = null;

  @SerializedName("coverageAreaGoegraphicalInfo")
  private String coverageAreaGoegraphicalInfo = null;

  @SerializedName("coverageAreaMinBandwidth")
  private BigDecimal coverageAreaMinBandwidth = null;

  @SerializedName("coverageAreaMaxBandwidth")
  private BigDecimal coverageAreaMaxBandwidth = null;

  @SerializedName("coverageAreaDelay")
  private BigDecimal coverageAreaDelay = null;

  @SerializedName("locationInfo")
  private LocationInfo locationInfo = null;

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
    return this;
  }

   /**
   * (numbered) Identifier of the Radio Coverage Area
   * @return coverageAreaId
  **/
  @ApiModelProperty(required = true, value = "(numbered) Identifier of the Radio Coverage Area")
  public String getCoverageAreaId() {
    return coverageAreaId;
  }

  public void setCoverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
  }

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaGoegraphicalInfo(String coverageAreaGoegraphicalInfo) {
    this.coverageAreaGoegraphicalInfo = coverageAreaGoegraphicalInfo;
    return this;
  }

   /**
   * Identifier of the Radio Coverage Area Geographical Information
   * @return coverageAreaGoegraphicalInfo
  **/
  @ApiModelProperty(value = "Identifier of the Radio Coverage Area Geographical Information")
  public String getCoverageAreaGoegraphicalInfo() {
    return coverageAreaGoegraphicalInfo;
  }

  public void setCoverageAreaGoegraphicalInfo(String coverageAreaGoegraphicalInfo) {
    this.coverageAreaGoegraphicalInfo = coverageAreaGoegraphicalInfo;
  }

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaMinBandwidth(BigDecimal coverageAreaMinBandwidth) {
    this.coverageAreaMinBandwidth = coverageAreaMinBandwidth;
    return this;
  }

   /**
   * Minimum Bandwidth of the Radio Coverage Area
   * @return coverageAreaMinBandwidth
  **/
  @ApiModelProperty(required = true, value = "Minimum Bandwidth of the Radio Coverage Area")
  public BigDecimal getCoverageAreaMinBandwidth() {
    return coverageAreaMinBandwidth;
  }

  public void setCoverageAreaMinBandwidth(BigDecimal coverageAreaMinBandwidth) {
    this.coverageAreaMinBandwidth = coverageAreaMinBandwidth;
  }

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaMaxBandwidth(BigDecimal coverageAreaMaxBandwidth) {
    this.coverageAreaMaxBandwidth = coverageAreaMaxBandwidth;
    return this;
  }

   /**
   * Maximum Bandwidth of the Radio Coverage Area
   * @return coverageAreaMaxBandwidth
  **/
  @ApiModelProperty(required = true, value = "Maximum Bandwidth of the Radio Coverage Area")
  public BigDecimal getCoverageAreaMaxBandwidth() {
    return coverageAreaMaxBandwidth;
  }

  public void setCoverageAreaMaxBandwidth(BigDecimal coverageAreaMaxBandwidth) {
    this.coverageAreaMaxBandwidth = coverageAreaMaxBandwidth;
  }

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo coverageAreaDelay(BigDecimal coverageAreaDelay) {
    this.coverageAreaDelay = coverageAreaDelay;
    return this;
  }

   /**
   * Minimum delay supported by the Radio Coverage Area.
   * @return coverageAreaDelay
  **/
  @ApiModelProperty(required = true, value = "Minimum delay supported by the Radio Coverage Area.")
  public BigDecimal getCoverageAreaDelay() {
    return coverageAreaDelay;
  }

  public void setCoverageAreaDelay(BigDecimal coverageAreaDelay) {
    this.coverageAreaDelay = coverageAreaDelay;
  }

  public RadioCoverageAreaListInnerRadioCoverageAreaInfo locationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

   /**
   * Get locationInfo
   * @return locationInfo
  **/
  @ApiModelProperty(required = true, value = "")
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
    return Objects.equals(this.coverageAreaId, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaId) &&
        Objects.equals(this.coverageAreaGoegraphicalInfo, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaGoegraphicalInfo) &&
        Objects.equals(this.coverageAreaMinBandwidth, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaMinBandwidth) &&
        Objects.equals(this.coverageAreaMaxBandwidth, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaMaxBandwidth) &&
        Objects.equals(this.coverageAreaDelay, radioCoverageAreaListInnerRadioCoverageAreaInfo.coverageAreaDelay) &&
        Objects.equals(this.locationInfo, radioCoverageAreaListInnerRadioCoverageAreaInfo.locationInfo);
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
