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


package com.rl.extinterface.nbi.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.
 */
@ApiModel(description = "Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint {
  @SerializedName("netGwIpAddress")
  private String netGwIpAddress = null;

  public NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint netGwIpAddress(String netGwIpAddress) {
    this.netGwIpAddress = netGwIpAddress;
    return this;
  }

   /**
   * 5GT - Reachable Gw IPv4 Address in terms of A.B.C.D (/32).
   * @return netGwIpAddress
  **/
  @ApiModelProperty(required = true, value = "5GT - Reachable Gw IPv4 Address in terms of A.B.C.D (/32).")
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
    return Objects.equals(this.netGwIpAddress, nfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint.netGwIpAddress);
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

