package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;

public class DocumentTool extends AbstractTool {
    private DocumentPresenter content;
    private NavigatorPresenter context;

    public DocumentTool() {
	super("docs");
    }

    // TODO: translate
    public String getCaption() {
	return "documentos";
    }

    public String getEncodedState() {
	return HistoryToken.encode(getName(), "");
    }

    public void setEncodedState(Object value) {
    }

    public WorkspaceComponent getContent() {
	if (content == null) {
	    DocumentPanel panel = new DocumentPanel();
	    this.content = new DocumentPresenter(panel);
	}
	return content;
    }

    public WorkspaceComponent getContext() {
	if (context == null) {
	    NavigationPanel panel = new NavigationPanel();
	    this.context = new NavigatorPresenter(panel);
	}
	return context;
    }

}
