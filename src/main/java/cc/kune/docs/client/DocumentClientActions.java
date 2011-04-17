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
 \*/
package cc.kune.docs.client;

import static cc.kune.docs.client.DocumentClientTool.TYPE_DOCUMENT;
import static cc.kune.docs.client.DocumentClientTool.TYPE_FOLDER;
import static cc.kune.docs.client.DocumentClientTool.TYPE_ROOT;
import static cc.kune.docs.client.DocumentClientTool.TYPE_UPLOADEDFILE;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;

public class DocumentClientActions extends AbstractFoldableContentActions {
    public DocumentClientActions(final I18nUITranslationService i18n, final Session session,
            final StateManager stateManager) {
        super(session, stateManager, i18n);
    }

    @Override
    protected void createActions() {
        final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
        final String[] containers = { TYPE_ROOT, TYPE_FOLDER, };
        final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
        final String[] containersNoRoot = { TYPE_FOLDER };
        final String[] contents = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };

        // final String parentMenuTitle = i18n.t("File");
        // final String parentMenuTitleCtx = i18n.t("Folder");
        //
        // createNewContainerAction(TYPE_FOLDER, "images/nav/folder_add.png",
        // i18n.t("New folder"), parentMenuTitleCtx,
        // i18n.t("New"), i18n.t("New folder"), Position.ctx, TYPE_ROOT,
        // TYPE_FOLDER);
        //
        // createNewContentAction(TYPE_DOCUMENT, "images/nav/page_add.png",
        // i18n.t("New document"), parentMenuTitleCtx,
        // Position.ctx, TYPE_ROOT, TYPE_FOLDER);
        //
        // createTranslateAction(parentMenuTitle, TYPE_DOCUMENT, TYPE_FOLDER);
        // createContentRenameAction(parentMenuTitle, i18n.t("Rename"),
        // contents);
        // createEditAction(parentMenuTitle, TYPE_DOCUMENT);
        // createContentModeratedActions(parentMenuTitle, contentsModerated);
        // createRenameContentInCtxAction(parentMenuTitleCtx, i18n.t("Rename"),
        // containersNoRoot);
        //
        // // final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt =
        // // createSetAsDefContent(parentMenuTitleCtx);
        //
        // createRefreshCntAction(parentMenuTitle, contents);
        // createRefreshCxtAction(parentMenuTitleCtx, containers);
        //
        // createSetAsDefContent(parentMenuTitle, contents);
        //
        // createSetGroupBackImageAction(parentMenuTitle, TYPE_UPLOADEDFILE);
        //
        // createUploadAction(i18n.t("Upload file"), "images/nav/upload.png",
        // i18n.t("Upload files (images, PDFs...)"),
        // null, containers);
        //
        // createDownloadActions(TYPE_UPLOADEDFILE);
        //
        // createGoAction(all);
        //
        // createGoHomeAction(containers);
        //
        // createDelContainerAction("Delete folder", parentMenuTitleCtx,
        // containersNoRoot);
        // createDelContentAction(parentMenuTitle, i18n.t("Delete"), contents);
        // createShowDeletedItems(parentMenuTitleCtx, all);
    }

    @Override
    protected void createPostSessionInitActions() {
        // super.createUploadMediaAction(TYPE_GALLERY);
    }
}
