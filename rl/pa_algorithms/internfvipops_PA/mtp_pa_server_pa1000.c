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

#include "mtp_pa_server_stream.h"
#include "mtp_pa_server_comp.h"
#include "mtp_pa_server_pa1000.h"

// Global Variables
struct map_nodes_t *mapNodes;
struct graph_t *graph;

gint K_MAX = 5;

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Check the feasability of a path wrt the constraints imposed by the request in terms of latency
 * 
 *  @param p
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_computed_path_feasability (struct compRouteOutput_t * p)
{
	gboolean ret = FALSE;
	float epsilon = 0.0000001; 
	if ((interNfviPopConnectionReq->delayCons - p->latency > 0.0) || (fabs (interNfviPopConnectionReq->delayCons - p->latency) < epsilon))
	{
		DEBUG_MTP_PA_SERVER ("Computed Path (latency: %f) is feasible wrt Connection Demand: %f", p->latency, interNfviPopConnectionReq->delayCons);
		ret = TRUE;		
	}	
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Removal of all the elements into a GList List
 * 
 *  @param userdata
 *  @param data
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void delete_list_nodes (gpointer data, gpointer userdata)
{
	DEBUG_MTP_PA_SERVER ("Removing list element");    
    GList *List = (GList *) userdata;
    if ((data != NULL) && (List != NULL))
    {
        List = g_list_remove (List, data);
        g_free (data);
    }
    return;    
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Sorting the GList Q items by distance 
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint sort_by_distance (gconstpointer a, gconstpointer b)
{
	  DEBUG_MTP_PA_SERVER ("sort by distance a and b");     
     /** check values */
     g_assert(a != NULL);
     g_assert(b != NULL);
	
	  DEBUG_MTP_PA_SERVER ("sort by distance a and b");
	  
     struct nodeItem_t *node1 = (struct nodeItem_t *)a;
     struct nodeItem_t *node2 = (struct nodeItem_t *)b;
     g_assert (node1);
	 g_assert (node2);
	 
     DEBUG_MTP_PA_SERVER ("a->distance (%u); b->distance (%u)", node1->distance, node2->distance); 

     return (node1->distance > node2->distance);
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Supporting fucntion to Check if a nodeId is already in the items of a given GList
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint find_nodeId (gconstpointer data, gconstpointer userdata)
{
     /** check values */
     g_assert(data != NULL);
     g_assert(userdata != NULL);
 
     struct nodeItem_t *SNodeId = (struct nodeItem_t *)data;
     guchar * nodeId = (guchar *)userdata; 
     
     DEBUG_MTP_PA_SERVER ("SNodeId (%s) nodeId (%s)", SNodeId->node.nodeId, nodeId);   
        
     if (!memcmp(SNodeId->node.nodeId, nodeId, strlen (SNodeId->node.nodeId)))
     {
            return (0);
     }
    return -1;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief This function compares ap and rootPath. If all the links are equal between both ap and rootPath till the sN, then the link from sN to next node 
 * 	ap is returned
 * 
 * @params ap
 * @params p
 * @params sN
 * @params e
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean matching_path_rootPath (struct compRouteOutput_t *ap, struct compRouteOutput_t *rootPath, struct nodes_t *sN, struct edges_t *e)
{
	gint j = 0;
	gboolean ret = FALSE;
	while ((j < ap->numRouteElements) && (j < rootPath->numRouteElements))
	{
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) == 0) &&
			//(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) != 0) &&
			(memcmp (sN->nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) == 0))
		{			
						
			duplicate_node_id (&ap->routeElement[j].aNodeId, &e->aNodeId);
			duplicate_node_id (&ap->routeElement[j].zNodeId, &e->zNodeId);		
			DEBUG_MTP_PA_SERVER (" --- copying linkids ----");
			DEBUG_MTP_PA_SERVER ("edge nodes: %s --> %s", e->aNodeId.nodeId, e->zNodeId.nodeId);
			e->aLinkId = ap->routeElement[j].aLinkId;
			e->zLinkId = ap->routeElement[j].zLinkId;
			DEBUG_MTP_PA_SERVER ("Exclude Edge e: %s (%u) ----> %s (%u) from Targeted graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			ret = TRUE;
			return ret;			
		}	
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) == 0) && 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) == 0))
		{
			j++;
			DEBUG_MTP_PA_SERVER ("ap and rootPath sharing the edge: %s (%u) --> %s (%u)", ap->routeElement[j].aNodeId.nodeId, ap->routeElement[j].aLinkId, ap->routeElement[j].zNodeId.nodeId, ap->routeElement[j].zLinkId);
			continue;			
		}
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) != 0) || 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) != 0))
		{
			DEBUG_MTP_PA_SERVER ("ap and rootPath not in the same path");
			return ret;
		}
	}	
	return ret;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief This function is used to modify the graph to be used for running the subsequent SP computations acording to the YEN algorithm principles
 * 
 * @params g
 * @params A
 * @params rootPath
 * @params spurNode
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void modify_targeted_graph (struct graph_t *g, struct path_set_t *A, struct compRouteOutput_t * rootPath, struct nodes_t * spurNode)
{
	DEBUG_MTP_PA_SERVER ("Modify the Targeted graph according to the Yen algorithm principles");
	gint j = 0;
	for (j = 0; j < A->numPaths; j++)
	{
		struct compRouteOutput_t *ap = &A->paths[j];
		struct edges_t *e = create_edge ();
		gboolean ret =  FALSE;
		ret = matching_path_rootPath (ap, rootPath, spurNode, e);		
		if (ret == TRUE)
		{
			DEBUG_MTP_PA_SERVER ("Removal of the edge %s [%u]--> %s [%u] from the graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			remove_edge_from_graph (g, e);
			DEBUG_MTP_PA_SERVER ("Print Resulting Graph");
			print_graph (g);
			g_free (e);			
		}
		if (ret == FALSE)
		{
			g_free (e);
			continue;
		}						
	}	
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Explores the link between u and v
 * 
 *  @param u
 *  @param v 
 *	@param g
 *  @param S
 *  @param Q
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint check_link (struct nodeItem_t *u, gint indexGraphU, gint indexGraphV, struct graph_t *g, GList **S, GList **Q)
{
    gint done = 0;
	struct targetNodes_t* v = &(g->vertices[indexGraphU].targetedVertices[indexGraphV]);      
    DEBUG_MTP_PA_SERVER ("Check link u (%s) --> v (%s)", u->node.nodeId, v->tVertice.nodeId);
    
    // Check whether targetedVertice has been explored in S
    GList *found = g_list_find_custom (*S, v->tVertice.nodeId, find_nodeId);
    if (found != NULL)
    {
        DEBUG_MTP_PA_SERVER ("tv (%s) is already in S", v->tVertice.nodeId);
        done = 0;
        return (done);
    } 
    
    guint32 distance_through_u = INFINITY_COST;
    gdouble latency_through_u = INFINITY_COST;
    
    // Check that there is sufficient availBandwidth on any link between u and v
    gint i = 0;
    gint foundAvailBw = 0;
    gdouble edgeAvailBw = 0.0;
    for (i = 0; i < v->numEdges; i++)
    {        
        memcpy (&edgeAvailBw, &(v->edges[i].linkAvailBw), sizeof (gdouble));
        DEBUG_MTP_PA_SERVER ("Available Bandwidth on edge u (%s)--> v (%s) %f", u->node.nodeId, v->tVertice.nodeId, edgeAvailBw);
        if (edgeAvailBw < interNfviPopConnectionReq->bandwidthCons)
        {
            continue;           
        }
        else
        {
            foundAvailBw = 1;
            break;
        }
    }
    
    if (foundAvailBw == 0)
    {
        DEBUG_MTP_PA_SERVER ("Available Bandwidth on links does not satisfy the request");
        done = 0;
        return (done);    
    }
    
    gint indexEdge = i;    
    
    // Update distance, latency and availBw through u to reach v
    gint map_uIndex = get_map_index_by_nodeId (u->node.nodeId, mapNodes);  
    distance_through_u = mapNodes->map[map_uIndex].distance + v->edges[indexEdge].linkCost;
    latency_through_u = mapNodes->map[map_uIndex].latency + v->edges[indexEdge].linkDelay;
    
    gdouble availBw_through_u = 0.0;   
    if (memcmp (u->node.nodeId, interNfviPopConnectionReq->srcPEId.nodeId, sizeof (u->node.nodeId)) == 0)
    {
        DEBUG_MTP_PA_SERVER ("u: %s is the src in the request --- so the availBw to v: %s is the link's availBw (%f)", u->node.nodeId, 
        																					v->tVertice.nodeId, 
        																					edgeAvailBw);        
        memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));
        DEBUG_MTP_PA_SERVER ("Available Bandwidth on the edge outgoing src (%s) is the that in the edge %f", u->node.nodeId, edgeAvailBw);
    }
    else 
    {
        // Get the minimum for the available bandwidth between the root and u and the edge connecting u and v
        DEBUG_MTP_PA_SERVER ("Available Bandwidth at map[u] (%s) is %f", u->node.nodeId, mapNodes->map[map_uIndex].avaiBandwidth);
        DEBUG_MTP_PA_SERVER ("edge availBw from u --> v %f", edgeAvailBw);
        if (mapNodes->map[map_uIndex].avaiBandwidth <= edgeAvailBw) 
        {
            memcpy (&availBw_through_u, &mapNodes->map[map_uIndex].avaiBandwidth, sizeof (gdouble));    
		}
		else
		{
			memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));
		} 
    }  
    
    DEBUG_MTP_PA_SERVER ("Resulting availBw (from u to v) is %f", availBw_through_u);
    
    gint map_vIndex = get_map_index_by_nodeId (v->tVertice.nodeId, mapNodes); 
	
    // If distance through u to reach v is larger than the current instance at map, discard the link
    if (distance_through_u >=  mapNodes->map[map_vIndex].distance)
    {
        DEBUG_MTP_PA_SERVER ("distance through u (%u) to reach v is larger than existing instance in to the map (%u)", distance_through_u, 
        																						mapNodes->map[map_vIndex].distance);  
        done = 0;
        return done;
    }
    // If the cost through u to reach v are the same as the previous instance into map, use latency to whether discard the link
    if ((distance_through_u ==  mapNodes->map[map_vIndex].distance) && (latency_through_u > mapNodes->map[map_vIndex].latency))
    {
        DEBUG_MTP_PA_SERVER ("distance through u to reach v is equal than existing instance in to the map, but accumulated latency is larger");  
        done = 0;
        return done;
    }
    
    DEBUG_MTP_PA_SERVER ("Link u (%s) --> v (%s) is Relaxed", u->node.nodeId, v->tVertice.nodeId);
    DEBUG_MTP_PA_SERVER ("AvailBw: %f, cost: %u, latency: %f", availBw_through_u, distance_through_u, latency_through_u);
    
    // Update Q list -- 
    struct nodeItem_t *nodeItem = g_malloc0 (sizeof (struct nodeItem_t));
    if (nodeItem == NULL)
    {
		DEBUG_MTP_PA_SERVER ("memory allocation failed\n");
		exit (-1);    
    }
    
    nodeItem->distance = distance_through_u;
	duplicate_node_id (&v->tVertice, &nodeItem->node);    
	
	// add node to the Q list
    *Q = g_list_insert_sorted (*Q, nodeItem, sort_by_distance);
    DEBUG_MTP_PA_SERVER ("nodeItem (%s) is added to Q (%d)", nodeItem->node.nodeId, g_list_length(*Q));    
    
    // Update the mapNodes for the specific reached tv   
    mapNodes->map[map_vIndex].distance = distance_through_u;
    memcpy (&mapNodes->map[map_vIndex].avaiBandwidth, &availBw_through_u, sizeof (gdouble));
    memcpy (&mapNodes->map[map_vIndex].latency, &latency_through_u, sizeof (gdouble));

    // Copy (duplicate) the predecessor edge into the mapNodes 
	struct edges_t *e1 = &(mapNodes->map[map_vIndex].predecessor);
	struct edges_t *e2 = &(v->edges[indexEdge]);
	duplicate_edge (e1, e2);	
	DEBUG_MTP_PA_SERVER ("v edge: %s --> %s", v->edges[indexEdge].aNodeId.nodeId, v->edges[indexEdge].zNodeId.nodeId);
	DEBUG_MTP_PA_SERVER ("mapNodes predecessor edge: %s --> %s", mapNodes->map[map_vIndex].predecessor.aNodeId.nodeId, mapNodes->map[map_vIndex].predecessor.zNodeId.nodeId);

    // Check if v is dstPEId
	 DEBUG_MTP_PA_SERVER ("Targeted dstPEId: %s", interNfviPopConnectionReq->dstPEId.nodeId);
	 DEBUG_MTP_PA_SERVER ("nodeId added to the map: %s", mapNodes->map[map_vIndex].verticeId.nodeId);    
    if (memcmp (mapNodes->map[map_vIndex].verticeId.nodeId, interNfviPopConnectionReq->dstPEId.nodeId, sizeof (mapNodes->map[map_vIndex].verticeId.nodeId)) == 0)
    {
        DEBUG_MTP_PA_SERVER ("v: %s is the targeted destination of the req: %s", mapNodes->map[map_vIndex].verticeId.nodeId, interNfviPopConnectionReq->dstPEId.nodeId);  
        done = 1;		
        return done;        
    }

	 //DEBUG_MTP_PA_SERVER ("Q Length: %d", g_list_length(*Q));
    done = 0;	
    return done;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief Dijkstra algorithm
 * 
 *  @param srcMapIndex
 *  @param dstMapIndex
 *	@param g
 *	@param SN
 *	@param RP 	
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void routing (gint srcMapIndex, gint dstMapIndex, struct graph_t *g, struct nodes_t *SN, struct compRouteOutput_t *RP)
{
    // Set params into mapNodes related to the source nodes of the request
    mapNodes->map[srcMapIndex].distance = 0;
    mapNodes->map[srcMapIndex].latency = 0.0;
    mapNodes->map[srcMapIndex].avaiBandwidth = 0.0;
    
    // Initialize the set Q and S
    GList *S = NULL, *Q = NULL;
    
    gint i = 0;
    gint indexVertice = -1;
    
    //  Add the source into the Q
    struct nodeItem_t *nodeItem = g_malloc0 (sizeof (struct nodeItem_t));
    if (nodeItem == NULL)
    {
        DEBUG_MTP_PA_SERVER ("memory allocation failed\n");
		exit (-1);    
    }
    
    nodeItem->distance = 0;    
	duplicate_node_id (&mapNodes->map[srcMapIndex].verticeId, &nodeItem->node);
    Q = g_list_insert_sorted (Q, nodeItem, sort_by_distance);    
	
	// Check whether there is spurNode (SN) and rootPath (RP)
	if (SN != NULL && RP != NULL)
	{
		DEBUG_MTP_PA_SERVER ("There is rootPath and SpurNode: %s to be considered", SN->nodeId);
		// Iterate through the rootPath from the source to the destination copying all the links till the SpurNode
		gint j = 0;
		struct routeElement_t *re;
		for (j = 0; j < RP->numRouteElements; j++)
		{
			// Get the source and target Nodes of the routeElement within the rootPath
			re = &RP->routeElement[j];
			DEBUG_MTP_PA_SERVER ("root Link: aNodeId: %s (%u) --> zNodeiId: %s (%u)", re->aNodeId.nodeId, re->aLinkId, re->zNodeId.nodeId, re->zLinkId);
			
			// if ingress of the root link (aNodeId) is the spurNode, then stops
			if (memcmp (re->aNodeId.nodeId, SN->nodeId, sizeof (SN->nodeId)) == 0)
			{
				DEBUG_MTP_PA_SERVER ("root Link: aNodeId: %s and spurNode: %s -- stop exploring the rootPath (RP)", re->aNodeId.nodeId, SN->nodeId);
				break;
			}
			// Extract from Q
			GList *listnode = g_list_first (Q);
			struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
			Q = g_list_remove (Q, node);
			
			DEBUG_MTP_PA_SERVER ("Exploring node %s", node->node.nodeId);
			indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
			g_assert (indexVertice >= 0);
			
			// Get the indexTargetedVertice
			gint indexTVertice = -1;
			indexTVertice = graph_targeted_vertice_lookup (indexVertice, re->zNodeId.nodeId, g);			               
			gint done = check_link (node, indexVertice, indexTVertice, g, &S, &Q);
			(void)done;
			
			// Add to the S list
			S = g_list_append (S, node);
			DEBUG_MTP_PA_SERVER ("S length: %d", g_list_length (S));     
		}
		
		// Check that the first node in Q set is SpurNode, otherwise something went wrong ...
		if (memcmp (re->aNodeId.nodeId, SN->nodeId, sizeof (SN->nodeId)) != 0)
		{
			DEBUG_MTP_PA_SERVER ("root Link: aNodeId: %s is NOT the spurNode: %s -- something wrong", re->aNodeId.nodeId, SN->nodeId);
			g_list_free_full (S, g_free);
			g_list_free_full (Q, g_free);
			return;
		}		
	}
        
	DEBUG_MTP_PA_SERVER ("Q length: %d", g_list_length (Q));		
    while (g_list_length (Q) > 0)
    {
        //Extract from Q set
		GList *listnode = g_list_first (Q);
        struct nodeItem_t *node = (struct nodeItem_t *)(listnode->data);
		Q = g_list_remove (Q, node);
        DEBUG_MTP_PA_SERVER ("Q length: %d", g_list_length (Q)); 
        DEBUG_MTP_PA_SERVER ("Exploring node %s", node->node.nodeId);            
               
        // visit all the links from u within the graph
        indexVertice = graph_vertice_lookup (node->node.nodeId, g);        
        g_assert (indexVertice >= 0);
		           
		// Check the targeted vertices from u
		for (i = 0; i < g->vertices[indexVertice].numTargetedVertices; i++)
		{
			DEBUG_MTP_PA_SERVER ("Trigger check Link i: %d", i);                
			gint done = check_link (node, indexVertice, i, g, &S, &Q);                
			if (done)
			{
				DEBUG_MTP_PA_SERVER ("destination: %s has been reached", interNfviPopConnectionReq->dstPEId.nodeId);
				g_list_free_full (S, g_free);
				g_list_free_full (Q, g_free);
				g_free (node);
				return;                    
			}
		}           
        // Add node into the S Set
        S = g_list_append (S, node);
        DEBUG_MTP_PA_SERVER ("S length: %d", g_list_length (S));              
    }
    
    g_list_free_full (S, g_free);
	g_list_free_full (Q, g_free);	
}


