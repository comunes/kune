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

public class MenuBinding extends GuiBindingAdapter {

    public static final String UI_MENU = "UI_MENU";

    private Boolean isSubmenu;

    @Override
    public AbstractGuiItem create(final OldGuiActionDescrip descriptor) {
        AbstractGuiItem item;
        if (descriptor.isChild()) {
            final SubMenuGui submenu = new SubMenuGui(descriptor);
            final AbstractMenuGui parentMenu = ((AbstractMenuGui) descriptor.getParent().getValue(UI_MENU));
            final int position = descriptor.getPosition();
            if (position == OldGuiActionDescrip.NO_POSITION) {
                parentMenu.add(submenu.getMenuItem());
            } else {
                parentMenu.insert(position, submenu.getMenuItem());
            }
            descriptor.putValue(UI_MENU, submenu);
            item = submenu;
            isSubmenu = true;
        } else {
            // Is main parent menu
            final MenuGui menu = new MenuGui((MenuDescriptor) descriptor);
            descriptor.putValue(UI_MENU, menu);
            item = menu;
            isSubmenu = false;
        }
        return item;
    }

    @Override
    public boolean isAttachable() {
        if (isSubmenu == null) {
            throw new UIException("Please create element before to check if is attachable");
        }
        return !isSubmenu;
    }

}
