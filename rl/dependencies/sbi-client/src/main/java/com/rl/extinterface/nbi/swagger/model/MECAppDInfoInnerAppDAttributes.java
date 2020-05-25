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
import io.swagger.annotations.ApiModelProperty;

/**
 * MECAppDInfoInnerAppDAttributes
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-08T16:03:30.222Z")
public class MECAppDInfoInnerAppDAttributes {
  @SerializedName("appDInfo")
  private String appDInfo = null;

  @SerializedName("mecAppDId")
  private String mecAppDId = null;

  public MECAppDInfoInnerAppDAttributes appDInfo(String appDInfo) {
    this.appDInfo = appDInfo;
    return this;
  }

   /**
   * It provides information about MEC APPD (TBD)
   * @return appDInfo
  **/
  @ApiModelProperty(required = true, value = "It provides information about MEC APPD (TBD)")
  public String getAppDInfo() {
    return appDInfo;
  }

  public void setAppDInfo(String appDInfo) {
    this.appDInfo = appDInfo;
  }

  public MECAppDInfoInnerAppDAttributes mecAppDId(String mecAppDId) {
    this.mecAppDId = mecAppDId;
    return this;
  }

   /**
   * Identification of the MEC APPdId
   * @return mecAppDId
  **/
  @ApiModelProperty(required = true, value = "Identification of the MEC APPdId")
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
    return Objects.equals(this.appDInfo, meCAppDInfoInnerAppDAttributes.appDInfo) &&
        Objects.equals(this.mecAppDId, meCAppDInfoInnerAppDAttributes.mecAppDId);
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
