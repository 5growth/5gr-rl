package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.dummyplugin.nbi.swagger.model.MECRegionInfoMecRegionInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about a region.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about a region.")

public class MECRegionInfo   {
  
  private @Valid MECRegionInfoMecRegionInfo mecRegionInfo = null;

  /**
   **/
  public MECRegionInfo mecRegionInfo(MECRegionInfoMecRegionInfo mecRegionInfo) {
    this.mecRegionInfo = mecRegionInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("mecRegionInfo")
  public MECRegionInfoMecRegionInfo getMecRegionInfo() {
    return mecRegionInfo;
  }
  public void setMecRegionInfo(MECRegionInfoMecRegionInfo mecRegionInfo) {
    this.mecRegionInfo = mecRegionInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECRegionInfo meCRegionInfo = (MECRegionInfo) o;
    return Objects.equals(mecRegionInfo, meCRegionInfo.mecRegionInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mecRegionInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionInfo {\n");
    
    sb.append("    mecRegionInfo: ").append(toIndentedString(mecRegionInfo)).append("\n");
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

