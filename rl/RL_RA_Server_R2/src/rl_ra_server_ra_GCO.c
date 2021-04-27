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
#include "rl_ra_server_ra_GCO.h"

gdouble reqRestBw = 0.0;	 // Total Requested Bw to be Restored
gdouble failedRestBw = 0.0; // Total amount of Failed Restored Bw
gint numReqRestConn = 0; // Number of Requested Restored Connections
gint numFailedRestConn = 0; // Number of Failed Restored Connections
struct timeval total_path_comp_time;
gint numGCOPathExecutions = 0;
gint maxRestConnBatch = 0;

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Function used to update the stats for the GCO computation
 *
 * 	@param routeConnList
 *	@param d
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2021
 */
 ////////////////////////////////////////////////////////////////////////////////////////
void GCO_stats(struct compRouteOutputList_t* routeConnList, struct timeval d)
{
	total_path_comp_time.tv_sec = total_path_comp_time.tv_sec + d.tv_sec;
	total_path_comp_time.tv_usec = total_path_comp_time.tv_usec + d.tv_usec;
	total_path_comp_time = tv_adjust(total_path_comp_time);

	gdouble path_comp_time_msec = (((total_path_comp_time.tv_sec) * 1000) + ((total_path_comp_time.tv_usec) / 1000));
	gdouble av_alg_comp_time = ((path_comp_time_msec / numGCOPathExecutions));

	if (reqList->numReqList > maxRestConnBatch)
		maxRestConnBatch = reqList->numReqList;

	// Determine the total amount of Bw to be restored 
	for (gint i = 0; i < reqList->numReqList; i++)
	{
		struct interNfviPop_connection_req_t* connReq = &(reqList->interNfviConnReqs[i]);
		reqRestBw += connReq->bandwidthCons;
		numReqRestConn++;
	}
	// Determine the total amount of Bw successfully being restored
	for (gint j = 0; j < routeConnList->numCompRouteConnList; j++)
	{
		struct compRouteOutput_t* routeOutput = &(routeConnList->compRouteConnection[j]);
		if (routeOutput->noPathIssue == NO_PATH_CONS_ISSUE)
		{
			numFailedRestConn++;
			gint foundFailed = 0;
			for (gint k = 0; k < reqList->numReqList; k++)
			{
				struct interNfviPop_connection_req_t* req = &(reqList->interNfviConnReqs[k]);
				if (strcmp(routeOutput->interNfviPopConnectivityId, req->interNfviPopConnectivityId) == 0)
				{
					failedRestBw += req->bandwidthCons;
					foundFailed = 1;
					break;
				}
			}
			if (foundFailed == 0)
			{
				DEBUG_RL_RA("Failed %s NOT FOUND in the reqList --- Weird!!", routeOutput->interNfviPopConnectivityId);
				exit(-1);
			}
			//struct compRouteOutputItem_t* route = &(routeOutput->compRoutes[0]);
			//failedRestBw += route->reqBw;
			//failedRestBw += routeOutput->reqBw;
		}
	}

	DEBUG_RL_RA("--- STATS GCO PATH COMP ----");
	DEBUG_RL_RA("Total Requested Restoration Conn: %d | Total Failed Restored Connec: %d", numReqRestConn, numFailedRestConn);
	DEBUG_RL_RA("Failed Bw To Be Restored: %f | Blocked Restored Bw: %f", reqRestBw, failedRestBw);
	gdouble restorability = (reqRestBw - failedRestBw) / reqRestBw;
	DEBUG_RL_RA("Restorability: %f", restorability);
	DEBUG_RL_RA("Num GCO Path Computations: %d | AV. PATH REST COMP ALG. TIME: %f ms", numGCOPathExecutions, av_alg_comp_time);
	gdouble avRestConn = numReqRestConn / numGCOPathExecutions;
	DEBUG_RL_RA("Max Rest Conn Batch: %d // Av Rest Connect: %f", maxRestConnBatch, avRestConn);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Function used to compute the factorial for n
 *
 * 	@param n
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
 ////////////////////////////////////////////////////////////////////////////////////////
gint factorial(gint n)
{
	gint f = 1;
	for (gint i = 1; i <= n; i++)
	{
		f = f * i;
	}
	return f;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief sorts the computed route connection lists according to the defined metrics
 *
 *  @param a
 *	@param b
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint sortRouteConnList (gconstpointer a, gconstpointer b)
{
	struct compRouteOutputList_t *o1 = (struct compRouteOutputList_t *)a;
	struct compRouteOutputList_t *o2 = (struct compRouteOutputList_t *)b;
	g_assert (o1);
	g_assert (o2);
	
	// Set of criteria to sort the o1 and o2 solutions for the computed paths for the set of connection requests
	// 1st . The solution having larger amount of succesfully computed connection requests (i.e., a feasible path for them is found)
	if (o1->compRouteOK > o2->compRouteOK)
		return -1;
	if (o2->compRouteOK > o1->compRouteOK)
		return 1;
	// 2nd. If o1 and o2 have the same number of succesfully computed connections, the solution having the larger average allocable bandwidth
	// among the computed paths for the connection requests is sorted firstly
	if (fabs(o1->compRouteConnAvBandwidth - o2->compRouteConnAvBandwidth) > EPSILON)
		return -1;
	if (fabs(o2->compRouteConnAvBandwidth - o1->compRouteConnAvBandwidth) > EPSILON)
		return 1;
	// 3rd. Select the solution where in average the length of the computed paths for the succesfully computed connections is lower
	if (fabs (o1->compRouteConnAvPathLength - o2->compRouteConnAvPathLength) < EPSILON1)
		return -1;
	if (fabs (o2->compRouteConnAvPathLength - o1->compRouteConnAvPathLength) < EPSILON1)
		return 1;
	return 0;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Fucntion to create the array of indexes in support of the randomize function
 *
 *  @param array
 *	@param size
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void createIndexArray (gint *array, gint size)
{	
	for (gint i = 0; i < size; i++)
	{
		array[i] = i;		
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Creates a new Requested Connections List using a randomized function from the original
 *	Requested Connection List
 *
 *  @param reqListOriginal
 *	@param reqListCopy
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void randomizeReqList (struct request_list_t *reqListOriginal, struct request_list_t *reqListCopy)
{
	g_assert (reqListOriginal);
	g_assert (reqListCopy);
	
	gint reqListLength = reqListOriginal->numReqList;
	DEBUG_RL_RA ("Number of Connection Requests: %d", reqListLength);

	// create an array of integers (starting from 0) with the size equal to reqListLength.
	// This array is used to afterwards randomize the Requested Connections into the targeted reqListCopy
	gint indexArray[reqListLength];
	createIndexArray (indexArray, reqListLength);
	
	for (gint i = reqListLength - 1; i > 0; i--)
	{
		gint j = rand() % (i + 1);
		// Do the swap of indexes
		gint temp = indexArray[i];
		indexArray[i] = indexArray[j];
		indexArray[j] = temp;		
	}
	
	for (gint h = 0; h < reqListLength; h++)
	{
		//DEBUG_RL_RA("RANDOMIZED index[%d]: %d", h, indexArray[h]);
	}
	reqListCopy->numReqList = reqListLength;
	// Create the copy for each element according to the randimized array
	for (gint j = 0, k = 0; j < reqListCopy->numReqList; j++, k++)
	{
		gint index = indexArray[j];
		struct interNfviPop_connection_req_t *oReqConn = &(reqListOriginal->interNfviConnReqs[index]);
		struct interNfviPop_connection_req_t *cReqConn = &(reqListCopy->interNfviConnReqs[k]);		
		duplicateReqConn (oReqConn, cReqConn);
	}
	
	for (gint l = 0; l < reqListCopy->numReqList; l++)
	{
		struct interNfviPop_connection_req_t *reqConn = &(reqListCopy->interNfviConnReqs[l]);
		//DEBUG_RL_RA ("interNfviPopConnectivityId: %s", reqConn->interNfviPopConnectivityId);		
	}
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
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
void sp_comp (gint srcMapIndex, gint dstMapIndex, struct graph_t *g, 
						struct interNfviPop_connection_req_t *r,
						struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{
	g_assert (g);
	g_assert (r);
	
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
		//DEBUG_RL_RA ("rootPath and SpurNode (%s) to be considered", SN->nodeId);
		// Iterate through the rootPath from the source to the destination copying all the links till the SpurNode		
		struct routeElement_t *re;
		for (gint j = 0; j < RP->numRouteElements; j++)
		{
			// Get the source and target Nodes of the routeElement within the rootPath
			re = &RP->routeElement[j];
			//DEBUG_RL_RA ("root Link: aNodeId: %s (%u) --> zNodeiId: %s (%u)", re->aNodeId.nodeId, re->aLinkId, re->zNodeId.nodeId, re->zLinkId);
			
			// if ingress of the root link (aNodeId) is the spurNode, then stops
			if (compare_node_id (&re->aNodeId, SN) == 0)
			{
				//DEBUG_RL_RA ("root Link: aNodeId: %s and spurNode: %s -- stop exploring the rootPath (RP)", re->aNodeId.nodeId, SN->nodeId);
				break;
			}
			// Extract from Q
			GList *listnode = g_list_first (Q);
			struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
			Q = g_list_remove (Q, node);
			
			//DEBUG_RL_RA ("Exploring node %s", node->node.nodeId);
			indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
			g_assert (indexVertice >= 0);
			
			// Get the indexTargetedVertice
			gint indexTVertice = -1;
			indexTVertice = graph_targeted_vertice_lookup (indexVertice, re->zNodeId.nodeId, g);			               
			gint done = check_link (node, indexVertice, indexTVertice, g, r, &S, &Q, mapNodes);
			(void)done;
			
			// Add to the S list
			S = g_list_append (S, node);
			//DEBUG_RL_RA ("S length: %d", g_list_length (S));     
		}
		
		// Check that the first node in Q set is SpurNode, otherwise something went wrong ...
		if (compare_node_id (&re->aNodeId, SN) != 0)
		{
			//DEBUG_RL_RA ("root Link: aNodeId: %s is NOT the spurNode: %s -- something wrong", re->aNodeId.nodeId, SN->nodeId);
			g_list_free_full (S, g_free);
			g_list_free_full (Q, g_free);
			return;
		}		
	}
        
	//DEBUG_RL_RA ("Q length: %d", g_list_length (Q));		
    while (g_list_length (Q) > 0)
    {
        //Extract from Q set
		GList *listnode = g_list_first (Q);
        struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
		Q = g_list_remove (Q, node);
        //DEBUG_RL_RA ("Q length: %d", g_list_length (Q)); 
        //DEBUG_RL_RA ("Node %s", node->node.nodeId);            
               
        // visit all the links from u within the graph
        indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
        g_assert (indexVertice >= 0);
		           
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
    g_list_free_full (S, g_free);
	g_list_free_full (Q, g_free);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief KSP computation using Dijkstra algorithm 
 *
 *  @param predecessors
 *	@param g
 *	@param req
 *	@param SN
 *	@param RP
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint ksp_comp (struct pred_t *predecessors, struct graph_t *g, struct interNfviPop_connection_req_t *req, 
				struct nodes_t *SN, struct compRouteOutputItem_t *RP)
{
	g_assert (predecessors);
	g_assert (g);
	g_assert (req);
	
	// Check that srcPEId and dstPeId in the Req are in the graph
	gint srcMapIndex = get_map_index_by_nodeId (req->srcPEId.nodeId, mapNodes);
    if (srcMapIndex == -1)
    {
        DEBUG_RL_RA ("src: %s NOT in the graph", req->srcPEId.nodeId);
        return -1;
    }
    gint dstMapIndex = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
    if (dstMapIndex == -1)
    {
        DEBUG_RL_RA ("dst: %s NOT in the graph", req->dstPEId.nodeId);       
        return -1;
    }	
	// Compute the route
    sp_comp (srcMapIndex, dstMapIndex, g, req, SN, RP);
	
	// Check that a feasible solution in term of latency and bandwidth is found
    gint map_dstIndex = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
	struct map_t *dest_map = &mapNodes->map[map_dstIndex];
    if (!(dest_map->distance < INFINITY_COST))
    {
        DEBUG_RL_RA ("destination: %s NOT reachable", req->dstPEId.nodeId);
        return -1;
    }
    
	DEBUG_RL_RA ("AvailBw @ %s is %f", dest_map->verticeId.nodeId, dest_map->avaiBandwidth);	  
    // Check that the computed available bandwidth is larger than 0.0
    if (dest_map->avaiBandwidth <= (gfloat)0.0)
    {
        DEBUG_RL_RA ("dst: %s NOT REACHABLE", req->dstPEId.nodeId);
        return -1;
    }    
	DEBUG_RL_RA ("dst: %s REACHABLE", req->dstPEId.nodeId);
	 // Handle predecessors
	build_predecessors (predecessors, req, mapNodes);	
    return 1;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Triggering the routing algorithm for req over g
 *
 *  @param route
 *	@param req
 *	@param g
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void ra_GCO_alg_per_connReq (struct compRouteOutput_t *route, struct interNfviPop_connection_req_t *req, struct graph_t *g)
{
	g_assert (route);
	g_assert (req);
	
	// Create map of nodes to handle the path computation using the graph
	mapNodes = create_map_node ();	
	build_map_node (mapNodes, g);	
    // predecessors to store the computed path    
	struct pred_t * predecessors = create_predecessors ();

	// Compute the 1st KSP path
    gint done = ksp_comp (predecessors, g, req, NULL, NULL);
	if (done == -1)
	{
		DEBUG_RL_RA("NO ROUTE %s -> %s", req->srcPEId.nodeId, req->dstPEId.nodeId);
		// NO PATH due to Constraint Issue
		comp_route_connection_issue_handler(route, req);
		g_free(predecessors);
		g_free(mapNodes);
		return;
	}
	
	// Construct the path from the computed predecessors
	struct compRouteOutputItem_t * path = create_path_item (); 
	print_predecessors (predecessors);
	build_path (path, predecessors, req);	
	//DEBUG_RL_RA ("Path is constructed");
	
	gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
	struct map_t *dst_map = &mapNodes->map[indexDest];	
	// Get the delay and cost
	path->pathCost = dst_map->distance;
	memcpy (&path->reqBw, &dst_map->avaiBandwidth, sizeof (dst_map->avaiBandwidth));
	memcpy (&path->latency, &dst_map->latency, sizeof (mapNodes->map[indexDest].latency));	
	//DEBUG_RL_RA ("Computed Path Avail Bw: %f, Path Cost: %d, latency: %f", path->reqBw, path->pathCost, path->latency);
	
	// If 1st SP satisfies the requirements from the req, STOP
	gboolean feasibleRoute = check_computed_path_feasability (req, path);
	if (feasibleRoute == TRUE)
	{
		DEBUG_RL_RA ("1st K-CSPF FEASIBLE, STOP!");
		//print_path (path);		
		route->numCompRoutes++;		
		// Copy the interNfviConnectivityId and RequestId bound to the computed path
		memcpy (route->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
		route->requestId = req->requestId;		
		// Copy the computed path
		struct compRouteOutputItem_t *targetedPath = &route->compRoutes[route->numCompRoutes - 1];
		duplicate_path (path, targetedPath);
		memcpy (&targetedPath->reqBw, &req->bandwidthCons, sizeof (gdouble));
		//print_path (targetedPath);
		g_free (predecessors);
		g_free (path);
		g_free(mapNodes);
		return;		
	}

	DEBUG_RL_RA("1st K-CSPF (%s -> %s) is NOT feasible, trigger KSP computation", req->srcPEId.nodeId, req->dstPEId.nodeId);
		
	// Create A and B sets of paths to handle the YEN algorithm
	struct path_set_t *A = create_path_set ();
	struct path_set_t *B = create_path_set ();	
		
	// Add the previously computed path into A->paths[0]	
	duplicate_path (path, &A->paths[0]);
	A->numPaths++;	
	g_free (predecessors);
	g_free (path);	
	for (gint k = 1; k < GCO_KSP_VALUE; k++)
	{
		DEBUG_RL_RA ("------------ kth (%d) ---------------------", k);
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
			build_map_node (mapNodes, g);			
			struct nodes_t *n = &re->aNodeId;			
			duplicate_node_id (n, spurNode);			
			n = &re->zNodeId;
			duplicate_node_id (n, nextSpurNode);
			//DEBUG_RL_RA ("\n");
			DEBUG_RL_RA ("spurNode: %s --> nextSpurNode: %s", spurNode->nodeId, nextSpurNode->nodeId);
			
			// rootPath contains a set of links of A[k-1] from the source Node till the SpurNode -> NextSpurNode
			// Example: A[k-1] = {L1, L2, L3, L4}, i.e. " Node_a -- L1 --> Node_b -- L2 --> Node_c -- L3 --> Node_d -- L4 --> Node_e "
			// E.g., for the ith iteration if the spurNode = Node_c and NextSpurNode = Node_d; then rootPath = {L1, L2, L3}			
			add_routeElement_path_back (re, rootPath);
			DEBUG_RL_RA ("rootPath:");
			print_path (rootPath);			
			
			// For all existing and computed paths p in A check if from the source to the NextSpurNode
			// the set of links matches with those contained in the rootPath
			// If YES, remove from the auxiliary graph the next link in p from NextSpurNode
			// Otherwise do nothing 
			struct graph_t *gAux = create_graph ();
			// Baseline graph 
			//build_graph (gAux);
			duplicate_graph(g, gAux);
			// Modified graph
			modify_targeted_graph (gAux, A, rootPath, spurNode);			
			
			// Trigger the computation of the path from src to dst constrained to traverse all the links from src 
			// to spurNode contained into rootPath over the resulting graph			
			if (ksp_comp (predecessors, gAux, req, spurNode, rootPath) == -1)
			{
				DEBUG_RL_RA ("FAILED SP from %s via spurNode: %s to %s", req->srcPEId.nodeId, spurNode->nodeId, req->dstPEId.nodeId);
				g_free (nextSpurNode);
				g_free (spurNode);
				g_free (gAux);
				g_free (predecessors);																							
				continue;        
			}			
			DEBUG_RL_RA ("SUCCESFUL SP from %s via spurNode: %s to %s", req->srcPEId.nodeId, spurNode->nodeId, req->dstPEId.nodeId);																								
			// Create the node list from the predecessors
			struct compRouteOutputItem_t * newKpath = create_path_item ();    
			build_path (newKpath, predecessors, req);	
			DEBUG_RL_RA ("new K (for k: %d) Path is built", k);			
			gint indexDest = get_map_index_by_nodeId (req->dstPEId.nodeId, mapNodes);
			struct map_t *dst_map = &mapNodes->map[indexDest];
			// Get the delay and cost and available Bw
			newKpath->pathCost = dst_map->distance;
			memcpy (&newKpath->reqBw, &dst_map->avaiBandwidth, sizeof (dst_map->avaiBandwidth));
			memcpy (&newKpath->latency, &dst_map->latency, sizeof (dst_map->latency));			
			DEBUG_RL_RA ("New PATH (@ kth: %d) ADDED to B[%d] - {Path Cost: %d, e2e latency: %f ms, bw: %f Mb/s}", k, B->numPaths, newKpath->pathCost, newKpath->latency, newKpath->reqBw);
			// Add the computed kth SP to the heap B
			duplicate_path (newKpath, &B->paths[B->numPaths]);						
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
		DEBUG_RL_RA ("-------------------------------------------------------------");
		DEBUG_RL_RA ("To Add SP from B[0] to A[%d] --- Path Cost: %d, e2e Latency: %f", A->numPaths, B->paths[0].pathCost, B->paths[0].latency);
		duplicate_path (&B->paths[0], &A->paths[A->numPaths]);
		A->numPaths++;
		DEBUG_RL_RA ("A Set size: %d", A->numPaths);
		DEBUG_RL_RA ("-------------------------------------------------------------");		
		
		// Remove/pòp front element from the path set B (i.e. remove B[0])
		pop_front_path_set (B);	
		DEBUG_RL_RA ("B Set Size: %d", B->numPaths);
	}
	
	// Set the name of the interNfviPopConnectivityId and the requestId
	memcpy (route->interNfviPopConnectivityId, req->interNfviPopConnectivityId, sizeof (req->interNfviPopConnectivityId));
	route->requestId = req->requestId;	
	for (gint ksp = 1; ksp < A->numPaths; ksp++)
	{		
		if (ksp >= GCO_KSP_VALUE)
		{
			DEBUG_RL_RA ("Number Requested paths (%d) REACHED - STOP", ksp);
			break;				
		}		
		gdouble feasibleRoute = check_computed_path_feasability (req, &A->paths[ksp]);
		if (feasibleRoute == TRUE)
		{		
			DEBUG_RL_RA ("A[k-th%d] available: %f, pathCost: %d; latency: %f", ksp, A->paths[ksp].reqBw, A->paths[ksp].pathCost, A->paths[ksp].latency);
			struct compRouteOutputItem_t *pathaux = &A->paths[ksp];
			route->numCompRoutes++;
			struct compRouteOutputItem_t *targetedPath = &route->compRoutes[route->numCompRoutes - 1];
			duplicate_path (pathaux, targetedPath);
			memcpy(&targetedPath->reqBw, &req->bandwidthCons, sizeof(gdouble));
			print_path (pathaux);
			remove_path_set (A);
			remove_path_set(B);
			return;
		}
	}
	remove_path_set(A);
	remove_path_set(B);	
	// No paths found --> Issue	
	DEBUG_RL_RA ("K-SP failed!!!");	
	comp_route_connection_issue_handler (route, req);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Function used compute the metrics associated to the set of computed routes 
 *	for different InteWANConnection Requests
 *	
 *
 *  @param cOutputList
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void gco_comp_metrics (struct compRouteOutputList_t *cOutputList)
{
	g_assert (cOutputList);	
	//DEBUG_RL_RA ("----- Computing Metrics for the output paths fulfilling the requested connections -----------------------");
	gint numCompRouteOk = 0;
	gdouble ConnAvBandwidth = 0.0f; // For all the succesfully computed requests, this provides the average routed bandwidth
	gdouble ConnAvPathLength = 0.0f; // For all the succesfully computed requests, this provides the average path length in terms of hops
	gdouble	aggConnBandwidth = 0.0f; // For all the succesfully computed requests, this aggregates the routed bandwidth
	gint aggConnPathLength = 0; // For all the succesfully computed requests, this aggregates the path length in terms of hops
		
	for (gint i = 0; i < cOutputList->numCompRouteConnList; i++)
	{
		struct compRouteOutput_t *routeOuput = &(cOutputList->compRouteConnection[i]);
		// Check if the path was succesfully computed		
		if (routeOuput->noPathIssue == NO_PATH_CONS_ISSUE)
		{
			DEBUG_RL_RA ("NO PATH for InterWanConnectionId: %s", routeOuput->interNfviPopConnectivityId);
			// Do not consider in the metrics
			continue;
		}
		// Increase the number of feasible computed paths
		numCompRouteOk++;		
		// Only a single route is computed for each interWanConnection RequestId
		struct compRouteOutputItem_t *route = &(routeOuput->compRoutes[0]);
		//DEBUG_RL_RA ("Ok Computed Route for Req Bw: %f // Path Length: %d", route->reqBw, route->numRouteElements);
		aggConnBandwidth += route->reqBw; // it is added the requested bandwidth of the interWanConnection supported by the computed route
		aggConnPathLength += route->numRouteElements; // it is added the path length (number of hops) 		
	}
	//DEBUG_RL_RA ("aggConnBandwidth: %f / aggConnPathLength: %d", aggConnBandwidth, aggConnPathLength);	
	ConnAvBandwidth = (gdouble)((1.0 / numCompRouteOk) * (aggConnBandwidth));
	ConnAvPathLength = (gdouble)((1.0/numCompRouteOk) * (aggConnPathLength));
	//DEBUG_RL_RA ("ConnAvBandwidth: %f / ConnAvPathLength: %f", ConnAvBandwidth, ConnAvPathLength);
	
	cOutputList->compRouteOK = numCompRouteOk;
	memcpy (&cOutputList->compRouteConnAvBandwidth, &ConnAvBandwidth, sizeof (gdouble));
	memcpy (&cOutputList->compRouteConnAvPathLength, &ConnAvPathLength, sizeof (gdouble));
	
	//DEBUG_RL_RA ("Requested Connections: %d / OK Connections: %d", cOutputList->numCompRouteConnList, cOutputList->compRouteOK);
	//DEBUG_RL_RA ("Metric --- Av. Computed Bw: %f // Av Computed Path Length: %f", cOutputList->compRouteConnAvBandwidth, cOutputList->compRouteConnAvPathLength);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief Function used to compute feasible routes (cOutputList) for the connection requests (cReqList) 
 *	using information in g
 *	
 *
 *  @param cOutputList
 *	@param g
 *	@param cReqList
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void alg_comp (struct compRouteOutputList_t *cOutputList, struct graph_t *g, struct request_list_t *cReqList)
{
	g_assert (cOutputList);
	g_assert (g);
	g_assert (cReqList);
	
	// Iterate over the current request list having the interNfviPop connecitons
	for (gint i = 0; i < cReqList->numReqList; i++)
	{
		// Get the connection request
		struct interNfviPop_connection_req_t *req = &cReqList->interNfviConnReqs[i];	
		DEBUG_RL_RA ("InterNfviPopConnectionId: %s (%s --> %s)", req->interNfviPopConnectivityId, req->srcPEId.nodeId, req->dstPEId.nodeId);
		// Get the connection response placeholder
		struct compRouteOutput_t *compRouteConn = &cOutputList->compRouteConnection[i];
		
		// Check source PE and dst PE are not the same 
		if (same_src_dst_pe_nodeid (req))
		{
			// Issue with this Connection Route Computation			
			comp_route_connection_issue_handler (compRouteConn, req);
			cOutputList->numCompRouteConnList++;
			continue;			
		}
		
		// process the connection route req and gather the resulting computed path
		ra_GCO_alg_per_connReq (compRouteConn, req, g);
		cOutputList->numCompRouteConnList++;
		
		// for each request in cReqList, it is returned a single computed path out of the possible K-CSPF
		// If the computed path is feasible (i.e., NO PATH ISSUE), then the resources used by such a path need to be 
		// reflected in the graph
		if (compRouteConn->noPathIssue == NO_PATH_CONS_ISSUE)
		{
			continue;
		}
		struct compRouteOutputItem_t *path = &(compRouteConn->compRoutes[compRouteConn->numCompRoutes - 1]);
		print_graph(g);
		allocate_graph_resources (path, req, g);
		allocate_graph_reverse_resources(path, req, g);
		print_graph(g);
	}
	// compute metrics for the succesfully computed paths from cReqList (i.e., total computed paths, average allocable bandwidth,
	// average path length
	gco_comp_metrics (cOutputList);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief handles path computations of the requested connections for MAX_NUM_SOL
 *
 *  @param solList
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void ra_GCO_alg_execution (GList **solList)
{
	DEBUG_RL_RA ("--- Executing the GCO for randomized connecttions requests  ---");
	gint sol = 0; // counter to track the amount of computed solutions for the received set of connection requests
	time_t t;
	srand((unsigned) time(&t));	
			
	// create a copy of the current graph
	struct graph_t *graphTemp = create_graph ();
	duplicate_graph (graph, graphTemp);	
	gint solutionId = 0;	
	struct compRouteOutputList_t *routeConnList = create_route_list ();	
	DEBUG_RL_RA ("------------- Triggering Connection Request (solution#%d) -----------------", solutionId);
	alg_comp (routeConnList, graphTemp, reqList);	
	*solList = g_list_insert_sorted (*solList, routeConnList, sortRouteConnList);
	g_free (graphTemp);
	sol++;
	// Retrieve the number of potential solutions to be computed
	gint numPotentialSol = factorial(routeConnList->numCompRouteConnList);
	gint numSolutions = 0;
	if (numPotentialSol == 1)
	{
		numSolutions = 1;
	}
	else
	{
		if (numPotentialSol < MAX_NUM_SOL)
		{
			numSolutions = numPotentialSol + 1;
		}
		else
		{
			numSolutions = MAX_NUM_SOL;
		}
	}
	// Perform the remaining computations randomizing the list of the connections
	while (sol < numSolutions)
	{
		sol++;
		DEBUG_RL_RA ("----- Solution: %d ------", sol);
		// randomize/shufle the requested List containing the connections to be requested
		struct request_list_t *cReqList = create_req_list ();
		randomizeReqList (reqList, cReqList);		
		// create a copy of the current graph
		struct graph_t *graphTemp = create_graph ();
		duplicate_graph (graph, graphTemp);
		solutionId++;
		struct compRouteOutputList_t *routeConnList2 = create_route_list ();		
		DEBUG_RL_RA ("------------- Triggering Connection Request (solutionId#%d) -----------------", solutionId);
		alg_comp (routeConnList2, graphTemp, cReqList);	
		// add output into the solList sorted
		*solList = g_list_insert_sorted (*solList, routeConnList2, sortRouteConnList);		
		g_free (cReqList);
		g_free (graphTemp);
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file 5gr_rl_ra/rl_ra_server_ra_GCO.c
 * 	@brief handles the path computation for restoration, re-allocation and reoptimization
 *	triggering the Global Concurrent Optimization
 *
 *  @param compRouteOutput
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint ra_GCO_alg (struct compRouteOutputList_t * routeConnList)
{   	
	DEBUG_RL_RA ("================================================================");
	DEBUG_RL_RA ("===========================   GCO   =========================");
	DEBUG_RL_RA ("================================================================");
	
	g_assert (routeConnList);

	// timestamp
	struct timeval t0;
	gettimeofday(&t0, NULL);

	numGCOPathExecutions++; // Number executions of the GCO

	gint http_code = HTTP_CODE_OK;	
	
	// Create and construct the current graph
	graph = create_graph ();		
	build_graph (graph);
	//print_graph (graph);

	GList *solutionList = NULL;	
	//Triggering the selected path computation
	ra_GCO_alg_execution (&solutionList);

	// -- timestamp t1
	struct timeval t1, delta;
	gettimeofday(&t1, NULL);
	delta.tv_sec = t1.tv_sec - t0.tv_sec;
	delta.tv_usec = t1.tv_usec - t0.tv_usec;
	delta = tv_adjust(delta);
	
	// Print all the solutions being computed
	//print_route_solution_list (solutionList);	
	// get the first solution from solutionList and copy the contents onto paOutput
	GList *e = g_list_first (solutionList);
	struct compRouteOutputList_t *firstOutput = (struct compRouteOutputList_t *)(e->data);
	duplicate_compRouteOutputList (routeConnList, firstOutput);
	print_graph(graph);
	print_path_list (routeConnList);
	GCO_stats(routeConnList, delta);
	// Remove and detroy the list used for the Solution List Array
	g_list_free_full (solutionList, (GDestroyNotify) destroy_compRouteOutputList);
	g_free (graph);	
	return http_code;
}