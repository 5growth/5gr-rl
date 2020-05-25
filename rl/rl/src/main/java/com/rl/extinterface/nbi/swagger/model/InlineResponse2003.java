package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.MFlist;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse2003   {
  
  private @Valid MFlist mfList = null;

  /**
   **/
  public InlineResponse2003 mfList(MFlist mfList) {
    this.mfList = mfList;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("mfList")
  public MFlist getMfList() {
    return mfList;
  }
  public void setMfList(MFlist mfList) {
    this.mfList = mfList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2003 inlineResponse2003 = (InlineResponse2003) o;
    return Objects.equals(mfList, inlineResponse2003.mfList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2003 {\n");
    
    sb.append("    mfList: ").append(toIndentedString(mfList)).append("\n");
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

