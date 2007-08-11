package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

public class StateManagerDefault implements StateManager {
    private final Application app;
    private final State state;
    private final KuneServiceAsync server;

    public StateManagerDefault(final Application app, final State state) {
	this.app = app;
	this.state = state;
	this.server = KuneService.App.getInstance();
    }

    public void onHistoryChanged(final String historyToken) {
	onHistoryChanged(new HistoryToken(historyToken));
    }

    private void onHistoryChanged(final HistoryToken newState) {

    }

    public String getUser() {
	return state.user;
    }

}
