package com.ericsson.dummyplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class AddressData   {
  

public enum AddressTypeEnum {

    MAC(String.valueOf("MAC")), IPV4(String.valueOf("IPV4")), IPV6(String.valueOf("IPV6"));


    private String value;

    AddressTypeEnum (String v) {
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
    public static AddressTypeEnum fromValue(String v) {
        for (AddressTypeEnum b : AddressTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid AddressTypeEnum addressType = null;
  private @Valid Object l2AddressData = null;
  private @Valid Object l3AddressData = null;

  /**
   * Describes the type of the address to be assigned to the CP instantiated from the parent CPD. The content type shall be aligned with the address type supported by the layerProtocol attribute of the parent CPD.
   **/
  public AddressData addressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Describes the type of the address to be assigned to the CP instantiated from the parent CPD. The content type shall be aligned with the address type supported by the layerProtocol attribute of the parent CPD.")
  @JsonProperty("addressType")
  @NotNull
  public AddressTypeEnum getAddressType() {
    return addressType;
  }
  public void setAddressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
  }

  /**
   * Provides the information on the MAC addresses to be assigned to the CP(s) instantiated from the parent CPD. Shall be present when the addressType is MAC address.
   **/
  public AddressData l2AddressData(Object l2AddressData) {
    this.l2AddressData = l2AddressData;
    return this;
  }

  
  @ApiModelProperty(value = "Provides the information on the MAC addresses to be assigned to the CP(s) instantiated from the parent CPD. Shall be present when the addressType is MAC address.")
  @JsonProperty("l2AddressData")
  public Object getL2AddressData() {
    return l2AddressData;
  }
  public void setL2AddressData(Object l2AddressData) {
    this.l2AddressData = l2AddressData;
  }

  /**
   * Provides the information on the IP addresses to be assigned to the CP instantiated from the parent CPD. Shall be present when the addressType is IP address.
   **/
  public AddressData l3AddressData(Object l3AddressData) {
    this.l3AddressData = l3AddressData;
    return this;
  }

  
  @ApiModelProperty(value = "Provides the information on the IP addresses to be assigned to the CP instantiated from the parent CPD. Shall be present when the addressType is IP address.")
  @JsonProperty("l3AddressData")
  public Object getL3AddressData() {
    return l3AddressData;
  }
  public void setL3AddressData(Object l3AddressData) {
    this.l3AddressData = l3AddressData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressData addressData = (AddressData) o;
    return Objects.equals(addressType, addressData.addressType) &&
        Objects.equals(l2AddressData, addressData.l2AddressData) &&
        Objects.equals(l3AddressData, addressData.l3AddressData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressType, l2AddressData, l3AddressData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressData {\n");
    
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    l2AddressData: ").append(toIndentedString(l2AddressData)).append("\n");
    sb.append("    l3AddressData: ").append(toIndentedString(l3AddressData)).append("\n");
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

