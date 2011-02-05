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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ChangeableObject;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class AbstractGuiActionDescrip extends ChangeableObject {

    protected static final AbstractGuiActionDescrip NO_PARENT = new NoParentGuiActionDescriptor();
    public static final int NO_POSITION = -1;
    /**
     * Coma separated, styles of the Gui item
     */
    public static final String STYLES = "stylesprop";
    public static final String VISIBLE = "visibleprop";

    /** The action. */
    private final AbstractAction action;

    private GuiAddCondition addCondition = new GuiAddConditionAdapter();

    /** The item DOM id. */
    private String id;

    /** The item location. */
    private String location;

    protected AbstractGuiActionDescrip parent = NO_PARENT;

    /** The position where the item will be inserted. */
    private int position;

    /**
     * Instantiates a new abstract ui action descriptor. This is used for
     * describe UI button, menus, menu items and so on
     * 
     * @param action
     *            the action
     */
    public AbstractGuiActionDescrip(final AbstractAction action) {
        this.action = action;
        position = NO_POSITION;
        parent = NO_PARENT;
    }

    public void fire(final ActionEvent event) {
        action.actionPerformed(event);
    }

    public AbstractAction getAction() {
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

    public AbstractGuiActionDescrip getParent() {
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
        return !parent.equals(NO_PARENT);
    }

    public boolean isEnabled() {
        return (Boolean) getValue(Action.ENABLED);
    }

    public boolean isVisible() {
        return (Boolean) getValue(VISIBLE);
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
        super.putValue(Action.ENABLED, enabled);
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

    public void setParent(final AbstractGuiActionDescrip parent) {
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

    public void setStyles(final String styles) {
        putValue(STYLES, styles);
    }

    public void setVisible(final boolean visible) {
        putValue(VISIBLE, visible);
    }

    @Override
    public String toString() {
        final String name = (String) getValue(Action.NAME);
        final String tooltip = (String) getValue(Action.SHORT_DESCRIPTION);
        return "[GuiActionDescrip: " + getClass().getName() + (name == null ? "" : " " + name)
                + (tooltip == null ? "" : " " + tooltip) + "]";
    }
}
