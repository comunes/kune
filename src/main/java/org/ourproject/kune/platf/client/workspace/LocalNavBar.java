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
package org.ourproject.kune.platf.client.workspace;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class LocalNavBar extends VerticalPanel implements ClickListener {
    public final static int NONE = -1;

    private int currentItem = NONE;

    public LocalNavBar() {
        super();
    }

    public void addItem(String name, String url) {
        Hyperlink hl = new Hyperlink(name, url);
        SimplePanel menuItem = new SimplePanel();
        menuItem.add(hl);
        menuItem.addStyleName("itemNotSelected");
        this.add(menuItem);
        hl.addClickListener(this);
    }

    public int getItemsCount() {
        return this.getWidgetCount();
    }

    public void selectItem(int item) {
        this.verifyExist(item);
        SimplePanel itemPanel = ((SimplePanel)this.getWidget(currentItem));
        if (currentItem != NONE) {
            itemPanel.removeStyleName("itemSelected");
            itemPanel.addStyleName("itemNotSelected");
        }
        itemPanel.removeStyleName("itemNotSelected");
        itemPanel.addStyleName("itemSelected");
        this.currentItem = item;
    }

    private void verifyExist(int item) {
        if (item >= getItemsCount()) {
            throw new IllegalArgumentException("LocalNavBar Item Not found");
        }
    }

    public void deleteItem(int item) {
        this.verifyExist(item);
        this.deleteItem(item);
        if (currentItem == item) {
            currentItem = NONE ;
        }
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public void onClick(Widget sender) {
        Hyperlink hl = new Hyperlink();
        for (int i = 0; i < getItemsCount(); i++) {
            hl = ((Hyperlink) ((SimplePanel) this.getWidget(i)).getWidget());
            if (hl == sender) {
                selectItem(i);
                return;
            }
        }
    }
}