package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;

public class ActionShortcutRegister {

    @SuppressWarnings("unchecked")
    private final HashMap<ActionShortcut, ActionItem> shortcuts;

    @SuppressWarnings("unchecked")
    public ActionShortcutRegister() {
        shortcuts = new HashMap<ActionShortcut, ActionItem>();
    }

    @SuppressWarnings("unchecked")
    public ActionItem get(ActionShortcut shortcut) {
        return shortcuts.get(shortcut);
    }

    @SuppressWarnings("unchecked")
    public ActionItem get(Event event) {
        int modifiers = KeyboardListenerCollection.getKeyboardModifiers(event);
        if (event.getTypeInt() == Event.ONKEYDOWN && modifiers != 0) {
            ActionShortcut shortcut = new ActionShortcut((char) event.getKeyCode(), modifiers);
            Log.debug("Shortcut pressed" + shortcut.toString());
            return shortcuts.get(shortcut);
        }
        // if (modifiers != 0) {
        // Log.debug("Not action associated with this shortcut");
        // }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void put(ActionShortcut shortcut, ActionItem actionItem) {
        if (shortcuts.get(shortcut) != null) {
            Log.warn("Shortcut" + shortcut + " already registered");
        }
        shortcuts.put(shortcut, actionItem);
    }

    @SuppressWarnings("unchecked")
    public void put(ActionShortcut shortcut, final Listener0 listener) {
        ActionDescriptor descriptor = new ActionDescriptor(AccessRolDTO.Viewer, new Listener0() {
            public void onEvent() {
                listener.onEvent();
            }
        });
        ActionItem item = new ActionItem(descriptor, null);
        put(shortcut, item);
    }

}
