/* JabberHTTPBind
 * 
 * Copyright (C) 2006 Stefan Strigler <steve@zeank.in-berlin.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 */
package org.jabber.JabberHTTPBind;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stefan Strigler <steve@zeank.in-berlin.de>
 */
public class Stream {

	private static final String STREAM_HEAD = "<stream:stream xmlns:stream='http://etherx.jabber.org/streams' xmlns='jabber:client' ";

	private static final String STREAM_TAIL = "</stream:stream>";

	private String to = "localhost";

	private String from = "localhost";

	private String version;
	
	private String id;

	private String error = "";

	/**
	 * 
	 */
	public Stream(String to) {
		this(to, null);
	}

	public Stream(String to, String version) {
		this.to = to;
		this.version = version;
	}

	/**
	 * @return Returns the from.
	 */
	public synchronized String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public synchronized void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return Returns the version.
	 */
	public synchronized String getVersion() {
		return version;
	}

	/**
	 * @return Returns the to.
	 */
	public synchronized String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public synchronized void setTo(String to) {
		this.to = to;
	}

	/* END GETTERS/SETTERS */
	
	/* START PUBLIC METHODS */
	
	/**
	 * @return Returns the id.
	 */
	public synchronized String getId() {
		return id;
	}

	public void init() {
		this.init(to, 5222, false);
	}

	public void init(String host, int port) {
		this.init(host, port, false);
	}

	public void init(String host, int port, boolean secure) {
		String streamxml = getStreamHeadXML();
	}

	public void reinit() {
		String streamxml = getStreamHeadXML();
	}

	/* END PUBLIC METHODS*/
	
	private String getStreamHeadXML() {
		String streamxml = STREAM_HEAD + " to=\"" + to + "\"";
		if (version != null)
			streamxml += " version=\"" + version + "\"";
		streamxml += ">";
		return streamxml;
	}
	
	private static Pattern streamIDPattern = Pattern.compile(".*\\<stream\\:stream.*id=['\"]([^'\"]+)['\"][^\\>]*\\>(.*)");
	
	private void extractID(String xml) {
		Matcher m = streamIDPattern.matcher(xml);
		if (m.matches()) 
			this.setId(m.group(1));
	}
	
	/**
	 * @param id The id to set.
	 */
	private synchronized void setId(String id) {
		this.id = id;
	}

	
}
