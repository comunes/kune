package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.History;

public class LoggedOutAction implements Action {

    public void execute(final Object value, final Object extra, Services services) {
	Site.sitebar.showLoggedUser(null);
	String token = History.getToken();
	Services.get().stateManager.onHistoryChanged(token);
    }

}
