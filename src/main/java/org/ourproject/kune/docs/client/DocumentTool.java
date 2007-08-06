package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.editor.DocumentEditor;
import org.ourproject.kune.docs.client.reader.DocumentReader;
import org.ourproject.kune.docs.client.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.admin.AjoAdmin;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;
import org.ourproject.kune.platf.client.workspace.navigation.NavigatorPresenter;

public class DocumentTool extends AbstractTool {
    public static final String NAME = "docs";
    private DocumentReader document;

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

    public void setEncodedState(final Object value) {
	getContext().setEncodedState((String) value);
	getContent().setEncodedState((String) value);
    }

    protected WorkspaceComponent createContent() {
	DocumentContentProvider provider = new DocumentContentProvider(DocumentService.App.getInstance(), state);
	DocumentReaderView view = DocumentViewFactory.getDocumentView();
	document = new DocumentReaderPresenter(dispatcher, provider, view, "welcome");
	return document;
    }

    protected WorkspaceComponent createContext() {
	DocumentContextProvider provider = new DocumentContextProvider(DocumentService.App.getInstance(), state);
	NavigationView view = DocumentViewFactory.getNavigationtView();
	NavigatorPresenter context = new NavigatorPresenter(provider, view, "home");
	return context;
    }

    public DocumentEditor getEditor() {
	// TODO Auto-generated method stub
	return null;
    }

    public AjoAdmin getAdmin() {
	// TODO Auto-generated method stub
	return null;
    }

}
