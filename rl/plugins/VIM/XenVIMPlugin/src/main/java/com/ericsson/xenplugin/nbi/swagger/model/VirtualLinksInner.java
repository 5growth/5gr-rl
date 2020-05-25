package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualLinksInner   {
  
  private @Valid VirtualLinksInnerVirtualLink virtualLink = null;

  /**
   **/
  public VirtualLinksInner virtualLink(VirtualLinksInnerVirtualLink virtualLink) {
    this.virtualLink = virtualLink;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualLink")
  @NotNull
  public VirtualLinksInnerVirtualLink getVirtualLink() {
    return virtualLink;
  }
  public void setVirtualLink(VirtualLinksInnerVirtualLink virtualLink) {
    this.virtualLink = virtualLink;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinksInner virtualLinksInner = (VirtualLinksInner) o;
    return Objects.equals(virtualLink, virtualLinksInner.virtualLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLink);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinksInner {\n");
    
    sb.append("    virtualLink: ").append(toIndentedString(virtualLink)).append("\n");
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

