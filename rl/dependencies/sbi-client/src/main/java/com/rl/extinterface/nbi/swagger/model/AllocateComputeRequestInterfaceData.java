/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rl.extinterface.nbi.swagger.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * AllocateComputeRequestInterfaceData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-08T16:03:30.222Z")
public class AllocateComputeRequestInterfaceData {
  @SerializedName("ipAddress")
  private List<String> ipAddress = new ArrayList<String>();

  @SerializedName("macAddress")
  private String macAddress = null;

  public AllocateComputeRequestInterfaceData ipAddress(List<String> ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  public AllocateComputeRequestInterfaceData addIpAddressItem(String ipAddressItem) {
    this.ipAddress.add(ipAddressItem);
    return this;
  }

   /**
   * The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.
   * @return ipAddress
  **/
  @ApiModelProperty(required = true, value = "The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.")
  public List<String> getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(List<String> ipAddress) {
    this.ipAddress = ipAddress;
  }

  public AllocateComputeRequestInterfaceData macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

   /**
   * The MAC address desired for the virtual network interface. The cardinality can be 0 to allow for network interface without specific MAC address configuration.
   * @return macAddress
  **/
  @ApiModelProperty(required = true, value = "The MAC address desired for the virtual network interface. The cardinality can be 0 to allow for network interface without specific MAC address configuration.")
  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequestInterfaceData allocateComputeRequestInterfaceData = (AllocateComputeRequestInterfaceData) o;
    return Objects.equals(this.ipAddress, allocateComputeRequestInterfaceData.ipAddress) &&
        Objects.equals(this.macAddress, allocateComputeRequestInterfaceData.macAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress, macAddress);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequestInterfaceData {\n");
    
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
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
