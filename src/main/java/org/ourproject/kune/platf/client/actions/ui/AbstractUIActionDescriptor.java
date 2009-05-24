package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUIActionDescriptor.
 */
public abstract class AbstractUIActionDescriptor {

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

    /**
     * Gets the position.
     * 
     * @return the position
     */
    public int getPosition() {
        return position;
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
