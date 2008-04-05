package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.StateManager;

public class GotoAction implements Action<String> {

    private final StateManager stateManager;

    public GotoAction(final StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void execute(final String value) {
        onGoto(value);
    }

    private void onGoto(final String token) {
        stateManager.setState(new StateToken(token));
    }
}
