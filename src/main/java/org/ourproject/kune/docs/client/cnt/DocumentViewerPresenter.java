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

import cc.kune.core.client.cnt.ActionContentToolbar;
import cc.kune.core.client.cnt.ContentActionRegistry;
import cc.kune.core.client.cnt.FoldableContentPresenter;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.MediaUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Provider;

public class DocumentViewerPresenter extends FoldableContentPresenter implements DocumentViewer {

    public DocumentViewerPresenter(final StateManager stateManager, final Session session,
            final I18nUITranslationService i18n, final ActionContentToolbar toolbar,
            final ContentActionRegistry actionRegistry, final Provider<FileDownloadUtils> downloadProvider,
            final Provider<MediaUtils> mediaUtils) {
        super(DocumentClientTool.NAME, stateManager, session, toolbar, actionRegistry, downloadProvider, i18n,
                mediaUtils);
        setUploadType(DocumentClientTool.TYPE_UPLOADEDFILE);
        setWaveType(DocumentClientTool.TYPE_WAVE);
    }

    public void init(final DocumentViewerView view) {
        super.init(view);
    }

    @Override
    protected void setState(final StateContentDTO state) {
        super.setState(state);
        setContent(state);
    }
}
