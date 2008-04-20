/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.actions.AddRoomAction;
import org.ourproject.kune.chat.client.actions.ChatLoginAction;
import org.ourproject.kune.chat.client.actions.ChatLogoutAction;
import org.ourproject.kune.chat.client.actions.InitChatEngineAction;
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
    }
}
