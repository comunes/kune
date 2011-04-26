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
package cc.kune.docs.client.actions;

import static cc.kune.docs.client.DocsClientTool.TYPE_DOCUMENT;
import static cc.kune.docs.client.DocsClientTool.TYPE_FOLDER;
import static cc.kune.docs.client.DocsClientTool.TYPE_ROOT;
import static cc.kune.docs.client.DocsClientTool.TYPE_UPLOADEDFILE;
import static cc.kune.docs.client.DocsClientTool.TYPE_WAVE;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.perspective.ActionGroups;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsClientActions extends AbstractFoldableContentActions {

    final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
    final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
    final String[] containersNoRoot = { TYPE_FOLDER };
    final String[] contents = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE, TYPE_WAVE };
    final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };

    @Inject
    public DocsClientActions(final I18nUITranslationService i18n, final Session session,
            final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
            final Provider<FolderGoUpBtn> folderGoUp, final Provider<NewDocBtn> newDocBtn,
            final Provider<NewFolderBtn> newFolderBtn, final Provider<OpenContentMenuItem> openContentMenuItem,
            final Provider<DelContentMenuItem> delContentMenuItem, final Provider<DelFolderMenuItem> delFolderMenuItem) {
        super(session, stateManager, i18n, registry);
        actionsRegistry.addAction(ActionGroups.VIEW, folderGoUp, contents);
        actionsRegistry.addAction(ActionGroups.VIEW, folderGoUp, containersNoRoot);
        actionsRegistry.addAction(ActionGroups.VIEW, newDocBtn, containers);
        actionsRegistry.addAction(ActionGroups.VIEW, newFolderBtn, containers);
        actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, contents);
        actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, containersNoRoot);
        actionsRegistry.addAction(ActionGroups.MENUITEM, delContentMenuItem, contents);
        actionsRegistry.addAction(ActionGroups.MENUITEM, delFolderMenuItem, containersNoRoot);
    }

    @Override
    protected void createActions() {
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
