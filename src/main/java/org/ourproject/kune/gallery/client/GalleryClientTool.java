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
package org.ourproject.kune.gallery.client;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.tool.OldFoldableAbstractClientTool;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.gspace.client.themes.GSpaceThemeManager;
import cc.kune.gspace.client.tool.selector.ToolSelector;

public class GalleryClientTool extends OldFoldableAbstractClientTool {
    public static final String NAME = "gallery";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_ALBUM = NAME + "." + "album";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + OldFoldableAbstractClientTool.UPLOADEDFILE_SUFFIX;

    public GalleryClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
            final GSpaceThemeManager wsThemePresenter, final WorkspaceSkeleton ws,
            final ContentCapabilitiesRegistry cntCapabReg) {
        super(NAME, i18n.t("gallery"), toolSelector, wsThemePresenter, ws, cntCapabReg);

        // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerAuthorableTypes(TYPE_UPLOADEDFILE);
        registerDragableTypes(TYPE_ALBUM, TYPE_UPLOADEDFILE);
        registerDropableTypes(TYPE_ROOT, TYPE_ALBUM);
        registerPublishModerableTypes(TYPE_UPLOADEDFILE);
        registerRateableTypes(TYPE_UPLOADEDFILE);
        registerRenamableTypes(TYPE_ALBUM, TYPE_UPLOADEDFILE);
        registerTageableTypes(TYPE_UPLOADEDFILE);
        // registerTranslatableTypes();

        registerIcons();
    }

    public String getName() {
        return NAME;
    }

    private void registerIcons() {
        registerContentTypeIcon(TYPE_ALBUM, "images/nav/gallery.png");
        registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    }

}
