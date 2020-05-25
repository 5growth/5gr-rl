package com.ericsson.xenplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class TunnelInfo   {
  

public enum TunnelTypeEnum {

    GTP_U(String.valueOf("GTP-U")), GRE(String.valueOf("GRE"));


    private String value;

    TunnelTypeEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TunnelTypeEnum fromValue(String v) {
        for (TunnelTypeEnum b : TunnelTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid TunnelTypeEnum tunnelType = null;
  private @Valid String tunnelDstAddress = null;
  private @Valid String tunnelSrcAddress = null;
  private @Valid Object tunnelSpecificData = null;

  /**
   * TType of tunnel (GTP-U, GRE, etc.)
   **/
  public TunnelInfo tunnelType(TunnelTypeEnum tunnelType) {
    this.tunnelType = tunnelType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "TType of tunnel (GTP-U, GRE, etc.)")
  @JsonProperty("tunnelType")
  @NotNull
  public TunnelTypeEnum getTunnelType() {
    return tunnelType;
  }
  public void setTunnelType(TunnelTypeEnum tunnelType) {
    this.tunnelType = tunnelType;
  }

  /**
   * Destination address of the tunnel.
   **/
  public TunnelInfo tunnelDstAddress(String tunnelDstAddress) {
    this.tunnelDstAddress = tunnelDstAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Destination address of the tunnel.")
  @JsonProperty("tunnelDstAddress")
  @NotNull
  public String getTunnelDstAddress() {
    return tunnelDstAddress;
  }
  public void setTunnelDstAddress(String tunnelDstAddress) {
    this.tunnelDstAddress = tunnelDstAddress;
  }

  /**
   * Source address of the tunnel.
   **/
  public TunnelInfo tunnelSrcAddress(String tunnelSrcAddress) {
    this.tunnelSrcAddress = tunnelSrcAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Source address of the tunnel.")
  @JsonProperty("tunnelSrcAddress")
  @NotNull
  public String getTunnelSrcAddress() {
    return tunnelSrcAddress;
  }
  public void setTunnelSrcAddress(String tunnelSrcAddress) {
    this.tunnelSrcAddress = tunnelSrcAddress;
  }

  /**
   * Parameters specific to the tunnel.
   **/
  public TunnelInfo tunnelSpecificData(Object tunnelSpecificData) {
    this.tunnelSpecificData = tunnelSpecificData;
    return this;
  }

  
  @ApiModelProperty(value = "Parameters specific to the tunnel.")
  @JsonProperty("tunnelSpecificData")
  public Object getTunnelSpecificData() {
    return tunnelSpecificData;
  }
  public void setTunnelSpecificData(Object tunnelSpecificData) {
    this.tunnelSpecificData = tunnelSpecificData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TunnelInfo tunnelInfo = (TunnelInfo) o;
    return Objects.equals(tunnelType, tunnelInfo.tunnelType) &&
        Objects.equals(tunnelDstAddress, tunnelInfo.tunnelDstAddress) &&
        Objects.equals(tunnelSrcAddress, tunnelInfo.tunnelSrcAddress) &&
        Objects.equals(tunnelSpecificData, tunnelInfo.tunnelSpecificData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tunnelType, tunnelDstAddress, tunnelSrcAddress, tunnelSpecificData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TunnelInfo {\n");
    
    sb.append("    tunnelType: ").append(toIndentedString(tunnelType)).append("\n");
    sb.append("    tunnelDstAddress: ").append(toIndentedString(tunnelDstAddress)).append("\n");
    sb.append("    tunnelSrcAddress: ").append(toIndentedString(tunnelSrcAddress)).append("\n");
    sb.append("    tunnelSpecificData: ").append(toIndentedString(tunnelSpecificData)).append("\n");
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

