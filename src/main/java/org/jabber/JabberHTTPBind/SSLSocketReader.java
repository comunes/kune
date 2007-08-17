/*
 * Created on Apr 11, 2004
 */
package org.jabber.JabberHTTPBind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLSocket;

/**
 * @author pau'ie
 *
 * This class extends BufferedReader to add expected functionality to the ready() method,
 * when this class is used on an SSLSocket.  An SSLSocket's inputStream will return 0 upon
 * a call to available() until a read is performed.
 * 
 * This means that if you construct a bufferedreader for an SSLSocket's inputstream, the ready()
 * method will always return false.  (This has something to do with just-in-time
 * decryption.  Might be a bug or a feature)  This class overwrites ready() to force a quick read
 * to see if the stream has any data available.
 * 
 * Please send comments on whether this introduces security risks to paul@sims.berkeley.edu
 */
public class SSLSocketReader extends BufferedReader
{	
	private SSLSocket sock;
	
	public SSLSocketReader( SSLSocket sock) throws IOException
	{
		super( new InputStreamReader( sock.getInputStream(), "UTF-8"));
		this.sock = sock;
	}
	
	/**
	 * HACK!
	 * This method does a quick read from the input stream, to see if data is
	 * actually available, then resets to the point before the read.
	 */
	public boolean ready()
	{
		int oldTimeout;
		try {
			oldTimeout = sock.getSoTimeout();
			sock.setSoTimeout( 10 );
			
			mark(1);
			
			try {
				read();
			} catch (SocketTimeoutException e) {
				sock.setSoTimeout(oldTimeout);
				return false;
			}
			
			reset();
			sock.setSoTimeout(oldTimeout);
			
			return true;
			
		} catch (SocketException e1) {
			throw new RuntimeException( "SSLSocketReader unable to set socket timeout: \n" + e1);
		} catch (IOException e) {
			throw new RuntimeException( "SSLSocketReader unable to access inputstream: \n" + e);
		}
	}
}
