package org.ourproject.kune.chat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.server.ChatServerTool;

public class ChatToolTest {

    @Test
    public void clientAndServerAreSync() {
	assertEquals(ChatServerTool.NAME, ChatClientTool.NAME);
	assertEquals(ChatServerTool.TYPE_ROOT, ChatClientTool.TYPE_ROOT);
	assertEquals(ChatServerTool.TYPE_ROOM, ChatClientTool.TYPE_ROOM);
	assertEquals(ChatServerTool.TYPE_CHAT, ChatClientTool.TYPE_CHAT);
    }
}
