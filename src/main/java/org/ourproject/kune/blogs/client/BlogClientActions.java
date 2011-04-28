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
 \*/
package org.ourproject.kune.blogs.client;

import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_BLOG;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_POST;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_ROOT;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_UPLOADEDFILE;

import org.ourproject.kune.blogs.client.cnt.BlogViewer;
import org.ourproject.kune.workspace.client.OldAbstractFoldableContentActions;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.upload.FileUploader;

import cc.kune.common.client.utils.SchedulerManager;
import cc.kune.core.client.cnt.ContentActionRegistry;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.style.GSpaceBackManager;

import com.calclab.suco.client.ioc.Provider;

public class BlogClientActions extends OldAbstractFoldableContentActions {
    public BlogClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
            final Session session, final StateManager stateManager, final SchedulerManager deferredCommandWrapper,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider, final Provider<FileUploader> fileUploaderProvider,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final EntityHeader entityLogo,
            final Provider<ContentEditor> textEditorProvider, final ErrorHandler errorHandler,
            final BlogViewer documentViewer, final Provider<ContextPropEditor> contextProvEditorProvider,
            final GSpaceBackManager wsBackManager) {
        super(session, stateManager, i18n, errorHandler, deferredCommandWrapper, groupServiceProvider,
                contentServiceProvider, fileUploaderProvider, contextNavigator, contentActionRegistry,
                contextActionRegistry, fileDownloadProvider, textEditorProvider, contextProvEditorProvider,
                documentViewer, entityLogo, wsBackManager);
    }

    @Override
    protected void createActions() {
        final String[] all = { TYPE_ROOT, TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE };
        final String[] contentsModerated = { TYPE_POST, TYPE_UPLOADEDFILE };
        final String[] containers = { TYPE_ROOT, TYPE_BLOG };
        final String[] containersNoRoot = { TYPE_BLOG };
        final String[] contents = { TYPE_POST, TYPE_UPLOADEDFILE };

        final String parentMenuTitle = i18n.t("Post");
        final String parentMenuTitleCtx = i18n.t("Blog");

        createNewContainerAction(TYPE_BLOG, "images/nav/blog_add.png", i18n.t("New blog"), parentMenuTitleCtx,
                i18n.t("New"), i18n.t("New blog"), Position.ctx, TYPE_ROOT);
        createNewContentAction(TYPE_POST, "images/nav/post_add.png", i18n.t("New post"), parentMenuTitleCtx,
                Position.ctx, TYPE_BLOG);

        createContentRenameAction(parentMenuTitle, i18n.t("Rename"), contents);
        createEditAction(parentMenuTitle, TYPE_POST);
        createContentModeratedActions(parentMenuTitle, contentsModerated);
        createRenameContentInCtxAction(parentMenuTitleCtx, i18n.t("Rename"), containersNoRoot);

        // final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt =
        // createSetAsDefContent(parentMenuTitleCtx);

        createRefreshCntAction(parentMenuTitle, contents);
        createRefreshCxtAction(parentMenuTitleCtx, containers);

        createUploadAction(i18n.t("Upload file"), "images/nav/upload.png", i18n.t("Upload files (images, PDFs...)"),
                null, containersNoRoot);

        createSetAsDefContent(parentMenuTitle, contents);
        createSetGroupBackImageAction(parentMenuTitle, TYPE_UPLOADEDFILE);

        createGoAction(all);
        createGoHomeAction(containers);

        // ContentRPC Authorized must permit folders
        // contentActionRegistry.addAction(setAsDefGroupCxt, TYPE_BLOG);

        createDelContainerAction("Delete blog", parentMenuTitleCtx, containersNoRoot);
        createDelContentAction(parentMenuTitle, i18n.t("Delete"), contents);
        createShowDeletedItems(parentMenuTitleCtx, all);
    }

    @Override
    protected void createPostSessionInitActions() {
    }
}
