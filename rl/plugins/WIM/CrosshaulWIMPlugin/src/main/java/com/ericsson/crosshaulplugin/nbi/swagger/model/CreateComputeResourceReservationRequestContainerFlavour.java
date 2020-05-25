package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourStorageAttributes;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourVirtualCpu;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourVirtualMemory;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * The containerFlavour encapsulates information of the virtualisation container to be reserved.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "The containerFlavour encapsulates information of the virtualisation container to be reserved.")

public class CreateComputeResourceReservationRequestContainerFlavour   {
  
  private @Valid String accelerationCapability = null;
  private @Valid String flavourId = null;
  private @Valid List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> storageAttributes = new ArrayList<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes>();
  private @Valid List<CreateComputeResourceReservationRequestContainerFlavourVirtualCpu> virtualCpu = new ArrayList<CreateComputeResourceReservationRequestContainerFlavourVirtualCpu>();
  private @Valid CreateComputeResourceReservationRequestContainerFlavourVirtualMemory virtualMemory = null;
  private @Valid CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface virtualNetworkInterface = null;

  /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is requested.
   **/
  public CreateComputeResourceReservationRequestContainerFlavour accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is requested.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public String getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * Identifier given to the compute flavour.
   **/
  public CreateComputeResourceReservationRequestContainerFlavour flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier given to the compute flavour.")
  @JsonProperty("flavourId")
  @NotNull
  public String getFlavourId() {
    return flavourId;
  }
  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  /**
   * Element containing information about the size of virtualised storage resources (e.g. size of volume, in GB), the type of storage (e.g. volume, object).
   **/
  public CreateComputeResourceReservationRequestContainerFlavour storageAttributes(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> storageAttributes) {
    this.storageAttributes = storageAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element containing information about the size of virtualised storage resources (e.g. size of volume, in GB), the type of storage (e.g. volume, object).")
  @JsonProperty("storageAttributes")
  @NotNull
  public List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> getStorageAttributes() {
    return storageAttributes;
  }
  public void setStorageAttributes(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> storageAttributes) {
    this.storageAttributes = storageAttributes;
  }

  /**
   * The virtual CPU(s) of the virtualised compute.
   **/
  public CreateComputeResourceReservationRequestContainerFlavour virtualCpu(List<CreateComputeResourceReservationRequestContainerFlavourVirtualCpu> virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The virtual CPU(s) of the virtualised compute.")
  @JsonProperty("virtualCpu")
  @NotNull
  public List<CreateComputeResourceReservationRequestContainerFlavourVirtualCpu> getVirtualCpu() {
    return virtualCpu;
  }
  public void setVirtualCpu(List<CreateComputeResourceReservationRequestContainerFlavourVirtualCpu> virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  /**
   **/
  public CreateComputeResourceReservationRequestContainerFlavour virtualMemory(CreateComputeResourceReservationRequestContainerFlavourVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualMemory")
  @NotNull
  public CreateComputeResourceReservationRequestContainerFlavourVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }
  public void setVirtualMemory(CreateComputeResourceReservationRequestContainerFlavourVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  /**
   **/
  public CreateComputeResourceReservationRequestContainerFlavour virtualNetworkInterface(CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualNetworkInterface")
  @NotNull
  public CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface getVirtualNetworkInterface() {
    return virtualNetworkInterface;
  }
  public void setVirtualNetworkInterface(CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequestContainerFlavour createComputeResourceReservationRequestContainerFlavour = (CreateComputeResourceReservationRequestContainerFlavour) o;
    return Objects.equals(accelerationCapability, createComputeResourceReservationRequestContainerFlavour.accelerationCapability) &&
        Objects.equals(flavourId, createComputeResourceReservationRequestContainerFlavour.flavourId) &&
        Objects.equals(storageAttributes, createComputeResourceReservationRequestContainerFlavour.storageAttributes) &&
        Objects.equals(virtualCpu, createComputeResourceReservationRequestContainerFlavour.virtualCpu) &&
        Objects.equals(virtualMemory, createComputeResourceReservationRequestContainerFlavour.virtualMemory) &&
        Objects.equals(virtualNetworkInterface, createComputeResourceReservationRequestContainerFlavour.virtualNetworkInterface);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, flavourId, storageAttributes, virtualCpu, virtualMemory, virtualNetworkInterface);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestContainerFlavour {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    storageAttributes: ").append(toIndentedString(storageAttributes)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    virtualNetworkInterface: ").append(toIndentedString(virtualNetworkInterface)).append("\n");
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

