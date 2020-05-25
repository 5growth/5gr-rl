package com.rl.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NfviPop   {
  
  private @Valid String geographicalLocationInfo = null;
  private @Valid String networkConnectivityEndpoint = null;
  private @Valid String nfviPopId = null;
  private @Valid String vimId = null;

  /**
   * It provides information about the geographic location (e.g. geographic coordinates or address of the building, etc.) of the NFVI resources that the VIM manages.
   **/
  public NfviPop geographicalLocationInfo(String geographicalLocationInfo) {
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
   * Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.
   **/
  public NfviPop networkConnectivityEndpoint(String networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.")
  @JsonProperty("networkConnectivityEndpoint")
  @NotNull
  public String getNetworkConnectivityEndpoint() {
    return networkConnectivityEndpoint;
  }
  public void setNetworkConnectivityEndpoint(String networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
  }

  /**
   * Identification of the NFVI-PoP.
   **/
  public NfviPop nfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the NFVI-PoP.")
  @JsonProperty("nfviPopId")
  @NotNull
  public String getNfviPopId() {
    return nfviPopId;
  }
  public void setNfviPopId(String nfviPopId) {
    this.nfviPopId = nfviPopId;
  }

  /**
   * Identification of the VIM.
   **/
  public NfviPop vimId(String vimId) {
    this.vimId = vimId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the VIM.")
  @JsonProperty("vimId")
  @NotNull
  public String getVimId() {
    return vimId;
  }
  public void setVimId(String vimId) {
    this.vimId = vimId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPop nfviPop = (NfviPop) o;
    return Objects.equals(geographicalLocationInfo, nfviPop.geographicalLocationInfo) &&
        Objects.equals(networkConnectivityEndpoint, nfviPop.networkConnectivityEndpoint) &&
        Objects.equals(nfviPopId, nfviPop.nfviPopId) &&
        Objects.equals(vimId, nfviPop.vimId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geographicalLocationInfo, networkConnectivityEndpoint, nfviPopId, vimId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPop {\n");
    
    sb.append("    geographicalLocationInfo: ").append(toIndentedString(geographicalLocationInfo)).append("\n");
    sb.append("    networkConnectivityEndpoint: ").append(toIndentedString(networkConnectivityEndpoint)).append("\n");
    sb.append("    nfviPopId: ").append(toIndentedString(nfviPopId)).append("\n");
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
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

