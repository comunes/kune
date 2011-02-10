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
package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.OldAbstractAction;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.OldGuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.GuiAddCondition;
import org.ourproject.kune.platf.client.actions.ui.IsActionExtensible;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;

import com.calclab.suco.client.events.Listener0;

// TODO: Auto-generated Javadoc
/**
 * The Interface RTEditor.
 */
public interface RTEditor extends IsActionExtensible {

    /**
     * The TOPBAR location used in {@link OldGuiActionDescrip#setLocation(String)}
     * for put the actions in the correct position
     */
    String TOPBAR = "rte-topbar";

    /**
     * The SNDBAR location used in {@link OldGuiActionDescrip#setLocation(String)}
     * for put the actions in the correct position
     */
    String SNDBAR = "rte-sndbar";

    /**
     * The LINKCTX location used in {@link OldGuiActionDescrip#setLocation(String)}
     * for put the actions in the links context menu
     */
    String LINKCTX = "rte-linkctx";

    /**
     * Adds the on edit listener (fired when the user do some edit).
     * 
     * @param listener
     *            the listener
     */
    void addOnEditListener(Listener0 listener);

    /**
     * Attach (used for lazy action loading)
     */
    void attach();

    /**
     * Detach.
     */
    void detach();

    /**
     * Gets the basic add condition (if the editor is of "basic" type).
     * 
     * @return the basic add condition
     */
    GuiAddCondition getBasicAddCondition();

    /**
     * Gets the "Edit" menu.
     * 
     * @return the edits the menu
     */
    MenuDescriptor getEditMenu();

    /**
     * Gets the editor area (used for gui layout)
     * 
     * @return the editor area
     */
    View getEditorArea();

    /**
     * Gets the extended add condition (editor can be a extended editor)
     * 
     * @return the extended add condition
     */
    GuiAddCondition getExtendedAddCondition();

    /**
     * Gets the file menu.
     * 
     * @return the file menu
     */
    MenuDescriptor getFileMenu();

    /**
     * Gets the format menu descriptor.
     * 
     * @return the format menu
     */
    MenuDescriptor getFormatMenu();

    /**
     * Gets the html of the text area.
     * 
     * @return the html
     */
    String getHtml();

    /**
     * Gets the insert menu descriptor.
     * 
     * @return the insert menu
     */
    MenuDescriptor getInsertMenu();

    /**
     * Gets the link ctx menu descriptor.
     * 
     * @return the link ctx menu
     */
    MenuDescriptor getLinkCtxMenu();

    /**
     * Gets the second bar of the editor.
     * 
     * @return the snd bar
     */
    View getSndBar();

    /**
     * Gets the text of the text area (not the html).
     * 
     * @return the text
     */
    String getText();

    /**
     * Gets the top bar.
     * 
     * @return the top bar
     */
    View getTopBar();

    /**
     * Reset.
     */
    void reset();

    /**
     * Sets an action shortcut.
     * 
     * @param key
     *            the key
     * @param action
     *            the action
     */
    void setActionShortcut(KeyStroke key, OldAbstractAction action);

    /**
     * Sets some action shortcut, some principal, other similar (used when some
     * UI buttons do the same action).
     * 
     * @param key
     *            the key
     * @param mainAction
     *            the main action
     * @param actions
     *            the actions
     */
    void setActionShortcut(KeyStroke key, OldAbstractAction mainAction, OldAbstractAction... actions);

    /**
     * Sets that editor must be extended type (if browser permit it).
     * 
     * @param extended
     *            the new extended
     */
    void setExtended(boolean extended);

    /**
     * Sets the focus on text editor.
     * 
     * @param focus
     *            the new focus
     */
    void setFocus(boolean focus);

    /**
     * Sets the html of the editor.
     * 
     * @param html
     *            the new html
     */
    void setHtml(String html);

    /**
     * We can define some location ("topbar", "second bar", etc) to action
     * descriptors.
     * 
     * @param location
     *            the location
     * @param descripts
     *            the descripts
     */
    void setLocation(String location, OldGuiActionDescrip... descripts);

    /**
     * Sets the text of the editor.
     * 
     * @param text
     *            the new text
     */
    void setText(String text);
}
