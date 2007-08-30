package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.dispatch.Action;

public class ChatLogoutAction implements Action {
    private final ChatEngine engine;

    public ChatLogoutAction(final ChatEngine engine) {
	this.engine = engine;
    }

    public void execute(final Object value, final Object extra) {
	engine.logout();
    }

}
