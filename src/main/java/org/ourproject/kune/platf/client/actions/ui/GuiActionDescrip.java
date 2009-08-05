package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ChangebleObject;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class GuiActionDescrip extends ChangebleObject {

    public static final String VISIBLE = "visibleprop";

    protected static final int NO_POSITION = -1;
    protected static final View NO_VIEW = null;
    protected static final GuiActionDescrip NO_PARENT = null;

    private GuiAddCondition addCondition = new GuiAddConditionAdapter();

    protected GuiActionDescrip parent;

    /** The action. */
    private final AbstractAction action;

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
    public GuiActionDescrip(final AbstractAction action) {
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
