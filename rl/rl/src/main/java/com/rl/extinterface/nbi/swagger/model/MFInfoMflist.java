package com.rl.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MFInfoMflist   {
  
  private @Valid String mfid = null;

  /**
   **/
  public MFInfoMflist mfid(String mfid) {
    this.mfid = mfid;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("mfid")
  public String getMfid() {
    return mfid;
  }
  public void setMfid(String mfid) {
    this.mfid = mfid;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MFInfoMflist mfInfoMflist = (MFInfoMflist) o;
    return Objects.equals(mfid, mfInfoMflist.mfid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MFInfoMflist {\n");
    
    sb.append("    mfid: ").append(toIndentedString(mfid)).append("\n");
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

