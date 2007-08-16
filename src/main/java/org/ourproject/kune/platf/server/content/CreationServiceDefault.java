package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CreationServiceDefault implements CreationService {
    private final ContainerManager containerManager;
    private final ContentManager contentManager;

    @Inject
    public CreationServiceDefault(final ContainerManager containerManager, final ContentManager contentManager) {
	this.containerManager = containerManager;
	this.contentManager = contentManager;
    }

    public Content createContent(final String title, final User user, final Container container) {
	return contentManager.createContent(title, user, container);
    }

    public Container createFolder(final Group group, final Long parentFolderId, final String name) {
	return containerManager.createFolder(group, parentFolderId, name);
    }

    public Content saveContent(final Group userGroup, final Content descriptor, final String content) {
	return contentManager.save(userGroup, descriptor, content);
    }

}
