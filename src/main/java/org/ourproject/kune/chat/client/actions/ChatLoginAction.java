package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.workspace.client.actions.LoginAction;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

public class ChatLoginAction extends WorkspaceAction {
    private static final String NAME = "chat.Login";
    private static final String EVENT = LoginAction.EVENT;
    private final ChatEngine engine;

    public ChatLoginAction(final ChatEngine engine) {
	this.engine = engine;
    }

    public void execute(final Object value, final Object extra) {
	login((UserDTO) value);
    }

    private void login(final UserDTO user) {
	engine.login(user.getChatName(), user.getChatPassword());
    }

    public String getActionName() {
	return NAME;
    }

    public String getEventName() {
	return EVENT;
    }

}
