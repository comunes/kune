package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.dto.ContentDTO;
import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public class DocumentReaderPresenter implements DocumentReader {
    private final DocumentReaderView view;

    public DocumentReaderPresenter(final DocumentReaderView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setReferences(final String ctxRef, final String cntRef) {
	load(ctxRef, cntRef);
    }

    private void load(final String contextRef, final String docRef) {
    }

    public void load(final String contextRef, final ContextItemDTO item) {
	String docRef = item.getReference();
	load(contextRef, docRef);
    }

    public void accept(final ContentDTO ctxData) {
	view.setContent(ctxData.getContent());
	view.setEditEnabled(true);
    }

    public void failed(final Throwable caugth) {
    }

    public String getContent() {
	return view.getContent();
    }

    public void setState(final String group, final String folder, final String document) {

    }

}
