package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.RenameTokenActionParams;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class RenameTokenAction implements Action<RenameTokenActionParams> {

    private final StateManager stateManager;
    private final Session session;

    public RenameTokenAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final RenameTokenActionParams params) {
        onRenameToken(params);
    }

    private void onRenameToken(final RenameTokenActionParams params) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.rename(session.getUserHash(), currentState.getGroup().getShortName(), params.getToken(), params
                .getNewName(), new AsyncCallbackSimple<String>() {
            public void onSuccess(final String result) {
                stateManager.reloadContextAndTitles();
                Site.hideProgress();
            }
        });
    }
}
