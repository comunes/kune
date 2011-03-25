/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.chat.client.cnt;

import org.ourproject.kune.chat.client.ChatClientTool;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.cnt.ActionContentToolbar;
import cc.kune.core.client.cnt.FoldableContentPresenter;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.MediaUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.inject.Provider;

public class ChatRoomPresenter extends FoldableContentPresenter implements ChatRoom {

    private final I18nUITranslationService i18n;
    private ChatRoomView view;

    public ChatRoomPresenter(final StateManager stateManager, final Session session,
            final I18nUITranslationService i18n, final ActionContentToolbar toolbar,
            final GuiActionDescCollection actionRegistry, final Provider<FileDownloadUtils> downloadProvider,
            final Provider<MediaUtils> mediaUtils) {
        super(ChatClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n, mediaUtils);
        this.i18n = i18n;
    }

    public void init(final ChatRoomView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    protected void setState(final StateContainerDTO state) {
        if (state.getTypeId().equals(ChatClientTool.TYPE_ROOT)) {
            if (state.getRootContainer().getChilds().size() == 0) {
                view.setInfo(i18n.t("This group has no chat rooms."));
            } else {
                view.setInfo(i18n.t("Select a chat room"));
            }
        } else {
            view.setInfo("History of room conversations..." + TextUtils.IN_DEVELOPMENT_P);
        }
        super.setState(state);
    }
}
