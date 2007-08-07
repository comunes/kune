package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.HistoryTokenOld;

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
	String ctxReference = tool.getContextRef();
	String cntReference = tool.getContentRef();
	dispatcher.fireState(HistoryTokenOld.encodeState(StateAction.NAME, state.getGroup().getShortName(), toolName,
		ctxReference, cntReference));
    }
}
