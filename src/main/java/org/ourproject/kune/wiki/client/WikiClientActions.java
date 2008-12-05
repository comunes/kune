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
 */package org.ourproject.kune.wiki.client;

import static org.ourproject.kune.wiki.client.WikiClientTool.TYPE_FOLDER;
import static org.ourproject.kune.wiki.client.WikiClientTool.TYPE_ROOT;
import static org.ourproject.kune.wiki.client.WikiClientTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.wiki.client.WikiClientTool.TYPE_WIKIPAGE;

import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.wiki.client.cnt.WikiViewer;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;

public class WikiClientActions extends AbstractFoldableContentActions {
    public WikiClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
            final Session session, final StateManager stateManager,
            final DeferredCommandWrapper deferredCommandWrapper,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider, final Provider<FileUploader> fileUploaderProvider,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final EntityHeader entityLogo,
            final Provider<TextEditor> textEditorProvider, final KuneErrorHandler errorHandler,
            final WikiViewer documentViewer, final Provider<ContextPropEditor> contextProvEditorProvider) {
        super(session, stateManager, i18n, errorHandler, deferredCommandWrapper, groupServiceProvider,
                contentServiceProvider, fileUploaderProvider, contextNavigator, contentActionRegistry,
                contextActionRegistry, fileDownloadProvider, textEditorProvider, contextProvEditorProvider,
                documentViewer, entityLogo);
    }

    @Override
    protected void createActions() {
        final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
        final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
        // final String[] contentsModerated = { };
        final String[] containersNoRoot = { TYPE_FOLDER };
        final String[] contents = { TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };

        String parentMenuTitle = i18n.t("Wikipage");
        String parentMenuTitleCtx = i18n.t("Wiki");

        createNewContainerAction(TYPE_FOLDER, "images/nav/folder_add.png", i18n.t("New folder"), parentMenuTitleCtx,
                i18n.t("New"), i18n.t("New folder"), Position.ctx, TYPE_ROOT, TYPE_FOLDER);

        createNewContentAction(TYPE_WIKIPAGE, "images/nav/wikipage_add.png", i18n.t("New wikipage"),
                parentMenuTitleCtx, Position.ctx, TYPE_ROOT, TYPE_FOLDER);

        // createContentModeratedActions(parentMenuTitle, contentsModerated);

        createContentRenameAction(parentMenuTitle, i18n.t("Rename"), contents);
        createRenameContentInCtxAction(parentMenuTitleCtx, i18n.t("Rename"), containersNoRoot);

        // final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt =
        // createSetAsDefContent(parentMenuTitleCtx);

        createRefreshCntAction(parentMenuTitle, contents);
        createRefreshCxtAction(parentMenuTitleCtx, containers);

        createSetAsDefContent(parentMenuTitle, contents);

        createUploadAction(i18n.t("Upload file"), "images/nav/upload.png",
                i18n.t("Upload some files (images, PDFs, ...)"), null, containers);

        createDownloadActions(TYPE_UPLOADEDFILE);

        createGoAction(all);

        createGoHomeAction(containers);

        createEditAction(TYPE_WIKIPAGE);

        createTranslateAction(TYPE_FOLDER, TYPE_WIKIPAGE);

        createDelContainerAction("Delete folder", parentMenuTitleCtx, containersNoRoot);
        createDelContentAction(parentMenuTitle, i18n.t("Delete"), contents);
    }

    @Override
    protected void createPostSessionInitActions() {
        // super.createUploadMediaAction();
    }
}
