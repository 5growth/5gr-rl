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

#include "mtp_pa_server_stream.h"
#include "mtp_pa_server_restapi.h"
#include "mtp_pa_server.h"

// External Variables
FILE *logfile = NULL;

// MTP IP address API Client
struct in_addr MTP_IpAddr;

// REST API ENABLED
int RESTAPI_ENABLED = 0;

GMainLoop * loop = NULL;


/////////////////////////////////////////////////////////////////////////////////////////
/**
* @brief Friendly function used to print received message 
*
*	@param Table - 
*	
*	@return NULL
*/
/////////////////////////////////////////////////////////////////////////////////////////
void print_message (guchar *buff, uint16_t length)
{    
        
    DEBUG_MTP_PA_SERVER ("--- Printing Message --- length: %d", length);
    u_char temp[8];  

    /** Friendly traces */
    int i = 0;
    for (i = 0; i < (length); i += 8)
    {
        temp[i%8] = buff[i];
        temp[(i+1)%8] = buff[i+1];
        temp[(i+2)%8] = buff[i+2];
        temp[(i+3)%8] = buff[i+3];

        if (i+4 < length + 4)        
        {
            temp[(i+4)%8] = buff[i+4];
            temp[(i+5)%8] = buff[i+5];
            temp[(i+6)%8] = buff[i+6];
            temp[(i+7)%8] = buff[i+7];
        }
        else
        {
            temp[4] = temp[5] = temp[6] = temp[7] = 0;
        }

        DEBUG_MTP_PA_SERVER (" %3d: %02x%02x%02x%02x %02x%02x%02x%02x - %3d %3d %3d %3d  %3d %3d %3d %3d\n", i,
                   temp[0], temp[1], temp[2], temp[3],
                   temp[4], temp[5], temp[6], temp[7],
                   temp[0], temp[1], temp[2], temp[3],
                   temp[4], temp[5], temp[6], temp[7]);
    }
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server.c
 * 	@brief Function for time processing
 *	
 * 	@param a
 * 
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
////////////////////////////////////////////////////////////////////////////////////////
struct timeval tv_adjust_pcc (struct timeval a)
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
 * 	@file mtp_pa_server/mtp_pa_server.c
 * 	@brief Read the mtp_pa_server.conf file located at /etc/mtp_pa_server/
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
void read_mtp_pa_server_config_file (FILE *fp)
{
    DEBUG_MTP_PA_SERVER ("Read the mtp_pa_server.conf");
    
    char buff[128], ip[128];   
    
    // READ MTP PA Server IP
    memset (&MTP_IpAddr, (int)0, sizeof (MTP_IpAddr));
    fscanf(fp, "%s %s ", buff, ip);
    MTP_IpAddr.s_addr = inet_addr(ip);    
    DEBUG_MTP_PA_SERVER ("MTP PA Server IP Addr: %s\n", inet_ntoa (MTP_IpAddr)); 
    memset (buff, 0, sizeof (buff));
        
    // Read REST API 
    fscanf (fp, "%s %d ", buff, &RESTAPI_ENABLED);
    if (RESTAPI_ENABLED) DEBUG_MTP_PA_SERVER ("REST API is Enabled");
    if (RESTAPI_ENABLED == 0) DEBUG_MTP_PA_SERVER ("REST API is NOT Enabled");
	

    memset (buff, 0, sizeof (buff));
    DEBUG_MTP_PA_SERVER ("CommandLine: %s", buff);
    
  
    return;
}

////////////////////////////////////////////////////////////////////////////////////////
/**
 * 	@file mtp_pa_server/mtp_pa_server.c
 * 	@brief Main function for MTP PA SERVER
 *
 *	@author Ricardo Martínez <ricardo.martinez@cttc.es>
 *	@date 2019
 */
/////////////////////////////////////////////////////////////////////////////////////////
int main()
{     
      DEBUG_MTP_PA_SERVER ("*****************************************************************"); 
      DEBUG_MTP_PA_SERVER ("*****************************************************************");
      DEBUG_MTP_PA_SERVER (" ---------------------- MTP PA SERVER ---------------------------");
      DEBUG_MTP_PA_SERVER ("*****************************************************************");
      DEBUG_MTP_PA_SERVER ("*****************************************************************"); 
  
      // open the log file
      logfile = fopen ("/var/log/mtp_pa_server.log", "w");
      
      DEBUG_MTP_PA_SERVER ("log file is opened");
      
      // Read the mtp_pa_server.conf file
      FILE *mtp_pa_server_config = NULL;
      mtp_pa_server_config = fopen ("/etc/mtp_pa_server/mtp_pa_server.conf", "r");
 
      DEBUG_MTP_PA_SERVER ("mtp_pa_server.conf is opened");
      
      if (mtp_pa_server_config == NULL)
      {	
        DEBUG_MTP_PA_SERVER ("File error\n");
        exit (-1);
      }
      
      read_mtp_pa_server_config_file (mtp_pa_server_config);     
      
      DEBUG_MTP_PA_SERVER ("\n");
      DEBUG_MTP_PA_SERVER (" ---- Starting the Main Loop ------");
      
      /** Creates a new GMainLoop structure */
      loop = g_main_loop_new (NULL, FALSE);
      
	// Iff RESTAPI_ENABLED is ENABLED
	if (RESTAPI_ENABLED)
	{
		rest_api_serve_init (MTP_PORT);			
	}     
      
	/** execute loop */
	g_main_loop_run (loop);

	/** decrease the one reference of loop when it is finished */
	g_main_loop_unref(loop);

    return 0;
}
