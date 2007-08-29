package org.ourproject.kune.workspace.client.actions;

public class LoginAction extends WorkspaceAction {
    private static final String NAME = "platf.LoginAction";
    public static final String EVENT = "platf.LoginAction";

    public void execute(final Object value, final Object extra) {
	stateManager.reload();
    }

    public String getActionName() {
	return NAME;
    }

    public String getEventName() {
	return EVENT;
    }

}
