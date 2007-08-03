package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;
import org.ourproject.kune.platf.client.workspace.navigation.NavigatorPresenter;

public class DocumentTool extends AbstractTool  {
    protected static final String NAME = "docs";
    private Document document;

    public DocumentTool() {
	super(NAME);
    }

    // TODO: translate
    public String getCaption() {
	return "documentos";
    }

    public Action getStateAction() {
	return new DocumentStateAction(this);
    }


    public String getEncodedState() {
	return HistoryToken.encode(getName(), getContext().getEncodedState(), getContent().getEncodedState());
    }

    public void setEncodedState(Object value) {
	getContext().setEncodedState((String) value);
	getContent().setEncodedState((String) value);
    }

    protected WorkspaceComponent createContent() {
	DocumentContentProvider provider = new DocumentContentProvider(DocumentService.App.getInstance(), state);
	DocumentView view = DocumentViewFactory.getDocumentView();
	document = new DocumentPresenter(provider, view, "welcome");
	return document;
    }

    protected WorkspaceComponent createContext() {
	DocumentContextProvider provider = new DocumentContextProvider(DocumentService.App.getInstance(), state);
	NavigationView view = DocumentViewFactory.getNavigationtView();
	NavigatorPresenter context = new NavigatorPresenter(provider, view, "home");
	return context;
    }

}
