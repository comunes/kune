package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.KuneTool;

public class WorkspacePresenter implements WorkspaceListener {
    private WorkspaceView view;
    private final Workspace workspace;

    public WorkspacePresenter(Workspace workspace) {
        this.workspace = workspace;
    }

    public void init(WorkspaceView view) {
        this.view = view;

        KuneTool[] tools = workspace.getTools();
        for (int index = 0; index < tools.length; index++) {
            KuneTool module = (KuneTool) tools[index];
            view.addTab(module.getName());
        }
        view.setLogo(workspace.getGroupName());
    }
}
