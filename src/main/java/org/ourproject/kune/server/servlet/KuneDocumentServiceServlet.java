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

package org.ourproject.kune.server.servlet;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;

import org.apache.jackrabbit.core.TransientRepository;
import org.ourproject.kune.KuneModule;
import org.ourproject.kune.client.rpc.KuneDocumentService;
import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.server.manager.DocumentManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.List;

public class KuneDocumentServiceServlet extends RemoteServiceServlet implements KuneDocumentService {
	private static final long serialVersionUID = 1L;
	private TransientRepository repository;
	private Session session;
	private DocumentManager documentManager;

	public KuneDocumentServiceServlet() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		inject();
		initRepository();
	}

	@Override
	public void destroy() {
		session.logout();
		super.destroy();
	}

	private void inject() {
		Injector injector = Guice.createInjector(new KuneModule());
		injector.injectMembers(this);
	}

	private void initRepository() {
		try {
			this.repository = new TransientRepository();
			session = repository.login(new SimpleCredentials("username", "password".toCharArray()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	public KuneDoc getRootDocument(String projectName) throws SerializableException {
		try {
			return documentManager.readRootDocument(session);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}

	public void setRootDocument(String projectName, KuneDoc doc) throws SerializableException {
		try {
			documentManager.saveDocument(doc, session);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}

	public DocumentManager getDocumentManager() {
		return documentManager;
	}

	@Inject
	public void setDocumentManager(DocumentManager documentManager) {
		this.documentManager = documentManager;
	}
	
    public List<KuneDoc> getChildren(KuneDoc parent) throws SerializableException {
		try {
            return documentManager.getChildren(session, parent);
		} catch (Exception e) {
            throw new SerializableException(e.toString());
		}
	}

	public KuneDoc createDocument(KuneDoc parent, String name) throws SerializableException {
		try {
			return documentManager.createDocument(session, parent, name);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}
}

