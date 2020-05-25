////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	# Copyright 2019 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * Author: Ricardo Martínez
 */
/////////////////////////////////////////////////////////////////////////////////////////

#ifndef _MTP_PA_SERVER_COMP_H
#define _MTP_PA_SERVER_COMP_H

#include <glib.h>
#include <glib/gstdio.h>
#include <glib-2.0/glib/gtypes.h>

#define INFINITY_COST                    0xFFFFFFFF
#define MAX_NUM_PRED					100


#define MAX_NODE_ID_SIZE				128
struct nodes_t {
	gchar nodeId[MAX_NODE_ID_SIZE];
};


struct nodeItem_t
{
    struct nodes_t node;
    guint32 distance;
};

////////////////////////////////////////////////////
// Structure for the Output of the computed route for the request interNfviPopConnectionReq
///////////////////////////////////////////////////
#define         MAX_ROUTE_ELEMENTS  50

struct routeElement_t {
  
	struct nodes_t aNodeId;
	struct nodes_t zNodeId;	
    guint32 aLinkId;
    guint32 zLinkId;    
};

struct compRouteOutput_t
{
    // Identifier of the requested InterNfviPopConnectivityId to be computed
    gchar interNfviPopConnectivityId[128];
    
    // Requested bandwidth (in Mb/s)
    gdouble reqBw; 
    
    struct routeElement_t routeElement[MAX_ROUTE_ELEMENTS];
    gint numRouteElements;  
	
	// delay
	gdouble latency;	
	// cost
	gint linkCost;
};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Structures for collecting the MTP topology including: intra WAN topology and inter-WAN links
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

struct edges_t {	
	//aNodeId	
	struct nodes_t aNodeId;	
	//zNodeId
	struct nodes_t zNodeId;
	
	struct in_addr aNodeIpAddr;
	struct in_addr zNodeIpAddr;
	
	//aLinkId and zlinkId
	guint32 aLinkId;
	guint32 zLinkId;
	
	//QoS attributes: cost/metric, available bandwidth (in Mb/s) and delay in ms
	gint linkCost;
	gdouble linkAvailBw;
	gdouble linkDelay;	
	gchar networkLinkLayer[128];
};


// Structure to handle the path computation
struct pred_comp_t
{
	struct nodes_t v;
	struct edges_t e;	
};

struct pred_t
{
    struct pred_comp_t predComp[MAX_NUM_PRED];
    gint numPredComp;
};

#define MAX_MAP_NODE_SIZE				100

// Structures for the managing the path computation algorithm
struct map_t {
	struct nodes_t verticeId;
	struct edges_t predecessor;
	guint32 distance;
	gdouble avaiBandwidth;
	gdouble latency;	
};

struct map_nodes_t {
    struct map_t map[MAX_MAP_NODE_SIZE];
    gint numMapNodes;
};

#define MAX_NUM_VERTICES				100
#define MAX_NUM_EDGES					100
// Structures for the graph composition
struct targetNodes_t 
{
	// remote / targeted node
	struct nodes_t tVertice;
	// edge conencting a pair of vertices
	struct edges_t edges[MAX_NUM_EDGES];	
	gint numEdges; 
};

struct vertices_t {
	struct targetNodes_t targetedVertices[MAX_NUM_VERTICES];
	gint numTargetedVertices;
    struct nodes_t verticeId;
};

struct graph_t {
	struct vertices_t vertices[MAX_NUM_VERTICES];
	gint numVertices;	
};

#define MAX_NUM_PATHS		10
struct path_set_t
{
	struct compRouteOutput_t paths[MAX_NUM_PATHS];
	gint numPaths;
};

////////////////////////////////////////////////////
// External Variables
///////////////////////////////////////////////////
extern struct interNfviPop_connection_req_t *interNfviPopConnectionReq;
extern struct mtp_topology_t *mtpTopo;


////////////////////////////////////////////////////
// Structure for the Inter Nfvi Pop Connection Requirements
///////////////////////////////////////////////////

struct interNfviPop_connection_req_t
{
	// Identifier of the requested InterNfviPopConnectivityId to be computed
	gchar interNfviPopConnectivityId[128];
	// Indentifier used to determine the used PA mechanism
	guint paId;
	
