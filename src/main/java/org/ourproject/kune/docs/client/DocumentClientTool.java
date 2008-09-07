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
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.calclab.suco.client.provider.Provider;

public class DocumentClientTool extends AbstractClientTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String NAME = "docs";
    private final Provider<DocumentContext> documentContextProvider;

    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
	    final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws,
	    final Provider<DocumentContext> documentContextProvider) {
	super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws);
	this.documentContextProvider = documentContextProvider;
    }

    public String getName() {
	return NAME;
    }

    public void onCancel() {
	documentContextProvider.get().showFolders();
    }

    public void onEdit() {
	documentContextProvider.get().showAdmin();
    }

}
