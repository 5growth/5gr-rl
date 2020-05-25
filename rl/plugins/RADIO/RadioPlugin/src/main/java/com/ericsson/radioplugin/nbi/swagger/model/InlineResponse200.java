package com.ericsson.radioplugin.nbi.swagger.model;

import com.ericsson.radioplugin.nbi.swagger.model.MECRegionInfo;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse200   {
  
  private @Valid List<MECRegionInfo> regions = new ArrayList<MECRegionInfo>();

  /**
   **/
  public InlineResponse200 regions(List<MECRegionInfo> regions) {
    this.regions = regions;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("Regions")
  public List<MECRegionInfo> getRegions() {
    return regions;
  }
  public void setRegions(List<MECRegionInfo> regions) {
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
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(regions, inlineResponse200.regions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
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

