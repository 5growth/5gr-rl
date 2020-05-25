# Copyright 2019 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
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

# Author: Ricardo Martínez
CC            =  gcc
# FLAGS         =  -O6 -Wall -DPOSIX_SOURCE
FLAGS = -O0 -ggdb -g
CFLAGS    =  $(FLAGS) -I. -I/usr/include/glib-2.0 -I/usr/lib/i386-linux-gnu/glib-2.0/include/ -DDEBUG
LDLIBS    =  -lm
LDFLAGS   =   -g 

all: mtp_pa_server

mtp_pa_server: mtp_pa_server.o mtp_pa_server_stream.o mtp_pa_server_cjson.o mtp_pa_server_comp.o mtp_pa_server_pa1000.o mtp_pa_server_restapi.o 
	gcc -o mtp_pa_server mtp_pa_server.o mtp_pa_server_stream.o mtp_pa_server_cjson.o mtp_pa_server_comp.o mtp_pa_server_pa1000.o mtp_pa_server_restapi.o -L/usr/lib/i386-linux-gnu/ -lglib-2.0  $(LDFLAGS) $(LDLIBS)

# strip mtp_pa_server
	
mtp_pa_server_stream.o: mtp_pa_server_stream.h mtp_pa_server_stream.c
	$(CC) $(CFLAGS) -c mtp_pa_server_stream.c -o mtp_pa_server_stream.o  

mtp_pa_server_cjson.o: mtp_pa_server_stream.h mtp_pa_server_cjson.h mtp_pa_server_cjson.c
	$(CC) $(CFLAGS) -c mtp_pa_server_cjson.c -o mtp_pa_server_cjson.o  
	
mtp_pa_server_comp.o: mtp_pa_server_stream.h mtp_pa_server_comp.h mtp_pa_server_comp.c
	$(CC) $(CFLAGS) -c mtp_pa_server_comp.c -o mtp_pa_server_comp.o  
	
mtp_pa_server_pa1000.o: mtp_pa_server_stream.h mtp_pa_server_comp.h mtp_pa_server_pa1000.h mtp_pa_server_pa1000.c
	$(CC) $(CFLAGS) -c mtp_pa_server_pa1000.c -o mtp_pa_server_pa1000.o

mtp_pa_server_restapi.o: mtp_pa_server_comp.h mtp_pa_server_stream.h mtp_pa_server_cjson.h mtp_pa_server_pa1000.h mtp_pa_server_restapi.h mtp_pa_server_restapi.c
	$(CC) $(CFLAGS) -c mtp_pa_server_restapi.c -o mtp_pa_server_restapi.o

mtp_pa_server.o: mtp_pa_server_stream.h mtp_pa_server_restapi.h mtp_pa_server.c mtp_pa_server.h
	$(CC) $(CFLAGS) -c mtp_pa_server.c -o mtp_pa_server.o 

clean:	
	rm -f *.o mtp_pa_server
