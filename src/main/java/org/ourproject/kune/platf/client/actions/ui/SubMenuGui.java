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

import com.gwtext.client.widgets.menu.MenuItem;

public class SubMenuGui extends AbstractMenuGui {

    private final MenuItem item;

    public SubMenuGui(final GuiActionDescrip descriptor) {
        super(descriptor);
        item = new MenuItem();
        item.setMenu(menu);
        configureItemFromProperties();
        // initWidget(item);
    }

    public MenuItem getMenuItem() {
        return item;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            item.enable();
        } else {
            item.disable();
        }
    }

    @Override
    public void setIconStyle(final String style) {
        item.setIconCls(style);
    }

    @Override
    public void setIconUrl(final String imageUrl) {
        item.setIcon(imageUrl);
    }

    @Override
    public void setText(final String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        item.setTitle(tooltip);
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }
}
