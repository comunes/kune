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

import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.tool.FoldableAbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public class DocumentClientTool extends FoldableAbstractClientTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String TYPE_GALLERY = "docs.gallery";
    public static final String TYPE_WIKI = "docs.wiki";
    public static final String TYPE_WIKIPAGE = "docs.wikipage";
    public static final String TYPE_UPLOADEDFILE = "docs.uploaded";
    public static final String NAME = "docs";

    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
            final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws,
            ContentCapabilitiesRegistry contentCapabilitiesRegistry) {
        super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws, contentCapabilitiesRegistry);

        // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerAuthorableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerDragableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE);
        registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_GALLERY);
        registerPublishModerableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerRateableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
        registerRenamableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_GALLERY, TYPE_UPLOADEDFILE, TYPE_WIKI, TYPE_WIKIPAGE);
        registerTageableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
        registerTranslatableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);

        registerIcons();
    }

    public String getName() {
        return NAME;
    }

    protected void registerIcons() {
        registerContentTypeIcon(TYPE_FOLDER, "images/nav/folder.png");
        registerContentTypeIcon(TYPE_GALLERY, "images/nav/gallery.png");
        registerContentTypeIcon(TYPE_DOCUMENT, "images/nav/page.png");
        registerContentTypeIcon(TYPE_WIKI, "images/nav/wiki.png");
        registerContentTypeIcon(TYPE_WIKIPAGE, "images/nav/wikipage.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("image"), "images/nav/picture.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("video"), "images/nav/film.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "pdf"),
                "images/nav/page_pdf.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "zip"),
                "images/nav/page_zip.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "zip"),
                "images/nav/page_zip.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("text"), "images/nav/page_text.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "msword"),
                "images/nav/page_word.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "excel"),
                "images/nav/page_excel.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, new BasicMimeTypeDTO("application", "mspowerpoint"),
                "images/nav/page_pps.png");
        registerContentTypeIcon(TYPE_UPLOADEDFILE, "images/nav/page.png");
    }

}
