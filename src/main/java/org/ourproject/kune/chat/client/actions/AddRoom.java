package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

public class AddRoom extends WorkspaceAction {
    private static final String KEY = "chats.AddRoom";
    private static final String EVENT = "chats.AddRoom";

    public void execute(final Object value, final Object extra) {
    }

    public String getActionName() {
	return KEY;
    }

    public String getEventName() {
	return EVENT;
    }

}
