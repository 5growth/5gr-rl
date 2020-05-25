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
#include <fcntl.h>

#include "rl_ra_server_stream.h"

/** A macro to check pointers in order to not
  go behind the allocated mem block
  S -- lrm_stream reference
  Z -- size of data to be written
*/

#define CHECK_SIZE(S, Z) \
	if (((S)->putp + (Z)) > (S)->size) \
           (Z) = (S)->size - (S)->putp;


/** Stream is fixed length buffer for network output/input. */

/** Make rl_ra_server_stream buffer.
 * @param size length buffer.
 * @return stream structure.
 */
struct rl_ra_server_stream * rl_ra_server_stream_new (size_t size)
{
	/** check values */
	g_assert(size > 0);

	struct rl_ra_server_stream *stream = NULL;

	stream = g_malloc0 (sizeof (struct rl_ra_server_stream));
	if (stream == NULL)
	{
		DEBUG_RL_RA ("%s memory failed\n",__FUNCTION__);
		exit(-1);
	}

	stream->data = g_malloc0 (size);
	if (stream->data == NULL)
	{
		DEBUG_RL_RA ("%s memory failed\n", __FUNCTION__);
		exit(-1);
	}
	stream->size = size;

	/** check values */
	g_assert(stream != NULL);

	return stream;
}

/** Free the stream structure now.
 * @param stream rl_ra_server_stream structure.
 */

void rl_ra_server_stream_free (struct rl_ra_server_stream *stream)
{
	/** check values */
	g_assert(stream != NULL);

	/** free data */
  	g_free(stream->data);
  	g_free(stream);
}

/**
 * Get the get pointer
 * @param stream struct rl_ra_server_stream
 * @return the getp of the stream.
 */
gulong rl_ra_server_stream_get_getp (struct rl_ra_server_stream *stream)
{
        /** check values*/
        g_assert(stream != NULL);

        return stream->getp;
}

/**
 * Get the put pointer
 * @param stream rl_ra_server_stream structure.
 * @return the putp of the stream.
 */
gulong rl_ra_server_stream_get_putp (struct rl_ra_server_stream *stream)
{
	/** check values*/
	g_assert(stream != NULL);

	return stream->putp;
}


/**
 * Get the endp
 * @param stream rl_ra_server_stream structure.
 * @return the endp of the stream.
 */
gulong rl_ra_server_stream_get_endp (struct rl_ra_server_stream *stream)
{
	/** check values*/
	g_assert(stream != NULL);

	return stream->endp;
}

/**
 * Get size of the stream.
 * @param stream rl_ra_server_stream structure.
 * @return size of the stream.
 */
gulong rl_ra_server_stream_get_size (struct rl_ra_server_stream *stream)
{
	/** check values*/
	g_assert(stream != NULL);

	return stream->size;
}

/** Stream structre' rl_ra_server_stream pointer related functions.  */

/**
 * Set the getp in the stream.
 * @param stream struct rl_ra_server_stream.
 * @param pos position.
 */
void rl_ra_server_stream_set_getp (struct rl_ra_server_stream *stream, gulong pos)
{
	/** check values*/
	g_assert(stream != NULL);
	g_assert(pos >= 0);

	stream->getp = pos;

	/** check values*/
	g_assert(stream->getp >= 0);
}

/**
 * Set the putp in the stream
 * @param stream struct rl_ra_server_stream.
 * @param pos position.
 */
void rl_ra_server_stream_set_putp (struct rl_ra_server_stream *stream, gulong pos)
{
	/** check values*/
	g_assert(stream != NULL);
	g_assert(pos >= 0);

	stream->putp = pos;

	/** check values*/
	g_assert(stream->putp >= 0);
}

/** Forward pointer.
 * @param stream struct rl_ra_server_stream.
 * @param size  size to be forwarded.
 */
void rl_ra_server_stream_forward (struct rl_ra_server_stream *stream, gint size)
{
	/** check values*/
	g_assert(stream != NULL);
	g_assert(size >= 0);

	stream->getp += size;

	/** check values*/
	g_assert(stream->getp >= 0);
}


/** Copy from rl_ra_server_stream to destination.
 * @param dst destination.
 * @param stream struct rl_ra_server_stream.
 * @param size size to be copied.
 */
