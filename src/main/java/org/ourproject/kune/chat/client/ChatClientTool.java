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
 */
package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.tool.FoldableAbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public class ChatClientTool extends FoldableAbstractClientTool {
    public static final String NAME = "chats";
    public static final String TYPE_ROOT = "chat.root";
    public static final String TYPE_ROOM = "chat.room";
    public static final String TYPE_CHAT = "chat.chat";

    public ChatClientTool(final I18nTranslationService i18n, final WorkspaceSkeleton ws,
            final ToolSelector toolSelector, final WsThemePresenter wsThemePresenter,
            ContentCapabilitiesRegistry contentCapabilitiesRegistry) {
        super(NAME, i18n.t("chat rooms"), toolSelector, wsThemePresenter, ws, contentCapabilitiesRegistry);
        registerContentTypeIcon(TYPE_ROOM, "images/emite-room.png");
    }

    public String getName() {
        return NAME;
    }
}
