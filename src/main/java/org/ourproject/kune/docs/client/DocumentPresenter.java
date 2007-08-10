package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.ContentDataDriver;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.ContentDataDriver.SaveListener;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class DocumentPresenter implements Document, DocumentReaderListener, TextEditorListener {

    private TextEditor editor;
    private DocumentReaderView readerView;
    private ContentDataDTO contentDTO;
    private final DocumentServiceAsync server;
    private DocumentView documentView;

    public DocumentPresenter() {
	this.server = DocumentService.App.getInstance();

    }

    public void attach() {
	documentView.show(readerView);
    }

    public void setReferences(final String ctxRef, final String cntRef) {
	load(ctxRef, cntRef);
    }

    private void load(final String contextRef, final String docRef) {
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
	    editor = WorkspaceFactory.createDocumentEditor(this);
	}
	return editor;
    }

    public void onCancel() {
	readerView.setContent(contentDTO.getContent());
	documentView.show(readerView);
    }

    public void onSave() {
	contentDTO.setContent(editor.getContent());
	// TODO
	ContentDataDriver dataDriver = null;
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

    public void setState(final String group, final String folder, final String document) {
    }

    public void setViews(final DocumentView docView, final DocumentReaderView readerView2) {

    }

}
