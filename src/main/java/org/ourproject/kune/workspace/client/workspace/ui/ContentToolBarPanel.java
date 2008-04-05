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
package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.HashMap;

import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarView;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ContentToolBarPanel extends HorizontalPanel implements ContentToolBarView {

    private final HorizontalPanel leftHP;

    public ContentToolBarPanel(final ContentToolBarPresenter presenter) {
        leftHP = new HorizontalPanel();
        add(leftHP);
        add(new Label(""));
        this.setWidth("100%");
        this.addStyleName("kune-ContentToolBarPanel");
        leftHP.addStyleName("kune-Margin-Large-l");
    }

    public HashMap<String, UIExtensionPoint> getExtensionPoints() {
        HashMap<String, UIExtensionPoint> extPoints = new HashMap<String, UIExtensionPoint>();
        String leftId = UIExtensionPoint.CONTENT_TOOLBAR_LEFT;
        extPoints.put(leftId, new UIExtensionPoint(leftId, leftHP));
        return extPoints;
    }
}
