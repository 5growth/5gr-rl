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
import com.rl.extinterface.nbi.swagger.model.MetaData;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * AllocateNetworkResultNetworkDataNetworkPort
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class AllocateNetworkResultNetworkDataNetworkPort {
  @SerializedName("attachedResourceId")
  private String attachedResourceId = null;

  @SerializedName("bandwidth")
  private BigDecimal bandwidth = null;

  @SerializedName("metadata")
  private MetaData metadata = null;

  @SerializedName("networkId")
  private String networkId = null;

  @SerializedName("operationalState")
  private String operationalState = null;

  @SerializedName("portType")
  private String portType = null;

  @SerializedName("resourceId")
  private String resourceId = null;

  @SerializedName("segmentId")
  private String segmentId = null;

  public AllocateNetworkResultNetworkDataNetworkPort attachedResourceId(String attachedResourceId) {
    this.attachedResourceId = attachedResourceId;
    return this;
  }

   /**
   * Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface). The cardinality can be \&quot;0\&quot; if there is no specific resource connected to the network port.
   * @return attachedResourceId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface). The cardinality can be \"0\" if there is no specific resource connected to the network port.")
  public String getAttachedResourceId() {
    return attachedResourceId;
  }

  public void setAttachedResourceId(String attachedResourceId) {
    this.attachedResourceId = attachedResourceId;
  }

  public AllocateNetworkResultNetworkDataNetworkPort bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * The bandwidth of the virtual network port (in Mbps). Cardinality can be \&quot;0\&quot; for virtual network ports without any specific allocated bandwidth.
   * @return bandwidth
  **/
  @ApiModelProperty(required = true, value = "The bandwidth of the virtual network port (in Mbps). Cardinality can be \"0\" for virtual network ports without any specific allocated bandwidth.")
  public BigDecimal getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  public AllocateNetworkResultNetworkDataNetworkPort metadata(MetaData metadata) {
    this.metadata = metadata;
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(value = "")
  public MetaData getMetadata() {
    return metadata;
  }

  public void setMetadata(MetaData metadata) {
    this.metadata = metadata;
  }

  public AllocateNetworkResultNetworkDataNetworkPort networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

   /**
   * Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.
   * @return networkId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.")
  public String getNetworkId() {
    return networkId;
  }

  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  public AllocateNetworkResultNetworkDataNetworkPort operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

   /**
   * The operational state of the virtual network port.
   * @return operationalState
  **/
  @ApiModelProperty(required = true, value = "The operational state of the virtual network port.")
  public String getOperationalState() {
    return operationalState;
  }

  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  public AllocateNetworkResultNetworkDataNetworkPort portType(String portType) {
    this.portType = portType;
    return this;
  }

   /**
   * Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.
   * @return portType
  **/
  @ApiModelProperty(required = true, value = "Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.")
  public String getPortType() {
    return portType;
  }

  public void setPortType(String portType) {
    this.portType = portType;
  }

  public AllocateNetworkResultNetworkDataNetworkPort resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

   /**
   * Identifier of the virtual network port.
   * @return resourceId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the virtual network port.")
  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public AllocateNetworkResultNetworkDataNetworkPort segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

   /**
   * The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; for flat networks without any specific segmentation.
   * @return segmentId
  **/
  @ApiModelProperty(required = true, value = "The isolated segment the network port belongs to. For instance, for a \"vlan\", it corresponds to the vlan identifier; and for a \"gre\", this corresponds to a gre key. The cardinality can be \"0\" for flat networks without any specific segmentation.")
  public String getSegmentId() {
    return segmentId;
  }

  public void setSegmentId(String segmentId) {
    this.segmentId = segmentId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateNetworkResultNetworkDataNetworkPort allocateNetworkResultNetworkDataNetworkPort = (AllocateNetworkResultNetworkDataNetworkPort) o;
    return Objects.equals(this.attachedResourceId, allocateNetworkResultNetworkDataNetworkPort.attachedResourceId) &&
        Objects.equals(this.bandwidth, allocateNetworkResultNetworkDataNetworkPort.bandwidth) &&
        Objects.equals(this.metadata, allocateNetworkResultNetworkDataNetworkPort.metadata) &&
        Objects.equals(this.networkId, allocateNetworkResultNetworkDataNetworkPort.networkId) &&
        Objects.equals(this.operationalState, allocateNetworkResultNetworkDataNetworkPort.operationalState) &&
        Objects.equals(this.portType, allocateNetworkResultNetworkDataNetworkPort.portType) &&
        Objects.equals(this.resourceId, allocateNetworkResultNetworkDataNetworkPort.resourceId) &&
        Objects.equals(this.segmentId, allocateNetworkResultNetworkDataNetworkPort.segmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attachedResourceId, bandwidth, metadata, networkId, operationalState, portType, resourceId, segmentId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResultNetworkDataNetworkPort {\n");
    
    sb.append("    attachedResourceId: ").append(toIndentedString(attachedResourceId)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    portType: ").append(toIndentedString(portType)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    segmentId: ").append(toIndentedString(segmentId)).append("\n");
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

