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

import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;

public interface ConferenceRoomDialog {

	void addTimeDelimiter(String dateTime);

	void addToConversation(String nick, HTML chat);

	void addUser(ChatroomUser user);

    void adjustSize(int frameWidth, int frameHeight);

    void clearConversation();

    void clearInputArea();

    void clearUsers();

	void delUser(ChatroomUser user);

	void enableSendButton(boolean enabled);

	void permitSubjectChange(boolean permit);

    void setCaption(String caption);

	void setSubject(String chatroomSubject);

	void setUsers(Vector userList);

    void showActivityInTitle(boolean activity);

    int usersInChat();

}
