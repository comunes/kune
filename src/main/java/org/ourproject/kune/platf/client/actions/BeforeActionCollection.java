package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

/**
 * The Class BeforeActionCollection.
 */
public class BeforeActionCollection extends ArrayList<BeforeActionListener> {

    private static final long serialVersionUID = 1L;

    /**
     * Check before action listeners.
     * 
     * @return true, if all listener returns true
     */
    public boolean checkBeforeAction() {
        for (BeforeActionListener listener : this) {
            if (!listener.beforeAction()) {
                return false;
            }
        }
        return true;
    }

}
