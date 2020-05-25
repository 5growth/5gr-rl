package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.LocationInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECRegionInfoMecRegionInfo   {
  
  private @Valid String regionId = null;
  private @Valid LocationInfo locationInfo = null;

  /**
   * Identifier of the region.
   **/
  public MECRegionInfoMecRegionInfo regionId(String regionId) {
    this.regionId = regionId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the region.")
  @JsonProperty("regionId")
  public String getRegionId() {
    return regionId;
  }
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  /**
   **/
  public MECRegionInfoMecRegionInfo locationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("locationInfo")
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
    MECRegionInfoMecRegionInfo meCRegionInfoMecRegionInfo = (MECRegionInfoMecRegionInfo) o;
    return Objects.equals(regionId, meCRegionInfoMecRegionInfo.regionId) &&
        Objects.equals(locationInfo, meCRegionInfoMecRegionInfo.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regionId, locationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionInfoMecRegionInfo {\n");
    
    sb.append("    regionId: ").append(toIndentedString(regionId)).append("\n");
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

