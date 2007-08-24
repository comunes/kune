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

import org.ourproject.kune.chat.client.ui.cnt.ChatContent;
import org.ourproject.kune.chat.client.ui.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.ui.ctx.ChatContext;
import org.ourproject.kune.chat.client.ui.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoom;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoomPanel;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoomPresenter;
import org.ourproject.kune.chat.client.ui.rooms.RoomImages;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.WorkspaceFactory;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

import com.google.gwt.user.client.ui.HTML;

public class ChatFactory {

    public static ChatContent createChatContent() {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	ChatContentPresenter presenter = new ChatContentPresenter(panel);
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

    public static HTML formatter(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");

	// TODO: see emoticons.xml for more
	message = message.replaceAll(":\\)", RoomImages.App.getInstance().smile().getHTML());
	message = message.replaceAll(":-\\)", RoomImages.App.getInstance().smile().getHTML());

	message = message.replaceAll("X-\\(", RoomImages.App.getInstance().angry().getHTML());
	message = message.replaceAll("X\\(", RoomImages.App.getInstance().angry().getHTML());

	message = message.replaceAll(":-D", RoomImages.App.getInstance().grin().getHTML());
	message = message.replaceAll(":D", RoomImages.App.getInstance().grin().getHTML());

	message = message.replaceAll(":\\(", RoomImages.App.getInstance().sad().getHTML());
	message = message.replaceAll(":-\\(", RoomImages.App.getInstance().sad().getHTML());

	message = message.replaceAll(":P", RoomImages.App.getInstance().tongue().getHTML());

	message = message.replaceAll(":\'\\(", RoomImages.App.getInstance().crying().getHTML());

	message = message.replaceAll(":-O", RoomImages.App.getInstance().surprised().getHTML());
	message = message.replaceAll(":O", RoomImages.App.getInstance().surprised().getHTML());

	message = message.replaceAll(":-\\*", RoomImages.App.getInstance().kissing().getHTML());
	message = message.replaceAll(":\\*", RoomImages.App.getInstance().kissing().getHTML());

	message = message.replaceAll(":-\\/", RoomImages.App.getInstance().uncertain().getHTML());
	message = message.replaceAll(":\\/", RoomImages.App.getInstance().uncertain().getHTML());

	message = message.replaceAll(";\\)", RoomImages.App.getInstance().wink().getHTML());
	message = message.replaceAll(";-\\)", RoomImages.App.getInstance().wink().getHTML());

	message = message.replaceAll(":\\?", RoomImages.App.getInstance().wondering().getHTML());

	message = message.replaceAll(":-xX", RoomImages.App.getInstance().love().getHTML());
	message = message.replaceAll(":-X", RoomImages.App.getInstance().love().getHTML());

	return new HTML(message);
    }
}
