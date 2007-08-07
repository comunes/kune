package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.workspace.editor.TextEditor;
import org.ourproject.kune.platf.client.workspace.editor.TextEditorListener;

public class DocumentPresenter implements Document, DocumentReaderListener, TextEditorListener {

    private final DocumentView view;
    private final DocumentReaderPresenter reader;
    private TextEditor editor;

    public DocumentPresenter(final DocumentContentProvider provider, final DocumentView view) {
	this.view = view;
	reader = DocumentFactory.createDocumentReader(provider, this);
    }

    public void attach() {
	view.show(reader.getView());
    }

    public void detach() {
    }

    public String getEncodedState() {
	return reader.getEncodedState();
    }

    public View getView() {
	return view;
    }

    public void setEncodedState(final String encodedState) {
	reader.setEncodedState(encodedState);
    }

    public void onEdit() {
	TextEditor editor = getDocumentEditor();
	editor.setContent(reader.getContent());
	view.show(editor.getView());
    }

    private TextEditor getDocumentEditor() {
	if (editor == null) {
	    editor = DocumentFactory.createDocumentEditor(this);
	}
	return editor;
    }
}
