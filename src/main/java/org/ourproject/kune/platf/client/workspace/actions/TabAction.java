package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.DefaultAction;

public class TabAction extends DefaultAction {

    public void execute(Object value) {
	String toolName = (String) value;
	if (!state.isCurrentTool(toolName)) {
	    changeTab(toolName);
	}
    }

    public void changeTab(String toolName) {
	Tool tool = app.getTool(toolName);
	dispatcher.fireState(tool.getEncodedState());
    }

}
