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

package org.ourproject.kune.chat.client.rooms;

public interface MultiRoomView {

    public static final int STATUS_ONLINE = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_INVISIBLE = 3;
    public static final int STATUS_XA = 4;
    public static final int STATUS_AWAY = 5;
    public static final int STATUS_MESSAGE = 6;

    void addRoomUsersPanel(RoomUserListView view);

    void show();

    String getInputText();

    void addRoom(Room room);

    void setInputText(String savedInput);

    void setSubject(String subject);

    void showUserList(RoomUserListView usersListView);

    void setSendEnabled(boolean enabled);

    void clearInputText();

    void highlightRoom(Room room);

    void setSubjectEditable(boolean editable);

    void setStatus(int statusOnline);

    void addPresenceBuddy(String buddyName, String title, int status);

    void removePresenceBuddy(String buddyName);

}
