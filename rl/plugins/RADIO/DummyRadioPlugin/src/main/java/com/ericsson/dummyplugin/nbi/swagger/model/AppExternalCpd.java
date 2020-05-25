package com.ericsson.dummyplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class AppExternalCpd   {
  
  private @Valid String cpdId = null;

public enum LayerProtocolEnum {

    ETHERNET(String.valueOf("Ethernet")), MPLS(String.valueOf("MPLS")), ODU2(String.valueOf("ODU2")), IPV4(String.valueOf("IPV4")), IPV6(String.valueOf("IPV6")), PSEUDO_WIRE(String.valueOf("Pseudo-Wire"));


    private String value;

    LayerProtocolEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static LayerProtocolEnum fromValue(String v) {
        for (LayerProtocolEnum b : LayerProtocolEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid LayerProtocolEnum layerProtocol = null;
  private @Valid String cpRole = null;
  private @Valid String description = null;
  private @Valid List<AddressData> addressData = new ArrayList<AddressData>();
  private @Valid List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<VirtualNetworkInterfaceRequirements>();

  /**
   * Identifier of this cpd information element.
   **/
  public AppExternalCpd cpdId(String cpdId) {
    this.cpdId = cpdId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of this cpd information element.")
  @JsonProperty("cpdId")
  @NotNull
  public String getCpdId() {
    return cpdId;
  }
  public void setCpdId(String cpdId) {
    this.cpdId = cpdId;
  }

  /**
   * Identifies which protocol the CP uses for connectivity purposes (Ethernet, MPLS, ODU2, IPV4, IPV6, Pseudo-Wire, etc.).
   **/
  public AppExternalCpd layerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifies which protocol the CP uses for connectivity purposes (Ethernet, MPLS, ODU2, IPV4, IPV6, Pseudo-Wire, etc.).")
  @JsonProperty("layerProtocol")
  @NotNull
  public LayerProtocolEnum getLayerProtocol() {
    return layerProtocol;
  }
  public void setLayerProtocol(LayerProtocolEnum layerProtocol) {
    this.layerProtocol = layerProtocol;
  }

  /**
   * Identifies the role of the port in the context of the traffic flow patterns in the VNF or parent NS. For example a VNF with a tree flow pattern within the VNF will have legal cpRoles of ROOT and LEAF.
   **/
  public AppExternalCpd cpRole(String cpRole) {
    this.cpRole = cpRole;
    return this;
  }

  
  @ApiModelProperty(value = "Identifies the role of the port in the context of the traffic flow patterns in the VNF or parent NS. For example a VNF with a tree flow pattern within the VNF will have legal cpRoles of ROOT and LEAF.")
  @JsonProperty("cpRole")
  public String getCpRole() {
    return cpRole;
  }
  public void setCpRole(String cpRole) {
    this.cpRole = cpRole;
  }

  /**
   * Provides human-readable information on the purpose of the CP (e.g. CP for control plane traffic).
   **/
  public AppExternalCpd description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(value = "Provides human-readable information on the purpose of the CP (e.g. CP for control plane traffic).")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Provides information on the addresses to be assigned to the CP(s) instantiated from this CPD.
   **/
  public AppExternalCpd addressData(List<AddressData> addressData) {
    this.addressData = addressData;
    return this;
  }

  
  @ApiModelProperty(value = "Provides information on the addresses to be assigned to the CP(s) instantiated from this CPD.")
  @JsonProperty("addressData")
  public List<AddressData> getAddressData() {
    return addressData;
  }
  public void setAddressData(List<AddressData> addressData) {
    this.addressData = addressData;
  }

  /**
   * Specifies requirements on a virtual network interface realizing the CPs instantiated from this CPD.
   **/
  public AppExternalCpd virtualNetworkInterfaceRequirements(List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
    this.virtualNetworkInterfaceRequirements = virtualNetworkInterfaceRequirements;
    return this;
  }

  
  @ApiModelProperty(value = "Specifies requirements on a virtual network interface realizing the CPs instantiated from this CPD.")
  @JsonProperty("virtualNetworkInterfaceRequirements")
  public List<VirtualNetworkInterfaceRequirements> getVirtualNetworkInterfaceRequirements() {
    return virtualNetworkInterfaceRequirements;
  }
  public void setVirtualNetworkInterfaceRequirements(List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
    this.virtualNetworkInterfaceRequirements = virtualNetworkInterfaceRequirements;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppExternalCpd appExternalCpd = (AppExternalCpd) o;
    return Objects.equals(cpdId, appExternalCpd.cpdId) &&
        Objects.equals(layerProtocol, appExternalCpd.layerProtocol) &&
        Objects.equals(cpRole, appExternalCpd.cpRole) &&
        Objects.equals(description, appExternalCpd.description) &&
        Objects.equals(addressData, appExternalCpd.addressData) &&
        Objects.equals(virtualNetworkInterfaceRequirements, appExternalCpd.virtualNetworkInterfaceRequirements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdId, layerProtocol, cpRole, description, addressData, virtualNetworkInterfaceRequirements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppExternalCpd {\n");
    
    sb.append("    cpdId: ").append(toIndentedString(cpdId)).append("\n");
    sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
    sb.append("    cpRole: ").append(toIndentedString(cpRole)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    addressData: ").append(toIndentedString(addressData)).append("\n");
    sb.append("    virtualNetworkInterfaceRequirements: ").append(toIndentedString(virtualNetworkInterfaceRequirements)).append("\n");
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

