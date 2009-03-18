package org.ourproject.kune.platf.client.shortcuts;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;

public class ShortcutRegister {

    @SuppressWarnings("unchecked")
    private final HashMap<ShortcutDescriptor, ActionItem> shortcuts;

    @SuppressWarnings("unchecked")
    public ShortcutRegister() {
        shortcuts = new HashMap<ShortcutDescriptor, ActionItem>();
    }

    @SuppressWarnings("unchecked")
    public ActionItem get(Event event) {
        if (event.getTypeInt() == Event.ONKEYDOWN) {
            int modifiers = KeyboardListenerCollection.getKeyboardModifiers(event);
            boolean fnKey = (event.getKeyCode() >= Keyboard.KEY_F2 && event.getKeyCode() <= Keyboard.KEY_F12);
            if (modifiers != 0 || fnKey) {
                ShortcutDescriptor shortcut = new ShortcutDescriptor(event.getKeyCode(), modifiers);
                return get(shortcut);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public ActionItem get(ShortcutDescriptor shortcut) {
        ActionItem actionItem = shortcuts.get(shortcut);
        if (actionItem != null) {
            Log.debug("Shortcut pressed" + actionItem.getAction().getShortcut());
        }
        return actionItem;
    }

    @SuppressWarnings("unchecked")
    public void put(ShortcutDescriptor shortcut, ActionItem actionItem) {
        if (shortcuts.get(shortcut) != null) {
            Log.warn("Shortcut" + shortcut + " already registered");
        }
        shortcuts.put(shortcut, actionItem);
    }

    @SuppressWarnings("unchecked")
    public void put(ShortcutDescriptor shortcut, final Listener0 listener) {
        ActionDescriptor descriptor = new ActionDescriptor(AccessRolDTO.Viewer, new Listener0() {
            public void onEvent() {
                listener.onEvent();
            }
        });
        ActionItem item = new ActionItem(descriptor, null);
        put(shortcut, item);
    }

}
