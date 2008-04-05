package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.workspace.Workspace;

@SuppressWarnings("unchecked")
public class StopAction implements Action {

    private final Workspace workspace;

    public StopAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onStop();
    }

    private void onStop() {
        workspace.setVisible(false);
    }
}
