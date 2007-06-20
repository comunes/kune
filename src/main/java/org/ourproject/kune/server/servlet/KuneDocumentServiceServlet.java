package org.ourproject.kune.server.servlet;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;

import org.apache.jackrabbit.core.TransientRepository;
import org.ourproject.kune.client.rpc.KuneDocumentService;
import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.server.manager.DocumentManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class KuneDocumentServiceServlet extends RemoteServiceServlet implements
		KuneDocumentService {
	private static final long serialVersionUID = 1L;
	private TransientRepository repository;
	private Session session;
	private final DocumentManager manager;

	public KuneDocumentServiceServlet() {
		this.manager = new DocumentManager();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			this.repository = new TransientRepository();
			session = repository.login(new SimpleCredentials("username",
					"password".toCharArray()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	public KuneDoc getRootDocument(String projectName) throws SerializableException {
		try {
			return manager.readRootDocument(session);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}

	
	public void setRootDocument(String projectName, KuneDoc doc) throws SerializableException {
		try {
			manager.saveDocument(doc, session);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}


}
