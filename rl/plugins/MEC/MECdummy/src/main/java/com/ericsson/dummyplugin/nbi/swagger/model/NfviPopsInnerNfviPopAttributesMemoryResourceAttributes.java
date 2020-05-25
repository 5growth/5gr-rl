package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class NfviPopsInnerNfviPopAttributesMemoryResourceAttributes   {
  
  private @Valid String availableCapacity = null;
  private @Valid String reservedCapacity = null;
  private @Valid String totalCapacity = null;
  private @Valid String allocatedCapacity = null;

  /**
   * The free capacity available for allocation and reservation of memory resources.
   **/
  public NfviPopsInnerNfviPopAttributesMemoryResourceAttributes availableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The free capacity available for allocation and reservation of memory resources.")
  @JsonProperty("availableCapacity")
  @NotNull
  public String getAvailableCapacity() {
    return availableCapacity;
  }
  public void setAvailableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
  }

  /**
   * The reserved capacity of   memory resources.
   **/
  public NfviPopsInnerNfviPopAttributesMemoryResourceAttributes reservedCapacity(String reservedCapacity) {
    this.reservedCapacity = reservedCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The reserved capacity of   memory resources.")
  @JsonProperty("reservedCapacity")
  @NotNull
  public String getReservedCapacity() {
    return reservedCapacity;
  }
  public void setReservedCapacity(String reservedCapacity) {
    this.reservedCapacity = reservedCapacity;
  }

  /**
   * The total capacity of memory resources.
   **/
  public NfviPopsInnerNfviPopAttributesMemoryResourceAttributes totalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The total capacity of memory resources.")
  @JsonProperty("totalCapacity")
  @NotNull
  public String getTotalCapacity() {
    return totalCapacity;
  }
  public void setTotalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
  }

  /**
   * The allocated capacity of memory resources.
   **/
  public NfviPopsInnerNfviPopAttributesMemoryResourceAttributes allocatedCapacity(String allocatedCapacity) {
    this.allocatedCapacity = allocatedCapacity;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The allocated capacity of memory resources.")
  @JsonProperty("allocatedCapacity")
  @NotNull
  public String getAllocatedCapacity() {
    return allocatedCapacity;
  }
  public void setAllocatedCapacity(String allocatedCapacity) {
    this.allocatedCapacity = allocatedCapacity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInnerNfviPopAttributesMemoryResourceAttributes nfviPopsInnerNfviPopAttributesMemoryResourceAttributes = (NfviPopsInnerNfviPopAttributesMemoryResourceAttributes) o;
    return Objects.equals(availableCapacity, nfviPopsInnerNfviPopAttributesMemoryResourceAttributes.availableCapacity) &&
        Objects.equals(reservedCapacity, nfviPopsInnerNfviPopAttributesMemoryResourceAttributes.reservedCapacity) &&
        Objects.equals(totalCapacity, nfviPopsInnerNfviPopAttributesMemoryResourceAttributes.totalCapacity) &&
        Objects.equals(allocatedCapacity, nfviPopsInnerNfviPopAttributesMemoryResourceAttributes.allocatedCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availableCapacity, reservedCapacity, totalCapacity, allocatedCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesMemoryResourceAttributes {\n");
    
    sb.append("    availableCapacity: ").append(toIndentedString(availableCapacity)).append("\n");
    sb.append("    reservedCapacity: ").append(toIndentedString(reservedCapacity)).append("\n");
    sb.append("    totalCapacity: ").append(toIndentedString(totalCapacity)).append("\n");
    sb.append("    allocatedCapacity: ").append(toIndentedString(allocatedCapacity)).append("\n");
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

