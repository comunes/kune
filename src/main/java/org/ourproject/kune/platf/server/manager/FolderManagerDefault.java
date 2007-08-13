package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Folder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class FolderManagerDefault extends DefaultManager<Folder, Long> implements FolderManager {

    @Inject
    public FolderManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Folder.class);
    }

}
