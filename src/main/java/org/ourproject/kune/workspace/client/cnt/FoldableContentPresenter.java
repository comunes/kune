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
package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;

import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public abstract class FoldableContentPresenter extends AbstractContentPresenter implements FoldableContent {

    private final String toolName;
    private final ActionRegistry<StateToken> actionRegistry;
    protected final Session session;
    private final ActionContentToolbar toolbar;
    private final Provider<FileDownloadUtils> downloadProvider;
    private final I18nTranslationService i18n;
    private final Provider<MediaUtils> mediaUtils;
    private String uploadType;
    private String waveType;

    public FoldableContentPresenter(final String toolName, final StateManager stateManager, final Session session,
            final ActionContentToolbar toolbar, final ActionRegistry<StateToken> actionRegistry,
            final Provider<FileDownloadUtils> downloadProvider, final I18nTranslationService i18n,
            final Provider<MediaUtils> mediaUtils) {
        this.toolName = toolName;
        this.session = session;
        this.toolbar = toolbar;
        this.actionRegistry = actionRegistry;
        this.downloadProvider = downloadProvider;
        this.i18n = i18n;
        this.mediaUtils = mediaUtils;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            @Override
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
    }

    public String getToolName() {
        return toolName;
    }

    public String getUploadType() {
        return uploadType;
    }

    public String getWaveType() {
        return waveType;
    }

    @Override
    public void refreshState() {
        setState(session.getContentState());
    }

    public void setUploadType(final String uploadType) {
        this.uploadType = uploadType;
    }

    public void setWaveType(final String waveType) {
        this.waveType = waveType;
    }

    protected void setContent(final StateContentDTO state) {
        final String typeId = state.getTypeId();
        if (typeId.equals(getUploadType())) {
            setUploadedContent(state);
        } else if (typeId.equals(getWaveType())) {
            // setWaveContent(state);
        } else {
            setNormalContent(state);
        }
        view.attach();
    }

    protected void setState(final StateAbstractDTO state) {
        toolbar.detach();
        if (state instanceof StateContainerDTO) {
            final StateContainerDTO stateCntCtx = (StateContainerDTO) state;
            if (stateCntCtx.getToolName().equals(toolName)) {
                // This tool
                if (stateCntCtx instanceof StateContentDTO) {
                    setState((StateContentDTO) stateCntCtx);
                } else {
                    setState(stateCntCtx);
                }
            }
        }
    }

    protected void setState(final StateContainerDTO state) {
        final ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContainerRights());
        setToolbar(collection);
        attach();
    }

    protected void setState(final StateContentDTO state) {
        final ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContentRights());
        setToolbar(collection);
    }

    private ActionItemCollection<StateToken> getActionCollection(final StateContainerDTO state,
            final AccessRights rights) {
        return actionRegistry.getCurrentActions(state.getStateToken(), state.getTypeId(), session.isLogged(), rights,
                true);
    }

    private String getContentBody(final StateContentDTO state) {
        final String contentBody = state.getContent();
        return contentBody;
    }

    private void setNormalContent(final StateContentDTO state) {
        final String contentBody = getContentBody(state);
        if (contentBody == null || contentBody.length() == 0) {
            if (state.getContentRights().isEditable()) {
                view.setInfoMessage(i18n.t("There is no text in this page. Feel free to edit this page"));
            } else {
                view.setInfoMessage(i18n.t("There is no text in this page"));
            }
        } else {
            view.setRawContent(contentBody);
        }
    }

    private void setToolbar(final ActionItemCollection<StateToken> collection) {
        toolbar.disableMenusAndClearButtons();
        toolbar.addActions(collection, AbstractFoldableContentActions.CONTENT_TOPBAR);
        toolbar.attach();
    }

    private void setUploadedContent(final StateContentDTO state) {
        final String contentBody = state.getContent();
        final StateToken token = state.getStateToken();
        final BasicMimeTypeDTO mimeType = state.getMimeType();
        if (mimeType != null) {
            final FileDownloadUtils fileDownloadUtils = downloadProvider.get();
            if (mimeType.isImage()) {
                view.showImage(fileDownloadUtils.getImageUrl(token),
                        fileDownloadUtils.getImageResizedUrl(token, ImageSize.sized), false);
            } else if (mimeType.isPdf()) {
                view.showImage(fileDownloadUtils.getImageUrl(token),
                        fileDownloadUtils.getImageResizedUrl(token, ImageSize.sized), true);
            } else if (mimeType.isMp3()) {
                view.setRawContent(mediaUtils.get().getMp3Embed(token));
            } else if (mimeType.isOgg()) {
                view.setRawContent(mediaUtils.get().getOggEmbed(token));
            } else if (mimeType.isFlv()) {
                view.setRawContent(mediaUtils.get().getFlvEmbed(token));
            } else if (mimeType.isAvi()) {
                view.setRawContent(mediaUtils.get().getAviEmbed(token));
            } else if (mimeType.isText()) {
                view.setContent(contentBody, true);
            } else {
                view.setNoPreview();
            }
        } else {
            view.setNoPreview();
        }
    }
}
