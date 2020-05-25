package com.ericsson.dummyplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class TrafficRuleDescriptor   {
  
  private @Valid String trafficRuleId = null;

public enum FilterTypeEnum {

    FLOW(String.valueOf("FLOW")), PACKET(String.valueOf("PACKET"));


    private String value;

    FilterTypeEnum (String v) {
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
    public static FilterTypeEnum fromValue(String v) {
        for (FilterTypeEnum b : FilterTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid FilterTypeEnum filterType = null;
  private @Valid BigDecimal priority = null;
  private @Valid List<TrafficFilter> trafficFilter = new ArrayList<TrafficFilter>();

public enum ActionEnum {

    DROP(String.valueOf("DROP")), FORWARD(String.valueOf("FORWARD")), DECAPSULATED(String.valueOf("DECAPSULATED")), FORWARD_AS_IS(String.valueOf("FORWARD_AS_IS")), PASSTHROUGH(String.valueOf("PASSTHROUGH")), DUPLICATED_DECAPSULATED(String.valueOf("DUPLICATED_DECAPSULATED")), DUPLICATE_AS_IS(String.valueOf("DUPLICATE_AS_IS"));


    private String value;

    ActionEnum (String v) {
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
    public static ActionEnum fromValue(String v) {
        for (ActionEnum b : ActionEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid ActionEnum action = null;
  private @Valid List<InterfaceDescriptor> dstInterface = new ArrayList<InterfaceDescriptor>();

  /**
   * Identifies the traffic rule.
   **/
  public TrafficRuleDescriptor trafficRuleId(String trafficRuleId) {
    this.trafficRuleId = trafficRuleId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifies the traffic rule.")
  @JsonProperty("trafficRuleId")
  @NotNull
  public String getTrafficRuleId() {
    return trafficRuleId;
  }
  public void setTrafficRuleId(String trafficRuleId) {
    this.trafficRuleId = trafficRuleId;
  }

  /**
   * Definition of filter type, i.e., per FLOW or PACKET. If it is per FLOW, the filter matches UE-&gt;EPC packets and the reverse packets are handled by the same context.
   **/
  public TrafficRuleDescriptor filterType(FilterTypeEnum filterType) {
    this.filterType = filterType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Definition of filter type, i.e., per FLOW or PACKET. If it is per FLOW, the filter matches UE->EPC packets and the reverse packets are handled by the same context.")
  @JsonProperty("filterType")
  @NotNull
  public FilterTypeEnum getFilterType() {
    return filterType;
  }
  public void setFilterType(FilterTypeEnum filterType) {
    this.filterType = filterType;
  }

  /**
   * Priority of this traffic rule. If traffic rule conflicts, the one with higher priority take precedence.
   **/
  public TrafficRuleDescriptor priority(BigDecimal priority) {
    this.priority = priority;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Priority of this traffic rule. If traffic rule conflicts, the one with higher priority take precedence.")
  @JsonProperty("priority")
  @NotNull
  public BigDecimal getPriority() {
    return priority;
  }
  public void setPriority(BigDecimal priority) {
    this.priority = priority;
  }

  /**
   * The filter used to identify specific flow/packets that need to be handled by the MEC host.
   **/
  public TrafficRuleDescriptor trafficFilter(List<TrafficFilter> trafficFilter) {
    this.trafficFilter = trafficFilter;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The filter used to identify specific flow/packets that need to be handled by the MEC host.")
  @JsonProperty("trafficFilter")
  @NotNull
  public List<TrafficFilter> getTrafficFilter() {
    return trafficFilter;
  }
  public void setTrafficFilter(List<TrafficFilter> trafficFilter) {
    this.trafficFilter = trafficFilter;
  }

  /**
   * Identifies the action of the ME host data plane, when a packet matches the trafficFilter. The example actions include DROP, FORWARD, DECAPSULATED, FORWARD_AS_IS, PASSTHROUGH, DUPLICATED_DECAPSULATED, DUPLICATE_AS_IS.
   **/
  public TrafficRuleDescriptor action(ActionEnum action) {
    this.action = action;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifies the action of the ME host data plane, when a packet matches the trafficFilter. The example actions include DROP, FORWARD, DECAPSULATED, FORWARD_AS_IS, PASSTHROUGH, DUPLICATED_DECAPSULATED, DUPLICATE_AS_IS.")
  @JsonProperty("action")
  @NotNull
  public ActionEnum getAction() {
    return action;
  }
  public void setAction(ActionEnum action) {
    this.action = action;
  }

  /**
   * Describes the destination interface information, if the action is FORWARD. Some applications (e.g. inline/tap) require two interfaces, where the first is on the UE side and the second is on the EPC side.
   **/
  public TrafficRuleDescriptor dstInterface(List<InterfaceDescriptor> dstInterface) {
    this.dstInterface = dstInterface;
    return this;
  }

  
  @ApiModelProperty(value = "Describes the destination interface information, if the action is FORWARD. Some applications (e.g. inline/tap) require two interfaces, where the first is on the UE side and the second is on the EPC side.")
  @JsonProperty("dstInterface")
  public List<InterfaceDescriptor> getDstInterface() {
    return dstInterface;
  }
  public void setDstInterface(List<InterfaceDescriptor> dstInterface) {
    this.dstInterface = dstInterface;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrafficRuleDescriptor trafficRuleDescriptor = (TrafficRuleDescriptor) o;
    return Objects.equals(trafficRuleId, trafficRuleDescriptor.trafficRuleId) &&
        Objects.equals(filterType, trafficRuleDescriptor.filterType) &&
        Objects.equals(priority, trafficRuleDescriptor.priority) &&
        Objects.equals(trafficFilter, trafficRuleDescriptor.trafficFilter) &&
        Objects.equals(action, trafficRuleDescriptor.action) &&
        Objects.equals(dstInterface, trafficRuleDescriptor.dstInterface);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trafficRuleId, filterType, priority, trafficFilter, action, dstInterface);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrafficRuleDescriptor {\n");
    
    sb.append("    trafficRuleId: ").append(toIndentedString(trafficRuleId)).append("\n");
    sb.append("    filterType: ").append(toIndentedString(filterType)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    trafficFilter: ").append(toIndentedString(trafficFilter)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    dstInterface: ").append(toIndentedString(dstInterface)).append("\n");
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