void rl_ra_server_stream_get (void *dst, struct rl_ra_server_stream *stream, size_t size)
{
	/** check values*/
	g_assert(stream != NULL);
	g_assert(size >= 0);

	memcpy (dst, stream->data + stream->getp, size);
	stream->getp += size;

	/** check values*/
	g_assert(stream != NULL);
	g_assert(stream->getp >= 0);
}

/** Get next character from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @return character.
 */
guchar rl_ra_server_stream_getc (struct rl_ra_server_stream *stream)
{
	/** check values*/
	g_assert(stream != NULL);

	guchar c;

	c = stream->data[stream->getp];
	stream->getp++;

	/** check values*/
	g_assert(stream != NULL);

	return c;
}

/** Get next character from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param from from what position get the next character.
 * @return character of that position.
 */
guchar rl_ra_server_stream_getc_from (struct rl_ra_server_stream *stream, gulong from)
{
	/** check values*/
	g_assert(stream != NULL);

	guchar c;

	c = stream->data[from];

	/** check values*/
	g_assert(stream != NULL);

	return c;
}

/** Get next word from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @return the next word.
 */
guint16 rl_ra_server_stream_getw (struct rl_ra_server_stream *stream)
{
	/** check values*/
	g_assert(stream != NULL);

  	guint16 word;


  	word = stream->data[stream->getp++] << 8;
  	word |= stream->data[stream->getp++];

	/** check values*/
	g_assert(stream != NULL);

  	return word;
}

/** Get next word from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param from from where to get the next word.
 * @return the next word.
 */
guint16 rl_ra_server_stream_getw_from (struct rl_ra_server_stream *stream, gulong from)
{
	/** check values*/
	g_assert(stream != NULL);
	g_assert(from >= 0);

  	guint16 word;

  	word = stream->data[from++] << 8;
  	word |= stream->data[from];

	/** check values*/
	g_assert(stream != NULL);

  	return word;
}

/** Get next long word from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @return the next long word.
 */
guint32 rl_ra_server_stream_getl (struct rl_ra_server_stream *stream)
{
  guint32 long_word;

	/** check values*/
	g_assert(stream != NULL);

  	long_word  = stream->data[stream->getp++] << 24;
  	long_word |= stream->data[stream->getp++] << 16;
  	long_word |= stream->data[stream->getp++] << 8;
  	long_word |= stream->data[stream->getp++];

	/** check values*/
	g_assert(stream != NULL);
	g_assert(long_word >= 0);

  	return long_word;
}

/**
 * Get next long word from the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @return the next long word.
 */
guint32 rl_ra_server_stream_get_ipv4 (struct rl_ra_server_stream *stream)
{
  guint32 long_word;

	/** check values*/
	g_assert(stream != NULL);

	/** are copy memory */
  	memcpy (&long_word, stream->data + stream->getp, 4);

  	stream->getp += 4;

	/** check values*/
	g_assert(stream != NULL);
	g_assert(long_word >= 0);

  	return long_word;
}

/**
 * Copy to source to rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param source source.
 * @param size size.
 */
void rl_ra_server_stream_put (struct rl_ra_server_stream *stream, void *source, size_t size)
{

	/** check values */
	g_assert(stream != NULL);
	g_assert(source != NULL);
	g_assert(size >= 0);


  	CHECK_SIZE(stream, size);

  	if (source)
    		memcpy (stream->data + stream->putp, source, size);
  	else
    		memset (stream->data + stream->putp, 0, size);

  	stream->putp += size;

  	if (stream->putp > stream->endp)
    		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(source != NULL);
	g_assert(size >= 0);
}

/**
 * Put character to the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param c character.
 * @return 1 sucess.
 */
gint rl_ra_server_stream_putc (struct rl_ra_server_stream *stream, guchar c)
{
	/** check values */
	g_assert(stream != NULL);

  	if (stream->putp >= stream->size)
		return 0;

  	stream->data[stream->putp] = c;
  	stream->putp++;

  	if (stream->putp > stream->endp)
    		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);

  return 1;
}

/** Put word to the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param word word.
 * @return 2 bytes.
 */
gint rl_ra_server_stream_putw (struct rl_ra_server_stream *stream, guint16 word)
{
	
	/** check values */
	g_assert(stream != NULL);

	
  	if ((stream->size - stream->putp) < 2)
	       	return 0;

  	stream->data[stream->putp++] = (guchar)(word >>  8);
  	stream->data[stream->putp++] = (guchar) word;

  	if (stream->putp > stream->endp)
    		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);

  return 2;
}

