package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public class WorkspaceDefault {
    private final KunePlatform platform;
    private final WorkspaceView view;
    private Tool currentTool;

    public WorkspaceDefault(KunePlatform platform, WorkspaceView view) {
        this.platform = platform;
        this.view = view;
    }

    public void showError(Throwable caught) {
        
    }

    public void setGroup(GroupDTO group) {
        view.setLogo("group name here");
    }

    public void setSelectedTool(int index) {
        currentTool = platform.getTool(index);
        view.setSelectedTab(index);
    }
}
