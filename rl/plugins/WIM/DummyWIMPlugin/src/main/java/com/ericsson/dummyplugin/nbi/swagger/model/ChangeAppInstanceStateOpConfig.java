package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Configuration parameters for the change application instance state operation. This follows the OperateVnfOpConfig definition of IFA 011.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Configuration parameters for the change application instance state operation. This follows the OperateVnfOpConfig definition of IFA 011.")

public class ChangeAppInstanceStateOpConfig   {
  
  private @Valid BigDecimal minGracefulStopTimeout = null;
  private @Valid BigDecimal macRecommendedGracefulStopTimeout = null;

  /**
   * Minimum timeout value for graceful stop of application instance.
   **/
  public ChangeAppInstanceStateOpConfig minGracefulStopTimeout(BigDecimal minGracefulStopTimeout) {
    this.minGracefulStopTimeout = minGracefulStopTimeout;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Minimum timeout value for graceful stop of application instance.")
  @JsonProperty("minGracefulStopTimeout")
  @NotNull
  public BigDecimal getMinGracefulStopTimeout() {
    return minGracefulStopTimeout;
  }
  public void setMinGracefulStopTimeout(BigDecimal minGracefulStopTimeout) {
    this.minGracefulStopTimeout = minGracefulStopTimeout;
  }

  /**
   * Maximum recommended timeout value that can be needed to gracefully stop an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.
   **/
  public ChangeAppInstanceStateOpConfig macRecommendedGracefulStopTimeout(BigDecimal macRecommendedGracefulStopTimeout) {
    this.macRecommendedGracefulStopTimeout = macRecommendedGracefulStopTimeout;
    return this;
  }

  
  @ApiModelProperty(value = "Maximum recommended timeout value that can be needed to gracefully stop an application instance of a particular type under certain conditions, such as maximum load condition. This is provided by application provider as information for the operator facilitating the selection of optimal timeout value. This value is not used as constraint.")
  @JsonProperty("macRecommendedGracefulStopTimeout")
  public BigDecimal getMacRecommendedGracefulStopTimeout() {
    return macRecommendedGracefulStopTimeout;
  }
  public void setMacRecommendedGracefulStopTimeout(BigDecimal macRecommendedGracefulStopTimeout) {
    this.macRecommendedGracefulStopTimeout = macRecommendedGracefulStopTimeout;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChangeAppInstanceStateOpConfig changeAppInstanceStateOpConfig = (ChangeAppInstanceStateOpConfig) o;
    return Objects.equals(minGracefulStopTimeout, changeAppInstanceStateOpConfig.minGracefulStopTimeout) &&
        Objects.equals(macRecommendedGracefulStopTimeout, changeAppInstanceStateOpConfig.macRecommendedGracefulStopTimeout);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minGracefulStopTimeout, macRecommendedGracefulStopTimeout);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangeAppInstanceStateOpConfig {\n");
    
    sb.append("    minGracefulStopTimeout: ").append(toIndentedString(minGracefulStopTimeout)).append("\n");
    sb.append("    macRecommendedGracefulStopTimeout: ").append(toIndentedString(macRecommendedGracefulStopTimeout)).append("\n");
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

