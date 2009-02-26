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
package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.services.I18nUITranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPresenter;

import com.calclab.suco.client.ioc.Provider;

public class DocumentViewerPresenter extends FoldableContentPresenter implements DocumentViewer {

    public DocumentViewerPresenter(StateManager stateManager, Session session, I18nUITranslationService i18n,
            ActionContentToolbar toolbar, ContentActionRegistry actionRegistry,
            Provider<FileDownloadUtils> downloadProvider) {
        super(DocumentClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n);
    }

    public void init(DocumentViewerView view) {
        super.init(view);
    }

    @Override
    protected void setState(StateContentDTO state) {
        super.setState(state);
        setContent(state, DocumentClientTool.TYPE_UPLOADEDFILE);
    }
}
