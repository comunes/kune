package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;

public interface IsActionExtensible {

    /**
     * Adds a action description
     * 
     * @param action
     *            the action
     */
    void addAction(AbstractGuiActionDescrip action);

    /**
     * Adds some action descriptions
     * 
     * @param action
     *            the action
     */
    void addActions(AbstractGuiActionDescrip... actions);

    /**
     * Adds some action descriptions
     * 
     * @param actions
     *            the actions
     */
    void addActions(GuiActionDescCollection actions);

}
