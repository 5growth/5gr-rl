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
	
	DEBUG_RL_RA ("\n");
	DEBUG_RL_RA ("==================================================================");	
	
	for (k = 0; k < path->numRouteElements; k++)
	{
		DEBUG_RL_RA ("aNodeId: %s (%d) --> zNodeId: %s (%d)", path->routeElement[k].aNodeId.nodeId, path->routeElement[k].aLinkId, 
																path->routeElement[k].zNodeId.nodeId, path->routeElement[k].zLinkId);
		
	}
	DEBUG_RL_RA ("==================================================================");   
		
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
	memcpy (dst->nodeId, src->nodeId, sizeof (src->nodeId));
	
	return;
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
	gint k = 0;	
	
	memcpy (&b->reqBw, &a->reqBw, sizeof (gdouble));
	b->numRouteElements = a->numRouteElements;	
	b->pathCost = a->pathCost;
	memcpy (&b->latency, &a->latency, sizeof (gdouble));
		
	for (k = 0; k < a->numRouteElements; k++)
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
		DEBUG_RL_RA ("[Route Element: %d]; %s (%u)  -----> %s (%u)", (k+1), b->routeElement[k].aNodeId.nodeId, b->routeElement[k].aLinkId, 
																					b->routeElement[k].zNodeId.nodeId, b->routeElement[k].zLinkId);
		
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
			DEBUG_RL_RA ("Index: %d", index);
            return index;            
        }
    }
	DEBUG_RL_RA ("Index: %d", index);
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
	
	DEBUG_RL_RA ("Get the Edge into map from node v: %s", v.nodeId);	
	// Get the edge reaching the node v from mapNodes
	gint map_vIndex = get_map_index_by_nodeId (v.nodeId, mapN);
	
	DEBUG_RL_RA ("aNodeId: %s --> zNodeId: %s", mapN->map[map_vIndex].predecessor.aNodeId.nodeId, mapN->map[map_vIndex].predecessor.zNodeId.nodeId);
	
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
	DEBUG_RL_RA ("Get edge outgoing node %s from predecessors list", n.nodeId);	
	for (gint i = 0; i < predecessors->numPredComp; i++)
	{
		if (memcmp (n.nodeId, predecessors->predComp[i].v.nodeId, sizeof (predecessors->predComp[i].v.nodeId)) == 0)
		{
			// Add to the predecessors list
			struct edges_t *te = &(predecessors->predComp[i].e);
			duplicate_edge (e, te);
			DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
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
	DEBUG_RL_RA ("\n");
	DEBUG_RL_RA ("Construct the computed path");	
	struct nodes_t v;
	
	duplicate_node_id (&req->srcPEId, &v);
	
	struct edges_t *e = g_malloc0 (sizeof (struct edges_t));
	if (e == NULL)
	{
		DEBUG_RL_RA ("Memory allocation problem");
		exit (-1);
	}
	
	// Get the edge for v in predecessors
	get_edge_from_predecessors (e, v, predecessors);
	
	DEBUG_RL_RA ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
	
	// Get the target for e
	struct nodes_t u;	
	duplicate_node_id (&e->zNodeId, &u);
	DEBUG_RL_RA ("u: %s", u.nodeId);
		
	memcpy (&path->reqBw, &req->bandwidthCons, sizeof (gdouble));
	
	// Add route element to the path being constructed
	gint k = 0;		
	
	duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
	duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);	
	path->routeElement[k].aLinkId = e->aLinkId;
	path->routeElement[k].zLinkId = e->zLinkId;	
	path->numRouteElements++;	
	DEBUG_RL_RA ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																						path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
	DEBUG_RL_RA ("Route Elements: %d", path->numRouteElements);
		
	while (memcmp ((const char*)(u.nodeId), (const char *) (req->dstPEId.nodeId), strlen(req->dstPEId.nodeId)) != 0)
	{
		k++; 
		path->numRouteElements++;
		// v = u		
		duplicate_node_id (&u, &v);
		DEBUG_RL_RA ("v: %s u: %s", v.nodeId, u.nodeId);
		
		get_edge_from_predecessors (e, v, predecessors);
		
		// Get the target u		
		duplicate_node_id (&e->zNodeId, &u);
		
		// Add route element to the path being constructed		
		duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
		duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);
		path->routeElement[k].aLinkId = e->aLinkId;
		path->routeElement[k].zLinkId = e->zLinkId;
		DEBUG_RL_RA ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																																 path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
		DEBUG_RL_RA ("Route Elements: %d", path->numRouteElements);		
	}
		
	g_free (e);
	DEBUG_RL_RA ("Path is constructed");
	
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
    DEBUG_RL_RA ("\n");
    DEBUG_RL_RA ("================================================================");
    DEBUG_RL_RA ("===========================   GRAPH   ==========================");
    DEBUG_RL_RA ("================================================================");
    
    gint i = 0, j = 0, k = 0;
    for (i = 0; i < g->numVertices; i++)
    {
        DEBUG_RL_RA ("Vertice [%s]", g->vertices[i].verticeId.nodeId);
        for (j = 0; j < g->vertices[i].numTargetedVertices; j++)
        {
            DEBUG_RL_RA ("  Targeted Vertice: %s", g->vertices[i].targetedVertices[j].tVertice.nodeId);
            for (k = 0; k < g->vertices[i].targetedVertices[j].numEdges; k++)
            {
                struct edges_t *e = &(g->vertices[i].targetedVertices[j].edges[k]);
				DEBUG_RL_RA ("		Edge %s --> %s", e->aNodeId.nodeId, e->zNodeId.nodeId);
				DEBUG_RL_RA ("      Links: %u %u, Cost: %d, AvailBw: %f, Delay: %f", e->aLinkId, e->zLinkId, e->linkCost, e->linkAvailBw, e->linkDelay);
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
		if ((memcmp (e->aNodeId.nodeId, g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].edges[j].aNodeId.nodeId, sizeof (e->aNodeId.nodeId)) == 0) &&
			(memcmp (e->zNodeId.nodeId, g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].edges[j].zNodeId.nodeId, sizeof (e->zNodeId.nodeId)) == 0) &&
			(e->aLinkId == g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].edges[j].aLinkId) &&
			(e->zLinkId == g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex].edges[j].zLinkId))
		{
			DEBUG_RL_RA ("Edge %s (%u) --> %s (%u) is found in the graph at index: %d", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId, j);
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
    gint i = 0;
    
    for (i = 0; i < g->numVertices; i++)
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
            DEBUG_RL_RA ("Targeted vertice %s reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);
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
	DEBUG_RL_RA ("Remove the edge: %s (%u) ---> %s (%u)", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
	gint verticeIndex = -1;		
	verticeIndex = graph_vertice_lookup (e->aNodeId.nodeId, g);
	if (verticeIndex == -1)
	{
		DEBUG_RL_RA ("Edge w/ aNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId);
		return;
	}
	
	// Find the targeted vertice from vertice Id
	gint targetedVerticeIndex = -1;
	targetedVerticeIndex = graph_targeted_vertice_lookup (verticeIndex, e->zNodeId.nodeId, g);
	if (targetedVerticeIndex == -1)
	{
		DEBUG_RL_RA ("Edge w/ aNodeId: %s to zNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	DEBUG_RL_RA ("Edge w/ aNodeId: %s and zNodeId: %s found in the Graph", e->aNodeId.nodeId, e->zNodeId.nodeId);
	
	// Get the edge position
	gint edgeIndex = -1;
	edgeIndex = graph_edge_lookup (verticeIndex, targetedVerticeIndex, e, g);
	if (edgeIndex == -1)
	{
		DEBUG_RL_RA ("Edge w/ aNodeId: %s to aNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	DEBUG_RL_RA ("Edge w/ aNodeId: %s and zNodeId: %s found in the Graph w/ edgeIndex: %d", e->aNodeId.nodeId, e->zNodeId.nodeId, edgeIndex);
	
	// Remove the edge
	DEBUG_RL_RA ("Start Removing edge from Graph");
	gint j = 0;	
	struct targetNodes_t *v = &(g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex]);
	
	for (j = edgeIndex; j < v->numEdges; j++)
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
	// Check the abstracted topology of through all the WANs
	gint i = 0, j = 0, k = 0;
    gint indexVerticesGraph = 0;
    gint verticeIndex = 0;
    gint addedTargetedVerticeIndex = -1;
   	struct edges_t *edge = create_edge();
    struct nodes_t *aNodeId = g_malloc0 (sizeof (struct nodes_t));
	struct nodes_t *zNodeId = g_malloc0 (sizeof (struct nodes_t));
	gint indexEdge = -1;
	
	if ((aNodeId == NULL) || (zNodeId == NULL))
	{
		DEBUG_RL_RA ("memory allocation problem");
		exit (-1);
	}
       
	for (i = 0; i < rlTopo->numAbsWanTopo; i++)
	{
		// Get the WIM
		DEBUG_RL_RA ("\n");
		DEBUG_RL_RA ("Creating the Graph Exploring WIM: %s", rlTopo->absWanTop[i].WimId);
	
		// Retrieve the set of nodes within the explored WAN
		for (j = 0; j < rlTopo->absWanTop[i].numNodes; j++)
		{
				g->numVertices++;
				//DEBUG_RL_RA ("Vertice from WAN: %s",  rlTopo->absWanTop[i].nodes[j].nodeId);
				struct nodes_t *verticeId = &g->vertices[indexVerticesGraph].verticeId;
				struct nodes_t *topoNode = &rlTopo->absWanTop[i].nodes[j];					
				duplicate_node_id (topoNode, verticeId);
				//DEBUG_RL_RA ("Vertice: %s added to the Graph", g->vertices[indexVerticesGraph].verticeId.nodeId);
				indexVerticesGraph++;
		}

		// Add to the graph the targeted vertices and for each of them the related edges for a given vertice already in the graph
		for (k = 0; k < rlTopo->absWanTop[i].numEdges; k++)
		{				
								
			struct nodes_t *nodeIdaux = &(rlTopo->absWanTop[i].edges[k].aNodeId);										
			duplicate_node_id (nodeIdaux, aNodeId);
			
			nodeIdaux = &(rlTopo->absWanTop[i].edges[k].zNodeId);
			duplicate_node_id (nodeIdaux, zNodeId);
			
			DEBUG_RL_RA ("Exploring edge %s --> %s", aNodeId->nodeId, zNodeId->nodeId);				
			duplicate_node_id (aNodeId, &edge->aNodeId);					
			duplicate_node_id (zNodeId, &edge->zNodeId);
			
			edge->aLinkId =  rlTopo->absWanTop[i].edges[k].aLinkId;
			edge->zLinkId = rlTopo->absWanTop[i].edges[k].zLinkId;
			edge->linkCost = rlTopo->absWanTop[i].edges[k].linkCost;
			memcpy (&(edge->linkAvailBw), &rlTopo->absWanTop[i].edges[k].linkAvailBw, sizeof (gdouble));
			memcpy (&(edge->linkDelay), &rlTopo->absWanTop[i].edges[k].linkDelay, sizeof (gdouble));
			memcpy (edge->networkLinkLayer, rlTopo->absWanTop[i].edges[k].networkLinkLayer, sizeof (rlTopo->absWanTop[i].edges[k].networkLinkLayer));   
	
			// Retrieve the Vertice from the graph related to aNodeId of the explored edge
			verticeIndex = graph_vertice_lookup (aNodeId->nodeId, g);
			if (verticeIndex == -1)
			{
				DEBUG_RL_RA ("%s is not in the current graph --- Weird", aNodeId->nodeId);
				exit (-1);                
			}
	
			// check if zNodeId as targeted vertice iff there is not
			addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, zNodeId->nodeId, g);                    
			//DEBUG_RL_RA ("addedTargetedVerticeIndex: %d ", addedTargetedVerticeIndex);
	
			if (addedTargetedVerticeIndex == -1)
			{
				DEBUG_RL_RA ("%s MUST not be added (because it exists) as targeted vertice ", zNodeId->nodeId);
				continue;
			}
	
			// Add the targeted vertice and related edge
			struct targetNodes_t * v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
			duplicate_node_id (zNodeId, &v->tVertice);
			g->vertices[verticeIndex].numTargetedVertices++;
			DEBUG_RL_RA ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, g->vertices[verticeIndex].verticeId.nodeId);
			DEBUG_RL_RA ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices); 
	
			v->numEdges++; 
			DEBUG_RL_RA ("Number of Edges from vertice: %s to targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
																									v->tVertice.nodeId,
																									v->numEdges);
					   
			indexEdge = v->numEdges - 1;           		                   
			graph_add_edge (verticeIndex, addedTargetedVerticeIndex, indexEdge, edge, g);
			memcpy (v->edges[indexEdge].networkLinkLayer, edge->networkLinkLayer, sizeof (edge->networkLinkLayer));                      
		}		
	}
	
	// Get the interWANLinks to add information into the graph
	for (i = 0; i < rlTopo->numInterWanLinks; i++)
    	{   	
			struct nodes_t *nodeIdaux = &(rlTopo->interWanLinks[i].aPEId);										
			duplicate_node_id (nodeIdaux, aNodeId);			
			nodeIdaux = &(rlTopo->interWanLinks[i].zPEId);
			duplicate_node_id (nodeIdaux, zNodeId);			        
        	DEBUG_RL_RA ("\n");
        	DEBUG_RL_RA ("Exploring interWANLink %s --> %s", aNodeId->nodeId, zNodeId->nodeId);            			
			
			duplicate_node_id (aNodeId, &edge->aNodeId);			
			duplicate_node_id (zNodeId, &edge->zNodeId);
        	edge->aLinkId =  rlTopo->interWanLinks[i].aLinkId;
        	edge->zLinkId = rlTopo->interWanLinks[i].zLinkId;
        	edge->linkCost = rlTopo->interWanLinks[i].linkCost;
        	memcpy (&(edge->linkAvailBw), &rlTopo->interWanLinks[i].linkAvailBw, sizeof (gdouble));
        	memcpy (&(edge->linkDelay), &rlTopo->interWanLinks[i].linkDelay, sizeof (gdouble));          
    
        	// Retrieve the Vertice from the graph related to aNodeId of the explored edge
        	verticeIndex = graph_vertice_lookup (aNodeId->nodeId, g);
        	if (verticeIndex == -1)
        	{
            		DEBUG_RL_RA ("%s is not in the current graph --- Weird", aNodeId->nodeId);
            		exit (-1);                
        	}
        
        	// check if zNodeId as targeted vertice iff there is not
        	addedTargetedVerticeIndex = -1;
        	addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, zNodeId->nodeId, g);
            
            DEBUG_RL_RA ("addedTargetedVerticeIndex: %d ", addedTargetedVerticeIndex);
            
        	if (addedTargetedVerticeIndex == -1)
        	{
            		DEBUG_RL_RA ("%s MUST not be added (because it exists) as targeted vertice ", zNodeId->nodeId);
            		continue;
        	}
        
        	// Add the targeted vertice and related edge
			struct targetNodes_t * v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
			duplicate_node_id (zNodeId, &v->tVertice);
        	g->vertices[verticeIndex].numTargetedVertices++;
        	DEBUG_RL_RA ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, 
                                                                                g->vertices[verticeIndex].verticeId.nodeId);
            DEBUG_RL_RA ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices); 
        
        	v->numEdges++; 
            DEBUG_RL_RA ("Number of Edges from vertice: %s to Targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
                                                                                        v->tVertice.nodeId,
                                                                                        v->numEdges);
        	           
        	indexEdge = v->numEdges - 1;        
        	graph_add_edge (verticeIndex, addedTargetedVerticeIndex, indexEdge, edge, g);         
    	}
    	g_free (edge);
		g_free (aNodeId);
		g_free (zNodeId);
	
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
	DEBUG_RL_RA ("Initializing and creating the Map of Nodes");            
    gint i = 0;    
    for (i = 0; i < g->numVertices; i++)
    {	
		duplicate_node_id (&g->vertices[i].verticeId, &mapN->map[i].verticeId);
        mapN->map[i].distance = INFINITY_COST;
        mapN->map[i].avaiBandwidth = 0.0;
        mapN->map[i].latency = INFINITY_COST;
        mapN->numMapNodes++;
    }
    DEBUG_RL_RA ("mapNodes is %d elements", mapN->numMapNodes);
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
			DEBUG_RL_RA ("path1 [metric: %d; latency: %f]; path2 [metric: %d; latency: %f]", path1->pathCost, path1->latency, path2->pathCost, path2->latency);
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
	DEBUG_RL_RA ("p->numRouteElements: %d", p->numRouteElements);
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
			DEBUG_RL_RA (" --- copying linkids ----");
			DEBUG_RL_RA ("edge nodes: %s --> %s", e->aNodeId.nodeId, e->zNodeId.nodeId);
			e->aLinkId = ap->routeElement[j].aLinkId;
			e->zLinkId = ap->routeElement[j].zLinkId;
			DEBUG_RL_RA ("Exclude Edge e: %s (%u) ----> %s (%u) from Targeted graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			ret = TRUE;
			return ret;			
		}	
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) == 0) && 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) == 0))
		{
			j++;
			DEBUG_RL_RA ("ap and rootPath sharing the edge: %s (%u) --> %s (%u)", ap->routeElement[j].aNodeId.nodeId, ap->routeElement[j].aLinkId, 
																							ap->routeElement[j].zNodeId.nodeId, ap->routeElement[j].zLinkId);
			continue;			
		}
		
		if ((memcmp (ap->routeElement[j].aNodeId.nodeId, rootPath->routeElement[j].aNodeId.nodeId, sizeof (ap->routeElement[j].aNodeId.nodeId)) != 0) || 
			(memcmp (ap->routeElement[j].zNodeId.nodeId, rootPath->routeElement[j].zNodeId.nodeId, sizeof (ap->routeElement[j].zNodeId.nodeId)) != 0))
		{
			DEBUG_RL_RA ("ap and rootPath not in the same path");
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
	DEBUG_RL_RA ("Modify the Targeted graph according to the Yen algorithm principles");
	gint j = 0;
	for (j = 0; j < A->numPaths; j++)
	{
		struct compRouteOutputItem_t *ap = &A->paths[j];
		struct edges_t *e = create_edge ();
		gboolean ret =  FALSE;
		ret = matching_path_rootPath (ap, rootPath, spurNode, e);		
		if (ret == TRUE)
		{
			DEBUG_RL_RA ("Removal of the edge %s [%u]--> %s [%u] from the graph", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
			remove_edge_from_graph (g, e);
			DEBUG_RL_RA ("Print Resulting Graph");
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
     
     DEBUG_RL_RA ("SNodeId (%s) nodeId (%s)", SNodeId->node.nodeId, nodeId);   
        
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
	struct targetNodes_t* v = &(g->vertices[indexGraphU].targetedVertices[indexGraphV]);      
    DEBUG_RL_RA ("Check link u (%s) --> v (%s)", u->node.nodeId, v->tVertice.nodeId);
    
    // Check whether targetedVertice has been explored in S
    GList *found = g_list_find_custom (*S, v->tVertice.nodeId, find_nodeId);
    if (found != NULL)
    {
        DEBUG_RL_RA ("tv (%s) is already in S", v->tVertice.nodeId);        
        return 0;
    } 
    
    guint32 distance_through_u = INFINITY_COST;
    gdouble latency_through_u = INFINITY_COST;
	
	gint i = 0;
    
    // Check that there is sufficient availBandwidth on any link between u and v    
    gint foundAvailBw = 0;
    gdouble edgeAvailBw = 0.0;	
    for (i = 0; i < v->numEdges; i++)
    {        
        memcpy (&edgeAvailBw, &(v->edges[i].linkAvailBw), sizeof (gdouble));
        DEBUG_RL_RA ("Available Bandwidth on edge u (%s)--> v (%s) %f", u->node.nodeId, v->tVertice.nodeId, edgeAvailBw);
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
        DEBUG_RL_RA ("Available Bandwidth on links does not satisfy the request");        
        return 0;    
    }    
    gint indexEdge = i;    
    // Update distance, latency and availBw through u to reach v
    gint map_uIndex = get_map_index_by_nodeId (u->node.nodeId, mapNodes);
	struct map_t *u_map = &mapNodes->map[map_uIndex];
    distance_through_u = u_map->distance + v->edges[indexEdge].linkCost;
    latency_through_u = u_map->latency + v->edges[indexEdge].linkDelay;
    
    gdouble availBw_through_u = 0.0;   
    if (memcmp (u->node.nodeId, req->srcPEId.nodeId, sizeof (u->node.nodeId)) == 0)
    {
        DEBUG_RL_RA ("u is the Req's src; unused Bw u->v: (%s->%s) is link's availBw: %f", u->node.nodeId, v->tVertice.nodeId, edgeAvailBw);        
        memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));        
    }
    else 
    {
        // Get the minimum available bandwidth between the src and u and the new added edge u->v
        DEBUG_RL_RA ("Current available Bw from src to u (%s) is %f", u->node.nodeId, u_map->avaiBandwidth);
        DEBUG_RL_RA ("edge u --> v /%s->%s) unused bw: %f", u->node.nodeId, v->tVertice.nodeId, edgeAvailBw);
        if (u_map->avaiBandwidth <= edgeAvailBw) 
        {
            memcpy (&availBw_through_u, &u_map->avaiBandwidth, sizeof (gdouble));    
		}
		else
		{
			memcpy (&availBw_through_u, &edgeAvailBw, sizeof (gdouble));
		} 
    }     
    //DEBUG_RL_RA ("u->v (%s->%s) unused bw: %f", u->node.nodeId, v->tVertice.nodeId, availBw_through_u);

	// Check to relax the link according to the pathCost and latency
    gint map_vIndex = get_map_index_by_nodeId (v->tVertice.nodeId, mapNodes);
	struct map_t *v_map = &mapNodes->map[map_vIndex];
    // If cost, i.e. dist (u, v) > current instance to v, discard the link
    if (distance_through_u > v_map->distance)
    {
        DEBUG_RL_RA ("dist(src, u) + dist(u, v): %u > current dist (src, v): %u --> Discard Link", distance_through_u, 
        																						v_map->distance);  
        return 0;
    }
    // If dist (src, u) + dist (u, v) = current dist(src, v), then latency as discarding criteria
    if ((distance_through_u ==  v_map->distance) && (latency_through_u > v_map->latency))
    {
        DEBUG_RL_RA ("dist(src,u) + dist(u,v) = current dist(src, v), but delay(src,u) + delay (u, v) > current delay(src, v)");          
        return 0;
    }
	
	// If dist (src, u) + dist (u,v) == current dist(src, v) AND latency (src, u) + latency (u, v) == current latency (src, v), the available bandwidth is the criteria
	if ((distance_through_u ==  v_map->distance) && (latency_through_u == v_map->latency) && (availBw_through_u < v_map->avaiBandwidth))
	{
		return 0;
	}
    
    DEBUG_RL_RA ("Link u->v (%s->%s) Relaxed", u->node.nodeId, v->tVertice.nodeId);
    DEBUG_RL_RA ("		AvailBw: %f, cost: %u, latency: %f", availBw_through_u, distance_through_u, latency_through_u);
    
    // Update Q list -- 
    struct nodeItem_t *nodeItem = g_malloc0 (sizeof (struct nodeItem_t));
    if (nodeItem == NULL)
    {
		DEBUG_RL_RA ("memory allocation failed\n");
		exit (-1);    
    }
    
    nodeItem->distance = distance_through_u;
	duplicate_node_id (&v->tVertice, &nodeItem->node);    
	
	// add node to the Q list
    *Q = g_list_insert_sorted (*Q, nodeItem, sort_by_distance);
    DEBUG_RL_RA ("nodeItem (%s) added to Q (%d)", nodeItem->node.nodeId, g_list_length(*Q));    
    
    // Update the mapNodes for the specific reached tv   
    v_map->distance = distance_through_u;
    memcpy (&v_map->avaiBandwidth, &availBw_through_u, sizeof (gdouble));
    memcpy (&v_map->latency, &latency_through_u, sizeof (gdouble));

    // Copy (duplicate) the predecessor edge into the mapNodes 
	struct edges_t *e1 = &(v_map->predecessor);
	struct edges_t *e2 = &(v->edges[indexEdge]);
	duplicate_edge (e1, e2);	
	DEBUG_RL_RA ("v edge: %s --> %s", v->edges[indexEdge].aNodeId.nodeId, v->edges[indexEdge].zNodeId.nodeId);
	DEBUG_RL_RA ("mapNodes predecessor edge: %s --> %s", v_map->predecessor.aNodeId.nodeId, v_map->predecessor.zNodeId.nodeId);

    // Check whether v is dstPEId
	 DEBUG_RL_RA ("Targeted dstPEId: %s", req->dstPEId.nodeId);
	 DEBUG_RL_RA ("nodeId added to the map: %s", v_map->verticeId.nodeId);
#if 0
    if (memcmp (v_map->verticeId.nodeId, req->dstPEId.nodeId, sizeof (v_map->verticeId.nodeId)) == 0)
    {
        DEBUG_RL_RA ("v: %s is Req's destination: %s", v_map->verticeId.nodeId, req->dstPEId.nodeId);       	
        return 1;        
    }
#endif

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
	}	
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
	  DEBUG_RL_RA ("sort by distance a and b");     
     /** check values */
     g_assert(a != NULL);
     g_assert(b != NULL);
	
	  DEBUG_RL_RA ("sort by distance a and b");
	  
     struct nodeItem_t *node1 = (struct nodeItem_t *)a;
     struct nodeItem_t *node2 = (struct nodeItem_t *)b;
     g_assert (node1);
	 g_assert (node2);
	 
     DEBUG_RL_RA ("a->distance (%u); b->distance (%u)", node1->distance, node2->distance); 

     return (node1->distance > node2->distance);
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
	
	return;
}