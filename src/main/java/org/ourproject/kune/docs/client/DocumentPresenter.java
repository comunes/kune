package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.AbstractComponent;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider.ContentDataAcceptor;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public class DocumentPresenter extends AbstractComponent implements Document, ContentDataAcceptor {
    private final DocumentView view;
    private final ContentDataProvider provider;

    public DocumentPresenter(ContentDataProvider provider, DocumentView view, String initalState) {
	this.provider = provider;
	this.view = view;
	encodedState = initalState;
    }

    public View getView() {
	return view;
    }

    public void setEncodedState(String encodedState) {
        super.setEncodedState(encodedState);
	String[] split = HistoryToken.split(encodedState);
	load(split[0], split[1]);
    }

    private void load(String contextRef, String docRef) {
	view.setWaiting();
	provider.getContent(contextRef, docRef, this);
    }

    public void load(String contextRef, ContextItemDTO item) {
	view.setContentName(item.getName());
	String docRef = item.getReference();
	load(contextRef, docRef);
    }

    public void accept(ContentDataDTO ctxData) {
	view.setContentName(ctxData.getTitle());
	view.setContent(ctxData.getContent());
    }

    public void failed(Throwable caugth) {
    }




}
