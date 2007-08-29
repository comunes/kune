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

package org.ourproject.kune.chat.client.ui;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.chat.client.ui.cnt.ChatContent;
import org.ourproject.kune.chat.client.ui.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomViewer;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomViewerListener;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomViewerPanel;
import org.ourproject.kune.chat.client.ui.ctx.ChatContext;
import org.ourproject.kune.chat.client.ui.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoom;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoomPanel;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoomPresenter;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.WorkspaceFactory;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;


public class ChatFactory {

    public static ChatContent createChatContent(final ChatEngine engine) {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	ChatContentPresenter presenter = new ChatContentPresenter(engine, panel);
	return presenter;
    }

    public static ChatContext createChatContext() {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	ChatContextPresenter presenter = new ChatContextPresenter(panel);
	return presenter;
    }

    public static RoomsAdmin createRoomsAdmin() {
	ContextItems contextItems = WorkspaceFactory.createContextItems();
	RoomsAdminPresenter presenter = new RoomsAdminPresenter(contextItems);
	return presenter;
    }

    public static MultiRoom createChatMultiRoom() {
	MultiRoomPresenter presenter = new MultiRoomPresenter();
	MultiRoomPanel panel = new MultiRoomPanel(presenter);
	presenter.init(panel);
	return presenter;
    }

    public static ChatRoomViewer createChatRoomViewer(final ChatRoomViewerListener listener) {
	ChatRoomViewerPanel panel = new ChatRoomViewerPanel(listener);
	return panel;
    }

}
