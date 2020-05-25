package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * 5GT - inter-Nfvi-Pop connectivity link.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "5GT - inter-Nfvi-Pop connectivity link.")

public class LogicalLinkAttributes   {
  
  private @Valid String logicalLinkId = null;
  private @Valid String srcGwIpAddress = null;
  private @Valid Integer localLinkId = null;
  private @Valid String dstGwIpAddress = null;
  private @Valid Integer remoteLinkId = null;

  /**
   * (numbered) Identifier of the logical link
   **/
  public LogicalLinkAttributes logicalLinkId(String logicalLinkId) {
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
   * 5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public LogicalLinkAttributes srcGwIpAddress(String srcGwIpAddress) {
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
  public LogicalLinkAttributes localLinkId(Integer localLinkId) {
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
  public LogicalLinkAttributes dstGwIpAddress(String dstGwIpAddress) {
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
  public LogicalLinkAttributes remoteLinkId(Integer remoteLinkId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkAttributes logicalLinkAttributes = (LogicalLinkAttributes) o;
    return Objects.equals(logicalLinkId, logicalLinkAttributes.logicalLinkId) &&
        Objects.equals(srcGwIpAddress, logicalLinkAttributes.srcGwIpAddress) &&
        Objects.equals(localLinkId, logicalLinkAttributes.localLinkId) &&
        Objects.equals(dstGwIpAddress, logicalLinkAttributes.dstGwIpAddress) &&
        Objects.equals(remoteLinkId, logicalLinkAttributes.remoteLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkId, srcGwIpAddress, localLinkId, dstGwIpAddress, remoteLinkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkAttributes {\n");
    
    sb.append("    logicalLinkId: ").append(toIndentedString(logicalLinkId)).append("\n");
    sb.append("    srcGwIpAddress: ").append(toIndentedString(srcGwIpAddress)).append("\n");
    sb.append("    localLinkId: ").append(toIndentedString(localLinkId)).append("\n");
    sb.append("    dstGwIpAddress: ").append(toIndentedString(dstGwIpAddress)).append("\n");
    sb.append("    remoteLinkId: ").append(toIndentedString(remoteLinkId)).append("\n");
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

