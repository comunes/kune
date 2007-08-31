package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.InitDataDTO;

import to.tipit.gwtlib.FireLog;

public class InitChatEngineAction implements Action {

    private final ChatClientTool chatTool;

    public InitChatEngineAction(final ChatClientTool chatTool) {
	this.chatTool = chatTool;
    }

    public void execute(final Object value, final Object extra, final Services services) {
	InitDataDTO initData = (InitDataDTO) value;
	ChatState state = new ChatState(initData.getChatHttpBase(), initData.getChatDomain(), initData
		.getChatRoomHost());
	chatTool.initEngine(state);
	FireLog.debug("chat engine initialized");
    }

}
