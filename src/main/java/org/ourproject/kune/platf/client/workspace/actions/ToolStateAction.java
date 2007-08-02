package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.DefaultAction;

public class ToolStateAction extends DefaultAction{
    private final Tool tool;

    public ToolStateAction(Tool tool) {
	this.tool = tool;
    }

    public void execute(Object value) {
	tool.setEncodedState(value);
	workspace.setTool(tool.getName());
	workspace.setContext(tool.getContext());
	workspace.setContent(tool.getContent());
    }

}
