package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.LogicalLinkAttributes;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class LogicalLinkPathListInner   {
  
  private @Valid LogicalLinkAttributes logicalLinkAttributes = null;

  /**
   **/
  public LogicalLinkPathListInner logicalLinkAttributes(LogicalLinkAttributes logicalLinkAttributes) {
    this.logicalLinkAttributes = logicalLinkAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("logicalLinkAttributes")
  @NotNull
  public LogicalLinkAttributes getLogicalLinkAttributes() {
    return logicalLinkAttributes;
  }
  public void setLogicalLinkAttributes(LogicalLinkAttributes logicalLinkAttributes) {
    this.logicalLinkAttributes = logicalLinkAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkPathListInner logicalLinkPathListInner = (LogicalLinkPathListInner) o;
    return Objects.equals(logicalLinkAttributes, logicalLinkPathListInner.logicalLinkAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkPathListInner {\n");
    
    sb.append("    logicalLinkAttributes: ").append(toIndentedString(logicalLinkAttributes)).append("\n");
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

