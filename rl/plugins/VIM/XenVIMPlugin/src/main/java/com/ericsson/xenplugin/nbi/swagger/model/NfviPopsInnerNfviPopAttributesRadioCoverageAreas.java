package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.xenplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesCovrageLocationInfo;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about Mec Specific parameters.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about Mec Specific parameters.")

public class NfviPopsInnerNfviPopAttributesRadioCoverageAreas   {
  
  private @Valid String coverageAreaId = null;
  private @Valid NfviPopsInnerNfviPopAttributesCovrageLocationInfo covrageLocationInfo = null;
  private @Valid BigDecimal minBandwidth = null;
  private @Valid BigDecimal maxBandwidth = null;
  private @Valid BigDecimal delay = null;

  /**
   * Coverage Area Identifier supported by Radio PoP
   **/
  public NfviPopsInnerNfviPopAttributesRadioCoverageAreas coverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
    return this;
  }

  
  @ApiModelProperty(value = "Coverage Area Identifier supported by Radio PoP")
  @JsonProperty("coverageAreaId")
  public String getCoverageAreaId() {
    return coverageAreaId;
  }
  public void setCoverageAreaId(String coverageAreaId) {
    this.coverageAreaId = coverageAreaId;
  }

  /**
   **/
  public NfviPopsInnerNfviPopAttributesRadioCoverageAreas covrageLocationInfo(NfviPopsInnerNfviPopAttributesCovrageLocationInfo covrageLocationInfo) {
    this.covrageLocationInfo = covrageLocationInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("covrageLocationInfo")
  public NfviPopsInnerNfviPopAttributesCovrageLocationInfo getCovrageLocationInfo() {
    return covrageLocationInfo;
  }
  public void setCovrageLocationInfo(NfviPopsInnerNfviPopAttributesCovrageLocationInfo covrageLocationInfo) {
    this.covrageLocationInfo = covrageLocationInfo;
  }

  /**
   * Minimummum bandwidth provided by the coverage area
   **/
  public NfviPopsInnerNfviPopAttributesRadioCoverageAreas minBandwidth(BigDecimal minBandwidth) {
    this.minBandwidth = minBandwidth;
    return this;
  }

  
  @ApiModelProperty(value = "Minimummum bandwidth provided by the coverage area")
  @JsonProperty("minBandwidth")
  public BigDecimal getMinBandwidth() {
    return minBandwidth;
  }
  public void setMinBandwidth(BigDecimal minBandwidth) {
    this.minBandwidth = minBandwidth;
  }

  /**
   * Maximum bandwidth provided by the coverage area
   **/
  public NfviPopsInnerNfviPopAttributesRadioCoverageAreas maxBandwidth(BigDecimal maxBandwidth) {
    this.maxBandwidth = maxBandwidth;
    return this;
  }

  
  @ApiModelProperty(value = "Maximum bandwidth provided by the coverage area")
  @JsonProperty("maxBandwidth")
  public BigDecimal getMaxBandwidth() {
    return maxBandwidth;
  }
  public void setMaxBandwidth(BigDecimal maxBandwidth) {
    this.maxBandwidth = maxBandwidth;
  }

  /**
   * minimum delay guaranteed by the coverage area
   **/
  public NfviPopsInnerNfviPopAttributesRadioCoverageAreas delay(BigDecimal delay) {
    this.delay = delay;
    return this;
  }

  
  @ApiModelProperty(value = "minimum delay guaranteed by the coverage area")
  @JsonProperty("delay")
  public BigDecimal getDelay() {
    return delay;
  }
  public void setDelay(BigDecimal delay) {
    this.delay = delay;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributesRadioCoverageAreas nfviPopsInnerNfviPopAttributesRadioCoverageAreas = (NfviPopsInnerNfviPopAttributesRadioCoverageAreas) o;
    return Objects.equals(coverageAreaId, nfviPopsInnerNfviPopAttributesRadioCoverageAreas.coverageAreaId) &&
        Objects.equals(covrageLocationInfo, nfviPopsInnerNfviPopAttributesRadioCoverageAreas.covrageLocationInfo) &&
        Objects.equals(minBandwidth, nfviPopsInnerNfviPopAttributesRadioCoverageAreas.minBandwidth) &&
        Objects.equals(maxBandwidth, nfviPopsInnerNfviPopAttributesRadioCoverageAreas.maxBandwidth) &&
        Objects.equals(delay, nfviPopsInnerNfviPopAttributesRadioCoverageAreas.delay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coverageAreaId, covrageLocationInfo, minBandwidth, maxBandwidth, delay);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesRadioCoverageAreas {\n");
    
    sb.append("    coverageAreaId: ").append(toIndentedString(coverageAreaId)).append("\n");
    sb.append("    covrageLocationInfo: ").append(toIndentedString(covrageLocationInfo)).append("\n");
    sb.append("    minBandwidth: ").append(toIndentedString(minBandwidth)).append("\n");
    sb.append("    maxBandwidth: ").append(toIndentedString(maxBandwidth)).append("\n");
    sb.append("    delay: ").append(toIndentedString(delay)).append("\n");
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

