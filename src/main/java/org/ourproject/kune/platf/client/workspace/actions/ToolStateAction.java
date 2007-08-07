package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Tool;

public abstract class ToolStateAction extends WorkspaceAction {
    private final Tool tool;

    public ToolStateAction(final Tool tool) {
	this.tool = tool;
    }

    public void execute(final Object value) {
    }

}
