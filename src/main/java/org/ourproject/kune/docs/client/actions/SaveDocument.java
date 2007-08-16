package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveDocument extends WorkspaceAction {
    public static final String KEY = "docs.save";

    public void execute(final Object value, final Object extra) {
	save((ContentDTO) value);
    }

    private void save(final ContentDTO content) {
	ContentServiceAsync server = ContentService.App.getInstance();
	server.save(user, content.getDocumentId(), content.getContent(), new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
	    }

	});
    }

}
