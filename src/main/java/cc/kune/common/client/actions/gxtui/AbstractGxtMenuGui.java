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
 */
package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractGxtMenuGui extends AbstractChildGuiItem implements ParentWidget {

    public enum MenuPosition {
        b, // The top left corner (default)
        bl, // The center of the top edge
        br, // The bottom right corner ,// The top right corner
        c, // The center of the left edge
        l, // In the center of the element
        r, // The center of the right edge
        t, // The bottom left corner
        tl, // The center of the bottom edge
        tr
    }
    public static final String DEF_MENU_POSITION = "bl";
    public static final String MENU_POSITION = "menu-position";
    protected Menu menu;

    public AbstractGxtMenuGui() {
    }

    public AbstractGxtMenuGui(final GuiActionDescrip descriptor) {
        super(descriptor);
    }

    @Override
    public void add(final UIObject item) {
        menu.add((MenuItem) item);
    }

    public void addSeparator() {
        menu.add(new SeparatorMenuItem());
    }

    @Override
    public void configureItemFromProperties() {
        super.configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.removeAll();
                }
            }
        });
    }

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        super.create(descriptor);
        menu = new Menu();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    menu.hide();
                }
            }
        });
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
                    show(descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO));
                }
            }
        });
        return this;
    }

    protected String getMenuPosition() {
        final MenuPosition position = (MenuPosition) descriptor.getValue(MENU_POSITION);
        return position == null ? DEF_MENU_POSITION : position.name();
    }

    @Override
    public void insert(final int position, final UIObject item) {
        menu.insert((MenuItem) item, position);
    }

    @Override
    public boolean shouldBeAdded() {
        return !descriptor.isChild();
    }

    public void show(final Object relative) {
        if (relative instanceof String) {
            menu.show(RootPanel.get((String) relative).getElement(), getMenuPosition());
        } else if (relative instanceof UIObject) {
            menu.show(((UIObject) relative).getElement(), getMenuPosition());
        } else if (relative instanceof Position) {
            final Position position = (Position) relative;
            menu.showAt(position.getX(), position.getY());
        }
    }

}