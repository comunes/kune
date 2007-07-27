package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.SimpleController;
import org.ourproject.kune.platf.client.utils.Convert;

public class WorkspaceActions extends SimpleController {
    public static final String NAME = "workspace";
    
    public WorkspaceActions(WorkspacePresenter workspace) {
        addAction("tab", new TabAction(workspace));
    }

    static class TabAction implements Action {
        private final WorkspacePresenter workspace;

        public TabAction(WorkspacePresenter workspace) {
            this.workspace = workspace;
        }

        public void execute(String value) {
            workspace.setSelectedTool(Convert.toInt(value));
        }
    }
    
}
