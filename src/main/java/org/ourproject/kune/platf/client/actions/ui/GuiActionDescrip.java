package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class GuiActionDescrip { // NOPMD by vjrj on 9/06/09 2:00

    protected static final int NO_POSITION = -1;
    protected static final View NO_VIEW = null;
    protected static final GuiActionDescrip NO_PARENT = null;

    private GuiAddCondition addCondition = new GuiAddConditionAdapter();
    private GuiVisibleCondition visibleCondition = new GuiVisibleConditionAdapter();

    protected GuiActionDescrip parent;

    /** The action. */
    protected AbstractAction action;

    /** The position where the item will be inserted. */
    private int position;

    /** The item location. */
    private String location;

    /**
     * Instantiates a new abstract ui action descriptor. This is used for
     * describe UI button, menus, menu items and so on
     * 
     * @param action
     *            the action
     */
    public GuiActionDescrip(final AbstractAction action) {
        this.action = action;
        position = NO_POSITION;
        parent = NO_PARENT;
    }

    public void fire(final ActionEvent event) {
        action.actionPerformed(event);
    }

    public GuiAddCondition getAddCondition() {
        return addCondition;
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

    public GuiActionDescrip getParent() {
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

    /**
     * Returns the value associated with the specified key.
     * 
     * @param key
     *            the key (not <code>null</code>).
     * 
     * @return The value associated with the specified key, or <code>null</code>
     *         if the key is not found.
     * 
     * @see #putValue(String, Object)
     */
    public Object getValue(final String key) {
        return action.getValue(key);
    }

    public GuiVisibleCondition getVisibleCondition() {
        return visibleCondition;
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

    public boolean mustBeVisible() {
        boolean result = false;
        if (visibleCondition != null) {
            result = visibleCondition.mustBeVisible();
        }
        return result;
    }

    /**
     * Sets the value associated with the specified key.
     * 
     * Any existing value associated with the key will be overwritten.
     * 
     * @param key
     *            the key (not <code>null</code>).
     * @param value
     *            the value (<code>null</code> permitted).
     */
    public void putValue(final String key, final Object value) {
        action.putValue(key, value);
    }

    public void setAddCondition(final GuiAddCondition addCondition) {
        this.addCondition = addCondition;
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
    public void setPosition(final int position) {
        this.position = position;
    }

    public void setVisibleCondition(final GuiVisibleCondition visibleCondition) {
        this.visibleCondition = visibleCondition;
    }

    @Override
    public String toString() {
        final String name = (String) action.getValue(Action.NAME);
        final String tooltip = (String) action.getValue(Action.SHORT_DESCRIPTION);
        return "[GuiActionDescrip: " + action.getClass() + (name == null ? "" : " " + name)
                + (tooltip == null ? "" : " " + tooltip) + "]";
    }

}
