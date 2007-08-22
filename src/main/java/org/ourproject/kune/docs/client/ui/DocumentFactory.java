/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.ui;

import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditor;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditorPanel;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditorPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerPanel;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContextPanel;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.WorkspaceFactory;
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
	DocumentReaderView view = new DocumentReaderPanel(listener);
	DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
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
	AdminContextView view = new AdminContextPanel();
	AdminContextPresenter presenter = new AdminContextPresenter(view);
	return presenter;
    }

}
