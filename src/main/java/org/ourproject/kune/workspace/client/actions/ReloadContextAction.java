package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.StateManager;

@SuppressWarnings("unchecked")
public class ReloadContextAction implements Action {

    private final StateManager stateManager;

    public ReloadContextAction(final StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void execute(final Object value) {
        onReloadContext();
    }

    private void onReloadContext() {
        stateManager.reloadContextAndTitles();
    }
}
