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
 */package org.ourproject.kune.blogs.client;

import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_BLOG;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_POST;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_ROOT;
import static org.ourproject.kune.blogs.client.BlogClientTool.TYPE_UPLOADEDFILE;

import org.ourproject.kune.blogs.client.cnt.BlogViewer;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;

public class BlogClientActions extends AbstractFoldableContentActions {
    public BlogClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
            final Session session, final StateManager stateManager,
            final DeferredCommandWrapper deferredCommandWrapper,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider, final Provider<FileUploader> fileUploaderProvider,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final EntityLogo entityLogo,
            final Provider<TextEditor> textEditorProvider, final KuneErrorHandler errorHandler,
            final BlogViewer documentViewer, final Provider<ContextPropEditor> contextProvEditorProvider) {
        super(session, stateManager, i18n, errorHandler, deferredCommandWrapper, groupServiceProvider,
                contentServiceProvider, fileUploaderProvider, contextNavigator, contentActionRegistry,
                contextActionRegistry, fileDownloadProvider, textEditorProvider, contextProvEditorProvider,
                documentViewer, entityLogo);
    }

    @Override
    protected void createActions() {
        String parentMenuTitle = i18n.t("Post");
        String parentMenuTitleCtx = i18n.t("Blog");

        final ActionToolbarMenuAndItemDescriptor<StateToken> addBlog = createContainerAction(TYPE_BLOG,
                "images/nav/blog_add.png", i18n.t("New blog"), parentMenuTitleCtx, i18n.t("New"), i18n.t("New blog"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addPost = createContentAction("images/nav/post_add.png",
                i18n.t("New post"), parentMenuTitleCtx, TYPE_POST);

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContainer = createDelContainerAction("Delete blog",
                parentMenuTitleCtx);

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContent = createDelContentAction(parentMenuTitle,
                i18n.t("Delete"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = createContentRenameAction(parentMenuTitle,
                i18n.t("Rename"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = createRenameContentInCtxAction(
                parentMenuTitleCtx, i18n.t("Rename"));

        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCnt = createSetAsDefContent(parentMenuTitle);

        // final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt =
        // createSetAsDefContent(parentMenuTitleCtx);

        final ActionToolbarMenuDescriptor<StateToken> refreshCnt = createRefreshCntAction(parentMenuTitle);

        final ActionToolbarMenuDescriptor<StateToken> refreshCtx = createRefreshCxtAction(parentMenuTitleCtx);

        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadFile = createUploadAction(i18n.t("Upload file"),
                "images/nav/upload.png", i18n.t("Upload some files (images, PDFs, ...)"), null);

        final ActionToolbarMenuDescriptor<StateToken> setPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Published online"), parentMenuTitle,
                ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setEditionInProgressStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Editing in progress"), parentMenuTitle,
                ContentStatusDTO.editingInProgress);
        final ActionToolbarMenuDescriptor<StateToken> setRejectStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Rejected"), parentMenuTitle, ContentStatusDTO.rejected);
        final ActionToolbarMenuDescriptor<StateToken> setSubmittedForPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Submitted for publish"), parentMenuTitle,
                ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setInTheDustBinStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("In the dustbin"), parentMenuTitle, ContentStatusDTO.inTheDustbin);

        final String[] all = { TYPE_ROOT, TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE };
        final String[] containersNoRoot = { TYPE_BLOG };
        final String[] containers = { TYPE_ROOT, TYPE_BLOG };
        final String[] contents = { TYPE_POST, TYPE_UPLOADEDFILE };
        final String[] contentsModerated = { TYPE_POST, TYPE_UPLOADEDFILE };

        contentActionRegistry.addAction(setPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setEditionInProgressStatus, contentsModerated);
        contentActionRegistry.addAction(setRejectStatus, contentsModerated);
        contentActionRegistry.addAction(setSubmittedForPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setInTheDustBinStatus, contentsModerated);
        contextActionRegistry.addAction(addBlog, TYPE_ROOT);
        contextActionRegistry.addAction(addPost, TYPE_BLOG);
        contextActionRegistry.addAction(go, all);
        contentActionRegistry.addAction(renameCtn, contents);
        contextActionRegistry.addAction(renameCtx, containersNoRoot);
        contextActionRegistry.addAction(refreshCtx, containers);
        contentActionRegistry.addAction(refreshCnt, contents);
        contextActionRegistry.addAction(uploadFile, TYPE_BLOG);
        contentActionRegistry.addAction(download, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(delContent, contents);
        contextActionRegistry.addAction(delContainer, containersNoRoot);
        contentActionRegistry.addAction(setAsDefGroupCnt, TYPE_POST, TYPE_UPLOADEDFILE);
        // ContentRPC Authorized must permit folders
        // contentActionRegistry.addAction(setAsDefGroupCxt, TYPE_BLOG);
        contextActionRegistry.addAction(goGroupHome, containers);
        contextActionRegistry.addAction(downloadCtx, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(editContent, TYPE_POST);
        // contentActionRegistry.addAction(translateContent, );
    }

    @Override
    protected void createPostSessionInitActions() {
        // contextActionRegistry.addAction(uploadMedia, TYPE_GALLERY);
    }
}
