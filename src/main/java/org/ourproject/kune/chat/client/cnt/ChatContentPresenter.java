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

package org.ourproject.kune.chat.client.cnt;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetId;
import org.ourproject.kune.platf.client.ui.UnknowComponent;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuimodule.client.EmiteUIDialog;

public class ChatContentPresenter implements ChatContent, ChatRoomListener {

    private final WorkspaceDeckView view;
    private final ChatComponents components;
    private StateDTO state;
    private final EmiteUIDialog emiteUIDialog;

    public ChatContentPresenter(final EmiteUIDialog emiteUIDialog, final WorkspaceDeckView view) {
	this.emiteUIDialog = emiteUIDialog;
	this.view = view;
	this.components = new ChatComponents(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void onEnterRoom() {
	final String roomName = state.getFolder().getName();
	emiteUIDialog.joinRoom(XmppURI.uri(roomName));
    }

    public void setState(final StateDTO state) {
	this.state = state;
	final String typeId = state.getTypeId();
	if (typeId.equals(ChatClientTool.TYPE_ROOT)) {
	    final ChatInfo info = components.getChatInfo();
	    view.show(info.getView());
	    DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXTENSIBLE_WIDGET,
		    ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT);
	} else if (typeId.equals(ChatClientTool.TYPE_ROOM)) {
	    final ChatRoom viewer = components.getChatRoom();
	    view.show(viewer.getView());
	    DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXTENSIBLE_WIDGET,
		    ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT);
	} else {
	    view.show(UnknowComponent.instance.getView());
	    DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXTENSIBLE_WIDGET,
		    ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT);
	}
    }

}
