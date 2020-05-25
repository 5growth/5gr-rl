package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Configuration parameters for the Terminate application instance operation. This follows the TerminateVnfInstanceOpConfig definition of IFA 011.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Configuration parameters for the Terminate application instance operation. This follows the TerminateVnfInstanceOpConfig definition of IFA 011.")

public class TerminateAppInstanceOpConfig   {
  
  private @Valid BigDecimal minGracefulTerminationTimeout = null;
  private @Valid BigDecimal macRecommendedGracefulTerminationTimeout = null;

  /**
   * Minimum timeout value for graceful termination of application instance.
   **/
  public TerminateAppInstanceOpConfig minGracefulTerminationTimeout(BigDecimal minGracefulTerminationTimeout) {
    this.minGracefulTerminationTimeout = minGracefulTerminationTimeout;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum timeout value for graceful termination of application instance.")
  @JsonProperty("minGracefulTerminationTimeout")
  @NotNull
  public BigDecimal getMinGracefulTerminationTimeout() {
    return minGracefulTerminationTimeout;
  }
  public void setMinGracefulTerminationTimeout(BigDecimal minGracefulTerminationTimeout) {
    this.minGracefulTerminationTimeout = minGracefulTerminationTimeout;
  }

  /**
   * Maximum recommended timeout value that can be needed to gracefully terminate an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.
   **/
  public TerminateAppInstanceOpConfig macRecommendedGracefulTerminationTimeout(BigDecimal macRecommendedGracefulTerminationTimeout) {
    this.macRecommendedGracefulTerminationTimeout = macRecommendedGracefulTerminationTimeout;
    return this;
  }

  
  @ApiModelProperty(value = "Maximum recommended timeout value that can be needed to gracefully terminate an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.")
  @JsonProperty("macRecommendedGracefulTerminationTimeout")
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
    return Objects.equals(minGracefulTerminationTimeout, terminateAppInstanceOpConfig.minGracefulTerminationTimeout) &&
        Objects.equals(macRecommendedGracefulTerminationTimeout, terminateAppInstanceOpConfig.macRecommendedGracefulTerminationTimeout);
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

