package com.ericsson.xenplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECTrafficServiceCreationResponse   {
  
  private @Valid String requestId = null;

  /**
   * Request identifier assigned by the MEC plugin. This will be used for future queries or service deletion requests.
   **/
  public MECTrafficServiceCreationResponse requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Request identifier assigned by the MEC plugin. This will be used for future queries or service deletion requests.")
  @JsonProperty("requestId")
  @NotNull
  public String getRequestId() {
    return requestId;
  }
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECTrafficServiceCreationResponse meCTrafficServiceCreationResponse = (MECTrafficServiceCreationResponse) o;
    return Objects.equals(requestId, meCTrafficServiceCreationResponse.requestId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECTrafficServiceCreationResponse {\n");
    
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
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

