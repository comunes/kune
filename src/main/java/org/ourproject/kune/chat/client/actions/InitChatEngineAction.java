/*
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

package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.InitDataDTO;

public class InitChatEngineAction implements Action<InitDataDTO> {

    private final ChatClientTool chatTool;

    public InitChatEngineAction(final ChatClientTool chatTool) {
        this.chatTool = chatTool;
    }

    public void execute(final InitDataDTO value) {
        InitDataDTO initData = value;
        ChatState state = new ChatState(initData.getChatHttpBase(), initData.getChatDomain(), initData
                .getChatRoomHost());
        chatTool.initEngine(state);
    }

}
