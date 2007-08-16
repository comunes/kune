package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.FolderManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class FolderManagerDefault extends DefaultManager<Folder, Long> implements FolderManager {

    @Inject
    public FolderManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Folder.class);
    }

    public Folder createRootFolder(final Group group, final String toolName, final String name) {
	Folder folder = new Folder(Folder.SEP, name, group, toolName);
	return persist(folder);
    }

    public Folder createFolder(final Group group, final Long parentFolderId, final String name) {
	Folder parent = find(parentFolderId);
	String relativePath = parent.getAbsolutePath() + Folder.SEP;
	Folder folder = new Folder(relativePath, name, group, parent.getToolName());
	folder.setParent(parent);
	parent.addFolder(folder);
	persist(parent);
	return persist(folder);
    }
}
