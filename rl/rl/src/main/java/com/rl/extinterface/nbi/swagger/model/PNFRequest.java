package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Set Request of PNF
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Set Request of PNF")

public class PNFRequest   {
  
  private @Valid String pnfId = null;
  private @Valid String popId = null;
  private @Valid MetaData metaData = null;

  /**
   * Identifier of the resource
   **/
  public PNFRequest pnfId(String pnfId) {
    this.pnfId = pnfId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the resource")
  @JsonProperty("pnfId")
  public String getPnfId() {
    return pnfId;
  }
  public void setPnfId(String pnfId) {
    this.pnfId = pnfId;
  }

  /**
   * Identifier of the resource
   **/
  public PNFRequest popId(String popId) {
    this.popId = popId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the resource")
  @JsonProperty("popId")
  public String getPopId() {
    return popId;
  }
  public void setPopId(String popId) {
    this.popId = popId;
  }

  /**
   **/
  public PNFRequest metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("metaData")
  @NotNull
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
    PNFRequest pnFRequest = (PNFRequest) o;
    return Objects.equals(pnfId, pnFRequest.pnfId) &&
        Objects.equals(popId, pnFRequest.popId) &&
        Objects.equals(metaData, pnFRequest.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfId, popId, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PNFRequest {\n");
    
    sb.append("    pnfId: ").append(toIndentedString(pnfId)).append("\n");
    sb.append("    popId: ").append(toIndentedString(popId)).append("\n");
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

