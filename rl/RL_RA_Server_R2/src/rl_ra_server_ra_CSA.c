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
#include "rl_ra_server_ra_CSA.h"

// Global Variables
struct map_nodes_t *mapNodes = NULL;
struct graph_t *graph = NULL;

gint errorBwCons = 0; // Path Computation Errors due to not fulfilling Bw constraints
gint errorLatCons = 0; // Path Computation Error due to not fulfilling Latency constraints
gint numPathCompIntents = 0;  // number of events triggering the path computation
gint numSuccesPathComp = 0; // number of events resulting in succesfully path computations fulfilling the constraints
struct timeval total_path_comp_time;


///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_CSA.c
 * 	@brief update statistics of the CSA path computation
 *
 *  @param d
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2021
 */
 /////////////////////////////////////////////////////////////////////////////////////////
void update_stats_csa_path_comp(struct timeval d)
{
	total_path_comp_time.tv_sec = total_path_comp_time.tv_sec + d.tv_sec;
	total_path_comp_time.tv_usec = total_path_comp_time.tv_usec + d.tv_usec;
	total_path_comp_time = tv_adjust(total_path_comp_time);

	gdouble path_comp_time_msec = (((total_path_comp_time.tv_sec) * 1000) + ((total_path_comp_time.tv_usec) / 1000));
	gdouble av_alg_comp_time = ((path_comp_time_msec / numSuccesPathComp));
	DEBUG_RL_RA("\t --- STATS CSA PATH COMP ----");
	DEBUG_RL_RA("Succesfully Comp: %d | Path Comp Requests: %d", numSuccesPathComp, numPathCompIntents);
	DEBUG_RL_RA("Error Bw Cons: %d | Error Latency Cons: %d", errorBwCons, errorLatCons);
	DEBUG_RL_RA("AV. PATH COMP ALG. TIME: %f ms", av_alg_comp_time);
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_CSA.c
 * 	@brief Dijkstra algorithm
 * 
 *  @param srcMapIndex
 *  @param dstMapIndex
 *	@param g
 *  @param r
 *	@param SN
 *	@param RP 	
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void routing_ra_CSA (gint srcMapIndex, gint dstMapIndex, struct graph_t *g, 
						struct interNfviPop_connection_req_t *r,
						struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{
    
	//DEBUG_RL_RA("Basic RSA CSA Routing");
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
	nodeItem->latency = 0.0;
	duplicate_node_id (&mapNodes->map[srcMapIndex].verticeId, &nodeItem->node);
    Q = g_list_insert_sorted (Q, nodeItem, sort_by_distance);    
	
	// Check whether there is spurNode (SN) and rootPath (RP)
	if (SN != NULL && RP != NULL)
	{
		DEBUG_RL_RA ("RootPath and SpurNode: %s", SN->nodeId);
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
		if (strcmp (re->aNodeId.nodeId, SN->nodeId) != 0)
		{
			DEBUG_RL_RA ("root Link: aNodeId: %s is NOT the spurNode: %s -- something wrong", re->aNodeId.nodeId, SN->nodeId);
			g_list_free_full (g_steal_pointer(&S), g_free);
			g_list_free_full (g_steal_pointer(&Q), g_free);
			return;
		}		
	}

	if (g_list_length(Q) == 0)
	{
		DEBUG_RL_RA("Q Length = 0, weird ..");
	}
        
	//DEBUG_RL_RA ("Q length: %d", g_list_length (Q));		
    while (g_list_length (Q) > 0)
    {
        //Extract from Q set
		GList *listnode = g_list_first (Q);
        struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
		Q = g_list_remove (Q, node);
        //DEBUG_RL_RA ("Q length: %d", g_list_length (Q)); 
        //DEBUG_RL_RA ("Exploring node %s", node->node.nodeId);            
               
        // visit all the links from u within the graph
        indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
        g_assert (indexVertice >= 0);

		if (g->vertices[indexVertice].numTargetedVertices == 0)
		{
			DEBUG_RL_RA("Weird, v: %s has %d tVertices", g->vertices[indexVertice].verticeId.nodeId, g->vertices[indexVertice].numTargetedVertices);
		}
		           
		// Check the targeted vertices from u
		for (gint i = 0; i < g->vertices[indexVertice].numTargetedVertices; i++)
		{
			//DEBUG_RL_RA ("Trigger check Link i: %d", i);                
			gint done = check_link (node, indexVertice, i, g, r, &S, &Q, mapNodes);
			(void)done;
		}           
        // Add node into the S Set
        S = g_list_append (S, node);
        //DEBUG_RL_RA ("S length: %d", g_list_length (S));              
    }
    
    g_list_free_full (g_steal_pointer(&S), g_free);
	g_list_free_full (g_steal_pointer(&Q), g_free);
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_CSA.c
 * 	@brief k-SP algorithm 
 * 
 *  @param pred
 *	@param g
 *	@param r
 *	@param SN
 *	@param RP
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint compute_path_sp (struct pred_t *predecessors, struct graph_t *g, struct interNfviPop_connection_req_t *r, 
						struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{	
	g_assert(r);
        
    // Check that srcPEId and dstPeId in the Req are in the graph
	gint srcMapIndex = get_map_index_by_nodeId (r->srcPEId.nodeId, mapNodes);
    if (srcMapIndex == -1)
    {
        DEBUG_RL_RA ("src %s NOT IN THE GRAPH", r->srcPEId.nodeId);
        return -1;
    }
    gint dstMapIndex = get_map_index_by_nodeId (r->dstPEId.nodeId, mapNodes);
    if (dstMapIndex == -1)
    {
        DEBUG_RL_RA ("dst %s NOT IN THE GRAPH", r->dstPEId.nodeId);       
        return -1;
    }
    
	// Compute the route
    routing_ra_CSA (srcMapIndex, dstMapIndex, g, r, SN, RP);    
    // Check that a feasible solution in term of latency and bandwidth is found
    gint map_dstIndex = get_map_index_by_nodeId (r->dstPEId.nodeId, mapNodes);
	struct map_t *dest_map = &mapNodes->map[map_dstIndex];
    if (!(dest_map->distance < INFINITY_COST))
    {
        DEBUG_RL_RA ("dst: %s NOT REACHABLE", r->dstPEId.nodeId);
		errorBwCons++;
        return -1;
    }
    
	//DEBUG_RL_RA ("AvailBw @ %s: %f (reqBw: %f)", dest_map->verticeId.nodeId, dest_map->avaiBandwidth, r->bandwidthCons);    
    if (dest_map->avaiBandwidth < r->bandwidthCons)
    {
        DEBUG_RL_RA ("dst: %s NOT REACHABLE - Bw failure", r->dstPEId.nodeId);
		errorBwCons++;
        return -1;
    }

	if (dest_map->latency > r->delayCons)
    {
        DEBUG_RL_RA ("dst: %s NOT REACHABLE - Latency failure", r->dstPEId.nodeId);
		errorLatCons++;
        return -1;
    }

	//DEBUG_RL_RA ("Destination %s is reachable", r->dstPEId.nodeId);    
    // Handle predecessors
	build_predecessors (predecessors, r, mapNodes);	
    return 1;
}    
	
////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_ra_CSA.c
 * 	@brief K-CSPF algorithm execution (YEN algorithm)
 *
 *  @param compRouteOutput
 *  @param http_code
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void ra_CSA_alg_execution (gint *http_code, struct compRouteOutputList_t * routeConnList)
{	
    g_assert (routeConnList);
	//DEBUG_RL_RA ("routeConnList : %p", routeConnList);		
	
	// For CSA, it is expected a single requested connection
	gint indexReq = 0;
	// check that srcPEId and dstPEId are not equal
	struct interNfviPop_connection_req_t *req = &(reqList->interNfviConnReqs[indexReq]);	
	
	// Check the number of requested paths is for CSA only 1
	if (reqList->numReqList > 1)
	{
		DEBUG_RL_RA ("K-CSPF Algorithm [RA CSA]; Requested kPaths = %d MUST BE equal to 1 for CSA!!!", req->kPaths);
		*http_code = HTTP_CODE_BAD_REQUEST;
		return;		
	}
	
	// Check that source PE and dst PE are NOT the same
	if (same_src_dst_pe_nodeid (req) == 1)
	{
		*http_code = HTTP_CODE_BAD_REQUEST;
		return;
	}
	
	// Create map of nodes to handle the path computation using the constructed graph
	mapNodes = create_map_node ();	
	build_map_node (mapNodes, graph);	
    
    // predecessors to store the computed path    
	struct pred_t * predecessors = create_predecessors ();
    
    // Compute SP applying Dijkstra algorithm
    gint done = compute_path_sp (predecessors, graph, req, NULL, NULL);
	if (done == -1)
	{
		DEBUG_RL_RA(" 1st SP DID NOT WORK...");
		//*http_code = HTTP_CODE_NOT_FOUND;
		routeConnList->numCompRouteConnList++;
		struct compRouteOutput_t* compRouteConn = &(routeConnList->compRouteConnection[routeConnList->numCompRouteConnList - 1]);
		comp_route_connection_issue_handler(compRouteConn, req);
		g_free(mapNodes);
		g_free(predecessors);
		return;
	}
    
    // Create the node list from the predecessors
	struct compRouteOutputItem_t * path = create_path_item ();    
	build_path (path, predecessors, req);	
	gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
	struct map_t *dst_map = &mapNodes->map[indexDest];
	// Get the delay and cost
	path->pathCost = dst_map->distance;
	memcpy (&path->latency, &dst_map->latency, sizeof (dst_map->latency));	
	//DEBUG_RL_RA ("Path Cost: %d, Path Latency: %f, Path Avail. Bw: %f", path->pathCost, path->latency, path->reqBw);
    
    // If 1st SP satisfies the requirements from the req, STOP
	gboolean feasibleRoute = check_computed_path_feasability (req, path);
	if (feasibleRoute == TRUE)
	{
		DEBUG_RL_RA ("1st SP is feasible, STOP");
		//print_path (path);
		// Only one route as output for the CSA
		routeConnList->numCompRouteConnList++;		
		struct compRouteOutput_t *compRouteConn = &(routeConnList->compRouteConnection[routeConnList->numCompRouteConnList - 1]);		
		compRouteConn->numCompRoutes++;
		
		// Copy the interNfviConnectivityId and RequestId bound to the computed path
		memcpy (compRouteConn->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
		compRouteConn->requestId = req->requestId;
		
		// Copy the computed path
		struct compRouteOutputItem_t *targetedPath = &compRouteConn->compRoutes[compRouteConn->numCompRoutes - 1];
		duplicate_path (path, targetedPath);
		g_free (predecessors);
		g_free (path);
		g_free(mapNodes);
		return;		
	}	
	
	DEBUG_RL_RA ("Computed 1st SP does not satisfy constraints -- go for K");
	g_free (predecessors);
	
	// Otherwise trigger the K-SP	
	// Create A and B sets of paths to handle the YEN algorithm	
	struct path_set_t *A = create_path_set ();
	struct path_set_t *B = create_path_set ();	
	
	// Add the computed path into A->paths[0]	
	duplicate_path (path, &A->paths[0]);
	A->numPaths++;
	g_free (path);
		
	for (gint k = 1; k < K_MAX; k++)
	{
		DEBUG_RL_RA ("----------------------------------------------------------");
		DEBUG_RL_RA (" Computing k-th (%d) SP ", k);
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
			
			// Trigger the computation of the path from src to dst constrained to traverse all the links from src to spurNode contained into rootPath
			// over the resulting graph			
			if (compute_path_sp (predecessors, gAux, req, spurNode, rootPath) == -1)
			{
				DEBUG_RL_RA ("FAILED SP -- src: %s via spurNode: %s towards dst: %s", req->srcPEId.nodeId, spurNode->nodeId, 
																						req->dstPEId.nodeId);
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
			gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
			struct map_t *dst_map = &mapNodes->map[indexDest];			
			// Get the delay and cost
			newKpath->pathCost = dst_map->distance;
			memcpy (&newKpath->latency, &dst_map->latency, sizeof (dst_map->latency));			
			DEBUG_RL_RA ("New Candidate (kth: %d) SP --- Path Cost: %d, e2e latency: %f, bw: %f", k, newKpath->pathCost, newKpath->latency, newKpath->reqBw);						
			// Add the computed kth SP to the heap B
			duplicate_path (newKpath, &B->paths[B->numPaths]);			
			
			B->numPaths++;
			DEBUG_RL_RA ("Number of B paths: %d", B->numPaths);
			
			g_free (newKpath);
			g_free (nextSpurNode);
			g_free (spurNode);
			g_free (gAux);
			g_free (predecessors);
			g_free (mapNodes);
		}
		
		// If B is empty then stops
		if (B->numPaths == 0)
		{
			DEBUG_RL_RA ("B does not have any path ... stop kth computation");
			break;
		}		
		// Sort the potential paths contained in B by cost and latency
		sort_path_set (B);		
		// Add the lowest path into A[k]
		
		DEBUG_RL_RA ("-------------------------------------------------------------");
		DEBUG_RL_RA ("Added SP from B[0] to A --- Path Cost: %d, e2e Latency: %f", B->paths[0].pathCost, B->paths[0].latency);
		duplicate_path (&B->paths[0], &A->paths[A->numPaths]);
		A->numPaths++;
		DEBUG_RL_RA ("A number of elements: %d", A->numPaths);
		DEBUG_RL_RA ("-------------------------------------------------------------");
		
		
		// Remove/pòp front element from the path set B (i.e. remove B[0])
		pop_front_path_set (B);	
		DEBUG_RL_RA ("B number of paths: %d", B->numPaths);
	}
		
	feasibleRoute = FALSE;
	for (gint ksp = 1; ksp < A->numPaths; ksp++)
	{
		DEBUG_RL_RA ("A[k-th:%d] linkCost: %d; latency: %f", ksp, A->paths[ksp].pathCost, A->paths[ksp].latency);
		feasibleRoute = check_computed_path_feasability (req, &A->paths[ksp]);
		if (feasibleRoute == TRUE)
		{		
			struct compRouteOutputItem_t *pathaux = &A->paths[ksp];
			print_path (pathaux);	
			
			// Only one route as output for the CSA
			routeConnList->numCompRouteConnList++;
			struct compRouteOutput_t *compRouteConn = &(routeConnList->compRouteConnection[routeConnList->numCompRouteConnList - 1]);
			compRouteConn->numCompRoutes++;
			
			// Copy the interNfviConnectivityId and RequestId bound to the computed path
			memcpy (compRouteConn->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
			compRouteConn->requestId = req->requestId;
			
			// Copy the computed path
			struct compRouteOutputItem_t *targetedPath = &compRouteConn->compRoutes[compRouteConn->numCompRoutes - 1];
			duplicate_path (pathaux, targetedPath);					
			remove_path_set (A);
			remove_path_set(B);
			return;		
		}	
	}
	remove_path_set(A);
	remove_path_set(B);
	
	// No path is found to satisfy the contraints, this should be notified	
	DEBUG_RL_RA ("K-SP failed!!!");    
	// Only one route as output for the CSA
	routeConnList->numCompRouteConnList++;	
	struct compRouteOutput_t *compRouteConn = &(routeConnList->compRouteConnection[routeConnList->numCompRouteConnList - 1]);
	comp_route_connection_issue_handler (compRouteConn, req);		  
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_CSA.c
 * 	@brief handles the path computation triggering k-cspf algorithm
 *
 *  @param compRouteOutput
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint ra_CSA_alg (struct compRouteOutputList_t * routeConnList)
{   	
	DEBUG_RL_RA ("================================================================");
	DEBUG_RL_RA ("===========================   CSA   =========================");
	DEBUG_RL_RA ("================================================================");
	// increase the number of Path Comp. Intents
	numPathCompIntents++;

	// timestamp
	struct timeval t0;
	gettimeofday(&t0, NULL);
	
	g_assert (routeConnList);
	//DEBUG_RL_RA ("routeConnList : %p", routeConnList);    	
	gint http_code = HTTP_CODE_OK;	
	
	// Create and construct the graph
	graph = create_graph ();		
	build_graph (graph);
	//print_graph (graph);
	
	//Triggering the selected path computation
	ra_CSA_alg_execution (&http_code, routeConnList);

	// -- timestamp t1
	struct timeval t1, delta;
	gettimeofday(&t1, NULL);
	delta.tv_sec = t1.tv_sec - t0.tv_sec;
	delta.tv_usec = t1.tv_usec - t0.tv_usec;
	delta = tv_adjust(delta);

	// Check whether succesfully computation
	gboolean successPathComp = FALSE;
	for (gint i = 0; i < routeConnList->numCompRouteConnList; i++)
	{
		struct compRouteOutput_t* compRouteO = &(routeConnList->compRouteConnection[i]);
		if (compRouteO->noPathIssue != NO_PATH_CONS_ISSUE)
		{
			successPathComp = TRUE;
			break;
		}
	}
	if (successPathComp == TRUE)
	{
		// increase the number of Succesfully Path Comp. Intents
		numSuccesPathComp++;
		update_stats_csa_path_comp(delta);
	}
	g_free (graph);
	return http_code;
}