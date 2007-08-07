package org.ourproject.kune.platf.server.jcr;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ContentManager {
    @Inject
    Provider<Session> sessionProvider;

    @JcrTransactional
    public String createContent(final String path) {
	// Session session = sessionProvider.get();
	return null;
    }

    @JcrTransactional
    public String persist(final String path, final String name, final ContentSource values) {
	try {
	    Session session = sessionProvider.get();
	    Node root = session.getRootNode();
	    Node newNode = root.addNode(path);
	    newNode.addMixin("mix:referenceable");
	    newNode.addMixin("mix:versionable");
	    persist(newNode, values);
	    return newNode.getUUID();
	} catch (RepositoryException e) {
	    throw new RuntimeException(e);
	}
    }

    private void persist(final Node newNode, final ContentSource values) throws ValueFormatException, VersionException,
	    LockException, ConstraintViolationException, RepositoryException {
	Iterable<String> keys = values.keys();
	for (String key : keys) {
	    newNode.setProperty(key, values.get(key));
	}
    }

    @JcrTransactional
    public ContentDestination get(final String uuid, final ContentDestination destination) throws ItemNotFoundException {
	try {
	    Session session = sessionProvider.get();
	    Node node = session.getNodeByUUID(uuid);
	    get(node, destination);
	    return destination;
	} catch (ItemNotFoundException e) {
	    throw e;
	} catch (RepositoryException e) {
	    throw new RuntimeException(e);
	}
    }

    private void get(final Node node, final ContentDestination destination) throws ValueFormatException,
	    PathNotFoundException, RepositoryException {
	for (String key : destination.keys()) {
	    destination.put(key, node.getProperty(key).getString());
	}
    }
}
