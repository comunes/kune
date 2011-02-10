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
package org.ourproject.kune.blogs.client;

import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.tool.FoldableAbstractClientTool;
import org.ourproject.kune.workspace.client.tool.ToolSelector;

import cc.kune.core.client.i18n.I18nUITranslationService;

public class BlogClientTool extends FoldableAbstractClientTool {
    public static final String NAME = "blogs";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_BLOG = NAME + "." + "blog";
    public static final String TYPE_POST = NAME + "." + "post";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + FoldableAbstractClientTool.UPLOADEDFILE_SUFFIX;

    public BlogClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
            final WsThemeManager wsThemePresenter, final WorkspaceSkeleton ws,
            final ContentCapabilitiesRegistry cntCapReg) {
        super(NAME, i18n.t("blogs"), toolSelector, wsThemePresenter, ws, cntCapReg);

        // registerAclEditableTypes();
        registerAuthorableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
        registerDragableTypes(TYPE_UPLOADEDFILE);
        registerDropableTypes(TYPE_ROOT);
        registerPublishModerableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
        registerRateableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
        registerRenamableTypes(TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE);
        registerTageableTypes(TYPE_BLOG, TYPE_UPLOADEDFILE, TYPE_POST);
        // registerTranslatableTypes();

        registerIcons();
    }

    public String getName() {
        return NAME;
    }

    private void registerIcons() {
        registerContentTypeIcon(TYPE_BLOG, "images/nav/blog.png");
        registerContentTypeIcon(TYPE_POST, "images/nav/post.png");
        registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    }
}
