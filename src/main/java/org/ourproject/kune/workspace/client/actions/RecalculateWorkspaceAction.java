package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.sitebar.SiteBarFactory;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.google.gwt.user.client.Window;

@SuppressWarnings("unchecked")
public class RecalculateWorkspaceAction implements Action {

    private final Workspace workspace;

    public RecalculateWorkspaceAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onRecalculateWorkspaceAction();
    }

    private void onRecalculateWorkspaceAction() {
        int width = workspace.calculateWidth(Window.getClientWidth());
        int height = workspace.calculateHeight(Window.getClientHeight());
        workspace.adjustSize(width, height);
        SiteBarFactory.getSiteMessage().adjustWidth(width);
    }
}
