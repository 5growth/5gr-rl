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


package com.rl.extinterface.nbi.swagger.model;;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

/**
 * 5GT - Element providing information about Quality of Service attributes (cost and delay) that the network shall support.
 */
@ApiModel(description = "5GT - Element providing information about Quality of Service attributes (cost and delay) that the network shall support.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-04-08T16:03:30.222Z")
public class VirtualLinksInnerVirtualLinkNetworkQoS {
  @SerializedName("linkCostValue")
  private BigDecimal linkCostValue = null;

  @SerializedName("linkDelayValue")
  private BigDecimal linkDelayValue = null;

  @SerializedName("packetLossRate")
  private BigDecimal packetLossRate = null;

  public VirtualLinksInnerVirtualLinkNetworkQoS linkCostValue(BigDecimal linkCostValue) {
    this.linkCostValue = linkCostValue;
    return this;
  }

   /**
   * 5GT - Value of Link Cost
   * @return linkCostValue
  **/
  @ApiModelProperty(required = true, value = "5GT - Value of Link Cost")
  public BigDecimal getLinkCostValue() {
    return linkCostValue;
  }

  public void setLinkCostValue(BigDecimal linkCostValue) {
    this.linkCostValue = linkCostValue;
  }

  public VirtualLinksInnerVirtualLinkNetworkQoS linkDelayValue(BigDecimal linkDelayValue) {
    this.linkDelayValue = linkDelayValue;
    return this;
  }

   /**
   * 5GT - Value of Link Delay (expressed in ms)
   * @return linkDelayValue
  **/
  @ApiModelProperty(required = true, value = "5GT - Value of Link Delay (expressed in ms)")
  public BigDecimal getLinkDelayValue() {
    return linkDelayValue;
  }

  public void setLinkDelayValue(BigDecimal linkDelayValue) {
    this.linkDelayValue = linkDelayValue;
  }

  public VirtualLinksInnerVirtualLinkNetworkQoS packetLossRate(BigDecimal packetLossRate) {
    this.packetLossRate = packetLossRate;
    return this;
  }

   /**
   * 5GT - Link packet loss rate.
   * @return packetLossRate
  **/
  @ApiModelProperty(required = true, value = "5GT - Link packet loss rate.")
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
    return Objects.equals(this.linkCostValue, virtualLinksInnerVirtualLinkNetworkQoS.linkCostValue) &&
        Objects.equals(this.linkDelayValue, virtualLinksInnerVirtualLinkNetworkQoS.linkDelayValue) &&
        Objects.equals(this.packetLossRate, virtualLinksInnerVirtualLinkNetworkQoS.packetLossRate);
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
