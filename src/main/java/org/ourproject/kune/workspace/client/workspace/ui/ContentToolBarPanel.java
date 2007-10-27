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

import org.ourproject.kune.platf.client.ui.CustomPushButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ContentToolBarPanel extends HorizontalPanel {
    private CustomPushButton btnEnter;

    public ContentToolBarPanel() {
        Label expand = new Label("");
        this.add(expand);
        this.setWidth("100%");
        expand.setWidth("100%");
        this.setCellWidth(expand, "100%");
        this.addStyleName("kune-ContentToolBarPanel");
    }

    public void addButton(final String caption, final ClickListener listener) {
        btnEnter = new CustomPushButton(caption, listener);
        this.add(btnEnter);
        btnEnter.addStyleName("kune-Button-Large-lrSpace");
    }

    public void setButtonVisible(final boolean isEnabled) {
        btnEnter.setVisible(isEnabled);
    }

}
