package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

public class GotoContainerAction implements Action<Long> {

    private final Session session;
    private final StateManager stateManager;

    public GotoContainerAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final Long value) {
        onGoto(value);
    }

    private void onGoto(final Long folderId) {
        StateToken newStateToken = session.getCurrentState().getStateToken();
        newStateToken.setDocument(null);
        newStateToken.setFolder(folderId.toString());
        stateManager.setState(newStateToken);
    }
}
