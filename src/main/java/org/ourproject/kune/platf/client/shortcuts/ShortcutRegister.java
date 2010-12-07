package org.ourproject.kune.platf.client.shortcuts;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.dom.client.NativeEvent;

public class ShortcutRegister {

    @SuppressWarnings("rawtypes")
    private final HashMap<ShortcutDescriptor, ActionItem> shortcuts;

    @SuppressWarnings("rawtypes")
    public ShortcutRegister() {
        shortcuts = new HashMap<ShortcutDescriptor, ActionItem>();
    }

    @SuppressWarnings({ "rawtypes" })
    public ActionItem get(final NativeEvent event) {
        final int modifiers = KeyStroke.getKeyboardModifiers(event);
        final boolean fnKey = (event.getKeyCode() >= Keyboard.KEY_F2 && event.getKeyCode() <= Keyboard.KEY_F12);
        if (modifiers != 0 || fnKey) {
            final ShortcutDescriptor shortcut = new ShortcutDescriptor(event.getKeyCode(), modifiers);
            return get(shortcut);
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public ActionItem get(final ShortcutDescriptor shortcut) {
        final ActionItem actionItem = shortcuts.get(shortcut);
        if (actionItem != null) {
            Log.debug("Shortcut pressed" + actionItem.getAction().getShortcut());
        }
        return actionItem;
    }

    @SuppressWarnings("rawtypes")
    public void put(final ShortcutDescriptor shortcut, final ActionItem actionItem) {
        if (shortcuts.get(shortcut) != null) {
            Log.warn("Shortcut" + shortcut + " already registered");
        }
        shortcuts.put(shortcut, actionItem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void put(final ShortcutDescriptor shortcut, final Listener0 listener) {
        final ActionDescriptor descriptor = new ActionDescriptor(AccessRolDTO.Viewer, new Listener0() {
            public void onEvent() {
                listener.onEvent();
            }
        });
        final ActionItem item = new ActionItem(descriptor, null);
        put(shortcut, item);
    }

}
