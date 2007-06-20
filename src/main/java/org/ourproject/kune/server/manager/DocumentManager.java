package org.ourproject.kune.server.manager;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.server.dao.DocumentDao;

import com.google.inject.Inject;

public class DocumentManager {
    private final DocumentDao documentDao;

    @Inject public DocumentManager(DocumentDao documentDao) {
	this.documentDao = documentDao;
    }

    public KuneDoc readRootDocument(Session session) throws RepositoryException {
	return documentDao.getRoot(session);
    }

    public void saveDocument(KuneDoc doc, Session session) throws RepositoryException {
	documentDao.saveDocument(session, doc);
    }
}
