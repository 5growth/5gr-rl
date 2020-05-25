package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.xenplugin.nbi.swagger.model.MetaDataInner;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * If subnet types are created satisfactorily, it contains the data relative to the allocated subnet. Cardinality can be \&quot;0\&quot; if the request did not include creation of such type of resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "If subnet types are created satisfactorily, it contains the data relative to the allocated subnet. Cardinality can be \"0\" if the request did not include creation of such type of resource.")

public class AllocateNetworkResultSubnetData   {
  
  private @Valid List<Integer> addressPool = new ArrayList<Integer>();
  private @Valid String cidr = null;
  private @Valid String gatewayIp = null;
  private @Valid String ipVersion = null;
  private @Valid Boolean isDhcpEnabled = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String networkId = null;
  private @Valid String operationalState = null;
  private @Valid String resourceId = null;

  /**
   * Address pools for the network/subnetwork. The cardinality can be 0 when VIM is allowed to allocate all addresses in the CIDR except for the address of the network/subnetwork gateway.
   **/
  public AllocateNetworkResultSubnetData addressPool(List<Integer> addressPool) {
    this.addressPool = addressPool;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Address pools for the network/subnetwork. The cardinality can be 0 when VIM is allowed to allocate all addresses in the CIDR except for the address of the network/subnetwork gateway.")
  @JsonProperty("addressPool")
  @NotNull
  public List<Integer> getAddressPool() {
    return addressPool;
  }
  public void setAddressPool(List<Integer> addressPool) {
    this.addressPool = addressPool;
  }

  /**
   * The CIDR of the network/subnetwork, i.e. network address and subnet mask.
   **/
  public AllocateNetworkResultSubnetData cidr(String cidr) {
    this.cidr = cidr;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The CIDR of the network/subnetwork, i.e. network address and subnet mask.")
  @JsonProperty("cidr")
  @NotNull
  public String getCidr() {
    return cidr;
  }
  public void setCidr(String cidr) {
    this.cidr = cidr;
  }

  /**
   * The IP address of the network/subnetwork gateway.
   **/
  public AllocateNetworkResultSubnetData gatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The IP address of the network/subnetwork gateway.")
  @JsonProperty("gatewayIp")
  @NotNull
  public String getGatewayIp() {
    return gatewayIp;
  }
  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  /**
   * The IP version of the network/subnetwork.
   **/
  public AllocateNetworkResultSubnetData ipVersion(String ipVersion) {
    this.ipVersion = ipVersion;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The IP version of the network/subnetwork.")
  @JsonProperty("ipVersion")
  @NotNull
  public String getIpVersion() {
    return ipVersion;
  }
  public void setIpVersion(String ipVersion) {
    this.ipVersion = ipVersion;
  }

  /**
   * True when DHCP is enabled for this network/subnetwork, or false otherwise.
   **/
  public AllocateNetworkResultSubnetData isDhcpEnabled(Boolean isDhcpEnabled) {
    this.isDhcpEnabled = isDhcpEnabled;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "True when DHCP is enabled for this network/subnetwork, or false otherwise.")
  @JsonProperty("isDhcpEnabled")
  @NotNull
  public Boolean isIsDhcpEnabled() {
    return isDhcpEnabled;
  }
  public void setIsDhcpEnabled(Boolean isDhcpEnabled) {
    this.isDhcpEnabled = isDhcpEnabled;
  }

  /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   **/
  public AllocateNetworkResultSubnetData metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(value = "List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.")
  @JsonProperty("metadata")
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }
  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  /**
   * The identifier of the virtualised network that the virtualised sub-network is attached to. The cardinality can be 0 to cover the case where this type is used to describe the L3 attributes of a network rather than a subnetwork.
   **/
  public AllocateNetworkResultSubnetData networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the virtualised network that the virtualised sub-network is attached to. The cardinality can be 0 to cover the case where this type is used to describe the L3 attributes of a network rather than a subnetwork.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * The operational state of the virtualised sub-network.
   **/
  public AllocateNetworkResultSubnetData operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The operational state of the virtualised sub-network.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * Identifier of the virtualised sub-network.
   **/
  public AllocateNetworkResultSubnetData resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised sub-network.")
  @JsonProperty("resourceId")
  @NotNull
  public String getResourceId() {
    return resourceId;
  }
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkResultSubnetData allocateNetworkResultSubnetData = (AllocateNetworkResultSubnetData) o;
    return Objects.equals(addressPool, allocateNetworkResultSubnetData.addressPool) &&
        Objects.equals(cidr, allocateNetworkResultSubnetData.cidr) &&
        Objects.equals(gatewayIp, allocateNetworkResultSubnetData.gatewayIp) &&
        Objects.equals(ipVersion, allocateNetworkResultSubnetData.ipVersion) &&
        Objects.equals(isDhcpEnabled, allocateNetworkResultSubnetData.isDhcpEnabled) &&
        Objects.equals(metadata, allocateNetworkResultSubnetData.metadata) &&
        Objects.equals(networkId, allocateNetworkResultSubnetData.networkId) &&
        Objects.equals(operationalState, allocateNetworkResultSubnetData.operationalState) &&
        Objects.equals(resourceId, allocateNetworkResultSubnetData.resourceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressPool, cidr, gatewayIp, ipVersion, isDhcpEnabled, metadata, networkId, operationalState, resourceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResultSubnetData {\n");
    
    sb.append("    addressPool: ").append(toIndentedString(addressPool)).append("\n");
    sb.append("    cidr: ").append(toIndentedString(cidr)).append("\n");
    sb.append("    gatewayIp: ").append(toIndentedString(gatewayIp)).append("\n");
    sb.append("    ipVersion: ").append(toIndentedString(ipVersion)).append("\n");
    sb.append("    isDhcpEnabled: ").append(toIndentedString(isDhcpEnabled)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
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

