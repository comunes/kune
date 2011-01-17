package cc.kune.common.client.shortcuts;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ChangeableObject;
import cc.kune.common.client.actions.InputMap;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class DefaultGlobalShortcutRegister extends InputMap implements GlobalShortcutRegister {

    private final NativePreviewHandler eventHandler;
    private HandlerRegistration handlerReg;

    public DefaultGlobalShortcutRegister() {
        super();
        eventHandler = new NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPrev) {
                if (nativeEventPrev.getTypeInt() == Event.ONKEYDOWN) {
                    final NativeEvent nativeEvent = nativeEventPrev.getNativeEvent();
                    final Event event = Event.as(nativeEvent);
                    final AbstractAction action = get(Shortcut.getShortcut(event.getCtrlKey(), event.getAltKey(),
                            event.getShiftKey(), event.getShiftKey(), (char) event.getKeyCode()));
                    if (action != null) {
                        GWT.log("Not propagating event");
                        nativeEvent.stopPropagation();
                        action.actionPerformed(new ActionEvent(nativeEvent.getCurrentEventTarget(), event));
                        nativeEventPrev.cancel();
                    }
                }
            }
        };
        enableImpl();
    }

    @Override
    public void disable() {
        if (handlerReg != null) {
            handlerReg.removeHandler();
        }
    }

    @Override
    public void enable() {
        enableImpl();
    }

    private void enableImpl() {
        handlerReg = Event.addNativePreviewHandler(eventHandler);
    }

    @Override
    public void put(final KeyStroke keystroke, final AbstractAction action) {
        final ChangeableObject previous = super.get(keystroke);
        if (previous != null) {
            GWT.log("Shortcut already registed by: " + previous + "(overriding)");
        }
        super.put(keystroke, action);
    }
}
