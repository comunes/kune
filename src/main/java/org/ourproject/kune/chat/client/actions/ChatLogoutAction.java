package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatProvider;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;

public class ChatLogoutAction implements Action {
    private final ChatProvider provider;

    public ChatLogoutAction(final ChatProvider provider) {
	this.provider = provider;
    }

    public void execute(final Object value, final Object extra, Services services) {
	provider.getChat().logout();
    }

}
