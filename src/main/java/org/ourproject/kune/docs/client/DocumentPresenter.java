package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.ContentDataDriver.ContentDataAcceptor;
import org.ourproject.kune.workspace.client.ContentDataDriver.SaveListener;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class DocumentPresenter implements Document, DocumentReaderListener, TextEditorListener {

    private TextEditor editor;
    private final DocumentContentDriver dataDriver;
    private DocumentView documentView;
    private DocumentReaderView readerView;
    private ContentDataDTO contentDTO;

    public DocumentPresenter(final DocumentContentDriver driver) {
	this.dataDriver = driver;
    }

    public void setViews(final DocumentView documentView, final DocumentReaderView readerView) {
	this.documentView = documentView;
	this.readerView = readerView;
    }

    public void attach() {
	documentView.show(readerView);
    }

    public void setReferences(final String ctxRef, final String cntRef) {
	load(ctxRef, cntRef);
    }

    private void load(final String contextRef, final String docRef) {
	dataDriver.load(contextRef, docRef, new ContentDataAcceptor() {
	    public void accept(final ContentDataDTO content) {
		contentDTO = content;
		readerView.setContent(contentDTO.getContent());
		readerView.setEditEnabled(true);
	    }

	    public void failed(final Throwable caugth) {
	    }

	});
    }

    public View getView() {
	return documentView;
    }

    public void onEdit() {
	TextEditor editor = getDocumentEditor();
	editor.setContent(readerView.getContent());
	documentView.show(editor.getView());
    }

    private TextEditor getDocumentEditor() {
	if (editor == null) {
	    editor = DocumentFactory.createDocumentEditor(this);
	}
	return editor;
    }

    public void onCancel() {
	readerView.setContent(contentDTO.getContent());
	documentView.show(readerView);
    }

    public void onSave() {
	contentDTO.setContent(editor.getContent());
	dataDriver.save(contentDTO, new SaveListener() {
	    public void failed(final Throwable caught) {
		// TODO: externalizar
		Site.error("no se ha podido guardar");
	    }

	    public void saveCompleted() {
		Site.info("documento guardado!");
	    }
	});
    }

    public void detach() {

    }

}
