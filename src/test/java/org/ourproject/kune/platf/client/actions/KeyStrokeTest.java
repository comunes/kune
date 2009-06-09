package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;

public class KeyStrokeTest {

    @Test
    public void altS() {
        final KeyStroke key = KeyStroke.getKeyStroke('S', Keyboard.MODIFIER_ALT);
        assertEquals(" (Alt+S)", key.toString());
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

    @Test
    public void equalsTest() {
        final KeyStroke key1 = KeyStroke.getKeyStroke(new Character('B'), Keyboard.MODIFIER_ALT);
        final KeyStroke key2 = KeyStroke.getKeyStroke(new Character('B'), Keyboard.MODIFIER_ALT);
        final KeyStroke key3 = KeyStroke.getKeyStroke(new Character('b'), Keyboard.MODIFIER_ALT);
        assertEquals(key1, key2);
        assertEquals(key1, key3);
    }
}