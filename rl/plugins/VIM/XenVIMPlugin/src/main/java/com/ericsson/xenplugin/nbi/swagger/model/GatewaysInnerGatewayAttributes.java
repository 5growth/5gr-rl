package com.ericsson.xenplugin.nbi.swagger.model;

import com.ericsson.xenplugin.nbi.swagger.model.GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GatewaysInnerGatewayAttributes   {
  
  private @Valid String geographicalLocationInfo = null;
  private @Valid String wimId = null;
  private @Valid List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint = new ArrayList<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint>();
  private @Valid String gatewayId = null;

  /**
   * It provides information about the geographic location (e.g. geographic coordinates or address of the building, etc.) of the NFVI resources that the VIM manages.
   **/
  public GatewaysInnerGatewayAttributes geographicalLocationInfo(String geographicalLocationInfo) {
    this.geographicalLocationInfo = geographicalLocationInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It provides information about the geographic location (e.g. geographic coordinates or address of the building, etc.) of the NFVI resources that the VIM manages.")
  @JsonProperty("geographicalLocationInfo")
  @NotNull
  public String getGeographicalLocationInfo() {
    return geographicalLocationInfo;
  }
  public void setGeographicalLocationInfo(String geographicalLocationInfo) {
    this.geographicalLocationInfo = geographicalLocationInfo;
  }

  /**
   * Identification of the WIM.
   **/
  public GatewaysInnerGatewayAttributes wimId(String wimId) {
    this.wimId = wimId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the WIM.")
  @JsonProperty("wimId")
  @NotNull
  public String getWimId() {
    return wimId;
  }
  public void setWimId(String wimId) {
    this.wimId = wimId;
  }

  /**
   **/
  public GatewaysInnerGatewayAttributes networkConnectivityEndpoint(List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkConnectivityEndpoint")
  @NotNull
  public List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> getNetworkConnectivityEndpoint() {
    return networkConnectivityEndpoint;
  }
  public void setNetworkConnectivityEndpoint(List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
  }

  /**
   * Identification of the gateway in the format IPv4.
   **/
  public GatewaysInnerGatewayAttributes gatewayId(String gatewayId) {
    this.gatewayId = gatewayId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the gateway in the format IPv4.")
  @JsonProperty("gatewayId")
  @NotNull
  public String getGatewayId() {
    return gatewayId;
  }
  public void setGatewayId(String gatewayId) {
    this.gatewayId = gatewayId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GatewaysInnerGatewayAttributes gatewaysInnerGatewayAttributes = (GatewaysInnerGatewayAttributes) o;
    return Objects.equals(geographicalLocationInfo, gatewaysInnerGatewayAttributes.geographicalLocationInfo) &&
        Objects.equals(wimId, gatewaysInnerGatewayAttributes.wimId) &&
        Objects.equals(networkConnectivityEndpoint, gatewaysInnerGatewayAttributes.networkConnectivityEndpoint) &&
        Objects.equals(gatewayId, gatewaysInnerGatewayAttributes.gatewayId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geographicalLocationInfo, wimId, networkConnectivityEndpoint, gatewayId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GatewaysInnerGatewayAttributes {\n");
    
    sb.append("    geographicalLocationInfo: ").append(toIndentedString(geographicalLocationInfo)).append("\n");
    sb.append("    wimId: ").append(toIndentedString(wimId)).append("\n");
    sb.append("    networkConnectivityEndpoint: ").append(toIndentedString(networkConnectivityEndpoint)).append("\n");
    sb.append("    gatewayId: ").append(toIndentedString(gatewayId)).append("\n");
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

