
package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.ChatContent;
import org.ourproject.kune.chat.client.ctx.ChatContext;

class ChatToolComponents {
    private ChatContent content;
    private ChatContext context;

    public ChatToolComponents() {
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
