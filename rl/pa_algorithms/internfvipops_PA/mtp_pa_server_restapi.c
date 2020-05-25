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

#include <fcntl.h>

#include "mtp_pa_server_stream.h"
#include "mtp_pa_server_comp.h"
#include "mtp_pa_server_cjson.h"
#include "mtp_pa_server_pa1000.h"
#include "mtp_pa_server_restapi.h"

#define ISspace(x) isspace((int)(x))

#define SERVER_STRING "Server: 5GT MTP PA-RA/0.1.0\r\n"

// List of Clients connected to the MTP PA Server
GList *rapi_tcp_client_list = NULL;

// Id for CLient HTTP (REST API) Connection
guint CLIENT_ID = 0;
guint32 paId_req = 0;

struct interNfviPop_connection_req_t *interNfviPopConnectionReq;
struct mtp_topology_t *mtpTopo;

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to find a mtp_client by its fd in the rapi_tcp_client_list
 * 	
 * 	@param data
 *  @param userdata
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint find_mtp_client_by_fd (gconstpointer data, gconstpointer userdata)
{
	 /** check values */
     g_assert(data != NULL);
     g_assert(userdata != NULL);
	 
	 struct mtp_client *mtp_client = (struct mtp_client *)data;
     gint fd = *(gint *)userdata; 
     
     if (mtp_client->fd == fd)
		 return 0;        
    return -1;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to send a message to the corresponding channel
 * 	
 * 	@param source
 *  @param buf
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint mtp_pa_server_rapi_send_message (GIOChannel *channel, char *buf)
{
	gsize nbytes, buffersize;

	DEBUG_MTP_PA_SERVER ("Msg prepared to be sent REST API RESPONSE ");
    gint len = strlen ((const char*) buf);
    DEBUG_MTP_PA_SERVER ("Targeted Length of the buffer: %d", len);
       

    buffersize = g_io_channel_get_buffer_size (channel);
    DEBUG_MTP_PA_SERVER ("GIOChannel with Buffer Size: %d", buffersize);
    
    gsize newBufferSize = MAX_GIO_CHANNEL_BUFFER_SIZE;
    g_io_channel_set_buffer_size (channel, newBufferSize);
    
    buffersize = g_io_channel_get_buffer_size (channel);
    DEBUG_MTP_PA_SERVER ("GIOChannel with Buffer Size: %d", buffersize);
    
	/** Send message.  */
    GError *error = NULL;
    
    char *ptr = buf;
    gint nleft = strlen ((const char *)buf);
    
    while (nleft > 0)
    {
        g_io_channel_write_chars (channel, (void *)ptr, nleft, &nbytes, &error);
        if (error)
        {
            DEBUG_MTP_PA_SERVER ("Error sending the message to TCP Client");
            return (-1);
        }
        
        DEBUG_MTP_PA_SERVER ("Sent %d bytes", nbytes);        
        nleft = nleft - nbytes;
        DEBUG_MTP_PA_SERVER ("Remaining to be sent %d", nleft);
        ptr += nbytes;        
    }  

	return 0;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to return when something goes wrong when processing the REST API Command
 * 	
 * 	@param source
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_unimplemented (GIOChannel *source)
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

      ret = mtp_pa_server_rapi_send_message (source, buf);
      //memset( buf, '\0', sizeof( buf )); 
      g_free (buf);

      (void)ret;

  return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to put in the buffer the date according to RFC 1123
 * 	
 * 	@param date
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_add_date_header (char *date)
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
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function that checks if an edge within the computed route is an interWanLink
 * 	
 * 	@param routeElement
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint found_interWanLink_topology (struct routeElement_t routeElement)
{
	gint ret = -1;
	gint i = 0;
	
	for (i = 0; i < mtpTopo->numInterWanLinks; i++)
	{
		// Check if routeElement (aNodeId, zNodeId, aLinkId and zLinkId) matches with one of the current interWanLinks
		if ((memcmp (routeElement.aNodeId.nodeId, mtpTopo->interWanLinks[i].aPEId.nodeId, sizeof (mtpTopo->interWanLinks[i].aPEId.nodeId)) == 0) &&
			(memcmp (routeElement.zNodeId.nodeId, mtpTopo->interWanLinks[i].zPEId.nodeId, sizeof (mtpTopo->interWanLinks[i].zPEId.nodeId)) == 0) &&
			(routeElement.aLinkId == mtpTopo->interWanLinks[i].aLinkId) && (routeElement.zLinkId == mtpTopo->interWanLinks[i].zLinkId))
		{
			DEBUG_MTP_PA_SERVER ("routeElement [aNodeId: %s (%u)---> zNodeId: %s (%u)] is an interWanLink", routeElement.aNodeId.nodeId, routeElement.aLinkId,			
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
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function that allows retrieving all the interWanLinks over the computed path returned by the algorithm
 * 	
 * 	@param edges
 *		@param numEdges
 *		@param path
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean retrieve_interWanLinks_from_comRouteOutput (struct interWanLink_t *edges, gint *numEdges, struct compRouteOutput_t *path)
{
	gboolean ret = FALSE;
		
 	gint i = 0;
 	for (i = 0; i < path->numRouteElements; i++)
 	{
 		// Check whether current link is on the mtpTopo->interWanLinks
 		gint index = -1;
 		index = found_interWanLink_topology (path->routeElement[i]);
 		if (index > -1)
 		{
 			DEBUG_MTP_PA_SERVER ("interWanLink is found at iter i: %d from mtpTopo", index);
 			// Add to edges containing interWanLinks
 			memcpy (edges[*numEdges].aWimId, mtpTopo->interWanLinks[index].aWimId, sizeof (mtpTopo->interWanLinks[index].aWimId));
 			memcpy (edges[*numEdges].zWimId, mtpTopo->interWanLinks[index].zWimId, sizeof (mtpTopo->interWanLinks[index].zWimId));
 			memcpy (edges[*numEdges].aPEId.nodeId, mtpTopo->interWanLinks[index].aPEId.nodeId, sizeof (mtpTopo->interWanLinks[index].aPEId.nodeId));
 			memcpy (edges[*numEdges].zPEId.nodeId, mtpTopo->interWanLinks[index].zPEId.nodeId, sizeof (mtpTopo->interWanLinks[index].zPEId.nodeId));
 			edges[*numEdges].aLinkId = mtpTopo->interWanLinks[index].aLinkId;
 			edges[*numEdges].zLinkId = mtpTopo->interWanLinks[index].zLinkId; 			
 			DEBUG_MTP_PA_SERVER ("interWanLink %s (aWimId: %s)--> %s (zWimId: %s) -- Added", edges[*numEdges].aPEId.nodeId, edges[*numEdges].aWimId,
 																														edges[*numEdges].zPEId.nodeId, edges[*numEdges].zWimId);
		 	*numEdges = *numEdges + 1;
		 	DEBUG_MTP_PA_SERVER ("numInterWanLinks: %d", *numEdges);		
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
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Find out the WAN (Wim Id) which contains a specific edge
 * 	
 * 	@param routeElement
 *
 *		@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *		@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint found_edge_wan (struct routeElement_t routeElement)
{
	gint ret = -1, i = 0;	
	gint j = 0;
	for (i = 0; i < mtpTopo->numAbsWanTopo; i++)
	{
		DEBUG_MTP_PA_SERVER ("Exploring WimId: %s", mtpTopo->absWanTop[i].WimId);
		
		for (j = 0; j < mtpTopo->absWanTop[i].numEdges; j++)
		{
			if ((memcmp (routeElement.aNodeId.nodeId, mtpTopo->absWanTop[i].edges[j].aNodeId.nodeId, sizeof (mtpTopo->absWanTop[i].edges[j].aNodeId.nodeId)) == 0) &&
			(memcmp (routeElement.zNodeId.nodeId, mtpTopo->absWanTop[i].edges[j].zNodeId.nodeId, sizeof (mtpTopo->absWanTop[i].edges[j].zNodeId.nodeId)) == 0) &&
			(routeElement.aLinkId == mtpTopo->absWanTop[i].edges[j].aLinkId) && (routeElement.zLinkId == mtpTopo->absWanTop[i].edges[j].zLinkId))
			{	
				DEBUG_MTP_PA_SERVER ("routeElement [aNodeId: %s (%u)---> zNodeId: %s (%u)] is within WAN (wimId: %s)", routeElement.aNodeId.nodeId, routeElement.aLinkId,			
																																			routeElement.zNodeId.nodeId, routeElement.zLinkId,
																																			mtpTopo->absWanTop[i].WimId);
				ret = i;			
				return ret;		
			}	
		}
	}
	return ret;
}


////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to partition the computed path (path) into specific segments for each traversed WAN
 * 	
 * 	@param wanSet
 *  	@param numWans
 *  	@param path
 *
 *		@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *		@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void retrieve_compRoute_segments_per_WAN (struct wansTopo_t *wanSet, struct compRouteOutput_t *path)
{
	gint i = 0;	
	gint indexWan = -1;
	for (i = 0; i < path->numRouteElements; i++)
 	{
 		// Find the WAN (within mtpTopo->absWanTopo) associated to every explored edge/link forming the computed path 		 
 		indexWan = found_edge_wan (path->routeElement[i]);
		DEBUG_MTP_PA_SERVER ("indexWan: %d", indexWan);
		
 		if (indexWan > -1)
 		{				
 			DEBUG_MTP_PA_SERVER ("Looked at Edge (%s ---> %s) within WAN (WimId: %s)", path->routeElement[i].aNodeId.nodeId,
 																						path->routeElement[i].zNodeId.nodeId, 
																						mtpTopo->absWanTop[indexWan].WimId);
 			if (wanSet->numAbsWanTopo == 0)
 			{
				memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo].WimId, mtpTopo->absWanTop[indexWan].WimId, sizeof (mtpTopo->absWanTop[indexWan].WimId));
				wanSet->absWanTop[wanSet->numAbsWanTopo].numEdges = 0; 				
 				wanSet->numAbsWanTopo = wanSet->numAbsWanTopo + 1;
 				DEBUG_MTP_PA_SERVER ("WIMID: %s is added with numEdges: %d", wanSet->absWanTop[wanSet->numAbsWanTopo].WimId, wanSet->absWanTop[wanSet->numAbsWanTopo].numEdges);
 			}
 			else 
 			{
				// Check if the wimId is already within the wanSet; if not add to the WanSet
				gint k = 0;
				gboolean found = FALSE;
				for (k = 0; k < wanSet->numAbsWanTopo; k++)
				{
					if (memcmp (wanSet->absWanTop[k].WimId, mtpTopo->absWanTop[indexWan].WimId, sizeof (mtpTopo->absWanTop[indexWan].WimId)) == 0)
					{
							DEBUG_MTP_PA_SERVER ("wanSet[k].WimId: %s not added so far ... added as traversed domain", wanSet->absWanTop[k].WimId);
							found = TRUE;
							break;				
					}				
				}
				if (found == FALSE)
				{
					memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo].WimId, mtpTopo->absWanTop[indexWan].WimId, sizeof (mtpTopo->absWanTop[indexWan].WimId));
					wanSet->absWanTop[wanSet->numAbsWanTopo].numEdges = 0;					
					wanSet->numAbsWanTopo = wanSet->numAbsWanTopo + 1;				
				} 				
			}	
																													
 			// Add to the edge on the WAN set within the specific WAN array
 			gint indexWanEdges = wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges;
			
 			memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].aNodeId.nodeId, path->routeElement[i].aNodeId.nodeId, sizeof (path->routeElement[i].aNodeId.nodeId));
 			memcpy (wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].zNodeId.nodeId, path->routeElement[i].zNodeId.nodeId, sizeof (path->routeElement[i].zNodeId.nodeId));
			wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].aLinkId = path->routeElement[i].aLinkId;
			wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].zLinkId = path->routeElement[i].zLinkId; 					
 			DEBUG_MTP_PA_SERVER ("Edge [within wimId (%s)] %s (%u) --> %s (%u)-- Added", wanSet->absWanTop[wanSet->numAbsWanTopo -1].WimId, 
 																						wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].aNodeId.nodeId,
																						wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].aLinkId,
 																						wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].zNodeId.nodeId,
																						wanSet->absWanTop[wanSet->numAbsWanTopo -1].edges[indexWanEdges].zLinkId);
		 	wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges++;
		 	DEBUG_MTP_PA_SERVER ("Num Edges within wimId: %s: %d", wanSet->absWanTop[wanSet->numAbsWanTopo -1].WimId, wanSet->absWanTop[wanSet->numAbsWanTopo -1].numEdges);
 		}  
 		else 
 		{
 			DEBUG_MTP_PA_SERVER ("Explored link does not belong to a WAN");
 			continue;
 		} 					
 	}
 	
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Compose the JSON Body of the succesfully interNfviPop connection
 * 	
 * 	@param body
 *  @param length
 *  @param compRouteOutput
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_response_json_contents (char *body, gint *length, struct compRouteOutput_t *compRouteOutput)
{
    char *buftmp;    
    cJSON *root;
    
	 DEBUG_MTP_PA_SERVER ("Creating the JSON body of the response");
    
    // Add Object related to the compRouteOutput
    root = cJSON_CreateObject();    
    cJSON *compRouteOutputObject;
	
	// Add the interNfviPopConnectivityId
	cJSON_AddItemToObject (root, "compRouteOutput", compRouteOutputObject = cJSON_CreateObject());
	cJSON_AddItemToObject (compRouteOutputObject, "interNfviConnectivityId", cJSON_CreateString (interNfviPopConnectionReq->interNfviPopConnectivityId));
    
    // Add the requested bandwidth
    cJSON_AddItemToObject (compRouteOutputObject, "reqBw", cJSON_CreateNumber (compRouteOutput->reqBw));
    
    // Add interWanLinks (if any in the compRouteOutput)
    gboolean interWanLinks = FALSE;
    // Array for containing computed InterWanLinks to be extracted from the compRouteOutput
    struct interWanLink_t compInterWanLink[MAX_INTER_WAN_LINK];	 
	 gint numCompInterWanLinks = 0;

	 interWanLinks = retrieve_interWanLinks_from_comRouteOutput (compInterWanLink, &numCompInterWanLinks, compRouteOutput);
	 
	 if (interWanLinks == TRUE)
	 {
		DEBUG_MTP_PA_SERVER ("InterWanLinks (# %d) being added to the JSON content of the Output", numCompInterWanLinks);
		// add specific JSON contents describing the interWanLinks
		cJSON *interWanLinksArray;
		interWanLinksArray =  cJSON_CreateArray();
		cJSON_AddItemToObject(compRouteOutputObject, "interWanLinks", interWanLinksArray);
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
  
  	DEBUG_MTP_PA_SERVER ("Add path segment on per WAN basis");
	// Add specific path segments for each traversed WAN 
	struct wansTopo_t *compRouteWan = NULL;	
	compRouteWan = g_malloc0 (sizeof (struct wansTopo_t));
	if (compRouteWan == NULL)
	{
		DEBUG_MTP_PA_SERVER ("memory allocation problem");
		exit (-1);	
	}
	  
	//struct absWanTopo_t compRouteWan[MAX_WAN_TOPO];
	//gint numCompWan = 0;

	// Gather for each specific traversed WAN the set of links within comRouteOutput	
	retrieve_compRoute_segments_per_WAN (compRouteWan, compRouteOutput);
	

    // Add specific JSON contents of the computed route edges on per Domain (wimId) basis
	cJSON *wanPathsArray;
	wanPathsArray =  cJSON_CreateArray();
	cJSON_AddItemToObject(compRouteOutputObject, "wanPaths", wanPathsArray);
	
	gint k = 0;
	for (k = 0; k < compRouteWan->numAbsWanTopo; k++)
	{
		cJSON *wanPathsArrayObject;
		cJSON_AddItemToArray (wanPathsArray, wanPathsArrayObject = cJSON_CreateObject());
		
		// Add the WIM id into the object
		cJSON_AddItemToObject (wanPathsArrayObject, "wimId", cJSON_CreateString (compRouteWan->absWanTop[k].WimId));
		
		// Add the set of edges for that WIM Id
		cJSON *wanEdgesArray;
		wanEdgesArray =  cJSON_CreateArray();
		cJSON_AddItemToObject(wanPathsArrayObject, "wanPathElements", wanEdgesArray);
		gint e = 0;
		for (e = 0; e < compRouteWan->absWanTop[k].numEdges; e++)
		{
			cJSON *wanEdgeObject;
			cJSON_AddItemToArray (wanEdgesArray, wanEdgeObject = cJSON_CreateObject());
			cJSON_AddItemToObject (wanEdgeObject, "aNodeId", cJSON_CreateString (compRouteWan->absWanTop[k].edges[e].aNodeId.nodeId));
			cJSON_AddItemToObject (wanEdgeObject, "zNodeId", cJSON_CreateString (compRouteWan->absWanTop[k].edges[e].zNodeId.nodeId));
			cJSON_AddItemToObject (wanEdgeObject, "aLinkId", cJSON_CreateNumber(compRouteWan->absWanTop[k].edges[e].aLinkId));
			cJSON_AddItemToObject (wanEdgeObject, "zLinkId", cJSON_CreateNumber(compRouteWan->absWanTop[k].edges[e].zLinkId));		
		}
	}


    DEBUG_MTP_PA_SERVER ("JSON Body Response DONE");
	
    buftmp = (char *)cJSON_Print(root);
    strcat (body, (const char*) buftmp);
    *length = strlen ((const char*)body);
    
    DEBUG_MTP_PA_SERVER ("JSON Body (length: %d)", *length);
    DEBUG_MTP_PA_SERVER ("%s", body);    
	cJSON_Delete (root);  
	
	g_free (compRouteWan);
    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to return response OK via REST API with the computed interNfviConnectivityId
 * 	
 * 	@param source
 *  @param httpCode
 *  @param compRouteOutput
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_response_ok (GIOChannel *source, gint httpCode, struct compRouteOutput_t *compRouteOutput)
{
    gint ret = 0;
    
    DEBUG_MTP_PA_SERVER ("Creating the JSON Body and sending the response of the succesfully computed interNfviConnectivity conenction (Id: %s)", interNfviPopConnectionReq->interNfviPopConnectivityId);
    
    guchar buftmp[1024];
    char * buf = g_malloc0 (sizeof (char) * 2048000); 
    // Create the Body of the Response 
    char * jsonBody = g_malloc0 (sizeof (char) * 2048000);
    //memset (jsonBody, 0, sizeof (jsonBody));
    gint length = 0;
    
    mtp_pa_server_rapi_response_json_contents (jsonBody, &length, compRouteOutput);
		
	sprintf((char *)buf, "HTTP/1.1 200 OK\r\n");    
	
    sprintf((char *)buftmp, SERVER_STRING);
    strcat ((char *)buf, (const char *)buftmp);    

    //sprintf((char *)buftmp, "Content-Type: text/plain\r\n");
    sprintf ((char *)buftmp, "Content-Type: application/json\r\n");
    strcat ((char *)buf, (const char *)buftmp);    
    
    // Add the length of the JSON enconding to the Content_Length
    char buff_length[16];
    
    sprintf(buff_length, "%d", length);
    DEBUG_MTP_PA_SERVER ("Buffer Length (JSON BODY): %d Added to the Content Length", length);
    
    sprintf ((char *)buftmp, "Content-Length: ");
    strcat ((char *)buftmp, (const char *)buff_length);
    strcat ((char *)buftmp, "\r\n");
    strcat ((char *)buf, (const char *)buftmp);    
    
    // Add DATE header
    mtp_pa_server_rapi_add_date_header ((char *)buftmp);
    strcat ((char *)buf, (const char *)buftmp);     
    sprintf((char *)buftmp, "\r\n");
    strcat ((char *)buf, (const char *)buftmp);
		
	strcat ((char *)buf, (const char *)jsonBody);		
	DEBUG_MTP_PA_SERVER ("Complete Message: %s", buf);
	    
    ret = mtp_pa_server_rapi_send_message (source, buf);    
    g_free (buf);
    memset (buftmp, '\0', sizeof ( buftmp));    
    g_free (jsonBody);
    (void)ret;
    
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to return response OK via REST API
 * 	
 * 	@param source
 *  @param error
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_response (GIOChannel *source, gint error)
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
	 mtp_pa_server_rapi_add_date_header ((char *)buftmp);
	 strcat ((char *)buf, (const char *)buftmp);     
	     
	 sprintf((char *)buftmp, "\r\n");
	 strcat ((char *)buf, (const char *)buftmp);
	 
	 DEBUG_MTP_PA_SERVER ("%s", buf);
	 
	 // Send the message
	 ret = mtp_pa_server_rapi_send_message (source, buf);	 
	 g_free (buf);	 
	 (void)ret;
	
	 return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to create an artificial compRouteOutput for debugging purposes
 * 	
 * 	@param compRouteOutput
 *  @param httpCode
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void debug_static_computeRouteOutput (struct compRouteOutput_t *compRouteOutput, gint *httpCode)
{
    DEBUG_MTP_PA_SERVER ("Executing debug_static_computeRouteOutput");
    
    // Add the interNfviPopConnectivityId
    memcpy(compRouteOutput->interNfviPopConnectivityId, interNfviPopConnectionReq->interNfviPopConnectivityId, strlen (interNfviPopConnectionReq->interNfviPopConnectivityId));
    DEBUG_MTP_PA_SERVER ("compRouteOutput->interNfviPopConnectivityId: %s", compRouteOutput->interNfviPopConnectivityId);
    
    // Add reqbw
    memcpy (&(compRouteOutput->reqBw), &interNfviPopConnectionReq->bandwidthCons, sizeof (gdouble));
    DEBUG_MTP_PA_SERVER ("compRouteOutput->reqBw: %f", compRouteOutput->reqBw);
    
    compRouteOutput->numRouteElements = 8;
    gint i = 0;
    
    // staring at i = 0 till compRouteOutput->numRouteElements
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.1a", strlen ("00.00.00.00.00.1a"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.3a", strlen ("00.00.00.00.00.3a"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
        
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.3a", strlen ("00.00.00.00.00.3a"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.2a", strlen ("00.00.00.00.00.2a"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.2a", strlen ("00.00.00.00.00.2a"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.3c", strlen ("00.00.00.00.00.3c"));
    compRouteOutput->routeElement[i].aLinkId = 1001;
    compRouteOutput->routeElement[i].zLinkId = 1002;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.3c", strlen ("00.00.00.00.00.3c"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.2c", strlen ("00.00.00.00.00.2c"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.2c", strlen ("00.00.00.00.00.2c"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.1c", strlen ("00.00.00.00.00.1c"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.1c", strlen ("00.00.00.00.00.1c"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.2b", strlen ("00.00.00.00.00.2b"));
    compRouteOutput->routeElement[i].aLinkId = 1001;
    compRouteOutput->routeElement[i].zLinkId = 1002;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.2b", strlen ("00.00.00.00.00.2b"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.3b", strlen ("00.00.00.00.00.3b"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
    
    i++;
    
    memcpy(compRouteOutput->routeElement[i].aNodeId.nodeId, "00.00.00.00.00.3b", strlen ("00.00.00.00.00.3b"));
    memcpy(compRouteOutput->routeElement[i].zNodeId.nodeId, "00.00.00.00.00.1b", strlen ("00.00.00.00.00.1b"));
    compRouteOutput->routeElement[i].aLinkId = 2;
    compRouteOutput->routeElement[i].zLinkId = 1;
    
    *httpCode = HTTP_RETURN_CODE_OK;
    
    
    gint j = 0;
    for (j = 0; j <  compRouteOutput->numRouteElements; j++)
    {
        DEBUG_MTP_PA_SERVER ("Route Element[%d]: {%s, %s, %u, %u]", j, compRouteOutput->routeElement[j].aNodeId.nodeId, compRouteOutput->routeElement[j].zNodeId.nodeId,
                                                                    compRouteOutput->routeElement[j].aLinkId, compRouteOutput->routeElement[j].zLinkId);
    }
    
    return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to parse the JSON object array describing the interWanLinks
 * 	
 * 	@param interWanLinksArray
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_interWanLinks (cJSON *interWanLinksArray)
{
    DEBUG_MTP_PA_SERVER ("Parsing the JSON for the interWanLinks: %d", (gint)cJSON_GetArraySize(interWanLinksArray));
    
    gint i = 0, index = 0;
    
    if (mtpTopo == NULL)
    {
         DEBUG_MTP_PA_SERVER ("weird");   
        exit (-1);
    }
    DEBUG_MTP_PA_SERVER ("mtpTopo num InterWanLinks: %d", mtpTopo->numInterWanLinks); 
    
    gint numberInterWanLinks = 0;
    numberInterWanLinks = (gint)cJSON_GetArraySize(interWanLinksArray);
    DEBUG_MTP_PA_SERVER ("numberInterWanLinks: %d", numberInterWanLinks);
       
    for (i = 0; i < numberInterWanLinks; i++)
    {
        mtpTopo->numInterWanLinks++;
        index = i;
        
        DEBUG_MTP_PA_SERVER ("Exploring numInterWanLinks: %d", mtpTopo->numInterWanLinks);
        
        cJSON *item = cJSON_GetArrayItem (interWanLinksArray, (int)i);
        
        // Get the aWimId
        cJSON *aWimId = cJSON_GetObjectItem (item, "aWimId");
        if (cJSON_IsString(aWimId))
        {        
            memcpy(mtpTopo->interWanLinks[index].aWimId, aWimId->valuestring, strlen (aWimId->valuestring));
            DEBUG_MTP_PA_SERVER ("aWimId: %s", mtpTopo->interWanLinks[index].aWimId);        
        }
        
        // Get zWimId
        cJSON *zWimId = cJSON_GetObjectItem (item, "zWimId");
        if (cJSON_IsString(zWimId))
        {
            memcpy(mtpTopo->interWanLinks[index].zWimId, zWimId->valuestring, strlen (zWimId->valuestring));
            DEBUG_MTP_PA_SERVER ("zWimId: %s", mtpTopo->interWanLinks[index].zWimId);        
        }
        
        // Get aPEId
        cJSON *aPEId = cJSON_GetObjectItem (item, "aPEId");
        if (cJSON_IsString(aPEId))
        {
            memcpy(mtpTopo->interWanLinks[index].aPEId.nodeId, aPEId->valuestring, strlen (aPEId->valuestring));
            DEBUG_MTP_PA_SERVER ("aPEId: %s", mtpTopo->interWanLinks[index].aPEId.nodeId);        
        }
        
        // Get zPEId
        cJSON *zPEId = cJSON_GetObjectItem (item, "zPEId");
        if (cJSON_IsString(zPEId))
        {
            memcpy(mtpTopo->interWanLinks[index].zPEId.nodeId, zPEId->valuestring, strlen (zPEId->valuestring));
            DEBUG_MTP_PA_SERVER ("zPEId: %s", mtpTopo->interWanLinks[index].zPEId.nodeId);        
        }  
        
        // Get aLinkId
        cJSON *aLinkId = cJSON_GetObjectItem (item, "aLinkId");
        if (cJSON_IsNumber(aLinkId))
        {
            mtpTopo->interWanLinks[index].aLinkId = (guint32)aLinkId->valuedouble;
            DEBUG_MTP_PA_SERVER ("aLinkId: %u", mtpTopo->interWanLinks[index].aLinkId);        
        }
        
        // Get zLinkId
        cJSON *zLinkId = cJSON_GetObjectItem (item, "zLinkId");
        if (cJSON_IsNumber(zLinkId))
        {
            mtpTopo->interWanLinks[index].zLinkId = (guint32)zLinkId->valuedouble;
            DEBUG_MTP_PA_SERVER ("zLinkId: %u", mtpTopo->interWanLinks[index].zLinkId);        
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
            mtpTopo->interWanLinks[index].linkCost = (gint)LinkCost->valuedouble;
            DEBUG_MTP_PA_SERVER ("linkCost: %d", mtpTopo->interWanLinks[index].linkCost);        
        }      
        
        if (cJSON_IsNumber(LinkDelay))
        {            
            memcpy (&(mtpTopo->interWanLinks[index].linkDelay), &LinkDelay->valuedouble, sizeof (gdouble));
            DEBUG_MTP_PA_SERVER ("linkDelay: %f", mtpTopo->interWanLinks[index].linkDelay);        
        }
        
        if (cJSON_IsNumber(LinkAvaiBw))
        {            
            memcpy (&(mtpTopo->interWanLinks[index].linkAvailBw), &LinkAvaiBw->valuedouble, sizeof (gdouble));
            DEBUG_MTP_PA_SERVER ("linkAvailBw: %f", mtpTopo->interWanLinks[index].linkAvailBw);        
        }    
    }

    return;
}

 
///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to parse the JSON object array the abstracted WAN topologies handled by the MTP
 * 	
 * 	@param absWanTopoArray
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_absWanTopo (cJSON *absWanTopoArray)
{
	DEBUG_MTP_PA_SERVER ("Parsing the JSON for the Abstracted WAN Topologies");
	gint i = 0, index = 0;
	gint j = 0, indexNodes = 0;
	gint k = 0, indexEdges = 0;
	 
	 
	for (i = 0; i < cJSON_GetArraySize(absWanTopoArray); i++)
	{
		mtpTopo->numAbsWanTopo++;
		index = i;

		cJSON *item = cJSON_GetArrayItem (absWanTopoArray, i);

		// Get the WimId
		cJSON *WimId = cJSON_GetObjectItem (item, "wimId");
		if (cJSON_IsString(WimId))
		{
			memcpy(mtpTopo->absWanTop[index].WimId, WimId->valuestring, strlen (WimId->valuestring));
			DEBUG_MTP_PA_SERVER ("Abs WAN Topo for WimId: %s", mtpTopo->absWanTop[index].WimId);        
		}
			
		// Get the abstracted set of nodes within the WAN
		cJSON *nodeWanArray = cJSON_GetObjectItemCaseSensitive (item, "nodes");
		gint numNodesWan = 0;
		numNodesWan = (gint)cJSON_GetArraySize (nodeWanArray); 
		DEBUG_MTP_PA_SERVER ("WAN (WIM: %s) has %d Nodes", mtpTopo->absWanTop[index].WimId, numNodesWan);
		for (j = 0; j < numNodesWan; j++)
		{
			mtpTopo->absWanTop[index].numNodes++;
			indexNodes = j;

			cJSON *itemNode = cJSON_GetArrayItem(nodeWanArray, j);
			//Get the nodeId
			cJSON *nodeId = cJSON_GetObjectItem(itemNode, "nodeId");
			if (cJSON_IsString(nodeId))
			{
				DEBUG_MTP_PA_SERVER ("Print the NodeId --- before %s", mtpTopo->absWanTop[index].nodes[indexNodes].nodeId);
				memcpy(mtpTopo->absWanTop[index].nodes[indexNodes].nodeId, nodeId->valuestring, strlen (nodeId->valuestring));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding NodeId: %s", mtpTopo->absWanTop[index].nodes[indexNodes].nodeId);        
			}                
		}    
			
		// Get the abstracted set of edges within the absWanTop
		cJSON *edgeWanArray = cJSON_GetObjectItemCaseSensitive (item, "edges");            
		for (k = 0; k < cJSON_GetArraySize(edgeWanArray); k++)
		{
			mtpTopo->absWanTop[index].numEdges++;
			indexEdges = k;

			cJSON *itemEdge = cJSON_GetArrayItem(edgeWanArray, k);

			//Get the aNodeId
			cJSON *aNodeId = cJSON_GetObjectItem(itemEdge, "aNodeId");
			if (cJSON_IsString(aNodeId))
			{
				memcpy(mtpTopo->absWanTop[index].edges[indexEdges].aNodeId.nodeId, aNodeId->valuestring, strlen (aNodeId->valuestring));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge aNodeId: %s", mtpTopo->absWanTop[index].edges[indexEdges].aNodeId.nodeId);        
			}

			//Get the zNodeId
			cJSON *zNodeId = cJSON_GetObjectItem(itemEdge, "zNodeId");
			if (cJSON_IsString(zNodeId))
			{
				memcpy(mtpTopo->absWanTop[index].edges[indexEdges].zNodeId.nodeId, zNodeId->valuestring, strlen (zNodeId->valuestring));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge zNodeId: %s", mtpTopo->absWanTop[index].edges[indexEdges].zNodeId.nodeId);        
			}

			// Get the aLinkId
			cJSON *aLinkId = cJSON_GetObjectItem(itemEdge, "aLinkId");
			if (cJSON_IsNumber(aLinkId))
			{
				mtpTopo->absWanTop[index].edges[indexEdges].aLinkId = (guint32)aLinkId->valuedouble;
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge aLinkId: %u", mtpTopo->absWanTop[index].edges[indexEdges].aLinkId);        
			}

			// Get the zLinkId
			cJSON *zLinkId = cJSON_GetObjectItem(itemEdge, "zLinkId");
			if (cJSON_IsNumber(zLinkId))
			{
				mtpTopo->absWanTop[index].edges[indexEdges].zLinkId = (guint32)zLinkId->valuedouble;
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge zLinkId: %u", mtpTopo->absWanTop[index].edges[indexEdges].zLinkId);        
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
				mtpTopo->absWanTop[index].edges[indexEdges].linkCost = (gint)LinkCost->valuedouble;
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge linkCost: %d", mtpTopo->absWanTop[index].edges[indexEdges].linkCost);        
			}      

			if (cJSON_IsNumber(LinkDelay))
			{               
				memcpy (&(mtpTopo->absWanTop[index].edges[indexEdges].linkDelay), &LinkDelay->valuedouble, sizeof (gdouble));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge linkDelay: %f", mtpTopo->absWanTop[index].edges[indexEdges].linkDelay);        
			}

			if (cJSON_IsNumber(LinkAvaiBw))
			{            
				memcpy (&(mtpTopo->absWanTop[index].edges[indexEdges].linkAvailBw), &LinkAvaiBw->valuedouble, sizeof (gdouble));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge linkAvailBw: %f", mtpTopo->absWanTop[index].edges[indexEdges].linkAvailBw);        
			}

			// Get the network Layer for te edge
			cJSON *netwLayer = cJSON_GetObjectItem(itemEdge, "networkLayer");
			if (cJSON_IsString(netwLayer))
			{
				memcpy(mtpTopo->absWanTop[index].edges[indexEdges].networkLinkLayer, netwLayer->valuestring, strlen (netwLayer->valuestring));
				DEBUG_MTP_PA_SERVER ("Abs WAN Topo - Adding Edge Network Layer: %s", mtpTopo->absWanTop[index].edges[indexEdges].networkLinkLayer);        
			}                
		}            
	}   

	return;
}


///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to parse the JSON object/s for the PA request (i.e. src, dst, topology, ...)
 * 	
 * 	@param root
 * 	@param source
 *  @param interNfviPopConnectivityId 
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void parsing_json_obj_pa_request (cJSON * root, GIOChannel * source, guchar * interNfviPopConnectivityId)
{
	DEBUG_MTP_PA_SERVER ("Parsing the PA Request (interNfviPopConnectivityId: %s)", interNfviPopConnectivityId);    
    
    	if (mtpTopo == NULL)
	{	
	  	DEBUG_MTP_PA_SERVER ("mtpTopo does not exist (was not created) ... STOP\n");
	  	exit(-1);
	}
	
	if (interNfviPopConnectionReq == NULL)
	{
		DEBUG_MTP_PA_SERVER ("interNfviPopConnectionReq does not exist (was not created) ... STOP\n");
		exit(-1);       
	} 

	DEBUG_MTP_PA_SERVER ("interNfviPopConnectionReq: %p", interNfviPopConnectionReq);
	DEBUG_MTP_PA_SERVER ("mtpTopo: %p", mtpTopo);
	DEBUG_MTP_PA_SERVER ("mtpTopo->numInterWanLinks: %d", mtpTopo->numInterWanLinks);
	DEBUG_MTP_PA_SERVER ("mtpTopo->numAbsWanTopo: %d", mtpTopo->numAbsWanTopo);
    
	//DEBUG_MTP_PA_SERVER ("interNfviPopConnectionReq (interNfviConnectivityId): %s", interNfviPopConnectionReq->interNfviPopConnectivityId);
	
	// Set the interNfviPopConnectivityId into the interNfviPopConnectionReq structure
	memset (interNfviPopConnectionReq->interNfviPopConnectivityId, 0, 128);
	memcpy(interNfviPopConnectionReq->interNfviPopConnectivityId, interNfviPopConnectivityId, strlen ((const char*)interNfviPopConnectivityId));
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectionReq->interNfviPopConnectivityId);			
    
    	// Get the PA id
	cJSON *paId = cJSON_GetObjectItemCaseSensitive (root, "paId");
	if (cJSON_IsNumber (paId))
	{
		interNfviPopConnectionReq->paId = (guint)paId->valuedouble;
		DEBUG_MTP_PA_SERVER ("paId: %u", interNfviPopConnectionReq->paId);
	}
	
	// Get the srcPEId
	cJSON *srcPEId = cJSON_GetObjectItemCaseSensitive (root, "srcPEId");   
	if (cJSON_IsString (srcPEId))
	{	
		memcpy(interNfviPopConnectionReq->srcPEId.nodeId, srcPEId->valuestring, strlen (srcPEId->valuestring));
		DEBUG_MTP_PA_SERVER ("srcPEId: %s", interNfviPopConnectionReq->srcPEId.nodeId);			
	}
	
	// Get the dstPeId
	cJSON *dstPEId = cJSON_GetObjectItemCaseSensitive (root, "dstPEId");
	if (cJSON_IsString (dstPEId))
	{	
		memcpy(interNfviPopConnectionReq->dstPEId.nodeId, dstPEId->valuestring, strlen (dstPEId->valuestring));
		DEBUG_MTP_PA_SERVER ("dstPEId: %s", interNfviPopConnectionReq->dstPEId.nodeId);			
	}
	
	// Get the interWanLinks
	cJSON *interWanLinksArray = cJSON_GetObjectItemCaseSensitive (root, "interWanLinks");
	if (interWanLinksArray != NULL)
		parsing_json_obj_interWanLinks (interWanLinksArray);
    
    // Get the abstrated WAN topologies
	cJSON *absWanTopoArray = cJSON_GetObjectItemCaseSensitive (root, "absWanTopo");
	parsing_json_obj_absWanTopo (absWanTopoArray);
    
	// Get the QoS Constraints
	cJSON *qosCons = cJSON_GetObjectItemCaseSensitive (root, "qosCons");
	cJSON *delayCons = cJSON_GetObjectItem (qosCons, "delayConsValue");               
	cJSON *bandwidthConsValue = cJSON_GetObjectItem (qosCons, "bandwidthConsValue");

	if (cJSON_IsNumber(bandwidthConsValue))
	{            
		memcpy (&(interNfviPopConnectionReq->bandwidthCons), &bandwidthConsValue->valuedouble, sizeof (gdouble));
		DEBUG_MTP_PA_SERVER ("Requested Bandwidth Constraint: %f", interNfviPopConnectionReq->bandwidthCons);        
	}

	if (cJSON_IsNumber(delayCons))
	{            
		memcpy (&(interNfviPopConnectionReq->delayCons), &delayCons->valuedouble, sizeof (gdouble));
		DEBUG_MTP_PA_SERVER ("Requested Delay Constraint: %f", interNfviPopConnectionReq->delayCons);        
	}    

	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used parse the JSON object/s 
 * 	
 * 	@param root
 * 	@param data
 * 	@param RestApiMethod
 *  @param source
 *  @param interNfviPopConnectivityId
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint parsing_json_obj (cJSON *root, guchar *data, guint RestApiMethod, GIOChannel *source, guchar *interNfviPopConnectivityId)
{
    DEBUG_MTP_PA_SERVER ("Starting the Parsing of JSON objects interNfviPopConnectivityId: %s", interNfviPopConnectivityId);

    root = cJSON_Parse((const char *)data);

    char * print = cJSON_Print(root);  
    DEBUG_MTP_PA_SERVER ("Input JSON content: %s", print);

    parsing_json_obj_pa_request (root, source, interNfviPopConnectivityId);

    return 0;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Create new tcp client connected to the MTP PA Server
 * 
 * 	@param channel_client, GIOChannel
 *  @param fd
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
struct mtp_client * mtp_pa_server_rapi_client_create (GIOChannel * channel_client, gint fd)
{
	/** check values */
	g_assert(channel_client != NULL); 

	struct mtp_client * client = NULL;
	client = g_malloc0 (sizeof (struct mtp_client));
	if (client == NULL )
	{
		DEBUG_MTP_PA_SERVER ("Malloc for the client failed");
		exit(-1);
	}  

	/** Make client input/output buffer. */
	client->channel = channel_client;	
	client->obuf = mtp_pa_server_stream_new (MAXLENGTH);
	client->ibuf = mtp_pa_server_stream_new (MAXLENGTH);
	client->fd = fd;

	// Clients connected to the MTP PA SERVER
	CLIENT_ID++;
	client->type = CLIENT_ID;

	DEBUG_MTP_PA_SERVER ("Client Id: %u is created (%p)", client->type, client);
	DEBUG_MTP_PA_SERVER ("Client ibuf: %p || obuf: %p", client->ibuf, client->obuf);

	// Add the tcp client to the list
	rapi_tcp_client_list = g_list_append (rapi_tcp_client_list, client);
	DEBUG_MTP_PA_SERVER ("Number of Connected TCP Clients to MTP PA Server: %d", g_list_length (rapi_tcp_client_list));     

	return client;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Close the tcp client, removing from the rapi_tcp_client_list
 * 
 * 	@param client
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_client_close (struct mtp_client * client)
{
	DEBUG_MTP_PA_SERVER ("Closing the client (Id: %d) %p", client->type, client);
	
	if (client->ibuf != NULL)
	{
		 mtp_pa_server_stream_free (client->ibuf);
		 client->ibuf = NULL;
	}
	if (client->obuf != NULL)
	{
		 mtp_pa_server_stream_free (client->obuf);
		 client->obuf = NULL;
	}
	// Remove from the list
	 rapi_tcp_client_list = g_list_remove (rapi_tcp_client_list, client);
	 DEBUG_MTP_PA_SERVER ("TCP Client List: %d", g_list_length(rapi_tcp_client_list));
	 
	 g_free (client);
	 client = NULL;
	 
	 if (client == NULL)
		DEBUG_MTP_PA_SERVER ("client has been removed ...");
	 
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Close operations over the passed tcp channel
 * 
 * 	@param source
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_close_operations (GIOChannel * source)
{
	gint fd = g_io_channel_unix_get_fd (source);
	
	DEBUG_MTP_PA_SERVER ("Stop all the operations over the fd: %d", fd);	
	g_io_channel_flush(source, NULL);
	GError *error = NULL;    
	g_io_channel_shutdown (source, TRUE, &error);
	if(error)
	{
		DEBUG_MTP_PA_SERVER ("An error occurred ...");
	}
	g_io_channel_unref (source);
	return;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Removce the client and close operations over the passed tcp channel
 * 
 * 	@param client
 *  @param source
 *  @param fd
 * 	
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void mtp_pa_server_rapi_stop (struct mtp_client * client, GIOChannel * source, gint fd)
{
	// remove client
	mtp_pa_server_rapi_client_close (client);
	// Stop operations over that channel
	mtp_pa_server_rapi_close_operations (source);
	close (fd);
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used read the different lines ending up in \r\n
 * 	
 * 	@param s
 * 	@param buf
 * 	@param size
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint mtp_pa_server_rapi_get_line (GIOChannel *channel, gchar *buf, gint size)
{
    gint i = 0;
    DEBUG_MTP_PA_SERVER ("\n");
    DEBUG_MTP_PA_SERVER ("----- Read REST API Line(\r\n) ------");
    gint n = 0;
    guchar c = '\0'; // END OF FILE
    
    gboolean cr = FALSE;
    
    //DEBUG_MTP_PA_SERVER ("Symbol of Carriage (r) Return %c", '\r');
    //DEBUG_MTP_PA_SERVER ("Symbol of backspace (n) %c", '\n'); 
    
    while (i < size - 1)
    {
		n = read_channel (channel, &c, 1);	
		
		if (n == -1)
		{
			DEBUG_MTP_PA_SERVER ("Close the channel and eliminate the client");
			return -1;			
		}
	
		if (n > 0)
		{
			//DEBUG_MTP_PA_SERVER ("%c", c);
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
    DEBUG_MTP_PA_SERVER ("Line (size: %d) buf: %s", i, buf);
    return i;
}  

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used read the method
 * 	
 * 	@param buf
 * 	@param j
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
guint mtp_pa_server_get_method (gchar *buf, gint *j)
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
	DEBUG_MTP_PA_SERVER ("REST API METHOD: %s", method);	
	
	// Check that the methods are GET of POST
	if (strcasecmp((const char *)method, "GET") && strcasecmp((const char *)method, "POST") && 
		strcasecmp ((const char *)method, "HTTP/1.1"))
	{
		DEBUG_MTP_PA_SERVER ("The method: %s is not currently supported ...", method);
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
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used read the url
 * 	
 * 	@param buf
 * 	@param j
 *  @param url
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint mtp_pa_server_get_url (gchar *buf, gint *j, gchar *url)
{
	gint i;	
	
	// Skip space char
	while (ISspace(buf[*j]) && (*j < strlen(buf)))
	{
		*j = *j + 1;
	}
	
	//DEBUG_MTP_PA_SERVER ("buf[%d]: %c", *j, buf[*j]);
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
	//DEBUG_MTP_PA_SERVER ("numChar: %d", numChar);
	memcpy (url, buf + initChar, numChar);
	DEBUG_MTP_PA_SERVER ("url: %s", url);
	i = numChar;
	return i;	
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used read the version
 * 	
 * 	@param buf
 * 	@param j
 *  @param version
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gint mtp_pa_server_get_version (gchar *buf, gint *j, gchar *version)
{
	gint i;	
	
	// Skip space char
	while (ISspace(buf[*j]) && (*j < strlen(buf)))
	{
		*j = *j + 1;
	}
	
	//DEBUG_MTP_PA_SERVER ("buf[%d]: %c", *j, buf[*j]);
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
	//DEBUG_MTP_PA_SERVER ("numChar: %d", numChar);
	memcpy (version, buf + initChar, numChar);
	DEBUG_MTP_PA_SERVER ("version: %s", version);
	i = numChar;
	return i;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to process the REST API commands
 * 	
 * 	@param source
 * 	@param cond
 * 	@param data
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean rapi_activity (GIOChannel *source, GIOCondition cond, gpointer data)
{  
	/** lets verify first */
	g_assert(source != NULL);
	g_assert(data != NULL);  
	gint nbytes;
	gchar buf[1024];
	//guchar method[255];
	gchar url[255];
	gchar interNfviPopConnectivityId_tmp[255];
	gchar version[255];
	gchar http_result[255];
	gint body_length = 0;
	guchar interNfviPopConnectivityId[255];

	struct mtp_client *mtp_client = (struct mtp_client *)(data);

	DEBUG_MTP_PA_SERVER (" ************************************************************************** ");    
	DEBUG_MTP_PA_SERVER ("                      REST API ACTIVITY Triggered ");
	DEBUG_MTP_PA_SERVER (" ************************************************************************** ");   

	gint fd = g_io_channel_unix_get_fd (source);
	DEBUG_MTP_PA_SERVER ("File Descriptor (fd): %d, cond: %d", fd, cond);

	if (cond != G_IO_IN)
	{
		DEBUG_MTP_PA_SERVER ("Something happening with the channel and fd ... (cond: %d)", cond);
		mtp_pa_server_rapi_stop (mtp_client, source, fd);		
		return FALSE;
	}	

	// get line
	nbytes = mtp_pa_server_rapi_get_line (source, buf, sizeof (buf));
	if (nbytes == -1)
	{
		DEBUG_MTP_PA_SERVER ("nbytes -1 ... then close the fd and eliminate associated client");						
		mtp_pa_server_rapi_stop (mtp_client, source, fd);
		return FALSE;						
	}
	
	/** Clear input buffer. */
	mtp_pa_server_stream_reset (mtp_client->ibuf);
	struct mtp_pa_server_stream *print_stream = mtp_pa_server_stream_new (MAXLENGTH);
	mtp_pa_server_stream_reset (print_stream);		
	
	if ((buf[0] == '\n') && (nbytes  == 1))
	{
		DEBUG_MTP_PA_SERVER (" -- buf[0] = newline --");
		return FALSE;
	}
	
	gint i = 0, j = 0;
	// Get the REST Method
	guint RestApiMethod = 0;		
	RestApiMethod = mtp_pa_server_get_method (buf, &j);
	if (RestApiMethod == 0)
	{
		DEBUG_MTP_PA_SERVER ("The method is NOT supported ...");
		mtp_pa_server_rapi_unimplemented (source);
		close (fd);
		return FALSE;
	}

	// get the REST url
	i = mtp_pa_server_get_url (buf, &j, url);	
	url[i] = '\0';	

	// for method POST and GET, chech url "/compRoute/"
	if (((RestApiMethod == REST_API_METHOD_POST) || (RestApiMethod == REST_API_METHOD_GET))
		&& (strncmp((const char*) url, "/compRoute/", 10) != 0))
	{
		DEBUG_MTP_PA_SERVER ("UnKnown url(%s)!!!", url);
		exit (-1);
	}	 
	
	// Get the selected interNfviPopConnectivtyId
	// Skip "/compRoute/" thus start reading url[11]
	memset (interNfviPopConnectivityId_tmp, 0, 255);
	for (i = 11, j = 0; i < sizeof (url); i++, j++)
	{
			interNfviPopConnectivityId_tmp[j] = url[i];
	}
	//interNfviPopConnectivityId[j] = '\0';
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId_tmp: %s", interNfviPopConnectivityId_tmp);
	memset (interNfviPopConnectivityId, 0, 255);
	memcpy (interNfviPopConnectivityId, interNfviPopConnectivityId_tmp, strlen (interNfviPopConnectivityId_tmp));	
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	
	// Process the HTTP/1.1 response
	if (RestApiMethod == REST_API_METHOD_HTTP)
	{
		int http_return = atoi ((const char *) url);
		DEBUG_MTP_PA_SERVER ("HTTP/1.1. %d", http_return);
	
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
		DEBUG_MTP_PA_SERVER ("HTTP/1.1. %d %s", http_return, http_result);
	}	

	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	// get the version
	i = mtp_pa_server_get_version (buf, &j, version);
	version[i] = '\0';		

	// Assume HTTP/1.1, then there is Host Header
	memset(buf, '\0', sizeof(buf));        
	nbytes = mtp_pa_server_rapi_get_line (source, buf, sizeof (buf));
	if (nbytes == -1)
	{
		DEBUG_MTP_PA_SERVER ("nbytes -1 ... then close the fd and eliminate associated client");			
		mtp_pa_server_rapi_stop (mtp_client, source, fd);		
		return FALSE;
					
	}
	DEBUG_MTP_PA_SERVER ("Header: %s", buf);	
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	// Headers --- The Header Fields ends up with a void line (i.e., \r\n)
	while ((nbytes > 0) && (strcmp ("\r\n", (const char *)buf) != 0))
	{	
		/* read & discard headers */
		memset(buf, '\0', sizeof(buf));  
		nbytes = mtp_pa_server_rapi_get_line (source, buf, sizeof (buf));
		if (nbytes == -1)
		{
			DEBUG_MTP_PA_SERVER ("nbytes -1 ... then close the fd and eliminate associated client");	
			mtp_pa_server_rapi_stop (mtp_client, source, fd);		
			return FALSE;
		}
		DEBUG_MTP_PA_SERVER ("Header: %s", buf);	  
		if (strncmp ((const char *)buf, "Content-Length:", 15) == 0)
		{
			DEBUG_MTP_PA_SERVER ("Header Content-Length Found");
			char str[20];
	  
			gint i = 15;  // "Content-Length:" We skip the first 16 characters to directly retrieve the length in bytes of the Body of Request
			gint k = 0;
			char contentLength[255];
			
			while (buf[i] != '\r')
			{
				//DEBUG_MTP_PA_SERVER ("%c", buf[i]);
				str[k] = buf[i];
				k++, i++;
			}
			
			j = 0, i = 0;
			while (ISspace(str[j]) && (j < strlen(str)))
					j++;
			while (!ISspace(str[j]) && (i < strlen(contentLength) - 1) && (j < strlen(str)))
			{
					contentLength[i] = str[j];
					i++; j++;
			}
			
			body_length = atoi (contentLength);
			DEBUG_MTP_PA_SERVER ("Body length: %d (%s) in Bytes", body_length, contentLength);	      
		}	  
	}
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	if (body_length == 0)
	{
		DEBUG_MTP_PA_SERVER ("--- NO REST API Body length (length = %d)", body_length);
		return TRUE;
	}
	DEBUG_MTP_PA_SERVER (" -- Processing API Request Body (length: %d) w/ JSON Contents --", body_length);        
	
	// Request Body
	nbytes = 0;
	//DEBUG_MTP_PA_SERVER ("nbytes: %d, mtp_client->ibuf->data: %s", nbytes, mtp_client->ibuf->data);
	nbytes = read_channel (source, (guchar *)(mtp_client->ibuf->data + mtp_client->ibuf->putp), body_length);
	//DEBUG_MTP_PA_SERVER ("nbytes: %d, mtp_client->ibuf->data: %s", nbytes, mtp_client->ibuf->data);
	if ((nbytes < 0) && (body_length > 0))
	{
		DEBUG_MTP_PA_SERVER ("nbytes: %d // body_length: %d", nbytes, body_length);
		exit (-1);
	}
	
	mtp_client->ibuf->putp += nbytes;
	mtp_client->ibuf->endp += nbytes;		

	cJSON * root = cJSON_malloc (sizeof (cJSON));
	//  Parsing the contents on the PA to be executed
	gint ret = 0;
	// Create the mtpTopo placeholder
	mtpTopo = create_mtp_topology ();
	// Create the interNfviPopConnectionReq placeholder
	interNfviPopConnectionReq = create_interNfviPop_conn_req ();
	// Process the json contents and store relevant information at both the mtpTopo and interNfviPopConnectionReq
	DEBUG_MTP_PA_SERVER ("interNfviPopConnectivityId: %s", interNfviPopConnectivityId);
	ret = parsing_json_obj (root, mtp_client->ibuf->data, RestApiMethod, source, interNfviPopConnectivityId);	
	// Release the root JSON object variable used for pa
	cJSON_free (root);	
	if (ret == -1)
	{
		DEBUG_MTP_PA_SERVER ("Something wrong with the JSON Objects ... ");
		//g_free (monitored_info);
		return FALSE;
	}	
	
	// Trigger the computation	
	struct compRouteOutput_t *compRouteOutput = create_path();
	gint httpCode = HTTP_RETURN_CODE_OK;
	httpCode = mtp_pa_server_pa1000_alg (compRouteOutput);
	
#if 0       
	// This function is executed for debugging purposes
	debug_static_computeRouteOutput (compRouteOutput, &httpCode);
#endif
	// Send the respsonse to the REST  API Client
	if (httpCode != HTTP_RETURN_CODE_OK)
	{            
		DEBUG_MTP_PA_SERVER ("HTTP CODE: %d -- NO OK", httpCode);
		mtp_pa_server_rapi_response (source, httpCode);
	}
	else
	{
		DEBUG_MTP_PA_SERVER ("HTTP CODE: %d -- OK", httpCode);
		mtp_pa_server_rapi_response_ok (source, httpCode, compRouteOutput);            
	}
	
	// Release the variables		
	g_free (mtpTopo);
	g_free (interNfviPopConnectionReq);
	g_free (compRouteOutput);
   
	return TRUE;  
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Function used to accept a new connection and add the client to list of clients
 * 
 * 	@param source, GIOChannel
 * 	@param cond, GIOCondition
 * 	@param data, gpointer
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
gboolean rest_api_tcp_new_connection (GIOChannel *source, GIOCondition cond, gpointer data)
{
	gint new; /** new socket descriptor */
	socklen_t client;
	GIOChannel *new_channel = NULL;
	GError *error = NULL;
	struct sockaddr_in client_addr;
	struct mtp_client *new_client = NULL;
	
	DEBUG_MTP_PA_SERVER (" \n");
	DEBUG_MTP_PA_SERVER (" ****** New TCP Connection (REST API) Received ******");

	/** get size of client_addre structure */
	client = sizeof(client_addr);
	
	if ((cond == G_IO_HUP) || (cond == G_IO_ERR) || (G_IO_NVAL))
	{
		DEBUG_MTP_PA_SERVER ("Something happening with the channel and fd ... cond: %d", cond);
		
		// Find the client associated and remove from the list, and stop all the operations over that channel
		struct mtp_client *client = NULL;
		gint fd = g_io_channel_unix_get_fd (source);
		GList *found = g_list_find_custom (rapi_tcp_client_list, &fd, find_mtp_client_by_fd);
		if (found != NULL)
		{
			client = (struct mtp_client *)(found->data);			
			// remove client
			mtp_pa_server_rapi_client_close (client);			
			// Stop operations over that channel
			mtp_pa_server_rapi_close_operations (source);
			close (fd);
			return FALSE;
		}		
	}	

	if (cond == G_IO_IN)
	{
		if ((new = accept (g_io_channel_unix_get_fd (source), (struct sockaddr *)&client_addr, &client)) < 0)
		{
			DEBUG_MTP_PA_SERVER ("Unable to accept new connection");
			return FALSE;
		}

		/** new channel */
		new_channel = g_io_channel_unix_new (new);		
		DEBUG_MTP_PA_SERVER ("TCP Connection (REST API) is UP; (socket: %d)", new);

		/** create mtp_pa_server client */		
		new_client = mtp_pa_server_rapi_client_create (new_channel, new);	
		DEBUG_MTP_PA_SERVER ("NEW mtp client created (%p) Id: %d with fd: %d", new_client, new_client->type, new);

		/** 
		* force binary encoding with NULL
		*/
		if ( g_io_channel_set_encoding (new_channel, NULL, &error) != G_IO_STATUS_NORMAL)
		{		
			DEBUG_MTP_PA_SERVER ("Error: %s", error->message);
			exit (-1);
		}

		g_io_channel_set_close_on_unref (new_channel, TRUE);

		/** On unbuffered channels, it is safe to mix read
			*  and write calls from the new and old APIs.
			*/
		g_io_channel_set_buffered (new_channel, FALSE);

		if (g_io_channel_set_flags (new_channel, G_IO_FLAG_NONBLOCK, &error) != G_IO_STATUS_NORMAL )
		{
			DEBUG_MTP_PA_SERVER ("Error: %s", error->message);
			exit (-1);
		}	

		/**
		* Adds the new channel into the main event loop.
		* the watch_id is used for removing permanent the
		* the connection from the list of monitor connections
		* of the MainLoop.
		*/
		
		DEBUG_MTP_PA_SERVER ("ibuf: %p // obud: %p", new_client->ibuf, new_client->obuf);		
		g_io_add_watch (new_channel, G_IO_IN, rapi_activity, new_client);
    }	
	return TRUE;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief enabling the reuse of the addr for the Server TCP
 * 	
 * 	@param sock gint
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_tcp_enable_reuseaddr (gint sock)
{
	gint tmp = 1;    

	if (sock < 0)
	{
	DEBUG_MTP_PA_SERVER (" socket: %d !!!",sock);
	exit (-1);
	}

	if (setsockopt (sock, SOL_SOCKET, SO_REUSEADDR, (gchar *)&tmp, sizeof (tmp)) == -1)
	{
	DEBUG_MTP_PA_SERVER ("bad setsockopt ...");
	exit (-1);
	}
	return;
}

///////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_serer_restapi.c
 * 	@brief Configuring options of the file descriptor
 * 	
 * 	@param sock gint
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rapi_setnonblocking(gint sock)
{
	gint opts;    

	 opts = fcntl (sock,F_GETFL);
	 if (opts < 0)
	{
		DEBUG_MTP_PA_SERVER ("fcntl(F_GETFL) error");
		exit(-1);
	}

	opts = (opts | O_NONBLOCK);

	if (fcntl(sock,F_SETFL,opts) < 0)
	{	
		DEBUG_MTP_PA_SERVER ("fcntl(F_SETFL) error");
		exit(-1);
	}
	return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server_restapi.c
 * 	@brief Main function for the creating / maintaining TCP session for the REST API
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void rest_api_serve_init (gint port)
{     
      
      DEBUG_MTP_PA_SERVER ("REST API PORT (listening): %d", port);
     
      gint                s;
      struct sockaddr_in  addr;
      GIOChannel          *channel;

      /** File Descriptor for our "listening" socket */
      s = socket (AF_INET, SOCK_STREAM, 0);	 
	  
      if (s == -1)
      {
	    DEBUG_MTP_PA_SERVER ("Error creating the socket");
	    exit (-1);
      }

      DEBUG_MTP_PA_SERVER ("Trigger REST API Connection (FD: %d)", s);

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
        DEBUG_MTP_PA_SERVER ("socket bind failed");
        exit (-1);
      }
      
      DEBUG_MTP_PA_SERVER ("Bind to Fd: %d Done", s);

      /** Set up queue for incoming connections */
      if (listen (s, 10) == -1)
      {
        close (s);
        DEBUG_MTP_PA_SERVER ("Socket listen failed");
        exit (-1);
      }
      
      DEBUG_MTP_PA_SERVER ("Listen (up to 10) to Fd: %d Done", s);

      /** Create new channel to handle the socket */
      channel = g_io_channel_unix_new (s);
      gsize buffersize = g_io_channel_get_buffer_size (channel);
      DEBUG_MTP_PA_SERVER ("GIOChannel with Buffer Size: %d", buffersize);
    
      gsize newBufferSize = MAX_GIO_CHANNEL_BUFFER_SIZE;
      g_io_channel_set_buffer_size (channel, newBufferSize);
    
      buffersize = g_io_channel_get_buffer_size (channel);
      DEBUG_MTP_PA_SERVER ("GIOChannel with Buffer Size: %d", buffersize);
      
      DEBUG_MTP_PA_SERVER ("Channel associated to fd: %d is created", s);

      /**
      * Adds the new channel into the main event loop.
      * According to a condition G_IO_IN, the function
      * rest_api_tcp_new_connection handles this event.
      */
      g_io_add_watch (channel, G_IO_IN | G_IO_ERR | G_IO_HUP | G_IO_NVAL, rest_api_tcp_new_connection, NULL);

      return;     
}  
