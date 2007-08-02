package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.workspace.AbstractComponent;

public class DocumentPresenter extends AbstractComponent {
    private final DocumentView view;

    public DocumentPresenter(DocumentView view) {
	this.view = view;
    }

    public Object getView() {
	return view;
    }

}
