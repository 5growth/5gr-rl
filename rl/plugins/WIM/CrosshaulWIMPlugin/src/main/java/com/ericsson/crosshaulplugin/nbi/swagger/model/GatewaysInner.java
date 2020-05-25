package com.ericsson.crosshaulplugin.nbi.swagger.model;

import com.ericsson.crosshaulplugin.nbi.swagger.model.GatewaysInnerGatewayAttributes;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GatewaysInner   {
  
  private @Valid GatewaysInnerGatewayAttributes gatewayAttributes = null;

  /**
   **/
  public GatewaysInner gatewayAttributes(GatewaysInnerGatewayAttributes gatewayAttributes) {
    this.gatewayAttributes = gatewayAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("gatewayAttributes")
  @NotNull
  public GatewaysInnerGatewayAttributes getGatewayAttributes() {
    return gatewayAttributes;
  }
  public void setGatewayAttributes(GatewaysInnerGatewayAttributes gatewayAttributes) {
    this.gatewayAttributes = gatewayAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GatewaysInner gatewaysInner = (GatewaysInner) o;
    return Objects.equals(gatewayAttributes, gatewaysInner.gatewayAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gatewayAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GatewaysInner {\n");
    
    sb.append("    gatewayAttributes: ").append(toIndentedString(gatewayAttributes)).append("\n");
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

