package org.ourproject.kune.server.jcr.util;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.core.TransientRepository;

public class CreateRepository {
	public static void main(String[] args) throws Exception {
		Repository repository = new TransientRepository();
		Session session = repository.login(new SimpleCredentials("username",
				"password".toCharArray()));
		try {
			Node rootNode = session.getRootNode();
			Node docsNode = createDocumentsNode(rootNode);
			initRootDocument(docsNode);
			session.save();
		} finally {
			session.logout();
		}
	}

	private static Node createDocumentsNode(Node rootNode)
			throws RepositoryException, ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException {
		Node docsNode;
		if (!rootNode.hasNode("documents")) {
			System.out.println("create documents node!");
			docsNode = rootNode.addNode("documents");
		} else {
			System.out.println("repository already has documents!");
			docsNode = rootNode.getNode("documents");
		}
		return docsNode;
	}

	private static final String content = "<p>Esto no es latín, pero es la página por defecto guardada en el repositorio</p>";
	private static void initRootDocument(Node docsNode)
			throws RepositoryException {
		Node rootDocument;
		if (docsNode.hasNode("root")) {
			System.out.println("repository alread has root document");
			rootDocument = docsNode.getNode("root");
		} else {
			System.out.println("creating root document");
			rootDocument = docsNode.addNode("root");
			rootDocument.addMixin("mix:referenceable");
			rootDocument.addMixin("mix:versionable");
		}
		
		System.out.println("initializing root node");
		rootDocument.setProperty("title", "this is the root document");
		rootDocument.setProperty("content", content);
		rootDocument.setProperty("license_name", "this is the default licence");
	}
}
