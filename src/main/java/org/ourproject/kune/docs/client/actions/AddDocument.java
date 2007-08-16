package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddDocument extends WorkspaceAction {
    public static final String EVENT = "docs.addDocument";
    private static final String NAME = "docs.AddDocument";

    public void execute(final Object value, final Object extra) {
	addDocument((String) value, getState().getFolder());
    }

    private void addDocument(final String name, final ContainerDTO containerDTO) {
	ContentServiceAsync server = ContentService.App.getInstance();
	server.addContent(user, containerDTO.getId(), name, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
	    }

	    public void onSuccess(final Object result) {
		StateDTO content = (StateDTO) result;
		stateManager.setState(content);
	    }
	});
    }

    public String getActionName() {
	return NAME;
    }

    public String getEventName() {
	return EVENT;
    }

}
