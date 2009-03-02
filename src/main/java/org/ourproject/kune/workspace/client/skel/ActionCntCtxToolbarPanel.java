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
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;

import com.calclab.suco.client.ioc.Provider;

public class ActionCntCtxToolbarPanel<T> extends ActionToolbarPanel<T> {

    public enum Position {
        content, context
    }

    private final Position position;
    private final WorkspaceSkeleton ws;

    public ActionCntCtxToolbarPanel(final Position position, final Provider<ActionManager> actionManagerProvider,
            final WorkspaceSkeleton ws) {
        super(actionManagerProvider);
        this.position = position;
        this.ws = ws;
    }

    @Override
    public void attach() {
        if (!topbar.isAttached()) {
            switch (position) {
            case content:
                ws.getEntityWorkspace().getContentTopBar().removeAll();
                ws.getEntityWorkspace().getContentTopBar().add(topbar);
                ws.getEntityWorkspace().getContentBottomBar().add(bottombar);
                break;
            case context:
            default:
                ws.getEntityWorkspace().getContextTopBar().removeAll();
                ws.getEntityWorkspace().getContextTopBar().add(topbar);
                ws.getEntityWorkspace().getContextBottomBar().add(bottombar);
            }
        }
    }

    @Override
    public void detach() {
        if (topbar.isAttached()) {
            switch (position) {
            case content:
                ws.getEntityWorkspace().getContentTopBar().remove(topbar);
                ws.getEntityWorkspace().getContentBottomBar().remove(bottombar);
                break;
            case context:
            default:
                ws.getEntityWorkspace().getContextTopBar().remove(topbar);
                ws.getEntityWorkspace().getContextBottomBar().remove(bottombar);
            }
        }
    }
}
