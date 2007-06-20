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

public class KuneDocumentServiceServlet extends RemoteServiceServlet implements
		KuneDocumentService {
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

	private void inject() {
	    Injector injector = Guice.createInjector(new KuneModule());
	    injector.injectMembers(this);
	}

	private void initRepository() {
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

	@Inject public void setDocumentManager(DocumentManager documentManager) {
	    this.documentManager = documentManager;
	}

}
