package com.rl.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECAppDInfoInnerAppDAttributes   {
  
  private @Valid String appDInfo = null;
  private @Valid String mecAppDId = null;

  /**
   * It provides information about MEC APPD (TBD)
   **/
  public MECAppDInfoInnerAppDAttributes appDInfo(String appDInfo) {
    this.appDInfo = appDInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It provides information about MEC APPD (TBD)")
  @JsonProperty("appDInfo")
  @NotNull
  public String getAppDInfo() {
    return appDInfo;
  }
  public void setAppDInfo(String appDInfo) {
    this.appDInfo = appDInfo;
  }

  /**
   * Identification of the MEC APPdId
   **/
  public MECAppDInfoInnerAppDAttributes mecAppDId(String mecAppDId) {
    this.mecAppDId = mecAppDId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the MEC APPdId")
  @JsonProperty("mecAppDId")
  @NotNull
  public String getMecAppDId() {
    return mecAppDId;
  }
  public void setMecAppDId(String mecAppDId) {
    this.mecAppDId = mecAppDId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECAppDInfoInnerAppDAttributes meCAppDInfoInnerAppDAttributes = (MECAppDInfoInnerAppDAttributes) o;
    return Objects.equals(appDInfo, meCAppDInfoInnerAppDAttributes.appDInfo) &&
        Objects.equals(mecAppDId, meCAppDInfoInnerAppDAttributes.mecAppDId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appDInfo, mecAppDId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECAppDInfoInnerAppDAttributes {\n");
    
    sb.append("    appDInfo: ").append(toIndentedString(appDInfo)).append("\n");
    sb.append("    mecAppDId: ").append(toIndentedString(mecAppDId)).append("\n");
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

