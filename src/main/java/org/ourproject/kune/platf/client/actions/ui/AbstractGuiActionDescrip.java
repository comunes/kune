package org.ourproject.kune.platf.client.actions.ui;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;

/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class AbstractGuiActionDescrip {

    protected static final int NO_POSITION = -1;
    protected static final View NO_VIEW = null;
    protected static final AbstractGuiActionDescrip NO_PARENT = null;

    private GuiAddCondition addCondition = new GuiAddConditionAdapter();
    private GuiVisibleCondition visibleCondition = new GuiVisibleConditionAdapter();

    protected AbstractGuiActionDescrip parent;
    private final Map<String, Object> store;

    /** The action. */
    protected AbstractAction action;

    /** The position where the item will be inserted. */
    private int position;

    /** If the action must be confirmed. */
    private boolean confirmRequired;

    /** The confirmation dialog title. */
    private String confirmationTitle;

    /** The confirmation dialog text. */
    private String confirmationText;

    /** The item location. */
    private String location;

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
        store = new HashMap<String, Object>();
    }

    public GuiAddCondition getAddCondition() {
        return addCondition;
    }

    /**
     * Gets the dialog confirmation text.
     * 
     * @return the confirmation text
     */
    public String getConfirmationText() {
        return confirmationText;
    }

    /**
     * Gets the dialog confirmation title.
     * 
     * @return the confirmation title
     */
    public String getConfirmationTitle() {
        return confirmationTitle;
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
        return store.get(key);
    }

    public GuiVisibleCondition getVisibleCondition() {
        return visibleCondition;
    }

    public boolean isChild() {
        // @PMD:REVIEWED:CompareObjectsWithEquals: by vjrj on 26/05/09 20:57
        return parent != NO_PARENT;
    }

    /**
     * Checks if is confirm required previous to do the action.
     * 
     * @return true, if is confirm required
     */
    public boolean isConfirmRequired() {
        return confirmRequired;
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
        store.put(key, value);
    }

    public void setAddCondition(final GuiAddCondition addCondition) {
        this.addCondition = addCondition;
    }

    /**
     * Sets the confirmation text.
     * 
     * @param confirmationText
     *            the new confirmation text
     */
    public void setConfirmationText(final String confirmationText) {
        this.confirmationText = confirmationText;
    }

    /**
     * Sets the confirmation title.
     * 
     * @param confirmationTitle
     *            the new confirmation title
     */
    public void setConfirmationTitle(final String confirmationTitle) {
        this.confirmationTitle = confirmationTitle;
    }

    /**
     * Sets the confirm required.
     * 
     * @param isConfirmRequired
     *            the new confirm required
     */
    public void setConfirmRequired(final boolean isConfirmRequired) {
        this.confirmRequired = isConfirmRequired;
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

    public void setVisibleCondition(final GuiVisibleCondition visibleCondition) {
        this.visibleCondition = visibleCondition;
    }

}
