package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.crosshaulplugin.nbi.swagger.model.MetaDataInner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information specifying additional attributes of the network resource to be reserved.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information specifying additional attributes of the network resource to be reserved.")

public class CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes   {
  
  private @Valid BigDecimal bandwidth = null;
  private @Valid Boolean isShared = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid String networkType = null;
  private @Valid String segmentType = null;

  /**
   * Minimum network bitrate (in Mbps).
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum network bitrate (in Mbps).")
  @JsonProperty("bandwidth")
  @NotNull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * It defines whether the virtualised network to be reserved is shared among consumers.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes isShared(Boolean isShared) {
    this.isShared = isShared;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It defines whether the virtualised network to be reserved is shared among consumers.")
  @JsonProperty("isShared")
  @NotNull
  public Boolean isIsShared() {
    return isShared;
  }
  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes metadata(List<MetaDataInner> metadata) {
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
   * The type of network that maps to the virtualised network to be reserved. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, etc.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network to be reserved. Examples are: \"local\", \"vlan\", \"vxlan\", \"gre\", etc.")
  @JsonProperty("networkType")
  @NotNull
  public String getNetworkType() {
    return networkType;
  }
  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  /**
   * The isolated segment for the virtualised network to be reserved. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network to be reserved. For instance, for a \"vlan\" networkType, it corresponds to the vlan identifier; and for a \"gre\" networkType, this corresponds to a gre key.")
  @JsonProperty("segmentType")
  @NotNull
  public String getSegmentType() {
    return segmentType;
  }
  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes createNetworkResourceReservationRequestNetworkReservationNetworkAttributes = (CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes) o;
    return Objects.equals(bandwidth, createNetworkResourceReservationRequestNetworkReservationNetworkAttributes.bandwidth) &&
        Objects.equals(isShared, createNetworkResourceReservationRequestNetworkReservationNetworkAttributes.isShared) &&
        Objects.equals(metadata, createNetworkResourceReservationRequestNetworkReservationNetworkAttributes.metadata) &&
        Objects.equals(networkType, createNetworkResourceReservationRequestNetworkReservationNetworkAttributes.networkType) &&
        Objects.equals(segmentType, createNetworkResourceReservationRequestNetworkReservationNetworkAttributes.segmentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, isShared, metadata, networkType, segmentType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
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

