/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.ourproject.kune.client.rpc.dto.KuneDoc;

public class DocumentDaoJCR implements DocumentDao {

    public KuneDoc getRoot(Session session) throws RepositoryException {
	Node rootDocument = getRootNode(session);
	return load(rootDocument, new KuneDoc());
    }
    
    public List<KuneDoc> getChildren (Session session, KuneDoc parent) throws RepositoryException {
	KuneDoc doc;
	List<KuneDoc> kids = new ArrayList<KuneDoc>();
	
	Node parentNode = session.getNodeByUUID(parent.getUuid());
	NodeIterator iterator = parentNode.getNodes();
	while (iterator.hasNext()) {
	    doc = new KuneDoc();
	    load(iterator.nextNode(), doc);
	    kids.add(doc);
	}
	return kids;
    }

    public KuneDoc createDocument(Session session, KuneDoc parent, String name) throws RepositoryException {
	Node parentNode = session.getNodeByUUID(parent.getUuid());
	Node newNode = parentNode.addNode(name);
	// FIXME: Quickfix to DEBUG - EXCEPTION => javax.jcr.PathNotFoundException: title
	newNode.setProperty("title", name);
	newNode.setProperty("content", "");
	newNode.setProperty("license_name", "");
	newNode.addMixin("mix:referenceable");
	newNode.addMixin("mix:versionable");
	session.save();
	return load(newNode, new KuneDoc());
    }

    public void saveDocument(Session session, KuneDoc doc) throws RepositoryException {
	Node rootDocument = getRootNode(session);
	save(rootDocument, doc);
    }

    private Node getRootNode(Session session) throws RepositoryException {
	Node rootDocument = session.getRootNode().getNode("documents").getNode("root");
	return rootDocument;
    }
    
    
    KuneDoc load(Node node, KuneDoc doc) throws RepositoryException {
	String uuid = getUUID(node);
	String title = node.getProperty("title").getValue().getString();
	String content = node.getProperty("content").getValue().getString();
	String license = node.getProperty("license_name").getValue().getString();
	doc.setUuid(uuid);
	doc.setName(title);
	doc.setContent(content);
	doc.setLicenseName(license);
	return doc;
    }

    private String getUUID(Node entry) throws RepositoryException{
	return entry.getProperty("jcr:uuid").getValue().getString();
    }
    
    private void save(Node node, KuneDoc doc) throws ValueFormatException, VersionException, LockException,
	    ConstraintViolationException, RepositoryException {
	node.setProperty("title", doc.getName());
	node.setProperty("content", doc.getContent());
	node.setProperty("license_name", doc.getLicenseName());
    }



}
