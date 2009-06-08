package org.ourproject.kune.platf.client.shortcuts;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class GlobalShortcutRegister extends ShortcutRegister {

    private final NativePreviewHandler eventHandler;
    private HandlerRegistration handlerReg;

    public GlobalShortcutRegister(final ActionManager actionManager) {
        super();
        eventHandler = new NativePreviewHandler() {
            @SuppressWarnings("unchecked")
            public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPrev) {
                final NativeEvent event = nativeEventPrev.getNativeEvent();
                if (nativeEventPrev.getAssociatedType().equals(KeyDownEvent.getType())) {
                    final ActionItem actionItem = get(event);
                    if (actionItem != null) {
                        // Log.debug("Not propagating event");
                        event.stopPropagation();
                        actionManager.doAction(actionItem);
                        nativeEventPrev.cancel();
                    }
                }
            }
        };
    }

    public void disable() {
        if (handlerReg != null) {
            handlerReg.removeHandler();
        }
    }

    public void enable() {
        handlerReg = Event.addNativePreviewHandler(eventHandler);
    }
}
