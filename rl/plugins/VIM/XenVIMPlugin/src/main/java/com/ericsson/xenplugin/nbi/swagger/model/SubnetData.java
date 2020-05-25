package com.ericsson.xenplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;


public class SubnetData   {
  
  private @Valid String resourceId = null;
  private @Valid String networkId = null;
  private @Valid String ipVersion = null;
  private @Valid String gatewayIp = null;
  private @Valid String cidr = null;
  private @Valid Boolean isDhcpEnabled = null;
  private @Valid List<Integer> addressPool = new ArrayList<Integer>();
  private @Valid MetaData metadata = null;

  /**
   * Identifiers of the network Resource
   **/
  public SubnetData resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifiers of the network Resource")
  @JsonProperty("resourceId")
  @NotNull
  public String getResourceId() {
    return resourceId;
  }
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Network identifier the subnetwork Resource
   **/
  public SubnetData networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Network identifier the subnetwork Resource")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * IP version of the subnetwork Resource
   **/
  public SubnetData ipVersion(String ipVersion) {
    this.ipVersion = ipVersion;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "IP version of the subnetwork Resource")
  @JsonProperty("ipVersion")
  @NotNull
  public String getIpVersion() {
    return ipVersion;
  }
  public void setIpVersion(String ipVersion) {
    this.ipVersion = ipVersion;
  }

  /**
   * gateway of the subnetwork Resource
   **/
  public SubnetData gatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "gateway of the subnetwork Resource")
  @JsonProperty("gatewayIp")
  @NotNull
  public String getGatewayIp() {
    return gatewayIp;
  }
  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  /**
   * cidr of the subnetwork Resource
   **/
  public SubnetData cidr(String cidr) {
    this.cidr = cidr;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "cidr of the subnetwork Resource")
  @JsonProperty("cidr")
  @NotNull
  public String getCidr() {
    return cidr;
  }
  public void setCidr(String cidr) {
    this.cidr = cidr;
  }

  /**
   * enable if dhcp of the network Resource
   **/
  public SubnetData isDhcpEnabled(Boolean isDhcpEnabled) {
    this.isDhcpEnabled = isDhcpEnabled;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "enable if dhcp of the network Resource")
  @JsonProperty("isDhcpEnabled")
  @NotNull
  public Boolean isIsDhcpEnabled() {
    return isDhcpEnabled;
  }
  public void setIsDhcpEnabled(Boolean isDhcpEnabled) {
    this.isDhcpEnabled = isDhcpEnabled;
  }

  /**
   * optional AddressPool to be used
   **/
  public SubnetData addressPool(List<Integer> addressPool) {
    this.addressPool = addressPool;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "optional AddressPool to be used")
  @JsonProperty("addressPool")
  @NotNull
  public List<Integer> getAddressPool() {
    return addressPool;
  }
  public void setAddressPool(List<Integer> addressPool) {
    this.addressPool = addressPool;
  }

  /**
   **/
  public SubnetData metadata(MetaData metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("metadata")
  @NotNull
  public MetaData getMetadata() {
    return metadata;
  }
  public void setMetadata(MetaData metadata) {
    this.metadata = metadata;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubnetData subnetData = (SubnetData) o;
    return Objects.equals(resourceId, subnetData.resourceId) &&
        Objects.equals(networkId, subnetData.networkId) &&
        Objects.equals(ipVersion, subnetData.ipVersion) &&
        Objects.equals(gatewayIp, subnetData.gatewayIp) &&
        Objects.equals(cidr, subnetData.cidr) &&
        Objects.equals(isDhcpEnabled, subnetData.isDhcpEnabled) &&
        Objects.equals(addressPool, subnetData.addressPool) &&
        Objects.equals(metadata, subnetData.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceId, networkId, ipVersion, gatewayIp, cidr, isDhcpEnabled, addressPool, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubnetData {\n");
    
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    ipVersion: ").append(toIndentedString(ipVersion)).append("\n");
    sb.append("    gatewayIp: ").append(toIndentedString(gatewayIp)).append("\n");
    sb.append("    cidr: ").append(toIndentedString(cidr)).append("\n");
    sb.append("    isDhcpEnabled: ").append(toIndentedString(isDhcpEnabled)).append("\n");
    sb.append("    addressPool: ").append(toIndentedString(addressPool)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

