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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class SubMenuDescriptor extends MenuDescriptor {

    public SubMenuDescriptor() {
        this(new BaseAction(null, null));
    }

    public SubMenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public SubMenuDescriptor(final AbstractGuiActionDescrip parent, final AbstractAction action) {
        super(action);
        setParent(parent);
        putValue(MENU_HIDE, false);
        putValue(MENU_SHOW, false);
        putValue(MENU_CLEAR, false);
        putValue(MENU_STANDALONE, false);
    }

    public SubMenuDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public SubMenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public SubMenuDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public SubMenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public SubMenuDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return SubMenuDescriptor.class;
    }

}
