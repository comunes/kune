package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.chat.client.ui.cnt.ChatContent;
import org.ourproject.kune.chat.client.ui.ctx.ChatContext;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;

class ChatToolComponents {
    private final AbstractClientTool chatClientTool;
    private ChatContent content;
    private ChatContext context;

    public ChatToolComponents(final AbstractClientTool chatClientTool) {
	this.chatClientTool = chatClientTool;
    }

    public ChatContent getContent() {
	if (content == null) {
	    content = ChatFactory.createChatContent();
	}
	return content;
    }

    public ChatContext getContext() {
	if (context == null) {
	    context = ChatFactory.createChatContext();
	}
	return context;
    }

}