///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief k-SP algorithm 
 * 
 *  @param pred
 *	@param g
 *	@param SN
 *	@param RP
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint compute_path_sp (struct pred_t *predecessors, struct graph_t *g, struct nodes_t *SN, struct compRouteOutput_t *RP)
{
    gint done = 0;
    gint srcMapIndex = -1, dstMapIndex = -1;   
        
    // Check that srcPEId and dstPeId in the Req are in the graph
    srcMapIndex = get_map_index_by_nodeId (interNfviPopConnectionReq->srcPEId.nodeId, mapNodes);
    if (srcMapIndex == -1)
    {
        DEBUG_MTP_PA_SERVER ("source: %s is not in the graph", interNfviPopConnectionReq->srcPEId.nodeId);
        done = -1;
        return done;
    }
    dstMapIndex = get_map_index_by_nodeId (interNfviPopConnectionReq->dstPEId.nodeId, mapNodes);
    if (dstMapIndex == -1)
    {
        DEBUG_MTP_PA_SERVER ("destination: %s is not in the graph", interNfviPopConnectionReq->dstPEId.nodeId);
        done = -1;
        return done;
    }
    
	// Compute the route
    routing (srcMapIndex, dstMapIndex, g, SN, RP);
    
    // Check that a feasible solution in term of latency and bandwidth is found
    gint map_dstIndex = get_map_index_by_nodeId (interNfviPopConnectionReq->dstPEId.nodeId, mapNodes);
    if (!(mapNodes->map[map_dstIndex].distance < INFINITY_COST))
    {
        DEBUG_MTP_PA_SERVER ("destination: %s is not reachable", interNfviPopConnectionReq->dstPEId.nodeId);
         done = -1;
        return done;
    }
    
	  DEBUG_MTP_PA_SERVER ("Computed avail bw at node: %s is %f", mapNodes->map[map_dstIndex].verticeId.nodeId, mapNodes->map[map_dstIndex].avaiBandwidth);
	  DEBUG_MTP_PA_SERVER ("Req bw at dst: %s is %f", interNfviPopConnectionReq->dstPEId.nodeId, interNfviPopConnectionReq->bandwidthCons);   
    
    if (mapNodes->map[map_dstIndex].avaiBandwidth < interNfviPopConnectionReq->bandwidthCons)
    {
        DEBUG_MTP_PA_SERVER ("destination: %s is not reachable", interNfviPopConnectionReq->dstPEId.nodeId);
         done = -1;
        return done;
    }
#if 0
    if (mapNodes->map[map_dstIndex].latency > interNfviPopConnectionReq->delayCons)
    {
        DEBUG_MTP_PA_SERVER ("destination: %s is not reachable", interNfviPopConnectionReq->dstPEId.nodeId);
         done = -1;
        return done;
    }
#endif
    
   DEBUG_MTP_PA_SERVER ("Destination %s is reachable", interNfviPopConnectionReq->dstPEId.nodeId);    
    
    // Handle predecessors
	struct nodes_t v;	
	duplicate_node_id (&interNfviPopConnectionReq->dstPEId, &v);
	
	struct edges_t *e = create_edge ();	
	get_edge_from_map_by_node (e, v, mapNodes);
	
	DEBUG_MTP_PA_SERVER ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
			
	// Get u (being source of edge e)
	struct nodes_t u;	
	duplicate_node_id (&e->aNodeId, &u);
		
	// Add to the predecessors list
	memcpy (predecessors->predComp[predecessors->numPredComp].v.nodeId, u.nodeId, sizeof (u.nodeId));
	
	struct edges_t *e1 = &(predecessors->predComp[predecessors->numPredComp].e);	
	duplicate_edge (e1, e);
	predecessors->numPredComp++;
	DEBUG_MTP_PA_SERVER ("items in predecessors: %d", predecessors->numPredComp);
		
	// Back-trace edges till reaching the srcPEId
	while (memcmp ((const char*)(u.nodeId), (const char *) (interNfviPopConnectionReq->srcPEId.nodeId), strlen(interNfviPopConnectionReq->srcPEId.nodeId)) != 0)
	{		
		duplicate_node_id (&u, &v);
		get_edge_from_map_by_node (e, v, mapNodes);
		DEBUG_MTP_PA_SERVER ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
		
		// Get the u (being source of edge e)		
		duplicate_node_id (&e->aNodeId, &u);
			
		// Add to the predecessors list					
		duplicate_node_id (&u, &predecessors->predComp[predecessors->numPredComp].v);
		
		struct edges_t *e1 = &(predecessors->predComp[predecessors->numPredComp].e);
		duplicate_edge (e1, e);
		predecessors->numPredComp++;
		DEBUG_MTP_PA_SERVER ("items in predecessors: %d", predecessors->numPredComp);		
	}    
    g_free (e);
    done = 1;
    return done;
}    
	
