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

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.services.Kune;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.core.bosh.BoshOptions;
import com.calclab.emite.client.im.roster.Roster.SubscriptionMode;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuiplugin.client.EmiteUIPlugin;
import com.calclab.emiteuiplugin.client.UserChatOptions;
import com.calclab.emiteuiplugin.client.dialog.OwnPresence;
import com.calclab.emiteuiplugin.client.dialog.OwnPresence.OwnStatus;
import com.calclab.emiteuiplugin.client.params.MultiChatCreationParam;

class ChatEngineXmpp implements ChatEngine {
    private final ChatState state;

    public ChatEngineXmpp(final ChatState state) {
	this.state = state;
    }

    public ChatState getState() {
	return state;
    }

    public void login(final String chatName, final String chatPassword) {
	UserChatOptions userChatOptions = new UserChatOptions(chatName + "@" + state.domain, chatPassword, "blue",
		SubscriptionMode.auto_accept_all);
	state.userOptions = userChatOptions;
	DefaultDispatcher.getInstance().fire(
		EmiteUIPlugin.CREATE_CHAT_DIALOG,
		new MultiChatCreationParam(Kune.I18N.t("Chat"), new BoshOptions(state.httpBase), state.roomHost,
			Kune.I18N, state.userOptions));
	Log.debug("LOGIN CHAT: " + chatName + "[" + chatPassword + "]");
	DefaultDispatcher.getInstance().fire(EmiteUIPlugin.SET_OWN_PRESENCE, new OwnPresence(OwnStatus.online));
    }

    public void logout() {
	DefaultDispatcher.getInstance().fire(EmiteUIPlugin.SET_OWN_PRESENCE, new OwnPresence(OwnStatus.offline));
	DefaultDispatcher.getInstance().fire(EmiteUIPlugin.CLOSE_ALLCHATS, new Boolean(false));
    }

    public void joinRoom(final String roomName, final String userAlias) {
	// FIXME muc nick support
	DefaultDispatcher.getInstance().fire(
		EmiteUIPlugin.ROOMOPEN,
		XmppURI.uri(roomName + "@" + state.roomHost + "/" + XmppURI.jid(state.userOptions.getUserJid()))
			.getNode());
    }
}
