package com.sssa.wimplugin.nbi.swagger.model;

import com.sssa.wimplugin.nbi.swagger.model.InterNfviPopConnnectivityIdList;
import com.sssa.wimplugin.nbi.swagger.model.MetaData;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class DeleteInterNfviPopConnectivityRequest   {
  
  private @Valid InterNfviPopConnnectivityIdList interNfviPopConnnectivityIdList = null;
  private @Valid MetaData metaData = null;

  /**
   **/
  public DeleteInterNfviPopConnectivityRequest interNfviPopConnnectivityIdList(InterNfviPopConnnectivityIdList interNfviPopConnnectivityIdList) {
    this.interNfviPopConnnectivityIdList = interNfviPopConnnectivityIdList;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("interNfviPopConnnectivityIdList")
  @NotNull
  public InterNfviPopConnnectivityIdList getInterNfviPopConnnectivityIdList() {
    return interNfviPopConnnectivityIdList;
  }
  public void setInterNfviPopConnnectivityIdList(InterNfviPopConnnectivityIdList interNfviPopConnnectivityIdList) {
    this.interNfviPopConnnectivityIdList = interNfviPopConnnectivityIdList;
  }

  /**
   **/
  public DeleteInterNfviPopConnectivityRequest metaData(MetaData metaData) {
    this.metaData = metaData;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("metaData")
  @NotNull
  public MetaData getMetaData() {
    return metaData;
  }
  public void setMetaData(MetaData metaData) {
    this.metaData = metaData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteInterNfviPopConnectivityRequest deleteInterNfviPopConnectivityRequest = (DeleteInterNfviPopConnectivityRequest) o;
    return Objects.equals(interNfviPopConnnectivityIdList, deleteInterNfviPopConnectivityRequest.interNfviPopConnnectivityIdList) &&
        Objects.equals(metaData, deleteInterNfviPopConnectivityRequest.metaData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interNfviPopConnnectivityIdList, metaData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteInterNfviPopConnectivityRequest {\n");
    
    sb.append("    interNfviPopConnnectivityIdList: ").append(toIndentedString(interNfviPopConnnectivityIdList)).append("\n");
    sb.append("    metaData: ").append(toIndentedString(metaData)).append("\n");
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

