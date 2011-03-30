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
 */
package cc.kune.docs.client;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.ToolSelector;

import com.google.inject.Inject;

public class DocumentClientTool extends FoldableAbstractClientTool {
    public static final String NAME = "docs";
    public static final String TYPE_DOCUMENT = NAME + "." + "doc";
    public static final String TYPE_FOLDER = NAME + "." + "folder";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + FoldableAbstractClientTool.UPLOADEDFILE_SUFFIX;
    public static final String TYPE_WAVE = NAME + "." + FoldableAbstractClientTool.WAVE_SUFFIX;

    @Inject
    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
            final ContentCapabilitiesRegistry cntCapRegistry) {
        super(NAME, i18n.t("documents"), toolSelector, cntCapRegistry);

        // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerAuthorableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerDragableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE);
        registerDropableTypes(TYPE_ROOT, TYPE_FOLDER);
        registerPublishModerableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerRateableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerRenamableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE);
        registerTageableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        registerTranslatableTypes(TYPE_DOCUMENT, TYPE_FOLDER);

        registerIcons();
    }

    @Override
    public String getName() {
        return NAME;
    }

    private void registerIcons() {
        registerContentTypeIcon(TYPE_FOLDER, "images/nav/folder.png");
        registerContentTypeIcon(TYPE_DOCUMENT, "images/nav/page.png");
        registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    }

}
