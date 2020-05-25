package com.ericsson.radioplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import javax.validation.Valid;


/**
 * Indicates transport and serialization formats supported made available to the service-consuming application. Defaults to REST+JSON if absent.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
@ApiModel(description = "Indicates transport and serialization formats supported made available to the service-consuming application. Defaults to REST+JSON if absent.")

public class ServiceDescriptorTransportsSupported   {
  
  private @Valid TransportDescriptor transport = null;

public enum SerializersEnum {

    JSON(String.valueOf("JSON")), XML(String.valueOf("XML")), PROTOBUF3(String.valueOf("PROTOBUF3"));


    private String value;

    SerializersEnum (String v) {
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
    public static SerializersEnum fromValue(String v) {
        for (SerializersEnum b : SerializersEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid SerializersEnum serializers = null;

  /**
   **/
  public ServiceDescriptorTransportsSupported transport(TransportDescriptor transport) {
    this.transport = transport;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("transport")
  public TransportDescriptor getTransport() {
    return transport;
  }
  public void setTransport(TransportDescriptor transport) {
    this.transport = transport;
  }

  /**
   * Information about the serializers in this binding, as defined in the SerializerTypes type in ETSI GS MEC 011.
   **/
  public ServiceDescriptorTransportsSupported serializers(SerializersEnum serializers) {
    this.serializers = serializers;
    return this;
  }

  
  @ApiModelProperty(value = "Information about the serializers in this binding, as defined in the SerializerTypes type in ETSI GS MEC 011.")
  @JsonProperty("serializers")
  public SerializersEnum getSerializers() {
    return serializers;
  }
  public void setSerializers(SerializersEnum serializers) {
    this.serializers = serializers;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceDescriptorTransportsSupported serviceDescriptorTransportsSupported = (ServiceDescriptorTransportsSupported) o;
    return Objects.equals(transport, serviceDescriptorTransportsSupported.transport) &&
        Objects.equals(serializers, serviceDescriptorTransportsSupported.serializers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transport, serializers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceDescriptorTransportsSupported {\n");
    
    sb.append("    transport: ").append(toIndentedString(transport)).append("\n");
    sb.append("    serializers: ").append(toIndentedString(serializers)).append("\n");
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

