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

import java.util.ArrayList;
import java.util.List;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ChangeableObject;
import cc.kune.common.client.actions.KeyStroke;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class AbstractGuiActionDescrip extends ChangeableObject implements GuiActionDescrip {

    /** The action. */
    private final AbstractAction action;

    private final List<GuiAddCondition> addConditions;

    /** The item DOM id. */
    private String id;

    /** The item location. */
    private String location;

    protected GuiActionDescrip parent = NO_PARENT;

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
        putValue(Action.ENABLED, Boolean.TRUE);
        putValue(VISIBLE, Boolean.TRUE);
        position = NO_POSITION;
        parent = NO_PARENT;
        addConditions = new ArrayList<GuiAddCondition>();
    }

    @Override
    public void add(final GuiAddCondition addCondition) {
        addConditions.add(addCondition);
    }

    @Override
    public void fire(final ActionEvent event) {
        action.actionPerformed(event);
    }

    @Override
    public AbstractAction getAction() {
        return action;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Gets the location, a string used to group actions with locations (top
     * bar, bottom bar, user bar...).
     * 
     * @return the location
     */
    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public GuiActionDescrip getParent() {
        return parent;
    }

    /**
     * Gets the position.
     * 
     * @return the position
     */
    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Object getTarget() {
        return getValue(TARGET);
    }

    @Override
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

    @Override
    public boolean hasTarget() {
        return getValue(TARGET) != null;
    }

    @Override
    public boolean isChild() {
        return !parent.equals(NO_PARENT);
    }

    @Override
    public boolean isEnabled() {
        return (Boolean) getValue(Action.ENABLED);
    }

    @Override
    public boolean isVisible() {
        return (Boolean) getValue(VISIBLE);
    }

    @Override
    public boolean mustBeAdded() {
        final boolean result = true;
        for (final GuiAddCondition addCondition : addConditions) {
            if (!addCondition.mustBeAdded(this)) {
                return false;
            }
        }
        return result;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        putValue(Action.ENABLED, enabled);
    }

    @Override
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
    @Override
    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    public void setParent(final GuiActionDescrip parent) {
        this.parent = parent;
    }

    /**
     * Sets the position (where the UI element will be positioned, for instance
     * in a toolbar or in a menu).
     * 
     * @param position
     *            the new position
     */
    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    @Override
    public void setStyles(final String styles) {
        putValue(Action.STYLES, styles);
    }

    @Override
    public void setTarget(final Object object) {
        putValue(TARGET, object);
    }

    @Override
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

    @Override
    public GuiActionDescrip withIcon(final Object icon) {
        super.putValue(Action.SMALL_ICON, icon);
        return this;
    }

    @Override
    public GuiActionDescrip withIconCls(final String icon) {
        putValue(Action.SMALL_ICON, icon);
        return this;
    }

    @Override
    public GuiActionDescrip withParent(final GuiActionDescrip parent) {
        setParent(parent);
        return this;
    }

    @Override
    public GuiActionDescrip withShortcut(final KeyStroke shortcut) {
        putValue(Action.ACCELERATOR_KEY, shortcut);
        return this;
    }

    @Override
    public GuiActionDescrip withStyles(final String styles) {
        setStyles(styles);
        return this;
    }

    @Override
    public GuiActionDescrip withText(final String text) {
        putValue(Action.NAME, text);
        return this;
    }

    @Override
    public GuiActionDescrip withToolTip(final String tooltip) {
        putValue(Action.SHORT_DESCRIPTION, tooltip);
        return this;
    }
}
