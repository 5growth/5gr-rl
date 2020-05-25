package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.VirtualLinksInnerVirtualLinkNetworkQoS;
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

public class VirtualLinksInnerVirtualLink   {
  
  private @Valid String virtualLinkId = null;
  private @Valid BigDecimal totalBandwidth = null;
  private @Valid BigDecimal availableBandwidth = null;
  private @Valid VirtualLinksInnerVirtualLinkNetworkQoS networkQoS = null;
  private @Valid String srcGwId = null;
  private @Valid Integer srcLinkId = null;
  private @Valid String dstGwId = null;
  private @Valid Integer dstLinkId = null;
  private @Valid String networkLayer = null;

  /**
   * (numbered) Identifier of the virtual link
   **/
  public VirtualLinksInnerVirtualLink virtualLinkId(String virtualLinkId) {
    this.virtualLinkId = virtualLinkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "(numbered) Identifier of the virtual link")
  @JsonProperty("virtualLinkId")
  @NotNull
  public String getVirtualLinkId() {
    return virtualLinkId;
  }
  public void setVirtualLinkId(String virtualLinkId) {
    this.virtualLinkId = virtualLinkId;
  }

  /**
   * Total bandwidth capacity supported by the logical link (in Mbps).
   **/
  public VirtualLinksInnerVirtualLink totalBandwidth(BigDecimal totalBandwidth) {
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
  public VirtualLinksInnerVirtualLink availableBandwidth(BigDecimal availableBandwidth) {
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
  public VirtualLinksInnerVirtualLink networkQoS(VirtualLinksInnerVirtualLinkNetworkQoS networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkQoS")
  @NotNull
  public VirtualLinksInnerVirtualLinkNetworkQoS getNetworkQoS() {
    return networkQoS;
  }
  public void setNetworkQoS(VirtualLinksInnerVirtualLinkNetworkQoS networkQoS) {
    this.networkQoS = networkQoS;
  }

  /**
   * 5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public VirtualLinksInnerVirtualLink srcGwId(String srcGwId) {
    this.srcGwId = srcGwId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  @JsonProperty("srcGwId")
  @NotNull
  public String getSrcGwId() {
    return srcGwId;
  }
  public void setSrcGwId(String srcGwId) {
    this.srcGwId = srcGwId;
  }

  /**
   * Local Logical Link Id.
   **/
  public VirtualLinksInnerVirtualLink srcLinkId(Integer srcLinkId) {
    this.srcLinkId = srcLinkId;
    return this;
  }

  
  @ApiModelProperty(value = "Local Logical Link Id.")
  @JsonProperty("srcLinkId")
  public Integer getSrcLinkId() {
    return srcLinkId;
  }
  public void setSrcLinkId(Integer srcLinkId) {
    this.srcLinkId = srcLinkId;
  }

  /**
   * 5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public VirtualLinksInnerVirtualLink dstGwId(String dstGwId) {
    this.dstGwId = dstGwId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  @JsonProperty("dstGwId")
  @NotNull
  public String getDstGwId() {
    return dstGwId;
  }
  public void setDstGwId(String dstGwId) {
    this.dstGwId = dstGwId;
  }

  /**
   * Remote Logical Link Id.
   **/
  public VirtualLinksInnerVirtualLink dstLinkId(Integer dstLinkId) {
    this.dstLinkId = dstLinkId;
    return this;
  }

  
  @ApiModelProperty(value = "Remote Logical Link Id.")
  @JsonProperty("dstLinkId")
  public Integer getDstLinkId() {
    return dstLinkId;
  }
  public void setDstLinkId(Integer dstLinkId) {
    this.dstLinkId = dstLinkId;
  }

  /**
   **/
  public VirtualLinksInnerVirtualLink networkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("networkLayer")
  public String getNetworkLayer() {
    return networkLayer;
  }
  public void setNetworkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualLinksInnerVirtualLink virtualLinksInnerVirtualLink = (VirtualLinksInnerVirtualLink) o;
    return Objects.equals(virtualLinkId, virtualLinksInnerVirtualLink.virtualLinkId) &&
        Objects.equals(totalBandwidth, virtualLinksInnerVirtualLink.totalBandwidth) &&
        Objects.equals(availableBandwidth, virtualLinksInnerVirtualLink.availableBandwidth) &&
        Objects.equals(networkQoS, virtualLinksInnerVirtualLink.networkQoS) &&
        Objects.equals(srcGwId, virtualLinksInnerVirtualLink.srcGwId) &&
        Objects.equals(srcLinkId, virtualLinksInnerVirtualLink.srcLinkId) &&
        Objects.equals(dstGwId, virtualLinksInnerVirtualLink.dstGwId) &&
        Objects.equals(dstLinkId, virtualLinksInnerVirtualLink.dstLinkId) &&
        Objects.equals(networkLayer, virtualLinksInnerVirtualLink.networkLayer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(virtualLinkId, totalBandwidth, availableBandwidth, networkQoS, srcGwId, srcLinkId, dstGwId, dstLinkId, networkLayer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualLinksInnerVirtualLink {\n");
    
    sb.append("    virtualLinkId: ").append(toIndentedString(virtualLinkId)).append("\n");
    sb.append("    totalBandwidth: ").append(toIndentedString(totalBandwidth)).append("\n");
    sb.append("    availableBandwidth: ").append(toIndentedString(availableBandwidth)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    srcGwId: ").append(toIndentedString(srcGwId)).append("\n");
    sb.append("    srcLinkId: ").append(toIndentedString(srcLinkId)).append("\n");
    sb.append("    dstGwId: ").append(toIndentedString(dstGwId)).append("\n");
    sb.append("    dstLinkId: ").append(toIndentedString(dstLinkId)).append("\n");
    sb.append("    networkLayer: ").append(toIndentedString(networkLayer)).append("\n");
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

