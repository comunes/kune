/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client;

import org.ourproject.kune.client.ui.chat.ConferenceRoomDialogImpl;
import org.ourproject.kune.client.ui.chat.ChatroomUser;
import org.ourproject.kune.client.ui.chat.ConferenceRoomImpl;

import com.google.gwt.junit.client.GWTTestCase;

public class ChatRoomTest extends GWTTestCase {

    /*
     * Specifies a module to use when running this test case. The returned
     * module must cause the source for this class to be included.
     *
     * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
     */
    public String getModuleName() {
        return "org.ourproject.kune.Main";
    }

    public void testUserLlist() {
        ConferenceRoomImpl chatroomControler = new ConferenceRoomImpl("chat-room", "luther.b");
        ConferenceRoomDialogImpl chat = new ConferenceRoomDialogImpl(chatroomControler);
        chatroomControler.init(chat);
        ChatroomUser anne = new ChatroomUser("anne.h", false);
        ChatroomUser luther = new ChatroomUser("luther.b", false);

        assertTrue(chat.usersInChat() == 0);
        chat.addUser(anne);
        chat.addUser(luther);
        chat.delUser(anne);
        chat.delUser(luther);
        assertTrue(chat.usersInChat() == 0);
    }
}
