/*
 * Copyright (C) 2005-2006 Stefan Strigler <steve@zeank.in-berlin.de>
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * this class reflects a session within http binding definition
 * 
 * @author Stefan Strigler <steve@zeank.in-berlin.de>
 */
public class Session {

	/**
	 * Default HTTP Content-Type header.
	 */
	public static final String DEFAULT_CONTENT = "text/xml; charset=utf-8";

	/**
	 * Longest allowable inactivity period (in seconds).
	 */
	public static final int MAX_INACTIVITY = 60;

	/**
	 * Maximum number of simultaneous requests allowed.
	 */
	public static final int MAX_REQUESTS = 2;

	/*
	 * ####### CONSTANTS #######
	 */

	/**
	 * Default value for longest time (in seconds) that the connection manager
	 * is allowed to wait before responding to any request during the session.
	 * This enables the client to prevent its TCP connection from expiring due
	 * to inactivity, as well as to limit the delay before it discovers any
	 * network failure.
	 */
	public static final int MAX_WAIT = 300;

	/**
	 * Shortest allowable polling interval (in seconds).
	 */
	public static final int MIN_POLLING = 2;

	/**
	 * Time to sleep on reading in MSEC.
	 */
	private static final int READ_TIMEOUT = 1;

	public static final int DEFAULT_XMPPPORT = 5222;

	protected static final String SESS_START = "starting";

	protected static final String SESS_ACTIVE = "active";

	protected static final String SESS_TERM = "term";

	/*
	 * ####### static #######
	 */

	private static Hashtable sessions = new Hashtable();

	private static TransformerFactory tff = TransformerFactory.newInstance();

	private static String createSessionID(int len) {
		String charlist = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

		Random rand = new Random();

		String str = new String();
		for (int i = 0; i < len; i++)
			str += charlist.charAt(rand.nextInt(charlist.length()));
		return str;
	}

	public static Session getSession(String sid) {
		return (Session) sessions.get(sid);
	}

	public static Enumeration getSessions() {
		return sessions.elements();
	}

	public static void stopSessions() {
		for (Enumeration e = sessions.elements(); e.hasMoreElements();)
			((Session) e.nextElement()).terminate();
	}

	/***************************************************************************
	 * END static
	 */

	private String authid; // stream id given by remote jabber server

	boolean authidSent = false;

	boolean streamFeatures = false;

	private String content = DEFAULT_CONTENT;

	private DocumentBuilder db;

	private int hold = MAX_REQUESTS - 1;

	private String inQueue = "";

	private BufferedReader br;

	private String key;

	private long lastActive;

	private long lastPoll = 0;

	// private int lastSentRid = 0;
	private OutputStreamWriter osw;

	// private TreeMap outQueue;
	private TreeMap responses;

	private String status = SESS_START;

	private String sid;

	public Socket sock;

	private String to;

	private DNSUtil.HostAddress host = null;

	private int wait = MAX_WAIT;

	private String xmllang = "en";

	private boolean reinit = false;

	private boolean secure = false;

	private boolean pauseForHandshake = false;

	private Pattern streamPattern;

	private Pattern stream10Test;

	private Pattern stream10Pattern;

