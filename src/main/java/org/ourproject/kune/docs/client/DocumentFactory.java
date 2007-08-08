package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;

public class DocumentFactory {

    public static final DocumentReaderPresenter createDocumentReader(final DocumentContentDriver provider,
	    final DocumentReaderListener listener) {
	DocumentReaderView readerView = DocumentViewFactory.getDocumentReaderView(listener);
	return new DocumentReaderPresenter(provider, readerView);
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
	TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
	TextEditorPanel panel = new TextEditorPanel(presenter);
	presenter.init(panel);
	return presenter;
    }

}
