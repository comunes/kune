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

package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.WorkspaceUIExtensionPoint;

public class MultiRoomPresenter extends AbstractPresenter implements MultiRoom, RoomListener {
    private MultiRoomView view;
    private Room currentRoom;
    private final MultiRoomListener listener;
    private boolean closeAllConfirmed;

    public MultiRoomPresenter(final MultiRoomListener listener) {
        this.listener = listener;
    }

    public void init(final MultiRoomView view) {
        this.view = view;
        closeAllConfirmed = false;

        // only for tests
        view.addPresenceBuddy("fulano", "I'm out for dinner", MultiRoomView.STATUS_AWAY);
        view.addPresenceBuddy("mengano", "Working", MultiRoomView.STATUS_BUSY);
    }

    public Room createRoom(final String roomName, final String userAlias, final UserType userType) {
        Room room = ChatFactory.createRoom(this);
        room.setRoomName(roomName);
        room.setUserAlias(userAlias);
        room.setUserType(userType);
        currentRoom = room;
        view.addRoomUsersPanel(room.getUsersListView());
        view.addRoom(room);
        return currentRoom;
    }

    public void show() {
        view.show();
        closeAllConfirmed = false;
    }

    public void onSend() {
        listener.onSendMessage(currentRoom, view.getInputText());
        // view.setSendEnabled(false);
        view.clearInputText();
    }

    public void closeRoom(final RoomPresenter room) {
        room.doClose();
        listener.onCloseRoom(room);
    }

    public void activateRoom(final Room nextRoom) {
        if (currentRoom != null) {
            currentRoom.saveInput(view.getInputText());
        }
        view.setSendEnabled(nextRoom.isReady());
        view.setInputText(nextRoom.getSavedInput());
        view.setSubject(nextRoom.getSubject());
        view.setSubjectEditable(nextRoom.getUserType().equals(RoomUser.MODERADOR));
        view.showUserList(nextRoom.getUsersListView());
        nextRoom.activate();
        currentRoom = nextRoom;
    }

    public void onRoomReady(final Room room) {
        if (currentRoom == room) {
            view.setSendEnabled(true);
        }
    }

    public void onMessageReceived(final Room room) {
        view.highlightRoom(room);
    }

    public void onCloseAllNotConfirmed() {
        closeAllConfirmed = false;
        view.setSubject("");
    }

    public boolean isCloseAllConfirmed() {
        return closeAllConfirmed;
    }

    public void attachIconToBottomBar(final View view) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ATTACH_TO_EXT_POINT,
                WorkspaceUIExtensionPoint.CONTENT_BOTTOM_ICONBAR, view);
    }

    public void changeRoomSubject(final String text) {
        // FIXME Do the real subject rename
        view.setSubject(text);
        currentRoom.setSubject(text);
    }

    public void onStatusSelected(final int status) {
        view.setStatus(status);
        switch (status) {
        case MultiRoomView.STATUS_AWAY:
            // FIXME
            break;
        default:
            break;
        }
    }

    public void addBuddy(final String shortName, final String longName) {
        // TODO Auto-generated method stub
    }

    public void inviteUserToRoom(final String shortName, final String longName) {
        // TODO Auto-generated method stub
    }

}
