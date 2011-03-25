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
package org.ourproject.kune.wiki.client.cnt;

import org.ourproject.kune.wiki.client.WikiClientTool;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.cnt.ActionContentToolbar;
import cc.kune.core.client.cnt.FoldableContentPresenter;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.MediaUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Provider;

public class WikiFolderContentPresenter extends FoldableContentPresenter implements WikiFolderContent {

    private final I18nTranslationService i18n;
    private WikiFolderContentView view;

    public WikiFolderContentPresenter(final StateManager stateManager, final Session session,
            final ActionContentToolbar toolbar, final GuiActionDescCollection actionRegistry,
            final I18nTranslationService i18n, final Provider<FileDownloadUtils> downloadProvider,
            final Provider<MediaUtils> mediaUtils) {
        super(WikiClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n, mediaUtils);
        this.i18n = i18n;
    }

    public void init(final WikiFolderContentView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    protected void setState(final StateContainerDTO state) {
        if (state.getTypeId().equals(WikiClientTool.TYPE_ROOT)) {
            final ContainerDTO rootContainer = state.getRootContainer();
            if (rootContainer.getChilds().size() == 0 && rootContainer.getContents().size() == 0) {
                view.setInfo(i18n.t("This wiki has no pages."));
            } else {
                view.setInfo("");
            }
        } else {
            view.setInfo("");
        }
        super.setState(state);
    }
}
