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
 */package org.ourproject.kune.chat.client;

import org.ourproject.kune.blogs.client.BlogClientTool;
import org.ourproject.kune.chat.client.ctx.room.AddRoom;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.gallery.client.GalleryClientTool;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.wiki.client.WikiClientTool;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class ChatClientActions {

    private final Session session;
    private final Provider<ChatEngine> chatEngineProvider;
    private final ContentActionRegistry contentActionRegistry;
    private final I18nUITranslationService i18n;
    private final ContextActionRegistry contextActionRegistry;
    private final Provider<AddRoom> addRoomProvider;

    public ChatClientActions(final I18nUITranslationService i18n, final Session session,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<ChatEngine> chatEngine, Provider<AddRoom> addRoomProvider) {
        this.i18n = i18n;
        this.session = session;
        this.contentActionRegistry = contentActionRegistry;
        this.contextActionRegistry = contextActionRegistry;
        this.chatEngineProvider = chatEngine;
        this.addRoomProvider = addRoomProvider;
        createActions();
    }

    private void createActions() {
        final ActionToolbarButtonDescriptor<StateToken> chatAbout = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        chatEngineProvider.get().joinRoom(token.toString().replaceAll("\\.", "-"),
                                session.getCurrentUserInfo().getShortName());
                        chatEngineProvider.get().show();
                    }
                });
        // chatAbout.setTextDescription(i18n.t("Chat about"));
        chatAbout.setIconUrl("images/emite-room.png");
        chatAbout.setToolTip("Chat and comment this");
        chatAbout.setLeftSeparator(ActionToolbarButtonSeparator.fill);

        ActionToolbarButtonDescriptor<StateToken> joinRoom = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(StateToken token) {
                        ChatEngine chatEngine = chatEngineProvider.get();
                        chatEngine.joinRoom(session.getContainerState().getContainer().getName(),
                                session.getCurrentUserInfo().getShortName());
                        chatEngine.show();
                    }
                });
        joinRoom.setTextDescription(i18n.t("Enter room"));
        joinRoom.setToolTip(i18n.t("Enter in this chat room"));
        joinRoom.setMustBeAuthenticated(true);

        ActionToolbarButtonDescriptor<StateToken> addRoom = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(StateToken token) {
                        addRoomProvider.get().show();
                    }
                });
        addRoom.setTextDescription(i18n.t("New room"));
        addRoom.setToolTip(i18n.t("Create a new chat room"));
        addRoom.setMustBeAuthenticated(true);

        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_DOCUMENT);
        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(chatAbout, WikiClientTool.TYPE_WIKIPAGE);
        contentActionRegistry.addAction(chatAbout, BlogClientTool.TYPE_POST);
        contentActionRegistry.addAction(chatAbout, WikiClientTool.TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(chatAbout, GalleryClientTool.TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(chatAbout, BlogClientTool.TYPE_UPLOADEDFILE);

        contextActionRegistry.addAction(addRoom, ChatClientTool.TYPE_ROOT, ChatClientTool.TYPE_ROOM);
        contentActionRegistry.addAction(joinRoom, ChatClientTool.TYPE_ROOM);
    }
}
