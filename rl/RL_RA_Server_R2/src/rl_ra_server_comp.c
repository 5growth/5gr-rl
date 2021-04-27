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

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server.c
 * 	@brief Function for time processing
 *
 * 	@param a
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
 ////////////////////////////////////////////////////////////////////////////////////////
struct timeval tv_adjust (struct timeval a)
{
	while (a.tv_usec >= 1000000)
	{
		a.tv_usec -= 1000000;
		a.tv_sec++;
	}

	while (a.tv_usec < 0)
	{
		a.tv_usec += 1000000;
		a.tv_sec--;
	}
	return a;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Function used to print the computed the path
 *
 *		@param path
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_path (struct compRouteOutputItem_t *path)
{
	gint k = 0;	
	DEBUG_RL_RA ("=========== COMPUTED PATH =======================");
	DEBUG_RL_RA ("Path Avail. Bw: %f, E2E Path Latency: %f, Path Cost: %d", path->reqBw, path->latency, path->pathCost);
	for (k = 0; k < path->numRouteElements; k++)
	{
		DEBUG_RL_RA ("aNodeId: %s (%u) --> zNodeId: %s (%u)", path->routeElement[k].aNodeId.nodeId, path->routeElement[k].aLinkId, 
																path->routeElement[k].zNodeId.nodeId, path->routeElement[k].zLinkId);		
	}
	DEBUG_RL_RA ("==================================================================");		
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Function used to print all the paths for all the served connections
 *
 *	@param pathConnectionList
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_path_list (struct compRouteOutputList_t *pathConnectionList)
{
	g_assert (pathConnectionList);
	
	DEBUG_RL_RA ("Number of total Connections: %d", pathConnectionList->numCompRouteConnList);
	DEBUG_RL_RA ("Number of OK computed Connections: %d", pathConnectionList->compRouteOK);
	DEBUG_RL_RA ("Average Bandwidth allocated for the OK computed Connections: %f", pathConnectionList->compRouteConnAvBandwidth);
	DEBUG_RL_RA ("Average Path Length (num hops) for the OK computed Connections: %f", pathConnectionList->compRouteConnAvPathLength);
	
	for (gint i = 0; i < pathConnectionList->numCompRouteConnList; i++)
	{
		struct compRouteOutput_t *ro = &(pathConnectionList->compRouteConnection[i]);
		DEBUG_RL_RA ("-------------------------------------------");
		DEBUG_RL_RA ("InterNfviPopConnectivityId: %s", ro->interNfviPopConnectivityId);
		DEBUG_RL_RA ("RequestId: %u", ro->requestId);
		DEBUG_RL_RA ("NoPathIssue: %d", ro->noPathIssue);
		
		// Set of computed paths for such connection Requests
		DEBUG_RL_RA ("Number of computed paths: %d", ro->numCompRoutes);
		for (gint j = 0; j < ro->numCompRoutes; j++)
		{
			DEBUG_RL_RA ("Compute Path#%d", j);
			struct compRouteOutputItem_t *path = &(ro->compRoutes[j]);
			print_path (path);	
		}	
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Creates the predecessors to keep the computed path
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct pred_t * create_predecessors ()
{
	struct pred_t *predecessors = g_malloc0 (sizeof (struct pred_t));
	if (predecessors == NULL)
	{
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);
	}   
	return predecessors;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Prints the list of the predecessors for a given computed Shortest Path
 *
 *	@param p 
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_predecessors (struct pred_t *p)
{
	g_assert (p);
	//DEBUG_RL_RA ("Number of Elements in Predecessor: %d", p->numPredComp);
	for (gint i = 0; i < p->numPredComp; i++)
	{
		struct pred_comp_t *pComp = &(p->predComp[i]);
		//DEBUG_RL_RA ("nodeId: %s", pComp->v.nodeId);
		struct edges_t *e = &(pComp->e);
		//DEBUG_RL_RA ("Edge #%d: [%s (%u) --> %s (%u)]", i, e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);	
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Builds the list of predecessors for the request destination using the computed Shortest Path
 *	being stored in map
 *
 *	@param p 
 *	@param req
 *	@param map
 *	
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_predecessors (struct pred_t *p, struct interNfviPop_connection_req_t *req, struct map_nodes_t *map)
{
	g_assert (p);
	g_assert (req);
	g_assert (map);
	
	struct nodes_t v;	
	duplicate_node_id (&req->dstPEId, &v);
	
	struct edges_t *e = create_edge ();	
	get_edge_from_map_by_node (e, v, map);
	
	//DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u, zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
			
	// Get u (being source of edge e)
	struct nodes_t u;	
	duplicate_node_id (&e->aNodeId, &u);
		
	// Add to the predecessors list
	struct pred_comp_t *pred = &(p->predComp[p->numPredComp]);
	duplicate_node_id (&u, &pred->v);	
	struct edges_t *e1 = &(pred->e);	
	duplicate_edge (e1, e);
	p->numPredComp++;	
	// Back-trace edges till reaching the srcPEId
	while (compare_node_id (&u, &req->srcPEId) != 0)
	{		
		duplicate_node_id (&u, &v);
		get_edge_from_map_by_node (e, v, map);
		//DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u, zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);		
		// Get the u (being source of edge e)		
		duplicate_node_id (&e->aNodeId, &u);		
		// Get the new predecessor
		struct pred_comp_t *pred = &p->predComp[p->numPredComp];			
		// Add to the predecessors list					
		duplicate_node_id (&u, &pred->v);		
		struct edges_t *e1 = &(pred->e);
		duplicate_edge (e1, e);
		p->numPredComp++;
		//DEBUG_RL_RA ("items in predecessors: %d", p->numPredComp);		
	}
	print_predecessors (p);
    g_free (e);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief It creates a struct nodes_t
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct nodes_t * create_node ()
{
	struct nodes_t *n = g_malloc0 (sizeof (struct nodes_t));
	if (n == NULL)
	{
		DEBUG_RL_RA ("memory allocation problem");
		exit (-1);
	}
	return n;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief It creates a routeElement_t
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct routeElement_t * create_routeElement ()
{
	struct routeElement_t *rE = g_malloc0 (sizeof (struct routeElement_t));
	if (rE == NULL)
	{
		DEBUG_RL_RA ("memory allocation problem");
		exit (-1);		
	}
	return rE;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief copy node ids
 *
 *	@param src
 *  @param dst
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_node_id (struct nodes_t *src, struct nodes_t *dst)
{	
	g_assert (src);
	g_assert (dst);
	
	//DEBUG_RL_RA ("Duplicate nodeId for %s", src->nodeId);	
	strcpy (dst->nodeId, src->nodeId);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief compares a pair of node Ids
 *
 *	@param a
 *  @param b
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint compare_node_id (struct nodes_t *a, struct nodes_t *b)
{
	g_assert (a);
	g_assert (b);	
	return (memcmp (&a->nodeId, b->nodeId, strlen (b->nodeId)));	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief duplicate two routeElement_t
 *
 *	@param src
 *  @param dst
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_routeElement (struct routeElement_t *src, struct routeElement_t *dst)
{
	g_assert (src);
	g_assert (dst);
	
	duplicate_node_id (&(src->aNodeId), &(dst->aNodeId));
	duplicate_node_id (&(src->zNodeId), &(dst->zNodeId));
	dst->aLinkId = src->aLinkId;
	dst->zLinkId = src->zLinkId;
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief duplicate two edges
 *
 *	@param e1 (destination)
 *  @param  e2 (source)
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_edge (struct edges_t *e1, struct edges_t *e2)
{
	g_assert (e1);
	g_assert (e2);
		
	duplicate_node_id (&e2->aNodeId, &e1->aNodeId);
	duplicate_node_id (&e2->zNodeId, &e1->zNodeId);
	//DEBUG_RL_RA ("e->aNodeId: %s --->  e->zNodeId: %s", e1->aNodeId.nodeId, e1->zNodeId.nodeId);
	e1->aLinkId = e2->aLinkId;
    e1->zLinkId = e2->zLinkId;
    e1->linkCost = e2->linkCost;
    memcpy (&e1->linkDelay, &e2->linkDelay, sizeof (gdouble));
    memcpy (&e1->linkAvailBw, &e2->linkAvailBw, sizeof (gdouble));
	strcpy (e1->networkLinkLayer, e2->networkLinkLayer);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Duplicate path 
 *
 *	@param a - original
 *  @param b - copy
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_path (struct compRouteOutputItem_t *a, struct compRouteOutputItem_t *b)
{		
	g_assert (a);
	g_assert (b);
	memcpy (&b->reqBw, &a->reqBw, sizeof (gdouble));
	b->numRouteElements = a->numRouteElements;	
	b->pathCost = a->pathCost;
	memcpy (&b->latency, &a->latency, sizeof (gdouble));
	for (gint k = 0; k < a->numRouteElements; k++)
	{			
		//DEBUG_RL_RA ("aNodeId: %s // zNodeId: %s", a->routeElement[k].aNodeId.nodeId, a->routeElement[k].zNodeId.nodeId);
		// aNodeId duplication
		struct nodes_t *n1 = &(a->routeElement[k].aNodeId);
		struct nodes_t *n2 = &(b->routeElement[k].aNodeId);			
		duplicate_node_id (n1, n2);			
					
		//zNodeId duplication
		n1 = &(a->routeElement[k].zNodeId);
		n2 = &(b->routeElement[k].zNodeId);			
		duplicate_node_id (n1, n2);			
					
		b->routeElement[k].aLinkId = a->routeElement[k].aLinkId;
		b->routeElement[k].zLinkId = a->routeElement[k].zLinkId;
		//DEBUG_RL_RA ("[Route Element: %d]; %s (%u)  -----> %s (%u)", (k+1), b->routeElement[k].aNodeId.nodeId, b->routeElement[k].aLinkId, 
		//																b->routeElement[k].zNodeId.nodeId, b->routeElement[k].zLinkId);
	}	
	return;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Duplicate all the computed paths for the set of connection requests
 *
 *	@param a - dCompRouteOutputList
 *  @param b - oCompRouteOutputList
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_compRouteOutputList (struct compRouteOutputList_t *dCompRouteOutputList, 
									struct compRouteOutputList_t *oCompRouteOutputList)
{
	g_assert (dCompRouteOutputList);
	g_assert (dCompRouteOutputList);
	
	dCompRouteOutputList->compRouteOK = oCompRouteOutputList->compRouteOK;
	memcpy (&dCompRouteOutputList->compRouteConnAvBandwidth, &oCompRouteOutputList->compRouteConnAvBandwidth, sizeof (gdouble));
	memcpy (&dCompRouteOutputList->compRouteConnAvPathLength, &oCompRouteOutputList->compRouteConnAvPathLength, sizeof (gdouble));	
	dCompRouteOutputList->numCompRouteConnList = oCompRouteOutputList->numCompRouteConnList;
	
	for (gint i = 0; i < dCompRouteOutputList->numCompRouteConnList; i++)
	{
		struct compRouteOutput_t *dCompRoute = &(dCompRouteOutputList->compRouteConnection[i]);
		struct compRouteOutput_t *oCompRoute = &(oCompRouteOutputList->compRouteConnection[i]);
		
		strcpy (dCompRoute->interNfviPopConnectivityId, oCompRoute->interNfviPopConnectivityId);
		dCompRoute->requestId = oCompRoute->requestId;
		dCompRoute->noPathIssue = oCompRoute->noPathIssue;		
		dCompRoute->numCompRoutes = oCompRoute->numCompRoutes;		
		for (gint j = 0; j < dCompRoute->numCompRoutes; j++)
		{
			struct compRouteOutputItem_t *dPaths = &(dCompRoute->compRoutes[j]);
			struct compRouteOutputItem_t *oPaths = &(oCompRoute->compRoutes[j]);
			duplicate_path (oPaths, dPaths);			
		}
	}
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Return the index into mapN related nodeId
 * 
 *  @param nodeId
 *  @para mapN
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint get_map_index_by_nodeId (gchar *nodeId, struct map_nodes_t * mapN)
{
    gint index = -1;
    gint i = 0;
    
    for (i = 0; i < mapN->numMapNodes; i++)
    {
		//DEBUG_RL_RA ("i: %d; current: %s // targeted: %s", i, mapN->map[i].verticeId.nodeId, nodeId);
        if (memcmp (mapN->map[i].verticeId.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            index = i;
			//DEBUG_RL_RA ("Index: %d", index);
            return index;            
        }
    }
	//DEBUG_RL_RA ("Index: %d", index);
    return index;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Get the edge e enabling reaching the computed v in mapNodes
 * 
 *  @param e
 *  @param v
 *  @param mapN
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void get_edge_from_map_by_node (struct edges_t *e, struct nodes_t v, struct map_nodes_t *mapN)
{
	
	//DEBUG_RL_RA ("Get the Edge into map from node v: %s", v.nodeId);	
	// Get the edge reaching the node v from mapNodes
	gint map_vIndex = get_map_index_by_nodeId (v.nodeId, mapN);
	
	//DEBUG_RL_RA ("aNodeId: %s --> zNodeId: %s", mapN->map[map_vIndex].predecessor.aNodeId.nodeId, mapN->map[map_vIndex].predecessor.zNodeId.nodeId);
	
	struct edges_t *te = &(mapN->map[map_vIndex].predecessor);	
	duplicate_edge (e, te);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Get the edge from the predecessors array for a given node n
 * 
 *  @param e
 *  @param n
 *  @param predecessors
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void get_edge_from_predecessors (struct edges_t *e, struct nodes_t n, struct pred_t *predecessors)
{
	//DEBUG_RL_RA ("Get edge outgoing node %s from predecessors list", n.nodeId);
	//print_predecessors (predecessors);
	for (gint i = 0; i < predecessors->numPredComp; i++)
	{
		struct pred_comp_t *pred = &(predecessors->predComp[i]);
		if (compare_node_id (&n, &pred->v) == 0)
		{
			// Add to the predecessors list
			struct edges_t *te = &(pred->e);
			duplicate_edge (e, te);
			//DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
			return;
		}	
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Construct the path using the predecessors list
 * 
 *  @param path
 *  @param predecessors
 *	@param req
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_path (struct compRouteOutputItem_t *path, struct pred_t *predecessors, struct interNfviPop_connection_req_t *req)
{
	//DEBUG_RL_RA ("\n");
	struct nodes_t v;	
	duplicate_node_id (&req->srcPEId, &v);	
	//DEBUG_RL_RA ("Construct the computed path - src PE Id: %s", v.nodeId);	
	struct edges_t *e = g_malloc0 (sizeof (struct edges_t));
	if (e == NULL)
	{
		DEBUG_RL_RA ("Memory allocation problem");
		exit (-1);
	}
	
	// Get the edge for v in predecessors
	get_edge_from_predecessors (e, v, predecessors);	
	//DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
	// Get the target for e
	struct nodes_t u;	
	duplicate_node_id (&e->zNodeId, &u);
	//DEBUG_RL_RA ("u: %s", u.nodeId);
	memcpy (&path->reqBw, &req->bandwidthCons, sizeof (gdouble));	
	// Add route element to the path being constructed
	gint k = 0;		
	
	duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
	duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);	
	path->routeElement[k].aLinkId = e->aLinkId;
	path->routeElement[k].zLinkId = e->zLinkId;	
	path->numRouteElements++;	
	//DEBUG_RL_RA ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																//path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
	//DEBUG_RL_RA ("Route Elements: %d", path->numRouteElements);
	while (compare_node_id (&u, &req->dstPEId) != 0)	
	{
		k++; 
		path->numRouteElements++;
		// v = u		
		duplicate_node_id (&u, &v);
		//DEBUG_RL_RA ("v: %s u: %s", v.nodeId, u.nodeId);
		get_edge_from_predecessors (e, v, predecessors);
		// Get the target u		
		duplicate_node_id (&e->zNodeId, &u);
			// Add route element to the path being constructed		
		duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
		duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);
		path->routeElement[k].aLinkId = e->aLinkId;
		path->routeElement[k].zLinkId = e->zLinkId;
		//DEBUG_RL_RA ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																// path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
		//DEBUG_RL_RA ("Route Elements: %d", path->numRouteElements);		
	}		
	g_free (e);
	//DEBUG_RL_RA ("Path is constructed");	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Print the graph for debugging purposes
 * 
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_graph (struct graph_t *g)
{	     
    DEBUG_RL_RA ("================================================================");
    DEBUG_RL_RA ("===========================   GRAPH   ==========================");
    DEBUG_RL_RA ("================================================================");
    
    gint i = 0, j = 0, k = 0;
    for (i = 0; i < g->numVertices; i++)
    {
        //DEBUG_RL_RA ("Head Vertice [%s]", g->vertices[i].verticeId.nodeId);
        for (j = 0; j < g->vertices[i].numTargetedVertices; j++)
        {
            //DEBUG_RL_RA ("  Tail Vertice: %s", g->vertices[i].targetedVertices[j].tVertice.nodeId);
            for (k = 0; k < g->vertices[i].targetedVertices[j].numEdges; k++)
            {
                struct edges_t *e = &(g->vertices[i].targetedVertices[j].edges[k]);
				DEBUG_RL_RA ("%s(%u) --> %s(%u) [C: %d, Bw: %f b/s, Delay: %f ms]", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId, e->linkCost, e->linkAvailBw, e->linkDelay);				
           }
        }       
    }     
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Look for a given edge into the graph
 *
 *  @param verticeIndex
 *	@param targetedVerticeIndex
 *  @param e
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint graph_edge_lookup (gint verticeIndex, gint targetedVerticeIndex, struct edges_t *e, struct graph_t *g)
{
	gint indexEdge = -1;
	gint j = 0;
	
	for (j = 0; j < g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].numEdges; j++)
	{
		struct edges_t *e2 = &(g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].edges[j]);
		if ((compare_node_id (&e->aNodeId, &e2->aNodeId) == 0) &&
			(compare_node_id (&e->zNodeId, &e2->zNodeId) == 0) &&
			(e->aLinkId == e2->aLinkId) &&
			(e->zLinkId == e2->zLinkId))
		{
			//DEBUG_RL_RA ("%s (%u) --> %s (%u) FOUND in the Graph at index: %d", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId, j);
			indexEdge = j;
			return indexEdge;
		}		
	}	
	return indexEdge;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Look for a given vertice within the graph using the nodeId
 *
 *  @param nodeId
 *	@param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint graph_vertice_lookup (gchar *nodeId, struct graph_t *g)
{
    gint index = -1;    
    for (gint i = 0; i < g->numVertices; i++)
    {
        if (memcmp (g->vertices[i].verticeId.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            index = i;
            //DEBUG_RL_RA ("%s is found in the graph vertice [%d]", nodeId, index);
            break;
        }     
    }  
    return (index);
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check if a nodeId is already considered into the set of targeted vertices from a given vertice
 *
 *  @param nodeId
 *  @param vIndex
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint graph_targeted_vertice_lookup (gint vIndex, gchar *nodeId, struct graph_t *g)
{
    gint addedTargetedVerticeIndex = -1;
    gint i = 0;
    
    if (g->vertices[vIndex].numTargetedVertices == 0)
    {
        return (addedTargetedVerticeIndex);
    }
    
    for (i = 0; i < g->vertices[vIndex].numTargetedVertices; i++)
    {
        if (memcmp (g->vertices[vIndex].targetedVertices[i].tVertice.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            DEBUG_RL_RA ("Targeted %s reachable from %s", nodeId, g->vertices[vIndex].verticeId.nodeId);
            addedTargetedVerticeIndex = i;
            return (addedTargetedVerticeIndex);
        }        
    }    
    // not found ...    
    return (addedTargetedVerticeIndex);
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check if a nodeId is already considered into the set of targeted vertices from a given vertice, if not to be added
 *
 *  @param nodeId
 *  @param vIndex
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint graph_targeted_vertice_add (gint vIndex, gchar *nodeId, struct graph_t *g)
{
    gint addedTargetedVerticeIndex = -1;
    gint i = 0;
    
    if (g->vertices[vIndex].numTargetedVertices == 0)
    {
        //DEBUG_RL_RA ("targeted vertice %s being reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);        
        addedTargetedVerticeIndex = 0;
        return (addedTargetedVerticeIndex);
    }
    
    for (i = 0; i < g->vertices[vIndex].numTargetedVertices; i++)
    {        
		if (memcmp (g->vertices[vIndex].targetedVertices[i].tVertice.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            //DEBUG_RL_RA ("Targeted vertice %s is already considered in the reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);
            addedTargetedVerticeIndex = -1;
            return (addedTargetedVerticeIndex);
        }        
    }    
    // It is not found, next to be added at i position
    addedTargetedVerticeIndex = i;
    return (addedTargetedVerticeIndex);
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Add the edge to the graph using passed vertice
 *
 *  @param verticeIndex
 *  @param addedTargetedVerticeIndex
 *  @param indexEdge
 *  @param edge
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void graph_add_edge (gint verticeIndex, gint addedTargetedVerticeIndex, gint indexEdge, struct edges_t *edge, struct graph_t *g)
{
    //DEBUG_RL_RA ("Adding Edge to the Graph");	
	struct edges_t *e = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex].edges[indexEdge]); 
	duplicate_edge (e, edge);    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Remove edge from the graph
 *
 *  @param g
 *  @param e
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
void remove_edge_from_graph (struct graph_t *g, struct edges_t *e)
{
	// Find the ingress vertice into the graph
	DEBUG_RL_RA ("Removing from Graph %s[%u]) ---> %s[%u]", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
	gint verticeIndex = -1;		
	verticeIndex = graph_vertice_lookup (e->aNodeId.nodeId, g);
	if (verticeIndex == -1)
	{
		DEBUG_RL_RA ("Edge w/ %s is NOT in the Graph!!", e->aNodeId.nodeId);
		return;
	}
	
	// Find the targeted vertice from vertice Id
	gint targetedVerticeIndex = -1;
	targetedVerticeIndex = graph_targeted_vertice_lookup (verticeIndex, e->zNodeId.nodeId, g);
	if (targetedVerticeIndex == -1)
	{
		DEBUG_RL_RA ("%s --> %s NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	//DEBUG_RL_RA ("%s --> %s found in the Graph", e->aNodeId.nodeId, e->zNodeId.nodeId);
	
	// Get the edge position
	gint edgeIndex = -1;
	edgeIndex = graph_edge_lookup (verticeIndex, targetedVerticeIndex, e, g);
	if (edgeIndex == -1)
	{
		DEBUG_RL_RA ("%s --> %s NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	//DEBUG_RL_RA ("%s --> %s FOUND in Graph w/ edgeIndex: %d", e->aNodeId.nodeId, e->zNodeId.nodeId, edgeIndex);
	
	// Remove the edge
	//DEBUG_RL_RA ("Start Removing %s --> %s from Graph", e->aNodeId.nodeId, e->zNodeId.nodeId);	
	struct targetNodes_t *v = &(g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex]);	
	for (gint j = edgeIndex; j < v->numEdges; j++)
	{	
		struct edges_t *e1 = &(v->edges[j]);
		struct edges_t *e2 = &(v->edges[j+1]);		
		duplicate_edge (e1, e2);
	}
	v->numEdges --;
	DEBUG_RL_RA ("Number of Edges between %s and %s is %d", e->aNodeId.nodeId, e->zNodeId.nodeId, v->numEdges);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief create edge
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct edges_t * create_edge ()
{
	struct edges_t *e = g_malloc0 (sizeof (struct edges_t));
	if (e == NULL)
	{
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);
	}    
	return e;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief create the pointer for keeping a set of the paths (struct compRouteOutput_t)
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct path_set_t * create_path_set ()
{
	struct path_set_t * p = g_malloc0 (sizeof (struct path_set_t));
	if (p == NULL)
	{
		DEBUG_RL_RA ("Memory allocation problem");
		exit (-1);		
	}
	return p;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Remove the path set
 *
 * @param p
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2021
 */
 /////////////////////////////////////////////////////////////////////////////////////////
void remove_path_set(struct path_set_t* p)
{
	g_assert(p);
	g_free(p);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check whether a specific node was determined to experience an anomaly
 *
 *	@param absWantopo
 *	@param node
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_anomaly_node_wim (struct absWanTopo_t *absWantopo, struct nodes_t *node)
{
	g_assert (absWantopo);
	g_assert (node);
		
	gboolean foundNode = FALSE;	
	for (gint i = 0; i < anomalyEventList->numAnomalyEvents; i++)
	{
		struct anomalyEvent_t *aE = &anomalyEventList->anomalyEvents[i];
		// Check the WimId
		if (memcmp(aE->nodeAnomaly.wimId, absWantopo->WimId, sizeof (absWantopo->WimId)) != 0)
			continue;
		// Check nodeId
		if (compare_node_id (&aE->nodeAnomaly.nodeId, node) != 0)
			continue;
		// Check node status 
		if (strncmp ((const char*)aE->nodeAnomaly.nodeStatus, "failed", 6) == 0);
		{
			foundNode = TRUE;
			return foundNode;
		}
	}
	return foundNode;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check whether a specific link is either failed or degraded (e.g., bandwidth reduction or
 *	decreased/increased delay)
 *
 *	@param absWantopo
 *	@param e
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_anomaly_link (struct absWanTopo_t *absWantopo, struct edges_t *e)
{
	g_assert (absWantopo);
	g_assert (e);	
	gboolean failedLink = FALSE;	
	
	for (gint i = 0; i < anomalyEventList->numAnomalyEvents; i++)
	{
		struct anomalyEvent_t *aE = &(anomalyEventList->anomalyEvents[i]);
		// Check the WimId of the WAN where the edge e belongs to
		if (memcmp(aE->linkAnomaly.aWimId, absWantopo->WimId, sizeof (absWantopo->WimId)) != 0)
			continue;
		// Check if head-end node of the edge and linkId matches with the failed link
		if (compare_node_id (&aE->linkAnomaly.aNodeId, &e->aNodeId) != 0)		
			continue;
		if (aE->linkAnomaly.aLinkId != e->aLinkId)
			continue;
		if (strncmp ((const char*)aE->linkAnomaly.linkStatus, "failed", 6) == 0)
		{
			DEBUG_RL_RA ("%s (%u) FAILED", e->aNodeId.nodeId, e->aLinkId);
			failedLink = TRUE;
			return failedLink;			
		}		
		if (strncmp ((const char*)aE->linkAnomaly.linkStatus, "degraded", 8) == 0)
		{
			DEBUG_RL_RA ("Degraded Link [%s(%u) --> %s(%u)]", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			if (aE->linkAnomaly.linkPacketLoss > 0.0f)
			{
				// Packet Loss x (e.g., 0.5) meaning that 50% of the packets are lost. 
				// If packet loss is 0.95, this entails lossing 95% of the packets
				// In terms of the link bw this can be assumed that the available bw is reduced (1 - packetLoss)
				gdouble linkBw = e->linkAvailBw * (1.0f - aE->linkAnomaly.linkPacketLoss);
				memcpy (&(e->linkAvailBw), &(linkBw), sizeof (gdouble));
				DEBUG_RL_RA ("After Anomaly [%s(%u) --> %s(%u)] Bw: %f", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId, e->linkAvailBw);
			}			
			if (aE->linkAnomaly.linkPacketDelay > 0.0f)
			{
				// Link Delay x (e.g., 0.5, 1.5) meaning that the link delay is either reduced by x (i.e., 0.5) or
				// increased by x (e.g., 1.5)
				gdouble linkDelay = e->linkDelay * aE->linkAnomaly.linkPacketDelay;
				memcpy (&(e->linkDelay), &(linkDelay), sizeof (gdouble));
				return failedLink;
			}	
		}		
	}	
	return failedLink;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check whether a border WAN node is failed. This entails discarding the 
 *	the interWANlinks outgoing that specific failed WAN node
 *
 *	@param l
 *	@param n
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_anomaly_node_interWanLink (struct interWanLink_t *l, struct nodes_t *n)
{
	g_assert (l);
	g_assert (n);	
	gboolean failedNode = FALSE;	
	
	for (gint i = 0; i < anomalyEventList->numAnomalyEvents; i++)
	{
		struct anomalyEvent_t *aE = &anomalyEventList->anomalyEvents[i];
		// Check WimId
		if (memcmp(aE->nodeAnomaly.wimId, l->aWimId, sizeof (l->aWimId)) != 0)
			continue;
		// Check nodeId
		if (compare_node_id (&aE->nodeAnomaly.nodeId, n) != 0)
			continue;
		// Check node status 
		if (strncmp ((const char*)aE->nodeAnomaly.nodeStatus, "failed", 6) == 0);
		{
			failedNode = TRUE;
			return failedNode;
		}		
	}	
	return failedNode;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check whether a interWAN link is failed or degraded (i.e., lower Bw or higher delay
 *
 *	@param l
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_anomaly_link_interWanLink (struct interWanLink_t *l)
{	
	g_assert (l);
	gboolean failedLink = FALSE;	
	
	for (gint i = 0; i < anomalyEventList->numAnomalyEvents; i++)
	{
		struct anomalyEvent_t *aE = &anomalyEventList->anomalyEvents[i];
		
		// Check that the anomaly event is an interWanlink
		struct link_anomaly_t *linkAnomaly = &(aE->linkAnomaly);
		if (linkAnomaly->interWanLink == FALSE)
			continue;		
		// Check whether the aWimId of the interWanLink Anomaly matches with the aWimId of l
		if (memcmp(linkAnomaly->aWimId, l->aWimId, sizeof (l->aWimId)) != 0)
			continue;
		// Check if head-end node of the edge and linkId matches with the failed link
		if (memcmp(linkAnomaly->zWimId, l->zWimId, sizeof (l->zWimId)) != 0)
			continue;
		// Check whether the aNodeId of the Anomaly Event InterWanLink matches with the aNodeId of the edge
		if (compare_node_id (&linkAnomaly->aNodeId, &l->aPEId) != 0)
			continue;
		if (linkAnomaly->aLinkId != l->aLinkId)
			continue;
		if (strncmp ((const char*)linkAnomaly->linkStatus, "failed", 6) == 0)
		{
			failedLink = TRUE;
			return failedLink;			
		}
		if (strncmp ((const char*)linkAnomaly->linkStatus, "degraded", 8) == 0)
		{
			if (fabs (linkAnomaly->linkPacketLoss) > 0.0f)
			{
				// Packet Loss x (e.g., 0.5) meaning that 50% of the packets are lost. In terms of the link bw
				// this can be assumed that the available bw is reduced by 50%
				gdouble linkBw = l->linkAvailBw * linkAnomaly->linkPacketLoss;
				memcpy (&(l->linkAvailBw), &(linkBw), sizeof (gdouble));				
			}			
			if (fabs (linkAnomaly->linkPacketDelay) > 0.0f)
			{
				// Link Delay x (e.g., 0.5, 1.5) meaning that the link delay is either reduced by x (i.e., 0.5) or
				// increased by x (e.g., 1.5)
				gdouble linkDelay = l->linkDelay * linkAnomaly->linkPacketDelay;
				memcpy (&(l->linkDelay), &(linkDelay), sizeof (gdouble));
				return failedLink;
			}	
		}		
	}	
	return failedLink;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Build from the both the Resource Layer Topology and Event Anomaly List 
 *	received information the graph for computing the routes wrt intra-WAN details
 *
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_graph_all_wim (struct graph_t *g)
{
	g_assert (g);
	// For all the WANs retrieved from the Resource Layer Topology (rlTopo)
	for (gint i = 0; i < rlTopo->numAbsWanTopo; i++)
	{
		// Get the WIM Identifier for a given WAN
		struct absWanTopo_t *absWantopo = &rlTopo->absWanTop[i];
		//DEBUG_RL_RA ("\n");
		//DEBUG_RL_RA ("Build Graph WIM: %s", absWantopo->WimId);	
		gint indexVerticesGraph = 0;
	
		// Gather the network nodes within the explored WAN
		for (gint j = 0; j < absWantopo->numNodes; j++)
		{
			// Check whether the network node is failed from the anomaly Event Node failures
			// If yes, the network nodes is NOT added in the graph
			struct nodes_t *topoNode = &(absWantopo->nodes[j]);			
			if (check_anomaly_node_wim (absWantopo, topoNode) == TRUE)
			{
				DEBUG_RL_RA ("%s is FAILED - DO NOT ADD TO THE GRAPH", topoNode->nodeId);
				continue;
			}
			g->numVertices++;
			//DEBUG_RL_RA ("Vertice from WAN: %s",  rlTopo->absWanTop[i].nodes[j].nodeId);
			struct nodes_t *verticeId = &g->vertices[indexVerticesGraph].verticeId;							
			duplicate_node_id (topoNode, verticeId);
			//DEBUG_RL_RA ("Vertice: %s added to the Graph", g->vertices[indexVerticesGraph].verticeId.nodeId);
			indexVerticesGraph++;
		}
		
		//DEBUG_RL_RA ("Set of RL Toplogy Nodes Added to the Graph");
		
		// Add to the graph the targeted vertices and for each of them the edges for a given graph
		for (gint k = 0; k < absWantopo->numEdges; k++)
		{				
			struct nodes_t *AnodeIdaux = &(absWantopo->edges[k].aNodeId);		
			struct nodes_t *ZnodeIdaux = &(absWantopo->edges[k].zNodeId);
					
			// If either edge node endpoints (i.e., aNode or zNode) are in the anomaly event node failure, then
			// discard this edge to be added to the graph;			
			if ((check_anomaly_node_wim (absWantopo, AnodeIdaux) == TRUE) || 
					(check_anomaly_node_wim (absWantopo, ZnodeIdaux) == TRUE))
				continue;
				
			// Create the edge
			//DEBUG_RL_RA ("Exploring edge %s --> %s", aNodeId->nodeId, zNodeId->nodeId);
			struct edges_t *edge = create_edge();
			struct edges_t *topoEdge = &(absWantopo->edges[k]);
			duplicate_edge (edge, topoEdge);			
			// Check if the edge/link is failed, if yes discard; Otherwise, if the link is degraded, apply accordingly the degradation
			// (i.e., reduction of bandwidth or increasing the delay)
			//DEBUG_RL_RA ("Check Anomaly for edge: [%s (%u) --> %s (%u)] ADDED to the Graph", edge->aNodeId.nodeId, edge->aLinkId, edge->zNodeId.nodeId, edge->zLinkId);
			if (check_anomaly_link (absWantopo, edge) == TRUE)
				continue;
	
			// Retrieve the Vertice from the graph related to aNodeId of the explored edge
			gint verticeIndex = graph_vertice_lookup (edge->aNodeId.nodeId, g);
			if (verticeIndex == -1)
			{
				DEBUG_RL_RA ("%s NOT in the Graph!! --- Weird", edge->aNodeId.nodeId);
				exit (-1);                
			}	
			// check if zNodeId as targeted vertice iff there is not
			gint addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, edge->zNodeId.nodeId, g);                    
			if (addedTargetedVerticeIndex == -1)
			{
				DEBUG_RL_RA ("%s MUST NOT be added since it exists as targeted vertice", edge->zNodeId.nodeId);
				continue;
			}	
			// Add the targeted vertice and related edge
			struct targetNodes_t *v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
			duplicate_node_id (&edge->zNodeId, &v->tVertice);
			g->vertices[verticeIndex].numTargetedVertices++;
			//DEBUG_RL_RA ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, g->vertices[verticeIndex].verticeId.nodeId);
			//DEBUG_RL_RA ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices);	
			v->numEdges++; 
			//DEBUG_RL_RA ("Number of Edges from vertice: %s to targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
				//																					v->tVertice.nodeId,
				//																					v->numEdges);					   
			gint indexEdge = v->numEdges - 1;           		                   
			graph_add_edge (verticeIndex, addedTargetedVerticeIndex, indexEdge, edge, g);
			memcpy (v->edges[indexEdge].networkLinkLayer, edge->networkLinkLayer, strlen (edge->networkLinkLayer));
			//DEBUG_RL_RA ("edge: [%s (%u) --> %s (%u)] ADDED to the Graph", edge->aNodeId.nodeId, edge->aLinkId, edge->zNodeId.nodeId, edge->zLinkId);
			g_free (edge);
		}		
	}
	return; 
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Build from the both the Resource Layer Topology and Event Anomaly List 
 *	received information the graph for computing the routes wrt interWAN link details
 *
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_graph_interWANlinks (struct graph_t *g)
{
	g_assert (g);
	
	// Get the interWANLinks to add information into the graph
	for (gint i = 0; i < rlTopo->numInterWanLinks; i++)
	{   	
		struct interWanLink_t *interWanLink = &(rlTopo->interWanLinks[i]);		
		DEBUG_RL_RA ("Exploring interWANLink %s --> %s", interWanLink->aPEId.nodeId, interWanLink->zPEId.nodeId);

		// If aNodeId is a WAN border node and it considered as failed, discard the inter-WAN linkAnomaly		
		if (check_anomaly_node_interWanLink (interWanLink, &interWanLink->aPEId) == TRUE)
			continue;
		
		struct edges_t *edge = create_edge();		
		duplicate_node_id (&interWanLink->aPEId, &edge->aNodeId);			
		duplicate_node_id (&interWanLink->zPEId, &edge->zNodeId);
		edge->aLinkId =  interWanLink->aLinkId;
		edge->zLinkId = interWanLink->zLinkId;
		edge->linkCost = interWanLink->linkCost;
		memcpy (&(edge->linkAvailBw), &interWanLink->linkAvailBw, sizeof (gdouble));
		memcpy (&(edge->linkDelay), &interWanLink->linkDelay, sizeof (gdouble));

		// Check if the edge/link is failed, if yes discard; Otherwise, if the link is degraded, apply accordingly the degradation (i.e., reduction of
		// bandwidth or increasing the delay)
		if (check_anomaly_link_interWanLink (interWanLink) == TRUE)
			continue;			

		// Retrieve the Vertice from the graph related to aNodeId of the explored edge
		gint verticeIndex = graph_vertice_lookup (interWanLink->aPEId.nodeId, g);
		if (verticeIndex == -1)
		{
				DEBUG_RL_RA ("%s is not in the current graph --- Weird", interWanLink->aPEId.nodeId);
				exit (-1);                
		}	
		// check if zPEId as targeted vertice iff there is not
		gint addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, interWanLink->zPEId.nodeId, g);		
		if (addedTargetedVerticeIndex == -1)
		{
			DEBUG_RL_RA ("%s MUST NOT be added (because it exists) as targeted vertice ", interWanLink->zPEId.nodeId);
			g_free (edge);
			continue;
		}
	
		// Add the targeted vertice and related edge
		struct targetNodes_t *v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
		duplicate_node_id (&interWanLink->zPEId, &v->tVertice);
		g->vertices[verticeIndex].numTargetedVertices++;
		DEBUG_RL_RA ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, 
																			g->vertices[verticeIndex].verticeId.nodeId);
		DEBUG_RL_RA ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices); 
	
		v->numEdges++; 
		DEBUG_RL_RA ("Number of Edges from vertice: %s to Targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
																					v->tVertice.nodeId,
																					v->numEdges);
				   
		gint indexEdge = v->numEdges - 1;        
		graph_add_edge (verticeIndex, addedTargetedVerticeIndex, indexEdge, edge, g);
		g_free (edge);
	}	
	return; 
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief compose the graph using the abstraction of the different WANs and the interWanLinks
 *
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_graph (struct graph_t *g)
{
	g_assert (rlTopo);
	g_assert (anomalyEventList);
	g_assert (g);
	
	// Build the graph for all the WANs (nodes and links) within the 
	// Resource Layer Topogy
	build_graph_all_wim (g);
	
	// Build the graph for all the interWANlinks passed as information 
	// in the Resource Layer Topology
	build_graph_interWANlinks (g);
	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Create map of nodes to handle the path computation
 *
 * 	@param mapN
 *  @param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_map_node (struct map_nodes_t *mapN, struct graph_t *g)
{
	//DEBUG_RL_RA ("Construction of the Map of Nodes");            
    gint i = 0;    
    for (i = 0; i < g->numVertices; i++)
    {	
		duplicate_node_id (&g->vertices[i].verticeId, &mapN->map[i].verticeId);
        mapN->map[i].distance = INFINITY_COST;
        mapN->map[i].avaiBandwidth = 0.0;
        mapN->map[i].latency = INFINITY_COST;
        mapN->numMapNodes++;
    }
    //DEBUG_RL_RA ("mapNodes formed by %d Nodes", mapN->numMapNodes);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for path of struct compRouteOutputList_t *
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct compRouteOutputList_t * create_route_list ()
{
	struct compRouteOutputList_t *p = g_malloc0 (sizeof (struct compRouteOutputList_t));
	if (p == NULL)
	{
		DEBUG_RL_RA ("Memory Allocation Problem");
		exit (-1);
	}
	return p;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for path of struct compRouteOutputItem_t *
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct compRouteOutputItem_t *create_path_item ()
{
	struct compRouteOutputItem_t *p = g_malloc0 (sizeof (struct compRouteOutputItem_t));
	if (p == NULL)
	{
		DEBUG_RL_RA ("Memory Allocation Problem");
		exit (-1);
	}
	return p;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Sort the set of paths according to the metric (1st criteria) and latency (2nd criteria)
 *
 *	@params setP
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void sort_path_set (struct path_set_t *setP)
{
	// Sort the paths contained in setP by shotest metric and latency
	gint i = 0;
	gint j = 0;
	float epsilon = 0.0000001;
	
	for (i = 0; i < setP->numPaths; i++)
	{
		for (j = 0; j < (setP->numPaths - i - 1); j++)
		{
			struct compRouteOutputItem_t *path1 = &setP->paths[j];
			struct compRouteOutputItem_t *path2 = &setP->paths[j+1];
			DEBUG_RL_RA ("path1 [Cost: %d; Latency: %f]; path2 [Cost: %d; Latency: %f]", path1->pathCost, path1->latency, path2->pathCost, path2->latency);
			struct compRouteOutputItem_t *pathTmp = create_path_item ();
			// 1st Criteria (avail Bw)
			if (path2->reqBw - path1->reqBw > 0.0)
			{
				duplicate_path (path1, pathTmp);
				duplicate_path (path2, path1);
				duplicate_path (pathTmp, path2);
				g_free (pathTmp);			
			}			
			else if ((fabs (path1->reqBw - path2->reqBw) < epsilon) || (path1->reqBw - path1->reqBw > 0.0))
			{
				// 1st criteria: sort by end-to-end latency
				if (path1->latency - path2->latency > 0.0)
				{
					duplicate_path (path1, pathTmp);
					duplicate_path (path2, path1);
					duplicate_path (pathTmp, path2);
					g_free (pathTmp);				
				}
				else if ((fabs (path1->latency - path2->latency) < epsilon) || (path2->latency - path1->latency > 0.0))
				{
					//2nd criteria sort by link metric
					if (path1->pathCost > path2->pathCost)
					{
						duplicate_path (path1, pathTmp);
						duplicate_path (path2, path1);
						duplicate_path (pathTmp, path2);
						g_free (pathTmp);
					}
					else if (path1->pathCost <= path2->pathCost)
					{
						g_free (pathTmp);
						continue;						
					}					
				}			
			}					
		}		
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Remove first element from the path sets 
 *
 *	@params setP
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void pop_front_path_set (struct path_set_t *setP)
{
	gint j = 0;	
	for (j = 0; j < setP->numPaths - 1; j++)
	{
		struct compRouteOutputItem_t *path1 = &setP->paths[j];
		struct compRouteOutputItem_t *path2 = &setP->paths[j+1];		
		duplicate_path (path2, path1);		
	}
	setP->numPaths--;	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Add routeElement to the back of the path
 *
 * 	@param rE
 * 	@param p
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void add_routeElement_path_back (struct routeElement_t *rE, struct compRouteOutputItem_t *p)
{
	//DEBUG_RL_RA ("p->numRouteElements: %d", p->numRouteElements);
	p->numRouteElements++;
	gint index = p->numRouteElements - 1;
	
	struct nodes_t *pn = &(p->routeElement[index].aNodeId);
	struct nodes_t *rEn = &(rE->aNodeId);
	
	// duplicate aNodeId
	duplicate_node_id (rEn, pn);	
	pn = &(p->routeElement[index].zNodeId);
	rEn = &(rE->zNodeId);
	duplicate_node_id (rEn, pn);	
	p->routeElement[index].aLinkId = rE->aLinkId;
	p->routeElement[index].zLinkId = rE->zLinkId;	
	return;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief This function compares ap and rootPath. If all the links are equal between both ap and rootPath till the sN, then the link from sN to next node 
 * 	ap is returned
 * 
 * @params ap
 * @params p
 * @params sN
 * @params e
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean matching_path_rootPath (struct compRouteOutputItem_t *ap, struct compRouteOutputItem_t *rootPath, struct nodes_t *sN, struct edges_t *e)
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
			//DEBUG_RL_RA (" --- copying linkids ----");
			//DEBUG_RL_RA ("edge nodes: %s --> %s", e->aNodeId.nodeId, e->zNodeId.nodeId);
			e->aLinkId = ap->routeElement[j].aLinkId;
			e->zLinkId = ap->routeElement[j].zLinkId;
			//DEBUG_RL_RA ("Exclude Edge e: %s (%u) ----> %s (%u) from Targeted graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			ret = TRUE;
			return ret;			
		}	
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) == 0) && 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) == 0))
		{
			j++;
			//DEBUG_RL_RA ("ap and rootPath sharing the edge: %s (%u) --> %s (%u)", ap->routeElement[j].aNodeId.nodeId, ap->routeElement[j].aLinkId, 
			//																				ap->routeElement[j].zNodeId.nodeId, ap->routeElement[j].zLinkId);
			continue;			
		}
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) != 0) || 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) != 0))
		{
			//DEBUG_RL_RA ("ap and rootPath not in the same path");
			return ret;
		}
	}	
	return ret;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief This function is used to modify the graph to be used for running the subsequent SP computations acording to the YEN algorithm principles
 * 
 * @params g
 * @params A
 * @params rootPath
 * @params spurNode
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void modify_targeted_graph (struct graph_t *g, struct path_set_t *A, struct compRouteOutputItem_t * rootPath, struct nodes_t * spurNode)
{
	//DEBUG_RL_RA ("Modify the Targeted graph according to the Yen algorithm principles");
	gint j = 0;
	for (j = 0; j < A->numPaths; j++)
	{
		struct compRouteOutputItem_t *ap = &A->paths[j];
		struct edges_t *e = create_edge ();
		gboolean ret =  FALSE;
		ret = matching_path_rootPath (ap, rootPath, spurNode, e);		
		if (ret == TRUE)
		{
			//DEBUG_RL_RA ("Removal %s [%u]--> %s [%u] from the graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			remove_edge_from_graph (g, e);
			//DEBUG_RL_RA ("Print Resulting Graph");
			//print_graph (g);
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
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Supporting fucntion to Check if a nodeId is already in the items of a given GList
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint find_nodeId (gconstpointer data, gconstpointer userdata)
{
     /** check values */
     g_assert(data != NULL);
     g_assert(userdata != NULL);
 
     struct nodeItem_t *SNodeId = (struct nodeItem_t *)data;
     guchar * nodeId = (guchar *)userdata; 
     
     //DEBUG_RL_RA ("SNodeId (%s) nodeId (%s)", SNodeId->node.nodeId, nodeId);   
        
     if (!memcmp(SNodeId->node.nodeId, nodeId, strlen (SNodeId->node.nodeId)))
     {
            return (0);
     }
    return -1;
}

///////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Explores the link between u and v
 * 
 *  @param u
 *  @param v 
 *	@param g
 *	@param req
 *  @param S
 *  @param Q
 *	@param mapNodes
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint check_link (struct nodeItem_t *u, gint indexGraphU, gint indexGraphV, struct graph_t *g, 
				struct interNfviPop_connection_req_t *req, GList **S, GList **Q, struct map_nodes_t *mapNodes)
{    
	struct targetNodes_t *v = &(g->vertices[indexGraphU].targetedVertices[indexGraphV]);	
    //DEBUG_RL_RA ("Explored %s --> %s", u->node.nodeId, v->tVertice.nodeId);
    
    // Check whether targetedVertice has been explored in S
    GList *found = g_list_find_custom (*S, v->tVertice.nodeId, find_nodeId);
    if (found != NULL)
    {
        //DEBUG_RL_RA ("v (%s) is already in S", v->tVertice.nodeId);        
        return 0;
    } 
    guint32 distance_through_u = INFINITY_COST;
    gdouble latency_through_u = INFINITY_COST;	
	gint i = 0;    
    // Check bandwidth requirement is fulfillied on edge u --> v    
    gint foundAvailBw = 0;
    gdouble edgeAvailBw = 0.0;	
    for (i = 0; i < v->numEdges; i++)
    {        
        struct edges_t *e = &(v->edges[i]);
		memcpy (&edgeAvailBw, &(e->linkAvailBw), sizeof (gdouble));
        //DEBUG_RL_RA ("%s(%u)--> %s (%u); AvailBw: %f", u->node.nodeId, e->aLinkId, 
		//										v->tVertice.nodeId, e->zLinkId, edgeAvailBw);
        if (edgeAvailBw < req->bandwidthCons)
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
        //DEBUG_RL_RA ("AvailBw: %f < ReqBw: %f -- Discard Edge", edgeAvailBw, req->bandwidthCons);
        return 0;    
    }    
    gint indexEdge = i; // get the index for the explored edge    
    // Update distance, latency and availBw through u to reach v
    gint map_uIndex = get_map_index_by_nodeId (u->node.nodeId, mapNodes);
	struct map_t *u_map = &mapNodes->map[map_uIndex];
    distance_through_u = u_map->distance + v->edges[indexEdge].linkCost;
    latency_through_u = u_map->latency + v->edges[indexEdge].linkDelay;    
    gdouble availBw_through_u = 0.0;
	// ingress endpoint (u) is the src of the request
    if (strcmp (u->node.nodeId, req->srcPEId.nodeId) == 0)
    {
        //DEBUG_RL_RA ("AvailBw %f on %s --> %s", edgeAvailBw, u->node.nodeId, v->tVertice.nodeId);        
        memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));        
    }
    else 
    {
        // Get the minimum available bandwidth between the src and u and the new added edge u->v
        //DEBUG_RL_RA ("Current AvailBw: %f from src to %s", u_map->avaiBandwidth, u->node.nodeId);
        //DEBUG_RL_RA ("AvailBw: %f %s --> %s", edgeAvailBw, u->node.nodeId, v->tVertice.nodeId);
        if (u_map->avaiBandwidth <= edgeAvailBw) 
        {
            memcpy (&availBw_through_u, &u_map->avaiBandwidth, sizeof (gdouble));    
		}
		else
		{
			memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));
		} 
    }     
    // Relax the link according to the pathCost and latency
    gint map_vIndex = get_map_index_by_nodeId (v->tVertice.nodeId, mapNodes);
	struct map_t *v_map = &mapNodes->map[map_vIndex];
    // If cost dist (u, v) > dist (src, v) relax the link
    if (distance_through_u > v_map->distance)
    {
        //DEBUG_RL_RA ("dist(src, u) + dist(u, v): %u > dist (src, v): %u --> Discard Link", distance_through_u, v_map->distance);  
        return 0;
    }
    // If dist (src, u) + dist (u, v) = current dist(src, v), then use the latency as discarding criteria
    if ((distance_through_u == v_map->distance) && (latency_through_u > v_map->latency))
    {
        //DEBUG_RL_RA ("dist(src, u) + dist(u,v) = current dist(src, v), but latency (src,u) + latency (u, v) > current latency (src, v)");          
        return 0;
    }	
	// If dist (src, u) + dist (u,v) == current dist(src, v) AND latency (src, u) + latency (u, v) == current latency (src, v), the available bandwidth is the criteria
	if ((distance_through_u ==  v_map->distance) && (latency_through_u == v_map->latency) && (availBw_through_u < v_map->avaiBandwidth))
	{
		return 0;
	}    
    //DEBUG_RL_RA ("%s --> %s Relaxed", u->node.nodeId, v->tVertice.nodeId);
    //DEBUG_RL_RA ("\t AvailBw: %f Mb/s, Cost: %u, Latency: %f ms", availBw_through_u, distance_through_u, latency_through_u);
    
    // Update Q list -- 
    struct nodeItem_t *nodeItem = g_malloc0 (sizeof (struct nodeItem_t));
    if (nodeItem == NULL)
    {
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);    
    }    
    nodeItem->distance = distance_through_u;
	memcpy(&nodeItem->latency, &latency_through_u, sizeof(gdouble));
	duplicate_node_id (&v->tVertice, &nodeItem->node);	
	// add node to the Q list
    *Q = g_list_insert_sorted (*Q, nodeItem, sort_by_distance);
    //DEBUG_RL_RA ("%s ADDED to Q (length: %d)", nodeItem->node.nodeId, g_list_length(*Q));    
    
    // Update the mapNodes for the specific reached tv   
    v_map->distance = distance_through_u;
    memcpy (&v_map->avaiBandwidth, &availBw_through_u, sizeof (gdouble));
    memcpy (&v_map->latency, &latency_through_u, sizeof (gdouble));
    // Copy (duplicate) the predecessor edge into the mapNodes 
	struct edges_t *e1 = &(v_map->predecessor);
	struct edges_t *e2 = &(v->edges[indexEdge]);
	duplicate_edge (e1, e2);	
	//DEBUG_RL_RA ("u->v edge: %s(%u) --> %s(%u)", e2->aNodeId.nodeId, e2->aLinkId, e2->zNodeId.nodeId, e2->zLinkId);
	//DEBUG_RL_RA ("Update Map for %s -- pred edge: %s(%u) --> %s(%u)", v_map->verticeId.nodeId, e1->aNodeId.nodeId, 
	//																e1->aLinkId, e1->zNodeId.nodeId, e1->zLinkId);

    // Check whether v is dstPEId
	//DEBUG_RL_RA ("Targeted dstPEId: %s", req->dstPEId.nodeId);
	//DEBUG_RL_RA ("nodeId added to the map: %s", v_map->verticeId.nodeId);
	//DEBUG_RL_RA ("Q Length: %d", g_list_length(*Q));
    return 0;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check the feasability of a path wrt the constraints imposed by the request in terms of latency
 * 
 *  @param req
 *	@param p
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean check_computed_path_feasability (struct interNfviPop_connection_req_t *req, struct compRouteOutputItem_t * p)
{
	gboolean ret = FALSE;
	float epsilon = 0.0000001; 
	if ((req->delayCons - p->latency > 0.0) || (fabs (req->delayCons - p->latency) < epsilon))
	{
		DEBUG_RL_RA ("Computed Path (latency: %f) is feasible wrt Connection Demand: %f", p->latency, req->delayCons);
		ret = TRUE;
		return ret;
	}
	DEBUG_RL_RA("Computed Path (latency: %f) is NOT feasible wrt Connection Demand: %f", p->latency, req->delayCons);
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_pa2000.c
 * 	@brief Sorting the GList Q items by distance 
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint sort_by_distance (gconstpointer a, gconstpointer b)
{
	//DEBUG_RL_RA ("sort by distance a and b");	
	g_assert(a != NULL);
	g_assert(b != NULL);
	
	//DEBUG_RL_RA ("sort by distance a and b");	  
	struct nodeItem_t *node1 = (struct nodeItem_t *)a;
	struct nodeItem_t *node2 = (struct nodeItem_t *)b;
	g_assert (node1);
	g_assert (node2);
	 
	//DEBUG_RL_RA ("a->distance %u; b->distance %u", node1->distance, node2->distance);
	//DEBUG_RL_RA("a->latency: %f; b->latency: %f", node1->latency, node2->latency);
	//1st criteria, sorting by lowest distance
	if (node1->distance > node2->distance)
		return 1;
	else if (node1->distance < node2->distance)
		return 0;
	if (node1->distance == node2->distance)
	{
		if (node1->latency > node2->latency)
			return 1;
		else if (node1->latency <= node2->latency)
			return 0;
	}
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for graph
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct graph_t * create_graph ()
{
	struct graph_t * g = g_malloc0 (sizeof (struct graph_t));
	if (g == NULL)
	{
		DEBUG_RL_RA ("Memory Allocation Problem");
		exit (-1);
	}
	return g;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for mapNodes
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct map_nodes_t * create_map_node ()
{
	struct map_nodes_t * mN = g_malloc0 (sizeof (struct map_nodes_t));
	if (mN == NULL)
	{
		DEBUG_RL_RA ("Memory allocation failed");
		exit (-1);
	}
	return mN;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for rl_topology_t
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct rl_topology_t * create_rl_topology ()
{
	struct rl_topology_t *rl_topology = g_malloc0 (sizeof (struct rl_topology_t));
	if (rl_topology == NULL)
	{
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);
	}	
	return rl_topology;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for request_list_t
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct request_list_t * create_req_list ()
{
	struct request_list_t * reqList = g_malloc0 (sizeof (struct request_list_t));
	if (reqList == NULL)
	{
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);
	}
	return reqList;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Allocate memory for anomalyEventList_t
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct anomalyEventList_t * create_anomaly_event_list ()
{
	struct anomalyEventList_t * anomalyEvList = g_malloc0 (sizeof (struct anomalyEventList_t));
	if (anomalyEvList == NULL)
	{
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);
	}
	return anomalyEvList;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Check whether src and dst PE nodeId of the req are the same
 *
 *	@param r
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint same_src_dst_pe_nodeid (struct interNfviPop_connection_req_t * r)
{
	// Check that source PE and dst PE are NOT the same
	if (memcmp ((const char*)(r->srcPEId.nodeId), (const char *) (r->dstPEId.nodeId), strlen(r->dstPEId.nodeId)) == 0)
	{
		DEBUG_RL_RA ("srcPE: %s; dstPE: %s are the same!!!", r->srcPEId.nodeId, r->dstPEId.nodeId);
		return 1;
	}
	return 0;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Handles issues with the route computation
 *
 *	@param route
 *	@param r
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void comp_route_connection_issue_handler (struct compRouteOutput_t *route, struct interNfviPop_connection_req_t *r)
{
	// Increase the number of computed routes despite there was an issue to be reported		
	route->numCompRoutes++;	
	// Copy the interNfviConnectivityId and RequestId bound to the computed path
	memcpy (route->interNfviPopConnectivityId, r->interNfviPopConnectivityId, sizeof (r->interNfviPopConnectivityId));
	route->requestId = r->requestId;
	route->noPathIssue = NO_PATH_CONS_ISSUE;
	// Copy the requested bw 
	memcpy(&route->reqBw, &r->bandwidthCons, sizeof(gdouble));
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief released the allocated memory fo compRouteOutputList_t
 *
 *	@param ro
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void destroy_compRouteOutputList (struct compRouteOutputList_t *ro)
{
	g_assert (ro);	
	g_free (ro);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief creates a copy of the underlying graph
 *
 *	@param originalGraph
 *	@param destGraph
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_graph (struct graph_t *originalGraph, struct graph_t *destGraph)
{
	g_assert (originalGraph);
	g_assert (destGraph);
	
	destGraph->numVertices = originalGraph->numVertices;
	for (gint i = 0; i < originalGraph->numVertices; i++)
	{
		struct vertices_t *oVertex = &(originalGraph->vertices[i]);
		struct vertices_t *dVertex = &(destGraph->vertices[i]);
		dVertex->numTargetedVertices = oVertex->numTargetedVertices;		
		duplicate_node_id (&oVertex->verticeId, &dVertex->verticeId);
		
		for (gint j = 0; j < oVertex->numTargetedVertices; j++)
		{
			struct targetNodes_t *oTargetedVertex = &(oVertex->targetedVertices[j]);
			struct targetNodes_t *dTargetedVertex = &(dVertex->targetedVertices[j]);
			duplicate_node_id (&oTargetedVertex->tVertice, &dTargetedVertex->tVertice);
			dTargetedVertex->numEdges = oTargetedVertex->numEdges;
			
			for (gint k = 0; k < oTargetedVertex->numEdges; k++)
			{
				struct edges_t *oEdge = &(oTargetedVertex->edges[k]);
				struct edges_t *dEdge = &(dTargetedVertex->edges[k]);
				duplicate_edge (dEdge, oEdge);						
			}
		}	
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief creates a duplication of the InterNfviPop Connection Request
 *
 *	@param oReqConn
 *	@param cReqConn
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicateReqConn (struct interNfviPop_connection_req_t * oReqConn, struct interNfviPop_connection_req_t *cReqConn)
{
	g_assert (cReqConn);
	g_assert (oReqConn);
	
	cReqConn->requestId = cReqConn->requestId;
	strcpy (cReqConn->interNfviPopConnectivityId, oReqConn->interNfviPopConnectivityId);
	strcpy (cReqConn->raId, oReqConn->raId);
	cReqConn->kPaths = oReqConn->kPaths;
	duplicate_node_id (&oReqConn->srcPEId, &cReqConn->srcPEId);
	duplicate_node_id (&oReqConn->dstPEId, &cReqConn->dstPEId);
	memcpy (&cReqConn->bandwidthCons, &oReqConn->bandwidthCons, sizeof (gdouble));
	memcpy (&cReqConn->delayCons, &oReqConn->delayCons, sizeof (gdouble));
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Finds the graph edge related to the routing element (re)
 *
 *	@param re
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct edges_t *graph_edge_re_lookup (struct routeElement_t *re, struct graph_t *g)
{
	g_assert (re);
	g_assert (g);
	
	for (gint i = 0; i < g->numVertices; i++)
	{
		struct vertices_t *v = &(g->vertices[i]);		
		// Check Route Element aNodeId with the v->verticeId
		if (compare_node_id (&re->aNodeId, &v->verticeId) != 0)
			continue;
		// Check Route Element zNodeis with any of reachable targeted vertices from v
		gboolean foundTargVert = FALSE;
		gint indexTargVert = -1;
		for (gint j = 0; j < v->numTargetedVertices; j++)
		{	
			struct targetNodes_t *tv = &(v->targetedVertices[j]);
			if (compare_node_id (&re->zNodeId, &tv->tVertice) == 0)
			{
				foundTargVert = TRUE;
				indexTargVert = j;
				break;
			}		
		}
		if (foundTargVert == FALSE)
			continue;
		
		// The targeted vertice is found, then check matching with the edge (aLinkId and zLinkId)
		struct targetNodes_t *tv = &(v->targetedVertices[indexTargVert]);
		for (gint k = 0; k < tv->numEdges; k++)
		{
			struct edges_t *e = &(tv->edges[k]);
			if ((re->aLinkId == e->aLinkId) && (re->zLinkId == e->zLinkId))
				return e;			
		}		
	}
	return NULL;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Finds the graph edge related to the routing element (re) for the reverse direction
 *
 *	@param re
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
 /////////////////////////////////////////////////////////////////////////////////////////
struct edges_t* graph_edge_re_lookup_reverse(struct routeElement_t* re, struct graph_t* g)
{
	g_assert(re);
	g_assert(g);

	for (gint i = 0; i < g->numVertices; i++)
	{
		struct vertices_t* v = &(g->vertices[i]);
		// Check Route Element zNodeId with the v->verticeId
		if (compare_node_id(&re->zNodeId, &v->verticeId) != 0)
			continue;
		// Check Route Element zNodeis with any of reachable targeted vertices from v
		gboolean foundTargVert = FALSE;
		gint indexTargVert = -1;
		for (gint j = 0; j < v->numTargetedVertices; j++)
		{
			struct targetNodes_t* tv = &(v->targetedVertices[j]);
			if (compare_node_id(&re->aNodeId, &tv->tVertice) == 0)
			{
				foundTargVert = TRUE;
				indexTargVert = j;
				break;
			}
		}
		if (foundTargVert == FALSE)
			continue;

		// The targeted vertice is found, then check matching with the edge (aLinkId and zLinkId)
		struct targetNodes_t* tv = &(v->targetedVertices[indexTargVert]);
		for (gint k = 0; k < tv->numEdges; k++)
		{
			struct edges_t* e = &(tv->edges[k]);
			if ((re->zLinkId == e->aLinkId) && (re->aLinkId == e->zLinkId))
				return e;
		}
	}
	return NULL;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Function used to reflect the resources contained in path p into the graph according 
 * 	to the requirements of r
 *
 *	@param p
 *	@param r
 *	@parma g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void allocate_graph_resources (struct compRouteOutputItem_t *p, struct interNfviPop_connection_req_t *r, struct graph_t *g)
{
	g_assert (p);
	g_assert (r);
	g_assert (g);
	
	for (gint i = 0; i < p->numRouteElements; i++)
	{
		struct routeElement_t *re = &(p->routeElement[i]);
		struct edges_t *e = graph_edge_re_lookup(re, g);
		if (e == NULL)
		{
			DEBUG_RL_RA ("Something weird -- the edge used by the routine element is not found in the graph");
			exit (-1);			
		}
		// Update the bandwidth resources in the edge
		gdouble resbw = e->linkAvailBw - r->bandwidthCons;
		DEBUG_RL_RA("Updating Bw @ edge %s[%u] -> %s[%u]", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
		DEBUG_RL_RA("Initial - e Avail Bw: %f - r->BwCons: %f, result: %f", e->linkAvailBw, r->bandwidthCons, resbw);
		memcpy (&e->linkAvailBw , &resbw, sizeof (gdouble));
		DEBUG_RL_RA("Final - e Avail Bw: %f", e->linkAvailBw);
	}		
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Function used to reflect the resources contained in path p for the reverse direction 
 *	into the graph according to the requirements of r
 *
 *	@param p
 *	@param r
 *	@parma g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2021
 */
 /////////////////////////////////////////////////////////////////////////////////////////
void allocate_graph_reverse_resources(struct compRouteOutputItem_t* p, struct interNfviPop_connection_req_t* r, struct graph_t* g)
{
	g_assert(p);
	g_assert(r);
	g_assert(g);

	for (gint i = 0; i < p->numRouteElements; i++)
	{
		struct routeElement_t* re = &(p->routeElement[i]);
		struct edges_t* e = graph_edge_re_lookup_reverse(re, g);
		if (e == NULL)
		{
			DEBUG_RL_RA("Something weird -- the edge used by the routine element is not found in the graph");
			exit(-1);
		}
		// Update the bandwidth resources in the edge
		gdouble resbw = e->linkAvailBw - r->bandwidthCons;
		DEBUG_RL_RA("Updating Bw @ edge %s[%u] -> %s[%u]", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
		DEBUG_RL_RA("Initial - e Avail Bw: %f - r->BwCons: %f, result: %f", e->linkAvailBw, r->bandwidthCons, resbw);
		memcpy(&e->linkAvailBw, &resbw, sizeof(gdouble));
		DEBUG_RL_RA("Final - e Avail Bw: %f", e->linkAvailBw);
	}
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_comp.c
 * 	@brief Function used to print for the requested WAN connections the resulting paths
 *	for all of them
 *
 *	@param List
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_route_solution_list (GList * List)
{
	g_assert (List);	
	GList *l;
	gint i = 0;
	DEBUG_RL_RA("");
	DEBUG_RL_RA ("Printing the computed routes for all the connection requests");
	for (l = List; l != NULL; l = g_list_next (l))
	{
		DEBUG_RL_RA ("");
		DEBUG_RL_RA ("================== Solution: %d ===========================", i);
		struct compRouteOutputList_t *routeConnList = (struct compRouteOutputList_t *)(l->data);
		print_path_list (routeConnList);
		DEBUG_RL_RA ("============================== ===========================");
		i++;
	}	
	return;
}