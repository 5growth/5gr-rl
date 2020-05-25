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

//#include "mtp_pa_server.h"
#include "mtp_pa_server_stream.h"
#include "mtp_pa_server_comp.h"

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Function used to print the computed the path
 *
 *		@param path
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_path (struct compRouteOutput_t *path)
{
	gint k = 0;
	
	DEBUG_MTP_PA_SERVER ("\n");
	DEBUG_MTP_PA_SERVER ("==================================================================");
	DEBUG_MTP_PA_SERVER ("Path for InterNfviPopConnectivityId: %s ", path->interNfviPopConnectivityId);
	
	for (k = 0; k < path->numRouteElements; k++)
	{
		DEBUG_MTP_PA_SERVER ("aNodeId: %s (%d) --> zNodeId: %s (%d)", path->routeElement[k].aNodeId.nodeId, path->routeElement[k].aLinkId, 
																path->routeElement[k].zNodeId.nodeId, path->routeElement[k].zLinkId);
		
	}
	DEBUG_MTP_PA_SERVER ("==================================================================");   
		
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Creates the predecessors to keep the computed path
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct pred_t * create_predecessors ()
{
	struct pred_t *predecessors = g_malloc0 (sizeof (struct pred_t));
	if (predecessors == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation failed\n");
		exit (-1);
	}   
	return predecessors;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief It creates a struct nodes_t
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct nodes_t * create_node ()
{
	struct nodes_t *n = g_malloc0 (sizeof (struct nodes_t));
	if (n == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation problem");
		exit (-1);
	}
	return n;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief It creates a routeElement_t
 *
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct routeElement_t * create_routeElement ()
{
	struct routeElement_t *rE = g_malloc0 (sizeof (struct routeElement_t));
	if (rE == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation problem");
		exit (-1);		
	}
	return rE;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief copy node ids
 *
 *	@param src
 *  @param dst
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_node_id (struct nodes_t *src, struct nodes_t *dst)
{	
	g_assert (src);
	g_assert (dst);
	
	//DEBUG_MTP_PA_SERVER ("Duplicate nodeId for %s", src->nodeId);	
	memcpy (dst->nodeId, src->nodeId, sizeof (src->nodeId));
	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief duplicate two routeElement_t
 *
 *	@param src
 *  @param dst
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief duplicate two edges
 *
 *	@param e1 (destination)
 *  @param  e2 (source)
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_edge (struct edges_t *e1, struct edges_t *e2)
{
	g_assert (e1);
	g_assert (e2);
		
	duplicate_node_id (&e2->aNodeId, &e1->aNodeId);
	duplicate_node_id (&e2->zNodeId, &e1->zNodeId);
	DEBUG_MTP_PA_SERVER ("e->aNodeId: %s --->  e->zNodeId: %s", e1->aNodeId.nodeId, e1->zNodeId.nodeId);
	e1->aLinkId = e2->aLinkId;
    e1->zLinkId = e2->zLinkId;
    e1->linkCost = e2->linkCost;
    memcpy (&e1->linkDelay, &e2->linkDelay, sizeof (gdouble));
    memcpy (&e1->linkAvailBw, &e2->linkAvailBw, sizeof (gdouble));
	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Duplicate path 
 *
 *	@param a - original
 *  @param b - copy
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void duplicate_path (struct compRouteOutput_t *a, struct compRouteOutput_t *b)
{	
	gint k = 0;
	DEBUG_MTP_PA_SERVER ("Path for connection: %s", a->interNfviPopConnectivityId);	
	memcpy (b->interNfviPopConnectivityId, a->interNfviPopConnectivityId, sizeof (a->interNfviPopConnectivityId));		
	
	memcpy (&b->reqBw, &a->reqBw, sizeof (gdouble));
	b->numRouteElements = a->numRouteElements;	
	b->linkCost = a->linkCost;
	memcpy (&b->latency, &a->latency, sizeof (gdouble));
		
	for (k = 0; k < a->numRouteElements; k++)
	{			
		//DEBUG_MTP_PA_SERVER ("aNodeId: %s // zNodeId: %s", a->routeElement[k].aNodeId.nodeId, a->routeElement[k].zNodeId.nodeId);
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
		DEBUG_MTP_PA_SERVER ("[Route Element: %d]; %s (%u)  -----> %s (%u)", (k+1), b->routeElement[k].aNodeId.nodeId, b->routeElement[k].aLinkId, 
																					b->routeElement[k].zNodeId.nodeId, b->routeElement[k].zLinkId);
		
	}	
	return;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Return the index into mapN related nodeId
 * 
 *  @param nodeId
 *  @para mapN
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint get_map_index_by_nodeId (gchar *nodeId, struct map_nodes_t * mapN)
{
    gint index = -1;
    gint i = 0;
    
    for (i = 0; i < mapN->numMapNodes; i++)
    {
        //DEBUG_MTP_PA_SERVER ("i: %d; current: %s // targeted: %s", i, mapN->map[i].verticeId.nodeId, nodeId);
        if (memcmp (mapN->map[i].verticeId.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            index = i;
			DEBUG_MTP_PA_SERVER ("Index: %d", index);
            return index;            
        }
    }
	DEBUG_MTP_PA_SERVER ("Index: %d", index);
    return index;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Get the edge e enabling reaching the computed v in mapNodes
 * 
 *  @param e
 *  @param v
 *  @param mapN
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void get_edge_from_map_by_node (struct edges_t *e, struct nodes_t v, struct map_nodes_t *mapN)
{
	
	DEBUG_MTP_PA_SERVER ("Get the Edge into map from node v: %s", v.nodeId);	
	// Get the edge reaching the node v from mapNodes
	gint map_vIndex = get_map_index_by_nodeId (v.nodeId, mapN);
	
	DEBUG_MTP_PA_SERVER ("mapN->map[map_vIndex].predecessor.aNodeId: %s, mapN->map[map_vIndex].predecessor.zNodeId: %s", mapN->map[map_vIndex].predecessor.aNodeId.nodeId, mapN->map[map_vIndex].predecessor.zNodeId.nodeId);
	
	struct edges_t *te = &(mapN->map[map_vIndex].predecessor);	
	duplicate_edge (e, te);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Get the edge from the predecessors array for a given node n
 * 
 *  @param e
 *  @param n
 *  @param predecessors
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void get_edge_from_predecessors (struct edges_t *e, struct nodes_t n, struct pred_t *predecessors)
{
	DEBUG_MTP_PA_SERVER ("Get edge outgoing node %s from predecessors list", n.nodeId);	
	gint i = 0;
	for (i = 0; i < predecessors->numPredComp; i++)
	{
		if (memcmp (n.nodeId, predecessors->predComp[i].v.nodeId, sizeof (predecessors->predComp[i].v.nodeId)) == 0)
		{
			// Add to the predecessors list
			struct edges_t *te = &(predecessors->predComp[i].e);
			duplicate_edge (e, te);
			DEBUG_MTP_PA_SERVER ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
			return;
		}	
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Construct the path using the predecessors list
 * 
 *  @param path
 *  @param predecessors
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_path (struct compRouteOutput_t *path, struct pred_t *predecessors)
{
	DEBUG_MTP_PA_SERVER ("\n");
	DEBUG_MTP_PA_SERVER ("Construct the computed path");	
	struct nodes_t v;
	
	duplicate_node_id (&interNfviPopConnectionReq->srcPEId, &v);
	
	struct edges_t *e = g_malloc0 (sizeof (struct edges_t));
	if (e == NULL)
	{
		DEBUG_MTP_PA_SERVER ("Memory allocation problem");
		exit (-1);
	}
	
	// Get the edge for v in predecessors
	get_edge_from_predecessors (e, v, predecessors);
	
	DEBUG_MTP_PA_SERVER ("edge -- aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", e->aNodeId.nodeId, e->zNodeId.nodeId, e->aLinkId, e->zLinkId);
	
	// Get the target for e
	struct nodes_t u;	
	duplicate_node_id (&e->zNodeId, &u);
	DEBUG_MTP_PA_SERVER ("u: %s", u.nodeId);
	
	memcpy (path->interNfviPopConnectivityId, interNfviPopConnectionReq->interNfviPopConnectivityId, sizeof (interNfviPopConnectionReq->interNfviPopConnectivityId));
	memcpy (&path->reqBw, &interNfviPopConnectionReq->bandwidthCons, sizeof (gdouble));
	
	// Add route element to the path being constructed
	gint k = 0;		
	
	duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
	duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);	
	path->routeElement[k].aLinkId = e->aLinkId;
	path->routeElement[k].zLinkId = e->zLinkId;	
	path->numRouteElements++;	
	DEBUG_MTP_PA_SERVER ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																												path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
	DEBUG_MTP_PA_SERVER ("Route Elements: %d", path->numRouteElements);
		
	while (memcmp ((const char*)(u.nodeId), (const char *) (interNfviPopConnectionReq->dstPEId.nodeId), strlen(interNfviPopConnectionReq->dstPEId.nodeId)) != 0)
	{
		k++; 
		path->numRouteElements++;
		// v = u		
		duplicate_node_id (&u, &v);
		DEBUG_MTP_PA_SERVER ("v: %s u: %s", v.nodeId, u.nodeId);
		
		get_edge_from_predecessors (e, v, predecessors);
		
		// Get the target u		
		duplicate_node_id (&e->zNodeId, &u);
		
		// Add route element to the path being constructed		
		duplicate_node_id (&e->aNodeId, &path->routeElement[k].aNodeId);
		duplicate_node_id (&e->zNodeId, &path->routeElement[k].zNodeId);
		path->routeElement[k].aLinkId = e->aLinkId;
		path->routeElement[k].zLinkId = e->zLinkId;
		DEBUG_MTP_PA_SERVER ("Route (item: %d) aNodeId: %s, zNodeId: %s, aLinkkId: %u. zLinkId: %u", k, path->routeElement[k].aNodeId.nodeId, path->routeElement[k].zNodeId.nodeId,
																																 path->routeElement[k].aLinkId, path->routeElement[k].zLinkId);
		DEBUG_MTP_PA_SERVER ("Route Elements: %d", path->numRouteElements);		
	}
		
	g_free (e);
	DEBUG_MTP_PA_SERVER ("Path is constructed");
	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Print the graph for debugging purposes
 * 
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void print_graph (struct graph_t *g)
{
    DEBUG_MTP_PA_SERVER ("\n");
    DEBUG_MTP_PA_SERVER ("================================================================");
    DEBUG_MTP_PA_SERVER ("===========================   GRAPH   ==========================");
    DEBUG_MTP_PA_SERVER ("================================================================");
    
    gint i = 0, j = 0, k = 0;
    for (i = 0; i < g->numVertices; i++)
    {
        DEBUG_MTP_PA_SERVER ("Vertice [%s]", g->vertices[i].verticeId.nodeId);
        for (j = 0; j < g->vertices[i].numTargetedVertices; j++)
        {
            DEBUG_MTP_PA_SERVER ("  Targeted Vertice: %s", g->vertices[i].targetedVertices[j].tVertice.nodeId);
            for (k = 0; k < g->vertices[i].targetedVertices[j].numEdges; k++)
            {
                struct edges_t *e = &(g->vertices[i].targetedVertices[j].edges[k]);
				DEBUG_MTP_PA_SERVER ("		Edge %s --> %s", e->aNodeId.nodeId, e->zNodeId.nodeId);
				DEBUG_MTP_PA_SERVER ("      Links: %u %u, Cost: %d, AvailBw: %f, Delay: %f", e->aLinkId, e->zLinkId, e->linkCost, e->linkAvailBw, e->linkDelay);
           }
        }       
    }   
    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Look for a given edge into the graph
 *
 *  @param verticeIndex
 *	@param targetedVerticeIndex
 *  @param e
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
			DEBUG_MTP_PA_SERVER ("Edge %s (%u) --> %s (%u) is found in the graph at index: %d", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId, j);
			indexEdge = j;
			return indexEdge;
		}		
	}	
	return indexEdge;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Look for a given vertice within the graph using the nodeId
 *
 *  @param nodeId
 *	@param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
            DEBUG_MTP_PA_SERVER ("%s is found in the graph vertice [%d]", nodeId, index);
            break;
        }     
    }  
    return (index);
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Check if a nodeId is already considered into the set of targeted vertices from a given vertice
 *
 *  @param nodeId
 *  @param vIndex
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
            DEBUG_MTP_PA_SERVER ("Targeted vertice %s reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);
            addedTargetedVerticeIndex = i;
            return (addedTargetedVerticeIndex);
        }        
    }
    
    // not found ...    
    return (addedTargetedVerticeIndex);
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Check if a nodeId is already considered into the set of targeted vertices from a given vertice, if not to be added
 *
 *  @param nodeId
 *  @param vIndex
 *  @param g
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint graph_targeted_vertice_add (gint vIndex, gchar *nodeId, struct graph_t *g)
{
    gint addedTargetedVerticeIndex = -1;
    gint i = 0;
    
    if (g->vertices[vIndex].numTargetedVertices == 0)
    {
        DEBUG_MTP_PA_SERVER ("targeted vertice %s being reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);        
        addedTargetedVerticeIndex = 0;
        return (addedTargetedVerticeIndex);
    }
    
    for (i = 0; i < g->vertices[vIndex].numTargetedVertices; i++)
    {
         if (memcmp (g->vertices[vIndex].targetedVertices[i].tVertice.nodeId, nodeId, strlen (nodeId)) == 0)
        {
            DEBUG_MTP_PA_SERVER ("Targeted vertice %s is already considered in the reachable from vertice %s", nodeId, g->vertices[vIndex].verticeId.nodeId);
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
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Add the edge to the graph using passed vertice
 *
 *  @param verticeIndex
 *  @param addedTargetedVerticeIndex
 *  @param indexEdge
 *  @param edge
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void graph_add_edge (gint verticeIndex, gint addedTargetedVerticeIndex, gint indexEdge, struct edges_t *edge, struct graph_t *g)
{
    DEBUG_MTP_PA_SERVER ("Adding Edge to the Graph");	
	struct edges_t *e = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex].edges[indexEdge]); 
	duplicate_edge (e, edge);    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Remove edge from the graph
 *
 *  @param g
 *  @param e
 * 
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
void remove_edge_from_graph (struct graph_t *g, struct edges_t *e)
{
	// Find the ingress vertice into the graph
	DEBUG_MTP_PA_SERVER ("Remove the edge: %s (%u) ---> %s (%u)", e->aNodeId.nodeId, e->aLinkId, e->zNodeId.nodeId, e->zLinkId);
	gint verticeIndex = -1;		
	verticeIndex = graph_vertice_lookup (e->aNodeId.nodeId, g);
	if (verticeIndex == -1)
	{
		DEBUG_MTP_PA_SERVER ("Edge w/ aNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId);
		return;
	}
	
	// Find the targeted vertice from vertice Id
	gint targetedVerticeIndex = -1;
	targetedVerticeIndex = graph_targeted_vertice_lookup (verticeIndex, e->zNodeId.nodeId, g);
	if (targetedVerticeIndex == -1)
	{
		DEBUG_MTP_PA_SERVER ("Edge w/ aNodeId: %s to zNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	DEBUG_MTP_PA_SERVER ("Edge w/ aNodeId: %s and zNodeId: %s found in the Graph", e->aNodeId.nodeId, e->zNodeId.nodeId);
	
	// Get the edge position
	gint edgeIndex = -1;
	edgeIndex = graph_edge_lookup (verticeIndex, targetedVerticeIndex, e, g);
	if (edgeIndex == -1)
	{
		DEBUG_MTP_PA_SERVER ("Edge w/ aNodeId: %s to aNodeId: %s is NOT in the Graph!!", e->aNodeId.nodeId, e->zNodeId.nodeId);
		return;
	}
	
	DEBUG_MTP_PA_SERVER ("Edge w/ aNodeId: %s and zNodeId: %s found in the Graph w/ edgeIndex: %d", e->aNodeId.nodeId, e->zNodeId.nodeId, edgeIndex);
	
	// Remove the edge
	DEBUG_MTP_PA_SERVER ("Start Removing edge from Graph");
	gint j = 0;	
	struct targetNodes_t *v = &(g->vertices[verticeIndex].targetedVertices[targetedVerticeIndex]);
	
	for (j = edgeIndex; j < v->numEdges; j++)
	{	
		struct edges_t *e1 = &(v->edges[j]);
		struct edges_t *e2 = &(v->edges[j+1]);		
		duplicate_edge (e1, e2);
	}
	v->numEdges --;
	DEBUG_MTP_PA_SERVER ("Number of Edges between %s and %s is %d", e->aNodeId.nodeId, e->zNodeId.nodeId, v->numEdges);	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief create edge
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct edges_t * create_edge ()
{
	struct edges_t *e = g_malloc0 (sizeof (struct edges_t));
	if (e == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation failed\n");
		exit (-1);
	}    
	return e;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief create the pointer for keeping a set of the paths (struct compRouteOutput_t)
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct path_set_t * create_path_set ()
{
	struct path_set_t * p = g_malloc0 (sizeof (struct path_set_t));
	if (p == NULL)
	{
		DEBUG_MTP_PA_SERVER ("Memory allocation problem");
		exit (-1);		
	}
	return p;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief compose the graph using the abstraction of the different WANs and the interWanLinks
 *
 *	@param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
		DEBUG_MTP_PA_SERVER ("memory allocation problem");
		exit (-1);
	}
       
	for (i = 0; i < mtpTopo->numAbsWanTopo; i++)
	{
		// Get the WIM
		DEBUG_MTP_PA_SERVER ("\n");
		DEBUG_MTP_PA_SERVER ("Creating the Graph Exploring WIM: %s", mtpTopo->absWanTop[i].WimId);
	
		// Retrieve the set of nodes within the explored WAN
		for (j = 0; j < mtpTopo->absWanTop[i].numNodes; j++)
		{
				g->numVertices++;
				DEBUG_MTP_PA_SERVER ("Vertice from WAN: %s",  mtpTopo->absWanTop[i].nodes[j].nodeId);
				struct nodes_t *verticeId = &g->vertices[indexVerticesGraph].verticeId;
				struct nodes_t *topoNode = &mtpTopo->absWanTop[i].nodes[j];					
				duplicate_node_id (topoNode, verticeId);
				DEBUG_MTP_PA_SERVER ("Vertice: %s added to the Graph", g->vertices[indexVerticesGraph].verticeId.nodeId);
				indexVerticesGraph++;
		}

		// Add to the graph the targeted vertices and for each of them the related edges for a given vertice already in the graph
		for (k = 0; k < mtpTopo->absWanTop[i].numEdges; k++)
		{				
								
			struct nodes_t *nodeIdaux = &(mtpTopo->absWanTop[i].edges[k].aNodeId);										
			duplicate_node_id (nodeIdaux, aNodeId);
			
			nodeIdaux = &(mtpTopo->absWanTop[i].edges[k].zNodeId);
			duplicate_node_id (nodeIdaux, zNodeId);
			
			DEBUG_MTP_PA_SERVER ("Exploring edge %s --> %s", aNodeId->nodeId, zNodeId->nodeId);				
			duplicate_node_id (aNodeId, &edge->aNodeId);					
			duplicate_node_id (zNodeId, &edge->zNodeId);
			
			edge->aLinkId =  mtpTopo->absWanTop[i].edges[k].aLinkId;
			edge->zLinkId = mtpTopo->absWanTop[i].edges[k].zLinkId;
			edge->linkCost = mtpTopo->absWanTop[i].edges[k].linkCost;
			memcpy (&(edge->linkAvailBw), &mtpTopo->absWanTop[i].edges[k].linkAvailBw, sizeof (gdouble));
			memcpy (&(edge->linkDelay), &mtpTopo->absWanTop[i].edges[k].linkDelay, sizeof (gdouble));
			memcpy (edge->networkLinkLayer, mtpTopo->absWanTop[i].edges[k].networkLinkLayer, sizeof (mtpTopo->absWanTop[i].edges[k].networkLinkLayer));   
	
			// Retrieve the Vertice from the graph related to aNodeId of the explored edge
			verticeIndex = graph_vertice_lookup (aNodeId->nodeId, g);
			if (verticeIndex == -1)
			{
				DEBUG_MTP_PA_SERVER ("%s is not in the current graph --- Weird", aNodeId->nodeId);
				exit (-1);                
			}
	
			// check if zNodeId as targeted vertice iff there is not
			addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, zNodeId->nodeId, g);                    
			DEBUG_MTP_PA_SERVER ("addedTargetedVerticeIndex: %d ", addedTargetedVerticeIndex);
	
			if (addedTargetedVerticeIndex == -1)
			{
				DEBUG_MTP_PA_SERVER ("%s MUST not be added (because it exists) as targeted vertice ", zNodeId->nodeId);
				continue;
			}
	
			// Add the targeted vertice and related edge
			struct targetNodes_t * v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
			duplicate_node_id (zNodeId, &v->tVertice);
			g->vertices[verticeIndex].numTargetedVertices++;
			DEBUG_MTP_PA_SERVER ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, g->vertices[verticeIndex].verticeId.nodeId);
			DEBUG_MTP_PA_SERVER ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices); 
	
			v->numEdges++; 
			DEBUG_MTP_PA_SERVER ("Number of Edges from vertice: %s to targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
																									v->tVertice.nodeId,
																									v->numEdges);
					   
			indexEdge = v->numEdges - 1;           		                   
			graph_add_edge (verticeIndex, addedTargetedVerticeIndex, indexEdge, edge, g);
			memcpy (v->edges[indexEdge].networkLinkLayer, edge->networkLinkLayer, sizeof (edge->networkLinkLayer));                      
		}		
	}
	
	// Get the interWANLinks to add information into the graph
	for (i = 0; i < mtpTopo->numInterWanLinks; i++)
    	{   	
			struct nodes_t *nodeIdaux = &(mtpTopo->interWanLinks[i].aPEId);										
			duplicate_node_id (nodeIdaux, aNodeId);			
			nodeIdaux = &(mtpTopo->interWanLinks[i].zPEId);
			duplicate_node_id (nodeIdaux, zNodeId);			        
        	DEBUG_MTP_PA_SERVER ("\n");
        	DEBUG_MTP_PA_SERVER ("Exploring interWANLink %s --> %s", aNodeId->nodeId, zNodeId->nodeId);            			
			
			duplicate_node_id (aNodeId, &edge->aNodeId);			
			duplicate_node_id (zNodeId, &edge->zNodeId);
        	edge->aLinkId =  mtpTopo->interWanLinks[i].aLinkId;
        	edge->zLinkId = mtpTopo->interWanLinks[i].zLinkId;
        	edge->linkCost = mtpTopo->interWanLinks[i].linkCost;
        	memcpy (&(edge->linkAvailBw), &mtpTopo->interWanLinks[i].linkAvailBw, sizeof (gdouble));
        	memcpy (&(edge->linkDelay), &mtpTopo->interWanLinks[i].linkDelay, sizeof (gdouble));          
    
        	// Retrieve the Vertice from the graph related to aNodeId of the explored edge
        	verticeIndex = graph_vertice_lookup (aNodeId->nodeId, g);
        	if (verticeIndex == -1)
        	{
            		DEBUG_MTP_PA_SERVER ("%s is not in the current graph --- Weird", aNodeId->nodeId);
            		exit (-1);                
        	}
        
        	// check if zNodeId as targeted vertice iff there is not
        	addedTargetedVerticeIndex = -1;
        	addedTargetedVerticeIndex = graph_targeted_vertice_add (verticeIndex, zNodeId->nodeId, g);
            
            DEBUG_MTP_PA_SERVER ("addedTargetedVerticeIndex: %d ", addedTargetedVerticeIndex);
            
        	if (addedTargetedVerticeIndex == -1)
        	{
            		DEBUG_MTP_PA_SERVER ("%s MUST not be added (because it exists) as targeted vertice ", zNodeId->nodeId);
            		continue;
        	}
        
        	// Add the targeted vertice and related edge
			struct targetNodes_t * v = &(g->vertices[verticeIndex].targetedVertices[addedTargetedVerticeIndex]);		
			duplicate_node_id (zNodeId, &v->tVertice);
        	g->vertices[verticeIndex].numTargetedVertices++;
        	DEBUG_MTP_PA_SERVER ("Targeted Vertice: %s from %s added to the Graph ", v->tVertice.nodeId, 
                                                                                g->vertices[verticeIndex].verticeId.nodeId);
            DEBUG_MTP_PA_SERVER ("Number of Targeted Vertices from %s is %d", g->vertices[verticeIndex].verticeId.nodeId, g->vertices[verticeIndex].numTargetedVertices); 
        
        	v->numEdges++; 
            DEBUG_MTP_PA_SERVER ("Number of Edges from vertice: %s to Targeted vertice: %s is %d", g->vertices[verticeIndex].verticeId.nodeId, 
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
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Create map of nodes to handle the path computation
 *
 * 	@param mapN
 *  @param g
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void build_map_node (struct map_nodes_t *mapN, struct graph_t *g)
{
	DEBUG_MTP_PA_SERVER ("Initializing and creating the Map of Nodes");            
    gint i = 0;    
    for (i = 0; i < g->numVertices; i++)
    {	
		duplicate_node_id (&g->vertices[i].verticeId, &mapN->map[i].verticeId);
        mapN->map[i].distance = INFINITY_COST;
        mapN->map[i].avaiBandwidth = 0.0;
        mapN->map[i].latency = INFINITY_COST;
        mapN->numMapNodes++;
    }
    DEBUG_MTP_PA_SERVER ("mapNodes is %d elements", mapN->numMapNodes);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Allocate memory for path of struct compRouteOutput_t *
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct compRouteOutput_t * create_path ()
{
	struct compRouteOutput_t *p = g_malloc0 (sizeof (struct compRouteOutput_t));
	if (p == NULL)
	{
		DEBUG_MTP_PA_SERVER ("Memory Allocation Problem");
		exit (-1);
	}
	return p;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Sort the set of paths according to the metric (1st criteria) and latency (2nd criteria)
 *
 *	@params setP
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
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
			struct compRouteOutput_t *path1 = &setP->paths[j];
			struct compRouteOutput_t *path2 = &setP->paths[j+1];
			DEBUG_MTP_PA_SERVER ("path1 [metric: %d; latency: %f]; path2 [metric: %d; latency: %f]", path1->linkCost, path1->latency, path2->linkCost, path2->latency);
			struct compRouteOutput_t *pathTmp = create_path ();
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
				if (path1->linkCost > path2->linkCost)
				{
					duplicate_path (path1, pathTmp);
					duplicate_path (path2, path1);
					duplicate_path (pathTmp, path2);
					g_free (pathTmp);
				}
				else if (path1->linkCost <= path2->linkCost)
				{
					g_free (pathTmp);
					continue;						
				}					
			}					
		}		
	}	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Remove first element from the path sets 
 *
 *	@params setP
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void pop_front_path_set (struct path_set_t *setP)
{
	gint j = 0;	
	for (j = 0; j < setP->numPaths - 1; j++)
	{
		struct compRouteOutput_t *path1 = &setP->paths[j];
		struct compRouteOutput_t *path2 = &setP->paths[j+1];
		
		duplicate_path (path2, path1);		
	}

	setP->numPaths--;	
	return;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Add routeElement to the back of the path
 *
 * 	@param rE
 * 	@para p
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void add_routeElement_path_back (struct routeElement_t *rE, struct compRouteOutput_t *p)
{
	DEBUG_MTP_PA_SERVER ("p->numRouteElements: %d", p->numRouteElements);
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

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Allocate memory for graph
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct graph_t * create_graph ()
{
	struct graph_t * g = g_malloc0 (sizeof (struct graph_t));
	if (g == NULL)
	{
		DEBUG_MTP_PA_SERVER ("Memory Allocation Problem");
		exit (-1);
	}
	return g;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Allocate memory for mapNodes
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct map_nodes_t * create_map_node ()
{
	struct map_nodes_t * mN = g_malloc0 (sizeof (struct map_nodes_t));
	if (mN == NULL)
	{
		DEBUG_MTP_PA_SERVER ("Memory Allocation Problem");
		exit (-1);
	}
	return mN;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Allocate memory for mtp_topology_t
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct mtp_topology_t * create_mtp_topology ()
{
	struct mtp_topology_t *mtp_topology = g_malloc0 (sizeof (struct mtp_topology_t));
	if (mtp_topology == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocatio failed\n");
		exit (-1);
	}
	
	return mtp_topology;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_comp.c
 * 	@brief Allocate memory for interNfviPop_connection_req_t
 *
 *
 * 	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct interNfviPop_connection_req_t * create_interNfviPop_conn_req ()
{
	struct interNfviPop_connection_req_t * interNfviConnReq = g_malloc0 (sizeof (struct interNfviPop_connection_req_t));
	if (interNfviConnReq == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation failed\n");
		exit (-1);
	}
	return interNfviConnReq;	
}
