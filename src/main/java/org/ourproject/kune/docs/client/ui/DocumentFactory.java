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

package org.ourproject.kune.docs.client.ui;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditorPresenter;
import org.ourproject.kune.docs.client.cnt.folder.ui.FolderEditorPanel;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.cnt.folder.viewer.ui.FolderViewerPanel;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlView;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderControlPanel;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ctx.admin.ui.AdminContextPanel;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

public class DocumentFactory {

    public static DocumentContent createDocumentContent(final DocumentContentListener listener) {
        WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
        DocumentContentPresenter presenter = new DocumentContentPresenter(listener, panel);
        return presenter;
    }

    public static DocumentContext createDocumentContext() {
        WorkspaceDeckPanel view = new WorkspaceDeckPanel();
        DocumentContextPresenter presenter = new DocumentContextPresenter(view);
        return presenter;
    }

    public static DocumentReader createDocumentReader(final DocumentReaderListener listener) {
        DocumentReaderView view = new DocumentReaderPanel();
        DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
        return presenter;
    }

    public static DocumentReaderControl createDocumentReaderControl(final DocumentReaderListener listener) {
        DocumentReaderControlView view = new DocumentReaderControlPanel(listener);
        DocumentReaderControlPresenter presenter = new DocumentReaderControlPresenter(view);
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