	/**
	 * Create a new session and connect to jabber server host denoted by
	 * <code>route</code> or <code>to</code>.
	 * 
	 * @param to
	 *            domain of the server to connect to.
	 * @param route
	 *            optional hostname of the server to connect to (might be null).
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Session(String to, String route) throws UnknownHostException,
			IOException {
		this.to = to;

		int port = DEFAULT_XMPPPORT;

		this.setLastActive();

		try {
			this.db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {
		}

		// first, try connecting throught the 'route' attribute.
		if (route != null && !route.equals("")) {
			JHBServlet.dbg(
					"Trying to use 'route' attribute to open a socket...", 3);
			if (route.startsWith("xmpp:")) {
				route = route.substring("xmpp:".length());
			}

			int i;
			// has 'route' the optional port?
			if ((i = route.lastIndexOf(":")) != -1) {
				try {
					int p = Integer.parseInt(route.substring(i + 1));
					if (p >= 0 && p <= 65535) {
						port = p;
						JHBServlet.dbg(
								"...route attribute holds a valid port ("
										+ port + ").", 3);
					}
				} catch (NumberFormatException nfe) {
				}

				route = route.substring(0, i);
			}

			JHBServlet.dbg("Trying to open a socket to '" + route
					+ "', using port " + port + ".", 3);

			try {
				this.sock = new Socket(route, port);
			} catch (Exception e) {
				JHBServlet.dbg(
						"Failed to open a socket using the 'route' attribute",
						3);
				/*
				 * none of the exceptions possible should be reason to throw
				 * anything. If the socket isn't opened, we'll get a retry using
				 * the 'to' attribute anyway.
				 */
			}
		}

		// If no socket has been opened, try connecting trough the 'to'
		// attribute.
		if (this.sock == null || !this.sock.isConnected()) {
			JHBServlet.dbg("Trying to use 'to' attribute to open a socket...",
					3);
			host = DNSUtil.resolveXMPPServerDomain(to, DEFAULT_XMPPPORT);
			try {
				JHBServlet.dbg("Trying to open a socket to '" + host.getHost()
						+ "', using port " + host.getPort() + ".", 3);
				this.sock = new Socket(host.getHost(), host.getPort());
			} catch (UnknownHostException uhe) {
				JHBServlet.dbg(
						"Failed to open a socket using the 'to' attribute", 3);
				throw uhe;
			} catch (IOException ioe) {
				JHBServlet.dbg(
						"Failed to open a socket using the 'to' attribute", 3);
				throw ioe;
			}
		}

		// at this point, we either have a socket, or an exception has already
		// been thrown.

		try {
			if (this.sock.isConnected())
				JHBServlet.dbg("Succesfully connected to " + to, 2);

			// instantiate <stream>
			this.osw = new OutputStreamWriter(this.sock.getOutputStream(),
					"UTF-8");

			this.osw.write("<stream:stream to='" + this.to + "'"
					+ " xmlns='jabber:client' "
					+ " xmlns:stream='http://etherx.jabber.org/streams'"
					+ " version='1.0'" + ">");
			this.osw.flush();

			// create unique session id
			while (sessions.get(this.sid = createSessionID(24)) != null)
				;

			JHBServlet.dbg("creating session with id " + this.sid, 2);

			// register session
			sessions.put(this.sid, this);

			// create list of responses
			responses = new TreeMap();

			this.br = new BufferedReader(new InputStreamReader(this.sock
					.getInputStream(), "UTF-8"));

			this.streamPattern = Pattern.compile(
					".*<stream:stream[^>]*id=['|\"]([^'|^\"]+)['|\"][^>]*>.*",
					Pattern.DOTALL);

			this.stream10Pattern = Pattern
					.compile(
							".*<stream:stream[^>]*id=['|\"]([^'|^\"]+)['|\"][^>]*>.*(<stream.*)$",
							Pattern.DOTALL);

			this.stream10Test = Pattern.compile(
					".*<stream:stream[^>]*version=['|\"]1.0['|\"][^>]*>.*",
					Pattern.DOTALL);

			this.setStatus(SESS_ACTIVE);
		} catch (UnknownHostException uhe) {
			throw uhe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * Adds new response to list of known responses. Truncates list to allowed
	 * size.
	 * 
	 * @param r
	 *            the response to add
	 * @return this session object
	 */
	public synchronized Response addResponse(Response r) {
		while (this.responses.size() > 0
				&& this.responses.size() >= Session.MAX_REQUESTS)
			this.responses.remove(this.responses.firstKey());
		return (Response) this.responses.put(new Long(r.getRID()), r);
	}

	/**
	 * checks InputStream from server for incoming packets blocks until request
	 * timeout or packets available
	 * 
	 * @return nl - NodeList of incoming Nodes
	 */
	private int init_retry = 0;
	public NodeList checkInQ(long rid) throws IOException {

		NodeList nl = null;

		inQueue += this.readFromSocket(rid);

		JHBServlet.dbg("inQueue: " + inQueue, 2);

		if (init_retry < 1000 && (this.authid == null || this.isReinit()) && inQueue.length() > 0) {
			init_retry++;
			if (stream10Test.matcher(inQueue).matches()) {
				Matcher m = stream10Pattern.matcher(inQueue);
				if (m.matches()) {
					this.authid = m.group(1);
					inQueue = m.group(2);
					JHBServlet.dbg("inQueue: " + inQueue, 2);
					/*
					 * whether there are stream features present we need to
					 * filter them and strip (start)tls information
					 */
					streamFeatures = inQueue.length() > 0;
				} else {
					JHBServlet.dbg("failed to get stream features", 2);
					try {
						Thread.sleep(5);
					} catch (InterruptedException ie) {
					}
					return this.checkInQ(rid); // retry
				}
			} else {
				// legacy jabber stream
				Matcher m = streamPattern.matcher(inQueue);
				if (m.matches()) {
					this.authid = m.group(1);
				} else {
					JHBServlet.dbg("failed to get authid", 2);
					try {
						Thread.sleep(5);
					} catch (InterruptedException ie) {
					}
					return this.checkInQ(rid); // retry
				}
			}
			init_retry = 0; // reset
		}

		// try to parse it
		if (!inQueue.equals("")) {
			try {
				/*
				 * wrap inQueue with element so that multiple nodes can be
				 * parsed
				 */
				Document doc = null;
				if (streamFeatures)
					doc = db.parse(new InputSource(new StringReader("<doc>"
							+ inQueue + "</doc>")));
				else
					try {
						doc = db.parse(new InputSource(new StringReader(
								"<doc xmlns='jabber:client'>" + inQueue
										+ "</doc>")));
					} catch (SAXException sex) {
						try {
							// stream closed?
							doc = db.parse(new InputSource(new StringReader(
									"<stream:stream>" + inQueue)));
							this.terminate();
						} catch (SAXException sex2) {
						}
					}
				if (doc != null)
					nl = doc.getFirstChild().getChildNodes();
				if (streamFeatures) {// check for starttls
					for (int i = 0; i < nl.item(0).getChildNodes().getLength(); i++) {
						if (nl.item(0).getChildNodes().item(i).getNodeName()
								.equals("starttls")) {
							if (!this.isReinit()) {
								JHBServlet
										.dbg(
												"starttls present, trying to use it",
												2);
								this.osw
										.write("<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>");
								this.osw.flush();
								String response = this.readFromSocket(rid);
								JHBServlet.dbg(response, 2);

								try {
									SSLSocketFactory sslFact = (SSLSocketFactory) SSLSocketFactory
											.getDefault();
									SSLSocket tls;
									tls = (SSLSocket) sslFact.createSocket(
											this.sock, this.sock
													.getInetAddress()
													.getHostName(), this.sock
													.getPort(), false);
									tls
											.addHandshakeCompletedListener(new HandShakeFinished(
													this));
									this.pauseForHandshake = true;
									JHBServlet.dbg("initiating handshake");
									tls.startHandshake();
									try {
										while (this.pauseForHandshake) {
											JHBServlet.dbg(".");
											Thread.sleep(5);
										}
									} catch (InterruptedException ire) {
									}

									JHBServlet.dbg("TLS Handshake complete", 2);

									this.sock = tls;

									this.br = new SSLSocketReader(
											(SSLSocket) tls);

									this.osw = new OutputStreamWriter(tls
											.getOutputStream(), "UTF-8");

									this.inQueue = ""; // reset
									this.setReinit(true);
									this.osw
											.write("<stream:stream to='"
													+ this.to
													+ "'"
													+ " xmlns='jabber:client' "
													+ " xmlns:stream='http://etherx.jabber.org/streams'"
													+ " version='1.0'" + ">");
									this.osw.flush();

									return this.checkInQ(rid);
								} catch (Exception ssle) {
									JHBServlet.dbg("STARTTLS failed: "
											+ ssle.toString(), 1);
									this.setReinit(false);
									if (this.isSecure()) {
										JHBServlet
												.dbg(
														"secure connection requested but failed",
														2);
										throw new IOException();
									}
									if (this.sock.isClosed()) {
										JHBServlet.dbg("socket closed", 1);
										// reconnect
										this.sock = new Socket(
												this.sock.getInetAddress()
														.getHostName(),
												this.sock.getPort());
										this.br = new BufferedReader(
												new InputStreamReader(this.sock
														.getInputStream(),
														"UTF-8"));
										this.osw = new OutputStreamWriter(
												this.sock.getOutputStream(),
												"UTF-8");

										this.inQueue = ""; // reset
										this.setReinit(true);

										this.osw
												.write("<stream:stream to='"
														+ this.to
														+ "'"
														+ " xmlns='jabber:client' "
														+ " xmlns:stream='http://etherx.jabber.org/streams'"
														+ " version='1.0'"
														+ ">");
										this.osw.flush();

										return this.checkInQ(rid);
									}
								}
							} else
								nl.item(0).removeChild(
										nl.item(0).getChildNodes().item(i));
						}
					}
				}
				inQueue = ""; // reset!
			} catch (SAXException sex3) { /* skip this */
				this.setReinit(false);
				JHBServlet.dbg("failed to parse inQueue: " + inQueue + "\n"
						+ sex3.toString(), 1);
				return null;
			}
		}
		this.setReinit(false);
		this.setLastActive();
		return nl;
	}

	private class HandShakeFinished implements
			javax.net.ssl.HandshakeCompletedListener {
		private Session sess;

		public HandShakeFinished(Session sess) {
			this.sess = sess;
		}

		public void handshakeCompleted(
				javax.net.ssl.HandshakeCompletedEvent event) {

			JHBServlet.dbg("startTLS: Handshake is complete", 2);

			this.sess.pauseForHandshake = false;
			return;
		}
	}

	/**
	 * Checks whether given request ID is valid within context of this session.
	 * 
	 * @param rid
	 *            Request ID to be checked
	 * @return true if rid is valid
	 */
	public synchronized boolean checkValidRID(long rid) {
		try {
			if (rid <= ((Long) this.responses.lastKey()).longValue()
					+ MAX_REQUESTS
					&& rid >= ((Long) this.responses.firstKey()).longValue())
				return true;
			else {
				JHBServlet.dbg("invalid request id: " + rid + " (last: "
						+ ((Long) this.responses.lastKey()).longValue() + ")",
						1);
				return false;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getAuthid() {
		return this.authid;
	}

	public String getContent() {
		return this.content;
	}

	public int getHold() {
		return this.hold;
	}

	/**
	 * @return Returns the key.
	 */
	public synchronized String getKey() {
		return key;
	}

	/**
	 * @return Returns the lastActive.
	 */
	public synchronized long getLastActive() {
		return lastActive;
	}

	/**
	 * @return Returns the lastPoll.
	 */
	public synchronized long getLastPoll() {
		return lastPoll;
	}

	/**
	 * lookup response for given request id
	 * 
	 * @param rid
	 *            Request id associated with response
	 * @return the response if found, null otherwise
	 */
	public synchronized Response getResponse(long rid) {
		return (Response) this.responses.get(new Long(rid));
	}

	public String getSID() {
		return this.sid;
	}

	/*
	 * ######## getters #########
	 */

	public String getTo() {
		return this.to;
	}

	public int getWait() {
		return this.wait;
	}

	public String getXMLLang() {
		return this.xmllang;
	}

	public synchronized int numPendingRequests() {
		int num_pending = 0;
		Iterator it = this.responses.values().iterator();
		while (it.hasNext()) {
			Response r = (Response) it.next();
			if (!r.getStatus().equals(Response.STATUS_DONE))
				num_pending++;
		}
		return num_pending;
	}

	private long lastDoneRID;

	public synchronized long getLastDoneRID() {
		return this.lastDoneRID;
	}

	/**
	 * reads from socket
	 * 
	 * @return string that was read
	 */
	private String readFromSocket(long rid) throws IOException {
		String retval = "";
		char buf[] = new char[16];
		int c = 0;

		Response r = this.getResponse(rid);

		while (!this.sock.isClosed() && !this.isStatus(SESS_TERM)) {
			this.setLastActive();
			try {
				if (this.br.ready()) {
					while (this.br.ready()
							&& (c = this.br.read(buf, 0, buf.length)) >= 0)
						retval += new String(buf, 0, c);
					break; // got sth. to send
				} else {
					if ((this.hold == 0 && r != null && System
							.currentTimeMillis()
							- r.getCDate() > 200)
							||
							/*
							 * makes polling clients feel a little bit more
							 * responsive
							 */
							(this.hold > 0 && ((r != null && System
									.currentTimeMillis()
									- r.getCDate() >= this.getWait() * 1000)
									|| this.numPendingRequests() > this
											.getHold() || !retval.equals("")))) {
						JHBServlet.dbg("readFromSocket done for " + rid, 3);
						break; // time exeeded
					}

					try {
						Thread.sleep(READ_TIMEOUT); // wait for incoming
						// packets
					} catch (InterruptedException ie) {
						System.err.println(ie.toString());
					}
				}
			} catch (IOException e) {
				System.err.println("Can't read from socket");
				this.terminate();
			}
		}

		if (this.sock.isClosed()) {
			throw new IOException();
		}
		return retval;
	}

	/**
	 * sends all nodes in list to remote jabber server make sure that nodes get
	 * sent in requested order
	 * 
	 * @param nl
	 *            list of nodes to send
	 * @return the session itself
	 */

	public Session sendNodes(NodeList nl) {
		// build a string
		String out = "";
		StreamResult strResult = new StreamResult();

		try {
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty("omit-xml-declaration", "yes");
			// loop list
			for (int i = 0; i < nl.getLength(); i++) {
				strResult.setWriter(new StringWriter());
				tf.transform(new DOMSource(nl.item(i)), strResult);
				String tStr = strResult.getWriter().toString();
				out += tStr;
			}
		} catch (Exception e) {
			JHBServlet.dbg("XML.toString(Document): " + e, 1);
		}

		try {
			if (this.isReinit()) {
				JHBServlet.dbg("Reinitializing Stream!", 2);
				this.osw.write("<stream:stream to='" + this.to + "'"
						+ " xmlns='jabber:client' "
						+ " xmlns:stream='http://etherx.jabber.org/streams'"
						+ " version='1.0'" + ">");
			}
			this.osw.write(out);
			this.osw.flush();
		} catch (IOException ioe) {
			JHBServlet.dbg(this.sid + " failed to write to stream", 1);
		}

		return this;
	}

	public Session setContent(String content) {
		this.content = content;
		return this;
	}

	/*
	 * ######## setters #########
	 */

	public Session setHold(int hold) {
		if (hold < MAX_REQUESTS && hold >= 0)
			this.hold = hold;
		return this;
	}

	/**
	 * @param key
	 *            The key to set.
	 */
	public synchronized void setKey(String key) {
		this.key = key;
	}

	/**
	 * set lastActive to current timestamp.
	 */
	public synchronized void setLastActive() {
		this.lastActive = System.currentTimeMillis();
	}

	public synchronized void setLastDoneRID(long rid) {
		this.lastDoneRID = rid;
	}

	/**
	 * set lastPoll to current timestamp.
	 */
	public synchronized void setLastPoll() {
		this.lastPoll = System.currentTimeMillis();
	}

	public int setWait(int wait) {
		if (wait < 0)
			wait = 0;
		if (wait > MAX_WAIT)
			wait = MAX_WAIT;
		this.wait = wait;
		return wait;
	}

	public Session setXMLLang(String xmllang) {
		this.xmllang = xmllang;
		return this;
	}

	/**
	 * @return Returns the reinit.
	 */
	public synchronized boolean isReinit() {
		return reinit;
	}

	/**
	 * @return the secure
	 */
	public synchronized boolean isSecure() {
		return secure;
	}

	/**
	 * @param reinit
	 *            The reinit to set.
	 */
	public synchronized void setReinit(boolean reinit) {
		this.reinit = reinit;
	}

	public synchronized void setStatus(String status) {
		this.status = status;
	}

	public synchronized boolean isStatus(String status) {
		return (this.status == status);
	}

	/**
	 * kill this session
	 * 
	 */
	public void terminate() {
		JHBServlet.dbg("terminating session " + this.getSID(), 2);
		this.setStatus(SESS_TERM);
		synchronized (this.sock) {
			if (!this.sock.isClosed()) {
				try {
					this.osw.write("</stream:stream>");
					this.osw.flush();
					this.sock.close();
				} catch (IOException ie) {
				}
			}
			this.sock.notifyAll();
		}
		sessions.remove(this.sid);
	}

	/**
	 * @param secure
	 *            the secure to set
	 */
	public synchronized void setSecure(boolean secure) {
		this.secure = secure;
	}

}
