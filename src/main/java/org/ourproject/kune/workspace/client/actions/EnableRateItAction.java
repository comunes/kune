package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.workspace.Workspace;

@SuppressWarnings("unchecked")
public class EnableRateItAction implements Action {

    private final Workspace workspace;

    public EnableRateItAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onEnableRateItAction();
    }

    private void onEnableRateItAction() {
        workspace.getContentBottomToolBarComponent().setEnabledRateIt(true);
    }
}
