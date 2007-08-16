package org.ourproject.kune.workspace.client.actions;

public class InitAction extends WorkspaceAction {
    public static final String KEY = "platf.InitAction";
    public static final String EVENT = "platf.InitAction";

    public void execute(final Object value, final Object extra) {
    }

    public String getActionName() {
	return KEY;
    }

    public String getEventName() {
	return EVENT;
    }
}
