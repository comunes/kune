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

package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class GoParentFolder extends WorkspaceAction {
    public static final String KEY = "platf.GoParentFolder";
    public static final String NAME = "platf.GoParentFolder";

    public void execute(final Object value, final Object extra) {
	goParent(getState());
    }

    private void goParent(final StateDTO state) {
	StateToken token = state.getState();
	token.setDocument(null);
	token.setFolder(state.getFolder().getParentFolderId().toString());
	stateManager.setState(token);
    }

    public String getActionName() {
	return KEY;
    }

    public String getEventName() {
	return NAME;
    }

}
