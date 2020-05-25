package com.rl.extinterface.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Element containing user data to customize the virtualised compute resource at boot-time.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Element containing user data to customize the virtualised compute resource at boot-time.")

public class AllocateComputeRequestUserData   {
  
  private @Valid String content = null;
  private @Valid String method = null;

  /**
   * String containing the user data to customize a virtualised compute resource at boot-time.
   **/
  public AllocateComputeRequestUserData content(String content) {
    this.content = content;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "String containing the user data to customize a virtualised compute resource at boot-time.")
  @JsonProperty("content")
  @NotNull
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Method used as transportation media to convey the content of the UserData to the virtualised compute resource. Possible values: CONFIG-DRIVE.
   **/
  public AllocateComputeRequestUserData method(String method) {
    this.method = method;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Method used as transportation media to convey the content of the UserData to the virtualised compute resource. Possible values: CONFIG-DRIVE.")
  @JsonProperty("method")
  @NotNull
  public String getMethod() {
    return method;
  }
  public void setMethod(String method) {
    this.method = method;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequestUserData allocateComputeRequestUserData = (AllocateComputeRequestUserData) o;
    return Objects.equals(content, allocateComputeRequestUserData.content) &&
        Objects.equals(method, allocateComputeRequestUserData.method);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, method);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequestUserData {\n");
    
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
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

