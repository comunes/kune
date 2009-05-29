package org.ourproject.kune.platf.client.shortcuts;

import org.ourproject.kune.platf.client.i18n.Resources;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ShortcutDescriptor {

    private static final String NO_KEYNAME = "nokeyname";

    private static boolean has(final int modifiers, final int modifier) {
        return ((modifiers & modifier) == modifier);
    }

    private final boolean alt;
    private final boolean ctrl;
    private final boolean shift;
    private final int keycode;
    private final String keyName;

    public ShortcutDescriptor(final boolean ctrl, final boolean alt, final boolean shift, final int key) {
        this(ctrl, alt, shift, key, NO_KEYNAME);
    }

    public ShortcutDescriptor(final boolean ctrl, final boolean alt, final boolean shift, final int key,
            final String keyName) {
        this.alt = alt;
        this.ctrl = ctrl;
        this.shift = shift;
        this.keycode = key;
        if (keyName == null) {
            this.keyName = NO_KEYNAME;
        } else {
            this.keyName = keyName;
        }
    }

    public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key) {
        this(ctrl, false, shift, key, NO_KEYNAME);
    }

    public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key, final String keyName) {
        this(ctrl, false, shift, key, keyName);
    }

    public ShortcutDescriptor(final boolean ctrl, final int key) {
        this(ctrl, false, false, key, NO_KEYNAME);
    }

    public ShortcutDescriptor(final boolean ctrl, final int keycode, final String keyName) {
        this(ctrl, false, false, keycode, keyName);
    }

    public ShortcutDescriptor(final int keycode, final int modifiers) {
        this(has(modifiers, KeyboardListener.MODIFIER_CTRL), has(modifiers, KeyboardListener.MODIFIER_ALT), has(
                modifiers, KeyboardListener.MODIFIER_SHIFT), keycode, NO_KEYNAME);
    }

    public ShortcutDescriptor(final int keycode, final int modifiers, final String keyName) {
        this(has(modifiers, KeyboardListener.MODIFIER_CTRL), has(modifiers, KeyboardListener.MODIFIER_ALT), has(
                modifiers, KeyboardListener.MODIFIER_SHIFT), keycode, keyName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShortcutDescriptor other = (ShortcutDescriptor) obj;
        if (alt != other.alt) {
            return false;
        }
        if (ctrl != other.ctrl) {
            return false;
        }
        if (keycode != other.keycode) {
            return false;
        }
        if (shift != other.shift) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (alt ? 1231 : 1237);
        result = prime * result + (ctrl ? 1231 : 1237);
        result = prime * result + keycode;
        result = prime * result + (shift ? 1231 : 1237);
        return result;
    }

    public boolean is(final char keyCode, final int modifiers) {
        return (this.keycode == keyCode && same(modifiers, KeyboardListener.MODIFIER_ALT, alt)
                && same(modifiers, KeyboardListener.MODIFIER_CTRL, ctrl) && same(modifiers,
                KeyboardListener.MODIFIER_SHIFT, shift));
    }

    public boolean same(final int modifiers, final int modifier, final boolean keyValue) {
        return (has(modifiers, modifier) == keyValue);
    }

    @Override
    public String toString() {
        String s = " (";
        s += sKey(alt, "Alt");
        s += sKey(ctrl, "Ctrl");
        s += sKey(shift, "Shift");
        s += !keyName.equals(NO_KEYNAME) ? translateKey(keyName) + ")" : ("" + (char) keycode).toUpperCase() + ")";
        return s;
    }

    private String sKey(final boolean key, final String specialKeyName) {
        return key ? translateKey(specialKeyName) + "+" : "";
    }

    private String translateKey(final String keyNameToTranslate) {
        return Resources.i18n.tWithNT(keyNameToTranslate, "The '" + keyNameToTranslate + "' keyboard key");
    }

}
