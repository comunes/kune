package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsView;

class Components {

    private FolderContext folderContext;
    private final DocumentContextPresenter listener;
    private AdminContext adminContext;

    public Components(final DocumentContextPresenter listener) {
	this.listener = listener;
    }

    public FolderContext getFolderContext() {
	if (folderContext == null) {
	    folderContext = DocumentFactory.createFolderContext();
	    ContextItemsView view = (ContextItemsView) folderContext.getView();
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
