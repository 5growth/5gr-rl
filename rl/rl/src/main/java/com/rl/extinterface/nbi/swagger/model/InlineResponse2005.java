package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPops;
import com.rl.extinterface.nbi.swagger.model.NfviPops;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse2005   {
  
  private @Valid NfviPops nfviPops = null;
  private @Valid LogicalLinkInterNfviPops logicalLinkInterNfviPops = null;

  /**
   **/
  public InlineResponse2005 nfviPops(NfviPops nfviPops) {
    this.nfviPops = nfviPops;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("NfviPops")
  public NfviPops getNfviPops() {
    return nfviPops;
  }
  public void setNfviPops(NfviPops nfviPops) {
    this.nfviPops = nfviPops;
  }

  /**
   **/
  public InlineResponse2005 logicalLinkInterNfviPops(LogicalLinkInterNfviPops logicalLinkInterNfviPops) {
    this.logicalLinkInterNfviPops = logicalLinkInterNfviPops;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("logicalLinkInterNfviPops")
  public LogicalLinkInterNfviPops getLogicalLinkInterNfviPops() {
    return logicalLinkInterNfviPops;
  }
  public void setLogicalLinkInterNfviPops(LogicalLinkInterNfviPops logicalLinkInterNfviPops) {
    this.logicalLinkInterNfviPops = logicalLinkInterNfviPops;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2005 inlineResponse2005 = (InlineResponse2005) o;
    return Objects.equals(nfviPops, inlineResponse2005.nfviPops) &&
        Objects.equals(logicalLinkInterNfviPops, inlineResponse2005.logicalLinkInterNfviPops);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nfviPops, logicalLinkInterNfviPops);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2005 {\n");
    
    sb.append("    nfviPops: ").append(toIndentedString(nfviPops)).append("\n");
    sb.append("    logicalLinkInterNfviPops: ").append(toIndentedString(logicalLinkInterNfviPops)).append("\n");
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

