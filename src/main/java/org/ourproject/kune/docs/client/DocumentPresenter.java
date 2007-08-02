package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.workspace.AbstractComponent;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider;
import org.ourproject.kune.platf.client.workspace.ContentDataProvider.ContentDataAcceptor;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public class DocumentPresenter extends AbstractComponent implements Document, ContentDataAcceptor {
    private final DocumentView view;
    private final ContentDataProvider provider;

    public DocumentPresenter(ContentDataProvider provider, DocumentView view) {
	this.provider = provider;
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void load(String contextRef, ContextItemDTO item) {
	view.setContentName(item.getName());
	view.setWaiting();
	provider.getContent(contextRef, item.getReference(), this);
    }

    public void accept(ContentDataDTO ctxData) {
	view.setContent(ctxData.getContent());
    }

    public void failed(Throwable caugth) {
    }




}
