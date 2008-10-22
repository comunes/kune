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

package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.DragDropContentRegistry;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.Provider;

public class DocumentClientTool extends AbstractClientTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String TYPE_GALLERY = "docs.gallery";
    public static final String TYPE_BLOG = "docs.blog";
    public static final String TYPE_POST = "docs.post";
    public static final String TYPE_WIKI = "docs.wiki";
    public static final String TYPE_WIKIPAGE = "docs.wikipage";
    public static final String TYPE_UPLOADEDFILE = "docs.uploaded";

    public static final String NAME = "docs";

    private final Provider<DocumentContext> documentContextProvider;
    private final DragDropContentRegistry dragDropContentRegistry;
    private final ContentIconsRegistry contentIconsRegistry;

    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
            final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws,
            final Provider<DocumentContext> documentContextProvider,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final ContentActionRegistry contentActionRegistry, final DragDropContentRegistry dragDropContentRegistry,
            final ContentIconsRegistry contentIconsRegistry) {
        super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws);
        this.documentContextProvider = documentContextProvider;
        this.dragDropContentRegistry = dragDropContentRegistry;
        this.contentIconsRegistry = contentIconsRegistry;
        registerDragDropTypes();
        registerIcons();
    }

    public String getName() {
        return NAME;
    }

    @Deprecated
    public void onCancel() {
        documentContextProvider.get().showFolders();
    }

    @Deprecated
    public void onEdit() {
        documentContextProvider.get().showAdmin();
    }

    private void registerDragDropTypes() {
        dragDropContentRegistry.registerDraggableType(TYPE_DOCUMENT);
        dragDropContentRegistry.registerDraggableType(TYPE_FOLDER);
        dragDropContentRegistry.registerDraggableType(TYPE_UPLOADEDFILE);

        dragDropContentRegistry.registerDroppableType(TYPE_ROOT);
        dragDropContentRegistry.registerDroppableType(TYPE_FOLDER);
        dragDropContentRegistry.registerDroppableType(TYPE_GALLERY);
    }

    private void registerIcons() {
        contentIconsRegistry.registerContentTypeIcon(TYPE_FOLDER, "images/nav/folder.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_BLOG, "images/nav/blog.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_GALLERY, "images/nav/gallery.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_DOCUMENT, "images/nav/page.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_POST, "images/nav/post.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_WIKI, "images/nav/wiki.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_WIKIPAGE, "images/nav/wikipage.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("image"),
                "images/nav/picture.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("video"),
                "images/nav/film.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "pdf"),
                "images/nav/page_pdf.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "zip"),
                "images/nav/page_zip.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "zip"),
                "images/nav/page_zip.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("text"),
                "images/nav/page_text.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "msword"),
                "images/nav/page_word.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "excel"),
                "images/nav/page_excel.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application",
                "mspowerpoint"), "images/nav/page_pps.png");
        contentIconsRegistry.registerContentTypeIcon(TYPE_UPLOADEDFILE, "images/nav/page.png");
    }
}
