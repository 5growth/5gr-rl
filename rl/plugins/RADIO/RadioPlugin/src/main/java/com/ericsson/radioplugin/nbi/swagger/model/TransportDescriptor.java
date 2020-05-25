package com.ericsson.radioplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about the transport in a transport binding.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
@ApiModel(description = "Information about the transport in a transport binding.")

public class TransportDescriptor   {
  

public enum TypeEnum {

    REST_HTTP(String.valueOf("REST_HTTP")), MB_TOPIC_BASED(String.valueOf("MB_TOPIC_BASED")), MB_ROUTING(String.valueOf("MB_ROUTING")), MB_PUBSUB(String.valueOf("MB_PUBSUB")), RPC(String.valueOf("RPC")), RPC_STREAMING(String.valueOf("RPC_STREAMING")), WEBSOCKET(String.valueOf("WEBSOCKET"));


    private String value;

    TypeEnum (String v) {
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
    public static TypeEnum fromValue(String v) {
        for (TypeEnum b : TypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid TypeEnum type = null;
  private @Valid String protocol = null;
  private @Valid String version = null;
  private @Valid Object security = null;

  /**
   * Type of the transport, as defined in the TransportTypes type in ETSI GS MEC 011.
   **/
  public TransportDescriptor type(TypeEnum type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of the transport, as defined in the TransportTypes type in ETSI GS MEC 011.")
  @JsonProperty("type")
  @NotNull
  public TypeEnum getType() {
    return type;
  }
  public void setType(TypeEnum type) {
    this.type = type;
  }

  /**
   * The name of the protocol used. Shall be set to \&quot;HTTP\&quot; for a REST API.
   **/
  public TransportDescriptor protocol(String protocol) {
    this.protocol = protocol;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The name of the protocol used. Shall be set to \"HTTP\" for a REST API.")
  @JsonProperty("protocol")
  @NotNull
  public String getProtocol() {
    return protocol;
  }
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  /**
   * The version of the protocol used.
   **/
  public TransportDescriptor version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The version of the protocol used.")
  @JsonProperty("version")
  @NotNull
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Information about the security used by the transport in ETSI GS MEC 011. (not implemented)
   **/
  public TransportDescriptor security(Object security) {
    this.security = security;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Information about the security used by the transport in ETSI GS MEC 011. (not implemented)")
  @JsonProperty("security")
  @NotNull
  public Object getSecurity() {
    return security;
  }
  public void setSecurity(Object security) {
    this.security = security;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransportDescriptor transportDescriptor = (TransportDescriptor) o;
    return Objects.equals(type, transportDescriptor.type) &&
        Objects.equals(protocol, transportDescriptor.protocol) &&
        Objects.equals(version, transportDescriptor.version) &&
        Objects.equals(security, transportDescriptor.security);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, protocol, version, security);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransportDescriptor {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    security: ").append(toIndentedString(security)).append("\n");
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

