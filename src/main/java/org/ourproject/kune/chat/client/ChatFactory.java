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

package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.ChatContent;
import org.ourproject.kune.chat.client.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.info.ui.ChatInfoPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControl;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlPresenter;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPresenter;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomControlPanel;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomPanel;
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.chat.client.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.container.Provider;

public class ChatFactory {

    private static I18nTranslationService i18n;
    private static Provider<StateManager> stateManagerProvider;
    private static Session session;
    private static Provider<ContentServiceAsync> contentServiceProvider;

    public static ChatContent createChatContent(final EmiteUIDialog emiteUIDialog, final I18nTranslationService i18n,
	    final Provider<StateManager> stateManagerProvider, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	ChatFactory.i18n = i18n;
	ChatFactory.stateManagerProvider = stateManagerProvider;
	ChatFactory.session = session;
	ChatFactory.contentServiceProvider = contentServiceProvider;
	final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	final ChatContentPresenter presenter = new ChatContentPresenter(emiteUIDialog, panel);
	return presenter;
    }

    public static ChatContext createChatContext() {
	final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	final ChatContextPresenter presenter = new ChatContextPresenter(panel);
	return presenter;
    }

    public static ChatInfo createChatInfo(final ChatRoomListener listener) {
	final ChatInfoPanel panel = new ChatInfoPanel(listener);
	return panel;
    }

    public static ChatRoomControl createChatRoomControlViewer(final ChatRoomListener listener) {
	final ChatRoomControlPanel panel = new ChatRoomControlPanel(listener, i18n);
	final ChatRoomControlPresenter presenter = new ChatRoomControlPresenter(panel);
	return presenter;
    }

    public static ChatRoom createChatRoomViewer(final ChatRoomListener listener) {
	final ChatRoomPanel panel = new ChatRoomPanel();
	final ChatRoomPresenter presenter = new ChatRoomPresenter(panel);
	return presenter;
    }

    public static RoomsAdmin createRoomsAdmin() {
	final ContextItems contextItems = WorkspaceFactory.createContextItems();
	final RoomsAdminPresenter presenter = new RoomsAdminPresenter(contextItems, i18n, stateManagerProvider,
		session, contentServiceProvider);
	return presenter;
    }

}
