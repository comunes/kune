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

package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

import com.calclab.suco.client.provider.Provider;

public class DocumentContextPresenter implements DocumentContext {
    private final WorkspaceDeckView view;
    private final Provider<FolderContext> folderContextProvider;
    private final Provider<AdminContext> adminContextProvider;

    public DocumentContextPresenter(final WorkspaceDeckView view, final Provider<FolderContext> folderContexProvider,
	    final Provider<AdminContext> adminContextProvider) {
	this.view = view;
	this.folderContextProvider = folderContexProvider;
	this.adminContextProvider = adminContextProvider;
    }

    public View getView() {
	return view;
    }

    public void setContext(final StateDTO content) {
	final StateToken state = content.getStateToken();
	folderContextProvider.get().setContainer(state, content.getFolder(), content.getFolderRights());
	adminContextProvider.get().setState(content);
	view.show(folderContextProvider.get().getView());
    }

    public void showAdmin() {
	final AdminContext adminContext = adminContextProvider.get();
	view.show(adminContext.getView());
    }

    public void showFolders() {
	final FolderContext folderContext = folderContextProvider.get();
	view.show(folderContext.getView());
    }
}
