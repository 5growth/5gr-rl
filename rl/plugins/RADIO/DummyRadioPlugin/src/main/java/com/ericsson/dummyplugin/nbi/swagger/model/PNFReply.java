package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PNFReply   {
  
  private @Valid String pnfResId = null;
  private @Valid MetaData metaData = null;

  /**
   **/
  public PNFReply pnfResId(String pnfResId) {
    this.pnfResId = pnfResId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("pnfResId")
  public String getPnfResId() {
    return pnfResId;
  }
  public void setPnfResId(String pnfResId) {
    this.pnfResId = pnfResId;
  }

  /**
   **/
  public PNFReply metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("metaData")
  public MetaData getMetaData() {
    return metaData;
  }
  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PNFReply pnFReply = (PNFReply) o;
    return Objects.equals(pnfResId, pnFReply.pnfResId) &&
        Objects.equals(metaData, pnFReply.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfResId, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PNFReply {\n");
    
    sb.append("    pnfResId: ").append(toIndentedString(pnfResId)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
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