	// Pair of src and dst PEs for the targeted interNfviPopConnectivity
	struct nodes_t srcPEId;
	struct nodes_t dstPEId;
	struct in_addr srcPEIpAddr;
	struct in_addr dstPEIpAddr;
	
	// Requested QoS constraints to be ensured for the requested interNfviPopConnectvity
	gdouble bandwidthCons;
	gdouble delayCons;	
};

#define MAX_INTER_WAN_LINK	 		100
#define MAX_WAN_TOPO		 		100
#define MAX_NUMBER_NODES_PER_WAN	100
#define MAX_NUMBER_EDGES_PER_WAN	200

struct absWanTopo_t {
	
	//WimId
	gchar WimId[128];
	
	// set of nodes within the WAN
	struct nodes_t nodes[MAX_NUMBER_NODES_PER_WAN];
	gint numNodes;
	
	// set of edges interconnecting the nodes
	struct edges_t edges[MAX_NUMBER_EDGES_PER_WAN];	
	gint numEdges;
};

struct interWanLink_t
{
	// aWimId
	gchar aWimId[128];
	gchar zWimId[128];
		
	struct nodes_t aPEId;
	struct nodes_t zPEId;
	
	struct in_addr aPEIpAddr;
	struct in_addr zPEIpAddr;
    
    guint32 aLinkId;
    guint32 zLinkId;
	
	//QoS attributes: cost/metric, available bandwidth (in Mb/s) and delay in ms
	gint linkCost;
	gdouble linkAvailBw;
	gdouble linkDelay;
};

struct mtp_topology_t 
{
	// interWanLink topology info
	gint numInterWanLinks;
	struct interWanLink_t interWanLinks[MAX_INTER_WAN_LINK];
	
	// Abstracted intraWanTopology
	gint numAbsWanTopo;
	struct absWanTopo_t absWanTop[MAX_WAN_TOPO];
};

struct wansTopo_t 
{
	gint numAbsWanTopo;
	struct absWanTopo_t absWanTop[MAX_WAN_TOPO];

};


// Prototype of external declaration of functions
void print_path (struct compRouteOutput_t *);
struct pred_t * create_predecessors ();
struct nodes_t * create_node ();
struct routeElement_t * create_routeElement ();
void duplicate_node_id (struct nodes_t *, struct nodes_t *);
void duplicate_routeElement (struct routeElement_t *, struct routeElement_t *);
void duplicate_edge (struct edges_t *, struct edges_t *);
void duplicate_path (struct compRouteOutput_t *, struct compRouteOutput_t *);
gint get_map_index_by_nodeId (gchar *, struct map_nodes_t *);
void get_edge_from_map_by_node (struct edges_t *, struct nodes_t, struct map_nodes_t *);
void get_edge_from_predecessors (struct edges_t *, struct nodes_t, struct pred_t *);
void build_path (struct compRouteOutput_t *, struct pred_t *);
void print_graph (struct graph_t *);

gint graph_vertice_lookup (gchar *, struct graph_t *);
gint graph_targeted_vertice_lookup (gint, gchar *, struct graph_t *);
gint graph_targeted_vertice_add (gint vIndex, gchar *nodeId, struct graph_t *g);

void graph_add_edge (gint, gint, gint, struct edges_t *, struct graph_t *);
void remove_edge_from_graph (struct graph_t *, struct edges_t *);
struct edges_t * create_edge ();

struct path_set_t * create_path_set ();
void sort_path_set (struct path_set_t *);
void pop_front_path_set (struct path_set_t *);

void build_graph (struct graph_t *);
void build_map_node(struct map_nodes_t *, struct graph_t *);
struct compRouteOutput_t * create_path();
void add_routeElement_path_back (struct routeElement_t *, struct compRouteOutput_t *);
struct graph_t * create_graph ();
struct map_nodes_t * create_map_node ();

struct mtp_topology_t * create_mtp_topology ();
struct interNfviPop_connection_req_t * create_interNfviPop_conn_req ();

#endif
