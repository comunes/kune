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
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.container.Provider;

class ChatToolComponents {
    private ChatContent content;
    private ChatContext context;
    private final EmiteUIDialog emiteUIDialog;
    private final I18nTranslationService i18n;
    private final Provider<StateManager> stateManagerProvider;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;

    public ChatToolComponents(final EmiteUIDialog emiteUIDialog, final I18nTranslationService i18n,
	    final Provider<StateManager> stateManagerProvider, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	this.emiteUIDialog = emiteUIDialog;
	this.i18n = i18n;
	this.stateManagerProvider = stateManagerProvider;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
    }

    public ChatContent getContent() {
	if (content == null) {
	    content = ChatFactory.createChatContent(emiteUIDialog, i18n, stateManagerProvider, session,
		    contentServiceProvider);
	}
	return content;
    }

    public ChatContext getContext() {
	if (context == null) {
	    context = ChatFactory.createChatContext();
	}
	return context;
    }

}
