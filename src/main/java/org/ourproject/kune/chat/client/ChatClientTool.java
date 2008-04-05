
package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public class ChatClientTool extends AbstractClientTool implements ChatProvider {
    public static final String NAME = "chats";
    public static final String TYPE_ROOT = "chat.root";
    public static final String TYPE_ROOM = "chat.room";
    public static final String TYPE_CHAT = "chat.chat";

    private final ChatToolComponents components;
    private ChatEngine chat;

    public ChatClientTool() {
        super(Kune.I18N.t("chat rooms"));
        components = new ChatToolComponents();
    }

    public ChatEngine getChat() {
        return chat;
    }

    public void initEngine(final ChatState state) {
        this.chat = new ChatEngineXmpp(state);
    }

    public WorkspaceComponent getContent() {
        return components.getContent();
    }

    public WorkspaceComponent getContext() {
        return components.getContext();
    }

    public String getName() {
        return NAME;
    }

    public void setContent(final StateDTO state) {
        components.getContent().setState(state);
    }

    public void setContext(final StateDTO state) {
        components.getContext().setState(state);
    }

}
