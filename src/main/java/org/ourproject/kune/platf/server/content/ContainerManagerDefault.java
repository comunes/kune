package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContainerManagerDefault extends DefaultManager<Container, Long> implements ContainerManager {

    @Inject
    public ContainerManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Container.class);
    }

    public Container createRootFolder(final Group group, final String toolName, final String name, final String type) {
	Container container = new Container("", name, group, toolName);
	container.setTypeId(type);
	return persist(container);
    }

    public Container createFolder(final Group group, final Long parentFolderId, final String name) {
	Container parent = find(parentFolderId);
	Container child = new Container(parent.getAbsolutePath(), name, group, parent.getToolName());
	parent.addChild(child);
	child.setParent(parent);
	persist(parent);
	persist(child);
	return child;
    }
}
