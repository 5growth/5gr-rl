package com.rl.extinterface.nbi.swagger.model;

import com.rl.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavour;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateComputeResourceReservationRequestVirtualisationContainerReservation   {
  
  private @Valid CreateComputeResourceReservationRequestContainerFlavour containerFlavour = null;
  private @Valid String containerId = null;

  /**
   **/
  public CreateComputeResourceReservationRequestVirtualisationContainerReservation containerFlavour(CreateComputeResourceReservationRequestContainerFlavour containerFlavour) {
    this.containerFlavour = containerFlavour;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("containerFlavour")
  @NotNull
  public CreateComputeResourceReservationRequestContainerFlavour getContainerFlavour() {
    return containerFlavour;
  }
  public void setContainerFlavour(CreateComputeResourceReservationRequestContainerFlavour containerFlavour) {
    this.containerFlavour = containerFlavour;
  }

  /**
   * The identifier of the virtualisation container to be reserved.
   **/
  public CreateComputeResourceReservationRequestVirtualisationContainerReservation containerId(String containerId) {
    this.containerId = containerId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the virtualisation container to be reserved.")
  @JsonProperty("containerId")
  @NotNull
  public String getContainerId() {
    return containerId;
  }
  public void setContainerId(String containerId) {
    this.containerId = containerId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequestVirtualisationContainerReservation createComputeResourceReservationRequestVirtualisationContainerReservation = (CreateComputeResourceReservationRequestVirtualisationContainerReservation) o;
    return Objects.equals(containerFlavour, createComputeResourceReservationRequestVirtualisationContainerReservation.containerFlavour) &&
        Objects.equals(containerId, createComputeResourceReservationRequestVirtualisationContainerReservation.containerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(containerFlavour, containerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestVirtualisationContainerReservation {\n");
    
    sb.append("    containerFlavour: ").append(toIndentedString(containerFlavour)).append("\n");
    sb.append("    containerId: ").append(toIndentedString(containerId)).append("\n");
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

