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

#include <fcntl.h>

#include "rl_ra_server_stream.h"
#include "rl_ra_server_comp.h"
#include "rl_ra_server_cjson.h"
#include "rl_ra_server_ra_CSA.h"
#include "rl_ra_server_ra_InA.h"
#include "rl_ra_server_restapi.h"

#define ISspace(x) isspace((int)(x))

#define SERVER_STRING "Server: 5GR RL RA/0.1.0\r\n"

// List of Clients connected to the 5Gr RL RA Server
GList *rapi_tcp_client_list = NULL;

// Id for CLient HTTP (REST API) Connection
guint CLIENT_ID = 0;
guint32 paId_req = 0;

struct request_list_t *reqList;
struct rl_topology_t *rlTopo;
gchar raId[MAX_RAID_LENGTH];
gboolean syncPath = FALSE;

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to find a rl_client by its fd in the rapi_tcp_client_list
 * 	
 * 	@param data
 *  @param userdata
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint find_rl_client_by_fd (gconstpointer data, gconstpointer userdata)
{
	 /** check values */
     g_assert(data != NULL);
     g_assert(userdata != NULL);
	 
	 struct rl_client *rl_client = (struct rl_client *)data;
     gint fd = *(gint *)userdata; 
     
     if (rl_client->fd == fd)
		 return 0;        
    return -1;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to send a message to the corresponding channel
 * 	
 * 	@param source
 *  @param buf
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint rapi_send_message (GIOChannel *channel, char *buf)
{
	gsize nbytes, buffersize;

	DEBUG_RL_RA ("Msg prepared to be sent REST API RESPONSE ");
    gint len = strlen ((const char*) buf);
    DEBUG_RL_RA ("Targeted Length of the buffer: %d", len);       

    buffersize = g_io_channel_get_buffer_size (channel);
    DEBUG_RL_RA ("GIOChannel with Buffer Size: %d", buffersize);
    
    gsize newBufferSize = MAX_GIO_CHANNEL_BUFFER_SIZE;
    g_io_channel_set_buffer_size (channel, newBufferSize);
    
    buffersize = g_io_channel_get_buffer_size (channel);
    DEBUG_RL_RA ("GIOChannel with Buffer Size: %d", buffersize);
    
	/** Send message.  */
    GError *error = NULL;
    
    char *ptr = buf;
    gint nleft = strlen ((const char *)buf);
    
    while (nleft > 0)
    {
        g_io_channel_write_chars (channel, (void *)ptr, nleft, &nbytes, &error);
        if (error)
        {
            DEBUG_RL_RA ("Error sending the message to TCP Client");
            return (-1);
        }
        
        DEBUG_RL_RA ("Sent %d bytes", nbytes);        
        nleft = nleft - nbytes;
        DEBUG_RL_RA ("Remaining to be sent %d", nleft);
        ptr += nbytes;        
    } 
	return 0;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to return when something goes wrong when processing the REST API Command
 * 	
 * 	@param source
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_unimplemented (GIOChannel *source)
{
      gint ret = 0;
      guchar buftmp[1024];
      char *buf = g_malloc0 (sizeof (char) * 2048000); 
      sprintf((char *)buf, "HTTP/1.1 400 Bad request\r\n");

      sprintf((char *)buftmp, SERVER_STRING);
      strcat ((char *)buf, (const char *)buftmp);

      sprintf((char *)buftmp, "Content-Type: text/plain\r\n");
      strcat ((char *)buf, (const char *)buftmp);

      sprintf((char *)buftmp, "\r\n");
      strcat ((char *)buf, (const char *)buftmp);
	      
      sprintf((char *)buftmp, "<HTML><HEAD><TITLE>Method Not Implemented\r\n");
      strcat ((char *)buf, (const char *)buftmp);

      sprintf((char *)buftmp, "</TITLE></HEAD>\r\n");
      strcat ((char *)buf, (const char *)buftmp);

      sprintf((char *)buftmp, "<BODY><P>HTTP request method not supported.\r\n");
      strcat ((char *)buf, (const char *)buftmp);	

      sprintf((char *)buftmp, "</BODY></HTML>\r\n");
      strcat ((char *)buf, (const char *)buftmp);

      ret = rapi_send_message (source, buf);
      //memset( buf, '\0', sizeof( buf )); 
      g_free (buf);

      (void)ret;

  return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to put in the buffer the date according to RFC 1123
 * 	
 * 	@param date
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_add_date_header (char *date)
{
    static const char *DAYS[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    static const char *MONTHS[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    time_t t = time(NULL);
    struct tm *tm = NULL;
    struct tm sys;
    gmtime_r(&t, &sys);
    tm = &sys;

    sprintf((char *)date, "DATE: %s, %02d %s %4d %02d:%02d:%02d GMT\r\n", DAYS[tm->tm_wday], tm->tm_mday, 
								      MONTHS[tm->tm_mon], 1900 + tm->tm_year, 
								      tm->tm_hour, tm->tm_min, tm->tm_sec);
    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function that checks if an edge within the computed route is an interWanLink
 * 	
 * 	@param routeElement
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint found_interWanLink_topology (struct routeElement_t routeElement)
{
	gint ret = -1;
	gint i = 0;
	
	for (i = 0; i < rlTopo->numInterWanLinks; i++)
	{
		// Check if routeElement (aNodeId, zNodeId, aLinkId and zLinkId) matches with one of the current interWanLinks
		if ((memcmp (routeElement.aNodeId.nodeId, rlTopo->interWanLinks[i].aPEId.nodeId, sizeof (rlTopo->interWanLinks[i].aPEId.nodeId)) == 0) &&
			(memcmp (routeElement.zNodeId.nodeId, rlTopo->interWanLinks[i].zPEId.nodeId, sizeof (rlTopo->interWanLinks[i].zPEId.nodeId)) == 0) &&
			(routeElement.aLinkId == rlTopo->interWanLinks[i].aLinkId) && (routeElement.zLinkId == rlTopo->interWanLinks[i].zLinkId))
		{
			DEBUG_RL_RA ("routeElement [aNodeId: %s (%u)---> zNodeId: %s (%u)] is an interWanLink", routeElement.aNodeId.nodeId, routeElement.aLinkId,			
																											routeElement.zNodeId.nodeId, routeElement.zLinkId);
			ret = i;			
			return ret;
		} 
	 	continue;	
	}
   	
	return ret;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function that allows retrieving all the interWanLinks over the computed path returned by the algorithm
 * 	
 * 	@param edges
 *		@param numEdges
 *		@param path
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean retrieve_interWanLinks_from_comRouteOutput (struct interWanLink_t *edges, gint *numEdges, struct compRouteOutputItem_t *path)
{
	gboolean ret = FALSE; 	
 	for (gint i = 0; i < path->numRouteElements; i++)
 	{
 		// Check whether current link is on the rlTopo->interWanLinks
 		gint index = -1;
 		index = found_interWanLink_topology (path->routeElement[i]);
		
 		if (index > -1)
 		{
			DEBUG_RL_RA ("interWanLink is found at iter i: %d from rlTopo", index);
			struct interWanLink_t *e1 = &(edges[*numEdges]);
			struct interWanLink_t *e2 = &(rlTopo->interWanLinks[index]);
 			// Add to edges containing interWanLinks
 			memcpy (e1->aWimId, e2->aWimId, sizeof (e2->aWimId));
 			memcpy (e1->zWimId, e2->zWimId, sizeof (e2->zWimId));
			duplicate_node_id (&e2->aPEId, &e1->aPEId);
 			duplicate_node_id (&e2->zPEId, &e1->zPEId); 			
 			e1->aLinkId = e2->aLinkId;
 			e1->zLinkId = e2->zLinkId; 			
 			DEBUG_RL_RA ("interWanLink %s (aWimId: %s)--> %s (zWimId: %s) -- Added", e1->aPEId.nodeId, e1->aWimId, e1->zPEId.nodeId, e1->zWimId);
		 	*numEdges = *numEdges + 1;
		 	DEBUG_RL_RA ("numInterWanLinks: %d", *numEdges);		
 		}
 		else 
 		{
		 	continue;	
 		} 	
 	}
	// check whether interWanLinks have been added 
	if (*numEdges > 0)
		ret = TRUE;
	else 
		ret = FALSE;

	return (ret);
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Find out the WAN (Wim Id) which contains a specific edge
 * 	
 * 	@param routeElement
 *
 *		@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *		@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint found_edge_wan (struct routeElement_t routeElement)
{
	gint ret = -1;		
	for (gint i = 0; i < rlTopo->numAbsWanTopo; i++)
	{
		DEBUG_RL_RA ("Exploring WimId: %s", rlTopo->absWanTop[i].WimId);
		
		for (gint j = 0; j < rlTopo->absWanTop[i].numEdges; j++)
		{
			if ((memcmp (routeElement.aNodeId.nodeId, rlTopo->absWanTop[i].edges[j].aNodeId.nodeId, sizeof (rlTopo->absWanTop[i].edges[j].aNodeId.nodeId)) == 0) &&
			(memcmp (routeElement.zNodeId.nodeId, rlTopo->absWanTop[i].edges[j].zNodeId.nodeId, sizeof (rlTopo->absWanTop[i].edges[j].zNodeId.nodeId)) == 0) &&
			(routeElement.aLinkId == rlTopo->absWanTop[i].edges[j].aLinkId) && (routeElement.zLinkId == rlTopo->absWanTop[i].edges[j].zLinkId))
			{	
				DEBUG_RL_RA ("routeElement [aNodeId: %s (%u)---> zNodeId: %s (%u)] is within WAN (wimId: %s)", routeElement.aNodeId.nodeId, routeElement.aLinkId,			
																																			routeElement.zNodeId.nodeId, routeElement.zLinkId,
																																			rlTopo->absWanTop[i].WimId);
				ret = i;			
				return ret;		
			}	
		}
	}
	return ret;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to partition the computed path (path) into specific segments for each traversed WAN
 * 	
 * 	@param wanSet
 *  	@param numWans
 *  	@param path
 *
 *		@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *		@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void retrieve_compRoute_segments_per_WAN (struct wansTopo_t *wanSet, struct compRouteOutputItem_t *path)
{
	gint i = 0;	
	gint indexWan = -1;
	for (i = 0; i < path->numRouteElements; i++)
 	{
 		// Find the WAN (within rlTopo->absWanTopo) associated to every explored edge/link forming the computed path 		 
 		indexWan = found_edge_wan (path->routeElement[i]);
		DEBUG_RL_RA ("indexWan: %d", indexWan);
		
 		if (indexWan > -1)
 		{				
 			DEBUG_RL_RA ("Edge (%s ---> %s) within WAN (WimId: %s)", path->routeElement[i].aNodeId.nodeId,
 																						path->routeElement[i].zNodeId.nodeId, 
																						rlTopo->absWanTop[indexWan].WimId);
			struct absWanTopo_t *newWanTopo = &(wanSet->absWanTop[wanSet->numAbsWanTopo]);
			struct absWanTopo_t *refWanTopo = &(rlTopo->absWanTop[indexWan]);
																						
 			if (wanSet->numAbsWanTopo == 0)
 			{
				memcpy (newWanTopo->WimId, refWanTopo->WimId, sizeof (refWanTopo->WimId));
				newWanTopo->numEdges = 0; 				
 				wanSet->numAbsWanTopo = wanSet->numAbsWanTopo + 1;
 				DEBUG_RL_RA ("WIMID: %s is added with numEdges: %d", newWanTopo->WimId, newWanTopo->numEdges);
 			}
 			else 
 			{
				// Check if the wimId is already within the wanSet; if not add to the WanSet				
				gboolean found = FALSE;
				for (gint k = 0; k < wanSet->numAbsWanTopo; k++)
				{
					if (memcmp (wanSet->absWanTop[k].WimId, refWanTopo->WimId, sizeof (refWanTopo->WimId)) == 0)
					{
							DEBUG_RL_RA ("wanSet[k].WimId: %s not added so far ... added as traversed domain", wanSet->absWanTop[k].WimId);
							found = TRUE;
							break;				
					}				
				}
				if (found == FALSE)
				{
					memcpy (newWanTopo->WimId, refWanTopo->WimId, sizeof (refWanTopo->WimId));
					newWanTopo->numEdges = 0;					
					wanSet->numAbsWanTopo = wanSet->numAbsWanTopo + 1;				
				} 				
			}	
																													
 			// Add to the edge on the WAN set within the specific WAN array
 			gint indexWanEdges = wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges;
			
			struct edges_t *e = &(wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges]);
			struct routeElement_t *re = &(path->routeElement[i]);
			
			duplicate_node_id (&re->aNodeId, &e->aNodeId);
			duplicate_node_id (&re->zNodeId, &e->zNodeId);
			
 			//memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].aNodeId.nodeId, path->routeElement[i].aNodeId.nodeId, sizeof (path->routeElement[i].aNodeId.nodeId));
 			//memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].zNodeId.nodeId, path->routeElement[i].zNodeId.nodeId, sizeof (path->routeElement[i].zNodeId.nodeId));
			e->aLinkId = re->aLinkId;
			e->zLinkId = re->zLinkId; 					
 			DEBUG_RL_RA ("Edge [within wimId (%s)] %s (%u) --> %s (%u)-- Added", wanSet->absWanTop[wanSet->numAbsWanTopo -1].WimId, 
 																						e->aNodeId.nodeId,
																						e->aLinkId,
 																						e->zNodeId.nodeId,
																						e->zLinkId);
		 	wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges++;
		 	DEBUG_RL_RA ("Num Edges within wimId: %s: %d", wanSet->absWanTop[wanSet->numAbsWanTopo -1].WimId, 
																	wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges);
 		}  
 		else 
 		{
 			DEBUG_RL_RA ("Explored link does not belong to a WAN");
 			continue;
 		} 					
 	} 	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Add to the json contents the WAN path for a given computed route
 * 	
 * 	@param wanSet
 *  @param route
 *  @param Object
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void add_compRouteOutput_json_WAN_paths (struct compRouteOutputItem_t *route, cJSON *Object)
{
	DEBUG_RL_RA ("Add path segment on per WAN basis");
	// Add specific path segments for each traversed WAN 
	struct wansTopo_t *compRouteWan = g_malloc0 (sizeof (struct wansTopo_t));
	if (compRouteWan == NULL)
	{
		DEBUG_RL_RA ("memory allocation problem");
		exit (-1);	
	}
	// Gather for each specific traversed WAN the set of links within comRouteOutput	
	retrieve_compRoute_segments_per_WAN (compRouteWan, route);	

    // Add specific JSON contents of the computed route edges on per Domain (wimId) basis
	cJSON *wanPathsArray = cJSON_CreateArray();
	cJSON_AddItemToObject(Object, "wanPaths", wanPathsArray);
		
	for (gint k = 0; k < compRouteWan->numAbsWanTopo; k++)
	{
		cJSON *wanPathsArrayObject;
		cJSON_AddItemToArray (wanPathsArray, wanPathsArrayObject = cJSON_CreateObject());
		
		// Add the WIM id into the object
		cJSON_AddItemToObject (wanPathsArrayObject, "wimId", cJSON_CreateString (compRouteWan->absWanTop[k].WimId));
		
		// Add the set of edges for that WIM Id
		cJSON *wanEdgesArray = cJSON_CreateArray();
		cJSON_AddItemToObject(wanPathsArrayObject, "wanPathElements", wanEdgesArray);		
		for (gint e = 0; e < compRouteWan->absWanTop[k].numEdges; e++)
		{
			cJSON *wanEdgeObject;
			cJSON_AddItemToArray (wanEdgesArray, wanEdgeObject = cJSON_CreateObject());
			cJSON_AddItemToObject (wanEdgeObject, "aNodeId", cJSON_CreateString (compRouteWan->absWanTop[k].edges[e].aNodeId.nodeId));
			cJSON_AddItemToObject (wanEdgeObject, "zNodeId", cJSON_CreateString (compRouteWan->absWanTop[k].edges[e].zNodeId.nodeId));
			cJSON_AddItemToObject (wanEdgeObject, "aLinkId", cJSON_CreateNumber(compRouteWan->absWanTop[k].edges[e].aLinkId));
			cJSON_AddItemToObject (wanEdgeObject, "zLinkId", cJSON_CreateNumber(compRouteWan->absWanTop[k].edges[e].zLinkId));		
		}
	}
    DEBUG_RL_RA ("JSON Body Response DONE");
	g_free (compRouteWan);	
	return;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used add interWanLinks on the JSON contents
 * 	
 * 	@param route
 *  @param Object
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void add_compRouteOutput_json_interWanLinks (struct compRouteOutputItem_t *route, cJSON *Object)
{		
	gboolean interWanLinks = FALSE;
	// Array for containing computed InterWanLinks to be extracted from the compRouteOutput
	struct interWanLink_t compInterWanLink[MAX_INTER_WAN_LINK];	 
	gint numCompInterWanLinks = 0;

	interWanLinks = retrieve_interWanLinks_from_comRouteOutput (compInterWanLink, &numCompInterWanLinks, route);
 
	if (interWanLinks == TRUE)
	{
		DEBUG_RL_RA ("InterWanLinks (# %d) being added to the JSON content of the Output", numCompInterWanLinks);
		// add specific JSON contents describing the interWanLinks
		cJSON *interWanLinksArray;
		interWanLinksArray =  cJSON_CreateArray();
		cJSON_AddItemToObject(Object, "interWanLinks", interWanLinksArray);
		gint j = 0;
		for (j = 0; j < numCompInterWanLinks; j++)
		{
			cJSON *interWanLinkObject;
			cJSON_AddItemToArray (interWanLinksArray, interWanLinkObject = cJSON_CreateObject());		
			cJSON_AddItemToObject (interWanLinkObject, "aWimId", cJSON_CreateString (compInterWanLink[j].aWimId));
			cJSON_AddItemToObject (interWanLinkObject, "zWimId", cJSON_CreateString (compInterWanLink[j].zWimId));
			cJSON_AddItemToObject (interWanLinkObject, "aPEId", cJSON_CreateString (compInterWanLink[j].aPEId.nodeId));
			cJSON_AddItemToObject (interWanLinkObject, "zPEId", cJSON_CreateString (compInterWanLink[j].zPEId.nodeId));
			cJSON_AddItemToObject (interWanLinkObject, "aLinkId", cJSON_CreateNumber(compInterWanLink[j].aLinkId));
			cJSON_AddItemToObject (interWanLinkObject, "zLinkId", cJSON_CreateNumber(compInterWanLink[j].zLinkId));
		}	 
	}
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Compose the JSON Body of the succesfully interNfviPop connection
 * 	
 * 	@param body
 *  @param length
 *  @param compRouteOutputList
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_response_json_contents (char *body, gint *length, struct compRouteOutputList_t *compRouteOutputList)
{
    char *buftmp;    
    cJSON *root = cJSON_CreateObject();     
	DEBUG_RL_RA ("Creating the JSON body of the response"); 

	// Create response-list array
	cJSON *responseListArray = cJSON_CreateArray();
	cJSON_AddItemToObject(root, "response-list", responseListArray);
	
	// Add computed routes to the response-list
	for (gint i = 0; i < compRouteOutputList->numCompRouteConnList; i++)
	{
		struct compRouteOutput_t *compRouteListElement = &compRouteOutputList->compRouteConnection[i];
		cJSON *compRouteListElementObject;
		cJSON_AddItemToArray (responseListArray, compRouteListElementObject = cJSON_CreateObject());
		
		// Add the requestId
		cJSON_AddItemToObject (compRouteListElementObject, "requestId", cJSON_CreateNumber (compRouteListElement->requestId));
		
		// Add the interNfviPopConnectivityId
		cJSON_AddItemToObject (compRouteListElementObject, "interNfviPopConnectivityId", cJSON_CreateString (compRouteListElement->interNfviPopConnectivityId));
		
		// Check whether Path has issues
		if (compRouteListElement->noPathIssue != 0)
		{
			DEBUG_RL_RA ("No Path Issue: %d", compRouteListElement->noPathIssue);
			cJSON *compRouteElementNoPathObj;
			cJSON_AddItemToObject (compRouteListElementObject, "noPath",compRouteElementNoPathObj = cJSON_CreateObject());
			cJSON_AddItemToObject (compRouteElementNoPathObj, "issue", cJSON_CreateNumber (compRouteListElement->noPathIssue));
			// next computed Route List Element
			continue;
		}
		
		// Create the Array to handle the set of computed paths for the compRouteListElement
		cJSON *responseArray = cJSON_CreateArray();
		cJSON_AddItemToObject(compRouteListElementObject, "response", responseArray);
		
		for (gint j = 0; j < compRouteListElement->numCompRoutes; j++)
		{
			struct compRouteOutputItem_t *routeItem = &compRouteListElement->compRoutes[j];
			cJSON  *compRouteItemObject;
			cJSON_AddItemToArray (responseArray, compRouteItemObject = cJSON_CreateObject());
			
			// Add requested/computed bandwidth for the considered routeItem
			cJSON_AddItemToObject (compRouteItemObject, "reqBw", cJSON_CreateNumber (routeItem->reqBw));
			
			// Add the computed latency for the considered routeItem
			cJSON_AddItemToObject (compRouteItemObject, "latency", cJSON_CreateNumber (routeItem->latency));
			
			// Add the compute end-to-end Path Cost for the considered routeItem
			cJSON_AddItemToObject (compRouteItemObject, "pathCost", cJSON_CreateNumber (routeItem->pathCost));
			
			// Add interWAN link for the considered routeItem (if any)
			add_compRouteOutput_json_interWanLinks (routeItem, compRouteItemObject);
			
			// Add specific path segments for each traversed WAN
			add_compRouteOutput_json_WAN_paths (routeItem, compRouteItemObject);			
		}	
	}
	
    DEBUG_RL_RA ("JSON Body Response DONE");
	
    buftmp = (char *)cJSON_Print(root);
    strcat (body, (const char*) buftmp);
    *length = strlen ((const char*)body);
    
    DEBUG_RL_RA ("JSON Body (length: %d)", *length);
    DEBUG_RL_RA ("%s", body);    
	cJSON_Delete (root);    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to return response OK via REST API with the computed interNfviConnectivityId
 * 	
 * 	@param source
 *  @param httpCode
 *  @param compRouteOutputList
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_response_ok (GIOChannel *source, gint httpCode, struct compRouteOutputList_t *compRouteOutputList)
{
    gint ret = 0;
    
    DEBUG_RL_RA ("Creating the JSON Body and sending the response of the computed Route List");
    
    guchar buftmp[1024];
    char * buf = g_malloc0 (sizeof (char) * 2048000); 
    // Create the Body of the Response 
    char * jsonBody = g_malloc0 (sizeof (char) * 2048000);    
    gint length = 0;
    
    rapi_response_json_contents (jsonBody, &length, compRouteOutputList);
		
	sprintf((char *)buf, "HTTP/1.1 200 OK\r\n");    
	
    sprintf((char *)buftmp, SERVER_STRING);
    strcat ((char *)buf, (const char *)buftmp);    
  
    sprintf ((char *)buftmp, "Content-Type: application/json\r\n");
    strcat ((char *)buf, (const char *)buftmp);    
    
    // Add the length of the JSON enconding to the Content_Length
    char buff_length[16];
    
    sprintf(buff_length, "%d", length);
    DEBUG_RL_RA ("Buffer Length (JSON BODY): %d Added to the Content Length", length);
    
    sprintf ((char *)buftmp, "Content-Length: ");
    strcat ((char *)buftmp, (const char *)buff_length);
    strcat ((char *)buftmp, "\r\n");
    strcat ((char *)buf, (const char *)buftmp);    
    
    // Add DATE header
    rapi_add_date_header ((char *)buftmp);
    strcat ((char *)buf, (const char *)buftmp);     
    sprintf((char *)buftmp, "\r\n");
    strcat ((char *)buf, (const char *)buftmp);
		
	strcat ((char *)buf, (const char *)jsonBody);		
	DEBUG_RL_RA ("Complete Message: %s", buf);
	    
    ret = rapi_send_message (source, buf);    
    g_free (buf);
    memset (buftmp, '\0', sizeof ( buftmp));    
    g_free (jsonBody);
    (void)ret;
    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to return response OK via REST API
 * 	
 * 	@param source
 *  @param error
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_response (GIOChannel *source, gint error)
{
	 int ret = 0;
	
	 guchar buftmp[1024];
	 char * buf = g_malloc0 (sizeof (char) * 2048000);
	 if (error == HTTP_RETURN_CODE_BAD_REQUEST)
		sprintf((char *)buf, "HTTP/1.1 400 BAD REQUEST\r\n");
	 else if (error == HTTP_RETURN_CODE_UNAUTHORIZED)
		sprintf((char *)buf, "HTTP/1.1 401 UNAUTHORIZED\r\n");
	 else if (error == HTTP_RETURN_CODE_FORBIDDEN)
		sprintf((char *)buf, "HTTP/1.1 403 FORBIDDEN\r\n");    
	 else if (error == HTTP_RETURN_CODE_NOT_FOUND)
		sprintf((char *)buf, "HTTP/1.1 404 NOT FOUND\r\n");
	 
	 sprintf((char *)buftmp, SERVER_STRING);
	 strcat ((char *)buf, (const char *)buftmp);    
	
	 sprintf((char *)buftmp, "Content-Type: text/plain\r\n");
	 strcat ((char *)buf, (const char *)buftmp);    
	 
	 sprintf((char *)buftmp, "Content-Length: 0/plain\r\n");
	 strcat ((char *)buf, (const char *)buftmp);    
	 
	 // Add DATE header
	 rapi_add_date_header ((char *)buftmp);
	 strcat ((char *)buf, (const char *)buftmp);     
	     
	 sprintf((char *)buftmp, "\r\n");
	 strcat ((char *)buf, (const char *)buftmp);
	 
	 DEBUG_RL_RA ("%s", buf);
	 
	 // Send the message
	 ret = rapi_send_message (source, buf);	 
	 g_free (buf);	 
	 (void)ret;
	
	 return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to parse the JSON object array describing the interWanLinks
 * 	
 * 	@param interWanLinksArray
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_interWanLinks (cJSON *interWanLinksArray)
{
    DEBUG_RL_RA ("Parsing the JSON for the interWanLinks: %d", (gint)cJSON_GetArraySize(interWanLinksArray));
    
    gint i = 0, index = 0;
    
    if (rlTopo == NULL)
    {
        DEBUG_RL_RA ("weird");   
        exit (-1);
    }
    DEBUG_RL_RA ("rlTopo num InterWanLinks: %d", rlTopo->numInterWanLinks); 
    
    gint numberInterWanLinks = 0;
    numberInterWanLinks = (gint)cJSON_GetArraySize(interWanLinksArray);
    DEBUG_RL_RA ("numberInterWanLinks: %d", numberInterWanLinks);
       
    for (i = 0; i < numberInterWanLinks; i++)
    {
        rlTopo->numInterWanLinks++;
        index = i;
		
		struct interWanLink_t *l = &(rlTopo->interWanLinks[index]);
        
        DEBUG_RL_RA ("Exploring numInterWanLinks: %d", rlTopo->numInterWanLinks);        
        cJSON *item = cJSON_GetArrayItem (interWanLinksArray, (int)i);        
        // Get the aWimId
        cJSON *aWimId = cJSON_GetObjectItem (item, "aWimId");
        if (cJSON_IsString(aWimId))
        {  
           	memcpy(l->aWimId, aWimId->valuestring, strlen (aWimId->valuestring));
            DEBUG_RL_RA ("aWimId: %s", l->aWimId);        
        }
        
        // Get zWimId
        cJSON *zWimId = cJSON_GetObjectItem (item, "zWimId");
        if (cJSON_IsString(zWimId))
        {
            memcpy(l->zWimId, zWimId->valuestring, strlen (zWimId->valuestring));
            DEBUG_RL_RA ("zWimId: %s", l->zWimId);        
        }
        
        // Get aPEId
        cJSON *aPEId = cJSON_GetObjectItem (item, "aPEId");
        if (cJSON_IsString(aPEId))
        {
            memcpy(l->aPEId.nodeId, aPEId->valuestring, strlen (aPEId->valuestring));
            DEBUG_RL_RA ("aPEId: %s", l->aPEId.nodeId);        
        }
        
        // Get zPEId
        cJSON *zPEId = cJSON_GetObjectItem (item, "zPEId");
        if (cJSON_IsString(zPEId))
        {
            memcpy(l->zPEId.nodeId, zPEId->valuestring, strlen (zPEId->valuestring));
            DEBUG_RL_RA ("zPEId: %s", l->zPEId.nodeId);        
        }  
        
        // Get aLinkId
        cJSON *aLinkId = cJSON_GetObjectItem (item, "aLinkId");
        if (cJSON_IsNumber(aLinkId))
        {
            l->aLinkId = (guint32)aLinkId->valuedouble;
            DEBUG_RL_RA ("aLinkId: %u", l->aLinkId);        
        }
        
        // Get zLinkId
        cJSON *zLinkId = cJSON_GetObjectItem (item, "zLinkId");
        if (cJSON_IsNumber(zLinkId))
        {
            l->zLinkId = (guint32)zLinkId->valuedouble;
            DEBUG_RL_RA ("zLinkId: %u", l->zLinkId);        
        }
        
        // Get QoS parameters: link cost, bw, delay
        cJSON *netwLinkQoS = cJSON_GetObjectItem(item, "netwLinkQoS");
        // Get the link cost in the netwLinkQoS object
        cJSON *LinkCost = cJSON_GetObjectItem (netwLinkQoS, "linkCostValue");       
        // Get the linkDelay
        cJSON *LinkDelay = cJSON_GetObjectItem (netwLinkQoS, "linkDelayValue");
        // Get the link Avail Bw
        cJSON *LinkAvaiBw = cJSON_GetObjectItem (netwLinkQoS, "linkAvailBwValue");
        
        if (cJSON_IsNumber(LinkCost))
        {
            l->linkCost = (gint)LinkCost->valuedouble;
            DEBUG_RL_RA ("linkCost: %d", l->linkCost);        
        }      
        
        if (cJSON_IsNumber(LinkDelay))
        {            
            memcpy (&(l->linkDelay), &LinkDelay->valuedouble, sizeof (gdouble));
            DEBUG_RL_RA ("linkDelay: %f", l->linkDelay);        
        }
        
        if (cJSON_IsNumber(LinkAvaiBw))
        {            
            memcpy (&(l->linkAvailBw), &LinkAvaiBw->valuedouble, sizeof (gdouble));
            DEBUG_RL_RA ("linkAvailBw: %f", l->linkAvailBw);        
        }    
    }
    return;
}
 
///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to parse the JSON object array the abstracted WAN topologies handled by the MTP
 * 	
 * 	@param absWanTopoArray
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_absWanTopo (cJSON *absWanTopoArray)
{
	DEBUG_RL_RA ("Parsing the JSON for the Abstracted WAN Topologies");	
	
	for (gint i = 0; i < cJSON_GetArraySize(absWanTopoArray); i++)
	{
		rlTopo->numAbsWanTopo++;
		gint index = i;
		// Get reference for this WAN topology instance
		struct absWanTopo_t *t = &(rlTopo->absWanTop[index]);

		cJSON *item = cJSON_GetArrayItem (absWanTopoArray, i);

		// Get the WimId
		cJSON *WimId = cJSON_GetObjectItem (item, "wimId");
		if (cJSON_IsString(WimId))
		{
			memcpy(t->WimId, WimId->valuestring, strlen (WimId->valuestring));
			DEBUG_RL_RA ("Abs WAN Topo for WimId: %s", t->WimId);        
		}
			
		// Get the abstracted set of nodes within the WAN
		cJSON *nodeWanArray = cJSON_GetObjectItemCaseSensitive (item, "nodes");
		gint numNodesWan = 0;
		numNodesWan = (gint)cJSON_GetArraySize (nodeWanArray); 
		DEBUG_RL_RA ("WAN (WIM: %s) has %d Nodes", t->WimId, numNodesWan);
		for (gint j = 0; j < numNodesWan; j++)
		{
			t->numNodes++;
			gint indexNodes = j;

			cJSON *itemNode = cJSON_GetArrayItem(nodeWanArray, j);
			//Get the nodeId
			cJSON *nodeId = cJSON_GetObjectItem(itemNode, "nodeId");
			if (cJSON_IsString(nodeId))
			{
				DEBUG_RL_RA ("Print the NodeId --- before %s", t->nodes[indexNodes].nodeId);
				memcpy(t->nodes[indexNodes].nodeId, nodeId->valuestring, strlen (nodeId->valuestring));
				DEBUG_RL_RA ("Abs WAN Topo - Adding NodeId: %s", t->nodes[indexNodes].nodeId);        
			}                
		}    
			
		// Get the abstracted set of edges within the absWanTop
		cJSON *edgeWanArray = cJSON_GetObjectItemCaseSensitive (item, "edges");            
		for (gint k = 0; k < cJSON_GetArraySize(edgeWanArray); k++)
		{
			rlTopo->absWanTop[index].numEdges++;
			gint indexEdges = k;

			cJSON *itemEdge = cJSON_GetArrayItem(edgeWanArray, k);

			//Get the aNodeId
			cJSON *aNodeId = cJSON_GetObjectItem(itemEdge, "aNodeId");
			if (cJSON_IsString(aNodeId))
			{
				memcpy(t->edges[indexEdges].aNodeId.nodeId, aNodeId->valuestring, strlen (aNodeId->valuestring));
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge aNodeId: %s", t->edges[indexEdges].aNodeId.nodeId);        
			}

			//Get the zNodeId
			cJSON *zNodeId = cJSON_GetObjectItem(itemEdge, "zNodeId");
			if (cJSON_IsString(zNodeId))
			{
				memcpy(t->edges[indexEdges].zNodeId.nodeId, zNodeId->valuestring, strlen (zNodeId->valuestring));
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge zNodeId: %s", t->edges[indexEdges].zNodeId.nodeId);        
			}

			// Get the aLinkId
			cJSON *aLinkId = cJSON_GetObjectItem(itemEdge, "aLinkId");
			if (cJSON_IsNumber(aLinkId))
			{
				t->edges[indexEdges].aLinkId = (guint32)aLinkId->valuedouble;
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge aLinkId: %u", t->edges[indexEdges].aLinkId);        
			}

			// Get the zLinkId
			cJSON *zLinkId = cJSON_GetObjectItem(itemEdge, "zLinkId");
			if (cJSON_IsNumber(zLinkId))
			{
				t->edges[indexEdges].zLinkId = (guint32)zLinkId->valuedouble;
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge zLinkId: %u", t->edges[indexEdges].zLinkId);        
			}

			// Get QoS parameters: link cost, bw, delay
			cJSON *netwLinkQoS = cJSON_GetObjectItem(itemEdge, "netwLinkQoS");
			// Get the link cost in the netwLinkQoS object
			cJSON *LinkCost = cJSON_GetObjectItem (netwLinkQoS, "linkCostValue");
			// Get the linkDelay
			cJSON *LinkDelay = cJSON_GetObjectItem (netwLinkQoS, "linkDelayValue");
			// Get the link Avail Bw
			cJSON *LinkAvaiBw = cJSON_GetObjectItem (netwLinkQoS, "linkAvailBwValue");

			if (cJSON_IsNumber(LinkCost))
			{
				t->edges[indexEdges].linkCost = (gint)LinkCost->valuedouble;
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge linkCost: %d", t->edges[indexEdges].linkCost);        
			}      

			if (cJSON_IsNumber(LinkDelay))
			{               
				memcpy (&(t->edges[indexEdges].linkDelay), &LinkDelay->valuedouble, sizeof (gdouble));
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge linkDelay: %f", t->edges[indexEdges].linkDelay);        
			}

			if (cJSON_IsNumber(LinkAvaiBw))
			{            
				memcpy (&(t->edges[indexEdges].linkAvailBw), &LinkAvaiBw->valuedouble, sizeof (gdouble));
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge linkAvailBw: %f", t->edges[indexEdges].linkAvailBw);        
			}

			// Get the network Layer for te edge
			cJSON *netwLayer = cJSON_GetObjectItem(itemEdge, "networkLayer");
			if (cJSON_IsString(netwLayer))
			{
				memcpy(t->edges[indexEdges].networkLinkLayer, netwLayer->valuestring, strlen (netwLayer->valuestring));
				DEBUG_RL_RA ("Abs WAN Topo - Adding Edge Network Layer: %s", t->edges[indexEdges].networkLinkLayer);        
			}                
		}            
	}
	return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to parse the array containing the multiple InterNfviPopConnectivity Connections
 * 	
 * 	@param requestListArray
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_array_requestList (cJSON * requestListArray)
{
	DEBUG_RL_RA ("Parsing the JSON Array for the InterNfviPopConnectivity Requests");	
	
	for (gint i = 0; i < cJSON_GetArraySize(requestListArray); i++)
	{
		reqList->numReqList++;
		gint index = i;
		// Get reference for this request intance
		struct interNfviPop_connection_req_t *r = &(reqList->interNfviConnReqs[index]);
		
		cJSON *item = cJSON_GetArrayItem (requestListArray, i);
		
		// Get the request Id, uint32_t
		cJSON *reqIdItem = cJSON_GetObjectItem (item, "requestId");
		if (cJSON_IsNumber(reqIdItem))
		{       
			r->requestId = (guint32)reqIdItem->valuedouble;
			DEBUG_RL_RA ("RequestId: %u", r->requestId);        
		} 
		
		// Get the InterNfviPopConnectivityId
		cJSON *reqConn_item = cJSON_GetObjectItem (item, "interNfviPopConnectivityId");
		if (cJSON_IsString(reqConn_item))
		{
			memcpy(r->interNfviPopConnectivityId, reqConn_item->valuestring, strlen (reqConn_item->valuestring));
			DEBUG_RL_RA ("Requested InterNfviPopConnectitivityId: %s", r->interNfviPopConnectivityId);        
		}

		// Copy the previously selected raId
		memcpy(r->raId, raId, strlen (raId));
		
		// Get the srcPEId
		cJSON *srcPEId = cJSON_GetObjectItemCaseSensitive (item, "srcPEId");   
		if (cJSON_IsString (srcPEId))
		{	
			memcpy(r->srcPEId.nodeId, srcPEId->valuestring, strlen (srcPEId->valuestring));
			DEBUG_RL_RA ("srcPEId: %s", r->srcPEId.nodeId);			
		}
	
		// Get the dstPEId
		cJSON *dstPEId = cJSON_GetObjectItemCaseSensitive (item, "dstPEId");
		if (cJSON_IsString (dstPEId))
		{	
			memcpy(r->dstPEId.nodeId, dstPEId->valuestring, strlen (dstPEId->valuestring));
			DEBUG_RL_RA ("dstPEId: %s", r->dstPEId.nodeId);			
		}	
		
		// Get the maximum number of paths to be computed (kPaths)
		cJSON *kPaths = cJSON_GetObjectItemCaseSensitive (item, "kPaths");
		if (cJSON_IsNumber (kPaths))
		{
			r->kPaths = (guint)kPaths->valuedouble;
			DEBUG_RL_RA ("kPaths: %u", r->kPaths);
		}
		
		// Get the QoS Constraints
		cJSON *qosCons = cJSON_GetObjectItemCaseSensitive (item, "qosCons");
		cJSON *delayConsValue = cJSON_GetObjectItem (qosCons, "delayConsValue");               
		cJSON *bandwidthConsValue = cJSON_GetObjectItem (qosCons, "bandwidthConsValue");

		if (cJSON_IsNumber(bandwidthConsValue))
		{            
			memcpy (&(r->bandwidthCons), &bandwidthConsValue->valuedouble, sizeof (gdouble));
			DEBUG_RL_RA ("Requested Bandwidth Constraint: %f", r->bandwidthCons);        
		}

		if (cJSON_IsNumber(delayConsValue))
		{            
			memcpy (&(r->delayCons), &delayConsValue->valuedouble, sizeof (gdouble));
			DEBUG_RL_RA ("Requested Delay Constraint: %f", r->delayCons);        
		}
	}
	
	return;
}


///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to parse the JSON object/s for the RA request (i.e. src, dst, topology, ...)
 * 	
 * 	@param root
 * 	@param source
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_ra_request (cJSON * root, GIOChannel * source)
{
	DEBUG_RL_RA ("Parsing the RA Request");    
    
    if (rlTopo == NULL)
	{	
	  	DEBUG_RL_RA ("rlTopo does not exist (was not created) ... STOP\n");
	  	exit(-1);
	}
	
	if (reqList == NULL)
	{
		DEBUG_RL_RA ("reqList does not exist (was not created) ... STOP\n");
		exit(-1);       
	} 

	DEBUG_RL_RA ("reqList: %p", reqList);
	DEBUG_RL_RA ("rlTopo: %p", rlTopo);
	DEBUG_RL_RA ("rlTopo->numInterWanLinks: %d", rlTopo->numInterWanLinks);
	DEBUG_RL_RA ("rlTopo->numAbsWanTopo: %d", rlTopo->numAbsWanTopo);	
    
    // Get the RA id
	cJSON *raId_oj = cJSON_GetObjectItemCaseSensitive (root, "raId");
	if (cJSON_IsString (raId_oj))
	{
		memcpy(raId, raId_oj->valuestring, strlen (raId_oj->valuestring));		
		DEBUG_RL_RA ("raId: %s", raId);
	}
	
	// Get the syncPaths
	cJSON *synchPath_obj = cJSON_GetObjectItemCaseSensitive (root, "syncPaths");
	if (cJSON_IsBool (synchPath_obj))
	{
		// Check Synchronization of multiple Paths to attain e.g. global concurrent optimization
		if (cJSON_IsTrue (synchPath_obj))
		{
			syncPath = TRUE;
			syncPath = TRUE;
			DEBUG_RL_RA ("Synchronization of Paths is requested");	
		}
		if (cJSON_IsFalse (synchPath_obj))
		{
			syncPath = TRUE;
			DEBUG_RL_RA ("Synchronization of Paths is NOT required");	
		}
	}
		
	// Get the requested list of interNfviPopConnections
	cJSON * requestListArray = cJSON_GetObjectItemCaseSensitive (root, "requestList");
	parsing_json_array_requestList (requestListArray);	
	
	// Get the interWanLinks
	cJSON *interWanLinksArray = cJSON_GetObjectItemCaseSensitive (root, "interWanLinks");
	if (interWanLinksArray != NULL)
		parsing_json_obj_interWanLinks (interWanLinksArray);
    
    // Get the abstrated WAN topologies
	cJSON *absWanTopoArray = cJSON_GetObjectItemCaseSensitive (root, "absWanTopo");
	parsing_json_obj_absWanTopo (absWanTopoArray);

	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used parse the JSON object/s 
 * 	
 * 	@param data
 *  @param source
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint parsing_json_obj (guchar *data, GIOChannel *source)
{
    cJSON * root = cJSON_Parse((const char *)data);

    char * print = cJSON_Print(root);  
    DEBUG_RL_RA ("Input JSON content: %s", print);

    parsing_json_obj_ra_request (root, source);
	
	// Release the root JSON object variable used for RA
	cJSON_free (root);	

    return 0;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Create new tcp client connected to the MTP PA Server
 * 
 * 	@param channel_client, GIOChannel
 *  @param fd
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct rl_client * rl_ra_server_rapi_client_create (GIOChannel * channel_client, gint fd)
{
	/** check values */
	g_assert(channel_client != NULL); 

	struct rl_client * client = g_malloc0 (sizeof (struct rl_client));
	if (client == NULL )
	{
		DEBUG_RL_RA ("Malloc for the client failed");
		exit(-1);
	}  

	/** Make client input/output buffer. */
	client->channel = channel_client;	
	client->obuf = rl_ra_server_stream_new (MAXLENGTH);
	client->ibuf = rl_ra_server_stream_new (MAXLENGTH);
	client->fd = fd;

	// Clients connected to the MTP PA SERVER
	CLIENT_ID++;
	client->type = CLIENT_ID;

	DEBUG_RL_RA ("Client Id: %u is created (%p)", client->type, client);
	DEBUG_RL_RA ("Client ibuf: %p || obuf: %p", client->ibuf, client->obuf);

	// Add the tcp client to the list
	rapi_tcp_client_list = g_list_append (rapi_tcp_client_list, client);
	DEBUG_RL_RA ("Number of Connected TCP Clients to MTP PA Server: %d", g_list_length (rapi_tcp_client_list));     

	return client;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Close the tcp client, removing from the rapi_tcp_client_list
 * 
 * 	@param client
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_client_close (struct rl_client * client)
{
	DEBUG_RL_RA ("Closing the client (Id: %d) %p", client->type, client);
	
	if (client->ibuf != NULL)
	{
		 rl_ra_server_stream_free (client->ibuf);
		 client->ibuf = NULL;
	}
	if (client->obuf != NULL)
	{
		 rl_ra_server_stream_free (client->obuf);
		 client->obuf = NULL;
	}
	// Remove from the list
	rapi_tcp_client_list = g_list_remove (rapi_tcp_client_list, client);
	DEBUG_RL_RA ("TCP Client List: %d", g_list_length(rapi_tcp_client_list));
	 
	g_free (client);
	client = NULL;	 
	DEBUG_RL_RA ("client has been removed ...");
	 
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Close operations over the passed tcp channel
 * 
 * 	@param source
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_close_operations (GIOChannel * source)
{
	gint fd = g_io_channel_unix_get_fd (source);
	
	DEBUG_RL_RA ("Stop all the operations over the fd: %d", fd);	
	g_io_channel_flush(source, NULL);
	GError *error = NULL;    
	g_io_channel_shutdown (source, TRUE, &error);
	if(error)
	{
		DEBUG_RL_RA ("An error occurred ...");
	}
	g_io_channel_unref (source);
	return;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Removce the client and close operations over the passed tcp channel
 * 
 * 	@param client
 *  @param source
 *  @param fd
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_stop (struct rl_client * client, GIOChannel * source, gint fd)
{
	// remove client
	rapi_client_close (client);
	// Stop operations over that channel
	rapi_close_operations (source);
	close (fd);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used read the different lines ending up in \r\n
 * 	
 * 	@param s
 * 	@param buf
 * 	@param size
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint rapi_get_line (GIOChannel *channel, gchar *buf, gint size)
{
    gint i = 0;
    DEBUG_RL_RA ("\n");
    DEBUG_RL_RA ("----- Read REST API Line(\r\n) ------");
    gint n = 0;
    guchar c = '\0'; // END OF FILE    
    gboolean cr = FALSE;
    while (i < size - 1)
    {
		n = read_channel (channel, &c, 1);		
		if (n == -1)
		{
			DEBUG_RL_RA ("Close the channel and eliminate the client");
			return -1;			
		}	
		if (n > 0)
		{
			//DEBUG_RL_RA ("%c", c);
			buf[i] = c;
			i++;	
			if (c == '\r')
			{
				cr = TRUE;	      
			}	  
			if ((c == '\n') && (cr == TRUE))
			{	   
				break;
			}	        
		} 
		else
		{
			c = '\n';
			buf[i] = c;
			i++;
			break;
		}
    }
    buf[i] = '\0';    
    DEBUG_RL_RA ("Line (size: %d) buf: %s", i, buf);
    return i;
}  

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used read the HTTP method
 * 	
 * 	@param buf
 * 	@param j
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
guint rl_ra_server_get_method (gchar *buf, gint *j)
{
	guint RestApiMethod = 0;
	gchar method[255];
	gint i = 0;	
	while (!ISspace(buf[*j]) && (i < sizeof(method) - 1))
	{
		method[i] = buf[*j];
		i++; 
		*j = *j + 1;
	}
	method[i] = '\0';
	DEBUG_RL_RA ("REST API METHOD: %s", method);	
	
	// Check that the methods are GET of POST
	if (strcasecmp((const char *)method, "GET") && strcasecmp((const char *)method, "POST") && 
		strcasecmp ((const char *)method, "HTTP/1.1"))
	{
		DEBUG_RL_RA ("The method: %s is not currently supported ...", method);
		return RestApiMethod;	
	}
	// Method selector
	if (strncmp ((const char*)method, "GET", 3) == 0)
	{
		RestApiMethod = REST_API_METHOD_GET;		
	}
	else if (strncmp ((const char*)method, "POST", 4) == 0)
	{
		RestApiMethod = REST_API_METHOD_POST;
	}	
	else if (strncmp ((const char *)method, "HTTP/1.1", 8) == 0)
	{
		RestApiMethod = REST_API_METHOD_HTTP;
	}	
	
	return RestApiMethod;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used read the url
 * 	
 * 	@param buf
 * 	@param j
 *  @param url
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint get_url (gchar *buf, gint *j, gchar *url)
{
	gint i;	
	
	// Skip space char
	while (ISspace(buf[*j]) && (*j < strlen(buf)))
	{
		*j = *j + 1;
	}
	
	//DEBUG_RL_RA ("buf[%d]: %c", *j, buf[*j]);
	int result = isspace (buf[*j]);	
	*buf = *buf + *j;
	gint numChar = 0;
	gint initChar = *j;
	result = 0;
	while (result == 0)
	{
		*j = *j + 1;
		result = isspace (buf[*j]);
		numChar++;
	}
	//DEBUG_RL_RA ("numChar: %d", numChar);
	memcpy (url, buf + initChar, numChar);
	DEBUG_RL_RA ("url: %s", url);
	i = numChar;
	return i;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used read the version
 * 	
 * 	@param buf
 * 	@param j
 *  @param version
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint get_version (gchar *buf, gint *j, gchar *version)
{
	gint i;	
	// Skip space char
	while (ISspace(buf[*j]) && (*j < strlen(buf)))
	{
		*j = *j + 1;
	}
	
	//DEBUG_RL_RA ("buf[%d]: %c", *j, buf[*j]);
	int result = isspace (buf[*j]);	
	*buf = *buf + *j;
	gint numChar = 0;
	gint initChar = *j;
	result = 0;
	while (result == 0)
	{
		*j = *j + 1;
		result = isspace (buf[*j]);
		numChar++;
	}
	//DEBUG_RL_RA ("numChar: %d", numChar);
	memcpy (version, buf + initChar, numChar);
	DEBUG_RL_RA ("version: %s", version);
	i = numChar;
	return i;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to trigger the route computation for all the interNfviPop Connection List and retrieve the result
 * 	
 * 	@param compRouteList
 * 	@param raId
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint triggering_routeComp (struct compRouteOutputList_t *compRouteList, gchar *raId)
{
	gint httpCode = HTTP_RETURN_CODE_OK;
	// Select the algorithm according to the requested RAId
		
	if (strncmp ((const char*)raId, "CSA", 3) == 0)
		httpCode = ra_CSA_alg (compRouteList);
	else if (strncmp ((const char*)raId, "InA", 3) == 0)
		httpCode = ra_InA_alg (compRouteList);
	
	return httpCode;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to process the REST API commands
 * 	
 * 	@param source
 * 	@param cond
 * 	@param data
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean rapi_activity (GIOChannel *source, GIOCondition cond, gpointer data)
{  
	/** some checks */
	g_assert(source != NULL);
	g_assert(data != NULL);	
	
	gchar buf[1024];
	//guchar method[255];
	
	gchar interNfviPopConnectivityId_tmp[255];
	gchar version[255];
	gchar http_result[255];
	gint body_length = 0;
	guchar interNfviPopConnectivityId[255];

	struct rl_client *rl_client = (struct rl_client *)(data);

	DEBUG_RL_RA (" ************************************************************************** ");    
	DEBUG_RL_RA ("                      REST API ACTIVITY Triggered ");
	DEBUG_RL_RA (" ************************************************************************** ");   

	gint fd = g_io_channel_unix_get_fd (source);
	DEBUG_RL_RA ("File Descriptor (fd): %d, cond: %d", fd, cond);

	if (cond != G_IO_IN)
	{
		DEBUG_RL_RA ("Something happening with the channel and fd ... (cond: %d)", cond);
		rapi_stop (rl_client, source, fd);		
		return FALSE;
	}	
	/** Clear input buffer. */
	rl_ra_server_stream_reset (rl_client->ibuf);

	// get line
	gint nbytes = rapi_get_line (source, buf, sizeof (buf));
	if (nbytes == -1)
	{
		DEBUG_RL_RA ("nbytes -1 ... then close the fd and eliminate associated client");						
		rapi_stop (rl_client, source, fd);
		return FALSE;						
	}		
	
	if ((buf[0] == '\n') && (nbytes  == 1))
	{
		DEBUG_RL_RA (" -- buf[0] = newline --");
		rapi_stop (rl_client, source, fd);
		return FALSE;
	}
	
	gint i = 0, j = 0;
	// Get the REST Method
	guint RestApiMethod = rl_ra_server_get_method (buf, &j);
	if (RestApiMethod == 0)
	{
		DEBUG_RL_RA ("The method is NOT supported ...");
		rapi_unimplemented (source);
		rapi_stop (rl_client, source, fd);
		return FALSE;
	}

	// get the REST url
	gchar url[255];
	i = get_url (buf, &j, url);	
	url[i] = '\0';	

	// for method POST and GET, chech url "/compRoute/"
	if (((RestApiMethod == REST_API_METHOD_POST) || (RestApiMethod == REST_API_METHOD_GET))
		&& (strncmp((const char*) url, "/compRoute/", 10) != 0))
	{
		DEBUG_RL_RA ("UnKnown url(%s)!!!", url);
		rapi_stop (rl_client, source, fd);
		exit (-1);
	}	 

	// Process the HTTP/1.1 response
	if (RestApiMethod == REST_API_METHOD_HTTP)
	{
		int http_return = atoi ((const char *) url);
		DEBUG_RL_RA ("HTTP/1.1. %d", http_return);
	
		// TBD - DO SOMETHING WITH THE RESTURNED VALUE
	
		i = 0;		
		while (ISspace(buf[j]) && (j < sizeof(buf)))
			j++;
		while (!ISspace(buf[j]) && (i < sizeof(version) - 1) && (j < sizeof(buf)))
		{
			http_result[i] = buf[j];
			i++; j++;
		}	
		http_result[i] = '\0';
		DEBUG_RL_RA ("HTTP/1.1. %d %s", http_return, http_result);
	}	
	
	// get the version	
	i = get_version (buf, &j, version);
	version[i] = '\0';		

	// Assume HTTP/1.1, then there is Host Header
	memset(buf, '\0', sizeof(buf));        
	nbytes = rapi_get_line (source, buf, sizeof (buf));
	if (nbytes == -1)
	{
		DEBUG_RL_RA ("nbytes -1 ... then close the fd and eliminate associated client");			
		rapi_stop (rl_client, source, fd);		
		return FALSE;					
	}
	DEBUG_RL_RA ("Header: %s", buf);	
	//DEBUG_RL_RA ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	// Headers --- The Header Fields ends up with a void line (i.e., \r\n)
	while ((nbytes > 0) && (strcmp ("\r\n", (const char *)buf) != 0))
	{	
		/* read & discard headers */
		memset(buf, '\0', sizeof(buf));  
		nbytes = rapi_get_line (source, buf, sizeof (buf));
		if (nbytes == -1)
		{
			DEBUG_RL_RA ("nbytes -1 ... then close the fd and eliminate associated client");	
			rapi_stop (rl_client, source, fd);		
			return FALSE;
		}
		DEBUG_RL_RA ("Header: %s", buf);	  
		if (strncmp ((const char *)buf, "Content-Length:", 15) == 0)
		{
			DEBUG_RL_RA ("Header Content-Length Found");
			gchar str[20];
	  
			gint i = 15, k = 0;  // "Content-Length:" We skip the first 16 characters to directly retrieve the length in bytes of the Body of Request
			gchar contentLength[255];
			memset (contentLength, '\0', sizeof (contentLength));			
			while (buf[i] != '\r')
			{
				//DEBUG_RL_RA ("%c", buf[i]);
				str[k] = buf[i];
				k++, i++;
			}
			str[k] = '\0';			
			j = 0, i = 0;
			while (ISspace(str[j]) && (j < strlen(str)))
			{
				j++;
			}
			while (j < strlen(str))
			{
				contentLength[i] = str[j];
				i++; j++;
			}
			contentLength[i] = '\0';			
			body_length = atoi (contentLength);
			DEBUG_RL_RA ("Body length: %d (%s) in Bytes", body_length, contentLength);	      
		}	  
	}	
	if (body_length == 0)
	{
		DEBUG_RL_RA ("--- NO REST API Body length (length = %d) ---", body_length);
		return TRUE;
	}       
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Processing Body of the Request
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	DEBUG_RL_RA ("Read the contents of the Body");
	nbytes = read_channel (source, (guchar *)(rl_client->ibuf->data + rl_client->ibuf->putp), body_length);
	DEBUG_RL_RA ("nbytes: %d, rl_client->ibuf->data: %s", nbytes, rl_client->ibuf->data);
	if ((nbytes < 0) && (body_length > 0))
	{
		DEBUG_RL_RA ("nbytes: %d // body_length: %d", nbytes, body_length);
		exit (-1);
	}
	
	rl_client->ibuf->putp += nbytes;
	rl_client->ibuf->endp += nbytes;		

	// -----------  Parsing the contents on the RA to be executed -----------------------
	// Build rlTopo placeholder	
	rlTopo = create_rl_topology ();
	
	// Create the interNfviPopConnectivity request List placeholder
	reqList = create_req_list ();
	
	// Process the json contents and store relevant information at both the rlTopo and interNfviPopConnectionReq		
	gint ret = parsing_json_obj (rl_client->ibuf->data, source);	
	if (ret == -1)
	{
		DEBUG_RL_RA ("Something wrong with the JSON Objects ... ");		
		rapi_stop (rl_client, source, fd);	
		return FALSE;
	}	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	// Trigger the required computation	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	struct compRouteOutputList_t *compRouteOutputList = create_route_list ();
	gint httpCode = triggering_routeComp (compRouteOutputList, raId);	

	// Send the response to the REST  API Client
	if (httpCode != HTTP_RETURN_CODE_OK)
	{            
		DEBUG_RL_RA ("HTTP CODE: %d -- NO OK", httpCode);
		rapi_response (source, httpCode);
	}
	else
	{
		DEBUG_RL_RA ("HTTP CODE: %d -- OK", httpCode);
		rapi_response_ok (source, httpCode, compRouteOutputList);            
	}
	
	// Release the variables		
	g_free (rlTopo);
	g_free (reqList);
	g_free (compRouteOutputList);
   
	return TRUE;  
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Function used to accept a new connection and add the client to list of clients
 * 
 * 	@param source, GIOChannel
 * 	@param cond, GIOCondition
 * 	@param data, gpointer
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean rest_api_tcp_new_connection (GIOChannel *source, GIOCondition cond, gpointer data)
{
	socklen_t client;	
	struct sockaddr_in client_addr;
	
	DEBUG_RL_RA (" \n");
	DEBUG_RL_RA (" ****** New Incoming TCP Connection (RESTful API) ******");

	/** get size of client_addre structure */
	client = sizeof(client_addr);
	
	if ((cond == G_IO_HUP) || (cond == G_IO_ERR) || (G_IO_NVAL))
	{
		DEBUG_RL_RA ("Something happening with the channel and fd ... cond: %d", cond);
		
		// Find the associated RL client (by the fd) and remove from active RL client list. 
		// Stop all the operations over that RL client bound channel
		struct rl_client *rl_client = NULL;
		gint fd = g_io_channel_unix_get_fd (source);
		GList *found = g_list_find_custom (rapi_tcp_client_list, &fd, find_rl_client_by_fd);
		if (found != NULL)
		{
			rl_client = (struct rl_client *)(found->data);			
			// remove RL client
			rapi_client_close (rl_client);			
			// Stop operations over that channel
			rapi_close_operations (source);
			close (fd);
			return FALSE;
		}		
	}	

	if (cond == G_IO_IN)
	{
		gint new;
		if ((new = accept (g_io_channel_unix_get_fd (source), (struct sockaddr *)&client_addr, &client)) < 0)
		{
			DEBUG_RL_RA ("Unable to accept new connection");
			return FALSE;
		}

		/** new channel */
		GIOChannel * new_channel = g_io_channel_unix_new (new);		
		DEBUG_RL_RA ("TCP Connection (REST API) is UP; (socket: %d)", new);

		/** create rl_ra_server client */		
		struct rl_client *new_client = rl_ra_server_rapi_client_create (new_channel, new);	
		DEBUG_RL_RA ("NEW RL client created (%p) Id: %d with fd: %d", new_client, new_client->type, new);

		/** 
		* force binary encoding with NULL
		*/
		GError *error = NULL;
		if ( g_io_channel_set_encoding (new_channel, NULL, &error) != G_IO_STATUS_NORMAL)
		{		
			DEBUG_RL_RA ("Error: %s", error->message);
			exit (-1);
		}

		g_io_channel_set_close_on_unref (new_channel, TRUE);

		/** On unbuffered channels, it is safe to mix read
			*  and write calls from the new and old APIs.
			*/
		g_io_channel_set_buffered (new_channel, FALSE);

		if (g_io_channel_set_flags (new_channel, G_IO_FLAG_NONBLOCK, &error) != G_IO_STATUS_NORMAL )
		{
			DEBUG_RL_RA ("Error: %s", error->message);
			exit (-1);
		}	

		/**
		* Adds the new channel into the main event loop.
		* the watch_id is used for removing permanent the
		* the connection from the list of monitor connections
		* of the MainLoop.
		*/
		
		DEBUG_RL_RA ("ibuf: %p // obud: %p", new_client->ibuf, new_client->obuf);		
		g_io_add_watch (new_channel, G_IO_IN, rapi_activity, new_client);
    }	
	return TRUE;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief enabling the reuse of the addr for the Server TCP
 * 	
 * 	@param sock gint
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_tcp_enable_reuseaddr (gint sock)
{
	gint tmp = 1;    

	if (sock < 0)
	{
		DEBUG_RL_RA (" socket: %d !!!",sock);
		exit (-1);
	}

	if (setsockopt (sock, SOL_SOCKET, SO_REUSEADDR, (gchar *)&tmp, sizeof (tmp)) == -1)
	{
		DEBUG_RL_RA ("bad setsockopt ...");
		exit (-1);
	}
	return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Configuring options of the file descriptor
 * 	
 * 	@param sock gint
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_setnonblocking(gint sock)
{
	gint opts;    

	 opts = fcntl (sock,F_GETFL);
	 if (opts < 0)
	{
		DEBUG_RL_RA ("fcntl(F_GETFL) error");
		exit(-1);
	}

	opts = (opts | O_NONBLOCK);

	if (fcntl(sock,F_SETFL,opts) < 0)
	{	
		DEBUG_RL_RA ("fcntl(F_SETFL) error");
		exit(-1);
	}
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server_restapi.c
 * 	@brief Main function for the creating / maintaining TCP session for the REST API
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rest_api_serve_init (gint port)
{     
      
	DEBUG_RL_RA ("REST API PORT (listening): %d", port);     
	gint                s;
	struct sockaddr_in  addr;
	GIOChannel          *channel;

	/** File Descriptor for our "listening" socket */
	s = socket (AF_INET, SOCK_STREAM, 0);
	if (s == -1)
	{
		DEBUG_RL_RA ("Error creating the socket");
		exit (-1);
	}
	DEBUG_RL_RA ("Trigger REST API Connection (FD: %d)", s);

	/** Set socket to non-blocking */
	//rapi_setnonblocking (s);

	/** So that we can re-bind to it without TIME_WAIT problems */
	rapi_tcp_enable_reuseaddr (s);

	memset (&addr, 0, sizeof (addr));
	addr.sin_family       = AF_INET;
	addr.sin_port         = htons ((u_short)port);
	addr.sin_addr.s_addr  = INADDR_ANY;      

	/** lets associate to an address*/
	if (bind (s, (struct sockaddr *)&addr, sizeof (addr)) == -1)
	{
		close (s);
		DEBUG_RL_RA ("socket bind failed");
		exit (-1);
	}
	DEBUG_RL_RA ("Bind to Fd: %d Done", s);

	/** Set up queue for incoming connections */
	if (listen (s, 10) == -1)
	{
		close (s);
		DEBUG_RL_RA ("Socket listen failed");
		exit (-1);
	}
	
	//DEBUG_RL_RA ("Listen (up to 10) to Fd: %d Done", s);

	/** Create new channel to handle the socket */
	channel = g_io_channel_unix_new (s);
	gsize buffersize = g_io_channel_get_buffer_size (channel);
	DEBUG_RL_RA ("GIOChannel with Buffer Size: %d", buffersize);

	gsize newBufferSize = MAX_GIO_CHANNEL_BUFFER_SIZE;
	g_io_channel_set_buffer_size (channel, newBufferSize);

	buffersize = g_io_channel_get_buffer_size (channel);
	DEBUG_RL_RA ("GIOChannel with Buffer Size: %d", buffersize);

	DEBUG_RL_RA ("Channel associated to fd: %d is created", s);

	/**
	* Adds the new channel into the main event loop.
	* According to a condition G_IO_IN, the function
	* rest_api_tcp_new_connection handles this event.
	*/
	g_io_add_watch (channel, G_IO_IN | G_IO_ERR | G_IO_HUP | G_IO_NVAL, rest_api_tcp_new_connection, NULL);

	return;     
}  
