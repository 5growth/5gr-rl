/*
 * 5GT-MTP PA API
 * REST-API for the MTP PA. Find more at http://5g-transformer.eu
 *
 * OpenAPI spec version: 0.0.0
 * Contact: cnd@lists.cttc.es
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.mtp.extinterface.pa.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.mtp.extinterface.pa.swagger.client.model.CompRouteInputComputeReqs;
import java.io.IOException;

/**
 * CompRouteInputNfviPopReqs
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-02T08:45:54.546Z")
public class CompRouteInputNfviPopReqs {
  @SerializedName("vimId")
  private String vimId = null;

  @SerializedName("nfviPopId")
  private String nfviPopId = null;

  @SerializedName("computeReqs")
  private CompRouteInputComputeReqs computeReqs = null;

  public CompRouteInputNfviPopReqs vimId(String vimId) {
    this.vimId = vimId;
    return this;
  }

   /**
   * Identifier of the VIM
   * @return vimId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the VIM")
  public String getVimId() {
    return vimId;
  }

  public void setVimId(String vimId) {
    this.vimId = vimId;
  }

  public CompRouteInputNfviPopReqs nfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
    return this;
  }

   /**
   * Identifier of the NFVI-PoP
   * @return nfviPopId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the NFVI-PoP")
  public String getNfviPopId() {
    return nfviPopId;
  }

  public void setNfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
  }

  public CompRouteInputNfviPopReqs computeReqs(CompRouteInputComputeReqs computeReqs) {
    this.computeReqs = computeReqs;
    return this;
  }

   /**
   * Get computeReqs
   * @return computeReqs
  **/
  @ApiModelProperty(required = true, value = "")
  public CompRouteInputComputeReqs getComputeReqs() {
    return computeReqs;
  }

  public void setComputeReqs(CompRouteInputComputeReqs computeReqs) {
    this.computeReqs = computeReqs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompRouteInputNfviPopReqs compRouteInputNfviPopReqs = (CompRouteInputNfviPopReqs) o;
    return Objects.equals(this.vimId, compRouteInputNfviPopReqs.vimId) &&
        Objects.equals(this.nfviPopId, compRouteInputNfviPopReqs.nfviPopId) &&
        Objects.equals(this.computeReqs, compRouteInputNfviPopReqs.computeReqs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vimId, nfviPopId, computeReqs);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompRouteInputNfviPopReqs {\n");
    
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
    sb.append("    nfviPopId: ").append(toIndentedString(nfviPopId)).append("\n");
    sb.append("    computeReqs: ").append(toIndentedString(computeReqs)).append("\n");
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

