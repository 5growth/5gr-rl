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
import com.mtp.extinterface.pa.swagger.client.model.CompRouteInputEdges;
import com.mtp.extinterface.pa.swagger.client.model.CompRouteInputNodes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CompRouteInputAbsWanTopo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-02T08:45:54.546Z")
public class CompRouteInputAbsWanTopo {
  @SerializedName("wimId")
  private String wimId = null;

  @SerializedName("nodes")
  private List<CompRouteInputNodes> nodes = new ArrayList<CompRouteInputNodes>();

  @SerializedName("edges")
  private List<CompRouteInputEdges> edges = new ArrayList<CompRouteInputEdges>();

  public CompRouteInputAbsWanTopo wimId(String wimId) {
    this.wimId = wimId;
    return this;
  }

   /**
   * Identifier of WIM related to the asbtracted WAN
   * @return wimId
  **/
  @ApiModelProperty(required = true, value = "Identifier of WIM related to the asbtracted WAN")
  public String getWimId() {
    return wimId;
  }

  public void setWimId(String wimId) {
    this.wimId = wimId;
  }

  public CompRouteInputAbsWanTopo nodes(List<CompRouteInputNodes> nodes) {
    this.nodes = nodes;
    return this;
  }

  public CompRouteInputAbsWanTopo addNodesItem(CompRouteInputNodes nodesItem) {
    this.nodes.add(nodesItem);
    return this;
  }

   /**
   * Get nodes
   * @return nodes
  **/
  @ApiModelProperty(required = true, value = "")
  public List<CompRouteInputNodes> getNodes() {
    return nodes;
  }

  public void setNodes(List<CompRouteInputNodes> nodes) {
    this.nodes = nodes;
  }

  public CompRouteInputAbsWanTopo edges(List<CompRouteInputEdges> edges) {
    this.edges = edges;
    return this;
  }

  public CompRouteInputAbsWanTopo addEdgesItem(CompRouteInputEdges edgesItem) {
    this.edges.add(edgesItem);
    return this;
  }

   /**
   * Get edges
   * @return edges
  **/
  @ApiModelProperty(required = true, value = "")
  public List<CompRouteInputEdges> getEdges() {
    return edges;
  }

  public void setEdges(List<CompRouteInputEdges> edges) {
    this.edges = edges;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompRouteInputAbsWanTopo compRouteInputAbsWanTopo = (CompRouteInputAbsWanTopo) o;
    return Objects.equals(this.wimId, compRouteInputAbsWanTopo.wimId) &&
        Objects.equals(this.nodes, compRouteInputAbsWanTopo.nodes) &&
        Objects.equals(this.edges, compRouteInputAbsWanTopo.edges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wimId, nodes, edges);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompRouteInputAbsWanTopo {\n");
    
    sb.append("    wimId: ").append(toIndentedString(wimId)).append("\n");
    sb.append("    nodes: ").append(toIndentedString(nodes)).append("\n");
    sb.append("    edges: ").append(toIndentedString(edges)).append("\n");
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
