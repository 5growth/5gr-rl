package com.ericsson.radioplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NetworkIds   {
  
  private @Valid String networkId = null;

  /**
   * Identifier of the virtualised network resource(s) to be terminated.
   **/
  public NetworkIds networkId(String networkId) {
    this.networkId = networkId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised network resource(s) to be terminated.")
  @JsonProperty("networkId")
  @NotNull
  public String getNetworkId() {
    return networkId;
  }
  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NetworkIds networkIds = (NetworkIds) o;
    return Objects.equals(networkId, networkIds.networkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetworkIds {\n");
    
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
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

