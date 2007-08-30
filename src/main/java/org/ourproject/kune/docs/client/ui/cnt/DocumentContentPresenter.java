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

package org.ourproject.kune.docs.client.ui.cnt;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditor;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class DocumentContentPresenter implements DocumentContent, DocumentReaderListener, TextEditorListener {
    private final WorkspaceDeckView view;
    private final DocumentComponents components;
    private StateDTO content;
    private final DocumentContentListener listener;

    public DocumentContentPresenter(final DocumentContentListener listener, final WorkspaceDeckView view) {
	this.listener = listener;
	this.view = view;
	this.components = new DocumentComponents(this);
    }

    public void setContent(final StateDTO content) {
	this.content = content;
	showContent();
    }

    public void onSaved() {
	components.getDocumentEditor().onSaved();
    }

    public void onSaveFailed() {
	components.getDocumentEditor().onSaveFailed();
    }

    public void onEdit() {
	if (content.hasDocument()) {
	    TextEditor editor = components.getDocumentEditor();
	    editor.setContent(content.getContent());
	    view.show(editor.getView());
	} else {
	    FolderEditor editor = components.getFolderEditor();
	    editor.setFolder(content.getFolder());
	    view.show(editor.getView());
	}
	listener.onEdit();
    }

    public void onSave(final String text) {
	content.setContent(text);
	Services.get().dispatcher.fire(DocsEvents.SAVE_DOCUMENT, content, this);
    }

    public void onCancel() {
	showContent();
	listener.onCancel();
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    private void showContent() {
	if (content.hasDocument()) {
	    DocumentReader reader = components.getDocumentReader();
	    reader.showDocument(content.getContent(), content.getContentRights());
	    view.show(reader.getView());
	} else {
	    FolderViewer viewer = components.getFolderViewer();
	    viewer.setFolder(content.getFolder());
	    view.show(viewer.getView());
	}
    }

}
