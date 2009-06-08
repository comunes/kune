package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;

public class ActionShortcutTest {

    @Test
    public void altS() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(false, true, false, 'S', null);
        assertEquals(" (Alt+S)", shortcut.toString());
        assertTrue(shortcut.is('S', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_SHIFT));
    }

    @Before
    public void before() {
        new Resources(new I18nTranslationServiceMocked());
    }

    @Test
    public void ctrlComa() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, ',');
        assertEquals(" (Ctrl+,)", shortcut.toString());
        assertTrue(shortcut.is(',', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is(',', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is(',', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlS() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 's');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('s', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is('s', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is('s', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlSCapital() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('S', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlShiftS() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, false, true, 'S', null);
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString());
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_ALT));
        assertTrue(shortcut.is('S', Keyboard.MODIFIER_SHIFT | Keyboard.MODIFIER_CTRL));
    }
}
