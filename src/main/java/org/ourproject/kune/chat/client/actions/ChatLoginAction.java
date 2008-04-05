package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatProvider;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

public class ChatLoginAction implements Action<UserInfoDTO> {
    private final ChatProvider provider;

    public ChatLoginAction(final ChatProvider provider) {
        this.provider = provider;
    }

    public void execute(final UserInfoDTO value) {
        login(value);
    }

    private void login(final UserInfoDTO user) {
        provider.getChat().login(user.getChatName(), user.getChatPassword());
    }
}
