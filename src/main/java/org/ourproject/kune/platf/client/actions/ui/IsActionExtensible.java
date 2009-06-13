package org.ourproject.kune.platf.client.actions.ui;

public interface IsActionExtensible {

    /**
     * Adds a action description
     * 
     * @param action
     *            the action
     */
    void addAction(GuiActionDescrip action);

    /**
     * Adds some action descriptions
     * 
     * @param actions
     *            the actions
     */
    void addActionCollection(GuiActionDescCollection actions);

    /**
     * Adds some action descriptions
     * 
     * @param action
     *            the action
     */
    void addActions(GuiActionDescrip... actions);

}
