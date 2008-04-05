
package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ui.DocumentFactory;

class DocumentContextComponents {

    private FolderContext folderContext;
    private AdminContext adminContext;

    public DocumentContextComponents(final DocumentContextPresenter listener) {
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
