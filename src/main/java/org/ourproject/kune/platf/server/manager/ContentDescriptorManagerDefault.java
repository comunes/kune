package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentDescriptorManagerDefault extends DefaultManager<ContentDescriptor, Long> implements
	ContentDescriptorManager {

    @Inject
    public ContentDescriptorManagerDefault(final Provider<EntityManager> provider, final ContentDescriptor finder) {
	super(provider, ContentDescriptor.class);
    }

    public ContentDescriptor createContent(final User user, final Folder folder) {
	ContentDescriptor descriptor = new ContentDescriptor();
	descriptor.setCreator(user);
	descriptor.setFolder(folder);
	Revision revision = new Revision();
	descriptor.addRevision(revision);
	return persist(descriptor);
    }

    public ContentDescriptor get(final Long id) {
	return find(id);
    }
}
