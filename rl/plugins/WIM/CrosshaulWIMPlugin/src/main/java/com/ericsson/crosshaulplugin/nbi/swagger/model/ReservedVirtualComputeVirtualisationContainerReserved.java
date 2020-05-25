package com.ericsson.crosshaulplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourStorageAttributes;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedFlavourId;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about the virtualisation container(s) that have been reserved.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about the virtualisation container(s) that have been reserved.")

public class ReservedVirtualComputeVirtualisationContainerReserved   {
  
  private @Valid String accelerationCapability = null;
  private @Valid String containerId = null;
  private @Valid List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedFlavourId>();
  private @Valid ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu = null;
  private @Valid List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks = new ArrayList<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes>();
  private @Valid ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory = null;
  private @Valid List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();
  private @Valid String zoneId = null;

  /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public String getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * The identifier of the virtualisation container that has been reserved.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved containerId(String containerId) {
    this.containerId = containerId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the virtualisation container that has been reserved.")
  @JsonProperty("containerId")
  @NotNull
  public String getContainerId() {
    return containerId;
  }
  public void setContainerId(String containerId) {
    this.containerId = containerId;
  }

  /**
   * Identifier of the given compute flavour used to reserve the virtualisation container.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved flavourId(List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the given compute flavour used to reserve the virtualisation container.")
  @JsonProperty("flavourId")
  @NotNull
  public List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> getFlavourId() {
    return flavourId;
  }
  public void setFlavourId(List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId) {
    this.flavourId = flavourId;
  }

  /**
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved virtualCpu(ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualCpu")
  @NotNull
  public ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu getVirtualCpu() {
    return virtualCpu;
  }
  public void setVirtualCpu(ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  /**
   * Element with information of the virtualised storage resources attached to the reserved virtualisation container.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved virtualDisks(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks) {
    this.virtualDisks = virtualDisks;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with information of the virtualised storage resources attached to the reserved virtualisation container.")
  @JsonProperty("virtualDisks")
  @NotNull
  public List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> getVirtualDisks() {
    return virtualDisks;
  }
  public void setVirtualDisks(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks) {
    this.virtualDisks = virtualDisks;
  }

  /**
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved virtualMemory(ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualMemory")
  @NotNull
  public ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }
  public void setVirtualMemory(ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  /**
   * Element with information of the virtual network interfaces of the reserved virtualisation container.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved virtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with information of the virtual network interfaces of the reserved virtualisation container.")
  @JsonProperty("virtualNetworkInterface")
  @NotNull
  public List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> getVirtualNetworkInterface() {
    return virtualNetworkInterface;
  }
  public void setVirtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
  }

  /**
   * References the resource zone where the virtualisation container has been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.
   **/
  public ReservedVirtualComputeVirtualisationContainerReserved zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "References the resource zone where the virtualisation container has been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.")
  @JsonProperty("zoneId")
  @NotNull
  public String getZoneId() {
    return zoneId;
  }
  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualComputeVirtualisationContainerReserved reservedVirtualComputeVirtualisationContainerReserved = (ReservedVirtualComputeVirtualisationContainerReserved) o;
    return Objects.equals(accelerationCapability, reservedVirtualComputeVirtualisationContainerReserved.accelerationCapability) &&
        Objects.equals(containerId, reservedVirtualComputeVirtualisationContainerReserved.containerId) &&
        Objects.equals(flavourId, reservedVirtualComputeVirtualisationContainerReserved.flavourId) &&
        Objects.equals(virtualCpu, reservedVirtualComputeVirtualisationContainerReserved.virtualCpu) &&
        Objects.equals(virtualDisks, reservedVirtualComputeVirtualisationContainerReserved.virtualDisks) &&
        Objects.equals(virtualMemory, reservedVirtualComputeVirtualisationContainerReserved.virtualMemory) &&
        Objects.equals(virtualNetworkInterface, reservedVirtualComputeVirtualisationContainerReserved.virtualNetworkInterface) &&
        Objects.equals(zoneId, reservedVirtualComputeVirtualisationContainerReserved.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, containerId, flavourId, virtualCpu, virtualDisks, virtualMemory, virtualNetworkInterface, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualComputeVirtualisationContainerReserved {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    containerId: ").append(toIndentedString(containerId)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
    sb.append("    virtualDisks: ").append(toIndentedString(virtualDisks)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    virtualNetworkInterface: ").append(toIndentedString(virtualNetworkInterface)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
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

