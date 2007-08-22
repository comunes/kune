/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.docs.client.ui.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class DocumentContextPresenter implements DocumentContext {
    private final WorkspaceDeckView view;
    private final Components components;

    public DocumentContextPresenter(final WorkspaceDeckView view) {
	this.view = view;
	this.components = new Components(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setContent(final StateDTO content) {
	StateToken state = content.getState();
	FolderContext folderContext = components.getFolderContext();
	folderContext.setContainer(state, content.getFolder(), content.getFolderRights());
	view.show(folderContext.getView());
    }

    public void showAdmin() {
	AdminContext adminContext = components.getAdminContext();
	view.show(adminContext.getView());
    }

    public void showFolders() {
	FolderContext folderContext = components.getFolderContext();
	view.show(folderContext.getView());
    }
}
