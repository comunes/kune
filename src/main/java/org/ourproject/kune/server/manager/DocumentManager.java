package org.ourproject.kune.server.manager;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.ourproject.kune.client.rpc.dto.KuneDoc;

public class DocumentManager {
    public KuneDoc readRootDocument(Session session) throws RepositoryException {
	Node rootDocument = session.getRootNode().getNode("documents").getNode("root");
	String title = rootDocument.getProperty("title").getValue().getString();
	String content = rootDocument.getProperty("content").getValue().getString();
	String license = rootDocument.getProperty("license_name").getValue().getString();
	return new KuneDoc(title, content, license);
    }

    public void saveDocument(KuneDoc doc, Session theSess) throws RepositoryException {
	Node rootDocument = theSess.getRootNode().getNode("documents").getNode("root");
	rootDocument.setProperty("title", doc.getName());
	rootDocument.setProperty("content", doc.getContent());
	rootDocument.setProperty("license_name", doc.getLicenseName());
    }
}
