package org.ourproject.kune.blogs.client.ctx;

import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.blogs.client.ctx.folder.FolderContext;
import org.ourproject.kune.blogs.client.ui.BlogFactory;

class BlogContextComponents {

    private FolderContext folderContext;
    private AdminContext adminContext;

    public BlogContextComponents(final BlogContextPresenter listener) {
    }

    public FolderContext getFolderContext() {
        if (folderContext == null) {
            folderContext = BlogFactory.createFolderContext();
        }
        return folderContext;
    }

    public AdminContext getAdminContext() {
        if (adminContext == null) {
            adminContext = BlogFactory.createAdminContext();
        }
        return adminContext;
    }

}
