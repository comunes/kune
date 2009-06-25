package org.ourproject.kune.platf.client.shortcuts;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.Shortcut;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class GlobalShortcutRegister extends InputMap {

    private final NativePreviewHandler eventHandler;
    private HandlerRegistration handlerReg;

    public GlobalShortcutRegister() {
        super();
        eventHandler = new NativePreviewHandler() {
            public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPrev) {
                if (nativeEventPrev.getTypeInt() == Event.ONKEYDOWN) {
                    final NativeEvent nativeEvent = nativeEventPrev.getNativeEvent();
                    final Event event = Event.as(nativeEvent);
                    final AbstractAction action = get(Shortcut.getShortcut(event.getCtrlKey(), event.getAltKey(),
                            event.getShiftKey(), event.getShiftKey(), (char) event.getKeyCode()));
                    if (action != null) {
                        Log.debug("Not propagating event");
                        nativeEvent.stopPropagation();
                        action.actionPerformed(new ActionEvent(nativeEvent.getCurrentEventTarget(), event));
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
