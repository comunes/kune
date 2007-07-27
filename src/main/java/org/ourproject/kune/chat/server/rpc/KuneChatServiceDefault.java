package org.ourproject.kune.chat.server.rpc;

import org.ourproject.kune.chat.client.rpc.ChatService;

public class KuneChatServiceDefault implements ChatService {
    public String test() {
        return "works!";
    }
}
