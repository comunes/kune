package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.folder.NavigationView;
import org.ourproject.kune.docs.client.folder.NavigatorPresenter;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.admin.AjoAdmin;

public class DocumentTool extends AbstractTool {
    public static final String NAME = "docs";
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
	String cntEncodedState;

	String ctxEncodedState = getContext().getEncodedState();
	if (ctxEncodedState == null) {
	    ctxEncodedState = "home";
	    cntEncodedState = "welcome";
	} else {
	    cntEncodedState = getContent().getEncodedState();
	}
	return HistoryToken.encode(getName(), ctxEncodedState, cntEncodedState);
    }

    public void setEncodedState(final Object value) {
	getContext().setEncodedState((String) value);
	getContent().setEncodedState((String) value);
    }

    protected WorkspaceComponent createContent() {
	DocumentContentDriver provider = new DocumentContentDriver(DocumentService.App.getInstance(), state);
	DocumentPresenter presenter = new DocumentPresenter(provider);
	DocumentView docView = DocumentViewFactory.getDocumentView();
	DocumentReaderView readerView = DocumentViewFactory.getDocumentReaderView(presenter);
	presenter.setViews(docView, readerView);
	document = presenter;
	return document;
    }

    protected WorkspaceComponent createContext() {
	DocumentContextProvider provider = new DocumentContextProvider(DocumentService.App.getInstance(), state);
	NavigationView view = DocumentViewFactory.getNavigationtView();
	NavigatorPresenter context = new NavigatorPresenter(provider, view);
	return context;
    }

    public AjoAdmin getAdmin() {
	// TODO Auto-generated method stub
	return null;
    }

}
