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
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public class FlowToolbar extends Composite implements AbstractToolbar {

    private final HorizontalPanel mainPanel;
    private final FlowPanel childPanel;

    public FlowToolbar() {
        super();
        mainPanel = new HorizontalPanel();
        childPanel = new FlowPanel();
        mainPanel.add(childPanel);
        initWidget(mainPanel);
    }

    public void add(final Widget widget) {
        childPanel.add(widget);
    }

    public void add(final Widget widget, final VerticalAlignmentConstant valign) {
        childPanel.add(widget);
    }

    public Widget addFill() {
        final Label emptyLabel = new Label("");
        emptyLabel.addStyleName("kune-floatleft");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public Widget addSeparator() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-sep");
        emptyLabel.addStyleName("k-toolbar-sep");
        emptyLabel.addStyleName("kune-floatleft");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public Widget addSpacer() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-spacer");
        emptyLabel.addStyleName("kune-floatleft");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public void insert(final Widget widget, final int position) {
        childPanel.insert(widget, position);
    }

    public void remove(final Widget widget) {
        childPanel.remove(widget);
    }

    public void removeAll() {
        childPanel.clear();
    }

    /**
     * Set the blank style
     */
    public void setBlankStyle() {
        setBasicStyle();
        addStyleName("k-blank-toolbar");
    }

    @Override
    public void setHeight(final String height) {
        mainPanel.setHeight(height);
        childPanel.setHeight(height);
    }

    /**
     * Set the normal grey style
     */
    public void setNormalStyle() {
        setBasicStyle();
        addStyleName("k-toolbar-bottom-line");
    }

    /**
     * Set the transparent style
     */
    public void setTranspStyle() {
        setBasicStyle();
        addStyleName("k-transp");
    }

    private void setBasicStyle() {
        setStyleName("x-toolbar");
        addStyleName("x-panel");
    }
}