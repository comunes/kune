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
            KuneTool tool = (KuneTool) tools[index];
            tool.setWorkspace(view);
            view.addTab(tool.getName());
        }
        this.view.setLogo(workspace.getGroupName());
        if (tools.length > 0)
            this.view.setSelectedTab(0);
    }

    public void onTabSelected(int tabIndex) {
        workspace.getTools()[tabIndex].show();
    }
}
