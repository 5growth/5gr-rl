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

#ifndef _MTP_PA_SERVER_STREAM_H
#define _MTP_PA_SERVER_STREAM_H


#include <glib.h>
#include <glib/gstdio.h>
#include <glib-2.0/glib/gtypes.h>

#define MAXLENGTH 		8192

/** Stream buffer. */
struct mtp_pa_server_stream
{
  struct mtp_pa_server_stream *next;

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
struct mtp_pa_server_stream_fifo
{
  gulong count;

  struct mtp_pa_server_stream *head;
  struct mtp_pa_server_stream *tail;
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


#define DEBUG_MTP_PA_SERVER(format,...) \
{			       \
	if (logfile != NULL)   \
	{		       \
		g_fprintf(logfile,"[D:%d] -- %30s:%1.5d -- %30s --\t "format"\n",\
                                getpid(), __SHORT_FILENAME__,     		\
                                __LINE__, __FUNCTION__, ##__VA_ARGS__);	        \
		fflush(logfile);					        \
	}								        \
	else 								        \
	{	                                                                \
		g_fprintf(stdout,"[D:%d] -- %30s:%1.5d -- %30s --\t "format"\n", \
                                getpid(), __SHORT_FILENAME__,     		\
                                __LINE__, __FUNCTION__, ##__VA_ARGS__);	        \
		fflush(stdout);					                \
	}                                                                       \
}   

/** Stream prototypes. */
struct mtp_pa_server_stream *mtp_pa_server_stream_new (size_t);
void mtp_pa_server_stream_free (struct mtp_pa_server_stream *);

gulong mtp_pa_server_stream_get_getp (struct mtp_pa_server_stream *);
gulong mtp_pa_server_stream_get_putp (struct mtp_pa_server_stream *);
gulong mtp_pa_server_stream_get_endp (struct mtp_pa_server_stream *);
gulong mtp_pa_server_stream_get_size (struct mtp_pa_server_stream *);
guchar *mtp_pa_server_stream_get_data (struct mtp_pa_server_stream *);

void mtp_pa_server_stream_set_getp (struct mtp_pa_server_stream *, gulong);
void mtp_pa_server_stream_set_putp (struct mtp_pa_server_stream *, gulong);

void mtp_pa_server_stream_forward (struct mtp_pa_server_stream *, gint);

void mtp_pa_server_stream_put (struct mtp_pa_server_stream *, void *, size_t);
gint mtp_pa_server_stream_putc (struct mtp_pa_server_stream *, guchar);
gint mtp_pa_server_stream_putc_at (struct mtp_pa_server_stream *, gulong, guchar);
gint mtp_pa_server_stream_putw (struct mtp_pa_server_stream *, guint16);
gint mtp_pa_server_stream_putw_at (struct mtp_pa_server_stream *, gulong, guint16);
gint mtp_pa_server_stream_putl (struct mtp_pa_server_stream *, guint32);
gint mtp_pa_server_stream_putl_at (struct mtp_pa_server_stream *, gulong, guint32);
gint mtp_pa_server_stream_put_ipv4 (struct mtp_pa_server_stream *, guint32);
gint mtp_pa_server_stream_put_in_addr (struct mtp_pa_server_stream *, struct in_addr *);
void mtp_pa_server_stream_put_undo (struct mtp_pa_server_stream *, gulong);

void mtp_pa_server_stream_get (void *, struct mtp_pa_server_stream *, size_t);
guchar mtp_pa_server_stream_getc (struct mtp_pa_server_stream *);
guchar mtp_pa_server_stream_getc_from (struct mtp_pa_server_stream *, gulong);
guint16 mtp_pa_server_stream_getw (struct mtp_pa_server_stream *);
guint16 mtp_pa_server_stream_getw_from (struct mtp_pa_server_stream *, gulong);
guint32 mtp_pa_server_stream_getl (struct mtp_pa_server_stream *);
guint32 mtp_pa_server_stream_get_ipv4 (struct mtp_pa_server_stream *);

gint read_channel (GIOChannel *, guchar *, gint );
gint mtp_pa_server_stream_read (struct mtp_pa_server_stream *, GIOChannel *channel, size_t n);
gint mtp_pa_server_stream_read_unblock (struct mtp_pa_server_stream *, gint, size_t);
gint mtp_pa_server_stream_write (struct mtp_pa_server_stream *, guchar *, size_t);

guchar *mtp_pa_server_stream_pnt (struct mtp_pa_server_stream *);
void mtp_pa_server_stream_reset (struct mtp_pa_server_stream *);
gint mtp_pa_server_stream_flush (struct mtp_pa_server_stream *, gint);
gint mtp_pa_server_stream_empty (struct mtp_pa_server_stream *);

/** Stream fifo. */
struct mtp_pa_server_stream_fifo *mtp_pa_server_stream_fifo_new ();
void mtp_pa_server_stream_fifo_push (struct mtp_pa_server_stream_fifo *fifo, struct mtp_pa_server_stream *s);
struct mtp_pa_server_stream *mtp_pa_server_stream_fifo_pop (struct mtp_pa_server_stream_fifo *fifo);
struct mtp_pa_server_stream *mtp_pa_server_stream_fifo_head (struct mtp_pa_server_stream_fifo *fifo);
void mtp_pa_server_stream_fifo_clean (struct mtp_pa_server_stream_fifo *fifo);
void mtp_pa_server_stream_fifo_free (struct mtp_pa_server_stream_fifo *fifo);

#endif
