package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;

class Components {

    private FolderContext folderContext;

    public FolderContext getFolderContext() {
	if (folderContext == null) {
	    folderContext = DocumentFactory.createFolderContext();
	}
	return folderContext;
    }

}
