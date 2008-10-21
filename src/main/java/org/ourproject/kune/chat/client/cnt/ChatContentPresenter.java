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
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.ioc.Provider;

public class ChatContentPresenter implements ChatContent {

    private final WorkspaceDeckView view;
    private StateDTO state;
    private final EmiteUIDialog emiteUIDialog;
    private final Provider<ChatInfo> chatInfoProvider;
    private final Provider<ChatRoom> chatRoomProvider;

    public ChatContentPresenter(final EmiteUIDialog emiteUIDialog, final WorkspaceDeckView view,
            final Provider<ChatInfo> chatInfoProvider, final Provider<ChatRoom> chatRoomProvider) {
        this.emiteUIDialog = emiteUIDialog;
        this.view = view;
        this.chatInfoProvider = chatInfoProvider;
        this.chatRoomProvider = chatRoomProvider;
    }

    public View getView() {
        return view;
    }

    public void onEnterRoom() {
        final String roomName = state.getContainer().getName();
        emiteUIDialog.joinRoom(XmppURI.uri(roomName));
    }

    public void setState(final StateDTO state) {
        this.state = state;
        final String typeId = state.getTypeId();
        if (typeId.equals(ChatClientTool.TYPE_ROOT)) {
            chatInfoProvider.get().show();
        } else if (typeId.equals(ChatClientTool.TYPE_ROOM)) {
            chatRoomProvider.get().show();
        } else {
            Log.error("Programming error: unknown component!! please contact kune-devel@lists-ourproject.org");
        }
    }
}
