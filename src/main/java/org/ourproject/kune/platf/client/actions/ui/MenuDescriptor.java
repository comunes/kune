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

import org.ourproject.kune.platf.client.actions.OldAbstractAction;
import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.resources.client.ImageResource;

public class MenuDescriptor extends OldGuiActionDescrip {

    public static final String MENU_HIDE = "hidemenu";
    public static final String MENU_SHOW = "showmenu";
    public static final String MENU_SHOW_ID = "showmenuid";
    public static final String MENU_CLEAR = "menuclear";

    private boolean standalone;

    public MenuDescriptor() {
        this(new BaseAction(null, null));
    }

    public MenuDescriptor(final OldAbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final OldGuiActionDescrip parent, final OldAbstractAction action) {
        super(action);
        setParent(parent);
        action.putValue(MENU_HIDE, false);
        action.putValue(MENU_SHOW, false);
        action.putValue(MENU_CLEAR, false);
        standalone = false;
    }

    public MenuDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public MenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public MenuDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public MenuDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public void clear() {
        // Action detects changes in values, then we fire a change (whatever) to
        // fire this method in the UI
        putValue(MENU_CLEAR, !((Boolean) getValue(MENU_CLEAR)));
    }

    @Override
    public Class<?> getType() {
        return MenuDescriptor.class;
    }

    public void hide() {
        putValue(MENU_HIDE, !((Boolean) getValue(MENU_HIDE)));
    }

    public boolean isStandalone() {
        return standalone;
    }

    /**
     * Sets the standalone property (if the menu should have button (for a
     * toolbar) or is a menu independent.
     * 
     * @param standalone
     *            the new standalone
     */
    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    public void setText(final String text) {
        putValue(Action.NAME, text);
    }

    /**
     * Show the menu near the Element id specified
     * 
     * @param id
     *            the element id to show menu near of it
     */
    public void show(final String id) {
        putValue(MENU_SHOW_ID, id);
        putValue(MENU_SHOW, !((Boolean) getValue(MENU_SHOW)));
    }
}