/** Put long word to the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream
 * @param int32.
 * @return 4 bytes.
 */
gint rl_ra_server_stream_putl (struct rl_ra_server_stream *stream, guint32 long_word)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(long_word >= 0);

  	if ((stream->size - stream->putp) < 4)
		return 0;

  	stream->data[stream->putp++] = (guchar)(long_word >> 24);
  	stream->data[stream->putp++] = (guchar)(long_word >> 16);
  	stream->data[stream->putp++] = (guchar)(long_word >>  8);
  	stream->data[stream->putp++] = (guchar)long_word;

  	if (stream->putp > stream->endp)
    		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);
	g_assert(long_word >= 0);

	return 4;
}

/**
 * Put character in the stream.
 * @param stream struct rl_ra_server_stream.
 * @param putp put pointer.
 * @param c character.
 * @return 1.
 */
gint rl_ra_server_stream_putc_at (struct rl_ra_server_stream *stream, gulong putp, guchar c)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(putp >= 0);

  	stream->data[putp] = c;

	/** check values */
	g_assert(stream != NULL);

  return 1;
}

/**
 * Put word in the stream.
 * @param stream struct rl_ra_server_stream.
 * @param putp put pointer length.
 * @param word word.
 * @return length word.
 */
gint rl_ra_server_stream_putw_at (struct rl_ra_server_stream *stream, gulong putp, guint16 word)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(putp >= 0);

  	stream->data[putp] = (guchar)(word >>  8);
  	stream->data[putp + 1] = (guchar) word;

	/** check values */
	g_assert(stream != NULL);

  	return 2;
}

/**
 * Put a long word in the stream.
 * @param stream struct rl_ra_server_stream.
 * @param putp put pointer.
 * @param long_word long word.
 * @return length of long word.
 */
gint rl_ra_server_stream_putl_at (struct rl_ra_server_stream *stream, gulong putp, guint32 long_word)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(putp >= 0);
	g_assert(long_word >= 0);

 	stream->data[putp] = (guchar)(long_word >> 24);
  	stream->data[putp + 1] = (guchar)(long_word >> 16);
  	stream->data[putp + 2] = (guchar)(long_word >>  8);
  	stream->data[putp + 3] = (guchar)long_word;

	/** check values */
	g_assert(stream != NULL);

  return 4;
}

/** Put long word to the rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 * @param long_word long word.
 * @return size of the long word (4 bytes).
 */
gint rl_ra_server_stream_put_ipv4 (struct rl_ra_server_stream *stream, guint32 long_word)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(long_word >= 0);

	if ((stream->size - stream->putp) < 4)
			return 0;

	memcpy (stream->data + stream->putp, &long_word, 4);

	stream->putp += 4;

	if (stream->putp > stream->endp)
			stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);
	g_assert(stream->size >= 0);

	return 4;
}

/** Put long word to the rl_ra_server_stream.
 *
 * @param stream struct rl_ra_server_stream.
 * @param addr struct in_addr
 * @return size of the long word (4 bytes).
 */
gint rl_ra_server_stream_put_in_addr (struct rl_ra_server_stream *stream, struct in_addr *addr)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(addr != NULL);

	if ((stream->size - stream->putp) < 4)
			return 0;

	memcpy (stream->data + stream->putp, addr, 4);

	stream->putp += 4;

	if (stream->putp > stream->endp)
		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);


  return 4;
}



/**
 * Undo what it was put at the end of the rl_ra_server_stream.
 *
 * @param stream struct rl_ra_server_stream.
 * @param size size.
 */
void rl_ra_server_stream_put_undo (struct rl_ra_server_stream *stream, gulong size)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(size >= 0);

	stream->putp -= size;
	stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);
}

/**
 * Read channel
 * @param channel to be read
 * @param ptr pointer to be read.
 * @param nbytes number of bytes to be read.
 * @return number of bytes that was read.
 */
