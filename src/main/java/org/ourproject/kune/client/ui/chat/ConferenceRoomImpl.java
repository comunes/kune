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
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.user.client.ui.HTML;

public class ConferenceRoomImpl implements ConferenceRoom {
    private ConferenceRoomDialog room = null;

    public void init(ConferenceRoomDialog room) {
        this.room = room;
    }

    public void onSend(String sentence) {
        room.enableSendButton(false);
        room.clearInputArea();
        sentence.replaceAll("\"", "&quot;");
        sentence.replaceAll("&", "&amp;");
        sentence.replaceAll("<", "&lt;");
        sentence.replaceAll(">", "&gt;");
        sentence.replaceAll("\\n", "<br>");
        SiteMessageDialog.get().setMessageInfo(sentence);
        // TODO test escape text
        room.addToConversation(Session.get().currentUser.getNickName(), new HTML(sentence));
    }

    public void onMessage(String nick, String sentence) {
        room.addToConversation(nick, new HTML(sentence));
    }

}
