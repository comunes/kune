package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ActionShortcutTest {

    @Test
    public void altS() {
        ShortcutDescriptor shortcut = new ShortcutDescriptor(false, true, false, 'S', null);
        assertEquals(" (Alt+S)", shortcut.toString());
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Before
    public void before() {
        new Resources(new I18nTranslationServiceMocked());
    }

    @Test
    public void ctrlComa() {
        ShortcutDescriptor shortcut = new ShortcutDescriptor(true, ',');
        assertEquals(" (Ctrl+,)", shortcut.toString());
        assertTrue(shortcut.is(',', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is(',', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is(',', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlS() {
        ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 's');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('s', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlSCapital() {
        ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlShiftS() {
        ShortcutDescriptor shortcut = new ShortcutDescriptor(true, false, true, 'S', null);
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString());
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_SHIFT | KeyboardListener.MODIFIER_CTRL));
    }
}
