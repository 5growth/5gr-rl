package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.PNFInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PNFlistInner   {
  
  private @Valid PNFInfo pnfinfo = null;

  /**
   **/
  public PNFlistInner pnfinfo(PNFInfo pnfinfo) {
    this.pnfinfo = pnfinfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("pnfinfo")
  @NotNull
  public PNFInfo getPnfinfo() {
    return pnfinfo;
  }
  public void setPnfinfo(PNFInfo pnfinfo) {
    this.pnfinfo = pnfinfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PNFlistInner pnFlistInner = (PNFlistInner) o;
    return Objects.equals(pnfinfo, pnFlistInner.pnfinfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfinfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PNFlistInner {\n");
    
    sb.append("    pnfinfo: ").append(toIndentedString(pnfinfo)).append("\n");
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

