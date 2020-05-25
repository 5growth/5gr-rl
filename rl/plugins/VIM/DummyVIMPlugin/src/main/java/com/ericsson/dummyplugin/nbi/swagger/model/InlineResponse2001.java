package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.RadioCoverageAreaList;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse2001   {
  
  private @Valid RadioCoverageAreaList regions = null;

  /**
   **/
  public InlineResponse2001 regions(RadioCoverageAreaList regions) {
    this.regions = regions;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("regions")
  public RadioCoverageAreaList getRegions() {
    return regions;
  }
  public void setRegions(RadioCoverageAreaList regions) {
    this.regions = regions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
    return Objects.equals(regions, inlineResponse2001.regions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");
    
    sb.append("    regions: ").append(toIndentedString(regions)).append("\n");
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

