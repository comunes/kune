package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;

class Components {

    private FolderContext folderContext;
    private AdminContext adminContext;

    public Components(final DocumentContextPresenter listener) {
    }

    public FolderContext getFolderContext() {
	if (folderContext == null) {
	    folderContext = DocumentFactory.createFolderContext();
	}
	return folderContext;
    }

    public AdminContext getAdminContext() {
	if (adminContext == null) {
	    adminContext = DocumentFactory.createAdminContext();
	}
	return adminContext;
    }

}
