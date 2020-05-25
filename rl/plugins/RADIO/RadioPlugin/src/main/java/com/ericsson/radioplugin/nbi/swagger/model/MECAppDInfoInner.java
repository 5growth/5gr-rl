package com.ericsson.radioplugin.nbi.swagger.model;

import com.ericsson.radioplugin.nbi.swagger.model.MECAppDInfoInnerAppDAttributes;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECAppDInfoInner   {
  
  private @Valid MECAppDInfoInnerAppDAttributes appDAttributes = null;

  /**
   **/
  public MECAppDInfoInner appDAttributes(MECAppDInfoInnerAppDAttributes appDAttributes) {
    this.appDAttributes = appDAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("appDAttributes")
  @NotNull
  public MECAppDInfoInnerAppDAttributes getAppDAttributes() {
    return appDAttributes;
  }
  public void setAppDAttributes(MECAppDInfoInnerAppDAttributes appDAttributes) {
    this.appDAttributes = appDAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECAppDInfoInner meCAppDInfoInner = (MECAppDInfoInner) o;
    return Objects.equals(appDAttributes, meCAppDInfoInner.appDAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appDAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECAppDInfoInner {\n");
    
    sb.append("    appDAttributes: ").append(toIndentedString(appDAttributes)).append("\n");
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

