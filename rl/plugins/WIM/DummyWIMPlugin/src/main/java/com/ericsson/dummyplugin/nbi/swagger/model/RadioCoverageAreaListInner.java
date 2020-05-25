package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.RadioCoverageAreaListInnerRadioCoverageAreaInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RadioCoverageAreaListInner   {
  
  private @Valid RadioCoverageAreaListInnerRadioCoverageAreaInfo radioCoverageAreaInfo = null;

  /**
   **/
  public RadioCoverageAreaListInner radioCoverageAreaInfo(RadioCoverageAreaListInnerRadioCoverageAreaInfo radioCoverageAreaInfo) {
    this.radioCoverageAreaInfo = radioCoverageAreaInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("radioCoverageAreaInfo")
  @NotNull
  public RadioCoverageAreaListInnerRadioCoverageAreaInfo getRadioCoverageAreaInfo() {
    return radioCoverageAreaInfo;
  }
  public void setRadioCoverageAreaInfo(RadioCoverageAreaListInnerRadioCoverageAreaInfo radioCoverageAreaInfo) {
    this.radioCoverageAreaInfo = radioCoverageAreaInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RadioCoverageAreaListInner radioCoverageAreaListInner = (RadioCoverageAreaListInner) o;
    return Objects.equals(radioCoverageAreaInfo, radioCoverageAreaListInner.radioCoverageAreaInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(radioCoverageAreaInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RadioCoverageAreaListInner {\n");
    
    sb.append("    radioCoverageAreaInfo: ").append(toIndentedString(radioCoverageAreaInfo)).append("\n");
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