////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief K-CSPF algorithm execution (YEN algorithm)
 *
 *  @param compRouteOutput
 *  @param http_code
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void pa1000_alg_execution (gint *http_code, struct compRouteOutput_t *compRouteOutput)
{	
    g_assert (compRouteOutput);
	DEBUG_MTP_PA_SERVER ("compRouteOutput : %p", compRouteOutput);	
	gint done = 0;
    
    // predecessors to store the computed path    
	struct pred_t * predecessors = create_predecessors ();
    
    // Compute the shortest path applying Dijkstra algorithm with the current graph
    done = compute_path_sp (predecessors, graph, NULL, NULL);    
#if 0
    if (done == -1)
    {
        DEBUG_MTP_PA_SERVER ("1st SP failed!!!");
        *http_code = HTTP_CODE_NOT_FOUND;
        return;
    }
#endif
    
    // Create the node list from the predecessors
	struct compRouteOutput_t * path = create_path ();    
	build_path (path, predecessors);	
	DEBUG_MTP_PA_SERVER ("Path is constructed");
	
	gint indexDest = get_map_index_by_nodeId (interNfviPopConnectionReq->dstPEId.nodeId, mapNodes);
	// Get the delay and cost
	path->linkCost = mapNodes->map[indexDest].distance;
	memcpy (&path->latency, &mapNodes->map[indexDest].latency, sizeof (mapNodes->map[indexDest].latency));	
	DEBUG_MTP_PA_SERVER ("Computed Path Cost: %d, latency: %f", path->linkCost, path->latency);
    
    // If 1st SP satisfies the requirements from the req, STOP
	gboolean feasibleRoute = check_computed_path_feasability (path);
	if (feasibleRoute == TRUE)
	{
		DEBUG_MTP_PA_SERVER ("1st K-CSPF is feasible, stop ...");
		print_path (path);
		duplicate_path (path, compRouteOutput);
		g_free (predecessors);
		g_free (path);
		return;		
	}
	
#if 1	
	DEBUG_MTP_PA_SERVER ("Computed 1st SP does not satisfy constraints -- go for K");
	// Otherwise trigger the K-SP
	// Add the computed path into A (vector of paths)	
	// Create A and B sets of paths to handle the YEN algorithm
	g_free (predecessors);
	
	struct path_set_t *A = create_path_set ();
	struct path_set_t *B = create_path_set ();	
	
	// Add the computed path into A->paths[0]	
	duplicate_path (path, &A->paths[0]);
	A->numPaths++;
	g_free (path);
	
	gint k = 0;
	for (k = 1; k < K_MAX; k++)
	{
		DEBUG_MTP_PA_SERVER ("----------------------------------------------------------");
		DEBUG_MTP_PA_SERVER ("Computing k-th (%d) SP", k);
		struct compRouteOutput_t *p = create_path ();
		// Get p = A.paths[k-1]		
		duplicate_path (&A->paths[k-1], p);		
				
		// The spurNode ranges from near-end node of the first link to the near-end of the last link forming the kth path
		gint i = 0;
		struct compRouteOutput_t *rootPath = create_path();		
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
			DEBUG_MTP_PA_SERVER ("\n");
			DEBUG_MTP_PA_SERVER ("spurNode: %s --> nextSpurNode: %s", spurNode->nodeId, nextSpurNode->nodeId);
			
			// rootPath contains a set of links of A[k-1] from the source Node till the SpurNode -> NextSpurNode
			// Example: A[k-1] = {L1, L2, L3, L4}, i.e. " Node_a -- L1 --> Node_b -- L2 --> Node_c -- L3 --> Node_d -- L4 --> Node_e "
			// E.g., for the ith iteration if the spurNode = Node_c and NextSpurNode = Node_d; then rootPath = {L1, L2, L3}			
			add_routeElement_path_back (re, rootPath);
			DEBUG_MTP_PA_SERVER ("Current rootPath:");
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
			done = compute_path_sp (predecessors, gAux, spurNode, rootPath);
			if (done == -1)
			{
				DEBUG_MTP_PA_SERVER ("FAILED SP from src: %s via spurNode: %s towards dst: %s", interNfviPopConnectionReq->srcPEId.nodeId, spurNode->nodeId, 
																								interNfviPopConnectionReq->dstPEId.nodeId);
				g_free (nextSpurNode);
				g_free (spurNode);
				g_free (gAux);
				g_free (predecessors);																							
				continue;        
			}	
			
			DEBUG_MTP_PA_SERVER ("SUCCESFUL SP from src: %s via spurNode: %s towards dst: %s", interNfviPopConnectionReq->srcPEId.nodeId, spurNode->nodeId, 
																								interNfviPopConnectionReq->dstPEId.nodeId);
																								
			// Create the node list from the predecessors
			struct compRouteOutput_t * newKpath = create_path ();    
			build_path (newKpath, predecessors);	
			DEBUG_MTP_PA_SERVER ("new K Path is built");
			
			gint indexDest = get_map_index_by_nodeId (interNfviPopConnectionReq->dstPEId.nodeId, mapNodes);
			// Get the delay and cost
			newKpath->linkCost = mapNodes->map[indexDest].distance;
			memcpy (&newKpath->latency, &mapNodes->map[indexDest].latency, sizeof (mapNodes->map[indexDest].latency));
			DEBUG_MTP_PA_SERVER ("\n");
			DEBUG_MTP_PA_SERVER ("*****************************************************************************");
			DEBUG_MTP_PA_SERVER ("New Candidate (kth: %d) SP --- Path Cost: %d, e2e latency: %f", k, newKpath->linkCost, newKpath->latency);						
			// Add the computed kth SP to the heap B
			duplicate_path (newKpath, &B->paths[B->numPaths]);
			DEBUG_MTP_PA_SERVER ("*****************************************************************************");
			DEBUG_MTP_PA_SERVER ("\n");
			
			B->numPaths++;
			DEBUG_MTP_PA_SERVER ("Number of B paths: %d", B->numPaths);
			
			g_free (newKpath);
			g_free (nextSpurNode);
			g_free (spurNode);
			g_free (gAux);
			g_free (predecessors);
		}
		
		// If B is empty then stops
		if (B->numPaths == 0)
		{
			DEBUG_MTP_PA_SERVER ("B does not have any path ... the stops kth computation");
			break;
		}
		
		// Sort the potential paths contained in B by cost and latency
		sort_path_set (B);
		
		// Add the lowest path into A[k]
		DEBUG_MTP_PA_SERVER ("\n");
		DEBUG_MTP_PA_SERVER ("-------------------------------------------------------------");
		DEBUG_MTP_PA_SERVER ("Added SP from B[0] to A --- Path Cost: %d, e2e Latency: %f", B->paths[0].linkCost, B->paths[0].latency);
		duplicate_path (&B->paths[0], &A->paths[A->numPaths]);
		A->numPaths++;
		DEBUG_MTP_PA_SERVER ("A number of elements: %d", A->numPaths);
		DEBUG_MTP_PA_SERVER ("-------------------------------------------------------------");
		DEBUG_MTP_PA_SERVER ("\n");
		
		// Remove/pòp front element from the path set B (i.e. remove B[0])
		pop_front_path_set (B);	
		DEBUG_MTP_PA_SERVER ("B number of paths", B->numPaths);
	}
	
	gint ksp = 1;
	feasibleRoute = FALSE;
	for (ksp = 1; ksp < A->numPaths; ksp++)
	{
		DEBUG_MTP_PA_SERVER ("A[k-th%d] linkCost: %d; latency: %f", ksp, A->paths[ksp].linkCost, A->paths[ksp].latency);
		feasibleRoute = check_computed_path_feasability (&A->paths[ksp]);
		if (feasibleRoute == TRUE)
		{		
			struct compRouteOutput_t *pathaux = &A->paths[ksp];
			duplicate_path (pathaux, compRouteOutput);
			print_path (pathaux);			
			g_free (A);
			g_free (B);			
			return;
		}	
	}
	g_free (A);
	g_free (B);	
	
	DEBUG_MTP_PA_SERVER ("K-SP failed!!!");
    *http_code = HTTP_CODE_NOT_FOUND;
