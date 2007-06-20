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

package org.ourproject.kune.server.manager;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.server.dao.DocumentDao;

import com.google.inject.Inject;
import java.util.List;

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
    
    public List<KuneDoc> getChildren(Session session, KuneDoc parent) throws RepositoryException {
    	return documentDao.getChildren(session, parent);
    }
    
    public KuneDoc createDocument(Session session, KuneDoc parent, String name) throws RepositoryException {
    	return documentDao.createDocument(session, parent, name);
    }
}
