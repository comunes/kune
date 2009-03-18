package org.ourproject.kune.platf.client.shortcuts;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

public class GlobalShortcutRegister extends ShortcutRegister {

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
