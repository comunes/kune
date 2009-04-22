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
package org.ourproject.kune.blogs.client.cnt;

import org.ourproject.kune.blogs.client.BlogClientTool;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbar;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPresenter;

import com.calclab.suco.client.ioc.Provider;

public class BlogFolderContentPresenter extends FoldableContentPresenter implements BlogFolderContent {

    private BlogFolderContentView view;
    private final I18nTranslationService i18n;

    public BlogFolderContentPresenter(final StateManager stateManager, final Session session,
            final ActionContentToolbar toolbar, final ActionRegistry<StateToken> actionRegistry,
            final I18nTranslationService i18n, final Provider<FileDownloadUtils> downloadProvider,
            final Provider<MediaUtils> mediaUtils) {
        super(BlogClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n, mediaUtils);
        this.i18n = i18n;
    }

    public void init(final BlogFolderContentView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    protected void setState(final StateContainerDTO state) {
        if (state.getTypeId().equals(BlogClientTool.TYPE_ROOT)) {
            if (state.getRootContainer().getChilds().size() == 0) {
                view.setInfo(i18n.t("This group has no blogs"));
            } else {
                view.setInfo("");
            }
        } else {
            view.setInfo("");
        }
        super.setState(state);
    }
}
