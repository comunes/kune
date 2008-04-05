
package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.actions.AddRoomAction;
import org.ourproject.kune.chat.client.actions.ChatLoginAction;
import org.ourproject.kune.chat.client.actions.ChatLogoutAction;
import org.ourproject.kune.chat.client.actions.InitChatEngineAction;
import org.ourproject.kune.chat.client.actions.JoinRoomAction;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

public class ChatClientModule implements ClientModule {

    private final Session session;
    private final StateManager stateManager;
    private final ChatClientTool chatTool;

    public ChatClientModule(final Session session, final StateManager stateManager, final ChatClientTool chatTool) {
        this.session = session;
        this.stateManager = stateManager;
        this.chatTool = chatTool;
    }

    public void configure(final Register register) {
        register.addAction(WorkspaceEvents.INIT_DATA_RECEIVED, new InitChatEngineAction(chatTool));
        register.addAction(WorkspaceEvents.USER_LOGGED_IN, new ChatLoginAction(chatTool));
        ChatLogoutAction logoutAction = new ChatLogoutAction(chatTool);
        register.addAction(WorkspaceEvents.USER_LOGGED_OUT, logoutAction);
        register.addAction(WorkspaceEvents.STOP_APP, logoutAction);
        register.addAction(ChatEvents.ADD_ROOM, new AddRoomAction(session, stateManager));
        register.addAction(ChatEvents.JOIN_ROOM, new JoinRoomAction(chatTool));
    }
}
