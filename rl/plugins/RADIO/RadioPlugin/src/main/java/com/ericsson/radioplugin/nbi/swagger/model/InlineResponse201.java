package com.ericsson.radioplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InlineResponse201   {
  
  private @Valid String interNfviPopConnnectivityId = null;
  private @Valid String interNfviPopNetworkSegmentType = null;

  /**
   **/
  public InlineResponse201 interNfviPopConnnectivityId(String interNfviPopConnnectivityId) {
    this.interNfviPopConnnectivityId = interNfviPopConnnectivityId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("interNfviPopConnnectivityId")
  @NotNull
  public String getInterNfviPopConnnectivityId() {
    return interNfviPopConnnectivityId;
  }
  public void setInterNfviPopConnnectivityId(String interNfviPopConnnectivityId) {
    this.interNfviPopConnnectivityId = interNfviPopConnnectivityId;
  }

  /**
   **/
  public InlineResponse201 interNfviPopNetworkSegmentType(String interNfviPopNetworkSegmentType) {
    this.interNfviPopNetworkSegmentType = interNfviPopNetworkSegmentType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("interNfviPopNetworkSegmentType")
  @NotNull
  public String getInterNfviPopNetworkSegmentType() {
    return interNfviPopNetworkSegmentType;
  }
  public void setInterNfviPopNetworkSegmentType(String interNfviPopNetworkSegmentType) {
    this.interNfviPopNetworkSegmentType = interNfviPopNetworkSegmentType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse201 inlineResponse201 = (InlineResponse201) o;
    return Objects.equals(interNfviPopConnnectivityId, inlineResponse201.interNfviPopConnnectivityId) &&
        Objects.equals(interNfviPopNetworkSegmentType, inlineResponse201.interNfviPopNetworkSegmentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interNfviPopConnnectivityId, interNfviPopNetworkSegmentType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse201 {\n");
    
    sb.append("    interNfviPopConnnectivityId: ").append(toIndentedString(interNfviPopConnnectivityId)).append("\n");
    sb.append("    interNfviPopNetworkSegmentType: ").append(toIndentedString(interNfviPopNetworkSegmentType)).append("\n");
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

