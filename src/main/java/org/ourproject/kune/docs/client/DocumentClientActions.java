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
 */package org.ourproject.kune.docs.client;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_BLOG;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_DOCUMENT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_FOLDER;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_GALLERY;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_POST;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_ROOT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_WIKI;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_WIKIPAGE;

import org.ourproject.kune.docs.client.cnt.DocumentViewer;
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

public class DocumentClientActions extends AbstractFoldableContentActions {
    public DocumentClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
            final Session session, final StateManager stateManager,
            final DeferredCommandWrapper deferredCommandWrapper,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider, final Provider<FileUploader> fileUploaderProvider,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final EntityLogo entityLogo,
            final Provider<TextEditor> textEditorProvider, final KuneErrorHandler errorHandler,
            final DocumentViewer documentViewer, final Provider<ContextPropEditor> contextProvEditorProvider) {
        super(session, stateManager, i18n, errorHandler, deferredCommandWrapper, groupServiceProvider,
                contentServiceProvider, fileUploaderProvider, contextNavigator, contentActionRegistry,
                contextActionRegistry, fileDownloadProvider, textEditorProvider, contextProvEditorProvider,
                documentViewer, entityLogo);
    }

    @Override
    protected void createActions() {
        String parentMenuTitle = i18n.t("File");
        String parentMenuTitleCtx = i18n.t("Folder");

        final ActionToolbarMenuAndItemDescriptor<StateToken> addFolder = createFolderAction(TYPE_FOLDER,
                "images/nav/folder_add.png", i18n.t("New folder"), parentMenuTitleCtx, i18n.t("New"),
                i18n.t("New folder"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addGallery = createFolderAction(TYPE_GALLERY,
                "images/nav/gallery_add.png", i18n.t("New gallery"), parentMenuTitleCtx, i18n.t("New"),
                i18n.t("New gallery"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addWiki = createFolderAction(TYPE_WIKI,
                "images/nav/wiki_add.png", i18n.t("New wiki"), parentMenuTitleCtx, i18n.t("New"), i18n.t("wiki"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addBlog = createFolderAction(TYPE_BLOG,
                "images/nav/blog_add.png", i18n.t("New blog"), parentMenuTitleCtx, i18n.t("New"), i18n.t("New blog"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> addDoc = createDocAction("images/nav/page_add.png",
                i18n.t("New document"), TYPE_DOCUMENT);
        final ActionToolbarMenuAndItemDescriptor<StateToken> addWikiPage = createDocAction(
                "images/nav/wikipage_add.png", i18n.t("New wikipage"), TYPE_WIKIPAGE);
        final ActionToolbarMenuAndItemDescriptor<StateToken> addPost = createDocAction("images/nav/post_add.png",
                i18n.t("New post"), TYPE_POST);

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContainer = createDelContainerAction("Delete folder",
                parentMenuTitleCtx);

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContent = createDelContentAction(parentMenuTitle,
                i18n.t("Delete"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = createContentRenameAction(parentMenuTitle,
                i18n.t("Rename"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = createRenameContentInCtxAction(
                parentMenuTitleCtx, i18n.t("Rename"));

        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCnt = createSetAsDefContent(parentMenuTitle);

        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt = createSetAsDefContent(parentMenuTitleCtx);

        final ActionToolbarMenuDescriptor<StateToken> refreshCnt = createRefreshCntAction(parentMenuTitle);

        final ActionToolbarMenuDescriptor<StateToken> refreshCtx = createRefreshCxtAction(parentMenuTitleCtx);

        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadFile = createUploadAction(i18n.t("Upload file"),
                "images/nav/upload.png", i18n.t("Upload some files (images, PDFs, ...)"), null);

        final ActionToolbarMenuDescriptor<StateToken> setPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Published online"), ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setEditionInProgressStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Editing in progress"), ContentStatusDTO.editingInProgress);
        final ActionToolbarMenuDescriptor<StateToken> setRejectStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Rejected"), ContentStatusDTO.rejected);
        final ActionToolbarMenuDescriptor<StateToken> setSubmittedForPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Submitted for publish"), ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setInTheDustBinStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("In the dustbin"), ContentStatusDTO.inTheDustbin);

        final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_GALLERY, TYPE_BLOG, TYPE_POST, TYPE_WIKI,
                TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
        final String[] containersNoRoot = { TYPE_FOLDER, TYPE_GALLERY, TYPE_BLOG, TYPE_WIKI };
        final String[] containers = { TYPE_ROOT, TYPE_FOLDER, TYPE_GALLERY, TYPE_BLOG, TYPE_WIKI };
        final String[] contents = { TYPE_DOCUMENT, TYPE_POST, TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
        final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_POST, TYPE_UPLOADEDFILE };

        contentActionRegistry.addAction(setPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setEditionInProgressStatus, contentsModerated);
        contentActionRegistry.addAction(setRejectStatus, contentsModerated);
        contentActionRegistry.addAction(setSubmittedForPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setInTheDustBinStatus, contentsModerated);
        contextActionRegistry.addAction(addDoc, TYPE_ROOT, TYPE_FOLDER);
        contextActionRegistry.addAction(addPost, TYPE_BLOG);
        contextActionRegistry.addAction(addWikiPage, TYPE_WIKI);
        contextActionRegistry.addAction(addFolder, TYPE_ROOT, TYPE_FOLDER);
        contextActionRegistry.addAction(addBlog, TYPE_ROOT);
        contextActionRegistry.addAction(addWiki, TYPE_ROOT);
        contextActionRegistry.addAction(addGallery, TYPE_ROOT);
        contextActionRegistry.addAction(go, all);
        contentActionRegistry.addAction(renameCtn, contents);
        contextActionRegistry.addAction(renameCtx, containersNoRoot);
        contextActionRegistry.addAction(refreshCtx, containers);
        contentActionRegistry.addAction(refreshCnt, contents);
        contextActionRegistry.addAction(uploadFile, TYPE_ROOT, TYPE_FOLDER, TYPE_BLOG);
        contentActionRegistry.addAction(download, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(delContent, contents);
        contextActionRegistry.addAction(delContainer, containersNoRoot);
        contentActionRegistry.addAction(setAsDefGroupCnt, TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(setAsDefGroupCxt, TYPE_BLOG);
        contextActionRegistry.addAction(goGroupHome, containers);
        contextActionRegistry.addAction(downloadCtx, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(editContent, TYPE_DOCUMENT, TYPE_POST, TYPE_WIKIPAGE);
        contentActionRegistry.addAction(translateContent, TYPE_DOCUMENT, TYPE_FOLDER, TYPE_GALLERY, TYPE_UPLOADEDFILE,
                TYPE_WIKI, TYPE_WIKIPAGE);
    }

    @Override
    protected void createPostSessionInitActions() {
        contextActionRegistry.addAction(uploadMedia, TYPE_GALLERY);
    }
}
