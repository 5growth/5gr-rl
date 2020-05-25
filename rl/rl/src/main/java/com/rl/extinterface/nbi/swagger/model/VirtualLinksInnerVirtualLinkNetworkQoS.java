package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * 5GT - Element providing information about Quality of Service attributes (cost and delay) that the network shall support.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "5GT - Element providing information about Quality of Service attributes (cost and delay) that the network shall support.")

public class VirtualLinksInnerVirtualLinkNetworkQoS   {
  
  private @Valid BigDecimal linkCostValue = null;
  private @Valid BigDecimal linkDelayValue = null;
  private @Valid BigDecimal packetLossRate = null;

  /**
   * 5GT - Value of Link Cost
   **/
  public VirtualLinksInnerVirtualLinkNetworkQoS linkCostValue(BigDecimal linkCostValue) {
    this.linkCostValue = linkCostValue;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Value of Link Cost")
  @JsonProperty("linkCostValue")
  @NotNull
  public BigDecimal getLinkCostValue() {
    return linkCostValue;
  }
  public void setLinkCostValue(BigDecimal linkCostValue) {
    this.linkCostValue = linkCostValue;
  }

  /**
   * 5GT - Value of Link Delay (expressed in ms)
   **/
  public VirtualLinksInnerVirtualLinkNetworkQoS linkDelayValue(BigDecimal linkDelayValue) {
    this.linkDelayValue = linkDelayValue;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Value of Link Delay (expressed in ms)")
  @JsonProperty("linkDelayValue")
  @NotNull
  public BigDecimal getLinkDelayValue() {
    return linkDelayValue;
  }
  public void setLinkDelayValue(BigDecimal linkDelayValue) {
    this.linkDelayValue = linkDelayValue;
  }

  /**
   * 5GT - Link packet loss rate.
   **/
  public VirtualLinksInnerVirtualLinkNetworkQoS packetLossRate(BigDecimal packetLossRate) {
    this.packetLossRate = packetLossRate;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Link packet loss rate.")
  @JsonProperty("packetLossRate")
  @NotNull
  public BigDecimal getPacketLossRate() {
    return packetLossRate;
  }
  public void setPacketLossRate(BigDecimal packetLossRate) {
    this.packetLossRate = packetLossRate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinksInnerVirtualLinkNetworkQoS virtualLinksInnerVirtualLinkNetworkQoS = (VirtualLinksInnerVirtualLinkNetworkQoS) o;
    return Objects.equals(linkCostValue, virtualLinksInnerVirtualLinkNetworkQoS.linkCostValue) &&
        Objects.equals(linkDelayValue, virtualLinksInnerVirtualLinkNetworkQoS.linkDelayValue) &&
        Objects.equals(packetLossRate, virtualLinksInnerVirtualLinkNetworkQoS.packetLossRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(linkCostValue, linkDelayValue, packetLossRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinksInnerVirtualLinkNetworkQoS {\n");
    
    sb.append("    linkCostValue: ").append(toIndentedString(linkCostValue)).append("\n");
    sb.append("    linkDelayValue: ").append(toIndentedString(linkDelayValue)).append("\n");
    sb.append("    packetLossRate: ").append(toIndentedString(packetLossRate)).append("\n");
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

