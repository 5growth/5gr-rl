package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.MFInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MFlistInner   {
  
  private @Valid MFInfo mfinfo = null;

  /**
   **/
  public MFlistInner mfinfo(MFInfo mfinfo) {
    this.mfinfo = mfinfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("mfinfo")
  @NotNull
  public MFInfo getMfinfo() {
    return mfinfo;
  }
  public void setMfinfo(MFInfo mfinfo) {
    this.mfinfo = mfinfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MFlistInner mflistInner = (MFlistInner) o;
    return Objects.equals(mfinfo, mflistInner.mfinfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfinfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MFlistInner {\n");
    
    sb.append("    mfinfo: ").append(toIndentedString(mfinfo)).append("\n");
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

