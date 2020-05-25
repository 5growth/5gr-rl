package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MFRequestInner   {
  
  private @Valid String mfid = null;
  private @Valid String status = null;

  /**
   **/
  public MFRequestInner mfid(String mfid) {
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

  /**
   **/
  public MFRequestInner status(String status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MFRequestInner mfRequestInner = (MFRequestInner) o;
    return Objects.equals(mfid, mfRequestInner.mfid) &&
        Objects.equals(status, mfRequestInner.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfid, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MFRequestInner {\n");
    
    sb.append("    mfid: ").append(toIndentedString(mfid)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

