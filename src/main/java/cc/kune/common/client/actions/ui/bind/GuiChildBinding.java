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
package cc.kune.common.client.actions.ui.bind;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.UIObject;

public abstract class GuiChildBinding extends AbstractGuiBinding {

    protected UIObject child;
    protected ParentWidget parent;

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        final int position = descriptor.getPosition();
        if (descriptor.isChild()) {
            // A menu item is a child, a toolbar separator, also. A button can
            // be a child of a toolbar or not
            parent = ((ParentWidget) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
            if (parent == null) {
                throw new UIException("To add a item you need to add its parent before. Item: " + descriptor);
            }
            if (child != null) {
                // Sometimes (menu/toolbar separators), there is no Widget to
                // add/insert
                if (position == GuiActionDescrip.NO_POSITION) {
                    parent.add(child);
                } else {
                    parent.insert(position, child);
                }
            }
        }
        return super.create(descriptor);
    }
}
