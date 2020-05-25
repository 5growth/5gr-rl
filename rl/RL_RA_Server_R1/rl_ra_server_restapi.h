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

#ifndef _RL_RA_SERVER_RESTAPI_H
#define _RL_RA_SERVER_RESTAPI_H


#include <glib.h>
#include <glib/gstdio.h>
#include <glib-2.0/glib/gtypes.h>


#define MAX_GIO_CHANNEL_BUFFER_SIZE     131072

// HTTP RETURN CODES
#define HTTP_RETURN_CODE_OK				200
#define HTTP_RETURN_CODE_CREATED 		201
#define HTTP_RETURN_CODE_BAD_REQUEST    400
#define HTTP_RETURN_CODE_UNAUTHORIZED   401
#define HTTP_RETURN_CODE_FORBIDDEN      403
#define HTTP_RETURN_CODE_NOT_FOUND		404
#define HTTP_RETURN_CODE_NOT_ACCEPTABLE	406

// REST API METHODS (SIMPLY INT ENCODING)
#define REST_API_METHOD_GET		1
#define REST_API_METHOD_POST	2
#define REST_API_METHOD_HTTP	3

////////////////////////////////////////////////////
// Client Struct for connecting to RL RA SERVER
////////////////////////////////////////////////////
// List of tcp clients connected to RL RA Server

#define RL_RA_CLIENT_TYPE	1000
struct rl_client
{
     /** IO Channel from client. */
     GIOChannel *channel;

     /** Input/output buffer to the client. */    
     struct rl_ra_server_stream *obuf;  
	 struct rl_ra_server_stream *ibuf;

	gint fd; // file descriptor     
    guint type;     
};

void rest_api_serve_init (gint);
#endif