#endif
  
	return;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_pa1000.c
 * 	@brief handles the path computation triggering k-cspf algorithm
 *
 *  @param compRouteOutput
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint mtp_pa_server_pa1000_alg (struct compRouteOutput_t * compRouteOutput)
{     
	 
	DEBUG_MTP_PA_SERVER ("\n");
	DEBUG_MTP_PA_SERVER ("================================================================");
	DEBUG_MTP_PA_SERVER ("===========================   k-CSPF   =========================");
	DEBUG_MTP_PA_SERVER ("================================================================");
	
	g_assert (compRouteOutput);
	DEBUG_MTP_PA_SERVER ("compRouteOutput : %p", compRouteOutput);
    
	DEBUG_MTP_PA_SERVER ("(PA1000) is triggered for %s -- srcPE: %s; dstPE: %s", interNfviPopConnectionReq->interNfviPopConnectivityId,
								interNfviPopConnectionReq->srcPEId.nodeId, interNfviPopConnectionReq->dstPEId.nodeId);

	gint http_code = HTTP_CODE_OK;

	if (memcmp ((const char*)(interNfviPopConnectionReq->srcPEId.nodeId), (const char *) (interNfviPopConnectionReq->dstPEId.nodeId), strlen(interNfviPopConnectionReq->dstPEId.nodeId)) == 0)
	{
		DEBUG_MTP_PA_SERVER ("K-CSPF Algorithm (PA1000); srcPE: %s; dstPE: %s are equal!!", interNfviPopConnectionReq->srcPEId.nodeId, interNfviPopConnectionReq->dstPEId.nodeId);
		return HTTP_CODE_BAD_REQUEST;
	}
	// Create and construct the graph
	graph = create_graph ();		
	build_graph (graph);
	print_graph (graph);
	
	// Create map of nodes to handle the path computation using the constructed graph
	mapNodes = create_map_node ();	
	build_map_node (mapNodes, graph);	

	//Triggering the selected path computation
	pa1000_alg_execution (&http_code, compRouteOutput);

	g_free (graph);
	g_free (mapNodes);	

	return http_code;
}
