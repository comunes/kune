package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ActionShortcut {

    final boolean alt;
    final boolean ctrl;
    final boolean shift;
    final char key;

    public ActionShortcut(boolean alt, boolean ctrl, boolean shift, char key) {
        this.alt = alt;
        this.ctrl = ctrl;
        this.shift = shift;
        this.key = key;
    }

    public ActionShortcut(boolean ctrl, boolean shift, char key) {
        this(false, ctrl, shift, key);
    }

    public ActionShortcut(boolean ctrl, char key) {
        this(false, ctrl, false, key);
    }

    public boolean is(char keyCode, int modifiers) {
        return (keyCode == keyCode && same(modifiers, KeyboardListener.MODIFIER_ALT, alt)
                && same(modifiers, KeyboardListener.MODIFIER_CTRL, ctrl) && same(modifiers,
                KeyboardListener.MODIFIER_SHIFT, shift));
    }

    public boolean same(int modifiers, int modifier, boolean keyValue) {
        return (((modifiers & modifier) == modifier) == keyValue);
    }

    public String toString(I18nTranslationService i18n) {
        String s = " (";
        s += sKey(alt, "Alt", i18n);
        s += sKey(ctrl, "Ctrl", i18n);
        s += sKey(shift, "Shift", i18n);
        s += String.valueOf(key).toUpperCase() + ")";
        return s;
    }

    private String sKey(boolean key, String keyName, I18nTranslationService i18n) {
        return key ? i18n.tWithNT(keyName, "The '" + keyName + "' keyboard key") + "+" : "";
    }

}
