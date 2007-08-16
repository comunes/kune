package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class GoParentFolder extends WorkspaceAction {
    public static final String KEY = "platf.GoParentFolder";
    public static final String NAME = "platf.GoParentFolder";

    public void execute(final Object value, final Object extra) {
	goParent(getState());
    }

    private void goParent(final StateDTO state) {
	StateToken token = state.getState();
	token.setDocument(null);
	token.setFolder(state.getFolder().getParentFolderId().toString());
	stateManager.setState(token);
    }

    public String getActionName() {
	return KEY;
    }

    public String getEventName() {
	return NAME;
    }

}
