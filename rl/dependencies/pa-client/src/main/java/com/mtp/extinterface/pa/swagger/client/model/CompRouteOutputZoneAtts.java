/*
 * 5GT-MTP PA API
 * REST-API for the MTP PA. Find more at http://5g-transformer.eu
 *
 * OpenAPI spec version: 0.0.0
 * Contact: cnd@lists.cttc.es
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.mtp.extinterface.pa.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * CompRouteOutputZoneAtts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-02T08:45:54.546Z")
public class CompRouteOutputZoneAtts {
  @SerializedName("zoneId")
  private String zoneId = null;

  @SerializedName("zoneName")
  private String zoneName = null;

  @SerializedName("allocatedCPU")
  private String allocatedCPU = null;

  @SerializedName("allocatedMemory")
  private String allocatedMemory = null;

  public CompRouteOutputZoneAtts zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

   /**
   * The identifier of the Resource Zone.
   * @return zoneId
  **/
  @ApiModelProperty(required = true, value = "The identifier of the Resource Zone.")
  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public CompRouteOutputZoneAtts zoneName(String zoneName) {
    this.zoneName = zoneName;
    return this;
  }

   /**
   * The name of the Resource Zone.
   * @return zoneName
  **/
  @ApiModelProperty(required = true, value = "The name of the Resource Zone.")
  public String getZoneName() {
    return zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  public CompRouteOutputZoneAtts allocatedCPU(String allocatedCPU) {
    this.allocatedCPU = allocatedCPU;
    return this;
  }

   /**
   * The allocated capacity of CPU resources.
   * @return allocatedCPU
  **/
  @ApiModelProperty(required = true, value = "The allocated capacity of CPU resources.")
  public String getAllocatedCPU() {
    return allocatedCPU;
  }

  public void setAllocatedCPU(String allocatedCPU) {
    this.allocatedCPU = allocatedCPU;
  }

  public CompRouteOutputZoneAtts allocatedMemory(String allocatedMemory) {
    this.allocatedMemory = allocatedMemory;
    return this;
  }

   /**
   * The allocated capacity of Memory resources.
   * @return allocatedMemory
  **/
  @ApiModelProperty(required = true, value = "The allocated capacity of Memory resources.")
  public String getAllocatedMemory() {
    return allocatedMemory;
  }

  public void setAllocatedMemory(String allocatedMemory) {
    this.allocatedMemory = allocatedMemory;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompRouteOutputZoneAtts compRouteOutputZoneAtts = (CompRouteOutputZoneAtts) o;
    return Objects.equals(this.zoneId, compRouteOutputZoneAtts.zoneId) &&
        Objects.equals(this.zoneName, compRouteOutputZoneAtts.zoneName) &&
        Objects.equals(this.allocatedCPU, compRouteOutputZoneAtts.allocatedCPU) &&
        Objects.equals(this.allocatedMemory, compRouteOutputZoneAtts.allocatedMemory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneId, zoneName, allocatedCPU, allocatedMemory);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompRouteOutputZoneAtts {\n");
    
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    zoneName: ").append(toIndentedString(zoneName)).append("\n");
    sb.append("    allocatedCPU: ").append(toIndentedString(allocatedCPU)).append("\n");
    sb.append("    allocatedMemory: ").append(toIndentedString(allocatedMemory)).append("\n");
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

