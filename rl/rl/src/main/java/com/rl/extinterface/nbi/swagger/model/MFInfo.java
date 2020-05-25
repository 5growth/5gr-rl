package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.rl.extinterface.nbi.swagger.model.MFInfoMflist;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Info of MF
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Info of MF")

public class MFInfo   {
  
  private @Valid String pnfid = null;
  private @Valid String vnfid = null;
  private @Valid List<MFInfoMflist> mflist = new ArrayList<MFInfoMflist>();

  /**
   **/
  public MFInfo pnfid(String pnfid) {
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
  public MFInfo vnfid(String vnfid) {
    this.vnfid = vnfid;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("vnfid")
  public String getVnfid() {
    return vnfid;
  }
  public void setVnfid(String vnfid) {
    this.vnfid = vnfid;
  }

  /**
   **/
  public MFInfo mflist(List<MFInfoMflist> mflist) {
    this.mflist = mflist;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("mflist")
  public List<MFInfoMflist> getMflist() {
    return mflist;
  }
  public void setMflist(List<MFInfoMflist> mflist) {
    this.mflist = mflist;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MFInfo mfInfo = (MFInfo) o;
    return Objects.equals(pnfid, mfInfo.pnfid) &&
        Objects.equals(vnfid, mfInfo.vnfid) &&
        Objects.equals(mflist, mfInfo.mflist);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pnfid, vnfid, mflist);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MFInfo {\n");
    
    sb.append("    pnfid: ").append(toIndentedString(pnfid)).append("\n");
    sb.append("    vnfid: ").append(toIndentedString(vnfid)).append("\n");
    sb.append("    mflist: ").append(toIndentedString(mflist)).append("\n");
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

