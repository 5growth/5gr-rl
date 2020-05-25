package com.rl.extinterface.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import javax.validation.Valid;


/**
 * The virtual CPU pinning configuration for the virtualised compute resource.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
@ApiModel(description = "The virtual CPU pinning configuration for the virtualised compute resource.")

public class VirtualCpuPinningData   {
  

public enum CpuPinningPolicyEnum {

    STATIC(String.valueOf("static")), DYNAMIC(String.valueOf("dynamic"));


    private String value;

    CpuPinningPolicyEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CpuPinningPolicyEnum fromValue(String v) {
        for (CpuPinningPolicyEnum b : CpuPinningPolicyEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid CpuPinningPolicyEnum cpuPinningPolicy = null;
  private @Valid Object cpuPinningMap = null;

  /**
   * ndicates the policy for CPU pinning. The policy can take values of \&quot;static\&quot; or \&quot;dynamic\&quot;. The cardinality can be 0 during the allocation request, if no particular value is requested.
   **/
  public VirtualCpuPinningData cpuPinningPolicy(CpuPinningPolicyEnum cpuPinningPolicy) {
    this.cpuPinningPolicy = cpuPinningPolicy;
    return this;
  }

  
  @ApiModelProperty(value = "ndicates the policy for CPU pinning. The policy can take values of \"static\" or \"dynamic\". The cardinality can be 0 during the allocation request, if no particular value is requested.")
  @JsonProperty("cpuPinningPolicy")
  public CpuPinningPolicyEnum getCpuPinningPolicy() {
    return cpuPinningPolicy;
  }
  public void setCpuPinningPolicy(CpuPinningPolicyEnum cpuPinningPolicy) {
    this.cpuPinningPolicy = cpuPinningPolicy;
  }

  /**
   * If cpuPinningPolicy is defined as \&quot;static\&quot;, the cpuPinningMap provides the map of pinning virtual CPU cores to physical CPU cores/threads. Cardinality is 0 if cpuPinningPolicy has a different value than \&quot;static\&quot;.
   **/
  public VirtualCpuPinningData cpuPinningMap(Object cpuPinningMap) {
    this.cpuPinningMap = cpuPinningMap;
    return this;
  }

  
  @ApiModelProperty(value = "If cpuPinningPolicy is defined as \"static\", the cpuPinningMap provides the map of pinning virtual CPU cores to physical CPU cores/threads. Cardinality is 0 if cpuPinningPolicy has a different value than \"static\".")
  @JsonProperty("cpuPinningMap")
  public Object getCpuPinningMap() {
    return cpuPinningMap;
  }
  public void setCpuPinningMap(Object cpuPinningMap) {
    this.cpuPinningMap = cpuPinningMap;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualCpuPinningData virtualCpuPinningData = (VirtualCpuPinningData) o;
    return Objects.equals(cpuPinningPolicy, virtualCpuPinningData.cpuPinningPolicy) &&
        Objects.equals(cpuPinningMap, virtualCpuPinningData.cpuPinningMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuPinningPolicy, cpuPinningMap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualCpuPinningData {\n");
    
    sb.append("    cpuPinningPolicy: ").append(toIndentedString(cpuPinningPolicy)).append("\n");
    sb.append("    cpuPinningMap: ").append(toIndentedString(cpuPinningMap)).append("\n");
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

