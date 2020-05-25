package com.rl.extinterface.nbi.swagger.model;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateComputeResourceReservationRequestContainerFlavourStorageAttributes   {
  
  private @Valid String flavourId = null;
  private @Valid String hostId = null;
  private @Valid String operationalState = null;
  private @Valid String ownerId = null;
  private @Valid BigDecimal sizeOfStorage = null;
  private @Valid String storageId = null;
  private @Valid String storageName = null;
  private @Valid String typeOfStorage = null;
  private @Valid String zoneId = null;

  /**
   * Identifier of the storage flavour used to instantiate this virtual storage.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the storage flavour used to instantiate this virtual storage.")
  @JsonProperty("flavourId")
  @NotNull
  public String getFlavourId() {
    return flavourId;
  }
  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  /**
   * Identifier of the host where the virtualised storage resource is allocated. A cardinality of 0 refers to distributed storage solutions.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes hostId(String hostId) {
    this.hostId = hostId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the host where the virtualised storage resource is allocated. A cardinality of 0 refers to distributed storage solutions.")
  @JsonProperty("hostId")
  @NotNull
  public String getHostId() {
    return hostId;
  }
  public void setHostId(String hostId) {
    this.hostId = hostId;
  }

  /**
   * Operational state of the resource.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Operational state of the resource.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * Identifier of the virtualised resource that owns and uses such a virtualised storage resource. The value can be NULL if the virtualised storage is not attached yet to any other resource (e.g. a virtual machine).
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes ownerId(String ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised resource that owns and uses such a virtualised storage resource. The value can be NULL if the virtualised storage is not attached yet to any other resource (e.g. a virtual machine).")
  @JsonProperty("ownerId")
  @NotNull
  public String getOwnerId() {
    return ownerId;
  }
  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  /**
   * Size of virtualised storage resource (e.g. size of volume, in GB).
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes sizeOfStorage(BigDecimal sizeOfStorage) {
    this.sizeOfStorage = sizeOfStorage;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Size of virtualised storage resource (e.g. size of volume, in GB).")
  @JsonProperty("sizeOfStorage")
  @NotNull
  public BigDecimal getSizeOfStorage() {
    return sizeOfStorage;
  }
  public void setSizeOfStorage(BigDecimal sizeOfStorage) {
    this.sizeOfStorage = sizeOfStorage;
  }

  /**
   * Identifier of the virtualised storage resource.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes storageId(String storageId) {
    this.storageId = storageId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised storage resource.")
  @JsonProperty("storageId")
  @NotNull
  public String getStorageId() {
    return storageId;
  }
  public void setStorageId(String storageId) {
    this.storageId = storageId;
  }

  /**
   * Name of the virtualised storage resource.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes storageName(String storageName) {
    this.storageName = storageName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name of the virtualised storage resource.")
  @JsonProperty("storageName")
  @NotNull
  public String getStorageName() {
    return storageName;
  }
  public void setStorageName(String storageName) {
    this.storageName = storageName;
  }

  /**
   * Type of virtualised storage resource (e.g. volume, object).
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes typeOfStorage(String typeOfStorage) {
    this.typeOfStorage = typeOfStorage;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of virtualised storage resource (e.g. volume, object).")
  @JsonProperty("typeOfStorage")
  @NotNull
  public String getTypeOfStorage() {
    return typeOfStorage;
  }
  public void setTypeOfStorage(String typeOfStorage) {
    this.typeOfStorage = typeOfStorage;
  }

  /**
   * If present, it identifies the Resource Zone where the virtual storage resources have been allocated.
   **/
  public CreateComputeResourceReservationRequestContainerFlavourStorageAttributes zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it identifies the Resource Zone where the virtual storage resources have been allocated.")
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
    CreateComputeResourceReservationRequestContainerFlavourStorageAttributes createComputeResourceReservationRequestContainerFlavourStorageAttributes = (CreateComputeResourceReservationRequestContainerFlavourStorageAttributes) o;
    return Objects.equals(flavourId, createComputeResourceReservationRequestContainerFlavourStorageAttributes.flavourId) &&
        Objects.equals(hostId, createComputeResourceReservationRequestContainerFlavourStorageAttributes.hostId) &&
        Objects.equals(operationalState, createComputeResourceReservationRequestContainerFlavourStorageAttributes.operationalState) &&
        Objects.equals(ownerId, createComputeResourceReservationRequestContainerFlavourStorageAttributes.ownerId) &&
        Objects.equals(sizeOfStorage, createComputeResourceReservationRequestContainerFlavourStorageAttributes.sizeOfStorage) &&
        Objects.equals(storageId, createComputeResourceReservationRequestContainerFlavourStorageAttributes.storageId) &&
        Objects.equals(storageName, createComputeResourceReservationRequestContainerFlavourStorageAttributes.storageName) &&
        Objects.equals(typeOfStorage, createComputeResourceReservationRequestContainerFlavourStorageAttributes.typeOfStorage) &&
        Objects.equals(zoneId, createComputeResourceReservationRequestContainerFlavourStorageAttributes.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flavourId, hostId, operationalState, ownerId, sizeOfStorage, storageId, storageName, typeOfStorage, zoneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequestContainerFlavourStorageAttributes {\n");
    
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    sizeOfStorage: ").append(toIndentedString(sizeOfStorage)).append("\n");
    sb.append("    storageId: ").append(toIndentedString(storageId)).append("\n");
    sb.append("    storageName: ").append(toIndentedString(storageName)).append("\n");
    sb.append("    typeOfStorage: ").append(toIndentedString(typeOfStorage)).append("\n");
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

