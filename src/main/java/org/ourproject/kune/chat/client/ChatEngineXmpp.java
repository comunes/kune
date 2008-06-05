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

import java.util.Date;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.emite.client.core.signal.Listener;
import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.emiteuimodule.client.UserChatOptions;
import com.calclab.emiteuimodule.client.params.AvatarProvider;
import com.calclab.emiteuimodule.client.status.OwnPresence.OwnStatus;
import com.google.gwt.user.client.Window;

class ChatEngineXmpp implements ChatEngine {
    private final ChatOptions chatOptions;
    private final EmiteUIDialog emiteDialog;
    private final I18nTranslationService i18n;

    public ChatEngineXmpp(final EmiteUIDialog emiteUIDialog, final ChatOptions chatOptions,
            final I18nTranslationService i18n) {
        this.emiteDialog = emiteUIDialog;
        this.chatOptions = chatOptions;
        this.i18n = i18n;
    }

    public ChatOptions getChatOptions() {
        return chatOptions;
    }

    public void joinRoom(final String roomName, final String userAlias) {
        XmppURI roomURI = XmppURI.uri(roomName + "@" + chatOptions.roomHost + "/"
                + XmppURI.jid(chatOptions.userOptions.getUserJid()));
        emiteDialog.joinRoom(roomURI);
    }

    public void login(final String chatName, final String chatPassword) {
        final String resource = "emiteui-" + new Date().getTime() + "-kune"; // +
        // getGwtMetaProperty(GWT_PROPERTY_RELEASE);
        // FIXME, get this from user profile
        final UserChatOptions userChatOptions = new UserChatOptions(chatName + "@" + chatOptions.domain, chatPassword,
                resource, "blue", SubscriptionMode.autoAcceptAll, true);
        // FIXME: Avatar provider
        final AvatarProvider avatarProvider = new AvatarProvider() {
            public String getAvatarURL(XmppURI userURI) {
                return "images/person-def.gif";
            }
        };

        final String initialWindowTitle = Window.getTitle();
        chatOptions.userOptions = userChatOptions;
        emiteDialog.start(userChatOptions, chatOptions.httpBase, chatOptions.roomHost, avatarProvider, i18n.t("Chat"));
        emiteDialog.show(OwnStatus.online);
        // emiteDialog.hide();
        emiteDialog.onChatAttended(new Listener<String>() {
            public void onEvent(final String parameter) {
                Window.setTitle(initialWindowTitle);
            }
        });
        emiteDialog.onChatUnattendedWithActivity(new Listener<String>() {
            public void onEvent(final String chatTitle) {
                Window.setTitle("(* " + chatTitle + ") " + initialWindowTitle);
            }
        });
    }

    public void logout() {
        if (!emiteDialog.isDialogNotStarted()) {
            emiteDialog.closeAllChats(false);
            emiteDialog.setOwnPresence(OwnStatus.offline);
        }
    }
}
