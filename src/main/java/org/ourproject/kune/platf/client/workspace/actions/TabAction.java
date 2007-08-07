package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Tool;

public class TabAction extends WorkspaceAction {
    public static final String NAME = "tab";

    /**
     * @param (String)toolName
     */
    public void execute(final Object value) {
	String toolName = (String) value;
	if (!state.isCurrentTool(toolName)) {
	    changeTab(toolName);
	}

    }

    public void changeTab(final String toolName) {
	Tool tool = app.getTool(toolName);
	state.setCurrentToolName(toolName);
	dispatcher.fireState(tool.getEncodedState());
    }

}
