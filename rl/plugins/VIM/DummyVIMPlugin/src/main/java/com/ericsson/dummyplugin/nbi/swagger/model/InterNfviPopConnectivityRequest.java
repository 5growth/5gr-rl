package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.LogicalLinkPathList;
import com.ericsson.dummyplugin.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InterNfviPopConnectivityRequest   {
  
  private @Valid LogicalLinkPathList logicalLinkPathList = null;
  private @Valid String networkLayer = null;
  private @Valid String interNfviPopNetworkType = null;
  private @Valid MetaData metaData = null;

  /**
   **/
  public InterNfviPopConnectivityRequest logicalLinkPathList(LogicalLinkPathList logicalLinkPathList) {
    this.logicalLinkPathList = logicalLinkPathList;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("logicalLinkPathList")
  @NotNull
  public LogicalLinkPathList getLogicalLinkPathList() {
    return logicalLinkPathList;
  }
  public void setLogicalLinkPathList(LogicalLinkPathList logicalLinkPathList) {
    this.logicalLinkPathList = logicalLinkPathList;
  }

  /**
   **/
  public InterNfviPopConnectivityRequest networkLayer(String networkLayer) {
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
  public InterNfviPopConnectivityRequest interNfviPopNetworkType(String interNfviPopNetworkType) {
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
  public InterNfviPopConnectivityRequest metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("metaData")
  @NotNull
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
    InterNfviPopConnectivityRequest interNfviPopConnectivityRequest = (InterNfviPopConnectivityRequest) o;
    return Objects.equals(logicalLinkPathList, interNfviPopConnectivityRequest.logicalLinkPathList) &&
        Objects.equals(networkLayer, interNfviPopConnectivityRequest.networkLayer) &&
        Objects.equals(interNfviPopNetworkType, interNfviPopConnectivityRequest.interNfviPopNetworkType) &&
        Objects.equals(metaData, interNfviPopConnectivityRequest.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkPathList, networkLayer, interNfviPopNetworkType, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InterNfviPopConnectivityRequest {\n");
    
    sb.append("    logicalLinkPathList: ").append(toIndentedString(logicalLinkPathList)).append("\n");
    sb.append("    networkLayer: ").append(toIndentedString(networkLayer)).append("\n");
    sb.append("    interNfviPopNetworkType: ").append(toIndentedString(interNfviPopNetworkType)).append("\n");
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

