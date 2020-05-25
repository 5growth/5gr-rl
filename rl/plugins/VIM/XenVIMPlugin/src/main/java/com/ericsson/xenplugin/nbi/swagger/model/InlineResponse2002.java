package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.Gateways;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualLinks;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse2002   {
  
  private @Valid Gateways gateways = null;
  private @Valid VirtualLinks virtualLinks = null;

  /**
   **/
  public InlineResponse2002 gateways(Gateways gateways) {
    this.gateways = gateways;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("gateways")
  public Gateways getGateways() {
    return gateways;
  }
  public void setGateways(Gateways gateways) {
    this.gateways = gateways;
  }

  /**
   **/
  public InlineResponse2002 virtualLinks(VirtualLinks virtualLinks) {
    this.virtualLinks = virtualLinks;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualLinks")
  public VirtualLinks getVirtualLinks() {
    return virtualLinks;
  }
  public void setVirtualLinks(VirtualLinks virtualLinks) {
    this.virtualLinks = virtualLinks;
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
    return Objects.equals(gateways, inlineResponse2002.gateways) &&
        Objects.equals(virtualLinks, inlineResponse2002.virtualLinks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gateways, virtualLinks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2002 {\n");
    
    sb.append("    gateways: ").append(toIndentedString(gateways)).append("\n");
    sb.append("    virtualLinks: ").append(toIndentedString(virtualLinks)).append("\n");
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

