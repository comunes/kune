package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.SimpleController;
import org.ourproject.kune.platf.client.utils.Convert;

public class WorkspaceActions extends SimpleController {
    public static String NAME = "workspace";
    
    public WorkspaceActions(WorkspaceDefault workspace) {
        addAction("tab", new TabAction(workspace));
    }

    static class TabAction implements Action {
        private final WorkspaceDefault workspace;

        public TabAction(WorkspaceDefault workspace) {
            this.workspace = workspace;
        }

        public void execute(String value) {
            workspace.setSelectedTool(Convert.toInt(value));
        }
    }
    
}
