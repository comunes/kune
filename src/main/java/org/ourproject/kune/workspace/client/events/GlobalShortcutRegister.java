package org.ourproject.kune.workspace.client.events;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionShortcutRegister;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

public class GlobalShortcutRegister extends ActionShortcutRegister {

    private final EventPreview eventPreview;

    public GlobalShortcutRegister(final ActionManager actionManager) {
        eventPreview = new EventPreview() {
            @SuppressWarnings("unchecked")
            public boolean onEventPreview(Event event) {
                ActionItem actionItem = get(event);
                if (actionItem != null) {
                    // Log.debug("Not propagating event");
                    DOM.eventPreventDefault(event);
                    actionManager.doAction(actionItem);
                    return false;
                }
                return true;
            }
        };
    }

    public void disable() {
        DOM.removeEventPreview(eventPreview);
    }

    public void enable() {
        DOM.addEventPreview(eventPreview);
    }
}
