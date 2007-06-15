package org.ourproject.kune.server;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;

import org.apache.jackrabbit.core.TransientRepository;
import org.ourproject.kune.client.rpc.KuneDocumentService;
import org.ourproject.kune.client.rpc.dto.KuneDoc;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class KuneDocumentServiceServlet extends RemoteServiceServlet implements
		KuneDocumentService {
	private static final long serialVersionUID = 1L;
	private TransientRepository repository;
	private Session session;

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
			Node rootDocument = session.getRootNode().getNode("documents").getNode("root");
			String title = rootDocument.getProperty("title").getValue().getString();
			String content = rootDocument.getProperty("content").getValue().getString();
			String license = rootDocument.getProperty("license_name").getValue().getString();
			return new KuneDoc(title, content, license);
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}
	
	public void setRootDocument(String projectName, KuneDoc doc) throws SerializableException {
		try {
			Node rootDocument = session.getRootNode().getNode("documents").getNode("root");
			rootDocument.setProperty("title", doc.getName());
			rootDocument.setProperty("content", doc.getContent());
			rootDocument.setProperty("license_name", doc.getLicenseName());
			session.save();
		} catch (Exception e) {
			throw new SerializableException(e.toString());
		}
	}
}
