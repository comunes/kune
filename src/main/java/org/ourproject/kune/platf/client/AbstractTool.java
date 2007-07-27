package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.workspace.WorkspaceView;

public abstract class AbstractTool implements KuneTool {
    protected WorkspaceView workspaceView;

    public void setWorkspace(WorkspaceView workspaceView) {
        this.workspaceView = workspaceView;
    }

    public void show() {
        // TODO Auto-generated method stub

    }

}
