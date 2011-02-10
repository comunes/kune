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
package org.ourproject.kune.platf.client.actions.ui;


import cc.kune.common.client.errors.UIException;

import com.gwtext.client.widgets.menu.Item;

public class MenuItemBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final OldGuiActionDescrip descriptor) {
        final MenuItemGui item = new MenuItemGui((OldMenuItemDescriptor) descriptor);
        final int position = descriptor.getPosition();
        final Item menuItem = (Item) item.getWidget();
        final AbstractMenuGui menu = ((AbstractMenuGui) descriptor.getParent().getValue(MenuBinding.UI_MENU));
        if (menu == null) {
            throw new UIException("To add a menu item you need to add the menu before. Item: " + descriptor);
        }
        if (position == OldGuiActionDescrip.NO_POSITION) {
            menu.add(menuItem);
        } else {
            menu.insert(position, menuItem);
        }
        return item;
    }

    @Override
    public boolean isAttachable() {
        return false;
    }
}
