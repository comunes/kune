package org.ourproject.kune.server.dao;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.ourproject.kune.client.rpc.dto.KuneDoc;

public interface DocumentDao {
    KuneDoc getRoot(Session session) throws RepositoryException;
    void saveDocument(Session session, KuneDoc doc) throws RepositoryException;
    public List<KuneDoc> getChildren(Session session, KuneDoc parent) throws RepositoryException;
    public KuneDoc createDocument(Session session, KuneDoc parent, String name) throws RepositoryException;

}
