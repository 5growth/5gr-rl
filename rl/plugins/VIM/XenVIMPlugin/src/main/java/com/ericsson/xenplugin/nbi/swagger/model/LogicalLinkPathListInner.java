package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.LogicalLinkAttributes;
import com.ericsson.xenplugin.nbi.swagger.model.MetaData;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class LogicalLinkPathListInner   {
  
  private @Valid LogicalLinkAttributes logicalLinkAttributes = null;
  private @Valid BigDecimal reqBandwidth = null;
  private @Valid BigDecimal reqLatency = null;
  private @Valid MetaData metaData = null;

  /**
   **/
  public LogicalLinkPathListInner logicalLinkAttributes(LogicalLinkAttributes logicalLinkAttributes) {
    this.logicalLinkAttributes = logicalLinkAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("logicalLinkAttributes")
  @NotNull
  public LogicalLinkAttributes getLogicalLinkAttributes() {
    return logicalLinkAttributes;
  }
  public void setLogicalLinkAttributes(LogicalLinkAttributes logicalLinkAttributes) {
    this.logicalLinkAttributes = logicalLinkAttributes;
  }

  /**
   * requested bandwidth (in Mbps).
   **/
  public LogicalLinkPathListInner reqBandwidth(BigDecimal reqBandwidth) {
    this.reqBandwidth = reqBandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "requested bandwidth (in Mbps).")
  @JsonProperty("reqBandwidth")
  @NotNull
  public BigDecimal getReqBandwidth() {
    return reqBandwidth;
  }
  public void setReqBandwidth(BigDecimal reqBandwidth) {
    this.reqBandwidth = reqBandwidth;
  }

  /**
   * 5GT - requested maximum end-to-end latency (expressed in ms)
   **/
  public LogicalLinkPathListInner reqLatency(BigDecimal reqLatency) {
    this.reqLatency = reqLatency;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - requested maximum end-to-end latency (expressed in ms)")
  @JsonProperty("reqLatency")
  @NotNull
  public BigDecimal getReqLatency() {
    return reqLatency;
  }
  public void setReqLatency(BigDecimal reqLatency) {
    this.reqLatency = reqLatency;
  }

  /**
   **/
  public LogicalLinkPathListInner metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("metaData")
  public MetaData getMetaData() {
    return metaData;
  }
  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkPathListInner logicalLinkPathListInner = (LogicalLinkPathListInner) o;
    return Objects.equals(logicalLinkAttributes, logicalLinkPathListInner.logicalLinkAttributes) &&
        Objects.equals(reqBandwidth, logicalLinkPathListInner.reqBandwidth) &&
        Objects.equals(reqLatency, logicalLinkPathListInner.reqLatency) &&
        Objects.equals(metaData, logicalLinkPathListInner.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkAttributes, reqBandwidth, reqLatency, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkPathListInner {\n");
    
    sb.append("    logicalLinkAttributes: ").append(toIndentedString(logicalLinkAttributes)).append("\n");
    sb.append("    reqBandwidth: ").append(toIndentedString(reqBandwidth)).append("\n");
    sb.append("    reqLatency: ").append(toIndentedString(reqLatency)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
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

