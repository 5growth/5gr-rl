#
# 	# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# Author: CTTC/CERCA CDN Ricardo Martínez (ricardo.martinez@cttc.es)
CC            =  gcc
# FLAGS         =  -O6 -Wall -DPOSIX_SOURCE
FLAGS = -O0 -ggdb -g
CFLAGS    =  $(FLAGS) -I. -I/usr/include/glib-2.0 -I/usr/lib/i386-linux-gnu/glib-2.0/include/ -DDEBUG
LDLIBS    =  -lm
LDFLAGS   =   -g 

all: rl_ra_server

rl_ra_server: rl_ra_server.o rl_ra_server_stream.o rl_ra_server_cjson.o rl_ra_server_comp.o rl_ra_server_ra_CSA.o rl_ra_server_ra_InA.o rl_ra_server_restapi.o 
	gcc -o rl_ra_server rl_ra_server.o rl_ra_server_stream.o rl_ra_server_cjson.o rl_ra_server_comp.o rl_ra_server_ra_CSA.o rl_ra_server_ra_InA.o rl_ra_server_restapi.o -L/usr/lib/i386-linux-gnu/ -lglib-2.0  $(LDFLAGS) $(LDLIBS)

# strip rl_ra_server
	
rl_ra_server_stream.o: rl_ra_server_stream.h rl_ra_server_stream.c
	$(CC) $(CFLAGS) -c rl_ra_server_stream.c -o rl_ra_server_stream.o  

rl_ra_server_cjson.o: rl_ra_server_stream.h rl_ra_server_cjson.h rl_ra_server_cjson.c
	$(CC) $(CFLAGS) -c rl_ra_server_cjson.c -o rl_ra_server_cjson.o  
	
rl_ra_server_comp.o: rl_ra_server_stream.h rl_ra_server_comp.h rl_ra_server_comp.c
	$(CC) $(CFLAGS) -c rl_ra_server_comp.c -o rl_ra_server_comp.o  
	
rl_ra_server_ra_CSA.o: rl_ra_server_stream.h rl_ra_server_comp.h rl_ra_server_ra_CSA.h rl_ra_server_ra_CSA.c
	$(CC) $(CFLAGS) -c rl_ra_server_ra_CSA.c -o rl_ra_server_ra_CSA.o
	
rl_ra_server_ra_InA.o: rl_ra_server_stream.h rl_ra_server_comp.h rl_ra_server_ra_InA.h rl_ra_server_ra_InA.c
	$(CC) $(CFLAGS) -c rl_ra_server_ra_InA.c -o rl_ra_server_ra_InA.o

rl_ra_server_restapi.o: rl_ra_server_comp.h rl_ra_server_stream.h rl_ra_server_cjson.h rl_ra_server_ra_CSA.h rl_ra_server_ra_InA.h rl_ra_server_restapi.h rl_ra_server_restapi.c
	$(CC) $(CFLAGS) -c rl_ra_server_restapi.c -o rl_ra_server_restapi.o

rl_ra_server.o: rl_ra_server_stream.h rl_ra_server_restapi.h rl_ra_server.c rl_ra_server.h
	$(CC) $(CFLAGS) -c rl_ra_server.c -o rl_ra_server.o 

clean:	
	rm -f *.o rl_ra_server
