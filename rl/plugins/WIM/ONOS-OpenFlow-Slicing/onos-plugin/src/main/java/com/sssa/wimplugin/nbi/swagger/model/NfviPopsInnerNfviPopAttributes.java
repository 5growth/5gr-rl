package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint;
import com.sssa.wimplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesResourceZoneAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NfviPopsInnerNfviPopAttributes   {
  
  private @Valid String geographicalLocationInfo = null;
  private @Valid String vimId = null;
  private @Valid List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint = new ArrayList<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint>();
  private @Valid String nfviPopId = null;
  private @Valid List<NfviPopsInnerNfviPopAttributesResourceZoneAttributes> resourceZoneAttributes = new ArrayList<NfviPopsInnerNfviPopAttributesResourceZoneAttributes>();

  /**
   * It provides information about the geographic location (e.g. geographic coordinates or address of the building, etc.) of the NFVI resources that the VIM manages.
   **/
  public NfviPopsInnerNfviPopAttributes geographicalLocationInfo(String geographicalLocationInfo) {
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
   * Identification of the VIM.
   **/
  public NfviPopsInnerNfviPopAttributes vimId(String vimId) {
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

  /**
   **/
  public NfviPopsInnerNfviPopAttributes networkConnectivityEndpoint(List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkConnectivityEndpoint")
  @NotNull
  public List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> getNetworkConnectivityEndpoint() {
    return networkConnectivityEndpoint;
  }
  public void setNetworkConnectivityEndpoint(List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> networkConnectivityEndpoint) {
    this.networkConnectivityEndpoint = networkConnectivityEndpoint;
  }

  /**
   * Identification of the NFVI-PoP.
   **/
  public NfviPopsInnerNfviPopAttributes nfviPopId(String nfviPopId) {
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
   **/
  public NfviPopsInnerNfviPopAttributes resourceZoneAttributes(List<NfviPopsInnerNfviPopAttributesResourceZoneAttributes> resourceZoneAttributes) {
    this.resourceZoneAttributes = resourceZoneAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("resourceZoneAttributes")
  @NotNull
  public List<NfviPopsInnerNfviPopAttributesResourceZoneAttributes> getResourceZoneAttributes() {
    return resourceZoneAttributes;
  }
  public void setResourceZoneAttributes(List<NfviPopsInnerNfviPopAttributesResourceZoneAttributes> resourceZoneAttributes) {
    this.resourceZoneAttributes = resourceZoneAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributes nfviPopsInnerNfviPopAttributes = (NfviPopsInnerNfviPopAttributes) o;
    return Objects.equals(geographicalLocationInfo, nfviPopsInnerNfviPopAttributes.geographicalLocationInfo) &&
        Objects.equals(vimId, nfviPopsInnerNfviPopAttributes.vimId) &&
        Objects.equals(networkConnectivityEndpoint, nfviPopsInnerNfviPopAttributes.networkConnectivityEndpoint) &&
        Objects.equals(nfviPopId, nfviPopsInnerNfviPopAttributes.nfviPopId) &&
        Objects.equals(resourceZoneAttributes, nfviPopsInnerNfviPopAttributes.resourceZoneAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geographicalLocationInfo, vimId, networkConnectivityEndpoint, nfviPopId, resourceZoneAttributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributes {\n");
    
    sb.append("    geographicalLocationInfo: ").append(toIndentedString(geographicalLocationInfo)).append("\n");
    sb.append("    vimId: ").append(toIndentedString(vimId)).append("\n");
    sb.append("    networkConnectivityEndpoint: ").append(toIndentedString(networkConnectivityEndpoint)).append("\n");
    sb.append("    nfviPopId: ").append(toIndentedString(nfviPopId)).append("\n");
    sb.append("    resourceZoneAttributes: ").append(toIndentedString(resourceZoneAttributes)).append("\n");
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

