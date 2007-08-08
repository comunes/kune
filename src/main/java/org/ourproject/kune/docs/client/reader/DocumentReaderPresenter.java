package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.AbstractComponent;
import org.ourproject.kune.workspace.client.ContentDataDriver;
import org.ourproject.kune.workspace.client.ContentDataDriver.ContentDataAcceptor;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;
import org.ourproject.kune.workspace.client.dto.ContextItemDTO;

public class DocumentReaderPresenter extends AbstractComponent implements DocumentReader, ContentDataAcceptor {
    private final DocumentReaderView view;
    private final ContentDataDriver provider;

    public DocumentReaderPresenter(final ContentDataDriver provider, final DocumentReaderView view) {
	this.provider = provider;
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setReferences(final String ctxRef, final String cntRef) {
	load(ctxRef, cntRef);
    }

    private void load(final String contextRef, final String docRef) {
	provider.load(contextRef, docRef, this);
    }

    public void load(final String contextRef, final ContextItemDTO item) {
	String docRef = item.getReference();
	load(contextRef, docRef);
    }

    public void accept(final ContentDataDTO ctxData) {
	view.setContent(ctxData.getContent());
	view.setEditEnabled(true);
    }

    public void failed(final Throwable caugth) {
    }

    public String getContent() {
	return view.getContent();
    }

}
