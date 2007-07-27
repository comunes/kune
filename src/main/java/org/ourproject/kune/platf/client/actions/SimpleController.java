package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

public class SimpleController implements Controller {
    private HashMap actions;

    public SimpleController() {
        this.actions = new HashMap();
    }

    public boolean execute(String actionName, String value) {
        Action action = (Action) actions.get(actionName);
        if (action != null) {
            inject(action);
            action.execute(value);
            return true;
        }
        return false;
    }

    /** template method **/
    public void inject(Action action) {
    }

    public void addAction(String name, Action action) {
        actions.put(name, action);
    }

}
