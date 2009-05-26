package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;

/**
 * The Class AbstractUIActionDescriptor.
 */
public class AbstractUIActionDescriptor {

    protected static final int NO_POSITION = -1;
    protected static final View NO_VIEW = null;
    protected static final AbstractUIActionDescriptor NO_PARENT = new AbstractUIActionDescriptor(null);

    protected transient View view;
    protected AbstractUIActionDescriptor parent;

    /** The action. */
    protected transient AbstractAction action;

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
    public AbstractUIActionDescriptor(final AbstractAction action) {
        this.action = action;
        position = NO_POSITION;
        parent = NO_PARENT;
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

    public AbstractUIActionDescriptor getParent() {
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

    public View getView() {
        return view;
    }

    public boolean isChild() {
        return !parent.equals(NO_PARENT);
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

    public void setParent(final AbstractUIActionDescriptor parent) {
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
}
