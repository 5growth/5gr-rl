package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.PNFlist;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse2002   {
  
  private @Valid PNFlist pnFList = null;

  /**
   **/
  public InlineResponse2002 pnFList(PNFlist pnFList) {
    this.pnFList = pnFList;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("PNFList")
  public PNFlist getPnFList() {
    return pnFList;
  }
  public void setPnFList(PNFlist pnFList) {
    this.pnFList = pnFList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2002 inlineResponse2002 = (InlineResponse2002) o;
    return Objects.equals(pnFList, inlineResponse2002.pnFList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnFList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2002 {\n");
    
    sb.append("    pnFList: ").append(toIndentedString(pnFList)).append("\n");
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

