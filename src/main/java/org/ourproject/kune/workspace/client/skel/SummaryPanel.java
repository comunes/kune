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
package org.ourproject.kune.workspace.client.skel;

import com.gwtext.client.widgets.Panel;

public class SummaryPanel extends Panel {

    private final WorkspaceSkeleton ws;

    public SummaryPanel(String title, String titleTooltip, WorkspaceSkeleton ws) {
        this.ws = ws;
        super.setBorder(false);
        super.setTitle("<span ext:qtip=\"" + titleTooltip + "\">" + title + "</span>");
        super.setAutoScroll(true);
    }

    public void doLayoutIfNeeded() {
        if (super.isRendered()) {
            super.doLayout(false);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        doLayoutIfNeeded();
        ws.refreshSummary();
    }
}
