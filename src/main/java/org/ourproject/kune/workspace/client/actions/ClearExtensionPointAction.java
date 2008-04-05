package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class ClearExtensionPointAction implements Action<String> {

    private final Workspace workspace;

    public ClearExtensionPointAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final String extId) {
        workspace.clearExtensionPoint(extId);
    }
}
