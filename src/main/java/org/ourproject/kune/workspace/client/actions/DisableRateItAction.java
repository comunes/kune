package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class DisableRateItAction implements Action<Object> {

    private final Workspace workspace;

    public DisableRateItAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onDisableRateItAction();
    }

    private void onDisableRateItAction() {
        workspace.getContentBottomToolBarComponent().setEnabledRateIt(false);
    }
}
