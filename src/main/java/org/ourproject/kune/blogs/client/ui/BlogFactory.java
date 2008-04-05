/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.blogs.client.ui;

import org.ourproject.kune.blogs.client.cnt.BlogContent;
import org.ourproject.kune.blogs.client.cnt.BlogContentListener;
import org.ourproject.kune.blogs.client.cnt.BlogContentPresenter;
import org.ourproject.kune.blogs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.blogs.client.cnt.folder.FolderEditorPresenter;
import org.ourproject.kune.blogs.client.cnt.folder.ui.FolderEditorPanel;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.ui.FolderViewerPanel;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReader;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControl;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControlPresenter;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControlView;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderListener;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderPresenter;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderView;
import org.ourproject.kune.blogs.client.cnt.reader.ui.BlogReaderControlPanel;
import org.ourproject.kune.blogs.client.cnt.reader.ui.BlogReaderPanel;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.blogs.client.ctx.BlogContextPresenter;
import org.ourproject.kune.blogs.client.ctx.folder.FolderContext;
import org.ourproject.kune.blogs.client.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ctx.admin.ui.AdminContextPanel;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

public class BlogFactory {

    public static BlogContent createDocumentContent(final BlogContentListener listener) {
        WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
        BlogContentPresenter presenter = new BlogContentPresenter(listener, panel);
        return presenter;
    }

    public static BlogContext createDocumentContext() {
        WorkspaceDeckPanel view = new WorkspaceDeckPanel();
        BlogContextPresenter presenter = new BlogContextPresenter(view);
        return presenter;
    }

    public static BlogReader createDocumentReader(final BlogReaderListener listener) {
        BlogReaderView view = new BlogReaderPanel();
        BlogReaderPresenter presenter = new BlogReaderPresenter(view);
        return presenter;
    }

    public static BlogReaderControl createDocumentReaderControl(final BlogReaderListener listener) {
        BlogReaderControlView view = new BlogReaderControlPanel(listener);
        BlogReaderControlPresenter presenter = new BlogReaderControlPresenter(view);
        return presenter;
    }

    public static FolderContext createFolderContext() {
        ContextItems contextItems = WorkspaceFactory.createContextItems();
        FolderContextPresenter presenter = new FolderContextPresenter(contextItems);
        return presenter;
    }

    public static FolderViewer createFolderViewer() {
        FolderViewerView view = new FolderViewerPanel();
        FolderViewerPresenter presenter = new FolderViewerPresenter(view);
        return presenter;
    }

    public static FolderEditor createFolderEditor() {
        FolderEditorPanel view = new FolderEditorPanel();
        FolderEditorPresenter presenter = new FolderEditorPresenter(view);
        return presenter;
    }

    public static AdminContext createAdminContext() {
        AdminContextPresenter presenter = new AdminContextPresenter();
        AdminContextView view = new AdminContextPanel(presenter);
        presenter.init(view);
        return presenter;
    }

}
