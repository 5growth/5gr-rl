package com.ericsson.radioplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InterNfviPopConnnectivityIdListInner   {
  
  private @Valid String interNfviPopConnnectivityId = null;

  /**
   **/
  public InterNfviPopConnnectivityIdListInner interNfviPopConnnectivityId(String interNfviPopConnnectivityId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InterNfviPopConnnectivityIdListInner interNfviPopConnnectivityIdListInner = (InterNfviPopConnnectivityIdListInner) o;
    return Objects.equals(interNfviPopConnnectivityId, interNfviPopConnnectivityIdListInner.interNfviPopConnnectivityId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interNfviPopConnnectivityId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InterNfviPopConnnectivityIdListInner {\n");
    
    sb.append("    interNfviPopConnnectivityId: ").append(toIndentedString(interNfviPopConnnectivityId)).append("\n");
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

