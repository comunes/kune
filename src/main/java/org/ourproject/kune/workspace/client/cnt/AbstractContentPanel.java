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
package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractContentPanel {
    private final WorkspaceSkeleton ws;
    private Widget widget;

    public AbstractContentPanel(final WorkspaceSkeleton ws) {
        this.ws = ws;
    }

    public void attach() {
        if (widget != null && !widget.isAttached()) {
            ws.getEntityWorkspace().setContent(widget);
        }
    }

    public void detach() {
        if (widget != null && widget.isAttached()) {
            widget.removeFromParent();
        }
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
}
