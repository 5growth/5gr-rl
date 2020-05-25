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

public class LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS   {
  
  private @Valid BigDecimal linkCostValue = null;
  private @Valid String linkCost = null;
  private @Valid BigDecimal linkDelayValue = null;
  private @Valid String linkDelay = null;

  /**
   * 5GT - Value of Link Cost
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkCostValue(BigDecimal linkCostValue) {
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
   * 5GT - Link cost name .
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkCost(String linkCost) {
    this.linkCost = linkCost;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Link cost name .")
  @JsonProperty("linkCost")
  @NotNull
  public String getLinkCost() {
    return linkCost;
  }
  public void setLinkCost(String linkCost) {
    this.linkCost = linkCost;
  }

  /**
   * 5GT - Value of Link Delay (expressed in ms)
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkDelayValue(BigDecimal linkDelayValue) {
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
   * 5GT - Link delay name.
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkDelay(String linkDelay) {
    this.linkDelay = linkDelay;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Link delay name.")
  @JsonProperty("linkDelay")
  @NotNull
  public String getLinkDelay() {
    return linkDelay;
  }
  public void setLinkDelay(String linkDelay) {
    this.linkDelay = linkDelay;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS logicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS = (LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS) o;
    return Objects.equals(linkCostValue, logicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS.linkCostValue) &&
        Objects.equals(linkCost, logicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS.linkCost) &&
        Objects.equals(linkDelayValue, logicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS.linkDelayValue) &&
        Objects.equals(linkDelay, logicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS.linkDelay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(linkCostValue, linkCost, linkDelayValue, linkDelay);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS {\n");
    
    sb.append("    linkCostValue: ").append(toIndentedString(linkCostValue)).append("\n");
    sb.append("    linkCost: ").append(toIndentedString(linkCost)).append("\n");
    sb.append("    linkDelayValue: ").append(toIndentedString(linkDelayValue)).append("\n");
    sb.append("    linkDelay: ").append(toIndentedString(linkDelay)).append("\n");
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

