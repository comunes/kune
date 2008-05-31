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

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public class ChatClientTool extends AbstractClientTool implements ChatProvider {
    public static final String NAME = "chats";
    public static final String TYPE_ROOT = "chat.root";
    public static final String TYPE_ROOM = "chat.room";
    public static final String TYPE_CHAT = "chat.chat";

    private final ChatToolComponents components;
    private ChatEngine chat;
    private final Kune kune;

    public ChatClientTool(final Kune kune) {
        super(Kune.I18N.t("chat rooms"));
        this.kune = kune;
        components = new ChatToolComponents(kune.getEmiteUIDialog());
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

    public void initEngine(final ChatOptions options) {
        this.chat = new ChatEngineXmpp(kune.getEmiteUIDialog(), options);
    }

    public void setContent(final StateDTO state) {
        components.getContent().setState(state);
    }

    public void setContext(final StateDTO state) {
        components.getContext().setState(state);
    }

}
