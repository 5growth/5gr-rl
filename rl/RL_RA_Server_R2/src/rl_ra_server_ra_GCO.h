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

#ifndef  _5GR_RL_RA_SERVER_RA_GCO_H
#define  _5GR_RL_RA_SERVER_RA_GCO_H

#include <glib.h>
#include <glib/gstdio.h>
#include <glib-2.0/glib/gtypes.h>

#define MAX_NUM_SOL						1

#define EPSILON							1.0f
#define EPSILON1						0.1f

// Defined K value for the KSP COMPUTATION
#define GCO_KSP_VALUE					5

// HTTP RETURN CODES
#define HTTP_CODE_OK					200
#define HTTP_CODE_CREATED 				201
#define HTTP_CODE_BAD_REQUEST    		400
#define HTTP_CODE_UNAUTHORIZED   		401
#define HTTP_CODE_FORBIDDEN      		403
#define HTTP_CODE_NOT_FOUND				404
#define HTTP_CODE_NOT_ACCEPTABLE		406

// Prototype of external declaration of functions
gint ra_GCO_alg (struct compRouteOutputList_t *);

#endif
