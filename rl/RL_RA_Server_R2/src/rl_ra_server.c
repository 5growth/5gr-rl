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
#include <ctype.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>
#include <glib.h>
#include <sys/time.h>

#include "rl_ra_server_stream.h"
#include "rl_ra_server_restapi.h"
#include "rl_ra_server.h"

// External Variables
FILE *logfile = NULL;

// RL IP address API Client
struct in_addr RL_IpAddr;

// REST API ENABLED
int RESTAPI_ENABLED = 0;

GMainLoop * loop = NULL;

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server.c
 * 	@brief Read the rl_ra_server.conf file located at /etc/rl_ra_server/
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
void read_rl_ra_server_config_file (FILE *fp)
{
    DEBUG_RL_RA ("Processing rl_ra_server.conf");
    
    char buff[128], ip[128];   
    
    // READ RL RA Server IP
    memset (&RL_IpAddr, (int)0, sizeof (RL_IpAddr));
    fscanf(fp, "%s %s ", buff, ip);
    RL_IpAddr.s_addr = inet_addr(ip);    
    DEBUG_RL_RA ("RL_IpAddr Server IP Addr: %s\n", inet_ntoa (RL_IpAddr)); 
    memset (buff, 0, sizeof (buff));
        
    // Read REST API 
    fscanf (fp, "%s %d ", buff, &RESTAPI_ENABLED);
    if (RESTAPI_ENABLED) DEBUG_RL_RA ("REST API is Enabled");
    if (RESTAPI_ENABLED == 0) DEBUG_RL_RA ("REST API is NOT Enabled");	

    memset (buff, 0, sizeof (buff));
    DEBUG_RL_RA ("CommandLine: %s", buff);
  
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file rl_ra_server/rl_ra_server.c
 * 	@brief Main function for RL RA SERVER
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2020
 */
/////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[])
{     
	DEBUG_RL_RA ("********************************************************************"); 
	DEBUG_RL_RA ("********************************************************************");
	DEBUG_RL_RA (" ---------------------- RL RA SERVER ---------------------------");
	DEBUG_RL_RA ("********************************************************************");
	DEBUG_RL_RA ("********************************************************************"); 
	
	// processing input parameters, aka arguments to trigger the RA server
	if (argc == 1)
	{
		DEBUG_RL_RA ("Arguments are missing ...");
		exit (-1);		
	}
	
	// argv[1] specifies the folder and the configuration file
	gchar configFile[50];
	strcpy (configFile, argv[1]);
	DEBUG_RL_RA ("RL RA Server Config File is: %s", configFile);
	
	// argv[2] specifies the folder and the log file
	gchar log[50];
	strcpy (log, argv[2]);
	DEBUG_RL_RA ("RL RA Server log File is: %s", log);	

	// open the log file	
	logfile = fopen (log, "w");
	DEBUG_RL_RA ("log file is opened");
	
	// Read the rl_ra_server.conf file
	FILE *rl_ra_server_config = NULL;	
	rl_ra_server_config = fopen (configFile, "r");
	if (rl_ra_server_config == NULL)
	{	
		DEBUG_RL_RA ("File error\n");
		exit (-1);
	}	
	DEBUG_RL_RA ("rl_ra_server.conf is opened");
	
	// Check if flag -d for daemonize 
	if (argc > 3)
	{
		gchar options[10];
		strcpy (options, argv[3]);
		gint ret = strcmp (options, "-d");
		if (ret == 0) daemon(0,0);
	}	
	
	// Process the config file
	read_rl_ra_server_config_file (rl_ra_server_config);     

	DEBUG_RL_RA ("\n");
	DEBUG_RL_RA (" ---- Starting the Main Loop ------");

	/** Creates a new GMainLoop structure */
	loop = g_main_loop_new (NULL, FALSE);
      
	// Iff RESTAPI_ENABLED is ENABLED
	if (RESTAPI_ENABLED)
	{
		rest_api_serve_init (RL_PORT);			
	}     
      
	/** execute loop */
	g_main_loop_run (loop);

	/** decrease the one reference of loop when it is finished */
	g_main_loop_unref(loop);
	loop = NULL;
    return 0;
}
