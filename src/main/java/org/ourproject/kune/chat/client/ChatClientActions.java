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

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class ChatClientActions {

    private final Session session;
    private final Provider<ChatClientTool> chatClientToolProvider;
    private final ContentActionRegistry contentActionRegistry;

    public ChatClientActions(final I18nUITranslationService i18n, final Session session,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<ChatClientTool> chatClientToolProvider) {
        this.session = session;
        this.contentActionRegistry = contentActionRegistry;
        this.chatClientToolProvider = chatClientToolProvider;
        createActions();
    }

    private void createActions() {
        final ActionToolbarButtonDescriptor<StateToken> chatAbout = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        chatClientToolProvider.get().getChat().joinRoom(token.toString().replaceAll("\\.", "-"),
                                session.getCurrentUserInfo().getShortName());
                        chatClientToolProvider.get().getChat().show();
                    }
                });
        // chatAbout.setTextDescription(i18n.t("Chat about"));
        chatAbout.setIconUrl("images/emite-room.png");
        chatAbout.setToolTip("Chat and comment this");
        chatAbout.setLeftSeparator(ActionToolbarButtonSeparator.fill);

        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_DOCUMENT);
        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_GALLERY);
        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_WIKIPAGE);
        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_POST);
        contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_UPLOADEDFILE);
    }
}
