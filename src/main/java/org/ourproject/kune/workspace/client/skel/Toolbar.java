/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.ui.SimpleToolbar;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class Toolbar {
    private static final String _100PC = "100%";
    private final Panel container;
    private final HorizontalPanel childPanel;

    public Toolbar() {
        container = new Panel();
        container.setBorder(false);
        container.setBodyBorder(false);
        container.setHeight(WorkspaceSkeleton.DEF_BAR_HEIGHT);
        container.setWidth(_100PC);
        container.setHeader(false);
        container.setBaseCls("x-toolbar");
        container.addClass("x-panel");
        childPanel = new HorizontalPanel();
        container.add(childPanel);
    }

    public void add(final Widget widget) {
        childPanel.add(widget);
        if (widget instanceof SimpleToolbar) {
            widget.setWidth(_100PC);
            childPanel.setCellWidth(widget, _100PC);
        }
        doLayoutIfNeeded();
    }

    public Widget addFill() {
        final Label emptyLabel = new Label("");
        this.add(emptyLabel);
        childPanel.setCellWidth(emptyLabel, _100PC);
        return emptyLabel;
    }

    public Widget addSeparator() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-sep");
        emptyLabel.addStyleName("k-toolbar-sep");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public Widget addSpacer() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-spacer");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public void addStyleName(final String cls) {
        container.addClass(cls);
    }

    public void doLayoutIfNeeded() {
        if (container.isRendered()) {
            container.doLayout(false);
        }
    }

    public Panel getPanel() {
        return container;
    }

    public void remove(final Widget widget) {
        childPanel.remove(widget);
        doLayoutIfNeeded();
    }

    public void removeAll() {
        childPanel.clear();
        doLayoutIfNeeded();
    }

}
