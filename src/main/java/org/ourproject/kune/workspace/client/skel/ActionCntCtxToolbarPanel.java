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
 \*/
package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.Widget;

public class ActionCntCtxToolbarPanel<T> extends ActionToolbarPanel<T> {

    Toolbar wsToolbar;

    public ActionCntCtxToolbarPanel(ActionToolbarPosition position,
            final Provider<ActionManager> actionManagerProvider, final WorkspaceSkeleton ws) {
        super(actionManagerProvider);
        EntityWorkspace entityWorkspace = ws.getEntityWorkspace();
        if (position.equals(AbstractFoldableContentActions.CONTENT_TOPBAR)) {
            wsToolbar = entityWorkspace.getContentTopBar();
        } else if (position.equals(AbstractFoldableContentActions.CONTENT_BOTTOMBAR)) {
            wsToolbar = entityWorkspace.getContentBottomBar();
        } else if (position.equals(AbstractFoldableContentActions.CONTEXT_TOPBAR)) {
            wsToolbar = entityWorkspace.getContextTopBar();
        } else if (position.equals(AbstractFoldableContentActions.CONTEXT_BOTTOMBAR)) {
            wsToolbar = entityWorkspace.getContextBottomBar();
        }

    }

    @Override
    public void attach() {
        if (!toolbar.isAttached()) {
            wsToolbar.removeAll();
            wsToolbar.add((Widget) toolbar);
        }
    }

    @Override
    public void detach() {
        if (toolbar.isAttached()) {
            wsToolbar.remove((Widget) toolbar);
        }
    }
}
