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
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

@Deprecated
public class DefaultBorderLayout {

    public enum Position {
        NORTH, CENTER, SOUTH, EAST, WEST
    }

    public static final int DEF_TOOLBAR_HEIGHT = 26;

    private static final int NO_SIZE = -666;

    private static RegionPosition[] regionPositions = new RegionPosition[] { RegionPosition.NORTH,
            RegionPosition.CENTER, RegionPosition.SOUTH, RegionPosition.EAST, RegionPosition.WEST };

    private final Panel mainPanel;

    public DefaultBorderLayout() {
        mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(false);
    }

    public DefaultBorderLayout(String title) {
        this();
        mainPanel.setTitle(title);
    }

    public void add(final Panel panel, final Position position) {
        add(panel, position, false);
    }

    public void add(final Panel panel, final Position position, final boolean split) {
        add(panel, position, split, NO_SIZE);
    }

    public void add(final Panel panel, final Position position, final boolean split, final int size) {
        final RegionPosition regionPosition = regionPositions[position.ordinal()];
        final BorderLayoutData borderLayoutData = new BorderLayoutData(regionPosition);
        borderLayoutData.setSplit(split);
        if (split) {
            borderLayoutData.setUseSplitTips(true);
            borderLayoutData.setCollapseModeMini(true);
        }
        if (size != NO_SIZE) {
            switch (position) {
            case NORTH:
            case SOUTH:
                panel.setHeight(size);
                break;
            case EAST:
            case WEST:
                panel.setWidth(size);
            }
        }
        mainPanel.add(panel, borderLayoutData);
        doLayoutIfNeeded();
    }

    public void add(final Panel panel, final Position position, final int size) {
        add(panel, position, false, size);
    }

    public void add(final Panel panel, final Widget widget) {
        panel.add(widget);
        if (panel.isRendered()) {
            panel.syncSize();
            panel.doLayout(false);
        }
        doLayoutIfNeeded();
    }

    public void addStyle(final String style) {
        mainPanel.addClass(style);
    }

    public Toolbar createBottomBar(final Panel panel) {
        return createBottomBar(panel, null);
    }

    public Toolbar createBottomBar(final Panel panel, final String cssStyle) {
        return createBottomBar(panel, cssStyle, null);
    }

    public Toolbar createBottomBar(final Panel panel, final String cssStyle, final String id) {
        final Toolbar bottomToolbar = new Toolbar();
        if (id != null) {
            bottomToolbar.setId(id);
        }
        bottomToolbar.setHeight(DEF_TOOLBAR_HEIGHT);
        if (cssStyle != null) {
            bottomToolbar.setCls(cssStyle);
        }
        panel.setBottomToolbar(bottomToolbar);
        return bottomToolbar;
    }

    public Toolbar createTopBar(final Panel panel) {
        return createTopBar(panel, null);
    }

    public Toolbar createTopBar(final Panel panel, final String cssStyle) {
        return createTopBar(panel, cssStyle);
    }

    public Toolbar createTopBar(final Panel panel, final String cssStyle, final String id) {
        final Toolbar topToolbar = new Toolbar();
        topToolbar.setHeight(DEF_TOOLBAR_HEIGHT);
        if (id != null) {
            topToolbar.setId(id);
        }
        if (cssStyle != null) {
            topToolbar.setCls(cssStyle);
        }
        panel.setTopToolbar(topToolbar);
        return topToolbar;
    }

    public void doLayoutIfNeeded() {
        if (mainPanel.isRendered()) {
            mainPanel.doLayout(false);
        }
    }

    public Panel getPanel() {
        return mainPanel;
    }

    public void removeStyle(final String style) {
        mainPanel.removeClass(style);
    }

    public void setBorder(final boolean border) {
        mainPanel.setBorder(border);
    }

    public void setPanel(final Panel panel, final Widget widget) {
        panel.clear();
        add(panel, widget);
        doLayoutIfNeeded();
    }

    public void setWidth(int width) {
        mainPanel.setWidth(width);
    }

    public void syncSize() {
        mainPanel.syncSize();
    }
}
