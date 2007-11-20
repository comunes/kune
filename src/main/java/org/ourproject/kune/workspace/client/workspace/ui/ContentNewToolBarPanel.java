/*
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

package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.HashMap;

import org.ourproject.kune.workspace.client.WorkspaceUIExtensionPoint;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarView;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ContentNewToolBarPanel extends HorizontalPanel implements ContentToolBarView {

    private final HorizontalPanel leftHP;

    public ContentNewToolBarPanel(final ContentToolBarPresenter presenter) {
        leftHP = new HorizontalPanel();
        add(leftHP);
        add(new Label(""));
        this.setWidth("100%");
        this.addStyleName("kune-ContentToolBarPanel");
        leftHP.addStyleName("kune-Margin-Large-l");
    }

    public HashMap getExtensionPoints() {
        HashMap extPoints = new HashMap();
        String leftId = WorkspaceUIExtensionPoint.CONTENT_TOOLBAR_LEFT;
        extPoints.put(leftId, new WorkspaceUIExtensionPoint(leftId, leftHP));
        return extPoints;
    }
}
