package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.History;

public class LoggedOutAction extends WorkspaceAction {

    public void execute(final Object value, final Object extra) {
	Site.sitebar.showLoggedUser(null);
	String token = History.getToken();
	Services.get().stateManager.onHistoryChanged(token);
    }

}
