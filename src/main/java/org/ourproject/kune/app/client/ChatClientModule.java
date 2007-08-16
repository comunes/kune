package org.ourproject.kune.app.client;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class ChatClientModule implements ClientModule {

    public void configure(final Register register) {
	register.addTool(new ChatClientTool());
    }

}
