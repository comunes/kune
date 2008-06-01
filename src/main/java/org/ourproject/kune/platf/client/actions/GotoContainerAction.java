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
package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.emite.client.modular.Provider;

public class GotoContainerAction implements Action<Long> {

    private final Session session;
    private final Provider<StateManager> stateManager;

    public GotoContainerAction(final Provider<StateManager> stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final Long value) {
        onGoto(value);
    }

    private void onGoto(final Long folderId) {
        StateToken newStateToken = session.getCurrentState().getStateToken();
        newStateToken.setDocument(null);
        newStateToken.setFolder(folderId.toString());
        stateManager.get().setState(newStateToken);
    }
}
