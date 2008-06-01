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

package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.editor.TextEditor;

class DocumentContentComponents {
    private DocumentReader reader;
    private TextEditor editor;
    private final DocumentContentPresenter documentContentPresenter;
    private FolderViewer folderViewer;
    private FolderEditor folderEditor;
    private DocumentReaderControl readerControl;
    private final DocumentFactory documentFactory;

    public DocumentContentComponents(final DocumentFactory documentFactory,
            final DocumentContentPresenter documentContentPresenter) {
        this.documentFactory = documentFactory;
        this.documentContentPresenter = documentContentPresenter;
    }

    public TextEditor getDocumentEditor() {
        if (editor == null) {
            editor = WorkspaceFactory.createDocumentEditor(documentContentPresenter);
        }
        return editor;
    }

    public DocumentReader getDocumentReader() {
        if (reader == null) {
            reader = documentFactory.createDocumentReader(documentContentPresenter);
        }
        return reader;
    }

    public DocumentReaderControl getDocumentReaderControl() {
        if (readerControl == null) {
            readerControl = documentFactory.createDocumentReaderControl(documentContentPresenter);
        }
        return readerControl;
    }

    public FolderEditor getFolderEditor() {
        if (folderEditor == null) {
            folderEditor = documentFactory.createFolderEditor();
        }
        return folderEditor;
    }

    public FolderViewer getFolderViewer() {
        if (folderViewer == null) {
            folderViewer = documentFactory.createFolderViewer();
        }
        return folderViewer;
    }

}
