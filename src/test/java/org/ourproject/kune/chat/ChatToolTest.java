/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
