package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

public class BeforeActionCollection extends ArrayList<BeforeActionListener> {

    private static final long serialVersionUID = 1L;

    public boolean checkBeforeAction() {
        for (BeforeActionListener listener : this) {
            if (!listener.beforeAction()) {
                return false;
            }
        }
        return true;
    }

}
