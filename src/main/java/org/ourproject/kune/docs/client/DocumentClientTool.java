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

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

public class DocumentClientTool extends AbstractClientTool implements DocumentContentListener {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String NAME = "docs";
    private final DocToolComponents components;

    public DocumentClientTool(final DocumentFactory documentFactory, final I18nUITranslationService i18n,
	    final ToolSelector toolSelector, final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws) {
	super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws);
	components = new DocToolComponents(documentFactory, this);
    }

    public WorkspaceComponent getContent() {
	return components.getContent();
    }

    public WorkspaceComponent getContext() {
	return components.getContext();
    }

    public String getName() {
	return NAME;
    }

    public void onCancel() {
	components.getContext().showFolders();
    }

    public void onEdit() {
	components.getContext().showAdmin();
    }

    public void setContent(final StateDTO state) {
	final DocumentContent docContent = components.getContent();
	docContent.setContent(state);

	// TODO: check trigger interface (setState)
	// trigger.setState(state.getStateToken().toString());
    }

    public void setContext(final StateDTO state) {
	final DocumentContext context = components.getContext();
	context.setContext(state);
    }

}
