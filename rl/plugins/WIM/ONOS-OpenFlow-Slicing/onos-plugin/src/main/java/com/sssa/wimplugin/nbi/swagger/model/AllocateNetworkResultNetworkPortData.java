package com.sssa.wimplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.sssa.wimplugin.nbi.swagger.model.MetaDataInner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * If network types are created satisfactorily, it contains the data relative to the instantiated virtualised network resource. Cardinality can be \&quot;0\&quot; if the request did not include creation of such type of resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "If network types are created satisfactorily, it contains the data relative to the instantiated virtualised network resource. Cardinality can be \"0\" if the request did not include creation of such type of resource.")

public class AllocateNetworkResultNetworkPortData   {
  
  private @Valid String attachedResourceId = null;
  private @Valid BigDecimal bandwidth = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String networkId = null;
  private @Valid String operationalState = null;
  private @Valid String portType = null;
  private @Valid String resourceId = null;
  private @Valid String segmentId = null;

  /**
   * Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface). The cardinality can be \&quot;0\&quot; if there is no specific resource connected to the network port.
   **/
  public AllocateNetworkResultNetworkPortData attachedResourceId(String attachedResourceId) {
    this.attachedResourceId = attachedResourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the attached resource to the network port (e.g. a virtualised compute resource, or identifier of the virtual network interface). The cardinality can be \"0\" if there is no specific resource connected to the network port.")
  @JsonProperty("attachedResourceId")
  @NotNull
  public String getAttachedResourceId() {
    return attachedResourceId;
  }
  public void setAttachedResourceId(String attachedResourceId) {
    this.attachedResourceId = attachedResourceId;
  }

  /**
   * The bandwidth of the virtual network port (in Mbps). Cardinality can be \&quot;0\&quot; for virtual network ports without any specific allocated bandwidth.
   **/
  public AllocateNetworkResultNetworkPortData bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The bandwidth of the virtual network port (in Mbps). Cardinality can be \"0\" for virtual network ports without any specific allocated bandwidth.")
  @JsonProperty("bandwidth")
  @NotNull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   **/
  public AllocateNetworkResultNetworkPortData metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(value = "List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.")
  @JsonProperty("metadata")
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }
  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  /**
   * Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.
   **/
  public AllocateNetworkResultNetworkPortData networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the network that the port belongs to. When creating a port, such port needs to be part of a network.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  /**
   * The operational state of the virtual network port.
   **/
  public AllocateNetworkResultNetworkPortData operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The operational state of the virtual network port.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.
   **/
  public AllocateNetworkResultNetworkPortData portType(String portType) {
    this.portType = portType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of network port. Examples of types are access ports (layer 2 or 3), or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.")
  @JsonProperty("portType")
  @NotNull
  public String getPortType() {
    return portType;
  }
  public void setPortType(String portType) {
    this.portType = portType;
  }

  /**
   * Identifier of the virtual network port.
   **/
  public AllocateNetworkResultNetworkPortData resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtual network port.")
  @JsonProperty("resourceId")
  @NotNull
  public String getResourceId() {
    return resourceId;
  }
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; for flat networks without any specific segmentation.
   **/
  public AllocateNetworkResultNetworkPortData segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment the network port belongs to. For instance, for a \"vlan\", it corresponds to the vlan identifier; and for a \"gre\", this corresponds to a gre key. The cardinality can be \"0\" for flat networks without any specific segmentation.")
  @JsonProperty("segmentId")
  @NotNull
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
    AllocateNetworkResultNetworkPortData allocateNetworkResultNetworkPortData = (AllocateNetworkResultNetworkPortData) o;
    return Objects.equals(attachedResourceId, allocateNetworkResultNetworkPortData.attachedResourceId) &&
        Objects.equals(bandwidth, allocateNetworkResultNetworkPortData.bandwidth) &&
        Objects.equals(metadata, allocateNetworkResultNetworkPortData.metadata) &&
        Objects.equals(networkId, allocateNetworkResultNetworkPortData.networkId) &&
        Objects.equals(operationalState, allocateNetworkResultNetworkPortData.operationalState) &&
        Objects.equals(portType, allocateNetworkResultNetworkPortData.portType) &&
        Objects.equals(resourceId, allocateNetworkResultNetworkPortData.resourceId) &&
        Objects.equals(segmentId, allocateNetworkResultNetworkPortData.segmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attachedResourceId, bandwidth, metadata, networkId, operationalState, portType, resourceId, segmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateNetworkResultNetworkPortData {\n");
    
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

