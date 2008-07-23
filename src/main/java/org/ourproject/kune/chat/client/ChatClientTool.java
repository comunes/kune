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

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.signal.Slot;

public class ChatClientTool extends AbstractClientTool implements ChatProvider {
    public static final String NAME = "chats";
    public static final String TYPE_ROOT = "chat.root";
    public static final String TYPE_ROOM = "chat.room";
    public static final String TYPE_CHAT = "chat.chat";
    private final ChatToolComponents components;
    private ChatEngine chat;

    public ChatClientTool(final Session session, final Application application, final I18nTranslationService i18n,
	    final EmiteUIDialog emiteUIDialog, final WorkspaceSkeleton ws) {
	super(i18n.t("chat rooms"));
	components = new ChatToolComponents(emiteUIDialog, i18n);
	session.onInitDataReceived(new Slot<InitDataDTO>() {
	    public void onEvent(final InitDataDTO initData) {
		final ChatOptions chatOptions = new ChatOptions(initData.getChatHttpBase(), initData.getChatDomain(),
			initData.getChatRoomHost());
		chat = new ChatEngineXmpp(emiteUIDialog, chatOptions, i18n, ws);
	    }
	});
	application.onApplicationStop(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
		chat.logout();
	    }
	});
	session.onUserSignOut(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
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

    public WorkspaceComponent getContent() {
	return components.getContent();
    }

    public WorkspaceComponent getContext() {
	return components.getContext();
    }

    public String getName() {
	return NAME;
    }

    public void setContent(final StateDTO state) {
	components.getContent().setState(state);
    }

    public void setContext(final StateDTO state) {
	components.getContext().setState(state);
    }

}
