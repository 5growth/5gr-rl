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

#ifndef _RL_RA_SERVER_STREAM_H
#define _RL_RA_SERVER_STREAM_H

#include <glib.h>
#include <glib/gstdio.h>
#include <glib-2.0/glib/gtypes.h>

#define MAXLENGTH 		131072

/** Stream buffer. */
struct rl_ra_server_stream
{
  struct rl_ra_server_stream *next;

  guchar *data;

  /** Put pointer. */
  gulong putp;

  /** Get pointer. */
  gulong getp;

  /** End of pointer. */
  gulong endp;

  /** Data size. */
  gulong size;
};

/** First in first out queue structure. */
struct rl_ra_server_stream_fifo
{
  gulong count;

  struct rl_ra_server_stream *head;
  struct rl_ra_server_stream *tail;
};

/** Utility macros. */
#define STREAM_PNT(S)   ((S)->data + (S)->getp)
#define STREAM_SIZE(S)  ((S)->size)
#define STREAM_REMAIN(S) ((S)->size - (S)->putp)
#define STREAM_DATA(S)  ((S)->data)

extern FILE *logfile;

//////////////////////////////////////////////////////
// For debugging
//////////////////////////////////////////////////////
#define __SHORT_FILENAME__ \
        (strrchr(__FILE__,'/') \
         ? strrchr(__FILE__,'/')+1 \
         : __FILE__ \
        )

#define DEBUG_RL_RA(format,...) \
{			       \
	if (logfile != NULL)   \
	{		       \
		g_fprintf(logfile,"%s:%1.5d  %30s "format"\n",\
                                __SHORT_FILENAME__,     		\
                                __LINE__, __FUNCTION__, ##__VA_ARGS__);	        \
		fflush(logfile);					        \
	}								        \
	else 								        \
	{	                                                                \
		g_fprintf(stdout,"%s:%1.5d  %30s "format"\n", \
                                __SHORT_FILENAME__,     		\
                                __LINE__, __FUNCTION__, ##__VA_ARGS__);	        \
		fflush(stdout);					                \
	}                                                                       \
} 

/** Stream prototypes. */
struct rl_ra_server_stream *rl_ra_server_stream_new (size_t);
void rl_ra_server_stream_free (struct rl_ra_server_stream *);

gulong rl_ra_server_stream_get_getp (struct rl_ra_server_stream *);
gulong rl_ra_server_stream_get_putp (struct rl_ra_server_stream *);
gulong rl_ra_server_stream_get_endp (struct rl_ra_server_stream *);
gulong rl_ra_server_stream_get_size (struct rl_ra_server_stream *);
guchar *rl_ra_server_stream_get_data (struct rl_ra_server_stream *);

void rl_ra_server_stream_set_getp (struct rl_ra_server_stream *, gulong);
void rl_ra_server_stream_set_putp (struct rl_ra_server_stream *, gulong);

void rl_ra_server_stream_forward (struct rl_ra_server_stream *, gint);

void rl_ra_server_stream_put (struct rl_ra_server_stream *, void *, size_t);
gint rl_ra_server_stream_putc (struct rl_ra_server_stream *, guchar);
gint rl_ra_server_stream_putc_at (struct rl_ra_server_stream *, gulong, guchar);
gint rl_ra_server_stream_putw (struct rl_ra_server_stream *, guint16);
gint rl_ra_server_stream_putw_at (struct rl_ra_server_stream *, gulong, guint16);
gint rl_ra_server_stream_putl (struct rl_ra_server_stream *, guint32);
gint rl_ra_server_stream_putl_at (struct rl_ra_server_stream *, gulong, guint32);
gint rl_ra_server_stream_put_ipv4 (struct rl_ra_server_stream *, guint32);
gint rl_ra_server_stream_put_in_addr (struct rl_ra_server_stream *, struct in_addr *);
void rl_ra_server_stream_put_undo (struct rl_ra_server_stream *, gulong);

void rl_ra_server_stream_get (void *, struct rl_ra_server_stream *, size_t);
guchar rl_ra_server_stream_getc (struct rl_ra_server_stream *);
guchar rl_ra_server_stream_getc_from (struct rl_ra_server_stream *, gulong);
guint16 rl_ra_server_stream_getw (struct rl_ra_server_stream *);
guint16 rl_ra_server_stream_getw_from (struct rl_ra_server_stream *, gulong);
guint32 rl_ra_server_stream_getl (struct rl_ra_server_stream *);
guint32 rl_ra_server_stream_get_ipv4 (struct rl_ra_server_stream *);

gint read_channel (GIOChannel *, guchar *, gint );
gint rl_ra_server_stream_read (struct rl_ra_server_stream *, GIOChannel *channel, size_t n);
gint rl_ra_server_stream_read_unblock (struct rl_ra_server_stream *, gint, size_t);
gint rl_ra_server_stream_write (struct rl_ra_server_stream *, guchar *, size_t);

guchar *rl_ra_server_stream_pnt (struct rl_ra_server_stream *);
void rl_ra_server_stream_reset (struct rl_ra_server_stream *);
gint rl_ra_server_stream_flush (struct rl_ra_server_stream *, gint);
gint rl_ra_server_stream_empty (struct rl_ra_server_stream *);

/** Stream fifo. */
struct rl_ra_server_stream_fifo *rl_ra_server_stream_fifo_new ();
void rl_ra_server_stream_fifo_push (struct rl_ra_server_stream_fifo *fifo, struct rl_ra_server_stream *s);
struct rl_ra_server_stream *rl_ra_server_stream_fifo_pop (struct rl_ra_server_stream_fifo *fifo);
struct rl_ra_server_stream *rl_ra_server_stream_fifo_head (struct rl_ra_server_stream_fifo *fifo);
void rl_ra_server_stream_fifo_clean (struct rl_ra_server_stream_fifo *fifo);
void rl_ra_server_stream_fifo_free (struct rl_ra_server_stream_fifo *fifo);

#endif
