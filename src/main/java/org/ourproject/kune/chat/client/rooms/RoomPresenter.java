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

package org.ourproject.kune.chat.client.rooms;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.View;

import to.tipit.gwtlib.FireLog;

import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public class RoomPresenter implements Room {

    private final static String[] USERCOLORS = { "green", "navy", "black", "grey", "olive", "teal", "blue", "lime",
	    "purple", "fuchsia", "maroon", "red" };

    private int currentColor;

    private RoomView view;
    private String input;
    private String subject;
    private final String sessionUserAlias;
    // FIXME: probablemente este mapa tiene más sentido en RoomUserList
    private final Map users;
    private final String roomName;

    private final RoomUserList userList;

    private XmppRoom handler;

    private final RoomListener listener;

    private boolean closeConfirmed;

    // TODO: la información del usuario no está disponible (ni debe estar ;)
    // cuando se crea
    // el room presenter => solución: usar setAlias, setRoomName,
    // setUserType... etc...
    public RoomPresenter(final RoomListener listener, final String roomName, final String userAlias,
	    final UserType userType, final RoomUserList userList) {
	this.listener = listener;
	this.roomName = roomName;
	this.sessionUserAlias = userAlias;
	this.userList = userList;
	this.input = "";
	this.currentColor = 0;
	users = new HashMap();
    }

    public void init(final RoomView view) {
	this.view = view;
	view.showRoomName(roomName);
	closeConfirmed = false;
    }

    public View getView() {
	return view;
    }

    public void addMessage(final String userAlias, final String message) {
	String userColor;

	RoomUser user = (RoomUser) users.get(userAlias);
	if (user != null) {
	    userColor = user.getColor();
	} else {
	    FireLog.debug("User " + userAlias + " not in our users list");
	    userColor = "black";
	}
	view.showMessage(userAlias, userColor, message);
	listener.onMessageReceived(this);
    }

    public void addInfoMessage(final String message) {
	view.showInfoMessage(message);
    }

    public void addUser(final String alias, final UserType type) {
	RoomUser user = new RoomUser(alias, getNextColor(), type);
	getUsersList().add(user);
	users.put(alias, user);
    }

    public void removeUser(final String alias) {
	getUsersList().remove((RoomUser) users.get(alias));
    }

    public void addDelimiter(final String datetime) {
	view.showDelimiter(datetime);
    }

    // TODO: ¿no bastaría con saveInput(null)?
    public void clearSavedInput() {
	input = null;
    }

    public String getSessionAlias() {
	return sessionUserAlias;
    }

    public void saveInput(final String inputText) {
	input = inputText;
    }

    public String getSavedInput() {
	return input;
    }

    protected void doClose() {
	handler.logout();
	closeConfirmed = true;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(final String subject) {
	this.subject = subject;
    }

    public String getName() {
	return roomName;
    }

    private String getNextColor() {
	String color = USERCOLORS[currentColor++];
	if (currentColor >= USERCOLORS.length) {
	    currentColor = 0;
	}
	return color;
    }

    public RoomUserList getUsersList() {
	return userList;
    }

    public RoomUserListView getUsersListView() {
	return userList.getView();
    }

    public void setHandler(final XmppRoom handler) {
	this.handler = handler;
	listener.onRoomReady(this);
    }

    public boolean isReady() {
	return handler != null;
    }

    public XmppRoom getHandler() {
	return handler;
    }

    public void onCloseNotConfirmed() {
	closeConfirmed = false;

    }

    public boolean isCloseConfirmed() {
	return closeConfirmed;
    }

    public void activate() {
	view.scrollDown();
    }

}
