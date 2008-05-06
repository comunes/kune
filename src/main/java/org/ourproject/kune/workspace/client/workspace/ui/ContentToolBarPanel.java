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

import org.ourproject.kune.platf.client.extend.ExtensibleWidget;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetId;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarView;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ContentToolBarPanel extends HorizontalPanel implements ContentToolBarView, ExtensibleWidget {

    private final HorizontalPanel leftHP;

    public ContentToolBarPanel(final ContentToolBarPresenter presenter) {
        leftHP = new HorizontalPanel();
        add(leftHP);
        add(new Label(""));
        this.setWidth("100%");
        this.addStyleName("kune-ContentToolBarPanel");
        leftHP.addStyleName("kune-Margin-Large-l");
    }

    public HashMap<String, ExtensibleWidget> getExtensionPoints() {
        HashMap<String, ExtensibleWidget> extPoints = new HashMap<String, ExtensibleWidget>();
        extPoints.put(ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT, this);
        return extPoints;
    }

    public void attach(final String id, final Widget widget) {
        if (id.equals(ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT)) {
            leftHP.add(widget);
        }
    }

    public void detach(final String id, final Widget widget) {
        if (id.equals(ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT)) {
            leftHP.remove(widget);
        }
    }

    public void detachAll(final String id) {
        if (id.equals(ExtensibleWidgetId.CONTENT_TOOLBAR_LEFT)) {
            leftHP.clear();
        }
    }
}
