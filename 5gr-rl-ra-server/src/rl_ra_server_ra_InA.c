////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
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

 * Author: CTTC/CERCA CDN Ricardo Martínez (ricardo.martinez@cttc.es)
 */
/////////////////////////////////////////////////////////////////////////////////////////
#include <stdio.h>
#include <stdlib.h> 
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>
#include <glib.h>
#include <sys/time.h>
#include <ctype.h>
#include <strings.h>
#include <time.h>
#include <math.h>
#include <fcntl.h>

#include "rl_ra_server_stream.h"
#include "rl_ra_server_comp.h"
#include "rl_ra_server_ra_InA.h"

// Global Variables
struct map_nodes_t *mapNodes;
struct graph_t *graph;

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_InA.c
 * 	@brief Dijkstra algorithm
 * 
 *  @param srcMapIndex
 *  @param dstMapIndex
 *	@param g
 *	@param r
 *	@param SN
 *	@param RP 	
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void routing_ra_InA (gint srcMapIndex, gint dstMapIndex, struct graph_t *g, 
						struct interNfviPop_connection_req_t *r,
						struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{
    // Set params into mapNodes related to the source nodes of the request
    mapNodes->map[srcMapIndex].distance = 0;
    mapNodes->map[srcMapIndex].latency = 0.0;
    mapNodes->map[srcMapIndex].avaiBandwidth = 0.0;
    
    // Initialize the set Q and S
    GList *S = NULL, *Q = NULL;
       
    gint indexVertice = -1;
    
    //  Add the source into the Q
    struct nodeItem_t *nodeItem = g_malloc0 (sizeof (struct nodeItem_t));
    if (nodeItem == NULL)
    {
        DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);    
    }
    
    nodeItem->distance = 0;    
	duplicate_node_id (&mapNodes->map[srcMapIndex].verticeId, &nodeItem->node);
    Q = g_list_insert_sorted (Q, nodeItem, sort_by_distance);    
	
	// Check whether there is spurNode (SN) and rootPath (RP)
	if (SN != NULL && RP != NULL)
	{
		DEBUG_RL_RA ("There is rootPath and SpurNode: %s to be considered", SN->nodeId);
		// Iterate through the rootPath from the source to the destination copying all the links till the SpurNode		
		struct routeElement_t *re;
		for (gint j = 0; j < RP->numRouteElements; j++)
		{
			// Get the source and target Nodes of the routeElement within the rootPath
			re = &RP->routeElement[j];
			DEBUG_RL_RA ("root Link: aNodeId: %s (%u) --> zNodeiId: %s (%u)", re->aNodeId.nodeId, re->aLinkId, re->zNodeId.nodeId, re->zLinkId);
			
			// if ingress of the root link (aNodeId) is the spurNode, then stops
			if (memcmp (re->aNodeId.nodeId, SN->nodeId, sizeof (SN->nodeId)) == 0)
			{
				DEBUG_RL_RA ("root Link: aNodeId: %s and spurNode: %s -- stop exploring the rootPath (RP)", re->aNodeId.nodeId, SN->nodeId);
				break;
			}
			// Extract from Q
			GList *listnode = g_list_first (Q);
			struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
			Q = g_list_remove (Q, node);
			
			DEBUG_RL_RA ("Exploring node %s", node->node.nodeId);
			indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
			g_assert (indexVertice >= 0);
			
			// Get the indexTargetedVertice
			gint indexTVertice = -1;
			indexTVertice = graph_targeted_vertice_lookup (indexVertice, re->zNodeId.nodeId, g);			               
			gint done = check_link (node, indexVertice, indexTVertice, g, r, &S, &Q, mapNodes);
			(void)done;
			
			// Add to the S list
			S = g_list_append (S, node);
			DEBUG_RL_RA ("S length: %d", g_list_length (S));     
		}
		
		// Check that the first node in Q set is SpurNode, otherwise something went wrong ...
		if (memcmp (re->aNodeId.nodeId, SN->nodeId, sizeof (SN->nodeId)) != 0)
		{
			DEBUG_RL_RA ("root Link: aNodeId: %s is NOT the spurNode: %s -- something wrong", re->aNodeId.nodeId, SN->nodeId);
			g_list_free_full (S, g_free);
			g_list_free_full (Q, g_free);
			return;
		}		
	}
        
	DEBUG_RL_RA ("Q length: %d", g_list_length (Q));		
    while (g_list_length (Q) > 0)
    {
        //Extract from Q set
		GList *listnode = g_list_first (Q);
        struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
		Q = g_list_remove (Q, node);
        DEBUG_RL_RA ("Q length: %d", g_list_length (Q)); 
        DEBUG_RL_RA ("Exploring node %s", node->node.nodeId);            
               
        // visit all the links from u within the graph
        indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
        g_assert (indexVertice >= 0);
		           
		// Check the targeted vertices from u
		for (gint i = 0; i < g->vertices[indexVertice].numTargetedVertices; i++)
		{
			DEBUG_RL_RA ("Trigger check Link i: %d", i);                
			gint done = check_link (node, indexVertice, i, g, r, &S, &Q, mapNodes); 
			(void)done;
#if 0			
			if (done)
			{
				DEBUG_RL_RA ("destination: %s has been reached", r->dstPEId.nodeId);
				g_list_free_full (S, g_free);
				g_list_free_full (Q, g_free);
				g_free (node);
				return;                    
			}
#endif
		}           
        // Add node into the S Set
        S = g_list_append (S, node);
        DEBUG_RL_RA ("S length: %d", g_list_length (S));              
    }
    
    g_list_free_full (S, g_free);
	g_list_free_full (Q, g_free);
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_InA.c
 * 	@brief k-SP algorithm 
 * 
 *  @param pred
 *	@param g
	@param r
 *	@param SN
 *	@param RP
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint compute_path_sp_ra_InA (struct pred_t *predecessors, struct graph_t *g, struct interNfviPop_connection_req_t *r,
							struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{
    g_assert (r);
	
	// Check that srcPEId and dstPeId in the Req are in the graph
	gint srcMapIndex = get_map_index_by_nodeId (r->srcPEId.nodeId, mapNodes);
    if (srcMapIndex == -1)
    {
        DEBUG_RL_RA ("source: %s is not in the graph", r->srcPEId.nodeId);
        return -1;
    }
    gint dstMapIndex = get_map_index_by_nodeId (r->dstPEId.nodeId, mapNodes);
    if (dstMapIndex == -1)
    {
        DEBUG_RL_RA ("destination: %s is not in the graph", r->dstPEId.nodeId);       
        return -1;
    }
	    
	// Compute the route
    routing_ra_InA (srcMapIndex, dstMapIndex, g, r, SN, RP);
    
    // Check that a feasible solution in term of latency and bandwidth is found
    gint map_dstIndex = get_map_index_by_nodeId (r->dstPEId.nodeId, mapNodes);
	struct map_t *dest_map = &mapNodes->map[map_dstIndex];
    if (!(dest_map->distance < INFINITY_COST))
    {
        DEBUG_RL_RA ("destination: %s is not reachable", r->dstPEId.nodeId);
        return -1;
    }
    
	DEBUG_RL_RA ("Computed avail bw at node: %s is %f", dest_map->verticeId.nodeId, dest_map->avaiBandwidth);	  
    // Check that the computed available bandwidth is larger than 0.0
    if (dest_map->avaiBandwidth <= (gfloat)0.0)
    {
        DEBUG_RL_RA ("destination: %s is not reachable", r->dstPEId.nodeId);
        return -1;
    }    
	DEBUG_RL_RA ("Destination %s is reachable", r->dstPEId.nodeId);    
    
    // Handle predecessors
	struct nodes_t v;	
	duplicate_node_id (&r->dstPEId, &v);
	
	struct edges_t *e = create_edge ();	
	get_edge_from_map_by_node (e, v, mapNodes);
	
	DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u, zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
			
	// Get u (being source of edge e)
	struct nodes_t u;	
	duplicate_node_id (&e->aNodeId, &u);
		
	// Add to the predecessors list
	struct pred_comp_t *pred = &predecessors->predComp[predecessors->numPredComp];
	memcpy (pred->v.nodeId, u.nodeId, sizeof (u.nodeId));
	
	struct edges_t *e1 = &(pred->e);	
	duplicate_edge (e1, e);
	predecessors->numPredComp++;
	DEBUG_RL_RA ("items in predecessors: %d", predecessors->numPredComp);
		
	// Back-trace edges till reaching the srcPEId
	while (memcmp ((const char*)(u.nodeId), (const char *) (r->srcPEId.nodeId), strlen(r->srcPEId.nodeId)) != 0)
	{		
		duplicate_node_id (&u, &v);
		get_edge_from_map_by_node (e, v, mapNodes);
		DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u, zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
		
		// Get the u (being source of edge e)		
		duplicate_node_id (&e->aNodeId, &u);
		
		// Get the new predecessor
		struct pred_comp_t *pred = &predecessors->predComp[predecessors->numPredComp];
			
		// Add to the predecessors list					
		duplicate_node_id (&u, &pred->v);
		
		struct edges_t *e1 = &(pred->e);
		duplicate_edge (e1, e);
		predecessors->numPredComp++;
		DEBUG_RL_RA ("items in predecessors: %d", predecessors->numPredComp);		
	}    
    g_free (e);
    return 1;
}    
	
////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_InA.c
 * 	@brief The algorithm should return the (up to) kPaths) 
 *
 *  @param connRoute
 *  @param req
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void ra_InA_alg_per_connReq (struct compRouteOutput_t *connRoute, struct interNfviPop_connection_req_t *req)
{	
    g_assert (connRoute);
	g_assert (req);
		
	DEBUG_RL_RA ("-- RA InA -- Maximum Number of required Paths: %u", req->kPaths);	
	    
	// Create map of nodes to handle the path computation using the constructed graph
	mapNodes = create_map_node ();	
	build_map_node (mapNodes, graph);
	
    // predecessors to store the computed path    
	struct pred_t * predecessors = create_predecessors ();	
    
    // Compute the shortest path applying Dijkstra algorithm with the current graph
    gint done = compute_path_sp_ra_InA (predecessors, graph, req, NULL, NULL);    
    
    // Create the node list from the predecessors
	struct compRouteOutputItem_t * path = create_path_item ();    
	build_path (path, predecessors, req);	
	DEBUG_RL_RA ("Path is constructed");
	
	gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
	struct map_t *dst_map = &mapNodes->map[indexDest];	
	// Get the delay and cost
	path->pathCost = dst_map->distance;
	memcpy (&path->reqBw, &dst_map->avaiBandwidth, sizeof (dst_map->avaiBandwidth));
	memcpy (&path->latency, &dst_map->latency, sizeof (mapNodes->map[indexDest].latency));	
	DEBUG_RL_RA ("Computed Path Avail Bw: %f, Path Cost: %d, latency: %f", path->reqBw, path->pathCost, path->latency);
    
    // If a single path was requested, then STOP	
	if (req->kPaths == 1)
	{		
		print_path (path);
		connRoute->numCompRoutes++;
		memcpy (connRoute->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
		connRoute->requestId = req->requestId;
		struct compRouteOutputItem_t *targetedPath = &connRoute->compRoutes[0];
		duplicate_path (path, targetedPath);
		g_free (predecessors);
		g_free (path);
		g_free (mapNodes);
		return;		
	}	
	g_free (predecessors);
	
	// Create A and B sets of paths to handle the YEN algorithm
	struct path_set_t *A = create_path_set ();
	struct path_set_t *B = create_path_set ();	
	
	// Add the previously computed path into A->paths[0]	
	duplicate_path (path, &A->paths[0]);
	A->numPaths++;
	g_free (path);
	
	for (gint k = 1; k < req->kPaths; k++)
	{
		DEBUG_RL_RA ("----------------------------------------------------------");
		DEBUG_RL_RA ("Computing k-th (%d) SP", k);
		struct compRouteOutputItem_t *p = create_path_item ();
		duplicate_path (&A->paths[k-1], p);		
				
		// The spurNode ranges from near-end node of the first link to the near-end of the last link forming the kth path
		gint i = 0;
		struct compRouteOutputItem_t *rootPath = create_path_item();		
		for (i = 0; i < p->numRouteElements; i++)
		{
			struct nodes_t *spurNode = create_node (); 
			struct nodes_t *nextSpurNode = create_node ();
			struct routeElement_t *re = &(p->routeElement[i]);
			// Create predecessors to store the computed path
			struct pred_t * predecessors = create_predecessors ();	

			// Clear previous mapNodes, i.e. create it again
			g_free (mapNodes);
			mapNodes = create_map_node ();
			build_map_node (mapNodes, graph);
			
			struct nodes_t *n = &re->aNodeId;			
			duplicate_node_id (n, spurNode);
			
			n = &re->zNodeId;
			duplicate_node_id (n, nextSpurNode);
			DEBUG_RL_RA ("\n");
			DEBUG_RL_RA ("spurNode: %s --> nextSpurNode: %s", spurNode->nodeId, nextSpurNode->nodeId);
			
			// rootPath contains a set of links of A[k-1] from the source Node till the SpurNode -> NextSpurNode
			// Example: A[k-1] = {L1, L2, L3, L4}, i.e. " Node_a -- L1 --> Node_b -- L2 --> Node_c -- L3 --> Node_d -- L4 --> Node_e "
			// E.g., for the ith iteration if the spurNode = Node_c and NextSpurNode = Node_d; then rootPath = {L1, L2, L3}			
			add_routeElement_path_back (re, rootPath);
			DEBUG_RL_RA ("Current rootPath:");
			print_path (rootPath);			
			
			// For all existing and computed paths p in A check if from the source to the NextSpurNode
			// the set of links matches with those contained in the rootPath
			// If YES, remove from the auxiliary graph the next link in p from NextSpurNode
			// Otherwise do nothing 
			struct graph_t *gAux = create_graph ();
			// Baseline graph 
			build_graph (gAux);		
			// Modified graph
			modify_targeted_graph (gAux, A, rootPath, spurNode);			
			
			// Trigger the computation of the path from src to dst constrained to traverse all the links from src 
			// to spurNode contained into rootPath over the resulting graph			
			if (compute_path_sp_ra_InA (predecessors, gAux, req, spurNode, rootPath) == -1)
			{
				DEBUG_RL_RA ("FAILED SP from src: %s via spurNode: %s towards dst: %s", req->srcPEId.nodeId, spurNode->nodeId, req->dstPEId.nodeId);
				g_free (nextSpurNode);
				g_free (spurNode);
				g_free (gAux);
				g_free (predecessors);																							
				continue;        
			}	
			
			DEBUG_RL_RA ("SUCCESFUL SP from src: %s via spurNode: %s towards dst: %s", req->srcPEId.nodeId, spurNode->nodeId, req->dstPEId.nodeId);																								
			// Create the node list from the predecessors
			struct compRouteOutputItem_t * newKpath = create_path_item ();    
			build_path (newKpath, predecessors, req);	
			DEBUG_RL_RA ("new K Path is built");
			
			gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
			struct map_t *dst_map = &mapNodes->map[indexDest];
			// Get the delay and cost and available Bw
			newKpath->pathCost = dst_map->distance;
			memcpy (&newKpath->reqBw, &dst_map->avaiBandwidth, sizeof (dst_map->avaiBandwidth));
			memcpy (&newKpath->latency, &dst_map->latency, sizeof (dst_map->latency));
			DEBUG_RL_RA ("*****************************************************************************");
			DEBUG_RL_RA ("New Candidate (kth: %d) SP --- Path Cost: %d, e2e latency: %f, bw: %f", k, newKpath->pathCost, newKpath->latency, newKpath->reqBw);				
			// Add the computed kth SP to the heap B
			duplicate_path (newKpath, &B->paths[B->numPaths]);
			DEBUG_RL_RA ("*****************************************************************************");
						
			B->numPaths++;
			DEBUG_RL_RA ("Number of B paths: %d", B->numPaths);
			
			g_free (newKpath);
			g_free (nextSpurNode);
			g_free (spurNode);
			g_free (gAux);
			g_free (predecessors);			
		}
		
		// If B is empty then stops
		if (B->numPaths == 0)
		{
			DEBUG_RL_RA ("B does not have any path ... the stops kth computation");
			break;
		}
		
		// Sort the potential paths contained in B by cost and latency and available bandwidth
		sort_path_set (B);
		
		// Add the lowest path into A[k]
		DEBUG_RL_RA ("\n");
		DEBUG_RL_RA ("-------------------------------------------------------------");
		DEBUG_RL_RA ("Added SP from B[0] to A --- Path Cost: %d, e2e Latency: %f", B->paths[0].pathCost, B->paths[0].latency);
		duplicate_path (&B->paths[0], &A->paths[A->numPaths]);
		A->numPaths++;
		DEBUG_RL_RA ("A number of elements: %d", A->numPaths);
		DEBUG_RL_RA ("-------------------------------------------------------------");
		DEBUG_RL_RA ("\n");
		
		// Remove/pòp front element from the path set B (i.e. remove B[0])
		pop_front_path_set (B);	
		DEBUG_RL_RA ("B number of paths", B->numPaths);
	}
	
	// Set the name of the interNfviPopConnectivityId and the requestId
	memcpy (connRoute->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
	connRoute->requestId = req->requestId;
	gint foundRoute = 0;
	for (gint ksp = 0; ksp < A->numPaths; ksp++)
	{		
		if (ksp >= req->kPaths)
		{
			DEBUG_RL_RA ("The number fo requested paths (%d) have been reached", ksp);
			break;				
		}
		
		DEBUG_RL_RA ("A[k-th%d] avilable: %f, pathCost: %d; latency: %f", ksp, A->paths[ksp].reqBw, A->paths[ksp].pathCost, A->paths[ksp].latency);
		struct compRouteOutputItem_t *pathaux = &A->paths[ksp];
		connRoute->numCompRoutes++;
		struct compRouteOutputItem_t *targetedPath = &connRoute->compRoutes[ksp];
		duplicate_path (pathaux, targetedPath);
		print_path (pathaux);
		foundRoute = 1;	
	}
	g_free (A);
	g_free (B);	
	if (foundRoute == 1) return;
	
	// No paths found --> Issue	
	DEBUG_RL_RA ("K-SP failed!!!");
	comp_route_connection_issue_handler (connRoute, req);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_InA.c
 * 	@brief The algorithm should return the (up to) kPaths) 
 *
 *  @param routeList
 *  @param http_code
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void ra_InA_alg_execution (gint *http_code, struct compRouteOutputList_t * routeList)
{	
	g_assert (routeList);
	DEBUG_RL_RA ("routeList : %p", routeList);

	// Iterate over the list of interNfviPop Connections
	for (gint i = 0; i < reqList->numReqList; i++)
	{
		// Get the connection request
		struct interNfviPop_connection_req_t *req = &reqList->interNfviConnReqs[i];
		
		// Get the connection response placeholder
		struct compRouteOutput_t *compRouteConn = &routeList->compRouteConnection[i];
		
		// Check source PE and dst PE are not the same 
		if (same_src_dst_pe_nodeid (req))
		{
			// Issue with this Connection Route Computation			
			comp_route_connection_issue_handler (compRouteConn, req);
			routeList->numCompRouteConnList++;
			continue;			
		}
		
		// process the connection route req and gather the resulting computed paths
		ra_InA_alg_per_connReq (compRouteConn, req);
		routeList->numCompRouteConnList++;
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_InA.c
 * 	@brief handles the path computation triggering k-cspf algorithm
 *
 *  @param compRouteOutputList_t
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint ra_InA_alg (struct compRouteOutputList_t * compRouteOutputList)
{     
	DEBUG_RL_RA ("\n");
	DEBUG_RL_RA ("================================================================");
	DEBUG_RL_RA ("===========================   RA InA   =========================");
	DEBUG_RL_RA ("================================================================");
	
	g_assert (compRouteOutputList);    

	gint http_code = HTTP_CODE_OK;
	
	// Create and construct the graph
	graph = create_graph ();		
	build_graph (graph);
	print_graph (graph);	

	//Triggering the selected path computation
	ra_InA_alg_execution (&http_code, compRouteOutputList);

	g_free (graph);
	g_free (mapNodes);	

	return http_code;
}
