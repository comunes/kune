package org.ourproject.kune.server.dao;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.ourproject.kune.client.rpc.dto.KuneDoc;

public class DocumentDao {

    public KuneDoc getRoot(Session session) throws RepositoryException {
	Node rootDocument = getRootNode(session);
	return load(rootDocument, new KuneDoc());
    }

    public void saveDocument(Session session, KuneDoc doc) throws RepositoryException {
	Node rootDocument = getRootNode(session);
	save(rootDocument, doc);
    }

    private Node getRootNode(Session session) throws PathNotFoundException, RepositoryException {
	Node rootDocument = session.getRootNode().getNode("documents").getNode("root");
	return rootDocument;
    }

    
    private KuneDoc load(Node node, KuneDoc doc) throws ValueFormatException, RepositoryException, PathNotFoundException {
	String title = node.getProperty("title").getValue().getString();
	String content = node.getProperty("content").getValue().getString();
	String license = node.getProperty("license_name").getValue().getString();
	doc.setName(title);
	doc.setContent(content);
	doc.setLicenseName(license);
	return doc;
    }

    private void save(Node node, KuneDoc doc) throws ValueFormatException, VersionException, LockException,
	    ConstraintViolationException, RepositoryException {
	node.setProperty("title", doc.getName());
	node.setProperty("content", doc.getContent());
	node.setProperty("license_name", doc.getLicenseName());
    }



}
