package org.ourproject.kune.chat.server.rpc;

import org.ourproject.kune.chat.client.rpc.KuneChatService;

public class KuneChatServiceDefault implements KuneChatService {
    public String test() {
        return "works!";
    }
}
