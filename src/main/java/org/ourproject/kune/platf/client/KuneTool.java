package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.workspace.WorkspaceView;

public interface KuneTool {
    String getName();
    void show();
    void setWorkspace(WorkspaceView workspaceView);
}
