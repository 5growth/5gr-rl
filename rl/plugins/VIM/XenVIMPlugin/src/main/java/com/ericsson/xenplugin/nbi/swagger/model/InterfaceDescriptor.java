package com.ericsson.xenplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class InterfaceDescriptor   {
  

public enum InterfaceTypeEnum {

    TUNNEL(String.valueOf("TUNNEL")), MAC(String.valueOf("MAC")), IP(String.valueOf("IP"));


    private String value;

    InterfaceTypeEnum (String v) {
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
    public static InterfaceTypeEnum fromValue(String v) {
        for (InterfaceTypeEnum b : InterfaceTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid InterfaceTypeEnum interfaceType = null;
  private @Valid TunnelInfo tunnelInfo = null;
  private @Valid String srcMACAddress = null;
  private @Valid String dstMACAddress = null;
  private @Valid String dstIPAddress = null;

  /**
   * Type of interface (TUNNEL, MAC, IP, etc.)
   **/
  public InterfaceDescriptor interfaceType(InterfaceTypeEnum interfaceType) {
    this.interfaceType = interfaceType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of interface (TUNNEL, MAC, IP, etc.)")
  @JsonProperty("interfaceType")
  @NotNull
  public InterfaceTypeEnum getInterfaceType() {
    return interfaceType;
  }
  public void setInterfaceType(InterfaceTypeEnum interfaceType) {
    this.interfaceType = interfaceType;
  }

  /**
   **/
  public InterfaceDescriptor tunnelInfo(TunnelInfo tunnelInfo) {
    this.tunnelInfo = tunnelInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("tunnelInfo")
  public TunnelInfo getTunnelInfo() {
    return tunnelInfo;
  }
  public void setTunnelInfo(TunnelInfo tunnelInfo) {
    this.tunnelInfo = tunnelInfo;
  }

  /**
   * If the interface type is MAC, the source address identifies the MAC address of the interface.
   **/
  public InterfaceDescriptor srcMACAddress(String srcMACAddress) {
    this.srcMACAddress = srcMACAddress;
    return this;
  }

  
  @ApiModelProperty(value = "If the interface type is MAC, the source address identifies the MAC address of the interface.")
  @JsonProperty("srcMACAddress")
  public String getSrcMACAddress() {
    return srcMACAddress;
  }
  public void setSrcMACAddress(String srcMACAddress) {
    this.srcMACAddress = srcMACAddress;
  }

  /**
   * If the interface type is MAC, the destination address identifies the MAC address of the interface. Only used for dstInterface.
   **/
  public InterfaceDescriptor dstMACAddress(String dstMACAddress) {
    this.dstMACAddress = dstMACAddress;
    return this;
  }

  
  @ApiModelProperty(value = "If the interface type is MAC, the destination address identifies the MAC address of the interface. Only used for dstInterface.")
  @JsonProperty("dstMACAddress")
  public String getDstMACAddress() {
    return dstMACAddress;
  }
  public void setDstMACAddress(String dstMACAddress) {
    this.dstMACAddress = dstMACAddress;
  }

  /**
   * f the interface type is IP, the destination address identifies the IP address of the destination. Only used for dstInterface.
   **/
  public InterfaceDescriptor dstIPAddress(String dstIPAddress) {
    this.dstIPAddress = dstIPAddress;
    return this;
  }

  
  @ApiModelProperty(value = "f the interface type is IP, the destination address identifies the IP address of the destination. Only used for dstInterface.")
  @JsonProperty("dstIPAddress")
  public String getDstIPAddress() {
    return dstIPAddress;
  }
  public void setDstIPAddress(String dstIPAddress) {
    this.dstIPAddress = dstIPAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InterfaceDescriptor interfaceDescriptor = (InterfaceDescriptor) o;
    return Objects.equals(interfaceType, interfaceDescriptor.interfaceType) &&
        Objects.equals(tunnelInfo, interfaceDescriptor.tunnelInfo) &&
        Objects.equals(srcMACAddress, interfaceDescriptor.srcMACAddress) &&
        Objects.equals(dstMACAddress, interfaceDescriptor.dstMACAddress) &&
        Objects.equals(dstIPAddress, interfaceDescriptor.dstIPAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interfaceType, tunnelInfo, srcMACAddress, dstMACAddress, dstIPAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InterfaceDescriptor {\n");
    
    sb.append("    interfaceType: ").append(toIndentedString(interfaceType)).append("\n");
    sb.append("    tunnelInfo: ").append(toIndentedString(tunnelInfo)).append("\n");
    sb.append("    srcMACAddress: ").append(toIndentedString(srcMACAddress)).append("\n");
    sb.append("    dstMACAddress: ").append(toIndentedString(dstMACAddress)).append("\n");
    sb.append("    dstIPAddress: ").append(toIndentedString(dstIPAddress)).append("\n");
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

