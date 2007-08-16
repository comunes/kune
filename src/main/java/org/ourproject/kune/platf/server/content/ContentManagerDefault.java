package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentManagerDefault extends DefaultManager<Content, Long> implements ContentManager {

    @Inject
    public ContentManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Content.class);
    }

    public Content createContent(final String title, final User user, final Container container) {
	Content descriptor = new Content();
	descriptor.setCreator(user.getUserGroup());
	descriptor.setFolder(container);
	container.addContent(descriptor);
	Revision revision = new Revision();
	revision.setTitle(title);
	descriptor.addRevision(revision);
	return persist(descriptor);
    }

    public Content save(final Group userGroup, final Content descriptor, final String content) {
	Revision revision = new Revision();
	revision.setTitle(descriptor.getTitle());
	revision.setDataContent(content);
	descriptor.addRevision(revision);
	return persist(descriptor);
    }
}
