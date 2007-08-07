package org.ourproject.kune.docs.client.reader2;

import org.ourproject.kune.docs.client.actions.EditAction;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.AbstractComponent;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider.ContentDataAcceptor;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public class DocumentReaderPresenter extends AbstractComponent implements DocumentReader, ContentDataAcceptor {
    private final DocumentReaderView view;
    private final ContentDataProvider provider;
    private final Dispatcher dispatcher;

    public DocumentReaderPresenter(final Dispatcher dispatcher, final ContentDataProvider provider,
	    final DocumentReaderView view, final String initalState) {
	this.dispatcher = dispatcher;
	this.provider = provider;
	this.view = view;
	encodedState = initalState;
    }

    public View getView() {
	return view;
    }

    public void setEncodedState(final String encodedState) {
	super.setEncodedState(encodedState);
	String[] split = HistoryToken.split(encodedState);
	load(split[0], split[1]);
    }

    private void load(final String contextRef, final String docRef) {
	provider.getContent(contextRef, docRef, this);
    }

    public void load(final String contextRef, final ContextItemDTO item) {
	view.setContentName(item.getName());
	String docRef = item.getReference();
	load(contextRef, docRef);
    }

    public void accept(final ContentDataDTO ctxData) {
	view.setContentName(ctxData.getTitle());
	view.setContent(ctxData.getContent());
    }

    public void failed(final Throwable caugth) {
    }

    public void onEdit() {
	dispatcher.fire(EditAction.NAME, "");
    }
}