gint read_channel (GIOChannel *channel, guchar *ptr, gint nbytes)
{
 /** check values */
  g_assert(channel != NULL);
  g_assert(ptr != NULL);
  g_assert(nbytes >= 0);	

  /** get the file descriptor */
  gint fd;
  fd = g_io_channel_unix_get_fd (channel);

  gsize nread;
  gint nleft;
  GError * error = NULL;
  GIOStatus status;

  nleft = nbytes;
  
  // Set blocking
  int flags = fcntl(fd, F_GETFL, 0);
  fcntl(fd, F_SETFL, flags &= ~O_NONBLOCK);

  while (nleft > 0)
  {

#if 1
	  status = g_io_channel_read_chars (channel, (void *)ptr, nleft, &nread, &error);
	  if (status !=  G_IO_STATUS_NORMAL )
	  {
		  DEBUG_RL_RA ("gio-test: ...from %d: G_IO_STATUS_%s\n", fd,
				  (status == G_IO_STATUS_AGAIN ? "AGAIN" :
				  (status == G_IO_STATUS_EOF ? "EOF" :
				  (status == G_IO_STATUS_ERROR ? "ERROR" : "???"))));

		  return -1;
	  }
#endif

	  if (nread < 0)
	  {
		  return (nread);
	  }
	  else
	  {
		  if (nread == 0)
			  break;
	  }

	  nleft -= nread;
	  ptr += nread;
  }

  /** check values */
  g_assert(channel != NULL);
  g_assert(ptr != NULL);
  g_assert(nleft >= 0);
  g_assert(nbytes >= 0);

  return nbytes - nleft;

}

/**
 * This routine read the stream.
 *
 * @param stream struct rl_ra_server_stream.
 * @param channel channel to use
 * @param size size that should be read.
 * @return number of bytes read.
 */
gint rl_ra_server_stream_read (struct rl_ra_server_stream *stream, GIOChannel *channel, size_t size)
{

    /** check values */
    g_assert(stream != NULL);
    g_assert(channel != NULL);
    g_assert(size > 0);
    
    gint nbytes = 0;

    nbytes = read_channel (channel, (guchar *)(stream->data + stream->putp), size);
    if (nbytes > 0)
    {
	    stream->putp += nbytes;
	    stream->endp += nbytes;
    }

    return nbytes;  
}


/** Write data to buffer.
 *
 * @param stream rl_ra_server_stream stream.
 * @param ptr buffer
 * @param size bytes to be written.
 * @return bytes that were written.
 */
gint rl_ra_server_stream_write (struct rl_ra_server_stream *stream, guchar *ptr, size_t size)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(ptr != NULL);
	g_assert(size >= 0);

	CHECK_SIZE(stream, size);

	memcpy (stream->data + stream->putp, ptr, size);

	stream->putp += size;

	if (stream->putp > stream->endp)
		stream->endp = stream->putp;

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);
	g_assert(ptr != NULL);
	g_assert(size >= 0);
  return size;
}

/**
 * rl_ra_server_stream stream pointer.
 *
 * @param stream struct rl_ra_server_stream.
 * @return current read pointer.
 */
guchar * rl_ra_server_stream_pnt (struct rl_ra_server_stream *stream)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->data != NULL);
	g_assert(stream->getp >= 0);

	return (guchar *)(stream->data + stream->getp);
}

/**
 * Check does this rl_ra_server_stream empty?
 *
 * @param stream struct rl_ra_server_stream
 * @return success 0 or not 1.
 */
gint rl_ra_server_stream_empty (struct rl_ra_server_stream *stream)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->getp >= 0);
	g_assert(stream->endp >= 0);

	if (stream->putp == 0 && stream->endp == 0 && stream->getp == 0)
		return 1;
	else
		return 0;
}

/**
 * Reset rl_ra_server_stream.
 * @param stream struct rl_ra_server_stream.
 */
void rl_ra_server_stream_reset (struct rl_ra_server_stream *stream)
{
	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->putp >= 0);
	g_assert(stream->endp >= 0);
	g_assert(stream->endp >= 0);

	/** reset */
	stream->putp = 0;
	stream->endp = 0;
	stream->getp = 0;
}

/**
 * Write rl_ra_server_stream contents to the file discriptor.
 *
 * @param stream struct rl_ra_server_stream stream.
 * @param fd file descriptor.
 * @return number of bytes
 */
gint rl_ra_server_stream_flush (struct rl_ra_server_stream *stream, gint fd)
{
    /** check values */
	g_assert(stream != NULL);
	g_assert(stream->data != NULL);
	g_assert(stream->getp >= 0);
	g_assert(stream->endp >= 0);
	
	gint nbytes;
	nbytes = write (fd, stream->data + stream->getp, stream->endp - stream->getp);

	/** check values */
	g_assert(stream != NULL);
	g_assert(stream->data != NULL);
	g_assert(stream->getp >= 0);
	g_assert(stream->endp >= 0);


	return nbytes;
}



