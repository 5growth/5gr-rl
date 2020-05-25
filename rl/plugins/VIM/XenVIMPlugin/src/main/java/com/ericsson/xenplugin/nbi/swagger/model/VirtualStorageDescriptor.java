package com.ericsson.xenplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Defines descriptors of virtual storage resources to be used by the mobile edge application.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Defines descriptors of virtual storage resources to be used by the mobile edge application.")

public class VirtualStorageDescriptor   {
  
  private @Valid String id = null;
  private @Valid String typeOfStorage = null;
  private @Valid BigDecimal sizeOfStorage = null;
  private @Valid Boolean rdmaEnabled = null;
  private @Valid String swImageDesc = null;

  /**
   * The identifier of this virtual storage descriptor in the appD.
   **/
  public VirtualStorageDescriptor id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of this virtual storage descriptor in the appD.")
  @JsonProperty("id")
  @NotNull
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Type of virtualised storage resource (e.g. volume, object).
   **/
  public VirtualStorageDescriptor typeOfStorage(String typeOfStorage) {
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
   * Size of virtualised storage resource (e.g. size of volume, in GB).
   **/
  public VirtualStorageDescriptor sizeOfStorage(BigDecimal sizeOfStorage) {
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
   * Indicate if the storage supports RDMA.
   **/
  public VirtualStorageDescriptor rdmaEnabled(Boolean rdmaEnabled) {
    this.rdmaEnabled = rdmaEnabled;
    return this;
  }

  
  @ApiModelProperty(value = "Indicate if the storage supports RDMA.")
  @JsonProperty("rdmaEnabled")
  public Boolean isRdmaEnabled() {
    return rdmaEnabled;
  }
  public void setRdmaEnabled(Boolean rdmaEnabled) {
    this.rdmaEnabled = rdmaEnabled;
  }

  /**
   * Software image to be loaded on the VirtualStorage Resource created based on this VirtualStorageDescriptor identifier.
   **/
  public VirtualStorageDescriptor swImageDesc(String swImageDesc) {
    this.swImageDesc = swImageDesc;
    return this;
  }

  
  @ApiModelProperty(value = "Software image to be loaded on the VirtualStorage Resource created based on this VirtualStorageDescriptor identifier.")
  @JsonProperty("swImageDesc")
  public String getSwImageDesc() {
    return swImageDesc;
  }
  public void setSwImageDesc(String swImageDesc) {
    this.swImageDesc = swImageDesc;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualStorageDescriptor virtualStorageDescriptor = (VirtualStorageDescriptor) o;
    return Objects.equals(id, virtualStorageDescriptor.id) &&
        Objects.equals(typeOfStorage, virtualStorageDescriptor.typeOfStorage) &&
        Objects.equals(sizeOfStorage, virtualStorageDescriptor.sizeOfStorage) &&
        Objects.equals(rdmaEnabled, virtualStorageDescriptor.rdmaEnabled) &&
        Objects.equals(swImageDesc, virtualStorageDescriptor.swImageDesc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, typeOfStorage, sizeOfStorage, rdmaEnabled, swImageDesc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualStorageDescriptor {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    typeOfStorage: ").append(toIndentedString(typeOfStorage)).append("\n");
    sb.append("    sizeOfStorage: ").append(toIndentedString(sizeOfStorage)).append("\n");
    sb.append("    rdmaEnabled: ").append(toIndentedString(rdmaEnabled)).append("\n");
    sb.append("    swImageDesc: ").append(toIndentedString(swImageDesc)).append("\n");
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

