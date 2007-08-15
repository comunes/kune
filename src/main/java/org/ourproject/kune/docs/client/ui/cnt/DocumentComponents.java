package org.ourproject.kune.docs.client.ui.cnt;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditor;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.editor.TextEditor;

class DocumentComponents {
    private DocumentReader reader;
    private TextEditor editor;
    private final DocumentContentPresenter documentContentPresenter;
    private FolderViewer folderViewer;
    private FolderEditor folderEditor;

    public DocumentComponents(final DocumentContentPresenter documentContentPresenter) {
	this.documentContentPresenter = documentContentPresenter;
    }

    public DocumentReader getDocumentReader() {
	if (reader == null) {
	    reader = DocumentFactory.createDocumentReader(documentContentPresenter);
	}
	return reader;
    }

    public TextEditor getDocumentEditor() {
	if (editor == null) {
	    editor = WorkspaceFactory.createDocumentEditor(documentContentPresenter);
	}
	return editor;
    }

    public FolderViewer getFolderViewer() {
	if (folderViewer == null) {
	    folderViewer = DocumentFactory.createFolderViewer();
	}
	return folderViewer;
    }

    public FolderEditor getFolderEditor() {
	if (folderEditor == null) {
	    folderEditor = DocumentFactory.createFolderEditor();
	}
	return folderEditor;
    }

}
