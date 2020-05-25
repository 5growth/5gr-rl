package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualNetworkInterfaceRequirements   {
  
  private @Valid String name = null;
  private @Valid String description = null;
  private @Valid Boolean supportMandatory = null;
  private @Valid Object requirement = null;

  /**
   * Provides a human readable name for this requirement.
   **/
  public VirtualNetworkInterfaceRequirements name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(value = "Provides a human readable name for this requirement.")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Provides a human readable description for this requirement.
   **/
  public VirtualNetworkInterfaceRequirements description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(value = "Provides a human readable description for this requirement.")
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Indicates whether fulfilling the constraint is mandatory (TRUE) for successful operation or desirable (FALSE).
   **/
  public VirtualNetworkInterfaceRequirements supportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Indicates whether fulfilling the constraint is mandatory (TRUE) for successful operation or desirable (FALSE).")
  @JsonProperty("supportMandatory")
  @NotNull
  public Boolean isSupportMandatory() {
    return supportMandatory;
  }
  public void setSupportMandatory(Boolean supportMandatory) {
    this.supportMandatory = supportMandatory;
  }

  /**
   * Specifies a requirement such as the support of SR-IOV, a particular data plane acceleration library, an API to be exposed by a NIC, etc.
   **/
  public VirtualNetworkInterfaceRequirements requirement(Object requirement) {
    this.requirement = requirement;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Specifies a requirement such as the support of SR-IOV, a particular data plane acceleration library, an API to be exposed by a NIC, etc.")
  @JsonProperty("requirement")
  @NotNull
  public Object getRequirement() {
    return requirement;
  }
  public void setRequirement(Object requirement) {
    this.requirement = requirement;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetworkInterfaceRequirements virtualNetworkInterfaceRequirements = (VirtualNetworkInterfaceRequirements) o;
    return Objects.equals(name, virtualNetworkInterfaceRequirements.name) &&
        Objects.equals(description, virtualNetworkInterfaceRequirements.description) &&
        Objects.equals(supportMandatory, virtualNetworkInterfaceRequirements.supportMandatory) &&
        Objects.equals(requirement, virtualNetworkInterfaceRequirements.requirement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, supportMandatory, requirement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetworkInterfaceRequirements {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    supportMandatory: ").append(toIndentedString(supportMandatory)).append("\n");
    sb.append("    requirement: ").append(toIndentedString(requirement)).append("\n");
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

