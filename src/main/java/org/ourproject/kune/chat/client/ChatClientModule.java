/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.actions.ChatLoginAction;
import org.ourproject.kune.chat.client.actions.JoinRoomAction;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

public class ChatClientModule implements ClientModule {

    public void configure(final Register register) {
	ChatClientTool chatTool = new ChatClientTool();
	register.addTool(chatTool);
	register.addAction(WorkspaceEvents.LOGIN, new ChatLoginAction(chatTool.engine));
	register.addAction(ChatEvents.JOIN_ROOM, new JoinRoomAction(chatTool.engine));
    }
}
