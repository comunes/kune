package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ActionShortcutTest {

    private I18nTranslationServiceMocked i18n;

    @Test
    public void altS() {
        ActionShortcut shortcut = new ActionShortcut(true, false, false, 'S');
        assertEquals(" (Alt+S)", shortcut.toString(i18n));
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Before
    public void before() {
        i18n = new I18nTranslationServiceMocked();
    }

    @Test
    public void ctrl_s() {
        ActionShortcut shortcut = new ActionShortcut(true, 's');
        assertEquals(" (Ctrl+S)", shortcut.toString(i18n));
        assertTrue(shortcut.is('s', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlS() {
        ActionShortcut shortcut = new ActionShortcut(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString(i18n));
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlShiftS() {
        ActionShortcut shortcut = new ActionShortcut(false, true, true, 'S');
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString(i18n));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_SHIFT | KeyboardListener.MODIFIER_CTRL));
    }
}
