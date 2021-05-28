/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rl.extinterface.nbi.swagger.model;

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
import java.math.BigDecimal;

/**
 * Configuration parameters for the Terminate application instance operation. This follows the TerminateVnfInstanceOpConfig definition of IFA 011.
 */
@ApiModel(description = "Configuration parameters for the Terminate application instance operation. This follows the TerminateVnfInstanceOpConfig definition of IFA 011.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-12T12:38:09.537Z")



public class TerminateAppInstanceOpConfig {
  @SerializedName("minGracefulTerminationTimeout")
  private BigDecimal minGracefulTerminationTimeout = null;

  @SerializedName("macRecommendedGracefulTerminationTimeout")
  private BigDecimal macRecommendedGracefulTerminationTimeout = null;

  public TerminateAppInstanceOpConfig minGracefulTerminationTimeout(BigDecimal minGracefulTerminationTimeout) {
    this.minGracefulTerminationTimeout = minGracefulTerminationTimeout;
    return this;
  }

   /**
   * Minimum timeout value for graceful termination of application instance.
   * @return minGracefulTerminationTimeout
  **/
  @ApiModelProperty(required = true, value = "Minimum timeout value for graceful termination of application instance.")
  public BigDecimal getMinGracefulTerminationTimeout() {
    return minGracefulTerminationTimeout;
  }

  public void setMinGracefulTerminationTimeout(BigDecimal minGracefulTerminationTimeout) {
    this.minGracefulTerminationTimeout = minGracefulTerminationTimeout;
  }

  public TerminateAppInstanceOpConfig macRecommendedGracefulTerminationTimeout(BigDecimal macRecommendedGracefulTerminationTimeout) {
    this.macRecommendedGracefulTerminationTimeout = macRecommendedGracefulTerminationTimeout;
    return this;
  }

   /**
   * Maximum recommended timeout value that can be needed to gracefully terminate an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.
   * @return macRecommendedGracefulTerminationTimeout
  **/
  @ApiModelProperty(value = "Maximum recommended timeout value that can be needed to gracefully terminate an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.")
  public BigDecimal getMacRecommendedGracefulTerminationTimeout() {
    return macRecommendedGracefulTerminationTimeout;
  }

  public void setMacRecommendedGracefulTerminationTimeout(BigDecimal macRecommendedGracefulTerminationTimeout) {
    this.macRecommendedGracefulTerminationTimeout = macRecommendedGracefulTerminationTimeout;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TerminateAppInstanceOpConfig terminateAppInstanceOpConfig = (TerminateAppInstanceOpConfig) o;
    return Objects.equals(this.minGracefulTerminationTimeout, terminateAppInstanceOpConfig.minGracefulTerminationTimeout) &&
        Objects.equals(this.macRecommendedGracefulTerminationTimeout, terminateAppInstanceOpConfig.macRecommendedGracefulTerminationTimeout);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minGracefulTerminationTimeout, macRecommendedGracefulTerminationTimeout);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerminateAppInstanceOpConfig {\n");
    
    sb.append("    minGracefulTerminationTimeout: ").append(toIndentedString(minGracefulTerminationTimeout)).append("\n");
    sb.append("    macRecommendedGracefulTerminationTimeout: ").append(toIndentedString(macRecommendedGracefulTerminationTimeout)).append("\n");
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

