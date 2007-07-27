package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.HistoryListener;

public class EventDispatcher implements HistoryListener {
    private final HashMap controllers;

    public EventDispatcher() {
        this.controllers = new HashMap();
    }

    public void register(String name, Controller controller) {
        controllers.put(name, controller);
    }
    
    public void onHistoryChanged(String historyToken) {
        if (!execute(historyToken)) {
            GWT.log("ACTION NOT FOUND: " + historyToken, null);
        }
    }

    private boolean execute(String historyToken) {
        HistoryToken token = HistoryToken.decode(historyToken);
        Controller controller = (Controller) controllers.get(token.controller);
        if (controller != null)
            return controller.execute(token.action, token.value);
        else
            return false;
    }
}
