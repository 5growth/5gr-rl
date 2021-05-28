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
import com.rl.extinterface.nbi.swagger.model.TransportDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TransportDependency
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class TransportDependency {
  @SerializedName("transport")
  private TransportDescriptor transport = null;

  /**
   * Information about the serializers in this transport binding, as defined in the SerializerTypes type in ETSI GS MEC 011. Support for at least one of the entries is required in conjunction with the transport.
   */
  @JsonAdapter(SerializersEnum.Adapter.class)
  public enum SerializersEnum {
    JSON("JSON"),
    
    XML("XML"),
    
    PROTOBUF3("PROTOBUF3");

    private String value;

    SerializersEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SerializersEnum fromValue(String text) {
      for (SerializersEnum b : SerializersEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<SerializersEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SerializersEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SerializersEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return SerializersEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("serializers")
  private SerializersEnum serializers = null;

  @SerializedName("labels")
  private List<String> labels = new ArrayList<String>();

  public TransportDependency transport(TransportDescriptor transport) {
    this.transport = transport;
    return this;
  }

   /**
   * Get transport
   * @return transport
  **/
  @ApiModelProperty(required = true, value = "")
  public TransportDescriptor getTransport() {
    return transport;
  }

  public void setTransport(TransportDescriptor transport) {
    this.transport = transport;
  }

  public TransportDependency serializers(SerializersEnum serializers) {
    this.serializers = serializers;
    return this;
  }

   /**
   * Information about the serializers in this transport binding, as defined in the SerializerTypes type in ETSI GS MEC 011. Support for at least one of the entries is required in conjunction with the transport.
   * @return serializers
  **/
  @ApiModelProperty(required = true, value = "Information about the serializers in this transport binding, as defined in the SerializerTypes type in ETSI GS MEC 011. Support for at least one of the entries is required in conjunction with the transport.")
  public SerializersEnum getSerializers() {
    return serializers;
  }

  public void setSerializers(SerializersEnum serializers) {
    this.serializers = serializers;
  }

  public TransportDependency labels(List<String> labels) {
    this.labels = labels;
    return this;
  }

  public TransportDependency addLabelsItem(String labelsItem) {
    this.labels.add(labelsItem);
    return this;
  }

   /**
   * Set of labels that allow to define groups of transport bindings.
   * @return labels
  **/
  @ApiModelProperty(required = true, value = "Set of labels that allow to define groups of transport bindings.")
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
    return Objects.equals(this.transport, transportDependency.transport) &&
        Objects.equals(this.serializers, transportDependency.serializers) &&
        Objects.equals(this.labels, transportDependency.labels);
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

