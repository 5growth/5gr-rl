package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about network connectivity endpoints to the NFVI-PoP that the WIM manage.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about network connectivity endpoints to the NFVI-PoP that the WIM manage.")

public class GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint   {
  
  private @Valid String netGwIpAddress = null;
  private @Valid BigDecimal netGwInterfceId = null;

  /**
   * 5GT - Reachable Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint netGwIpAddress(String netGwIpAddress) {
    this.netGwIpAddress = netGwIpAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Reachable Gw IPv4 Address in terms of A.B.C.D (/32).")
  @JsonProperty("netGwIpAddress")
  @NotNull
  public String getNetGwIpAddress() {
    return netGwIpAddress;
  }
  public void setNetGwIpAddress(String netGwIpAddress) {
    this.netGwIpAddress = netGwIpAddress;
  }

  /**
   * 5GT - Reachable Gw interface ID.
   **/
  public GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint netGwInterfceId(BigDecimal netGwInterfceId) {
    this.netGwInterfceId = netGwInterfceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "5GT - Reachable Gw interface ID.")
  @JsonProperty("netGwInterfceId")
  @NotNull
  public BigDecimal getNetGwInterfceId() {
    return netGwInterfceId;
  }
  public void setNetGwInterfceId(BigDecimal netGwInterfceId) {
    this.netGwInterfceId = netGwInterfceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint gatewaysInnerGatewayAttributesNetworkConnectivityEndpoint = (GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint) o;
    return Objects.equals(netGwIpAddress, gatewaysInnerGatewayAttributesNetworkConnectivityEndpoint.netGwIpAddress) &&
        Objects.equals(netGwInterfceId, gatewaysInnerGatewayAttributesNetworkConnectivityEndpoint.netGwInterfceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(netGwIpAddress, netGwInterfceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint {\n");
    
    sb.append("    netGwIpAddress: ").append(toIndentedString(netGwIpAddress)).append("\n");
    sb.append("    netGwInterfceId: ").append(toIndentedString(netGwInterfceId)).append("\n");
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

