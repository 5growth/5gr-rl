package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes;
import com.ericsson.radioplugin.nbi.swagger.model.CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Type and configuration of virtualised network resources that need to be reserved, e.g. {\&quot;PublicIps\&quot;: 20}
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Type and configuration of virtualised network resources that need to be reserved, e.g. {\"PublicIps\": 20}")

public class CreateNetworkResourceReservationRequestNetworkReservation   {
  
  private @Valid CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes networkAttributes = null;
  private @Valid List<CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts> networkPorts = new ArrayList<CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts>();
  private @Valid Integer numPublicIps = null;

  /**
   **/
  public CreateNetworkResourceReservationRequestNetworkReservation networkAttributes(CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes networkAttributes) {
    this.networkAttributes = networkAttributes;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("networkAttributes")
  @NotNull
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes getNetworkAttributes() {
    return networkAttributes;
  }
  public void setNetworkAttributes(CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes networkAttributes) {
    this.networkAttributes = networkAttributes;
  }

  /**
   * List of specific network ports to be reserved.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservation networkPorts(List<CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts> networkPorts) {
    this.networkPorts = networkPorts;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "List of specific network ports to be reserved.")
  @JsonProperty("networkPorts")
  @NotNull
  public List<CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts> getNetworkPorts() {
    return networkPorts;
  }
  public void setNetworkPorts(List<CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts> networkPorts) {
    this.networkPorts = networkPorts;
  }

  /**
   * Number of public IP addresses to be reserved.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservation numPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Number of public IP addresses to be reserved.")
  @JsonProperty("numPublicIps")
  @NotNull
  public Integer getNumPublicIps() {
    return numPublicIps;
  }
  public void setNumPublicIps(Integer numPublicIps) {
    this.numPublicIps = numPublicIps;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNetworkResourceReservationRequestNetworkReservation createNetworkResourceReservationRequestNetworkReservation = (CreateNetworkResourceReservationRequestNetworkReservation) o;
    return Objects.equals(networkAttributes, createNetworkResourceReservationRequestNetworkReservation.networkAttributes) &&
        Objects.equals(networkPorts, createNetworkResourceReservationRequestNetworkReservation.networkPorts) &&
        Objects.equals(numPublicIps, createNetworkResourceReservationRequestNetworkReservation.numPublicIps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkAttributes, networkPorts, numPublicIps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceReservationRequestNetworkReservation {\n");
    
    sb.append("    networkAttributes: ").append(toIndentedString(networkAttributes)).append("\n");
    sb.append("    networkPorts: ").append(toIndentedString(networkPorts)).append("\n");
    sb.append("    numPublicIps: ").append(toIndentedString(numPublicIps)).append("\n");
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

