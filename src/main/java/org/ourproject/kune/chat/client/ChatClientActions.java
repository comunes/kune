/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.chat.client;

import org.ourproject.kune.blogs.client.BlogClientTool;
import org.ourproject.kune.chat.client.ctx.room.AddRoom;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.gallery.client.GalleryClientTool;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.wiki.client.WikiClientTool;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;
import org.ourproject.kune.workspace.client.cnt.ContentActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class ChatClientActions {

    private final Session session;
    private final Provider<ChatEngine> chatEngineProvider;
    private final ContentActionRegistry contentActionRegistry;
    private final I18nUITranslationService i18n;
    private final ContextActionRegistry contextActionRegistry;
    private final Provider<AddRoom> addRoomProvider;
    private final Provider<DeferredCommandWrapper> deferredWrapper;

    public ChatClientActions(final I18nUITranslationService i18n, final Session session,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<ChatEngine> chatEngine, final Provider<AddRoom> addRoomProvider,
            final Provider<DeferredCommandWrapper> deferredWrapper) {
        this.i18n = i18n;
        this.session = session;
        this.contentActionRegistry = contentActionRegistry;
        this.contextActionRegistry = contextActionRegistry;
        this.chatEngineProvider = chatEngine;
        this.addRoomProvider = addRoomProvider;
        this.deferredWrapper = deferredWrapper;
        createActions();
    }

    private void createActions() {
        final ActionToolbarButtonDescriptor<StateToken> chatAbout = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, AbstractFoldableContentActions.CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        deferredWrapper.get().addCommand(new Listener0() {
                            public void onEvent() {
                                final String subject = i18n.t("Chat about: [%s]", session.getContentState().getTitle());
                                chatEngineProvider.get().joinRoom(token.toString().replaceAll("\\.", "-"), subject,
                                        session.getCurrentUserInfo().getShortName());
                                chatEngineProvider.get().show();
                            }
                        });
                    }
                });
        // chatAbout.setTextDescription(i18n.t("Chat about"));
        chatAbout.setIconUrl("images/emite-room.png");
        chatAbout.setToolTip("Chat and comment this");
        chatAbout.setLeftSeparator(ActionToolbarButtonSeparator.fill);
        chatAbout.setEnableCondition(notDeleted());

        final ActionToolbarButtonDescriptor<StateToken> joinRoom = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, AbstractFoldableContentActions.CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        final ChatEngine chatEngine = chatEngineProvider.get();
                        chatEngine.joinRoom(session.getContainerState().getContainer().getName(),
                                session.getCurrentUserInfo().getShortName());
                        chatEngine.show();
                    }
                });
        joinRoom.setTextDescription(i18n.t("Enter room"));
        joinRoom.setToolTip(i18n.t("Enter in this chat room"));
        joinRoom.setMustBeAuthenticated(true);

        final ActionToolbarButtonDescriptor<StateToken> addRoom = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Administrator, AbstractFoldableContentActions.CONTEXT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
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

    private ActionEnableCondition<StateToken> notDeleted() {
        return new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken token) {
                final boolean isNotDeleted = !(session.isCurrentStateAContent() && session.getContentState().getStatus().equals(
                        ContentStatus.inTheDustbin));
                return isNotDeleted;
            }
        };
    }
}
