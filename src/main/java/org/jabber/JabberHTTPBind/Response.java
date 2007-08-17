/*
 * Copyright (C) 2005 Stefan Strigler <steve@zeank.in-berlin.de>
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

import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Stefan Strigler <steve@zeank.in-berlin.de> 
 */
public class Response {
	private static TransformerFactory tff = TransformerFactory.newInstance();
	public static final String STATUS_LEAVING = "leaving"; 
	public static final String STATUS_PENDING = "pending"; 
	public static final String STATUS_DONE = "done";
	
	private HttpServletResponse response;
	private long cDate;
	private Document doc;
	private Element body;
	private long rid;
	
	private String status;
	
	/**
	 * creates new high level response object specific to http binding 
	 * responses
	 * 
	 * @param response low level response object
	 * @param doc empty document to start with
	 */
	public Response(HttpServletResponse response, Document doc) {		
		this.response = response;
		this.doc = doc;
	
		this.body = this.doc.createElement("body");
		this.doc.appendChild(this.body);

		this.body.setAttribute("xmlns","http://jabber.org/protocol/httpbind");
		
		this.cDate = System.currentTimeMillis();
		
		setStatus(STATUS_PENDING);
	}
	
	/**
	 * adds an attribute to request's body element
	 * 
	 * @param key	attribute key
	 * @param val	attribute value
	 * @return	the response
	 */
	public Response setAttribute(String key, String val) {
		this.body.setAttribute(key,val);
		return this;
	}
	
	/**
	 * sets content type header value of low-level response object
	 * 
	 * @param type	the content-type definition e.g. 'text/xml'
	 * @return the response
	 */
	public Response setContentType(String type) {
		this.response.setContentType(type);
		return this;
	}
	
	/**
	 * adds node as child of replies body element
	 * 
	 * @param n The node to add
	 * @return Returns the response again
	 */
	public Response addNode(Node n, String ns) {
		/* make sure we set proper namespace for all nodes
		 * which must be 'jabber:client'
		 */
		try {
			if (!((Element) n).getAttribute("xmlns").equals(ns))
				((Element) n).setAttribute("xmlns",ns);
		} catch (ClassCastException e) { /* ? skip! */	}
		this.body.appendChild(this.doc.importNode(n,true));
		return this;
	}
	
	/**
	 * sends this response
	 */
	public synchronized void send() {
		StringWriter strWtr = new StringWriter();
		StreamResult strResult = new StreamResult(strWtr);
		try {
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty("omit-xml-declaration", "yes");
			tf.transform(new DOMSource(this.doc.getDocumentElement()), strResult);
			this.response.getWriter().println(strResult.getWriter().toString());
			JHBServlet.dbg("sent response for "+this.getRID(),3);
		} catch (Exception e) {
			JHBServlet.dbg("XML.toString(Document): " + e,1);
		}
		setStatus(STATUS_DONE);
	}
	/**
	 * @return Returns the status.
	 */
	public synchronized String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public synchronized void setStatus(String status) {
		JHBServlet.dbg("response status "+status+" for "+this.getRID(),3);
		this.status = status;
	}
	
	public long getRID() { return this.rid; } 
	
	public Response setRID(long rid) {
		this.rid = rid;
		return this;
	}
	/**
	 * @return Returns the cDate.
	 */
	public synchronized long getCDate() {
		return cDate;
	}
}
