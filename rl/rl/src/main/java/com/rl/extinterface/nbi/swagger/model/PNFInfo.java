package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Info of PNF
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Info of PNF")

public class PNFInfo   {
  
  private @Valid String pnfid = null;
  private @Valid MetaData metadata = null;

  /**
   **/
  public PNFInfo pnfid(String pnfid) {
    this.pnfid = pnfid;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("pnfid")
  public String getPnfid() {
    return pnfid;
  }
  public void setPnfid(String pnfid) {
    this.pnfid = pnfid;
  }

  /**
   **/
  public PNFInfo metadata(MetaData metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("metadata")
  public MetaData getMetadata() {
    return metadata;
  }
  public void setMetadata(MetaData metadata) {
    this.metadata = metadata;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PNFInfo pnFInfo = (PNFInfo) o;
    return Objects.equals(pnfid, pnFInfo.pnfid) &&
        Objects.equals(metadata, pnFInfo.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfid, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PNFInfo {\n");
    
    sb.append("    pnfid: ").append(toIndentedString(pnfid)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

