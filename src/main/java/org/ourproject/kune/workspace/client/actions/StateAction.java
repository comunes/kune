package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.HistoryTokenOld;

public class StateAction extends WorkspaceAction {
    public static final String NAME = "group";

    public void execute(final Object value) {
	HistoryTokenOld token = new HistoryTokenOld(value.toString());
	String groupName = token.getParam(0);
	if (!state.isCurrentGroup(groupName)) {
	    changeGroup();
	}
	String toolName = token.getParam(1);
	Tool tool = app.getTool(toolName);
	tool.setReferences(token.getParam(2, null), token.getParam(3, null));
	workspace.setTool(tool.getName());
	workspace.setContext(tool.getContext());
	workspace.setContent(tool.getContent());

    }

    private void changeGroup() {
	// TODO Auto-generated method stub
    }
}
