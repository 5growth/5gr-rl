package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * 5GT - Element providing information the attributes related to particular logical links between pair of NFVI-PoPs.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "5GT - Element providing information the attributes related to particular logical links between pair of NFVI-PoPs.")

public class LogicalLinkInterNfviPopsInnerLogicalLinks   {
  
  private @Valid String logicalLinkId = null;
  private @Valid BigDecimal totalBandwidth = null;
  private @Valid BigDecimal availableBandwidth = null;
  private @Valid LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS networkQoS = null;
  private @Valid String srcGwIpAddress = null;
  private @Valid Integer localLinkId = null;
  private @Valid String dstGwIpAddress = null;
  private @Valid Integer remoteLinkId = null;
  private @Valid String networkLayer = null;
  private @Valid String interNfviPopNetworkType = null;
  private @Valid String interNfviPopNetworkTopology = null;

  /**
   * (numbered) Identifier of the logical link
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks logicalLinkId(String logicalLinkId) {
    this.logicalLinkId = logicalLinkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "(numbered) Identifier of the logical link")
  @JsonProperty("logicalLinkId")
  @NotNull
  public String getLogicalLinkId() {
    return logicalLinkId;
  }
  public void setLogicalLinkId(String logicalLinkId) {
    this.logicalLinkId = logicalLinkId;
  }

  /**
   * Total bandwidth capacity supported by the logical link (in Mbps).
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks totalBandwidth(BigDecimal totalBandwidth) {
    this.totalBandwidth = totalBandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Total bandwidth capacity supported by the logical link (in Mbps).")
  @JsonProperty("totalBandwidth")
  @NotNull
  public BigDecimal getTotalBandwidth() {
    return totalBandwidth;
  }
  public void setTotalBandwidth(BigDecimal totalBandwidth) {
    this.totalBandwidth = totalBandwidth;
  }

  /**
   * Available bandwidth capacity supported by the logical link (in Mbps).
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks availableBandwidth(BigDecimal availableBandwidth) {
    this.availableBandwidth = availableBandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Available bandwidth capacity supported by the logical link (in Mbps).")
  @JsonProperty("availableBandwidth")
  @NotNull
  public BigDecimal getAvailableBandwidth() {
    return availableBandwidth;
  }
  public void setAvailableBandwidth(BigDecimal availableBandwidth) {
    this.availableBandwidth = availableBandwidth;
  }

  /**
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks networkQoS(LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkQoS")
  @NotNull
  public LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS getNetworkQoS() {
    return networkQoS;
  }
  public void setNetworkQoS(LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS networkQoS) {
    this.networkQoS = networkQoS;
  }

  /**
   * 5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks srcGwIpAddress(String srcGwIpAddress) {
    this.srcGwIpAddress = srcGwIpAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  @JsonProperty("srcGwIpAddress")
  @NotNull
  public String getSrcGwIpAddress() {
    return srcGwIpAddress;
  }
  public void setSrcGwIpAddress(String srcGwIpAddress) {
    this.srcGwIpAddress = srcGwIpAddress;
  }

  /**
   * Local Logical Link Id.
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks localLinkId(Integer localLinkId) {
    this.localLinkId = localLinkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Local Logical Link Id.")
  @JsonProperty("localLinkId")
  @NotNull
  public Integer getLocalLinkId() {
    return localLinkId;
  }
  public void setLocalLinkId(Integer localLinkId) {
    this.localLinkId = localLinkId;
  }

  /**
   * 5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks dstGwIpAddress(String dstGwIpAddress) {
    this.dstGwIpAddress = dstGwIpAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  @JsonProperty("dstGwIpAddress")
  @NotNull
  public String getDstGwIpAddress() {
    return dstGwIpAddress;
  }
  public void setDstGwIpAddress(String dstGwIpAddress) {
    this.dstGwIpAddress = dstGwIpAddress;
  }

  /**
   * Remote Logical Link Id.
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks remoteLinkId(Integer remoteLinkId) {
    this.remoteLinkId = remoteLinkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Remote Logical Link Id.")
  @JsonProperty("remoteLinkId")
  @NotNull
  public Integer getRemoteLinkId() {
    return remoteLinkId;
  }
  public void setRemoteLinkId(Integer remoteLinkId) {
    this.remoteLinkId = remoteLinkId;
  }

  /**
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks networkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkLayer")
  @NotNull
  public String getNetworkLayer() {
    return networkLayer;
  }
  public void setNetworkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
  }

  /**
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks interNfviPopNetworkType(String interNfviPopNetworkType) {
    this.interNfviPopNetworkType = interNfviPopNetworkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("interNfviPopNetworkType")
  @NotNull
  public String getInterNfviPopNetworkType() {
    return interNfviPopNetworkType;
  }
  public void setInterNfviPopNetworkType(String interNfviPopNetworkType) {
    this.interNfviPopNetworkType = interNfviPopNetworkType;
  }

  /**
   **/
  public LogicalLinkInterNfviPopsInnerLogicalLinks interNfviPopNetworkTopology(String interNfviPopNetworkTopology) {
    this.interNfviPopNetworkTopology = interNfviPopNetworkTopology;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("interNfviPopNetworkTopology")
  @NotNull
  public String getInterNfviPopNetworkTopology() {
    return interNfviPopNetworkTopology;
  }
  public void setInterNfviPopNetworkTopology(String interNfviPopNetworkTopology) {
    this.interNfviPopNetworkTopology = interNfviPopNetworkTopology;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkInterNfviPopsInnerLogicalLinks logicalLinkInterNfviPopsInnerLogicalLinks = (LogicalLinkInterNfviPopsInnerLogicalLinks) o;
    return Objects.equals(logicalLinkId, logicalLinkInterNfviPopsInnerLogicalLinks.logicalLinkId) &&
        Objects.equals(totalBandwidth, logicalLinkInterNfviPopsInnerLogicalLinks.totalBandwidth) &&
        Objects.equals(availableBandwidth, logicalLinkInterNfviPopsInnerLogicalLinks.availableBandwidth) &&
        Objects.equals(networkQoS, logicalLinkInterNfviPopsInnerLogicalLinks.networkQoS) &&
        Objects.equals(srcGwIpAddress, logicalLinkInterNfviPopsInnerLogicalLinks.srcGwIpAddress) &&
        Objects.equals(localLinkId, logicalLinkInterNfviPopsInnerLogicalLinks.localLinkId) &&
        Objects.equals(dstGwIpAddress, logicalLinkInterNfviPopsInnerLogicalLinks.dstGwIpAddress) &&
        Objects.equals(remoteLinkId, logicalLinkInterNfviPopsInnerLogicalLinks.remoteLinkId) &&
        Objects.equals(networkLayer, logicalLinkInterNfviPopsInnerLogicalLinks.networkLayer) &&
        Objects.equals(interNfviPopNetworkType, logicalLinkInterNfviPopsInnerLogicalLinks.interNfviPopNetworkType) &&
        Objects.equals(interNfviPopNetworkTopology, logicalLinkInterNfviPopsInnerLogicalLinks.interNfviPopNetworkTopology);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkId, totalBandwidth, availableBandwidth, networkQoS, srcGwIpAddress, localLinkId, dstGwIpAddress, remoteLinkId, networkLayer, interNfviPopNetworkType, interNfviPopNetworkTopology);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkInterNfviPopsInnerLogicalLinks {\n");
    
    sb.append("    logicalLinkId: ").append(toIndentedString(logicalLinkId)).append("\n");
    sb.append("    totalBandwidth: ").append(toIndentedString(totalBandwidth)).append("\n");
    sb.append("    availableBandwidth: ").append(toIndentedString(availableBandwidth)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    srcGwIpAddress: ").append(toIndentedString(srcGwIpAddress)).append("\n");
    sb.append("    localLinkId: ").append(toIndentedString(localLinkId)).append("\n");
    sb.append("    dstGwIpAddress: ").append(toIndentedString(dstGwIpAddress)).append("\n");
    sb.append("    remoteLinkId: ").append(toIndentedString(remoteLinkId)).append("\n");
    sb.append("    networkLayer: ").append(toIndentedString(networkLayer)).append("\n");
    sb.append("    interNfviPopNetworkType: ").append(toIndentedString(interNfviPopNetworkType)).append("\n");
    sb.append("    interNfviPopNetworkTopology: ").append(toIndentedString(interNfviPopNetworkTopology)).append("\n");
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

