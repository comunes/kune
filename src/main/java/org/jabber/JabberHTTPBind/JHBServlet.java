/* JabberHTTPBind - An implementation of JEP-0124 (HTTP Binding)
 * Please see http://www.jabber.org/jeps/jep-0124.html for details.
 *
 * Copyright (c) 2005-2006 Stefan Strigler <steve@zeank.in-berlin.de>
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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An implementation of JEP-0124 (HTTP Binding). See
 * http://www.jabber.org/jeps/jep-0124.html for details.
 * 
 * @author Stefan Strigler <steve@zeank.in-berlin.de>
 */

public final class JHBServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String APP_VERSION = "1.0";

	public static final String APP_NAME = "Jabber HTTP Binding Servlet";

	public static final boolean DEBUG = false;

	public static final int DEBUG_LEVEL = 1;

	private DocumentBuilder db;

	private Janitor janitor;

	public void init() throws ServletException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			log("failed to create DocumentBuilderFactory", e);
		}

		janitor = new Janitor(); // cleans up sessions
		new Thread(janitor).start();
	}

	public void destroy() {
		Session.stopSessions();
		janitor.stop();
	}

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
					.toLowerCase().substring(1, 3));
		}
		return sb.toString();
	}

	public static String sha1(String message) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			return hex(sha.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static void dbg(String msg) {
		dbg(msg, 0);
	}

	public static void dbg(String msg, int lvl) {
		if (!DEBUG)
			return;
		if (lvl > DEBUG_LEVEL)
			return;
		System.err.println("[" + lvl + "] " + msg);
	}

	/**
	 * We only need to respond to POST requests ...
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are producing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		long rid = 0;
		try {
			/*
			 * parse request
			 */
			Document doc;
			synchronized (db) {
				doc = db.parse(request.getInputStream());
			}

			Node rootNode = doc.getDocumentElement();
			if (rootNode == null || !rootNode.getNodeName().equals("body"))
				// not a <body>-tag - don't know what to do with it
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			else {

				/*
				 * we got a <body /> request - let's look if there something
				 * useful we could do with it
				 */

				NamedNodeMap attribs = rootNode.getAttributes();

				if (attribs.getNamedItem("sid") != null) {

					/***********************************************************
					 * lookup existing session ***
					 */

					Session sess = Session.getSession(attribs.getNamedItem(
							"sid").getNodeValue());

					if (sess != null) {
						dbg("incoming request for " + sess.getSID(), 3);

						/*******************************************************
						 * check if request is valid
						 */
						// check if rid valid
						if (attribs.getNamedItem("rid") == null) {
							// rid missing
							dbg("rid missing", 1);
							response
									.sendError(HttpServletResponse.SC_NOT_FOUND);
							sess.terminate();
						} else {
							try {
								rid = Integer.parseInt(attribs.getNamedItem(
										"rid").getNodeValue());
							} catch (NumberFormatException e) {
								dbg("rid not a number", 1);
								response
										.sendError(HttpServletResponse.SC_BAD_REQUEST);
								return;
							}
							Response r = sess.getResponse(rid);
							if (r != null) { // resend
								dbg("resend rid " + rid, 2);
								r.send();
								return;
							}
							if (!sess.checkValidRID(rid)) {
								dbg("invalid rid " + rid, 1);
								response
										.sendError(HttpServletResponse.SC_NOT_FOUND);
								sess.terminate();
								return;
							}
						}

						dbg("found valid rid " + rid, 3);

						/* too many simultaneous requests? */
						if (sess.numPendingRequests() >= Session.MAX_REQUESTS) {
							dbg("too many simultaneous requests: "
									+ sess.numPendingRequests(), 1);
							response
									.sendError(HttpServletResponse.SC_FORBIDDEN);
							// no pardon - kick it
							sess.terminate();
							return;
						}

						/*******************************************************
						 * we got a valid request start processing it
						 */

						Response jresp = new Response(response, db
								.newDocument());
						jresp.setRID(rid);
						jresp.setContentType(sess.getContent());
						sess.addResponse(jresp);

						try {
							synchronized (sess.sock) {
								/* wait till it's our turn */

								/*
								 * NOTE: This only works when having
								 * MAX_REQUESTS set to 1 or 2 Otherwise we would
								 * fail on the condition that incoming data from
								 * the client has to be forwarded as soon as
								 * possible (the request might be pending
								 * waiting for others to timeout first)
								 */

								/*
								 * wait 'till we are the lowest rid that's
								 * pending
								 */

								long lastrid = sess.getLastDoneRID();
								while (rid != lastrid + 1) {
									if (sess.isStatus(Session.SESS_TERM)) {
										dbg("session terminated for " + rid, 1);
										response
												.sendError(HttpServletResponse.SC_NOT_FOUND);
										sess.sock.notifyAll();
										return;
									}
									try {
										dbg(rid + " waiting for "
												+ (lastrid + 1), 2);
										sess.sock.wait();
										dbg("bell for " + rid, 2);
										lastrid = sess.getLastDoneRID();
									} catch (InterruptedException e) {
									}
								}

								dbg("handling response " + rid, 3);
								/*
								 * check key
								 */
								String key = sess.getKey();
								if (key != null) {
									dbg("checking keys for " + rid, 3);
									if (attribs.getNamedItem("key") == null
											|| !sha1(
													attribs.getNamedItem("key")
															.getNodeValue())
													.equals(key)) {
										dbg("Key sequence error", 1);
										response
												.sendError(HttpServletResponse.SC_NOT_FOUND);
										sess.terminate();
										return;
									}
									if (attribs.getNamedItem("newkey") != null)
										sess.setKey(attribs.getNamedItem(
												"newkey").getNodeValue());
									else
										sess.setKey(attribs.getNamedItem("key")
												.getNodeValue());
									dbg("key valid for " + rid, 3);
								}

								if (attribs.getNamedItem("xmpp:restart") != null) {
									dbg("XMPP RESTART", 2);
									sess.setReinit(true);
								}

								/*
								 * check if we got sth to forward to remote
								 * jabber server
								 */
								if (rootNode.hasChildNodes())
									sess.sendNodes(rootNode.getChildNodes());
								else {
									/*
									 * check if polling too frequently only
									 * empty polls are considered harmfull
									 */
									long now = System.currentTimeMillis();
									if (now - sess.getLastPoll() < Session.MIN_POLLING * 1000) {
										// indeed (s)he's violating our rules
										dbg("polling too frequently! [now:"
												+ now + ", last:"
												+ sess.getLastPoll() + "("
												+ (now - sess.getLastPoll())
												+ ")]", 1);

										response
												.sendError(HttpServletResponse.SC_FORBIDDEN);
										// no pardon - kick it
										sess.terminate();
										return;
									}
									// mark last empty poll
									sess.setLastPoll();
								}

								/*
								 * send response
								 */

								/* request to terminate session? */
								if (attribs.getNamedItem("type") != null) {
									String rType = attribs.getNamedItem("type")
											.getNodeValue();
									if (rType.equals("terminate")) {
										sess.terminate();
										jresp.send();
										return;
									}
								}

								/* check incoming queue */
								NodeList nl = sess.checkInQ(rid);
								// add items to response
								if (nl != null)
									for (int i = 0; i < nl.getLength(); i++)
										jresp.addNode(nl.item(i),
												"jabber:client");

								if (sess.streamFeatures) {
									jresp.setAttribute("xmlns:stream",
											"http://etherx.jabber.org/streams");
									sess.streamFeatures = false; // reset
								}

								/* check for streamid (digest auth!) */
								if (!sess.authidSent
										&& sess.getAuthid() != null) {
									sess.authidSent = true;
									jresp.setAttribute("authid", sess
											.getAuthid());
								}

								if (sess.isStatus(Session.SESS_TERM)) {
									// closed due to stream error
									jresp.setAttribute("type", "terminate");
									jresp.setAttribute("condition",
											"remote-stream-error");
								}

								/* finally send back response */
								jresp.send();
								sess.setLastDoneRID(jresp.getRID());
								sess.sock.notifyAll();
							}
						} catch (IOException ioe) {
							sess.terminate();
							jresp.setAttribute("type", "terminate");
							jresp.setAttribute("condition",
									"remote-connection-failed");
							jresp.send();
						}
					} else {
						/* session not found! */
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
				} else {

					/***********************************************************
					 * request to create a new session ***
					 */

					if (attribs.getNamedItem("rid") == null) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						return;
					} else {
						try {
							rid = Integer.parseInt(attribs.getNamedItem("rid")
									.getNodeValue());
						} catch (NumberFormatException e) {
							response
									.sendError(HttpServletResponse.SC_BAD_REQUEST);
							return;
						}
					}

					Response jresp = new Response(response, db.newDocument());
					jresp.setRID(rid);

					// check 'route' attribute
					String route = null;
					if (attribs.getNamedItem("route") != null
							&& isValidRoute(attribs.getNamedItem("route")
									.getNodeValue())) {
						route = attribs.getNamedItem("route").getNodeValue()
								.substring("xmpp:".length());
					}

					// check 'to' attribute
					String to = null;
					if ((attribs.getNamedItem("to") != null)
							&& (attribs.getNamedItem("to").getNodeValue() != "")) {
						to = attribs.getNamedItem("to").getNodeValue();
					}

					if (to == null || to.equals("")) {
						/*
						 * ERROR: 'to' attribute missing or emtpy
						 */

						if (attribs.getNamedItem("content") != null) {
							jresp.setContentType(attribs
									.getNamedItem("content").getNodeValue());
						} else {
							jresp.setContentType(Session.DEFAULT_CONTENT);
						}

						jresp.setAttribute("type", "terminate");
						jresp.setAttribute("condition", "improper-addressing");

						jresp.send();
						return;
					}

					/*
					 * really create new session
					 */
					try {
						Session sess = new Session(to, route);

						if (attribs.getNamedItem("content") != null)
							sess.setContent(attribs.getNamedItem("content")
									.getNodeValue());

						if (attribs.getNamedItem("wait") != null)
							sess.setWait(Integer.parseInt(attribs.getNamedItem(
									"wait").getNodeValue()));

						if (attribs.getNamedItem("hold") != null)
							sess.setHold(Integer.parseInt(attribs.getNamedItem(
									"hold").getNodeValue()));

						if (attribs.getNamedItem("xml:lang") != null)
							sess.setXMLLang(attribs.getNamedItem("xml:lang")
									.getNodeValue());

						if (attribs.getNamedItem("newkey") != null)
							sess.setKey(attribs.getNamedItem("newkey")
									.getNodeValue());

						if (attribs.getNamedItem("secure") != null
								&& (attribs.getNamedItem("secure")
										.getNodeValue().equals("true") || attribs
										.getNamedItem("secure").getNodeValue()
										.equals("1")))
							sess.setSecure(true); // forces IOException

						sess.addResponse(jresp);

						/*
						 * send back response
						 */
						jresp.setContentType(sess.getContent());

						/* check incoming queue */
						NodeList nl = sess.checkInQ(0);

						// add items to response
						if (nl != null)
							for (int i = 0; i < nl.getLength(); i++) {
								if (!nl.item(i).getNodeName()
										.equals("starttls"))
									jresp.addNode(nl.item(i), "");
							}

						if (sess.streamFeatures) {
							jresp.setAttribute("xmlns:stream",
									"http://etherx.jabber.org/streams");
							sess.streamFeatures = false; // reset
						}

						jresp.setAttribute("sid", sess.getSID());
						jresp.setAttribute("wait", String.valueOf(sess
								.getWait()));
						jresp.setAttribute("inactivity", String
								.valueOf(Session.MAX_INACTIVITY));
						jresp.setAttribute("polling", String
								.valueOf(Session.MIN_POLLING));

						jresp.setAttribute("requests", String
								.valueOf(Session.MAX_REQUESTS));

						if (sess.getAuthid() != null) {
							sess.authidSent = true;
							jresp.setAttribute("authid", sess.getAuthid());
						}

						if (sess.isStatus(Session.SESS_TERM))
							jresp.setAttribute("type", "terminate");

						jresp.send();
						sess.setLastDoneRID(jresp.getRID());
					} catch (UnknownHostException uhe) {
						/*
						 * ERROR: remote host unknown
						 */

						if (attribs.getNamedItem("content") != null)
							jresp.setContentType(attribs
									.getNamedItem("content").getNodeValue());
						else
							jresp.setContentType(Session.DEFAULT_CONTENT);

						jresp.setAttribute("type", "terminate");
						jresp.setAttribute("condition", "host-unknown");

						jresp.send();
					} catch (IOException ioe) {
						/*
						 * ERROR: could not connect to remote host
						 */

						if (attribs.getNamedItem("content") != null)
							jresp.setContentType(attribs
									.getNamedItem("content").getNodeValue());
						else
							jresp.setContentType(Session.DEFAULT_CONTENT);

						jresp.setAttribute("type", "terminate");
						jresp.setAttribute("condition",
								"remote-connection-failed");

						jresp.send();
					} catch (NumberFormatException nfe) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
				}
			}
		} catch (SAXException se) {
			/*
			 * ERROR: Parser error
			 */
			dbg(se.toString(), 1);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {

			System.err.println(e.toString());
			e.printStackTrace();
			try {
				Response jresp = new Response(response, db.newDocument());
				jresp.setAttribute("type", "terminate");
				jresp.setAttribute("condition", "internal-server-error");
				jresp.send();
			} catch (Exception e2) {
				e2.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		/*
		 * This one's just for convenience to inform of improper use of
		 * component
		 */

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		String title = APP_NAME + " v" + APP_VERSION;
		writer
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		writer.println("<html>\n<head>\n<title>" + title
				+ "</title>\n</head>\n<body>\n");
		writer.println("<h1>" + title + "</h1>");
		writer
				.println("This is an implementation of JEP-0124 (HTTP-Binding). Please see <a href=\"http://www.jabber.org/jeps/jep-0124.html\">http://www.jabber.org/jeps/jep-0124.html</a> for details.");
		writer.println("\n</body>\n</html>");
	}

	/**
	 * Checks if supplied parameter is a valid host value. Valid host values
	 * should be in the form: "xmpp:" ihost [ ":" port ]
	 * 
	 * NOTE: RFC 3987 defines the form of ihost like this: ihost = IP-literal /
	 * IPv4address / ireg-name This function cuts corners and uses
	 * InetAddress.getByName() to check ihost validity.
	 * 
	 * 
	 * @see java.net.InetAddress
	 * @param route
	 *            hostname that should be checked
	 * @return 'true' if host is in the form "xmpp:" ihost [ ":" port ]
	 *         otherwise 'false'.
	 */
	private static boolean isValidRoute(String route) {
		if (!route.startsWith("xmpp:")) {
			return false;
		}

		route = route.substring("xmpp:".length());

		// check for port validity, if a port is given.
		int port;
		if ((port = route.lastIndexOf(":")) != -1) {
			try {
				int p = Integer.parseInt(route.substring(port + 1));
				if (p < 0 || p > 65535) {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
			route = route.substring(0, port);
		}

		try {
			InetAddress.getByName(route);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
