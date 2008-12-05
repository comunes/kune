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
package org.ourproject.kune.gallery.client.cnt;

import org.ourproject.kune.gallery.client.GalleryClientTool;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPresenter;

import com.calclab.suco.client.ioc.Provider;

public class GalleryFolderContentPresenter extends FoldableContentPresenter implements GalleryFolderContent {

    private GalleryFolderContentView view;
    private final I18nTranslationService i18n;
    private final Provider<FileDownloadUtils> downloadUtils;

    public GalleryFolderContentPresenter(StateManager stateManager, Session session, ActionContentToolbar toolbar,
            final ActionRegistry<StateToken> actionRegistry, I18nTranslationService i18n,
            Provider<FileDownloadUtils> downloadUtils) {
        super(GalleryClientTool.NAME, stateManager, session, toolbar, actionRegistry);
        this.i18n = i18n;
        this.downloadUtils = downloadUtils;
    }

    public void init(final GalleryFolderContentView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    protected void setState(StateContainerDTO state) {
        if (state.getTypeId().equals(GalleryClientTool.TYPE_ROOT)) {
            ContainerDTO rootContainer = state.getRootContainer();
            if (rootContainer.getChilds().size() == 0 && rootContainer.getContents().size() == 0) {
                view.setInfo(i18n.t("This gallery has no content"));
            } else {
                view.setInfo("");
            }
        } else if (state.isType(GalleryClientTool.TYPE_ALBUM) && (state.getContainer().getContents().size() > 0)) {
            view.setThumbPanel();
            for (ContentSimpleDTO content : state.getContainer().getContents()) {
                if (content.getMimeType().isImage()) {
                    StateToken token = content.getStateToken();
                    String imgUrl = downloadUtils.get().getImageResizedUrl(token, ImageSize.thumb);
                    view.addThumb(token, content.getTitle(), imgUrl);
                }
            }
        } else {
            view.setInfo("");
        }
        super.setState(state);
    }
}
