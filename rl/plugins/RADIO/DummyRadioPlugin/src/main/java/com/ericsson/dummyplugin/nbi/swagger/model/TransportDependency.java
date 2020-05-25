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


public class TransportDependency   {
  
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
  private @Valid List<String> labels = new ArrayList<String>();

  /**
   **/
  public TransportDependency transport(TransportDescriptor transport) {
    this.transport = transport;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("transport")
  @NotNull
  public TransportDescriptor getTransport() {
    return transport;
  }
  public void setTransport(TransportDescriptor transport) {
    this.transport = transport;
  }

  /**
   * Information about the serializers in this transport binding, as defined in the SerializerTypes type in ETSI GS MEC 011. Support for at least one of the entries is required in conjunction with the transport.
   **/
  public TransportDependency serializers(SerializersEnum serializers) {
    this.serializers = serializers;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Information about the serializers in this transport binding, as defined in the SerializerTypes type in ETSI GS MEC 011. Support for at least one of the entries is required in conjunction with the transport.")
  @JsonProperty("serializers")
  @NotNull
  public SerializersEnum getSerializers() {
    return serializers;
  }
  public void setSerializers(SerializersEnum serializers) {
    this.serializers = serializers;
  }

  /**
   * Set of labels that allow to define groups of transport bindings.
   **/
  public TransportDependency labels(List<String> labels) {
    this.labels = labels;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Set of labels that allow to define groups of transport bindings.")
  @JsonProperty("labels")
  @NotNull
  public List<String> getLabels() {
    return labels;
  }
  public void setLabels(List<String> labels) {
    this.labels = labels;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransportDependency transportDependency = (TransportDependency) o;
    return Objects.equals(transport, transportDependency.transport) &&
        Objects.equals(serializers, transportDependency.serializers) &&
        Objects.equals(labels, transportDependency.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transport, serializers, labels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransportDependency {\n");
    
    sb.append("    transport: ").append(toIndentedString(transport)).append("\n");
    sb.append("    serializers: ").append(toIndentedString(serializers)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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

