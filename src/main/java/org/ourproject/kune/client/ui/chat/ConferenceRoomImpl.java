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
package org.ourproject.kune.client.ui.chat;

import org.ourproject.kune.client.Session;

import com.google.gwt.user.client.ui.HTML;

public class ConferenceRoomImpl implements ConferenceRoom {
    private ConferenceRoomDialog room = null;
    private String name;

    public void init(ConferenceRoomDialog room) {
        this.room = room;
    }

    public void onSend(String sentence) {
        room.enableSendButton(false);
        room.clearInputArea();
        sentence = sentence.replaceAll("&", "&amp;");
        sentence = sentence.replaceAll("\"", "&quot;");
        sentence = sentence.replaceAll("<", "&lt;");
        sentence = sentence.replaceAll(">", "&gt;");
        sentence = sentence.replaceAll("\n", "<br>\n");
        room.addToConversation(Session.get().currentUser.getNickName(), new HTML(sentence));
    }

    public void onMessage(String nick, String sentence) {
        room.addToConversation(nick, new HTML(sentence));
    }

}
