package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class InitAction extends WorkspaceAction {
    public static final String NAME = "init";

    public void execute(final Object value) {
	KuneServiceAsync server = KuneService.App.getInstance();
	server.getDefaultGroup(user, new AsyncCallback() {

	    public void onFailure(final Throwable caught) {
		workspace.showError(caught);
	    }

	    public void onSuccess(final Object result) {
		GroupDTO group = (GroupDTO) result;
		stateManager.setState(group);
	    }
	});
    }
}
