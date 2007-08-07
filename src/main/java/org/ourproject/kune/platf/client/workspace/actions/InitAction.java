package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.dispatch.HistoryTokenOld;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.UIObject;

public class InitAction extends WorkspaceAction {
    public static final String NAME = "init";

    public void execute(final Object value) {

	String token = History.getToken();
	if (token.length() > 0) {
	    dispatcher.onHistoryChanged(token);
	} else {
	    KuneServiceAsync server = KuneService.App.getInstance();
	    server.getDefaultGroup(userHash, new AsyncCallback() {

		public void onFailure(final Throwable caught) {
		    workspace.showError(caught);
		}

		public void onSuccess(final Object result) {
		    GroupDTO group = (GroupDTO) result;
		    state.setGroup(group);
		    workspace.setGroup(group);
		    UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);
		    dispatcher.fireState(HistoryTokenOld.encode(TabAction.NAME, app.getDefaultToolName()));
		}
	    });
	}
    }
}
