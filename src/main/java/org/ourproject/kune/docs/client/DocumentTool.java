package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationListener;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;
import org.ourproject.kune.platf.client.workspace.navigation.NavigatorPresenter;

public class DocumentTool extends AbstractTool implements NavigationListener {
    protected static final String NAME = "docs";
    private Document document;

    public DocumentTool() {
	super(NAME);
    }

    // TODO: translate
    public String getCaption() {
	return "documentos";
    }

    public String getEncodedState() {
	return HistoryToken.encode(getName(), getContext().getEncodedState(), getContent().getEncodedState());
    }

    public void setEncodedState(Object value) {
	String[] split = HistoryToken.split(value);
	getContext().setEncodedState(split[0]);
	getContent().setEncodedState(split[1]);
    }

    protected WorkspaceComponent createContent() {
	DocumentContentProvider provider = new DocumentContentProvider(DocumentService.App.getInstance(), userHash);
	DocumentView view = DocumentViewFactory.getDocumentView();
	document = new DocumentPresenter(provider, view);
	document.setEncodedState("welcome");
	return document;
    }

    protected WorkspaceComponent createContext() {
	DocumentContextProvider provider = new DocumentContextProvider(DocumentService.App.getInstance(), userHash);
	NavigationView view = DocumentViewFactory.getNavigationtView();
	NavigatorPresenter context = new NavigatorPresenter(this, provider, view);
	context.setEncodedState("home");
	return context;
    }

    public void contextChanged(String contextRef, ContextItemDTO selectedItem) {
//	document.load(contextRef, selectedItem);
    }

}
