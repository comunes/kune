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
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummary;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;

public class ChatClientTool extends AbstractClientTool implements ChatProvider {
    public static final String NAME = "chats";
    public static final String TYPE_ROOT = "chat.root";
    public static final String TYPE_ROOM = "chat.room";
    public static final String TYPE_CHAT = "chat.chat";
    private ChatEngine chat;
    private final Provider<ChatContext> chatContextProvider;
    private final Provider<ChatContent> chatContentProvider;

    public ChatClientTool(final Session session, final Application application, final I18nTranslationService i18n,
	    final EmiteUIDialog emiteUIDialog, final WorkspaceSkeleton ws,
	    final Provider<GroupMembersSummary> groupMembersSummaryProvider, final ToolSelector toolSelector,
	    final WsThemePresenter wsThemePresenter, final Provider<ChatContent> chatContentProvider,
	    final Provider<ChatContext> chatContextProvider) {
	super(NAME, i18n.t("chat rooms"), toolSelector, wsThemePresenter, ws);
	this.chatContentProvider = chatContentProvider;
	this.chatContextProvider = chatContextProvider;
	session.onInitDataReceived(new Slot<InitDataDTO>() {
	    public void onEvent(final InitDataDTO initData) {
		checkChatDomain(initData.getChatDomain());
		final ChatOptions chatOptions = new ChatOptions(initData.getChatHttpBase(), initData.getChatDomain(),
			initData.getChatRoomHost());
		chat = new ChatEngineXmpp(emiteUIDialog, chatOptions, i18n, ws);
		groupMembersSummaryProvider.get().addUserOperation(
			new MenuItem<GroupDTO>("images/new-chat.gif", i18n.t("Start a chat with this member"),
				new Slot<GroupDTO>() {
				    public void onEvent(final GroupDTO group) {
					emiteUIDialog.show();
					if (emiteUIDialog.isLoggedIn()) {
					    emiteUIDialog.chat(XmppURI.jid(group.getShortName() + "@"
						    + initData.getChatDomain()));
					} else {
					    ws.showAlertMessage(i18n.t("Error"), i18n
						    .t("To start a chat you need to be 'online'."));
					}
				    }
				}), true);
	    }

	    private void checkChatDomain(final String chatDomain) {
		final String httpDomain = WindowUtils.getLocation().getHostName();
		if (!chatDomain.equals(httpDomain)) {
		    Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
			    + "). This will produce problems with the chat functionality. "
			    + "Check kune.properties on the server.");
		}
	    }
	});
	application.onApplicationStop(new Slot0() {
	    public void onEvent() {
		chat.logout();
	    }
	});
	session.onUserSignOut(new Slot0() {
	    public void onEvent() {
		chat.logout();
	    }
	});
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO user) {
		chat.login(user.getChatName(), user.getChatPassword());
	    }
	});
    }

    public ChatEngine getChat() {
	return chat;
    }

    public String getName() {
	return NAME;
    }

    public void setContent(final StateDTO state) {
	chatContentProvider.get().setState(state);
    }

    public void setContext(final StateDTO state) {
	chatContextProvider.get().setState(state);
    }

}
