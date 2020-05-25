package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.")

public class NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint   {
  
  private @Valid String netGwIpAddress = null;

  /**
   * 5GT - Reachable Gw IPv4 Address in terms of A.B.C.D (/32).
   **/
  public NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint netGwIpAddress(String netGwIpAddress) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint nfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint = (NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint) o;
    return Objects.equals(netGwIpAddress, nfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint.netGwIpAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(netGwIpAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint {\n");
    
    sb.append("    netGwIpAddress: ").append(toIndentedString(netGwIpAddress)).append("\n");
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

