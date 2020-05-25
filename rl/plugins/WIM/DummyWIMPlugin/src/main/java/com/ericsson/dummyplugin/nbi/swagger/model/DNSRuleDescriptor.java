package com.ericsson.dummyplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class DNSRuleDescriptor   {
  
  private @Valid String dnsRuleId = null;
  private @Valid String domainName = null;

public enum IpAddressTypeEnum {

    V6(String.valueOf("IP_V6")), V4(String.valueOf("IP_V4"));


    private String value;

    IpAddressTypeEnum (String v) {
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
    public static IpAddressTypeEnum fromValue(String v) {
        for (IpAddressTypeEnum b : IpAddressTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid IpAddressTypeEnum ipAddressType = null;
  private @Valid String ipAddress = null;
  private @Valid BigDecimal ttl = null;

  /**
   * Identifies the DNS rule.
   **/
  public DNSRuleDescriptor dnsRuleId(String dnsRuleId) {
    this.dnsRuleId = dnsRuleId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifies the DNS rule.")
  @JsonProperty("dnsRuleId")
  @NotNull
  public String getDnsRuleId() {
    return dnsRuleId;
  }
  public void setDnsRuleId(String dnsRuleId) {
    this.dnsRuleId = dnsRuleId;
  }

  /**
   * FQDN of the DNS rule.
   **/
  public DNSRuleDescriptor domainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "FQDN of the DNS rule.")
  @JsonProperty("domainName")
  @NotNull
  public String getDomainName() {
    return domainName;
  }
  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  /**
   * Specifies the IP address type (IP_V6, IP_V4)
   **/
  public DNSRuleDescriptor ipAddressType(IpAddressTypeEnum ipAddressType) {
    this.ipAddressType = ipAddressType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Specifies the IP address type (IP_V6, IP_V4)")
  @JsonProperty("ipAddressType")
  @NotNull
  public IpAddressTypeEnum getIpAddressType() {
    return ipAddressType;
  }
  public void setIpAddressType(IpAddressTypeEnum ipAddressType) {
    this.ipAddressType = ipAddressType;
  }

  /**
   * IP address given by the DNS rule.
   **/
  public DNSRuleDescriptor ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "IP address given by the DNS rule.")
  @JsonProperty("ipAddress")
  @NotNull
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * Time-to-live value.
   **/
  public DNSRuleDescriptor ttl(BigDecimal ttl) {
    this.ttl = ttl;
    return this;
  }

  
  @ApiModelProperty(value = "Time-to-live value.")
  @JsonProperty("ttl")
  public BigDecimal getTtl() {
    return ttl;
  }
  public void setTtl(BigDecimal ttl) {
    this.ttl = ttl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DNSRuleDescriptor dnSRuleDescriptor = (DNSRuleDescriptor) o;
    return Objects.equals(dnsRuleId, dnSRuleDescriptor.dnsRuleId) &&
        Objects.equals(domainName, dnSRuleDescriptor.domainName) &&
        Objects.equals(ipAddressType, dnSRuleDescriptor.ipAddressType) &&
        Objects.equals(ipAddress, dnSRuleDescriptor.ipAddress) &&
        Objects.equals(ttl, dnSRuleDescriptor.ttl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dnsRuleId, domainName, ipAddressType, ipAddress, ttl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DNSRuleDescriptor {\n");
    
    sb.append("    dnsRuleId: ").append(toIndentedString(dnsRuleId)).append("\n");
    sb.append("    domainName: ").append(toIndentedString(domainName)).append("\n");
    sb.append("    ipAddressType: ").append(toIndentedString(ipAddressType)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
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

