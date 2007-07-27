package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;


public class ChatModule implements ClientModule {
    public void configure(Register register) {
        register.registerTool("chat", null, new ChatViewFactory());
    }

}
