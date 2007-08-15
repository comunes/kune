package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveDocument extends WorkspaceAction {
    public static final String KEY = "docs.save";

    public void execute(final Object value) {
	ContentServiceAsync server = ContentService.App.getInstance();
	ContentDTO content = (ContentDTO) value;
	Window.alert("epa!");
	server.save(user, content, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
	    }

	});
    }

}
