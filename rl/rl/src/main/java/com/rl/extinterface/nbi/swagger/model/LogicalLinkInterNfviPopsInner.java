package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPopsInnerLogicalLinks;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class LogicalLinkInterNfviPopsInner   {
  
  private @Valid LogicalLinkInterNfviPopsInnerLogicalLinks logicalLinks = null;

  /**
   **/
  public LogicalLinkInterNfviPopsInner logicalLinks(LogicalLinkInterNfviPopsInnerLogicalLinks logicalLinks) {
    this.logicalLinks = logicalLinks;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("logicalLinks")
  @NotNull
  public LogicalLinkInterNfviPopsInnerLogicalLinks getLogicalLinks() {
    return logicalLinks;
  }
  public void setLogicalLinks(LogicalLinkInterNfviPopsInnerLogicalLinks logicalLinks) {
    this.logicalLinks = logicalLinks;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkInterNfviPopsInner logicalLinkInterNfviPopsInner = (LogicalLinkInterNfviPopsInner) o;
    return Objects.equals(logicalLinks, logicalLinkInterNfviPopsInner.logicalLinks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkInterNfviPopsInner {\n");
    
    sb.append("    logicalLinks: ").append(toIndentedString(logicalLinks)).append("\n");
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

