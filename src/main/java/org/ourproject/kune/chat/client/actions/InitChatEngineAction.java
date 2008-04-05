package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.InitDataDTO;

public class InitChatEngineAction implements Action<InitDataDTO> {

    private final ChatClientTool chatTool;

    public InitChatEngineAction(final ChatClientTool chatTool) {
        this.chatTool = chatTool;
    }

    public void execute(final InitDataDTO value) {
        InitDataDTO initData = value;
        ChatState state = new ChatState(initData.getChatHttpBase(), initData.getChatDomain(), initData
                .getChatRoomHost());
        chatTool.initEngine(state);
    }

}
