/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.platf.client.workspace.ui;

import org.ourproject.kune.platf.client.ColorScheme;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.ui.BorderDecorator;
import org.ourproject.kune.platf.client.ui.HasColor;
import org.ourproject.kune.platf.client.workspace.WorkspaceListener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupNavBar extends VerticalPanel {
    private static final String ITEM_SELECTED = "itemSelected";
    private Widget currentTab;

    public GroupNavBar(final WorkspaceListener listener) {
        currentTab = null;
        addStyleName("kune-GroupNavBar");
    }

    public void addItem(final String name) {
        final int nextIndex = this.getWidgetCount();
        // TODO: revistar el history token
        final Widget menuItem = createItem(nextIndex, name, "tab" + nextIndex);
        setTabSelected(menuItem, false);
        this.add(menuItem);
    }

    private Widget createItem(final int myIndex, final String name, final String historyToken) {
        final SimplePanel menuItem = new SimplePanel();
        addStyleName("Tab");
        final Hyperlink hl = new Hyperlink(name, historyToken);
        hl.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                selectItem(myIndex);
            }});
        menuItem.add(hl);
        return new BorderDecorator(menuItem, BorderDecorator.RIGHT);
    }

    public void selectItem(final int index) {
        if (currentTab != null) {
            setTabSelected(currentTab, false);
        }
        currentTab = this.getWidget(index);
        setTabSelected(currentTab, true);
    }

    private void setTabSelected(Widget tab, boolean isSelected) {
        ColorScheme scheme = Kune.getInstance().c;
        if (isSelected) tab.addStyleName(ITEM_SELECTED);
        else tab.removeStyleName(ITEM_SELECTED);
        String color = isSelected ? scheme.getSelected() : scheme.getUnselected();
        ((HasColor) tab).setColor(color);
    }
}