package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ActionShortcut {

    private static boolean has(int modifiers, int modifier) {
        return ((modifiers & modifier) == modifier);
    }

    private final boolean alt;
    private final boolean ctrl;
    private final boolean shift;
    private final char key;
    private final String keyName;

    public ActionShortcut(boolean alt, boolean ctrl, boolean shift, char key, String keyName) {
        this.alt = alt;
        this.ctrl = ctrl;
        this.shift = shift;
        this.key = key;
        this.keyName = keyName;
    }

    public ActionShortcut(boolean ctrl, boolean shift, char key) {
        this(false, ctrl, shift, key, null);
    }

    public ActionShortcut(boolean ctrl, boolean shift, char key, String keyName) {
        this(false, ctrl, shift, key, keyName);
    }

    public ActionShortcut(boolean ctrl, char key) {
        this(false, ctrl, false, key, null);
    }

    public ActionShortcut(boolean ctrl, char key, String keyName) {
        this(false, ctrl, false, key, keyName);
    }

    public ActionShortcut(char key, int modifiers) {
        this(has(modifiers, KeyboardListener.MODIFIER_ALT), has(modifiers, KeyboardListener.MODIFIER_CTRL), has(
                modifiers, KeyboardListener.MODIFIER_SHIFT), key, null);
    }

    public ActionShortcut(char key, int modifiers, String keyName) {
        this(has(modifiers, KeyboardListener.MODIFIER_ALT), has(modifiers, KeyboardListener.MODIFIER_CTRL), has(
                modifiers, KeyboardListener.MODIFIER_SHIFT), key, keyName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ActionShortcut other = (ActionShortcut) obj;
        if (alt != other.alt) {
            return false;
        }
        if (ctrl != other.ctrl) {
            return false;
        }
        if (key != other.key) {
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
        result = prime * result + key;
        result = prime * result + (shift ? 1231 : 1237);
        return result;
    }

    public boolean is(char keyCode, int modifiers) {
        return (keyCode == keyCode && same(modifiers, KeyboardListener.MODIFIER_ALT, alt)
                && same(modifiers, KeyboardListener.MODIFIER_CTRL, ctrl) && same(modifiers,
                KeyboardListener.MODIFIER_SHIFT, shift));
    }

    public boolean same(int modifiers, int modifier, boolean keyValue) {
        return (has(modifiers, modifier) == keyValue);
    }

    public String toString(I18nTranslationService i18n) {
        String s = " (";
        s += sKey(alt, "Alt", i18n);
        s += sKey(ctrl, "Ctrl", i18n);
        s += sKey(shift, "Shift", i18n);
        s += keyName != null ? translateKey(keyName, i18n) + ")" : String.valueOf(key).toUpperCase() + ")";
        return s;
    }

    private String sKey(boolean key, String specialKeyName, I18nTranslationService i18n) {
        return key ? translateKey(specialKeyName, i18n) + "+" : "";
    }

    private String translateKey(String keyNameToTranslate, I18nTranslationService i18n) {
        return i18n.tWithNT(keyNameToTranslate, "The '" + keyNameToTranslate + "' keyboard key");
    }

}
