package com.ericsson.xenplugin.nbi.swagger.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TrafficFilter   {
  
  private @Valid List<String> srcAddress = new ArrayList<String>();
  private @Valid List<String> dstAddress = new ArrayList<String>();
  private @Valid List<String> srcPort = new ArrayList<String>();
  private @Valid List<String> dstPort = new ArrayList<String>();
  private @Valid List<String> protocol = new ArrayList<String>();
  private @Valid List<String> token = new ArrayList<String>();
  private @Valid List<String> srcTunnelAddress = new ArrayList<String>();
  private @Valid List<String> dstTunnelAddress = new ArrayList<String>();
  private @Valid List<String> srcTunnelPort = new ArrayList<String>();
  private @Valid List<String> dstTunnelPort = new ArrayList<String>();
  private @Valid BigDecimal qci = null;
  private @Valid BigDecimal dscp = null;
  private @Valid BigDecimal tc = null;

  /**
   * An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.
   **/
  public TrafficFilter srcAddress(List<String> srcAddress) {
    this.srcAddress = srcAddress;
    return this;
  }

  
  @ApiModelProperty(value = "An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.")
  @JsonProperty("srcAddress")
  public List<String> getSrcAddress() {
    return srcAddress;
  }
  public void setSrcAddress(List<String> srcAddress) {
    this.srcAddress = srcAddress;
  }

  /**
   * An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.
   **/
  public TrafficFilter dstAddress(List<String> dstAddress) {
    this.dstAddress = dstAddress;
    return this;
  }

  
  @ApiModelProperty(value = "An IP address or a range of IP addresses. For IPv4, the IP address could be an IP address plus mask, or an individual IP address, or a range of IP addresses. For IPv6, the IP address could be an IP prefix, or a range of IP prefixes.")
  @JsonProperty("dstAddress")
  public List<String> getDstAddress() {
    return dstAddress;
  }
  public void setDstAddress(List<String> dstAddress) {
    this.dstAddress = dstAddress;
  }

  /**
   * A port or a range of ports.
   **/
  public TrafficFilter srcPort(List<String> srcPort) {
    this.srcPort = srcPort;
    return this;
  }

  
  @ApiModelProperty(value = "A port or a range of ports.")
  @JsonProperty("srcPort")
  public List<String> getSrcPort() {
    return srcPort;
  }
  public void setSrcPort(List<String> srcPort) {
    this.srcPort = srcPort;
  }

  /**
   * A port or a range of ports.
   **/
  public TrafficFilter dstPort(List<String> dstPort) {
    this.dstPort = dstPort;
    return this;
  }

  
  @ApiModelProperty(value = "A port or a range of ports.")
  @JsonProperty("dstPort")
  public List<String> getDstPort() {
    return dstPort;
  }
  public void setDstPort(List<String> dstPort) {
    this.dstPort = dstPort;
  }

  /**
   * Specifies the protocol of the traffic rule.
   **/
  public TrafficFilter protocol(List<String> protocol) {
    this.protocol = protocol;
    return this;
  }

  
  @ApiModelProperty(value = "Specifies the protocol of the traffic rule.")
  @JsonProperty("protocol")
  public List<String> getProtocol() {
    return protocol;
  }
  public void setProtocol(List<String> protocol) {
    this.protocol = protocol;
  }

  /**
   * Used for token based traffic rule.
   **/
  public TrafficFilter token(List<String> token) {
    this.token = token;
    return this;
  }

  
  @ApiModelProperty(value = "Used for token based traffic rule.")
  @JsonProperty("token")
  public List<String> getToken() {
    return token;
  }
  public void setToken(List<String> token) {
    this.token = token;
  }

  /**
   * Used for GTP tunnel based traffic rule.
   **/
  public TrafficFilter srcTunnelAddress(List<String> srcTunnelAddress) {
    this.srcTunnelAddress = srcTunnelAddress;
    return this;
  }

  
  @ApiModelProperty(value = "Used for GTP tunnel based traffic rule.")
  @JsonProperty("srcTunnelAddress")
  public List<String> getSrcTunnelAddress() {
    return srcTunnelAddress;
  }
  public void setSrcTunnelAddress(List<String> srcTunnelAddress) {
    this.srcTunnelAddress = srcTunnelAddress;
  }

  /**
   * Used for GTP tunnel based traffic rule.
   **/
  public TrafficFilter dstTunnelAddress(List<String> dstTunnelAddress) {
    this.dstTunnelAddress = dstTunnelAddress;
    return this;
  }

  
  @ApiModelProperty(value = "Used for GTP tunnel based traffic rule.")
  @JsonProperty("dstTunnelAddress")
  public List<String> getDstTunnelAddress() {
    return dstTunnelAddress;
  }
  public void setDstTunnelAddress(List<String> dstTunnelAddress) {
    this.dstTunnelAddress = dstTunnelAddress;
  }

  /**
   * Used for GTP tunnel based traffic rule.
   **/
  public TrafficFilter srcTunnelPort(List<String> srcTunnelPort) {
    this.srcTunnelPort = srcTunnelPort;
    return this;
  }

  
  @ApiModelProperty(value = "Used for GTP tunnel based traffic rule.")
  @JsonProperty("srcTunnelPort")
  public List<String> getSrcTunnelPort() {
    return srcTunnelPort;
  }
  public void setSrcTunnelPort(List<String> srcTunnelPort) {
    this.srcTunnelPort = srcTunnelPort;
  }

  /**
   * Used for GTP tunnel based traffic rule.
   **/
  public TrafficFilter dstTunnelPort(List<String> dstTunnelPort) {
    this.dstTunnelPort = dstTunnelPort;
    return this;
  }

  
  @ApiModelProperty(value = "Used for GTP tunnel based traffic rule.")
  @JsonProperty("dstTunnelPort")
  public List<String> getDstTunnelPort() {
    return dstTunnelPort;
  }
  public void setDstTunnelPort(List<String> dstTunnelPort) {
    this.dstTunnelPort = dstTunnelPort;
  }

  /**
   * Used to match all packets that have the same QCI.
   **/
  public TrafficFilter qci(BigDecimal qci) {
    this.qci = qci;
    return this;
  }

  
  @ApiModelProperty(value = "Used to match all packets that have the same QCI.")
  @JsonProperty("qci")
  public BigDecimal getQci() {
    return qci;
  }
  public void setQci(BigDecimal qci) {
    this.qci = qci;
  }

  /**
   * Used to match all IPv4 packets that have the same DSCP.
   **/
  public TrafficFilter dscp(BigDecimal dscp) {
    this.dscp = dscp;
    return this;
  }

  
  @ApiModelProperty(value = "Used to match all IPv4 packets that have the same DSCP.")
  @JsonProperty("dscp")
  public BigDecimal getDscp() {
    return dscp;
  }
  public void setDscp(BigDecimal dscp) {
    this.dscp = dscp;
  }

  /**
   * Used to match all IPv6 packets that have the same TC.
   **/
  public TrafficFilter tc(BigDecimal tc) {
    this.tc = tc;
    return this;
  }

  
  @ApiModelProperty(value = "Used to match all IPv6 packets that have the same TC.")
  @JsonProperty("tc")
  public BigDecimal getTc() {
    return tc;
  }
  public void setTc(BigDecimal tc) {
    this.tc = tc;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrafficFilter trafficFilter = (TrafficFilter) o;
    return Objects.equals(srcAddress, trafficFilter.srcAddress) &&
        Objects.equals(dstAddress, trafficFilter.dstAddress) &&
        Objects.equals(srcPort, trafficFilter.srcPort) &&
        Objects.equals(dstPort, trafficFilter.dstPort) &&
        Objects.equals(protocol, trafficFilter.protocol) &&
        Objects.equals(token, trafficFilter.token) &&
        Objects.equals(srcTunnelAddress, trafficFilter.srcTunnelAddress) &&
        Objects.equals(dstTunnelAddress, trafficFilter.dstTunnelAddress) &&
        Objects.equals(srcTunnelPort, trafficFilter.srcTunnelPort) &&
        Objects.equals(dstTunnelPort, trafficFilter.dstTunnelPort) &&
        Objects.equals(qci, trafficFilter.qci) &&
        Objects.equals(dscp, trafficFilter.dscp) &&
        Objects.equals(tc, trafficFilter.tc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(srcAddress, dstAddress, srcPort, dstPort, protocol, token, srcTunnelAddress, dstTunnelAddress, srcTunnelPort, dstTunnelPort, qci, dscp, tc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrafficFilter {\n");
    
    sb.append("    srcAddress: ").append(toIndentedString(srcAddress)).append("\n");
    sb.append("    dstAddress: ").append(toIndentedString(dstAddress)).append("\n");
    sb.append("    srcPort: ").append(toIndentedString(srcPort)).append("\n");
    sb.append("    dstPort: ").append(toIndentedString(dstPort)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    srcTunnelAddress: ").append(toIndentedString(srcTunnelAddress)).append("\n");
    sb.append("    dstTunnelAddress: ").append(toIndentedString(dstTunnelAddress)).append("\n");
    sb.append("    srcTunnelPort: ").append(toIndentedString(srcTunnelPort)).append("\n");
    sb.append("    dstTunnelPort: ").append(toIndentedString(dstTunnelPort)).append("\n");
    sb.append("    qci: ").append(toIndentedString(qci)).append("\n");
    sb.append("    dscp: ").append(toIndentedString(dscp)).append("\n");
    sb.append("    tc: ").append(toIndentedString(tc)).append("\n");
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

