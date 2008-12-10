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
package org.ourproject.kune.blogs.client.cnt;

import org.ourproject.kune.blogs.client.BlogClientTool;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;

public class BlogViewerPresenter extends FoldableContentPresenter implements BlogViewer {

    public BlogViewerPresenter(StateManager stateManager, Session session, I18nUITranslationService i18n,
            ActionContentToolbar toolbar, ContentActionRegistry actionRegistry,
            Provider<FileDownloadUtils> downloadProvider) {
        super(BlogClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n);
    }

    public void init(BlogViewerView view) {
        super.init(view);
    }

    @Override
    protected void setState(StateContentDTO state) {
        super.setState(state);
        super.setContent(state, BlogClientTool.TYPE_UPLOADEDFILE);
    }

}
