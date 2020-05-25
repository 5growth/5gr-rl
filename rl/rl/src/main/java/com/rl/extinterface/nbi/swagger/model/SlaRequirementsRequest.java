package com.rl.extinterface.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SlaRequirementsRequest {

  
  private @Valid String sliceId = null;
  private @Valid String srcEndpoint = null;
  private @Valid String dstEndpoint = null;
  private @Valid String maxCapacity = null;
  
  /**
   * Identifier of the slice to which apply performance isolation
   **/
  public SlaRequirementsRequest sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }


  @ApiModelProperty(required = true, value = "Identifier of the slice to which apply performance isolation")
  @JsonProperty("sliceId")
  @NotNull
  public String getSliceId() {
    return sliceId;
  }
  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  /**
   * Identifier of the source endpoint of the Network Slice.
   **/
  public SlaRequirementsRequest srcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
    return this;
  }


  @ApiModelProperty(required = true, value = "Identifier of the source endpoint of the Network Slice.")
  @JsonProperty("srcEndpoint")
  @NotNull
  public String getSrcEndpoint() {
    return srcEndpoint;
  }
  public void setSrcEndpoint(String srcEndpoint) {
    this.srcEndpoint = srcEndpoint;
  }

  /**
   * Identifier of the destination endpoint of the Network Slice.
   **/
  public SlaRequirementsRequest dstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
    return this;
  }


  @ApiModelProperty(required = true, value = "Identifier of the destination endpoint of the Network Slice.")
  @JsonProperty("dstEndpoint")
  @NotNull
  public String getDstEndpoint() {
    return dstEndpoint;
  }
  public void setDstEndpoint(String dstEndpoint) {
    this.dstEndpoint = dstEndpoint;
  }


  /**
   * Maximum capacity allowed for the slice
   **/
  public SlaRequirementsRequest maxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
    return this;
  }


  @ApiModelProperty(required = true, value = "Maximum capacity allowed for the slice.")
  @JsonProperty("maxCapacity")
  @NotNull
  public String getMaxCapacity() {
    return maxCapacity;
  }
  public void setMaxCapacity(String maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SlaRequirementsRequest allocateComputeRequest = (SlaRequirementsRequest) o;
    return
        Objects.equals(sliceId, allocateComputeRequest.sliceId) &&
        Objects.equals(srcEndpoint, allocateComputeRequest.srcEndpoint) &&
        Objects.equals(dstEndpoint, allocateComputeRequest.dstEndpoint) &&
        Objects.equals(maxCapacity, allocateComputeRequest.maxCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId, srcEndpoint, dstEndpoint, maxCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequest {\n");

    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    srcEndpoint: ").append(toIndentedString(srcEndpoint)).append("\n");
    sb.append("    dstEndpoint: ").append(toIndentedString(dstEndpoint)).append("\n");
    sb.append("    maxCapacity: ").append(toIndentedString(maxCapacity)).append("\n");
    
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

