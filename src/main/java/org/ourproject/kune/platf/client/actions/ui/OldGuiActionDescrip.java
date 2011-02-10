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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.OldAbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ChangebleObject;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class OldGuiActionDescrip extends ChangebleObject {

    public static final String VISIBLE = "visibleprop";

    protected static final int NO_POSITION = -1;
    protected static final View NO_VIEW = null;
    protected static final OldGuiActionDescrip NO_PARENT = null;

    private GuiAddCondition addCondition = new GuiAddConditionAdapter();

    protected OldGuiActionDescrip parent;

    /** The action. */
    private final OldAbstractAction action;

    /** The position where the item will be inserted. */
    private int position;

    /** The item location. */
    private String location;

    /** The item DOM id. */
    private String id;

    /**
     * Instantiates a new abstract ui action descriptor. This is used for
     * describe UI button, menus, menu items and so on
     * 
     * @param action
     *            the action
     */
    public OldGuiActionDescrip(final OldAbstractAction action) {
        this.action = action;
        position = NO_POSITION;
        parent = NO_PARENT;
    }

    public void fire(final ActionEvent event) {
        action.actionPerformed(event);
    }

    public OldAbstractAction getAction() {
        return action;
    }

    public GuiAddCondition getAddCondition() {
        return addCondition;
    }

    public String getId() {
        return id;
    }

    /**
     * Gets the location, a string used to group actions with locations (top
     * bar, bottom bar, user bar...).
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    public OldGuiActionDescrip getParent() {
        return parent;
    }

    /**
     * Gets the position.
     * 
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    public abstract Class<?> getType();

    @Override
    /**
     * We try to get the gui property (for instance the name) and if it's empty we try to get the same property in actions. This permit to have several gui items with the same action but different gui properties (like text descriptions) if necessary.
     */
    public Object getValue(final String key) {
        final Object guiValue = super.getValue(key);
        if (guiValue == null) {
            return action.getValue(key);
        } else {
            return guiValue;
        }
    }

    public boolean isChild() {
        // @PMD:REVIEWED:CompareObjectsWithEquals: by vjrj on 26/05/09 20:57
        return parent != NO_PARENT;
    }

    public boolean mustBeAdded() {
        boolean result = false;
        if (addCondition != null) {
            result = addCondition.mustBeAdded();
        }
        return result;
    }

    public void setAddCondition(final GuiAddCondition addCondition) {
        this.addCondition = addCondition;
    }

    public void setEnabled(final boolean enabled) {
        action.setEnabled(enabled);
    }

    public void setId(final String id) {
        this.id = id;
    }

    /**
     * If we have several toolbars, we can group with the "location" string key
     * actions that must be in the same location (ex: top bar, bottom bar, and
     * so on).
     * 
     * @param location
     *            the new location
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    public void setParent(final OldGuiActionDescrip parent) {
        this.parent = parent;
    }

    /**
     * Sets the position (where the UI element will be positioned, for instance
     * in a toolbar or in a menu).
     * 
     * @param position
     *            the new position
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    public void setVisible(final boolean visible) {
        action.putValue(VISIBLE, visible);
    }

    @Override
    public String toString() {
        final String name = (String) getValue(Action.NAME);
        final String tooltip = (String) getValue(Action.SHORT_DESCRIPTION);
        return "[GuiActionDescrip: " + action.getClass() + (name == null ? "" : " " + name)
                + (tooltip == null ? "" : " " + tooltip) + "]";
    }

}
