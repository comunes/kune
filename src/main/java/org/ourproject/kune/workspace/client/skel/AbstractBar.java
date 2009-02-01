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
 \*/
package org.ourproject.kune.workspace.client.skel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 *
 */
public class AbstractBar extends Composite {
    private final HorizontalPanel childPanel;
    private final HorizontalPanel mainPanel;

    public AbstractBar() {
        mainPanel = new HorizontalPanel();
        childPanel = new HorizontalPanel();
        mainPanel.add(childPanel);
        initWidget(mainPanel);
        mainPanel.setWidth("100%");
    }

    public void add(final Widget widget) {
        childPanel.add(widget);
        childPanel.setCellVerticalAlignment(widget, VerticalPanel.ALIGN_MIDDLE);
    }

    public void addFill() {
        final Label emptyLabel = new Label("");
        this.add(emptyLabel);
        childPanel.setCellWidth(emptyLabel, "100%");
    }

    public void addSeparator() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-sep");
        emptyLabel.addStyleName("k-toolbar-sep");
        this.add(emptyLabel);
    }

    public void addSpacer() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-spacer");
        this.add(emptyLabel);
    }

}
